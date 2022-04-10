package com.camerakit.api;

import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import kotlin.Metadata;
/* compiled from: CameraAttributes.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lcom/camerakit/api/CameraAttributes;", "", "facing", "Lcom/camerakit/type/CameraFacing;", "getFacing", "()Lcom/camerakit/type/CameraFacing;", "flashes", "", "Lcom/camerakit/type/CameraFlash;", "getFlashes", "()[Lcom/camerakit/type/CameraFlash;", "photoSizes", "Lcom/camerakit/type/CameraSize;", "getPhotoSizes", "()[Lcom/camerakit/type/CameraSize;", "previewSizes", "getPreviewSizes", "sensorOrientation", "", "getSensorOrientation", "()I", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public interface CameraAttributes {
    CameraFacing getFacing();

    CameraFlash[] getFlashes();

    CameraSize[] getPhotoSizes();

    CameraSize[] getPreviewSizes();

    int getSensorOrientation();
}
