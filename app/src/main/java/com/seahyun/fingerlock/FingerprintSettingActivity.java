package com.seahyun.fingerlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FingerprintSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = FingerprintSettingActivity.class.getSimpleName();
    private ListView fingersetting_listView;
    private FingerprintListviewAdapter fingerprintListviewAdapter;
    private ArrayList<FingerprintListviewItem> fingerprintList; // pref에 저장된 fingerprint List
    private ArrayList<FingerprintListviewItem> showList; // 사용자에게 보여줄 fingerprint List
    private ArrayList<String> fingerIDInSys; // 시스템에서 받아온 fingerID List
    private String fingeredit_tempID = ""; // 수정 할 fingerID

    private Button complete;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("지문 환경설정");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));
        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));
        setContentView(R.layout.fingerprint_setting);

        complete = (Button)findViewById(R.id.setting_complete);
        complete.setOnClickListener((View.OnClickListener)FingerprintSettingActivity.this);

        fingerprintList = new ArrayList<FingerprintListviewItem>();
        showList = new ArrayList<FingerprintListviewItem>();
        fingerIDInSys = new ArrayList<String>();

        fingersetting_listView = (ListView) findViewById(R.id.fingersetting_listView);
        getFingerIdInSys(); // fingerIDInSys에 시스템에 저장 된 fingerID 불러오기
        getAllPreferences(); // fingerprintList에 pref에 저장 된 fingerprint List 불러오기

        fingerprintListviewAdapter = new FingerprintListviewAdapter(FingerprintSettingActivity.this, showList); // 실제로 사용자에게 보여줄 showList 를 붙인다.
        fingersetting_listView.setAdapter(fingerprintListviewAdapter);
        compareFinger(); //

        fingersetting_listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG,"EDIT MODE START");

                        fingeredit_tempID = showList.get(position).getFingerprint_id();

                        String fingeredit_fingername = showList.get(position).getFingerprint_name();
                        String fingeredit_appname = showList.get(position).getApp_name();


                        Log.d("BEFORE EDIT FNAME>>", fingeredit_fingername);
                        Log.d("BEFORE EDIT ANAME>>", fingeredit_appname);
                        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("fingeredit_fingername", fingeredit_fingername);
                        editor.putString("fingeredit_appname", fingeredit_appname);
                        editor.putBoolean("edit_mode",true); // EDIT 버튼을 눌렀음을 알림
                        editor.commit();

                        Intent intent = new Intent(FingerprintSettingActivity.this, FingerprintEditActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.setting_complete){
            Log.d(TAG, "완료버튼 클릭");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void getFingerIdInSys() {
        try {

            FingerprintManager fingerprintManager = (FingerprintManager) FingerprintSettingActivity.this.getSystemService(Context.FINGERPRINT_SERVICE);
            Method method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
            Object obj = method.invoke(fingerprintManager);
            Class<?> clazz = Class.forName("android.hardware.fingerprint.Fingerprint");
            Method getFingerId = clazz.getDeclaredMethod("getFingerId");
            if (obj != null) {
                for (int i = 0; i < ((List) obj).size(); i++) {
                    Object item = ((List) obj).get(i);
                    if (null == item) {
                        continue;
                    }
                    String str = getFingerId.invoke(item).toString();
                    fingerIDInSys.add(str);
                    Log.d("aaaaaa", "등록된 fingerId: " + getFingerId.invoke(item));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // 값(All Data) 불러와서 fingerprintlist에 저장
    private void getAllPreferences() {
        SharedPreferences pref1 = getSharedPreferences("fingerprint1", MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences("fingerprint2", MODE_PRIVATE);

        // 현재 저장된 List를 비운다
        fingerprintList.clear();

        Map<String, ?> allEntries1 = pref1.getAll();
        Map<String, ?> allEntries2 = pref2.getAll();

        for (Map.Entry<String, ?> entry1 : allEntries1.entrySet()) {
            for (Map.Entry<String, ?> entry2 : allEntries2.entrySet()) {
                if (entry1.getKey().equals(entry2.getKey())) {
                    Log.d("Preferences에 저장된 List Finger:>>", entry1.getKey() + ", Fname: " + entry1.getValue().toString() + ",Aname: " + entry2.getValue().toString());
                    fingerprintList.add(new FingerprintListviewItem(entry1.getKey(), entry1.getValue().toString(), entry2.getValue().toString()));
                }
            }
        }
    }

    public void compareFinger() {
        showList.clear();

        //fingerprintList.clear();
        if(fingerprintList.size() != 0){
            for (int i = 0; i < fingerIDInSys.size(); i++) {
                for (int j = 0; j < fingerprintList.size(); j++) {
                    // pref에서 받아온 fingerList에서, 시스템에서 받아온 fingerID와 같은 ID를 가진 fingerprint는
                    // showList에 추가한다.
                    if (fingerIDInSys.get(i).toString().equals(fingerprintList.get(j).getFingerprint_id())) {
                        Log.d("compareFinger()","시스템과 같은 fingerID의 정보 갱신");
                        showList.add(new FingerprintListviewItem(fingerIDInSys.get(i).toString(), fingerprintList.get(j).getFingerprint_name(), fingerprintList.get(j).getApp_name()));
                        savePreferences(showList.get(i).getFingerprint_id(), showList.get(i).getFingerprint_name(), showList.get(i).getApp_name());
                    }
                    // TODO:만약 pref에는 있지만 시스템에는 없는 fingerID라면
                    // 시스템에서 삭제된 fingerprint이므로
                    // pref에서 해당 fingerprint를 제거

                    // TODO:만약 시스템에는 있지만 pref에는 없는 fingerID라면
                    // 시스템에서 추가된 fingerprint이므로
                    // pref에서 해당 fingerprint를 생성, - Fname과 Aname은 비워둔다
                }
            }
        }
        else
        {
            // 어플을 처음 실행한 경우
                Log.d("frist start", "get saved fingerID");
                for (int i = 0; i < fingerIDInSys.size(); i++) {
                    showList.add(new FingerprintListviewItem(fingerIDInSys.get(i).toString(), "손가락" + String.valueOf(i + 1)));
                    savePreferences(showList.get(i).getFingerprint_id(), showList.get(i).getFingerprint_name(), showList.get(i).getApp_name());
            }
        }

        // TODO: 실행할 어플 이미지 붙이기

        fingerprintListviewAdapter.notifyDataSetChanged();
    }

//    // 값(Key Data) 불러오기
//    private void getPreferences(String key) {
//        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
//        pref.getString(key, "");//defValue is null
//    }
//
//    // 값(Key Data) 삭제하기
//    private void removePreferences(String key) {
//        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.remove(key);
//        editor.commit();
//    }
//    // 값(All Data) 삭제하기
//    private void removeAllPreferences() {
//        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.clear();
//        editor.commit();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // save the FingerprintListviewItem list to preference
//        for(int i=0;i<savedItemList.size();i++){
//            editor.putString(savedItemList.get(i).getFingerprint_name(),savedItemList.get(i).getApp_name());
//            editor.commit();
//        }


    // 값 저장하기
    private void savePreferences(String Id,String Fname, String Aname) {
        SharedPreferences pref1 = getSharedPreferences("fingerprint1", MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences("fingerprint2", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.putString(Id, Fname);
        editor1.commit();
        SharedPreferences.Editor editor2 = pref2.edit();
        editor2.putString(Id, Aname);
        editor2.commit();
    }


    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
        if(prefs.getBoolean("edit_mode",false)==true) {
            Log.d(TAG,"EDIT MODE END");
            if (prefs.getBoolean("complete_edit", false) == true && !fingeredit_tempID.equals("")) {
                Log.d(TAG, "EDIT COMPLETE!");
                String fingeredit_fingername = prefs.getString("fingeredit_fingername", "");
                String fingeredit_appname = prefs.getString("fingeredit_appname", ""); // 나중에 null 인거 처리해줘야댐!

                Log.d("EDITED fingername>>", fingeredit_fingername);
                Log.d("EDITED appname>>", fingeredit_appname);

                savePreferences(fingeredit_tempID, fingeredit_fingername, fingeredit_appname);

                getAllPreferences();
                compareFinger();

                // prefs 초기화
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("complete_edit", false);
                editor.putBoolean("edit_mode",false);
                editor.putString("fingeredit_fingername", "");
                editor.putString("fingeredit_appname", "");
                editor.commit();
            } else if (prefs.getBoolean("complete_edit", false) == false && !fingeredit_tempID.equals("")) {
                Log.d(TAG, "NOT SAVE EDIT");
                fingeredit_tempID = "";
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("edit_mode",false);
            } else {
                Log.d(TAG, "그밖의상황!!!!!!!");
            }
        }
        // tempId 초기화
        fingeredit_tempID = "";
    }

}

