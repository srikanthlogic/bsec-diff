package com.nitgen.SDK.AndroidBSP;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.os.Handler;
import android.os.Message;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class USBCommandThread extends Thread implements StaticVals {
    static final int BULK_FAIL = 0;
    static final int BULK_SUCCESS = 1;
    static final int CTL_FAIL = 0;
    static final int CTL_SUCCESS = 1;
    static final int NITGEN_VGA_FRAME_SIZE = 400384;
    static final int NITGEN_VGA_FRAME_SIZE_08 = 307200;
    static final int NITGEN_VGA_FRAME_SIZE_HEGITH = 782;
    static final int NITGEN_VGA_FRAME_SIZE_HEGITH_08 = 600;
    static final int NITGEN_VGA_FRAME_SIZE_WIDTH = 512;
    static final int PRODUCT_ID_NITGEN_06 = 1538;
    static final int PRODUCT_ID_NITGEN_06_2 = 256;
    static final int PRODUCT_ID_NITGEN_08 = 1616;
    private static final String TAG = USBCommandThread.class.getSimpleName();
    static final int TRY_CONNECT_TIME = 10000;
    static final int VENDOR_ID_NITGEN = 2694;
    static final int bRequest_SENSOR_LED = 144;
    static final int bRequest_START_CONT_IMAGE_DATA = 241;
    ControlCommandVo commandVo;
    UsbEndpoint endpointIn;
    Handler handler;
    UsbDeviceConnection usbDeviceConnection;

    /* JADX INFO: Access modifiers changed from: protected */
    public USBCommandThread(UsbDeviceConnection mUsbDeviceConnection, Handler mHandler, ControlCommandVo mCommandVo) {
        this.usbDeviceConnection = mUsbDeviceConnection;
        this.handler = mHandler;
        this.commandVo = mCommandVo;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public USBCommandThread(UsbDeviceConnection mUsbDeviceConnection, Handler mHandler, ControlCommandVo mCommandVo, UsbEndpoint mEndpointIn) {
        this.usbDeviceConnection = mUsbDeviceConnection;
        this.handler = mHandler;
        this.commandVo = mCommandVo;
        this.endpointIn = mEndpointIn;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        byte[] buffer;
        try {
            Message msg = Message.obtain();
            msg.what = this.commandVo.getRequest();
            if (this.usbDeviceConnection.controlTransfer(this.commandVo.getRequestType(), this.commandVo.getRequest(), this.commandVo.getValue(), this.commandVo.getIndex(), this.commandVo.getBuffer(), this.commandVo.getLength(), TRY_CONNECT_TIME) >= 0) {
                if (this.commandVo.getRequest() == bRequest_START_CONT_IMAGE_DATA) {
                    if (!(NBioBSPJNI.CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06 || NBioBSPJNI.CURRENT_PRODUCT_ID == 256)) {
                        buffer = new byte[NITGEN_VGA_FRAME_SIZE_08];
                        int pos = 0;
                        int i = 0;
                        while (true) {
                            if (i >= 20) {
                                break;
                            }
                            byte[] tempBuffer = new byte[15360];
                            if (this.usbDeviceConnection.bulkTransfer(this.endpointIn, tempBuffer, tempBuffer.length, TRY_CONNECT_TIME) < 0) {
                                msg.arg2 = 0;
                                break;
                            }
                            System.arraycopy(tempBuffer, 0, buffer, pos, 15360);
                            pos += 15360;
                            msg.arg2 = 1;
                            i++;
                        }
                        msg.obj = buffer;
                    }
                    buffer = new byte[NITGEN_VGA_FRAME_SIZE];
                    int pos2 = 0;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= NITGEN_VGA_FRAME_SIZE_HEGITH) {
                            break;
                        }
                        byte[] tempBuffer2 = new byte[512];
                        if (this.usbDeviceConnection.bulkTransfer(this.endpointIn, tempBuffer2, tempBuffer2.length, TRY_CONNECT_TIME) < 0) {
                            msg.arg2 = 0;
                            break;
                        }
                        System.arraycopy(tempBuffer2, 0, buffer, pos2, 512);
                        pos2 += 512;
                        msg.arg2 = 1;
                        i2++;
                    }
                    msg.obj = buffer;
                } else {
                    if (this.commandVo.getRequest() == 144) {
                        Thread.sleep(50);
                    }
                    msg.obj = this.commandVo.getBuffer();
                }
                msg.arg1 = 1;
            } else {
                msg.arg1 = 0;
            }
            this.handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            this.handler.sendEmptyMessage(0);
        }
    }
}
