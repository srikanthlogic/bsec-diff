package com.example.aadhaarfpoffline.tatvik.activity;

import androidx.exifinterface.media.ExifInterface;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import java.util.function.Predicate;
/* compiled from: lambda */
/* renamed from: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$ListUserActivity$17$IB4M3NQI4gZ2SQn48ZnTvdAeEm0  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$ListUserActivity$17$IB4M3NQI4gZ2SQn48ZnTvdAeEm0 implements Predicate {
    public static final /* synthetic */ $$Lambda$ListUserActivity$17$IB4M3NQI4gZ2SQn48ZnTvdAeEm0 INSTANCE = new $$Lambda$ListUserActivity$17$IB4M3NQI4gZ2SQn48ZnTvdAeEm0();

    private /* synthetic */ $$Lambda$ListUserActivity$17$IB4M3NQI4gZ2SQn48ZnTvdAeEm0() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((VoterDataNewModel) obj).getVOTED().equals(ExifInterface.GPS_MEASUREMENT_3D);
    }
}
