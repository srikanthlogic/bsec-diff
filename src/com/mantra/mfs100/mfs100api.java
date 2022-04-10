package com.mantra.mfs100;
/* loaded from: classes3.dex */
public final class mfs100api {
    public static String ErrorString = null;
    public static final int MFS100_CANNOT_READ_PRIVATE_KEY = -1327;
    public static final int MFS100_E_AES_GCM_ENCRYPTION_FAILED = -1332;
    public static final int MFS100_E_B64_ENCODE_FAILED = -1330;
    public static final int MFS100_E_BAD_FORMAT = -1132;
    public static final int MFS100_E_BAD_LICENSE = -1129;
    public static final int MFS100_E_BAD_QUALITY = -1318;
    public static final int MFS100_E_BAD_TEMPLATE = -1135;
    public static final int MFS100_E_BAD_VALUE = -1133;
    public static final int MFS100_E_BLANKIMAGE = -1114;
    public static final int MFS100_E_CAPTURING_IN_PROCESS = -1323;
    public static final int MFS100_E_CAPTURING_STOPPED = -1319;
    public static final int MFS100_E_CORRUPT_SERIAL = -1314;
    public static final int MFS100_E_EEPROM_DATA_FAILED = -1316;
    public static final int MFS100_E_EEPROM_DECRYPT_FAILED = -1313;
    public static final int MFS100_E_EEPROM_READ_FAILED = -1315;
    public static final int MFS100_E_EXTRACTOR_INIT_FAILED = -1116;
    public static final int MFS100_E_FILE_IO = -1117;
    public static final int MFS100_E_IMPROPER_OR_NO_FINGER_PLACEMENT = -1324;
    public static final int MFS100_E_INVALIDPARAM = -1101;
    public static final int MFS100_E_INVALID_KEY = -1322;
    public static final int MFS100_E_INVALID_TYPE = -1337;
    public static final int MFS100_E_LATENT_FINGER = -1320;
    public static final int MFS100_E_LOAD_FIRMWARE_FAILED = -1317;
    public static final int MFS100_E_MEMORY = -1120;
    static final int MFS100_E_NOPERMISSION = -1001;
    public static final int MFS100_E_NOT_DEFINED = -1137;
    public static final int MFS100_E_NOT_GOOD_IMAGE = -1115;
    public static final int MFS100_E_NOT_IMPLEMENTED = -1134;
    public static final int MFS100_E_NOT_INITIALIZED = -1309;
    public static final int MFS100_E_NO_DEVICE = -1307;
    public static final int MFS100_E_NULLPARAM = -1121;
    public static final int MFS100_E_NULL_TEMPLATE = -1138;
    public static final int MFS100_E_OTHER = -1122;
    public static final int MFS100_E_PRIVATE_KEY_SIGN_FAILED = -1329;
    public static final int MFS100_E_READ_ONLY = -1136;
    public static final int MFS100_E_RSA_ENCRYPTION_FAILED = -1333;
    public static final int MFS100_E_SHA256_FAILED = -1328;
    public static final int MFS100_E_SUCCESS = 0;
    public static final int MFS100_E_SYNC_PROBLEM = -1139;
    public static final int MFS100_E_TIMEOUT = -1140;
    static final int MFS100_E_UNHANDLEDEXCEPTION = -1000;
    public static final int MFS100_E_UNKNOWN_SENSOR = -1142;
    public static final int MFS100_E_USB_CLAIM_INTERFACE_FAILED = -14;
    public static final int MFS100_E_USB_ERROR_ACCESS = -3;
    public static final int MFS100_E_USB_ERROR_BUSY = -6;
    public static final int MFS100_E_USB_ERROR_INTERRUPTED = -10;
    public static final int MFS100_E_USB_ERROR_INVALID_PARAM = -2;
    public static final int MFS100_E_USB_ERROR_IO = -1;
    public static final int MFS100_E_USB_ERROR_NOT_FOUND = -5;
    public static final int MFS100_E_USB_ERROR_NOT_SUPPORTED = -12;
    public static final int MFS100_E_USB_ERROR_NO_DEVICE = -4;
    public static final int MFS100_E_USB_ERROR_NO_MEM = -11;
    public static final int MFS100_E_USB_ERROR_OTHER = -99;
    public static final int MFS100_E_USB_ERROR_OVERFLOW = -8;
    public static final int MFS100_E_USB_ERROR_PIPE = -9;
    public static final int MFS100_E_USB_ERROR_TIMEOUT = -7;
    public static final int MFS100_E_USB_LOAD_RAM_FAILED = -15;
    public static final int MFS100_E_USB_OPEN_FAILED = -13;
    public static final int MFS100_PID_XML_FAILED = -1331;
    static MFS100Event mfs100Event = null;

    /* loaded from: classes3.dex */
    public interface MFS100Event {
        void OnMFS100Preview(byte[] bArr);

