package com.example.aadhaarfpoffline.tatvik;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
/* loaded from: classes2.dex */
public class ProgressMultipleRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private String content_type;
    private File mFile;
    private List<File> mFiles;
    private UploadCallbacks mListener;
    private String mPath;

    /* loaded from: classes2.dex */
    public interface UploadCallbacks {
        void onError();

        void onFinish();

        void onProgressUpdate(int i);
    }

    public ProgressMultipleRequestBody(List<File> file, String content_type, UploadCallbacks listener) {
        this.mFiles = new ArrayList();
        this.content_type = content_type;
        this.mFiles = file;
        this.mListener = listener;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return MediaType.parse(this.content_type + "/*");
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        return this.mFile.length();
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink sink) throws IOException {
        Throwable th;
        long[] fileLength = new long[this.mFiles.size()];
        FileInputStream[] in = new FileInputStream[this.mFiles.size()];
        for (int i = 0; i < this.mFiles.size(); i++) {
            fileLength[i] = this.mFiles.get(i).length();
        }
        byte[] buffer = new byte[2048];
        for (int i2 = 0; i2 < this.mFiles.size(); i2++) {
            in[i2] = new FileInputStream(this.mFiles.get(i2));
        }
        for (int i3 = 0; i3 < this.mFiles.size(); i3++) {
            try {
                Handler handler = new Handler(Looper.getMainLooper());
                long uploaded = 0;
                while (true) {
                    try {
                        int read = in[i3].read(buffer);
                        if (read != -1) {
                            handler.post(new ProgressUpdater(uploaded, fileLength[i3]));
                            uploaded += (long) read;
                            try {
                                sink.write(buffer, 0, read);
                            } catch (Throwable th2) {
                                th = th2;
                                in[i3].close();
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                in[i3].close();
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    /* loaded from: classes2.dex */
    private class ProgressUpdater implements Runnable {
        private long mTotal;
        private long mUploaded;

        public ProgressUpdater(long uploaded, long total) {
            this.mUploaded = uploaded;
            this.mTotal = total;
        }

        @Override // java.lang.Runnable
        public void run() {
            ProgressMultipleRequestBody.this.mListener.onProgressUpdate((int) ((this.mUploaded * 100) / this.mTotal));
        }
    }
}
