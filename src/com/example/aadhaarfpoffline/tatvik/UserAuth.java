package com.example.aadhaarfpoffline.tatvik;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.firebase.analytics.FirebaseAnalytics;
/* loaded from: classes2.dex */
public class UserAuth {
    private Context mcontext;
    private String MYPREF = "userdata";
    private String BASE_URL = "baseurl";
    private String TRANSACTION_ID = "transactionid";
    private String FINGERPRINTDEVICE = "fingerprintdevice";

    public UserAuth(Context context) {
        this.mcontext = context;
    }

    public Boolean ifLogin() {
        return Boolean.valueOf(this.mcontext.getSharedPreferences(this.MYPREF, 0).getBoolean("islogin", false));
    }

    public void setLogin(Boolean yn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putBoolean("islogin", yn.booleanValue());
        editor.commit();
    }

    public void setBoothId(String boothid) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("boothid", boothid);
        editor.commit();
    }

    public String getBoothId() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("boothid", "");
    }

    public void setPhone(String phone) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("phone", phone);
        editor.commit();
    }

    public String getPhone() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("phone", "");
    }

    public void setBoothLocation(String location) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString(FirebaseAnalytics.Param.LOCATION, location);
        editor.commit();
    }

    public String getBoothLocation() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString(FirebaseAnalytics.Param.LOCATION, "");
    }

    public void setPanchayatId(String panchayatid) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("panchayatid", panchayatid);
        editor.commit();
    }

    public void setWardNo(String wardNo) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("wardno", wardNo);
        editor.commit();
    }

    public String getPanchayatId() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("panchayatid", "");
    }

    public String getWardNo() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("wardno", "");
    }

    public void setOnlineOffline(String mode) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("onlineoffinemode", mode);
        editor.commit();
    }

    public String getOnlineOfflineMode() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("onlineoffinemode", "");
    }

    public Boolean ifLocked() {
        return Boolean.valueOf(this.mcontext.getSharedPreferences(this.MYPREF, 0).getBoolean("islocked", true));
    }

    public void setLock(Boolean yn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putBoolean("islocked", yn.booleanValue());
        editor.commit();
    }

    public Boolean ifLockedVisible() {
        return Boolean.valueOf(this.mcontext.getSharedPreferences(this.MYPREF, 0).getBoolean("islockvisible", true));
    }

    public void setLockVisible(Boolean yn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putBoolean("islockvisible", yn.booleanValue());
        editor.commit();
    }

    public void setBaseUrl(String baseurl) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString(this.BASE_URL, baseurl);
        editor.commit();
    }

    public String getBaseUrl() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString(this.BASE_URL, "http://cim.phoneme.in/");
    }

    public void setBoothNo(String boothno) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("boothno", boothno);
        editor.commit();
    }

    public String getBoothNo() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("boothno", "");
    }

    public void setDistrictNo(String boothno) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("distno", boothno);
        editor.commit();
    }

    public String getDistrictNo() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("distno", "");
    }

    public void setBlockID(String boothno) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("blockid", boothno);
        editor.commit();
    }

    public String getBlockID() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("blockid", "");
    }

    public void setTransactionId(Long boothno) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putLong(this.TRANSACTION_ID, boothno.longValue());
        editor.commit();
    }

    public Long getTransactionId() {
        return Long.valueOf(this.mcontext.getSharedPreferences(this.MYPREF, 0).getLong(this.TRANSACTION_ID, 0));
    }

    public void setFingerPrintDevice(String fingerPrintDevice) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString(this.FINGERPRINTDEVICE, fingerPrintDevice);
        editor.apply();
    }

    public String getFingerPrintDevice() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString(this.FINGERPRINTDEVICE, "");
    }

    public void setPanchayat_NAME_EN(String panchayat_name_en) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("panchayat_name_en", panchayat_name_en);
        editor.apply();
    }

    public String getPanchayat_NAME_EN() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("panchayat_name_en", "");
    }

    public void setPanchayat_NAME_HN(String panchayat_name_hn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("panchayat_name_hn", panchayat_name_hn);
        editor.apply();
    }

    public String getPanchayat_NAME_HN() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("panchayat_name_hn", "");
    }

    public void setBlock_NAME_EN(String panchayat_name_hn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("block_name_en", panchayat_name_hn);
        editor.apply();
    }

    public String getBlock_NAME_EN() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("block_name_en", "");
    }

    public void setBlock_NAME_HN(String panchayat_name_hn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("block_name_hn", panchayat_name_hn);
        editor.apply();
    }

    public String getBlock_NAME_HN() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("block_name_hn", "");
    }

    public void setDIST_NAME_EN(String panchayat_name_hn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("dist_name_en", panchayat_name_hn);
        editor.apply();
    }

    public String getDIST_NAME_EN() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("dist_name_en", "");
    }

    public void setDIST_NAME_HN(String panchayat_name_hn) {
        SharedPreferences.Editor editor = this.mcontext.getSharedPreferences(this.MYPREF, 0).edit();
        editor.putString("dist_name_hn", panchayat_name_hn);
        editor.apply();
    }

    public String getDIST_NAME_HN() {
        return this.mcontext.getSharedPreferences(this.MYPREF, 0).getString("dist_name_hn", "");
    }
}
