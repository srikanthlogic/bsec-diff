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
import org.json.JSONObject;
import org.json.XML;
/* loaded from: classes3.dex */
public class BiometricActivityPieSupport extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String d = "";
    public static int e = 30;
    public static int f = 0;
    private TextView A;
    private TextView B;
    private Spinner C;
    private CheckBox D;
    private EditText F;
    private TextView G;
    private TextView H;
    BluetoothAdapter g;
    private Button y;
    private Button z;
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private String l = "";
    private String m = "";
    private String n = " ";
    private String o = "";
    private String p = "";
    private String q = "";
    private String r = "";
    private String s = "";
    private String t = "";
    private String u = "";
    private String v = "";
    private int w = 0;
    private int x = 3;
    private final int E = 1;
    private int I = 0;
    private String J = null;
    private String K = "";
    private String L = null;
    private Key M = null;
    private Cipher N = null;

    private String a(byte[] bArr) {
        try {
            return new String(Base64.encodeBase64(bArr));
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public void a(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void b(String str, String str2) {
        boolean z;
        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.INFO");
            intent.setPackage(str);
            startActivityForResult(intent, 1);
            z = false;
        } catch (Throwable th) {
            h();
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
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                create.setCanceledOnTouchOutside(false);
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    private void f(String str) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage(str + " device connected.Please click on Capture Finger Print button to continue.");
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                create.dismiss();
            }
        });
        create.show();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x01e4, code lost:
        if (r7.g == null) goto L_0x01c8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void g() {
        this.g = BluetoothAdapter.getDefaultAdapter();
        this.F = (EditText) findViewById(R.id.otp_aadhaar);
        this.A = (TextView) findViewById(R.id.txt_adhaarText);
        this.B = (TextView) findViewById(R.id.text_DeviceType);
        this.y = (Button) findViewById(R.id.buttonsubmit);
        Button button = (Button) findViewById(R.id.buttoncancel);
        this.C = (Spinner) findViewById(R.id.sp_device);
        this.D = (CheckBox) findViewById(R.id.checkbox_consentBiometric);
        this.z = (Button) findViewById(R.id.btn_Capture);
        this.y.setEnabled(false);
        this.C.setEnabled(false);
        button.setOnClickListener(this);
        this.y.setOnClickListener(this);
        this.C.setOnItemSelectedListener(this);
        this.G = (TextView) findViewById(R.id.wiredDevice);
        this.H = (TextView) findViewById(R.id.bluetoothDevice);
        try {
            this.J = getIntent().getStringExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n0fb405858a246a14e79e325ec52bd6bf5d6cbee1b0b6dab4e949db563554cff9)))));
            this.q = getIntent().getStringExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n4a761a8a5ed458635e70b25419c97627)))));
            this.s = getIntent().getStringExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n0fdcd355f1e769b60910fc8fb72f5ce1)))));
            this.h = getIntent().getStringExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n39a642b94c3cb81529a43b45c5d4147f)))));
            this.i = getIntent().getStringExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n8b296e11dfa2587332551af1eff3d9e4)))));
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.G.setEnabled(false);
        this.H.setEnabled(false);
        this.z.setEnabled(false);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, b());
        arrayAdapter.setDropDownViewResource(17367049);
        this.C.setAdapter((SpinnerAdapter) arrayAdapter);
        String str = "";
        if (!d(this.s)) {
            this.s = this.s.replace("<b>", str);
            this.s = this.s.replace("<br>", str);
            this.s = this.s.replace("</b>", str);
            StringTokenizer stringTokenizer = new StringTokenizer(this.s.trim(), "^^");
            while (stringTokenizer.hasMoreTokens()) {
                str = str + IOUtils.LINE_SEPARATOR_UNIX + stringTokenizer.nextToken();
            }
        }
        e(str);
        if (Build.VERSION.SDK_INT <= 19) {
            this.C.setVisibility(0);
            this.B.setVisibility(0);
            this.G.setVisibility(8);
        } else {
            this.C.setVisibility(8);
            this.B.setVisibility(8);
            this.G.setVisibility(0);
            this.H.setVisibility(0);
        }
        this.H.setVisibility(8);
        if (3 == (getResources().getConfiguration().screenLayout & 15)) {
            this.F.getLayoutParams().height = 60;
            this.y.getLayoutParams().height = 60;
            button.getLayoutParams().height = 60;
            this.D.getLayoutParams().height = 60;
            this.z.getLayoutParams().height = 60;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.A.getLayoutParams();
            layoutParams.setMargins(50, 25, 0, 0);
            this.A.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.F.getLayoutParams();
            layoutParams2.setMargins(25, 25, 50, 25);
            this.F.setLayoutParams(layoutParams2);
            this.A.setGravity(17);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.z.getLayoutParams();
            layoutParams3.setMargins(100, 25, 100, 0);
            this.z.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.y.getLayoutParams();
            layoutParams4.setMargins(50, 30, 25, 0);
            this.y.setLayoutParams(layoutParams4);
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams5.setMargins(0, 30, 50, 0);
            button.setLayoutParams(layoutParams5);
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.D.getLayoutParams();
            layoutParams6.setMargins(40, 0, 0, 0);
            this.D.setLayoutParams(layoutParams6);
            LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) this.B.getLayoutParams();
            layoutParams7.setMargins(50, 25, 25, 0);
            this.B.setLayoutParams(layoutParams7);
            LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) this.C.getLayoutParams();
            layoutParams8.setMargins(0, 25, 50, 0);
            this.C.setLayoutParams(layoutParams8);
        }
    }

    private void g(String str) {
        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.setPackage(str);
            if (d != null) {
                this.t = d;
            }
            if (d == null) {
                d = this.t;
            }
            intent.putExtra("PID_OPTIONS", d);
            startActivityForResult(intent, 2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private String h(String str) {
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

    private void h() {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage(getResources().getString(R.string.rdnotavailable));
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                BiometricActivityPieSupport.this.z.setEnabled(false);
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    public void i() {
        this.J = this.J.toUpperCase();
        String str = this.J;
        if (str == null || (!str.contains("Freedom ABB-100-NIR") && !this.J.contains("BIZZPOS WHQ2(Wizarhand Q2)") && !this.J.contains("WIZARPOS Q2") && !this.J.contains("WIZARHAND_Q1"))) {
            this.z.setEnabled(true);
            e = 30;
            if (a()) {
                this.z.setEnabled(true);
                e = 30;
                f("Biometric wired");
                return;
            }
            this.z.setEnabled(false);
            this.y.setEnabled(false);
            Toast.makeText(this, "" + getResources().getString(R.string.devicenotdecact), 1).show();
            return;
        }
        e = 30;
        this.z.setEnabled(true);
        b("com.digitsecure.lmrdservice", "Digit Secure");
    }

    private void i(String str) {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage("" + str);
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    public void j() {
        if (Build.VERSION.SDK_INT <= 19) {
            String str = this.p;
            if (str == null || str != "0") {
                try {
                    Intent intent = new Intent();
                    intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                    if (d != null) {
                        this.t = d;
                    }
                    if (d == null) {
                        d = this.t;
                    }
                    intent.putExtra("PID_OPTIONS", d);
                    startActivityForResult(intent, 2);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                c("Please select Device from Dropdown");
            }
        } else {
            int i = this.I;
            if (i == 1) {
                e();
            } else if (i == 2) {
                g("com.evolute.rdservice");
            } else {
                Toast.makeText(this, "" + getResources().getString(R.string.devicenotdecact), 1).show();
            }
        }
    }

    public void j(String str) {
        try {
            Intent intent = new Intent();
            intent.putExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n262f6be2018ae99d5125822e2c4241fe3c5e5d5179e1b355ebc98154bdd95ef0)))), str);
            setResult(5000, intent);
            finish();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void k() {
        try {
            Intent intent = new Intent(this, BTDiscoveryActivity.class);
            intent.putExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n5295b5706c5ff69c025aa37abd6bdca7)))), this.p);
            intent.putExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n0afd0d70794c4539b66186596cde5061)))), this.q);
            intent.putExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n6a24110f339b41d34748ba46ce562025)))), this.r);
            startActivityForResult(intent, 2000);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void l() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage("Finger Print Captured Successfully. Please click Ok to continue.");
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                BiometricActivityPieSupport.this.z.setEnabled(false);
                BiometricActivityPieSupport.this.y.setEnabled(true);
                create.dismiss();
            }
        });
        create.show();
    }

    public boolean m() {
        return ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    public void n() {
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
        this.j = str;
        this.k = "Left_Index";
        this.l = "Y";
        this.m = "N";
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))), this.h);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.n1c7911f1c75e50ddf3c559dcca8e0f2b)))), this.i);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.n1ccd1497b8804dc034bafb4f646f3da2)))), this.F.getText().toString());
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.n2335314313a1d742e15c4dcb751e4d9e)))), this.j);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.ne376a799635196b454c4d5cc920763a7)))), "0");
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.na9e233946388478fef23fcea4f0294eb)))), this.k);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.n76f1be54a0dfb2f7937a6df4250edb6b)))), this.n);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.nbfb05bcbd2469009a2bb307034d67dd1)))), this.l);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.nd730cb0ebbd3fbe0869ae7200e3fce8b)))), this.m);
            hashMap.put(new String(this.N.doFinal(b(getResources().getString(R.string.nb30b20c6a0c696b4f37829218ea1b2a2)))), "N");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        AnonymousClass6 r9 = new StringRequest(1, f36a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.4
            /* renamed from: a */
            public void onResponse(String str3) {
                BiometricActivityPieSupport biometricActivityPieSupport;
                String string;
                if (str3 != null) {
                    try {
                        if (!str3.isEmpty() && str3.length() != 0 && str3.contains("xml")) {
                            if (BiometricActivityPieSupport.this.j.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                                BiometricActivityPieSupport.this.a(progressDialog);
                                BiometricActivityPieSupport.this.j(str3);
                                biometricActivityPieSupport = BiometricActivityPieSupport.this;
                                string = "User opted termination of eSign process by clicking Cancel button.";
                            } else {
                                JSONObject jSONObject = XML.toJSONObject(str3).getJSONObject(new String(BiometricActivityPieSupport.this.N.doFinal(BaseActivity.b(BiometricActivityPieSupport.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                                String string2 = jSONObject.getString(new String(BiometricActivityPieSupport.this.N.doFinal(BaseActivity.b(BiometricActivityPieSupport.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                                BiometricActivityPieSupport.this.u = new String(jSONObject.getString(new String(BiometricActivityPieSupport.this.N.doFinal(BaseActivity.b(BiometricActivityPieSupport.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b))))));
                                BiometricActivityPieSupport.this.v = new String(jSONObject.getString(new String(BiometricActivityPieSupport.this.N.doFinal(BaseActivity.b(BiometricActivityPieSupport.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd))))));
                                if (string2.equalsIgnoreCase("0")) {
                                    BiometricActivityPieSupport.this.w++;
                                    if (BiometricActivityPieSupport.this.w < 3) {
                                        int i = BiometricActivityPieSupport.this.x - BiometricActivityPieSupport.this.w;
                                        if (BiometricActivityPieSupport.this.v.equalsIgnoreCase("300")) {
                                            AlertDialog create = new AlertDialog.Builder(BiometricActivityPieSupport.this).create();
                                            create.setMessage(BiometricActivityPieSupport.this.u);
                                            create.setCanceledOnTouchOutside(false);
                                            create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.4.1
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public void onClick(DialogInterface dialogInterface, int i2) {
                                                    BiometricActivityPieSupport.this.a(progressDialog);
                                                    BiometricActivityPieSupport.this.z.setEnabled(true);
                                                    BiometricActivityPieSupport.this.y.setEnabled(false);
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            create.show();
                                            Toast makeText = Toast.makeText(BiometricActivityPieSupport.this.getApplicationContext(), "Total invalid Biometric Attempts : " + BiometricActivityPieSupport.this.x + " and Attempts Remaining : " + i, 1);
                                            ((TextView) makeText.getView().findViewById(16908299)).setTextColor(SupportMenu.CATEGORY_MASK);
                                            makeText.show();
                                            return;
                                        }
                                        BiometricActivityPieSupport.this.a(progressDialog);
                                        BiometricActivityPieSupport.this.j(str3);
                                        biometricActivityPieSupport = BiometricActivityPieSupport.this;
                                        string = BiometricActivityPieSupport.this.u;
                                    } else {
                                        BiometricActivityPieSupport.this.a(progressDialog);
                                        BiometricActivityPieSupport.this.j(str3);
                                        biometricActivityPieSupport = BiometricActivityPieSupport.this;
                                        string = BiometricActivityPieSupport.this.getResources().getString(R.string.Somethingwentwrong);
                                    }
                                } else {
                                    BiometricActivityPieSupport.this.a(progressDialog);
                                    BiometricActivityPieSupport.this.j(str3);
                                    biometricActivityPieSupport = BiometricActivityPieSupport.this;
                                    string = BiometricActivityPieSupport.this.getResources().getString(R.string.eSignsuccessfullforgiveninputxmlrequest);
                                }
                            }
                            biometricActivityPieSupport.c(string);
                            return;
                        }
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                        BiometricActivityPieSupport.this.a(progressDialog);
                        BiometricActivityPieSupport biometricActivityPieSupport2 = BiometricActivityPieSupport.this;
                        biometricActivityPieSupport2.j(biometricActivityPieSupport2.getResources().getString(R.string.Somethingwentwrong));
                        return;
                    }
                }
                BiometricActivityPieSupport.this.a(progressDialog);
                BiometricActivityPieSupport.this.j(BiometricActivityPieSupport.this.getResources().getString(R.string.Somethingwentwrong));
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.5
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                BiometricActivityPieSupport biometricActivityPieSupport;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                BiometricActivityPieSupport.this.a(progressDialog);
                if (volleyError instanceof NetworkError) {
                    biometricActivityPieSupport = BiometricActivityPieSupport.this;
                    resources = biometricActivityPieSupport.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    biometricActivityPieSupport = BiometricActivityPieSupport.this;
                    resources = biometricActivityPieSupport.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    biometricActivityPieSupport = BiometricActivityPieSupport.this;
                    resources = biometricActivityPieSupport.getResources();
                    i = R.string.Somethingwentwrong;
                }
                biometricActivityPieSupport.j(resources.getString(i));
            }
        }) { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.6
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
        String str2 = this.q;
        if (str2 == null || !str2.equalsIgnoreCase("PROD")) {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"PP\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        } else {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        }
        sb.append(str);
        sb.append(this.r);
        sb.append("\" posh=\"UNKNOWN\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>");
        d = sb.toString();
        Intent intent = new Intent(this, NsdlDeviceDetectActivity.class);
        intent.putExtra("PID_OPTIONS", d);
        startActivityForResult(intent, 1000);
    }

    public void f() {
        String str;
        StringBuilder sb;
        String str2 = this.q;
        if (str2 == null || !str2.equalsIgnoreCase("PROD")) {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"PP\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        } else {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        }
        sb.append(str);
        sb.append(this.r);
        sb.append("\" posh=\"UNKNOWN\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>");
        d = sb.toString();
    }

    @Override // com.nsdl.egov.esignaar.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        Bundle extras2;
        Bundle extras3;
        super.onActivityResult(i, i2, intent);
        if (f == 5) {
            f = 0;
            f("Evolute bluetooth");
        }
        if (e == 10) {
            this.z.setEnabled(false);
            this.y.setEnabled(false);
        } else {
            this.z.setEnabled(true);
            this.y.setEnabled(false);
            if (i2 == -1) {
                d = intent.getStringExtra("ResponseXml");
            }
            if (i == 1 && i2 == -1 && (extras3 = intent.getExtras()) != null) {
                String string = extras3.getString("DEVICE_INFO", "");
                String string2 = extras3.getString("RD_SERVICE_INFO", "");
                try {
                    XML.toJSONObject(string).getJSONObject("DeviceInfo").getString("dpId");
                    this.K = XML.toJSONObject(string2).getJSONObject("RDService").getString(NotificationCompat.CATEGORY_STATUS);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                f();
            }
            if (i == 2) {
                if (i2 == -1) {
                    try {
                        this.n = intent.getStringExtra(new String(this.N.doFinal(b(getResources().getString(R.string.n5065be31c3e5a299c584abbc2c5a3e77)))));
                        if (this.n == null || this.n == "" || !this.n.contains("Resp")) {
                            AlertDialog create = new AlertDialog.Builder(this).create();
                            create.setMessage("Finger print not available.");
                            create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.14
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i3) {
                                    dialogInterface.dismiss();
                                }
                            });
                            create.show();
                        } else if (this.n.contains("errCode=\"0\"")) {
                            l();
                        } else {
                            String string3 = XML.toJSONObject(this.n).getJSONObject(new String(this.N.doFinal(b(getResources().getString(R.string.n2d65a1818936149f006466b792506cf7))))).getJSONObject(new String(this.N.doFinal(b(getResources().getString(R.string.n289a21a685e759e174165b295a7abe80))))).getString(new String(this.N.doFinal(b(getResources().getString(R.string.n92802c4103d2f765c8063c169de82268)))));
                            i("" + string3);
                        }
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                    }
                }
                h();
            }
        }
        if (i != 1000) {
            return;
        }
        if (i2 == -1) {
            if (intent != null && (extras2 = intent.getExtras()) != null) {
                this.n = extras2.getString("PID_DATA", "");
                l();
            }
        } else if (intent != null && (extras = intent.getExtras()) != null) {
            extras.getString("error_code", "");
            String string4 = extras.getString("error_message", "");
            i("" + string4);
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

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.biometricactivity);
        this.b = Integer.parseInt(getResources().getString(R.string.n405f0ffcd4afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
        try {
            this.L = getResources().getString(R.string.b71fbb6c3854e097f8aa89a3c9ebc318d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf);
            this.L = this.L.substring(this.b, this.b * this.c);
            this.M = new SecretKeySpec(this.L.getBytes(), getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.N = Cipher.getInstance(getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.N.init(2, this.M);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        g();
        this.z.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BiometricActivityPieSupport.this.j();
            }
        });
        this.G.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BiometricActivityPieSupport.this.I = 1;
                BiometricActivityPieSupport.this.y.setEnabled(false);
                BiometricActivityPieSupport.this.i();
            }
        });
        this.H.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BiometricActivityPieSupport.this.I = 2;
                if (Build.VERSION.SDK_INT < 23 || BiometricActivityPieSupport.this.m()) {
                    BiometricActivityPieSupport.this.k();
                } else {
                    BiometricActivityPieSupport.this.n();
                }
            }
        });
        this.F.addTextChangedListener(new TextWatcher() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.9
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    return;
                }
                if ((editable.toString().length() > 12 && editable.toString().length() < 16) || editable.toString().length() < 12) {
                    BiometricActivityPieSupport.this.D.setChecked(false);
                    BiometricActivityPieSupport.this.D.setEnabled(true);
                    BiometricActivityPieSupport.this.z.setEnabled(false);
                    BiometricActivityPieSupport.this.y.setEnabled(false);
                    if (Build.VERSION.SDK_INT <= 19) {
                        BiometricActivityPieSupport.this.C.setSelection(0);
                        BiometricActivityPieSupport.this.C.getSelectedView().setEnabled(false);
                        BiometricActivityPieSupport.this.C.setEnabled(false);
                        return;
                    }
                    BiometricActivityPieSupport.this.G.setEnabled(false);
                    BiometricActivityPieSupport.this.H.setEnabled(false);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.D.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nsdl.egov.esignaar.BiometricActivityPieSupport.10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                String str;
                BiometricActivityPieSupport biometricActivityPieSupport;
                Resources resources;
                int i;
                if (BiometricActivityPieSupport.this.F.getText().toString() == null || TextUtils.isEmpty(BiometricActivityPieSupport.this.F.getText().toString())) {
                    BiometricActivityPieSupport.this.D.setChecked(false);
                    biometricActivityPieSupport = BiometricActivityPieSupport.this;
                    resources = biometricActivityPieSupport.getResources();
                    i = R.string.enteraadharvid;
                } else if (!d.a(BiometricActivityPieSupport.this.F.getText().toString()) || !(BiometricActivityPieSupport.this.F.getText().toString().length() == 12 || BiometricActivityPieSupport.this.F.getText().toString().length() == 16)) {
                    BiometricActivityPieSupport.this.D.setChecked(false);
                    biometricActivityPieSupport = BiometricActivityPieSupport.this;
                    resources = biometricActivityPieSupport.getResources();
                    i = R.string.validenteraadharvid;
                } else {
                    if (z) {
                        BiometricActivityPieSupport.this.D.setEnabled(false);
                    }
                    if (!BiometricActivityPieSupport.this.D.isChecked()) {
                        BiometricActivityPieSupport.this.C.setSelection(0);
                        BiometricActivityPieSupport.this.C.getSelectedView().setEnabled(false);
                        BiometricActivityPieSupport.this.C.setEnabled(false);
                        BiometricActivityPieSupport.this.z.setEnabled(false);
                        BiometricActivityPieSupport.this.G.setEnabled(false);
                        BiometricActivityPieSupport.this.H.setEnabled(false);
                        biometricActivityPieSupport = BiometricActivityPieSupport.this;
                        str = "Please agree all the conditions.";
                        biometricActivityPieSupport.c(str);
                    } else if (Build.VERSION.SDK_INT <= 19) {
                        BiometricActivityPieSupport.this.C.getSelectedView().setEnabled(false);
                        BiometricActivityPieSupport.this.C.setEnabled(true);
                        return;
                    } else {
                        BiometricActivityPieSupport.this.I = 1;
                        BiometricActivityPieSupport.this.i();
                        BiometricActivityPieSupport.this.G.setEnabled(true);
                        BiometricActivityPieSupport.this.H.setEnabled(true);
                        return;
                    }
                }
                str = resources.getString(i);
                biometricActivityPieSupport.c(str);
            }
        });
        try {
            this.r = null;
            this.r = new BiometricActivityPieSupport().h("2.5FYNNN");
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        String str;
        StringBuilder sb;
        HashMap hashMap = new HashMap();
        hashMap.put("Morpho E1/E2/E3 MSO1300", "5");
        hashMap.put("Secugen Hamster Pro", "7");
        hashMap.put("Mantra MFS100", "8");
        hashMap.put("Precision Biometric PB510", "9");
        hashMap.put("Startek FM220U", "6");
        hashMap.put("Evolute Falcon / Identi5 / Leopard", "4");
        hashMap.put("Cogent CSD200", "10");
        hashMap.put("Digit Secure", "15");
        hashMap.put("Please select device", "0");
        this.o = (String) adapterView.getSelectedItem();
        if ("0" == hashMap.get(this.o)) {
            this.p = (String) hashMap.get(this.o);
            this.z.setEnabled(false);
            return;
        }
        this.p = (String) hashMap.get(this.o);
        this.z.setEnabled(true);
        String str2 = this.q;
        if (str2 == null || !str2.equalsIgnoreCase("PROD")) {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"PP\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        } else {
            sb = new StringBuilder();
            str = "<PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" wadh=\"";
        }
        sb.append(str);
        sb.append(this.r);
        sb.append("\" posh=\"UNKNOWN\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>");
        d = sb.toString();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                n();
            } else {
                k();
            }
        }
    }
}
