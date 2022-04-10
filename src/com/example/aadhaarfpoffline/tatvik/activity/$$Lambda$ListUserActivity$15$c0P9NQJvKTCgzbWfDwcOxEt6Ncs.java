package com.example.aadhaarfpoffline.tatvik.activity;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import java.util.function.Predicate;
import okhttp3.internal.cache.DiskLruCache;
/* compiled from: lambda */
/* renamed from: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs implements Predicate {
    public static final /* synthetic */ $$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs INSTANCE = new $$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs();

    private /* synthetic */ $$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((VoterDataNewModel) obj).getVOTED().equals(DiskLruCache.VERSION_1);
    }
}
