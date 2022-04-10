package com.alcorlink.camera;

import android.hardware.usb.UsbDevice;
import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes5.dex */
final class d implements Parcelable.Creator<AlCameraDevice> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AlCameraDevice createFromParcel(Parcel parcel) {
        return new AlCameraDevice((UsbDevice) parcel.readParcelable(UsbDevice.class.getClassLoader()));
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ AlCameraDevice[] newArray(int i) {
        return new AlCameraDevice[i];
    }
}
