package com.example.aadhaarfpoffline.tatvik.network;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class MultipleFpUploadResponse {
    @SerializedName("Found")
    private Boolean found;
    @SerializedName("message")
    private String message;
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;
    @SerializedName(FirebaseAnalytics.Param.SCORE)
    private Double score;

    public Boolean isFound() {
        return this.found;
    }

    public String getName() {
        return this.name;
    }

    public Double getConf() {
        return this.score;
    }

    public String getMessage() {
        return this.message;
    }
}
