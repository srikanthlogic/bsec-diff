package com.surepass.surepassesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.facebook.common.util.UriUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
/* compiled from: RenderResponse.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u0002012\u0006\u00102\u001a\u00020\u0004H\u0002J\b\u00103\u001a\u000201H\u0016J\u0012\u00104\u001a\u0002012\n\u00105\u001a\u000606j\u0002`7J\u000e\u00108\u001a\u0002012\u0006\u00109\u001a\u00020:J\u0012\u0010;\u001a\u0002012\b\u0010<\u001a\u0004\u0018\u00010=H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100¨\u0006>"}, d2 = {"Lcom/surepass/surepassesign/RenderResponse;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "ESIGN_CONSOLE", "", "MY_PREFS_NAME", "getMY_PREFS_NAME", "()Ljava/lang/String;", "setMY_PREFS_NAME", "(Ljava/lang/String;)V", "PDF_NAME", "getPDF_NAME", "setPDF_NAME", "byteArray", "", "getByteArray", "()[B", "setByteArray", "([B)V", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "warningDialog", "Lcom/surepass/surepassesign/WarningDialog;", "getWarningDialog", "()Lcom/surepass/surepassesign/WarningDialog;", "setWarningDialog", "(Lcom/surepass/surepassesign/WarningDialog;)V", "", ImagesContract.URL, "onBackPressed", "onCaughtFailureException", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onCaughtWrongResponse", "statusCode", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class RenderResponse extends AppCompatActivity {
    private final String ESIGN_CONSOLE = "ESIGN_CONSOLE";
    private String MY_PREFS_NAME = "MyPrefsFile";
    private String PDF_NAME;
    private HashMap _$_findViewCache;
    private byte[] byteArray;
    public OkHttpClient client;
    public Dialog dialog;
    public OkHttpRequest request;
    public SharedPreferences sharedPreferences;
    public WarningDialog warningDialog;

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

    public final String getMY_PREFS_NAME() {
        return this.MY_PREFS_NAME;
    }

    public final void setMY_PREFS_NAME(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.MY_PREFS_NAME = str;
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

    public final String getPDF_NAME() {
        return this.PDF_NAME;
    }

    public final void setPDF_NAME(String str) {
        this.PDF_NAME = str;
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra(ImagesContract.URL);
        this.client = new OkHttpClient();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        this.request = new OkHttpRequest(okHttpClient, this);
        this.dialog = new Dialog(this);
        this.warningDialog = new WarningDialog(this, new Function0<Unit>() { // from class: com.surepass.surepassesign.RenderResponse$onCreate$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2() {
                RenderResponse.this.finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(this.MY_PREFS_NAME, 0);
        Intrinsics.checkExpressionValueIsNotNull(sharedPreferences, "getSharedPreferences(MY_…ME, Context.MODE_PRIVATE)");
        this.sharedPreferences = sharedPreferences;
        SharedPreferences sharedPreferences2 = this.sharedPreferences;
        if (sharedPreferences2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        this.PDF_NAME = sharedPreferences2.getString("pdf-name", null);
        String str = url;
        if (str == null || str.length() == 0) {
            PDFView pDFView = (PDFView) _$_findCachedViewById(R.id.pdfView);
            Intrinsics.checkExpressionValueIsNotNull(pDFView, "pdfView");
            pDFView.setVisibility(8);
            ImageView imageView = (ImageView) _$_findCachedViewById(R.id.imageView);
            Intrinsics.checkExpressionValueIsNotNull(imageView, "imageView");
            imageView.setVisibility(0);
            TextView textView = (TextView) _$_findCachedViewById(R.id.textView4);
            Intrinsics.checkExpressionValueIsNotNull(textView, "textView4");
            textView.setVisibility(0);
        } else {
            PDFView pDFView2 = (PDFView) _$_findCachedViewById(R.id.pdfView);
            Intrinsics.checkExpressionValueIsNotNull(pDFView2, "pdfView");
            pDFView2.setVisibility(0);
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R.id.imageView);
            Intrinsics.checkExpressionValueIsNotNull(imageView2, "imageView");
            imageView2.setVisibility(8);
            TextView textView2 = (TextView) _$_findCachedViewById(R.id.textView4);
            Intrinsics.checkExpressionValueIsNotNull(textView2, "textView4");
            textView2.setVisibility(8);
            if (url == null) {
                Intrinsics.throwNpe();
            }
            getByteArray(url);
        }
        ((Button) _$_findCachedViewById(R.id.showDoc)).setOnClickListener(new View.OnClickListener(url) { // from class: com.surepass.surepassesign.RenderResponse$onCreate$2
            final /* synthetic */ String $url;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.$url = r2;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                JSONObject responseJson = new Constants().getJsonResponse(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                responseJson.put(UriUtil.DATA_SCHEME, this.$url);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("signedResponse", responseJson.toString());
                RenderResponse.this.setResult(-1, resultIntent);
                RenderResponse.this.finish();
            }
        });
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

    private final void getByteArray(String url) {
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        dialog.showDialog();
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        if (url == null) {
            Intrinsics.throwNpe();
        }
        okHttpRequest.GET(url, new RenderResponse$getByteArray$1(this));
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
