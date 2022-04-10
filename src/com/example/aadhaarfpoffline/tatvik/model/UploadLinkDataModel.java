package com.example.aadhaarfpoffline.tatvik.model;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class UploadLinkDataModel {
    @SerializedName("fields")
    private UploadFieldDataModel fields;
    @SerializedName("link_generated")
    private Boolean link_generated;
    @SerializedName(ImagesContract.URL)
    private String url;

    public Boolean getLink_generated() {
        return this.link_generated;
    }

    public String getUrl() {
        return this.url;
    }

    public UploadFieldDataModel getFields() {
        return this.fields;
    }
}
