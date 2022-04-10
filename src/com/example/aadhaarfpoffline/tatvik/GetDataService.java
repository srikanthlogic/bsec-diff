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
import com.example.aadhaarfpoffline.tatvik.network.LoginForUrlResponse;
import com.example.aadhaarfpoffline.tatvik.network.LoginTimeUpdateGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.MultipleFaceImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.MultipleFpUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.OfficialDataGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.PostUploadResponse;
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
/* loaded from: classes2.dex */
public interface GetDataService {
    @GET("/election/VoterAuthenticationapi/getboothidbyphone")
    Call<OfficialDataGetResponse> getBoothIdBasedOnPhone(@Query("phone") String str);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/esign/initialize")
    Call<InitializeResponse> getInitialize2(@Body String str);

    @GET("/election/VoterAuthenticationapi/getLoginNew")
    Call<ElectionBoothLoginGetResponse> getLoginNewMethod(@Query("phone") String str, @Query("otp") String str2, @Query("location") String str3);

    @GET("/election/VoterAuthenticationapi/LoginUpdate")
    Call<LoginTimeUpdateGetResponse> getLoginTimeUpdateMethod(@Query("phone") String str);

    @GET("/election/VoterAuthenticationapi/getLoginWithMultipleCluster")
    Call<ElectionBoothLoginGetResponse> getLoginWithMultipleCluster(@Query("phone") String str, @Query("otp") String str2, @Query("location") String str3);

    @FormUrlEncoded
    @POST("/PanchayatElection/login")
    Call<LoginForUrlResponse> getLoginWithUrl(@FieldMap Map<String, String> map);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/esign/report/{clientid}")
    Call<PostUploadResponse> getReport(@Path("clientid") String str, @Body String str2);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/esign/get-upload-link")
    Call<GetUploadLinkResponse> getUploadLink(@Body String str);

    @GET("/election/VoterAuthenticationapi/aadhaaruserexists")
    Call<AadhaarUserCheckGetResponse> getVoterByVoterAadhaarNum(@Query("aadhaarnum") String str);

    @GET("/election/VoterAuthenticationapi/getVoterData")
    Call<VoterDataGetResponse> getVoterByVoterId(@Query("voterid") String str);

    @GET("/election/VoterAuthenticationapi/getvoterlist")
    Call<VoterListGetResponse> getVoterList();

    @GET("/election/VoterAuthenticationapi/getvoterlistbasedonboothid")
    Call<VoterListGetResponse> getVoterListByBoothId(@Query("boothid") String str);

    @GET("/election/VoterAuthenticationapi/getvoterlistnewtable")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByBlockId(@Query("blockid") int i);

    @GET("/election/VoterAuthenticationapi/getvoterlistbyboothno")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByBoothNo(@Query("dist_no") String str, @Query("block_id") String str2, @Query("panchayat_id") String str3, @Query("ward_no") String str4, @Query("booth_no") String str5);

    @GET("/election/VoterAuthenticationapi/getvoterlistnewtable")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByPanchayatId(@Query("panchayatid") String str);

    @GET("/election/VoterAuthenticationapi/getvoterlistbyward")
    Call<VoterListNewTableGetResponse> getVoterListNewTableByWard(@Query("ward_no") String str);

    @GET("/election/VoterAuthenticationapi/gettableforsync")
    Call<VoterListNewTableGetResponse> getVoterListTableForSync(@Query("dist_no") String str, @Query("block_id") String str2, @Query("panchayat_id") String str3, @Query("ward_no") String str4, @Query("booth_no") String str5);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/aadhaarmatchupdate")
    Call<AadhaarMatchUpdatePostResponse> postAadhaarMatchUpdate(@FieldMap Map<String, String> map);

    @POST("/election/VoterAuthenticationapi/comparefingerprintmfs100")
    Call<FinperprintCompareServerResponse> postCompareFpServer(@Query("voterid") String str, @Query("fpbytearray") byte[] bArr);

    @POST("/election/VoterAuthenticationapi/comparefingerprintmfs100")
    @Multipart
    Call<FinperprintCompareServerResponse> postCompareFpServer2(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/syncdataserver")
    Call<DBUpdateResponse> postDBUpdate(@FieldMap Map<String, String> map);

    @Headers({"Content-Type: application/xml"})
    @POST("/baafprodv25/auaservice/authenticate/")
    Call<String> postFPAadhaar(@Body String str);

    @Headers({"Content-Type: application/xml"})
    @POST("/baafprodv25/auaservice/authenticate/")
    Call<String> postFPAadhaar1(@Body RequestBody requestBody);

    @POST("/election/VoterAuthenticationapi/uploadfp")
    @Multipart
    Call<ImageUploadResponse> postFPUploadWithImage(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/election/VoterAuthenticationapi/uploadface")
    @Multipart
    Call<ImageUploadResponse> postFaceImage(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/election/VoterAuthenticationapi/uploadfaceforchecking")
    @Multipart
    Call<FaceImageUploadCheckResponse> postFaceImageForCheck(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/facematchstatusupdate")
    Call<UserFaceMatchStatusUpdatePostResponse> postFaceMatchStatusUpdate(@FieldMap Map<String, String> map);

    @POST("/election/VoterAuthenticationapi/uploadfingerprint")
    @Multipart
    Call<ImageUploadResponse> postFingerprintImage(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/election/VoterAuthenticationapi/comparefingerprintmfs100")
    @Multipart
    Call<FinperprintCompareServerResponse> postFingerprintTemplateCompare(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/updatedevicedetachedtimeboothofficer")
    Call<BoothOfficerDeviceStatusUpdatePostResponse> postFpDeviceStatusUpdate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/locationupdate")
    Call<UserLocationUpdatePostResponse> postLocationUpdate(@FieldMap Map<String, String> map);

    @POST("/election/VoterAuthenticationapi/syncdatawithimage")
    @Multipart
    Call<ImageUploadResponse> postUpdateDBrow(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/")
    @Multipart
    Call<String> postUploadFile(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part part);

    @POST("/election/VoterAuthenticationapi/uploadvoteridentification")
    @Multipart
    Call<ImageUploadResponse> postVoterIdentification(@Part MultipartBody.Part part, @PartMap Map<String, RequestBody> map);

    @POST("/election/VoterAuthenticationapi/uploadmultiplefingerprintsforchecking")
    @Multipart
    Call<MultipleFpUploadResponse> postVoterIdentificationMultiFingerP(@Part MultipartBody.Part[] partArr, @PartMap Map<String, RequestBody> map);

    @POST("/election/VoterAuthenticationapi/uploadmultiplefacesforchecking")
    @Multipart
    Call<MultipleFaceImageUploadResponse> postVoterIdentificationMultiImages(@Part MultipartBody.Part[] partArr, @PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/votingstatusupdate")
    Call<UserVotingStatusUpdatePostResponse> postVotingStatusUpdate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/voteridmatchupdate")
    Call<FacefpmatchvoteridUpdatePostResponse> postfacefpmatchvoterid(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/election/VoterAuthenticationapi/updatefpasstring")
    Call<FinperprintCompareServerResponse> updateFpServer2(@FieldMap Map<String, String> map);
}
