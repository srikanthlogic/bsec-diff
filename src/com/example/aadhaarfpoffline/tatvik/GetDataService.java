package com.example.aadhaarfpoffline.tatvik;

import com.example.aadhaarfpoffline.tatvik.network.AadhaarMatchUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.AadhaarUserCheckGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.BoothOfficerDeviceStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.DBUpdateResponse;
import com.example.aadhaarfpoffline.tatvik.network.ElectionBoothLoginGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.FaceImageUploadCheckResponse;
import com.example.aadhaarfpoffline.tatvik.network.FacefpmatchvoteridUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.FinperprintCompareServerResponse;
import com.example.aadhaarfpoffline.tatvik.network.GetUploadLinkResponse;
import com.example.aadhaarfpoffline.tatvik.network.ImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.InitializeResponse;
import com.example.aadhaarfpoffline.tatvik.network.LockUpdateResponse;
import com.example.aadhaarfpoffline.tatvik.network.LoginForUrlResponse;
import com.example.aadhaarfpoffline.tatvik.network.LoginTimeUpdateGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.MultipleFaceImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.MultipleFpUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.OfficialDataGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.PostUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransTableGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransactionRowPostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserFaceMatchStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserLocationUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserVotingStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterDataGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterListGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterListNewTableGetResponse;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
/* loaded from: classes2.dex */
public interface GetDataService {
    @GET("/VoterAuthenticationapi/getboothidbyphone")
    Call<OfficialDataGetResponse> getBoothIdBasedOnPhone(@Query("phone") String str);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/esign/initialize")
    Call<InitializeResponse> getInitialize2(@Body String str);

    @GET("/VoterAuthenticationapi/getLoginNew")
    Call<ElectionBoothLoginGetResponse> getLoginNewMethod(@Query("phone") String str, @Query("otp") String str2, @Query("location") String str3);

    @GET("/VoterAuthenticationapi/LoginUpdate")
    Call<LoginTimeUpdateGetResponse> getLoginTimeUpdateMethod(@Query("phone") String str);

    @GET("/VoterAuthenticationapi/getLoginWithMultipleCluster")
    Call<ElectionBoothLoginGetResponse> getLoginWithMultipleCluster(@Query("phone") String str, @Query("otp") String str2, @Query("location") String str3);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/login")
    Call<LoginForUrlResponse> getLoginWithUrl(@FieldMap Map<String, String> map);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/esign/report/{clientid}")
    Call<PostUploadResponse> getReport(@Path("clientid") String str, @Body String str2);

    @GET("/VoterAuthenticationapi/gettranslistbyboothno")
    Call<TransTableGetResponse> getTransTable(@QueryMap Map<String, String> map);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/esign/get-upload-link")
    Call<GetUploadLinkResponse> getUploadLink(@Body String str);

    @GET("/VoterAuthenticationapi/getVoterData")
    Call<VoterDataGetResponse> getVoterByUserId(@QueryMap Map<String, String> map);

    @GET("/VoterAuthenticationapi/getVoterData")
    Call<VoterDataGetResponse> getVoterByUserId_old(@Query("user_id") String str);

    @GET("/VoterAuthenticationapi/aadhaaruserexists")
    Call<AadhaarUserCheckGetResponse> getVoterByVoterAadhaarNum(@QueryMap Map<String, String> map);

    @GET("/VoterAuthenticationapi/aadhaaruserexists")
    Call<AadhaarUserCheckGetResponse> getVoterByVoterAadhaarNum_old(@Query("aadhaarnum") String str);

    @GET("/VoterAuthenticationapi/getVoterData")
    Call<VoterDataGetResponse> getVoterByVoterId(@Query("voterid") String str);

    @GET("/VoterAuthenticationapi/getvoterlist")
    Call<VoterListGetResponse> getVoterList();

    @GET("/VoterAuthenticationapi/getvoterlistbasedonboothid")
    Call<VoterListGetResponse> getVoterListByBoothId(@Query("boothid") String str);

    @GET("/VoterAuthenticationapi/getvoterlistnewtable")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByBlockId(@Query("blockid") int i);

    @GET("/VoterAuthenticationapi/getvoterlistbyboothno")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByBoothNo(@QueryMap Map<String, String> map);

    @GET("/VoterAuthenticationapi/getvoterlistbyboothno")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByBoothNo_old(@Query("dist_no") String str, @Query("block_id") String str2, @Query("panchayat_id") String str3, @Query("ward_no") String str4, @Query("booth_no") String str5);

