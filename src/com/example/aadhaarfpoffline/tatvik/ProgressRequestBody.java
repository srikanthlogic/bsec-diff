package com.example.aadhaarfpoffline.tatvik;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
/* loaded from: classes2.dex */
public class ProgressRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private String content_type;
    private File mFile;
    private UploadCallbacks mListener;
    private String mPath;

    /* loaded from: classes2.dex */
    public interface UploadCallbacks {
        void onError();

        void onFinish();

        void onProgressUpdate(int i);
    }

    public ProgressRequestBody(File file, String content_type, UploadCallbacks listener) {
        this.content_type = content_type;
        this.mFile = file;
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
        long fileLength = this.mFile.length();
        byte[] buffer = new byte[2048];
        FileInputStream in = new FileInputStream(this.mFile);
        try {
            Handler handler = new Handler(Looper.getMainLooper());
            long uploaded = 0;
            while (true) {
                try {
                    int read = in.read(buffer);
                    if (read != -1) {
                        handler.post(new ProgressUpdater(uploaded, fileLength));
                        uploaded += (long) read;
                        try {
                            sink.write(buffer, 0, read);
                        } catch (Throwable th2) {
                            th = th2;
                            in.close();
                            throw th;
                        }
                    } else {
                        in.close();
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
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
            ProgressRequestBody.this.mListener.onProgressUpdate((int) ((this.mUploaded * 100) / this.mTotal));
        }
    }
}
