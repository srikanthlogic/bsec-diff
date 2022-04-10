package com.nitgen.SDK.AndroidBSP;

import android.graphics.Bitmap;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import org.apache.commons.io.IOUtils;
/* loaded from: classes4.dex */
class Utils {
    Utils() {
    }

    protected static Bitmap getBitmap(byte[] rawBuf, int width, int height) {
        Bitmap capturedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ByteBuffer src = ByteBuffer.allocate(width * height * 4);
        byte[] srcbuf = src.array();
        for (int i = 0; i < rawBuf.length; i++) {
            srcbuf[i * 4] = rawBuf[i];
            srcbuf[(i * 4) + 1] = rawBuf[i];
            srcbuf[(i * 4) + 2] = rawBuf[i];
            srcbuf[(i * 4) + 3] = -1;
        }
        src.position(0);
        capturedImage.copyPixelsFromBuffer(src);
        return capturedImage;
    }

    protected static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    protected static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(i * 2, (i * 2) + 2), 16);
        }
        return ba;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void printByteArray(String title, String msg, byte[] pBuf) {
        StringBuilder stringBuilder = new StringBuilder(pBuf.length);
        for (int i = 0; i < pBuf.length; i++) {
            if (i % 10 == 0) {
                stringBuilder.append(IOUtils.LINE_SEPARATOR_UNIX + String.format("%02X ", Byte.valueOf(pBuf[i])));
            } else {
                stringBuilder.append(String.format("%02X ", Byte.valueOf(pBuf[i])));
            }
        }
        Log.d(title, msg + stringBuilder.toString());
    }
}
