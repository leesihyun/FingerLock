package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by seahyun on 2017-09-23.
 */

public class RE_ColorTabFourPassword extends AppCompatActivity implements View.OnClickListener{

    //private static int user_password[] = new int[8];
    //private static int input_arr[] = new int[8];
    //private int pre_input_arr[] = new int[8]; //이전 비밀번호 설정창에서 입력한 비밀번호가 저장된 배열
    private static ArrayList<Integer> user_password = new ArrayList<Integer>();
    private static ArrayList<Integer> input_arr = new ArrayList<Integer>();
    private static ArrayList<Integer> pre_input_arr = new ArrayList<Integer>();
    //private static int password_count = 0;
    private static String TAG = "RE_ColorTabFourPassword";
    private static int color[] = new int[6];

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    ColorTabFourPassword o = new ColorTabFourPassword();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("비밀번호를 확인합니다");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.color_tab_four_password);


        TextView info_text = (TextView)findViewById(R.id.info_text);
        Button finish_button = (Button)findViewById(R.id.complete);

        info_text.setText("비밀번호를 한번 더 입력해주세요");
        finish_button.setText("완료");

        //패스워드 배열 값, 입력 배열 값 초기화
        /*for(int i=0; i<8; i++){
            user_password[i] = 0;
            input_arr[i] = 0;
        }*/
        pre_input_arr = o.getInputArray();
