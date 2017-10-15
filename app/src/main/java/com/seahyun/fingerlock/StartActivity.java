package com.seahyun.fingerlock;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Intent  intent=new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager manager=getPackageManager();
        final ResolveInfo mInfo = manager.resolveActivity(intent, 0);
        System.out.println("Default Lancher is " + mInfo.activityInfo.applicationInfo.packageName);

        ComponentName componentName = new ComponentName(StartActivity.this, StartActivity.class);

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



        Intent i = null;
        //if(o.touch_tab.isChecked()){
        //if(lock.equals("touch_tab")){

        SharedPreferences prefs = getSharedPreferences("lock_mode", MODE_PRIVATE);
        int lock_mode = prefs.getInt("lock",-1);
        Log.d("어떤 lock? ", String.valueOf(lock_mode));
        if(lock_mode == 1){
            i = new Intent(getApplicationContext(), TouchTabLockScreen.class);
        }
        else if(lock_mode == 2){
            Toast.makeText(StartActivity.this, "아직 미완성", Toast.LENGTH_SHORT).show();
        }
        else if(lock_mode == 3){
            i = new Intent(getApplicationContext(), PinLock.class);
        }
        else{
            i= new Intent(getApplicationContext(),MainActivity.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(i);
    }
    boolean isDefaultLauncher()
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
}

