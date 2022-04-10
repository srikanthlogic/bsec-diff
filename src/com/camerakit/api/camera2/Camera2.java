package com.camerakit.api.camera2;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.view.Surface;
import com.camerakit.api.CameraApi;
import com.camerakit.api.CameraAttributes;
import com.camerakit.api.CameraEvents;
import com.camerakit.api.CameraHandler;
import com.camerakit.api.camera2.ext.CameraCharacteristicsKt;
import com.camerakit.api.camera2.ext.CameraDeviceKt;
import com.camerakit.api.camera2.ext.CameraManagerKt;
import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Camera2.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008b\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u0014\b\u0007\u0018\u0000 E2\u00020\u00012\u00020\u0002:\u0002DEB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J+\u0010*\u001a\u00020$2!\u0010+\u001a\u001d\u0012\u0013\u0012\u00110 ¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020$0\u001fH\u0016J\b\u0010,\u001a\u00020$H\u0002J\b\u0010-\u001a\u00020$H\u0002J\t\u0010.\u001a\u00020$H\u0096\u0001J\t\u0010/\u001a\u00020$H\u0096\u0001J\u0011\u00100\u001a\u00020$2\u0006\u0010\u0007\u001a\u00020\bH\u0096\u0001J\t\u00101\u001a\u00020$H\u0096\u0001J\t\u00102\u001a\u00020$H\u0096\u0001J\t\u00103\u001a\u00020$H\u0096\u0001J\u0010\u00104\u001a\u00020$2\u0006\u00105\u001a\u00020\fH\u0016J\b\u00106\u001a\u00020$H\u0016J\b\u00107\u001a\u00020$H\u0002J\u0010\u00108\u001a\u00020$2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u00109\u001a\u00020$2\u0006\u0010:\u001a\u00020;H\u0016J\u0010\u0010<\u001a\u00020$2\u0006\u0010=\u001a\u00020\u0019H\u0016J\u0010\u0010>\u001a\u00020$2\u0006\u0010:\u001a\u00020;H\u0016J\u0010\u0010?\u001a\u00020$2\u0006\u0010@\u001a\u00020AH\u0016J\b\u0010B\u001a\u00020$H\u0016J\b\u0010C\u001a\u00020$H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R+\u0010\u001e\u001a\u001f\u0012\u0013\u0012\u00110 ¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020$\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lcom/camerakit/api/camera2/Camera2;", "Lcom/camerakit/api/CameraApi;", "Lcom/camerakit/api/CameraEvents;", "eventsDelegate", "context", "Landroid/content/Context;", "(Lcom/camerakit/api/CameraEvents;Landroid/content/Context;)V", "cameraAttributes", "Lcom/camerakit/api/CameraAttributes;", "cameraDevice", "Landroid/hardware/camera2/CameraDevice;", "cameraFacing", "Lcom/camerakit/type/CameraFacing;", "cameraHandler", "Lcom/camerakit/api/CameraHandler;", "getCameraHandler", "()Lcom/camerakit/api/CameraHandler;", "cameraManager", "Landroid/hardware/camera2/CameraManager;", "captureCallback", "com/camerakit/api/camera2/Camera2$captureCallback$1", "Lcom/camerakit/api/camera2/Camera2$captureCallback$1;", "captureSession", "Landroid/hardware/camera2/CameraCaptureSession;", "captureState", "", "flash", "Lcom/camerakit/type/CameraFlash;", "imageReader", "Landroid/media/ImageReader;", "photoCallback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "jpeg", "", "previewRequestBuilder", "Landroid/hardware/camera2/CaptureRequest$Builder;", "previewStarted", "", "waitingFrames", "capturePhoto", "callback", "captureStillPicture", "lockFocus", "onCameraClosed", "onCameraError", "onCameraOpened", "onPreviewError", "onPreviewStarted", "onPreviewStopped", "open", "facing", "release", "runPreCaptureSequence", "setFlash", "setPhotoSize", "size", "Lcom/camerakit/type/CameraSize;", "setPreviewOrientation", "degrees", "setPreviewSize", "startPreview", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", "stopPreview", "unlockFocus", "Attributes", "Companion", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class Camera2 implements CameraApi, CameraEvents {
    public static final Companion Companion = new Companion(null);
    private static final int STATE_PICTURE_TAKEN;
    private static final int STATE_PREVIEW;
    private static final int STATE_WAITING_LOCK;
    private static final int STATE_WAITING_NON_PRECAPTURE;
    private static final int STATE_WAITING_PRECAPTURE;
    private final /* synthetic */ CameraEvents $$delegate_0;
    private CameraAttributes cameraAttributes;
    private CameraDevice cameraDevice;
    private CameraFacing cameraFacing;
    private final CameraHandler cameraHandler = CameraHandler.Companion.get();
    private final CameraManager cameraManager;
    private final Camera2$captureCallback$1 captureCallback;
    private CameraCaptureSession captureSession;
    private int captureState;
    private CameraFlash flash;
    private ImageReader imageReader;
    private Function1<? super byte[], Unit> photoCallback;
    private CaptureRequest.Builder previewRequestBuilder;
    private boolean previewStarted;
    private int waitingFrames;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[CameraFlash.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1 = new int[CameraFlash.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$2 = new int[CameraFlash.values().length];

        static {
            $EnumSwitchMapping$0[CameraFlash.ON.ordinal()] = 1;
            $EnumSwitchMapping$1[CameraFlash.ON.ordinal()] = 1;
            $EnumSwitchMapping$2[CameraFlash.ON.ordinal()] = 1;
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

    public Camera2(CameraEvents eventsDelegate, Context context) {
        Intrinsics.checkParameterIsNotNull(eventsDelegate, "eventsDelegate");
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.$$delegate_0 = eventsDelegate;
        Object systemService = context.getSystemService("camera");
        if (systemService != null) {
            this.cameraManager = (CameraManager) systemService;
            this.flash = CameraFlash.OFF;
            this.cameraFacing = CameraFacing.BACK;
            this.captureCallback = new Camera2$captureCallback$1(this);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.hardware.camera2.CameraManager");
    }

    @Override // com.camerakit.api.CameraApi
    public CameraHandler getCameraHandler() {
        return this.cameraHandler;
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void open(CameraFacing facing) {
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        this.cameraFacing = facing;
        String cameraId = CameraManagerKt.getCameraId(this.cameraManager, facing);
        if (cameraId != null) {
            CameraManagerKt.whenDeviceAvailable(this.cameraManager, cameraId, getCameraHandler(), new Camera2$open$1(this, cameraId, this.cameraManager.getCameraCharacteristics(cameraId), facing));
        } else {
            throw new RuntimeException();
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void release() {
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
        }
        this.cameraDevice = null;
        CameraCaptureSession cameraCaptureSession = this.captureSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
        }
        this.captureSession = null;
        this.cameraAttributes = null;
        ImageReader imageReader = this.imageReader;
        if (imageReader != null) {
            imageReader.close();
        }
        this.imageReader = null;
        this.previewStarted = false;
        onCameraClosed();
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPreviewOrientation(int degrees) {
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPreviewSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void startPreview(SurfaceTexture surfaceTexture) {
        Intrinsics.checkParameterIsNotNull(surfaceTexture, "surfaceTexture");
        CameraDevice cameraDevice = this.cameraDevice;
        ImageReader imageReader = this.imageReader;
        if (!(cameraDevice == null || imageReader == null)) {
            Surface surface = new Surface(surfaceTexture);
            CameraDeviceKt.getCaptureSession(cameraDevice, surface, imageReader, getCameraHandler(), new Function1<CameraCaptureSession, Unit>(cameraDevice, surface) { // from class: com.camerakit.api.camera2.Camera2$startPreview$1
                final /* synthetic */ CameraDevice $cameraDevice;
                final /* synthetic */ Surface $surface;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.$cameraDevice = r2;
                    this.$surface = r3;
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(CameraCaptureSession cameraCaptureSession) {
                    invoke2(cameraCaptureSession);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(CameraCaptureSession captureSession) {
                    Camera2.this.captureSession = captureSession;
                    if (captureSession != null) {
                        CaptureRequest.Builder previewRequestBuilder = this.$cameraDevice.createCaptureRequest(1);
                        previewRequestBuilder.addTarget(this.$surface);
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                        previewRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
                        captureSession.setRepeatingRequest(previewRequestBuilder.build(), Camera2.this.captureCallback, Camera2.this.getCameraHandler());
                        Camera2.this.previewRequestBuilder = previewRequestBuilder;
                    }
                }
            });
        }
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void stopPreview() {
        CameraCaptureSession captureSession = this.captureSession;
        this.captureSession = null;
        if (captureSession != null) {
            try {
                captureSession.stopRepeating();
                captureSession.abortCaptures();
                captureSession.close();
                onPreviewStopped();
            } catch (Exception e) {
                onPreviewStopped();
            } catch (Throwable th) {
                onPreviewStopped();
                throw th;
            }
        }
        this.previewStarted = false;
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setFlash(CameraFlash flash) {
        Intrinsics.checkParameterIsNotNull(flash, "flash");
        this.flash = flash;
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void setPhotoSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        this.imageReader = ImageReader.newInstance(size.getWidth(), size.getHeight(), 256, 2);
    }

    @Override // com.camerakit.api.CameraActions
    public synchronized void capturePhoto(Function1<? super byte[], Unit> function1) {
        Intrinsics.checkParameterIsNotNull(function1, "callback");
        this.photoCallback = function1;
        if (this.cameraFacing == CameraFacing.BACK) {
            lockFocus();
        } else {
            captureStillPicture();
        }
    }

    private final void lockFocus() {
        CaptureRequest.Builder previewRequestBuilder = this.previewRequestBuilder;
        CameraCaptureSession captureSession = this.captureSession;
        if (previewRequestBuilder != null && captureSession != null) {
            try {
                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                this.captureState = 1;
                this.waitingFrames = 0;
                captureSession.capture(previewRequestBuilder.build(), this.captureCallback, getCameraHandler());
                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, null);
            } catch (Exception e) {
            }
        }
    }

    public final void runPreCaptureSequence() {
        CaptureRequest.Builder previewRequestBuilder = this.previewRequestBuilder;
        CameraCaptureSession captureSession = this.captureSession;
        if (previewRequestBuilder != null && captureSession != null) {
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
            int i = 2;
            this.captureState = 2;
            captureSession.capture(previewRequestBuilder.build(), this.captureCallback, getCameraHandler());
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, null);
            CaptureRequest.Key key = CaptureRequest.FLASH_MODE;
            if (WhenMappings.$EnumSwitchMapping$0[this.flash.ordinal()] != 1) {
                i = 0;
            }
            previewRequestBuilder.set(key, Integer.valueOf(i));
            captureSession.setRepeatingRequest(previewRequestBuilder.build(), this.captureCallback, getCameraHandler());
        }
    }

    public final void captureStillPicture() {
        int i;
        long delay;
        CameraCaptureSession captureSession = this.captureSession;
        CameraDevice cameraDevice = this.cameraDevice;
        ImageReader imageReader = this.imageReader;
        if (captureSession != null && cameraDevice != null && imageReader != null) {
            CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(2);
            captureBuilder.addTarget(imageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
            CaptureRequest.Key key = CaptureRequest.FLASH_MODE;
            if (WhenMappings.$EnumSwitchMapping$1[this.flash.ordinal()] != 1) {
                i = 0;
            } else {
                i = 1;
            }
            captureBuilder.set(key, Integer.valueOf(i));
            if (WhenMappings.$EnumSwitchMapping$2[this.flash.ordinal()] != 1) {
                delay = 0;
            } else {
                delay = 75;
            }
            getCameraHandler().postDelayed(new Camera2$captureStillPicture$1(this, captureSession, captureBuilder), delay);
        }
    }

    public final void unlockFocus() {
        CaptureRequest.Builder previewRequestBuilder = this.previewRequestBuilder;
        CameraCaptureSession captureSession = this.captureSession;
        if (previewRequestBuilder != null && captureSession != null) {
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
            captureSession.capture(previewRequestBuilder.build(), this.captureCallback, getCameraHandler());
            this.captureState = 0;
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, null);
            previewRequestBuilder.set(CaptureRequest.FLASH_MODE, 0);
            captureSession.setRepeatingRequest(previewRequestBuilder.build(), this.captureCallback, getCameraHandler());
        }
    }

    /* compiled from: Camera2.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/camerakit/api/camera2/Camera2$Companion;", "", "()V", "STATE_PICTURE_TAKEN", "", "STATE_PREVIEW", "STATE_WAITING_LOCK", "STATE_WAITING_NON_PRECAPTURE", "STATE_WAITING_PRECAPTURE", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* compiled from: Camera2.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u0004¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000bX\u0096\u0004¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u000bX\u0096\u0004¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0016\u0010\u0013R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lcom/camerakit/api/camera2/Camera2$Attributes;", "Lcom/camerakit/api/CameraAttributes;", "cameraCharacteristics", "Landroid/hardware/camera2/CameraCharacteristics;", "cameraFacing", "Lcom/camerakit/type/CameraFacing;", "(Landroid/hardware/camera2/CameraCharacteristics;Lcom/camerakit/type/CameraFacing;)V", "facing", "getFacing", "()Lcom/camerakit/type/CameraFacing;", "flashes", "", "Lcom/camerakit/type/CameraFlash;", "getFlashes", "()[Lcom/camerakit/type/CameraFlash;", "[Lcom/camerakit/type/CameraFlash;", "photoSizes", "Lcom/camerakit/type/CameraSize;", "getPhotoSizes", "()[Lcom/camerakit/type/CameraSize;", "[Lcom/camerakit/type/CameraSize;", "previewSizes", "getPreviewSizes", "sensorOrientation", "", "getSensorOrientation", "()I", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public static final class Attributes implements CameraAttributes {
        private final CameraFacing facing;
        private final CameraFlash[] flashes;
        private final CameraSize[] photoSizes;
        private final CameraSize[] previewSizes;
        private final int sensorOrientation;

        public Attributes(CameraCharacteristics cameraCharacteristics, CameraFacing cameraFacing) {
            Intrinsics.checkParameterIsNotNull(cameraCharacteristics, "cameraCharacteristics");
            Intrinsics.checkParameterIsNotNull(cameraFacing, "cameraFacing");
            this.facing = cameraFacing;
            this.sensorOrientation = CameraCharacteristicsKt.getSensorOrientation(cameraCharacteristics);
            this.previewSizes = CameraCharacteristicsKt.getPreviewSizes(cameraCharacteristics);
            this.photoSizes = CameraCharacteristicsKt.getPhotoSizes(cameraCharacteristics);
            this.flashes = CameraCharacteristicsKt.getFlashes(cameraCharacteristics);
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
