package com.nsdl.egov.esignaar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.exifinterface.media.ExifInterface;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.surepass.surepassesign.R;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.internal.cache.DiskLruCache;
import org.a.b.a.a;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
/* loaded from: classes3.dex */
public class WithoutOTGSupportBiometricActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView A;
    Spinner B;
    CheckBox C;
    EditText D;
    TextView E;
    Button w;
    Button x;
    Button y;
    TextView z;
    public static String l = "";
    public static int I = 30;
    public static int L = 0;
    String d = "";
    String e = "";
    String f = "";
    String g = "";
    String h = "";
    String i = "";
    String j = "";
    String k = " ";
    String m = "";
    String n = "";
    String o = "";
    String p = "";
    String q = "";
    String r = "";
    String s = "";
    String t = "";
    int u = 0;
    int v = 3;
    private final int M = 1;
    int F = 0;
    BluetoothAdapter G = null;
    String H = null;
    String J = "";
    String K = "";
    private String N = null;
    private Key O = null;
    private Cipher P = null;

    private String a(byte[] bArr) {
        return new String(Base64.encodeBase64(bArr));
    }

    private void b(String str, String str2) {
        boolean z;
        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.INFO");
            intent.setPackage(str);
            startActivityForResult(intent, 2);
            z = false;
        } catch (Throwable th) {
            i();
            z = true;
        }
        if (!z) {
            f(str2);
        }
    }

    private void e(String str) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        create.setTitle("Terms & Conditions");
        create.setMessage(str);
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                create.setCanceledOnTouchOutside(false);
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    private void f() {
        this.D = (EditText) findViewById(R.id.otp_aadhaar);
        this.z = (TextView) findViewById(R.id.txt_adhaarText);
        this.A = (TextView) findViewById(R.id.text_DeviceType);
        this.w = (Button) findViewById(R.id.buttonsubmit);
        this.x = (Button) findViewById(R.id.buttoncancel);
        this.B = (Spinner) findViewById(R.id.sp_device);
        this.C = (CheckBox) findViewById(R.id.checkbox_consentBiometric);
        this.y = (Button) findViewById(R.id.btn_Capture);
        this.w.setEnabled(false);
        this.B.setEnabled(false);
        this.x.setOnClickListener(this);
        this.w.setOnClickListener(this);
        this.B.setOnItemSelectedListener(this);
        this.E = (TextView) findViewById(R.id.bluetoothDevice);
        try {
            this.H = getIntent().getStringExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n0fb405858a246a14e79e325ec52bd6bf5d6cbee1b0b6dab4e949db563554cff9)))));
            this.o = getIntent().getStringExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n4a761a8a5ed458635e70b25419c97627)))));
            this.q = getIntent().getStringExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n0fdcd355f1e769b60910fc8fb72f5ce1)))));
            this.d = getIntent().getStringExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n39a642b94c3cb81529a43b45c5d4147f)))));
            this.e = getIntent().getStringExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n8b296e11dfa2587332551af1eff3d9e4)))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.E.setEnabled(false);
        this.y.setEnabled(false);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, c());
        arrayAdapter.setDropDownViewResource(17367049);
        this.B.setAdapter((SpinnerAdapter) arrayAdapter);
        String str = "";
        if (!d(this.q)) {
            this.q = this.q.replace("<b>", str);
            this.q = this.q.replace("<br>", str);
            this.q = this.q.replace("</b>", str);
            StringTokenizer stringTokenizer = new StringTokenizer(this.q.trim(), "^^");
            while (stringTokenizer.hasMoreTokens()) {
                str = str + IOUtils.LINE_SEPARATOR_UNIX + stringTokenizer.nextToken();
            }
        }
        e(str);
        if (Build.VERSION.SDK_INT <= 19) {
            this.B.setVisibility(0);
            this.A.setVisibility(0);
            this.E.setVisibility(8);
        } else {
            this.B.setVisibility(8);
            this.A.setVisibility(8);
            this.E.setVisibility(0);
        }
        if (3 == (getResources().getConfiguration().screenLayout & 15)) {
            this.D.getLayoutParams().height = 60;
            this.w.getLayoutParams().height = 60;
            this.x.getLayoutParams().height = 60;
            this.C.getLayoutParams().height = 60;
            this.y.getLayoutParams().height = 60;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.z.getLayoutParams();
            layoutParams.setMargins(50, 25, 0, 0);
            this.z.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.D.getLayoutParams();
            layoutParams2.setMargins(25, 25, 50, 25);
            this.D.setLayoutParams(layoutParams2);
            this.z.setGravity(17);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.y.getLayoutParams();
            layoutParams3.setMargins(100, 25, 100, 0);
            this.y.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.w.getLayoutParams();
            layoutParams4.setMargins(50, 30, 25, 0);
            this.w.setLayoutParams(layoutParams4);
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.x.getLayoutParams();
            layoutParams5.setMargins(0, 30, 50, 0);
            this.x.setLayoutParams(layoutParams5);
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.C.getLayoutParams();
            layoutParams6.setMargins(40, 0, 0, 0);
            this.C.setLayoutParams(layoutParams6);
            LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) this.A.getLayoutParams();
            layoutParams7.setMargins(50, 25, 25, 0);
            this.A.setLayoutParams(layoutParams7);
            LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) this.B.getLayoutParams();
            layoutParams8.setMargins(0, 25, 50, 0);
            this.B.setLayoutParams(layoutParams8);
        }
    }

    private void f(String str) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage(str + " device connected.Please click on Capture Finger Print button to continue.");
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                create.dismiss();
            }
        });
        create.show();
    }

    private String g(String str) {
        byte[] bArr;
        Security.addProvider(new a());
        try {
            MessageDigest instance = Build.VERSION.SDK_INT >= 28 ? MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256) : MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256, "BC");
            instance.reset();
            bArr = instance.digest(str.getBytes(StandardCharsets.UTF_8));
        } catch (Throwable th) {
            th.printStackTrace();
            bArr = null;
        }
        return a(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        try {
            Intent intent = new Intent(this, BTDiscoveryActivity.class);
            intent.putExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n5295b5706c5ff69c025aa37abd6bdca7)))), this.n);
            intent.putExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n0afd0d70794c4539b66186596cde5061)))), this.o);
            intent.putExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n6a24110f339b41d34748ba46ce562025)))), this.p);
            startActivityForResult(intent, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void h() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage("Finger Print Captured Successfully. Please click Ok to continue.");
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                WithoutOTGSupportBiometricActivity.this.y.setEnabled(false);
                WithoutOTGSupportBiometricActivity.this.w.setEnabled(true);
                create.dismiss();
            }
        });
        create.show();
    }

    private void h(String str) {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage("" + str);
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    private void i() {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage(getResources().getString(R.string.rdnotavailable));
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                WithoutOTGSupportBiometricActivity.this.y.setEnabled(false);
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    private void i(String str) {
        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.setPackage(str);
            if (l != null) {
                this.r = l;
            }
            if (l == null) {
                l = this.r;
            }
            intent.putExtra("PID_OPTIONS", l);
            startActivityForResult(intent, 1);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        String str;
        if (Build.VERSION.SDK_INT <= 19) {
            String str2 = this.n;
            if (str2 == null || str2 != "0") {
                try {
                    Intent intent = new Intent();
                    intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                    if (l != null) {
                        this.r = l;
                    }
                    if (l == null) {
                        l = this.r;
                    }
                    intent.putExtra("PID_OPTIONS", l);
                    startActivityForResult(intent, 1);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                c("Please select Device from Dropdown");
            }
        } else {
            this.H = this.H.toUpperCase();
            String str3 = this.H;
            if (str3 != null && (str3.contains("Freedom ABB-100-NIR") || this.H.contains("BIZZPOS WHQ2(Wizarhand Q2)") || this.H.contains("WIZARPOS Q2") || this.H.contains("WIZARHAND_Q1"))) {
                str = "com.digitsecure.lmrdservice";
            } else if (this.F == 2) {
                str = "com.evolute.rdservice";
            } else {
                return;
            }
            i(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j(String str) {
        try {
            Intent intent = new Intent();
            intent.putExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n262f6be2018ae99d5125822e2c4241fe3c5e5d5179e1b355ebc98154bdd95ef0)))), str);
            setResult(5000, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.H = this.H.toUpperCase();
        String str = this.H;
        if (str == null) {
            return;
        }
        if (str.contains("Freedom ABB-100-NIR") || this.H.contains("BIZZPOS WHQ2(Wizarhand Q2)") || this.H.contains("WIZARPOS Q2") || this.H.contains("WIZARHAND_Q1")) {
            I = 30;
            this.y.setEnabled(true);
            b("com.digitsecure.lmrdservice", "Digit Secure");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean l() {
        return ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION"};
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
            ActivityCompat.requestPermissions(this, strArr, 1);
        } else {
            ActivityCompat.requestPermissions(this, strArr, 1);
        }
    }

    public void a(String str, String str2) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(str2);
        progressDialog.setMessage(getResources().getString(R.string.loding));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        c.a();
        this.f = str;
        this.g = "Left_Index";
        this.h = "Y";
        this.i = "N";
        this.j = "N";
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))), this.d);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.n1c7911f1c75e50ddf3c559dcca8e0f2b)))), this.e);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.n1ccd1497b8804dc034bafb4f646f3da2)))), this.D.getText().toString());
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.n2335314313a1d742e15c4dcb751e4d9e)))), this.f);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.ne376a799635196b454c4d5cc920763a7)))), "0");
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.na9e233946388478fef23fcea4f0294eb)))), this.g);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.n76f1be54a0dfb2f7937a6df4250edb6b)))), this.k);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.nbfb05bcbd2469009a2bb307034d67dd1)))), this.h);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.nd730cb0ebbd3fbe0869ae7200e3fce8b)))), this.i);
            hashMap.put(new String(this.P.doFinal(b(getResources().getString(R.string.nb30b20c6a0c696b4f37829218ea1b2a2)))), this.j);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AnonymousClass5 r9 = new StringRequest(1, a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.3
            /* renamed from: a */
            public void onResponse(String str3) {
                ProgressDialog progressDialog2;
                String string;
                if (str3 != null) {
                    try {
                        if (!str3.isEmpty() && str3.length() != 0 && str3.contains("xml")) {
                            if (WithoutOTGSupportBiometricActivity.this.f.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                                WithoutOTGSupportBiometricActivity.this.j(str3.toString());
                                WithoutOTGSupportBiometricActivity.this.c("User opted termination of eSign process by clicking Cancel button.");
                                if (progressDialog != null) {
                                    progressDialog2 = progressDialog;
                                } else {
                                    return;
                                }
                            } else {
                                try {
                                    JSONObject jSONObject = XML.toJSONObject(str3.toString()).getJSONObject(new String(WithoutOTGSupportBiometricActivity.this.P.doFinal(BaseActivity.b(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                                    string = jSONObject.getString(new String(WithoutOTGSupportBiometricActivity.this.P.doFinal(BaseActivity.b(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                                    WithoutOTGSupportBiometricActivity.this.s = new String(jSONObject.getString(new String(WithoutOTGSupportBiometricActivity.this.P.doFinal(BaseActivity.b(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b))))));
                                    WithoutOTGSupportBiometricActivity.this.t = new String(jSONObject.getString(new String(WithoutOTGSupportBiometricActivity.this.P.doFinal(BaseActivity.b(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd))))));
                                } catch (Exception e2) {
                                }
                                if (string.equalsIgnoreCase("0")) {
                                    WithoutOTGSupportBiometricActivity.this.u++;
                                    if (WithoutOTGSupportBiometricActivity.this.u < 3) {
                                        int i = WithoutOTGSupportBiometricActivity.this.v - WithoutOTGSupportBiometricActivity.this.u;
                                        if (WithoutOTGSupportBiometricActivity.this.t.equalsIgnoreCase("300")) {
                                            AlertDialog create = new AlertDialog.Builder(WithoutOTGSupportBiometricActivity.this).create();
                                            create.setMessage(WithoutOTGSupportBiometricActivity.this.s);
                                            create.setCanceledOnTouchOutside(false);
                                            create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.3.1
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public void onClick(DialogInterface dialogInterface, int i2) {
                                                    if (progressDialog != null) {
                                                        progressDialog.dismiss();
                                                    }
                                                    WithoutOTGSupportBiometricActivity.this.y.setEnabled(true);
                                                    WithoutOTGSupportBiometricActivity.this.w.setEnabled(false);
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            create.show();
                                            Toast makeText = Toast.makeText(WithoutOTGSupportBiometricActivity.this.getApplicationContext(), "Total invalid Biometric Attempts : " + WithoutOTGSupportBiometricActivity.this.v + " and Attempts Remaining : " + i, 1);
                                            ((TextView) makeText.getView().findViewById(16908299)).setTextColor(SupportMenu.CATEGORY_MASK);
                                            makeText.show();
                                            return;
                                        }
                                        WithoutOTGSupportBiometricActivity.this.j(str3.toString());
                                        WithoutOTGSupportBiometricActivity.this.c(WithoutOTGSupportBiometricActivity.this.s);
                                        if (progressDialog != null) {
                                            progressDialog2 = progressDialog;
                                        } else {
                                            return;
                                        }
                                    } else {
                                        WithoutOTGSupportBiometricActivity.this.j(str3.toString());
                                        WithoutOTGSupportBiometricActivity.this.c(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.Somethingwentwrong));
                                        if (progressDialog != null) {
                                            progressDialog2 = progressDialog;
                                        } else {
                                            return;
                                        }
                                    }
                                } else {
                                    WithoutOTGSupportBiometricActivity.this.j(str3.toString());
                                    WithoutOTGSupportBiometricActivity.this.c(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.eSignsuccessfullforgiveninputxmlrequest));
                                    if (progressDialog != null) {
                                        progressDialog2 = progressDialog;
                                    } else {
                                        return;
                                    }
                                }
                            }
                            progressDialog2.dismiss();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        WithoutOTGSupportBiometricActivity withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                        withoutOTGSupportBiometricActivity.j(withoutOTGSupportBiometricActivity.getResources().getString(R.string.Somethingwentwrong));
                        ProgressDialog progressDialog3 = progressDialog;
                        if (progressDialog3 != null) {
                            progressDialog3.dismiss();
                            return;
                        }
                        return;
                    }
                }
                WithoutOTGSupportBiometricActivity.this.j(WithoutOTGSupportBiometricActivity.this.getResources().getString(R.string.Somethingwentwrong));
                if (progressDialog != null) {
                    progressDialog2 = progressDialog;
                    progressDialog2.dismiss();
                }
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.4
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                WithoutOTGSupportBiometricActivity withoutOTGSupportBiometricActivity;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                if (volleyError instanceof NetworkError) {
                    withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                    resources = withoutOTGSupportBiometricActivity.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                    resources = withoutOTGSupportBiometricActivity.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                    resources = withoutOTGSupportBiometricActivity.getResources();
                    i = R.string.Somethingwentwrong;
                }
                withoutOTGSupportBiometricActivity.j(resources.getString(i));
                ProgressDialog progressDialog2 = progressDialog;
                if (progressDialog2 != null) {
                    progressDialog2.dismiss();
                }
            }
        }) { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.5
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        r9.setRetryPolicy(new b(300000, 1, 0.0f));
        newRequestQueue.add(r9);
    }

    public void e() {
        String str;
        StringBuilder sb;
        String str2 = this.o;
        if (str2 == null || !str2.equalsIgnoreCase("PROD")) {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"PP\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        } else {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        }
        sb.append(str);
        sb.append(this.p);
        sb.append("\" posh=\"UNKNOWN\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>");
        l = sb.toString();
    }

    @Override // com.nsdl.egov.esignaar.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        super.onActivityResult(i, i2, intent);
        if (L == 5) {
            L = 0;
            f("Evolute bluetooth");
        }
        if (I == 10) {
            this.y.setEnabled(false);
            this.w.setEnabled(false);
            return;
        }
        this.y.setEnabled(true);
        this.w.setEnabled(false);
        if (i2 == -1) {
            l = intent.getStringExtra("ResponseXml");
        }
        if (i == 2 && i2 == -1 && (extras = intent.getExtras()) != null) {
            String string = extras.getString("DEVICE_INFO", "");
            String string2 = extras.getString("RD_SERVICE_INFO", "");
            try {
                this.J = XML.toJSONObject(string.toString()).getJSONObject("DeviceInfo").getString("dpId");
                this.K = XML.toJSONObject(string2.toString()).getJSONObject("RDService").getString(NotificationCompat.CATEGORY_STATUS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            e();
        }
        if (i == 1) {
            if (i2 == -1) {
                try {
                    this.k = intent.getStringExtra(new String(this.P.doFinal(b(getResources().getString(R.string.n5065be31c3e5a299c584abbc2c5a3e77)))));
                    if (this.k == null || this.k == "" || !this.k.contains("Resp")) {
                        AlertDialog create = new AlertDialog.Builder(this).create();
                        create.setMessage("Finger print not available.");
                        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.11
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i3) {
                                dialogInterface.dismiss();
                            }
                        });
                        create.show();
                        return;
                    } else if (this.k.contains("errCode=\"0\"")) {
                        h();
                        return;
                    } else {
                        String string3 = XML.toJSONObject(this.k).getJSONObject(new String(this.P.doFinal(b(getResources().getString(R.string.n2d65a1818936149f006466b792506cf7))))).getJSONObject(new String(this.P.doFinal(b(getResources().getString(R.string.n289a21a685e759e174165b295a7abe80))))).getString(new String(this.P.doFinal(b(getResources().getString(R.string.n92802c4103d2f765c8063c169de82268)))));
                        h("" + string3);
                        return;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            i();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        String string;
        String str;
        int id = view.getId();
        if (id == R.id.buttonsubmit) {
            if (d()) {
                string = getResources().getString(R.string.esignprogress);
                str = DiskLruCache.VERSION_1;
                a(str, string);
                return;
            }
            c(getResources().getString(R.string.networknotavailable));
        } else if (id == R.id.buttoncancel) {
            if (d()) {
                string = getResources().getString(R.string.otpcancel);
                str = ExifInterface.GPS_MEASUREMENT_2D;
                a(str, string);
                return;
            }
            c(getResources().getString(R.string.networknotavailable));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.withoutotgsupportbiometricactivity);
        this.b = Integer.parseInt(getResources().getString(R.string.n405f0ffcd4afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
        try {
            this.N = getResources().getString(R.string.b71fbb6c3854e097f8aa89a3c9ebc318d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf);
            this.N = this.N.substring(this.b, this.b * this.c);
            this.O = new SecretKeySpec(this.N.getBytes(), getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.P = Cipher.getInstance(getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.P.init(2, this.O);
        } catch (Exception e) {
            e.printStackTrace();
        }
        f();
        this.G = BluetoothAdapter.getDefaultAdapter();
        this.y.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WithoutOTGSupportBiometricActivity.this.j();
            }
        });
        this.E.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WithoutOTGSupportBiometricActivity.this.F = 2;
                if (Build.VERSION.SDK_INT < 23 || WithoutOTGSupportBiometricActivity.this.l()) {
                    WithoutOTGSupportBiometricActivity.this.g();
                } else {
                    WithoutOTGSupportBiometricActivity.this.m();
                }
            }
        });
        this.D.addTextChangedListener(new TextWatcher() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.7
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    return;
                }
                if ((editable.toString().length() > 12 && editable.toString().length() < 16) || editable.toString().length() < 12) {
                    WithoutOTGSupportBiometricActivity.this.C.setChecked(false);
                    WithoutOTGSupportBiometricActivity.this.C.setEnabled(true);
                    WithoutOTGSupportBiometricActivity.this.y.setEnabled(false);
                    WithoutOTGSupportBiometricActivity.this.w.setEnabled(false);
                    if (Build.VERSION.SDK_INT <= 19) {
                        WithoutOTGSupportBiometricActivity.this.B.setSelection(0);
                        WithoutOTGSupportBiometricActivity.this.B.getSelectedView().setEnabled(false);
                        WithoutOTGSupportBiometricActivity.this.B.setEnabled(false);
                        return;
                    }
                    WithoutOTGSupportBiometricActivity.this.E.setEnabled(false);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.C.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nsdl.egov.esignaar.WithoutOTGSupportBiometricActivity.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                String str;
                WithoutOTGSupportBiometricActivity withoutOTGSupportBiometricActivity;
                Resources resources;
                int i;
                if (WithoutOTGSupportBiometricActivity.this.D.getText().toString() == null || TextUtils.isEmpty(WithoutOTGSupportBiometricActivity.this.D.getText().toString())) {
                    WithoutOTGSupportBiometricActivity.this.C.setChecked(false);
                    withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                    resources = withoutOTGSupportBiometricActivity.getResources();
                    i = R.string.enteraadharvid;
                } else if (!d.a(WithoutOTGSupportBiometricActivity.this.D.getText().toString()) || !(WithoutOTGSupportBiometricActivity.this.D.getText().toString().length() == 12 || WithoutOTGSupportBiometricActivity.this.D.getText().toString().length() == 16)) {
                    WithoutOTGSupportBiometricActivity.this.C.setChecked(false);
                    withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                    resources = withoutOTGSupportBiometricActivity.getResources();
                    i = R.string.validenteraadharvid;
                } else {
                    if (z) {
                        WithoutOTGSupportBiometricActivity.this.C.setEnabled(false);
                    }
                    if (!WithoutOTGSupportBiometricActivity.this.C.isChecked()) {
                        WithoutOTGSupportBiometricActivity.this.B.setSelection(0);
                        WithoutOTGSupportBiometricActivity.this.B.getSelectedView().setEnabled(false);
                        WithoutOTGSupportBiometricActivity.this.B.setEnabled(false);
                        WithoutOTGSupportBiometricActivity.this.y.setEnabled(false);
                        WithoutOTGSupportBiometricActivity.this.E.setEnabled(false);
                        withoutOTGSupportBiometricActivity = WithoutOTGSupportBiometricActivity.this;
                        str = "Please agree all the conditions.";
                        withoutOTGSupportBiometricActivity.c(str);
                    } else if (Build.VERSION.SDK_INT <= 19) {
                        WithoutOTGSupportBiometricActivity.this.B.getSelectedView().setEnabled(false);
                        WithoutOTGSupportBiometricActivity.this.B.setEnabled(true);
                        return;
                    } else {
                        WithoutOTGSupportBiometricActivity.this.k();
                        WithoutOTGSupportBiometricActivity.this.E.setEnabled(true);
                        return;
                    }
                }
                str = resources.getString(i);
                withoutOTGSupportBiometricActivity.c(str);
            }
        });
        try {
            this.p = null;
            this.p = new WithoutOTGSupportBiometricActivity().g("2.5FYNNN");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        String str;
        StringBuilder sb;
        HashMap hashMap = new HashMap();
        hashMap.put("Evolute Falcon / Identi5 / Leopard", "4");
        hashMap.put("Digit Secure Freedom ABB-100-NIR", "15");
        hashMap.put("Please select device", "0");
        this.m = (String) adapterView.getSelectedItem();
        if ("0" == hashMap.get(this.m)) {
            this.n = (String) hashMap.get(this.m);
            this.y.setEnabled(false);
            return;
        }
        this.n = (String) hashMap.get(this.m);
        this.y.setEnabled(true);
        String str2 = this.o;
        if (str2 == null || !str2.equalsIgnoreCase("PROD")) {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"PP\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        } else {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        }
        sb.append(str);
        sb.append(this.p);
        sb.append("\" posh=\"UNKNOWN\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>");
        l = sb.toString();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                m();
            } else {
                g();
            }
        }
    }
}
