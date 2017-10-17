package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FingerReadActivity extends AppCompatActivity {

    private static final String TAG = FingerReadActivity.class.getSimpleName();


    private KeyPair keyPair;
    private ReaderTask readerTask;
    private static final String KEY_PUBLIC = "publicKey";
    private static final String KEY_PRIVATE = "privateKey";

    String timeLimit = "";
    String inputTime = "";
    String inputMin[] = new String[]{"", "", ""};
    String LogStr = "";
    String fID = "";

    ProgressBar circle_bar;

    private ArrayList<FingerprintListviewItem> fingerprintList;

    private boolean stopRead = false;

    private static class StatusUpdate {
        private int statusMessage;
        private List<String> lines;

        public StatusUpdate(int statusMessage, List<String> lines) {
            this.statusMessage = statusMessage;
            this.lines = lines;
        }

        public int getStatusMessage() {
            return statusMessage;
        }

        public List<String> getLines() {
            return lines;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_read);

        stopRead = false;

        fingerprintList = new ArrayList<FingerprintListviewItem>();

        Intent intent = getIntent();
        inputTime = intent.getStringExtra("time");
        String tmp[] = inputTime.split(":");

        String plusOne[] = new String[]{"","",""};
        plusOne[0] = tmp[0];
        plusOne[1] = tmp[1];
        plusOne[2] = tmp[2];

        int sec_plus_one = Integer.parseInt(tmp[2])+1;

        if(sec_plus_one == 60){
            plusOne[2] = "00";

            int min_plus_one = Integer.parseInt(tmp[1])+1;

            if(min_plus_one == 60){
                plusOne[1] = "00";

                int hour_plus_one = Integer.parseInt(tmp[0])+1;

                if(hour_plus_one == 25){
                    plusOne[0] = "00";
                } else {
                    plusOne[0] = String.valueOf(hour_plus_one);
                }

            } else if (min_plus_one < 10){
                plusOne[1] = "0"+ String.valueOf(min_plus_one);
            } else {
                plusOne[1] = String.valueOf(min_plus_one);
            }

        } else if(sec_plus_one < 10){
            plusOne[2] = "0"+ String.valueOf(sec_plus_one);
        } else {
            plusOne[2] = String.valueOf(sec_plus_one);
        }

//        inputMin[0] =
        timeLimit = plusOne[0]+":"+plusOne[1]+":"+plusOne[2];
        Log.d("99999999 >>",timeLimit);

//        String min[] = new String[]{"", ""};
//        int num1 = Integer.parseInt(tmp[1]) - 1;
//        if (num1 < 10)
//            min[0] = "0" + String.valueOf(num1);
//        else
//            min[0] = String.valueOf(num1);
//
//        int num2 = Integer.parseInt(tmp[1]) + 1;
//        if (num2 < 10)
//            min[1] = "0" + String.valueOf(num2);
//        else
//            min[1] = String.valueOf(num2);


//        inputMin[0] = tmp[0] + ":" + min[0] + ":";
//        inputMin[1] = tmp[0] + ":" + tmp[1] + ":";
//        inputMin[2] = tmp[0] + ":" + min[1] + ":";

        circle_bar = (ProgressBar) findViewById(R.id.progressBar);
        circle_bar.setVisibility(View.VISIBLE);

        try {
            keyPair = getKeyPair(); // crashes on non-main thread
        } catch (GeneralSecurityException | IOException e) {
            Log.w(TAG, e);
        }

        restartReader();


    }

    private void stopReader() {
        // adapter.clear();
        if (readerTask != null) {
            readerTask.cancel(true);
            readerTask = null;
        }
    }

    private void restartReader() {
        Log.d(TAG, "aaaaaaaa");
        stopReader();
        readerTask = new ReaderTask();
        readerTask.execute();
    }

    protected void onDestroy() {
        super.onDestroy();
        stopReader();
    }

    private KeyPair getKeyPair() throws GeneralSecurityException, IOException {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        KeyPair keyPair;

        if (preferences.contains(KEY_PUBLIC) && preferences.contains(KEY_PRIVATE)) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(
                    Base64.decode(preferences.getString(KEY_PUBLIC, null), Base64.DEFAULT)));
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
                    Base64.decode(preferences.getString(KEY_PRIVATE, null), Base64.DEFAULT)));

            keyPair = new KeyPair(publicKey, privateKey);
        } else {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();

            preferences
                    .edit()
                    .putString(KEY_PUBLIC, Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT))
                    .putString(KEY_PRIVATE, Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT))
                    .apply();
        }

        return keyPair;
    }

    public void setFid() {
        Log.d(TAG, "$$$$$$$setFid");
        String aaaa[] = LogStr.split("=");
        String bbbb[] = aaaa[1].split(",");
        fID = bbbb[0];

        SharedPreferences pref1 = getSharedPreferences("fingerprint1", MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences("fingerprint2", MODE_PRIVATE);

        // 현재 저장된 List를 비운다
        fingerprintList.clear();

        Map<String, ?> allEntries1 = pref1.getAll();
        Map<String, ?> allEntries2 = pref2.getAll();

        for (Map.Entry<String, ?> entry1 : allEntries1.entrySet()) {
            for (Map.Entry<String, ?> entry2 : allEntries2.entrySet()) {
                if (entry1.getKey().equals(entry2.getKey())) {
                    Log.d("Preferences에 저장된 List Finger:>>", entry1.getKey() + ", Fname: " + entry1.getValue().toString() + ",Aname: " + entry2.getValue().toString());
                    fingerprintList.add(new FingerprintListviewItem(entry1.getKey(), entry1.getValue().toString(), entry2.getValue().toString()));
                }
            }
        }

        //fingerprintList.clear();

        String lauch_app_name = "";

        if (fingerprintList.size() != 0) {
            for (int i = 0; i < fingerprintList.size(); i++) {
                // pref에서 받아온 fingerList에서, 시스템에서 받아온 fingerID와 같은 ID를 가진 fingerprint는
                // showList에 추가한다.
                if (fID.equals(fingerprintList.get(i).getFingerprint_id())) {
                    Log.d("fid:" + fID, "fingerprintList id" + fingerprintList.get(i).getFingerprint_id());
                    lauch_app_name = fingerprintList.get(i).getApp_name();
                }
            }
        }

        PackageManager packageManager = this.getPackageManager();
        if (!lauch_app_name.equals("")) {
            Log.d("package실행", lauch_app_name);
            Intent intent = packageManager.getLaunchIntentForPackage(lauch_app_name);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Log.d("없음녀", "그냥잠금해제");
            finish();
        }
        //앱실행
//        Intent intent = new Intent(this, LogActivity.class);
//        intent.putExtra("fID", fID);
//        this.startActivity(intent);
//        finish();
    }


    private class ReaderTask extends AsyncTask<Void, StatusUpdate, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Log.d(TAG, "###############");

            final Reader reader = new RemoteReader(keyPair);
            //Reader reader = new LocalReader();


            reader.read(new Reader.UpdateHandler() {
                @Override
                public boolean isCancelled() {
                    return ReaderTask.this.isCancelled();
                }

                @Override
                public void update(int status, List<String> lines) {
                    if (!stopRead)
                        publishProgress(new StatusUpdate(status, lines));
                    else {
                        Log.d(TAG, "3333333333");
                        reader.onPostExecute(true);
                    }

                }
            });


            return null;
        }

        protected void onProgressUpdate(StatusUpdate... items) {
            //StatusUpdate for문
            for (StatusUpdate statusUpdate : items) {
                //stopRead if문
                if (!stopRead) {
                    //getLine null 확인문
                    if (statusUpdate.getLines() != null) {
                        for (String str : statusUpdate.getLines()) {

                            if(str.trim().contains(timeLimit)){
                                Log.d(TAG,"4444444444444444444444444");
                                stopRead = true;
                                break;
                            }
                            else{
                                if (str.trim().contains("onAuthenticated(fid=")) {
                                    Log.d(TAG, "@@@@@@@@@@@@@@");
                                    LogStr = str.trim();
                                }
                            }
//                            if (str.trim().contains(inputMin[0])) {
//                                if (str.trim().contains("onAuthenticated(fid=")) {
//                                    Log.d(TAG, "@@@@@@@@@@@@@@");
//                                    LogStr = str.trim();
//                                }
//                            } else if (str.trim().contains(inputMin[1])) {
//                                if (str.trim().contains("onAuthenticated(fid=")) {
//                                    Log.d(TAG, "@@@@@@@@@@@@@@");
//                                    LogStr = str.trim();
//                                    stopRead = true;
//                                    break;
//                                }
//                                } else if (str.trim().contains(inputTime)){
//                                }
//                            } else if (str.trim().contains(inputTime))
                        }
                    }//getLine null 확인문
                }//stopRead if문
                else
                    break;

            } //StatusUpdate for문
        }//onProgressUpdate

        protected void onCancelled() {
            // TODO 작업이 취소된후에 호출된다.
            super.onCancelled();
            Log.d(TAG, "111111111111");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            circle_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            circle_bar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "$$$$onPostExecute");
            setFid();
            stopReader();
        }

    }
}
