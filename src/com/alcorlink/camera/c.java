package com.alcorlink.camera;

import com.mantra.mfs100.mfs100api;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;
/* loaded from: classes5.dex */
public class c {

    /* renamed from: a  reason: collision with root package name */
    private int f17a = 0;

    public c(AlCamHAL alCamHAL) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int a(int i) {
        if (i == -900) {
            return AlErrorCode.ERR_NULL_POINTER;
        }
        if (i == -701) {
            return AlErrorCode.ERR_NOT_SUPPORTED;
        }
        if (i == -401) {
            return AlErrorCode.ERR_NATIVE_LAYER;
        }
        if (i == -99) {
            return 255;
        }
        if (i == -30) {
            return AlErrorCode.ERR_TRANSFER;
        }
        if (i == -19) {
            return AlErrorCode.ERR_NO_DEVICE;
        }
        switch (i) {
            case -806:
            case -805:
            case -804:
            case -803:
            case -802:
            case -801:
                return AlErrorCode.ERR_VERIFY;
            default:
                switch (i) {
                    case -504:
                        return AlErrorCode.ERR_NOT_FOUND;
                    case -503:
                        return AlErrorCode.ERR_NO_MEM;
                    case -502:
                    case -501:
                    case -500:
                        return AlErrorCode.ERR_NOT_SUPPORTED;
                    default:
                        switch (i) {
                            case -53:
                                return AlErrorCode.ERR_BAD_FRAME;
                            case -52:
                                return AlErrorCode.ERR_IN_USE;
                            case -51:
                                return AlErrorCode.ERR_INVALID_PARAM;
                            case -50:
                                return AlErrorCode.ERR_INVALID_DEVICE;
                            default:
                                switch (i) {
                                    case mfs100api.MFS100_E_USB_ERROR_NOT_SUPPORTED:
                                        return AlErrorCode.ERR_NOT_SUPPORTED;
                                    case mfs100api.MFS100_E_USB_ERROR_NO_MEM:
                                        return AlErrorCode.ERR_NO_MEM;
                                    case mfs100api.MFS100_E_USB_ERROR_INTERRUPTED:
                                    case mfs100api.MFS100_E_USB_ERROR_PIPE:
                                        return AlErrorCode.ERR_TRANSFER;
                                    case mfs100api.MFS100_E_USB_ERROR_OVERFLOW:
                                        return 165;
                                    case mfs100api.MFS100_E_USB_ERROR_TIMEOUT:
                                        return 164;
                                    case mfs100api.MFS100_E_USB_ERROR_BUSY:
                                        return 163;
                                    case mfs100api.MFS100_E_USB_ERROR_NOT_FOUND:
                                        return AlErrorCode.ERR_NOT_FOUND;
                                    case -4:
                                        return AlErrorCode.ERR_NO_DEVICE;
                                    case -3:
                                        return 162;
                                    case -2:
                                        return AlErrorCode.ERR_INVALID_PARAM;
                                    case -1:
                                        return AlErrorCode.ERR_TRANSFER;
                                    case 0:
                                        return 0;
                                    default:
                                        return 255;
                                }
                        }
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void a(String str) {
        Throwable th;
        FileOutputStream fileOutputStream;
        String str2 = str + IOUtils.LINE_SEPARATOR_UNIX;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(new File("/sdcard/DCIM/Camera/", "alcamera.txt"), true);
            try {
                fileOutputStream.write(str2.getBytes());
                fileOutputStream.flush();
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                fileOutputStream2 = fileOutputStream;
                try {
                    fileOutputStream2.close();
                } catch (Exception e3) {
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    fileOutputStream.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (Exception e5) {
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }
}
