package com.camerakit.api.camera1.ext;

import android.hardware.Camera;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
/* compiled from: Parameters.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0019\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00060\u0003R\u00020\u0004¢\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00060\u0003R\u00020\u0004¢\u0006\u0002\u0010\b\u001a\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00060\u0003R\u00020\u0004¢\u0006\u0002\u0010\b¨\u0006\n"}, d2 = {"getFlashes", "", "Lcom/camerakit/type/CameraFlash;", "Landroid/hardware/Camera$Parameters;", "Landroid/hardware/Camera;", "(Landroid/hardware/Camera$Parameters;)[Lcom/camerakit/type/CameraFlash;", "getPhotoSizes", "Lcom/camerakit/type/CameraSize;", "(Landroid/hardware/Camera$Parameters;)[Lcom/camerakit/type/CameraSize;", "getPreviewSizes", "camerakit_release"}, k = 2, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class ParametersKt {
    /* JADX INFO: Multiple debug info for r0v5 java.util.ArrayList: [D('$receiver$iv' java.util.Collection), D('$receiver$iv' java.lang.Iterable)] */
    public static final CameraSize[] getPreviewSizes(Camera.Parameters $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Iterable supportedPreviewSizes = $receiver.getSupportedPreviewSizes();
        Intrinsics.checkExpressionValueIsNotNull(supportedPreviewSizes, "supportedPreviewSizes");
        Iterable<Camera.Size> $receiver$iv = supportedPreviewSizes;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Camera.Size it : $receiver$iv) {
            destination$iv$iv.add(new CameraSize(it.width, it.height));
        }
        Object[] array = ((List) destination$iv$iv).toArray(new CameraSize[0]);
        if (array != null) {
            return (CameraSize[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    /* JADX INFO: Multiple debug info for r0v5 java.util.ArrayList: [D('$receiver$iv' java.util.Collection), D('$receiver$iv' java.lang.Iterable)] */
    public static final CameraSize[] getPhotoSizes(Camera.Parameters $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Iterable supportedPictureSizes = $receiver.getSupportedPictureSizes();
        Intrinsics.checkExpressionValueIsNotNull(supportedPictureSizes, "supportedPictureSizes");
        Iterable<Camera.Size> $receiver$iv = supportedPictureSizes;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Camera.Size it : $receiver$iv) {
            destination$iv$iv.add(new CameraSize(it.width, it.height));
        }
        Object[] array = ((List) destination$iv$iv).toArray(new CameraSize[0]);
        if (array != null) {
            return (CameraSize[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    /* JADX INFO: Multiple debug info for r0v9 java.util.ArrayList: [D('$receiver$iv' java.util.Collection), D('$receiver$iv' java.lang.Iterable)] */
    public static final CameraFlash[] getFlashes(Camera.Parameters $receiver) {
        int i;
        CameraFlash cameraFlash;
        int hashCode;
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        if ($receiver.getSupportedFlashModes() == null) {
            return new CameraFlash[0];
        }
        Iterable $receiver$iv = $receiver.getSupportedFlashModes();
        Intrinsics.checkExpressionValueIsNotNull($receiver$iv, "supportedFlashModes");
        Collection destination$iv$iv = new ArrayList();
        Iterator<T> it = $receiver$iv.iterator();
        while (true) {
            i = 3551;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            String it2 = (String) element$iv$iv;
            boolean z = true;
            if (it2 == null || ((hashCode = it2.hashCode()) == 3551 ? !it2.equals(DebugKt.DEBUG_PROPERTY_VALUE_ON) : hashCode == 109935 ? !it2.equals(DebugKt.DEBUG_PROPERTY_VALUE_OFF) : hashCode == 3005871 ? !it2.equals(DebugKt.DEBUG_PROPERTY_VALUE_AUTO) : hashCode != 110547964 || !it2.equals("torch"))) {
                z = false;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable<String> $receiver$iv2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        for (String it3 : $receiver$iv2) {
            if (it3 != null) {
                int hashCode2 = it3.hashCode();
                if (hashCode2 != i) {
                    if (hashCode2 != 109935) {
                        if (hashCode2 != 3005871) {
                            if (hashCode2 == 110547964 && it3.equals("torch")) {
                                cameraFlash = CameraFlash.TORCH;
                                destination$iv$iv2.add(cameraFlash);
                                i = 3551;
                            }
                        } else if (it3.equals(DebugKt.DEBUG_PROPERTY_VALUE_AUTO)) {
                            cameraFlash = CameraFlash.AUTO;
                            destination$iv$iv2.add(cameraFlash);
                            i = 3551;
                        }
                    } else if (it3.equals(DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                        cameraFlash = CameraFlash.OFF;
                        destination$iv$iv2.add(cameraFlash);
                        i = 3551;
                    }
                } else if (it3.equals(DebugKt.DEBUG_PROPERTY_VALUE_ON)) {
                    cameraFlash = CameraFlash.ON;
                    destination$iv$iv2.add(cameraFlash);
                    i = 3551;
                }
            }
            cameraFlash = CameraFlash.OFF;
            destination$iv$iv2.add(cameraFlash);
            i = 3551;
        }
        Object[] array = ((List) destination$iv$iv2).toArray(new CameraFlash[0]);
        if (array != null) {
            return (CameraFlash[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
}
