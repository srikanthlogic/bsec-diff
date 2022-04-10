package com.example.aadhaarfpoffline.tatvik.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
/* loaded from: classes2.dex */
public class BackgroundService extends IntentService {
    public static final String ACTION = "com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver";

    public BackgroundService() {
        super("backgroundService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        Log.i("backgroundService", "Service running " + getCurrentTimeInFormat());
        Intent toastIntent = new Intent(ACTION);
        toastIntent.putExtra("resultCode", -1);
        toastIntent.putExtra("toastMessage", "I'M running after ever 15 minutes");
        sendBroadcast(toastIntent);
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }
}
