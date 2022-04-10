package com.alcorlink.camera;

import a.a.a;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
/* loaded from: classes5.dex */
public class j {

    /* renamed from: a  reason: collision with root package name */
    private final a f22a = a.a();

    public boolean a(UsbDevice usbDevice) {
        if (usbDevice.getInterfaceCount() == 6) {
            new StringBuilder("deviceID =").append(usbDevice.getDeviceId());
            UsbInterface usbInterface = usbDevice.getInterface(0);
            if (usbInterface != null) {
                new StringBuilder("endpoint cnt=").append(usbInterface.getEndpointCount());
                usbInterface.getId();
            }
        }
        return usbDevice.getDeviceClass() == 239 && usbDevice.getDeviceSubclass() == 2 && usbDevice.getDeviceProtocol() == 1;
    }
}
