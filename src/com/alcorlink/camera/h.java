package com.alcorlink.camera;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes5.dex */
final class h implements Parcelable.Creator<StreamConfig> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ StreamConfig createFromParcel(Parcel parcel) {
        StreamConfig streamConfig = new StreamConfig();
        streamConfig.streamId = parcel.readByte();
        streamConfig.width = parcel.readInt();
        streamConfig.height = parcel.readInt();
        streamConfig.format = parcel.readInt();
        streamConfig.fps = parcel.readInt();
        streamConfig.sFormat = parcel.readString();
        return streamConfig;
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ StreamConfig[] newArray(int i) {
        return new StreamConfig[i];
    }
}
