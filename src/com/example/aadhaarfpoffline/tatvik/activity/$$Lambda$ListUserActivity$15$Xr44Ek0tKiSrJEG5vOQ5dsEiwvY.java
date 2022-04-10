package com.example.aadhaarfpoffline.tatvik.activity;

import androidx.exifinterface.media.ExifInterface;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import java.util.function.Predicate;
/* compiled from: lambda */
/* renamed from: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY implements Predicate {
    public static final /* synthetic */ $$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY INSTANCE = new $$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY();

    private /* synthetic */ $$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((VoterDataNewModel) obj).getVOTED().equals(ExifInterface.GPS_MEASUREMENT_3D);
    }
}
