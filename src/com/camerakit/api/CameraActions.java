package com.camerakit.api;

import android.graphics.SurfaceTexture;
import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
/* compiled from: CameraActions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J+\u0010\u0002\u001a\u00020\u00032!\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\u00030\u0005H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u0003H&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001aH&J\b\u0010\u001b\u001a\u00020\u0003H&¨\u0006\u001c"}, d2 = {"Lcom/camerakit/api/CameraActions;", "", "capturePhoto", "", "callback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "jpeg", "open", "facing", "Lcom/camerakit/type/CameraFacing;", "release", "setFlash", "flash", "Lcom/camerakit/type/CameraFlash;", "setPhotoSize", "size", "Lcom/camerakit/type/CameraSize;", "setPreviewOrientation", "degrees", "", "setPreviewSize", "startPreview", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", "stopPreview", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public interface CameraActions {
    void capturePhoto(Function1<? super byte[], Unit> function1);

    void open(CameraFacing cameraFacing);

    void release();

    void setFlash(CameraFlash cameraFlash);

    void setPhotoSize(CameraSize cameraSize);

    void setPreviewOrientation(int i);

    void setPreviewSize(CameraSize cameraSize);

    void startPreview(SurfaceTexture surfaceTexture);

    void stopPreview();
}
