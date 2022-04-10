package com.camerakit.api.camera1;

import android.hardware.Camera;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Camera1.kt */
@Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
/* loaded from: classes.dex */
final class Camera1$sam$android_hardware_Camera_PictureCallback$0 implements Camera.PictureCallback {
    private final /* synthetic */ Function2 function;

    Camera1$sam$android_hardware_Camera_PictureCallback$0(Function2 function2) {
        this.function = function2;
    }

    @Override // android.hardware.Camera.PictureCallback
    public final /* synthetic */ void onPictureTaken(byte[] bArr, Camera camera) {
        Intrinsics.checkExpressionValueIsNotNull(this.function.invoke(bArr, camera), "invoke(...)");
    }
}
