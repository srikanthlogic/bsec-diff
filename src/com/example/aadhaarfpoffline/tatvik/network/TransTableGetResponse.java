package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.TransTableDataModel;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class TransTableGetResponse {
    @SerializedName("translist")
    private List<TransTableDataModel> listTransTalbe;
    @SerializedName("message")
    private String message;

    public List<TransTableDataModel> getTransTableData() {
        return this.listTransTalbe;
    }

    public String getMessage() {
        return this.message;
    }
}
