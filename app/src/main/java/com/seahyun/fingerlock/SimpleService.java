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

	MainActivity o = new MainActivity();

	private BroadcastReceiver mReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "서비스 실행중!", Toast.LENGTH_LONG).show();
			String action = intent.getAction();
			if(action.equals("android.intent.action.SCREEN_ON")){
				//String lock = o.switch_checked();
				Intent i = null;
				//if(o.touch_tab.isChecked()){
				//if(lock.equals("touch_tab")){

				SharedPreferences prefs = getSharedPreferences("lock_mode", MODE_PRIVATE);
				int lock_mode = prefs.getInt("lock",-1);
				Log.d("어떤 lock? ", String.valueOf(lock_mode));
				//if(o.lock.equals("touch_tab")){
				if(lock_mode == 1){
					i = new Intent(context, touchtab_lockscreen.class);
				}
				//else if(o.color_tab.isChecked()){
				//else if(lock.equals("color_tab")){
				//else if(o.lock.equals("color_tab")){
				else if(lock_mode == 2){
					Toast.makeText(SimpleService.this, "아직 미완성", Toast.LENGTH_SHORT).show();
				}
				//else if(o.pin_lock.isChecked()){
				//else if(lock.equals("pin_lock")){
				//else if(o.lock.equals("pin_lock")){
				else if(lock_mode == 3){
					i = new Intent(context, PinLock.class);
				}
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
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
