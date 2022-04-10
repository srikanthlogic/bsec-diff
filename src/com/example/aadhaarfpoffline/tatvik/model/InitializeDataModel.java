package com.example.aadhaarfpoffline.tatvik.model;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class InitializeDataModel {
    @SerializedName("client_id")
    private String clientId;
    @SerializedName("token")
    private String token;
    @SerializedName(ImagesContract.URL)
    private String url;

    public String getToken() {
        return this.token;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getUrl() {
        return this.url;
    }
}
