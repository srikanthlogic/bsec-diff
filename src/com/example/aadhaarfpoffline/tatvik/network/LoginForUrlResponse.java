package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.ReportDataModel;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class LoginForUrlResponse {
    @SerializedName("BlockID")
    private String blockId;
    @SerializedName("BoothNo")
    private String boothNo;
    @SerializedName("boothid")
    private String boothid;
    @SerializedName("dblocation")
    private String dblocation;
    @SerializedName("DIST_NO")
    private String distNo;
    @SerializedName("loginallowed")
    private Boolean loginallowed;
    @SerializedName("message")
    private String message;
    @SerializedName("message_code")
    private String message_code;
    @SerializedName("PanchayatID")
    private String panchayatid;
    @SerializedName(UriUtil.DATA_SCHEME)
    private ReportDataModel reportDataModel;
    @SerializedName("status_code")
    private int status_code;
    @SerializedName(FirebaseAnalytics.Param.SUCCESS)
    private Boolean success;
    @SerializedName(ImagesContract.URL)
    private String url;
    @SerializedName("WardNo")
    private String wardno;

    public Boolean isLoginAllowed() {
        return this.loginallowed;
    }

    public String getBoothid() {
        return this.boothid;
    }

    public String getDistNo() {
        return this.distNo;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public String getDblocation() {
        return this.dblocation;
    }

    public String getPanchayatid() {
        return this.panchayatid;
    }

    public String getWardno() {
        return this.wardno;
    }

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

    public String getBoothNo() {
        return this.boothNo;
    }

    public String getUrl() {
        return this.url;
    }
}
