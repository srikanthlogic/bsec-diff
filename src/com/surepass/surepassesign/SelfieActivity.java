package com.surepass.surepassesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.facebook.common.util.UriUtil;
import com.google.common.util.concurrent.ListenableFuture;
import com.sec.biometric.license.SecBiometricLicenseManager;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: SelfieActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\r\u0018\u0000 p2\u00020\u0001:\u0001pB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010E\u001a\u00020FH\u0002J \u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020LH\u0002J\u0010\u0010N\u001a\u00020\u00042\u0006\u0010I\u001a\u00020JH\u0002J\b\u0010O\u001a\u00020-H\u0002J\b\u0010P\u001a\u00020QH\u0002J\"\u0010R\u001a\u00020Q2\u0006\u0010S\u001a\u00020\u00102\u0006\u0010T\u001a\u00020\u00102\b\u0010U\u001a\u0004\u0018\u00010=H\u0014J\u0014\u0010V\u001a\u00020Q2\n\u0010W\u001a\u00060Xj\u0002`YH\u0002J\u0010\u0010Z\u001a\u00020Q2\u0006\u0010[\u001a\u00020\u0010H\u0002J\u0012\u0010\\\u001a\u00020Q2\b\u0010]\u001a\u0004\u0018\u00010^H\u0014J\b\u0010_\u001a\u00020QH\u0014J+\u0010`\u001a\u00020Q2\u0006\u0010S\u001a\u00020\u00102\f\u0010a\u001a\b\u0012\u0004\u0012\u00020\u00040b2\u0006\u0010c\u001a\u00020dH\u0016¢\u0006\u0002\u0010eJ\u0010\u0010f\u001a\u00020-2\u0006\u0010g\u001a\u00020HH\u0002J\u0006\u0010h\u001a\u00020QJ\u0006\u0010i\u001a\u00020QJ\u0006\u0010j\u001a\u00020QJ\b\u0010k\u001a\u00020QH\u0002J\b\u0010l\u001a\u00020QH\u0002J\b\u0010m\u001a\u00020QH\u0002J\b\u0010n\u001a\u00020QH\u0002J\u0006\u0010o\u001a\u00020QR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u0010X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u00020\u0004X\u0086D¢\u0006\n\n\u0002\b\u0015\u001a\u0004\b\u0014\u0010\u0006R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u001fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010.\u001a\u00020/X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u000e\u00104\u001a\u000205X\u0082.¢\u0006\u0002\n\u0000R\u001a\u00106\u001a\u000207X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001a\u0010<\u001a\u00020=X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010B\u001a\u00020=X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010?\"\u0004\bD\u0010A¨\u0006q"}, d2 = {"Lcom/surepass/surepassesign/SelfieActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "BASEURL", "", "getBASEURL", "()Ljava/lang/String;", "setBASEURL", "(Ljava/lang/String;)V", "BRAND_LOGO_URL", "getBRAND_LOGO_URL", "setBRAND_LOGO_URL", "BRAND_NAME", "getBRAND_NAME", "setBRAND_NAME", "REQUEST_CODE_FOR_VERIFICATION_RESULT", "", "getREQUEST_CODE_FOR_VERIFICATION_RESULT", "()I", "TAG", "getTAG", "TAG$1", "box_disabled", "Lcom/surepass/surepassesign/Circle;", "getBox_disabled", "()Lcom/surepass/surepassesign/Circle;", "setBox_disabled", "(Lcom/surepass/surepassesign/Circle;)V", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "imageCapture", "Landroidx/camera/core/ImageCapture;", "outputDirectory", "Ljava/io/File;", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "savedUri", "Landroid/net/Uri;", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "uploadDocs", "Landroid/content/Intent;", "getUploadDocs", "()Landroid/content/Intent;", "setUploadDocs", "(Landroid/content/Intent;)V", "virtualSignIntent", "getVirtualSignIntent", "setVirtualSignIntent", "allPermissionsGranted", "", "cropImage", "", "bitmap", "Landroid/graphics/Bitmap;", "frame", "Landroid/view/View;", "reference", "getBitmaptoBase64", "getOutputDirectory", "moveForward", "", "onActivityResult", "requestCode", "resultCode", UriUtil.DATA_SCHEME, "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "saveImage", "bytes", "setAppLogo", "setAppName", "setDefaultLogo", "startCamera", "startUploadingActivity", "startVirtulSignActivity", "takePhoto", "uploadBase64", "Companion", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class SelfieActivity extends AppCompatActivity {
    private static final String FILENAME_FORMAT;
    private static final String IMAGE_DIRECTORY;
    private static final int REQUEST_CODE_PERMISSIONS;
    private static final String TAG;
    private String BASEURL;
    private String BRAND_LOGO_URL;
    private String BRAND_NAME;
    private HashMap _$_findViewCache;
    public Circle box_disabled;
    private ExecutorService cameraExecutor;
    public OkHttpClient client;
    public Dialog dialog;
    private ImageCapture imageCapture;
    private File outputDirectory;
    public OkHttpRequest request;
    private Uri savedUri;
    public Store store;
    public Intent uploadDocs;
    public Intent virtualSignIntent;
    public static final Companion Companion = new Companion(null);
    private static final String[] REQUIRED_PERMISSIONS = {"android.permission.CAMERA"};
    private final String TAG$1 = "SelfieActivity";
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

    public static final /* synthetic */ Uri access$getSavedUri$p(SelfieActivity $this) {
        Uri uri = $this.savedUri;
        if (uri == null) {
            Intrinsics.throwUninitializedPropertyAccessException("savedUri");
        }
        return uri;
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

    public final String getTAG() {
        return this.TAG$1;
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

    public final String getBASEURL() {
        return this.BASEURL;
    }

    public final void setBASEURL(String str) {
        this.BASEURL = str;
    }

    public final int getREQUEST_CODE_FOR_VERIFICATION_RESULT() {
        return this.REQUEST_CODE_FOR_VERIFICATION_RESULT;
    }

    public final Circle getBox_disabled() {
        Circle circle = this.box_disabled;
        if (circle == null) {
            Intrinsics.throwUninitializedPropertyAccessException("box_disabled");
        }
        return circle;
    }

    public final void setBox_disabled(Circle circle) {
        Intrinsics.checkParameterIsNotNull(circle, "<set-?>");
        this.box_disabled = circle;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        ((ImageView) _$_findCachedViewById(R.id.imageViewBackBlack)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.SelfieActivity$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                SelfieActivity.this.finish();
            }
        });
        try {
            Store store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String brandDetails = store.getBrandDetails();
            this.BRAND_LOGO_URL = new JSONObject(brandDetails).getString("brand_image_url");
            this.BRAND_NAME = new JSONObject(brandDetails).getString("brand_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.BRAND_LOGO_URL;
        if (!(str == null || str.length() == 0)) {
            setAppLogo();
        } else {
            String str2 = this.BRAND_NAME;
            if (!(str2 == null || str2.length() == 0)) {
                setAppName();
            } else {
                setDefaultLogo();
            }
        }
        this.uploadDocs = new Intent(this, UploadDocs.class);
        this.virtualSignIntent = new Intent(this, SignatureActivity.class);
        this.box_disabled = new Circle(this, true, false);
        ConstraintLayout constraintLayout = (ConstraintLayout) _$_findCachedViewById(R.id.screenLayout);
        Circle circle = this.box_disabled;
        if (circle == null) {
            Intrinsics.throwUninitializedPropertyAccessException("box_disabled");
        }
        constraintLayout.addView(circle);
        ((TextView) _$_findCachedViewById(R.id.tv_identity_verification)).bringToFront();
        ((ImageView) _$_findCachedViewById(R.id.surepass_image)).bringToFront();
        ((TextView) _$_findCachedViewById(R.id.textcardHeader)).bringToFront();
        ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R.id.upload_progress);
        Intrinsics.checkExpressionValueIsNotNull(progressBar, "upload_progress");
        progressBar.setVisibility(8);
        this.store = new Store(this);
        this.dialog = new Dialog(this);
        this.client = new OkHttpClient();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        Store store2 = this.store;
        if (store2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        this.BASEURL = store2.getBaseUrl();
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 10);
        }
        ((Button) _$_findCachedViewById(R.id.picture)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.SelfieActivity$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                SelfieActivity.this.takePhoto();
            }
        });
        this.outputDirectory = getOutputDirectory();
        String str3 = this.TAG$1;
        StringBuilder sb = new StringBuilder();
        sb.append("getOutputDirectory ");
        File file = this.outputDirectory;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outputDirectory");
        }
        sb.append(file);
        Log.e(str3, sb.toString());
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkExpressionValueIsNotNull(newSingleThreadExecutor, "Executors.newSingleThreadExecutor()");
        this.cameraExecutor = newSingleThreadExecutor;
        ((Button) _$_findCachedViewById(R.id.again_button)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.SelfieActivity$onCreate$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                SelfieActivity.this.startCamera();
                ImageView imageView = (ImageView) SelfieActivity.this._$_findCachedViewById(R.id.iv_set_image_view_finder);
                Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_set_image_view_finder");
                imageView.setVisibility(8);
                PreviewView previewView = (PreviewView) SelfieActivity.this._$_findCachedViewById(R.id.viewFinder);
                Intrinsics.checkExpressionValueIsNotNull(previewView, "viewFinder");
                previewView.setVisibility(0);
                TextView textView = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.textcardHeader);
                Intrinsics.checkExpressionValueIsNotNull(textView, "textcardHeader");
                textView.setVisibility(0);
                TextView textView2 = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.make_sure);
                Intrinsics.checkExpressionValueIsNotNull(textView2, "make_sure");
                textView2.setVisibility(8);
                Button button = (Button) SelfieActivity.this._$_findCachedViewById(R.id.nextButton);
                Intrinsics.checkExpressionValueIsNotNull(button, "nextButton");
                button.setVisibility(8);
                Button button2 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.nextButton);
                Intrinsics.checkExpressionValueIsNotNull(button2, "nextButton");
                button2.setEnabled(true);
                Button button3 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.again_button);
                Intrinsics.checkExpressionValueIsNotNull(button3, "again_button");
                button3.setEnabled(true);
                Button button4 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.again_button);
                Intrinsics.checkExpressionValueIsNotNull(button4, "again_button");
                button4.setVisibility(8);
                Button button5 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.picture);
                Intrinsics.checkExpressionValueIsNotNull(button5, "picture");
                button5.setVisibility(0);
                ((ConstraintLayout) SelfieActivity.this._$_findCachedViewById(R.id.screenLayout)).setBackgroundColor(Color.parseColor("#000000"));
                ((TextView) SelfieActivity.this._$_findCachedViewById(R.id.tv_identity_verification)).setTextColor(Color.parseColor("#FFFFFF"));
                TextView textView3 = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.tv_identity_verification);
                Intrinsics.checkExpressionValueIsNotNull(textView3, "tv_identity_verification");
                textView3.setText("Click Selfie");
                ((ImageView) SelfieActivity.this._$_findCachedViewById(R.id.imageViewBackBlack)).setImageResource(R.mipmap.back_arrow_black);
                ((ImageView) SelfieActivity.this._$_findCachedViewById(R.id.surepass_image)).setImageResource(R.mipmap.powered_by_surepass_white);
                ((ConstraintLayout) SelfieActivity.this._$_findCachedViewById(R.id.screenLayout)).addView(SelfieActivity.this.getBox_disabled());
            }
        });
        ((Button) _$_findCachedViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() { // from class: com.surepass.surepassesign.SelfieActivity$onCreate$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                try {
                    TextView textView = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.text_progress);
                    Intrinsics.checkExpressionValueIsNotNull(textView, "text_progress");
                    textView.setVisibility(0);
                    Button button = (Button) SelfieActivity.this._$_findCachedViewById(R.id.nextButton);
                    Intrinsics.checkExpressionValueIsNotNull(button, "nextButton");
                    button.setVisibility(8);
                    Button button2 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.again_button);
                    Intrinsics.checkExpressionValueIsNotNull(button2, "again_button");
                    button2.setVisibility(8);
                    TextView textView2 = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.make_sure);
                    Intrinsics.checkExpressionValueIsNotNull(textView2, "make_sure");
                    textView2.setVisibility(8);
                    Button button3 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.nextButton);
                    Intrinsics.checkExpressionValueIsNotNull(button3, "nextButton");
                    button3.setEnabled(false);
                    Button button4 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.again_button);
                    Intrinsics.checkExpressionValueIsNotNull(button4, "again_button");
                    button4.setEnabled(false);
                    ProgressBar progressBar2 = (ProgressBar) SelfieActivity.this._$_findCachedViewById(R.id.upload_progress);
                    Intrinsics.checkExpressionValueIsNotNull(progressBar2, "upload_progress");
                    progressBar2.setVisibility(0);
                    SelfieActivity.this.uploadBase64();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public final void setAppLogo() {
        Picasso.get().load(this.BRAND_LOGO_URL).into((ImageView) _$_findCachedViewById(R.id.surepass_image));
    }

    public final void setDefaultLogo() {
        Picasso.get().load(R.mipmap.powered_by_surepass_white).into((ImageView) _$_findCachedViewById(R.id.surepass_image));
    }

    public final void setAppName() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R.id.surepass_image);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "surepass_image");
        imageView.setVisibility(8);
        TextView textView = (TextView) _$_findCachedViewById(R.id.textView7);
        Intrinsics.checkExpressionValueIsNotNull(textView, "textView7");
        textView.setVisibility(0);
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.textView7);
        Intrinsics.checkExpressionValueIsNotNull(textView2, "textView7");
        textView2.setText(this.BRAND_NAME);
    }

    public final void uploadBase64() {
        Bitmap newBitmap;
        try {
            Uri uri = this.savedUri;
            if (uri == null) {
                Intrinsics.throwUninitializedPropertyAccessException("savedUri");
            }
            File file = new File(uri.getPath());
            Uri newFileUri = Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= 28) {
                newBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), newFileUri));
            } else {
                newBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), newFileUri);
            }
            Log.e(this.TAG$1, "newfile  " + file);
            Log.e(this.TAG$1, "newfile uri " + newFileUri);
            String url = Intrinsics.stringPlus(this.BASEURL, "upload-image");
            Intrinsics.checkExpressionValueIsNotNull(newBitmap, "newBitmap");
            String selfieBase64 = "data:image/jpg;base64," + getBitmaptoBase64(newBitmap);
            Log.e(this.TAG$1, "selfieBase64 " + selfieBase64);
            HashMap map = MapsKt.hashMapOf(TuplesKt.to("selfie", selfieBase64));
            OkHttpRequest okHttpRequest = this.request;
            if (okHttpRequest == null) {
                Intrinsics.throwUninitializedPropertyAccessException("request");
            }
            okHttpRequest.POST(url, map, new Callback() { // from class: com.surepass.surepassesign.SelfieActivity$uploadBase64$1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException e) {
                    Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    e.printStackTrace();
                    SelfieActivity.this.onCaughtFailureException(e);
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                    Intrinsics.checkParameterIsNotNull(response, "response");
                    ResponseBody body = response.body();
                    String responseData = body != null ? body.string() : null;
                    if (response.isSuccessful()) {
                        try {
                            SelfieActivity.this.moveForward();
                            String tag = SelfieActivity.this.getTAG();
                            Log.e(tag, "selfie upload with message " + String.valueOf(responseData));
                        } catch (Exception e) {
                            String tag2 = SelfieActivity.this.getTAG();
                            Log.e(tag2, "Got branding exception with message " + e.getMessage());
                            e.printStackTrace();
                            SelfieActivity.this.getDialog().closeDialog();
                        }
                    } else {
                        String tag3 = SelfieActivity.this.getTAG();
                        Log.e(tag3, "unsuccessful response " + response.message());
                        SelfieActivity.this.onCaughtWrongResponse(response.code());
                        SelfieActivity.this.getDialog().closeDialog();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.TAG$1, "uplaod bas64 selfie" + e.getMessage());
        }
    }

    public final void moveForward() {
        try {
            Store store = this.store;
            if (store == null) {
                Intrinsics.throwUninitializedPropertyAccessException("store");
            }
            String config = store.getConfig();
            JSONObject configObj = new JSONObject(config).getJSONObject("config");
            String str = this.TAG$1;
            Log.e(str, "configObj " + String.valueOf(config));
            boolean acceptVirtualSign = configObj.getBoolean("accept_virtual_sign");
            ExecutorService executorService = this.cameraExecutor;
            if (executorService == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cameraExecutor");
            }
            executorService.shutdown();
            if (acceptVirtualSign) {
                startVirtulSignActivity();
            } else {
                startUploadingActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
            onCaughtFailureException(e);
        }
    }

    private final void startUploadingActivity() {
        Intent intent = this.uploadDocs;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadDocs");
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

    private final String getBitmaptoBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkExpressionValueIsNotNull(byteArray, "byteArrayOutputStream.toByteArray()");
        String encoded = Base64.encodeToString(byteArray, 0);
        Intrinsics.checkExpressionValueIsNotNull(encoded, "Base64.encodeToString(byteArray, Base64.DEFAULT)");
        return encoded;
    }

    public final void takePhoto() {
        ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R.id.camera_shutter_progress_bar);
        Intrinsics.checkExpressionValueIsNotNull(progressBar, "camera_shutter_progress_bar");
        progressBar.setVisibility(0);
        ImageCapture imageCapture = this.imageCapture;
        if (imageCapture != null) {
            File file = this.outputDirectory;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("outputDirectory");
            }
            File photoFile = new File(file, new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Long.valueOf(System.currentTimeMillis())) + ".jpg");
            ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();
            Intrinsics.checkExpressionValueIsNotNull(outputOptions, "ImageCapture.OutputFileO…uilder(photoFile).build()");
            imageCapture.lambda$takePicture$4$ImageCapture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback(photoFile) { // from class: com.surepass.surepassesign.SelfieActivity$takePhoto$1
                final /* synthetic */ File $photoFile;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.$photoFile = $captured_local_variable$1;
                }

                @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
                public void onError(ImageCaptureException exc) {
                    Intrinsics.checkParameterIsNotNull(exc, "exc");
                    String tag = SelfieActivity.this.getTAG();
                    Log.e(tag, "Photo capture failed: " + exc.getMessage(), exc);
                }

                @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
                public void onImageSaved(ImageCapture.OutputFileResults output) {
                    Intrinsics.checkParameterIsNotNull(output, "output");
                    ((ConstraintLayout) SelfieActivity.this._$_findCachedViewById(R.id.screenLayout)).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ((TextView) SelfieActivity.this._$_findCachedViewById(R.id.tv_identity_verification)).setTextColor(Color.parseColor("#000000"));
                    ((ImageView) SelfieActivity.this._$_findCachedViewById(R.id.imageViewBackBlack)).setImageResource(R.mipmap.back_arrow_black);
                    ((ImageView) SelfieActivity.this._$_findCachedViewById(R.id.surepass_image)).setImageResource(R.mipmap.power_by_surepass_black);
                    SelfieActivity selfieActivity = SelfieActivity.this;
                    Uri fromFile = Uri.fromFile(this.$photoFile);
                    Intrinsics.checkExpressionValueIsNotNull(fromFile, "Uri.fromFile(photoFile)");
                    selfieActivity.savedUri = fromFile;
                    String tag = SelfieActivity.this.getTAG();
                    Log.e(tag, "output saved uri " + output);
                    ((ConstraintLayout) SelfieActivity.this._$_findCachedViewById(R.id.screenLayout)).removeView(SelfieActivity.this.getBox_disabled());
                    TextView textView = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.tv_identity_verification);
                    Intrinsics.checkExpressionValueIsNotNull(textView, "tv_identity_verification");
                    textView.setText("Verify Selfie");
                    PreviewView previewView = (PreviewView) SelfieActivity.this._$_findCachedViewById(R.id.viewFinder);
                    Intrinsics.checkExpressionValueIsNotNull(previewView, "viewFinder");
                    previewView.setVisibility(8);
                    ImageView imageView = (ImageView) SelfieActivity.this._$_findCachedViewById(R.id.iv_set_image_view_finder);
                    Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_set_image_view_finder");
                    imageView.setVisibility(0);
                    TextView textView2 = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.textcardHeader);
                    Intrinsics.checkExpressionValueIsNotNull(textView2, "textcardHeader");
                    textView2.setVisibility(8);
                    TextView textView3 = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.textcardText);
                    Intrinsics.checkExpressionValueIsNotNull(textView3, "textcardText");
                    textView3.setVisibility(8);
                    TextView textView4 = (TextView) SelfieActivity.this._$_findCachedViewById(R.id.make_sure);
                    Intrinsics.checkExpressionValueIsNotNull(textView4, "make_sure");
                    textView4.setVisibility(0);
                    Button button = (Button) SelfieActivity.this._$_findCachedViewById(R.id.picture);
                    Intrinsics.checkExpressionValueIsNotNull(button, "picture");
                    button.setVisibility(8);
                    Button button2 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.again_button);
                    Intrinsics.checkExpressionValueIsNotNull(button2, "again_button");
                    button2.setVisibility(0);
                    Button button3 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.nextButton);
                    Intrinsics.checkExpressionValueIsNotNull(button3, "nextButton");
                    button3.setVisibility(0);
                    Button button4 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.nextButton);
                    Intrinsics.checkExpressionValueIsNotNull(button4, "nextButton");
                    button4.setEnabled(true);
                    Button button5 = (Button) SelfieActivity.this._$_findCachedViewById(R.id.again_button);
                    Intrinsics.checkExpressionValueIsNotNull(button5, "again_button");
                    button5.setEnabled(true);
                    ((ImageView) SelfieActivity.this._$_findCachedViewById(R.id.iv_set_image_view_finder)).setImageURI(SelfieActivity.access$getSavedUri$p(SelfieActivity.this));
                    ProgressBar progressBar2 = (ProgressBar) SelfieActivity.this._$_findCachedViewById(R.id.camera_shutter_progress_bar);
                    Intrinsics.checkExpressionValueIsNotNull(progressBar2, "camera_shutter_progress_bar");
                    progressBar2.setVisibility(8);
                    Log.d(SelfieActivity.this.getTAG(), "Photo capture succeeded: " + SelfieActivity.access$getSavedUri$p(SelfieActivity.this));
                }
            });
        }
    }

    public final void startCamera() {
        ListenableFuture cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        Intrinsics.checkExpressionValueIsNotNull(cameraProviderFuture, "ProcessCameraProvider.getInstance(this)");
        cameraProviderFuture.addListener(new Runnable(cameraProviderFuture) { // from class: com.surepass.surepassesign.SelfieActivity$startCamera$1
            final /* synthetic */ ListenableFuture $cameraProviderFuture;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.$cameraProviderFuture = r2;
            }

            /* JADX INFO: Multiple debug info for r1v2 androidx.camera.core.Preview: [D('it' androidx.camera.core.Preview), D('preview' androidx.camera.core.Preview)] */
            @Override // java.lang.Runnable
            public final void run() {
                V v = this.$cameraProviderFuture.get();
                Intrinsics.checkExpressionValueIsNotNull(v, "cameraProviderFuture.get()");
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) v;
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(((PreviewView) SelfieActivity.this._$_findCachedViewById(R.id.viewFinder)).createSurfaceProvider());
                Intrinsics.checkExpressionValueIsNotNull(preview, "Preview.Builder()\n      …ider())\n                }");
                SelfieActivity.this.imageCapture = new ImageCapture.Builder().build();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
                Intrinsics.checkExpressionValueIsNotNull(cameraSelector, "CameraSelector.DEFAULT_FRONT_CAMERA");
                try {
                    cameraProvider.unbindAll();
                    cameraProvider.bindToLifecycle(SelfieActivity.this, cameraSelector, preview, SelfieActivity.this.imageCapture);
                } catch (Exception exc) {
                    Log.e(SelfieActivity.this.getTAG(), "Use case binding failed", exc);
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private final byte[] cropImage(Bitmap bitmap, View frame, View reference) {
        int heightOriginal = frame.getHeight();
        int widthOriginal = frame.getWidth();
        int heightFrame = reference.getHeight();
        int widthFrame = reference.getWidth();
        int leftFrame = reference.getLeft();
        int topFrame = reference.getTop();
        int heightReal = bitmap.getHeight();
        int widthReal = bitmap.getWidth();
        Bitmap bitmapFinal = Bitmap.createBitmap(bitmap, (leftFrame * widthReal) / widthOriginal, (topFrame * heightReal) / heightOriginal, (widthFrame * widthReal) / widthOriginal, (heightFrame * heightReal) / heightOriginal);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapFinal.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Intrinsics.checkExpressionValueIsNotNull(byteArray, "stream.toByteArray()");
        return byteArray;
    }

    private final File saveImage(byte[] bytes) {
        File directoryName = new File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY);
        File file = new File(directoryName, "KTP" + System.currentTimeMillis() + ".jpg");
        if (!directoryName.exists()) {
            directoryName.mkdirs();
        }
        try {
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(bytes);
            MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(this.TAG$1, e.getMessage());
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.e(this.TAG$1, e2.getMessage());
        }
        return file;
    }

    private final boolean allPermissionsGranted() {
        String[] strArr = REQUIRED_PERMISSIONS;
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

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkParameterIsNotNull(permissions, "permissions");
        Intrinsics.checkParameterIsNotNull(grantResults, "grantResults");
        if (requestCode != 10) {
            return;
        }
        if (allPermissionsGranted()) {
            startCamera();
            return;
        }
        Toast.makeText(this, "Permissions not granted by the user.", 0).show();
        finish();
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

    /* compiled from: SelfieActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/surepass/surepassesign/SelfieActivity$Companion;", "", "()V", "FILENAME_FORMAT", "", "IMAGE_DIRECTORY", "REQUEST_CODE_PERMISSIONS", "", "REQUIRED_PERMISSIONS", "", "[Ljava/lang/String;", "TAG", "app_release"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ExecutorService executorService = this.cameraExecutor;
        if (executorService == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cameraExecutor");
        }
        executorService.shutdown();
    }
}
