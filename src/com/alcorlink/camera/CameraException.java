package com.alcorlink.camera;
/* loaded from: classes5.dex */
public class CameraException extends Exception {

    /* renamed from: a  reason: collision with root package name */
    private AlErrorCode f14a;

    public CameraException() {
    }

    public CameraException(String str) {
        super(str);
    }

    public CameraException(String str, Throwable th) {
        super(str, th);
    }

    public CameraException(Throwable th) {
        super(th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void a(AlErrorCode alErrorCode) {
        this.f14a = alErrorCode;
    }

    public int getErrorCode() {
        AlErrorCode alErrorCode = this.f14a;
        if (alErrorCode == null) {
            return 0;
        }
        return alErrorCode.getErrCode();
    }
}
