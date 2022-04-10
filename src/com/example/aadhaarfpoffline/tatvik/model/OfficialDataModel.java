package com.example.aadhaarfpoffline.tatvik.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class OfficialDataModel {
    @SerializedName("id")
    private String Id;
    @SerializedName("otp")
    private String Otp;
    @SerializedName("booth_id")
    private String boothId;
    @SerializedName(FirebaseAnalytics.Param.LOCATION)
    private String location;
    @SerializedName("phone")
    private String phone;

    public String getLocation() {
        return this.location;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getBoothId() {
        return this.boothId;
    }
}
