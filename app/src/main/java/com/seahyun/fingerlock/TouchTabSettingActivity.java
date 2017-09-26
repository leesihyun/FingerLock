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
    private static String TAG = "TouchTabSettingActivity";
    TouchTabPreview object = new TouchTabPreview();
    Spinner s;


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


        Button select = (Button)findViewById(R.id.application_select);
        Button preview = (Button)findViewById(R.id.preview);
        Button set_password = (Button)findViewById(R.id.set_password);
        Button complete = (Button)findViewById(R.id.setting_complete);
        Button init = (Button)findViewById(R.id.application_init);

        preview.setOnClickListener((View.OnClickListener)TouchTabSettingActivity.this);
        select.setOnClickListener((View.OnClickListener) TouchTabSettingActivity.this);
        set_password.setOnClickListener((View.OnClickListener)TouchTabSettingActivity.this);
        complete.setOnClickListener((View.OnClickListener)TouchTabSettingActivity.this);
        init.setOnClickListener((View.OnClickListener)TouchTabSettingActivity.this);

        //tab_num 스피너 생성
        s = (Spinner)findViewById(R.id.touch_tab_num);
        SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
        Log.d(TAG, "사용자가 설정한 탭 개수 >>"+prefs.getInt("tab_num", 4));
        if(prefs.getInt("tab_num", 4)==4){
            s.setSelection(0);
        }
        else if(prefs.getInt("tab_num", 4)==5){
            s.setSelection(1);
        }
        else if(prefs.getInt("tab_num", 4) == 6){
            s.setSelection(2);
        }
        //s.setSelection(prefs.getInt("tab_num", 4));

        select_touch_tab();
    }

    //터치탭 개수 설정하기
    public void select_touch_tab(){

        SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = prefs.edit();

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Toast.makeText(TouchTabSettingActivity.this, parent.getItemAtPosition(position)+"를 선택하셨습니다!", Toast.LENGTH_SHORT).show();
                //if(parent.getItemAtPosition(position).toString().equals("선택하지 않음")){
                //    tab_num = 0;
                //    prefsEditor.putInt("tab_num", 0);
                //    prefsEditor.commit();
                    //tab_num 선택하지 않을 시 대화상자 띄우기
                    /*final AlertDialog.Builder nothing_selected = new AlertDialog.Builder(TouchTabSettingActivity.this);

                    nothing_selected.setTitle("아무것도 선택하지 않으셨습니다")
                            .setMessage("기본 설정 값인 4개로 설정됩니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                //확인 버튼 클릭
                                public void onClick(DialogInterface dialog, int whichButton){
                                    tab_num = 4;
                                    //putTabnum();
                                    prefsEditor.putInt("tab_num", 4);
                                    prefsEditor.commit();
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                //취소 버튼 클릭
                                public void onClick(DialogInterface dialog, int whichButton){
                                    tab_num = 4;
                                    prefsEditor.putInt("tab_num", 4);
                                    prefsEditor.commit();
                                    dialog.cancel();
                                }

                            });*/
                //}
                if(parent.getItemAtPosition(position).toString().contains("4")){
                    tab_num = 4;
                    prefsEditor.putInt("tab_num", 4);
                    prefsEditor.commit();
                }
                else if(parent.getItemAtPosition(position).toString().contains("5")){
                    tab_num = 5;
                    prefsEditor.putInt("tab_num", 5);
                    prefsEditor.commit();
                }
                else if(parent.getItemAtPosition(position).toString().contains("6")){
                    tab_num = 6;
                    prefsEditor.putInt("tab_num", 6);
                    prefsEditor.commit();
                }
                //putTabnum();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.preview: //미리보기 버튼
                Intent intent = new Intent(getApplicationContext(), TouchTabPreview.class);
                startActivity(intent);
                break;
            case R.id.application_select: //어플 선택하기 버튼
                SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
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
            case R.id.application_init: //어플 선택 초기화 버튼
                Toast.makeText(this, "어플 바로가기 설정이 초기화 되었습니다", Toast.LENGTH_SHORT).show();
                PrefInit();
                break;
            case R.id.set_password: //비밀번호 설정 버튼
                Log.d("버튼 입력 >> ", "비밀번호 설정");
                SharedPreferences prefs2 = getSharedPreferences("user_tab_num", MODE_PRIVATE);
                int tab_num2 = prefs2.getInt("tab_num", 4);
                Log.d(TAG, "터치탭 개수 >> "+tab_num2);
                if(tab_num2 == 4){
                    Intent intent3 = new Intent(getApplicationContext(), TouchTabFourPassword.class);
                    startActivity(intent3);
                }
                else if(tab_num2 == 5){
                    Intent intent3 = new Intent(getApplicationContext(), TouchTabFivePassword.class);
                    startActivity(intent3);

                }
                else if(tab_num2 == 6){
                    Intent intent3 = new Intent(getApplicationContext(), TouchTabSixPassword.class);
                    startActivity(intent3);
                }
                break;
            case R.id.setting_complete:
                Log.d(TAG, "완료버튼 클릭");
                /*final SharedPreferences p = getSharedPreferences("user_tab_num", MODE_PRIVATE);
                final SharedPreferences.Editor prefsEditor = p.edit();
                int n = p.getInt("tab_num", 0);
                Log.d(TAG, "선택한 터치탭 개수 >> "+n);
                if(n==0){
                    //tab_num 선택하지 않을 시 대화상자 띄우기
                    final AlertDialog.Builder nothing_selected = new AlertDialog.Builder(TouchTabSettingActivity.this);

                    nothing_selected.setTitle("아무것도 선택하지 않으셨습니다")
                            .setMessage("기본 설정 값인 4개로 설정됩니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                //확인 버튼 클릭
                                public void onClick(DialogInterface dialog, int whichButton){
                                    tab_num = 4;
                                    //putTabnum();
                                    prefsEditor.putInt("tab_num",4);
                                    prefsEditor.commit();
                                    dialog.cancel();
                                    startActivity(new Intent(TouchTabSettingActivity.this, MainActivity.class));
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                //취소 버튼 클릭
                                public void onClick(DialogInterface dialog, int whichButton){
                                    tab_num = 4;
                                    prefsEditor.putInt("tab_num", 4);
                                    prefsEditor.commit();
                                    dialog.cancel();

                                    startActivity(new Intent(TouchTabSettingActivity.this, MainActivity.class));
                                }

                            });
                    nothing_selected.show();
                }
                else*/
                    finish();
        }
    }

    /*public void putTabnum(){
        Log.d("Setting put 터치탭 개수 >> ", String.valueOf(tab_num));
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("tab_num", tab_num);
        prefsEditor.commit();
    }*/

    public void PrefInit(){
        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        prefsEditor.putBoolean("select1", false);
        prefsEditor.putBoolean("select2", false);
        prefsEditor.putBoolean("select3", false);
        prefsEditor.putBoolean("select4", false);
        prefsEditor.commit();


        SharedPreferences prefs2 = getSharedPreferences("Message", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs2.edit();

        prefsEditor2.putString("msg1", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg2", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg3", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg4", "여기에 어플을 등록합니다");
        prefsEditor2.commit();

        SharedPreferences prefs3 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor3 = prefs3.edit();

        prefsEditor3.putString("name1","");
        prefsEditor3.putString("name2","");
        prefsEditor3.putString("name3","");
        prefsEditor3.putString("name4","");
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
//        s.setSelection(prefs.getInt("tab_num", 4));
//    }
}
