package com.alcorlink.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import java.util.HashMap;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class e extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    private /* synthetic */ AlDevManager f18a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(AlDevManager alDevManager) {
        this.f18a = alDevManager;
    }

    private void a(String str, AlCameraDevice alCameraDevice) {
        Intent intent = new Intent();
        intent.setAction(str);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AlDevManager.CAMERA_BROADCAST, alCameraDevice);
        intent.putExtras(bundle);
        this.f18a.d.sendBroadcast(intent);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        AlCameraDevice b;
        AlCameraDevice b2;
        String action = intent.getAction();
        if ("com.alcorlink.camera.AlDevManager.USB_PERMISSION".equals(action)) {
            synchronized (this) {
                if (intent.getBooleanExtra("permission", false)) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (usbDevice == null || this.f18a.b.getDeviceName().compareTo(usbDevice.getDeviceName()) != 0) {
                        usbDevice = null;
                    }
                    if (!(usbDevice == null || (b2 = AlDevManager.b(this.f18a, usbDevice)) == null)) {
                        a(AlDevManager.ACTION_CAM_PERMISSION, b2);
                    }
                }
            }
        } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
            synchronized (this) {
                UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
                if (!(usbDevice2 == null || (b = AlDevManager.b(this.f18a, usbDevice2)) == null)) {
                    AlDevManager.b(this.f18a, b);
                    a(AlDevManager.ACTION_CAM_DETACH, b);
                }
            }
        } else if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
            synchronized (this) {
                UsbDevice usbDevice3 = (UsbDevice) intent.getParcelableExtra("device");
                if (this.f18a.e.a(usbDevice3)) {
                    AlCameraDevice alCameraDevice = new AlCameraDevice(usbDevice3);
                    HashMap hashMap = this.f18a.f;
                    AlDevManager alDevManager = this.f18a;
                    hashMap.put(AlDevManager.a(usbDevice3), alCameraDevice);
                    a(AlDevManager.ACTION_CAM_ATTACH, alCameraDevice);
                }
            }
        }
    }
}
