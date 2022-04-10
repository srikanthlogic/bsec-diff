package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class VoterDataGetResponse {
    @SerializedName("voter")
    private VoterDataNewModel voter;

    public VoterDataNewModel getVoters() {
        return this.voter;
    }
}
