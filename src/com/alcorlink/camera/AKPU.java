package com.alcorlink.camera;

import a.a.a;
/* loaded from: classes5.dex */
public class AKPU {
    public static final int PU_BACKLIGHT_COMPENSATION = 1;
    public static final int PU_BRIGHTNESS = 2;
    public static final int PU_CONTRAST = 3;
    public static final int PU_GAIN = 4;
    public static final int PU_GAMMA = 9;
    public static final int PU_HUE = 6;
    public static final int PU_POWER_LINE_FREQUENCY = 5;
    public static final int PU_SATURATION = 7;
    public static final int PU_SHARPNESS = 8;
    public static final int PU_WHITE_BALANCE_AUTO = 11;
    public static final int PU_WHITE_BALANCE_TEMPERATURE = 10;

    /* renamed from: a  reason: collision with root package name */
    private AlCamHAL f7a;
    private a b = a.a();

    /* JADX INFO: Access modifiers changed from: protected */
    public final int a(AlCamHAL alCamHAL) {
        if (this.f7a != null) {
            return AlErrorCode.ERR_IN_USE;
        }
        this.f7a = alCamHAL;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void a() {
        synchronized (this.f7a) {
            this.f7a = null;
        }
    }

    public int getPU(int i, int[] iArr, PuAttribute puAttribute) {
        AlCamHAL alCamHAL = this.f7a;
        if (alCamHAL != null) {
            int[] iArr2 = new int[1];
            int[] iArr3 = new int[1];
            int[] iArr4 = new int[1];
            int[] iArr5 = new int[1];
            int[] iArr6 = new int[1];
            int nativeGetPU = alCamHAL.nativeGetPU(iArr2, iArr3, iArr4, iArr5, iArr6, i);
            StringBuilder sb = new StringBuilder("min=");
            sb.append(iArr2[0]);
            sb.append(" max=");
            sb.append(iArr3[0]);
            sb.append(" step=");
            sb.append(iArr4[0]);
            sb.append(" def=");
            sb.append(iArr5[0]);
            sb.append(" cur=");
            sb.append(iArr6[0]);
            puAttribute.maximum = iArr3[0];
            puAttribute.minimum = iArr2[0];
            puAttribute.current = iArr6[0];
            puAttribute.defaultValue = iArr5[0];
            puAttribute.step = iArr4[0];
            iArr[0] = iArr6[0];
            return AlCamHAL.a(nativeGetPU);
        }
        throw new RuntimeException("device is closed or not properly initialized in AKPU.getPU()");
    }

    public int setPU(int i, int i2) {
        AlCamHAL alCamHAL = this.f7a;
        if (alCamHAL != null) {
            return AlCamHAL.a(alCamHAL.nativeSetPU(i2, i));
        }
        throw new RuntimeException("device is closed or not properly initialized in AKPU.getPU()");
    }
}
