package com.alcorlink.camera;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes5.dex */
public class StreamConfig implements Parcelable {
    public static final Parcelable.Creator<StreamConfig> CREATOR = new h();
    public static final int VS_FORMAT_FRAME_BASED;
    public static final int VS_FORMAT_MJPEG;
    public static final int VS_FORMAT_RESERVED;
    public static final int VS_FORMAT_UNCOMPRESSED;
    public int format;
    private int fps;
    public int height;
    private String sFormat;
    public byte streamId;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getFormatString() {
        String str;
        String str2 = this.sFormat;
        if (str2 != null) {
            return str2;
        }
        int i = this.format;
        if (i == 4) {
            str = "YUV";
        } else if (i != 6) {
            if (i == 16) {
                str = "H264";
            }
            return this.sFormat;
        } else {
            str = "MJPEG";
        }
        this.sFormat = str;
        return this.sFormat;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.streamId);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.format);
        parcel.writeInt(this.fps);
        parcel.writeString(this.sFormat);
    }
}
