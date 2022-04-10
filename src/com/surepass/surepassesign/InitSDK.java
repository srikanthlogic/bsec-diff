package com.surepass.surepassesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import com.sec.biometric.license.SecBiometricLicenseManager;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: InitSDK.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010F\u001a\u00020GH\u0002J\b\u0010H\u001a\u00020GH\u0002J\b\u0010I\u001a\u00020GH\u0002J\"\u0010J\u001a\u00020G2\u0006\u0010K\u001a\u00020\u000b2\u0006\u0010L\u001a\u00020\u000b2\b\u0010M\u001a\u0004\u0018\u00010&H\u0014J\u0014\u0010N\u001a\u00020G2\n\u0010O\u001a\u00060Pj\u0002`QH\u0002J\u0010\u0010R\u001a\u00020G2\u0006\u0010S\u001a\u00020\u000bH\u0002J\u0012\u0010T\u001a\u00020G2\b\u0010U\u001a\u0004\u0018\u00010VH\u0014J\b\u0010W\u001a\u00020GH\u0002J\b\u0010X\u001a\u00020GH\u0002J\b\u0010Y\u001a\u00020GH\u0002J\b\u0010Z\u001a\u00020GH\u0002J\b\u0010[\u001a\u00020GH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0006R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\bR\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010(\"\u0004\b3\u0010*R\u001a\u00104\u001a\u000205X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001c\u0010:\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u0006\"\u0004\b<\u0010\bR\u001a\u0010=\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010(\"\u0004\b?\u0010*R\u001a\u0010@\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010(\"\u0004\bB\u0010*R\u001a\u0010C\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010(\"\u0004\bE\u0010*¨\u0006\\"}, d2 = {"Lcom/surepass/surepassesign/InitSDK;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "BASEURL", "", "getBASEURL", "()Ljava/lang/String;", "setBASEURL", "(Ljava/lang/String;)V", "ESIGN_CONSOLE", "REQUEST_CODE_FOR_VERIFICATION_RESULT", "", "getREQUEST_CODE_FOR_VERIFICATION_RESULT", "()I", "TAG", "getTAG", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "setEditor", "(Landroid/content/SharedPreferences$Editor;)V", "env", "getEnv", "setEnv", "phoneNumberIntent", "Landroid/content/Intent;", "getPhoneNumberIntent", "()Landroid/content/Intent;", "setPhoneNumberIntent", "(Landroid/content/Intent;)V", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "selfieIntent", "getSelfieIntent", "setSelfieIntent", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "token", "getToken", "setToken", "uploadDocs", "getUploadDocs", "setUploadDocs", "userDetailsIntent", "getUserDetailsIntent", "setUserDetailsIntent", "virtualSignIntent", "getVirtualSignIntent", "setVirtualSignIntent", "getBranding", "", "getPrefillOptions", "initVerification", "onActivityResult", "requestCode", "resultCode", UriUtil.DATA_SCHEME, "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "startPhoneNumberActivity", "startSelfieActivity", "startUploadingActivity", "startUserDetailsActivity", "startVirtulSignActivity", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class InitSDK extends AppCompatActivity {
    private String BASEURL;
    private HashMap _$_findViewCache;
    public OkHttpClient client;
    public Dialog dialog;
    public SharedPreferences.Editor editor;
    private String env;
    public Intent phoneNumberIntent;
    public OkHttpRequest request;
    public Intent selfieIntent;
    public Store store;
    private String token;
    public Intent uploadDocs;
    public Intent userDetailsIntent;
    public Intent virtualSignIntent;
    private final String TAG = "InitSDK";
    private final String ESIGN_CONSOLE = "ESIGN_CONSOLE";
    private final int REQUEST_CODE_FOR_VERIFICATION_RESULT = CameraAccessExceptionCompat.CAMERA_UNAVAILABLE_DO_NOT_DISTURB;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final Intent getUserDetailsIntent() {
        Intent intent = this.userDetailsIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userDetailsIntent");
        }
        return intent;
    }

    public final void setUserDetailsIntent(Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "<set-?>");
        this.userDetailsIntent = intent;
    }

    public final Intent getPhoneNumberIntent() {
        Intent intent = this.phoneNumberIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneNumberIntent");
        }
        return intent;
    }

    public final void setPhoneNumberIntent(Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "<set-?>");
        this.phoneNumberIntent = intent;
    }

    public final Intent getSelfieIntent() {
        Intent intent = this.selfieIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selfieIntent");
        }
        return intent;
    }

    public final void setSelfieIntent(Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "<set-?>");
        this.selfieIntent = intent;
    }

    public final Intent getVirtualSignIntent() {
        Intent intent = this.virtualSignIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("virtualSignIntent");
        }
        return intent;
    }

    public final void setVirtualSignIntent(Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "<set-?>");
        this.virtualSignIntent = intent;
    }

    public final Intent getUploadDocs() {
        Intent intent = this.uploadDocs;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadDocs");
        }
        return intent;
    }

    public final void setUploadDocs(Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "<set-?>");
        this.uploadDocs = intent;
    }

    public final String getToken() {
        return this.token;
    }

    public final void setToken(String str) {
        this.token = str;
    }

    public final String getEnv() {
        return this.env;
    }

    public final void setEnv(String str) {
        this.env = str;
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

    public final Dialog getDialog() {
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        return dialog;
    }

    public final void setDialog(Dialog dialog) {
        Intrinsics.checkParameterIsNotNull(dialog, "<set-?>");
        this.dialog = dialog;
    }

    public final String getBASEURL() {
        return this.BASEURL;
    }

    public final void setBASEURL(String str) {
        this.BASEURL = str;
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

    public final OkHttpRequest getRequest() {
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        return okHttpRequest;
    }

    public final void setRequest(OkHttpRequest okHttpRequest) {
        Intrinsics.checkParameterIsNotNull(okHttpRequest, "<set-?>");
        this.request = okHttpRequest;
    }

    public final int getREQUEST_CODE_FOR_VERIFICATION_RESULT() {
        return this.REQUEST_CODE_FOR_VERIFICATION_RESULT;
    }

    public final SharedPreferences.Editor getEditor() {
        SharedPreferences.Editor editor = this.editor;
        if (editor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        return editor;
    }

    public final void setEditor(SharedPreferences.Editor editor) {
        Intrinsics.checkParameterIsNotNull(editor, "<set-?>");
        this.editor = editor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        String str;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        this.userDetailsIntent = new Intent(this, UserDetails.class);
        this.phoneNumberIntent = new Intent(this, PhoneNumber.class);
        this.uploadDocs = new Intent(this, UploadDocs.class);
        this.selfieIntent = new Intent(this, SelfieActivity.class);
        this.virtualSignIntent = new Intent(this, SignatureActivity.class);
        this.store = new Store(this);
        this.token = getIntent().getStringExtra("token");
        this.env = getIntent().getStringExtra("env");
        this.dialog = new Dialog(this);
        this.client = new OkHttpClient();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        if (this.token == null || (str = this.env) == null) {
            onCaughtFailureException(new NullPointerException("Token and must be not null"));
            return;
        }
        if (Intrinsics.areEqual(str, "PREPROD")) {
            Store store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            store.setBaseUrl(Constants.Companion.getBASEURL_PRE_PROD());
        } else if (Intrinsics.areEqual(this.env, "PROD")) {
            Store store2 = this.store;
            if (store2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            store2.setBaseUrl(Constants.Companion.getBASEURL_PROD());
        } else {
            onCaughtFailureException(new NullPointerException("env value must be PROD or PREPROD"));
        }
        Store store3 = this.store;
        if (store3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        String str2 = this.token;
        if (str2 == null) {
            Intrinsics.throwNpe();
        }
        String str3 = this.env;
        if (str3 == null) {
            Intrinsics.throwNpe();
        }
        store3.storeToken(str2, str3);
        getPrefillOptions();
    }

    private final void getPrefillOptions() {
        Store store = this.store;
        if (store == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        this.BASEURL = store.getBaseUrl();
        String url = Intrinsics.stringPlus(this.BASEURL, "get-options");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.GET_FOR_ESIGN(url, new Callback() { // from class: com.surepass.surepassesign.InitSDK$getPrefillOptions$1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                e.printStackTrace();
                InitSDK.this.getDialog().closeDialog();
                InitSDK.this.onCaughtFailureException(e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                String responseData = body != null ? body.string() : null;
                if (response.isSuccessful()) {
                    try {
                        JSONObject data = new JSONObject(responseData).getJSONObject(UriUtil.DATA_SCHEME);
                        Store store2 = InitSDK.this.getStore();
                        Intrinsics.checkExpressionValueIsNotNull(data, UriUtil.DATA_SCHEME);
                        store2.setConfig(data);
                        InitSDK.this.getBranding();
                    } catch (Exception e) {
                        String tag = InitSDK.this.getTAG();
                        Log.e(tag, "Get options with message " + e.getMessage());
                        e.printStackTrace();
                        InitSDK.this.getDialog().closeDialog();
                    }
                } else {
                    String tag2 = InitSDK.this.getTAG();
                    Log.e(tag2, "unsuccessful response " + response.message());
                    InitSDK.this.getDialog().closeDialog();
                    InitSDK.this.onCaughtWrongResponse(response.code());
                }
            }
        });
    }

    public final void getBranding() {
        String url = Intrinsics.stringPlus(this.BASEURL, "get-branding");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.GET_FOR_ESIGN(url, new Callback() { // from class: com.surepass.surepassesign.InitSDK$getBranding$1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                e.printStackTrace();
                InitSDK.this.getDialog().closeDialog();
                InitSDK.this.onCaughtFailureException(e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                String responseData = body != null ? body.string() : null;
                Log.e(InitSDK.this.getTAG(), responseData);
                if (response.isSuccessful()) {
                    try {
                        JSONObject data = new JSONObject(responseData).getJSONObject(UriUtil.DATA_SCHEME);
                        Store store = InitSDK.this.getStore();
                        Intrinsics.checkExpressionValueIsNotNull(data, UriUtil.DATA_SCHEME);
                        store.setBrandDetails(data);
                        InitSDK.this.initVerification();
                        InitSDK.this.getDialog().closeDialog();
                    } catch (Exception e) {
                        InitSDK.this.initVerification();
                        e.printStackTrace();
                        InitSDK.this.getDialog().closeDialog();
                    }
                } else {
                    InitSDK.this.initVerification();
                    InitSDK.this.getDialog().closeDialog();
                }
            }
        });
    }

    public final void initVerification() {
        try {
            Store store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String config = store.getConfig();
            JSONObject configObj = new JSONObject(config).getJSONObject("config");
            String str = this.TAG;
            Log.e(str, "configObj " + String.valueOf(config));
            boolean skipMainScreen = configObj.getBoolean("skip_main_screen");
            boolean skipOtp = configObj.getBoolean("skip_otp");
            boolean acceptSelfie = configObj.getBoolean("accept_selfie");
            boolean acceptVirtualSign = configObj.getBoolean("accept_virtual_sign");
            if (!skipMainScreen) {
                startUserDetailsActivity();
            } else if (!skipOtp) {
                startPhoneNumberActivity();
            } else if (acceptSelfie) {
                startSelfieActivity();
            } else if (acceptVirtualSign) {
                startVirtulSignActivity();
            } else {
                startUploadingActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Dialog dialog = this.dialog;
            if (dialog == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dialog");
            }
            dialog.closeDialog();
            onCaughtFailureException(e);
        }
    }

    private final void startUserDetailsActivity() {
        Intent intent = this.userDetailsIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userDetailsIntent");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    private final void startPhoneNumberActivity() {
        Intent intent = this.phoneNumberIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneNumberIntent");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    private final void startUploadingActivity() {
        Intent intent = this.uploadDocs;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadDocs");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    private final void startSelfieActivity() {
        Intent intent = this.selfieIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selfieIntent");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    private final void startVirtulSignActivity() {
        Intent intent = this.virtualSignIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("virtualSignIntent");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    public final void onCaughtWrongResponse(int statusCode) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("signedResponse", new Constants().getJsonResponse(statusCode).toString());
        setResult(-1, resultIntent);
        finish();
    }

    public final void onCaughtFailureException(Exception e) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("signedResponse", new Constants().getJsonResponse(SecBiometricLicenseManager.ERROR_INTERNAL_SERVER, e.getMessage()).toString());
        setResult(-1, resultIntent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CODE_FOR_VERIFICATION_RESULT) {
            Intent resultIntent = new Intent();
            if (data == null) {
                try {
                    Intrinsics.throwNpe();
                } catch (NullPointerException e) {
                    onCaughtWrongResponse(433);
                    return;
                }
            }
            String eSignResponse = data.getStringExtra("signedResponse");
            Intrinsics.checkExpressionValueIsNotNull(eSignResponse, "data!!.getStringExtra(\"signedResponse\")");
            resultIntent.putExtra("signedResponse", eSignResponse);
            String str = this.ESIGN_CONSOLE;
            Log.d(str, "Response " + eSignResponse);
            setResult(-1, resultIntent);
            finish();
        } else if (requestCode == 10001) {
            if (data == null) {
                try {
                    Intrinsics.throwNpe();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            String eSignResponse2 = data.getStringExtra("signedResponse");
            String esign_console = Constants.Companion.getESIGN_CONSOLE();
            Log.e(esign_console, "esign response \n" + eSignResponse2);
        }
    }
}
