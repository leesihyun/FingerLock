package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by seahyun on 2017-09-23.
 */

public class TouchTabFiveActivity extends AppCompatActivity implements View.OnClickListener {

    public TouchTabFiveActivity(){};

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("터치탭 어플 등록");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.touch_tab_five);

    }
    @Override
    public void onClick(View v) {
        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        switch (v.getId()) {
            case R.id.button5_1:
                prefsEditor.putBoolean("select1", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button5_2:
                prefsEditor.putBoolean("select2", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button5_3:
                prefsEditor.putBoolean("select3", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button5_4:
                prefsEditor.putBoolean("select4", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button5_5:
                prefsEditor.putBoolean("select5", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.app_registration:
                finish();
                break;
            case R.id.app_cancel:
                PrefInit();
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button button5_1 = (Button) findViewById(R.id.button5_1);
        Button button5_2 = (Button) findViewById(R.id.button5_2);
        Button button5_3 = (Button) findViewById(R.id.button5_3);
        Button button5_4 = (Button) findViewById(R.id.button5_4);
        Button button5_5 = (Button) findViewById(R.id.button5_5);

        Button register = (Button) findViewById(R.id.app_registration);
        Button cancel = (Button) findViewById(R.id.app_cancel);

        button5_1.setOnClickListener(this);
        button5_2.setOnClickListener(this);
        button5_3.setOnClickListener(this);
        button5_4.setOnClickListener(this);
        button5_5.setOnClickListener(this);

        register.setOnClickListener(this);
        cancel.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("Message", MODE_PRIVATE);
        button5_1.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
        button5_2.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
        button5_3.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
        button5_4.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
        button5_5.setText(prefs.getString("msg5", "여기에 어플을 등록합니다"));
    }

    public void PrefInit(){
        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        prefsEditor.putBoolean("select1", false);
        prefsEditor.putBoolean("select2", false);
        prefsEditor.putBoolean("select3", false);
        prefsEditor.putBoolean("select4", false);
        prefsEditor.putBoolean("select5", false);
        prefsEditor.commit();


        SharedPreferences prefs2 = getSharedPreferences("Message", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs2.edit();

        prefsEditor2.putString("msg1", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg2", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg3", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg4", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg5", "여기에 어플을 등록합니다");
        prefsEditor2.commit();

        SharedPreferences prefs3 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor3 = prefs3.edit();

        prefsEditor3.putString("name1","");
        prefsEditor3.putString("name2","");
        prefsEditor3.putString("name3","");
        prefsEditor3.putString("name4","");
        prefsEditor3.putString("name5","");

    }
}
