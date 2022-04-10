package com.example.aadhaarfpoffline.tatvik.util;

import android.content.Context;
import android.widget.Toast;
/* loaded from: classes2.dex */
public class DisplayToast implements Runnable {
    private final Context mContext;
    String mText;

    public DisplayToast(Context mContext, String text) {
        this.mContext = mContext;
        this.mText = text;
    }

    @Override // java.lang.Runnable
    public void run() {
        Toast.makeText(this.mContext, this.mText, 0).show();
    }
}
