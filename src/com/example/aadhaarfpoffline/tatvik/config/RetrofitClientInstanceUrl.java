package com.example.aadhaarfpoffline.tatvik.config;

import com.example.aadhaarfpoffline.tatvik.LogJsonInterceptor;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
/* loaded from: classes2.dex */
public class RetrofitClientInstanceUrl {
    private static final String BASE_URL = "https://kyc-api.aadhaarkyc.io/";
    private static final String BASE_URL2 = "https://sandbox.surepass.io/";
    private static final String BASE_URL3 = "https://sandbox.aadhaarkyc.io/";
    private static final String defaultToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjM2NTU1MjQsIm5iZiI6MTYyMzY1NTUyNCwianRpIjoiZWI5YjE1YmYtYWRiMS00M2RlLWExYzEtNjJiNzhmNjgyNDZkIiwiZXhwIjoxNjI2MjQ3NTI0LCJpZGVudGl0eSI6ImRldi5teXBob25lbWVAYWFkaGFhcmFwaS5pbyIsImZyZXNoIjpmYWxzZSwidHlwZSI6ImFjY2VzcyIsInVzZXJfY2xhaW1zIjp7InNjb3BlcyI6WyJyZWFkIl19fQ.JPNY8ur3Hw_KAEHl8o_G36Gzl0QR-H5Ogctq_tIHyaI";

    /* renamed from: retrofit2  reason: collision with root package name */
    private static Retrofit f26retrofit2;

    public static Retrofit getRetrofitInstanceDefaultTokenNewUrl(String url) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new LogJsonInterceptor());
        OkHttpClient client = httpClient.build();
        if (f26retrofit2 == null) {
            f26retrofit2 = new Retrofit.Builder().baseUrl(url).addConverterFactory(ScalarsConverterFactory.create()).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return f26retrofit2;
    }
}
