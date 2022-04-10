package com.camerakit;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.camerakit.api.CameraApi;
import com.camerakit.api.CameraAttributes;
import com.camerakit.api.CameraEvents;
import com.camerakit.api.ManagedCameraApi;
import com.camerakit.api.camera1.Camera1;
import com.camerakit.api.camera2.Camera2;
import com.camerakit.preview.CameraSurfaceTexture;
import com.camerakit.preview.CameraSurfaceTextureListener;
import com.camerakit.preview.CameraSurfaceView;
import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import com.camerakit.util.CameraSizeCalculator;
import com.facebook.imagepipeline.common.RotationOptions;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.ThreadPoolDispatcherKt;
/* compiled from: CameraPreview.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0018\u0018\u0000 k2\u00020\u00012\u00020\u0002:\u0006jklmnoB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010V\u001a\u00020\u00132\u0006\u0010W\u001a\u00020XJ\u0011\u0010Y\u001a\u00020\u0013H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010ZJ\b\u0010[\u001a\u00020\u0013H\u0016J\b\u0010\\\u001a\u00020\u0013H\u0016J\u0010\u0010]\u001a\u00020\u00132\u0006\u0010^\u001a\u00020\nH\u0016J\b\u0010_\u001a\u00020\u0013H\u0016J\b\u0010`\u001a\u00020\u0013H\u0016J\b\u0010a\u001a\u00020\u0013H\u0016J\u0011\u0010b\u001a\u00020\u0013H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010ZJ\u0006\u0010c\u001a\u00020\u0013J\u0006\u0010d\u001a\u00020\u0013J\u000e\u0010e\u001a\u00020\u00132\u0006\u0010f\u001a\u00020\u0010J\u0011\u0010g\u001a\u00020\u0013H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010ZJ\u0006\u0010h\u001a\u00020\u0013J\u0011\u0010i\u001a\u00020\u0013H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010ZR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R\u001a\u0010&\u001a\u00020'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u00102\u001a\u000203X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001c\u00108\u001a\u0004\u0018\u000109X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010>\u001a\u00020?X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001a\u0010D\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010 \"\u0004\bF\u0010\"R\u001a\u0010G\u001a\u00020?X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010A\"\u0004\bI\u0010CR\u0016\u0010J\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010K\u001a\u00020?8FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010A\"\u0004\bM\u0010CR\u001a\u0010N\u001a\u00020OX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u0010\u0010T\u001a\u0004\u0018\u00010UX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006p"}, d2 = {"Lcom/camerakit/CameraPreview;", "Landroid/widget/FrameLayout;", "Lcom/camerakit/api/CameraEvents;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attributeSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "attributes", "Lcom/camerakit/api/CameraAttributes;", "cameraApi", "Lcom/camerakit/api/CameraApi;", "cameraDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "cameraFacing", "Lcom/camerakit/type/CameraFacing;", "cameraOpenContinuation", "Lkotlin/coroutines/Continuation;", "", "state", "Lcom/camerakit/CameraPreview$CameraState;", "cameraState", "getCameraState", "()Lcom/camerakit/CameraPreview$CameraState;", "setCameraState", "(Lcom/camerakit/CameraPreview$CameraState;)V", "cameraSurfaceView", "Lcom/camerakit/preview/CameraSurfaceView;", "captureOrientation", "", "getCaptureOrientation", "()I", "setCaptureOrientation", "(I)V", "displayOrientation", "getDisplayOrientation", "setDisplayOrientation", "flash", "Lcom/camerakit/type/CameraFlash;", "getFlash", "()Lcom/camerakit/type/CameraFlash;", "setFlash", "(Lcom/camerakit/type/CameraFlash;)V", "imageMegaPixels", "", "getImageMegaPixels", "()F", "setImageMegaPixels", "(F)V", "lifecycleState", "Lcom/camerakit/CameraPreview$LifecycleState;", "getLifecycleState", "()Lcom/camerakit/CameraPreview$LifecycleState;", "setLifecycleState", "(Lcom/camerakit/CameraPreview$LifecycleState;)V", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/camerakit/CameraPreview$Listener;", "getListener", "()Lcom/camerakit/CameraPreview$Listener;", "setListener", "(Lcom/camerakit/CameraPreview$Listener;)V", "photoSize", "Lcom/camerakit/type/CameraSize;", "getPhotoSize", "()Lcom/camerakit/type/CameraSize;", "setPhotoSize", "(Lcom/camerakit/type/CameraSize;)V", "previewOrientation", "getPreviewOrientation", "setPreviewOrientation", "previewSize", "getPreviewSize", "setPreviewSize", "previewStartContinuation", "surfaceSize", "getSurfaceSize", "setSurfaceSize", "surfaceState", "Lcom/camerakit/CameraPreview$SurfaceState;", "getSurfaceState", "()Lcom/camerakit/CameraPreview$SurfaceState;", "setSurfaceState", "(Lcom/camerakit/CameraPreview$SurfaceState;)V", "surfaceTexture", "Lcom/camerakit/preview/CameraSurfaceTexture;", "capturePhoto", "callback", "Lcom/camerakit/CameraPreview$PhotoCallback;", "closeCamera", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCameraClosed", "onCameraError", "onCameraOpened", "cameraAttributes", "onPreviewError", "onPreviewStarted", "onPreviewStopped", "openCamera", "pause", "resume", "start", "facing", "startPreview", "stop", "stopPreview", "CameraState", "Companion", "LifecycleState", "Listener", "PhotoCallback", "SurfaceState", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraPreview extends FrameLayout implements CameraEvents {
    public static final Companion Companion = new Companion(null);
    private static final boolean FORCE_DEPRECATED_API;
    private CameraAttributes attributes;
    private final CameraApi cameraApi;
    private Continuation<? super Unit> cameraOpenContinuation;
    private final CameraSurfaceView cameraSurfaceView;
    private int captureOrientation;
    private int displayOrientation;
    private Listener listener;
    private int previewOrientation;
    private Continuation<? super Unit> previewStartContinuation;
    private CameraSurfaceTexture surfaceTexture;
    private LifecycleState lifecycleState = LifecycleState.STOPPED;
    private SurfaceState surfaceState = SurfaceState.SURFACE_WAITING;
    private CameraState cameraState = CameraState.CAMERA_CLOSED;
    private CameraSize previewSize = new CameraSize(0, 0);
    private CameraSize surfaceSize = new CameraSize(0, 0);
    private CameraSize photoSize = new CameraSize(0, 0);
    private CameraFlash flash = CameraFlash.OFF;
    private float imageMegaPixels = 2.0f;
    private CameraFacing cameraFacing = CameraFacing.BACK;
    private final CoroutineDispatcher cameraDispatcher = ThreadPoolDispatcherKt.newSingleThreadContext("CAMERA");

    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/camerakit/CameraPreview$CameraState;", "", "(Ljava/lang/String;I)V", "CAMERA_OPENING", "CAMERA_OPENED", "PREVIEW_STARTING", "PREVIEW_STARTED", "PREVIEW_STOPPING", "PREVIEW_STOPPED", "CAMERA_CLOSING", "CAMERA_CLOSED", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public enum CameraState {
        CAMERA_OPENING,
        CAMERA_OPENED,
        PREVIEW_STARTING,
        PREVIEW_STARTED,
        PREVIEW_STOPPING,
        PREVIEW_STOPPED,
        CAMERA_CLOSING,
        CAMERA_CLOSED
    }

    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/camerakit/CameraPreview$LifecycleState;", "", "(Ljava/lang/String;I)V", "STARTED", "RESUMED", "PAUSED", "STOPPED", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public enum LifecycleState {
        STARTED,
        RESUMED,
        PAUSED,
        STOPPED
    }

    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007"}, d2 = {"Lcom/camerakit/CameraPreview$Listener;", "", "onCameraClosed", "", "onCameraOpened", "onPreviewStarted", "onPreviewStopped", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public interface Listener {
        void onCameraClosed();

        void onCameraOpened();

        void onPreviewStarted();

        void onPreviewStopped();
    }

    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/camerakit/CameraPreview$PhotoCallback;", "", "onCapture", "", "jpeg", "", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public interface PhotoCallback {
        void onCapture(byte[] bArr);
    }

    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/camerakit/CameraPreview$SurfaceState;", "", "(Ljava/lang/String;I)V", "SURFACE_AVAILABLE", "SURFACE_WAITING", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public enum SurfaceState {
        SURFACE_AVAILABLE,
        SURFACE_WAITING
    }

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[CameraState.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1 = new int[CameraFacing.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$2 = new int[CameraFacing.values().length];

        static {
            $EnumSwitchMapping$0[CameraState.CAMERA_OPENED.ordinal()] = 1;
            $EnumSwitchMapping$0[CameraState.PREVIEW_STARTED.ordinal()] = 2;
            $EnumSwitchMapping$0[CameraState.PREVIEW_STOPPED.ordinal()] = 3;
            $EnumSwitchMapping$0[CameraState.CAMERA_CLOSING.ordinal()] = 4;
            $EnumSwitchMapping$1[CameraFacing.BACK.ordinal()] = 1;
            $EnumSwitchMapping$1[CameraFacing.FRONT.ordinal()] = 2;
            $EnumSwitchMapping$2[CameraFacing.BACK.ordinal()] = 1;
            $EnumSwitchMapping$2[CameraFacing.FRONT.ordinal()] = 2;
        }
    }

    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/camerakit/CameraPreview$Companion;", "", "()V", "FORCE_DEPRECATED_API", "", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public final LifecycleState getLifecycleState() {
        return this.lifecycleState;
    }

    public final void setLifecycleState(LifecycleState lifecycleState) {
        Intrinsics.checkParameterIsNotNull(lifecycleState, "<set-?>");
        this.lifecycleState = lifecycleState;
    }

    public final SurfaceState getSurfaceState() {
        return this.surfaceState;
    }

    public final void setSurfaceState(SurfaceState surfaceState) {
        Intrinsics.checkParameterIsNotNull(surfaceState, "<set-?>");
        this.surfaceState = surfaceState;
    }

    public final CameraState getCameraState() {
        return this.cameraState;
    }

    public final void setCameraState(CameraState state) {
        Listener listener;
        Intrinsics.checkParameterIsNotNull(state, "state");
        this.cameraState = state;
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            Listener listener2 = this.listener;
            if (listener2 != null) {
                listener2.onCameraOpened();
            }
        } else if (i == 2) {
            Listener listener3 = this.listener;
            if (listener3 != null) {
                listener3.onPreviewStarted();
            }
        } else if (i == 3) {
            Listener listener4 = this.listener;
            if (listener4 != null) {
                listener4.onPreviewStopped();
            }
        } else if (i == 4 && (listener = this.listener) != null) {
            listener.onCameraClosed();
        }
    }

    public final Listener getListener() {
        return this.listener;
    }

    public final void setListener(Listener listener) {
        this.listener = listener;
    }

    public final int getDisplayOrientation() {
        return this.displayOrientation;
    }

    public final void setDisplayOrientation(int i) {
        this.displayOrientation = i;
    }

    public final int getPreviewOrientation() {
        return this.previewOrientation;
    }

    public final void setPreviewOrientation(int i) {
        this.previewOrientation = i;
    }

    public final int getCaptureOrientation() {
        return this.captureOrientation;
    }

    public final void setCaptureOrientation(int i) {
        this.captureOrientation = i;
    }

    public final CameraSize getPreviewSize() {
        return this.previewSize;
    }

    public final void setPreviewSize(CameraSize cameraSize) {
        Intrinsics.checkParameterIsNotNull(cameraSize, "<set-?>");
        this.previewSize = cameraSize;
    }

    public final void setSurfaceSize(CameraSize cameraSize) {
        Intrinsics.checkParameterIsNotNull(cameraSize, "<set-?>");
        this.surfaceSize = cameraSize;
    }

    public final CameraSize getSurfaceSize() {
        CameraSize size;
        CameraSurfaceTexture cameraSurfaceTexture = this.surfaceTexture;
        return (cameraSurfaceTexture == null || (size = cameraSurfaceTexture.getSize()) == null) ? this.surfaceSize : size;
    }

    public final CameraSize getPhotoSize() {
        return this.photoSize;
    }

    public final void setPhotoSize(CameraSize cameraSize) {
        Intrinsics.checkParameterIsNotNull(cameraSize, "<set-?>");
        this.photoSize = cameraSize;
    }

    public final CameraFlash getFlash() {
        return this.flash;
    }

    public final void setFlash(CameraFlash cameraFlash) {
        Intrinsics.checkParameterIsNotNull(cameraFlash, "<set-?>");
        this.flash = cameraFlash;
    }

    public final float getImageMegaPixels() {
        return this.imageMegaPixels;
    }

    public final void setImageMegaPixels(float f) {
        this.imageMegaPixels = f;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraPreview(Context context) {
        super(context);
        Camera2 camera2;
        Intrinsics.checkParameterIsNotNull(context, "context");
        boolean z = false;
        Context context2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context2, "context");
        this.cameraSurfaceView = new CameraSurfaceView(context2);
        z = Build.VERSION.SDK_INT < 21 ? true : z;
        if (z) {
            camera2 = new Camera1(this);
        } else if (!z) {
            Context context3 = getContext();
            Intrinsics.checkExpressionValueIsNotNull(context3, "context");
            camera2 = new Camera2(this, context3);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        this.cameraApi = new ManagedCameraApi(camera2);
        Object systemService = getContext().getSystemService("window");
        if (systemService != null) {
            Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
            Intrinsics.checkExpressionValueIsNotNull(defaultDisplay, "windowManager.defaultDisplay");
            this.displayOrientation = defaultDisplay.getRotation() * 90;
            this.cameraSurfaceView.setCameraSurfaceTextureListener(new CameraSurfaceTextureListener() { // from class: com.camerakit.CameraPreview.1
                @Override // com.camerakit.preview.CameraSurfaceTextureListener
                public void onSurfaceReady(CameraSurfaceTexture cameraSurfaceTexture) {
                    Intrinsics.checkParameterIsNotNull(cameraSurfaceTexture, "cameraSurfaceTexture");
                    CameraPreview.this.surfaceTexture = cameraSurfaceTexture;
                    CameraPreview.this.setSurfaceState(SurfaceState.SURFACE_AVAILABLE);
                    if (CameraPreview.this.getLifecycleState() == LifecycleState.RESUMED) {
                        CameraPreview.this.resume();
                    }
                }
            });
            addView(this.cameraSurfaceView);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Camera2 camera2;
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attributeSet, "attributeSet");
        boolean z = false;
        Context context2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context2, "context");
        this.cameraSurfaceView = new CameraSurfaceView(context2);
        z = Build.VERSION.SDK_INT < 21 ? true : z;
        if (z) {
            camera2 = new Camera1(this);
        } else if (!z) {
            Context context3 = getContext();
            Intrinsics.checkExpressionValueIsNotNull(context3, "context");
            camera2 = new Camera2(this, context3);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        this.cameraApi = new ManagedCameraApi(camera2);
        Object systemService = getContext().getSystemService("window");
        if (systemService != null) {
            Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
            Intrinsics.checkExpressionValueIsNotNull(defaultDisplay, "windowManager.defaultDisplay");
            this.displayOrientation = defaultDisplay.getRotation() * 90;
            this.cameraSurfaceView.setCameraSurfaceTextureListener(new CameraSurfaceTextureListener() { // from class: com.camerakit.CameraPreview.1
                @Override // com.camerakit.preview.CameraSurfaceTextureListener
                public void onSurfaceReady(CameraSurfaceTexture cameraSurfaceTexture) {
                    Intrinsics.checkParameterIsNotNull(cameraSurfaceTexture, "cameraSurfaceTexture");
                    CameraPreview.this.surfaceTexture = cameraSurfaceTexture;
                    CameraPreview.this.setSurfaceState(SurfaceState.SURFACE_AVAILABLE);
                    if (CameraPreview.this.getLifecycleState() == LifecycleState.RESUMED) {
                        CameraPreview.this.resume();
                    }
                }
            });
            addView(this.cameraSurfaceView);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
    }

    public final void start(CameraFacing facing) {
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        Job unused = BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, this.cameraDispatcher, null, new CameraPreview$start$1(this, facing, null), 2, null);
    }

    public final void resume() {
        Job unused = BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, this.cameraDispatcher, null, new CameraPreview$resume$1(this, null), 2, null);
    }

    public final void pause() {
        Job unused = BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, this.cameraDispatcher, null, new CameraPreview$pause$1(this, null), 2, null);
    }

    public final void stop() {
        Job unused = BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, this.cameraDispatcher, null, new CameraPreview$stop$1(this, null), 2, null);
    }

    public final void capturePhoto(PhotoCallback callback) {
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        Job unused = BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, this.cameraDispatcher, null, new CameraPreview$capturePhoto$1(this, callback, null), 2, null);
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraOpened(CameraAttributes cameraAttributes) {
        Intrinsics.checkParameterIsNotNull(cameraAttributes, "cameraAttributes");
        setCameraState(CameraState.CAMERA_OPENED);
        this.attributes = cameraAttributes;
        Continuation<? super Unit> continuation = this.cameraOpenContinuation;
        if (continuation != null) {
            Unit unit = Unit.INSTANCE;
            Result.Companion companion = Result.Companion;
            continuation.resumeWith(Result.m13constructorimpl(unit));
        }
        this.cameraOpenContinuation = null;
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraClosed() {
        setCameraState(CameraState.CAMERA_CLOSED);
    }

    @Override // com.camerakit.api.CameraEvents
    public void onCameraError() {
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewStarted() {
        setCameraState(CameraState.PREVIEW_STARTED);
        Continuation<? super Unit> continuation = this.previewStartContinuation;
        if (continuation != null) {
            Unit unit = Unit.INSTANCE;
            Result.Companion companion = Result.Companion;
            continuation.resumeWith(Result.m13constructorimpl(unit));
        }
        this.previewStartContinuation = null;
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewStopped() {
        setCameraState(CameraState.PREVIEW_STOPPED);
    }

    @Override // com.camerakit.api.CameraEvents
    public void onPreviewError() {
    }

    public final /* synthetic */ Object openCamera(Continuation<? super Unit> continuation) {
        SafeContinuation it = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        this.cameraOpenContinuation = it;
        setCameraState(CameraState.CAMERA_OPENING);
        this.cameraApi.open(this.cameraFacing);
        Object orThrow = it.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    public final /* synthetic */ Object startPreview(Continuation<? super Unit> continuation) {
        int i;
        int i2;
        CameraSize cameraSize;
        CameraSize cameraSize2;
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        SafeContinuation it = safeContinuation;
        boolean z = false;
        this.previewStartContinuation = it;
        CameraSurfaceTexture surfaceTexture = this.surfaceTexture;
        CameraAttributes attributes = this.attributes;
        if (surfaceTexture == null || attributes == null) {
            Result.Companion companion = Result.Companion;
            it.resumeWith(Result.m13constructorimpl(ResultKt.createFailure(new IllegalStateException())));
            this.previewStartContinuation = null;
        } else {
            setCameraState(CameraState.PREVIEW_STARTING);
            int i3 = WhenMappings.$EnumSwitchMapping$1[this.cameraFacing.ordinal()];
            if (i3 == 1) {
                i = ((attributes.getSensorOrientation() - getDisplayOrientation()) + 360) % 360;
            } else if (i3 == 2) {
                i = (360 - ((attributes.getSensorOrientation() + getDisplayOrientation()) % 360)) % 360;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            setPreviewOrientation(i);
            int i4 = WhenMappings.$EnumSwitchMapping$2[this.cameraFacing.ordinal()];
            if (i4 == 1) {
                i2 = ((attributes.getSensorOrientation() - getDisplayOrientation()) + 360) % 360;
            } else if (i4 == 2) {
                i2 = ((attributes.getSensorOrientation() + getDisplayOrientation()) + 360) % 360;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            setCaptureOrientation(i2);
            if (Build.VERSION.SDK_INT >= 21) {
                surfaceTexture.setRotation(getDisplayOrientation());
            }
            CameraSizeCalculator cameraSizeCalculator = new CameraSizeCalculator(attributes.getPreviewSizes());
            if (getPreviewOrientation() % RotationOptions.ROTATE_180 == 0) {
                z = true;
            }
            if (z) {
                cameraSize = new CameraSize(getWidth(), getHeight());
            } else if (!z) {
                cameraSize = new CameraSize(getHeight(), getWidth());
            } else {
                throw new NoWhenBranchMatchedException();
            }
            setPreviewSize(cameraSizeCalculator.findClosestSizeContainingTarget(cameraSize));
            surfaceTexture.setDefaultBufferSize(getPreviewSize().getWidth(), getPreviewSize().getHeight());
            if (getPreviewOrientation() % RotationOptions.ROTATE_180 != 0) {
                cameraSize2 = new CameraSize(getPreviewSize().getHeight(), getPreviewSize().getWidth());
            } else {
                cameraSize2 = getPreviewSize();
            }
            surfaceTexture.setSize(cameraSize2);
            setPhotoSize(new CameraSizeCalculator(attributes.getPhotoSizes()).findClosestSizeMatchingArea((int) (getImageMegaPixels() * ((float) 1000000))));
            this.cameraApi.setPreviewOrientation(getPreviewOrientation());
            this.cameraApi.setPreviewSize(getPreviewSize());
            this.cameraApi.setPhotoSize(getPhotoSize());
            this.cameraApi.startPreview(surfaceTexture);
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    public final /* synthetic */ Object stopPreview(Continuation<? super Unit> continuation) {
        SafeContinuation it = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        setCameraState(CameraState.PREVIEW_STOPPING);
        this.cameraApi.stopPreview();
        Unit unit = Unit.INSTANCE;
        Result.Companion companion = Result.Companion;
        it.resumeWith(Result.m13constructorimpl(unit));
        Object orThrow = it.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    public final /* synthetic */ Object closeCamera(Continuation<? super Unit> continuation) {
        SafeContinuation it = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        setCameraState(CameraState.CAMERA_CLOSING);
        this.cameraApi.release();
        Unit unit = Unit.INSTANCE;
        Result.Companion companion = Result.Companion;
        it.resumeWith(Result.m13constructorimpl(unit));
        Object orThrow = it.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }
}
