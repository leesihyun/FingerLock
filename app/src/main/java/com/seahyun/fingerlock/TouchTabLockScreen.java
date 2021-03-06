package com.seahyun.fingerlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.Touch;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TouchTabLockScreen extends AppCompatActivity implements OnClickListener {

	private static int password_size =0;
	private static int count = 0;
	private int input_arr[] = new int[8];
	private int user_password[] = new int[8];
	private boolean select_mode = false;
	private int application_number = 0;
	//private int app_num[] = new int[4];
	private static String TAG = "TouchTabLockscreen";

	public static Activity TouchTabLockScreenActivity;

	private HomeKeyLocker mHomeKeyLocker;
	TouchTabFourPassword o = new TouchTabFourPassword();

	ArrayList<String> arGeneral;
	List<ApplicationInfo> list;
	ArrayList<String> pacageNm;

	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;

	//SimpleService simpleservice = new SimpleService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
		getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);



		SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);

		TouchTabLockScreenActivity = TouchTabLockScreen.this;
		password_size = 0;
		int num = prefs.getInt("tab_num", 4);
		Log.d("잠금화면 터치탭 개수 >>", String.valueOf(num));
		try
		{
			this.getSupportActionBar().hide();
		} catch (NullPointerException e){}

		if(num == 4){
			setContentView(R.layout.lock_screen_touch_tab_four);
		}
		else if(num == 5){
			setContentView(R.layout.lock_screen_touch_tab_five);
		}
		else if(num == 6){
			setContentView(R.layout.lock_screen_touch_tab_six);
		}

		for(int i=0; i<input_arr.length; i++){
			input_arr[i] = 0;
		}

		arGeneral = new ArrayList<String>();
		pacageNm = new ArrayList<String>();

		//password_size = o.getPassword_count();
		//Log.d("패스워드 자릿수 >> ", String.valueOf(password_size));
		//사용자가 설정한 비밀번호 가져오기
		SharedPreferences prefs2 = getSharedPreferences("password", MODE_PRIVATE);
		user_password[0] = prefs2.getInt("1st password",0);
		user_password[1] = prefs2.getInt("2nd password",0);
		user_password[2] = prefs2.getInt("3rd password",0);
		user_password[3] = prefs2.getInt("4th password",0);
		user_password[4] = prefs2.getInt("5th password",0);
		user_password[5] = prefs2.getInt("6th password",0);
		user_password[6] = prefs2.getInt("7th password",0);
		user_password[7] = prefs2.getInt("8th password",0);

