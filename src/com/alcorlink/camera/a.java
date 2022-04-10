package com.alcorlink.camera;

import java.util.Iterator;
/* loaded from: classes5.dex */
public final class a<T> implements Iterable<T> {

    /* renamed from: a */
    int f15a = 0;
    int b = 0;
    final /* synthetic */ AlCamHAL c;

    public a(AlCamHAL alCamHAL) {
        this.c = alCamHAL;
    }

    @Override // java.lang.Iterable
    public final Iterator<T> iterator() {
        this.f15a = 0;
        this.b = this.c.h;
        return new b(this);
    }
}
