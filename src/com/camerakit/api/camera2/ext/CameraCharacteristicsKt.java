package com.camerakit.api.camera2.ext;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import android.view.SurfaceHolder;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraCharacteristics.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u001a\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0004\u001a\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0007\u001a\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0007\u001a\f\u0010\t\u001a\u00020\n*\u00020\u0003H\u0007¨\u0006\u000b"}, d2 = {"getFlashes", "", "Lcom/camerakit/type/CameraFlash;", "Landroid/hardware/camera2/CameraCharacteristics;", "(Landroid/hardware/camera2/CameraCharacteristics;)[Lcom/camerakit/type/CameraFlash;", "getPhotoSizes", "Lcom/camerakit/type/CameraSize;", "(Landroid/hardware/camera2/CameraCharacteristics;)[Lcom/camerakit/type/CameraSize;", "getPreviewSizes", "getSensorOrientation", "", "camerakit_release"}, k = 2, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraCharacteristicsKt {
    public static final int getSensorOrientation(CameraCharacteristics $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Integer num = (Integer) $receiver.get(CameraCharacteristics.SENSOR_ORIENTATION);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static final CameraSize[] getPreviewSizes(CameraCharacteristics $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        StreamConfigurationMap streamConfigMap = (StreamConfigurationMap) $receiver.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigMap == null) {
            return new CameraSize[0];
        }
        Size[] outputSizes = streamConfigMap.getOutputSizes(SurfaceHolder.class);
        if (outputSizes == null) {
            return new CameraSize[0];
        }
        Collection destination$iv$iv = new ArrayList(outputSizes.length);
        for (Size size : outputSizes) {
            Intrinsics.checkExpressionValueIsNotNull(size, "it");
            destination$iv$iv.add(new CameraSize(size.getWidth(), size.getHeight()));
        }
        Object[] array = ((List) destination$iv$iv).toArray(new CameraSize[0]);
        if (array != null) {
            return (CameraSize[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final CameraSize[] getPhotoSizes(CameraCharacteristics $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        StreamConfigurationMap streamConfigMap = (StreamConfigurationMap) $receiver.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigMap == null) {
            return new CameraSize[0];
        }
        Size[] outputSizes = streamConfigMap.getOutputSizes(256);
        if (outputSizes == null) {
            return new CameraSize[0];
        }
        Collection destination$iv$iv = new ArrayList(outputSizes.length);
        for (Size size : outputSizes) {
            Intrinsics.checkExpressionValueIsNotNull(size, "it");
            destination$iv$iv.add(new CameraSize(size.getWidth(), size.getHeight()));
        }
        Object[] array = ((List) destination$iv$iv).toArray(new CameraSize[0]);
        if (array != null) {
            return (CameraSize[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final CameraFlash[] getFlashes(CameraCharacteristics $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Boolean flashSupported = (Boolean) $receiver.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        Intrinsics.checkExpressionValueIsNotNull(flashSupported, "flashSupported");
        return flashSupported.booleanValue() ? new CameraFlash[]{CameraFlash.OFF, CameraFlash.ON, CameraFlash.AUTO, CameraFlash.TORCH} : new CameraFlash[0];
    }
}
