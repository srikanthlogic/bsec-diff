package com.alcorlink.camera.extension;
/* loaded from: classes5.dex */
public class H264QpStepLayers {
    public static final byte H264_FRAME_TYPE_ALL = 7;
    public static final byte H264_FRAME_TYPE_B = 4;
    public static final byte H264_FRAME_TYPE_I = 1;
    public static final byte H264_FRAME_TYPE_P = 2;
    public byte bFrameType;
    public byte bMaxQp;
    public byte bMinQp;
    public short wLayerID;

    public H264QpStepLayers() {
        setValue(0, (byte) 0, (byte) 0, (byte) 0);
    }

    public H264QpStepLayers(short s, byte b, byte b2, byte b3) {
        setValue(s, b, b2, b3);
    }

    public void setValue(short s, byte b, byte b2, byte b3) {
        this.wLayerID = s;
        this.bFrameType = b;
        this.bMinQp = b2;
        this.bMaxQp = b3;
    }
}
