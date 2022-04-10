package com.nsdl.egov.esignaar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.exifinterface.media.ExifInterface;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.surepass.surepassesign.R;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.internal.cache.DiskLruCache;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
/* loaded from: classes3.dex */
public class NsdlEsignActivity extends BaseActivity {
    String d;
    String e;
    String f;
    String h;
    String j;
    int l;
    String g = null;
    int i = 100;
    String k = "";
    String m = null;
    String n = null;
    Key o = null;
    Cipher p = null;
    String q = null;

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Intent intent, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        try {
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.na522903e91d6649cbd603332e5077861)))), str);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n39a642b94c3cb81529a43b45c5d4147f)))), str2);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n8b296e11dfa2587332551af1eff3d9e4)))), str3);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n06a9f5bb02abc5058b2d8f2302a2738d)))), str4);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n4a761a8a5ed458635e70b25419c97627)))), str5);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n0fdcd355f1e769b60910fc8fb72f5ce1)))), str6);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n9ca3b84268985432760fd8cde7c2de09)))), str7);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n0fb405858a246a14e79e325ec52bd6bf5d6cbee1b0b6dab4e949db563554cff9)))), str8);
            intent.putExtra(new String(this.p.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2)))), str9);
            startActivityForResult(intent, this.i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(String str) {
        try {
            Intent intent = new Intent();
            String str2 = new String(this.p.doFinal(b(getResources().getString(R.string.n6efbafa1ab09642fa6838bf20db3403c))));
            intent.putExtra(str2, "" + str);
            setResult(-1, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        finish();
        System.exit(0);
    }

    public void e() {
        String str;
        Bundle extras = getIntent().getExtras();
        this.m = Build.MODEL;
        this.l = Build.VERSION.SDK_INT;
        this.b = Integer.parseInt(getResources().getString(R.string.n405f0ffcd4afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
        try {
            this.n = getResources().getString(R.string.b71fbb6c3854e097f8aa89a3c9ebc318d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf);
            this.n = this.n.substring(this.b, this.b * this.c);
            this.o = new SecretKeySpec(this.n.getBytes(), getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.p = Cipher.getInstance(getResources().getString(R.string.afa4d88b71f405f0ff405f0ffbb6c3854e097f8aa89a3c9ebc31405f0ffcd4afa4d888d65dd4405f0ffcd4afa4d88fbe561160f5ce2Kf));
            this.p.init(2, this.o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extras != null) {
            try {
                this.d = extras.getString(new String(this.p.doFinal(b(getResources().getString(R.string.nffd50925ba513c45ddc02a6b6110995b)))));
                this.e = extras.getString(new String(this.p.doFinal(b(getResources().getString(R.string.n4a761a8a5ed458635e70b25419c97627)))));
                this.h = extras.getString(new String(this.p.doFinal(b(getResources().getString(R.string.n06a9f5bb02abc5058b2d8f2302a2738d)))));
                Bundle extras2 = getIntent().getExtras();
                if (extras2 != null) {
                    this.q = extras2.containsKey(new String(this.p.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2))))) ? extras2.getString(new String(this.p.doFinal(b(getResources().getString(R.string.nd961c5428e915e5a83ca5401f8f953f2))))) : "NA";
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            f();
        }
        String str2 = this.e;
        if (str2 != null) {
            try {
                f36a = str2.equalsIgnoreCase(new String(this.p.doFinal(b(getResources().getString(R.string.nbb5db6596c7ab75a351056b8eb448990))))) ? new String(this.p.doFinal(b(getResources().getString(R.string.n57b4d2430fd03265753b22acae9989d72f2451dfaf9abf07c63b537711d6fc0772ddd587e4938d0dc77c138704f9f5ce20bd4ea62ffada667e371df7a1450b4e)))) : new String(this.p.doFinal(b(getResources().getString(R.string.n274b5c7933864679323b0aec6d4b0b2d090f339db2eb5562387fc9f0edaa7e2598d86e656ddbd61a66328bb78ba70261c35ae9865aa76239078882e6a3e86e03))));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            if (d()) {
                PackageManager packageManager = getPackageManager();
                try {
                    JSONObject jSONObject = XML.toJSONObject(this.d).getJSONObject("Esign");
                    if (jSONObject.has("AuthMode")) {
                        String string = jSONObject.getString("AuthMode");
                        char c = 65535;
                        switch (string.hashCode()) {
                            case 49:
                                if (string.equals(DiskLruCache.VERSION_1)) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 50:
                                if (string.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 51:
                                if (string.equals(ExifInterface.GPS_MEASUREMENT_3D)) {
                                    c = 2;
                                    break;
                                }
                                break;
                        }
                        if (c != 0) {
                            if (c == 1) {
                                List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent("in.gov.uidai.rdservice.fp.CAPTURE"), 0);
                                if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
                                    str = "Please install respective RdService.";
                                }
                            } else if (c != 2) {
                                str = "Please provide valid AuthMode";
                            } else {
                                List<ResolveInfo> queryIntentActivities2 = getPackageManager().queryIntentActivities(new Intent("in.gov.uidai.rdservice.iris.CAPTURE"), 0);
                                ArrayList arrayList = new ArrayList();
                                for (ResolveInfo resolveInfo : queryIntentActivities2) {
                                    arrayList.add(resolveInfo.activityInfo.packageName);
                                }
                                if (!a(this.m) && !arrayList.contains("com.iritech.rdservice") && !arrayList.contains("com.mantra.mis100v2.rdservice")) {
                                    str = "IRIS functionality not available for this device or Respective RdService is not installed.";
                                }
                            }
                        }
                        g();
                        return;
                    }
                    str = "Please provide valid request XML";
                    e(str);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                    e(getResources().getString(R.string.Somethingwentwrong));
                }
            } else {
                c(getResources().getString(R.string.networknotavailable));
            }
        } else {
            f();
        }
    }

    public void f() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.force_exit_dialog, (ViewGroup) null);
        builder.setView(inflate);
        builder.setCancelable(false);
        builder.setTitle("Not correct way to open the Apk");
        final AlertDialog create = builder.create();
        ((Button) inflate.findViewById(R.id.btn_Ok)).setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.NsdlEsignActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                create.dismiss();
                NsdlEsignActivity.this.h();
            }
        });
        create.show();
    }

    public void g() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.pleasewait));
        progressDialog.setMessage(getResources().getString(R.string.loding));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        final HashMap hashMap = new HashMap();
        try {
            hashMap.put(new String(this.p.doFinal(b(getResources().getString(R.string.nffd50925ba513c45ddc02a6b6110995b)))), this.d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.a();
        AnonymousClass4 r1 = new StringRequest(1, f36a, new Response.Listener<String>() { // from class: com.nsdl.egov.esignaar.NsdlEsignActivity.2
            /* JADX WARN: Removed duplicated region for block: B:27:0x0180 A[Catch: Exception -> 0x02f0, TryCatch #0 {Exception -> 0x02f0, blocks: (B:8:0x0018, B:10:0x00b6, B:12:0x00be, B:13:0x00e7, B:14:0x0108, B:15:0x0129, B:24:0x015c, B:25:0x0178, B:27:0x0180, B:28:0x019f, B:29:0x01a8, B:32:0x01b4, B:34:0x01c4, B:36:0x01ca, B:38:0x01ec, B:41:0x01f4, B:42:0x0214, B:43:0x0222, B:44:0x0227, B:46:0x022f, B:47:0x025b, B:49:0x0261, B:50:0x026f, B:53:0x027d, B:55:0x0283, B:57:0x028f, B:59:0x029b, B:61:0x02a2, B:62:0x02b2, B:63:0x02d3, B:64:0x02e3), top: B:69:0x0018 }] */
            /* JADX WARN: Removed duplicated region for block: B:29:0x01a8 A[Catch: Exception -> 0x02f0, TRY_LEAVE, TryCatch #0 {Exception -> 0x02f0, blocks: (B:8:0x0018, B:10:0x00b6, B:12:0x00be, B:13:0x00e7, B:14:0x0108, B:15:0x0129, B:24:0x015c, B:25:0x0178, B:27:0x0180, B:28:0x019f, B:29:0x01a8, B:32:0x01b4, B:34:0x01c4, B:36:0x01ca, B:38:0x01ec, B:41:0x01f4, B:42:0x0214, B:43:0x0222, B:44:0x0227, B:46:0x022f, B:47:0x025b, B:49:0x0261, B:50:0x026f, B:53:0x027d, B:55:0x0283, B:57:0x028f, B:59:0x029b, B:61:0x02a2, B:62:0x02b2, B:63:0x02d3, B:64:0x02e3), top: B:69:0x0018 }] */
            /* renamed from: a */
            /* Code decompiled incorrectly, please refer to instructions dump */
            public void onResponse(String str) {
                String str2;
                NsdlEsignActivity nsdlEsignActivity;
                String string;
                Intent intent;
                NsdlEsignActivity nsdlEsignActivity2;
                String str3;
                String str4;
                String str5;
                String str6;
                String str7;
                JSONException e2;
                String str8 = "";
                if (str != null && !str.isEmpty() && str.contains("xml")) {
                    try {
                        JSONObject jSONObject = XML.toJSONObject(str).getJSONObject(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.n0a66a0713f3c2f1f6f733998b2efba93)))));
                        String string2 = jSONObject.getString(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.ne21ccb3d6b6387623724fa9b149190de)))));
                        NsdlEsignActivity.this.g = str8;
                        NsdlEsignActivity.this.f = str8;
                        NsdlEsignActivity.this.g = jSONObject.getString(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.n01ae87ec52edc1b94726f739b2b058bd)))));
                        NsdlEsignActivity.this.f = jSONObject.getString(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.n6fc762d02a9c0ab3539925ae1766082b)))));
                        if (string2.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                            NsdlEsignActivity.this.a(progressDialog);
                            String str9 = null;
                            try {
                                JSONObject jSONObject2 = XML.toJSONObject(NsdlEsignActivity.this.d).getJSONObject(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.nb5927637f923353b733d05c3b313d98a)))));
                                try {
                                    str2 = jSONObject2.getString(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.n16f27fdc0c7b3685642e4b76cf528bd7)))));
                                    try {
                                        str9 = jSONObject2.getString(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.nca5e3b23b172ccf90b0363c7655e88e5)))));
                                        try {
                                            str8 = jSONObject2.getString(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.naa6b1d7282b98df7270cfca80a94fcd4)))));
                                        } catch (JSONException e3) {
                                            e2 = e3;
                                            e2.printStackTrace();
                                            NsdlEsignActivity.this.a(progressDialog);
                                            NsdlEsignActivity.this.e(NsdlEsignActivity.this.getResources().getString(R.string.Somethingwentwrong));
                                            if (!str8.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                                            }
                                            nsdlEsignActivity2.a(intent, string2, str2, str9, str3, str4, str5, str8, str6, str7);
                                            return;
                                        }
                                    } catch (JSONException e4) {
                                        e2 = e4;
                                        str9 = str8;
                                        str8 = null;
                                    }
                                } catch (JSONException e5) {
                                    e2 = e5;
                                    str2 = str8;
                                    str8 = null;
                                }
                            } catch (JSONException e6) {
                                e2 = e6;
                                str8 = null;
                                str2 = null;
                            }
                            if (!str8.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                                intent = new Intent(NsdlEsignActivity.this, OTPActivity.class);
                                nsdlEsignActivity2 = NsdlEsignActivity.this;
                                str3 = NsdlEsignActivity.this.h;
                                str4 = NsdlEsignActivity.this.e;
                                str5 = NsdlEsignActivity.this.k;
                                str6 = NsdlEsignActivity.this.m;
                                str7 = NsdlEsignActivity.this.q;
                            } else if (str8.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                                boolean hasSystemFeature = NsdlEsignActivity.this.getPackageManager().hasSystemFeature("android.hardware.usb.host");
                                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                                if (hasSystemFeature && NsdlEsignActivity.this.l >= 19) {
                                    intent = new Intent(NsdlEsignActivity.this, BiometricActivityPieSupport.class);
                                    nsdlEsignActivity2 = NsdlEsignActivity.this;
                                    str3 = NsdlEsignActivity.this.h;
                                    str4 = NsdlEsignActivity.this.e;
                                    str5 = NsdlEsignActivity.this.k;
                                    str6 = NsdlEsignActivity.this.m;
                                    str7 = NsdlEsignActivity.this.q;
                                } else if (hasSystemFeature || NsdlEsignActivity.this.l < 19 || defaultAdapter == null) {
                                    nsdlEsignActivity = NsdlEsignActivity.this;
                                    string = NsdlEsignActivity.this.getResources().getString(R.string.nobiometric);
                                    nsdlEsignActivity.e(string);
                                    return;
                                } else {
                                    intent = new Intent(NsdlEsignActivity.this, WithoutOTGSupportBiometricActivity.class);
                                    nsdlEsignActivity2 = NsdlEsignActivity.this;
                                    str3 = NsdlEsignActivity.this.h;
                                    str4 = NsdlEsignActivity.this.e;
                                    str5 = NsdlEsignActivity.this.k;
                                    str6 = NsdlEsignActivity.this.m;
                                    str7 = NsdlEsignActivity.this.q;
                                }
                            } else if (str8.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
                                NsdlEsignActivity.this.a(progressDialog);
                                boolean hasSystemFeature2 = NsdlEsignActivity.this.getPackageManager().hasSystemFeature("android.hardware.usb.host");
                                List<ResolveInfo> queryIntentActivities = NsdlEsignActivity.this.getPackageManager().queryIntentActivities(new Intent("in.gov.uidai.rdservice.iris.CAPTURE"), 0);
                                ArrayList arrayList = new ArrayList();
                                for (ResolveInfo resolveInfo : queryIntentActivities) {
                                    arrayList.add(resolveInfo.activityInfo.packageName);
                                }
                                if (!(NsdlEsignActivity.this.a(NsdlEsignActivity.this.m) || hasSystemFeature2) || NsdlEsignActivity.this.l < 19) {
                                    nsdlEsignActivity = NsdlEsignActivity.this;
                                    string = NsdlEsignActivity.this.getResources().getString(R.string.noiris);
                                } else if (!NsdlEsignActivity.this.q.equalsIgnoreCase("NA") || !NsdlEsignActivity.this.m.equalsIgnoreCase("SM-T116IR") || arrayList.size() != 1) {
                                    intent = new Intent(NsdlEsignActivity.this, IrisAuthenticationActivity.class);
                                    nsdlEsignActivity2 = NsdlEsignActivity.this;
                                    str3 = NsdlEsignActivity.this.h;
                                    str4 = NsdlEsignActivity.this.e;
                                    str5 = NsdlEsignActivity.this.k;
                                    str6 = NsdlEsignActivity.this.m;
                                    str7 = NsdlEsignActivity.this.q;
                                } else {
                                    nsdlEsignActivity = NsdlEsignActivity.this;
                                    string = NsdlEsignActivity.this.getResources().getString(R.string.validiris);
                                }
                                nsdlEsignActivity.e(string);
                                return;
                            } else {
                                return;
                            }
                            nsdlEsignActivity2.a(intent, string2, str2, str9, str3, str4, str5, str8, str6, str7);
                            return;
                        }
                        NsdlEsignActivity.this.a(progressDialog);
                        NsdlEsignActivity.this.e(str);
                        return;
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                NsdlEsignActivity.this.a(progressDialog);
                NsdlEsignActivity nsdlEsignActivity3 = NsdlEsignActivity.this;
                nsdlEsignActivity3.e(nsdlEsignActivity3.getResources().getString(R.string.Somethingwentwrong));
            }
        }, new Response.ErrorListener() { // from class: com.nsdl.egov.esignaar.NsdlEsignActivity.3
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                NsdlEsignActivity nsdlEsignActivity;
                Resources resources;
                int i;
                volleyError.printStackTrace();
                NsdlEsignActivity.this.a(progressDialog);
                if (volleyError instanceof NetworkError) {
                    nsdlEsignActivity = NsdlEsignActivity.this;
                    resources = nsdlEsignActivity.getResources();
                    i = R.string.Somethingwentwrongfornetwork;
                } else if (volleyError instanceof TimeoutError) {
                    nsdlEsignActivity = NsdlEsignActivity.this;
                    resources = nsdlEsignActivity.getResources();
                    i = R.string.Timeoutforconnectionexceeded;
                } else {
                    nsdlEsignActivity = NsdlEsignActivity.this;
                    resources = nsdlEsignActivity.getResources();
                    i = R.string.Somethingwentwrong;
                }
                nsdlEsignActivity.e(resources.getString(i));
            }
        }) { // from class: com.nsdl.egov.esignaar.NsdlEsignActivity.4
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }

            @Override // com.android.volley.toolbox.StringRequest, com.android.volley.Request
            protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
                String str;
                new HashMap();
                try {
                    Map<String, String> map = networkResponse.headers;
                    str = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                    NsdlEsignActivity.this.k = map.get(new String(NsdlEsignActivity.this.p.doFinal(BaseActivity.b(NsdlEsignActivity.this.getResources().getString(R.string.nc9b1e6cfe801d715c5fc99b6f40bf0e7)))));
                } catch (Exception e2) {
                    str = new String(networkResponse.data);
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(networkResponse));
            }
        };
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        r1.setRetryPolicy(new b(300000, 1, 0.0f));
        newRequestQueue.add(r1);
    }

    @Override // com.nsdl.egov.esignaar.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.i && i2 == 5000) {
            try {
                this.j = intent.getStringExtra(new String(this.p.doFinal(b(getResources().getString(R.string.n262f6be2018ae99d5125822e2c4241fe3c5e5d5179e1b355ebc98154bdd95ef0)))));
                e(this.j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.esignnsdlactivity);
        e();
    }
}
