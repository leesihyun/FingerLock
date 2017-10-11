package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by seahyun on 2017-09-23.
 */

public class ColorTabFivePassword extends AppCompatActivity implements View.OnClickListener{

    //private static int user_password[] = new int[8];
    public static ArrayList<Integer> input_arr = new ArrayList<Integer>();
    //private static int input_arr[] = new int[8];
    private static int password_count = 0;
    private static String TAG = "ColorTabFivePassword";
    private static int color[] = new int[6];

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("비밀번호를 설정합니다");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.color_tab_five_password);

        TextView info_text = (TextView)findViewById(R.id.info_text);
        Button finish_button = (Button)findViewById(R.id.complete);

        info_text.setText("사용할 비밀번호를 입력해주세요");
        finish_button.setText("다음으로");

        //패스워드 배열 값, 입력 배열 값 초기화
        /*for(int i=0; i<8; i++){
            user_password[i] = 0;
            input_arr[i] = 0;
        }*/


        button1 = (Button)findViewById(R.id.button5_1);
        button2 = (Button)findViewById(R.id.button5_2);
        button3 = (Button)findViewById(R.id.button5_3);
        button4 = (Button)findViewById(R.id.button5_4);
        button5 = (Button)findViewById(R.id.button5_5);
        Button next = (Button)findViewById(R.id.complete);

        password_count = 0;

        tabColorSetting();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //int num=0;
        int passwd;


        switch (v.getId()) {
            case R.id.button5_1:
                Log.d("비밀번호 설정 >>", "버튼 1 입력");
                int colorId1 = getButtonColor(1);
                passwd = setPasswd(colorId1);
                Log.d(TAG, "버튼 색 >> "+String.valueOf(passwd));
                //for(int i=0; i<input_arr.length; i++){
                //  if(input_arr[i]==0){
                //    input_arr[i] = passwd;
                //  i = input_arr.length;
                //}
                //}
                input_arr.add(passwd);
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                //passwd=0;
                break;
            case R.id.button5_2:
                Log.d("비밀번호 설정 >>", "버튼 2 입력");
                int colorId2 = getButtonColor(2);
                passwd = setPasswd(colorId2);
                Log.d(TAG, "버튼 색 >> "+String.valueOf(passwd));
//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.button5_3:
                Log.d("비밀번호 설정 >> ", "버튼 3 입력");
                int colorId3 = getButtonColor(3);
                passwd = setPasswd(colorId3);
                Log.d(TAG, "버튼 색 >> "+String.valueOf(passwd));
//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.button5_4:
                Log.d("비밀번호 설정 >> ", "버튼 4 입력");
                int colorId4 = getButtonColor(4);
                passwd = setPasswd(colorId4);
                Log.d(TAG, "버튼 색 >> "+String.valueOf(passwd));
//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.button5_5:
                Log.d("비밀번호 설정 >> ", "버튼 5 입력");
                int colorId5 = getButtonColor(5);
                passwd = setPasswd(colorId5);
                Log.d(TAG, "버튼 색 >> "+String.valueOf(passwd));
//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                password_count++;
                Log.d("count 값 : ", String.valueOf(password_count));
                break;
            case R.id.complete:
                Log.d(TAG, "비밀번호 확인 Activity로 이동");
                Log.d(TAG, "1) 비밀번호 개수 >> "+String.valueOf(password_count));
                Intent intent = new Intent(getApplicationContext(), RE_ColorTabFivePassword.class);
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

    public void tabColorSetting(){

        String colorname[] = new String[5];
        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        colorname[0] = prefs.getString("color1", "#FFFFFF");
        colorname[1] = prefs.getString("color2", "#FFFFFF");
        colorname[2] = prefs.getString("color3", "#FFFFFF");
        colorname[3] = prefs.getString("color4", "#FFFFFF");
        colorname[4] = prefs.getString("color5", "#FFFFFF");

        int color[] = new int[5];

        for(int i=0; i<5; i++){
            if(colorname[i].equals("Orange"))
                color[i] = R.color.Orange;
            else if(colorname[i].equals("Yellow"))
                color[i] = R.color.Yellow;
            else if(colorname[i].equals("Green"))
                color[i] = R.color.Green;
            else if(colorname[i].equals("Blue"))
                color[i] = R.color.Blue;
            else if(colorname[i].equals("Purple"))
                color[i] = R.color.Purple;
        }

//        Log.d(TAG, colorname[0]);
//        Log.d(TAG, colorname[1]);
//        Log.d(TAG, colorname[2]);
//        Log.d(TAG, colorname[3]);

        button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[0]));
        button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[1]));
        button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[2]));
        button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[3]));
        button5.setBackgroundColor(getApplicationContext().getResources().getColor(color[4]));

