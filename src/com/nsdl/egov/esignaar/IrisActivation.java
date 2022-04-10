package com.nsdl.egov.esignaar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sec.biometric.license.SecBiometricLicenseManager;
import com.surepass.surepassesign.R;
import java.io.PrintStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/* loaded from: classes3.dex */
public class IrisActivation extends BaseActivity {
    Context f;
    SecBiometricLicenseManager g;
    boolean d = false;
    String e = null;
    String h = null;
    Key i = null;
    Cipher j = null;
    BroadcastReceiver k = new BroadcastReceiver() { // from class: com.nsdl.egov.esignaar.IrisActivation.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String str;
            PrintStream printStream = System.out;
            printStream.println("Received " + intent.getAction());
            if (intent.getAction().equals(SecBiometricLicenseManager.ACTION_LICENSE_STATUS)) {
                IrisActivation irisActivation = IrisActivation.this;
                irisActivation.unregisterReceiver(irisActivation.k);
                Bundle extras = intent.getExtras();
                String string = extras.getString(SecBiometricLicenseManager.EXTRA_LICENSE_STATUS);
                int i = extras.getInt(SecBiometricLicenseManager.EXTRA_LICENSE_ERROR_CODE);
                if (string.equals(FirebaseAnalytics.Param.SUCCESS)) {
                    IrisActivation.this.c("License activated");
                } else {
                    if (i == 102) {
                        str = "UNKNOWN ERROR";
                    } else if (i == 201) {
                        str = "INVALID LICENSE ERROR";
                    } else if (i == 301) {
                        str = "INTERNAL ERROR";
                    } else if (i == 401) {
                        str = "INTERNAL SERVER ERROR";
                    } else if (i == 601) {
                        str = "USER DISAGREES LICENSE AGREEMENT ERROR";
                    } else if (i == 501) {
                        str = "NETWORK DISCONNECTED ERROR";
                    } else if (i != 502) {
                        switch (i) {
                            case SecBiometricLicenseManager.ERROR_LICENSE_TERMINATED:
                                str = "LICENSE TERMINATED ERROR";
                                break;
                            case SecBiometricLicenseManager.ERROR_INVALID_PACKAGE_NAME:
                                str = "INVALID PACKAGE NAME ERROR";
                                break;
                            case 205:
                                str = "NOT CURRENT DATE ERROR";
                                break;
                        }
                        IrisActivation irisActivation2 = IrisActivation.this;
                        irisActivation2.c("error code = " + i + " error message -: " + IrisAuthenticationActivity.F);
                    } else {
                        str = "NETWORK GENERAL ERROR";
                    }
                    IrisAuthenticationActivity.F = str;
                    IrisActivation irisActivation22 = IrisActivation.this;
                    irisActivation22.c("error code = " + i + " error message -: " + IrisAuthenticationActivity.F);
                }
                IrisActivation.this.finish();
            }
        }
    };

    protected Boolean e() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SecBiometricLicenseManager.ACTION_LICENSE_STATUS);
            registerReceiver(this.k, intentFilter);
            this.g.activateLicense(this.e, getPackageName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            c("status = fail , error code = unknown error");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activation);
        this.b = Integer.parseInt(getResources().getString(R.string.n405f0ffcd4afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
        try {
            this.h = getResources().getString(R.string.b71fbb6c3854e097f8aa89a3c9ebc318d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf);
            this.h = this.h.substring(this.b, this.b * this.c);
            this.i = new SecretKeySpec(this.h.getBytes(), getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.j = Cipher.getInstance(getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.j.init(2, this.i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f = getApplicationContext();
        this.g = SecBiometricLicenseManager.getInstance(this.f);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.e = extras.containsKey(new String(this.j.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2))))) ? extras.getString(new String(this.j.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2))))) : "NA";
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        e();
        if (!e().booleanValue()) {
            unregisterReceiver(this.k);
            finish();
        }
    }
}
