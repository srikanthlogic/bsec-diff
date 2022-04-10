package com.example.aadhaarfpoffline.tatvik;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
/* loaded from: classes2.dex */
public class Home extends Application {
    static Context mContext;

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
