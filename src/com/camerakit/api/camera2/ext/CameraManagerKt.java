package com.camerakit.api.camera2.ext;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import com.camerakit.type.CameraFacing;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007\u001a*\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0007¨\u0006\f"}, d2 = {"getCameraId", "", "Landroid/hardware/camera2/CameraManager;", "facing", "Lcom/camerakit/type/CameraFacing;", "whenDeviceAvailable", "", "targetCameraId", "handler", "Landroid/os/Handler;", "callback", "Lkotlin/Function0;", "camerakit_release"}, k = 2, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraManagerKt {

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[CameraFacing.values().length];

        static {
            $EnumSwitchMapping$0[CameraFacing.BACK.ordinal()] = 1;
            $EnumSwitchMapping$0[CameraFacing.FRONT.ordinal()] = 2;
        }
    }

    public static final String getCameraId(CameraManager $receiver, CameraFacing facing) {
        int targetFacingCharacteristic;
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        int i = WhenMappings.$EnumSwitchMapping$0[facing.ordinal()];
        if (i == 1) {
            targetFacingCharacteristic = 1;
        } else if (i == 2) {
            targetFacingCharacteristic = 0;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        String[] cameraIdList = $receiver.getCameraIdList();
        Intrinsics.checkExpressionValueIsNotNull(cameraIdList, "cameraIdList");
        for (String cameraId : cameraIdList) {
            Integer facingCharacteristic = (Integer) $receiver.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING);
            if (((facingCharacteristic != null && facingCharacteristic.intValue() == targetFacingCharacteristic) ? 1 : null) != null) {
                return cameraId;
            }
        }
        return null;
    }

    public static final void whenDeviceAvailable(CameraManager $receiver, String targetCameraId, Handler handler, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Intrinsics.checkParameterIsNotNull(targetCameraId, "targetCameraId");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        Intrinsics.checkParameterIsNotNull(function0, "callback");
        $receiver.registerAvailabilityCallback(new CameraManager.AvailabilityCallback($receiver, targetCameraId, function0) { // from class: com.camerakit.api.camera2.ext.CameraManagerKt$whenDeviceAvailable$1
            final /* synthetic */ Function0 $callback;
            final /* synthetic */ String $targetCameraId;
            final /* synthetic */ CameraManager $this_whenDeviceAvailable;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.$this_whenDeviceAvailable = $receiver;
                this.$targetCameraId = $captured_local_variable$1;
                this.$callback = $captured_local_variable$2;
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public void onCameraAvailable(String cameraId) {
                Intrinsics.checkParameterIsNotNull(cameraId, "cameraId");
                if (Intrinsics.areEqual(cameraId, this.$targetCameraId)) {
                    this.$this_whenDeviceAvailable.unregisterAvailabilityCallback(this);
                    this.$callback.invoke();
                }
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public void onCameraUnavailable(String cameraId) {
                Intrinsics.checkParameterIsNotNull(cameraId, "cameraId");
                Intrinsics.areEqual(cameraId, this.$targetCameraId);
            }
        }, handler);
    }
}
