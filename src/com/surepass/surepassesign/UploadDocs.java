package com.surepass.surepassesign;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.nsdl.egov.esignaar.NsdlEsignActivity;
import com.sec.biometric.license.SecBiometricLicenseManager;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: UploadDocs.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020\nH\u0002J\b\u0010t\u001a\u00020rH\u0002J\u0006\u0010u\u001a\u00020rJ\b\u0010v\u001a\u00020rH\u0002J\"\u0010w\u001a\u00020r2\u0006\u0010x\u001a\u00020\u00162\u0006\u0010y\u001a\u00020\u00162\b\u0010z\u001a\u0004\u0018\u00010{H\u0014J\b\u0010|\u001a\u00020rH\u0016J\u0013\u0010}\u001a\u00020r2\u000b\u0010~\u001a\u00070\u007fj\u0003`\u0080\u0001J\u0010\u0010\u0081\u0001\u001a\u00020r2\u0007\u0010\u0082\u0001\u001a\u00020\u0016J\u0015\u0010\u0083\u0001\u001a\u00020r2\n\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0085\u0001H\u0014J3\u0010\u0086\u0001\u001a\u00020r2\u0006\u0010x\u001a\u00020\u00162\u0010\u0010\u0087\u0001\u001a\u000b\u0012\u0006\b\u0001\u0012\u00020\n0\u0088\u00012\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001H\u0016¢\u0006\u0003\u0010\u008b\u0001J\t\u0010\u008c\u0001\u001a\u00020rH\u0002J\u0007\u0010\u008d\u0001\u001a\u00020rJ\u0007\u0010\u008e\u0001\u001a\u00020rJ\u0007\u0010\u008f\u0001\u001a\u00020rJ\u0015\u0010\u0090\u0001\u001a\u00020r2\n\u0010\u0091\u0001\u001a\u0005\u0018\u00010\u0092\u0001H\u0002J\u000f\u0010\u0093\u0001\u001a\u00020r2\u0006\u0010s\u001a\u00020\nJ\t\u0010\u0094\u0001\u001a\u00020rH\u0002J\u0012\u0010\u0095\u0001\u001a\u00020r2\u0007\u0010\u0096\u0001\u001a\u00020\nH\u0002J\u0011\u0010\u0097\u0001\u001a\u00020r2\u0006\u0010s\u001a\u00020\nH\u0002J.\u0010\u0098\u0001\u001a\u00020r2\u0006\u0010s\u001a\u00020\n2\u001b\u0010\u0099\u0001\u001a\u0016\u0012\u0002\b\u0003\u0012\u0002\b\u00030Nj\n\u0012\u0002\b\u0003\u0012\u0002\b\u0003`OH\u0002J\u0012\u0010\u009a\u0001\u001a\u00020r2\u0007\u0010\u009b\u0001\u001a\u00020\nH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u0014\u0010\u0015\u001a\u00020\u0016X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u0016X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0014\u0010\u001b\u001a\u00020\u0016X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u001a\u0010\u001d\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010\u000eR\u001a\u0010 \u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\f\"\u0004\b\"\u0010\u000eR\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u000200X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u00105\u001a\u000206X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001a\u0010;\u001a\u00020<X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010A\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020HX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR.\u0010M\u001a\u0016\u0012\u0002\b\u0003\u0012\u0002\b\u00030Nj\n\u0012\u0002\b\u0003\u0012\u0002\b\u0003`OX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010\u0006\"\u0004\bU\u0010\bR\u001a\u0010V\u001a\u00020WX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u001a\u0010\\\u001a\u00020]X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR\u001a\u0010b\u001a\u00020cX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR\u001a\u0010h\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010\f\"\u0004\bj\u0010\u000eR\u001a\u0010k\u001a\u00020lX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010n\"\u0004\bo\u0010p¨\u0006\u009c\u0001"}, d2 = {"Lcom/surepass/surepassesign/UploadDocs;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "ALLOW_DOWNLOAD", "", "getALLOW_DOWNLOAD", "()Z", "setALLOW_DOWNLOAD", "(Z)V", "BASEURL", "", "getBASEURL", "()Ljava/lang/String;", "setBASEURL", "(Ljava/lang/String;)V", "BRAND_LOGO_URL", "getBRAND_LOGO_URL", "setBRAND_LOGO_URL", "BRAND_NAME", "getBRAND_NAME", "setBRAND_NAME", "REQUEST_CODE_FOR_NSDL", "", "getREQUEST_CODE_FOR_NSDL", "()I", "REQUEST_CODE_FOR_SELECT_PDF", "getREQUEST_CODE_FOR_SELECT_PDF", "REQUEST_CODE_FOR_VERIFICATION_RESULT", "getREQUEST_CODE_FOR_VERIFICATION_RESULT", "SIGN_TYPE", "getSIGN_TYPE", "setSIGN_TYPE", "TAG", "getTAG", "setTAG", "byteArray", "", "getByteArray", "()[B", "setByteArray", "([B)V", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "setEditor", "(Landroid/content/SharedPreferences$Editor;)V", UriUtil.LOCAL_FILE_SCHEME, "Ljava/io/File;", "getFile", "()Ljava/io/File;", "setFile", "(Ljava/io/File;)V", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "setGson", "(Lcom/google/gson/Gson;)V", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "setHandler", "(Landroid/os/Handler;)V", "hashmapOfFields", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getHashmapOfFields", "()Ljava/util/HashMap;", "setHashmapOfFields", "(Ljava/util/HashMap;)V", "isUploaded", "setUploaded", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "urlForUploadingData", "getUrlForUploadingData", "setUrlForUploadingData", "warningDialog", "Lcom/surepass/surepassesign/WarningDialog;", "getWarningDialog", "()Lcom/surepass/surepassesign/WarningDialog;", "setWarningDialog", "(Lcom/surepass/surepassesign/WarningDialog;)V", "getPreUploadedXML", "", ImagesContract.URL, "getSuresign", "getUploadLink", "getXmlRequestPerformed", "onActivityResult", "requestCode", "resultCode", UriUtil.DATA_SCHEME, "Landroid/content/Intent;", "onBackPressed", "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "openFile", "setAppLogo", "setAppName", "setDefaultLogo", "setFileData", "uri", "Landroid/net/Uri;", "showPreuploadedXml", "startFinishActivity", "startNSDLActivity", "xmlString", "startRenderResponseIntent", "uploadPdf", "map", "uploadSignedXml", "eSignResponse", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class UploadDocs extends AppCompatActivity {
    private boolean ALLOW_DOWNLOAD;
    private String BASEURL;
    private String BRAND_LOGO_URL;
    private String BRAND_NAME;
    private HashMap _$_findViewCache;
    private byte[] byteArray;
    public OkHttpClient client;
    public Dialog dialog;
    public SharedPreferences.Editor editor;
    public File file;
    public Handler handler;
    public HashMap<?, ?> hashmapOfFields;
    private boolean isUploaded;
    public OkHttpRequest request;
    public SharedPreferences sharedPreferences;
    public Store store;
    public WarningDialog warningDialog;
    private String TAG = "UploadDocs";
    private Gson gson = new Gson();
    private final int REQUEST_CODE_FOR_VERIFICATION_RESULT = CameraAccessExceptionCompat.CAMERA_UNAVAILABLE_DO_NOT_DISTURB;
    private final int REQUEST_CODE_FOR_NSDL = 100;
    private final int REQUEST_CODE_FOR_SELECT_PDF = 7;
    private String SIGN_TYPE = "";
    private String urlForUploadingData = "";

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

    public final byte[] getByteArray() {
        return this.byteArray;
    }

    public final void setByteArray(byte[] bArr) {
        this.byteArray = bArr;
    }

    public final File getFile() {
        File file = this.file;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException(UriUtil.LOCAL_FILE_SCHEME);
        }
        return file;
    }

    public final void setFile(File file) {
        Intrinsics.checkParameterIsNotNull(file, "<set-?>");
        this.file = file;
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

    public final Gson getGson() {
        return this.gson;
    }

    public final void setGson(Gson gson) {
        Intrinsics.checkParameterIsNotNull(gson, "<set-?>");
        this.gson = gson;
    }

    public final Handler getHandler() {
        Handler handler = this.handler;
        if (handler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("handler");
        }
        return handler;
    }

    public final void setHandler(Handler handler) {
        Intrinsics.checkParameterIsNotNull(handler, "<set-?>");
        this.handler = handler;
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

    public final HashMap<?, ?> getHashmapOfFields() {
        HashMap<?, ?> hashMap = this.hashmapOfFields;
        if (hashMap == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hashmapOfFields");
        }
        return hashMap;
    }

    public final void setHashmapOfFields(HashMap<?, ?> hashMap) {
        Intrinsics.checkParameterIsNotNull(hashMap, "<set-?>");
        this.hashmapOfFields = hashMap;
    }

    public final int getREQUEST_CODE_FOR_VERIFICATION_RESULT() {
        return this.REQUEST_CODE_FOR_VERIFICATION_RESULT;
    }

    public final int getREQUEST_CODE_FOR_NSDL() {
        return this.REQUEST_CODE_FOR_NSDL;
    }

    public final int getREQUEST_CODE_FOR_SELECT_PDF() {
        return this.REQUEST_CODE_FOR_SELECT_PDF;
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

    public final boolean isUploaded() {
        return this.isUploaded;
    }

    public final void setUploaded(boolean z) {
        this.isUploaded = z;
    }

    public final boolean getALLOW_DOWNLOAD() {
        return this.ALLOW_DOWNLOAD;
    }

    public final void setALLOW_DOWNLOAD(boolean z) {
        this.ALLOW_DOWNLOAD = z;
    }

    public final String getSIGN_TYPE() {
        return this.SIGN_TYPE;
    }

    public final void setSIGN_TYPE(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.SIGN_TYPE = str;
    }

    public final String getBASEURL() {
        return this.BASEURL;
    }

    public final void setBASEURL(String str) {
        this.BASEURL = str;
    }

    public final String getUrlForUploadingData() {
        return this.urlForUploadingData;
    }

    public final void setUrlForUploadingData(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.urlForUploadingData = str;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        String str = this.TAG;
        Log.e(str, "bundle " + String.valueOf(savedInstanceState));
        this.handler = new Handler();
        this.client = new OkHttpClient();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        this.dialog = new Dialog(this);
        this.warningDialog = new WarningDialog(this, new Function0<Unit>() { // from class: com.surepass.surepassesign.UploadDocs$onCreate$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2() {
                UploadDocs.this.finish();
            }
        });
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
        this.store = new Store(this);
        try {
            Store store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String config = store.getConfig();
            Store store2 = this.store;
            if (store2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String brandDetails = store2.getBrandDetails();
            JSONObject configObj = new JSONObject(config).getJSONObject("config");
            this.ALLOW_DOWNLOAD = configObj.getBoolean("allow_download");
            String string = configObj.getString("sign_type");
            Intrinsics.checkExpressionValueIsNotNull(string, "configObj.getString(\"sign_type\")");
            this.SIGN_TYPE = string;
            this.BRAND_LOGO_URL = new JSONObject(brandDetails).getString("brand_image_url");
            this.BRAND_NAME = new JSONObject(brandDetails).getString("brand_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Store store3 = this.store;
        if (store3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        this.BASEURL = store3.getBaseUrl();
        if (Build.VERSION.SDK_INT > 23 && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 10);
        }
        getUploadLink();
        ((RelativeLayout) _$_findCachedViewById(R.id.uploadingView)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UploadDocs$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                UploadDocs.this.openFile();
            }
        });
        ((Button) _$_findCachedViewById(R.id.uploadDoc)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.UploadDocs$onCreate$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                if (!UploadDocs.this.isUploaded()) {
                    UploadDocs uploadDocs = UploadDocs.this;
                    uploadDocs.uploadPdf(uploadDocs.getUrlForUploadingData(), UploadDocs.this.getHashmapOfFields());
                } else if (Intrinsics.areEqual(UploadDocs.this.getSIGN_TYPE(), "suresign")) {
                    UploadDocs.this.getSuresign();
                } else {
                    UploadDocs.this.getXmlRequestPerformed();
                }
            }
        });
    }

    public final void getUploadLink() {
        String url = Intrinsics.stringPlus(this.BASEURL, "upload-link");
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        dialog.showDialog();
        try {
            OkHttpRequest okHttpRequest = this.request;
            if (okHttpRequest == null) {
                Intrinsics.throwUninitializedPropertyAccessException("request");
            }
            okHttpRequest.GET_FOR_ESIGN(url, new UploadDocs$getUploadLink$1(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void showPreuploadedXml(String url) {
        Intrinsics.checkParameterIsNotNull(url, ImagesContract.URL);
        new AsyncTask<Void, Void, Void>(new InputStream[1], url) { // from class: com.surepass.surepassesign.UploadDocs$showPreuploadedXml$1
            final /* synthetic */ InputStream[] $input;
            final /* synthetic */ String $url;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.$input = $captured_local_variable$1;
                this.$url = $captured_local_variable$2;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public Void doInBackground(Void... voids) {
                Intrinsics.checkParameterIsNotNull(voids, "voids");
                try {
                    this.$input[0] = new URL(this.$url).openStream();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public void onPostExecute(Void aVoid) {
                super.onPostExecute((UploadDocs$showPreuploadedXml$1) aVoid);
                ((PDFView) UploadDocs.this._$_findCachedViewById(R.id.pdfView)).fromStream(this.$input[0]).pageFitPolicy(FitPolicy.WIDTH).load();
            }
        }.execute(new Void[0]);
    }

    public final void setAppLogo() {
        Picasso.get().load(this.BRAND_LOGO_URL).into((ImageView) _$_findCachedViewById(R.id.uploadLogoView));
    }

    public final void setDefaultLogo() {
        Picasso.get().load(R.drawable.surepass);
    }

    public final void setAppName() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R.id.uploadLogoView);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "uploadLogoView");
        imageView.setVisibility(8);
        TextView textView = (TextView) _$_findCachedViewById(R.id.headerTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView, "headerTitle");
        textView.setVisibility(0);
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.headerTitle);
        Intrinsics.checkExpressionValueIsNotNull(textView2, "headerTitle");
        textView2.setText(this.BRAND_NAME);
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

    private final void getPreUploadedXML(String url) {
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        dialog.showDialog();
        String esign_console = Constants.Companion.getESIGN_CONSOLE();
        Log.d(esign_console, "pre uploaded " + url);
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        if (url == null) {
            Intrinsics.throwNpe();
        }
        okHttpRequest.GET(url, new Callback() { // from class: com.surepass.surepassesign.UploadDocs$getPreUploadedXML$1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                String esign_console2 = Constants.Companion.getESIGN_CONSOLE();
                Log.d(esign_console2, "Request Failure For Upload File" + e.getMessage());
                e.printStackTrace();
                UploadDocs.this.getDialog().closeDialog();
                UploadDocs.this.onCaughtFailureException(e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                String str = null;
                if (body != null) {
                    body.byteStream();
                }
                if (response.isSuccessful()) {
                    try {
                        String esign_console2 = Constants.Companion.getESIGN_CONSOLE();
                        StringBuilder sb = new StringBuilder();
                        sb.append("response from pre uploaded file ");
                        ResponseBody body2 = response.body();
                        if (body2 != null) {
                            str = body2.string();
                        }
                        sb.append(str);
                        Log.d(esign_console2, sb.toString());
                        UploadDocs.this.runOnUiThread(UploadDocs$getPreUploadedXML$1$onResponse$1.INSTANCE);
                        UploadDocs.this.getEditor().putString("pdf-name", "Surepass Esign");
                        UploadDocs.this.getEditor().commit();
                        UploadDocs.this.getDialog().closeDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                        UploadDocs.this.getDialog().closeDialog();
                        UploadDocs.this.onCaughtFailureException(e);
                    }
                } else {
                    UploadDocs.this.getDialog().closeDialog();
                    UploadDocs.this.onCaughtWrongResponse(response.code());
                }
            }
        });
    }

    public final void uploadPdf(String url, HashMap<?, ?> hashMap) {
        if (this.byteArray != null) {
            Dialog dialog = this.dialog;
            if (dialog == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dialog");
            }
            dialog.showDialog();
            SharedPreferences sharedPreferences = this.sharedPreferences;
            if (sharedPreferences == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
            }
            String pdf_name = sharedPreferences.getString("pdf-name", null);
            OkHttpRequest okHttpRequest = this.request;
            if (okHttpRequest == null) {
                Intrinsics.throwUninitializedPropertyAccessException("request");
            }
            byte[] bArr = this.byteArray;
            if (bArr == null) {
                Intrinsics.throwNpe();
            }
            okHttpRequest.UPLOAD_PDF(url, bArr, pdf_name, hashMap, new UploadDocs$uploadPdf$1(this));
        }
    }

    public final void getXmlRequestPerformed() {
        HashMap map = MapsKt.hashMapOf(TuplesKt.to("page_no", 1), TuplesKt.to("x_coordinate", 0), TuplesKt.to("y_coordinate", 0));
        String url = Intrinsics.stringPlus(this.BASEURL, "get-xml");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.POST(url, map, new UploadDocs$getXmlRequestPerformed$1(this));
    }

    public final void startNSDLActivity(String xmlString) {
        String env = new Store(this).getEnv();
        String esign_console = Constants.Companion.getESIGN_CONSOLE();
        Log.e(esign_console, "env " + env);
        String esign_console2 = Constants.Companion.getESIGN_CONSOLE();
        Log.e(esign_console2, "xmlString " + xmlString);
        Intent appStartIntent = new Intent(this, NsdlEsignActivity.class);
        appStartIntent.putExtra(NotificationCompat.CATEGORY_MESSAGE, xmlString);
        appStartIntent.putExtra("env", env);
        appStartIntent.putExtra("returnUrl", BuildConfig.LIBRARY_PACKAGE_NAME);
        startActivityForResult(appStartIntent, this.REQUEST_CODE_FOR_NSDL);
    }

    public final void startRenderResponseIntent(String url) {
        Intent webviewIntent = new Intent(this, RenderResponse.class);
        webviewIntent.putExtra(ImagesContract.URL, url);
        startActivityForResult(webviewIntent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
    }

    public final void openFile() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select a pdf"), this.REQUEST_CODE_FOR_SELECT_PDF);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkParameterIsNotNull(permissions, "permissions");
        Intrinsics.checkParameterIsNotNull(grantResults, "grantResults");
        if (requestCode == 10) {
            int i = grantResults[0];
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            String esign_console = Constants.Companion.getESIGN_CONSOLE();
            Log.d(esign_console, "esign response " + eSignResponse);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("signedResponse", eSignResponse);
            setResult(-1, resultIntent);
            finish();
        } else if (requestCode == this.REQUEST_CODE_FOR_SELECT_PDF) {
            if (resultCode == -1) {
                if (data == null) {
                    Intrinsics.throwNpe();
                }
                Uri uri = data.getData();
                ((PDFView) _$_findCachedViewById(R.id.pdfView)).fromUri(uri).defaultPage(0).enableSwipe(true).swipeHorizontal(false).enableDoubletap(true).fitEachPage(true).enableAnnotationRendering(true).scrollHandle(new DefaultScrollHandle(this)).load();
                ContentResolver contentResolver = getContentResolver();
                if (uri == null) {
                    Intrinsics.throwNpe();
                }
                this.byteArray = ByteStreams.toByteArray(contentResolver.openInputStream(uri));
                setFileData(data.getData());
            }
        } else if (requestCode == this.REQUEST_CODE_FOR_NSDL) {
            if (data == null) {
                try {
                    Intrinsics.throwNpe();
                } catch (Exception e2) {
                    onCaughtWrongResponse(SecBiometricLicenseManager.ERROR_NETWORK_DISCONNECTED);
                    return;
                }
            }
            String eSignResponse2 = data.getStringExtra("signedResponse");
            String esign_console2 = Constants.Companion.getESIGN_CONSOLE();
            Log.e(esign_console2, "esign response \n" + eSignResponse2);
            Intrinsics.checkExpressionValueIsNotNull(eSignResponse2, "eSignResponse");
            uploadSignedXml(eSignResponse2);
        }
    }

    public final void getSuresign() {
        HashMap map = MapsKt.hashMapOf(TuplesKt.to("", ""));
        String url = Intrinsics.stringPlus(this.BASEURL, "suresign");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.POST(url, map, new Callback() { // from class: com.surepass.surepassesign.UploadDocs$getSuresign$1
            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                String responseData = body != null ? body.string() : null;
                if (response.isSuccessful()) {
                    UploadDocs.this.getDialog().closeDialog();
                    try {
                        String url2 = "";
                        try {
                            String string = new JSONObject(responseData).getJSONObject(UriUtil.DATA_SCHEME).getString(ImagesContract.URL);
                            Intrinsics.checkExpressionValueIsNotNull(string, "data.getString(\"url\")");
                            url2 = string;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String tag = UploadDocs.this.getTAG();
                        Log.e(tag, "url " + url2);
                        if (UploadDocs.this.getALLOW_DOWNLOAD()) {
                            UploadDocs.this.startRenderResponseIntent(url2);
                        } else {
                            UploadDocs.this.startRenderResponseIntent("");
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e(UploadDocs.this.getTAG(), e2.getMessage());
                        UploadDocs.this.getDialog().closeDialog();
                    }
                } else {
                    UploadDocs.this.getDialog().closeDialog();
                    UploadDocs.this.onCaughtWrongResponse(SecBiometricLicenseManager.ERROR_NETWORK_DISCONNECTED);
                }
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                e.printStackTrace();
                UploadDocs.this.getDialog().closeDialog();
                UploadDocs.this.onCaughtFailureException(e);
            }
        });
    }

    private final void uploadSignedXml(String eSignResponse) {
        Log.e("UploadDocs ", eSignResponse);
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        dialog.showDialog();
        HashMap map = MapsKt.hashMapOf(TuplesKt.to("xml", eSignResponse));
        String url = Intrinsics.stringPlus(this.BASEURL, "get-document-mobile");
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.POST(url, map, new Callback() { // from class: com.surepass.surepassesign.UploadDocs$uploadSignedXml$1
            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                String responseData = body != null ? body.string() : null;
                if (response.isSuccessful()) {
                    UploadDocs.this.getDialog().closeDialog();
                    try {
                        String urlForUpload = "";
                        try {
                            String string = new JSONObject(responseData).getJSONObject(UriUtil.DATA_SCHEME).getString(ImagesContract.URL);
                            Intrinsics.checkExpressionValueIsNotNull(string, "data.getString(\"url\")");
                            urlForUpload = string;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String tag = UploadDocs.this.getTAG();
                        Log.e(tag, "url " + urlForUpload);
                        if (UploadDocs.this.getALLOW_DOWNLOAD()) {
                            UploadDocs.this.startRenderResponseIntent(urlForUpload);
                        } else {
                            UploadDocs.this.startRenderResponseIntent("");
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e(UploadDocs.this.getTAG(), e2.getMessage());
                        UploadDocs.this.getDialog().closeDialog();
                    }
                } else {
                    UploadDocs.this.getDialog().closeDialog();
                    UploadDocs.this.onCaughtWrongResponse(SecBiometricLicenseManager.ERROR_NETWORK_DISCONNECTED);
                }
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                e.printStackTrace();
                UploadDocs.this.getDialog().closeDialog();
                UploadDocs.this.onCaughtFailureException(e);
            }
        });
    }

    private final void startFinishActivity() {
    }

    private final void setFileData(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        if (uri == null) {
            Intrinsics.throwNpe();
        }
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        if (cursor != null) {
            Cursor cursor2 = cursor;
            th = null;
            try {
                Cursor it = cursor2;
                if (it.moveToFirst()) {
                    String displayName = it.getString(it.getColumnIndex("_display_name"));
                    Intrinsics.checkExpressionValueIsNotNull(displayName, "it.getString(it.getColum…bleColumns.DISPLAY_NAME))");
                    ((TextView) _$_findCachedViewById(R.id.textView)).setText(displayName);
                    SharedPreferences.Editor editor = this.editor;
                    if (editor == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("editor");
                    }
                    editor.putString("pdf-name", displayName);
                    SharedPreferences.Editor editor2 = this.editor;
                    if (editor2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("editor");
                    }
                    editor2.commit();
                    int sizeIndex = it.getColumnIndex("_size");
                    if (!it.isNull(sizeIndex)) {
                        Intrinsics.checkExpressionValueIsNotNull(it.getString(sizeIndex), "it.getString(sizeIndex)");
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                try {
                    throw th;
                } finally {
                    CloseableKt.closeFinally(cursor2, th);
                }
            }
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
}
