package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.sql.BatchUpdateException;

/**
 * Created by seahyun on 2017-09-23.
 */

public class TouchTabFourPassword extends AppCompatActivity implements View.OnClickListener{

    private static int user_password[] = new int[8];
    private static int input_arr[] = new int[8];
    private static int password_count = 0;
    private static String TAG = "TouchTabFourPassword";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("비밀번호를 설정합니다");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.touch_tab_four_password);

        TextView info_text = (TextView)findViewById(R.id.info_text);
        Button finish_button = (Button)findViewById(R.id.complete);

        info_text.setText("사용할 비밀번호를 입력해주세요");
        finish_button.setText("다음으로");

        //패스워드 배열 값, 입력 배열 값 초기화
        for(int i=0; i<8; i++){
            user_password[i] = 0;
            input_arr[i] = 0;
        }

        Button button1 = (Button)findViewById(R.id.button4_1);
        Button button2 = (Button)findViewById(R.id.button4_2);
        Button button3 = (Button)findViewById(R.id.button4_3);
        Button button4 = (Button)findViewById(R.id.button4_4);
        Button next = (Button)findViewById(R.id.complete);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        next.setOnClickListener(this);

        password_count = 0;
    }

    @Override
    public void onClick(View v) {
        int num=0;

        switch (v.getId()) {
            case R.id.button4_1:
                Log.d("비밀번호 설정", "1입력");
                for(int i=0; i<input_arr.length; i++){
                    if(input_arr[i]==0){
                        input_arr[i] = 1;

                        i = input_arr.length;
                    }
                }
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.button4_2:
                Log.d("비밀번호 설정", "2입력");
                for(int i=0; i<input_arr.length; i++){
                    if(input_arr[i]==0){
                        input_arr[i] = 2;
                        i = input_arr.length;
                    }
                }
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.button4_3:
                Log.d("비밀번호 설정", "3입력");
                for(int i=0; i<input_arr.length; i++){
                    if(input_arr[i]==0){
                        input_arr[i] = 3;
                        i = input_arr.length;
                    }
                }
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.button4_4:
                Log.d("비밀번호 설정", "4입력");
                for(int i=0; i<input_arr.length; i++){
                    if(input_arr[i]==0){
                        input_arr[i] = 4;
                        i = input_arr.length;
                    }
                }
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.complete:
                Log.d(TAG, "비밀번호 확인 Activity로 이동");
                Log.d(TAG, "1) 비밀번호 개수 >> "+String.valueOf(password_count));
                Intent intent = new Intent(getApplicationContext(), RE_TouchTabFourPassword.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("비밀번호 개수 >> ", String.valueOf(password_count));
    }

    public int[] getInputArray(){
        return input_arr;
    }
    public int getPassword_count(){
        return password_count;
    }
}
