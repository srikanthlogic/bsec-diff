package com.example.aadhaarfpoffline.tatvik.network;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class VoterByUserIdGetResponse {
    @SerializedName("voter")
    private VoterDataNewModel Voter;
    @SerializedName("matchuserexists")
    private Boolean matchuserexists;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return this.message;
    }

    public Boolean getUserIdExists() {
        return this.matchuserexists;
    }

    public VoterDataNewModel getVoter() {
        return this.Voter;
    }
}
