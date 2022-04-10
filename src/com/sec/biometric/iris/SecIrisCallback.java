package com.sec.biometric.iris;
/* loaded from: classes3.dex */
public interface SecIrisCallback {
    void onCaptureFailed(int i);

    void onCaptureSuccess();

    void onEyeInfoUiHints(String str, String str2);

    void onTimeOut();

    void showPreviewFrame(byte[] bArr, int i, int i2, int i3);
}
