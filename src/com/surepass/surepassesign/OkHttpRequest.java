package com.surepass.surepassesign;

import android.content.Context;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.common.net.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
/* compiled from: OkHttpRequest.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u0000 $2\u00020\u0001:\u0001$B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0016\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJB\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182*\u0010\u001d\u001a&\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001ej\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u0001`\u001f2\u0006\u0010\u0019\u001a\u00020\u001aJD\u0010 \u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u00182\u001a\u0010\u001d\u001a\u0016\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\n\u0012\u0002\b\u0003\u0012\u0002\b\u0003`\u001f2\u0006\u0010\u0019\u001a\u00020\u001aR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006%"}, d2 = {"Lcom/surepass/surepassesign/OkHttpRequest;", "", "client", "Lokhttp3/OkHttpClient;", "context", "Landroid/content/Context;", "(Lokhttp3/OkHttpClient;Landroid/content/Context;)V", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "GET", "Lokhttp3/Call;", ImagesContract.URL, "", "callback", "Lokhttp3/Callback;", "GET_FOR_ESIGN", "POST", "parameters", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "UPLOAD_PDF", "byteArray", "", "docName", "Companion", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class OkHttpRequest {
    public static final Companion Companion = new Companion(null);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client;
    public Context context;
    public Store store;

    public OkHttpRequest(OkHttpClient client, Context context) {
        Intrinsics.checkParameterIsNotNull(client, "client");
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.client = client;
        this.context = context;
        Context context2 = this.context;
        if (context2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
        }
        this.store = new Store(context2);
    }

    public final OkHttpClient getClient() {
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        return okHttpClient;
    }

    public final void setClient(OkHttpClient okHttpClient) {
        Intrinsics.checkParameterIsNotNull(okHttpClient, "<set-?>");
        this.client = okHttpClient;
    }

    public final Context getContext() {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
        }
        return context;
    }

    public final void setContext(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "<set-?>");
        this.context = context;
    }

    public final Store getStore() {
        Store store = this.store;
        if (store == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        return store;
    }

    public final void setStore(Store store) {
        Intrinsics.checkParameterIsNotNull(store, "<set-?>");
        this.store = store;
    }

    public final Call GET(String url, Callback callback) {
        Intrinsics.checkParameterIsNotNull(url, ImagesContract.URL);
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        Intrinsics.checkExpressionValueIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        return call;
    }

    public final Call GET_FOR_ESIGN(String url, Callback callback) {
        Intrinsics.checkParameterIsNotNull(url, ImagesContract.URL);
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        Store store = this.store;
        if (store == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        sb.append(store.getToken());
        Request request = new Request.Builder().url(url).addHeader(HttpHeaders.AUTHORIZATION, sb.toString()).build();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        Intrinsics.checkExpressionValueIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        return call;
    }

    public final Call POST(String url, HashMap<Object, Object> hashMap, Callback callback) {
        Intrinsics.checkParameterIsNotNull(url, ImagesContract.URL);
        Intrinsics.checkParameterIsNotNull(hashMap, "parameters");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        JSONObject builder = new JSONObject();
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        Store store = this.store;
        if (store == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        sb.append(store.getToken());
        String token = sb.toString();
        if (hashMap.size() > 0) {
            for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
                if (entry != null) {
                    Map.Entry<Object, Object> pair = entry;
                    builder.put(String.valueOf(pair.getKey()), pair.getValue());
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map.Entry<*, *>");
                }
            }
            Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), builder.toString())).addHeader(HttpHeaders.AUTHORIZATION, token).build();
            OkHttpClient okHttpClient = this.client;
            if (okHttpClient == null) {
                Intrinsics.throwUninitializedPropertyAccessException("client");
            }
            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
            Intrinsics.checkExpressionValueIsNotNull(call, NotificationCompat.CATEGORY_CALL);
            return call;
        }
        Request request2 = new Request.Builder().url(url).addHeader(HttpHeaders.AUTHORIZATION, token).build();
        OkHttpClient okHttpClient2 = this.client;
        if (okHttpClient2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        Call call2 = okHttpClient2.newCall(request2);
        call2.enqueue(callback);
        Intrinsics.checkExpressionValueIsNotNull(call2, NotificationCompat.CATEGORY_CALL);
        return call2;
    }

    public final Call UPLOAD_PDF(String url, byte[] byteArray, String docName, HashMap<?, ?> hashMap, Callback callback) {
        Intrinsics.checkParameterIsNotNull(url, ImagesContract.URL);
        Intrinsics.checkParameterIsNotNull(byteArray, "byteArray");
        Intrinsics.checkParameterIsNotNull(hashMap, "parameters");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<?, ?> entry : hashMap.entrySet()) {
            if (entry != null) {
                Map.Entry<?, ?> pair = entry;
                builder.addFormDataPart(String.valueOf(pair.getKey()), String.valueOf(pair.getValue()));
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map.Entry<*, *>");
            }
        }
        builder.addFormDataPart(UriUtil.LOCAL_FILE_SCHEME, docName, RequestBody.create(MediaType.parse("application/pdf"), byteArray));
        MultipartBody formBody = builder.build();
        Intrinsics.checkExpressionValueIsNotNull(formBody, "builder.build()");
        Request request = new Request.Builder().url(url).post(formBody).build();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        Intrinsics.checkExpressionValueIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        return call;
    }

    /* compiled from: OkHttpRequest.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/surepass/surepassesign/OkHttpRequest$Companion;", "", "()V", "JSON", "Lokhttp3/MediaType;", "getJSON", "()Lokhttp3/MediaType;", "newInstance", "Lcom/surepass/surepassesign/OTP;", "app_release"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final MediaType getJSON() {
            return OkHttpRequest.JSON;
        }

        public final OTP newInstance() {
            return new OTP();
        }
    }
}
