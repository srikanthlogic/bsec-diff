package com.example.aadhaarfpoffline.tatvik;

import android.os.Handler;
import android.os.Looper;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.google.gson.JsonElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
/* loaded from: classes2.dex */
public class FileUploader {
    public FileUploaderCallback fileUploaderCallback;
    private File[] files;
    private GetDataService getDataService;
    private String[] responses;
    private UploadInterface uploadInterface;
    public int uploadIndex = -1;
    private String uploadURL = "";
    private long totalFileLength = 0;
    private long totalFileUploaded = 0;
    private String filekey = "";
    private String auth_token = "";

    /* loaded from: classes2.dex */
    public interface FileUploaderCallback {
        void onError();

        void onFinish(String[] strArr);

        void onProgressUpdate(int i, int i2, int i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface UploadInterface {
        @POST
        @Multipart
        Call<JsonElement> uploadFile(@Url String str, @Part MultipartBody.Part part);

        @POST
        @Multipart
        Call<JsonElement> uploadFile(@Url String str, @Part MultipartBody.Part part, @Header("Authorization") String str2);
    }

    /* loaded from: classes2.dex */
    public class PRRequestBody extends RequestBody {
        private static final int DEFAULT_BUFFER_SIZE = 2048;
        private File mFile;

        public PRRequestBody(File file) {
            this.mFile = file;
        }

        @Override // okhttp3.RequestBody
        public MediaType contentType() {
            return MediaType.parse("image/*");
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
    }

    public FileUploader() {
        GetDataService getDataService = (GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    }

    public void uploadFiles(String url, String filekey, File[] files, FileUploaderCallback fileUploaderCallback) {
        uploadFiles(url, filekey, files, fileUploaderCallback, "");
    }

    public void uploadFiles(String url, String filekey, File[] files, FileUploaderCallback fileUploaderCallback, String auth_token) {
        this.fileUploaderCallback = fileUploaderCallback;
        this.files = files;
        this.uploadIndex = -1;
        this.uploadURL = url;
        this.filekey = filekey;
        this.auth_token = auth_token;
        this.totalFileUploaded = 0;
        this.totalFileLength = 0;
        this.uploadIndex = -1;
        this.responses = new String[files.length];
        for (File file : files) {
            this.totalFileLength += file.length();
        }
        uploadNext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadNext() {
        File[] fileArr = this.files;
        if (fileArr.length > 0) {
            int i = this.uploadIndex;
            if (i != -1) {
                this.totalFileUploaded += fileArr[i].length();
            }
            this.uploadIndex++;
            int i2 = this.uploadIndex;
            if (i2 < this.files.length) {
                uploadSingleFile(i2);
            } else {
                this.fileUploaderCallback.onFinish(this.responses);
            }
        } else {
            this.fileUploaderCallback.onFinish(this.responses);
        }
    }

    private void uploadSingleFile(final int index) {
        Call<JsonElement> call;
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(this.filekey, this.files[index].getName(), new PRRequestBody(this.files[index]));
        if (this.auth_token.isEmpty()) {
            call = this.uploadInterface.uploadFile(this.uploadURL, filePart);
        } else {
            call = this.uploadInterface.uploadFile(this.uploadURL, filePart, this.auth_token);
        }
        call.enqueue(new Callback<JsonElement>() { // from class: com.example.aadhaarfpoffline.tatvik.FileUploader.1
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonElement> call2, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    FileUploader.this.responses[index] = response.body().toString();
                } else {
                    FileUploader.this.responses[index] = "";
                }
                FileUploader.this.uploadNext();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonElement> call2, Throwable t) {
                FileUploader.this.fileUploaderCallback.onError();
            }
        });
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
            FileUploader.this.fileUploaderCallback.onProgressUpdate((int) ((this.mUploaded * 100) / this.mTotal), (int) (((FileUploader.this.totalFileUploaded + this.mUploaded) * 100) / FileUploader.this.totalFileLength), FileUploader.this.uploadIndex + 1);
        }
    }
}