//        button1.setBackgroundColor(Integer.parseInt(color1));
//        button2.setBackgroundColor(Integer.parseInt(color2));
//        button3.setBackgroundColor(Integer.parseInt(color3));
//        button4.setBackgroundColor(Integer.parseInt(color4));
    }


    public int setPasswd(int colorid){

        int num = 0;

        if(colorid == R.color.Orange)
            num = 1;
        else if(colorid == R.color.Yellow)
            num = 2;
        else if(colorid == R.color.Green)
            num = 3;
        else if(colorid == R.color.Blue)
            num = 4;
        else if(colorid == R.color.Purple)
            num = 5;

        return num;
    }

    public int getButtonColor(int button_num){

        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);

        if(button_num == 1){
            String colorname = prefs.getString("color1", "#FFFFFF");

            if(colorname.equals("Orange"))
                return R.color.Orange;
            if(colorname.equals("Yellow"))
                return R.color.Yellow;
            if(colorname.equals("Green"))
                return R.color.Green;
            if(colorname.equals("Blue"))
                return R.color.Blue;
            if(colorname.equals("Purple"))
                return R.color.Purple;
        }
        else if(button_num == 2){
            String colorname = prefs.getString("color2", "#FFFFFF");

            if(colorname.equals("Orange"))
                return R.color.Orange;
            if(colorname.equals("Yellow"))
                return R.color.Yellow;
            if(colorname.equals("Green"))
                return R.color.Green;
            if(colorname.equals("Blue"))
                return R.color.Blue;
            if(colorname.equals("Purple"))
                return R.color.Purple;
        }
        else if(button_num == 3){
            String colorname = prefs.getString("color3", "#FFFFFF");

            if(colorname.equals("Orange"))
                return R.color.Orange;
            if(colorname.equals("Yellow"))
                return R.color.Yellow;
            if(colorname.equals("Green"))
                return R.color.Green;
            if(colorname.equals("Blue"))
                return R.color.Blue;
            if(colorname.equals("Purple"))
                return R.color.Purple;
        }
        else if(button_num == 4){
            String colorname = prefs.getString("color4", "#FFFFFF");

            if(colorname.equals("Orange"))
                return R.color.Orange;
            if(colorname.equals("Yellow"))
                return R.color.Yellow;
            if(colorname.equals("Green"))
                return R.color.Green;
            if(colorname.equals("Blue"))
                return R.color.Blue;
            if(colorname.equals("Purple"))
                return R.color.Purple;
        }
        else if(button_num == 5){
            String colorname = prefs.getString("color5", "#FFFFFF");

            if(colorname.equals("Orange"))
                return R.color.Orange;
            if(colorname.equals("Yellow"))
                return R.color.Yellow;
            if(colorname.equals("Green"))
                return R.color.Green;
            if(colorname.equals("Blue"))
                return R.color.Blue;
            if(colorname.equals("Purple"))
                return R.color.Purple;
        }
        return 0;

    }
    public ArrayList<Integer> getInputArray(){
        return input_arr;
    }

    public int getPassword_count(){
        return password_count;
    }
}
