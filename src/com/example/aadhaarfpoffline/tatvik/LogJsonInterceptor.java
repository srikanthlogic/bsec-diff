package com.example.aadhaarfpoffline.tatvik;

import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
/* loaded from: classes2.dex */
public class LogJsonInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String rawJson = response.body().string();
        Log.d(BuildConfig.APPLICATION_ID, String.format("raw JSON response is: %s", rawJson));
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
    }
}
