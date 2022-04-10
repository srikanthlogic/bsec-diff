package com.surepass.surepassesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.facebook.common.util.UriUtil;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sec.biometric.license.SecBiometricLicenseManager;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: SignatureActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u00107\u001a\u000208H\u0002J\u0010\u00109\u001a\u00020\u00042\u0006\u0010:\u001a\u00020;H\u0002J\b\u0010<\u001a\u00020=H\u0002J\"\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u00102\b\u0010B\u001a\u0004\u0018\u000102H\u0014J\u0014\u0010C\u001a\u00020?2\n\u0010D\u001a\u00060Ej\u0002`FH\u0002J\u0010\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020\u0010H\u0002J\u0012\u0010I\u001a\u00020?2\b\u0010J\u001a\u0004\u0018\u00010KH\u0014J+\u0010L\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00102\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00040\u00152\u0006\u0010N\u001a\u00020OH\u0016¢\u0006\u0002\u0010PJ\u0010\u0010Q\u001a\u00020=2\u0006\u0010R\u001a\u00020SH\u0002J\u0006\u0010T\u001a\u00020?J\u0006\u0010U\u001a\u00020?J\u0006\u0010V\u001a\u00020?J\b\u0010W\u001a\u00020?H\u0002J\u000e\u0010X\u001a\u00020?2\u0006\u0010:\u001a\u00020;R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u0010X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082D¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u000202X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106¨\u0006Y"}, d2 = {"Lcom/surepass/surepassesign/SignatureActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "BASEURL", "", "getBASEURL", "()Ljava/lang/String;", "setBASEURL", "(Ljava/lang/String;)V", "BRAND_LOGO_URL", "getBRAND_LOGO_URL", "setBRAND_LOGO_URL", "BRAND_NAME", "getBRAND_NAME", "setBRAND_NAME", "REQUEST_CODE_FOR_VERIFICATION_RESULT", "", "getREQUEST_CODE_FOR_VERIFICATION_RESULT", "()I", "REQUEST_CODE_PERMISSIONS", "REQUIRED_PERMISSIONS", "", "[Ljava/lang/String;", "TAG", "getTAG", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "uploadDocs", "Landroid/content/Intent;", "getUploadDocs", "()Landroid/content/Intent;", "setUploadDocs", "(Landroid/content/Intent;)V", "allPermissionsGranted", "", "getBitmaptoBase64", "bitmap", "Landroid/graphics/Bitmap;", "getOutputDirectory", "Ljava/io/File;", "onActivityResult", "", "requestCode", "resultCode", UriUtil.DATA_SCHEME, "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "grantResults", "", "(I[Ljava/lang/String;[I)V", "saveImage", "bytes", "", "setAppLogo", "setAppName", "setDefaultLogo", "startUploadingActivity", "uploadBase64", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class SignatureActivity extends AppCompatActivity {
    private String BASEURL;
    private String BRAND_LOGO_URL;
    private String BRAND_NAME;
    private HashMap _$_findViewCache;
    public OkHttpClient client;
    public Dialog dialog;
    public OkHttpRequest request;
    public Store store;
    public Intent uploadDocs;
    private final String TAG = "SignatureActivity";
    private final String[] REQUIRED_PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private final int REQUEST_CODE_PERMISSIONS = 10;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        this.store = new Store(this);
        this.dialog = new Dialog(this);
        this.client = new OkHttpClient();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        Store store = this.store;
        if (store == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        this.BASEURL = store.getBaseUrl();
        this.uploadDocs = new Intent(this, UploadDocs.class);
        try {
            Store store2 = this.store;
            if (store2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String brandDetails = store2.getBrandDetails();
            this.BRAND_LOGO_URL = new JSONObject(brandDetails).getString("brand_image_url");
            this.BRAND_NAME = new JSONObject(brandDetails).getString("brand_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.BRAND_LOGO_URL;
        boolean z = false;
        if (!(str == null || str.length() == 0)) {
            setAppLogo();
        } else {
            String str2 = this.BRAND_NAME;
            if (str2 == null || str2.length() == 0) {
                z = true;
            }
            if (!z) {
                setAppName();
            } else {
                setDefaultLogo();
            }
        }
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, this.REQUIRED_PERMISSIONS, this.REQUEST_CODE_PERMISSIONS);
        }
        ((Button) _$_findCachedViewById(R.id.clearButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.SignatureActivity$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                ((SignaturePad) SignatureActivity.this._$_findCachedViewById(R.id.signature_pad)).clear();
            }
        });
        ((Button) _$_findCachedViewById(R.id.saveButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.SignatureActivity$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                SignatureActivity.this.getDialog().showDialog();
                SignaturePad signaturePad = (SignaturePad) SignatureActivity.this._$_findCachedViewById(R.id.signature_pad);
                Intrinsics.checkExpressionValueIsNotNull(signaturePad, "signature_pad");
                if (signaturePad.getSignatureBitmap() != null) {
                    SignaturePad signaturePad2 = (SignaturePad) SignatureActivity.this._$_findCachedViewById(R.id.signature_pad);
                    Intrinsics.checkExpressionValueIsNotNull(signaturePad2, "signature_pad");
                    Bitmap signatureBitmap = signaturePad2.getSignatureBitmap();
                    Intrinsics.checkExpressionValueIsNotNull(signatureBitmap, "signature_pad.signatureBitmap");
                    SignatureActivity.this.uploadBase64(signatureBitmap);
                }
            }
        });
    }

    public final void setAppLogo() {
        Picasso.get().load(this.BRAND_LOGO_URL).into((ImageView) _$_findCachedViewById(R.id.imageView3));
    }

    public final void setDefaultLogo() {
        ((ImageView) _$_findCachedViewById(R.id.imageView3)).setImageResource(R.drawable.surepass);
    }

    public final void setAppName() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R.id.imageView3);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "imageView3");
        imageView.setVisibility(8);
        TextView textView = (TextView) _$_findCachedViewById(R.id.textView6);
        Intrinsics.checkExpressionValueIsNotNull(textView, "textView6");
        textView.setVisibility(0);
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.textView6);
        Intrinsics.checkExpressionValueIsNotNull(textView2, "textView6");
        textView2.setText(this.BRAND_NAME);
    }

    public final void uploadBase64(Bitmap bitmap) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        String url = Intrinsics.stringPlus(this.BASEURL, "upload-image");
        String signatureBase64 = "data:image/jpg;base64," + getBitmaptoBase64(bitmap);
        Log.e(this.TAG, "signatureBase64 " + signatureBase64);
        HashMap map = MapsKt.hashMapOf(TuplesKt.to("signature", signatureBase64));
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        okHttpRequest.POST(url, map, new Callback() { // from class: com.surepass.surepassesign.SignatureActivity$uploadBase64$1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(e, "e");
                e.printStackTrace();
                SignatureActivity.this.getDialog().closeDialog();
                SignatureActivity.this.onCaughtFailureException(e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                Intrinsics.checkParameterIsNotNull(response, "response");
                ResponseBody body = response.body();
                String responseData = body != null ? body.string() : null;
                if (response.isSuccessful()) {
                    try {
                        SignatureActivity.this.getDialog().closeDialog();
                        SignatureActivity.this.startUploadingActivity();
                        String tag = SignatureActivity.this.getTAG();
                        Log.d(tag, "signature upload with message " + String.valueOf(responseData));
                    } catch (Exception e) {
                        e.printStackTrace();
                        SignatureActivity.this.getDialog().closeDialog();
                    }
                } else {
                    SignatureActivity.this.onCaughtWrongResponse(response.code());
                    SignatureActivity.this.getDialog().closeDialog();
                }
            }
        });
    }

    public final void startUploadingActivity() {
        Intent intent = this.uploadDocs;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadDocs");
        }
        startActivityForResult(intent, this.REQUEST_CODE_FOR_VERIFICATION_RESULT);
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
            String esign_console = Constants.Companion.getESIGN_CONSOLE();
            Log.d(esign_console, "esign response " + eSignResponse);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("signedResponse", eSignResponse);
            setResult(-1, resultIntent);
            finish();
        }
    }

    private final String getBitmaptoBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkExpressionValueIsNotNull(byteArray, "byteArrayOutputStream.toByteArray()");
            saveImage(byteArray);
            String encoded = Base64.encodeToString(byteArray, 0);
            Intrinsics.checkExpressionValueIsNotNull(encoded, "Base64.encodeToString(byteArray, Base64.DEFAULT)");
            return encoded;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private final boolean allPermissionsGranted() {
        String[] strArr = this.REQUIRED_PERMISSIONS;
        int length = strArr.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= length) {
                return true;
            }
            if (ContextCompat.checkSelfPermission(getBaseContext(), strArr[i]) != 0) {
                z = false;
            }
            if (!z) {
                return false;
            }
            i++;
        }
    }

    private final File getOutputDirectory() {
        File $this$apply;
        File[] externalMediaDirs = getExternalMediaDirs();
        Intrinsics.checkExpressionValueIsNotNull(externalMediaDirs, "externalMediaDirs");
        File it = (File) ArraysKt.firstOrNull(externalMediaDirs);
        if (it != null) {
            $this$apply = new File(it, "verify");
            $this$apply.mkdirs();
        } else {
            $this$apply = null;
        }
        if ($this$apply != null && $this$apply.exists()) {
            return $this$apply;
        }
        File filesDir = getFilesDir();
        Intrinsics.checkExpressionValueIsNotNull(filesDir, "filesDir");
        return filesDir;
    }

    private final File saveImage(byte[] bytes) {
        File directoryName = getOutputDirectory();
        File file = new File(directoryName, "signature" + System.currentTimeMillis() + ".jpg");
        if (!directoryName.exists()) {
            directoryName.mkdirs();
        }
        Log.e(this.TAG, file.getAbsolutePath());
        try {
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(bytes);
            MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(this.TAG, "FileNotFoundException " + e.getMessage());
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.e(this.TAG, "IOException " + e2.getMessage());
        }
        return file;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkParameterIsNotNull(permissions, "permissions");
        Intrinsics.checkParameterIsNotNull(grantResults, "grantResults");
        if (requestCode == this.REQUEST_CODE_PERMISSIONS && !allPermissionsGranted()) {
            Toast.makeText(this, "Permissions not granted by the user.", 0).show();
            finish();
        }
    }

    public final void onCaughtFailureException(Exception e) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("signedResponse", new Constants().getJsonResponse(SecBiometricLicenseManager.ERROR_INTERNAL_SERVER, e.getMessage()).toString());
        setResult(-1, resultIntent);
        finish();
    }

    public final void onCaughtWrongResponse(int statusCode) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("signedResponse", new Constants().getJsonResponse(statusCode).toString());
        setResult(-1, resultIntent);
        finish();
    }
}
