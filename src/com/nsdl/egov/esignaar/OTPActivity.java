package com.nsdl.egov.esignaar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
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
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.internal.cache.DiskLruCache;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
/* loaded from: classes3.dex */
public class OTPActivity extends BaseActivity implements View.OnClickListener {
    TextView A;
    CheckBox B;
    ImageView C;
    CountDownTimer D;
    EditText E;
    TextView s;
    TextView t;
    Button u;
    Button v;
    Button w;
    Button x;
    Button y;
    EditText z;
    String d = "";
    String e = "";
    String f = "";
    String g = "";
    String h = "";
    String i = "";
    String j = "";
    String k = "";
    String l = "";
    String m = "";
    String n = "";
    int o = 0;
    int p = 0;
    int q = 1;
    String r = "30";
    public int F = 0;
    public int G = 0;
    String H = null;
    Key I = null;
    Cipher J = null;

    private void e() {
        this.E = (EditText) findViewById(R.id.otp_aadhaar);
        this.t = (TextView) findViewById(R.id.txt_adhaarText);
        this.u = (Button) findViewById(R.id.btn_generateOtp);
        this.v = (Button) findViewById(R.id.btn_cancelOtp);
        this.w = (Button) findViewById(R.id.btn_submitButton);
        this.x = (Button) findViewById(R.id.btn_cancelButton);
        this.y = (Button) findViewById(R.id.btn_resendOtp);
        this.z = (EditText) findViewById(R.id.editText_otp);
        this.A = (TextView) findViewById(R.id.tv_enterOtp);
        this.B = (CheckBox) findViewById(R.id.checkbox_consentOtp);
        this.C = (ImageView) findViewById(R.id.image_eye);
        this.s = (TextView) findViewById(R.id.txt_displaytimer);
        this.u.setOnClickListener(this);
        this.v.setOnClickListener(this);
        this.w.setOnClickListener(this);
        this.x.setOnClickListener(this);
        this.z.setOnClickListener(this);
        this.y.setOnClickListener(this);
        if (3 == (getResources().getConfiguration().screenLayout & 15)) {
            this.E.getLayoutParams().height = 60;
            this.u.getLayoutParams().height = 60;
            this.v.getLayoutParams().height = 60;
            this.A.getLayoutParams().height = 60;
            this.w.getLayoutParams().height = 60;
            this.x.getLayoutParams().height = 60;
            this.y.getLayoutParams().height = 60;
            this.z.getLayoutParams().height = 60;
            this.C.getLayoutParams().height = 60;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.E.getLayoutParams();
            layoutParams.setMargins(25, 25, 50, 25);
            this.E.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.t.getLayoutParams();
            layoutParams2.setMargins(50, 25, 0, 0);
            this.t.setLayoutParams(layoutParams2);
            this.t.setGravity(17);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.u.getLayoutParams();
            layoutParams3.setMargins(50, 25, 25, 0);
            this.u.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.v.getLayoutParams();
            layoutParams4.setMargins(0, 25, 50, 0);
            this.v.setLayoutParams(layoutParams4);
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.s.getLayoutParams();
            layoutParams5.setMargins(50, 25, 50, 0);
            this.s.setLayoutParams(layoutParams5);
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.w.getLayoutParams();
            layoutParams6.setMargins(50, 20, 25, 0);
            this.w.setLayoutParams(layoutParams6);
            LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) this.x.getLayoutParams();
            layoutParams7.setMargins(0, 20, 50, 0);
            this.x.setLayoutParams(layoutParams7);
            LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) this.A.getLayoutParams();
            layoutParams8.setMargins(50, 0, 0, 0);
            this.A.setLayoutParams(layoutParams8);
            LinearLayout.LayoutParams layoutParams9 = (LinearLayout.LayoutParams) this.y.getLayoutParams();
            layoutParams9.setMargins(0, 0, 50, 5);
            this.y.setLayoutParams(layoutParams9);
            LinearLayout.LayoutParams layoutParams10 = (LinearLayout.LayoutParams) this.z.getLayoutParams();
            layoutParams10.setMargins(50, 0, 0, 0);
            this.z.setLayoutParams(layoutParams10);
            LinearLayout.LayoutParams layoutParams11 = (LinearLayout.LayoutParams) this.C.getLayoutParams();
            layoutParams11.setMargins(0, 0, 25, 0);
            this.C.setLayoutParams(layoutParams11);
            LinearLayout.LayoutParams layoutParams12 = (LinearLayout.LayoutParams) this.B.getLayoutParams();
            layoutParams12.setMargins(40, 0, 0, 0);
            this.B.setLayoutParams(layoutParams12);
        }
        try {
            this.e = getIntent().getStringExtra(new String(this.J.doFinal(b(getResources().getString(R.string.n4a761a8a5ed458635e70b25419c97627)))));
            this.d = getIntent().getStringExtra(new String(this.J.doFinal(b(getResources().getString(R.string.n0fdcd355f1e769b60910fc8fb72f5ce1)))));
            this.f = getIntent().getStringExtra(new String(this.J.doFinal(b(getResources().getString(R.string.n39a642b94c3cb81529a43b45c5d4147f)))));
            this.g = getIntent().getStringExtra(new String(this.J.doFinal(b(getResources().getString(R.string.n8b296e11dfa2587332551af1eff3d9e4)))));
            this.h = getIntent().getStringExtra(new String(this.J.doFinal(b(getResources().getString(R.string.n9ca3b84268985432760fd8cde7c2de09)))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.z.setEnabled(false);
        this.u.setEnabled(false);
    }

    private void f(String str) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        create.setTitle("Terms & Conditions");
        create.setMessage(str);
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                create.setCanceledOnTouchOutside(false);
                dialogInterface.dismiss();
            }
        });
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(String str) {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage("" + str);
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                OTPActivity.this.G++;
                OTPActivity.this.D = new CountDownTimer(30000, 1000) { // from class: com.nsdl.egov.esignaar.OTPActivity.3.1
                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                        OTPActivity.this.s.setVisibility(8);
                        if (OTPActivity.this.F < 3) {
                            OTPActivity.this.y.setEnabled(true);
                        } else {
                            OTPActivity.this.y.setEnabled(false);
                        }
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) OTPActivity.this.w.getLayoutParams();
                        layoutParams.setMargins(50, 40, 25, 0);
                        OTPActivity.this.w.setLayoutParams(layoutParams);
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) OTPActivity.this.x.getLayoutParams();
                        layoutParams2.setMargins(0, 40, 50, 0);
                        OTPActivity.this.x.setLayoutParams(layoutParams2);
                    }

                    @Override // android.os.CountDownTimer
                    public void onTick(long j) {
                        TextView textView;
                        String str2;
                        if (OTPActivity.this.F < 3) {
                            OTPActivity.this.s.setVisibility(0);
                            textView = OTPActivity.this.s;
                            str2 = "<b>Info !</b><br>" + ("Resend attempts remaining " + (3 - OTPActivity.this.G) + ".") + "<br>" + ("Resend OTP will be enabled in " + (j / 1000) + " seconds.");
                        } else {
                            OTPActivity.this.s.setVisibility(0);
                            textView = OTPActivity.this.s;
                            str2 = "<b>Info !</b><br>" + ("Resend attempts remaining " + (3 - OTPActivity.this.G) + ".");
                        }
                        textView.setText(Html.fromHtml(str2));
                        OTPActivity.this.s.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    }
                }.start();
                OTPActivity.this.F++;
            }
        });
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h(String str) {
        AlertDialog create = new AlertDialog.Builder(this).create();
        create.setMessage("" + str);
        create.setCanceledOnTouchOutside(false);
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                OTPActivity.this.u.setEnabled(false);
                OTPActivity.this.E.setEnabled(false);
                OTPActivity.this.D = new CountDownTimer(30000, 1000) { // from class: com.nsdl.egov.esignaar.OTPActivity.4.1
                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                        OTPActivity.this.s.setVisibility(8);
                        OTPActivity.this.u.setEnabled(true);
                        OTPActivity.this.E.setEnabled(true);
                    }

                    @Override // android.os.CountDownTimer
                    public void onTick(long j) {
                        OTPActivity.this.s.setVisibility(0);
                        TextView textView = OTPActivity.this.s;
                        textView.setText(Html.fromHtml("<b>Info !</b><br>" + ("Generate OTP will be enabled in " + (j / 1000) + " seconds.") + "<br>"));
                        OTPActivity.this.s.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    }
                }.start();
            }
        });
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i(String str) {
        try {
            Intent intent = new Intent();
            intent.putExtra(new String(this.J.doFinal(b(getResources().getString(R.string.n262f6be2018ae99d5125822e2c4241fe3c5e5d5179e1b355ebc98154bdd95ef0)))), str);
            setResult(5000, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            if (this.e != null) {
                this.r = this.e.equalsIgnoreCase(new String(this.J.doFinal(b(getResources().getString(R.string.nbb5db6596c7ab75a351056b8eb448990))))) ? "30" : "10";
            } else {
                c(getResources().getString(R.string.Somethingwentwrong));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))), this.f);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n1c7911f1c75e50ddf3c559dcca8e0f2b)))), this.g);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n605f17671bce426439e2edd7fe64b905)))), this.E.getText().toString());
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n2335314313a1d742e15c4dcb751e4d9e)))), str);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.ncbb6b12854cabf96ba0549fe0af9dfba)))), this.h);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.nc5afc2f3dae3ae4744e981b21428c50f)))), this.i);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AnonymousClass13 r9 = new StringRequest(1, f36a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.OTPActivity.11
            /* renamed from: a */
            public void onResponse(String str3) {
                ProgressDialog progressDialog2;
                if (str3 != null) {
                    try {
                        if (!str3.isEmpty() && str3.contains("xml")) {
                            OTPActivity.this.e(str3.toString());
                            try {
                                JSONObject jSONObject = XML.toJSONObject(str3.toString()).getJSONObject(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                                OTPActivity.this.l = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                                OTPActivity.this.m = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b)))));
                                OTPActivity.this.n = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd)))));
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            if (OTPActivity.this.l.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                                AlertDialog create = new AlertDialog.Builder(OTPActivity.this).create();
                                create.setMessage("OTP is sent to the mobile number registered with UIDAI and it is valid for " + OTPActivity.this.r + " minutes. If you have not received the OTP then click on 'Resend OTP' button after 30 seconds to 'Regenerate OTP'.");
                                create.setCanceledOnTouchOutside(false);
                                create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.11.1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        OTPActivity.this.w.setEnabled(false);
                                        OTPActivity.this.z.setEnabled(true);
                                        OTPActivity.this.E.setEnabled(false);
                                        OTPActivity.this.u.setEnabled(false);
                                        OTPActivity.this.v.setEnabled(false);
                                        OTPActivity.this.z.setVisibility(0);
                                        OTPActivity.this.C.setVisibility(0);
                                        OTPActivity.this.A.setVisibility(0);
                                        OTPActivity.this.y.setVisibility(0);
                                        OTPActivity.this.x.setVisibility(0);
                                        OTPActivity.this.w.setVisibility(0);
                                        dialogInterface.dismiss();
                                        OTPActivity.this.D = new CountDownTimer(30000, 1000) { // from class: com.nsdl.egov.esignaar.OTPActivity.11.1.1
                                            @Override // android.os.CountDownTimer
                                            public void onFinish() {
                                                OTPActivity.this.s.setVisibility(8);
                                                OTPActivity.this.y.setEnabled(true);
                                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) OTPActivity.this.w.getLayoutParams();
                                                layoutParams.setMargins(50, 40, 25, 0);
                                                OTPActivity.this.w.setLayoutParams(layoutParams);
                                                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) OTPActivity.this.x.getLayoutParams();
                                                layoutParams2.setMargins(0, 40, 50, 0);
                                                OTPActivity.this.x.setLayoutParams(layoutParams2);
                                            }

                                            @Override // android.os.CountDownTimer
                                            public void onTick(long j) {
                                                OTPActivity.this.s.setVisibility(0);
                                                TextView textView = OTPActivity.this.s;
                                                textView.setText(Html.fromHtml("<b>Info !</b><br>" + ("Resend OTP will be enabled in " + (j / 1000) + " seconds.")));
                                                OTPActivity.this.s.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                                            }
                                        }.start();
                                    }
                                });
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                create.show();
                                return;
                            }
                            if (OTPActivity.this.n.equalsIgnoreCase("952")) {
                                OTPActivity.this.h(OTPActivity.this.m);
                                if (progressDialog != null) {
                                    progressDialog2 = progressDialog;
                                } else {
                                    return;
                                }
                            } else if (OTPActivity.this.l.equalsIgnoreCase("0")) {
                                OTPActivity.this.i(str3.toString());
                                if (progressDialog != null) {
                                    progressDialog2 = progressDialog;
                                } else {
                                    return;
                                }
                            } else {
                                OTPActivity.this.i(OTPActivity.this.getResources().getString(R.string.Somethingwentwrong));
                                if (progressDialog != null) {
                                    progressDialog2 = progressDialog;
                                } else {
                                    return;
                                }
                            }
                            progressDialog2.dismiss();
                        }
                    } catch (JSONException e4) {
                        e4.printStackTrace();
                        OTPActivity oTPActivity = OTPActivity.this;
                        oTPActivity.i(oTPActivity.getResources().getString(R.string.Somethingwentwrong));
                        ProgressDialog progressDialog3 = progressDialog;
                        if (progressDialog3 != null) {
                            progressDialog3.dismiss();
                            return;
                        }
                        return;
                    }
                }
                OTPActivity.this.i(OTPActivity.this.getResources().getString(R.string.Somethingwentwrong));
                if (progressDialog != null) {
                    progressDialog2 = progressDialog;
                    progressDialog2.dismiss();
                }
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.12
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                OTPActivity oTPActivity;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                if (volleyError instanceof NetworkError) {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Somethingwentwrong;
                }
                oTPActivity.i(resources.getString(i));
                ProgressDialog progressDialog2 = progressDialog;
                if (progressDialog2 != null) {
                    progressDialog2.dismiss();
                }
            }
        }) { // from class: com.nsdl.egov.esignaar.OTPActivity.13
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        r9.setRetryPolicy(new b(300000, 1, 0.0f));
        newRequestQueue.add(r9);
    }

    public void b(String str, String str2) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(str2);
        progressDialog.setMessage(getResources().getString(R.string.loding));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        c.a();
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))), this.f);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n1c7911f1c75e50ddf3c559dcca8e0f2b)))), this.g);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n605f17671bce426439e2edd7fe64b905)))), this.E.getText().toString());
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n2335314313a1d742e15c4dcb751e4d9e)))), str);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.ncbb6b12854cabf96ba0549fe0af9dfba)))), this.h);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.nc5afc2f3dae3ae4744e981b21428c50f)))), this.i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AnonymousClass2 r9 = new StringRequest(1, f36a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.OTPActivity.14
            /* renamed from: a */
            public void onResponse(String str3) {
                ProgressDialog progressDialog2;
                OTPActivity oTPActivity;
                String str4;
                if (str3 != null) {
                    try {
                        if (!str3.isEmpty() && str3.contains("xml")) {
                            try {
                                JSONObject jSONObject = XML.toJSONObject(str3.toString()).getJSONObject(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                                OTPActivity.this.l = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                                OTPActivity.this.m = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b)))));
                                OTPActivity.this.n = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd)))));
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            if (OTPActivity.this.l.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                                OTPActivity.this.o = 0;
                                OTPActivity.this.e(str3.toString());
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                oTPActivity = OTPActivity.this;
                                str4 = "OTP is generated and sent to the registered mobile number.";
                            } else if (OTPActivity.this.n.equalsIgnoreCase("952")) {
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                oTPActivity = OTPActivity.this;
                                str4 = "" + OTPActivity.this.m;
                            } else {
                                OTPActivity.this.i(str3.toString());
                                if (progressDialog != null) {
                                    progressDialog2 = progressDialog;
                                    progressDialog2.dismiss();
                                }
                                return;
                            }
                            oTPActivity.g(str4);
                            return;
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                        OTPActivity oTPActivity2 = OTPActivity.this;
                        oTPActivity2.i(oTPActivity2.getResources().getString(R.string.Somethingwentwrong));
                        ProgressDialog progressDialog3 = progressDialog;
                        if (progressDialog3 != null) {
                            progressDialog3.dismiss();
                            return;
                        }
                        return;
                    }
                }
                OTPActivity.this.i(OTPActivity.this.getResources().getString(R.string.Somethingwentwrong));
                if (progressDialog != null) {
                    progressDialog2 = progressDialog;
                    progressDialog2.dismiss();
                }
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.15
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                OTPActivity oTPActivity;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                if (volleyError instanceof NetworkError) {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Somethingwentwrong;
                }
                oTPActivity.i(resources.getString(i));
                ProgressDialog progressDialog2 = progressDialog;
                if (progressDialog2 != null) {
                    progressDialog2.dismiss();
                }
            }
        }) { // from class: com.nsdl.egov.esignaar.OTPActivity.2
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        r9.setRetryPolicy(new b(300000, 1, 0.0f));
        newRequestQueue.add(r9);
    }

    public void c(final String str, String str2) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(str2);
        progressDialog.setMessage(getResources().getString(R.string.loding));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        c.a();
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))), this.f);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n1c7911f1c75e50ddf3c559dcca8e0f2b)))), this.g);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n596241dbd44311c1aee6306d36c45273)))), this.E.getText().toString());
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n2335314313a1d742e15c4dcb751e4d9e)))), str);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.na1cf783d5b474e2e4ff892fbec2fb40f)))), this.j);
            hashMap.put(new String(this.J.doFinal(b(getResources().getString(R.string.n48edd936798f54904a7fc519494e528f)))), this.k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AnonymousClass7 r10 = new StringRequest(1, f36a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.OTPActivity.5
            /* renamed from: a */
            public void onResponse(String str3) {
                ProgressDialog progressDialog2;
                if (str3 != null) {
                    try {
                        if (!str3.isEmpty() && str3.contains("xml")) {
                            OTPActivity.this.z.getText().clear();
                            if (str.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                                OTPActivity.this.i(str3.toString());
                                OTPActivity.this.c("User opted termination of eSign process by clicking Cancel button.");
                                if (progressDialog != null) {
                                    progressDialog2 = progressDialog;
                                } else {
                                    return;
                                }
                            } else {
                                try {
                                    JSONObject jSONObject = XML.toJSONObject(str3.toString()).getJSONObject(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                                    OTPActivity.this.l = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                                    OTPActivity.this.m = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b)))));
                                    OTPActivity.this.n = jSONObject.getString(new String(OTPActivity.this.J.doFinal(BaseActivity.b(OTPActivity.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd)))));
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                if (OTPActivity.this.l.equalsIgnoreCase("0")) {
                                    OTPActivity.this.o++;
                                    if (OTPActivity.this.o < 3) {
                                        OTPActivity.this.p = 3 - OTPActivity.this.o;
                                        if (OTPActivity.this.n.equalsIgnoreCase("400")) {
                                            AlertDialog create = new AlertDialog.Builder(OTPActivity.this).create();
                                            create.setMessage(OTPActivity.this.m);
                                            create.setCanceledOnTouchOutside(false);
                                            create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.5.1
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    if (progressDialog != null) {
                                                        progressDialog.dismiss();
                                                    }
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            create.show();
                                            Toast makeText = Toast.makeText(OTPActivity.this.getApplicationContext(), "Total invalid OTP Attempts : 3 and Attempts Remaining : " + OTPActivity.this.p, 1);
                                            ((TextView) makeText.getView().findViewById(16908299)).setTextColor(SupportMenu.CATEGORY_MASK);
                                            makeText.show();
                                            return;
                                        }
                                        OTPActivity.this.i(str3.toString());
                                        OTPActivity.this.c(OTPActivity.this.m);
                                        if (progressDialog != null) {
                                            progressDialog2 = progressDialog;
                                        } else {
                                            return;
                                        }
                                    } else {
                                        OTPActivity.this.i(str3.toString());
                                        OTPActivity.this.c(OTPActivity.this.getResources().getString(R.string.error));
                                        if (progressDialog != null) {
                                            progressDialog2 = progressDialog;
                                        } else {
                                            return;
                                        }
                                    }
                                } else {
                                    OTPActivity.this.i(str3.toString());
                                    OTPActivity.this.c(OTPActivity.this.getResources().getString(R.string.eSignsuccessfullforgiveninputxmlrequest));
                                    if (progressDialog != null) {
                                        progressDialog2 = progressDialog;
                                    } else {
                                        return;
                                    }
                                }
                            }
                            progressDialog2.dismiss();
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                        OTPActivity oTPActivity = OTPActivity.this;
                        oTPActivity.i(oTPActivity.getResources().getString(R.string.Somethingwentwrong));
                        ProgressDialog progressDialog3 = progressDialog;
                        if (progressDialog3 != null) {
                            progressDialog3.dismiss();
                            return;
                        }
                        return;
                    }
                }
                OTPActivity.this.i(OTPActivity.this.getResources().getString(R.string.Somethingwentwrong));
                if (progressDialog != null) {
                    progressDialog2 = progressDialog;
                    progressDialog2.dismiss();
                }
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.6
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                OTPActivity oTPActivity;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                if (volleyError instanceof NetworkError) {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.Somethingwentwrong;
                }
                oTPActivity.i(resources.getString(i));
                ProgressDialog progressDialog2 = progressDialog;
                if (progressDialog2 != null) {
                    progressDialog2.dismiss();
                }
            }
        }) { // from class: com.nsdl.egov.esignaar.OTPActivity.7
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        r10.setRetryPolicy(new b(300000, 1, 0.0f));
        newRequestQueue.add(r10);
    }

    public void e(String str) {
        try {
            this.j = XML.toJSONObject(str.toString()).getJSONObject("EsignResp").getString("ts");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:
        if (d() != false) goto L_0x003f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:
        c(androidx.exifinterface.media.ExifInterface.GPS_MEASUREMENT_2D, getResources().getString(com.surepass.surepassesign.R.string.otpcancel));
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0096, code lost:
        if (d() != false) goto L_0x003f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    @Override // android.view.View.OnClickListener
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void onClick(View view) {
        Button button;
        int id = view.getId();
        if (id == R.id.btn_generateOtp) {
            if (d()) {
                a(DiskLruCache.VERSION_1, getResources().getString(R.string.otprequest));
                button = this.u;
                button.setEnabled(false);
                return;
            }
            c(getResources().getString(R.string.networknotavailable));
        } else if (id != R.id.btn_cancelOtp) {
            if (id == R.id.btn_resendOtp) {
                if (d()) {
                    if (this.F < 3) {
                        b(DiskLruCache.VERSION_1, getResources().getString(R.string.otpregenerate));
                        this.u.setEnabled(false);
                        this.v.setEnabled(false);
                        button = this.y;
                        button.setEnabled(false);
                        return;
                    }
                    return;
                }
            } else if (id == R.id.btn_submitButton) {
                if (d()) {
                    c(DiskLruCache.VERSION_1, getResources().getString(R.string.esignprogress));
                    return;
                }
            } else if (id != R.id.btn_cancelButton) {
                return;
            }
            c(getResources().getString(R.string.networknotavailable));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_otp);
        this.b = Integer.parseInt(getResources().getString(R.string.n405f0ffcd4afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
        try {
            this.H = getResources().getString(R.string.b71fbb6c3854e097f8aa89a3c9ebc318d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf);
            this.H = this.H.substring(this.b, this.b * this.c);
            this.I = new SecretKeySpec(this.H.getBytes(), getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.J = Cipher.getInstance(getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.J.init(2, this.I);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.F = 0;
        e();
        this.B.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Resources resources;
                OTPActivity oTPActivity;
                int i;
                String str;
                OTPActivity oTPActivity2;
                if (OTPActivity.this.E.getText().toString() == null || TextUtils.isEmpty(OTPActivity.this.E.getText().toString())) {
                    OTPActivity.this.B.setChecked(false);
                    OTPActivity.this.u.setEnabled(false);
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.enteraadharvid;
                } else if (!d.a(OTPActivity.this.E.getText().toString()) || !(OTPActivity.this.E.getText().toString().length() == 12 || OTPActivity.this.E.getText().toString().length() == 16)) {
                    OTPActivity.this.B.setChecked(false);
                    oTPActivity = OTPActivity.this;
                    resources = oTPActivity.getResources();
                    i = R.string.validenteraadharvid;
                } else {
                    OTPActivity.this.u.setEnabled(true);
                    if (z) {
                        OTPActivity.this.B.setEnabled(false);
                    }
                    if (OTPActivity.this.B.isChecked()) {
                        oTPActivity2 = OTPActivity.this;
                        str = "true";
                    } else {
                        oTPActivity2 = OTPActivity.this;
                        str = "false";
                    }
                    oTPActivity2.i = str;
                    return;
                }
                oTPActivity.c(resources.getString(i));
            }
        });
        this.E.addTextChangedListener(new TextWatcher() { // from class: com.nsdl.egov.esignaar.OTPActivity.8
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    return;
                }
                if ((editable.toString().length() > 12 && editable.toString().length() < 16) || editable.toString().length() < 12) {
                    OTPActivity.this.B.setChecked(false);
                    OTPActivity.this.u.setEnabled(false);
                    OTPActivity.this.B.setEnabled(true);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        String str = "";
        if (!d(this.d)) {
            this.d = this.d.replace("<b>", str);
            this.d = this.d.replace("<br>", str);
            this.d = this.d.replace("</b>", str);
            StringTokenizer stringTokenizer = new StringTokenizer(this.d.trim(), "^^");
            while (stringTokenizer.hasMoreTokens()) {
                str = str + IOUtils.LINE_SEPARATOR_UNIX + stringTokenizer.nextToken();
            }
        }
        f(str);
        this.z.addTextChangedListener(new TextWatcher() { // from class: com.nsdl.egov.esignaar.OTPActivity.9
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Button button;
                boolean z = false;
                OTPActivity.this.u.setEnabled(false);
                OTPActivity.this.v.setEnabled(false);
                OTPActivity oTPActivity = OTPActivity.this;
                oTPActivity.k = oTPActivity.z.getText().toString();
                OTPActivity.this.z.setSelection(OTPActivity.this.z.getText().length());
                if (OTPActivity.this.k.length() != 6) {
                    button = OTPActivity.this.w;
                } else {
                    OTPActivity.this.x.setVisibility(0);
                    z = true;
                    OTPActivity.this.w.setEnabled(true);
                    button = OTPActivity.this.x;
                }
                button.setEnabled(z);
                OTPActivity.this.C.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.OTPActivity.9.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        OTPActivity oTPActivity2;
                        int i = 1;
                        if (OTPActivity.this.q == 1) {
                            OTPActivity.this.z.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            oTPActivity2 = OTPActivity.this;
                            i = 0;
                        } else if (OTPActivity.this.q == 0) {
                            OTPActivity.this.z.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            oTPActivity2 = OTPActivity.this;
                        } else {
                            return;
                        }
                        oTPActivity2.q = i;
                    }
                });
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }
}
