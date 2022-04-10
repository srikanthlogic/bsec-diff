package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class BoothOfficerDeviceStatusUpdatePostResponse {
    @SerializedName("updated")
    private Boolean Updated;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return this.message;
    }

    public Boolean isUpdated() {
        return this.Updated;
    }
}
