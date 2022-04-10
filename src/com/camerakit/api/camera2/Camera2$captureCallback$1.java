package com.camerakit.api.camera2;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Camera2.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\fH\u0002¨\u0006\u000e"}, d2 = {"com/camerakit/api/camera2/Camera2$captureCallback$1", "Landroid/hardware/camera2/CameraCaptureSession$CaptureCallback;", "onCaptureCompleted", "", "session", "Landroid/hardware/camera2/CameraCaptureSession;", "request", "Landroid/hardware/camera2/CaptureRequest;", "result", "Landroid/hardware/camera2/TotalCaptureResult;", "onCaptureProgressed", "partialResult", "Landroid/hardware/camera2/CaptureResult;", "process", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class Camera2$captureCallback$1 extends CameraCaptureSession.CaptureCallback {
    final /* synthetic */ Camera2 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Incorrect args count in method signature: ()V */
    public Camera2$captureCallback$1(Camera2 $outer) {
        this.this$0 = $outer;
    }

    private final void process(CaptureResult result) {
        int i = this.this$0.captureState;
        if (i == 0) {
            ImageReader imageReader = this.this$0.imageReader;
            Image image = imageReader != null ? imageReader.acquireLatestImage() : null;
            if (image != null) {
                Image.Plane plane = image.getPlanes()[0];
                Intrinsics.checkExpressionValueIsNotNull(plane, "image.planes[0]");
                ByteBuffer buffer = plane.getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                Function1 function1 = this.this$0.photoCallback;
                if (function1 != null) {
                    Unit unit = (Unit) function1.invoke(bytes);
                }
                this.this$0.photoCallback = null;
                image.close();
            }
        } else if (i == 1) {
            Integer afState = (Integer) result.get(CaptureResult.CONTROL_AF_STATE);
            if ((afState != null && 4 == afState.intValue()) || (afState != null && 5 == afState.intValue())) {
                this.this$0.runPreCaptureSequence();
            } else if (afState == null || afState.intValue() == 0) {
                this.this$0.captureStillPicture();
            } else if (this.this$0.waitingFrames >= 5) {
                this.this$0.waitingFrames = 0;
                this.this$0.captureStillPicture();
            } else {
                Camera2 camera2 = this.this$0;
                camera2.waitingFrames = camera2.waitingFrames + 1;
            }
        } else if (i == 2) {
            Integer aeState = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
            if (aeState == null || aeState.intValue() == 5 || aeState.intValue() == 4) {
                this.this$0.captureState = 3;
            }
        } else if (i == 3) {
            Integer aeState2 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
            if (aeState2 == null || aeState2.intValue() != 5) {
                this.this$0.captureState = 4;
                this.this$0.captureStillPicture();
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
        Intrinsics.checkParameterIsNotNull(session, "session");
        Intrinsics.checkParameterIsNotNull(request, "request");
        Intrinsics.checkParameterIsNotNull(result, "result");
        if (!(this.this$0.previewStarted)) {
            this.this$0.onPreviewStarted();
            this.this$0.previewStarted = true;
        }
        process(result);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
        Intrinsics.checkParameterIsNotNull(session, "session");
        Intrinsics.checkParameterIsNotNull(request, "request");
        Intrinsics.checkParameterIsNotNull(partialResult, "partialResult");
        process(partialResult);
    }
}
