package com.alcorlink.camera;

import android.hardware.usb.UsbDevice;
import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes5.dex */
public class AlCameraDevice implements Parcelable {
    public static final Parcelable.Creator<AlCameraDevice> CREATOR = new d();

    /* renamed from: a */
    private UsbDevice f10a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private String g;

    public AlCameraDevice() {
    }

    public AlCameraDevice(UsbDevice usbDevice) {
        this.f10a = usbDevice;
        this.b = usbDevice.getVendorId();
        this.c = usbDevice.getProductId();
        this.d = usbDevice.getDeviceClass();
        this.e = usbDevice.getDeviceSubclass();
        this.f = usbDevice.getDeviceProtocol();
        this.g = new String(usbDevice.getDeviceName());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getDeviceClass() {
        return this.d;
    }

    public String getDeviceName() {
        return this.g;
    }

    public int getDeviceProtocol() {
        return this.f;
    }

    public int getDeviceSubClass() {
        return this.e;
    }

    public int getPid() {
        return this.c;
    }

    public UsbDevice getUsbDev() {
        return this.f10a;
    }

    public int getVid() {
        return this.b;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f10a, i);
    }
}
