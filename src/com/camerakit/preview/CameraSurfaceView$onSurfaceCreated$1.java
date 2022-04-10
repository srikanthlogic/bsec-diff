package com.camerakit.preview;

import android.graphics.SurfaceTexture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CameraSurfaceView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "inputTexture", "", "outputTexture", "invoke"}, k = 3, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraSurfaceView$onSurfaceCreated$1 extends Lambda implements Function2<Integer, Integer, Unit> {
    final /* synthetic */ CameraSurfaceView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraSurfaceView$onSurfaceCreated$1(CameraSurfaceView cameraSurfaceView) {
        super(2);
        this.this$0 = cameraSurfaceView;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2) {
        invoke(num.intValue(), num2.intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(int inputTexture, int outputTexture) {
        CameraSurfaceView cameraSurfaceView = this.this$0;
        CameraSurfaceTexture $receiver = new CameraSurfaceTexture(inputTexture, outputTexture);
        $receiver.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.camerakit.preview.CameraSurfaceView$onSurfaceCreated$1$$special$$inlined$apply$lambda$1
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(SurfaceTexture it) {
                CameraSurfaceView$onSurfaceCreated$1.this.this$0.requestRender();
            }
        });
        CameraSurfaceTextureListener cameraSurfaceTextureListener = this.this$0.getCameraSurfaceTextureListener();
        if (cameraSurfaceTextureListener != null) {
            cameraSurfaceTextureListener.onSurfaceReady($receiver);
        }
        cameraSurfaceView.cameraSurfaceTexture = $receiver;
    }
}
