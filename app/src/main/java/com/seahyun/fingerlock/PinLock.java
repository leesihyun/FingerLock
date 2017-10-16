package com.seahyun.fingerlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PinLock extends AppCompatActivity implements OnClickListener {

    private static int password_size ;
    private static int count = 0;
    private int input_arr[] = new int[5];
    private int user_password[] = new int[4];
    private boolean select_mode = false;
    private int application_number = 0;
    private int app_num[]=new int [4];

    public static Activity PinLockActivity;

    TouchTabFourPassword o = new TouchTabFourPassword();

    ArrayList<String> arGeneral;
    List<ApplicationInfo> list;
    ArrayList<String> pacageNm;

    EditText num1;
    EditText num2;
    EditText num3;
    EditText num4;
    EditText num5;

    //SimpleService simpleservice = new SimpleService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);

        PinLockActivity = PinLock.this;
        int num = prefs.getInt("tab_num", 4);
        Log.d("잠금화면 터치탭 개수 >>", String.valueOf(num));
        setContentView(R.layout.lock_screen_pin);

        for(int i=0; i<5; i++){
            input_arr[i] = 0;
        }

        arGeneral = new ArrayList<String>();
        pacageNm = new ArrayList<String>();

        password_size = o.getPassword_count();
        Log.d("패스워드 자릿수 >> ", String.valueOf(password_size));
        //사용자가 설정한 비밀번호 가져오기
        SharedPreferences prefs2 = getSharedPreferences("password", MODE_PRIVATE);
        user_password[0] = prefs2.getInt("1st password",0);
        user_password[1] = prefs2.getInt("2nd password",0);
        user_password[2] = prefs2.getInt("3rd password",0);
        user_password[3] = prefs2.getInt("4rd password",0);

        SharedPreferences prefs3 = getSharedPreferences("num", MODE_PRIVATE);
        app_num[0] = prefs3.getInt("num1",0);
        app_num[1] = prefs3.getInt("num2",0);
        app_num[2] = prefs3.getInt("num3",0);
        app_num[3] = prefs3.getInt("num4",0);

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        num3 = (EditText) findViewById(R.id.num3);
        num4 = (EditText) findViewById(R.id.num4);
        num5 = (EditText) findViewById(R.id.num5);

        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);

        //check_application();

        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num1.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[0]= Integer.parseInt(num1.getText().toString());
                    num2.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num2.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[1]= Integer.parseInt(num2.getText().toString());
                    num3.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num3.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[2]= Integer.parseInt(num3.getText().toString());
                    num4.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num4.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[3]= Integer.parseInt(num4.getText().toString());
                    // input_handler();
                    //num5.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num5.length() == 1) {  // edit1  값의 제한값을 6이라고 가정했을때
                    input_arr[4]= Integer.parseInt(num5.getText().toString());
                    application_number=Integer.parseInt(num5.getText().toString());
                    execute_application(application_number);
                    // input_handler();
                    //num5.requestFocus(); // 두번째EditText 로 포커스가 넘어가게 됩니다
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    @Override
    public void onClick(View v) {
  /*
        switch (v.getId()){
            case R.id.button4_1:
            case R.id.button5_1:
            case R.id.button6_1:
                Toast.makeText(this, "1클릭", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "2클릭", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "3클릭", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "4클릭", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "5클릭", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "6클릭", Toast.LENGTH_SHORT).show();
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
*/
        if(count == password_size){
            count = 0;
            for(int i=0; i<4; i++){
                Log.d("사용자 입력 패스워드 : ", String.valueOf(input_arr[i]));
            }
            input_handler();
        }
    }

    public void input_handler(){
        int cnt=0;
        int i =0;
        select_mode=false;
        Log.d("전) cnt 값 : ", String.valueOf(cnt));
        for(int j=0; j<4; j++){
        //while(input_arr[i]!=0) {
            if (user_password[j] == input_arr[j]) {
                Log.d("패스워드 값 비교 : ", "user : " + String.valueOf(user_password[j]) + " input : " + String.valueOf(input_arr[j]));
                cnt++;
            }
            i++;
        }
        //}
        Log.d("후) cnt 값 : ", String.valueOf(cnt));
        if(cnt == 4){
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

        if(num == app_num[0]){
            String pakage_name = prefs.getString("name1", "");
            if(pakage_name.equals("")){
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
            else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        else if(num == app_num[1]){
            String pakage_name = prefs.getString("name2", "");
            if(pakage_name.equals("")){
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
            else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        else if(num == app_num[2]){
            String pakage_name = prefs.getString("name3", "");
            if(pakage_name.equals("")){
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
            else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        if(num == app_num[3]){
            String pakage_name = prefs.getString("name4", "");
            if(pakage_name.equals("")){
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
            else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

}
