package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by seahyun on 2017-09-20.
 */

public class ColorTabSettingActivity extends AppCompatActivity implements View.OnClickListener {


    public int tab_num;
    private static String TAG =  "ColorTabSettingActivity";
    ColorTabPreview object = new ColorTabPreview();
    Spinner s;
    private String PackageName[] = new String[6];

    private static int color[] = new int[6];

    private View header4,header5, header6;

    //private boolean select = false;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("컬러탭 환경설정");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.color_tab_setting);


        Button select = (Button)findViewById(R.id.application_select);
        Button preview = (Button)findViewById(R.id.preview);
        Button set_password = (Button)findViewById(R.id.set_password);
        Button complete = (Button)findViewById(R.id.setting_complete);
        Button init = (Button)findViewById(R.id.application_init);

        preview.setOnClickListener((View.OnClickListener)ColorTabSettingActivity.this);
        select.setOnClickListener((View.OnClickListener) ColorTabSettingActivity.this);
        set_password.setOnClickListener((View.OnClickListener)ColorTabSettingActivity.this);
        complete.setOnClickListener((View.OnClickListener)ColorTabSettingActivity.this);
        init.setOnClickListener((View.OnClickListener)ColorTabSettingActivity.this);



        imageView1 = (ImageView)findViewById(R.id.image1);
        imageView2 = (ImageView)findViewById(R.id.image2);
        imageView3 = (ImageView)findViewById(R.id.image3);
        imageView4 = (ImageView)findViewById(R.id.image4);
        imageView5 = (ImageView)findViewById(R.id.image5);
        imageView6 = (ImageView)findViewById(R.id.image6);

        textView1 = (TextView)findViewById(R.id.app_name1);
        textView2 = (TextView)findViewById(R.id.app_name2);
        textView3 = (TextView)findViewById(R.id.app_name3);
        textView4 = (TextView)findViewById(R.id.app_name4);
        textView5 = (TextView)findViewById(R.id.app_name5);
        textView6 = (TextView)findViewById(R.id.app_name6);


        //tab_num 스피너 생성
        s = (Spinner)findViewById(R.id.color_tab_num);
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

        color[0] = R.color.Orange;
        color[1] = R.color.Yellow;
        color[2] = R.color.Green;
        color[3] = R.color.Blue;
        color[4] = R.color.Purple;
        color[5] = R.color.Pink;

