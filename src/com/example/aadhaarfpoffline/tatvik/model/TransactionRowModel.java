package com.example.aadhaarfpoffline.tatvik.model;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class TransactionRowModel {
    @SerializedName("message")
    private String message;
    @SerializedName("updated")
    private boolean updated;

    public String getMessage() {
        return this.message;
    }

    public boolean getUpdated() {
        return this.updated;
    }
}
