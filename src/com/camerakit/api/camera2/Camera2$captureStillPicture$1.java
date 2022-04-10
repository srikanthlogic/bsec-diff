package com.camerakit.api.camera2;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Camera2.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class Camera2$captureStillPicture$1 implements Runnable {
    final /* synthetic */ CaptureRequest.Builder $captureBuilder;
    final /* synthetic */ CameraCaptureSession $captureSession;
    final /* synthetic */ Camera2 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Camera2$captureStillPicture$1(Camera2 camera2, CameraCaptureSession cameraCaptureSession, CaptureRequest.Builder builder) {
        this.this$0 = camera2;
        this.$captureSession = cameraCaptureSession;
        this.$captureBuilder = builder;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$captureSession.capture(this.$captureBuilder.build(), new CameraCaptureSession.CaptureCallback() { // from class: com.camerakit.api.camera2.Camera2$captureStillPicture$1.1
            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                Intrinsics.checkParameterIsNotNull(session, "session");
                Intrinsics.checkParameterIsNotNull(request, "request");
                Intrinsics.checkParameterIsNotNull(result, "result");
                Camera2$captureStillPicture$1.this.this$0.unlockFocus();
            }
        }, this.this$0.getCameraHandler());
    }
}