    @GET("/VoterAuthenticationapi/getvoterlistnewtable")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByPanchayatId(@Query("panchayatid") String str);

    @GET("/VoterAuthenticationapi/getvoterlistbyward")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByWard(@Query("ward_no") String str);

    @GET("/VoterAuthenticationapi/gettableforsync")
    Call<VoterListNewTableGetResponse> getVoterListTableForSync(@Query("dist_no") String str, @Query("block_id") String str2, @Query("panchayat_id") String str3, @Query("ward_no") String str4, @Query("booth_no") String str5);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/savemachineinfo")
    Call<LockUpdateResponse> lockRecordUpdate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/aadhaarmatchupdate")
    Call<AadhaarMatchUpdatePostResponse> postAadhaarMatchUpdate(@FieldMap Map<String, String> map);

    @POST("/VoterAuthenticationapi/comparefingerprintmfs100")
    Call<FinperprintCompareServerResponse> postCompareFpServer(@Query("voterid") String str, @Query("fpbytearray") byte[] bArr);

    @POST("/VoterAuthenticationapi/comparefingerprintmfs100")
    @Multipart
    Call<FinperprintCompareServerResponse> postCompareFpServer2(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/syncdataserver")
    Call<DBUpdateResponse> postDBUpdate(@FieldMap Map<String, String> map);

    @Headers({"Content-Type: application/xml"})
    @POST("/baafprodv25/auaservice/authenticate/")
    Call<String> postFPAadhaar(@Body String str);

    @Headers({"Content-Type: application/xml"})
    @POST("/baafprodv25/auaservice/authenticate/")
    Call<String> postFPAadhaar1(@Body RequestBody requestBody);

    @POST("/VoterAuthenticationapi/uploadfp")
    @Multipart
    Call<ImageUploadResponse> postFPUploadWithImage(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/VoterAuthenticationapi/uploadface")
    @Multipart
    Call<ImageUploadResponse> postFaceImage(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/VoterAuthenticationapi/uploadfaceforchecking")
    @Multipart
    Call<FaceImageUploadCheckResponse> postFaceImageForCheck(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/facematchstatusupdate")
    Call<UserFaceMatchStatusUpdatePostResponse> postFaceMatchStatusUpdate(@FieldMap Map<String, String> map);

    @POST("/VoterAuthenticationapi/uploadfingerprint")
    @Multipart
    Call<ImageUploadResponse> postFingerprintImage(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/VoterAuthenticationapi/comparefingerprintmfs100")
    @Multipart
    Call<FinperprintCompareServerResponse> postFingerprintTemplateCompare(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/updatedevicedetachedtimeboothofficer")
    Call<BoothOfficerDeviceStatusUpdatePostResponse> postFpDeviceStatusUpdate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/locationupdate")
    Call<UserLocationUpdatePostResponse> postLocationUpdate(@FieldMap Map<String, String> map);

    @POST("/VoterAuthenticationapi/syncdatawithimage")
    @Multipart
    Call<ImageUploadResponse> postUpdateDBrow(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/")
    @Multipart
    Call<String> postUploadFile(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part part);

    @POST("/VoterAuthenticationapi/uploadvoteridentification")
    @Multipart
    Call<ImageUploadResponse> postVoterIdentification(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/VoterAuthenticationapi/uploadmultiplefingerprintsforchecking")
    @Multipart
    Call<MultipleFpUploadResponse> postVoterIdentificationMultiFingerP(@Part MultipartBody.Part[] partArr, @PartMap Map<String, RequestBody> map);

    @POST("/VoterAuthenticationapi/uploadmultiplefacesforchecking")
    @Multipart
    Call<MultipleFaceImageUploadResponse> postVoterIdentificationMultiImages(@Part MultipartBody.Part[] partArr, @PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/votingstatusupdate")
    Call<UserVotingStatusUpdatePostResponse> postVotingStatusUpdate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/voteridmatchupdate")
    Call<FacefpmatchvoteridUpdatePostResponse> postfacefpmatchvoterid(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/updatefpasstring")
    Call<FinperprintCompareServerResponse> updateFpServer2(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/VoterAuthenticationapi/updatetransactionrow")
    Call<TransactionRowPostResponse> updateTransactionRow(@FieldMap Map<String, String> map);
}