//        for(int i=0; i<8; i++){
//            Log.d(TAG, "예전에 입력한 배열 >> "+pre_input_arr[i]);
//        }
        for(int i=0; i<pre_input_arr.size(); i++){
            Log.d(TAG, "예전에 입력한 배열 >> "+pre_input_arr.get(i));
        }

        button1 = (Button)findViewById(R.id.button4_1);
        button2 = (Button)findViewById(R.id.button4_2);
        button3 = (Button)findViewById(R.id.button4_3);
        button4 = (Button)findViewById(R.id.button4_4);
        Button next = (Button)findViewById(R.id.complete);

        //password_count=0;

        color[0] = R.color.Orange;
        color[1] = R.color.Yellow;
        color[2] = R.color.Green;
        color[3] = R.color.Blue;
        color[4] = R.color.Purple;
        color[5] = R.color.Pink;

        tabColorSetting();

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
        int passwd;

        switch (v.getId()) {
            case R.id.button4_1:
                Log.d("비밀번호 설정 >>", "버튼 1 입력");
                int colorId1 = getButtonColor(1);
                passwd = setPasswd(colorId1);

//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                //password_count++;
                Log.d("count 값 : ", String.valueOf(input_arr.size()));
                //passwd=0;
                break;
            case R.id.button4_2:
                Log.d("비밀번호 설정 >>", "버튼 2 입력");
                int colorId2 = getButtonColor(2);
                passwd = setPasswd(colorId2);

//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                //password_count++;
                Log.d("count 값 : ", String.valueOf(input_arr.size()));
                break;
            case R.id.button4_3:
                Log.d("비밀번호 설정 >> ", "버튼 3 입력");
                int colorId3 = getButtonColor(3);
                passwd = setPasswd(colorId3);

//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                //password_count++;
                Log.d("count 값 : ", String.valueOf(input_arr.size()));
                break;
            case R.id.button4_4:
                Log.d("비밀번호 설정 >> ", "버튼 4 입력");
                int colorId4 = getButtonColor(4);
                passwd = setPasswd(colorId4);

//                for(int i=0; i<input_arr.length; i++){
//                    if(input_arr[i]==0){
//                        input_arr[i] = passwd;
//
//                        i = input_arr.length;
//                    }
//                }
                input_arr.add(passwd);
                //password_count++;
                Log.d("count 값 : ", String.valueOf(input_arr.size()));
                break;
            case R.id.complete:
                if(check_password() == true){

                    Log.d(TAG, "터치탭 환경설정 화면으로 이동");
                    input_arr.clear();
                    pre_input_arr.clear();
                    o.input_arr.clear();
                    Intent intent = new Intent(getApplicationContext(), ColorTabSettingActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "비밀번호를 다시 입력합니다.", Toast.LENGTH_SHORT).show();
                    input_arr.clear();
                    pre_input_arr.clear();
                    o.input_arr.clear();
                    //password_count=0;
                    Intent intent = new Intent(getApplicationContext(), ColorTabFourPassword.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("비밀번호 개수 >> ", String.valueOf(password_count));
        //Log.d("비밀번호 개수 >> ", String.valueOf(input_arr.size()));
    }

    public boolean check_password(){

        int cnt = 0;

//        for(int i=0; i<8; i++){
//            Log.d(TAG, "이전 비밀번호 >> "+pre_input_arr[i]);
//        }
//        for(int i=0; i<8; i++){
//            Log.d(TAG, "현재 비밀번호 >> "+input_arr[i]);
//        }
//        for(int i=0; i<8; i++){
//            if(pre_input_arr[i] == input_arr[i]) {
//                if (input_arr[i] != 0)
//                    cnt++;
//            }
//        }
        for(int i=0; i<pre_input_arr.size(); i++){
            Log.d(TAG, "이전 비밀번호 >> "+pre_input_arr.get(i));
        }
        for(int i=0; i<input_arr.size(); i++){
            Log.d(TAG, "현재 비밀번호 >> "+input_arr.get(i));
        }
        for(int i=0; i<pre_input_arr.size(); i++){
            if(pre_input_arr.size() == input_arr.size()) {
                if (pre_input_arr.get(i) == input_arr.get(i)) {
                    if (input_arr.get(i) != 0)
                        cnt++;
                }
            }
        }
        if((cnt == input_arr.size())&&(cnt == o.getPassword_count())) {
            //비밀번호 저장
            SharedPreferences prefs = getSharedPreferences("password", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            try {
                prefsEditor.putInt("1st password", input_arr.get(0));
                prefsEditor.putInt("2nd password", input_arr.get(1));
                prefsEditor.putInt("3rd password", input_arr.get(2));
                prefsEditor.putInt("4th password", input_arr.get(3));
                prefsEditor.putInt("5th password", input_arr.get(4));
                prefsEditor.putInt("6th password", input_arr.get(5));
                prefsEditor.putInt("7th password", input_arr.get(6));
                prefsEditor.putInt("8th password", input_arr.get(7));
            }
            catch ( IndexOutOfBoundsException e){
            }
            prefsEditor.commit();
            Toast.makeText(this, "비밀번호 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void tabColorSetting() {


        String colorname[] = new String[4];
        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        colorname[0] = prefs.getString("color1", "#FFFFFF");
        colorname[1] = prefs.getString("color2", "#FFFFFF");
        colorname[2] = prefs.getString("color3", "#FFFFFF");
        colorname[3] = prefs.getString("color4", "#FFFFFF");

        int color[] = new int[4];

        for(int i=0; i<4; i++){
            if(colorname[i].equals("Orange"))
                color[i] = R.color.Orange;
            else if(colorname[i].equals("Yellow"))
                color[i] = R.color.Yellow;
            else if(colorname[i].equals("Green"))
                color[i] = R.color.Green;
            else if(colorname[i].equals("Blue"))
                color[i] = R.color.Blue;
        }

//        Log.d(TAG, colorname[0]);
//        Log.d(TAG, colorname[1]);
//        Log.d(TAG, colorname[2]);
//        Log.d(TAG, colorname[3]);

        button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[0]));
        button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[1]));
        button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[2]));
        button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[3]));
//        button1.setBackgroundColor(Integer.parseInt(color1));
//        button2.setBackgroundColor(Integer.parseInt(color2));
//        button3.setBackgroundColor(Integer.parseInt(color3));
//        button4.setBackgroundColor(Integer.parseInt(color4));
    }

    public int setPasswd(int colorid){

        int num = 0;
        if(colorid == color[0])
            num = 1;
        else if(colorid == color[1])
            num = 2;
        else if(colorid == color[2])
            num = 3;
        else if(colorid == color[3])
            num = 4;

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
        }
        return 0;

    }
}
