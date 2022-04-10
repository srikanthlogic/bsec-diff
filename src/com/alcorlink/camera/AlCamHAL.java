package com.alcorlink.camera;

import a.a.a;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import java.nio.ByteBuffer;
import java.util.Iterator;
/* loaded from: classes5.dex */
public class AlCamHAL {

    /* renamed from: a */
    private Context f9a;
    private UsbDevice b;
    private UsbManager c;
    private UsbDeviceConnection d;
    private boolean e;
    private c f;
    private StreamConfig[] g;
    private int h;
    private a<StreamConfig> i;
    private int[] k = new int[1];
    private int[] l = new int[1];
    private a j = a.a();

    public AlCamHAL(Context context, UsbDevice usbDevice) throws CameraException {
        System.loadLibrary("AlCamera");
        this.f9a = context;
        this.c = (UsbManager) this.f9a.getSystemService("usb");
        if (this.c != null) {
            this.b = usbDevice;
            this.d = null;
            this.e = false;
            this.f = new c(this);
            return;
        }
        throw new CameraException("Can't get UsbManager from given Context");
    }

    public static int a(int i) {
        if (i == -9) {
            return AlErrorCode.ERR_COMMAND;
        }
        if (i != -7) {
            return i != -4 ? i != 0 ? 255 : 0 : AlErrorCode.ERR_NO_DEVICE;
        }
        return 164;
    }

    private void a(Context context) {
        int[] iArr = new int[1];
        String[] strArr = (String[]) nativeGetLogMsg(iArr, new int[1]);
        if (strArr != null) {
            c.a("============================>");
            for (int i = 0; i < iArr[0]; i++) {
                strArr[i].isEmpty();
            }
            c.a("<============================");
        }
    }

    private int d() {
        this.d = this.c.openDevice(this.b);
        if (this.d == null) {
            return AlErrorCode.ERR_RESOURCE;
        }
        int i = 0;
        for (int i2 = 0; i2 <= 0; i2++) {
            i = this.d.getFileDescriptor();
            new StringBuilder("fd=").append(Integer.toHexString(i));
            if (i > 0) {
                break;
            }
            new StringBuilder("get device fd fail ").append(Integer.toHexString(i));
        }
        if (i <= 0) {
            new StringBuilder("get device fd fail ").append(Integer.toHexString(i));
            return 192;
        }
        int nativeInitLib = nativeInitLib(i, this.b.getVendorId(), this.b.getProductId(), 0);
        if (nativeInitLib == 0) {
            return 0;
        }
        a(this.f9a);
        return c.a(nativeInitLib);
    }

    private native void nativeCloseCamera();

    private native int nativeGetFrameOnce(int i, ByteBuffer byteBuffer, int[] iArr, int[] iArr2, int i2);

    private native Object[] nativeGetLogMsg(int[] iArr, int[] iArr2);

    private native int nativeGetStreamConfig(StreamConfig[] streamConfigArr, int[] iArr);

    private native int nativeInitLib(int i, int i2, int i3, int i4);

    private native int nativeSetResolution(int i, int i2, int i3, int i4, int i5);

    private native int nativeStartStreaming(byte b);

