package com.alcorlink.camera.extension;
/* loaded from: classes5.dex */
public class H264BitRateLayers {
    public int dwAverageBitrate;
    public int dwPeakBitrate;
    public short wLayerID;

    public H264BitRateLayers() {
        setValue(0, 0, 0);
    }

    public H264BitRateLayers(short s, int i, int i2) {
        setValue(s, i, i2);
    }

    public void setValue(short s, int i, int i2) {
        this.wLayerID = s;
        this.dwPeakBitrate = i;
        this.dwAverageBitrate = i2;
    }
}
