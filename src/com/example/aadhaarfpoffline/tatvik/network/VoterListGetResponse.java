package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataModel;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class VoterListGetResponse {
    @SerializedName("voterlist")
    private List<VoterDataModel> voters;

    public List<VoterDataModel> getVoters() {
        return this.voters;
    }
}
