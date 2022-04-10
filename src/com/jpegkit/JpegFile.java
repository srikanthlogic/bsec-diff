package com.jpegkit;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/* loaded from: classes3.dex */
public class JpegFile extends Jpeg {
    public static final Parcelable.Creator<JpegFile> CREATOR = new Parcelable.Creator<JpegFile>() { // from class: com.jpegkit.JpegFile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JpegFile createFromParcel(Parcel in) {
            try {
                return new JpegFile(new File(in.readString()));
            } catch (IOException e) {
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JpegFile[] newArray(int size) {
            return new JpegFile[size];
        }
    };
    private File mJpegFile;

    public JpegFile(File jpegFile) throws IOException {
        super(dumpFile(jpegFile));
        this.mJpegFile = jpegFile;
    }

    public File getFile() {
        return this.mJpegFile;
    }

    public void save() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(this.mJpegFile);
        outputStream.write(getJpegBytes());
        outputStream.close();
    }

    public void reload() throws Exception {
        byte[] jpegBytes = dumpFile(this.mJpegFile);
        release();
        mount(jpegBytes);
    }

    private static byte[] dumpFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[inputStream.available()];
        inputStream.read(fileBytes);
        inputStream.close();
        return fileBytes;
    }

    @Override // com.jpegkit.Jpeg, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.jpegkit.Jpeg, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        try {
            save();
            dest.writeString(getFile().getAbsolutePath());
        } catch (IOException e) {
        }
    }
}
