package com.example.aadhaarfpoffline.tatvik.model;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class ReportDataModel {
    @SerializedName("client_id")
    private String clientId;
    @SerializedName("reports")
    private Reports reports;

    public String getClientId() {
        return this.clientId;
    }

    public Reports getReports() {
        return this.reports;
    }
}
