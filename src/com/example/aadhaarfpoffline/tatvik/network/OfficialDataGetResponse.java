package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.OfficialDataModel;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class OfficialDataGetResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("officialdata")
    private OfficialDataModel officialDataModel;

    public OfficialDataModel getOfficialDataModel() {
        return this.officialDataModel;
    }
}
