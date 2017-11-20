package com.seahyun.fingerlock;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soyoung on 2017-10-13.
 */

public class FingerprintUiHelper extends FingerprintManager.AuthenticationCallback {
    private static final long ERROR_TIMEOUT_MILLIS = 1600;
    private static final long SUCCESS_DELAY_MILLIS = 1300;

    private static final String TAG = FingerprintUiHelper.class.getSimpleName();

    FingerprintLockScreen fLockActivity = (FingerprintLockScreen)FingerprintLockScreen.FingerLockActivity;
//    MainActivity MActivity = (MainActivity) MainActivity.MainActivity;

    private final FingerprintManager mFingerprintManager;
    private final ImageView mIcon;
    private final TextView mErrorTextView;
    private final Callback mCallback;
    private final Context mContext;
    private CancellationSignal mCancellationSignal;


//    private KeyPair keyPair;
//    private ReaderTask readerTask;
//    private static final String KEY_PUBLIC = "publicKey";
//    private static final String KEY_PRIVATE = "privateKey";
//    private String LogStr = "";
//    private String fID = "";


    FingerprintUiHelper(FingerprintManager fingerprintManager,
                        ImageView icon, TextView errorTextView, Callback callback, Context context) {
        mFingerprintManager = fingerprintManager;
        mIcon = icon;
        mErrorTextView = errorTextView;
        mCallback = callback;
        mContext = context;
    }

    public boolean isFingerprintAuthAvailable() {
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        return mFingerprintManager.isHardwareDetected()
                && mFingerprintManager.hasEnrolledFingerprints();
    }

    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (!isFingerprintAuthAvailable()) {    
            return;
        }
        mCancellationSignal = new CancellationSignal();
//        mSelfCancelled = false;
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        mFingerprintManager
                .authenticate(cryptoObject, mCancellationSignal, 0 /* flags */, this, null);
        mIcon.setImageResource(R.drawable.ic_fp_40px);
    }


    public void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    public void onAuthenticationError(int errMsgId, CharSequence errString) {
//        if (!mSelfCancelled) {
            showError(errString);
            mIcon.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCallback.onError();
                }
            }, ERROR_TIMEOUT_MILLIS);
//        }
    }

    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        showError(helpString);
    }

    public void onAuthenticationFailed() {
        showError(mIcon.getResources().getString(
                R.string.fingerprint_not_recognized));
    }


    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        mIcon.setImageResource(R.drawable.ic_fingerprint_success);
        mErrorTextView.setTextColor(
                mErrorTextView.getResources().getColor(R.color.success_color, null));
        mIcon.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.onAuthenticated();
            }
        }, SUCCESS_DELAY_MILLIS);


        long now = System.currentTimeMillis();

        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
        String formatDate = sdfNow.format(date);
        String abc [] = formatDate.split("-");
        String inputTime = abc[abc.length-1];

        Log.d("입력 inputFinger >>", inputTime);
        fLockActivity.finish();
        Intent intent = new Intent(mContext, FingerReadActivity.class);
        intent.putExtra("time", inputTime);
        mContext.startActivity(intent);

//        MActivity.finish();
//        Intent intent = new Intent(mContext, FingerReadActivity.class);
//        intent.putExtra("time", inputTime);
//        mContext.startActivity(intent);

//        restartReader();
//
//        new Handler().postDelayed(new Runnable(){
//
//            @Override
//            public void run() {
//                preGetFingerID();
//
//                MActivity.finish();
//                Intent intent = new Intent(mContext, LogActivity.class);
//                intent.putExtra("fID", fID);
//                mContext.startActivity(intent);
//            }
//        }, 3000);




    }

    private void showError(CharSequence error) {
        mIcon.setImageResource(R.drawable.ic_fingerprint_error);
        mErrorTextView.setText(error);
        mErrorTextView.setTextColor(
                mErrorTextView.getResources().getColor(R.color.warning_color, null));
        mErrorTextView.removeCallbacks(mResetErrorTextRunnable);
        mErrorTextView.postDelayed(mResetErrorTextRunnable, ERROR_TIMEOUT_MILLIS);
    }


    private Runnable mResetErrorTextRunnable = new Runnable() {
        @Override
        public void run() {
            mErrorTextView.setTextColor(
                    mErrorTextView.getResources().getColor(R.color.hint_color, null));
            mErrorTextView.setText(
                    mErrorTextView.getResources().getString(R.string.fingerprint_hint));
            mIcon.setImageResource(R.drawable.ic_fp_40px);
        }
    };



    public interface Callback {

        void onAuthenticated();

        void onError();
    }


