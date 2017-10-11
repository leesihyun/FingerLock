package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FingerprintEditActivity extends AppCompatActivity {
    String TAG = FingerprintEditActivity.this.toString();
    Button fingeredit_selectButton;
    EditText fingeredit_editText;
    Button fingeredit_completeButton;
    Button fingeredit_deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_edit);

        fingeredit_selectButton = (Button) findViewById(R.id.fingeredit_selectButton);
        fingeredit_editText = (EditText) findViewById(R.id.fingeredit_editText);
        fingeredit_deleteButton = (Button) findViewById(R.id.fingeredit_deleteButton);
        fingeredit_completeButton = (Button) findViewById(R.id.fingeredit_completeButton);


        SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
        String fingeredit_fingername = prefs.getString("fingeredit_fingername", "이름 없는 지문");
        String fingeredit_appname = prefs.getString("fingeredit_appname","실행 앱 설정안된 지문"); // 나중에 null 인거 처리해줘야댐!

        // fingername불러와서
        fingeredit_editText.setText(fingeredit_fingername);
        // appname불러와서
        fingeredit_selectButton.setText(fingeredit_appname);

        fingeredit_deleteButton.setOnClickListener(new View.OnClickListener() {
                SharedPreferences prefs = getSharedPreferences("fingerdelete", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                @Override
                public void onClick(View v) {
                    String fingerdelete_fingername = fingeredit_editText.getText().toString();
                    String fingerdelete_appname = fingeredit_selectButton.getText().toString();

                    Log.d("지문삭제 fingername>>",fingerdelete_fingername);
                    Log.d("지문삭제 appname>>",fingerdelete_fingername);

                    prefsEditor.putString("fingerdelete_fingername",fingerdelete_fingername);
                    prefsEditor.putString("fingerdelete_appname",fingerdelete_appname);
                    prefsEditor.commit();

                    finish();
            }
        });

        fingeredit_completeButton.setOnClickListener(new View.OnClickListener() {
                SharedPreferences prefs = getSharedPreferences("fingeredit", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                @Override
                public void onClick(View v) {
                    String fingeredit_fingername = fingeredit_editText.getText().toString();
                    String fingeredit_appname = fingeredit_selectButton.getText().toString();

                    Log.d("지문수정 fingername>>",fingeredit_fingername);
                    Log.d("지문수정 appname>>",fingeredit_appname);

                    prefsEditor.putString("fingeredit_fingername",fingeredit_fingername);
                    prefsEditor.putString("fingeredit_appname",fingeredit_appname);
                    prefsEditor.commit();

                    finish();
            }
        });

        fingeredit_selectButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();

            public void onClick(View v) {
                prefsEditor.putBoolean("select1", true);
                prefsEditor.commit();
                Intent intent = new Intent(FingerprintEditActivity.this, ApplicationSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("Message", MODE_PRIVATE);
        fingeredit_selectButton.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
    }
}
