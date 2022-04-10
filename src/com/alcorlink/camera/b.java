package com.alcorlink.camera;

import java.util.Iterator;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class b implements Iterator<T> {

    /* renamed from: a  reason: collision with root package name */
    private /* synthetic */ a f16a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(a aVar) {
        this.f16a = aVar;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f16a.f15a < this.f16a.b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Iterator
    public final T next() {
        T t = this.f16a.c.g[this.f16a.f15a] != null ? this.f16a.c.g[this.f16a.f15a] : 0;
        this.f16a.f15a++;
        return t;
    }

    @Override // java.util.Iterator
    public final void remove() {
    }
}