//    public void preGetFingerID(){
//
//        try {
//            keyPair = getKeyPair(); // crashes on non-main thread
//        } catch (GeneralSecurityException | IOException e) {
//            Log.w(TAG, e);
//        }
//
//        getFingerID();
//    }
//
//    public void getFingerID(){
//        //LogStr => ...onAuthenticated(fid=[숫자], gid=[숫자])
//        LogStr = LogStr.trim();
//        Log.d("$$$$$", "LogStr : "+LogStr);
//        //aaaa[0] =>
//        String aaaa[] = LogStr.split("=");
//        for(int i = 0; i < aaaa.length; i++){
//            Log.d("$$$$$", "aaaa["+String.valueOf(i)+"] :"+aaaa[i]);
//        }
//        String bbbb[] = aaaa[1].split(",");
//        for(int i = 0; i < bbbb.length; i++){
//            Log.d("$$$$$", "bbbb["+String.valueOf(i)+"] :"+bbbb[i]);
//        }
//        fID = bbbb[0];
//    }
//
//
//    private void restartReader() {
//        Log.d(TAG, "aaaaaaaa");
//        stopReader();
//        readerTask = new ReaderTask();
//        readerTask.execute();
//    }
//
//    private void stopReader() {
//        // adapter.clear();
//        if (readerTask != null) {
//            readerTask.cancel(true);
//            readerTask = null;
//        }
//    }
//
//    private static class StatusUpdate {
//        private int statusMessage;
//        private List<String> lines;
//
//        public StatusUpdate(int statusMessage, List<String> lines) {
//            this.statusMessage = statusMessage;
//            this.lines = lines;
//        }
//
//        public int getStatusMessage() {
//            return statusMessage;
//        }
//
//        public List<String> getLines() {
//            return lines;
//        }
//    }
//
//    private KeyPair getKeyPair() throws GeneralSecurityException, IOException {
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//
//        KeyPair keyPair;
//
//        if (preferences.contains(KEY_PUBLIC) && preferences.contains(KEY_PRIVATE)) {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(
//                    Base64.decode(preferences.getString(KEY_PUBLIC, null), Base64.DEFAULT)));
//            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
//                    Base64.decode(preferences.getString(KEY_PRIVATE, null), Base64.DEFAULT)));
//
//            keyPair = new KeyPair(publicKey, privateKey);
//        } else {
//            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//            generator.initialize(2048);
//            keyPair = generator.generateKeyPair();
//
//            preferences
//                    .edit()
//                    .putString(KEY_PUBLIC, Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT))
//                    .putString(KEY_PRIVATE, Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT))
//                    .apply();
//        }
//
//        return keyPair;
//    }
//
//    private class ReaderTask extends AsyncTask<Void, StatusUpdate, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            Log.d(TAG,"###############");
//
//            Reader reader = new RemoteReader(keyPair);
//            //Reader reader = new LocalReader();
//
//            reader.read(new Reader.UpdateHandler() {
//                @Override
//                public boolean isCancelled() {
//                    return ReaderTask.this.isCancelled();
//                }
//
//                @Override
//                public void update(int status, List<String> lines) {
//                    publishProgress(new StatusUpdate(status, lines));
//                }
//            });
//
//            return null;
//        }
//
//        protected void onProgressUpdate(StatusUpdate... items) {
//            //Log.d(TAG,"onProgessUpdate 함수");
//            for (StatusUpdate statusUpdate : items) {
//                // Log.d(TAG,"1111111");
//
//                if (statusUpdate.getLines() != null) {
//                    for(String str:statusUpdate.getLines()){
//                        if(str.trim().contains("onAuthenticated(fid=")){
//                            Log.d("@@@@@@@","@@");
//                            LogStr = str;
//                        }
//                    }
//                }
//
//            }
//        }
//    }

}
