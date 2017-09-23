package com.seahyun.fingerlock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by seahyun on 2017-09-20.
 */

public class TouchTabSettingActivity extends AppCompatActivity implements View.OnClickListener {


    public int tab_num;

    TouchTabPreview object = new TouchTabPreview();

    //public TouchTabSettingActivity(){};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("터치탭 환경설정");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.touch_tab_setting);

        select_touch_tab();

        Button select = (Button)findViewById(R.id.application_select);
        Button preview = (Button)findViewById(R.id.preview);
        preview.setOnClickListener((View.OnClickListener)TouchTabSettingActivity.this);
        select.setOnClickListener((View.OnClickListener) TouchTabSettingActivity.this);

    }

    //터치탭 개수 설정하기
    public void select_touch_tab(){

        //tab_num 선택하지 않을 시 대화상자 띄우기
        final AlertDialog.Builder nothing_selected = new AlertDialog.Builder(this);

        nothing_selected.setTitle("아무것도 선택하지 않으셨습니다")
                .setMessage("기본 설정 값인 4개로 설정됩니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    //확인 버튼 클릭
                    public void onClick(DialogInterface dialog, int whichButton){
                        tab_num = 4;
                        putTabnum();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    //취소 버튼 클릭
                    public void onClick(DialogInterface dialog, int whichButton){
                        tab_num = 4;
                        putTabnum();
                        dialog.cancel();
                    }

                });

        //tab_num 스피너 생성
        Spinner s = (Spinner)findViewById(R.id.touch_tab_num);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(TouchTabSettingActivity.this, parent.getItemAtPosition(position)+"를 선택하셨습니다!", Toast.LENGTH_SHORT).show();
                if(parent.getItemAtPosition(position).toString().contains("4")){
                    tab_num = 4;
                }
                else if(parent.getItemAtPosition(position).toString().contains("5")){
                    tab_num = 5;
                }
                else if(parent.getItemAtPosition(position).toString().contains("6")){
                    tab_num = 6;
                }
                putTabnum();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                AlertDialog dialog = nothing_selected.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

            }
        });



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.preview:
                Intent intent = new Intent(getApplicationContext(), TouchTabPreview.class);
                startActivity(intent);
                break;
            case R.id.application_select:
                SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                int num = prefs.getInt("tab_num", 4);
                if(num == 4){
                    Intent intent2 = new Intent(getApplicationContext(), TouchTabFourActivity.class);
                    startActivity(intent2);
                }
                else if(num == 5){
                    Intent intent2 = new Intent(getApplicationContext(), TouchTabFiveActivity.class);
                    startActivity(intent2);
                }
                else if(num == 6){
                    Intent intent2 = new Intent(getApplicationContext(), TouchTabSixActivity.class);
                    startActivity(intent2);
                }
                break;
        }
    }

    public void putTabnum(){
        Log.d("Setting put 터치탭 개수 >> ", String.valueOf(tab_num));
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("tab_num", tab_num);
        prefsEditor.commit();
    }



}
