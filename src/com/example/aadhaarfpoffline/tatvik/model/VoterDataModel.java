package com.example.aadhaarfpoffline.tatvik.model;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class VoterDataModel {
    @SerializedName("create_at")
    private String CreatedAt;
    @SerializedName("district")
    private String District;
    @SerializedName("GSTIN")
    private String GSTIN;
    @SerializedName("id")
    private String Id;
    @SerializedName("PAM")
    private String PAM;
    @SerializedName("voter_id")
    private String VoterId;
    @SerializedName("voter_name")
    private String Voter_name;
    @SerializedName("age")
    private int age;
    @SerializedName("block_no")
    private String blockNo;
    @SerializedName("booth_id")
    private String booth_id;
    @SerializedName("gender")
    private String gender;
    @SerializedName("voted")
    private Integer voted;

    public int getAge() {
        return this.age;
    }

    public int getVoted() {
        Integer num = this.voted;
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPAM() {
        return this.PAM;
    }

    public String getGSTIN() {
        return this.GSTIN;
    }

    public String getBooth_id() {
        return this.booth_id;
    }

    public String getDistrict() {
        return this.District;
    }

    public String getBlockNo() {
        return this.blockNo;
    }

    public String getCreatedAt() {
        return this.CreatedAt;
    }

    public String getVoter_name() {
        return this.Voter_name;
    }

    public String getId() {
        return this.Id;
    }

    public String getVoterId() {
        return this.VoterId;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public void setVoter_name(String name) {
        this.Voter_name = name;
    }

    public void setCreatedAt(String createdAt) {
        this.CreatedAt = createdAt;
    }

    public void setVoterId(String name) {
        this.VoterId = name;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public void setDistrict(String district) {
        this.District = district;
    }

    public void setBooth_id(String booth_id) {
        this.booth_id = booth_id;
    }

    public void setGSTIN(String gstin) {
        this.GSTIN = gstin;
    }

    public void setPAM(String pam) {
        this.PAM = pam;
    }
}
