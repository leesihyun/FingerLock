package com.seahyun.fingerlock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private Button startServiceButton;
    private Button stopServiceButton;
    private Button checkAliveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startServiceButton = (Button)findViewById(R.id.startService);
        stopServiceButton = (Button)findViewById(R.id.stopService);
        checkAliveButton = (Button)findViewById(R.id.checkAlive);

        startServiceButton.setOnClickListener(this);
        stopServiceButton.setOnClickListener(this);
        checkAliveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startService:
                startService(new Intent(this, SimpleService.class));
                break;

            case R.id.stopService:
                stopService(new Intent(this, SimpleService.class));
                break;

            case R.id.checkAlive:
                sendBroadcast(new Intent("com.androidhuman.action.isAlive"));
                break;
        }
    }
}
