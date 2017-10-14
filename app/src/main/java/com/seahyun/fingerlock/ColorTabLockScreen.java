package com.seahyun.fingerlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ColorTabLockScreen extends AppCompatActivity implements OnClickListener {

    private static int password_size = 0;
    private static int count = 0;
    private int input_arr[] = new int[8];
    private int user_password[] = new int[8];
    private boolean select_mode = false;
    private int application_number = 0;
    //private int app_num[] = new int[4];
    private View header4, header5, header6;

    private static int color[] = new int[6];

    private static String TAG = "ColorTabLockscreen";
    public static Activity ColorTabLockScreenActivity;

    TouchTabFourPassword o = new TouchTabFourPassword();

    ArrayList<String> arGeneral;
    List<ApplicationInfo> list;
    ArrayList<String> pacageNm;

//    Button button1;
//    Button button2;
//    Button button3;
//    Button button4;
//    Button button5;
//    Button button6;

    SimpleService simpleservice = new SimpleService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);

        ColorTabLockScreenActivity = ColorTabLockScreen.this;

        password_size = 0;
        color[0] = R.color.Orange;
        color[1] = R.color.Yellow;
        color[2] = R.color.Green;
        color[3] = R.color.Blue;
        color[4] = R.color.Purple;
        color[5] = R.color.Pink;

        int num = prefs.getInt("tab_num", 4);
        Log.d("잠금화면 컬러탭 개수 >>", String.valueOf(num));
        if (num == 4) {
            setContentView(R.layout.lock_screen_color_tab_four);
        } else if (num == 5) {
            setContentView(R.layout.lock_screen_color_tab_five);
        } else if (num == 6) {
            setContentView(R.layout.lock_screen_color_tab_six);
        }


        tabColorSetting();


        for (int i = 0; i < input_arr.length; i++) {
            input_arr[i] = 0;
        }

        arGeneral = new ArrayList<String>();
        pacageNm = new ArrayList<String>();

        //password_size = o.getPassword_count();
        //Log.d("패스워드 자릿수 >> ", String.valueOf(password_size));
        //사용자가 설정한 비밀번호 가져오기
        SharedPreferences prefs2 = getSharedPreferences("password", MODE_PRIVATE);
        user_password[0] = prefs2.getInt("1st password", 0);
        user_password[1] = prefs2.getInt("2nd password", 0);
        user_password[2] = prefs2.getInt("3rd password", 0);
        user_password[3] = prefs2.getInt("4th password", 0);
        user_password[4] = prefs2.getInt("5th password", 0);
        user_password[5] = prefs2.getInt("6th password", 0);
        user_password[6] = prefs2.getInt("7th password", 0);
        user_password[7] = prefs2.getInt("8th password", 0);

//		SharedPreferences pref3 = getSharedPreferences("num", MODE_PRIVATE);
//		app_num[0]=pref3.getInt("num1",0);
//		app_num[1]=pref3.getInt("num2",0);
//		app_num[2]=pref3.getInt("num3",0);
//		app_num[3]=pref3.getInt("num4",0);

        Log.d(TAG, "사용자가 설정한 비밀번호?");
        for (int i = 0; i < 8; i++) {
            Log.d(TAG, String.valueOf(user_password[i]));
            if (user_password[i] != 0) {
                password_size++;
            }
        }
        Log.d(TAG, "사용자가 설정한 비밀번호 자릿수? " + String.valueOf(password_size));

