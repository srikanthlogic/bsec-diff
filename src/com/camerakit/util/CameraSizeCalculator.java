package com.camerakit.util;

import com.camerakit.type.CameraSize;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraSizeCalculator.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bR\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\f"}, d2 = {"Lcom/camerakit/util/CameraSizeCalculator;", "", "sizes", "", "Lcom/camerakit/type/CameraSize;", "([Lcom/camerakit/type/CameraSize;)V", "[Lcom/camerakit/type/CameraSize;", "findClosestSizeContainingTarget", "target", "findClosestSizeMatchingArea", "area", "", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraSizeCalculator {
    private final CameraSize[] sizes;

    public CameraSizeCalculator(CameraSize[] sizes) {
        Intrinsics.checkParameterIsNotNull(sizes, "sizes");
        this.sizes = sizes;
    }

    public final CameraSize findClosestSizeContainingTarget(CameraSize target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        CameraSize[] cameraSizeArr = this.sizes;
        if (cameraSizeArr != null) {
            ArraysKt.sort((Object[]) cameraSizeArr);
            CameraSize cameraSize = (CameraSize) ArraysKt.last(this.sizes);
            int bestArea = Integer.MAX_VALUE;
            CameraSize[] cameraSizeArr2 = this.sizes;
            for (CameraSize cameraSize2 : cameraSizeArr2) {
                if (cameraSize2.getWidth() >= target.getWidth() && cameraSize2.getHeight() >= target.getHeight() && cameraSize2.area() < bestArea) {
                    cameraSize = cameraSize2;
                    bestArea = cameraSize2.area();
                }
            }
            return cameraSize;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    }

    public final CameraSize findClosestSizeMatchingArea(int area) {
        CameraSize[] cameraSizeArr = this.sizes;
        if (cameraSizeArr != null) {
            ArraysKt.sort((Object[]) cameraSizeArr);
            CameraSize cameraSize = (CameraSize) ArraysKt.last(this.sizes);
            CameraSize[] cameraSizeArr2 = this.sizes;
            for (CameraSize cameraSize2 : cameraSizeArr2) {
                if (Math.abs(area - cameraSize2.area()) < Math.abs(area - cameraSize.area())) {
                    cameraSize = cameraSize2;
                }
            }
            return cameraSize;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    }
}
