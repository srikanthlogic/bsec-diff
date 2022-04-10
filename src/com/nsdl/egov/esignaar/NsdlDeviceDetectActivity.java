package com.nsdl.egov.esignaar;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import com.surepass.surepassesign.R;
import java.util.List;
import org.json.JSONObject;
import org.json.XML;
/* loaded from: classes3.dex */
public class NsdlDeviceDetectActivity extends BaseActivity {
    private int d;
    private List<ResolveInfo> e;
    private boolean f;
    private String g;

    private void a(String str, String str2) {
        this.f = false;
        Intent intent = new Intent();
        intent.putExtra("error_code", str);
        intent.putExtra("error_message", str2);
        setResult(0, intent);
        finish();
    }

    private void e() {
        this.e = getPackageManager().queryIntentActivities(new Intent("in.gov.uidai.rdservice.fp.CAPTURE"), 0);
        List<ResolveInfo> list = this.e;
        if (list == null || list.size() == 0) {
            a("8616", "RD Service not found");
            return;
        }
        this.d = 0;
        this.f = true;
        e(this.e.get(this.d).activityInfo.packageName);
    }

    private void e(String str) {
        if (str.equals("com.evolute.rdservice")) {
            this.d++;
            e(this.e.get(this.d).activityInfo.packageName);
            return;
        }
        Intent intent = new Intent("in.gov.uidai.rdservice.fp.INFO");
        intent.setPackage(str);
        startActivityForResult(intent, 2);
    }

    private void f(String str) {
        Intent intent = new Intent();
        intent.putExtra("PID_DATA", str);
        setResult(-1, intent);
        this.f = false;
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x012f  */
    @Override // com.nsdl.egov.esignaar.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        String str;
        String str2;
        String str3;
        Exception e;
        Bundle extras2;
        super.onActivityResult(i, i2, intent);
        if (i == 2) {
            if (i2 == -1 && (extras2 = intent.getExtras()) != null) {
                try {
                    String string = new JSONObject(XML.toJSONObject(extras2.getString("RD_SERVICE_INFO", "")).getString("RDService")).getString(NotificationCompat.CATEGORY_STATUS);
                    if (!string.equalsIgnoreCase("READY") && !string.equalsIgnoreCase("USED")) {
                        if (string.equalsIgnoreCase("NOTREADY")) {
                            this.d++;
                            if (this.d < this.e.size()) {
                                e(this.e.get(this.d).activityInfo.packageName);
                                return;
                            } else {
                                a("8618", "Connected fingerprint device not supported by Installed RD Service or may be USB permission denied by User. Or connected fingerprint device not Registered");
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    Intent intent2 = new Intent("in.gov.uidai.rdservice.fp.CAPTURE");
                    intent2.putExtra("PID_OPTIONS", this.g);
                    intent2.setPackage(this.e.get(this.d).activityInfo.packageName);
                    startActivityForResult(intent2, 1);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    a("8617", "Error occoured during capture " + e2.toString());
                }
            } else {
                return;
            }
        } else if (i == 1 && i2 == -1 && (extras = intent.getExtras()) != null) {
            String string2 = extras.getString("PID_DATA");
            if (string2.contains("errCode=\"0\"")) {
                f(string2);
                return;
            }
            String str4 = null;
            try {
                JSONObject jSONObject = XML.toJSONObject(string2).getJSONObject("PidData").getJSONObject("Resp");
                str = jSONObject.getString("errInfo");
                try {
                    str4 = jSONObject.getString("errCode");
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    a("8617", "Error occoured during capture." + e.toString());
                    if (str == null) {
                    }
                    a(str3, str2);
                    return;
                }
            } catch (Exception e4) {
                e = e4;
                str = null;
            }
            if (str == null) {
                str3 = "" + str4;
                str2 = "" + str;
            } else {
                str3 = "8620";
                str2 = "Something went wrong";
            }
            a(str3, str2);
            return;
        } else {
            return;
        }
        e2.printStackTrace();
        a("8617", "Error occoured during capture " + e2.toString());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        String str;
        String str2;
        if (this.f) {
            str2 = "8619";
            str = "Cancelled by user";
        } else {
            str2 = "8620";
            str = "Something went wrong!!";
        }
        a(str2, str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.g = extras.getString("PID_OPTIONS", null);
            if (this.g != null) {
                if (a()) {
                    e();
                    return;
                } else {
                    a("8615", getResources().getString(R.string.devicenotdecact));
                    return;
                }
            }
        }
        a("8614", "Pid value empty..");
    }
}
