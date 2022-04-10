package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.ReportDataModel;
import com.facebook.common.util.UriUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class PostUploadResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("message_code")
    private String message_code;
    @SerializedName(UriUtil.DATA_SCHEME)
    private ReportDataModel reportDataModel;
    @SerializedName("status_code")
    private int status_code;
    @SerializedName(FirebaseAnalytics.Param.SUCCESS)
    private Boolean success;

    public int getStatus_code() {
        return this.status_code;
    }

    public String getMessage_code() {
        return this.message_code;
    }

    public String getMessage() {
        return this.message;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public ReportDataModel getReportDataModel() {
        return this.reportDataModel;
    }
}
