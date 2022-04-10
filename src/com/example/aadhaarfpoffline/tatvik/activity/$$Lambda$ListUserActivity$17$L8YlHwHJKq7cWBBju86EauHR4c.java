package com.example.aadhaarfpoffline.tatvik.activity;

import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import java.util.function.Predicate;
import okhttp3.internal.cache.DiskLruCache;
/* compiled from: lambda */
/* renamed from: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$ListUserActivity$17$L8YlHw-HJKq7cWBBju86EauHR4c  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$ListUserActivity$17$L8YlHwHJKq7cWBBju86EauHR4c implements Predicate {
    public static final /* synthetic */ $$Lambda$ListUserActivity$17$L8YlHwHJKq7cWBBju86EauHR4c INSTANCE = new $$Lambda$ListUserActivity$17$L8YlHwHJKq7cWBBju86EauHR4c();

    private /* synthetic */ $$Lambda$ListUserActivity$17$L8YlHwHJKq7cWBBju86EauHR4c() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((VoterDataNewModel) obj).getVOTED().equals(DiskLruCache.VERSION_1);
    }
}
