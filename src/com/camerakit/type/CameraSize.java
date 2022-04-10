package com.camerakit.type;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CameraSize.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\t\u001a\u00020\u0003J\u0006\u0010\n\u001a\u00020\u000bJ\u0011\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0000H\u0096\u0002J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\r\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0017"}, d2 = {"Lcom/camerakit/type/CameraSize;", "", "width", "", "height", "(II)V", "getHeight", "()I", "getWidth", "area", "aspectRatio", "", "compareTo", "other", "component1", "component2", "copy", "equals", "", "", "hashCode", "toString", "", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class CameraSize implements Comparable<CameraSize> {
    private final int height;
    private final int width;

    public static /* synthetic */ CameraSize copy$default(CameraSize cameraSize, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = cameraSize.width;
        }
        if ((i3 & 2) != 0) {
            i2 = cameraSize.height;
        }
        return cameraSize.copy(i, i2);
    }

    public final int component1() {
        return this.width;
    }

    public final int component2() {
        return this.height;
    }

    public final CameraSize copy(int i, int i2) {
        return new CameraSize(i, i2);
    }

    @Override // java.lang.Object
    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof CameraSize) {
                CameraSize cameraSize = (CameraSize) obj;
                if (this.width == cameraSize.width) {
                    if (this.height == cameraSize.height) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // java.lang.Object
    public int hashCode() {
        return (this.width * 31) + this.height;
    }

    @Override // java.lang.Object
    public String toString() {
        return "CameraSize(width=" + this.width + ", height=" + this.height + ")";
    }

    public CameraSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int area() {
        return this.width * this.height;
    }

    public final float aspectRatio() {
        int i;
        int i2 = this.width;
        if (i2 == 0 || (i = this.height) == 0) {
            return 1.0f;
        }
        return ((float) i2) / ((float) i);
    }

    public int compareTo(CameraSize other) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        int areaDiff = (this.width * this.height) - (other.width * other.height);
        if (areaDiff > 0) {
            return 1;
        }
        if (areaDiff < 0) {
            return -1;
        }
        return 0;
    }
}
