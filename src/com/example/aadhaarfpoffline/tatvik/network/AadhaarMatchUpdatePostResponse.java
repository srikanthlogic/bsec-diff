package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class AadhaarMatchUpdatePostResponse {
    @SerializedName("added")
    private Boolean added;
    @SerializedName("message")
    private String message;

    public Boolean isAdded() {
        return this.added;
    }

    public String getMessage() {
        return this.message;
    }
}
