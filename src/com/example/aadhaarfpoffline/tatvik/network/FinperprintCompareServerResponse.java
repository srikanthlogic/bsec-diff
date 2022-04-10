package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class FinperprintCompareServerResponse {
    @SerializedName("Found")
    private Boolean found;
    @SerializedName("matchedvoterid")
    private String matchedvoterid;
    @SerializedName("message")
    private String message;

    public Boolean isFound() {
        return this.found;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMatchedvoterid() {
        return this.matchedvoterid;
    }
}
