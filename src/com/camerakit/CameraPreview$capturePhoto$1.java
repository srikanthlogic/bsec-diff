package com.camerakit;

import com.camerakit.CameraPreview;
import com.jpegkit.Jpeg;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CameraPreview.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 13})
@DebugMetadata(c = "com/camerakit/CameraPreview$capturePhoto$1", f = "CameraPreview.kt", i = {}, l = {155}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class CameraPreview$capturePhoto$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ CameraPreview.PhotoCallback $callback;
    int label;
    private CoroutineScope p$;
    final /* synthetic */ CameraPreview this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraPreview$capturePhoto$1(CameraPreview cameraPreview, CameraPreview.PhotoCallback photoCallback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cameraPreview;
        this.$callback = photoCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        CameraPreview$capturePhoto$1 cameraPreview$capturePhoto$1 = new CameraPreview$capturePhoto$1(this.this$0, this.$callback, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        cameraPreview$capturePhoto$1.p$ = (CoroutineScope) obj;
        return cameraPreview$capturePhoto$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CameraPreview$capturePhoto$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 13})
    @DebugMetadata(c = "com/camerakit/CameraPreview$capturePhoto$1$1", f = "CameraPreview.kt", i = {}, l = {156}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.camerakit.CameraPreview$capturePhoto$1$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;
        private CoroutineScope p$;

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            Intrinsics.checkParameterIsNotNull(continuation, "completion");
            AnonymousClass1 r0 = new AnonymousClass1(continuation);
            CoroutineScope coroutineScope = (CoroutineScope) obj;
            r0.p$ = (CoroutineScope) obj;
            return r0;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object result) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            } else if (!(result instanceof Result.Failure)) {
                CoroutineScope coroutineScope = this.p$;
                CameraPreview$capturePhoto$1.this.this$0.cameraApi.setFlash(CameraPreview$capturePhoto$1.this.this$0.getFlash());
                CameraPreview$capturePhoto$1.this.this$0.cameraApi.capturePhoto(new Function1<byte[], Unit>() { // from class: com.camerakit.CameraPreview.capturePhoto.1.1.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(byte[] bArr) {
                        invoke2(bArr);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke  reason: avoid collision after fix types in other method */
                    public final void invoke2(final byte[] it) {
                        Intrinsics.checkParameterIsNotNull(it, "it");
                        CameraPreview$capturePhoto$1.this.this$0.cameraApi.getCameraHandler().post(new Runnable() { // from class: com.camerakit.CameraPreview.capturePhoto.1.1.1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Jpeg jpeg = new Jpeg(it);
                                jpeg.rotate(CameraPreview$capturePhoto$1.this.this$0.getCaptureOrientation());
                                byte[] transformedBytes = jpeg.getJpegBytes();
                                Intrinsics.checkExpressionValueIsNotNull(transformedBytes, "jpeg.jpegBytes");
                                jpeg.release();
                                CameraPreview$capturePhoto$1.this.$callback.onCapture(transformedBytes);
                            }
                        });
                    }
                });
                return Unit.INSTANCE;
            } else {
                throw ((Result.Failure) result).exception;
            }
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object result) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else if (!(result instanceof Result.Failure)) {
            CoroutineScope coroutineScope = this.p$;
            Object unused = BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(null), 1, null);
            return Unit.INSTANCE;
        } else {
            throw ((Result.Failure) result).exception;
        }
    }
}
