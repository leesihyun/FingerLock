package com.seahyun.fingerlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class SimpleService extends Service {
	private KeyguardManager km = null;
	private KeyguardManager.KeyguardLock keylock = null;

	//MainActivity o = new MainActivity();
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
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
			else if(action.equals("android.intent.action.SCREEN_OFF")){
				Log.d("알림 >> ", "화면 꺼짐");
				//TouchActivity.finish();
				TouchTabLockScreen activity1 = (TouchTabLockScreen)TouchTabLockScreen.TouchTabLockScreenActivity;
				if(activity1!=null)
					activity1.finish();

                ColorTabLockScreen activity2 = (ColorTabLockScreen)ColorTabLockScreen.ColorTabLockScreenActivity;
                if(activity2!=null)
                    activity2.finish();

                PinLock activity3 = (PinLock) PinLock.PinLockActivity;
                if(activity3!=null)
                    activity3.finish();

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
