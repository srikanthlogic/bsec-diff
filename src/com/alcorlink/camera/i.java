package com.alcorlink.camera;
/* loaded from: classes5.dex */
public final class i {

    /* renamed from: a  reason: collision with root package name */
    private long f21a;

    /* JADX INFO: Access modifiers changed from: protected */
    public final void a() {
        this.f21a = System.nanoTime();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final long b() {
        return System.nanoTime() - this.f21a;
    }
}
