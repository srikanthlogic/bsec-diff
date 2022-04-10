package com.camerakit.api;

import kotlin.Metadata;
/* compiled from: CameraApi.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/camerakit/api/CameraApi;", "Lcom/camerakit/api/CameraActions;", "Lcom/camerakit/api/CameraEvents;", "cameraHandler", "Lcom/camerakit/api/CameraHandler;", "getCameraHandler", "()Lcom/camerakit/api/CameraHandler;", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public interface CameraApi extends CameraActions, CameraEvents {
    CameraHandler getCameraHandler();
}
