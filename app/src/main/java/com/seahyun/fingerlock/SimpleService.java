package com.seahyun.fingerlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SimpleService extends Service {
	private KeyguardManager km = null;
	private KeyguardManager.KeyguardLock keylock = null;

	public SimpleService(){};

	private BroadcastReceiver mReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "서비스 실행중!", Toast.LENGTH_LONG).show();
            /*String action = intent.getAction();
			if(action.equals("android.intent.action.SCREEN_OFF")){
            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	context.startActivity(i);
            }*/
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				if (km == null)
					km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

				if (keylock == null)
					keylock = km.newKeyguardLock(Context.KEYGUARD_SERVICE);

				disableKeyguard();

				Intent i = new Intent(context, touchtab_lockscreen.class);
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

		//km=(KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
        //if(km!=null){
        	//keylock = km.newKeyguardLock("test");
        	//keylock = km.newKeyguardLock(Activity.KEYGUARD_SERVICE);
			//keylock.disableKeyguard();
       // }
        
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Toast.makeText(this, "서비스가 시작되었습니다.", Toast.LENGTH_LONG).show();
		IntentFilter filter = new IntentFilter("com.seahyun.action.isAlive");
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

	public KeyguardManager.KeyguardLock getKeyLock(){
		return keylock;
	}

	/*public void setKeyGuardEnable(boolean bflag){
		Log.d("key gaurd 설정", "I will use keyguard");
		if(bflag == true){
			if(keylock == null)
				keylock = km.newKeyguardLock(Activity.KEYGUARD_SERVICE);
			keylock.disableKeyguard();
			Log.d("unlock >>", "unlock keyguard");

		}
		else{
			if(keylock!=null)
				keylock.reenableKeyguard();
			Log.d("lock >>", "lock keyguard");
		}
	}*/

	public void reenableKeyguard() {
		keylock.reenableKeyguard();
	}

	public void disableKeyguard() {
		keylock.disableKeyguard();
	}
}
