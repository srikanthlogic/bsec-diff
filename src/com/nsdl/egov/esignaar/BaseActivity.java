package com.nsdl.egov.esignaar;

import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes3.dex */
public class BaseActivity extends AppCompatActivity {
    public static String a;
    private static List<String> d = null;
    private static final byte[] e = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    int b = 0;
    int c = 2;

    public static byte[] b(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public boolean a() {
        return ((UsbManager) getSystemService("usb")).getDeviceList().size() != 0;
    }

    public boolean a(String str) {
        d = new ArrayList();
        d.add("SM-T116IR");
        d.add("R961");
        d.add("WIZARHAND_Q1");
        return d.contains(str);
    }

    public ArrayList<String> b() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Please select device");
        arrayList.add("Morpho E1/E2/E3 MSO1300");
        arrayList.add("Secugen Hamster Pro");
        arrayList.add("Mantra MFS100");
        arrayList.add("Startek FM220U");
        arrayList.add("Evolute Falcon / Identi5 / Leopard");
        arrayList.add("Cogent CSD200");
        arrayList.add("Precision Biometric PB510");
        arrayList.add("Digit Secure");
        return arrayList;
    }

    public ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Please select device");
        arrayList.add("Evolute Falcon / Identi5 / Leopard");
        arrayList.add("Digit Secure Freedom ABB-100-NIR");
        return arrayList;
    }

    public void c(String str) {
        Toast makeText = Toast.makeText(this, "" + str, 0);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public boolean d() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean d(String str) {
        return str == null || TextUtils.isEmpty(str) || str.length() == 0 || str.equals("null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }
}
