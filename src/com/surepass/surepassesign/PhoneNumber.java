package com.surepass.surepassesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: PhoneNumber.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020\u00112\u0006\u0010X\u001a\u00020\u00112\b\u0010Y\u001a\u0004\u0018\u000108H\u0014J\b\u0010Z\u001a\u00020VH\u0016J\u0012\u0010[\u001a\u00020V2\n\u0010\\\u001a\u00060]j\u0002`^J\u000e\u0010_\u001a\u00020V2\u0006\u0010`\u001a\u00020\u0011J\u0012\u0010a\u001a\u00020V2\b\u0010b\u001a\u0004\u0018\u00010cH\u0014J\u0006\u0010d\u001a\u00020VJ\u000e\u0010e\u001a\u00020V2\u0006\u0010f\u001a\u00020gJ \u0010h\u001a\u00020V2\u0006\u0010f\u001a\u00020g2\u0006\u0010i\u001a\u00020\u00042\b\u0010j\u001a\u0004\u0018\u00010\u0004J\u0010\u0010k\u001a\u00020V2\b\u0010l\u001a\u0004\u0018\u00010\u0004J\u0006\u0010m\u001a\u00020VJ\u0006\u0010n\u001a\u00020VJ\u0006\u0010o\u001a\u00020VJ\b\u0010p\u001a\u00020VH\u0002J\b\u0010q\u001a\u00020VH\u0002J\b\u0010r\u001a\u00020VH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u0011X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0006R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0006\"\u0004\b\u001e\u0010\bR\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0006\"\u0004\b!\u0010\bR\u001c\u0010\"\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\bR\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u000202X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u000208X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u00020>X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u001a\u0010C\u001a\u00020DX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u0010I\u001a\u000208X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010:\"\u0004\bK\u0010<R\u001a\u0010L\u001a\u000208X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010:\"\u0004\bN\u0010<R\u001a\u0010O\u001a\u00020PX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010T¨\u0006s"}, d2 = {"Lcom/surepass/surepassesign/PhoneNumber;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "BASEURL", "", "getBASEURL", "()Ljava/lang/String;", "setBASEURL", "(Ljava/lang/String;)V", "BRAND_LOGO_URL", "getBRAND_LOGO_URL", "setBRAND_LOGO_URL", "BRAND_NAME", "getBRAND_NAME", "setBRAND_NAME", "ESIGN_CONSOLE", "REQUEST_CODE_FOR_VERIFICATION_RESULT", "", "getREQUEST_CODE_FOR_VERIFICATION_RESULT", "()I", "TAG", "getTAG", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "clientEmail", "getClientEmail", "setClientEmail", "clientName", "getClientName", "setClientName", "clientNumber", "getClientNumber", "setClientNumber", "configObj", "Lorg/json/JSONObject;", "getConfigObj", "()Lorg/json/JSONObject;", "setConfigObj", "(Lorg/json/JSONObject;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "selfieIntent", "Landroid/content/Intent;", "getSelfieIntent", "()Landroid/content/Intent;", "setSelfieIntent", "(Landroid/content/Intent;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "uploadDocs", "getUploadDocs", "setUploadDocs", "virtualSignIntent", "getVirtualSignIntent", "setVirtualSignIntent", "warningDialog", "Lcom/surepass/surepassesign/WarningDialog;", "getWarningDialog", "()Lcom/surepass/surepassesign/WarningDialog;", "setWarningDialog", "(Lcom/surepass/surepassesign/WarningDialog;)V", "onActivityResult", "", "requestCode", "resultCode", UriUtil.DATA_SCHEME, "onBackPressed", "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openDocUploadActivity", "removeFragment", "fragment", "Landroidx/fragment/app/Fragment;", "replaceFragment", "tag", "phone", "sendOtp", "mobile", "setAppLogo", "setAppName", "setDefaultLogo", "startSelfieActivity", "startUploadingActivity", "startVirtulSignActivity", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class PhoneNumber extends AppCompatActivity {
    private String BASEURL;
    private String BRAND_LOGO_URL;
    private String BRAND_NAME;
    private HashMap _$_findViewCache;
    public OkHttpClient client;
    private String clientEmail;
    private String clientName;
    private String clientNumber;
    public JSONObject configObj;
    public Dialog dialog;
    public OkHttpRequest request;
    public Intent selfieIntent;
    public SharedPreferences sharedPreferences;
    public Store store;
    public Intent uploadDocs;
    public Intent virtualSignIntent;
    public WarningDialog warningDialog;
    private final String TAG = "PhoneNumber";
    private final int REQUEST_CODE_FOR_VERIFICATION_RESULT = CameraAccessExceptionCompat.CAMERA_UNAVAILABLE_DO_NOT_DISTURB;
    private final String ESIGN_CONSOLE = "ESIGN_CONSOLE";

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

    public final String getClientEmail() {
        return this.clientEmail;
    }

    public final void setClientEmail(String str) {
        this.clientEmail = str;
    }

    public final String getClientName() {
        return this.clientName;
    }

    public final void setClientName(String str) {
        this.clientName = str;
    }

    public final String getClientNumber() {
        return this.clientNumber;
    }

    public final void setClientNumber(String str) {
        this.clientNumber = str;
    }

    public final String getTAG() {
        return this.TAG;
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

    public final int getREQUEST_CODE_FOR_VERIFICATION_RESULT() {
        return this.REQUEST_CODE_FOR_VERIFICATION_RESULT;
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

    public final JSONObject getConfigObj() {
        JSONObject jSONObject = this.configObj;
        if (jSONObject == null) {
            Intrinsics.throwUninitializedPropertyAccessException("configObj");
        }
        return jSONObject;
    }

    public final void setConfigObj(JSONObject jSONObject) {
        Intrinsics.checkParameterIsNotNull(jSONObject, "<set-?>");
        this.configObj = jSONObject;
    }

    public final String getBASEURL() {
        return this.BASEURL;
    }

    public final void setBASEURL(String str) {
        this.BASEURL = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0095 A[Catch: Exception -> 0x00b3, TryCatch #2 {Exception -> 0x00b3, blocks: (B:3:0x005e, B:5:0x0062, B:6:0x0065, B:8:0x0089, B:14:0x0095, B:15:0x0099, B:17:0x009f, B:23:0x00ab, B:24:0x00af), top: B:73:0x005e }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0099 A[Catch: Exception -> 0x00b3, TryCatch #2 {Exception -> 0x00b3, blocks: (B:3:0x005e, B:5:0x0062, B:6:0x0065, B:8:0x0089, B:14:0x0095, B:15:0x0099, B:17:0x009f, B:23:0x00ab, B:24:0x00af), top: B:73:0x005e }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ab A[Catch: Exception -> 0x00b3, TryCatch #2 {Exception -> 0x00b3, blocks: (B:3:0x005e, B:5:0x0062, B:6:0x0065, B:8:0x0089, B:14:0x0095, B:15:0x0099, B:17:0x009f, B:23:0x00ab, B:24:0x00af), top: B:73:0x005e }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00af A[Catch: Exception -> 0x00b3, TRY_LEAVE, TryCatch #2 {Exception -> 0x00b3, blocks: (B:3:0x005e, B:5:0x0062, B:6:0x0065, B:8:0x0089, B:14:0x0095, B:15:0x0099, B:17:0x009f, B:23:0x00ab, B:24:0x00af), top: B:73:0x005e }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void onCreate(Bundle savedInstanceState) {
        Store store;
        String str;
        OkHttpClient okHttpClient;
        String str2;
        String str3;
        boolean z;
        boolean z2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        Bundle extras = intent.getExtras();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.Companion.getMY_PREFS_NAME(), 0);
        Intrinsics.checkExpressionValueIsNotNull(sharedPreferences, "getSharedPreferences(MY_…ME, Context.MODE_PRIVATE)");
        this.sharedPreferences = sharedPreferences;
        this.warningDialog = new WarningDialog(this, new Function0<Unit>() { // from class: com.surepass.surepassesign.PhoneNumber$onCreate$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2() {
                PhoneNumber.this.finish();
            }
        });
        this.selfieIntent = new Intent(this, SelfieActivity.class);
        this.virtualSignIntent = new Intent(this, SignatureActivity.class);
        this.store = new Store(this);
        boolean z3 = true;
        try {
            Store store2 = this.store;
            if (store2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String brandDetails = store2.getBrandDetails();
            this.BRAND_LOGO_URL = new JSONObject(brandDetails).getString("brand_image_url");
            this.BRAND_NAME = new JSONObject(brandDetails).getString("brand_name");
            str3 = this.BRAND_LOGO_URL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!(str3 == null || str3.length() == 0)) {
            z = false;
            if (z) {
                setAppLogo();
            } else {
                String str4 = this.BRAND_NAME;
                if (!(str4 == null || str4.length() == 0)) {
                    z2 = false;
                    if (z2) {
                        setAppName();
                    } else {
                        setDefaultLogo();
                    }
                }
                z2 = true;
                if (z2) {
                }
            }
            store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            this.BASEURL = store.getBaseUrl();
            if (extras == null) {
                try {
                    Intrinsics.throwNpe();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    try {
                        Store store3 = this.store;
                        if (store3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("store");
                        }
                        JSONObject configObj = new JSONObject(store3.getConfig());
                        this.clientName = configObj.getString("full_name");
                        this.clientEmail = configObj.getString("user_email");
                        this.clientNumber = configObj.getString("mobile_number");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            this.clientName = extras.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
            this.clientEmail = extras.getString("email");
            this.clientNumber = extras.getString("mobile_number");
            str = this.clientEmail;
            if (str != null || str.length() == 0) {
                String str5 = this.clientName;
                if (str5 == null || str5.length() == 0) {
                    this.clientEmail = "";
                    this.clientName = "";
                }
            }
            this.dialog = new Dialog(this);
            this.client = new OkHttpClient();
            okHttpClient = this.client;
            if (okHttpClient == null) {
                Intrinsics.throwUninitializedPropertyAccessException("client");
            }
            this.request = new OkHttpRequest(okHttpClient, this);
            WindowManager windowManager = getWindowManager();
            Intrinsics.checkExpressionValueIsNotNull(windowManager, "windowManager");
            ResponsiveDimensions responseDimensions = new ResponsiveDimensions(windowManager);
            TextView textView = (TextView) _$_findCachedViewById(R.id.sliderTitle);
            Intrinsics.checkExpressionValueIsNotNull(textView, "sliderTitle");
            textView.setTextSize(responseDimensions.getResponsiveSize(20));
            TextView textView2 = (TextView) _$_findCachedViewById(R.id.description);
            Intrinsics.checkExpressionValueIsNotNull(textView2, "description");
            textView2.setTextSize(responseDimensions.getResponsiveSize(14));
            str2 = this.clientNumber;
            if (!(str2 == null || str2.length() == 0)) {
                z3 = false;
            }
            if (!z3) {
                ((EditText) _$_findCachedViewById(R.id.phoneNumber)).setText(this.clientNumber);
            }
            ((Button) _$_findCachedViewById(R.id.getOTPButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.PhoneNumber$onCreate$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View it) {
                    EditText editText = (EditText) PhoneNumber.this._$_findCachedViewById(R.id.phoneNumber);
                    Intrinsics.checkExpressionValueIsNotNull(editText, "phoneNumber");
                    String pNumber = editText.getText().toString();
                    if (pNumber.length() == 10 && PhoneNumber.this.getClientEmail() != null && PhoneNumber.this.getClientName() != null) {
                        PhoneNumber.this.sendOtp(pNumber);
                    }
                }
            });
        }
        z = true;
        if (z) {
        }
        store = this.store;
        if (store == null) {
        }
        this.BASEURL = store.getBaseUrl();
        if (extras == null) {
        }
        this.clientName = extras.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
        this.clientEmail = extras.getString("email");
        this.clientNumber = extras.getString("mobile_number");
        str = this.clientEmail;
        if (str != null || str.length() == 0) {
        }
        this.dialog = new Dialog(this);
        this.client = new OkHttpClient();
        okHttpClient = this.client;
        if (okHttpClient == null) {
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        WindowManager windowManager2 = getWindowManager();
        Intrinsics.checkExpressionValueIsNotNull(windowManager2, "windowManager");
        ResponsiveDimensions responseDimensions2 = new ResponsiveDimensions(windowManager2);
        TextView textView3 = (TextView) _$_findCachedViewById(R.id.sliderTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView3, "sliderTitle");
        textView3.setTextSize(responseDimensions2.getResponsiveSize(20));
        TextView textView22 = (TextView) _$_findCachedViewById(R.id.description);
        Intrinsics.checkExpressionValueIsNotNull(textView22, "description");
        textView22.setTextSize(responseDimensions2.getResponsiveSize(14));
        str2 = this.clientNumber;
        if (str2 == null) {
            z3 = false;
        }
        if (!z3) {
        }
        ((Button) _$_findCachedViewById(R.id.getOTPButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.PhoneNumber$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                EditText editText = (EditText) PhoneNumber.this._$_findCachedViewById(R.id.phoneNumber);
                Intrinsics.checkExpressionValueIsNotNull(editText, "phoneNumber");
                String pNumber = editText.getText().toString();
                if (pNumber.length() == 10 && PhoneNumber.this.getClientEmail() != null && PhoneNumber.this.getClientName() != null) {
                    PhoneNumber.this.sendOtp(pNumber);
                }
            }
        });
    }

    public final void setAppLogo() {
        Picasso.get().load(this.BRAND_LOGO_URL).into((ImageView) _$_findCachedViewById(R.id.phoneNumberlogoView));
    }

    public final void setDefaultLogo() {
        Picasso.get().load(R.drawable.surepass);
    }

    public final void setAppName() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R.id.phoneNumberlogoView);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "phoneNumberlogoView");
        imageView.setVisibility(8);
        TextView textView = (TextView) _$_findCachedViewById(R.id.headerTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView, "headerTitle");
        textView.setVisibility(0);
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.headerTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView2, "headerTitle");
        textView2.setText(this.BRAND_NAME);
    }

    public final void sendOtp(String mobile) {
        HashMap map = MapsKt.hashMapOf(TuplesKt.to(AppMeasurementSdk.ConditionalUserProperty.NAME, this.clientName), TuplesKt.to("email", this.clientEmail), TuplesKt.to("mobile_no", mobile));
        String url = Intrinsics.stringPlus(this.BASEURL, "send-sms-otp");
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        dialog.showDialog();
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.POST(url, map, new Callback(mobile) { // from class: com.surepass.surepassesign.PhoneNumber$sendOtp$1
            final /* synthetic */ String $mobile;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.$mobile = $captured_local_variable$1;
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                e.printStackTrace();
                PhoneNumber.this.getDialog().closeDialog();
                PhoneNumber.this.onCaughtFailureException(e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                if (body != null) {
                    body.string();
                }
                if (response.isSuccessful()) {
                    try {
                        PhoneNumber.this.getDialog().closeDialog();
                        PhoneNumber.this.replaceFragment(OTP.Companion.newInstance(), "OTP Fragment", this.$mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        PhoneNumber.this.getDialog().closeDialog();
                        PhoneNumber.this.onCaughtFailureException(e);
                    }
                } else {
                    PhoneNumber.this.getDialog().closeDialog();
                    PhoneNumber.this.onCaughtWrongResponse(response.code());
                }
            }
        });
    }

    public final void openDocUploadActivity() {
        try {
            Store store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String config = store.getConfig();
            JSONObject configObj = new JSONObject(config).getJSONObject("config");
            String str = this.TAG;
            Log.e(str, "configObj " + String.valueOf(config));
            boolean acceptSelfie = configObj.getBoolean("accept_selfie");
            boolean acceptVirtualSign = configObj.getBoolean("accept_virtual_sign");
            if (acceptSelfie) {
                startSelfieActivity();
            } else if (acceptVirtualSign) {
                startVirtulSignActivity();
            } else {
                startUploadingActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void startUploadingActivity() {
        this.uploadDocs = new Intent(this, UploadDocs.class);
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

    public final void replaceFragment(Fragment fragment, String tag, String phone) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Bundle args = new Bundle();
        args.putString("phone", phone);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.phoneNumberLayout, fragment, tag).addToBackStack(null).commit();
    }

    public final void removeFragment(Fragment fragment) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        fragment.setArguments(new Bundle());
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        WarningDialog warningDialog = this.warningDialog;
        if (warningDialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("warningDialog");
        }
        warningDialog.showDialog();
    }

    public final void onCaughtFailureException(Exception e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CODE_FOR_VERIFICATION_RESULT) {
            if (data == null) {
                try {
                    Intrinsics.throwNpe();
                } catch (NullPointerException e) {
                    onCaughtWrongResponse(433);
                    return;
                }
            }
            String eSignResponse = data.getStringExtra("signedResponse");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("signedResponse", eSignResponse);
            setResult(-1, resultIntent);
            finish();
        }
    }
}
