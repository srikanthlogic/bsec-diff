package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class AadhaarUserCheckGetResponse {
    @SerializedName("voter")
    private VoterDataNewModel Voter;
    @SerializedName("aadhaaruserexists")
    private Boolean aadhaaruserexists;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return this.message;
    }

    public Boolean getAadhaaruserexists() {
        return this.aadhaaruserexists;
    }

    public VoterDataNewModel getVoter() {
        return this.Voter;
    }
}
