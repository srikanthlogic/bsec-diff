package com.camerakit;

import com.camerakit.CameraPreview;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CameraPreview.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 13})
@DebugMetadata(c = "com/camerakit/CameraPreview$stop$1", f = "CameraPreview.kt", i = {}, l = {146}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class CameraPreview$stop$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    private CoroutineScope p$;
    final /* synthetic */ CameraPreview this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraPreview$stop$1(CameraPreview cameraPreview, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cameraPreview;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        CameraPreview$stop$1 cameraPreview$stop$1 = new CameraPreview$stop$1(this.this$0, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        cameraPreview$stop$1.p$ = (CoroutineScope) obj;
        return cameraPreview$stop$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CameraPreview$stop$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CameraPreview.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 13})
    @DebugMetadata(c = "com/camerakit/CameraPreview$stop$1$1", f = "CameraPreview.kt", i = {}, l = {147, 150}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.camerakit.CameraPreview$stop$1$1  reason: invalid class name */
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
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else if (result instanceof Result.Failure) {
                    throw ((Result.Failure) result).exception;
                }
            } else if (!(result instanceof Result.Failure)) {
                CoroutineScope coroutineScope = this.p$;
                CameraPreview$stop$1.this.this$0.setLifecycleState(CameraPreview.LifecycleState.STOPPED);
                CameraPreview cameraPreview = CameraPreview$stop$1.this.this$0;
                this.label = 1;
                if (cameraPreview.closeCamera(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                throw ((Result.Failure) result).exception;
            }
            return Unit.INSTANCE;
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
