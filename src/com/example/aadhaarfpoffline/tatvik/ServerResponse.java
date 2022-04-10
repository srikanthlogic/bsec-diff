package com.example.aadhaarfpoffline.tatvik;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class ServerResponse {
    @SerializedName("message")
    String message;
    @SerializedName(FirebaseAnalytics.Param.SUCCESS)
    boolean success;

    String getMessage() {
        return this.message;
    }

    boolean getSuccess() {
        return this.success;
    }
}
