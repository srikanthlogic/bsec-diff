package com.example.aadhaarfpoffline.tatvik.model;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class TransTableDataModel {
    @SerializedName("AADHAAR_MATCH")
    private String AADHAAR_MATCH;
    @SerializedName("AADHAAR_NO")
    private String AADHAAR_NO;
    @SerializedName("AGE")
    private String AGE;
    @SerializedName("FINGERPRINT_TEMPLATE")
    private String FINGERPRINT_TEMPLATE;
    @SerializedName("GENDER")
    private String GENDER;
    @SerializedName("ID_DOCUMENT_IMAGE")
    private String ID_DOCUMENT_IMAGE;
    @SerializedName("TRANSID")
    private String TRANSID;
    @SerializedName("VOTED")
    private String VOTED;
    @SerializedName("VOTING_DATE")
    private String VOTING_DATE;
    @SerializedName("VOTING_TYPE")
    private String VOTING_TYPE;
    @SerializedName("voter_id")
    private String voter_id;

    public String getUser_id() {
        return this.voter_id;
    }

    public String getTRANSID() {
        return this.TRANSID;
    }

    public String getFINGERPRINT_TEMPLATE() {
        return this.FINGERPRINT_TEMPLATE;
    }

    public String getVOTED() {
        return this.VOTED;
    }

    public String getID_DOCUMENT_IMAGE() {
        return this.ID_DOCUMENT_IMAGE;
    }

    public String getVOTING_DATE() {
        return this.VOTING_DATE;
    }

    public String getAADHAAR_MATCH() {
        return this.AADHAAR_MATCH;
    }

    public String getAADHAAR_NO() {
        return this.AADHAAR_NO;
    }

    public String getVOTING_TYPE() {
        return this.VOTING_TYPE;
    }

    public String getGENDER() {
        return this.GENDER;
    }

    public String getAGE() {
        return this.AGE;
    }

    public void setVoter_id(String voter_id) {
        this.voter_id = voter_id;
    }

    public void setTRANSID(String TRANSID) {
        this.TRANSID = TRANSID;
    }

    public void setFINGERPRINT_TEMPLATE(String FINGERPRINT_TEMPLATE) {
        this.FINGERPRINT_TEMPLATE = FINGERPRINT_TEMPLATE;
    }

    public void setVOTED(String VOTED) {
        this.VOTED = VOTED;
    }

    public void setID_DOCUMENT_IMAGE(String ID_DOCUMENT_IMAGE) {
        this.ID_DOCUMENT_IMAGE = ID_DOCUMENT_IMAGE;
    }

    public void setVOTING_DATE(String VOTING_DATE) {
        this.VOTING_DATE = VOTING_DATE;
    }

    public void setAADHAAR_MATCH(String AADHAAR_MATCH) {
        this.AADHAAR_MATCH = AADHAAR_MATCH;
    }

    public void setAADHAAR_NO(String AADHAAR_NO) {
        this.AADHAAR_NO = AADHAAR_NO;
    }

    public void setVOTING_TYPE(String VOTING_TYPE) {
        this.VOTING_TYPE = VOTING_TYPE;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }
}
