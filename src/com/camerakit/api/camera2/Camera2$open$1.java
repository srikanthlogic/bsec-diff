package com.camerakit.api.camera2;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import com.camerakit.api.camera2.Camera2;
import com.camerakit.type.CameraFacing;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
/* compiled from: Camera2.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 13})
/* loaded from: classes.dex */
final class Camera2$open$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ CameraCharacteristics $cameraCharacteristics;
    final /* synthetic */ String $cameraId;
    final /* synthetic */ CameraFacing $facing;
    final /* synthetic */ Camera2 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Camera2$open$1(Camera2 camera2, String str, CameraCharacteristics cameraCharacteristics, CameraFacing cameraFacing) {
        super(0);
        this.this$0 = camera2;
        this.$cameraId = str;
        this.$cameraCharacteristics = cameraCharacteristics;
        this.$facing = cameraFacing;
    }

    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2() {
        this.this$0.cameraManager.openCamera(this.$cameraId, new CameraDevice.StateCallback() { // from class: com.camerakit.api.camera2.Camera2$open$1.1
            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onOpened(CameraDevice cameraDevice) {
                Intrinsics.checkParameterIsNotNull(cameraDevice, "cameraDevice");
                CameraCharacteristics cameraCharacteristics = Camera2$open$1.this.$cameraCharacteristics;
                Intrinsics.checkExpressionValueIsNotNull(cameraCharacteristics, "cameraCharacteristics");
                Camera2.Attributes cameraAttributes = new Camera2.Attributes(cameraCharacteristics, Camera2$open$1.this.$facing);
                Camera2$open$1.this.this$0.cameraDevice = cameraDevice;
                Camera2$open$1.this.this$0.cameraAttributes = cameraAttributes;
                Camera2$open$1.this.this$0.onCameraOpened(cameraAttributes);
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onDisconnected(CameraDevice cameraDevice) {
                Intrinsics.checkParameterIsNotNull(cameraDevice, "cameraDevice");
                cameraDevice.close();
                Camera2$open$1.this.this$0.cameraDevice = null;
                Camera2$open$1.this.this$0.captureSession = null;
                Camera2$open$1.this.this$0.onCameraClosed();
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onError(CameraDevice cameraDevice, int error) {
                Intrinsics.checkParameterIsNotNull(cameraDevice, "cameraDevice");
                cameraDevice.close();
                Camera2$open$1.this.this$0.cameraDevice = null;
                Camera2$open$1.this.this$0.captureSession = null;
            }
        }, this.this$0.getCameraHandler());
    }
}
