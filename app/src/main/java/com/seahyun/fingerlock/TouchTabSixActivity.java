package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by seahyun on 2017-09-23.
 */

public class TouchTabSixActivity extends AppCompatActivity implements View.OnClickListener {


    public TouchTabSixActivity(){};

    public String[] Message = new String[6];
    public String[] PackageName = new String[6];

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("터치탭 어플 등록");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.touch_tab_six);

        //기존의 바로가기 설정 어플 목록 가져오기
        applicationList();

    }
    @Override
    public void onClick(View v) {
        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        switch (v.getId()) {
            case R.id.button6_1:
                prefsEditor.putBoolean("select1", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button6_2:
                prefsEditor.putBoolean("select2", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button6_3:
                prefsEditor.putBoolean("select3", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button6_4:
                prefsEditor.putBoolean("select4", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button6_5:
                prefsEditor.putBoolean("select5", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button6_6:
                prefsEditor.putBoolean("select6", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.app_registration:
                finish();
                break;
            case R.id.app_cancel:
                returnApplicationList();
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button button6_1 = (Button) findViewById(R.id.button6_1);
        Button button6_2 = (Button) findViewById(R.id.button6_2);
        Button button6_3 = (Button) findViewById(R.id.button6_3);
        Button button6_4 = (Button) findViewById(R.id.button6_4);
        Button button6_5 = (Button) findViewById(R.id.button6_5);
        Button button6_6 = (Button) findViewById(R.id.button6_6);

        Button register = (Button) findViewById(R.id.app_registration);
        Button cancel = (Button) findViewById(R.id.app_cancel);

        button6_1.setOnClickListener(this);
        button6_2.setOnClickListener(this);
        button6_3.setOnClickListener(this);
        button6_4.setOnClickListener(this);
        button6_5.setOnClickListener(this);
        button6_6.setOnClickListener(this);

        register.setOnClickListener(this);
        cancel.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("Message", MODE_PRIVATE);
        button6_1.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
        button6_2.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
        button6_3.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
        button6_4.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
        button6_5.setText(prefs.getString("msg5", "여기에 어플을 등록합니다"));
        button6_6.setText(prefs.getString("msg6", "여기에 어플을 등록합니다"));

    }

    public void applicationList(){

        SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);

        SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);

        Message[0] = prefs3.getString("msg1","여기에 어플을 등록합니다");
        Message[1] = prefs3.getString("msg2","여기에 어플을 등록합니다");
        Message[2] = prefs3.getString("msg3","여기에 어플을 등록합니다");
        Message[3] = prefs3.getString("msg4","여기에 어플을 등록합니다");
        Message[4] = prefs3.getString("msg5","여기에 어플을 등록합니다");
        Message[5] = prefs3.getString("msg6","여기에 어플을 등록합니다");

        PackageName[0] = prefs4.getString("name1","");
        PackageName[1] = prefs4.getString("name2","");
        PackageName[2] = prefs4.getString("name3","");
        PackageName[3] = prefs4.getString("name4","");
        PackageName[4] = prefs4.getString("name5","");
        PackageName[5] = prefs4.getString("name6","");

        for(int i=0; i<6; i++){
            Log.d("TouchTabSixActivity", "Message >> "+Message[i]);
            Log.d("TouchTabSixActivity", "PackageName >> "+PackageName[i]);
        }
    }

    public void returnApplicationList(){

        SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs3.edit();

        SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor3 = prefs4.edit();

        prefsEditor2.putString("msg1", Message[0]);
        prefsEditor2.putString("msg2", Message[1]);
        prefsEditor2.putString("msg3", Message[2]);
        prefsEditor2.putString("msg4", Message[3]);
        prefsEditor2.putString("msg5", Message[4]);
        prefsEditor2.putString("msg6", Message[5]);

        prefsEditor2.commit();

        prefsEditor3.putString("name1", PackageName[0]);
        prefsEditor3.putString("name2", PackageName[1]);
        prefsEditor3.putString("name3", PackageName[2]);
        prefsEditor3.putString("name4", PackageName[3]);
        prefsEditor3.putString("name5", PackageName[4]);
        prefsEditor3.putString("name6", PackageName[5]);

        prefsEditor3.commit();


    }
}
