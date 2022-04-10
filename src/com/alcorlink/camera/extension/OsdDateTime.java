package com.alcorlink.camera.extension;
/* loaded from: classes5.dex */
public class OsdDateTime {
    public byte bDay;
    public byte bHour;
    public byte bMinute;
    public byte bMonth;
    public byte bSecond;
    public byte bWeek;
    public byte bYearHi;
    public byte bYearLo;

    public OsdDateTime() {
    }

    public OsdDateTime(short s, byte b, byte b2, byte b3, byte b4, byte b5, byte b6) {
        setValue(s, b, b2, b3, b4, b5, b6);
    }

    private static byte a(byte b) {
        return (byte) (((b / 10) << 4) + (b % 10));
    }

    public void setValue(short s, byte b, byte b2, byte b3, byte b4, byte b5, byte b6) {
        this.bYearHi = a((byte) (s / 100));
        this.bYearLo = a((byte) (s % 100));
        this.bMonth = a(b);
        this.bDay = a(b2);
        this.bWeek = a(b3);
        this.bHour = a(b4);
        this.bMinute = a(b5);
        this.bSecond = a(b6);
    }
}
