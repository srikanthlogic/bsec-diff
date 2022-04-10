package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/* loaded from: classes2.dex */
public class VoterListNewTableGetResponse {
    @SerializedName("voterlist")
    private List<VoterDataNewModel> voters;

    public List<VoterDataNewModel> getVoters() {
        return this.voters;
    }
}