        select_color_tab();
    }

    //터치탭 개수 설정하기
    public void select_color_tab(){

        SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = prefs.edit();

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(parent.getItemAtPosition(position).toString().contains("4")){
                    tab_num = 4;
                    prefsEditor.putInt("tab_num", 4);
                    prefsEditor.commit();
                    imageView5.setImageDrawable(null);
                    textView5.setText("");
                    imageView6.setImageDrawable(null);
                    textView6.setText("");
                }
                else if(parent.getItemAtPosition(position).toString().contains("5")){
                    tab_num = 5;
                    prefsEditor.putInt("tab_num", 5);
                    prefsEditor.commit();
                    imageView6.setImageDrawable(null);
                    textView6.setText("");
                }
                else if(parent.getItemAtPosition(position).toString().contains("6")){
                    tab_num = 6;
                    prefsEditor.putInt("tab_num", 6);
                    prefsEditor.commit();
                }
                tabColorSetting();

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
                Intent intent = new Intent(getApplicationContext(), ColorTabPreview.class);
                startActivity(intent);
                break;
            case R.id.application_select: //어플 선택하기 버튼
                //select = true;
                SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
                int num = prefs.getInt("tab_num", 4);
                if(num == 4){
                    Intent intent2 = new Intent(getApplicationContext(), ColorTabFourActivity.class);
                    startActivity(intent2);
                }
                else if(num == 5){
                    Intent intent2 = new Intent(getApplicationContext(), ColorTabFiveActivity.class);
                    startActivity(intent2);
                }
                else if(num == 6){
                    Intent intent2 = new Intent(getApplicationContext(), ColorTabSixActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.application_init: //어플 선택 초기화 버튼
                Toast.makeText(this, "어플 바로가기 설정이 초기화 되었습니다", Toast.LENGTH_SHORT).show();
                PrefInit();
                break;
            case R.id.set_password: //비밀번호 설정 버튼
                Log.d("버튼 입력 >> ", "비밀번호 설정");
                //if(select == false){
                //    Toast.makeText(this, "바로가기 설정할 어플을 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                //}
                //else {
                    SharedPreferences prefs2 = getSharedPreferences("user_tab_num", MODE_PRIVATE);
                    int tab_num2 = prefs2.getInt("tab_num", 4);
                    Log.d(TAG, "터치탭 개수 >> " + tab_num2);
                    if (tab_num2 == 4) {
                        Intent intent3 = new Intent(getApplicationContext(), ColorTabFourPassword.class);
                        startActivity(intent3);
                    } else if (tab_num2 == 5) {
                        Intent intent3 = new Intent(getApplicationContext(), ColorTabFivePassword.class);
                        startActivity(intent3);

                    } else if (tab_num2 == 6) {
                        Intent intent3 = new Intent(getApplicationContext(), ColorTabSixPassword.class);
                        startActivity(intent3);
                    }
               // }
                break;
            case R.id.setting_complete:
                Log.d(TAG, "완료버튼 클릭");
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent3);
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
        prefsEditor.putBoolean("select5", false);
        prefsEditor.putBoolean("select6", false);

        prefsEditor.commit();


        SharedPreferences prefs2 = getSharedPreferences("Message", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs2.edit();

        prefsEditor2.putString("msg1", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg2", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg3", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg4", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg5", "여기에 어플을 등록합니다");
        prefsEditor2.putString("msg6", "여기에 어플을 등록합니다");
        prefsEditor2.commit();

        SharedPreferences prefs3 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor3 = prefs3.edit();

        prefsEditor3.putString("name1","");
        prefsEditor3.putString("name2","");
        prefsEditor3.putString("name3","");
        prefsEditor3.putString("name4","");
        prefsEditor3.putString("name5","");
        prefsEditor3.putString("name6","");
        prefsEditor3.commit();

        TextView textView1 = (TextView)findViewById(R.id.app_name1);
        TextView textView2 = (TextView)findViewById(R.id.app_name2);
        TextView textView3 = (TextView)findViewById(R.id.app_name3);
        TextView textView4 = (TextView)findViewById(R.id.app_name4);
        TextView textView5 = (TextView)findViewById(R.id.app_name5);
        TextView textView6 = (TextView)findViewById(R.id.app_name6);

        textView1.setText("");
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");
        textView5.setText("");
        textView6.setText("");

        ImageView imageView1 = (ImageView)findViewById(R.id.image1);
        ImageView imageView2 = (ImageView)findViewById(R.id.image2);
        ImageView imageView3 = (ImageView)findViewById(R.id.image3);
        ImageView imageView4 = (ImageView)findViewById(R.id.image4);
        ImageView imageView5 = (ImageView)findViewById(R.id.image5);
        ImageView imageView6 = (ImageView)findViewById(R.id.image6);

        imageView1.setImageDrawable(null);
        imageView2.setImageDrawable(null);
        imageView3.setImageDrawable(null);
        imageView4.setImageDrawable(null);
        imageView5.setImageDrawable(null);
        imageView6.setImageDrawable(null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //tabColorSetting();

        SharedPreferences pref = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences("Message", MODE_PRIVATE);

        PackageManager pm = getPackageManager();

        PackageName[0] = pref.getString("name1","");
        PackageName[1] = pref.getString("name2","");
        PackageName[2] = pref.getString("name3","");
        PackageName[3] = pref.getString("name4","");
        PackageName[4] = pref.getString("name5","");
        PackageName[5] = pref.getString("name6","");

        for(int i=0; i<6; i++){
            Log.d(TAG, "바로가기 설정된 어플 >> "+PackageName[i]);
        }

        try {

            if(!PackageName[0].equals("")) {
                Drawable App_icon1 = pm.getApplicationIcon(PackageName[0]);
                Log.d(TAG, "어플이름 >> "+pref2.getString("msg1",""));
                imageView1.setImageDrawable(App_icon1);
                textView1.setText(pref2.getString("msg1",""));
            }

            if(!PackageName[1].equals("")) {
                Drawable App_icon2 = pm.getApplicationIcon(PackageName[1]);
                Log.d(TAG, "어플이름 >> "+pref2.getString("msg2",""));
                imageView2.setImageDrawable(App_icon2);
                textView2.setText(pref2.getString("msg2",""));
            }

            if(!PackageName[2].equals("")) {
                Drawable App_icon3 = pm.getApplicationIcon(PackageName[2]);
                Log.d(TAG, "어플이름 >> "+pref2.getString("msg3",""));
                imageView3.setImageDrawable(App_icon3);
                textView3.setText(pref2.getString("msg3",""));
            }
            if(!PackageName[3].equals("")) {
                Drawable App_icon4 = pm.getApplicationIcon(PackageName[3]);
                Log.d(TAG, "어플이름 >> "+pref2.getString("msg4",""));
                imageView4.setImageDrawable(App_icon4);
                textView4.setText(pref2.getString("msg4",""));
            }
            if(!PackageName[4].equals("")) {
                Drawable App_icon5 = pm.getApplicationIcon(PackageName[4]);
                Log.d(TAG, "어플이름 >> "+pref2.getString("msg5",""));
                imageView5.setImageDrawable(App_icon5);
                textView5.setText(pref2.getString("msg5",""));
            }

            if(!PackageName[5].equals("")) {
                Drawable App_icon6 = pm.getApplicationIcon(PackageName[5]);
                Log.d(TAG, "어플이름 >> "+pref2.getString("msg6",""));
                imageView6.setImageDrawable(App_icon6);
                textView6.setText(pref2.getString("msg6",""));
            }

            if(tab_num == 4){
                imageView5.setImageDrawable(null);
                imageView6.setImageDrawable(null);
                textView5.setText("");
                textView6.setText("");

            }
            if(tab_num == 5){
                imageView6.setImageDrawable(null);
                textView6.setText("");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void tabColorSetting(){

        SharedPreferences prefs2 = getSharedPreferences("user_tab_num", MODE_PRIVATE);
        int tab_num2 = prefs2.getInt("tab_num", 4);

        int random[] = new int[tab_num2];

        for(int i=0; i<random.length; i++){

            random[i] = (int)(Math.random()*tab_num2);

            for(int j=0; j<i; j++){
                if(random[i] == random[j]){
                    i--;
                    break;
                }
            }

        }

        for (int i=0; i<random.length; i++){
            Log.d(TAG, "랜덤숫자 >> "+String.valueOf(random[i]));
        }
        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        String colorname[] = new String[tab_num2];

        for(int i=0; i<tab_num2; i++) {
            if (color[random[i]] == R.color.Orange)
                colorname[i] = "Orange";
            else if (color[random[i]] == R.color.Yellow)
                colorname[i] = "Yellow";
            else if (color[random[i]] == R.color.Green)
                colorname[i] = "Green";
            else if (color[random[i]] == R.color.Blue)
                colorname[i] = "Blue";
            else if(color[random[i]] == R.color.Purple)
                colorname[i] = "Purple";
            else if(color[random[i]] == R.color.Pink)
                colorname[i] = "Pink";
        }
        if(tab_num2 == 4){
            prefsEditor.putString("color1", colorname[0]);
            prefsEditor.putString("color2", colorname[1]);
            prefsEditor.putString("color3", colorname[2]);
            prefsEditor.putString("color4", colorname[3]);
        }
        else if(tab_num2 == 5){
            prefsEditor.putString("color1", colorname[0]);
            prefsEditor.putString("color2", colorname[1]);
            prefsEditor.putString("color3", colorname[2]);
            prefsEditor.putString("color4", colorname[3]);
            prefsEditor.putString("color5", colorname[4]);
        }
        else if(tab_num2 == 6){
            prefsEditor.putString("color1", colorname[0]);
            prefsEditor.putString("color2", colorname[1]);
            prefsEditor.putString("color3", colorname[2]);
            prefsEditor.putString("color4", colorname[3]);
            prefsEditor.putString("color5", colorname[4]);
            prefsEditor.putString("color6", colorname[5]);
        }



//        Log.d(TAG, "탭 색깔 >> "+colorname[0]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[1]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[2]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[3]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[4]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[5]);
        prefsEditor.commit();



        Button button1, button2, button3, button4, button5, button6;

        header4 = getLayoutInflater().inflate(R.layout.color_tab_four, null, false);
        header5 = getLayoutInflater().inflate(R.layout.color_tab_five, null, false);
        header6 = getLayoutInflater().inflate(R.layout.color_tab_six, null, false);

        if(tab_num2 == 4){
            button1 = (Button)header4.findViewById(R.id.button4_1);
            button2 = (Button)header4.findViewById(R.id.button4_2);
            button3 = (Button)header4.findViewById(R.id.button4_3);
            button4 = (Button)header4.findViewById(R.id.button4_4);

            button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[0]]));
            button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[1]]));
            button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[2]]));
            button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[3]]));
        }
        else if(tab_num2 == 5){
            button1 = (Button)header5.findViewById(R.id.button5_1);
            button2 = (Button)header5.findViewById(R.id.button5_2);
            button3 = (Button)header5.findViewById(R.id.button5_3);
            button4 = (Button)header5.findViewById(R.id.button5_4);
            button5 = (Button)header5.findViewById(R.id.button5_5);

            button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[0]]));
            button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[1]]));
            button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[2]]));
            button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[3]]));
            button5.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[4]]));
        }
        else if(tab_num2 == 6){
            button1 = (Button)header6.findViewById(R.id.button6_1);
            button2 = (Button)header6.findViewById(R.id.button6_2);
            button3 = (Button)header6.findViewById(R.id.button6_3);
            button4 = (Button)header6.findViewById(R.id.button6_4);
            button5 = (Button)header6.findViewById(R.id.button6_5);
            button6 = (Button)header6.findViewById(R.id.button6_6);

            button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[0]]));
            button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[1]]));
            button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[2]]));
            button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[3]]));
            button5.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[4]]));
            button6.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[5]]));
        }



    }

}
