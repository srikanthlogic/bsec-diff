package com.example.aadhaarfpoffline.tatvik.model;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class NameMatch {
    @SerializedName("certificate_details")
    private CertificateDetails certificate_details;
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;
    @SerializedName("name_match_score")
    private String name_match_score;
    @SerializedName("name_matched")
    private Boolean name_matched;
    @SerializedName("should_name_match")
    private Boolean should_name_match;

    public String getName() {
        return this.name;
    }

    public String getName_match_score() {
        return this.name_match_score;
    }

    public Boolean getName_matched() {
        return this.name_matched;
    }

    public Boolean getShould_name_match() {
        return this.should_name_match;
    }

    public CertificateDetails getCertificate_details() {
        return this.certificate_details;
    }
}
