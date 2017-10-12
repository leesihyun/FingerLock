package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class FingerprintAddActivity extends AppCompatActivity {
    String TAG = FingerprintAddActivity.this.toString();
    Button fingeradd_selectButton;
    EditText fingeradd_editText;
    Button fingeradd_completeButton;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_add);

        fingeradd_selectButton = (Button) findViewById(R.id.fingeradd_selectButton);
        fingeradd_editText = (EditText) findViewById(R.id.fingeradd_editText);
        fingeradd_completeButton = (Button) findViewById(R.id.fingeradd_completeButton);

        fingeradd_selectButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();

            public void onClick(View v) {
                prefsEditor.putBoolean("select1", true);
                prefsEditor.commit();
                Intent intent = new Intent(FingerprintAddActivity.this, ApplicationSelectionActivity.class);
                startActivity(intent);
            }
        });

        fingeradd_completeButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getSharedPreferences("fingeradd", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            @Override
            public void onClick(View v) {
                String fingeradd_fingername = fingeradd_editText.getText().toString();
                String fingeradd_appname = fingeradd_selectButton.getText().toString();

                Log.d("지문등록 fingername>>",fingeradd_fingername);
                Log.d("지문등록 appname>>",fingeradd_fingername);

                prefsEditor.putString("fingeradd_fingername",fingeradd_fingername);
                prefsEditor.putString("fingeradd_appname",fingeradd_appname);
                prefsEditor.commit();

                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("Message", MODE_PRIVATE);
        fingeradd_selectButton.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
    }
}