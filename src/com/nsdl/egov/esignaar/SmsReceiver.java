package com.nsdl.egov.esignaar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.nsdl.egov.esignaar.a;
/* loaded from: classes3.dex */
public class SmsReceiver extends BroadcastReceiver {
    private static a.AbstractC0001a a;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Object[] objArr = new Object[0];
        if (extras != null) {
            objArr = (Object[]) extras.get("pdus");
        }
        if (objArr != null && objArr.length > 0) {
            SmsMessage createFromPdu = SmsMessage.createFromPdu((byte[]) objArr[0]);
            createFromPdu.getDisplayOriginatingAddress();
            String str = null;
            for (String str2 : createFromPdu.getMessageBody().split(" ")) {
                str = str2.replaceAll("[^0-9]", "");
                if (str.length() == 6) {
                    break;
                }
            }
            a.AbstractC0001a aVar = a;
            if (aVar != null) {
                aVar.a(str);
            }
        }
    }
}
