package com.seahyun.fingerlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import  android.support.v7.app.ActionBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    //private Button startServiceButton;
    //private Button stopServiceButton;
    //private Button checkAliveButton;
    Switch touch_tab;
    Switch pin_lock;
    ImageButton touch_tab_setting;
    ImageButton pin_lock_setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("손가LOCK");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));


        setContentView(R.layout.activity_main);

        touch_tab = (Switch)findViewById(R.id.touch_tab_lock);
        touch_tab.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(touch_tab.isChecked()){
                            startService(new Intent(MainActivity.this, SimpleService.class));
                            touch_tab.setChecked(true);
                        }
                        else{
                            stopService(new Intent(MainActivity.this, SimpleService.class));
                            touch_tab.setChecked(false);
                        }
                    }
                });
        pin_lock = (Switch)findViewById(R.id.pin_lock);
        pin_lock.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(pin_lock.isChecked()){
                            startService(new Intent(MainActivity.this, SimpleServicePin.class));
                            pin_lock.setChecked(true);
                        }
                        else{
                            stopService(new Intent(MainActivity.this, SimpleServicePin.class));
                            pin_lock.setChecked(false);
                        }
                    }
                });

        touch_tab_setting = (ImageButton)findViewById(R.id.touch_tab_lock_setting);
        touch_tab_setting.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Log.d("환경설정 >> ", "터치탭");
                        Intent intent = new Intent(getApplicationContext(), TouchTabSettingActivity.class);
                        startActivity(intent);

                    }
                }
        );
        pin_lock_setting=(ImageButton)findViewById(R.id.pin_lock_setting);
        pin_lock_setting.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Log.d("환경설정 >> ", "PinLock");
                        Intent intent = new Intent(getApplicationContext(), PinSettingActivity.class);
                        startActivity(intent);

                    }
                }
        );


    }

}
