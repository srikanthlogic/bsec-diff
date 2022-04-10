package com.nsdl.egov.esignaar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
/* loaded from: classes3.dex */
public class b implements RetryPolicy {
    private int a;
    private int b;
    private final int c;
    private final float d;

    public b() {
        this(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0.0f);
    }

    public b(int i, int i2, float f) {
        this.a = i;
        this.c = i2;
        this.d = f;
    }

    protected boolean a() {
        return this.b <= this.c;
    }

    @Override // com.android.volley.RetryPolicy
    public int getCurrentRetryCount() {
        return this.b;
    }

    @Override // com.android.volley.RetryPolicy
    public int getCurrentTimeout() {
        return this.a;
    }

    @Override // com.android.volley.RetryPolicy
    public void retry(VolleyError volleyError) throws VolleyError {
        this.b++;
        int i = this.a;
        this.a = (int) (((float) i) + (((float) i) * this.d));
        if (!a()) {
            throw volleyError;
        }
    }
}