//
//        if(num == 4) {
//            button1 = (Button) findViewById(R.id.button4_1);
//            button2 = (Button) findViewById(R.id.button4_2);
//            button3 = (Button) findViewById(R.id.button4_3);
//            button4 = (Button) findViewById(R.id.button4_4);
//
//            button1.setOnClickListener(this);
//            button2.setOnClickListener(this);
//            button3.setOnClickListener(this);
//            button4.setOnClickListener(this);
//        }
//        else if(num == 5){
//            button1 = (Button) findViewById(R.id.button5_1);
//            button2 = (Button) findViewById(R.id.button5_2);
//            button3 = (Button) findViewById(R.id.button5_3);
//            button4 = (Button) findViewById(R.id.button5_4);
//            button5 = (Button) findViewById(R.id.button5_5);
//
//            button1.setOnClickListener(this);
//            button2.setOnClickListener(this);
//            button3.setOnClickListener(this);
//            button4.setOnClickListener(this);
//            button5.setOnClickListener(this);
//
//        }
//        else if(num == 6){
//            button1 = (Button) findViewById(R.id.button6_1);
//            button2 = (Button) findViewById(R.id.button6_2);
//            button3 = (Button) findViewById(R.id.button6_3);
//            button4 = (Button) findViewById(R.id.button6_4);
//            button5 = (Button) findViewById(R.id.button6_5);
//            button6 = (Button) findViewById(R.id.button6_6);
//
//            button1.setOnClickListener(this);
//            button2.setOnClickListener(this);
//            button3.setOnClickListener(this);
//            button4.setOnClickListener(this);
//            button5.setOnClickListener(this);
//            button6.setOnClickListener(this);
//        }


        //check_application();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button4_1:
            case R.id.button5_1:
            case R.id.button6_1:
                Toast.makeText(this, "1클릭", Toast.LENGTH_SHORT).show();
                if (select_mode == false) {
                    for (int i = 0; i < input_arr.length; i++) {
                        if (input_arr[i] == 0) {
                            int button_color = getButtonColor(1);
                            input_arr[i] = button_color;
                            i = input_arr.length;
                        }
                    }
                    count++;
                    Log.d("count 값 : ", String.valueOf(count));
                } else {
                    application_number = getButtonColor(1);
                    execute_application(application_number);
                }
                break;

            case R.id.button4_2:
            case R.id.button5_2:
            case R.id.button6_2:
                Toast.makeText(this, "2클릭", Toast.LENGTH_SHORT).show();
                if (select_mode == false) {
                    for (int i = 0; i < input_arr.length; i++) {
                        if (input_arr[i] == 0) {
                            int button_color = getButtonColor(2);
                            input_arr[i] = button_color;
                            i = input_arr.length;
                        }
                    }
                    count++;
                    Log.d("count 값 : ", String.valueOf(count));
                } else {
                    application_number = getButtonColor(2);
                    execute_application(application_number);
                }
                break;

            case R.id.button4_3:
            case R.id.button5_3:
            case R.id.button6_3:
                Toast.makeText(this, "3클릭", Toast.LENGTH_SHORT).show();
                if (select_mode == false) {
                    for (int i = 0; i < input_arr.length; i++) {
                        if (input_arr[i] == 0) {
                            int button_color = getButtonColor(3);
                            input_arr[i] = button_color;
                            i = input_arr.length;
                        }
                    }
                    count++;
                    Log.d("count 값 : ", String.valueOf(count));
                } else {
                    application_number = getButtonColor(3);
                    execute_application(application_number);
                }
                break;

            case R.id.button4_4:
            case R.id.button5_4:
            case R.id.button6_4:
                Toast.makeText(this, "4클릭", Toast.LENGTH_SHORT).show();
                if (select_mode == false) {
                    for (int i = 0; i < input_arr.length; i++) {
                        if (input_arr[i] == 0) {
                            int button_color = getButtonColor(4);
                            input_arr[i] = button_color;
                            i = input_arr.length;
                        }
                    }
                    count++;
                    Log.d("count 값 : ", String.valueOf(count));
                } else {
                    application_number = getButtonColor(4);
                    execute_application(application_number);
                }
                break;
            case R.id.button5_5:
            case R.id.button6_5:
                Toast.makeText(this, "5클릭", Toast.LENGTH_SHORT).show();
                if (select_mode == false) {
                    for (int i = 0; i < input_arr.length; i++) {
                        if (input_arr[i] == 0) {
                            int button_color = getButtonColor(5);
                            input_arr[i] = button_color;
                            i = input_arr.length;
                        }
                    }
                    count++;
                    Log.d("count 값 : ", String.valueOf(count));
                } else {
                    application_number = getButtonColor(5);
                    execute_application(application_number);
                }
                break;
            case R.id.button6_6:
                Toast.makeText(this, "6클릭", Toast.LENGTH_SHORT).show();
                if (select_mode == false) {
                    for (int i = 0; i < input_arr.length; i++) {
                        if (input_arr[i] == 0) {
                            int button_color = getButtonColor(6);
                            input_arr[i] = button_color;
                            i = input_arr.length;
                        }
                    }
                    count++;
                    Log.d("count 값 : ", String.valueOf(count));
                } else {
                    application_number = getButtonColor(6);
                    execute_application(application_number);
                }
                break;
        }

        if (count == password_size) {
            count = 0;
            for (int i = 0; i < input_arr.length; i++) {
                Log.d("사용자 입력 패스워드 : ", String.valueOf(input_arr[i]));
            }
            input_handler();
        }
    }

    public void input_handler() {
        int cnt = 0;
        int i = 0;
        select_mode = false;
        Log.d("전) cnt 값 : ", String.valueOf(cnt));
        //for(int i=0; input_arr[i]!=0; i++){
        while (input_arr[i] != 0) {
            if (user_password[i] == input_arr[i]) {
                Log.d("패스워드 값 비교 : ", "user : " + String.valueOf(user_password[i]) + " input : " + String.valueOf(input_arr[i]));
                cnt++;
            }
            i++;
        }
        //}
        Log.d("후) cnt 값 : ", String.valueOf(cnt));
        if (cnt == password_size) {
            Toast.makeText(this, "비밀번호가 동일합니다. 바로가기 원하는 어플 번호를 터치해주세요", Toast.LENGTH_SHORT).show();
            select_mode = true;
            //simpleservice.setKeyGuardEnable(true);

        } else {
            Toast.makeText(this, "비밀번호를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
        for (int j = 0; j < input_arr.length; j++) {
            input_arr[j] = 0;
        }
    }

    public void execute_application(int num) {
        Toast.makeText(this, "선택한 어플리케이션 번호는 " + String.valueOf(application_number), Toast.LENGTH_SHORT).show();
        PackageManager packageManager = this.getPackageManager();

        SharedPreferences prefs = getSharedPreferences("PakageName", MODE_PRIVATE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        if (num == 1) {
            String pakage_name = prefs.getString("name1", "");
            if (pakage_name.equals("")) {
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            } else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        } else if (num == 2) {
            String pakage_name = prefs.getString("name2", "");
            if (pakage_name.equals("")) {
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            } else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        } else if (num == 3) {
            String pakage_name = prefs.getString("name3", "");
            if (pakage_name.equals("")) {
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            } else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
        if (num == 4) {
            String pakage_name = prefs.getString("name4", "");
            if (pakage_name.equals("")) {
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            } else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
        if (num == 5) {
            String pakage_name = prefs.getString("name5", "");
            if (pakage_name.equals("")) {
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            } else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
        if (num == 6) {
            String pakage_name = prefs.getString("name6", "");
            if (pakage_name.equals("")) {
                Toast.makeText(this, "미리 등록한 어플이 없습니다", Toast.LENGTH_SHORT).show();
                System.exit(0);
            } else {
                Intent intent = packageManager.getLaunchIntentForPackage(pakage_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    public void tabColorSetting() {

        SharedPreferences prefs2 = getSharedPreferences("user_tab_num", MODE_PRIVATE);
        int tab_num2 = prefs2.getInt("tab_num", 4);

        int random[] = new int[tab_num2];

        for (int i = 0; i < random.length; i++) {

            random[i] = (int) (Math.random() * tab_num2);

            for (int j = 0; j < i; j++) {
                if (random[i] == random[j]) {
                    i--;
                    break;
                }
            }

        }

        for (int i = 0; i < random.length; i++) {
            Log.d(TAG, "랜덤숫자 >> " + String.valueOf(random[i]));
        }
        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        String colorname[] = new String[tab_num2];


        for (int i = 0; i < tab_num2; i++) {
            if (color[random[i]] == R.color.Orange)
                colorname[i] = "Orange";
            else if (color[random[i]] == R.color.Yellow)
                colorname[i] = "Yellow";
            else if (color[random[i]] == R.color.Green)
                colorname[i] = "Green";
            else if (color[random[i]] == R.color.Blue)
                colorname[i] = "Blue";
            else if (color[random[i]] == R.color.Purple)
                colorname[i] = "Purple";
            else if (color[random[i]] == R.color.Pink)
                colorname[i] = "Pink";
        }

        for(int i=0; i<tab_num2; i++){
            Log.d(TAG, "컬러네임 >> "+colorname[i]);
        }
        Log.d(TAG,"tab_num2 : "+String.valueOf(tab_num2));


        if (tab_num2 == 4) {
            prefsEditor.putString("color1", colorname[0]);
            prefsEditor.putString("color2", colorname[1]);
            prefsEditor.putString("color3", colorname[2]);
            prefsEditor.putString("color4", colorname[3]);
        } else if (tab_num2 == 5) {
            prefsEditor.putString("color1", colorname[0]);
            prefsEditor.putString("color2", colorname[1]);
            prefsEditor.putString("color3", colorname[2]);
            prefsEditor.putString("color4", colorname[3]);
            prefsEditor.putString("color5", colorname[4]);
        } else if (tab_num2 == 6) {
            prefsEditor.putString("color1", colorname[0]);
            prefsEditor.putString("color2", colorname[1]);
            prefsEditor.putString("color3", colorname[2]);
            prefsEditor.putString("color4", colorname[3]);
            prefsEditor.putString("color5", colorname[4]);
            prefsEditor.putString("color6", colorname[5]);
        }


//        Log.d(TAG, "탭 색깔 >> "+colorname[0]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[1]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[2]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[3]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[4]);
//        Log.d(TAG, "탭 색깔 >> "+colorname[5]);
        prefsEditor.commit();


        Button button1, button2, button3, button4, button5, button6;

//        header4 = getLayoutInflater().inflate(R.layout.color_tab_four, null, false);
//        header5 = getLayoutInflater().inflate(R.layout.color_tab_five, null, false);
//        header6 = getLayoutInflater().inflate(R.layout.color_tab_six, null, false);

        if (tab_num2 == 4) {
            button1 = (Button) findViewById(R.id.button4_1);
            button2 = (Button) findViewById(R.id.button4_2);
            button3 = (Button) findViewById(R.id.button4_3);
            button4 = (Button) findViewById(R.id.button4_4);

            button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[0]]));
            button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[1]]));
            button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[2]]));
            button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[3]]));

            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);

        } else if (tab_num2 == 5) {
            button1 = (Button) findViewById(R.id.button5_1);
            button2 = (Button) findViewById(R.id.button5_2);
            button3 = (Button) findViewById(R.id.button5_3);
            button4 = (Button) findViewById(R.id.button5_4);
            button5 = (Button) findViewById(R.id.button5_5);

            button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[0]]));
            button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[1]]));
            button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[2]]));
            button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[3]]));
            button5.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[4]]));

            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);
            button5.setOnClickListener(this);

        } else if (tab_num2 == 6) {
            button1 = (Button) findViewById(R.id.button6_1);
            button2 = (Button) findViewById(R.id.button6_2);
            button3 = (Button) findViewById(R.id.button6_3);
            button4 = (Button) findViewById(R.id.button6_4);
            button5 = (Button) findViewById(R.id.button6_5);
            button6 = (Button) findViewById(R.id.button6_6);

            button1.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[0]]));
            button2.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[1]]));
            button3.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[2]]));
            button4.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[3]]));
            button5.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[4]]));
            button6.setBackgroundColor(getApplicationContext().getResources().getColor(color[random[5]]));

            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);
            button5.setOnClickListener(this);
            button6.setOnClickListener(this);
        }
    }

    public int getButtonColor(int button_num) {

        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        Log.d(TAG, "button_num >> " + String.valueOf(button_num));
        if (button_num == 1) {
            String colorname = prefs.getString("color1", "#FFFFFF");
            Log.d(TAG, "Colorname >> " + colorname);

            if (colorname.equals("Orange"))
                return 1;
            if (colorname.equals("Yellow"))
                return 2;
            if (colorname.equals("Green"))
                return 3;
            if (colorname.equals("Blue"))
                return 4;
            if (colorname.equals("Purple"))
                return 5;
            if (colorname.equals("Pink"))
                return 6;
        } else if (button_num == 2) {
            String colorname = prefs.getString("color2", "#FFFFFF");

            if (colorname.equals("Orange"))
                return 1;
            if (colorname.equals("Yellow"))
                return 2;
            if (colorname.equals("Green"))
                return 3;
            if (colorname.equals("Blue"))
                return 4;
            if (colorname.equals("Purple"))
                return 5;
            if (colorname.equals("Pink"))
                return 6;

        } else if (button_num == 3) {
            String colorname = prefs.getString("color3", "#FFFFFF");

            if (colorname.equals("Orange"))
                return 1;
            if (colorname.equals("Yellow"))
                return 2;
            if (colorname.equals("Green"))
                return 3;
            if (colorname.equals("Blue"))
                return 4;
            if (colorname.equals("Purple"))
                return 5;
            if (colorname.equals("Pink"))
                return 6;

        } else if (button_num == 4) {
            String colorname = prefs.getString("color4", "#FFFFFF");

            if (colorname.equals("Orange"))
                return 1;
            if (colorname.equals("Yellow"))
                return 2;
            if (colorname.equals("Green"))
                return 3;
            if (colorname.equals("Blue"))
                return 4;
            if (colorname.equals("Purple"))
                return 5;
            if (colorname.equals("Pink"))
                return 6;

        } else if (button_num == 5) {
            String colorname = prefs.getString("color5", "#FFFFFF");

            if (colorname.equals("Orange"))
                return 1;
            if (colorname.equals("Yellow"))
                return 2;
            if (colorname.equals("Green"))
                return 3;
            if (colorname.equals("Blue"))
                return 4;
            if (colorname.equals("Purple"))
                return 5;
            if (colorname.equals("Pink"))
                return 6;

        } else if (button_num == 6) {
            String colorname = prefs.getString("color6", "#FFFFFF");

            if (colorname.equals("Orange"))
                return 1;
            if (colorname.equals("Yellow"))
                return 2;
            if (colorname.equals("Green"))
                return 3;
            if (colorname.equals("Blue"))
                return 4;
            if (colorname.equals("Purple"))
                return 5;
            if (colorname.equals("Pink"))
                return 6;
        }
        return 0;

    }


    @Override
    public void onBackPressed() {
        if (select_mode == false)
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        else {
            //simpleservice.onDestroy();
            System.exit(0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        tabColorSetting();

    }
}
