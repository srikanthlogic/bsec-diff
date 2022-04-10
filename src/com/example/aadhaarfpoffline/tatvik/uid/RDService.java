package com.example.aadhaarfpoffline.tatvik.uid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.example.aadhaarfpoffline.tatvik.uid.RDService;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes2.dex */
public class RDService {
    private static ActivityResultLauncher<Intent> captureActivityResultLauncher;
    private static String capturePath;
    private static CountDownLatch countDownLatch;
    private static String currentPkg;
    private static final String fingerprintPIDOptionsDev;
    private static final String fingerprintPIDOptionsProd;
    private static ActivityResultLauncher<Intent> infoActivityResultLauncher;
    private static final String irisPIDOptionsDev;
    private static final String irisPIDOptionsProd;
    private static String modality;
    private static OnRDServiceDiscoveryAndCapture onRDServiceDiscovery;
    private static String pkg;
    private static int retrties = 1;
    private static int currentTry = 0;

    /* loaded from: classes2.dex */
    public interface OnRDServiceDiscoveryAndCapture {
        void onError(VolleyError volleyError);

        void onSuccess(String str);
    }

    public static void registerForActivityResult(AppCompatActivity activity) {
        infoActivityResultLauncher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), $$Lambda$RDService$CdBKpY4Xo1JPqaeSjGLi1unMTCY.INSTANCE);
        captureActivityResultLauncher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), $$Lambda$RDService$jsvcFU4qv05gVUFo9L3FsPINyoE.INSTANCE);
    }

    public static /* synthetic */ void lambda$registerForActivityResult$0(ActivityResult result) {
        Bundle b;
        int resultCode = result.getResultCode();
        Intent data = result.getData();
        if (!(resultCode != -1 || data == null || (b = data.getExtras()) == null)) {
            String RDStatus = b.getString("RD_SERVICE_INFO", "");
            Log.e("RD_SERVICE_INFO %s", RDStatus);
            if (RDStatus.contains("status=\"READY\"")) {
                pkg = currentPkg;
                Pattern p = Pattern.compile("Interface id=\"CAPTURE\" path=\"(.+?)\"");
                Pattern p1 = Pattern.compile("Interface id=\"PROCESS_CALL\" path=\"(.+?)\"");
                Matcher m = p.matcher(RDStatus);
                Matcher m1 = p1.matcher(RDStatus);
                if (m.find()) {
                    String capturePath2 = m.group(1);
                    capturePath = capturePath2;
                    if (capturePath2.toLowerCase().contains("iris")) {
                        modality = "iris";
                    } else {
                        modality = "fingerprint";
                    }
                    Log.e("pkg %s", pkg);
                    Log.e("path %s", capturePath2);
                    Log.e("modality %s", modality);
                } else if (m1.find()) {
                    String capturePath3 = m1.group(1);
                    capturePath = capturePath3;
                    if (capturePath3.toLowerCase().contains("iris")) {
                        modality = "iris";
                    } else {
                        modality = "fingerprint";
                    }
                    Log.e("pkg %s", pkg);
                    Log.e("path %s", capturePath3);
                    Log.e("modality %s", modality);
                }
            }
        }
        CountDownLatch countDownLatch2 = countDownLatch;
        if (countDownLatch2 != null) {
            countDownLatch2.countDown();
        }
    }

    public static /* synthetic */ void lambda$registerForActivityResult$1(ActivityResult result) {
        int resultCode = result.getResultCode();
        Intent data = result.getData();
        if (resultCode != -1 || data == null) {
            onRDServiceDiscovery.onError(new VolleyError(new NetworkResponse("Error capturing biometric data".getBytes())));
            return;
        }
        Bundle b = data.getExtras();
        if (b != null) {
            String pidData = b.getString("PID_DATA");
            if (TextUtils.isEmpty(pidData)) {
                onRDServiceDiscovery.onError(new VolleyError(new NetworkResponse("Invalid PID Xml".getBytes())));
            } else if (pidData.contains("errCode=\"0\"")) {
                Log.e("PID data %s", pidData);
                onRDServiceDiscovery.onSuccess(pidData);
            } else {
                Matcher m = Pattern.compile("errInfo=\"(.+?)\"").matcher(pidData);
                if (m.find()) {
                    onRDServiceDiscovery.onError(new VolleyError(new NetworkResponse(m.group(1).getBytes())));
                }
            }
        } else {
            onRDServiceDiscovery.onError(new VolleyError(new NetworkResponse("Error capturing biometric data".getBytes())));
        }
    }

    public static void discoverAndCapture(AppCompatActivity activity, boolean demo, OnRDServiceDiscoveryAndCapture onRDServiceDiscovery2) {
        new Thread(new Runnable(demo, onRDServiceDiscovery2) { // from class: com.example.aadhaarfpoffline.tatvik.uid.-$$Lambda$RDService$nYYJ2ijrSOXZe5eY1b0shEHNeEs
            private final /* synthetic */ boolean f$1;
            private final /* synthetic */ RDService.OnRDServiceDiscoveryAndCapture f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            @Override // java.lang.Runnable
            public final void run() {
                RDService.__discoverAndCapture(AppCompatActivity.this, this.f$1, this.f$2);
            }
        }).start();
    }

    public static void __discoverAndCapture(AppCompatActivity activity, boolean demo, OnRDServiceDiscoveryAndCapture onRDServiceDiscovery2) {
        onRDServiceDiscovery = onRDServiceDiscovery2;
        capturePath = "in.gov.uidai.rdservice.fp.CAPTURE";
        Intent captureIntent = new Intent(capturePath);
        captureIntent.setPackage("in.bioenable.rdservice.fp");
        modality = "fingerprint";
        String PIDXml = fingerprintPIDOptionsDev;
        if (modality.equals("iris")) {
            if (demo) {
                PIDXml = irisPIDOptionsDev;
            } else {
                PIDXml = irisPIDOptionsProd;
            }
        } else if (modality.equals("fingerprint")) {
            if (demo) {
                PIDXml = fingerprintPIDOptionsDev;
            } else {
                PIDXml = fingerprintPIDOptionsProd;
            }
        }
        captureIntent.putExtra("PID_OPTIONS", PIDXml);
        captureActivityResultLauncher.launch(captureIntent);
    }
}
