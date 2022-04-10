package com.jpegkit;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/* loaded from: classes3.dex */
public final class JpegKit {
    private static void writeFile(Jpeg jpeg, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(jpeg.getJpegBytes());
        outputStream.close();
    }

    public static JpegFile writeToInternalFilesDirectory(Context context, String filePath, String fileName, Jpeg jpeg) throws IOException {
        File directory = context.getFilesDir();
        if (filePath != null && filePath.length() > 0) {
            directory = new File(directory, filePath);
            if (!directory.exists() && !directory.mkdirs()) {
                directory = context.getFilesDir();
            }
        }
        File targetFile = new File(directory, fileName);
        writeFile(jpeg, targetFile);
        return new JpegFile(targetFile);
    }

    public static JpegFile writeToInternalCacheDirectory(Context context, String filePath, String fileName, Jpeg jpeg) throws IOException {
        File directory = context.getCacheDir();
        if (filePath != null && filePath.length() > 0) {
            directory = new File(directory, filePath);
            if (!directory.exists() && !directory.mkdirs()) {
                directory = context.getCacheDir();
            }
        }
        File targetFile = new File(directory, fileName);
        writeFile(jpeg, targetFile);
        return new JpegFile(targetFile);
    }

    public static JpegFile writeToExternalPrivateDirectory(Context context, String filePath, String fileName, Jpeg jpeg) throws IOException {
        if (!isExternalStorageWritable()) {
            return null;
        }
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filePath != null && filePath.length() > 0) {
            directory = new File(directory, filePath);
            if (!directory.mkdirs()) {
                return null;
            }
        }
        File targetFile = new File(directory, fileName);
        writeFile(jpeg, targetFile);
        return new JpegFile(targetFile);
    }

    public static JpegFile writeToExternalPublicDirectory(Context context, String filePath, String fileName, Jpeg jpeg) throws IOException {
        if (!isExternalStorageWritable()) {
            return null;
        }
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (filePath != null && filePath.length() > 0) {
            directory = new File(directory, filePath);
            if (!directory.mkdirs()) {
                return null;
            }
        }
        File targetFile = new File(directory, fileName);
        writeFile(jpeg, targetFile);
        return new JpegFile(targetFile);
    }

    public static boolean isExternalStorageWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }
}
