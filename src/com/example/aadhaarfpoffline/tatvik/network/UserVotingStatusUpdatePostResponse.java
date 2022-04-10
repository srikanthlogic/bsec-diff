package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class UserVotingStatusUpdatePostResponse {
    @SerializedName("added")
    private Boolean Added;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return this.message;
    }

    public Boolean isAdded() {
        return this.Added;
    }
}