//		SharedPreferences pref3 = getSharedPreferences("num", MODE_PRIVATE);
//		app_num[0]=pref3.getInt("num1",0);
//		app_num[1]=pref3.getInt("num2",0);
//		app_num[2]=pref3.getInt("num3",0);
//		app_num[3]=pref3.getInt("num4",0);

		Log.d(TAG, "##사용자가 설정한 비밀번호?");
		for(int i=0;i<8; i++){
			Log.d(TAG, String.valueOf(user_password[i]));
			if(user_password[i]!=0){
				password_size++;
			}
		}
		Log.d(TAG, "사용자가 설정한 비밀번호 자릿수? "+String.valueOf(password_size));


		if(num == 4) {
			button1 = (Button) findViewById(R.id.button4_1);
			button2 = (Button) findViewById(R.id.button4_2);
			button3 = (Button) findViewById(R.id.button4_3);
			button4 = (Button) findViewById(R.id.button4_4);

			button1.setOnClickListener(this);
			button2.setOnClickListener(this);
			button3.setOnClickListener(this);
			button4.setOnClickListener(this);
		}
		else if(num == 5){
			button1 = (Button) findViewById(R.id.button5_1);
			button2 = (Button) findViewById(R.id.button5_2);
			button3 = (Button) findViewById(R.id.button5_3);
			button4 = (Button) findViewById(R.id.button5_4);
			button5 = (Button) findViewById(R.id.button5_5);

			button1.setOnClickListener(this);
			button2.setOnClickListener(this);
			button3.setOnClickListener(this);
			button4.setOnClickListener(this);
			button5.setOnClickListener(this);

		}
		else if(num == 6){
			button1 = (Button) findViewById(R.id.button6_1);
			button2 = (Button) findViewById(R.id.button6_2);
			button3 = (Button) findViewById(R.id.button6_3);
			button4 = (Button) findViewById(R.id.button6_4);
			button5 = (Button) findViewById(R.id.button6_5);
			button6 = (Button) findViewById(R.id.button6_6);

			button1.setOnClickListener(this);
			button2.setOnClickListener(this);
			button3.setOnClickListener(this);
			button4.setOnClickListener(this);
			button5.setOnClickListener(this);
			button6.setOnClickListener(this);
		}

		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(this);
		//check_application();


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.button4_1:
			case R.id.button5_1:
			case R.id.button6_1:
				//Toast.makeText(this, "1클릭", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "1클릭");
				if(select_mode == false){
					for(int i=0; i<input_arr.length; i++){
						if(input_arr[i]==0){
							input_arr[i] = 1;
							i = input_arr.length;
						}
					}
					count++;
					Log.d("count 값 : ", String.valueOf(count));
				}
				else{
					application_number = 1;
					execute_application(application_number);
				}
				break;

			case R.id.button4_2:
			case R.id.button5_2:
			case R.id.button6_2:
				//Toast.makeText(this, "2클릭", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "2클릭");
				if(select_mode == false) {
					for (int i = 0; i < input_arr.length; i++) {
						if (input_arr[i] == 0) {
							input_arr[i] = 2;
							i = input_arr.length;
						}
					}
					count++;
					Log.d("count 값 : ", String.valueOf(count));
				}
				else{
					application_number = 2;
					execute_application(application_number);
				}
				break;

			case R.id.button4_3:
			case R.id.button5_3:
			case R.id.button6_3:
				//Toast.makeText(this, "3클릭", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "3클릭");
				if(select_mode == false) {
					for (int i = 0; i < input_arr.length; i++) {
						if (input_arr[i] == 0) {
							input_arr[i] = 3;
							i = input_arr.length;
						}
					}
					count++;
					Log.d("count 값 : ", String.valueOf(count));
				}
				else{
					application_number = 3;
					execute_application(application_number);
				}
				break;

			case R.id.button4_4:
			case R.id.button5_4:
			case R.id.button6_4:
				//Toast.makeText(this, "4클릭", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "4클릭");
				if(select_mode == false) {
					for (int i = 0; i < input_arr.length; i++) {
						if (input_arr[i] == 0) {
							input_arr[i] = 4;
							i = input_arr.length;
						}
					}
					count++;
					Log.d("count 값 : ", String.valueOf(count));
				}
				else{
					application_number = 4;
					execute_application(application_number);
				}
				break;
			case R.id.button5_5:
			case R.id.button6_5:
				//Toast.makeText(this, "5클릭", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "5클릭");
				if(select_mode == false) {
					for (int i = 0; i < input_arr.length; i++) {
						if (input_arr[i] == 0) {
							input_arr[i] = 5;
							i = input_arr.length;
						}
					}
					count++;
					Log.d("count 값 : ", String.valueOf(count));
				}
				else{
					application_number = 5;
					execute_application(application_number);
				}
				break;
			case R.id.button6_6:
				//Toast.makeText(this, "6클릭", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "6클릭");
				if(select_mode == false) {
					for (int i = 0; i < input_arr.length; i++) {
						if (input_arr[i] == 0) {
							input_arr[i] = 6;
							i = input_arr.length;
						}
					}
					count++;
					Log.d("count 값 : ", String.valueOf(count));
				}
				else{
					application_number = 6;
					execute_application(application_number);
				}
				break;
		}

		if(count == password_size){
			count = 0;
			for(int i=0; i<input_arr.length; i++){
				Log.d("##사용자 입력 패스워드 : ", String.valueOf(input_arr[i]));
			}
			input_handler();
		}
	}

	public void input_handler(){
		int cnt=0;
		int i =0;
		select_mode=false;
		//Log.d("전) cnt 값 : ", String.valueOf(cnt));
		//for(int i=0; input_arr[i]!=0; i++){
		while(input_arr[i]!=0) {
			if (user_password[i] == input_arr[i]) {
				Log.d("패스워드 값 비교 : ", "user : " + String.valueOf(user_password[i]) + " input : " + String.valueOf(input_arr[i]));
				cnt++;
			}
			i++;
		}
		//}
		//Log.d("후) cnt 값 : ", String.valueOf(cnt));
		if(cnt == password_size){
			Toast.makeText(this, "비밀번호가 동일합니다. 바로가기 원하는 어플 번호를 터치해주세요", Toast.LENGTH_SHORT).show();
			select_mode = true;
			//simpleservice.setKeyGuardEnable(true);

		}
		else{
			Toast.makeText(this, "비밀번호를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
		}
		for(int j=0; j<input_arr.length; j++){
			input_arr[j] = 0;
		}
	}

	public void execute_application(int num) {
		Toast.makeText(this, "선택한 어플리케이션 번호는 "+ String.valueOf(application_number), Toast.LENGTH_SHORT).show();
		PackageManager packageManager = this.getPackageManager();

		SharedPreferences prefs = getSharedPreferences("PakageName", MODE_PRIVATE);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		if(num == 1){
			String pakage_name = prefs.getString("name1", "");
			if(pakage_name.equals("")){
				Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
				System.exit(0);
			}
			else {
				Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mHomeKeyLocker.unlock();
				finish();
			}
		}

		else if(num == 2){
			String pakage_name = prefs.getString("name2", "");
			if(pakage_name.equals("")){
				Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
				System.exit(0);
			}
			else {
				Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mHomeKeyLocker.unlock();
				finish();
			}
		}
		else if(num == 3){
			String pakage_name = prefs.getString("name3", "");
			if(pakage_name.equals("")){
				Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
				System.exit(0);
			}
			else {
				Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mHomeKeyLocker.unlock();
				finish();
			}
		}
		if(num == 4){
			String pakage_name = prefs.getString("name4", "");
			if(pakage_name.equals("")){
				Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
				System.exit(0);
			}
			else {
				Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mHomeKeyLocker.unlock();
				finish();
			}
		}
		if(num == 5){
			String pakage_name = prefs.getString("name5", "");
			if(pakage_name.equals("")){
				Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
				System.exit(0);
			}
			else {
				Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mHomeKeyLocker.unlock();
				finish();
			}
		}
		if(num == 6){
			String pakage_name = prefs.getString("name6", "");
			if(pakage_name.equals("")){
				Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
				System.exit(0);
			}
			else {
				Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mHomeKeyLocker.unlock();
				finish();
			}
		}
	}

/*public void check_application(){
		Toast.makeText(this, "설치된 앱 확인", Toast.LENGTH_SHORT).show();

		final PackageManager pm = getPackageManager();
		List<ApplicationInfo> list = pm.getInstalledApplications(0);
		for (ApplicationInfo applicationInfo : list) {
			String name = String.valueOf(applicationInfo.loadLabel(pm));    // 앱 이름
			String pName = applicationInfo.packageName;   // 앱 패키지
			Log.d("설치된 앱 이름>> ", name);
			Log.d("설치된 앱 패키지>> ", pName);
		}

	}*/

	@Override
	public void onBackPressed(){
		if(select_mode == false)
			Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
		else{
			//simpleservice.onDestroy();
			System.exit(0);
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_BACK:
					// 단말기의 BACK버튼
					return false;
				case KeyEvent.KEYCODE_MENU:
					return false;
			}

		}
		return super.dispatchKeyEvent(event);
	}

//	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//
//			Log.i("", ">>> Home Event");
//
//			String action = intent.getAction();
//			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
//				if (reason != null) {
//					if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//						Log.i("", ">>> Home Clcik Event");
//						if (select_mode == false)
//							Toast.makeText(TouchTabLockScreen.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
//						else {
//							//simpleservice.onDestroy();
//							System.exit(0);
//						}
//					} else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//						Log.i("", ">>> Home Long Press Event");
//						if (select_mode == false)
//							Toast.makeText(TouchTabLockScreen.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
//						else {
//							//simpleservice.onDestroy();
//							System.exit(0);
//						}
//					}
//				}
//			}
//
//		}
//	};



}
