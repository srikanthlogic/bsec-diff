package com.camerakit.preview;

import android.graphics.SurfaceTexture;
import android.opengl.Matrix;
import com.camerakit.type.CameraSize;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraSurfaceTexture.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0014J\t\u0010\u0017\u001a\u00020\u0016H\u0082 J\u0019\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0082 J\t\u0010\u0019\u001a\u00020\u0016H\u0082 J\u0019\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u0003H\u0082 J\u0019\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007H\u0082 J\b\u0010\u001e\u001a\u00020\u0016H\u0016J\u000e\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0003J\b\u0010!\u001a\u00020\u0016H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/camerakit/preview/CameraSurfaceTexture;", "Landroid/graphics/SurfaceTexture;", "inputTexture", "", "outputTexture", "(II)V", "extraTransformMatrix", "", "nativeHandle", "", "getOutputTexture", "()I", "previewInvalidated", "", "size", "Lcom/camerakit/type/CameraSize;", "getSize", "()Lcom/camerakit/type/CameraSize;", "setSize", "(Lcom/camerakit/type/CameraSize;)V", "transformMatrix", "finalize", "", "nativeFinalize", "nativeInit", "nativeRelease", "nativeSetSize", "width", "height", "nativeUpdateTexImage", "release", "setRotation", "degrees", "updateTexImage", "Companion", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraSurfaceTexture extends SurfaceTexture {
    public static final Companion Companion = new Companion(null);
    private long nativeHandle;
    private final int outputTexture;
    private boolean previewInvalidated;
    private CameraSize size = new CameraSize(0, 0);
    private final float[] transformMatrix = new float[16];
    private final float[] extraTransformMatrix = new float[16];

    private final native void nativeFinalize();

    private final native void nativeInit(int i, int i2);

    private final native void nativeRelease();

    private final native void nativeSetSize(int i, int i2);

    private final native void nativeUpdateTexImage(float[] fArr, float[] fArr2);

    public CameraSurfaceTexture(int inputTexture, int outputTexture) {
        super(inputTexture);
        this.outputTexture = outputTexture;
        nativeInit(inputTexture, this.outputTexture);
        Matrix.setIdentityM(this.extraTransformMatrix, 0);
    }

    public final int getOutputTexture() {
        return this.outputTexture;
    }

    public final CameraSize getSize() {
        return this.size;
    }

    public final void setSize(CameraSize size) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        this.size = size;
        this.previewInvalidated = true;
    }

    @Override // android.graphics.SurfaceTexture
    public void updateTexImage() {
        if (this.previewInvalidated) {
            nativeSetSize(this.size.getWidth(), this.size.getHeight());
            this.previewInvalidated = false;
        }
        super.updateTexImage();
        getTransformMatrix(this.transformMatrix);
        nativeUpdateTexImage(this.transformMatrix, this.extraTransformMatrix);
    }

    @Override // android.graphics.SurfaceTexture
    public void release() {
        nativeRelease();
    }

    public final void setRotation(int degrees) {
        Matrix.setIdentityM(this.extraTransformMatrix, 0);
        Matrix.rotateM(this.extraTransformMatrix, 0, (float) degrees, 0.0f, 0.0f, 1.0f);
    }

    @Override // android.graphics.SurfaceTexture, java.lang.Object
    protected void finalize() {
        super.finalize();
        try {
            nativeFinalize();
        } catch (Exception e) {
        }
    }

    /* compiled from: CameraSurfaceTexture.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/camerakit/preview/CameraSurfaceTexture$Companion;", "", "()V", "camerakit_release"}, k = 1, mv = {1, 1, 13})
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
