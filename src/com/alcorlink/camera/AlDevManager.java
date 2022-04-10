package com.alcorlink.camera;

import a.a.a;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import java.util.HashMap;
/* loaded from: classes5.dex */
public class AlDevManager {
    public static final String ACTION_CAM_ATTACH;
    public static final String ACTION_CAM_DETACH;
    public static final String ACTION_CAM_PERMISSION;
    public static final String CAMERA_BROADCAST;
    private static AlDevManager i;

    /* renamed from: a */
    private UsbManager f11a;
    private UsbDevice b;
    private PendingIntent c;
    private Context d;
    private j e;
    private HashMap<String, AlCameraDevice> f;
    private HashMap<String, f> g;
    private a h;
    private final BroadcastReceiver j;

    private AlDevManager() {
        this.j = new e(this);
    }

    private AlDevManager(UsbManager usbManager, Context context) {
        this.j = new e(this);
        this.d = context.getApplicationContext();
        this.f11a = usbManager;
        this.f = new HashMap<>();
        this.g = new HashMap<>();
        this.h = a.a();
        this.e = new j();
        this.c = PendingIntent.getBroadcast(this.d, 0, new Intent("com.alcorlink.camera.AlDevManager.USB_PERMISSION"), 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction("com.alcorlink.camera.AlDevManager.USB_PERMISSION");
        this.d.registerReceiver(this.j, intentFilter);
    }

    private f a(String str) throws CameraException {
        f fVar = this.g.get(str);
        if (fVar != null) {
            return fVar;
        }
        AlErrorCode alErrorCode = new AlErrorCode(AlErrorCode.ERR_NOT_INIT);
        CameraException cameraException = new CameraException("Device is not in hash table");
        cameraException.a(alErrorCode);
        throw cameraException;
    }

    public static String a(UsbDevice usbDevice) {
        return usbDevice.getProductId() + " " + usbDevice.getDeviceName();
    }

    public static /* synthetic */ AlCameraDevice b(AlDevManager alDevManager, UsbDevice usbDevice) {
        String a2 = a(usbDevice);
        if (!alDevManager.f.containsKey(a2)) {
            return null;
        }
        return alDevManager.f.get(a2);
    }

    public static /* synthetic */ void b(AlDevManager alDevManager, AlCameraDevice alCameraDevice) {
        String a2 = a(alCameraDevice.getUsbDev());
        if (alDevManager.f.containsKey(a2)) {
            alDevManager.f.remove(a2);
        }
    }

    public static synchronized AlDevManager getInstance(UsbManager usbManager, Context context) {
        AlDevManager alDevManager;
        synchronized (AlDevManager.class) {
            if (i == null) {
                i = new AlDevManager(usbManager, context);
            }
            alDevManager = i;
        }
        return alDevManager;
    }

    public void closeAlCameraDevice(AlCameraDevice alCameraDevice) {
        String a2 = a(alCameraDevice.getUsbDev());
        try {
            a(a2).c();
            f fVar = this.g.get(a2);
            if (fVar != null) {
                this.g.remove(fVar);
            }
        } catch (CameraException e) {
        }
    }

    public void exit() {
        i = null;
        this.d.unregisterReceiver(this.j);
    }

    public AKAVImage getAKAVImage(AlCameraDevice alCameraDevice) throws CameraException {
        return a(a(alCameraDevice.getUsbDev())).f19a;
    }

    public AKPU getAKPU(AlCameraDevice alCameraDevice) throws CameraException {
        return a(a(alCameraDevice.getUsbDev())).a();
    }

    public AKXU getAKXU(AlCameraDevice alCameraDevice) throws CameraException {
        return a(a(alCameraDevice.getUsbDev())).b();
    }

    public HashMap<String, AlCameraDevice> getDeviceList() {
        for (UsbDevice usbDevice : this.f11a.getDeviceList().values()) {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(usbDevice.getVendorId()));
            sb.append(" ");
            sb.append(Integer.toHexString(usbDevice.getProductId()));
            if (this.e.a(usbDevice)) {
                StringBuilder sb2 = new StringBuilder("Found Device  ");
                sb2.append(usbDevice.getProductId());
                sb2.append(" ");
                sb2.append(usbDevice.getDeviceName());
                this.f.put(a(usbDevice), new AlCameraDevice(usbDevice));
            }
        }
        return this.f;
    }

    public void openAlCameraDevice(AlCameraDevice alCameraDevice, Context context) throws CameraException {
        if (this.f11a.hasPermission(alCameraDevice.getUsbDev())) {
            String a2 = a(alCameraDevice.getUsbDev());
            this.g.containsKey(a2);
            this.g.put(a2, new f(this, alCameraDevice, context));
            return;
        }
        new CameraException("Dev Not Permitted").a(new AlErrorCode(AlErrorCode.ERR_PERMISSION_DENIED));
        throw new CameraException("Dev Not Permitted");
    }

    public void requestPermission(AlCameraDevice alCameraDevice) {
        AlCameraDevice alCameraDevice2 = this.f.get(a(alCameraDevice.getUsbDev()));
        if (alCameraDevice2 != null) {
            new StringBuilder("dev from map ").append(alCameraDevice2.getDeviceName());
            new StringBuilder("devincome ").append(alCameraDevice.getDeviceName());
            if (alCameraDevice2.getDeviceName().compareTo(alCameraDevice.getDeviceName()) == 0) {
                this.b = alCameraDevice.getUsbDev();
            }
        }
        UsbDevice usbDevice = this.b;
        if (usbDevice != null) {
            this.f11a.requestPermission(usbDevice, this.c);
        }
    }
}
