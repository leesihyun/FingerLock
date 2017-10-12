package com.seahyun.fingerlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FingerprintSettingActivity extends AppCompatActivity {
    ListView fingersetting_listView;
    FingerprintListviewAdapter fingerprintListviewAdapter;
    ArrayList<FingerprintListviewItem> fingerprintList;
    ArrayList<FingerprintListviewItem> showList;

    String tempId = "";

    ArrayList<String> fingerIDInSys;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_setting);

        fingerprintList = new ArrayList<FingerprintListviewItem>();
        showList = new ArrayList<FingerprintListviewItem>();
        fingerIDInSys = new ArrayList<String>();

        fingersetting_listView = (ListView) findViewById(R.id.fingersetting_listView);
        getFingerIdInSys();

        getAllPreferences();

        fingerprintListviewAdapter = new FingerprintListviewAdapter(FingerprintSettingActivity.this, showList);
        fingersetting_listView.setAdapter(fingerprintListviewAdapter);
        compareFinger();



        fingersetting_listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("Fingerprint 수정 시작>>", showList.get(position).getFingerprint_name());


                        tempId = showList.get(position).getFingerprint_id();

                        String fingeredit_fingername = showList.get(position).getFingerprint_name();
                        String fingeredit_appname = showList.get(position).getApp_name();


                        Log.d("Fingerprint 수정 시작 전 이름>>", fingeredit_fingername);
                        Log.d("Fingerprint 수정 시작 전 어플>>", fingeredit_appname);
                        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("fingeredit_fingername", fingeredit_fingername);
                        editor.putString("fingeredit_appname", fingeredit_appname);
                        editor.commit();

                        Intent intent = new Intent(FingerprintSettingActivity.this, FingerprintEditActivity.class);
                        startActivity(intent);
                    }
                }
        );

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
                    Log.d("저장된 List Finger:>>", entry1.getKey() + ", Fname " + entry1.getValue().toString() + "Aname " + entry2.getValue().toString());
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
                    Log.d("fingerID : ", fingerIDInSys.get(i).toString());
                    Log.d("fingerList : ", fingerprintList.get(j).getFingerprint_id());
                    if (fingerIDInSys.get(i).toString().equals(fingerprintList.get(j).getFingerprint_id())) {
                        showList.add(new FingerprintListviewItem(fingerIDInSys.get(i).toString(), fingerprintList.get(j).getFingerprint_name(), fingerprintList.get(j).getApp_name()));
                        savePreferences(showList.get(i).getFingerprint_id(), showList.get(i).getFingerprint_name(), showList.get(i).getLaunchapp_name());
                    }
                }
            }
        }
        else
        {
            Log.d("frist start", "get saved fingerID");
            for(int i = 0; i < fingerIDInSys.size(); i++){
                showList.add(new FingerprintListviewItem(fingerIDInSys.get(i).toString(), "손가락"+String.valueOf(i+1)));
                savePreferences(showList.get(i).getFingerprint_id(), showList.get(i).getFingerprint_name(), showList.get(i).getLaunchapp_name());
            }
        }


        fingerprintListviewAdapter.notifyDataSetChanged();
    }

//    // 값(Key Data) 불러오기
//    private void getPreferences(String key) {
//        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
//        pref.getString(key, "");//defValue is null
//    }

    // 값 저장하기
    private void savePreferences(String id,String key, String value) {
        SharedPreferences pref1 = getSharedPreferences("fingerprint1", MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences("fingerprint2", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.putString(id, key);
        editor1.commit();
        SharedPreferences.Editor editor2 = pref2.edit();
        editor2.putString(id, value);
        editor2.commit();
    }
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

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);

        if (prefs.getBoolean("edit", false) == true) {
            Log.d("수정 완료", "");
            String fingeredit_fingername = prefs.getString("fingeredit_fingername", "");
            String fingeredit_appname = prefs.getString("fingeredit_appname", ""); // 나중에 null 인거 처리해줘야댐!


            Log.d("수정된지문 fingername>>", fingeredit_fingername);
            Log.d("수정된지문 appname>>", fingeredit_appname);
            savePreferences(tempId,fingeredit_fingername, fingeredit_appname);

            getAllPreferences();
            compareFinger();

            // prefs 초기화
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("edit", false);
            editor.putString("fingeredit_fingername","");
            editor.putString("fingeredit_appname","");
            editor.commit();
        }
    }

}


//
//    }



