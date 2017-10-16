package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FingerprintEditActivity extends AppCompatActivity {
    private String TAG = FingerprintEditActivity.class.getSimpleName();
    private ImageView fingeredit_image;
    private Button fingeredit_selectButton;
    private EditText fingeredit_editText;
    private Button fingeredit_completeButton;
    private Button fingeredit_deleteButton;

    public String pack_name = "";
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("지문 수정");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));
        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));
        setContentView(R.layout.fingerprint_edit);

        fingeredit_selectButton = (Button) findViewById(R.id.fingeredit_selectButton);
        fingeredit_editText = (EditText) findViewById(R.id.fingeredit_editText);
//        fingeredit_deleteButton = (Button) findViewById(R.id.fingeredit_deleteButton);
        fingeredit_completeButton = (Button) findViewById(R.id.fingeredit_completeButton);


        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);

        String fingeredit_fingername = prefs.getString("fingeredit_fingername", "");
        String fingeredit_appname = prefs.getString("fingeredit_appname", ""); // 나중에 null 인거 처리해줘야댐!

        Log.d("Fingerprint 수정 전 받아온 이름>>", fingeredit_fingername);
        Log.d("Fingerprint 수정 전 받아온 어플>>", fingeredit_appname);

        // fingername불러와서
        ApplicationInfo info = null;
        pack_name = fingeredit_appname;
        fingeredit_editText.setText(fingeredit_fingername);
        if ((!fingeredit_appname.equals("")) && (fingeredit_appname != null)) {
            pm = this.getPackageManager();
            try {
                info = pm.getApplicationInfo(fingeredit_appname.toLowerCase(), PackageManager.GET_META_DATA);
                if (info != null) {
                    fingeredit_selectButton.setText(pm.getApplicationLabel(info));
                } else if(info == null) {
                    fingeredit_selectButton.setText("여기에 어플을 등록합니다");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.d(fingeredit_appname, "는 설치되지 않은 패키지 입니다.");
                fingeredit_selectButton.setText("여기에 어플을 등록합니다");
            }
        }

        // TODO: 초기화 버튼으로 만들기 ( 지문 이름, 실행할 어플 )
        // 실행할 어플이 없으므로 일반적인 잠금 해제와 같음!
//        fingeredit_deleteButton.setOnClickListener(new View.OnClickListener() {
//                SharedPreferences prefs = getSharedPreferences("fingerdelete", MODE_PRIVATE);
//                SharedPreferences.Editor prefsEditor = prefs.edit();
//                @Override
//                public void onClick(View v) {
//                    String fingerdelete_fingername = fingeredit_editText.getText().toString();
//                    String fingerdelete_appname = fingeredit_selectButton.getText().toString();
//
//                    Log.d("지문삭제 fingername>>",fingerdelete_fingername);
//                    Log.d("지문삭제 appname>>",fingerdelete_fingername);
//
//                    prefsEditor.putString("fingerdelete_fingername",fingerdelete_fingername);
//                    prefsEditor.putString("fingerdelete_appname",fingerdelete_appname);
//                    prefsEditor.commit();
//
//                    finish();
//            }
//        });

        fingeredit_selectButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
            //            SharedPreferences prefs2 = getSharedPreferences("PakageName",MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();
//            SharedPreferences.Editor prefsEditor2 = prefs2.edit();

            public void onClick(View v) {
                prefsEditor.putBoolean("select1", true);
//                prefsEditor2.putString("PackageName",pack_name);
//                prefsEditor2.commit();
                prefsEditor.commit();
                Intent intent = new Intent(FingerprintEditActivity.this, ApplicationSelectionActivity.class);
                startActivity(intent);
            }
        });

        fingeredit_completeButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();

            @Override
            public void onClick(View v) {
                Log.d(TAG, "수정하고 EDIT 종료합니다.");
                String fingeredit_fingername2 = fingeredit_editText.getText().toString();
                String fingeredit_appname2 = pack_name;

                Log.d("수정할 FNAME>>", fingeredit_fingername2);
                Log.d("수정할 ANAME>>", fingeredit_appname2);

                prefsEditor.putString("fingeredit_fingername", fingeredit_fingername2);
                prefsEditor.putString("fingeredit_appname", fingeredit_appname2);
                prefsEditor.putBoolean("complete_edit", true);
                prefsEditor.commit();
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences prefs1 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences prefs2 = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs2.edit();

        pack_name = prefs1.getString("name1", "");

        ApplicationInfo info = null;

        if ((!pack_name.equals("")) && (pack_name != null)) {
            pm = getPackageManager();
            try {
                info = pm.getApplicationInfo(pack_name.toLowerCase(), PackageManager.GET_META_DATA);

                if (info != null) {
                    fingeredit_selectButton.setText(pm.getApplicationLabel(info));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.d(pack_name, "는 설치되지 않은 패키지 입니다.");
                fingeredit_selectButton.setText("여기에 어플을 등록합니다");
            }
        }
        // pref 초기화
        SharedPreferences.Editor prefsEditor1 = prefs1.edit();
        prefsEditor1.putString("name1", "");
        prefsEditor1.commit();
    }
//
//    public void setLaunchAname(String package_name){
//        try {
//            if (!package_name.equals("")) { // 실행 될 앱 이름이 있다면
//                Log.d("setApppIcon(): package name is",pack_name);
//
//                Drawable AppIcon = getPackageManager().getApplicationInfo(package_name.toString());
//
//                fingeredit_image.setImageDrawable(AppIcon);
//                //fingeredit_image.invalidate();
//
//            } else {
//                Log.d("setApppIcon(): package name is null","");
//                fingeredit_selectButton.setText("여기에 어플을 등록합니다");
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("setApppIcon():","Package:"+pack_name+"is Not Found!");
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean("complete_edit", false);
        prefsEditor.commit();

        Log.d(TAG, "EDIT하지 않고 종료합니다.");
        FingerprintEditActivity.this.finish();
    }
}