    private native int nativeStopStreaming(byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void GetVersion(char[] cArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264GetBitrate(int i, Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264GetFormat(byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264GetIFramePeriod(short[] sArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264GetQpSteps(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264GetRateCtrlMode(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264GetVersion(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264Reset(short s);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264SetBitrate(int i, Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264SetFormat(byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264SetIFramePeriod(short[] sArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264SetNextFrameType(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264SetQpSteps(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int H264SetRateCtrlMode(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int MpExtRomWriteEnable(int i, byte b, byte b2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int MpFwCompare(int i, byte[] bArr, int i2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int MpFwDump(int i, byte[] bArr, int i2, int[] iArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int MpFwRevision(int i, short[] sArr, char[] cArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int MpFwUpgrade(int i, byte[] bArr, int i2, byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int MpQueryExtRom(int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcEepromRead(int i, short s, short s2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcEepromWrite(int i, short s, short s2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcLedBrightnessGet(int i, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcLedBrightnessSet(int i, byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcSpiSectorErase(int i, byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcSpiSectorRead(int i, byte b, short s, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcSpiSectorWrite(int i, byte b, short s, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcTouchStatus(int i, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int UcUsbSpeed(int i, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuChipReset(int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuGetMirror(int i, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuOsdGetDatetime(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuOsdGetString(byte b, Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuOsdSetDatetime(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuOsdSetString(byte b, Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuSensorRead(int i, short s, short s2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuSensorWrite(int i, short s, short s2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuSetFlip(int i, byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int XuSetMirror(int i, byte b);

    public final int a(byte b) {
        if (!this.e) {
            return AlErrorCode.ERR_NOT_INIT;
        }
        int nativeStartStreaming = nativeStartStreaming(b);
        if (nativeStartStreaming == 0) {
            return nativeStartStreaming;
        }
        new StringBuilder("start stream error ").append(nativeStartStreaming);
        a(this.f9a);
        return c.a(nativeStartStreaming);
    }

    public final int a(int i, int i2, int i3, int i4) {
        if (!this.e) {
            return AlErrorCode.ERR_NOT_INIT;
        }
        int nativeSetResolution = nativeSetResolution(i, i2, i3, i4, 30);
        if (nativeSetResolution == 0) {
            return nativeSetResolution;
        }
        new Object[1][0] = Integer.valueOf(nativeSetResolution);
        a(this.f9a);
        return c.a(nativeSetResolution);
    }

    public final int a(AlFrame alFrame, int i) {
        if (!this.e) {
            return 0;
        }
        int nativeGetFrameOnce = nativeGetFrameOnce(alFrame.getStreamId(), alFrame.getFrameByteBuffer(), this.k, this.l, i);
        if (nativeGetFrameOnce > 0) {
            alFrame.validBufferLength = nativeGetFrameOnce;
            alFrame.serialId = this.k[0];
            alFrame.timeFromLast = this.l[0];
            return 0;
        } else if (nativeGetFrameOnce == -27) {
            return 255;
        } else {
            if (nativeGetFrameOnce != -3) {
                return c.a(nativeGetFrameOnce);
            }
            return 208;
        }
    }

    public final void a() {
        if (this.e) {
            nativeCloseCamera();
            this.e = false;
        }
        UsbDeviceConnection usbDeviceConnection = this.d;
        if (usbDeviceConnection != null) {
            usbDeviceConnection.close();
        }
        this.d = null;
        if (this.g != null) {
            for (int i = 0; i < 32; i++) {
                this.g[i] = null;
            }
        }
    }

    public final int b(byte b) {
        if (!this.e) {
            return AlErrorCode.ERR_NOT_INIT;
        }
        int nativeStopStreaming = nativeStopStreaming(b);
        if (nativeStopStreaming == 0) {
            return nativeStopStreaming;
        }
        new StringBuilder("stop stream error ").append(nativeStopStreaming);
        a(this.f9a);
        return c.a(nativeStopStreaming);
    }

    public final Iterator<StreamConfig> b() {
        return this.i.iterator();
    }

    public final int c() {
        synchronized (this.f) {
            StringBuilder sb = new StringBuilder("HAL ref count =");
            c cVar = this.f;
            sb.append(0);
            c cVar2 = this.f;
        }
        if (this.b == null) {
            return AlErrorCode.ERR_NO_DEVICE;
        }
        int d = d();
        if (d != 0) {
            new StringBuilder("init lib fail ").append(d);
            return d;
        }
        int[] iArr = new int[1];
        this.g = new StreamConfig[32];
        for (int i = 0; i < 32; i++) {
            this.g[i] = new StreamConfig();
        }
        this.i = new a<>(this);
        int nativeGetStreamConfig = nativeGetStreamConfig(this.g, iArr);
        if (nativeGetStreamConfig != 0) {
            a(this.f9a);
            return c.a(nativeGetStreamConfig);
        }
        new StringBuilder("Stream config num =").append(iArr[0]);
        this.h = iArr[0];
        this.e = true;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public native int nativeGetPU(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int nativeSetPU(int i, int i2);
}
