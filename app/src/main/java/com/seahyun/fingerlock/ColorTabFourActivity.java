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

public class ColorTabFourActivity extends AppCompatActivity implements View.OnClickListener {


    public ColorTabFourActivity(){};

    public String[] Message = new String[6];
    public String[] PackageName = new String[6];
    private static String TAG = "ColorTabFourActivity";

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("터치탭 어플 등록");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.color_tab_four);

        button1 = (Button) findViewById(R.id.button4_1);
        button2 = (Button) findViewById(R.id.button4_2);
        button3 = (Button) findViewById(R.id.button4_3);
        button4 = (Button) findViewById(R.id.button4_4);

        Button register = (Button) findViewById(R.id.app_registration);
        Button cancel = (Button) findViewById(R.id.app_cancel);


//        color[0] = R.color.Orange;
//        color[1] = R.color.Yellow;
//        color[2] = R.color.Green;
//        color[3] = R.color.Blue;
//        color[4] = R.color.Purple;
//        color[5] = R.color.Pink;

        tabColorSetting();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        register.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //기존의 바로가기 설정 어플 목록 가져오기
        applicationList();

    }
    @Override
    public void onClick(View v) {

        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        SharedPreferences prefs2 = getSharedPreferences("select_color", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs2.edit();
        int passwd;

        switch (v.getId()) {
            case R.id.button4_1:
                int colorId1 = getButtonColor(1);
                passwd = setPasswd(colorId1);
                Log.d(TAG, "passwd값 >> "+String.valueOf(passwd));
                if(passwd == 1)
                    prefsEditor.putBoolean("select1", true);
                else if(passwd == 2)
                    prefsEditor.putBoolean("select2", true);
                else if(passwd == 3)
                    prefsEditor.putBoolean("select3", true);
               else if(passwd == 4)
                    prefsEditor.putBoolean("select4", true);
                //prefsEditor.putBoolean("select1", true);
                prefsEditor.commit();

                prefsEditor2.putInt("passwd", passwd);
                prefsEditor2.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button4_2:
                int colorId2 = getButtonColor(2);
                passwd = setPasswd(colorId2);
                //prefsEditor.putBoolean("select2", true);
                if(passwd == 1)
                    prefsEditor.putBoolean("select1", true);
                else if(passwd == 2)
                    prefsEditor.putBoolean("select2", true);
                else if(passwd == 3)
                    prefsEditor.putBoolean("select3", true);
                else if(passwd == 4)
                    prefsEditor.putBoolean("select4", true);
                prefsEditor.commit();

                prefsEditor2.putInt("passwd", passwd);
                prefsEditor2.commit();

                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button4_3:
                int colorId3 = getButtonColor(3);
                passwd = setPasswd(colorId3);
                if(passwd == 1)
                    prefsEditor.putBoolean("select1", true);
                else if(passwd == 2)
                    prefsEditor.putBoolean("select2", true);
                else if(passwd == 3)
                    prefsEditor.putBoolean("select3", true);
                else if(passwd == 4)
                    prefsEditor.putBoolean("select4", true);
                //prefsEditor.putBoolean("select3", true);
                prefsEditor.commit();

                prefsEditor2.putInt("passwd", passwd);
                prefsEditor2.commit();

                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button4_4:
                int colorId4 = getButtonColor(4);
                passwd = setPasswd(colorId4);
                //prefsEditor.putBoolean("select4", true);
                if(passwd == 1)
                    prefsEditor.putBoolean("select1", true);
                else if(passwd == 2)
                    prefsEditor.putBoolean("select2", true);
                else if(passwd == 3)
                    prefsEditor.putBoolean("select3", true);
                else if(passwd == 4)
                    prefsEditor.putBoolean("select4", true);
                prefsEditor.commit();

                prefsEditor2.putInt("passwd", passwd);
                prefsEditor2.commit();

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

        SharedPreferences prefs = getSharedPreferences("Message", MODE_PRIVATE);
        int colorId1, colorId2, colorId3, colorId4;
        int button_color1, button_color2, button_color3, button_color4;

        colorId1 = getButtonColor(1);
        colorId2 = getButtonColor(2);
        colorId3 = getButtonColor(3);
        colorId4 = getButtonColor(4);

        button_color1 = setPasswd(colorId1);
        button_color2 = setPasswd(colorId2);
        button_color3 = setPasswd(colorId3);
        button_color4 = setPasswd(colorId4);

        Log.d(TAG, "버튼 1 색깔 : "+String.valueOf(button_color1));
        Log.d(TAG, "버튼 2 색깔 : "+String.valueOf(button_color2));
        Log.d(TAG, "버튼 3 색깔 : "+String.valueOf(button_color3));
        Log.d(TAG, "버튼 4 색깔 : "+String.valueOf(button_color4));

        switch(button_color1){
            case 1:
                button1.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
                break;
            case 2:
                button1.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
                break;
            case 3:
                button1.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
                break;
            case 4:
                button1.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
                break;
            default:
                button1.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
                break;
        }

        switch(button_color2){
            case 1:
                button2.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
                break;
            case 2:
                button2.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
                break;
            case 3:
                button2.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
                break;
            case 4:
                button2.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
                break;
            default:
                button2.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
                break;
        }

        switch(button_color3){
            case 1:
                button3.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
                break;
            case 2:
                button3.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
                break;
            case 3:
                button3.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
                break;
            case 4:
                button3.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
                break;
            default:
                button3.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
                break;
        }

        switch(button_color4){
            case 1:
                button4.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
                break;
            case 2:
                button4.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
                break;
            case 3:
                button4.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
                break;
            case 4:
                button4.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
                break;
            default:
                button4.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));
                break;
        }
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
            Log.d("ColorTabFourActivity", "Message >> "+Message[i]);
            Log.d("ColorTabFourActivity", "PackageName >> "+PackageName[i]);
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

    public void tabColorSetting(){

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
//            else if(colorname[i].equals("Purple"))
//                color[i] = R.color.Purple;
//            else if(colorname[i].equals("Pink"))
//                color[i] = R.color.Pink;
        }

        Log.d(TAG, "무슨색? >>"+colorname[0]);
        Log.d(TAG, "무슨색? >>"+colorname[1]);
        Log.d(TAG, "무슨색? >>"+colorname[2]);
        Log.d(TAG, "무슨색? >>"+colorname[3]);

        button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[0]));
        button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[1]));
        button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[2]));
        button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[3]));
//        button1.setBackgroundColor(Integer.parseInt(color1));
//        button2.setBackgroundColor(Integer.parseInt(color2));
//        button3.setBackgroundColor(Integer.parseInt(color3));
//        button4.setBackgroundColor(Integer.parseInt(color4));
    }

    public int getButtonColor(int button_num){

        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        Log.d(TAG, "button_num >> "+String.valueOf(button_num));
        if(button_num == 1){
            String colorname = prefs.getString("color1", "#FFFFFF");
            Log.d(TAG, "Colorname >> "+colorname);
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
        return num;
    }

}
