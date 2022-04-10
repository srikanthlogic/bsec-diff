package com.example.aadhaarfpoffline.tatvik.model;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class UploadFieldDataModel {
    @SerializedName("key")
    private String key;
    @SerializedName("policy")
    private String policy;
    @SerializedName("x-amz-algorithm")
    private String x_amz_algorithm;
    @SerializedName("x-amz-credential")
    private String x_amz_credential;
    @SerializedName("x-amz-date")
    private String x_amz_date;
    @SerializedName("x-amz-signature")
    private String x_amz_signature;

    public String getKey() {
        return this.key;
    }

    public String getX_amz_algorithm() {
        return this.x_amz_algorithm;
    }

    public String getX_amz_credential() {
        return this.x_amz_credential;
    }

    public String getX_amz_date() {
        return this.x_amz_date;
    }

    public String getPolicy() {
        return this.policy;
    }

    public String getX_amz_signature() {
        return this.x_amz_signature;
    }
}
