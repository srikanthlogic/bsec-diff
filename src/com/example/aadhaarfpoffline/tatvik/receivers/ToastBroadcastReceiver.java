package com.example.aadhaarfpoffline.tatvik.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.aadhaarfpoffline.tatvik.services.BackgroundService;
/* loaded from: classes2.dex */
public class ToastBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackgroundService.class));
    }
}
