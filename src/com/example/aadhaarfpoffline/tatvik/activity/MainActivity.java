package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import com.example.aadhaarfpoffline.tatvik.R;
import com.facebook.common.util.UriUtil;
import com.surepass.surepassesign.InitSDK;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: MainActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\u0012\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0004H\u0002J\u000e\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/example/aadhaarfpoffline/tatvik/activity/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "SAMPLE_APP", "", "onActivityResult", "", "requestCode", "", "resultCode", UriUtil.DATA_SCHEME, "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setResponseOnTextView", "resp", "startEsignProcess", "tok", "app_debug"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class MainActivity extends AppCompatActivity {
    private final String SAMPLE_APP = "Sample App";
    private HashMap _$_findViewCache;

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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String token = getIntent().getStringExtra("token");
        Intrinsics.checkExpressionValueIsNotNull(token, "intent.getStringExtra(\"token\")");
        Log.d("TAG", token);
        ((Button) _$_findCachedViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener(token) { // from class: com.example.aadhaarfpoffline.tatvik.activity.MainActivity$onCreate$1
            final /* synthetic */ String $token;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.$token = r2;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View it) {
                MainActivity.this.startEsignProcess(this.$token);
            }
        });
    }

    public final void startEsignProcess(String tok) {
        Intrinsics.checkParameterIsNotNull(tok, "tok");
        Intent fullVerificationIntent = new Intent(this, InitSDK.class);
        fullVerificationIntent.putExtra("token", tok);
        fullVerificationIntent.putExtra("env", "PREPROD");
        startActivityForResult(fullVerificationIntent, CameraAccessExceptionCompat.CAMERA_UNAVAILABLE_DO_NOT_DISTURB);
    }

    private final void setResponseOnTextView(String resp) {
        TextView textView = (TextView) _$_findCachedViewById(R.id.responseView);
        Intrinsics.checkExpressionValueIsNotNull(textView, "responseView");
        textView.setText(resp);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            if (data == null) {
                Intrinsics.throwNpe();
            }
            String eSignResponse = data.getStringExtra("signedResponse");
            String str = this.SAMPLE_APP;
            Log.d(str, "eSign Response " + eSignResponse);
            Intrinsics.checkExpressionValueIsNotNull(eSignResponse, "eSignResponse");
            setResponseOnTextView(eSignResponse);
        }
    }
}
