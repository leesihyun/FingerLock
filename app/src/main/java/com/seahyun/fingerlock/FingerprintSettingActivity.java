package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class FingerprintSettingActivity extends AppCompatActivity {
    Boolean finger_add = false;
    Boolean finger_edit = false;

    ListView fingersetting_listView;
    Button fingersetting_addButton;
    FingerprintListviewAdapter fingerprintListviewAdapter;
    ArrayList<FingerprintListviewItem> fingerprintList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_setting);

        fingerprintList = new ArrayList<FingerprintListviewItem>();
        fingersetting_listView = (ListView) findViewById(R.id.fingersetting_listView);

        fingerprintListviewAdapter = new FingerprintListviewAdapter(FingerprintSettingActivity.this, fingerprintList);
        fingersetting_listView.setAdapter(fingerprintListviewAdapter);
        getAllPreferences();
        fingersetting_listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("Fingerprint 수정 시작>>",fingerprintList.get(position).getFingerprint_name());
                        finger_edit = true;

                        String fingeredit_fingername = fingerprintList.get(position).getFingerprint_name();
                        String fingeredit_appname = fingerprintList.get(position).getApp_name();

                        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("fingeredit_fingername",fingeredit_fingername);
                        editor.putString("fingeredit_appname",fingeredit_appname);
                        editor.commit();

                        removePreferences(fingeredit_fingername);

                        Intent intent = new Intent(FingerprintSettingActivity.this,FingerprintEditActivity.class);
                        startActivity(intent);
                    }
                }
        );

        fingersetting_addButton = (Button) findViewById(R.id.fingersetting_addButton);
        fingersetting_addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fingerprint 추가 시작>>","");
                finger_add = true;

                Intent intent = new Intent(FingerprintSettingActivity.this, FingerprintAddActivity.class);
                startActivity(intent);
            }
        });

    }

    // 값(All Data) 불러와서 fingerprintlist에 저장
    private void getAllPreferences(){
        SharedPreferences pref = getSharedPreferences("fingerprint",MODE_PRIVATE);
        // 현재 저장된 List를 비운다
        fingerprintList.clear();

        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("저장된 List>>", entry.getKey() + ": " + entry.getValue().toString());
            fingerprintList.add(new FingerprintListviewItem(entry.getKey(), entry.getValue().toString()));
        }

        fingerprintListviewAdapter.notifyDataSetChanged();
    }

    // 값(Key Data) 불러오기
    private void getPreferences(String key) {
        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
        pref.getString(key,"");//defValue is null
    }

    // 값 저장하기
    private void savePreferences(String key,String value){
        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences(String key){
        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(All Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("fingerprint", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

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

        if(finger_add==true&&finger_edit==false) {
            Log.d("추가 완료","");
            SharedPreferences prefs = getSharedPreferences("fingeradd", MODE_PRIVATE);
            String fingeradd_fingername = prefs.getString("fingeradd_fingername", "이름 없는 지문");
            String fingeradd_appname = prefs.getString("fingeradd_appname", "실행 앱 설정안된 지문"); // 나중에 null 인거 처리해줘야댐!

            Log.d("추가된지문 fingername>>", fingeradd_fingername);
            Log.d("추가된지문 appname>>", fingeradd_appname);
            savePreferences(fingeradd_fingername, fingeradd_appname);

            getAllPreferences();
            finger_add = false;
        }

        else if(finger_add==false&&finger_edit==true){
            Log.d("수정 완료","");
            SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);

            String fingeredit_fingername = prefs.getString("fingeredit_fingername", "이름 없는 지문");
            String fingeredit_appname = prefs.getString("fingeredit_appname", "실행 앱 설정안된 지문"); // 나중에 null 인거 처리해줘야댐!

            Log.d("수정된지문 fingername>>", fingeredit_fingername);
            Log.d("수정된지문 appname>>", fingeredit_appname);
            savePreferences(fingeredit_fingername, fingeredit_appname);

            getAllPreferences();
            finger_edit = false;
        }

        else{
            Log.d("add, edit 모드 둘다, 잘못됨","");
        }
    }

    }



//
//    }



