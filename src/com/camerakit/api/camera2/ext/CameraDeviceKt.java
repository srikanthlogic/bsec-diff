package com.camerakit.api.camera2.ext;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.media.ImageReader;
import android.os.Handler;
import android.view.Surface;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraDevice.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aI\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2#\u0010\t\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\nH\u0007¨\u0006\u000f"}, d2 = {"getCaptureSession", "", "Landroid/hardware/camera2/CameraDevice;", "surface", "Landroid/view/Surface;", "imageReader", "Landroid/media/ImageReader;", "handler", "Landroid/os/Handler;", "callback", "Lkotlin/Function1;", "Landroid/hardware/camera2/CameraCaptureSession;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "captureSession", "camerakit_release"}, k = 2, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraDeviceKt {
    public static final void getCaptureSession(CameraDevice $receiver, Surface surface, ImageReader imageReader, Handler handler, Function1<? super CameraCaptureSession, Unit> function1) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Intrinsics.checkParameterIsNotNull(surface, "surface");
        Intrinsics.checkParameterIsNotNull(imageReader, "imageReader");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        Intrinsics.checkParameterIsNotNull(function1, "callback");
        $receiver.createCaptureSession(CollectionsKt.listOf((Object[]) new Surface[]{surface, imageReader.getSurface()}), new CameraCaptureSession.StateCallback() { // from class: com.camerakit.api.camera2.ext.CameraDeviceKt$getCaptureSession$1
            @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
            public void onConfigured(CameraCaptureSession captureSession) {
                Intrinsics.checkParameterIsNotNull(captureSession, "captureSession");
                Function1.this.invoke(captureSession);
            }

            @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
            public void onClosed(CameraCaptureSession session) {
                Intrinsics.checkParameterIsNotNull(session, "session");
                Function1.this.invoke(null);
                super.onClosed(session);
            }

            @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
            public void onConfigureFailed(CameraCaptureSession captureSession) {
                Intrinsics.checkParameterIsNotNull(captureSession, "captureSession");
                Function1.this.invoke(null);
            }
        }, handler);
    }
}
