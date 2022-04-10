package com.example.aadhaarfpoffline.tatvik.network;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class FaceImageUploadCheckResponse {
    @SerializedName("conf")
    private Double conf;
    @SerializedName("Found")
    private Boolean found;
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    public Boolean isFound() {
        return this.found;
    }

    public String getName() {
        return this.name;
    }

    public Double getConf() {
        return this.conf;
    }
}
