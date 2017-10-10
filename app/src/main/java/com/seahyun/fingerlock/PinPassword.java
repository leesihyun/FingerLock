package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.BatchUpdateException;

/**
 * Created by seahyun on 2017-09-23.
 */

public class PinPassword extends AppCompatActivity implements View.OnClickListener{

    private static int user_password[] = new int[4];
    private static int input_arr[] = new int[4];
    private static int password_count = 0;
    private static String TAG = "PinPassword";
    EditText num1;
    EditText num2;
    EditText num3;
    EditText num4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("비밀번호를 설정합니다");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.pin_password);

        TextView info_text = (TextView)findViewById(R.id.info_text);
        Button finish_button = (Button)findViewById(R.id.complete);

        info_text.setText("사용할 비밀번호를 입력해주세요");
        finish_button.setText("다음으로");

        //패스워드 배열 값, 입력 배열 값 초기화
        for(int i=0; i<4; i++){
            user_password[i] = 0;
            input_arr[i] = 0;
        }


        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        num3 = (EditText) findViewById(R.id.num3);
        num4 = (EditText) findViewById(R.id.num4);
        Button next = (Button)findViewById(R.id.complete);

        next.setOnClickListener(this);

        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num1.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[0]= Integer.parseInt(num1.getText().toString());
                    num2.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num2.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[1]= Integer.parseInt(num2.getText().toString());
                    num3.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num3.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[2]= Integer.parseInt(num3.getText().toString());
                    num4.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num4.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[3]= Integer.parseInt(num4.getText().toString());
                   // input_handler();
                    //num5.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        password_count = 0;
    }

    @Override
    public void onClick(View v) {
        int num=0;

        switch (v.getId()) {
           /* case R.id.button4_1:
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
                break;*/
            case R.id.complete:
                Log.d(TAG, "비밀번호 확인 Activity로 이동");
                Log.d(TAG, "1) 비밀번호 개수 >> "+String.valueOf(password_count));
                Intent intent = new Intent(getApplicationContext(), RE_PinPassword.class);
                startActivity(intent);
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

