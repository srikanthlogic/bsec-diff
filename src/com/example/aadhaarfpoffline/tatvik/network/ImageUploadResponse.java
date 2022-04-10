package com.example.aadhaarfpoffline.tatvik.network;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class ImageUploadResponse {
    @SerializedName("added")
    private Boolean Added;
    @SerializedName("filename")
    private String filename;
    @SerializedName("message")
    private String message;

    public String getFilename() {
        return this.filename;
    }

    public String getMessage() {
        return this.message;
    }

    public Boolean isAdded() {
        return this.Added;
    }
}
