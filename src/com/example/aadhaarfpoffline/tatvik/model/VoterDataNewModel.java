package com.example.aadhaarfpoffline.tatvik.model;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes2.dex */
public class VoterDataNewModel {
    @SerializedName("AADHAAR_MATCH")
    private String AADHAAR_MATCH;
    @SerializedName("AADHAAR_NO")
    private String AADHAAR_NO;
    @SerializedName("AC_NO")
    private String AC_NO;
    @SerializedName("BlockID")
    private String BlockID;
    @SerializedName("C_HOUSE_NO")
    private String C_HOUSE_NO;
    @SerializedName("C_HOUSE_NO_V1")
    private String C_HOUSE_NO_V1;
    @SerializedName("DIST_NO")
    private String DIST_NO;
    @SerializedName("DOB")
    private String DOB;
    @SerializedName("ELECTOR_TYPE")
    private String ELECTOR_TYPE;
    @SerializedName("EMAIL_ID")
    private String EMAIL_ID;
    @SerializedName("EPIC_NO")
    private String EPIC_NO;
    @SerializedName("FACE_MATCH")
    private String FACE_MATCH;
    @SerializedName("FINGERPRINT_MATCH")
    private String FINGERPRINT_MATCH;
    @SerializedName("FM_NAME_EN")
    private String FM_NAME_EN;
    @SerializedName("FM_NAME_V1")
    private String FM_NAME_V1;
    @SerializedName("GENDER")
    private String GENDER;
    @SerializedName("ID_DOCUMENT_IMAGE")
    private String ID_DOCUMENT_IMAGE;
    @SerializedName("ID")
    private String Id;
    @SerializedName("LASTNAME_EN")
    private String LASTNAME_EN;
    @SerializedName("LASTNAME_V1")
    private String LASTNAME_V1;
    @SerializedName("MOBILE_NO")
    private String MOBILE_NO;
    @SerializedName("PART_NO")
    private String PART_NO;
    @SerializedName("PanchayatID")
    private String PanchayatID;
    @SerializedName("RLN_FM_NM_EN")
    private String RLN_FM_NM_EN;
    @SerializedName("RLN_FM_NM_V1")
    private String RLN_FM_NM_V1;
    @SerializedName("RLN_L_NM_EN")
    private String RLN_L_NM_EN;
    @SerializedName("RLN_L_NM_V1")
    private String RLN_L_NM_V1;
    @SerializedName("RLN_TYPE")
    private String RLN_TYPE;
    @SerializedName("SECTION_NO")
    private String SECTION_NO;
    @SerializedName("SLNOINPART")
    private String SLNOINPART;
    @SerializedName("STATUS_TYPE")
    private String STATUS_TYPE;
    @SerializedName("SlNoInWard")
    private String SlNoInWard;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("VOTED")
    private String VOTED;
    @SerializedName("VOTER_FINGERPRINT")
    private String VOTER_FINGERPRINT;
    @SerializedName("VOTER_IMAGE")
    private String VOTER_IMAGE;
    @SerializedName("VOTING_DATE")
    private String VOTING_DATE;
    @SerializedName("VillageName")
    private String VillageName;
    @SerializedName("WardNo")
    private String WardNo;
    @SerializedName("AGE")
    private String age;

    public String getLASTNAME_V1() {
        return this.LASTNAME_V1;
    }

