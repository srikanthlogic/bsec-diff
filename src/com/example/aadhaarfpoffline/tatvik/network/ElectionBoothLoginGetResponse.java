package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class ElectionBoothLoginGetResponse {
    @SerializedName("boothid")
    private String boothid;
    @SerializedName("dblocation")
    private String dblocation;
    @SerializedName("loginallowed")
    private Boolean loginallowed;
    @SerializedName("message")
    private String message;
    @SerializedName("panchayatid")
    private String panchayatid;
    @SerializedName("wardno")
    private String wardno;

    public Boolean isLoginAllowed() {
        return this.loginallowed;
    }

    public String getDblocation() {
        return this.dblocation;
    }

    public String getMessage() {
        return this.message;
    }

    public String getBoothid() {
        return this.boothid;
    }

    public String getPanchayatid() {
        return this.panchayatid;
    }

    public String getWardno() {
        return this.wardno;
    }
}
