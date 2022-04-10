package com.camerakit.preview;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraSurfaceView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 22\u00020\u00012\u00020\u0002:\u00012B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0015J@\u0010\u0015\u001a\u00020\u001426\u0010\u0016\u001a2\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u00140\u0017H\u0002J!\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0018H\u0082 J\t\u0010!\u001a\u00020\u0014H\u0082 J\t\u0010\"\u001a\u00020\u0014H\u0082 J\t\u0010#\u001a\u00020\u0014H\u0082 J\u0019\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u0018H\u0082 J\t\u0010'\u001a\u00020\u0014H\u0082 J\t\u0010(\u001a\u00020\u0014H\u0082 J\u0010\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020\u0014H\u0016J\b\u0010-\u001a\u00020\u0014H\u0016J \u0010.\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+2\u0006\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u0018H\u0016J\u0018\u0010/\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+2\u0006\u00100\u001a\u000201H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u00128\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/camerakit/preview/CameraSurfaceView;", "Landroid/opengl/GLSurfaceView;", "Landroid/opengl/GLSurfaceView$Renderer;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attributeSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "cameraSurfaceTexture", "Lcom/camerakit/preview/CameraSurfaceTexture;", "cameraSurfaceTextureListener", "Lcom/camerakit/preview/CameraSurfaceTextureListener;", "getCameraSurfaceTextureListener", "()Lcom/camerakit/preview/CameraSurfaceTextureListener;", "setCameraSurfaceTextureListener", "(Lcom/camerakit/preview/CameraSurfaceTextureListener;)V", "nativeHandle", "", "finalize", "", "genTextures", "textureCallback", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "inputTexture", "outputTexture", "nativeDrawTexture", "texture", "textureWidth", "textureHeight", "nativeFinalize", "nativeInit", "nativeOnDrawFrame", "nativeOnSurfaceChanged", "width", "height", "nativeOnSurfaceCreated", "nativeRelease", "onDrawFrame", "gl", "Ljavax/microedition/khronos/opengles/GL10;", "onPause", "onResume", "onSurfaceChanged", "onSurfaceCreated", "config", "Ljavax/microedition/khronos/egl/EGLConfig;", "Companion", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    public static final Companion Companion = new Companion(null);
    private CameraSurfaceTexture cameraSurfaceTexture;
    private CameraSurfaceTextureListener cameraSurfaceTextureListener;
    private long nativeHandle;

    private final native void nativeDrawTexture(int i, int i2, int i3);

    private final native void nativeFinalize();

    private final native void nativeInit();

    private final native void nativeOnDrawFrame();

    private final native void nativeOnSurfaceChanged(int i, int i2);

    private final native void nativeOnSurfaceCreated();

    private final native void nativeRelease();

    public final CameraSurfaceTextureListener getCameraSurfaceTextureListener() {
        return this.cameraSurfaceTextureListener;
    }

    public final void setCameraSurfaceTextureListener(CameraSurfaceTextureListener cameraSurfaceTextureListener) {
        this.cameraSurfaceTextureListener = cameraSurfaceTextureListener;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraSurfaceView(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(0);
        nativeInit();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attributeSet, "attributeSet");
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(0);
        nativeInit();
    }

    @Override // android.opengl.GLSurfaceView
    public void onResume() {
        super.onResume();
    }

    @Override // android.opengl.GLSurfaceView
    public void onPause() {
        super.onPause();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Intrinsics.checkParameterIsNotNull(gl, "gl");
        Intrinsics.checkParameterIsNotNull(config, "config");
        genTextures(new CameraSurfaceView$onSurfaceCreated$1(this));
        nativeOnSurfaceCreated();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Intrinsics.checkParameterIsNotNull(gl, "gl");
        nativeOnSurfaceChanged(width, height);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        Intrinsics.checkParameterIsNotNull(gl, "gl");
        CameraSurfaceTexture cameraSurfaceTexture = this.cameraSurfaceTexture;
        if (cameraSurfaceTexture != null) {
            nativeOnDrawFrame();
            cameraSurfaceTexture.updateTexImage();
            nativeDrawTexture(cameraSurfaceTexture.getOutputTexture(), cameraSurfaceTexture.getSize().getWidth(), cameraSurfaceTexture.getSize().getHeight());
        }
    }

    private final void genTextures(Function2<? super Integer, ? super Integer, Unit> function2) {
        int[] textures = new int[2];
        GLES20.glGenTextures(2, textures, 0);
        function2.invoke(Integer.valueOf(textures[0]), Integer.valueOf(textures[1]));
    }

    @Override // android.opengl.GLSurfaceView, java.lang.Object
    protected void finalize() {
        super.finalize();
        try {
            nativeFinalize();
        } catch (Exception e) {
        }
    }

    /* compiled from: CameraSurfaceView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/camerakit/preview/CameraSurfaceView$Companion;", "", "()V", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        System.loadLibrary("camerakit");
    }
}
