package com.camerakit.api;

import android.graphics.SurfaceTexture;
import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ManagedCameraApi.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J+\u0010\b\u001a\u00020\t2!\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\t0\u000bH\u0016J\t\u0010\u0010\u001a\u00020\tH\u0096\u0001J\t\u0010\u0011\u001a\u00020\tH\u0096\u0001J\u0011\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0096\u0001J\t\u0010\u0015\u001a\u00020\tH\u0096\u0001J\t\u0010\u0016\u001a\u00020\tH\u0096\u0001J\t\u0010\u0017\u001a\u00020\tH\u0096\u0001J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\tH\u0016J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\t2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020\tH\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/camerakit/api/ManagedCameraApi;", "Lcom/camerakit/api/CameraApi;", "delegate", "(Lcom/camerakit/api/CameraApi;)V", "cameraHandler", "Lcom/camerakit/api/CameraHandler;", "getCameraHandler", "()Lcom/camerakit/api/CameraHandler;", "capturePhoto", "", "callback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "jpeg", "onCameraClosed", "onCameraError", "onCameraOpened", "cameraAttributes", "Lcom/camerakit/api/CameraAttributes;", "onPreviewError", "onPreviewStarted", "onPreviewStopped", "open", "facing", "Lcom/camerakit/type/CameraFacing;", "release", "setFlash", "flash", "Lcom/camerakit/type/CameraFlash;", "setPhotoSize", "size", "Lcom/camerakit/type/CameraSize;", "setPreviewOrientation", "degrees", "", "setPreviewSize", "startPreview", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", "stopPreview", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class ManagedCameraApi implements CameraApi {
    private final CameraApi delegate;

    @Override // com.camerakit.api.CameraApi
    public CameraHandler getCameraHandler() {
        return this.delegate.getCameraHandler();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraClosed() {
        this.delegate.onCameraClosed();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraError() {
        this.delegate.onCameraError();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraOpened(CameraAttributes cameraAttributes) {
        Intrinsics.checkParameterIsNotNull(cameraAttributes, "cameraAttributes");
        this.delegate.onCameraOpened(cameraAttributes);
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewError() {
        this.delegate.onPreviewError();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewStarted() {
        this.delegate.onPreviewStarted();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewStopped() {
        this.delegate.onPreviewStopped();
    }

    public ManagedCameraApi(CameraApi delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void open(CameraFacing facing) {
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        getCameraHandler();
        this.delegate.open(facing);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void release() {
        getCameraHandler();
        this.delegate.release();
    }

    @Override // com.camerakit.api.CameraActions
    public void setPreviewSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        getCameraHandler();
        this.delegate.setPreviewSize(size);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPreviewOrientation(int degrees) {
        getCameraHandler();
        this.delegate.setPreviewOrientation(degrees);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void startPreview(SurfaceTexture surfaceTexture) {
        Intrinsics.checkParameterIsNotNull(surfaceTexture, "surfaceTexture");
        getCameraHandler();
        this.delegate.startPreview(surfaceTexture);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void stopPreview() {
        getCameraHandler();
        this.delegate.stopPreview();
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setFlash(CameraFlash flash) {
        Intrinsics.checkParameterIsNotNull(flash, "flash");
        getCameraHandler();
        this.delegate.setFlash(flash);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPhotoSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        getCameraHandler();
        this.delegate.setPhotoSize(size);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void capturePhoto(Function1<? super byte[], Unit> function1) {
        Intrinsics.checkParameterIsNotNull(function1, "callback");
        getCameraHandler();
        this.delegate.capturePhoto(function1);
    }
}
