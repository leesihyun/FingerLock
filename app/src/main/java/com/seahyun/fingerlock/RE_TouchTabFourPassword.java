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
import android.widget.Toast;

/**
 * Created by seahyun on 2017-09-23.
 */

public class RE_TouchTabFourPassword extends AppCompatActivity implements View.OnClickListener{

    private static int user_password[] = new int[8];
    private static int input_arr[] = new int[8];
    private int pre_input_arr[] = new int[8]; //이전 비밀번호 설정창에서 입력한 비밀번호가 저장된 배열
    private static int password_count = 0;
    private static String TAG = "TouchTabFourPassword";
    TouchTabFourPassword o = new TouchTabFourPassword();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("비밀번호를 확인합니다");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.touch_tab_four_password);


        TextView info_text = (TextView)findViewById(R.id.info_text);
        Button finish_button = (Button)findViewById(R.id.complete);

        info_text.setText("비밀번호를 한번 더 입력해주세요");
        finish_button.setText("완료");

        //패스워드 배열 값, 입력 배열 값 초기화
        for(int i=0; i<8; i++){
            user_password[i] = 0;
            input_arr[i] = 0;
        }
        pre_input_arr = o.getInputArray();
        for(int i=0; i<8; i++){
            Log.d(TAG, "예전에 입력한 배열 >> "+pre_input_arr[i]);
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

    }

    @Override
    public void onClick(View v) {
        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

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
                if(check_password() == true){
                    Log.d(TAG, "터치탭 환경설정 화면으로 이동");
                    Intent intent = new Intent(getApplicationContext(), TouchTabSettingActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "비밀번호를 다시 입력합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TouchTabFourPassword.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("비밀번호 개수 >> ", String.valueOf(password_count));
    }

    public boolean check_password(){

        int cnt = 0;

        for(int i=0; i<8; i++){
            Log.d(TAG, "이전 비밀번호 >> "+pre_input_arr[i]);
        }
        for(int i=0; i<8; i++){
            Log.d(TAG, "현재 비밀번호 >> "+input_arr[i]);
        }
        for(int i=0; i<8; i++){
            if(pre_input_arr[i] == input_arr[i]) {
                if (input_arr[i] != 0)
                    cnt++;
            }
        }
        if((cnt == password_count)&&(cnt == o.getPassword_count())) {
            //비밀번호 저장
            SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putInt("1st password", input_arr[0]);
            prefsEditor.putInt("2nd password", input_arr[1]);
            prefsEditor.putInt("3rd password", input_arr[2]);
            prefsEditor.putInt("4th password", input_arr[3]);
            prefsEditor.putInt("5th password", input_arr[4]);
            prefsEditor.putInt("6th password", input_arr[5]);
            prefsEditor.putInt("7th password", input_arr[6]);
            prefsEditor.putInt("8th password", input_arr[7]);
            prefsEditor.commit();
            Toast.makeText(this, "비밀번호 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
