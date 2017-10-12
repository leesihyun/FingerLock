package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FingerprintEditActivity extends AppCompatActivity {
    String TAG = FingerprintEditActivity.this.toString();
    ImageView fingeredit_image;
    Button fingeredit_selectButton;
    EditText fingeredit_editText;
    Button fingeredit_completeButton;
    Button fingeredit_deleteButton;

    public String pack_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_edit);

        fingeredit_image = (ImageView) findViewById(R.id.fingeredit_image);
        fingeredit_selectButton = (Button) findViewById(R.id.fingeredit_selectButton);
        fingeredit_editText = (EditText) findViewById(R.id.fingeredit_editText);
        fingeredit_deleteButton = (Button) findViewById(R.id.fingeredit_deleteButton);
        fingeredit_completeButton = (Button) findViewById(R.id.fingeredit_completeButton);


        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);

        String fingeredit_fingername = prefs.getString("fingeredit_fingername", "");
        String fingeredit_appname = prefs.getString("fingeredit_appname",""); // 나중에 null 인거 처리해줘야댐!

        Log.d("Fingerprint 수정 전 받아온 이름>>", fingeredit_fingername);
        Log.d("Fingerprint 수정 전 받아온 어플>>", fingeredit_appname);

        // fingername불러와서
        fingeredit_editText.setText(fingeredit_fingername);
        // appname불러와서
        pack_name = fingeredit_appname;
        setAppIcon();

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
                String fingeredit_fingername2 = fingeredit_editText.getText().toString();
                String fingeredit_appname2 = pack_name;

                Log.d("지문수정 fingername>>",fingeredit_fingername2);
                Log.d("지문수정 appname>>",fingeredit_appname2);

                prefsEditor.putString("fingeredit_fingername",fingeredit_fingername2);
                prefsEditor.putString("fingeredit_appname",fingeredit_appname2);
                prefsEditor.putBoolean("edit",true);
                prefsEditor.commit();
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences prefs1 = getSharedPreferences("PakageName",MODE_PRIVATE);
        SharedPreferences prefs2 = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs2.edit();

            pack_name = prefs1.getString("name1", "");
            setAppIcon();
            // pref 초기화
            SharedPreferences.Editor prefsEditor1 = prefs1.edit();
            prefsEditor1.putString("name1", "");
            prefsEditor1.commit();
    }

    public void setAppIcon(){
        try {
            if (!pack_name.equals("")) { // 실행 될 앱 이름이 있다면
                Log.d("setApppIcon(): package name is",pack_name);

                Drawable AppIcon = getPackageManager().getApplicationIcon(pack_name);

                fingeredit_image.setImageDrawable(AppIcon);
                //fingeredit_image.invalidate();

            } else {
                Log.d("setApppIcon(): package name is null","");
                fingeredit_image.setImageDrawable(null);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("setApppIcon():","Package:"+pack_name+"is Not Found!");
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean("edit",false);
        prefsEditor.commit();

        Log.d("FingerprintEditActivity>>","지문 수정하지 않고 종료");
        FingerprintEditActivity.this.finish();
    }
}
