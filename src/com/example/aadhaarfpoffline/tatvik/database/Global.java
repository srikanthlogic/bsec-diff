package com.example.aadhaarfpoffline.tatvik.database;

import android.app.Application;
import android.util.Log;
import java.util.Hashtable;
/* loaded from: classes2.dex */
public class Global extends Application {
    private Hashtable<Object, Object> data;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Log.w("Application", "Global Object Created");
        this.data = new Hashtable<>();
    }

    public void setAttribute(Object key, Object value) {
        this.data.put(key, value);
    }

    public Object getAttribute(Object key) {
        return this.data.get(key);
    }

    public void removeAttribute(Object key) {
        this.data.remove(key);
    }

    public void clear() {
        this.data.clear();
    }
}
