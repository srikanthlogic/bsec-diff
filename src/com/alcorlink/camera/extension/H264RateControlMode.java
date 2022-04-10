package com.alcorlink.camera.extension;
/* loaded from: classes5.dex */
public class H264RateControlMode {
    public static final byte H264_RATE_MODE_CBR = 1;
    public static final byte H264_RATE_MODE_CONSTANT_QP = 3;
    public static final byte H264_RATE_MODE_VBR = 2;
    public byte bRateCtrlMode;
    public short wLayerID;

    public H264RateControlMode() {
        setValue(0, (byte) 0);
    }

    public H264RateControlMode(short s, byte b) {
        setValue(s, b);
    }

    public void setValue(short s, byte b) {
        this.wLayerID = s;
        this.bRateCtrlMode = b;
    }
}
