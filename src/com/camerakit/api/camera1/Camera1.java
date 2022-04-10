package com.camerakit.api.camera1;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import com.camerakit.api.CameraApi;
import com.camerakit.api.CameraAttributes;
import com.camerakit.api.CameraEvents;
import com.camerakit.api.CameraHandler;
import com.camerakit.api.camera1.ext.ParametersKt;
import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
/* compiled from: Camera1.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u0001-B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J+\u0010\r\u001a\u00020\u000e2!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000e0\u0010H\u0016J\t\u0010\u0015\u001a\u00020\u000eH\u0096\u0001J\t\u0010\u0016\u001a\u00020\u000eH\u0096\u0001J\u0011\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0096\u0001J\t\u0010\u0018\u001a\u00020\u000eH\u0096\u0001J\t\u0010\u0019\u001a\u00020\u000eH\u0096\u0001J\t\u0010\u001a\u001a\u00020\u000eH\u0096\u0001J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016J\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020\u000eH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006."}, d2 = {"Lcom/camerakit/api/camera1/Camera1;", "Lcom/camerakit/api/CameraApi;", "Lcom/camerakit/api/CameraEvents;", "eventsDelegate", "(Lcom/camerakit/api/CameraEvents;)V", "camera", "Landroid/hardware/Camera;", "cameraAttributes", "Lcom/camerakit/api/CameraAttributes;", "cameraHandler", "Lcom/camerakit/api/CameraHandler;", "getCameraHandler", "()Lcom/camerakit/api/CameraHandler;", "capturePhoto", "", "callback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "jpeg", "onCameraClosed", "onCameraError", "onCameraOpened", "onPreviewError", "onPreviewStarted", "onPreviewStopped", "open", "facing", "Lcom/camerakit/type/CameraFacing;", "release", "setFlash", "flash", "Lcom/camerakit/type/CameraFlash;", "setPhotoSize", "size", "Lcom/camerakit/type/CameraSize;", "setPreviewOrientation", "degrees", "", "setPreviewSize", "startPreview", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", "stopPreview", "Attributes", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class Camera1 implements CameraApi, CameraEvents {
    private final /* synthetic */ CameraEvents $$delegate_0;
    private Camera camera;
    private CameraAttributes cameraAttributes;
    private final CameraHandler cameraHandler = CameraHandler.Companion.get();

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[CameraFacing.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1 = new int[CameraFlash.values().length];

        static {
            $EnumSwitchMapping$0[CameraFacing.BACK.ordinal()] = 1;
            $EnumSwitchMapping$0[CameraFacing.FRONT.ordinal()] = 2;
            $EnumSwitchMapping$1[CameraFlash.OFF.ordinal()] = 1;
            $EnumSwitchMapping$1[CameraFlash.ON.ordinal()] = 2;
            $EnumSwitchMapping$1[CameraFlash.AUTO.ordinal()] = 3;
            $EnumSwitchMapping$1[CameraFlash.TORCH.ordinal()] = 4;
        }
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraClosed() {
        this.$$delegate_0.onCameraClosed();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraError() {
        this.$$delegate_0.onCameraError();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraOpened(CameraAttributes cameraAttributes) {
        Intrinsics.checkParameterIsNotNull(cameraAttributes, "cameraAttributes");
        this.$$delegate_0.onCameraOpened(cameraAttributes);
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewError() {
        this.$$delegate_0.onPreviewError();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewStarted() {
        this.$$delegate_0.onPreviewStarted();
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewStopped() {
        this.$$delegate_0.onPreviewStopped();
    }

    public Camera1(CameraEvents eventsDelegate) {
        Intrinsics.checkParameterIsNotNull(eventsDelegate, "eventsDelegate");
        this.$$delegate_0 = eventsDelegate;
    }

    @Override // com.camerakit.api.CameraApi
    public CameraHandler getCameraHandler() {
        return this.cameraHandler;
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void open(CameraFacing facing) {
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        int i = WhenMappings.$EnumSwitchMapping$0[facing.ordinal()];
        int cameraId = 1;
        if (i == 1) {
            cameraId = 0;
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == cameraId) {
                Camera camera = Camera.open(i2);
                Intrinsics.checkExpressionValueIsNotNull(camera, "camera");
                Camera.Parameters cameraParameters = camera.getParameters();
                Intrinsics.checkExpressionValueIsNotNull(cameraParameters, "cameraParameters");
                Attributes cameraAttributes = new Attributes(cameraInfo, cameraParameters, facing);
                this.camera = camera;
                this.cameraAttributes = cameraAttributes;
                onCameraOpened(cameraAttributes);
            }
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void release() {
        Camera camera = this.camera;
        if (camera != null) {
            camera.release();
        }
        this.camera = null;
        this.cameraAttributes = null;
        onCameraClosed();
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPreviewOrientation(int degrees) {
        Camera camera = this.camera;
        if (camera != null) {
            camera.setDisplayOrientation(degrees);
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPreviewSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        Camera camera = this.camera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(size.getWidth(), size.getHeight());
            camera.setParameters(parameters);
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void startPreview(SurfaceTexture surfaceTexture) {
        Intrinsics.checkParameterIsNotNull(surfaceTexture, "surfaceTexture");
        Camera camera = this.camera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Intrinsics.checkExpressionValueIsNotNull(parameters, "parameters");
            if (parameters.getSupportedFocusModes() != null && parameters.getSupportedFocusModes().contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
                camera.setParameters(parameters);
            }
            camera.setPreviewTexture(surfaceTexture);
            camera.setOneShotPreviewCallback(new Camera.PreviewCallback() { // from class: com.camerakit.api.camera1.Camera1$startPreview$1
                @Override // android.hardware.Camera.PreviewCallback
                public final void onPreviewFrame(byte[] $noName_0, Camera $noName_1) {
                    Camera1.this.onPreviewStarted();
                }
            });
            camera.startPreview();
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void stopPreview() {
        Camera camera = this.camera;
        if (camera != null) {
            camera.stopPreview();
            onPreviewStopped();
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setFlash(CameraFlash flash) {
        String str;
        Intrinsics.checkParameterIsNotNull(flash, "flash");
        Camera camera = this.camera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Intrinsics.checkExpressionValueIsNotNull(parameters, "parameters");
            int i = WhenMappings.$EnumSwitchMapping$1[flash.ordinal()];
            if (i == 1) {
                str = DebugKt.DEBUG_PROPERTY_VALUE_OFF;
            } else if (i == 2) {
                str = DebugKt.DEBUG_PROPERTY_VALUE_ON;
            } else if (i == 3) {
                str = DebugKt.DEBUG_PROPERTY_VALUE_AUTO;
            } else if (i == 4) {
                str = "torch";
            } else {
                throw new NoWhenBranchMatchedException();
            }
            parameters.setFlashMode(str);
            try {
                camera.setParameters(parameters);
            } catch (Exception e) {
            }
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPhotoSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        Camera camera = this.camera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureSize(size.getWidth(), size.getHeight());
            try {
                camera.setParameters(parameters);
            } catch (Exception e) {
            }
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void capturePhoto(Function1<? super byte[], Unit> function1) {
        Intrinsics.checkParameterIsNotNull(function1, "callback");
        Camera camera = this.camera;
        if (camera != null) {
            camera.takePicture(null, null, new Camera.PictureCallback(camera) { // from class: com.camerakit.api.camera1.Camera1$capturePhoto$1
                final /* synthetic */ Camera $camera;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.$camera = r2;
                }

                @Override // android.hardware.Camera.PictureCallback
                public final void onPictureTaken(byte[] data, Camera $noName_1) {
                    Function1 function12 = Function1.this;
                    Intrinsics.checkExpressionValueIsNotNull(data, UriUtil.DATA_SCHEME);
                    function12.invoke(data);
                    this.$camera.startPreview();
                }
            });
        }
    }

    /* compiled from: Camera1.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005R\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\n\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eX\u0096\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eX\u0096\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0019\u0010\u0016R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lcom/camerakit/api/camera1/Camera1$Attributes;", "Lcom/camerakit/api/CameraAttributes;", "cameraInfo", "Landroid/hardware/Camera$CameraInfo;", "cameraParameters", "Landroid/hardware/Camera$Parameters;", "Landroid/hardware/Camera;", "cameraFacing", "Lcom/camerakit/type/CameraFacing;", "(Landroid/hardware/Camera$CameraInfo;Landroid/hardware/Camera$Parameters;Lcom/camerakit/type/CameraFacing;)V", "facing", "getFacing", "()Lcom/camerakit/type/CameraFacing;", "flashes", "", "Lcom/camerakit/type/CameraFlash;", "getFlashes", "()[Lcom/camerakit/type/CameraFlash;", "[Lcom/camerakit/type/CameraFlash;", "photoSizes", "Lcom/camerakit/type/CameraSize;", "getPhotoSizes", "()[Lcom/camerakit/type/CameraSize;", "[Lcom/camerakit/type/CameraSize;", "previewSizes", "getPreviewSizes", "sensorOrientation", "", "getSensorOrientation", "()I", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    private static final class Attributes implements CameraAttributes {
        private final CameraFacing facing;
        private final CameraFlash[] flashes;
        private final CameraSize[] photoSizes;
        private final CameraSize[] previewSizes;
        private final int sensorOrientation;

        public Attributes(Camera.CameraInfo cameraInfo, Camera.Parameters cameraParameters, CameraFacing cameraFacing) {
            Intrinsics.checkParameterIsNotNull(cameraInfo, "cameraInfo");
            Intrinsics.checkParameterIsNotNull(cameraParameters, "cameraParameters");
            Intrinsics.checkParameterIsNotNull(cameraFacing, "cameraFacing");
            this.facing = cameraFacing;
            this.sensorOrientation = cameraInfo.orientation;
            this.previewSizes = ParametersKt.getPreviewSizes(cameraParameters);
            this.photoSizes = ParametersKt.getPhotoSizes(cameraParameters);
            this.flashes = ParametersKt.getFlashes(cameraParameters);
        }

        @Override // com.camerakit.api.CameraAttributes
        public CameraFacing getFacing() {
            return this.facing;
        }

        @Override // com.camerakit.api.CameraAttributes
        public int getSensorOrientation() {
            return this.sensorOrientation;
        }

        @Override // com.camerakit.api.CameraAttributes
        public CameraSize[] getPreviewSizes() {
            return this.previewSizes;
        }

        @Override // com.camerakit.api.CameraAttributes
        public CameraSize[] getPhotoSizes() {
            return this.photoSizes;
        }

        @Override // com.camerakit.api.CameraAttributes
        public CameraFlash[] getFlashes() {
            return this.flashes;
        }
    }
}
