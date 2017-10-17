package com.seahyun.fingerlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StartService extends Service {
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keylock = null;

   // MainActivity o = new MainActivity();
    //TouchTabLockScreen TouchActivity = (TouchTabLockScreen)TouchTabLockScreen.TouchTabLockScreenActivity;

    private BroadcastReceiver mReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "서비스 실행중!", Toast.LENGTH_LONG).show();
//            ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            String action = intent.getAction();
            SharedPreferences prefs = getSharedPreferences("lock_mode", MODE_PRIVATE);

            int lock_mode = prefs.getInt("lock",-1);
            Intent i = null;
            if(action.equals("android.intent.action.SCREEN_ON")){
                Log.d("알림 >> ", "화면 켜짐");
                //String lock = o.switch_checked();

                //if(o.touch_tab.isChecked()){
                //if(lock.equals("touch_tab")){

                Log.d("어떤 lock? ", String.valueOf(lock_mode));
                //if(o.lock.equals("touch_tab")){
                if(lock_mode == 1){
                    Log.d("터치탭 실행","");
                    i = new Intent(context, TouchTabLockScreen.class);
                }
                //else if(o.color_tab.isChecked()){
                //else if(lock.equals("color_tab")){
                //else if(o.lock.equals("color_tab")){
                else if(lock_mode == 2){
                    Log.d("컬러탭 실행","");
                    i = new Intent(context, ColorTabLockScreen.class);
                }
                //else if(o.pin_lock.isChecked()){
                //else if(lock.equals("pin_lock")){
                //else if(o.lock.equals("pin_lock")){
                else if(lock_mode == 3){
                    Log.d("핀번호 실행","");
                    i = new Intent(context, PinLock.class);
                }
                else if(lock_mode == 4){
                    Log.d("지문 실행","");
                    i = new Intent(context, FingerprintLockScreen.class);
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else if(action.equals("android.intent.action.SCREEN_OFF")){
                Log.d("알림 >> ", "화면 꺼짐");
                //TouchActivity.finish();
                TouchTabLockScreen activity1 = (TouchTabLockScreen)TouchTabLockScreen.TouchTabLockScreenActivity;
                if(activity1!=null) {
                    Log.d("activity>>", "터치탭꺼짐");
                    activity1.finish();
                }
                ColorTabLockScreen activity2 = (ColorTabLockScreen)ColorTabLockScreen.ColorTabLockScreenActivity;
                if(activity2!=null) {
                    Log.d("activity>>", "컬러탭꺼짐");
                    activity2.finish();
                }

                PinLock activity3 = (PinLock) PinLock.PinLockActivity;
                if(activity3!=null) {
                    Log.d("activity>>", "pin꺼짐");
                    activity3.finish();

                }

                FingerprintLockScreen activity4 = (FingerprintLockScreen)FingerprintLockScreen.FingerLockActivity;
                if(activity4!=null) {
                    Log.d("activity>>", "finger꺼짐");
                    activity4.finish();
                }
            }
            else{
                Log.d("##################","#######");
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        km=(KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
        if(km!=null){
            keylock = km.newKeyguardLock("test");
            keylock.disableKeyguard();
        }

        Intent  intent=new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager manager=getPackageManager();
        final ResolveInfo mInfo = manager.resolveActivity(intent, 0);
        System.out.println("Default Lancher is " + mInfo.activityInfo.applicationInfo.packageName);
        ComponentName componentName = new ComponentName(StartService.this, StartService.class);

        if (!isDefaultLauncher())
        {
            Log.e("D", "MyActivity is not default home activity!");

            // 가짜 홈 Activity의 enable 상태를 변경한다.
            PackageManager pm = getPackageManager();
            int flag = ((pm.getComponentEnabledSetting(componentName) ==
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED) ?
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    : PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
            pm.setComponentEnabledSetting(componentName, flag,
                    PackageManager.DONT_KILL_APP);

            // 홈 Activty 요청하여 홈 선택 창이 오픈 되도록 한다.
            Intent selector = new Intent(Intent.ACTION_MAIN);
            selector.addCategory(Intent.CATEGORY_HOME);
            startActivity(selector);
        }

    }

    public boolean isDefaultLauncher()
    {
        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        List<IntentFilter> filters = new ArrayList<IntentFilter>();
        filters.add(filter);
        final String myPackageName = getPackageName();

        List<ComponentName> activities = new ArrayList<ComponentName>();
        final PackageManager packageManager = (PackageManager) getPackageManager();

        packageManager.getPreferredActivities(filters, activities, null);

        for (ComponentName activity : activities)
        {
            if (myPackageName.equals(activity.getPackageName()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "서비스가 시작되었습니다.", Toast.LENGTH_LONG).show();
        IntentFilter filter = new IntentFilter("com.androidhuman.action.isAlive");
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        if(keylock!=null){
            keylock.reenableKeyguard();
        }

        if(mReceiver != null)
            unregisterReceiver(mReceiver);
        Toast.makeText(this, "서비스 종료", Toast.LENGTH_LONG).show();
    }


}
