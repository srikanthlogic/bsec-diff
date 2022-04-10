package com.alcorlink.camera.extension;
/* loaded from: classes5.dex */
public class H264PictureTypeControl {
    public static final byte H264_PIC_TYPE_IDR = 1;
    public static final byte H264_PIC_TYPE_IDR_SPS_PPS = 2;
    public static final byte H264_PIC_TYPE_I_FRAME = 0;
    public short wLayerID;
    public short wPicType;

    public H264PictureTypeControl() {
        setValue(0, 0);
    }

    public H264PictureTypeControl(short s, short s2) {
        setValue(s, s2);
    }

    public void setValue(short s, short s2) {
        this.wLayerID = s;
        this.wPicType = s2;
    }
}
