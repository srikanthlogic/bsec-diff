package com.camerakit.api;

import android.os.Handler;
import android.os.HandlerThread;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
/* compiled from: CameraHandler.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0006"}, d2 = {"Lcom/camerakit/api/CameraHandler;", "Landroid/os/Handler;", "thread", "Landroid/os/HandlerThread;", "(Landroid/os/HandlerThread;)V", "Companion", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraHandler extends Handler {
    public static final Companion Companion = new Companion(null);

    private CameraHandler(HandlerThread thread) {
        super(thread.getLooper());
        thread.setUncaughtExceptionHandler(AnonymousClass1.INSTANCE);
    }

    public /* synthetic */ CameraHandler(HandlerThread thread, DefaultConstructorMarker $constructor_marker) {
        this(thread);
    }

    /* compiled from: CameraHandler.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/camerakit/api/CameraHandler$Companion;", "", "()V", "get", "Lcom/camerakit/api/CameraHandler;", "camerakit_release"}, k = 1, mv = {1, 1, 13})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final CameraHandler get() {
            HandlerThread cameraThread = new HandlerThread("CameraHandler@" + System.currentTimeMillis());
            cameraThread.start();
            return new CameraHandler(cameraThread, null);
        }
    }
}
