package com.seahyun.fingerlock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends Activity implements OnClickListener {

    //private Button startServiceButton;
    //private Button stopServiceButton;
    //private Button checkAliveButton;
    Switch touch_tab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        touch_tab = (Switch)findViewById(R.id.touch_tab);
        touch_tab.setOnClickListener(this);
        /*startServiceButton = (Button)findViewById(R.id.startService);
        stopServiceButton = (Button)findViewById(R.id.stopService);
        checkAliveButton = (Button)findViewById(R.id.checkAlive);

        startServiceButton.setOnClickListener(this);
        stopServiceButton.setOnClickListener(this);
        checkAliveButton.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {

        if(touch_tab.isChecked()){
            startService(new Intent(this, SimpleService.class));
            touch_tab.setChecked(true);
        }
        else{
            stopService(new Intent(this, SimpleService.class));
            touch_tab.setChecked(false);
        }
        /*switch(v.getId()){
            case R.id.startService:
                startService(new Intent(this, SimpleService.class));
                break;

            case R.id.stopService:
                stopService(new Intent(this, SimpleService.class));
                break;

            case R.id.checkAlive:
                sendBroadcast(new Intent("com.androidhuman.action.isAlive"));
                break;
        }*/
    }
}