    public String getAge() {
        return this.age;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getDIST_NO() {
        return this.DIST_NO;
    }

    public String getBlockID() {
        return this.BlockID;
    }

    public String getWardNo() {
        return this.WardNo;
    }

    public String getC_HOUSE_NO_V1() {
        return this.C_HOUSE_NO_V1;
    }

    public String getFM_NAME_EN() {
        return this.FM_NAME_EN;
    }

    public String getLASTNAME_EN() {
        return this.LASTNAME_EN;
    }

    public String getFM_NAME_V1() {
        return this.FM_NAME_V1;
    }

    public String getRLN_TYPE() {
        return this.RLN_TYPE;
    }

    public String getRLN_FM_NM_EN() {
        return this.RLN_FM_NM_EN;
    }

    public String getRLN_L_NM_EN() {
        return this.RLN_L_NM_EN;
    }

    public String getRLN_FM_NM_V1() {
        return this.RLN_FM_NM_V1;
    }

    public String getRLN_L_NM_V1() {
        return this.RLN_L_NM_V1;
    }

    public String getEPIC_NO() {
        return this.EPIC_NO;
    }

    public String getSTATUS_TYPE() {
        return this.STATUS_TYPE;
    }

    public String getGENDER() {
        return this.GENDER;
    }

    public String getDOB() {
        return this.DOB;
    }

    public String getEMAIL_ID() {
        return this.EMAIL_ID;
    }

    public String getMOBILE_NO() {
        return this.MOBILE_NO;
    }

    public String getELECTOR_TYPE() {
        return this.ELECTOR_TYPE;
    }

    public String getVillageName() {
        return this.VillageName;
    }

    public String getUserId() {
        return this.UserId;
    }

    public String getVOTER_IMAGE() {
        return this.VOTER_IMAGE;
    }

    public String getVOTER_FINGERPRINT() {
        return this.VOTER_FINGERPRINT;
    }

    public String getID_DOCUMENT_IMAGE() {
        return this.ID_DOCUMENT_IMAGE;
    }

    public String getVOTING_DATE() {
        return this.VOTING_DATE;
    }

    public String getVOTED() {
        return this.VOTED;
    }

    public String getPanchayatID() {
        return this.PanchayatID;
    }

    public String getAC_NO() {
        return this.AC_NO;
    }

    public String getPART_NO() {
        return this.PART_NO;
    }

    public String getSECTION_NO() {
        return this.SECTION_NO;
    }

    public String getSLNOINPART() {
        return this.SLNOINPART;
    }

    public String getC_HOUSE_NO() {
        return this.C_HOUSE_NO;
    }

    public String getFACE_MATCH() {
        return this.FACE_MATCH;
    }

    public String getFINGERPRINT_MATCH() {
        return this.FINGERPRINT_MATCH;
    }

    public String getAADHAAR_MATCH() {
        return this.AADHAAR_MATCH;
    }

    public void setVOTING_DATE(String voting_date) {
        this.VOTING_DATE = voting_date;
    }

    public void setAADHAAR_MATCH(String aadhaar_match) {
        this.AADHAAR_MATCH = aadhaar_match;
    }

    public void setAADHAAR_NO(String aadhaar_no) {
        this.AADHAAR_NO = aadhaar_no;
    }

    public void setFACE_MATCH(String face_match) {
        this.FACE_MATCH = face_match;
    }

    public void setVOTER_IMAGE(String voter_image) {
        this.VOTER_IMAGE = voter_image;
    }

    public void setVOTER_FINGERPRINT(String voter_fingerprint) {
        this.VOTER_FINGERPRINT = voter_fingerprint;
    }

    public void setID_DOCUMENT_IMAGE(String id_document_image) {
        this.ID_DOCUMENT_IMAGE = id_document_image;
    }

    public void setFINGERPRINT_MATCH(String fingerprint_match) {
        this.FINGERPRINT_MATCH = fingerprint_match;
    }

    public void setDIST_NO(String dist_no) {
        this.DIST_NO = dist_no;
    }

    public void setAC_NO(String ac_no) {
        this.AC_NO = ac_no;
    }

    public void setPART_NO(String part_no) {
        this.PART_NO = part_no;
    }

    public void setSECTION_NO(String section_no) {
        this.SECTION_NO = section_no;
    }

    public void setSLNOINPART(String slNoInPart) {
        this.SLNOINPART = this.SLNOINPART;
    }

    public void setSlNoInWard(String slNoInWard) {
        this.SlNoInWard = slNoInWard;
    }

    public void setC_HOUSE_NO(String house_no) {
        this.C_HOUSE_NO = house_no;
    }

    public void setC_HOUSE_NO_V1(String house_no_v1) {
        this.C_HOUSE_NO_V1 = house_no_v1;
    }

    public void setFM_NAME_EN(String fm_name_en) {
        this.FM_NAME_EN = fm_name_en;
    }

    public void setLASTNAME_EN(String LASTNAME_EN) {
        this.LASTNAME_EN = LASTNAME_EN;
    }

    public void setFM_NAME_V1(String fm_name_v1) {
        this.FM_NAME_V1 = fm_name_v1;
    }

    public void setLASTNAME_V1(String lastname_v1) {
        this.LASTNAME_V1 = lastname_v1;
    }

    public void setRLN_TYPE(String rln_type) {
        this.RLN_TYPE = rln_type;
    }

    public void setRLN_FM_NM_EN(String rln_fm_nm_en) {
        this.RLN_FM_NM_EN = rln_fm_nm_en;
    }

    public void setRLN_L_NM_EN(String rln_l_nm_en) {
        this.RLN_L_NM_EN = rln_l_nm_en;
    }

    public void setRLN_FM_NM_V1(String rln_fm_nm_v1) {
        this.RLN_FM_NM_V1 = rln_fm_nm_v1;
    }

    public void setRLN_L_NM_V1(String rln_l_nm_v1) {
        this.RLN_L_NM_V1 = rln_l_nm_v1;
    }

    public void setEPIC_NO(String epic_no) {
        this.EPIC_NO = epic_no;
    }

    public void setSTATUS_TYPE(String status_type) {
        this.STATUS_TYPE = status_type;
    }

    public void setGENDER(String gender) {
        this.GENDER = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDOB(String dob) {
        this.DOB = dob;
    }

    public void setEMAIL_ID(String email_id) {
        this.EMAIL_ID = email_id;
    }

    public void setMOBILE_NO(String mobile_no) {
        this.MOBILE_NO = mobile_no;
    }

    public void setELECTOR_TYPE(String elector_type) {
        this.ELECTOR_TYPE = elector_type;
    }

    public void setBlockID(String blockID) {
        this.BlockID = blockID;
    }

    public void setPanchayatID(String panchayatID) {
        this.PanchayatID = panchayatID;
    }

    public void setVillageName(String villageName) {
        this.VillageName = villageName;
    }

    public void setWardNo(String wardNo) {
        this.WardNo = wardNo;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public void setVOTED(String voted) {
        this.VOTED = voted;
    }

    public String getSlNoInWard() {
        return this.SlNoInWard;
    }
}
