package com.alcorlink.camera;

import a.a.a;
/* loaded from: classes5.dex */
public class AKXU {

    /* renamed from: a  reason: collision with root package name */
    private AlCamHAL f8a;
    private a b = a.a();

    public void GetVersion(char[] cArr) {
        this.f8a.GetVersion(cArr);
    }

    public int MPFwDump(byte[] bArr, int i, int[] iArr) {
        return AlCamHAL.a(this.f8a.MpFwDump(0, bArr, i, iArr));
    }

    public int MpExtRomWriteEnable(byte b, byte b2) {
        return AlCamHAL.a(this.f8a.MpExtRomWriteEnable(0, b, b2));
    }

    public int MpFwCompare(byte[] bArr, int i) {
        return AlCamHAL.a(this.f8a.MpFwCompare(0, bArr, i));
    }

    public int MpFwRevision(short[] sArr, char[] cArr) {
        return AlCamHAL.a(this.f8a.MpFwRevision(0, sArr, cArr));
    }

    public int MpFwUpgrade(byte[] bArr, int i, byte b) {
        return AlCamHAL.a(this.f8a.MpFwUpgrade(0, bArr, i, b));
    }

    public int MpQueryExtRom() {
        return AlCamHAL.a(this.f8a.MpQueryExtRom(0));
    }

    public int UcEepromRead(short s, short s2, byte[] bArr) {
        return AlCamHAL.a(this.f8a.UcEepromRead(0, s, s2, bArr));
    }

    public int UcEepromWrite(short s, short s2, byte[] bArr) {
        return AlCamHAL.a(this.f8a.UcEepromWrite(0, s, s2, bArr));
    }

    public int UcLedBrightnessGet(byte[] bArr) {
        return AlCamHAL.a(this.f8a.UcLedBrightnessGet(0, bArr));
    }

    public int UcLedBrightnessSet(byte b) {
        return AlCamHAL.a(this.f8a.UcLedBrightnessSet(0, b));
    }

    public int UcSpiSectorErase(byte b) {
        return AlCamHAL.a(this.f8a.UcSpiSectorErase(0, b));
    }

    public int UcSpiSectorRead(byte b, short s, byte[] bArr) {
        return AlCamHAL.a(this.f8a.UcSpiSectorRead(0, b, s, bArr));
    }

    public int UcSpiSectorWrite(byte b, short s, byte[] bArr) {
        return AlCamHAL.a(this.f8a.UcSpiSectorWrite(0, b, s, bArr));
    }

    public int UcTouchStatus(byte[] bArr) {
        return this.f8a.UcTouchStatus(0, bArr);
    }

    public int UcUsbSpeed(byte[] bArr) {
        return AlCamHAL.a(this.f8a.UcUsbSpeed(0, bArr));
    }

    public int XuChipReset() {
        return AlCamHAL.a(this.f8a.XuChipReset(0));
    }

    public int XuGetMirror(byte[] bArr) {
        return AlCamHAL.a(this.f8a.XuGetMirror(0, bArr));
    }

    public int XuSensorRead(short s, short s2, byte[] bArr) {
        return AlCamHAL.a(this.f8a.XuSensorRead(0, s, s2, bArr));
    }

    public int XuSensorWrite(short s, short s2, byte[] bArr) {
        return AlCamHAL.a(this.f8a.XuSensorWrite(0, s, s2, bArr));
    }

    public int XuSetFlip(byte b) {
        return AlCamHAL.a(this.f8a.XuSetFlip(0, b));
    }

    public int XuSetMirror(byte b) {
        return AlCamHAL.a(this.f8a.XuSetMirror(0, b));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int a(AlCamHAL alCamHAL) {
        if (this.f8a != null) {
            return AlErrorCode.ERR_IN_USE;
        }
        this.f8a = alCamHAL;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void a() {
        synchronized (this.f8a) {
            this.f8a = null;
        }
    }
}
