package com.example.aadhaarfpoffline.tatvik.config;

import com.example.aadhaarfpoffline.tatvik.Home;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.google.common.net.HttpHeaders;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/* loaded from: classes2.dex */
public class RetrofitClientInstance {
    private static final String BASE_URL;
    private static final String BASE_URL2;
    private static final String CIM_BASE_URL;
    private static Retrofit retrofit;

    /* renamed from: retrofit2 */
    private static Retrofit f23retrofit2;

    public static Retrofit getRetrofitInstanceLoginOnly() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(90, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() { // from class: com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, "").build());
            }
        });
        OkHttpClient client = httpClient.build();
        new UserAuth(Home.getContext());
        if (f23retrofit2 == null) {
            f23retrofit2 = new Retrofit.Builder().baseUrl(CIM_BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return f23retrofit2;
    }

    public static Retrofit getRetrofitInstance() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(40, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() { // from class: com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance.2
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, "").build());
            }
        });
        OkHttpClient client = httpClient.build();
        UserAuth userAuth = new UserAuth(Home.getContext());
        Retrofit retrofit3 = retrofit;
        if (retrofit3 == null || !retrofit3.baseUrl().equals(userAuth.getBaseUrl())) {
            retrofit = new Retrofit.Builder().baseUrl(userAuth.getBaseUrl()).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit;
    }
}
