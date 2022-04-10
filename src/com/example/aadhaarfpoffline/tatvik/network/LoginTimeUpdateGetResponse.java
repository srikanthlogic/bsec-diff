package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class LoginTimeUpdateGetResponse {
    @SerializedName("logintimeupdate")
    private Boolean loginTimeUpdated;
    @SerializedName("message")
    private String message;

    public Boolean getLoginTimeUpdated() {
        return this.loginTimeUpdated;
    }

    public String getMessage() {
        return this.message;
    }
}
