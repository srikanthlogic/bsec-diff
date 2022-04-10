package com.alcorlink.camera;

import android.content.Context;
import android.hardware.usb.UsbDevice;
/* loaded from: classes5.dex */
public final class f {

    /* renamed from: a */
    private AKAVImage f19a;
    private AlCameraDevice b;
    private AKPU c;
    private AKXU d;
    private AKOSD e;
    private AKH264 f;
    private AlCamHAL g;
    private /* synthetic */ AlDevManager h;

    public f(AlDevManager alDevManager, AlCameraDevice alCameraDevice, Context context) throws CameraException {
        this.h = alDevManager;
        UsbDevice usbDev = alCameraDevice.getUsbDev();
        if (alDevManager.f11a.hasPermission(usbDev)) {
            this.b = alCameraDevice;
            AlDevManager alDevManager2 = this.h;
            String unused = AlDevManager.a(usbDev);
            AlCamHAL alCamHAL = new AlCamHAL(context, usbDev);
            int c = alCamHAL.c();
            if (c == 0) {
                this.g = alCamHAL;
                AlDevManager alDevManager3 = this.h;
                String unused2 = AlDevManager.a(alCameraDevice.getUsbDev());
                AKAVImage aKAVImage = new AKAVImage();
                if (aKAVImage.a(this.g) != 196) {
                    StringBuilder sb = new StringBuilder("openDevice -");
                    sb.append(alCameraDevice.getPid());
                    sb.append(" ");
                    sb.append(alCameraDevice.getDeviceName());
                    this.f19a = aKAVImage;
                    return;
                }
                AlErrorCode alErrorCode = new AlErrorCode(AlErrorCode.ERR_IN_USE);
                CameraException cameraException = new CameraException("Device is in used");
                cameraException.a(alErrorCode);
                throw cameraException;
            }
            AlErrorCode alErrorCode2 = new AlErrorCode(c);
            CameraException cameraException2 = new CameraException("Open device fail-" + alErrorCode2.getErrCodeString());
            cameraException2.a(alErrorCode2);
            throw cameraException2;
        }
        new CameraException("Dev Not Permitted").a(new AlErrorCode(AlErrorCode.ERR_PERMISSION_DENIED));
        throw new CameraException("Dev Not Permitted");
    }

    public static /* synthetic */ AKAVImage a(f fVar) {
        return fVar.f19a;
    }

    public final AKPU a() {
        if (this.c == null) {
            AlCameraDevice alCameraDevice = this.b;
            AlDevManager alDevManager = this.h;
            String unused = AlDevManager.a(alCameraDevice.getUsbDev());
            AKPU akpu = new AKPU();
            akpu.a(this.g);
            this.c = akpu;
        }
        return this.c;
    }

    public final AKXU b() {
        if (this.d == null) {
            AlCameraDevice alCameraDevice = this.b;
            AlDevManager alDevManager = this.h;
            String unused = AlDevManager.a(alCameraDevice.getUsbDev());
            AKXU akxu = new AKXU();
            akxu.a(this.g);
            this.d = akxu;
        }
        return this.d;
    }

    public final void c() {
        AKPU akpu = this.c;
        if (akpu != null) {
            akpu.a();
        }
        AKXU akxu = this.d;
        if (akxu != null) {
            akxu.a();
        }
        AKAVImage aKAVImage = this.f19a;
        if (aKAVImage != null) {
            aKAVImage.a();
        }
        AlCamHAL alCamHAL = this.g;
        if (alCamHAL != null) {
            alCamHAL.a();
        }
        this.f19a = null;
        this.e = null;
        this.f = null;
        this.c = null;
        this.d = null;
        this.g = null;
    }
}
