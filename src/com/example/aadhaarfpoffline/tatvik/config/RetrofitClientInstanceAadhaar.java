package com.example.aadhaarfpoffline.tatvik.config;

import com.google.common.net.HttpHeaders;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
/* loaded from: classes2.dex */
public class RetrofitClientInstanceAadhaar {
    private static final String BASE_URL = "http://support.phoneme.in/";
    private static final String BASE_URL2 = "http://support.phoneme.in/";
    private static final String BASE_URL3 = "https://baafauth.bihar.gov.in/";
    private static Retrofit retrofit;

    /* renamed from: retrofit2  reason: collision with root package name */
    private static Retrofit f4retrofit2;

    public static Retrofit getRetrofitInstance() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() { // from class: com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstanceAadhaar.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, "").build());
            }
        });
        OkHttpClient client = httpClient.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL3).client(client).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitInstance2() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() { // from class: com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstanceAadhaar.2
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, "").build());
            }
        });
        OkHttpClient client = httpClient.build();
        if (f4retrofit2 == null) {
            f4retrofit2 = new Retrofit.Builder().baseUrl("http://support.phoneme.in/").client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return f4retrofit2;
    }
}
