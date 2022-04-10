package com.example.aadhaarfpoffline.tatvik.model;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class CertificateDetails {
    @SerializedName("aaadhar_last_four_digits")
    private String aaadhar_last_four_digits;
    @SerializedName("country")
    private String country;
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;
    @SerializedName("pin_code")
    private String pincode;
    @SerializedName("state")
    private String state;

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getState() {
        return this.state;
    }

    public String getPincode() {
        return this.pincode;
    }

    public String getAaadhar_last_four_digits() {
        return this.aaadhar_last_four_digits;
    }
}
