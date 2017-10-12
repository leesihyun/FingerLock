package com.seahyun.fingerlock;

/**
 * Created by DAHEE on 2017-09-30.
 */

public class FingerprintListviewItem {

    public String getLaunchapp_name() {
        return launchapp_name;
    }

    public void setLaunchapp_name(String launchapp_name) {
        this.launchapp_name = launchapp_name;
    }

    private String fingerprint_id;
    private String fingerprint_name;
    private String launchapp_name;


    public FingerprintListviewItem(String fingerprint_id,String fingerprint_name, String launchapp_name) {
        this.fingerprint_id = fingerprint_id;
        this.fingerprint_name = fingerprint_name;
        this.launchapp_name = launchapp_name;
    }

    public FingerprintListviewItem(String fingerprint_id, String fingerprint_name) {
        this.fingerprint_id = fingerprint_id;
        this.fingerprint_name = fingerprint_name + " "+fingerprint_id;
        this.launchapp_name = "";
    }


    public FingerprintListviewItem() {
    }

    public String getFingerprint_name() {
        return fingerprint_name;
    }

    public void setFingerprint_name(String fingerprint_name) {
        this.fingerprint_name = fingerprint_name;
    }

    public String getApp_name() {
        return launchapp_name;
    }

    public void setApp_name(String app_name) {
        this.launchapp_name = app_name;
    }

    public String getFingerprint_id() { return fingerprint_id;}

    public void setFingerprint_id(String fingerprint_id){
        this.fingerprint_id = fingerprint_id;
    }
}