        void onMFS100AutoCaptureFeedback(int i, int i2, int i3);
    }

    public static final native int MFS100AutoCapture(long j, int i, byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr, int[] iArr2, int[] iArr3, int i2);

    public static native int MFS100ChangeKey(byte[] bArr, int i);

    public static native int MFS100ConvertRawToBmp(long j, byte[] bArr, byte[] bArr2, int i, int i2);

    public static native int MFS100DeviceConnected(int i);

    public static native int MFS100ExtractANSITemplate(long j, byte[] bArr, byte[] bArr2);

    public static native int MFS100ExtractISOImage(long j, byte[] bArr, byte[] bArr2, byte b, int i);

    public static native int MFS100ExtractISOTemplate(long j, byte[] bArr, byte[] bArr2);

    public static native int MFS100ExtractWSQImage(long j, byte[] bArr, byte[] bArr2, float f);

    public static native int MFS100GetContrast(long j);

    public static native int MFS100GetFinalFrame(long j, byte[] bArr);

    public static native int MFS100GetHeight(long j);

    public static native String MFS100GetMake(int i);

    public static native String MFS100GetModel(int i);

    public static native int MFS100GetNFIQ(long j);

    public static native int MFS100GetPreviewFrame(long j, byte[] bArr);

    public static native int MFS100GetQuality(long j);

    public static native int MFS100GetSDKVersion();

    public static native int MFS100GetWidth(long j);

    public static native long MFS100Init(byte[] bArr, int i, int i2, int i3);

    public static native long MFS100InitWithKey(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

    public static native int MFS100IsLive(long j, byte[] bArr, int i);

    public static native int MFS100LastErrorCode();

    public static final native long MFS100LoadFirmware(int i);

    public static native int MFS100MatchANSI(long j, byte[] bArr, byte[] bArr2, int i);

    public static native int MFS100MatchISO(long j, byte[] bArr, byte[] bArr2, int i);

    public static native int MFS100ReadBit();

    public static native int MFS100RotateImage(long j, int i);

    public static native void MFS100StopAutoCapture();

    public static native int MFS100Uninit(long j);

    public static native int MFS100WriteBit(int i);

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("MFS100V9032");
    }

    public static void MFS100AutoCaptureFeedback(int ErrorCode, int Quality, int NoofMinetues) {
        MFS100Event mFS100Event = mfs100Event;
        if (mFS100Event != null) {
            mFS100Event.onMFS100AutoCaptureFeedback(ErrorCode, Quality, NoofMinetues);
        }
    }

    public static final void SetContext(MFS100Event event) {
        mfs100Event = event;
    }

    public static final void MFS100AutoCaptureCallBack(byte[] Frame) {
        MFS100Event mFS100Event = mfs100Event;
        if (mFS100Event != null) {
            mFS100Event.OnMFS100Preview(Frame);
        }
    }

