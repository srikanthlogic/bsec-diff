package com.camerakit.api.camera1;

import android.hardware.Camera;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Camera1.kt */
@Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
/* loaded from: classes.dex */
final class Camera1$sam$android_hardware_Camera_ShutterCallback$0 implements Camera.ShutterCallback {
    private final /* synthetic */ Function0 function;

    Camera1$sam$android_hardware_Camera_ShutterCallback$0(Function0 function0) {
        this.function = function0;
    }

    @Override // android.hardware.Camera.ShutterCallback
    public final /* synthetic */ void onShutter() {
        Intrinsics.checkExpressionValueIsNotNull(this.function.invoke(), "invoke(...)");
    }
}
