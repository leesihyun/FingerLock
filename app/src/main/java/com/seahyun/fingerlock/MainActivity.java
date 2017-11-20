package com.seahyun.fingerlock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.tananaev.adblib.AdbConnection;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MainActivity extends AppCompatActivity{

    //private Button startServiceButton;
    //private Button stopServiceButton;
    //private Button checkAliveButton;
    Switch touch_tab;
    Switch pin_lock;
    Switch color_tab;
    Switch finger_lock;

    ImageButton touch_tab_setting;
    ImageButton pin_lock_setting;
    ImageButton color_tab_setting;
    ImageButton finger_lock_setting;

    private BackPressCloseSystem backPressCloseSystem;

    boolean firstLogConnect;

    public Reader reader;
    KeyPair keyPair;
    private static final String KEY_PUBLIC = "publicKey";
    private static final String KEY_PRIVATE = "privateKey";

    public static Activity MActivity;

    public MainActivity(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MActivity = MainActivity.this;

        firstLogConnect = false;

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("손가LOCK");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.activity_main);
        backPressCloseSystem = new BackPressCloseSystem(this);


        SharedPreferences prefs = getSharedPreferences("lock_mode", MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = prefs.edit();

        final SharedPreferences prefs2 = getSharedPreferences("switch_state", MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor2 = prefs2.edit();



        touch_tab = (Switch)findViewById(R.id.touch_tab_lock);
        /*이전 스위치 on/off 정보 유지*/
        if(prefs2.getBoolean("touch_tab", false)){
            touch_tab.setChecked(true);
        }
        touch_tab.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(touch_tab.isChecked()){
                            startService(new Intent(MainActivity.this, SimpleService.class));
                            touch_tab.setChecked(true);

                            prefsEditor.putInt("lock",1);
                            prefsEditor.commit();

                            prefsEditor2.putBoolean("touch_tab", true);
                            prefsEditor2.commit();

                        }
                        else{
                            stopService(new Intent(MainActivity.this, SimpleService.class));
                            touch_tab.setChecked(false);

                            prefsEditor2.putBoolean("touch_tab", false);
                            prefsEditor2.commit();
                        }
                    }
                });
        color_tab = (Switch)findViewById(R.id.color_tab_lock);
        /*이전 스위치 on/off 정보 유지*/
        if(prefs2.getBoolean("color_tab", false)){
            color_tab.setChecked(true);
        }
        color_tab.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(color_tab.isChecked()){
                            startService(new Intent(MainActivity.this, SimpleService.class));
                            color_tab.setChecked(true);

                            prefsEditor.putInt("lock",2);
                            prefsEditor.commit();

                            prefsEditor2.putBoolean("color_tab", true);
                            prefsEditor2.commit();
                        }
                        else{
                            stopService(new Intent(MainActivity.this, SimpleService.class));
                            color_tab.setChecked(false);

                            prefsEditor2.putBoolean("color_tab", false);
                            prefsEditor2.commit();
                        }
                    }
                });
        /*이전 스위치 on/off 정보 유지*/
        pin_lock = (Switch)findViewById(R.id.pin_lock);
        if(prefs2.getBoolean("pin_lock", false)){
            pin_lock.setChecked(true);
        }

        pin_lock.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(pin_lock.isChecked()){
                            startService(new Intent(MainActivity.this, SimpleService.class));
                            pin_lock.setChecked(true);

                            prefsEditor.putInt("lock",3);
                            prefsEditor.commit();

                            prefsEditor2.putBoolean("pin_lock", true);
                            prefsEditor2.commit();
                        }
                        else{
                            stopService(new Intent(MainActivity.this, SimpleService.class));
                            pin_lock.setChecked(false);

                            prefsEditor2.putBoolean("pin_lock", false);
                            prefsEditor2.commit();
                        }
                    }
                });


        finger_lock = (Switch)findViewById(R.id.finger_lock) ;
        if(prefs2.getBoolean("finger_lock", false)){
            finger_lock.setChecked(true);
        }
        finger_lock.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(finger_lock.isChecked()){
                            Intent intent = new Intent(MainActivity.this, SimpleService.class);

                            try {
                                keyPair = getKeyPair();
                                reader = new RemoteReader(keyPair);

                                new Thread(){
                                    public void run(){
                                        reader.create();
                                    }
                                }.start();

                            } catch (GeneralSecurityException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            startService(intent);
                            finger_lock.setChecked(true);


                            prefsEditor.putInt("lock",4);
                            prefsEditor.commit();

                            prefsEditor2.putBoolean("finger_lock", true);
                            prefsEditor2.commit();
                        }
                        else{
                            stopService(new Intent(MainActivity.this, SimpleService.class));
                            finger_lock.setChecked(false);

                            prefsEditor2.putBoolean("finger_lock", false);
                            prefsEditor2.commit();
                        }
                    }
                }
        );


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

        color_tab_setting = (ImageButton)findViewById(R.id.color_tab_lock_setting);
        color_tab_setting.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Log.d("환경설정 >> ", "컬러탭");
                        Intent intent = new Intent(getApplicationContext(), ColorTabSettingActivity.class);
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

        finger_lock_setting = (ImageButton)findViewById(R.id.finger_lock_setting);
        finger_lock_setting.setOnClickListener(new OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       Log.d("환경설정 >> ", "지문설정");
                                                       Intent intent = new Intent(getApplicationContext(), FingerprintSettingActivity.class);
                                                       startActivity(intent);
                                                   }
                                               }
        );



    }

    @Override public void onBackPressed() {
        super.onBackPressed();

        //backPressCloseSystem.onBackPressed();
        //
        this.moveTaskToBack(true);
        this.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

//    public String switch_checked(){
//
//        Log.d("선택된 스피너? in MainActivity>> ", lock);
//        return lock;
//
//    }




    private KeyPair getKeyPair() throws GeneralSecurityException, IOException {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        KeyPair keyPair;

        if (preferences.contains(KEY_PUBLIC) && preferences.contains(KEY_PRIVATE)) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(
                    Base64.decode(preferences.getString(KEY_PUBLIC, null), Base64.DEFAULT)));
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
                    Base64.decode(preferences.getString(KEY_PRIVATE, null), Base64.DEFAULT)));

            keyPair = new KeyPair(publicKey, privateKey);
        } else {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();

            preferences
                    .edit()
                    .putString(KEY_PUBLIC, Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT))
                    .putString(KEY_PRIVATE, Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT))
                    .apply();
        }

        return keyPair;
    }

}
