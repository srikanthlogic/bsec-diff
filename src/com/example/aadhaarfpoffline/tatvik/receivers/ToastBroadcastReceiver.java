package com.example.aadhaarfpoffline.tatvik.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.aadhaarfpoffline.tatvik.services.BackgroundService;
import java.text.SimpleDateFormat;
import java.util.Date;
/* loaded from: classes2.dex */
public class ToastBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, BackgroundService.class);
        Log.d("onreceivetoast", "time=" + getCurrentTimeInFormat());
        context.startService(serviceIntent);
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }
}
