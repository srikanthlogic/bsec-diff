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
    private static final String CIM_BASE_URL_OLD;
    private static final String IMAGE_UPLOAD_NEW_URL;
    private static Retrofit retrofit;

    /* renamed from: retrofit2 */
    private static Retrofit f23retrofit2;
    private static Retrofit retrofit3;
    private static Retrofit retrofit4;
    private static Retrofit retrofit5;
    private static Retrofit retrofit6;
    private static Retrofit retrofit7;

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
        OkHttpClient client = httpClient.build();
        UserAuth userAuth = new UserAuth(Home.getContext());
        Retrofit retrofit8 = retrofit;
        if (retrofit8 == null || !retrofit8.baseUrl().equals(userAuth.getBaseUrl())) {
            retrofit = new Retrofit.Builder().baseUrl(userAuth.getBaseUrl()).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitInstanceLoginFailCheck() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(90, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() { // from class: com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance.2
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(HttpHeaders.AUTHORIZATION, "").build());
            }
        });
        OkHttpClient client = httpClient.build();
        new UserAuth(Home.getContext());
        if (retrofit3 == null) {
            retrofit3 = new Retrofit.Builder().baseUrl(CIM_BASE_URL_OLD).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit3;
    }

    public static Retrofit getRetrofitInstanceCimUrlForVoterIdUpload() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(30, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();
        new UserAuth(Home.getContext());
        if (retrofit4 == null) {
            retrofit4 = new Retrofit.Builder().baseUrl(CIM_BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit4;
    }

    public static Retrofit getRetrofitInstanceForSync() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(90, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();
        UserAuth userAuth = new UserAuth(Home.getContext());
        if (retrofit5 == null) {
            retrofit5 = new Retrofit.Builder().baseUrl(userAuth.getBaseUrl()).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit5;
    }

    public static Retrofit getRetrofitInstanceCimUrlForVoterIdUploadAlternate() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(30, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();
        new UserAuth(Home.getContext());
        if (retrofit6 == null) {
            retrofit6 = new Retrofit.Builder().baseUrl(CIM_BASE_URL_OLD).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit6;
    }

    public static Retrofit getRetrofitInstanceImageUploadNewUrl() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.callTimeout(30, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();
        new UserAuth(Home.getContext());
        if (retrofit7 == null) {
            retrofit7 = new Retrofit.Builder().baseUrl(IMAGE_UPLOAD_NEW_URL).client(client).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return retrofit7;
    }
}
