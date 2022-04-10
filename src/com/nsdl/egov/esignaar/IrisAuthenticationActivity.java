package com.nsdl.egov.esignaar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class IrisAuthenticationActivity extends BaseActivity implements View.OnClickListener {
    public static String F = "Something went wrong while SM-T116IR device Iris activation";
    CheckBox A;
    EditText d;
    TextView e;
    Button f;
    Button i;
    Button j;
    int g = 0;
    public final int h = 1;
    String k = "";
    String l = "";
    String m = "";
    String n = "";
    String o = "";
    String p = "";
    String q = "";
    String r = "";
    String s = " ";
    String t = "";
    String u = "";
    String v = "";
    String w = "";
    int x = 0;
    int y = 3;
    String z = null;
    String B = "NA";
    String C = null;
    Key D = null;
    Cipher E = null;

    private String a(byte[] bArr) {
        return new String(Base64.encodeBase64(bArr));
    }

    public void a(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private String b(int i) {
        String format;
        int i2 = 5400;
        if (i == 2) {
            i2 = 15000;
        }
        try {
            if (this.o == null || !this.o.equalsIgnoreCase(new String(this.E.doFinal(b(getResources().getString(R.string.nbb5db6596c7ab75a351056b8eb448990)))))) {
                format = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver=\"1.0\" ><Opts iCount=\"1\" format=\"0\" iType=\"0\" pidVer=\"2.0\" env=\"PP\" timeout=\"" + i2 + "\" wadh=\"" + this.t + "\"/><Demo></Demo></PidOptions>", new Object[0]);
            } else {
                format = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver=\"1.0\" ><Opts iCount=\"1\" format=\"0\" iType=\"0\" pidVer=\"2.0\" env=\"P\" timeout=\"" + i2 + "\" wadh=\"" + this.t + "\"/><Demo></Demo></PidOptions>", new Object[0]);
            }
            this.u = format;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return this.u;
    }

    private void e() {
        if (!g()) {
            try {
                Intent intent = new Intent(this, IrisActivation.class);
                intent.putExtra(new String(this.E.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2)))), this.B);
                startActivityForResult(intent, 999);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void e(String str) {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setTitle("Terms & Conditions");
        create.setCanceledOnTouchOutside(false);
        create.setMessage(str);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    private String f(String str) {
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

    private void f() {
        this.d = (EditText) findViewById(R.id.otp_aadhaar);
        this.e = (TextView) findViewById(R.id.txt_adhaarText);
        this.j = (Button) findViewById(R.id.buttonsubmit);
        this.i = (Button) findViewById(R.id.buttoncancel);
        this.A = (CheckBox) findViewById(R.id.checkbox_consentIris);
        this.f = (Button) findViewById(R.id.AuthBtn);
        this.j = (Button) findViewById(R.id.buttonsubmit);
        this.i = (Button) findViewById(R.id.buttoncancel);
        this.j.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.j.setEnabled(false);
        try {
            this.z = getIntent().getStringExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n0fb405858a246a14e79e325ec52bd6bf5d6cbee1b0b6dab4e949db563554cff9)))));
            this.o = getIntent().getStringExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n4a761a8a5ed458635e70b25419c97627)))));
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.B = extras.containsKey(new String(this.E.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2))))) ? extras.getString(new String(this.E.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2))))) : "NA";
            }
            this.k = getIntent().getStringExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n0fdcd355f1e769b60910fc8fb72f5ce1)))));
            this.l = getIntent().getStringExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n39a642b94c3cb81529a43b45c5d4147f)))));
            this.m = getIntent().getStringExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n8b296e11dfa2587332551af1eff3d9e4)))));
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String str = "";
        if (!d(this.k)) {
            this.k = this.k.replace("<b>", str);
            this.k = this.k.replace("<br>", str);
            this.k = this.k.replace("</b>", str);
            StringTokenizer stringTokenizer = new StringTokenizer(this.k.trim(), "^^");
            while (stringTokenizer.hasMoreTokens()) {
                str = str + IOUtils.LINE_SEPARATOR_UNIX + stringTokenizer.nextToken();
            }
        }
        e(str);
        if (3 == (getResources().getConfiguration().screenLayout & 15)) {
            this.d.getLayoutParams().height = 60;
            this.j.getLayoutParams().height = 60;
            this.i.getLayoutParams().height = 60;
            this.A.getLayoutParams().height = 60;
            this.f.getLayoutParams().height = 60;
            this.j.getLayoutParams().height = 60;
            this.i.getLayoutParams().height = 60;
            this.A.getLayoutParams().height = 60;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.e.getLayoutParams();
            layoutParams.setMargins(50, 25, 0, 0);
            this.e.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.d.getLayoutParams();
            layoutParams2.setMargins(25, 25, 50, 25);
            this.d.setLayoutParams(layoutParams2);
            this.e.setGravity(17);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.f.getLayoutParams();
            layoutParams3.setMargins(100, 25, 100, 0);
            this.f.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.j.getLayoutParams();
            layoutParams4.setMargins(50, 30, 25, 0);
            this.j.setLayoutParams(layoutParams4);
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.i.getLayoutParams();
            layoutParams5.setMargins(0, 30, 50, 0);
            this.i.setLayoutParams(layoutParams5);
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.A.getLayoutParams();
            layoutParams6.setMargins(40, 0, 0, 0);
            this.A.setLayoutParams(layoutParams6);
        }
    }

    public void g(String str) {
        try {
            Intent intent = new Intent();
            intent.putExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n262f6be2018ae99d5125822e2c4241fe3c5e5d5179e1b355ebc98154bdd95ef0)))), str);
            setResult(5000, intent);
            finish();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private boolean g() {
        try {
            return getApplicationContext().getPackageManager().checkPermission("com.sec.enterprise.biometric.permission.IRIS_RECOGNITION", getApplicationContext().getPackageName()) == 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public void a(int i) {
        String str;
        String str2;
        String str3;
        this.g = 1;
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent("in.gov.uidai.rdservice.iris.CAPTURE");
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        String str4 = null;
        if (queryIntentActivities != null || !queryIntentActivities.isEmpty()) {
            str4 = queryIntentActivities.get(0).activityInfo.packageName;
            str = queryIntentActivities.get(0).activityInfo.name;
        } else {
            str = null;
        }
        try {
            if (i == 2) {
                str2 = "com.iritech.rdservice";
                str3 = "com.iritech.rdservice.irishield.IriShieldRDActivity";
            } else if (i == 3) {
                str2 = "com.mantra.mis100v2.rdservice";
                str3 = "com.mantra.mis100v2.rdservice.RDServiceActivity";
            } else {
                intent.setClassName(str4, str);
                intent.putExtra("PID_OPTIONS", b(i));
                startActivityForResult(intent, 1);
                return;
            }
            startActivityForResult(intent, 1);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            if (this.z.equals("SM-T116IR") && !g()) {
                Toast.makeText(this, "Please pass valid licence key to perform IRIS functionality.", 0).show();
                return;
            }
            return;
        }
        intent.setClassName(str2, str3);
        intent.putExtra("PID_OPTIONS", b(i));
    }

    public void a(String str, String str2) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(str2);
        progressDialog.setMessage(getResources().getString(R.string.loding));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        c.a();
        this.n = str;
        this.p = "Y";
        this.q = "N";
        this.r = "N";
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))), this.l);
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.n1c7911f1c75e50ddf3c559dcca8e0f2b)))), this.m);
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.n1ccd1497b8804dc034bafb4f646f3da2)))), this.d.getText().toString());
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.n2335314313a1d742e15c4dcb751e4d9e)))), this.n);
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.n9d33d55dc0c32cb6bb148322fba793bc)))), this.s);
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.nbfb05bcbd2469009a2bb307034d67dd1)))), this.p);
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.nd730cb0ebbd3fbe0869ae7200e3fce8b)))), this.q);
            hashMap.put(new String(this.E.doFinal(b(getResources().getString(R.string.nb30b20c6a0c696b4f37829218ea1b2a2)))), this.r);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        AnonymousClass2 r9 = new StringRequest(1, f36a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.16
            /* renamed from: a */
            public void onResponse(String str3) {
                IrisAuthenticationActivity irisAuthenticationActivity;
                String string;
                if (str3 != null) {
                    try {
                        if (!str3.isEmpty() && str3.length() != 0 && str3.contains("xml")) {
                            if (IrisAuthenticationActivity.this.n.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                                IrisAuthenticationActivity.this.g(str3);
                                IrisAuthenticationActivity.this.c(IrisAuthenticationActivity.this.getResources().getString(R.string.cancelbtn));
                                IrisAuthenticationActivity.this.a(progressDialog);
                                return;
                            }
                            JSONObject jSONObject = XML.toJSONObject(str3).getJSONObject(new String(IrisAuthenticationActivity.this.E.doFinal(BaseActivity.b(IrisAuthenticationActivity.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                            String string2 = jSONObject.getString(new String(IrisAuthenticationActivity.this.E.doFinal(BaseActivity.b(IrisAuthenticationActivity.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                            IrisAuthenticationActivity.this.v = new String(jSONObject.getString(new String(IrisAuthenticationActivity.this.E.doFinal(BaseActivity.b(IrisAuthenticationActivity.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b))))));
                            IrisAuthenticationActivity.this.w = new String(jSONObject.getString(new String(IrisAuthenticationActivity.this.E.doFinal(BaseActivity.b(IrisAuthenticationActivity.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd))))));
                            if (string2.equalsIgnoreCase("0")) {
                                IrisAuthenticationActivity.this.x++;
                                if (IrisAuthenticationActivity.this.x < 3) {
                                    int i = IrisAuthenticationActivity.this.y - IrisAuthenticationActivity.this.x;
                                    if (!IrisAuthenticationActivity.this.w.equalsIgnoreCase("300") && !IrisAuthenticationActivity.this.w.equalsIgnoreCase("800")) {
                                        IrisAuthenticationActivity.this.a(progressDialog);
                                        IrisAuthenticationActivity.this.g(str3.toString());
                                        irisAuthenticationActivity = IrisAuthenticationActivity.this;
                                        string = IrisAuthenticationActivity.this.v;
                                    }
                                    android.app.AlertDialog create = new AlertDialog.Builder(IrisAuthenticationActivity.this).create();
                                    create.setMessage(IrisAuthenticationActivity.this.v);
                                    create.setCanceledOnTouchOutside(false);
                                    create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.16.1
                                        @Override // android.content.DialogInterface.OnClickListener
                                        public void onClick(DialogInterface dialogInterface, int i2) {
                                            IrisAuthenticationActivity.this.a(progressDialog);
                                            IrisAuthenticationActivity.this.j.setEnabled(false);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    create.show();
                                    Toast makeText = Toast.makeText(IrisAuthenticationActivity.this.getApplicationContext(), "Total invalid Biometric Attempts : " + IrisAuthenticationActivity.this.y + " and Attempts Remaining : " + i, 1);
                                    ((TextView) makeText.getView().findViewById(16908299)).setTextColor(SupportMenu.CATEGORY_MASK);
                                    makeText.show();
                                    return;
                                }
                                IrisAuthenticationActivity.this.a(progressDialog);
                                IrisAuthenticationActivity.this.g(str3.toString());
                                irisAuthenticationActivity = IrisAuthenticationActivity.this;
                                string = IrisAuthenticationActivity.this.getResources().getString(R.string.Somethingwentwrong);
                            } else {
                                IrisAuthenticationActivity.this.a(progressDialog);
                                IrisAuthenticationActivity.this.g(str3);
                                irisAuthenticationActivity = IrisAuthenticationActivity.this;
                                string = IrisAuthenticationActivity.this.getResources().getString(R.string.eSignsuccessfullforgiveninputxmlrequest);
                            }
                            irisAuthenticationActivity.c(string);
                            return;
                        }
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                        IrisAuthenticationActivity.this.a(progressDialog);
                        IrisAuthenticationActivity irisAuthenticationActivity2 = IrisAuthenticationActivity.this;
                        irisAuthenticationActivity2.g(irisAuthenticationActivity2.getResources().getString(R.string.Somethingwentwrong));
                        return;
                    }
                }
                IrisAuthenticationActivity.this.a(progressDialog);
                IrisAuthenticationActivity.this.g(IrisAuthenticationActivity.this.getResources().getString(R.string.Somethingwentwrong));
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.17
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                IrisAuthenticationActivity irisAuthenticationActivity;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                IrisAuthenticationActivity.this.a(progressDialog);
                if (volleyError instanceof NetworkError) {
                    irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    resources = irisAuthenticationActivity.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    resources = irisAuthenticationActivity.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    resources = irisAuthenticationActivity.getResources();
                    i = R.string.Somethingwentwrong;
                }
                irisAuthenticationActivity.g(resources.getString(i));
            }
        }) { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.2
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        r9.setRetryPolicy(new b(300000, 1, 0.0f));
        newRequestQueue.add(r9);
    }

    public void a(String str, List<String> list, String str2) {
        final RadioButton radioButton;
        int i;
        int i2;
        int i3;
        String str3;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.iris_device_dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.txt_device_title);
        Button button = (Button) dialog.findViewById(R.id.button_close);
        RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.radio_inbuild);
        final RadioButton radioButton3 = (RadioButton) dialog.findViewById(R.id.radio_wired);
        final RadioButton radioButton4 = (RadioButton) dialog.findViewById(R.id.radio_wired_mantra);
        radioButton3.setVisibility(8);
        radioButton4.setVisibility(8);
        if (3 == (getResources().getConfiguration().screenLayout & 15)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(20, 20, 0, 20);
            textView.setLayoutParams(layoutParams);
            radioButton2.setTextSize(20.0f);
            radioButton3.setTextSize(20.0f);
            radioButton4.setTextSize(20.0f);
            textView.setTextSize(25.0f);
            button.getLayoutParams().height = 60;
            button.getLayoutParams().width = 60;
        }
        if (!list.isEmpty()) {
            if (this.z.equals("SM-T116IR") && !g()) {
                radioButton2.setVisibility(8);
                textView.setText("USB device connected");
            }
            radioButton2.setText("Inbuild : " + str);
            radioButton = radioButton2;
            if (Build.VERSION.SDK_INT >= 21) {
                if (!list.contains("com.iritech.rdservice") || !str2.contains("IriTech")) {
                    i3 = 0;
                    i2 = 0;
                } else {
                    i3 = 0;
                    radioButton3.setVisibility(0);
                    radioButton3.setText("USB : IriShield 2120UL");
                    i2 = 1;
                }
                if (list.contains("com.mantra.mis100v2.rdservice") && str2.contains("MANTRA")) {
                    radioButton4.setVisibility(i3);
                    radioButton4.setText("USB : Mantra MIS 100V2");
                    i2++;
                }
                if (i2 == 0) {
                    if (!this.z.equals("SM-T116IR") || g()) {
                        str3 = "" + str + " connected";
                    } else {
                        str3 = "Please pass valid licence key \n to perform IRIS functionality.";
                    }
                    textView.setText(str3);
                }
            } else {
                if (list.contains("com.iritech.rdservice")) {
                    i = 0;
                    radioButton3.setVisibility(0);
                    radioButton3.setText("USB : IriShield 2120UL");
                } else {
                    i = 0;
                }
                if (list.contains("com.mantra.mis100v2.rdservice")) {
                    radioButton4.setVisibility(i);
                    radioButton4.setText("USB : Mantra MIS 100V2");
                }
            }
        } else {
            radioButton = radioButton2;
            dialog.dismiss();
            radioButton3.setChecked(false);
            radioButton4.setChecked(false);
            a(1);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        radioButton.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                IrisAuthenticationActivity.this.a(1);
                dialog.dismiss();
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                radioButton.setChecked(false);
                radioButton4.setChecked(false);
                IrisAuthenticationActivity.this.a(2);
                dialog.dismiss();
            }
        });
        radioButton4.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                radioButton.setChecked(false);
                radioButton3.setChecked(false);
                IrisAuthenticationActivity.this.a(3);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0117  */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void a(List<String> list, String str) {
        final Dialog dialog;
        Button button;
        int i;
        int i2;
        Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.iris_device_dialog);
        TextView textView = (TextView) dialog2.findViewById(R.id.txt_device_title);
        Button button2 = (Button) dialog2.findViewById(R.id.button_close);
        RadioButton radioButton = (RadioButton) dialog2.findViewById(R.id.radio_inbuild);
        final RadioButton radioButton2 = (RadioButton) dialog2.findViewById(R.id.radio_wired);
        final RadioButton radioButton3 = (RadioButton) dialog2.findViewById(R.id.radio_wired_mantra);
        radioButton.setVisibility(8);
        radioButton2.setVisibility(8);
        radioButton3.setVisibility(8);
        int i3 = getResources().getConfiguration().screenLayout & 15;
        if (3 == i3) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(20, 20, 0, 20);
            textView.setLayoutParams(layoutParams);
            radioButton.setTextSize(20.0f);
            radioButton2.setTextSize(20.0f);
            radioButton3.setTextSize(20.0f);
            textView.setTextSize(25.0f);
            button2.getLayoutParams().height = 60;
            button2.getLayoutParams().width = 60;
        }
        if (list.isEmpty()) {
            if (3 == i3) {
                textView.setText("Connected device not supported by Installed RD Service or may be USB permission denied by User. Or connected device not Registered");
            } else {
                textView.setText("Connected device not \nsupported by Installed RD \nService or may be USB permission denied by User. Or connected device not Registered");
            }
            dialog = dialog2;
            button = button2;
        } else {
            button = button2;
            dialog = dialog2;
            if (Build.VERSION.SDK_INT >= 21) {
                if (!list.contains("com.iritech.rdservice") || !str.contains("IriTech")) {
                    i = 0;
                } else {
                    textView.setText("IriShield 2120UL Connected");
                    radioButton2.setVisibility(0);
                    radioButton2.setText("USB : IriShield 2120UL");
                    i = 1;
                }
                if (list.contains("com.mantra.mis100v2.rdservice") && str.contains("MANTRA")) {
                    textView.setText("Mantra MIS 100V2 Connected");
                    i2 = 0;
                    radioButton3.setVisibility(i2);
                    radioButton3.setText("USB : Mantra MIS 100V2");
                    i++;
                }
                if (i == 2) {
                    textView.setText("Detected multiple devices.\nPlease select one of them.");
                }
                if (i == 0) {
                    if (3 == i3) {
                        textView.setText("Connected device not supported by Installed RD Service or may be USB permission denied by User. Or connected device not Registered");
                    } else {
                        textView.setText("Connected device not \nsupported by Installed RD \nService or may be USB permission denied by User. Or connected device not Registered");
                    }
                }
            } else {
                i2 = 0;
                if (list.contains("com.iritech.rdservice")) {
                    textView.setText("IriShield 2120UL Connected");
                    radioButton2.setVisibility(0);
                    radioButton2.setText("USB : IriShield 2120UL");
                    i = 1;
                } else {
                    i = 0;
                }
                if (list.contains("com.mantra.mis100v2.rdservice")) {
                    textView.setText("Mantra MIS 100V2 Connected");
                    radioButton3.setVisibility(i2);
                    radioButton3.setText("USB : Mantra MIS 100V2");
                    i++;
                }
                if (i == 2) {
                }
                if (i == 0) {
                }
            }
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                radioButton3.setChecked(false);
                IrisAuthenticationActivity.this.a(2);
                dialog.dismiss();
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                radioButton2.setChecked(false);
                IrisAuthenticationActivity.this.a(3);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override // com.nsdl.egov.esignaar.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1) {
            if (i == 999 && !g()) {
                g(F);
            }
        } else if (i2 == -1) {
            try {
                this.s = intent.getStringExtra(new String(this.E.doFinal(b(getResources().getString(R.string.n5065be31c3e5a299c584abbc2c5a3e77)))));
                if (this.s == null || this.s == "" || !this.s.contains("Resp")) {
                    android.app.AlertDialog create = new AlertDialog.Builder(this).create();
                    create.setMessage(getResources().getString(R.string.notiris));
                    create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.15
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i3) {
                            dialogInterface.dismiss();
                        }
                    });
                    create.show();
                } else if (this.s.contains("errCode=\"0\"")) {
                    final android.app.AlertDialog create2 = new AlertDialog.Builder(this).create();
                    create2.setMessage(getResources().getString(R.string.iris));
                    create2.setCanceledOnTouchOutside(false);
                    create2.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.13
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i3) {
                            create2.dismiss();
                        }
                    });
                    create2.show();
                    this.j.setEnabled(true);
                } else {
                    String string = XML.toJSONObject(this.s).getJSONObject(new String(this.E.doFinal(b(getResources().getString(R.string.n2d65a1818936149f006466b792506cf7))))).getJSONObject(new String(this.E.doFinal(b(getResources().getString(R.string.n289a21a685e759e174165b295a7abe80))))).getString(new String(this.E.doFinal(b(getResources().getString(R.string.n92802c4103d2f765c8063c169de82268)))));
                    android.app.AlertDialog create3 = new AlertDialog.Builder(this).create();
                    create3.setMessage("" + string);
                    create3.setCanceledOnTouchOutside(false);
                    create3.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.14
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i3) {
                            dialogInterface.dismiss();
                        }
                    });
                    create3.show();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_iris_authentication);
        this.b = Integer.parseInt(getResources().getString(R.string.n405f0ffcd4afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
        try {
            this.C = getResources().getString(R.string.b71fbb6c3854e097f8aa89a3c9ebc318d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf);
            this.C = this.C.substring(this.b, this.b * this.c);
            this.D = new SecretKeySpec(this.C.getBytes(), getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.E = Cipher.getInstance(getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.E.init(2, this.D);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        f();
        this.d.addTextChangedListener(new TextWatcher() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    return;
                }
                if ((editable.toString().length() > 12 && editable.toString().length() < 16) || editable.toString().length() < 12) {
                    IrisAuthenticationActivity.this.A.setChecked(false);
                    IrisAuthenticationActivity.this.f.setEnabled(false);
                    IrisAuthenticationActivity.this.A.setEnabled(true);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.A.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                String str;
                IrisAuthenticationActivity irisAuthenticationActivity;
                Resources resources;
                int i;
                if (IrisAuthenticationActivity.this.d.getText().toString() == null || TextUtils.isEmpty(IrisAuthenticationActivity.this.d.getText().toString())) {
                    IrisAuthenticationActivity.this.A.setChecked(false);
                    IrisAuthenticationActivity.this.f.setEnabled(false);
                    irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    resources = irisAuthenticationActivity.getResources();
                    i = R.string.enteraadharvid;
                } else if (!d.a(IrisAuthenticationActivity.this.d.getText().toString()) || !(IrisAuthenticationActivity.this.d.getText().toString().length() == 12 || IrisAuthenticationActivity.this.d.getText().toString().length() == 16)) {
                    IrisAuthenticationActivity.this.A.setChecked(false);
                    irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    resources = irisAuthenticationActivity.getResources();
                    i = R.string.validenteraadharvid;
                } else {
                    IrisAuthenticationActivity irisAuthenticationActivity2 = IrisAuthenticationActivity.this;
                    if (z) {
                        irisAuthenticationActivity2.A.setEnabled(false);
                        IrisAuthenticationActivity.this.f.setEnabled(true);
                        return;
                    }
                    irisAuthenticationActivity2.f.setEnabled(false);
                    irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    str = "Please agree all the conditions.";
                    irisAuthenticationActivity.c(str);
                }
                str = resources.getString(i);
                irisAuthenticationActivity.c(str);
            }
        });
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.IrisAuthenticationActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                List<ResolveInfo> queryIntentActivities = IrisAuthenticationActivity.this.getPackageManager().queryIntentActivities(new Intent("in.gov.uidai.rdservice.iris.CAPTURE"), 0);
                ArrayList arrayList = new ArrayList();
                for (ResolveInfo resolveInfo : queryIntentActivities) {
                    arrayList.add(resolveInfo.activityInfo.packageName);
                }
                HashMap<String, UsbDevice> deviceList = ((UsbManager) IrisAuthenticationActivity.this.getSystemService("usb")).getDeviceList();
                String str = "";
                if (deviceList.size() > 0) {
                    for (UsbDevice usbDevice : deviceList.values()) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            str = usbDevice.getManufacturerName();
                        }
                    }
                }
                if (IrisAuthenticationActivity.this.a()) {
                    IrisAuthenticationActivity irisAuthenticationActivity = IrisAuthenticationActivity.this;
                    if (irisAuthenticationActivity.a(irisAuthenticationActivity.z)) {
                        IrisAuthenticationActivity irisAuthenticationActivity2 = IrisAuthenticationActivity.this;
                        irisAuthenticationActivity2.a(irisAuthenticationActivity2.z, arrayList, str);
                        return;
                    }
                }
                if (IrisAuthenticationActivity.this.a()) {
                    IrisAuthenticationActivity irisAuthenticationActivity3 = IrisAuthenticationActivity.this;
                    if (!irisAuthenticationActivity3.a(irisAuthenticationActivity3.z)) {
                        IrisAuthenticationActivity.this.a(arrayList, str);
                        return;
                    }
                }
                IrisAuthenticationActivity irisAuthenticationActivity4 = IrisAuthenticationActivity.this;
                if (irisAuthenticationActivity4.a(irisAuthenticationActivity4.z)) {
                    IrisAuthenticationActivity.this.a(1);
                } else {
                    Toast.makeText(IrisAuthenticationActivity.this, "Please connect IRIS device.", 0).show();
                }
            }
        });
        this.f.setEnabled(false);
        this.t = null;
        try {
            this.t = new IrisAuthenticationActivity().f("2.5IYNNN");
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        if (!this.B.equals("NA") && this.z.equals("SM-T116IR")) {
            e();
        }
    }
}