    public static int CheckError(int returnValue) {
        ErrorString = "None";
        if (returnValue == -1337) {
            ErrorString = "Invalid ISO image type";
            return -1;
        } else if (returnValue == -1314) {
            ErrorString = "Serial no currupted";
            return -1;
        } else if (returnValue == -1309) {
            ErrorString = "Device Not Initialized";
            return -1;
        } else if (returnValue == -1307) {
            ErrorString = "No Device Connected";
            return -1;
        } else if (returnValue == -1142) {
            ErrorString = "Unknown Sensor";
            return -1;
        } else if (returnValue == -1129) {
            ErrorString = "Provided license is not valid, or no license was found";
            return -1;
        } else if (returnValue == -1101) {
            ErrorString = "Invalid Parameters";
            return -1;
        } else if (returnValue != -99) {
            if (returnValue != MFS100_E_NOPERMISSION) {
                if (returnValue != -1000) {
                    switch (returnValue) {
                        case MFS100_E_IMPROPER_OR_NO_FINGER_PLACEMENT /* -1324 */:
                            ErrorString = "Improper Finger Placement";
                            return -1;
                        case MFS100_E_CAPTURING_IN_PROCESS /* -1323 */:
                            ErrorString = "Capturing in Process";
                            return -1;
                        case MFS100_E_INVALID_KEY /* -1322 */:
                            ErrorString = "Invalid Key";
                            return -1;
                        default:
                            switch (returnValue) {
                                case MFS100_E_LATENT_FINGER /* -1320 */:
                                    ErrorString = "latent finger on device";
                                    return -1;
                                case MFS100_E_CAPTURING_STOPPED /* -1319 */:
                                    ErrorString = "Capturing stopped";
                                    return -1;
                                case MFS100_E_BAD_QUALITY /* -1318 */:
                                    ErrorString = "Bad Quality Finger";
                                    return -1;
                                case MFS100_E_LOAD_FIRMWARE_FAILED /* -1317 */:
                                    ErrorString = "Firmware failed to load";
                                    return -1;
                                default:
                                    switch (returnValue) {
                                        case MFS100_E_TIMEOUT /* -1140 */:
                                            ErrorString = "Timeout";
                                            return -1;
                                        case MFS100_E_SYNC_PROBLEM /* -1139 */:
                                            ErrorString = "Sync Problem";
                                            return -1;
                                        case MFS100_E_NULL_TEMPLATE /* -1138 */:
                                            ErrorString = "Template is NULL (contains no finger)";
                                            return -1;
                                        case MFS100_E_NOT_DEFINED /* -1137 */:
                                            ErrorString = "Value is not defined";
                                            return -1;
                                        case MFS100_E_READ_ONLY /* -1136 */:
                                            ErrorString = "Value cannot be modified";
                                            return -1;
                                        case MFS100_E_BAD_TEMPLATE /* -1135 */:
                                            ErrorString = "Invalide template or unsupported template format";
                                            return -1;
                                        case MFS100_E_NOT_IMPLEMENTED /* -1134 */:
                                            ErrorString = "Function not implemented";
                                            return -1;
                                        case MFS100_E_BAD_VALUE /* -1133 */:
                                            ErrorString = "Invalid Value Provided";
                                            return -1;
                                        case MFS100_E_BAD_FORMAT /* -1132 */:
                                            ErrorString = "Unsupported Format";
                                            return -1;
                                        default:
                                            switch (returnValue) {
                                                case MFS100_E_OTHER /* -1122 */:
                                                    ErrorString = "Other Error";
                                                    return -1;
                                                case MFS100_E_NULLPARAM /* -1121 */:
                                                    ErrorString = "Null Parameters";
                                                    return -1;
                                                case MFS100_E_MEMORY /* -1120 */:
                                                    ErrorString = "Memory allocation failed";
                                                    return -1;
                                                default:
                                                    switch (returnValue) {
                                                        case MFS100_E_FILE_IO /* -1117 */:
                                                            ErrorString = "Error occured while opening/reading file";
                                                            return -1;
                                                        case MFS100_E_EXTRACTOR_INIT_FAILED /* -1116 */:
                                                            ErrorString = "Extractor Library cannot Initialize";
                                                            return -1;
                                                        case MFS100_E_NOT_GOOD_IMAGE /* -1115 */:
                                                            ErrorString = "Input image is not good";
                                                            return -1;
                                                        case MFS100_E_BLANKIMAGE /* -1114 */:
                                                            ErrorString = "Image is blank or contains non-recognizable fingerprint";
                                                            return -1;
                                                        default:
                                                            switch (returnValue) {
                                                                case MFS100_E_USB_LOAD_RAM_FAILED /* -15 */:
                                                                    ErrorString = "MFS100_E_USB_LOAD_RAM_FAILED";
                                                                    return -1;
                                                                case MFS100_E_USB_CLAIM_INTERFACE_FAILED /* -14 */:
                                                                    ErrorString = "MFS100_E_USB_CLAIM_INTERFACE_FAILED";
                                                                    return -1;
                                                                case MFS100_E_USB_OPEN_FAILED /* -13 */:
                                                                    ErrorString = "MFS100_E_USB_OPEN_FAILED";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_NOT_SUPPORTED /* -12 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_NOT_SUPPORTED";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_NO_MEM /* -11 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_NO_MEM";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_INTERRUPTED /* -10 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_INTERRUPTED";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_PIPE /* -9 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_PIPE";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_OVERFLOW /* -8 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_OVERFLOW";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_TIMEOUT /* -7 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_TIMEOUT";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_BUSY /* -6 */:
                                                                    ErrorString = "MFS100 used by another application";
                                                                    return -1;
                                                                case MFS100_E_USB_ERROR_NOT_FOUND /* -5 */:
                                                                    ErrorString = "MFS100_E_USB_ERROR_NOT_FOUND";
                                                                    return -1;
                                                                case -4:
                                                                    ErrorString = "MFS100_E_USB_ERROR_NO_DEVICE";
                                                                    return -1;
                                                                case -3:
                                                                    ErrorString = "MFS100_E_USB_ERROR_ACCESS";
                                                                    return -1;
                                                                case -2:
                                                                    ErrorString = "MFS100_E_USB_ERROR_INVALID_PARAM";
                                                                    return -1;
                                                                case -1:
                                                                    ErrorString = "MFS100_E_USB_ERROR_IO";
                                                                    return -1;
                                                                case 0:
                                                                    ErrorString = "";
                                                                    return 0;
                                                                default:
                                                                    ErrorString = "Unknown Error";
                                                                    return -1;
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                } else {
                    ErrorString = "Unhandled exception";
                }
            }
            ErrorString = "Permission denied";
            return -1;
        } else {
            ErrorString = "MFS100_E_USB_ERROR_OTHER";
            return -1;
        }
    }

    public static String GetErrorMsg(int returnValue) {
        CheckError(returnValue);
        return ErrorString;
    }
}
