package com.jpegkit;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
/* loaded from: classes3.dex */
public class Jpeg implements Parcelable {
    private ByteBuffer mHandle;
    private static final Object sJniLock = new Object();
    public static final Parcelable.Creator<Jpeg> CREATOR = new Parcelable.Creator<Jpeg>() { // from class: com.jpegkit.Jpeg.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Jpeg createFromParcel(Parcel in) {
            byte[] jpegBytes = new byte[in.readInt()];
            in.readByteArray(jpegBytes);
            return new Jpeg(jpegBytes);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Jpeg[] newArray(int size) {
            return new Jpeg[size];
        }
    };

    private native void jniCrop(ByteBuffer byteBuffer, int i, int i2, int i3, int i4);

    private native void jniFlipHorizontal(ByteBuffer byteBuffer);

    private native void jniFlipVertical(ByteBuffer byteBuffer);

    private native int jniGetHeight(ByteBuffer byteBuffer);

    private native byte[] jniGetJpegBytes(ByteBuffer byteBuffer);

    private native long jniGetJpegSize(ByteBuffer byteBuffer);

    private native int jniGetWidth(ByteBuffer byteBuffer);

    private native ByteBuffer jniMount(byte[] bArr);

    private native void jniRelease(ByteBuffer byteBuffer);

    private native void jniRotate(ByteBuffer byteBuffer, int i);

    static {
        System.loadLibrary("jpegkit");
    }

    private Jpeg() {
    }

    public Jpeg(byte[] jpegBytes) {
        mount(jpegBytes);
    }

    protected void mount(byte[] jpegBytes) {
        synchronized (sJniLock) {
            this.mHandle = jniMount(jpegBytes);
        }
    }

    public byte[] getJpegBytes() {
        byte[] jniGetJpegBytes;
        synchronized (sJniLock) {
            jniGetJpegBytes = jniGetJpegBytes(this.mHandle);
        }
        return jniGetJpegBytes;
    }

    public long getJpegSize() {
        long jniGetJpegSize;
        synchronized (sJniLock) {
            jniGetJpegSize = jniGetJpegSize(this.mHandle);
        }
        return jniGetJpegSize;
    }

    public int getWidth() {
        int jniGetWidth;
        synchronized (sJniLock) {
            jniGetWidth = jniGetWidth(this.mHandle);
        }
        return jniGetWidth;
    }

    public int getHeight() {
        int jniGetHeight;
        synchronized (sJniLock) {
            jniGetHeight = jniGetHeight(this.mHandle);
        }
        return jniGetHeight;
    }

    public void rotate(int degrees) {
        synchronized (sJniLock) {
            jniRotate(this.mHandle, degrees);
        }
    }

    public void flipHorizontal() {
        synchronized (sJniLock) {
            jniFlipHorizontal(this.mHandle);
        }
    }

    public void flipVertical() {
        synchronized (sJniLock) {
            jniFlipVertical(this.mHandle);
        }
    }

    public void crop(Rect crop) {
        synchronized (sJniLock) {
            jniCrop(this.mHandle, crop.left, crop.top, crop.width(), crop.height());
        }
    }

    public void release() {
        synchronized (sJniLock) {
            jniRelease(this.mHandle);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte[] jpegBytes = getJpegBytes();
        dest.writeInt(jpegBytes.length);
        dest.writeByteArray(jpegBytes);
    }
}
