package com.surepass.surepassesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
/* compiled from: UserDetails.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010d\u001a\u00020eH\u0002J\b\u0010f\u001a\u00020eH\u0002J\"\u0010g\u001a\u00020e2\u0006\u0010h\u001a\u00020\u00192\u0006\u0010i\u001a\u00020\u00192\b\u0010j\u001a\u0004\u0018\u00010>H\u0014J\b\u0010k\u001a\u00020eH\u0016J\u0014\u0010l\u001a\u00020e2\n\u0010m\u001a\u00060nj\u0002`oH\u0002J\u0010\u0010p\u001a\u00020e2\u0006\u0010q\u001a\u00020\u0019H\u0002J\u0012\u0010r\u001a\u00020e2\b\u0010s\u001a\u0004\u0018\u00010tH\u0014J\b\u0010u\u001a\u00020eH\u0014J\u0006\u0010v\u001a\u00020eJ\u0006\u0010w\u001a\u00020eJ\u0006\u0010x\u001a\u00020eJ\b\u0010y\u001a\u00020eH\u0002J\b\u0010z\u001a\u00020eH\u0002J\b\u0010{\u001a\u00020eH\u0002J\b\u0010|\u001a\u00020eH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u00020\u0019X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\bR\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0006\"\u0004\b-\u0010\bR\u001a\u0010.\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0006\"\u0004\b0\u0010\bR\u001a\u00101\u001a\u000202X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u000208X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u00020>X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u001a\u0010C\u001a\u00020DX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u0010I\u001a\u00020>X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010@\"\u0004\bK\u0010BR\u001a\u0010L\u001a\u00020MX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020SX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001a\u0010X\u001a\u00020>X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010@\"\u0004\bZ\u0010BR\u001a\u0010[\u001a\u00020>X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010@\"\u0004\b]\u0010BR\u001a\u0010^\u001a\u00020_X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b`\u0010a\"\u0004\bb\u0010c¨\u0006}"}, d2 = {"Lcom/surepass/surepassesign/UserDetails;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "BASEURL", "", "getBASEURL", "()Ljava/lang/String;", "setBASEURL", "(Ljava/lang/String;)V", "BRAND_LOGO_URL", "getBRAND_LOGO_URL", "setBRAND_LOGO_URL", "BRAND_NAME", "getBRAND_NAME", "setBRAND_NAME", "CLIENT_EMAIL", "getCLIENT_EMAIL", "setCLIENT_EMAIL", "CLIENT_FULL_NAME", "getCLIENT_FULL_NAME", "setCLIENT_FULL_NAME", "CLIENT_PHONE_NUMBER", "getCLIENT_PHONE_NUMBER", "setCLIENT_PHONE_NUMBER", "REQUEST_CODE_FOR_VERIFICATION_RESULT", "", "getREQUEST_CODE_FOR_VERIFICATION_RESULT", "()I", "SKIP_OTP", "", "getSKIP_OTP", "()Z", "setSKIP_OTP", "(Z)V", "TAG", "getTAG", "setTAG", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "clientEmail", "getClientEmail", "setClientEmail", "clientName", "getClientName", "setClientName", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "setEditor", "(Landroid/content/SharedPreferences$Editor;)V", "phoneNumberIntent", "Landroid/content/Intent;", "getPhoneNumberIntent", "()Landroid/content/Intent;", "setPhoneNumberIntent", "(Landroid/content/Intent;)V", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "selfieIntent", "getSelfieIntent", "setSelfieIntent", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "uploadDocs", "getUploadDocs", "setUploadDocs", "virtualSignIntent", "getVirtualSignIntent", "setVirtualSignIntent", "warningDialog", "Lcom/surepass/surepassesign/WarningDialog;", "getWarningDialog", "()Lcom/surepass/surepassesign/WarningDialog;", "setWarningDialog", "(Lcom/surepass/surepassesign/WarningDialog;)V", "getBranding", "", "getPrefillOptions", "onActivityResult", "requestCode", "resultCode", UriUtil.DATA_SCHEME, "onBackPressed", "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "setAppLogo", "setAppName", "setDefaultLogo", "startPhoneNumberActivity", "startSelfieActivity", "startUploadingActivity", "startVirtulSignActivity", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class UserDetails extends AppCompatActivity {
    private String BASEURL;
    private String BRAND_LOGO_URL;
    private String BRAND_NAME;
    private String CLIENT_EMAIL;
    private String CLIENT_FULL_NAME;
    private String CLIENT_PHONE_NUMBER;
    private boolean SKIP_OTP;
    private HashMap _$_findViewCache;
    public OkHttpClient client;
    public String clientEmail;
    public String clientName;
    public Dialog dialog;
    public SharedPreferences.Editor editor;
    public Intent phoneNumberIntent;
    public OkHttpRequest request;
    public Intent selfieIntent;
    public SharedPreferences sharedPreferences;
    public Store store;
    public Intent uploadDocs;
    public Intent virtualSignIntent;
    public WarningDialog warningDialog;
    private String TAG = "UserDetails";
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

    public final void setTAG(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.TAG = str;
    }

    public final String getClientEmail() {
        String str = this.clientEmail;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientEmail");
        }
        return str;
    }

    public final void setClientEmail(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.clientEmail = str;
    }

    public final String getClientName() {
        String str = this.clientName;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientName");
        }
        return str;
    }

    public final void setClientName(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.clientName = str;
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

    public final String getBRAND_LOGO_URL() {
        return this.BRAND_LOGO_URL;
    }

    public final void setBRAND_LOGO_URL(String str) {
        this.BRAND_LOGO_URL = str;
    }

    public final String getBRAND_NAME() {
        return this.BRAND_NAME;
    }

    public final void setBRAND_NAME(String str) {
        this.BRAND_NAME = str;
    }

    public final String getCLIENT_FULL_NAME() {
        return this.CLIENT_FULL_NAME;
    }

    public final void setCLIENT_FULL_NAME(String str) {
        this.CLIENT_FULL_NAME = str;
    }

    public final String getCLIENT_EMAIL() {
        return this.CLIENT_EMAIL;
    }

    public final void setCLIENT_EMAIL(String str) {
        this.CLIENT_EMAIL = str;
    }

    public final String getCLIENT_PHONE_NUMBER() {
        return this.CLIENT_PHONE_NUMBER;
    }

    public final void setCLIENT_PHONE_NUMBER(String str) {
        this.CLIENT_PHONE_NUMBER = str;
    }

    public final boolean getSKIP_OTP() {
        return this.SKIP_OTP;
    }

    public final void setSKIP_OTP(boolean z) {
        this.SKIP_OTP = z;
    }

    public final SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences;
    }

    public final void setSharedPreferences(SharedPreferences sharedPreferences) {
        Intrinsics.checkParameterIsNotNull(sharedPreferences, "<set-?>");
        this.sharedPreferences = sharedPreferences;
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

    public final WarningDialog getWarningDialog() {
        WarningDialog warningDialog = this.warningDialog;
        if (warningDialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("warningDialog");
        }
        return warningDialog;
    }

    public final void setWarningDialog(WarningDialog warningDialog) {
        Intrinsics.checkParameterIsNotNull(warningDialog, "<set-?>");
        this.warningDialog = warningDialog;
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

    public final String getBASEURL() {
        return this.BASEURL;
    }

    public final void setBASEURL(String str) {
        this.BASEURL = str;
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x00de A[Catch: Exception -> 0x0168, TryCatch #0 {Exception -> 0x0168, blocks: (B:6:0x0071, B:8:0x0075, B:9:0x0078, B:11:0x0080, B:12:0x0083, B:14:0x00d2, B:20:0x00de, B:21:0x00e2, B:23:0x00e8, B:29:0x00f4, B:30:0x00f8, B:31:0x00fb, B:33:0x0102, B:39:0x010e, B:41:0x0114, B:47:0x0120, B:48:0x015f), top: B:59:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00e2 A[Catch: Exception -> 0x0168, TryCatch #0 {Exception -> 0x0168, blocks: (B:6:0x0071, B:8:0x0075, B:9:0x0078, B:11:0x0080, B:12:0x0083, B:14:0x00d2, B:20:0x00de, B:21:0x00e2, B:23:0x00e8, B:29:0x00f4, B:30:0x00f8, B:31:0x00fb, B:33:0x0102, B:39:0x010e, B:41:0x0114, B:47:0x0120, B:48:0x015f), top: B:59:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f4 A[Catch: Exception -> 0x0168, TryCatch #0 {Exception -> 0x0168, blocks: (B:6:0x0071, B:8:0x0075, B:9:0x0078, B:11:0x0080, B:12:0x0083, B:14:0x00d2, B:20:0x00de, B:21:0x00e2, B:23:0x00e8, B:29:0x00f4, B:30:0x00f8, B:31:0x00fb, B:33:0x0102, B:39:0x010e, B:41:0x0114, B:47:0x0120, B:48:0x015f), top: B:59:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00f8 A[Catch: Exception -> 0x0168, TryCatch #0 {Exception -> 0x0168, blocks: (B:6:0x0071, B:8:0x0075, B:9:0x0078, B:11:0x0080, B:12:0x0083, B:14:0x00d2, B:20:0x00de, B:21:0x00e2, B:23:0x00e8, B:29:0x00f4, B:30:0x00f8, B:31:0x00fb, B:33:0x0102, B:39:0x010e, B:41:0x0114, B:47:0x0120, B:48:0x015f), top: B:59:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x010e A[Catch: Exception -> 0x0168, TryCatch #0 {Exception -> 0x0168, blocks: (B:6:0x0071, B:8:0x0075, B:9:0x0078, B:11:0x0080, B:12:0x0083, B:14:0x00d2, B:20:0x00de, B:21:0x00e2, B:23:0x00e8, B:29:0x00f4, B:30:0x00f8, B:31:0x00fb, B:33:0x0102, B:39:0x010e, B:41:0x0114, B:47:0x0120, B:48:0x015f), top: B:59:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0120 A[Catch: Exception -> 0x0168, TryCatch #0 {Exception -> 0x0168, blocks: (B:6:0x0071, B:8:0x0075, B:9:0x0078, B:11:0x0080, B:12:0x0083, B:14:0x00d2, B:20:0x00de, B:21:0x00e2, B:23:0x00e8, B:29:0x00f4, B:30:0x00f8, B:31:0x00fb, B:33:0x0102, B:39:0x010e, B:41:0x0114, B:47:0x0120, B:48:0x015f), top: B:59:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0191  */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void onCreate(Bundle savedInstanceState) {
        Store store;
        OkHttpClient okHttpClient;
        String str;
        boolean z;
        String str2;
        boolean z2;
        boolean z3;
        boolean z4;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.Companion.getMY_PREFS_NAME(), 0);
        Intrinsics.checkExpressionValueIsNotNull(sharedPreferences, "getSharedPreferences(MY_…ME, Context.MODE_PRIVATE)");
        this.sharedPreferences = sharedPreferences;
        SharedPreferences sharedPreferences2 = this.sharedPreferences;
        if (sharedPreferences2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        SharedPreferences.Editor edit = sharedPreferences2.edit();
        Intrinsics.checkExpressionValueIsNotNull(edit, "sharedPreferences.edit()");
        this.editor = edit;
        this.warningDialog = new WarningDialog(this, new Function0<Unit>() { // from class: com.surepass.surepassesign.UserDetails$onCreate$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2() {
                UserDetails.this.finish();
            }
        });
        this.uploadDocs = new Intent(this, UploadDocs.class);
        this.selfieIntent = new Intent(this, SelfieActivity.class);
        this.virtualSignIntent = new Intent(this, SignatureActivity.class);
        this.store = new Store(this);
        try {
            Store store2 = this.store;
            if (store2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String config = store2.getConfig();
            Store store3 = this.store;
            if (store3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String brandDetails = store3.getBrandDetails();
            JSONObject configObj = new JSONObject(config);
            this.SKIP_OTP = configObj.getJSONObject("config").getBoolean("skip_otp");
            this.CLIENT_FULL_NAME = configObj.getString("full_name");
            this.CLIENT_EMAIL = configObj.getString("user_email");
            this.CLIENT_PHONE_NUMBER = configObj.getString("mobile_number");
            this.BRAND_LOGO_URL = new JSONObject(brandDetails).getString("brand_image_url");
            this.BRAND_NAME = new JSONObject(brandDetails).getString("brand_name");
            str = this.BRAND_LOGO_URL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!(str == null || str.length() == 0)) {
            z = false;
            if (z) {
                setAppLogo();
            } else {
                String str3 = this.BRAND_NAME;
                if (!(str3 == null || str3.length() == 0)) {
                    z4 = false;
                    if (z4) {
                        setAppName();
                    } else {
                        setDefaultLogo();
                    }
                }
                z4 = true;
                if (z4) {
                }
            }
            str2 = this.CLIENT_EMAIL;
            if (!(str2 == null || str2.length() == 0)) {
                z2 = false;
                if (!z2) {
                    String str4 = this.CLIENT_FULL_NAME;
                    if (!(str4 == null || str4.length() == 0)) {
                        z3 = false;
                        if (!z3) {
                            ((EditText) _$_findCachedViewById(R.id.Name)).setText(this.CLIENT_FULL_NAME);
                            ((EditText) _$_findCachedViewById(R.id.Email)).setText(this.CLIENT_EMAIL);
                            EditText editText = (EditText) _$_findCachedViewById(R.id.Name);
                            Intrinsics.checkExpressionValueIsNotNull(editText, "Name");
                            editText.setEnabled(false);
                            EditText editText2 = (EditText) _$_findCachedViewById(R.id.Email);
                            Intrinsics.checkExpressionValueIsNotNull(editText2, "Email");
                            editText2.setEnabled(false);
                            store = this.store;
                            if (store == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("store");
                            }
                            this.BASEURL = store.getBaseUrl();
                            this.dialog = new Dialog(this);
                            this.client = new OkHttpClient();
                            okHttpClient = this.client;
                            if (okHttpClient == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("client");
                            }
                            this.request = new OkHttpRequest(okHttpClient, this);
                            Button button = (Button) _$_findCachedViewById(R.id.nextButton);
                            Intrinsics.checkExpressionValueIsNotNull(button, "nextButton");
                            button.setEnabled(true);
                            ((Button) _$_findCachedViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UserDetails$onCreate$2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View it) {
                                    UserDetails userDetails = UserDetails.this;
                                    EditText editText3 = (EditText) userDetails._$_findCachedViewById(R.id.Name);
                                    Intrinsics.checkExpressionValueIsNotNull(editText3, "Name");
                                    userDetails.setClientName(editText3.getText().toString());
                                    UserDetails userDetails2 = UserDetails.this;
                                    EditText editText4 = (EditText) userDetails2._$_findCachedViewById(R.id.Email);
                                    Intrinsics.checkExpressionValueIsNotNull(editText4, "Email");
                                    userDetails2.setClientEmail(editText4.getText().toString());
                                    String clientEmail = UserDetails.this.getClientEmail();
                                    boolean z5 = false;
                                    if (!(clientEmail == null || clientEmail.length() == 0)) {
                                        String clientName = UserDetails.this.getClientName();
                                        if (clientName == null || clientName.length() == 0) {
                                            z5 = true;
                                        }
                                        if (!z5) {
                                            try {
                                                String config2 = UserDetails.this.getStore().getConfig();
                                                JSONObject configObj2 = new JSONObject(config2).getJSONObject("config");
                                                Log.e(UserDetails.this.getTAG(), "configObj " + String.valueOf(config2));
                                                boolean skipOtp = configObj2.getBoolean("skip_otp");
                                                boolean acceptSelfie = configObj2.getBoolean("accept_selfie");
                                                boolean acceptVirtualSign = configObj2.getBoolean("accept_virtual_sign");
                                                if (!skipOtp) {
                                                    UserDetails.this.startPhoneNumberActivity();
                                                    return;
                                                } else if (acceptSelfie) {
                                                    UserDetails.this.startSelfieActivity();
                                                    return;
                                                } else if (acceptVirtualSign) {
                                                    UserDetails.this.startVirtulSignActivity();
                                                    return;
                                                } else {
                                                    UserDetails.this.startUploadingActivity();
                                                    return;
                                                }
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                                return;
                                            }
                                        }
                                    }
                                    Toast.makeText(UserDetails.this, "*All fields are required", 1).show();
                                }
                            });
                        }
                    }
                    z3 = true;
                    if (!z3) {
                    }
                }
                Log.e(this.TAG, "else");
                store = this.store;
                if (store == null) {
                }
                this.BASEURL = store.getBaseUrl();
                this.dialog = new Dialog(this);
                this.client = new OkHttpClient();
                okHttpClient = this.client;
                if (okHttpClient == null) {
                }
                this.request = new OkHttpRequest(okHttpClient, this);
                Button button2 = (Button) _$_findCachedViewById(R.id.nextButton);
                Intrinsics.checkExpressionValueIsNotNull(button2, "nextButton");
                button2.setEnabled(true);
                ((Button) _$_findCachedViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UserDetails$onCreate$2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View it) {
                        UserDetails userDetails = UserDetails.this;
                        EditText editText3 = (EditText) userDetails._$_findCachedViewById(R.id.Name);
                        Intrinsics.checkExpressionValueIsNotNull(editText3, "Name");
                        userDetails.setClientName(editText3.getText().toString());
                        UserDetails userDetails2 = UserDetails.this;
                        EditText editText4 = (EditText) userDetails2._$_findCachedViewById(R.id.Email);
                        Intrinsics.checkExpressionValueIsNotNull(editText4, "Email");
                        userDetails2.setClientEmail(editText4.getText().toString());
                        String clientEmail = UserDetails.this.getClientEmail();
                        boolean z5 = false;
                        if (!(clientEmail == null || clientEmail.length() == 0)) {
                            String clientName = UserDetails.this.getClientName();
                            if (clientName == null || clientName.length() == 0) {
                                z5 = true;
                            }
                            if (!z5) {
                                try {
                                    String config2 = UserDetails.this.getStore().getConfig();
                                    JSONObject configObj2 = new JSONObject(config2).getJSONObject("config");
                                    Log.e(UserDetails.this.getTAG(), "configObj " + String.valueOf(config2));
                                    boolean skipOtp = configObj2.getBoolean("skip_otp");
                                    boolean acceptSelfie = configObj2.getBoolean("accept_selfie");
                                    boolean acceptVirtualSign = configObj2.getBoolean("accept_virtual_sign");
                                    if (!skipOtp) {
                                        UserDetails.this.startPhoneNumberActivity();
                                        return;
                                    } else if (acceptSelfie) {
                                        UserDetails.this.startSelfieActivity();
                                        return;
                                    } else if (acceptVirtualSign) {
                                        UserDetails.this.startVirtulSignActivity();
                                        return;
                                    } else {
                                        UserDetails.this.startUploadingActivity();
                                        return;
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            }
                        }
                        Toast.makeText(UserDetails.this, "*All fields are required", 1).show();
                    }
                });
            }
            z2 = true;
            if (!z2) {
            }
            Log.e(this.TAG, "else");
            store = this.store;
            if (store == null) {
            }
            this.BASEURL = store.getBaseUrl();
            this.dialog = new Dialog(this);
            this.client = new OkHttpClient();
            okHttpClient = this.client;
            if (okHttpClient == null) {
            }
            this.request = new OkHttpRequest(okHttpClient, this);
            Button button22 = (Button) _$_findCachedViewById(R.id.nextButton);
            Intrinsics.checkExpressionValueIsNotNull(button22, "nextButton");
            button22.setEnabled(true);
            ((Button) _$_findCachedViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UserDetails$onCreate$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View it) {
                    UserDetails userDetails = UserDetails.this;
                    EditText editText3 = (EditText) userDetails._$_findCachedViewById(R.id.Name);
                    Intrinsics.checkExpressionValueIsNotNull(editText3, "Name");
                    userDetails.setClientName(editText3.getText().toString());
                    UserDetails userDetails2 = UserDetails.this;
                    EditText editText4 = (EditText) userDetails2._$_findCachedViewById(R.id.Email);
                    Intrinsics.checkExpressionValueIsNotNull(editText4, "Email");
                    userDetails2.setClientEmail(editText4.getText().toString());
                    String clientEmail = UserDetails.this.getClientEmail();
                    boolean z5 = false;
                    if (!(clientEmail == null || clientEmail.length() == 0)) {
                        String clientName = UserDetails.this.getClientName();
                        if (clientName == null || clientName.length() == 0) {
                            z5 = true;
                        }
                        if (!z5) {
                            try {
                                String config2 = UserDetails.this.getStore().getConfig();
                                JSONObject configObj2 = new JSONObject(config2).getJSONObject("config");
                                Log.e(UserDetails.this.getTAG(), "configObj " + String.valueOf(config2));
                                boolean skipOtp = configObj2.getBoolean("skip_otp");
                                boolean acceptSelfie = configObj2.getBoolean("accept_selfie");
                                boolean acceptVirtualSign = configObj2.getBoolean("accept_virtual_sign");
                                if (!skipOtp) {
                                    UserDetails.this.startPhoneNumberActivity();
                                    return;
                                } else if (acceptSelfie) {
                                    UserDetails.this.startSelfieActivity();
                                    return;
                                } else if (acceptVirtualSign) {
                                    UserDetails.this.startVirtulSignActivity();
                                    return;
                                } else {
                                    UserDetails.this.startUploadingActivity();
                                    return;
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                    }
                    Toast.makeText(UserDetails.this, "*All fields are required", 1).show();
                }
            });
        }
        z = true;
        if (z) {
        }
        str2 = this.CLIENT_EMAIL;
        if (str2 == null) {
            z2 = false;
            if (!z2) {
            }
            Log.e(this.TAG, "else");
            store = this.store;
            if (store == null) {
            }
            this.BASEURL = store.getBaseUrl();
            this.dialog = new Dialog(this);
            this.client = new OkHttpClient();
            okHttpClient = this.client;
            if (okHttpClient == null) {
            }
            this.request = new OkHttpRequest(okHttpClient, this);
            Button button222 = (Button) _$_findCachedViewById(R.id.nextButton);
            Intrinsics.checkExpressionValueIsNotNull(button222, "nextButton");
            button222.setEnabled(true);
            ((Button) _$_findCachedViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UserDetails$onCreate$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View it) {
                    UserDetails userDetails = UserDetails.this;
                    EditText editText3 = (EditText) userDetails._$_findCachedViewById(R.id.Name);
                    Intrinsics.checkExpressionValueIsNotNull(editText3, "Name");
                    userDetails.setClientName(editText3.getText().toString());
                    UserDetails userDetails2 = UserDetails.this;
                    EditText editText4 = (EditText) userDetails2._$_findCachedViewById(R.id.Email);
                    Intrinsics.checkExpressionValueIsNotNull(editText4, "Email");
                    userDetails2.setClientEmail(editText4.getText().toString());
                    String clientEmail = UserDetails.this.getClientEmail();
                    boolean z5 = false;
                    if (!(clientEmail == null || clientEmail.length() == 0)) {
                        String clientName = UserDetails.this.getClientName();
                        if (clientName == null || clientName.length() == 0) {
                            z5 = true;
                        }
                        if (!z5) {
                            try {
                                String config2 = UserDetails.this.getStore().getConfig();
                                JSONObject configObj2 = new JSONObject(config2).getJSONObject("config");
                                Log.e(UserDetails.this.getTAG(), "configObj " + String.valueOf(config2));
                                boolean skipOtp = configObj2.getBoolean("skip_otp");
                                boolean acceptSelfie = configObj2.getBoolean("accept_selfie");
                                boolean acceptVirtualSign = configObj2.getBoolean("accept_virtual_sign");
                                if (!skipOtp) {
                                    UserDetails.this.startPhoneNumberActivity();
                                    return;
                                } else if (acceptSelfie) {
                                    UserDetails.this.startSelfieActivity();
                                    return;
                                } else if (acceptVirtualSign) {
                                    UserDetails.this.startVirtulSignActivity();
                                    return;
                                } else {
                                    UserDetails.this.startUploadingActivity();
                                    return;
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                    }
                    Toast.makeText(UserDetails.this, "*All fields are required", 1).show();
                }
            });
        }
        z2 = true;
        if (!z2) {
        }
        Log.e(this.TAG, "else");
        store = this.store;
        if (store == null) {
        }
        this.BASEURL = store.getBaseUrl();
        this.dialog = new Dialog(this);
        this.client = new OkHttpClient();
        okHttpClient = this.client;
        if (okHttpClient == null) {
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        Button button2222 = (Button) _$_findCachedViewById(R.id.nextButton);
        Intrinsics.checkExpressionValueIsNotNull(button2222, "nextButton");
        button2222.setEnabled(true);
        ((Button) _$_findCachedViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UserDetails$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                UserDetails userDetails = UserDetails.this;
                EditText editText3 = (EditText) userDetails._$_findCachedViewById(R.id.Name);
                Intrinsics.checkExpressionValueIsNotNull(editText3, "Name");
                userDetails.setClientName(editText3.getText().toString());
                UserDetails userDetails2 = UserDetails.this;
                EditText editText4 = (EditText) userDetails2._$_findCachedViewById(R.id.Email);
                Intrinsics.checkExpressionValueIsNotNull(editText4, "Email");
                userDetails2.setClientEmail(editText4.getText().toString());
                String clientEmail = UserDetails.this.getClientEmail();
                boolean z5 = false;
                if (!(clientEmail == null || clientEmail.length() == 0)) {
                    String clientName = UserDetails.this.getClientName();
                    if (clientName == null || clientName.length() == 0) {
                        z5 = true;
                    }
                    if (!z5) {
                        try {
                            String config2 = UserDetails.this.getStore().getConfig();
                            JSONObject configObj2 = new JSONObject(config2).getJSONObject("config");
                            Log.e(UserDetails.this.getTAG(), "configObj " + String.valueOf(config2));
                            boolean skipOtp = configObj2.getBoolean("skip_otp");
                            boolean acceptSelfie = configObj2.getBoolean("accept_selfie");
                            boolean acceptVirtualSign = configObj2.getBoolean("accept_virtual_sign");
                            if (!skipOtp) {
                                UserDetails.this.startPhoneNumberActivity();
                                return;
                            } else if (acceptSelfie) {
                                UserDetails.this.startSelfieActivity();
                                return;
                            } else if (acceptVirtualSign) {
                                UserDetails.this.startVirtulSignActivity();
                                return;
                            } else {
                                UserDetails.this.startUploadingActivity();
                                return;
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                }
                Toast.makeText(UserDetails.this, "*All fields are required", 1).show();
            }
        });
    }

    public final void startPhoneNumberActivity() {
        this.phoneNumberIntent = new Intent(this, PhoneNumber.class);
        Intent intent = this.phoneNumberIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneNumberIntent");
        }
        String str = this.clientName;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientName");
        }
        intent.putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, str);
        Intent intent2 = this.phoneNumberIntent;
        if (intent2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneNumberIntent");
        }
        String str2 = this.clientEmail;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientEmail");
        }
        intent2.putExtra("email", str2);
        Intent intent3 = this.phoneNumberIntent;
        if (intent3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneNumberIntent");
        }
        intent3.putExtra("mobile_number", this.CLIENT_PHONE_NUMBER);
        Intent intent4 = this.phoneNumberIntent;
        if (intent4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneNumberIntent");
        }
        startActivityForResult(intent4, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    public final void startUploadingActivity() {
        Intent intent = this.uploadDocs;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadDocs");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    public final void startSelfieActivity() {
        Intent intent = this.selfieIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selfieIntent");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    public final void startVirtulSignActivity() {
        Intent intent = this.virtualSignIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("virtualSignIntent");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    public final void onCaughtFailureException(Exception e) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("signedResponse", new Constants().getJsonResponse(500, e.getMessage()).toString());
        setResult(-1, resultIntent);
        finish();
    }

    public final void onCaughtWrongResponse(int statusCode) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("signedResponse", new Constants().getJsonResponse(statusCode).toString());
        setResult(-1, resultIntent);
        finish();
    }

    private final void getPrefillOptions() {
        String url = Intrinsics.stringPlus(this.BASEURL, "get-options");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.GET_FOR_ESIGN(url, new UserDetails$getPrefillOptions$1(this));
    }

    private final void getBranding() {
        String url = Intrinsics.stringPlus(this.BASEURL, "get-branding");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.GET_FOR_ESIGN(url, new UserDetails$getBranding$1(this));
    }

    public final void setAppLogo() {
        Picasso.get().load(this.BRAND_LOGO_URL).into((ImageView) _$_findCachedViewById(R.id.userDetailslogo));
    }

    public final void setDefaultLogo() {
        Picasso.get().load(R.drawable.surepass);
    }

    public final void setAppName() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R.id.userDetailslogo);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "userDetailslogo");
        imageView.setVisibility(8);
        TextView textView = (TextView) _$_findCachedViewById(R.id.headerTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView, "headerTitle");
        textView.setVisibility(0);
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.headerTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView2, "headerTitle");
        textView2.setText(this.BRAND_NAME);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            if (eSignResponse == null) {
                Intrinsics.throwNpe();
            }
            resultIntent.putExtra("signedResponse", eSignResponse);
            setResult(-1, resultIntent);
            finish();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        WarningDialog warningDialog = this.warningDialog;
        if (warningDialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("warningDialog");
        }
        warningDialog.showDialog();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }
}
