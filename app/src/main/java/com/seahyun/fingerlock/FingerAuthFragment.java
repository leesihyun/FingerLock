package com.seahyun.fingerlock;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;


public class FingerAuthFragment extends Fragment implements  FingerprintUiHelper.Callback {

    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintUiHelper mFingerprintUiHelper;
    private FingerprintLockScreen fLockScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        final View v = inflater.inflate(R.layout.fragment_finger_auth, container, false);

//        Log.d("******", "FingerAuthFragment enter");

        fLockScreen = (FingerprintLockScreen) getActivity();

        mFingerprintUiHelper = new FingerprintUiHelper(
                fLockScreen.getSystemService(FingerprintManager.class),
                (ImageView) v.findViewById(R.id.fingerprint_icon),
                (TextView) v.findViewById(R.id.fingerprint_text), this, this.getContext());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFingerprintUiHelper.startListening(mCryptoObject);
    }


    private void goToBackup() {
        mFingerprintUiHelper.stopListening();
    }

    @Override
    public void onAuthenticated() {
        fLockScreen.onPurchased(true /* withFingerprint */, mCryptoObject);
        // dismiss();
    }

    @Override
    public void onError() {

        goToBackup();
    }

    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        mCryptoObject = cryptoObject;
    }
}
