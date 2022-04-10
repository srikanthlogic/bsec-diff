package com.nitgen.SDK.AndroidBSP;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.alcorlink.camera.AlCameraDevice;
import com.alcorlink.camera.AlDevManager;
import com.alcorlink.camera.AlErrorCode;
import com.sec.biometric.license.SecBiometricLicenseManager;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import org.zz.protocol.MXErrCode;
/* loaded from: classes4.dex */
public class NBioBSPJNI implements StaticVals {
    static final String ACTION_USB_PERMISSION;
    static final int BULK_FAIL;
    static final int BULK_SUCCESS;
    static final int CTL_FAIL;
    static final int CTL_SUCCESS;
    static final int MODIFY_IMAGE_HEIGHT_06_H;
    static final int MODIFY_IMAGE_HEIGHT_08_PIV;
    static final int MODIFY_IMAGE_WIDTH_06_H;
    static final int MODIFY_IMAGE_WIDTH_08_PIV;
    static final int NITGEN_VGA_FRAME_SIZE;
    static final int NITGEN_VGA_FRAME_SIZE_08;
    static final int NITGEN_VGA_FRAME_SIZE_HEGITH;
    static final int NITGEN_VGA_FRAME_SIZE_HEGITH_08;
    static final int NITGEN_VGA_FRAME_SIZE_WIDTH;
    private static final int OEM_CODE;
    static final int ORIGINAL_IMAGE_HEIGHT_06_H;
    static final int ORIGINAL_IMAGE_HEIGHT_08_PIV;
    static final int ORIGINAL_IMAGE_WIDTH_06_H;
    static final int ORIGINAL_IMAGE_WIDTH_08_PIV;
    static final int POS_HEADER;
    static final int POS_NONE;
    static final int POS_TAIL;
    static final int PRODUCT_ID_NITGEN_06;
    static final int PRODUCT_ID_NITGEN_06_2;
    static final int PRODUCT_ID_NITGEN_06_SP;
    static final int PRODUCT_ID_NITGEN_08;
    private static final int RELEASE_VERSION;
    static final int RESIZE_HEIGHT_06_H;
    static final int RESIZE_HEIGHT_08_PIV;
    static final int RESIZE_WIDTH_06_H;
    static final int RESIZE_WIDTH_08_PIV;
    static final int TRY_CONNECT_TIME;
    static final int VENDOR_ID_NITGEN;
    static final double ZOOM_OUT_C_1_06_H;
    static final double ZOOM_OUT_C_1_08_PIV;
    static final double ZOOM_OUT_C_2_08_PIV;
    static final double ZOOM_OUT_R_1_06_H;
    static final double ZOOM_OUT_R_1_08_PIV;
    static final double ZOOM_OUT_R_2_08_PIV;
    static final int bRequestType_IN;
    static final int bRequestType_OUT;
    static final int bRequest_GET_DEVICE_DESC;
    static final int bRequest_GET_DUMP_IMAGE;
    static final int bRequest_GET_E2PROM_DATA;
    static final int bRequest_GET_ID;
    static final int bRequest_GET_IMAGE_DATA;
    static final int bRequest_GET_LIVE_IMAGE;
    static final int bRequest_GET_SENSOR_INFORMATION;
    static final int bRequest_GET_SENSOR_OPTION;
    static final int bRequest_GET_SENSOR_SETTING;
    static final int bRequest_GET_TOUCH_STATUS;
    static final int bRequest_GET_VALUE;
    static final int bRequest_GET_VISIBLE_IMAGE;
    static final int bRequest_INIT_DEVICE;
    static final int bRequest_IR_START_CONT_IMAGE_DATA;
    static final int bRequest_SENSOR_LED;
    static final int bRequest_SET_E2PROM_DATA;
    static final int bRequest_SET_ID;
    static final int bRequest_SET_SENSOR_OPTION;
    static final int bRequest_SET_SENSOR_SETTING;
    static final int bRequest_SET_VALUE;
    static final int bRequest_START_CONT_IMAGE_DATA;
    static final int bRequest_STOP_CONT_IMAGE_DATA;
    static boolean s_useNative;
    static final int wIndex_DEFAULT_VALUE;
    static final int wIndex_PCB_LED_1;
    static final int wIndex_PCB_LED_2;
    static final int wIndex_PCB_LED_3;
    static final int wIndex_SENSOR_LED;
    static final int wIndex_SENSOR_LED_EXTINCTION;
    static final int wIndex_SENSOR_LED_INFRARED;
    static final int wIndex_SENSOR_LED_SCATTERING;
    static final int wLength_CHECK_AUTOON_STATUS;
    static final int wLength_DEFAULT_VALUE;
    static final int wLength_GET_DEVICE_DESC;
    static final int wLength_GET_ID;
    static final int wLength_GET_SENSOR_INFORMATION;
    static final int wLength_GET_SENSOR_OPTION;
    static final int wLength_GET_SENSOR_SETTING;
    static final int wLength_GET_TOUCH_STATUS;
    static final int wLength_GET_VALUE;
    static final int wLength_INIT_DEVICE;
    static final int wLength_SENSOR_LED;
    static final int wLength_SET_ID;
    static final int wLength_SET_SENSOR_OPTION;
    static final int wLength_SET_SENSOR_SETTING;
    static final int wLength_SET_VALUE;
    static final int wValue_DEFAULT_VALUE;
    static final int wValue_GET_IMAGE_DATA_LATENT;
    static final int wValue_GET_IMAGE_DATA_NORMAL;
    static final int wValue_GET_VISIBLE_IMAGE_LATENT;
    static final int wValue_GET_VISIBLE_IMAGE_NORMAL;
    static final int wValue_INIT_DEVICE_FDU14_ABSOLUTENESS;
    static final int wValue_INIT_DEVICE_FDU14_BOTH;
    static final int wValue_INIT_DEVICE_FDU14_CHECK;
    static final int wValue_INIT_DEVICE_MOUSE;
    static final int wValue_INIT_DEVICE_SENSOR;
    static final int wValue_SENSOR_LED_OFF;
    static final int wValue_SENSOR_LED_ON;
    Activity activity;
    int captureCount;
    CAPTURE_CALLBACK capture_callback;
    CAPTURED_DATA capturedDatas;
    byte[] crop;
    Handler eHandler;
    CAPTURE_QUALITY_INFO init_capture_quality_info;
    boolean isBreak;
    boolean isLedOn;
    boolean isReceive;
    boolean isSkip;
    boolean isSyncOpenDevice;
    private UsbEndpoint mEndpointIn;
    HFDU06SP mHFDU06SP;
    private Handler mHandler;
    private UsbManager mManager;
    PendingIntent mPermissionIntent;
    private UsbDeviceConnection mUsbDeviceConnection;
    private final BroadcastReceiver mUsbReceiver;
    int m_CenterX;
    int m_CenterY;
    private String m_bspVersion;
    private long m_hNBioBSP;
    private int m_nErrCode;
    int[] m_pSctable;
    int[] m_pSrtable;
    int[] m_pXtable;
    int[] m_pYtable;
    private PZoomAry06H[] m_pZoomAry06H;
    byte[] modify;
    byte[] rawData;
    int reqId;
    private static final String TAG = NBioBSPJNI.class.getSimpleName();
    public static boolean isSetSensorOption = false;
    public static boolean isGetSensorSetting = false;
    public static int CURRENT_PRODUCT_ID = 0;
    public static int CURRENT_BRIGHT_06 = 100;
    public static int CURRENT_BRIGHT_08 = 40;
    public static int BRIGHT_INTERVAL = 20;
    static boolean isConnected = false;

    /* loaded from: classes4.dex */
    public interface CAPTURE_CALLBACK {
        int OnCaptured(CAPTURED_DATA captured_data);

        void OnConnected();

        void OnDisConnected();
    }

    /* loaded from: classes4.dex */
    public static class COMPRESS_MODE {
        public static final byte NONE;
        public static final byte WSQ;
    }

    /* loaded from: classes4.dex */
    public static class DEVICE_ID {
        public static final short AUTO;
        public static final short NONE;
    }

    /* loaded from: classes4.dex */
    public static class DEVICE_NAME {
        public static final short FDP02;
        public static final short FDU01;
        public static final short FDU03;
        public static final short FDU05;
        public static final short FDU08;
        public static final short FDU11;
        public static final short FSC01;
        public static final short NND_FPC6410;
        public static final short NND_URU4KB;
        public static final short NONE;
        public static final short OSU02;
    }

    /* loaded from: classes4.dex */
    public static class ERROR {
        public static final int NBioAPIERROR_ALREADY_PROCESSED;
        private static final int NBioAPIERROR_BASE_DEVICE;
        private static final int NBioAPIERROR_BASE_GENERAL;
        private static final int NBioAPIERROR_BASE_INDEXSEARCH;
        private static final int NBioAPIERROR_BASE_UI;
        public static final int NBioAPIERROR_CAPTURE_FAKE_SUSPICIOUS;
        public static final int NBioAPIERROR_CAPTURE_TIMEOUT;
        public static final int NBioAPIERROR_DATA_PROCESS_FAIL;
        public static final int NBioAPIERROR_DEVICE_ALREADY_OPENED;
        public static final int NBioAPIERROR_DEVICE_BRIGHTNESS;
        public static final int NBioAPIERROR_DEVICE_CONTRAST;
        public static final int NBioAPIERROR_DEVICE_DLL_GET_PROC_FAIL;
        public static final int NBioAPIERROR_DEVICE_DLL_LOAD_FAIL;
        public static final int NBioAPIERROR_DEVICE_GAIN;
        public static final int NBioAPIERROR_DEVICE_INIT_FAIL;
        public static final int NBioAPIERROR_DEVICE_IO_CONTROL_FAIL;
        public static final int NBioAPIERROR_DEVICE_LFD;
        public static final int NBioAPIERROR_DEVICE_LOST_DEVICE;
        public static final int NBioAPIERROR_DEVICE_MAKE_INSTANCE_FAIL;
        public static final int NBioAPIERROR_DEVICE_NOT_CONNECTED;
        public static final int NBioAPIERROR_DEVICE_NOT_OPENED;
        public static final int NBioAPIERROR_DEVICE_NOT_PERMISSION;
        public static final int NBioAPIERROR_DEVICE_NOT_SUPPORT;
        public static final int NBioAPIERROR_DEVICE_OPEN_FAIL;
        public static final int NBioAPIERROR_ENCRYPTED_DATA_ERROR;
        public static final int NBioAPIERROR_ENROLL_EVENT_EXTRACT;
        public static final int NBioAPIERROR_ENROLL_EVENT_HOLD;
        public static final int NBioAPIERROR_ENROLL_EVENT_MATCH_FAILED;
        public static final int NBioAPIERROR_ENROLL_EVENT_PLACE;
        public static final int NBioAPIERROR_ENROLL_EVENT_PLACE_AGAIN;
        public static final int NBioAPIERROR_ENROLL_EVENT_REMOVE;
        public static final int NBioAPIERROR_EXPIRED_VERSION;
        public static final int NBioAPIERROR_EXTRACTION_OPEN_FAIL;
        public static final int NBioAPIERROR_FUNCTION_FAIL;
        public static final int NBioAPIERROR_INDEXSEARCH_DUPLICATED_ID;
        public static final int NBioAPIERROR_INDEXSEARCH_IDENTIFY_FAIL;
        public static final int NBioAPIERROR_INDEXSEARCH_IDENTIFY_STOP;
        public static final int NBioAPIERROR_INDEXSEARCH_INIT_FAIL;
        public static final int NBioAPIERROR_INDEXSEARCH_LOAD_DB;
        public static final int NBioAPIERROR_INDEXSEARCH_SAVE_DB;
        public static final int NBioAPIERROR_INDEXSEARCH_UNKNOWN_VER;
        public static final int NBioAPIERROR_INIT_ENROLLQUALITY;
        public static final int NBioAPIERROR_INIT_ENROLLSECURITYLEVEL;
        public static final int NBioAPIERROR_INIT_IDENTIFYQUALITY;
        public static final int NBioAPIERROR_INIT_MAXFINGER;
        public static final int NBioAPIERROR_INIT_NECESSARYENROLLNUM;
        public static final int NBioAPIERROR_INIT_PRESEARCHRATE;
        public static final int NBioAPIERROR_INIT_RESERVED1;
        public static final int NBioAPIERROR_INIT_RESERVED2;
        public static final int NBioAPIERROR_INIT_RESERVED3;
        public static final int NBioAPIERROR_INIT_RESERVED4;
        public static final int NBioAPIERROR_INIT_RESERVED5;
        public static final int NBioAPIERROR_INIT_RESERVED6;
        public static final int NBioAPIERROR_INIT_RESERVED7;
        public static final int NBioAPIERROR_INIT_SAMPLESPERFINGER;
        public static final int NBioAPIERROR_INIT_SECURITYLEVEL;
        public static final int NBioAPIERROR_INIT_VERIFYQUALITY;
        public static final int NBioAPIERROR_INTERNAL_CHECKSUM_FAIL;
        public static final int NBioAPIERROR_INVALID_DEVICE_CODE;
        public static final int NBioAPIERROR_INVALID_DEVICE_ID;
        public static final int NBioAPIERROR_INVALID_HANDLE;
        public static final int NBioAPIERROR_INVALID_MINSIZE;
        public static final int NBioAPIERROR_INVALID_POINTER;
        public static final int NBioAPIERROR_INVALID_SAMPLESPERFINGER;
        public static final int NBioAPIERROR_INVALID_TEMPLATE;
        public static final int NBioAPIERROR_INVALID_TYPE;
        public static final int NBioAPIERROR_LOWVERSION_DRIVER;
        public static final int NBioAPIERROR_MUST_BE_PROCESSED_DATA;
        public static final int NBioAPIERROR_NONE;
        public static final int NBioAPIERROR_NOTSUPPORT_FUNC;
        public static final int NBioAPIERROR_OUT_OF_MEMORY;
        public static final int NBioAPIERROR_STRUCTTYPE_NOT_MATCHED;
        public static final int NBioAPIERROR_UNKNOWN_FORMAT;
        public static final int NBioAPIERROR_UNKNOWN_INPUTFORMAT;
        public static final int NBioAPIERROR_UNKNOWN_VERSION;
        public static final int NBioAPIERROR_USER_BACK;
        public static final int NBioAPIERROR_USER_CANCEL;
        public static final int NBioAPIERROR_VALIDITY_FAIL;
        public static final int NBioAPIERROR_VERIFICATION_OPEN_FAIL;
        public static final int NBioAPIERROR_WRONG_DEVICE_ID;
    }

    /* loaded from: classes4.dex */
    public static class EXPORT_MINCONV_TYPE {
        public static final int ANSI;
        public static final int EXTENSION;
        public static final int FDA;
        public static final int FDAC;
        public static final int FDP;
        public static final int FDU;
        public static final int FELICA;
        public static final int FIM01_HD;
        public static final int FIM01_HV;
        public static final int FIM10_HV;
        public static final int FIM10_LV;
        public static final int ISO;
        public static final int OLD_FDA;
        public static final int TEMPLATESIZE_112;
        public static final int TEMPLATESIZE_128;
        public static final int TEMPLATESIZE_144;
        public static final int TEMPLATESIZE_160;
        public static final int TEMPLATESIZE_176;
        public static final int TEMPLATESIZE_192;
        public static final int TEMPLATESIZE_208;
        public static final int TEMPLATESIZE_224;
        public static final int TEMPLATESIZE_240;
        public static final int TEMPLATESIZE_256;
        public static final int TEMPLATESIZE_272;
        public static final int TEMPLATESIZE_288;
        public static final int TEMPLATESIZE_304;
        public static final int TEMPLATESIZE_32;
        public static final int TEMPLATESIZE_320;
        public static final int TEMPLATESIZE_336;
        public static final int TEMPLATESIZE_352;
        public static final int TEMPLATESIZE_368;
        public static final int TEMPLATESIZE_384;
        public static final int TEMPLATESIZE_400;
        public static final int TEMPLATESIZE_48;
        public static final int TEMPLATESIZE_64;
        public static final int TEMPLATESIZE_80;
        public static final int TEMPLATESIZE_96;
    }

    /* loaded from: classes4.dex */
    public static class FINGER_ID {
        public static final byte LEFT_INDEX;
        public static final byte LEFT_LITTLE;
        public static final byte LEFT_MIDDLE;
        public static final byte LEFT_RING;
        public static final byte LEFT_THUMB;
        public static final byte RIGHT_INDEX;
        public static final byte RIGHT_LITTLE;
        public static final byte RIGHT_MIDDLE;
        public static final byte RIGHT_RING;
        public static final byte RIGHT_THUMB;
        public static final byte UNKNOWN;
    }

    /* loaded from: classes4.dex */
    public static class FIR_DATA_TYPE {
        public static final int ENCRYPTED;
        public static final int INTERMEDIATE;
        public static final int LINEPATTERN;
        public static final int PROCESSED;
        public static final int RAW;
    }

    /* loaded from: classes4.dex */
    public static class FIR_FORM {
        public static final int FULLFIR;
        public static final int HANDLE;
        public static final int TEXTENCODE;
    }

    /* loaded from: classes4.dex */
    public static class FIR_FORMAT {
        public static final int EXTENSION;
        public static final int NBAS;
        public static final int STANDARD;
        public static final int STANDARD_256AES;
        public static final int STANDARD_3DES;
        public static final int STANDARD_AES;
    }

    /* loaded from: classes4.dex */
    public static class FIR_PURPOSE {
        @Deprecated
        public static final int AUDIT;
        public static final int ENROLL;
        public static final int ENROLL_FOR_IDENTIFICATION_ONLY;
        public static final int ENROLL_FOR_VERIFICATION_ONLY;
        @Deprecated
        public static final int IDENTIFY;
        @Deprecated
        public static final int UPDATE;
        public static final int VERIFY;
    }

    /* loaded from: classes4.dex */
    public static class FIR_SECURITY_LEVEL {
        public static final int ABOVE_NORMAL;
        public static final int BELOW_NORMAL;
        public static final int HIGH;
        public static final int HIGHER;
        public static final int HIGHEST;
        public static final int LOW;
        public static final int LOWER;
        public static final int LOWEST;
        public static final int NORMAL;
    }

    static native int NBioAPI_GetNFIQInfoFromRaw(NFIQInfo nFIQInfo);

    static native int NBioAPI_NativeAddFIRToIndexSearchDB(long j, INPUT_FIR input_fir, int i, IndexSearch.SAMPLE_INFO sample_info);

    static native int NBioAPI_NativeAdjustDevice(long j, CAPTURE_CALLBACK capture_callback);

    static native int NBioAPI_NativeCapture(long j, int i, FIR_HANDLE fir_handle, int i2, FIR_HANDLE fir_handle2, CAPTURED_DATA captured_data, CAPTURE_CALLBACK capture_callback);

    static native int NBioAPI_NativeCheckDataExistFromIndexSearchDB(long j, IndexSearch.FP_INFO fp_info, Boolean bool);

    static native int NBioAPI_NativeCheckFinger(long j, Boolean bool);

    static native int NBioAPI_NativeClearIndexSearchDB(long j);

    static native int NBioAPI_NativeCloseDevice(long j, short s);

    static native int NBioAPI_NativeCreateTemplate(long j, INPUT_FIR input_fir, INPUT_FIR input_fir2, FIR_HANDLE fir_handle, FIR_PAYLOAD fir_payload);

    static native int NBioAPI_NativeEnumerateDevice(long j, DEVICE_ENUM_INFO device_enum_info);

    static native int NBioAPI_NativeExportRawToISOV1(Export.AUDIT audit, ISOBUFFER isobuffer, boolean z, byte b);

    static native int NBioAPI_NativeExportRawToISOV2(byte b, short s, short s2, byte[] bArr, ISOBUFFER isobuffer, boolean z, byte b2);

    static native int NBioAPI_NativeFC_Core_Quality_from_Template(byte[] bArr, int i, int i2, int i3);

    static native int NBioAPI_NativeFDxToNBioBSP(long j, byte[] bArr, int i, int i2, int i3, FIR_HANDLE fir_handle);

    static native int NBioAPI_NativeFreeFIRHandle(long j);

    static native int NBioAPI_NativeGetDataCountFromIndexSearchDB(long j, Integer num);

    static native int NBioAPI_NativeGetDeviceInfo(long j, short s, DEVICE_INFO device_info);

    static native int NBioAPI_NativeGetFIRFromHandle(long j, FIR_HANDLE fir_handle, FIR fir, int i);

    static native int NBioAPI_NativeGetIndexSearchInitInfo(long j, IndexSearch.INIT_INFO init_info);

    static native int NBioAPI_NativeGetInitInfo(long j, INIT_INFO_0 init_info_0);

    static native short NBioAPI_NativeGetOpenedDeviceID(long j);

    static native int NBioAPI_NativeGetTextFIRFromHandle(long j, FIR_HANDLE fir_handle, FIR_TEXTENCODE fir_textencode, int i);

    static native void NBioAPI_NativeGetVersion(long j, NBioBSPJNI nBioBSPJNI);

    static native int NBioAPI_NativeIdentifyDataFromIndexSearchDB(long j, INPUT_FIR input_fir, int i, IndexSearch.FP_INFO fp_info, int i2);

    static native int NBioAPI_NativeImageToNBioBSP(long j, Export.AUDIT audit, FIR_HANDLE fir_handle);

    static native int NBioAPI_NativeImportBioAPIOpaqueToFIRHandle(long j, byte[] bArr, FIR_HANDLE fir_handle);

    static native int NBioAPI_NativeImportDataToNBioBSP(long j, Export.DATA data, int i, int i2, FIR_HANDLE fir_handle);

    static native int NBioAPI_NativeImportISOToRaw(ISOBUFFER isobuffer, NIMPORTRAWSET nimportrawset);

    static native int NBioAPI_NativeInit(NBioBSPJNI nBioBSPJNI, String str);

    static native int NBioAPI_NativeInitIndexSearchEngine(long j);

    static native int NBioAPI_NativeLoadIndexSearchDBFromFile(long j, String str);

    static native int NBioAPI_NativeNBioBSPToFDx(long j, INPUT_FIR input_fir, Export.DATA data, int i);

    static native int NBioAPI_NativeNBioBSPToImage(long j, INPUT_FIR input_fir, Export.AUDIT audit);

    static native int NBioAPI_NativeOpenDevice(long j, short s);

    static native int NBioAPI_NativeProcess(long j, INPUT_FIR input_fir, FIR_HANDLE fir_handle);

    static native int NBioAPI_NativeQualityCheck(int i, int i2, byte[] bArr, byte[] bArr2);

    static native int NBioAPI_NativeRawToWSQ(byte[] bArr, int i, int i2, Export.TEMPLATE_DATA template_data, float f);

    static native int NBioAPI_NativeReadDataFromDevice(long j, int i, Integer num);

    static native int NBioAPI_NativeRemoveDataFromIndexSearchDB(long j, IndexSearch.FP_INFO fp_info);

    static native int NBioAPI_NativeRemoveUserFromIndexSearchDB(long j, int i);

    static native int NBioAPI_NativeResizeRaw(byte[] bArr, Export.AUDIT audit, int i, int i2, int i3, int i4);

    static native int NBioAPI_NativeSaveIndexSearchDBToFile(long j, String str);

    static native int NBioAPI_NativeSetDeviceInfo(long j, short s, DEVICE_INFO device_info);

    static native int NBioAPI_NativeSetIndexSearchInitInfo(long j, IndexSearch.INIT_INFO init_info);

    static native int NBioAPI_NativeSetInitInfo(long j, INIT_INFO_0 init_info_0);

    static native int NBioAPI_NativeTerminate(long j);

    static native int NBioAPI_NativeTerminateIndexSearchEngine(long j);

    static native int NBioAPI_NativeVerify(long j, INPUT_FIR input_fir, Boolean bool, FIR_PAYLOAD fir_payload, int i, FIR_HANDLE fir_handle, CAPTURED_DATA captured_data, CAPTURE_CALLBACK capture_callback);

    static native int NBioAPI_NativeVerifyMatchEx(long j, INPUT_FIR input_fir, INPUT_FIR input_fir2, Boolean bool, FIR_PAYLOAD fir_payload, MATCH_OPTION match_option);

    static native int NBioAPI_NativeWSQToRaw(byte[] bArr, int i, Export.AUDIT audit);

    static native int NBioAPI_NativeWriteDataToDevice(long j, int i, int i2);

    static {
        try {
            System.loadLibrary("native-lib");
            s_useNative = true;
        } catch (Exception e) {
            s_useNative = false;
        }
    }

    private NBioBSPJNI(String serialCode) {
        this.m_CenterX = 0;
        this.m_CenterY = 0;
        this.isLedOn = false;
        this.reqId = 0;
        this.captureCount = 0;
        this.isReceive = false;
        this.isSyncOpenDevice = false;
        this.mUsbReceiver = new BroadcastReceiver() { // from class: com.nitgen.SDK.AndroidBSP.NBioBSPJNI.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (AlDevManager.ACTION_CAM_PERMISSION.equals(action)) {
                    if (!NBioBSPJNI.this.isReceive) {
                        NBioBSPJNI.this.isReceive = true;
                        if (NBioBSPJNI.this.mHFDU06SP.getDevice().getDeviceName().compareTo(((AlCameraDevice) intent.getParcelableExtra(AlDevManager.CAMERA_BROADCAST)).getDeviceName()) != 0) {
                            Log.d(NBioBSPJNI.TAG, "device Fail");
                            NBioBSPJNI.this.setInterface(null, null);
                            NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                            nBioBSPJNI.isReceive = false;
                            nBioBSPJNI.m_nErrCode = 257;
                            if (NBioBSPJNI.this.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                        } else if (NBioBSPJNI.this.mHFDU06SP.openDevice()) {
                            byte[] m_pClsBuffer = NBioBSPJNI.this.mHFDU06SP.readSensorSettingValues();
                            if (m_pClsBuffer != null) {
                                NBioBSPJNI nBioBSPJNI2 = NBioBSPJNI.this;
                                nBioBSPJNI2.m_CenterX = m_pClsBuffer[2] & 255;
                                nBioBSPJNI2.m_CenterY = m_pClsBuffer[3] & 255;
                                if (nBioBSPJNI2.m_CenterX < 124 || NBioBSPJNI.this.m_CenterX > 166) {
                                    NBioBSPJNI.this.m_CenterX = AlErrorCode.ERR_PERMISSION_DENIED;
                                }
                                if (NBioBSPJNI.this.m_CenterY < 146 || NBioBSPJNI.this.m_CenterY > 188) {
                                    NBioBSPJNI.this.m_CenterY = MXErrCode.ERR_NO_KEY_TEE;
                                }
                                NBioBSPJNI.this.InitModifyTable06H();
                                NBioBSPJNI nBioBSPJNI3 = NBioBSPJNI.this;
                                nBioBSPJNI3.isReceive = false;
                                if (nBioBSPJNI3.capture_callback != null) {
                                    NBioBSPJNI.this.capture_callback.OnConnected();
                                    return;
                                }
                                return;
                            }
                            Log.d(NBioBSPJNI.TAG, "setInterface Fail");
                            NBioBSPJNI.this.setInterface(null, null);
                            NBioBSPJNI nBioBSPJNI4 = NBioBSPJNI.this;
                            nBioBSPJNI4.isReceive = false;
                            nBioBSPJNI4.m_nErrCode = 257;
                            if (NBioBSPJNI.this.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                        } else {
                            Log.d(NBioBSPJNI.TAG, "device Fail");
                            NBioBSPJNI.this.setInterface(null, null);
                            NBioBSPJNI nBioBSPJNI5 = NBioBSPJNI.this;
                            nBioBSPJNI5.isReceive = false;
                            nBioBSPJNI5.m_nErrCode = 257;
                            if (NBioBSPJNI.this.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                        }
                    }
                } else if (NBioBSPJNI.ACTION_USB_PERMISSION.equals(action)) {
                    if (!NBioBSPJNI.this.isReceive) {
                        NBioBSPJNI.this.isReceive = true;
                        UsbDevice device = (UsbDevice) intent.getParcelableExtra("device");
                        if (!intent.getBooleanExtra("permission", false)) {
                            Log.d(NBioBSPJNI.TAG, "EXTRA_PERMISSION_GRANTED Fail");
                            NBioBSPJNI.this.setInterface(null, null);
                            NBioBSPJNI nBioBSPJNI6 = NBioBSPJNI.this;
                            nBioBSPJNI6.isReceive = false;
                            nBioBSPJNI6.m_nErrCode = 257;
                            if (NBioBSPJNI.this.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                        } else if (device != null) {
                            UsbInterface usbInterface = NBioBSPJNI.findInterface(device);
                            Log.d(NBioBSPJNI.TAG, "setInterface");
                            if (NBioBSPJNI.this.setInterface(device, usbInterface)) {
                                NBioBSPJNI.this.initDevice();
                                return;
                            }
                            Log.d(NBioBSPJNI.TAG, "setInterface Fail");
                            NBioBSPJNI.this.setInterface(null, null);
                            NBioBSPJNI nBioBSPJNI7 = NBioBSPJNI.this;
                            nBioBSPJNI7.isReceive = false;
                            nBioBSPJNI7.m_nErrCode = 257;
                            if (NBioBSPJNI.this.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                        } else {
                            Log.d(NBioBSPJNI.TAG, "device Fail");
                            NBioBSPJNI.this.setInterface(null, null);
                            NBioBSPJNI nBioBSPJNI8 = NBioBSPJNI.this;
                            nBioBSPJNI8.isReceive = false;
                            nBioBSPJNI8.m_nErrCode = 257;
                            if (NBioBSPJNI.this.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                        }
                    }
                } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                    Log.d(NBioBSPJNI.TAG, "ACTION_USB_DEVICE_DETACHED Fail");
                    NBioBSPJNI.this.setInterface(null, null);
                    NBioBSPJNI nBioBSPJNI9 = NBioBSPJNI.this;
                    nBioBSPJNI9.isReceive = false;
                    nBioBSPJNI9.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_LOST_DEVICE;
                    if (NBioBSPJNI.this.capture_callback != null) {
                        NBioBSPJNI.this.capture_callback.OnDisConnected();
                    }
                }
            }
        };
        this.isBreak = false;
        this.isSkip = false;
        this.mHandler = new Handler() { // from class: com.nitgen.SDK.AndroidBSP.NBioBSPJNI.2
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                int i = msg.what;
                if (i != 34) {
                    if (i != NBioBSPJNI.bRequest_SET_SENSOR_OPTION) {
                        if (i == 255) {
                            if (msg.arg1 == 1) {
                                NBioBSPJNI.this.m_nErrCode = 0;
                                if (!NBioBSPJNI.isSetSensorOption) {
                                    NBioBSPJNI.this.setSensorOption();
                                    return;
                                }
                                return;
                            }
                            NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                            nBioBSPJNI.isReceive = false;
                            if (nBioBSPJNI.capture_callback != null) {
                                NBioBSPJNI.this.capture_callback.OnDisConnected();
                            }
                            NBioBSPJNI.this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_IO_CONTROL_FAIL;
                            Log.d(NBioBSPJNI.TAG, "bRequest_INIT_DEVICE Fail");
                        }
                    } else if (msg.arg1 == 1) {
                        NBioBSPJNI.this.m_nErrCode = 0;
                        Log.d(NBioBSPJNI.TAG, "bRequest_SET_SENSOR_OPTION Success");
                        NBioBSPJNI.isSetSensorOption = true;
                        if (!NBioBSPJNI.isGetSensorSetting) {
                            NBioBSPJNI.this.getSensorSetting();
                        }
                    } else {
                        NBioBSPJNI nBioBSPJNI2 = NBioBSPJNI.this;
                        nBioBSPJNI2.isReceive = false;
                        nBioBSPJNI2.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_IO_CONTROL_FAIL;
                        if (NBioBSPJNI.this.capture_callback != null) {
                            NBioBSPJNI.this.capture_callback.OnDisConnected();
                        }
                        Log.d(NBioBSPJNI.TAG, "bRequest_SET_SENSOR_OPTION Fail");
                    }
                } else if (msg.arg1 == 1) {
                    NBioBSPJNI.this.m_nErrCode = 0;
                    NBioBSPJNI.isGetSensorSetting = true;
                    byte[] m_pClsBuffer = (byte[]) msg.obj;
                    if (NBioBSPJNI.CURRENT_PRODUCT_ID == NBioBSPJNI.PRODUCT_ID_NITGEN_06 || NBioBSPJNI.CURRENT_PRODUCT_ID == 256) {
                        NBioBSPJNI nBioBSPJNI3 = NBioBSPJNI.this;
                        nBioBSPJNI3.m_CenterX = m_pClsBuffer[2] & 255;
                        nBioBSPJNI3.m_CenterY = m_pClsBuffer[3] & 255;
                        if (nBioBSPJNI3.m_CenterX < 124 || NBioBSPJNI.this.m_CenterX > 166) {
                            NBioBSPJNI.this.m_CenterX = AlErrorCode.ERR_PERMISSION_DENIED;
                        }
                        if (NBioBSPJNI.this.m_CenterY < 146 || NBioBSPJNI.this.m_CenterY > 188) {
                            NBioBSPJNI.this.m_CenterY = MXErrCode.ERR_NO_KEY_TEE;
                        }
                        NBioBSPJNI.this.InitModifyTable06H();
                    } else {
                        NBioBSPJNI.this.m_CenterX = ByteBuffer.wrap(new byte[]{m_pClsBuffer[3], m_pClsBuffer[2]}).getShort();
                        NBioBSPJNI.this.m_CenterY = ByteBuffer.wrap(new byte[]{m_pClsBuffer[5], m_pClsBuffer[4]}).getShort();
                        if (NBioBSPJNI.this.m_CenterX < 130 || NBioBSPJNI.this.m_CenterX > 270) {
                            NBioBSPJNI.this.m_CenterX = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                        }
                        if (NBioBSPJNI.this.m_CenterY < 165 || NBioBSPJNI.this.m_CenterY > 315) {
                            NBioBSPJNI.this.m_CenterY = NBioBSPJNI.bRequest_GET_DUMP_IMAGE;
                        }
                        NBioBSPJNI.this.InitModifyTable08PIV();
                    }
                    NBioBSPJNI nBioBSPJNI4 = NBioBSPJNI.this;
                    nBioBSPJNI4.isReceive = false;
                    if (nBioBSPJNI4.capture_callback != null) {
                        NBioBSPJNI.this.capture_callback.OnConnected();
                    }
                } else {
                    NBioBSPJNI nBioBSPJNI5 = NBioBSPJNI.this;
                    nBioBSPJNI5.isReceive = false;
                    nBioBSPJNI5.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_IO_CONTROL_FAIL;
                    if (NBioBSPJNI.this.capture_callback != null) {
                        NBioBSPJNI.this.capture_callback.OnDisConnected();
                    }
                    Log.d(NBioBSPJNI.TAG, "bRequest_GET_SENSOR_SETTING Fail");
                }
            }
        };
        this.m_pZoomAry06H = null;
        this.m_pXtable = null;
        this.m_pYtable = null;
        this.m_pSrtable = null;
        this.m_pSctable = null;
        this.m_hNBioBSP = 0;
        this.m_nErrCode = 0;
        this.m_bspVersion = "0.0000";
        this.init_capture_quality_info = new CAPTURE_QUALITY_INFO();
        this.eHandler = new Handler();
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeInit(this, serialCode);
        }
    }

    @Deprecated
    public NBioBSPJNI(String serialCode, Activity mActivity) {
        this(serialCode);
        this.m_nErrCode = 0;
        this.activity = mActivity;
        this.mManager = (UsbManager) this.activity.getSystemService("usb");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(AlDevManager.ACTION_CAM_PERMISSION);
        filter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.activity.registerReceiver(this.mUsbReceiver, filter);
    }

    public NBioBSPJNI(String serialCode, Activity mActivity, CAPTURE_CALLBACK mCallback) {
        this(serialCode);
        this.m_nErrCode = 0;
        this.activity = mActivity;
        this.mManager = (UsbManager) this.activity.getSystemService("usb");
        this.capture_callback = mCallback;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(AlDevManager.ACTION_CAM_PERMISSION);
        filter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.activity.registerReceiver(this.mUsbReceiver, filter);
    }

    public static UsbInterface findInterface(UsbDevice device) {
        if (0 < device.getInterfaceCount()) {
            return device.getInterface(0);
        }
        return null;
    }

    private boolean setEndpoint(UsbInterface intf) {
        ArrayList<UsbEndpoint> epOutList = new ArrayList<>();
        ArrayList<UsbEndpoint> epInList = new ArrayList<>();
        for (int i = 0; i < intf.getEndpointCount(); i++) {
            UsbEndpoint ep = intf.getEndpoint(i);
            if (ep.getType() == 2) {
                if (ep.getDirection() == 0) {
                    epOutList.add(ep);
                } else {
                    epInList.add(ep);
                }
            }
        }
        if (epInList.size() == 0 || epOutList.size() == 0) {
            return false;
        }
        if (epInList.size() > 1) {
            this.mEndpointIn = epInList.get(0);
        } else {
            this.mEndpointIn = epInList.get(0);
        }
        return true;
    }

    public boolean setInterface(UsbDevice device, UsbInterface intf) {
        UsbDeviceConnection usbDeviceConnection = this.mUsbDeviceConnection;
        if (usbDeviceConnection != null) {
            if (intf != null) {
                usbDeviceConnection.releaseInterface(intf);
            }
            this.mUsbDeviceConnection.close();
            this.mUsbDeviceConnection = null;
        }
        if (device == null || intf == null) {
            Log.d(TAG, "device null or intf null");
            return false;
        } else if (setEndpoint(intf)) {
            UsbDeviceConnection connection = this.mManager.openDevice(device);
            if (connection == null) {
                Log.d(TAG, "connection null");
                return false;
            } else if (connection.claimInterface(intf, true)) {
                Log.d(TAG, "connection claimInterface success");
                this.mUsbDeviceConnection = connection;
                return true;
            } else {
                Log.d(TAG, "connection claimInterface fail");
                connection.close();
                return false;
            }
        } else {
            Log.d(TAG, "setEndpoint fail");
            return false;
        }
    }

    public void SetBrightness(int brightness) {
        if (CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06_SP) {
            this.mHFDU06SP.setBrightness(brightness);
        } else {
            setSensorOption(brightness);
        }
    }

    private void bright_lower() {
        int i;
        int i2 = CURRENT_PRODUCT_ID;
        if (i2 == PRODUCT_ID_NITGEN_06 || i2 == 256) {
            int i3 = CURRENT_BRIGHT_06;
            if (i3 > 0) {
                CURRENT_BRIGHT_06 = i3 - BRIGHT_INTERVAL;
            }
        } else if (i2 == PRODUCT_ID_NITGEN_08 && (i = CURRENT_BRIGHT_08) > 0) {
            CURRENT_BRIGHT_08 = i - BRIGHT_INTERVAL;
        }
        setSensorOption();
    }

    private void bright_higher() {
        int i;
        int i2 = CURRENT_PRODUCT_ID;
        if (i2 == PRODUCT_ID_NITGEN_06 || i2 == 256) {
            int i3 = CURRENT_BRIGHT_06;
            if (i3 < 100) {
                CURRENT_BRIGHT_06 = i3 + BRIGHT_INTERVAL;
            }
        } else if (i2 == PRODUCT_ID_NITGEN_08 && (i = CURRENT_BRIGHT_08) < 100) {
            CURRENT_BRIGHT_08 = i + BRIGHT_INTERVAL;
        }
        setSensorOption();
    }

    public void CaptureCancel() {
        this.isBreak = true;
    }

    private int ConvertCoreQuality(int nBasicCoreQuality) {
        if (nBasicCoreQuality < 10) {
            return 10;
        }
        if (10 <= nBasicCoreQuality && nBasicCoreQuality < 30) {
            return 9;
        }
        if (30 <= nBasicCoreQuality && nBasicCoreQuality < 50) {
            return 8;
        }
        if (50 <= nBasicCoreQuality && nBasicCoreQuality < 60) {
            return 7;
        }
        if (60 <= nBasicCoreQuality && nBasicCoreQuality < 70) {
            return 6;
        }
        if (70 <= nBasicCoreQuality && nBasicCoreQuality < 80) {
            return 5;
        }
        if (80 <= nBasicCoreQuality && nBasicCoreQuality < 85) {
            return 4;
        }
        if (85 <= nBasicCoreQuality && nBasicCoreQuality < 90) {
            return 3;
        }
        if (90 <= nBasicCoreQuality && nBasicCoreQuality < 95) {
            return 2;
        }
        if (95 <= nBasicCoreQuality) {
            return 1;
        }
        return 0;
    }

    private ControlCommandVo sendControlCommand(ControlCommandVo commandVo) {
        byte[] buffer;
        try {
            if (this.mUsbDeviceConnection.controlTransfer(commandVo.getRequestType(), commandVo.getRequest(), commandVo.getValue(), commandVo.getIndex(), commandVo.getBuffer(), commandVo.getLength(), TRY_CONNECT_TIME) >= 0) {
                if (commandVo.getRequest() == bRequest_START_CONT_IMAGE_DATA) {
                    if (!(CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06 || CURRENT_PRODUCT_ID == 256)) {
                        buffer = new byte[NITGEN_VGA_FRAME_SIZE_08];
                        int pos = 0;
                        int i = 0;
                        while (true) {
                            if (i >= 20) {
                                break;
                            }
                            byte[] tempBuffer = new byte[15360];
                            if (this.mUsbDeviceConnection.bulkTransfer(this.mEndpointIn, tempBuffer, tempBuffer.length, TRY_CONNECT_TIME) < 0) {
                                commandVo.setBlkSuccess(false);
                                break;
                            }
                            System.arraycopy(tempBuffer, 0, buffer, pos, 15360);
                            pos += 15360;
                            commandVo.setBlkSuccess(true);
                            i++;
                        }
                        commandVo.setBufferBlk(buffer);
                    }
                    buffer = new byte[NITGEN_VGA_FRAME_SIZE];
                    int pos2 = 0;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= NITGEN_VGA_FRAME_SIZE_HEGITH) {
                            break;
                        }
                        byte[] tempBuffer2 = new byte[512];
                        if (this.mUsbDeviceConnection.bulkTransfer(this.mEndpointIn, tempBuffer2, tempBuffer2.length, TRY_CONNECT_TIME) < 0) {
                            commandVo.setBlkSuccess(false);
                            break;
                        }
                        System.arraycopy(tempBuffer2, 0, buffer, pos2, 512);
                        pos2 += 512;
                        commandVo.setBlkSuccess(true);
                        i2++;
                    }
                    commandVo.setBufferBlk(buffer);
                }
                commandVo.setCtlSuccess(true);
            } else {
                commandVo.setCtlSuccess(false);
            }
        } catch (Exception e) {
            commandVo.setCtlSuccess(false);
        }
        return commandVo;
    }

    public boolean initDevice() {
        if (!sendControlCommand(nitgen_INIT_DEVICE()).isCtlSuccess() || !sendControlCommand(nitgen_SET_SENSOR_OPTION()).isCtlSuccess()) {
            return false;
        }
        ControlCommandVo commandVo = sendControlCommand(nitgen_GET_SENSOR_SETTING());
        if (!commandVo.isCtlSuccess()) {
            return false;
        }
        this.m_nErrCode = 0;
        isGetSensorSetting = true;
        byte[] m_pClsBuffer = commandVo.getBuffer();
        Utils.printByteArray(TAG, "eeprom", m_pClsBuffer);
        int i = CURRENT_PRODUCT_ID;
        if (i == PRODUCT_ID_NITGEN_06 || i == 256) {
            this.m_CenterX = m_pClsBuffer[2] & 255;
            this.m_CenterY = m_pClsBuffer[3] & 255;
            int i2 = this.m_CenterX;
            if (i2 < 124 || i2 > 166) {
                this.m_CenterX = AlErrorCode.ERR_PERMISSION_DENIED;
            }
            int i3 = this.m_CenterY;
            if (i3 < 146 || i3 > 188) {
                this.m_CenterY = MXErrCode.ERR_NO_KEY_TEE;
            }
            InitModifyTable06H();
        } else {
            this.m_CenterX = ByteBuffer.wrap(new byte[]{m_pClsBuffer[3], m_pClsBuffer[2]}).getShort();
            this.m_CenterY = ByteBuffer.wrap(new byte[]{m_pClsBuffer[5], m_pClsBuffer[4]}).getShort();
            int i4 = this.m_CenterX;
            if (i4 < 130 || i4 > 270) {
                this.m_CenterX = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            }
            int i5 = this.m_CenterY;
            if (i5 < 165 || i5 > 315) {
                this.m_CenterY = bRequest_GET_DUMP_IMAGE;
            }
            InitModifyTable08PIV();
        }
        this.isReceive = false;
        CAPTURE_CALLBACK capture_callback = this.capture_callback;
        if (capture_callback != null) {
            capture_callback.OnConnected();
        }
        return true;
    }

    public void setSensorOption() {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_SET_SENSOR_OPTION()).start();
    }

    private void setSensorOption(int brightness) {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_SET_SENSOR_OPTION(brightness)).start();
    }

    public void getSensorSetting() {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_GET_SENSOR_SETTING()).start();
    }

    private void startContImageData() {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_START_CONT_IMAGE_DATA(), this.mEndpointIn).start();
    }

    private void stopContImageData() {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_STOP_CONT_IMAGE_DATA()).start();
    }

    private void getTouchStatus() {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_GET_TOUCH_STATUS()).start();
    }

    private void sensorLed() {
        new USBCommandThread(this.mUsbDeviceConnection, this.mHandler, nitgen_SENSOR_LED(this.isLedOn)).start();
    }

    private ControlCommandVo nitgen_INIT_DEVICE() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(bRequestType_IN);
        commandVo.setRequest(255);
        commandVo.setValue(0);
        commandVo.setIndex(wIndex_SENSOR_LED);
        commandVo.setBuffer(new byte[8]);
        commandVo.setLength(8);
        return commandVo;
    }

    private ControlCommandVo nitgen_SENSOR_LED(boolean isOn) {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(64);
        commandVo.setRequest(144);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        byte[] buffer = new byte[64];
        if (isOn) {
            buffer[0] = 1;
        }
        commandVo.setBuffer(buffer);
        commandVo.setLength(64);
        return commandVo;
    }

    private ControlCommandVo nitgen_START_CONT_IMAGE_DATA() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(64);
        commandVo.setRequest(bRequest_START_CONT_IMAGE_DATA);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        commandVo.setBuffer(new byte[0]);
        commandVo.setLength(0);
        return commandVo;
    }

    private ControlCommandVo nitgen_STOP_CONT_IMAGE_DATA() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(64);
        commandVo.setRequest(bRequest_STOP_CONT_IMAGE_DATA);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        commandVo.setBuffer(new byte[0]);
        commandVo.setLength(0);
        return commandVo;
    }

    private ControlCommandVo nitgen_SET_SENSOR_OPTION() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(64);
        commandVo.setRequest(bRequest_SET_SENSOR_OPTION);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        byte[] buffer = new byte[8];
        buffer[0] = 2;
        int i = CURRENT_PRODUCT_ID;
        if (i == PRODUCT_ID_NITGEN_06 || i == 256) {
            int i2 = CURRENT_BRIGHT_06;
            if (i2 == 100) {
                buffer[1] = 99;
            } else if (i2 == 0) {
                buffer[1] = 1;
            } else {
                buffer[1] = (byte) i2;
            }
        } else if (i == PRODUCT_ID_NITGEN_08) {
            int i3 = CURRENT_BRIGHT_08;
            if (i3 == 100) {
                buffer[1] = 99;
            } else if (i3 == 0) {
                buffer[1] = 1;
            } else {
                buffer[1] = (byte) i3;
            }
        }
        commandVo.setBuffer(buffer);
        commandVo.setLength(8);
        return commandVo;
    }

    private ControlCommandVo nitgen_SET_SENSOR_OPTION(int brightness) {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(64);
        commandVo.setRequest(bRequest_SET_SENSOR_OPTION);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        byte[] buffer = new byte[8];
        buffer[0] = 2;
        buffer[1] = (byte) brightness;
        commandVo.setBuffer(buffer);
        commandVo.setLength(8);
        return commandVo;
    }

    private ControlCommandVo nitgen_GET_SENSOR_SETTING() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(bRequestType_IN);
        commandVo.setRequest(34);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        commandVo.setBuffer(new byte[64]);
        commandVo.setLength(64);
        return commandVo;
    }

    private ControlCommandVo nitgen_GET_TOUCH_STATUS() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(bRequestType_IN);
        commandVo.setRequest(33);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        commandVo.setBuffer(new byte[64]);
        commandVo.setLength(64);
        return commandVo;
    }

    private ControlCommandVo nitgen_GET_DEV_ID() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(bRequestType_IN);
        commandVo.setRequest(116);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        commandVo.setBuffer(new byte[8]);
        commandVo.setLength(8);
        return commandVo;
    }

    private ControlCommandVo nitgen_GET_VALUE() {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(bRequestType_IN);
        commandVo.setRequest(4);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        commandVo.setBuffer(new byte[64]);
        commandVo.setLength(64);
        return commandVo;
    }

    private ControlCommandVo nitgen_SET_VALUE(byte[] value) {
        ControlCommandVo commandVo = new ControlCommandVo();
        commandVo.setRequestType(64);
        commandVo.setRequest(8);
        commandVo.setValue(0);
        commandVo.setIndex(0);
        byte[] buffer = new byte[64];
        boolean z = true;
        boolean z2 = (value != null) & (value.length != 0);
        if (value.length > 64) {
            z = false;
        }
        if (z && z2) {
            System.arraycopy(value, 0, buffer, 0, value.length);
        }
        commandVo.setBuffer(buffer);
        commandVo.setLength(64);
        return commandVo;
    }

    public int getValue(byte[] value) {
        this.m_nErrCode = 1;
        if (CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06_SP) {
            this.mHFDU06SP.getValue(value);
            this.m_nErrCode = 0;
        } else {
            ControlCommandVo commandVo = sendControlCommand(nitgen_GET_VALUE());
            if (commandVo.isCtlSuccess()) {
                byte[] temp = commandVo.getBuffer();
                System.arraycopy(temp, 0, value, 0, temp.length);
                Utils.printByteArray(TAG, "getValue", value);
                this.m_nErrCode = 0;
            } else {
                this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_OPENED;
            }
        }
        return this.m_nErrCode;
    }

    public int setValue(byte[] value) {
        this.m_nErrCode = 1;
        if (CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06_SP) {
            this.mHFDU06SP.setValue(value);
            this.m_nErrCode = 0;
        } else if (sendControlCommand(nitgen_SET_VALUE(value)).isCtlSuccess()) {
            this.m_nErrCode = 0;
        } else {
            this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_OPENED;
        }
        return this.m_nErrCode;
    }

    public int getDeviceID(byte[] id) {
        this.m_nErrCode = 1;
        if (CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06_SP) {
            this.mHFDU06SP.getDeviceID(id);
            this.m_nErrCode = 0;
        } else {
            ControlCommandVo commandVo = sendControlCommand(nitgen_GET_DEV_ID());
            if (commandVo.isCtlSuccess()) {
                byte[] temp = commandVo.getBuffer();
                System.arraycopy(temp, 0, id, 0, temp.length);
                this.m_nErrCode = 0;
            } else {
                this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_OPENED;
            }
        }
        return this.m_nErrCode;
    }

    private boolean ImgModify06H(byte[] pOrgBuf, byte[] pMdfBuf) {
        int pos = 0;
        int posy = 2;
        int divX = 0;
        int i = 1600;
        while (i < 383200) {
            if (posy > 490) {
                return false;
            }
            if ((pOrgBuf[i] & 255) == 255 && (pOrgBuf[i + 1] & 255) == 0 && (pOrgBuf[i + 2] & 255) == 0 && (pOrgBuf[i + 3] & 255) == 128 && (pOrgBuf[i + 4] & 255) == 128 && (pOrgBuf[i + 5] & 255) == 16) {
                pos = 1;
            }
            if (pos == 0 && (pOrgBuf[i] & 255) == 128 && (pOrgBuf[i + 1] & 255) == 16 && (pOrgBuf[i + 2] & 255) == 255 && (pOrgBuf[i + 3] & 255) == 0 && (pOrgBuf[i + 4] & 255) == 0 && (pOrgBuf[i + 5] & 255) == 171) {
                pos = 2;
            }
            if (pos == 1) {
                if (divX != i % SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) {
                    divX = i % SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION;
                    System.arraycopy(pOrgBuf, i, pMdfBuf, posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION, SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION);
                    for (int m = 0; m < 800; m++) {
                        pMdfBuf[((posy - 1) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + m] = -1;
                    }
                    for (int k = 0; k < 800; k++) {
                        pMdfBuf[((posy - 1) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k] = (byte) ((pMdfBuf[((posy - 2) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k] + pMdfBuf[(posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k]) >> 1);
                    }
                    posy++;
                    for (int k2 = 0; k2 < 800; k2++) {
                        pMdfBuf[(posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k2] = (byte) ((pMdfBuf[((posy - 1) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k2] + pOrgBuf[(i + SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k2]) >> 1);
                    }
                } else {
                    System.arraycopy(pOrgBuf, i, pMdfBuf, posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION, SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION);
                }
                i += SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION;
                posy++;
            } else if (pos == 2) {
                if (divX != (i - 154) % SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) {
                    divX = (i - 154) % SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION;
                    System.arraycopy(pOrgBuf, i - 154, pMdfBuf, posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION, SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION);
                    for (int m2 = 0; m2 < 800; m2++) {
                        pMdfBuf[((posy - 1) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + m2] = -1;
                    }
                    for (int k3 = 0; k3 < 800; k3++) {
                        pMdfBuf[((posy - 1) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k3] = (byte) ((pMdfBuf[((posy - 2) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k3] + pMdfBuf[(posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k3]) >> 1);
                    }
                    posy++;
                    for (int k4 = 0; k4 < 800; k4++) {
                        pMdfBuf[(posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k4] = (byte) ((pMdfBuf[((posy - 1) * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + k4] + pOrgBuf[(i + 645) + k4]) >> 1);
                    }
                } else {
                    System.arraycopy(pOrgBuf, i - 154, pMdfBuf, posy * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION, SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION);
                }
                i += 645;
                posy++;
            } else {
                i++;
            }
            pos = 0;
        }
        return true;
    }

    private int CheckImageQuality(int x_size, int y_size, byte[] img) {
        int num_diff;
        int step = x_size / 28;
        int num_diff2 = 0;
        for (int i = 0; i < x_size; i += step) {
            for (int j = 2; j < y_size; j += 2) {
                if (Math.abs(IMG1(i, j, img, x_size) - IMG1(i, j - 2, img, x_size)) > 10) {
                    num_diff2++;
                }
            }
        }
        for (int i2 = 2; i2 < x_size; i2 += 2) {
            for (int j2 = 0; j2 < y_size; j2 += 8) {
                if (Math.abs(IMG1(i2, j2, img, x_size) - IMG1(i2 - 2, j2, img, x_size)) > 10) {
                    num_diff2++;
                }
            }
        }
        int i3 = CURRENT_PRODUCT_ID;
        if (i3 == PRODUCT_ID_NITGEN_06 || i3 == 256 || i3 == PRODUCT_ID_NITGEN_06_SP) {
            num_diff = ((num_diff2 - 1200) * 100) / PathInterpolatorCompat.MAX_NUM_POINTS;
        } else {
            num_diff = ((num_diff2 - 2000) * 100) / PathInterpolatorCompat.MAX_NUM_POINTS;
        }
        if (num_diff > 100) {
            return 100;
        }
        if (num_diff < 0) {
            return 0;
        }
        return num_diff;
    }

    private int IMG1(int i, int j, byte[] img, int x_size) {
        return img[(j * x_size) + i];
    }

    /* loaded from: classes4.dex */
    public class PZoomAry06H {
        public long dwI1;
        public long dwI2;
        public long dwI3;
        public long dwI4;
        public long dwI5;
        public long dwLPosition;
        public long dwRPosition;
        public int i_orgc;
        public int i_orgr;
        public float nvI1;
        public float nvI2;
        public float nvI3;
        public float nvI4;
        public float r_orgc;
        public float r_orgr;

        private PZoomAry06H() {
            NBioBSPJNI.this = r1;
        }
    }

    public void InitModifyTable06H() {
        long new_width;
        int widthm1;
        NBioBSPJNI nBioBSPJNI = this;
        nBioBSPJNI.m_pZoomAry06H = new PZoomAry06H[97776];
        long new_height = 336;
        long new_width2 = 290;
        int widthm12 = 639;
        long r = 0;
        while (r < new_height) {
            float tzoomInOutr = 0.705557f;
            float tzoomInOutc = 0.4556722f;
            long c = 0;
            while (c < new_width2) {
                int nindex = (int) ((290 * r) + c);
                float tr_orgr = ((float) r) / tzoomInOutr;
                float tr_orgc = ((float) c) / tzoomInOutc;
                int ti_orgr = (int) Math.floor((double) tr_orgr);
                int ti_orgc = (int) Math.floor((double) tr_orgc);
                float sr = tr_orgr - ((float) ti_orgr);
                float sc = tr_orgc - ((float) ti_orgc);
                nBioBSPJNI.m_pZoomAry06H[nindex] = new PZoomAry06H();
                PZoomAry06H[] pZoomAry06HArr = nBioBSPJNI.m_pZoomAry06H;
                pZoomAry06HArr[nindex].r_orgc = tr_orgc;
                pZoomAry06HArr[nindex].r_orgr = tr_orgr;
                pZoomAry06HArr[nindex].i_orgc = ti_orgc;
                pZoomAry06HArr[nindex].i_orgr = ti_orgr;
                pZoomAry06HArr[nindex].dwLPosition = (r * new_width2) + c + new_width2;
                pZoomAry06HArr[nindex].dwRPosition = ((r * new_width2) - c) + new_width2;
                if (ti_orgr < 0 || ti_orgr > 479 || ti_orgc < 0) {
                    widthm1 = widthm12;
                    new_width = new_width2;
                } else if (ti_orgc > widthm12) {
                    new_width = new_width2;
                    widthm1 = widthm12;
                } else {
                    new_width = new_width2;
                    pZoomAry06HArr[nindex].dwI1 = (long) ((ti_orgr * 640) + ti_orgc + 640);
                    pZoomAry06HArr[nindex].dwI2 = (long) ((ti_orgr * 640) + ti_orgc + 1 + 640);
                    pZoomAry06HArr[nindex].dwI3 = (long) (((ti_orgr + 1) * 640) + ti_orgc + 1 + 640);
                    pZoomAry06HArr[nindex].dwI4 = (long) (((ti_orgr + 1) * 640) + ti_orgc + 640);
                    widthm1 = widthm12;
                    pZoomAry06HArr[nindex].nvI1 = (float) ((1.0d - ((double) sc)) * (1.0d - ((double) sr)));
                    pZoomAry06HArr[nindex].nvI2 = (float) (((double) sc) * (1.0d - ((double) sr)));
                    pZoomAry06HArr[nindex].nvI3 = sc * sr;
                    pZoomAry06HArr[nindex].nvI4 = (float) (((double) sr) * (1.0d - ((double) sc)));
                }
                c++;
                nBioBSPJNI = this;
                tzoomInOutr = tzoomInOutr;
                tzoomInOutc = tzoomInOutc;
                new_height = new_height;
                widthm12 = widthm1;
                new_width2 = new_width;
            }
            r++;
            nBioBSPJNI = this;
            new_width2 = new_width2;
        }
    }

    private boolean CorrectImageCalc06H(byte[] srcBuf, long srcXn, long srcYn, byte[] dstBuf, long dstXn, long dstYn, long centerX, long centerY) {
        long new_width;
        byte[] ptmpOrg;
        NBioBSPJNI nBioBSPJNI = this;
        if (srcBuf == null || dstBuf == null || nBioBSPJNI.m_pZoomAry06H == null) {
            return false;
        }
        byte[] ptmpImgBuf = new byte[307680];
        byte[] ptmpOrg2 = new byte[307680];
        long new_height = 336;
        long new_width2 = 290;
        System.arraycopy(srcBuf, 0, ptmpOrg2, 0, NITGEN_VGA_FRAME_SIZE_08);
        long r = 0;
        while (true) {
            long j = 290;
            if (r >= new_height) {
                break;
            }
            long c = 0;
            while (c < new_width2) {
                int nindex = (int) ((r * j) + c);
                if (nBioBSPJNI.m_pZoomAry06H[nindex].i_orgr < 0 || nBioBSPJNI.m_pZoomAry06H[nindex].i_orgr > 479 || nBioBSPJNI.m_pZoomAry06H[nindex].i_orgc < 0) {
                    ptmpOrg = ptmpOrg2;
                    new_width = new_width2;
                } else if (nBioBSPJNI.m_pZoomAry06H[nindex].i_orgc > 639) {
                    ptmpOrg = ptmpOrg2;
                    new_width = new_width2;
                } else {
                    new_width = new_width2;
                    float nv1 = nBioBSPJNI.m_pZoomAry06H[nindex].nvI1;
                    float nv2 = nBioBSPJNI.m_pZoomAry06H[nindex].nvI2;
                    float nv3 = nBioBSPJNI.m_pZoomAry06H[nindex].nvI3;
                    ptmpOrg = ptmpOrg2;
                    ptmpImgBuf[(int) nBioBSPJNI.m_pZoomAry06H[nindex].dwLPosition] = (byte) ((int) ((((float) (ptmpOrg2[(int) nBioBSPJNI.m_pZoomAry06H[nindex].dwI1] & 255)) * nv1) + (((float) (ptmpOrg2[(int) nBioBSPJNI.m_pZoomAry06H[nindex].dwI2] & 255)) * nv2) + (((float) (ptmpOrg2[(int) nBioBSPJNI.m_pZoomAry06H[nindex].dwI3] & 255)) * nv3) + (((float) (ptmpOrg2[(int) nBioBSPJNI.m_pZoomAry06H[nindex].dwI4] & 255)) * nBioBSPJNI.m_pZoomAry06H[nindex].nvI4)));
                }
                c++;
                nBioBSPJNI = this;
                ptmpOrg2 = ptmpOrg;
                new_width2 = new_width;
                new_height = new_height;
                j = 290;
            }
            r++;
            nBioBSPJNI = this;
        }
        for (long r2 = 0; r2 < 292; r2++) {
            System.arraycopy(ptmpImgBuf, (int) ((((r2 + centerY) - 146) * 290) + (centerX - 124)), dstBuf, (int) (248 * r2), 248);
        }
        return true;
    }

    public void InitModifyTable08PIV() {
        int new_width;
        int new_height;
        int nhalf;
        this.m_pXtable = new int[193024];
        this.m_pYtable = new int[1504];
        this.m_pSrtable = new int[1424];
        this.m_pSctable = new int[193024];
        int new_height2 = 480;
        int new_width2 = 400;
        int nhalf2 = 400 / 2;
        for (int r = 0; r < new_height2; r++) {
            float tzoomInOutr = 0.969706f - (((float) r) * 1.97095E-5f);
            float tzoomInOutc = 0.620979f - (((float) r) * 4.33866E-5f);
            int c = 0;
            while (c < nhalf2) {
                float tr_orgr = ((float) r) / tzoomInOutr;
                float tr_orgc = ((float) c) / tzoomInOutc;
                int ti_orgr = (int) Math.floor((double) tr_orgr);
                int ti_orgc = (int) Math.floor((double) tr_orgc);
                float sr = tr_orgr - ((float) ti_orgr);
                float sc = tr_orgc - ((float) ti_orgc);
                int dwLPosition = (r * new_width2) + c + (new_width2 / 2);
                int dwRPosition = ((r * new_width2) - c) + (new_width2 / 2);
                if (ti_orgr < 0 || ti_orgr > 479 || ti_orgc < 0) {
                    nhalf = nhalf2;
                    new_height = new_height2;
                    new_width = new_width2;
                } else {
                    nhalf = nhalf2;
                    if (ti_orgc > 639 / 2) {
                        new_height = new_height2;
                        new_width = new_width2;
                    } else {
                        int[] iArr = this.m_pXtable;
                        new_height = new_height2;
                        iArr[dwLPosition] = ti_orgc + 320;
                        iArr[dwRPosition] = 320 - ti_orgc;
                        this.m_pYtable[r] = ti_orgr;
                        new_width = new_width2;
                        this.m_pSrtable[r] = (int) (((double) sr) * 1024.0d);
                        int[] iArr2 = this.m_pSctable;
                        iArr2[dwLPosition] = (int) (((double) sc) * 1024.0d);
                        iArr2[dwRPosition] = (int) (((double) sc) * 1024.0d);
                    }
                }
                c++;
                nhalf2 = nhalf;
                new_height2 = new_height;
                new_width2 = new_width;
            }
        }
    }

    private boolean CorrectImageCalc08PIV(byte[] srcBuf, int srcXn, int srcYn, byte[] dstBuf, int dstXn, int dstYn, int centerX, int centerY) {
        int srcxposL;
        int startx;
        int sc;
        int sr;
        int srcxposR;
        NBioBSPJNI nBioBSPJNI = this;
        byte[] bArr = srcBuf;
        if (!(bArr == null || dstBuf == null)) {
            int startx2 = centerX - (dstXn / 2);
            int starty = (centerY - (dstYn / 2)) - 12;
            int srcypos = 0;
            int srcweight1 = 0;
            int srcxposR2 = 0;
            int srcweight2 = SecBiometricLicenseManager.ERROR_INVALID_LICENSE;
            byte[] m_ptmpImgBuf = new byte[NITGEN_VGA_FRAME_SIZE_08];
            for (int i = 0; i < 192000; i++) {
                m_ptmpImgBuf[i] = -1;
            }
            int i2 = 0;
            while (i2 < 480) {
                int dstpos = i2 * 400;
                int[] iArr = nBioBSPJNI.m_pYtable;
                srcypos = iArr[i2] * 640;
                int srcyposp = (iArr[i2] + 1) * 640;
                int j = 0;
                while (j < srcweight2) {
                    int sc2 = nBioBSPJNI.m_pSctable[(i2 * 400) + j];
                    int sr2 = nBioBSPJNI.m_pSrtable[i2];
                    int[] iArr2 = nBioBSPJNI.m_pXtable;
                    int srcxposL2 = iArr2[(i2 * 400) + j];
                    int srcxposR3 = iArr2[(i2 * 400) + (400 - j)];
                    int srcweight12 = ((1024 - sc2) * (1024 - sr2)) >> 10;
                    int srcweight22 = ((1024 - sr2) * sc2) >> 10;
                    int srcweight3 = (sc2 * sr2) >> 10;
                    int srcweight4 = ((1024 - sc2) * sr2) >> 10;
                    if (srcypos == 0 && srcxposL2 == 0) {
                        startx = startx2;
                        srcxposL = srcxposL2;
                        sr = sr2;
                        sc = sc2;
                    } else {
                        sr = sr2;
                        sc = sc2;
                        if (srcypos + srcxposL2 > bArr.length) {
                            startx = startx2;
                            srcxposL = srcxposL2;
                        } else if (srcyposp + srcxposL2 >= bArr.length) {
                            startx = startx2;
                            srcxposL = srcxposL2;
                        } else {
                            startx = startx2;
                            srcxposL = srcxposL2;
                            m_ptmpImgBuf[dstpos + j] = (byte) ((((((bArr[srcypos + srcxposL2] & 255) * srcweight12) + ((bArr[(srcypos + srcxposL2) - 1] & 255) * srcweight22)) + ((bArr[(srcyposp + srcxposL2) - 1] & 255) * srcweight3)) + ((bArr[srcyposp + srcxposL2] & 255) * srcweight4)) >> 10);
                        }
                    }
                    if (srcypos + srcxposR3 >= bArr.length - 1) {
                        srcxposR = srcxposR3;
                    } else if (srcyposp + srcxposR3 >= bArr.length - 1) {
                        srcxposR = srcxposR3;
                    } else {
                        srcxposR = srcxposR3;
                        m_ptmpImgBuf[(400 - j) + dstpos] = (byte) ((((((bArr[srcypos + srcxposR3] & 255) * srcweight12) + ((bArr[(srcypos + srcxposR3) + 1] & 255) * srcweight22)) + ((bArr[(srcyposp + srcxposR3) + 1] & 255) * srcweight3)) + ((bArr[srcyposp + srcxposR3] & 255) * srcweight4)) >> 10);
                    }
                    j++;
                    nBioBSPJNI = this;
                    bArr = srcBuf;
                    srcxposR2 = srcxposR;
                    srcweight2 = srcweight2;
                    startx2 = startx;
                    srcweight1 = srcxposL;
                }
                i2++;
                nBioBSPJNI = this;
                bArr = srcBuf;
            }
            for (int i3 = 0; i3 < MODIFY_IMAGE_HEIGHT_08_PIV; i3++) {
                System.arraycopy(m_ptmpImgBuf, ((i3 + starty) * 400) + startx2, dstBuf, i3 * ERROR.NBioAPIERROR_DEVICE_ALREADY_OPENED, ERROR.NBioAPIERROR_DEVICE_ALREADY_OPENED);
            }
            return true;
        }
        return false;
    }

    /* loaded from: classes4.dex */
    public class NFIQInfo {
        public byte[] pRawImage = null;
        public int nImgHeight = 0;
        public int nImgWidth = 0;
        public int pNFIQ = 0;

        public NFIQInfo() {
            NBioBSPJNI.this = this$0;
        }
    }

    /* loaded from: classes4.dex */
    public class INIT_INFO_0 {
        public int MaxFingersForEnroll = 10;
        public int SamplesPerFinger = 2;
        public int DefaultTimeout = NBioBSPJNI.TRY_CONNECT_TIME;
        public int EnrollImageQuality = 50;
        public int VerifyImageQuality = 30;
        public int IdentifyImageQuality = 50;
        public int SecurityLevel = 5;

        public INIT_INFO_0() {
            NBioBSPJNI.this = this$0;
        }
    }

    /* loaded from: classes4.dex */
    public class CAPTURE_QUALITY_INFO {
        public int EnrollCoreQuality = 0;
        public int EnrollFeatureQuality = 0;
        public int VerifyCoreQuality = 0;
        public int VerifyFeatureQuality = 0;

        public CAPTURE_QUALITY_INFO() {
            NBioBSPJNI.this = this$0;
        }
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public class DEVICE_INFO {
        public int Brightness;
        public int Contrast;
        public int Gain;
        public int ImageHeight;
        public int ImageWidth;

        public DEVICE_INFO() {
            NBioBSPJNI.this = this$0;
        }
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public class DEVICE_INFO_EX {
        public int AutoOn;
        public int Brightness;
        public int Contrast;
        public String Description;
        public short DeviceID;
        public String Dll;
        public int Gain;
        public short Instance;
        public String Name;
        public short NameID;
        public int Reserved1;
        public int Reserved2;
        public int Reserved3;
        public int Reserved4;
        public int Reserved5;
        public int Reserved6;
        public int Reserved7;
        public int Reserved8;
        public String Sys;

        public DEVICE_INFO_EX() {
            NBioBSPJNI.this = this$0;
        }
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public class DEVICE_ENUM_INFO {
        public int DeviceCount;
        public DEVICE_INFO_EX[] DeviceInfo;

        public DEVICE_ENUM_INFO() {
            NBioBSPJNI.this = this$0;
        }

        public void NativeInit(int _DeviceNum) {
            this.DeviceCount = _DeviceNum;
            this.DeviceInfo = new DEVICE_INFO_EX[_DeviceNum];
            for (int i = 0; i < _DeviceNum; i++) {
                this.DeviceInfo[i] = new DEVICE_INFO_EX();
            }
        }
    }

    /* loaded from: classes4.dex */
    public class MATCH_OPTION {
        public byte[] NoMatchFinger = new byte[11];
        public int[] Reserved = new int[8];

        public MATCH_OPTION() {
            NBioBSPJNI.this = this$0;
            int n = this.NoMatchFinger.length;
            for (int i = 0; i < n; i++) {
                this.NoMatchFinger[i] = 0;
            }
            int n2 = this.Reserved.length;
            for (int i2 = 0; i2 < n2; i2++) {
                this.Reserved[i2] = 0;
            }
        }
    }

    /* loaded from: classes4.dex */
    public class FIR_HANDLE {
        private long Handle = 0;

        public FIR_HANDLE() {
            NBioBSPJNI.this = this$0;
        }

        public void dispose() {
            long j = this.Handle;
            if (j != 0) {
                NBioBSPJNI.NBioAPI_NativeFreeFIRHandle(j);
                this.Handle = 0;
            }
        }

        protected void finalize() throws Throwable {
            try {
                dispose();
            } finally {
                super.finalize();
            }
        }
    }

    /* loaded from: classes4.dex */
    public class FIR_HEADER {
        public short DataType;
        public short Purpose;
        public short Quality;
        public int Reserved;
        public short Version;

        public FIR_HEADER() {
            NBioBSPJNI.this = this$0;
        }
    }

    /* loaded from: classes4.dex */
    public class FIR {
        public byte[] Data;
        public int Format;
        public FIR_HEADER Header;

        public FIR() {
            NBioBSPJNI.this = this$0;
            this.Header = new FIR_HEADER();
        }
    }

    /* loaded from: classes4.dex */
    public class FIR_TEXTENCODE {
        public String TextFIR;

        public FIR_TEXTENCODE() {
            NBioBSPJNI.this = this$0;
        }
    }

    /* loaded from: classes4.dex */
    public class INPUT_FIR {
        private long FIRHandle;
        private int Form;
        private FIR FullFIR;
        private FIR_TEXTENCODE TextFIR;

        public INPUT_FIR() {
            NBioBSPJNI.this = this$0;
        }

        public void SetFIRHandle(FIR_HANDLE handle) {
            this.Form = 2;
            this.FIRHandle = handle.Handle;
        }

        public void SetFullFIR(FIR fullFIR) {
            this.Form = 3;
            this.FullFIR = fullFIR;
        }

        public void SetTextFIR(FIR_TEXTENCODE textFIR) {
            this.Form = 4;
            this.TextFIR = textFIR;
        }
    }

    /* loaded from: classes4.dex */
    public class FIR_PAYLOAD {
        private byte[] Data = null;
        private String Text = null;

        public FIR_PAYLOAD() {
            NBioBSPJNI.this = this$0;
        }

        public void SetData(byte[] data) {
            this.Data = data;
            this.Text = null;
        }

        public void SetText(String text) {
            this.Data = null;
            this.Text = text;
        }

        public byte[] GetData() {
            return this.Data;
        }

        public String GetText() {
            return this.Text;
        }
    }

    /* loaded from: classes4.dex */
    public class CAPTURED_DATA {
        private int deviceError = 0;
        private int imageQuality = 0;
        private Bitmap capturedImage = null;

        public CAPTURED_DATA() {
            NBioBSPJNI.this = this$0;
        }

        public void NativeSetData(int width, int height, byte[] rawBuf, int error, int quality) {
            if (rawBuf != null) {
                if (this.capturedImage == null) {
                    this.capturedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }
                ByteBuffer src = ByteBuffer.allocate(width * height * 4);
                byte[] srcbuf = src.array();
                for (int i = 0; i < rawBuf.length; i++) {
                    srcbuf[i * 4] = rawBuf[i];
                    srcbuf[(i * 4) + 1] = rawBuf[i];
                    srcbuf[(i * 4) + 2] = rawBuf[i];
                    srcbuf[(i * 4) + 3] = -1;
                }
                src.position(0);
                this.capturedImage.copyPixelsFromBuffer(src);
            }
            this.deviceError = error;
            this.imageQuality = quality;
        }

        public int getDeviceError() {
            return this.deviceError;
        }

        public int getImageQuality() {
            return this.imageQuality;
        }

        public Bitmap getImage() {
            return this.capturedImage;
        }
    }

    public int GetErrorCode() {
        return this.m_nErrCode;
    }

    public boolean IsErrorOccured() {
        return this.m_nErrCode != 0;
    }

    public void dispose() {
        HFDU06SP hfdu06sp = this.mHFDU06SP;
        if (hfdu06sp != null) {
            hfdu06sp.release();
            this.mHFDU06SP = null;
        }
        Activity activity = this.activity;
        if (activity != null) {
            activity.unregisterReceiver(this.mUsbReceiver);
        }
        if (s_useNative) {
            long j = this.m_hNBioBSP;
            if (j != 0) {
                NBioAPI_NativeTerminate(j);
                this.m_hNBioBSP = 0;
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public String GetVersion() {
        if (s_useNative) {
            NBioAPI_NativeGetVersion(this.m_hNBioBSP, this);
        }
        String[] array = this.m_bspVersion.split("\\.");
        return Integer.parseInt(array[0]) + "." + Integer.parseInt(array[1]) + ".0.2";
    }

    public int GetInitInfo(INIT_INFO_0 initInfo0) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeGetInitInfo(this.m_hNBioBSP, initInfo0);
        }
        return this.m_nErrCode;
    }

    public int SetInitInfo(INIT_INFO_0 initInfo0) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeSetInitInfo(this.m_hNBioBSP, initInfo0);
        }
        return this.m_nErrCode;
    }

    public int GetCaptureQualityInfo(CAPTURE_QUALITY_INFO captureQualityInfo) {
        this.m_nErrCode = 0;
        captureQualityInfo.EnrollCoreQuality = this.init_capture_quality_info.EnrollCoreQuality;
        captureQualityInfo.EnrollFeatureQuality = this.init_capture_quality_info.EnrollFeatureQuality;
        captureQualityInfo.VerifyCoreQuality = this.init_capture_quality_info.VerifyCoreQuality;
        captureQualityInfo.VerifyFeatureQuality = this.init_capture_quality_info.VerifyFeatureQuality;
        return this.m_nErrCode;
    }

    public int SetCaptureQualityInfo(CAPTURE_QUALITY_INFO captureQualityInfo) {
        this.m_nErrCode = 0;
        if (captureQualityInfo.EnrollCoreQuality < 0 || captureQualityInfo.EnrollCoreQuality > 100) {
            this.m_nErrCode = 18;
        } else if (captureQualityInfo.EnrollFeatureQuality < 0 || captureQualityInfo.EnrollFeatureQuality > 50) {
            this.m_nErrCode = 18;
        } else if (captureQualityInfo.VerifyCoreQuality < 0 || captureQualityInfo.VerifyCoreQuality > 100) {
            this.m_nErrCode = 19;
        } else if (captureQualityInfo.VerifyFeatureQuality < 0 || captureQualityInfo.VerifyFeatureQuality > 50) {
            this.m_nErrCode = 19;
        }
        if (this.m_nErrCode == 0) {
            this.init_capture_quality_info = captureQualityInfo;
        }
        return this.m_nErrCode;
    }

    @Deprecated
    public int EnumerateDevice(DEVICE_ENUM_INFO deviceInfo) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    public boolean OpenDevice() {
        this.isReceive = false;
        isSetSensorOption = false;
        isGetSensorSetting = false;
        isConnected = false;
        Map<String, UsbDevice> deviceList = this.mManager.getDeviceList();
        String str = TAG;
        Log.d(str, "deviceList.size():" + deviceList.size());
        if (deviceList.size() == 0) {
            CAPTURE_CALLBACK capture_callback = this.capture_callback;
            if (capture_callback != null) {
                capture_callback.OnDisConnected();
            }
            this.m_nErrCode = 336;
            return false;
        }
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (true) {
            if (!deviceIterator.hasNext()) {
                break;
            }
            UsbDevice device = deviceIterator.next();
            String str2 = TAG;
            Log.d(str2, "device.getVendorId():" + device.getVendorId());
            String str3 = TAG;
            Log.d(str3, "device.getProductId():" + device.getProductId());
            if (device.getVendorId() == VENDOR_ID_NITGEN) {
                if (device.getProductId() == PRODUCT_ID_NITGEN_06 || device.getProductId() == PRODUCT_ID_NITGEN_08 || device.getProductId() == 256) {
                    isConnected = true;
                    if (!this.isSyncOpenDevice || !this.mManager.hasPermission(device)) {
                        CURRENT_PRODUCT_ID = device.getProductId();
                        this.mPermissionIntent = PendingIntent.getBroadcast(this.activity, 0, new Intent(ACTION_USB_PERMISSION), 0);
                        this.mManager.requestPermission(device, this.mPermissionIntent);
                        if (this.isSyncOpenDevice) {
                            this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_PERMISSION;
                        }
                        return !this.isSyncOpenDevice;
                    }
                    UsbInterface usbInterface = findInterface(device);
                    Log.d(TAG, "setInterface");
                    if (setInterface(device, usbInterface)) {
                        boolean bInit = initDevice();
                        if (bInit) {
                            CURRENT_PRODUCT_ID = device.getProductId();
                        } else {
                            this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_INIT_FAIL;
                        }
                        return bInit;
                    }
                    Log.d(TAG, "setInterface Fail");
                    setInterface(null, null);
                    this.isReceive = false;
                    this.m_nErrCode = 257;
                    CAPTURE_CALLBACK capture_callback2 = this.capture_callback;
                    if (capture_callback2 != null) {
                        capture_callback2.OnDisConnected();
                    }
                    return false;
                } else if (device.getProductId() == PRODUCT_ID_NITGEN_06_SP) {
                    isConnected = true;
                    this.mHFDU06SP = new HFDU06SP(this.mManager, this.activity);
                    if (!this.isSyncOpenDevice || !this.mManager.hasPermission(device)) {
                        CURRENT_PRODUCT_ID = device.getProductId();
                        this.mHFDU06SP.requestPermission();
                        if (this.isSyncOpenDevice) {
                            this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_PERMISSION;
                        }
                        return !this.isSyncOpenDevice;
                    }
                    this.mHFDU06SP.setDevice(this.mHFDU06SP.getAlCameraDevice());
                    if (this.mHFDU06SP.openDevice()) {
                        CURRENT_PRODUCT_ID = device.getProductId();
                        byte[] m_pClsBuffer = this.mHFDU06SP.readSensorSettingValues();
                        if (m_pClsBuffer != null) {
                            this.m_CenterX = m_pClsBuffer[2] & 255;
                            this.m_CenterY = m_pClsBuffer[3] & 255;
                            int i = this.m_CenterX;
                            if (i < 124 || i > 166) {
                                this.m_CenterX = AlErrorCode.ERR_PERMISSION_DENIED;
                            }
                            int i2 = this.m_CenterY;
                            if (i2 < 146 || i2 > 188) {
                                this.m_CenterY = MXErrCode.ERR_NO_KEY_TEE;
                            }
                            InitModifyTable06H();
                            return true;
                        }
                        this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_INIT_FAIL;
                        return false;
                    }
                    Log.d(TAG, "setInterface Fail");
                    setInterface(null, null);
                    this.isReceive = false;
                    this.m_nErrCode = 257;
                    CAPTURE_CALLBACK capture_callback3 = this.capture_callback;
                    if (capture_callback3 != null) {
                        capture_callback3.OnDisConnected();
                    }
                    return false;
                }
            }
        }
        if (isConnected) {
            return false;
        }
        CAPTURE_CALLBACK capture_callback4 = this.capture_callback;
        if (capture_callback4 != null) {
            capture_callback4.OnDisConnected();
        }
        this.m_nErrCode = 257;
        return false;
    }

    @Deprecated
    public int OpenDevice(short nDeviceName, short nInstance) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int CloseDevice() {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int CloseDevice(short nOpenedDeviceID) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int CloseDevice(short nDeviceName, short nInstance) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int GetDeviceInfo(DEVICE_INFO deviceInfo) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int GetDeviceInfo(short nDeviceName, short nInstance, DEVICE_INFO deviceInfo) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int SetDeviceInfo(DEVICE_INFO deviceInfo) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    @Deprecated
    public int SetDeviceInfo(short nDeviceName, short nInstance, DEVICE_INFO deviceInfo) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    public int GetOpenedDeviceID() {
        int i = CURRENT_PRODUCT_ID;
        if (i == 256 || i == PRODUCT_ID_NITGEN_06_SP || i == PRODUCT_ID_NITGEN_06) {
            return 4;
        }
        if (i != PRODUCT_ID_NITGEN_08) {
            return 0;
        }
        return 8;
    }

    public int WriteDataToDevice(int nIndex, int nData) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            return NBioAPI_NativeWriteDataToDevice(this.m_hNBioBSP, nIndex, nData);
        }
        return this.m_nErrCode;
    }

    public int ReadDataFromDevice(int nIndex, Integer nData) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            return NBioAPI_NativeReadDataFromDevice(this.m_hNBioBSP, nIndex, nData);
        }
        return this.m_nErrCode;
    }

    public int GetFIRFromHandle(FIR_HANDLE hFIR, FIR FullFIR) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeGetFIRFromHandle(this.m_hNBioBSP, hFIR, FullFIR, 1);
        }
        return this.m_nErrCode;
    }

    public int GetFIRFromHandle(FIR_HANDLE hFIR, FIR FullFIR, int Format) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeGetFIRFromHandle(this.m_hNBioBSP, hFIR, FullFIR, Format);
        }
        return this.m_nErrCode;
    }

    public int GetTextFIRFromHandle(FIR_HANDLE hFIR, FIR_TEXTENCODE TextFIR) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeGetTextFIRFromHandle(this.m_hNBioBSP, hFIR, TextFIR, 1);
        }
        return this.m_nErrCode;
    }

    public int GetTextFIRFromHandle(FIR_HANDLE hFIR, FIR_TEXTENCODE TextFIR, int Format) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeGetTextFIRFromHandle(this.m_hNBioBSP, hFIR, TextFIR, Format);
        }
        return this.m_nErrCode;
    }

    private int quality_check(int width, int hegiht, byte[] InputImg, byte[] TemplateData) {
        return NBioAPI_NativeQualityCheck(width, hegiht, InputImg, TemplateData);
    }

    public int getNFIQInfoFromRaw(NFIQInfo info) {
        this.m_nErrCode = NBioAPI_GetNFIQInfoFromRaw(info);
        return this.m_nErrCode;
    }

    public int Capture(FIR_HANDLE hFIR) {
        return Capture(1, hFIR, TRY_CONNECT_TIME, new FIR_HANDLE(), new CAPTURED_DATA());
    }

    public byte[] CaptureWithType(int type) {
        FIR_HANDLE hFIR = new FIR_HANDLE();
        Capture(3, hFIR, TRY_CONNECT_TIME, new FIR_HANDLE(), new CAPTURED_DATA());
        INPUT_FIR inputFIR = new INPUT_FIR();
        Export exportEngine = new Export();
        inputFIR.SetFIRHandle(hFIR);
        exportEngine.getClass();
        Export.DATA exportData = new Export.DATA();
        exportEngine.ExportFIR(inputFIR, exportData, type);
        if (IsErrorOccured()) {
            return null;
        }
        return exportData.FingerData[0].Template[0].Data;
    }

    @Deprecated
    public int Capture(int purpose, FIR_HANDLE hFIR, int timeout, FIR_HANDLE hAudit, CAPTURED_DATA capturedData, CAPTURE_CALLBACK captureCallback, int nObjType, Object object) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x03bc, code lost:
        r12.m_nErrCode = 0;
        r12.isBreak = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x03c1, code lost:
        monitor-exit(r28);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0440, code lost:
        r9 = r12;
        r7 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x09b9, code lost:
        r9.m_nErrCode = 0;
        r9.isBreak = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x09bf, code lost:
        monitor-exit(r28);
     */
    /* JADX WARN: Removed duplicated region for block: B:237:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0772  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0a32  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0aa7  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0b2f A[LOOP:2: B:170:0x04f6->B:412:0x0b2f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0b0a A[EDGE_INSN: B:485:0x0b0a->B:404:0x0b0a ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public int Capture(int purpose, FIR_HANDLE hFIR, int timeout, FIR_HANDLE hAudit, CAPTURED_DATA capturedData) {
        int imageQuality;
        NBioBSPJNI nBioBSPJNI;
        CAPTURED_DATA captured_data;
        int imageQuality2;
        int imageQuality3;
        int radius;
        int nIOCtrlFailCnt;
        int imageQuality4;
        byte b;
        int i;
        int score;
        byte b2;
        int i2;
        int i3;
        int x_size;
        int y_size;
        CAPTURED_DATA captured_data2;
        Throwable th;
        int imageQuality5;
        StringBuilder sb;
        int score2;
        int settingScore;
        byte[] result_buffer;
        INIT_INFO_0 init_info_0;
        CAPTURED_DATA captured_data3;
        NBioBSPJNI nBioBSPJNI2;
        boolean z;
        int imageQuality6;
        FIR_HANDLE fir_handle;
        int imageQuality7;
        int settingScore2;
        CAPTURED_DATA captured_data4;
        Throwable th2;
        int imageQuality8;
        int score3;
        int settingScore3;
        NBioBSPJNI nBioBSPJNI3 = this;
        FIR_HANDLE fir_handle2 = hFIR;
        int i4 = timeout;
        CAPTURED_DATA captured_data5 = capturedData;
        if (GetOpenedDeviceID() == 0) {
            nBioBSPJNI3.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_OPENED;
            return nBioBSPJNI3.m_nErrCode;
        }
        boolean z2 = true;
        nBioBSPJNI3.isLedOn = true;
        nBioBSPJNI3.isBreak = false;
        long startTime = Calendar.getInstance().getTimeInMillis();
        INIT_INFO_0 init_info_02 = new INIT_INFO_0();
        nBioBSPJNI3.m_nErrCode = nBioBSPJNI3.GetInitInfo(init_info_02);
        if (purpose == 1) {
            imageQuality = init_info_02.VerifyImageQuality;
        } else if (purpose == 2) {
            imageQuality = init_info_02.IdentifyImageQuality;
        } else if (purpose != 3) {
            imageQuality = 0;
        } else {
            imageQuality = init_info_02.EnrollImageQuality;
        }
        int imageQuality9 = CURRENT_PRODUCT_ID;
        int i5 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
        if (imageQuality9 == PRODUCT_ID_NITGEN_06_SP) {
            while (true) {
                nBioBSPJNI3.mHFDU06SP.setLED(z2);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] pBuf = nBioBSPJNI3.mHFDU06SP.capture();
                if (pBuf != null) {
                    byte[] pBuf2 = new byte[72416];
                    init_info_0 = init_info_02;
                    CorrectImageCalc06H(pBuf, 640, 480, pBuf2, 248, 292, (long) nBioBSPJNI3.m_CenterX, (long) nBioBSPJNI3.m_CenterY);
                    int i6 = 0;
                    while (i6 < pBuf2.length) {
                        int pixel = (int) (((float) (pBuf2[i6] & 255)) * 1.3f);
                        pBuf2[i6] = (byte) (pixel > bRequest_GET_VISIBLE_IMAGE ? bRequest_GET_VISIBLE_IMAGE : pixel);
                        i6++;
                        pBuf2 = pBuf2;
                    }
                    nBioBSPJNI2 = this;
                    nBioBSPJNI2.rawData = pBuf2;
                    nBioBSPJNI2.mHFDU06SP.setLED(false);
                    if (!nBioBSPJNI2.mHFDU06SP.onTouch()) {
                        imageQuality7 = timeout;
                        captured_data3 = capturedData;
                        imageQuality6 = imageQuality;
                        settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                        z = true;
                        fir_handle = hFIR;
                        if (Calendar.getInstance().getTimeInMillis() - startTime >= ((long) imageQuality7)) {
                            nBioBSPJNI2.m_nErrCode = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                            nBioBSPJNI = nBioBSPJNI2;
                            captured_data = captured_data3;
                            break;
                        } else if (nBioBSPJNI2.capture_callback != null) {
                        }
                    } else {
                        Export export = new Export();
                        export.getClass();
                        Export.AUDIT importAudit = new Export.AUDIT();
                        importAudit.FingerNum = 1;
                        importAudit.SamplesPerFinger = 1;
                        importAudit.ImageWidth = 248;
                        importAudit.ImageHeight = MODIFY_IMAGE_HEIGHT_06_H;
                        importAudit.FingerData = new Export.FINGER_DATA[importAudit.FingerNum];
                        Export.FINGER_DATA[] finger_dataArr = importAudit.FingerData;
                        export.getClass();
                        finger_dataArr[0] = new Export.FINGER_DATA();
                        importAudit.FingerData[0].Template = new Export.TEMPLATE_DATA[importAudit.SamplesPerFinger];
                        Export.TEMPLATE_DATA[] template_dataArr = importAudit.FingerData[0].Template;
                        export.getClass();
                        template_dataArr[0] = new Export.TEMPLATE_DATA();
                        importAudit.FingerData[0].FingerID = 0;
                        importAudit.FingerData[0].Template[0].Data = new byte[nBioBSPJNI2.rawData.length];
                        Export.TEMPLATE_DATA template_data = importAudit.FingerData[0].Template[0];
                        byte[] bArr = nBioBSPJNI2.rawData;
                        template_data.Data = bArr;
                        if (nBioBSPJNI2.CheckImageQuality(248, MODIFY_IMAGE_HEIGHT_06_H, bArr) < imageQuality) {
                            captured_data3 = capturedData;
                            settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                            fir_handle = hFIR;
                            imageQuality6 = imageQuality;
                            z = true;
                            imageQuality7 = timeout;
                            if (Calendar.getInstance().getTimeInMillis() - startTime >= ((long) imageQuality7)) {
                                nBioBSPJNI2.m_nErrCode = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                nBioBSPJNI = nBioBSPJNI2;
                                captured_data = captured_data3;
                                break;
                            }
                        } else {
                            if (nBioBSPJNI2.capturedDatas == null) {
                                nBioBSPJNI2.capturedDatas = new CAPTURED_DATA();
                            }
                            CAPTURED_DATA captured_data6 = nBioBSPJNI2.capturedDatas;
                            synchronized (captured_data6) {
                                try {
                                    if (nBioBSPJNI2.isBreak) {
                                        captured_data3 = capturedData;
                                        imageQuality6 = imageQuality;
                                        break;
                                    }
                                    try {
                                        export.ImportAudit(importAudit, hAudit);
                                        INPUT_FIR inputFIR = new INPUT_FIR();
                                        inputFIR.SetFIRHandle(hAudit);
                                        fir_handle = hFIR;
                                        try {
                                            nBioBSPJNI2.m_nErrCode = nBioBSPJNI2.Process(inputFIR, fir_handle);
                                            if (nBioBSPJNI2.m_nErrCode != 6) {
                                                if (nBioBSPJNI2.m_nErrCode != 0) {
                                                    captured_data3 = capturedData;
                                                    imageQuality6 = imageQuality;
                                                    break;
                                                }
                                            } else {
                                                try {
                                                    nBioBSPJNI2.m_nErrCode = 0;
                                                } catch (Throwable th3) {
                                                    th2 = th3;
                                                    captured_data4 = captured_data6;
                                                    throw th2;
                                                }
                                            }
                                            export.getClass();
                                            Export.DATA exportData = new Export.DATA();
                                            inputFIR.SetFIRHandle(fir_handle);
                                            export.ExportFIR(inputFIR, exportData, 3);
                                            byte[] bArr2 = new byte[exportData.FingerData[0].Template[0].Data.length];
                                            byte[] byTemplate1 = exportData.FingerData[0].Template[0].Data;
                                            if (purpose == 3) {
                                                try {
                                                    if (byTemplate1[8] != 0) {
                                                        imageQuality8 = imageQuality;
                                                        try {
                                                            int score4 = NBioAPI_NativeFC_Core_Quality_from_Template(byTemplate1, 0, 0, 0);
                                                            int settingScore4 = nBioBSPJNI2.ConvertCoreQuality(nBioBSPJNI2.init_capture_quality_info.EnrollCoreQuality);
                                                            if (score4 > settingScore4) {
                                                                try {
                                                                    String str = TAG;
                                                                    try {
                                                                        StringBuilder sb2 = new StringBuilder();
                                                                        try {
                                                                            sb2.append("FAIL core score:settingScore =");
                                                                            sb2.append(score4);
                                                                            sb2.append(":");
                                                                            sb2.append(settingScore4);
                                                                            Log.d(str, sb2.toString());
                                                                            imageQuality7 = timeout;
                                                                            captured_data3 = capturedData;
                                                                            imageQuality6 = imageQuality8;
                                                                            settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                            z = true;
                                                                        } catch (Throwable th4) {
                                                                            th2 = th4;
                                                                            captured_data4 = captured_data6;
                                                                            throw th2;
                                                                        }
                                                                    } catch (Throwable th5) {
                                                                        th2 = th5;
                                                                        captured_data4 = captured_data6;
                                                                    }
                                                                } catch (Throwable th6) {
                                                                    th2 = th6;
                                                                    captured_data4 = captured_data6;
                                                                }
                                                            }
                                                        } catch (Throwable th7) {
                                                            th2 = th7;
                                                            captured_data4 = captured_data6;
                                                        }
                                                    } else {
                                                        imageQuality8 = imageQuality;
                                                    }
                                                    if (byTemplate1[13] < nBioBSPJNI2.init_capture_quality_info.EnrollFeatureQuality) {
                                                        Log.d(TAG, "FAIL feature count:settingCount =" + ((int) byTemplate1[13]) + ":" + nBioBSPJNI2.init_capture_quality_info.EnrollFeatureQuality);
                                                        imageQuality7 = timeout;
                                                        captured_data3 = capturedData;
                                                        imageQuality6 = imageQuality8;
                                                        settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                        z = true;
                                                    }
                                                } catch (Throwable th8) {
                                                    th2 = th8;
                                                    captured_data4 = captured_data6;
                                                }
                                            } else {
                                                imageQuality8 = imageQuality;
                                                try {
                                                    if (byTemplate1[8] != 0 && (score3 = NBioAPI_NativeFC_Core_Quality_from_Template(byTemplate1, 0, 0, 0)) > (settingScore3 = nBioBSPJNI2.ConvertCoreQuality(nBioBSPJNI2.init_capture_quality_info.VerifyCoreQuality))) {
                                                        Log.d(TAG, "FAIL core score:settingScore =" + score3 + ":" + settingScore3);
                                                        imageQuality7 = timeout;
                                                        captured_data3 = capturedData;
                                                        imageQuality6 = imageQuality8;
                                                        settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                        z = true;
                                                    }
                                                    if (byTemplate1[13] < nBioBSPJNI2.init_capture_quality_info.VerifyFeatureQuality) {
                                                        Log.d(TAG, "FAIL feature count:settingCount =" + ((int) byTemplate1[13]) + ":" + nBioBSPJNI2.init_capture_quality_info.VerifyFeatureQuality);
                                                        imageQuality7 = timeout;
                                                        captured_data3 = capturedData;
                                                        imageQuality6 = imageQuality8;
                                                        settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                        z = true;
                                                    }
                                                } catch (Throwable th9) {
                                                    th2 = th9;
                                                    captured_data4 = captured_data6;
                                                    throw th2;
                                                }
                                            }
                                            int quality = 0;
                                            if (!nBioBSPJNI2.isSkip) {
                                                quality = nBioBSPJNI2.quality_check(importAudit.ImageWidth, importAudit.ImageHeight, nBioBSPJNI2.rawData, byTemplate1);
                                            }
                                            Log.d(TAG, "errorcode : " + nBioBSPJNI2.m_nErrCode + "    quality : " + quality);
                                            captured_data3 = capturedData;
                                            if (captured_data3 != null) {
                                                try {
                                                    captured_data4 = captured_data6;
                                                    imageQuality6 = imageQuality8;
                                                    z = true;
                                                    try {
                                                        capturedData.NativeSetData(importAudit.ImageWidth, importAudit.ImageHeight, nBioBSPJNI2.rawData, nBioBSPJNI2.m_nErrCode, quality);
                                                        nBioBSPJNI2.capturedDatas = captured_data3;
                                                    } catch (Throwable th10) {
                                                        th2 = th10;
                                                        throw th2;
                                                    }
                                                } catch (Throwable th11) {
                                                    th2 = th11;
                                                    captured_data4 = captured_data6;
                                                    throw th2;
                                                }
                                            } else {
                                                captured_data4 = captured_data6;
                                                imageQuality6 = imageQuality8;
                                                z = true;
                                            }
                                            if (nBioBSPJNI2.capture_callback != null) {
                                                nBioBSPJNI2.activity.runOnUiThread(new Runnable() { // from class: com.nitgen.SDK.AndroidBSP.NBioBSPJNI.3
                                                    @Override // java.lang.Runnable
                                                    public void run() {
                                                        NBioBSPJNI nBioBSPJNI4 = NBioBSPJNI.this;
                                                        nBioBSPJNI4.m_nErrCode = nBioBSPJNI4.capture_callback.OnCaptured(NBioBSPJNI.this.capturedDatas);
                                                        if (NBioBSPJNI.this.m_nErrCode == 513) {
                                                            NBioBSPJNI.this.m_nErrCode = 0;
                                                            NBioBSPJNI.this.isBreak = true;
                                                        }
                                                    }
                                                });
                                            }
                                            if (quality >= imageQuality6) {
                                                break;
                                            }
                                            imageQuality7 = timeout;
                                            if (Calendar.getInstance().getTimeInMillis() - startTime >= ((long) imageQuality7)) {
                                                nBioBSPJNI2.m_nErrCode = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                nBioBSPJNI = nBioBSPJNI2;
                                                captured_data = captured_data3;
                                                break;
                                            }
                                            try {
                                                settingScore2 = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                            } catch (Throwable th12) {
                                                th2 = th12;
                                                throw th2;
                                            }
                                        } catch (Throwable th13) {
                                            th2 = th13;
                                            captured_data4 = captured_data6;
                                            throw th2;
                                        }
                                    } catch (Throwable th14) {
                                        th2 = th14;
                                    }
                                } catch (Throwable th15) {
                                    th2 = th15;
                                    captured_data4 = captured_data6;
                                }
                            }
                        }
                    }
                } else {
                    settingScore2 = i5;
                    imageQuality6 = imageQuality;
                    init_info_0 = init_info_02;
                    fir_handle = fir_handle2;
                    imageQuality7 = i4;
                    nBioBSPJNI2 = nBioBSPJNI3;
                    captured_data3 = captured_data5;
                    z = z2;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                if (nBioBSPJNI2.isBreak) {
                    nBioBSPJNI = nBioBSPJNI2;
                    captured_data = captured_data3;
                    break;
                }
                i5 = settingScore2;
                z2 = z;
                nBioBSPJNI3 = nBioBSPJNI2;
                captured_data5 = captured_data3;
                i4 = imageQuality7;
                fir_handle2 = fir_handle;
                imageQuality = imageQuality6;
                init_info_02 = init_info_0;
            }
        } else {
            int i7 = 515;
            int imageQuality10 = imageQuality;
            NBioBSPJNI nBioBSPJNI4 = nBioBSPJNI3;
            CAPTURED_DATA captured_data7 = captured_data5;
            int i8 = 1;
            int nIOCtrlFailCnt2 = 0;
            while (true) {
                nBioBSPJNI4.isSkip = false;
                if (nBioBSPJNI4.sendControlCommand(nBioBSPJNI4.nitgen_SENSOR_LED(nBioBSPJNI4.isLedOn)).isCtlSuccess()) {
                    ControlCommandVo commandVo = nBioBSPJNI4.sendControlCommand(nitgen_START_CONT_IMAGE_DATA());
                    if (!commandVo.isCtlSuccess() || !commandVo.isBlkSuccess()) {
                        radius = i7;
                        imageQuality3 = imageQuality10;
                        imageQuality2 = i8;
                        nBioBSPJNI = nBioBSPJNI4;
                        captured_data = captured_data7;
                        nIOCtrlFailCnt2++;
                        if (nIOCtrlFailCnt2 >= 5) {
                            nBioBSPJNI.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_IO_CONTROL_FAIL;
                            break;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                        if (nBioBSPJNI.isBreak) {
                            break;
                        }
                        i7 = radius;
                        captured_data7 = captured_data;
                        nBioBSPJNI4 = nBioBSPJNI;
                        i8 = imageQuality2;
                        imageQuality10 = imageQuality3;
                    } else {
                        byte[] temp_buffer = commandVo.getBufferBlk();
                        int i9 = CURRENT_PRODUCT_ID;
                        if (!(i9 == PRODUCT_ID_NITGEN_06 || i9 == 256)) {
                            byte[] pBuf3 = new byte[85800];
                            for (int i10 = 0; i10 < temp_buffer.length; i10++) {
                                int pixel2 = (int) (((float) (temp_buffer[i10] & 255)) * 1.2f);
                                int i11 = bRequest_GET_VISIBLE_IMAGE;
                                if (pixel2 <= bRequest_GET_VISIBLE_IMAGE) {
                                    i11 = pixel2;
                                }
                                temp_buffer[i10] = (byte) i11;
                            }
                            CorrectImageCalc08PIV(temp_buffer, 640, 480, pBuf3, ERROR.NBioAPIERROR_DEVICE_ALREADY_OPENED, MODIFY_IMAGE_HEIGHT_08_PIV, nBioBSPJNI4.m_CenterX, nBioBSPJNI4.m_CenterY);
                            nBioBSPJNI4.rawData = pBuf3;
                            imageQuality4 = imageQuality10;
                            nBioBSPJNI = nBioBSPJNI4;
                            nIOCtrlFailCnt = nIOCtrlFailCnt2;
                            i = 271;
                            b = 255;
                            if (!nBioBSPJNI.sendControlCommand(nitgen_STOP_CONT_IMAGE_DATA()).isCtlSuccess()) {
                                ControlCommandVo commandVo2 = nBioBSPJNI.sendControlCommand(nitgen_GET_TOUCH_STATUS());
                                if (commandVo2.isCtlSuccess()) {
                                    byte[] temp = commandVo2.getBuffer();
                                    if (b != (temp[0] & b)) {
                                        b2 = 1;
                                        nBioBSPJNI.isSkip = true;
                                    } else {
                                        b2 = 1;
                                    }
                                    if (b != (temp[0] & b)) {
                                        captured_data = capturedData;
                                        imageQuality3 = imageQuality4;
                                        radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                        if (Calendar.getInstance().getTimeInMillis() - startTime >= ((long) timeout)) {
                                            nBioBSPJNI.m_nErrCode = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                            break;
                                        }
                                        if (nBioBSPJNI.capture_callback != null) {
                                        }
                                        nIOCtrlFailCnt2 = nIOCtrlFailCnt;
                                        imageQuality2 = 1;
                                        if (nBioBSPJNI.isBreak) {
                                        }
                                    } else {
                                        Export export2 = new Export();
                                        export2.getClass();
                                        Export.AUDIT importAudit2 = new Export.AUDIT();
                                        importAudit2.FingerNum = b2;
                                        importAudit2.SamplesPerFinger = b2;
                                        int i12 = CURRENT_PRODUCT_ID;
                                        if (i12 != PRODUCT_ID_NITGEN_06) {
                                            i2 = 256;
                                            if (i12 != 256) {
                                                importAudit2.ImageWidth = ERROR.NBioAPIERROR_DEVICE_ALREADY_OPENED;
                                                importAudit2.ImageHeight = MODIFY_IMAGE_HEIGHT_08_PIV;
                                                importAudit2.FingerData = new Export.FINGER_DATA[importAudit2.FingerNum];
                                                Export.FINGER_DATA[] finger_dataArr2 = importAudit2.FingerData;
                                                export2.getClass();
                                                finger_dataArr2[0] = new Export.FINGER_DATA();
                                                importAudit2.FingerData[0].Template = new Export.TEMPLATE_DATA[importAudit2.SamplesPerFinger];
                                                Export.TEMPLATE_DATA[] template_dataArr2 = importAudit2.FingerData[0].Template;
                                                export2.getClass();
                                                template_dataArr2[0] = new Export.TEMPLATE_DATA();
                                                importAudit2.FingerData[0].FingerID = 0;
                                                importAudit2.FingerData[0].Template[0].Data = new byte[nBioBSPJNI.rawData.length];
                                                importAudit2.FingerData[0].Template[0].Data = nBioBSPJNI.rawData;
                                                i3 = CURRENT_PRODUCT_ID;
                                                if (i3 != PRODUCT_ID_NITGEN_06 || i3 == i2) {
                                                    y_size = MODIFY_IMAGE_HEIGHT_06_H;
                                                    x_size = 248;
                                                } else {
                                                    y_size = MODIFY_IMAGE_HEIGHT_08_PIV;
                                                    x_size = 260;
                                                }
                                                if (nBioBSPJNI.CheckImageQuality(x_size, y_size, nBioBSPJNI.rawData) >= imageQuality4) {
                                                    captured_data = capturedData;
                                                    imageQuality3 = imageQuality4;
                                                    radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                    if (Calendar.getInstance().getTimeInMillis() - startTime >= ((long) timeout)) {
                                                        nBioBSPJNI.m_nErrCode = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                        break;
                                                    }
                                                    nIOCtrlFailCnt2 = nIOCtrlFailCnt;
                                                    imageQuality2 = 1;
                                                    if (nBioBSPJNI.isBreak) {
                                                    }
                                                } else {
                                                    if (nBioBSPJNI.capturedDatas == null) {
                                                        nBioBSPJNI.capturedDatas = new CAPTURED_DATA();
                                                    }
                                                    CAPTURED_DATA captured_data8 = nBioBSPJNI.capturedDatas;
                                                    synchronized (captured_data8) {
                                                        try {
                                                            if (nBioBSPJNI.isBreak) {
                                                                captured_data = capturedData;
                                                                break;
                                                            }
                                                            export2.ImportAudit(importAudit2, hAudit);
                                                            INPUT_FIR inputFIR2 = new INPUT_FIR();
                                                            inputFIR2.SetFIRHandle(hAudit);
                                                            nBioBSPJNI.m_nErrCode = nBioBSPJNI.Process(inputFIR2, hFIR);
                                                            if (nBioBSPJNI.m_nErrCode != 6) {
                                                                if (nBioBSPJNI.m_nErrCode != 0) {
                                                                    captured_data = capturedData;
                                                                    break;
                                                                }
                                                            } else {
                                                                try {
                                                                    nBioBSPJNI.m_nErrCode = 0;
                                                                } catch (Throwable th16) {
                                                                    th = th16;
                                                                    captured_data2 = captured_data8;
                                                                    throw th;
                                                                }
                                                            }
                                                            export2.getClass();
                                                            Export.DATA exportData2 = new Export.DATA();
                                                            inputFIR2.SetFIRHandle(hFIR);
                                                            export2.ExportFIR(inputFIR2, exportData2, 3);
                                                            byte[] bArr3 = new byte[exportData2.FingerData[0].Template[0].Data.length];
                                                            byte[] byTemplate12 = exportData2.FingerData[0].Template[0].Data;
                                                            if (purpose == 3) {
                                                                try {
                                                                    if (byTemplate12[8] != 0) {
                                                                        int score5 = NBioAPI_NativeFC_Core_Quality_from_Template(byTemplate12, 0, 0, 0);
                                                                        int settingScore5 = nBioBSPJNI.ConvertCoreQuality(nBioBSPJNI.init_capture_quality_info.EnrollCoreQuality);
                                                                        if (score5 > settingScore5) {
                                                                            try {
                                                                                String str2 = TAG;
                                                                                try {
                                                                                    sb = new StringBuilder();
                                                                                } catch (Throwable th17) {
                                                                                    th = th17;
                                                                                    captured_data2 = captured_data8;
                                                                                }
                                                                                try {
                                                                                    sb.append("FAIL core score:settingScore =");
                                                                                    sb.append(score5);
                                                                                    sb.append(":");
                                                                                    sb.append(settingScore5);
                                                                                    Log.d(str2, sb.toString());
                                                                                    captured_data = capturedData;
                                                                                    imageQuality3 = imageQuality4;
                                                                                    score = nIOCtrlFailCnt;
                                                                                    radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                                    imageQuality2 = 1;
                                                                                    nIOCtrlFailCnt2 = score;
                                                                                    if (nBioBSPJNI.isBreak) {
                                                                                    }
                                                                                } catch (Throwable th18) {
                                                                                    th = th18;
                                                                                    captured_data2 = captured_data8;
                                                                                    throw th;
                                                                                }
                                                                            } catch (Throwable th19) {
                                                                                th = th19;
                                                                                captured_data2 = captured_data8;
                                                                            }
                                                                        } else {
                                                                            imageQuality5 = imageQuality4;
                                                                        }
                                                                    } else {
                                                                        imageQuality5 = imageQuality4;
                                                                    }
                                                                    if (byTemplate12[13] < nBioBSPJNI.init_capture_quality_info.EnrollFeatureQuality) {
                                                                        Log.d(TAG, "FAIL feature count:settingCount =" + ((int) byTemplate12[13]) + ":" + nBioBSPJNI.init_capture_quality_info.EnrollFeatureQuality);
                                                                        captured_data = capturedData;
                                                                        imageQuality3 = imageQuality5;
                                                                        score = nIOCtrlFailCnt;
                                                                        radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                        imageQuality2 = 1;
                                                                        nIOCtrlFailCnt2 = score;
                                                                        if (nBioBSPJNI.isBreak) {
                                                                        }
                                                                    }
                                                                } catch (Throwable th20) {
                                                                    th = th20;
                                                                    captured_data2 = captured_data8;
                                                                }
                                                            } else {
                                                                imageQuality5 = imageQuality4;
                                                                try {
                                                                    if (byTemplate12[8] != 0 && (score2 = NBioAPI_NativeFC_Core_Quality_from_Template(byTemplate12, 0, 0, 0)) > (settingScore = nBioBSPJNI.ConvertCoreQuality(nBioBSPJNI.init_capture_quality_info.VerifyCoreQuality))) {
                                                                        Log.d(TAG, "FAIL core score:settingScore =" + score2 + ":" + settingScore);
                                                                        captured_data = capturedData;
                                                                        imageQuality3 = imageQuality5;
                                                                        score = nIOCtrlFailCnt;
                                                                        radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                        imageQuality2 = 1;
                                                                        nIOCtrlFailCnt2 = score;
                                                                        if (nBioBSPJNI.isBreak) {
                                                                        }
                                                                    }
                                                                    if (byTemplate12[13] < nBioBSPJNI.init_capture_quality_info.VerifyFeatureQuality) {
                                                                        Log.d(TAG, "FAIL feature count:settingCount =" + ((int) byTemplate12[13]) + ":" + nBioBSPJNI.init_capture_quality_info.VerifyFeatureQuality);
                                                                        captured_data = capturedData;
                                                                        imageQuality3 = imageQuality5;
                                                                        score = nIOCtrlFailCnt;
                                                                        radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                        imageQuality2 = 1;
                                                                        nIOCtrlFailCnt2 = score;
                                                                        if (nBioBSPJNI.isBreak) {
                                                                        }
                                                                    }
                                                                } catch (Throwable th21) {
                                                                    th = th21;
                                                                    captured_data2 = captured_data8;
                                                                    throw th;
                                                                }
                                                            }
                                                            int quality2 = 0;
                                                            if (!nBioBSPJNI.isSkip) {
                                                                quality2 = nBioBSPJNI.quality_check(importAudit2.ImageWidth, importAudit2.ImageHeight, nBioBSPJNI.rawData, byTemplate12);
                                                            }
                                                            Log.d(TAG, "errorcode : " + nBioBSPJNI.m_nErrCode + "    quality : " + quality2);
                                                            captured_data = capturedData;
                                                            if (captured_data != null) {
                                                                try {
                                                                    captured_data2 = captured_data8;
                                                                    imageQuality3 = imageQuality5;
                                                                    try {
                                                                        capturedData.NativeSetData(importAudit2.ImageWidth, importAudit2.ImageHeight, nBioBSPJNI.rawData, nBioBSPJNI.m_nErrCode, quality2);
                                                                        nBioBSPJNI.capturedDatas = captured_data;
                                                                    } catch (Throwable th22) {
                                                                        th = th22;
                                                                        throw th;
                                                                    }
                                                                } catch (Throwable th23) {
                                                                    th = th23;
                                                                    captured_data2 = captured_data8;
                                                                    throw th;
                                                                }
                                                            } else {
                                                                captured_data2 = captured_data8;
                                                                imageQuality3 = imageQuality5;
                                                            }
                                                            if (nBioBSPJNI.capture_callback != null) {
                                                                nBioBSPJNI.activity.runOnUiThread(new Runnable() { // from class: com.nitgen.SDK.AndroidBSP.NBioBSPJNI.4
                                                                    @Override // java.lang.Runnable
                                                                    public void run() {
                                                                        NBioBSPJNI nBioBSPJNI5 = NBioBSPJNI.this;
                                                                        nBioBSPJNI5.m_nErrCode = nBioBSPJNI5.capture_callback.OnCaptured(NBioBSPJNI.this.capturedDatas);
                                                                        if (NBioBSPJNI.this.m_nErrCode == 513) {
                                                                            NBioBSPJNI.this.m_nErrCode = 0;
                                                                            NBioBSPJNI.this.isBreak = true;
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                            if (quality2 >= imageQuality3) {
                                                                break;
                                                            }
                                                            if (Calendar.getInstance().getTimeInMillis() - startTime >= ((long) timeout)) {
                                                                nBioBSPJNI.m_nErrCode = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                break;
                                                            }
                                                            try {
                                                                nIOCtrlFailCnt2 = nIOCtrlFailCnt;
                                                                radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                                                imageQuality2 = 1;
                                                                if (nBioBSPJNI.isBreak) {
                                                                }
                                                            } catch (Throwable th24) {
                                                                th = th24;
                                                                throw th;
                                                            }
                                                        } catch (Throwable th25) {
                                                            th = th25;
                                                            captured_data2 = captured_data8;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            i2 = 256;
                                        }
                                        importAudit2.ImageWidth = 248;
                                        importAudit2.ImageHeight = MODIFY_IMAGE_HEIGHT_06_H;
                                        importAudit2.FingerData = new Export.FINGER_DATA[importAudit2.FingerNum];
                                        Export.FINGER_DATA[] finger_dataArr22 = importAudit2.FingerData;
                                        export2.getClass();
                                        finger_dataArr22[0] = new Export.FINGER_DATA();
                                        importAudit2.FingerData[0].Template = new Export.TEMPLATE_DATA[importAudit2.SamplesPerFinger];
                                        Export.TEMPLATE_DATA[] template_dataArr22 = importAudit2.FingerData[0].Template;
                                        export2.getClass();
                                        template_dataArr22[0] = new Export.TEMPLATE_DATA();
                                        importAudit2.FingerData[0].FingerID = 0;
                                        importAudit2.FingerData[0].Template[0].Data = new byte[nBioBSPJNI.rawData.length];
                                        importAudit2.FingerData[0].Template[0].Data = nBioBSPJNI.rawData;
                                        i3 = CURRENT_PRODUCT_ID;
                                        if (i3 != PRODUCT_ID_NITGEN_06) {
                                        }
                                        y_size = MODIFY_IMAGE_HEIGHT_06_H;
                                        x_size = 248;
                                        if (nBioBSPJNI.CheckImageQuality(x_size, y_size, nBioBSPJNI.rawData) >= imageQuality4) {
                                        }
                                    }
                                } else {
                                    captured_data = capturedData;
                                    imageQuality3 = imageQuality4;
                                    radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                    nIOCtrlFailCnt2 = nIOCtrlFailCnt + 1;
                                    if (nIOCtrlFailCnt2 >= 5) {
                                        nBioBSPJNI.m_nErrCode = i;
                                        break;
                                    }
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e4) {
                                        e4.printStackTrace();
                                    }
                                    imageQuality2 = 1;
                                    if (nBioBSPJNI.isBreak) {
                                    }
                                }
                            } else {
                                captured_data = capturedData;
                                imageQuality3 = imageQuality4;
                                score = nIOCtrlFailCnt;
                                radius = ERROR.NBioAPIERROR_CAPTURE_TIMEOUT;
                                if (score >= 5) {
                                    int i13 = score + 1;
                                    nBioBSPJNI.m_nErrCode = i;
                                    break;
                                }
                                imageQuality2 = 1;
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e5) {
                                    e5.printStackTrace();
                                }
                                nIOCtrlFailCnt2 = score;
                                if (nBioBSPJNI.isBreak) {
                                }
                            }
                        }
                        byte[] mdfBuf = new byte[temp_buffer.length];
                        byte[] pBuf4 = new byte[NITGEN_VGA_FRAME_SIZE_08];
                        int nDepth = 368000;
                        while (nDepth < 400000 && ((temp_buffer[nDepth] & 255) != 255 || (temp_buffer[nDepth + 1] & 255) != 0 || (temp_buffer[nDepth + 2] & 255) != 0 || (temp_buffer[nDepth + 3] & 255) != 157 || (temp_buffer[nDepth + 4] & 255) != 128 || (temp_buffer[nDepth + 5] & 255) != 16)) {
                            nDepth++;
                        }
                        if (nDepth >= 384000) {
                            byte[] result_buffer2 = new byte[temp_buffer.length - (nDepth - 384000)];
                            System.arraycopy(temp_buffer, nDepth - 384000, result_buffer2, 0, temp_buffer.length - (nDepth - 384000));
                            result_buffer = result_buffer2;
                        } else {
                            byte[] result_buffer3 = new byte[temp_buffer.length];
                            System.arraycopy(temp_buffer, 0, result_buffer3, 0, temp_buffer.length);
                            result_buffer = result_buffer3;
                        }
                        boolean bmdfRet = nBioBSPJNI4.ImgModify06H(result_buffer, mdfBuf);
                        if (nDepth >= 400000 || !bmdfRet) {
                            for (int i14 = 0; i14 < 384000; i14++) {
                                mdfBuf[i14] = 0;
                            }
                        }
                        for (int i15 = 0; i15 < 480; i15++) {
                            System.arraycopy(mdfBuf, (i15 * SecBiometricLicenseManager.LICENSE_RESULT_TYPE_ACTIVATION) + AlErrorCode.ERR_COMMAND, pBuf4, i15 * 640, 640);
                        }
                        for (int i16 = 0; i16 < pBuf4.length; i16++) {
                            pBuf4[i16] = (byte) (~pBuf4[i16]);
                        }
                        byte[] pBuf22 = new byte[72416];
                        imageQuality4 = imageQuality10;
                        nIOCtrlFailCnt = nIOCtrlFailCnt2;
                        i = 271;
                        CorrectImageCalc06H(pBuf4, 640, 480, pBuf22, 248, 292, (long) nBioBSPJNI4.m_CenterX, (long) nBioBSPJNI4.m_CenterY);
                        int i17 = 0;
                        while (i17 < pBuf22.length) {
                            int pixel3 = (int) (((float) (pBuf22[i17] & 255)) * 1.3f);
                            pBuf22[i17] = (byte) (pixel3 > bRequest_GET_VISIBLE_IMAGE ? bRequest_GET_VISIBLE_IMAGE : pixel3);
                            i17++;
                            pBuf22 = pBuf22;
                        }
                        b = 255;
                        nBioBSPJNI = this;
                        nBioBSPJNI.rawData = pBuf22;
                        if (!nBioBSPJNI.sendControlCommand(nitgen_STOP_CONT_IMAGE_DATA()).isCtlSuccess()) {
                        }
                    }
                } else {
                    radius = i7;
                    imageQuality3 = imageQuality10;
                    imageQuality2 = i8;
                    nBioBSPJNI = nBioBSPJNI4;
                    captured_data = captured_data7;
                    nIOCtrlFailCnt2++;
                    if (nIOCtrlFailCnt2 >= 5) {
                        nBioBSPJNI.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_IO_CONTROL_FAIL;
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e6) {
                        e6.printStackTrace();
                    }
                    if (nBioBSPJNI.isBreak) {
                    }
                }
            }
        }
        if (captured_data != null && !nBioBSPJNI.isBreak) {
            capturedData.NativeSetData(0, 0, null, nBioBSPJNI.m_nErrCode, 0);
            nBioBSPJNI.capturedDatas = captured_data;
            if (nBioBSPJNI.capture_callback != null) {
                nBioBSPJNI.activity.runOnUiThread(new Runnable() { // from class: com.nitgen.SDK.AndroidBSP.NBioBSPJNI.5
                    @Override // java.lang.Runnable
                    public void run() {
                        NBioBSPJNI.this.capture_callback.OnCaptured(NBioBSPJNI.this.capturedDatas);
                    }
                });
            }
        }
        return nBioBSPJNI.m_nErrCode;
    }

    void saveImage(byte[] image, String fileName) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NBioBSP";
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(new File(path + "/" + fileName + ".raw"));
            fos.write(image);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int Process(INPUT_FIR capturedFIR, FIR_HANDLE hProcessedFIR) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeProcess(this.m_hNBioBSP, capturedFIR, hProcessedFIR);
        }
        return this.m_nErrCode;
    }

    public int CreateTemplate(INPUT_FIR capturedFIR, INPUT_FIR storedFIR, FIR_HANDLE hNewFIR, FIR_PAYLOAD payload) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeCreateTemplate(this.m_hNBioBSP, capturedFIR, storedFIR, hNewFIR, payload);
        }
        return this.m_nErrCode;
    }

    @Deprecated
    public int Verify(INPUT_FIR storedFIR, Boolean bResult, FIR_PAYLOAD payload) {
        return Verify(storedFIR, bResult, payload, -1, null, null, null);
    }

    @Deprecated
    public int Verify(INPUT_FIR storedFIR, Boolean bResult, FIR_PAYLOAD payload, int nTimeout, FIR_HANDLE hAudit, CAPTURED_DATA capturedData, CAPTURE_CALLBACK captureCallback) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeVerify(this.m_hNBioBSP, storedFIR, bResult, payload, nTimeout, hAudit, capturedData, captureCallback);
        }
        return this.m_nErrCode;
    }

    public int VerifyMatch(INPUT_FIR capturedFIR, INPUT_FIR storedFIR, Boolean bResult, FIR_PAYLOAD payload) {
        return VerifyMatch(capturedFIR, storedFIR, bResult, payload, null);
    }

    public int VerifyMatch(INPUT_FIR capturedFIR, INPUT_FIR storedFIR, Boolean bResult, FIR_PAYLOAD payload, MATCH_OPTION matchOption) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeVerifyMatchEx(this.m_hNBioBSP, capturedFIR, storedFIR, bResult, payload, matchOption);
        }
        return this.m_nErrCode;
    }

    @Deprecated
    public int CheckFinger(Boolean bFingerExist) {
        this.m_nErrCode = 255;
        return this.m_nErrCode;
    }

    public int CheckFinger(byte[] bFingerExist) {
        this.m_nErrCode = 1;
        if (bFingerExist == null) {
            bFingerExist = new byte[]{0};
        }
        if (CURRENT_PRODUCT_ID == PRODUCT_ID_NITGEN_06_SP) {
            if (this.mHFDU06SP.onTouch()) {
                bFingerExist[0] = 1;
            } else {
                bFingerExist[0] = 0;
            }
            this.m_nErrCode = 0;
        } else {
            ControlCommandVo commandVo = sendControlCommand(nitgen_GET_TOUCH_STATUS());
            if (!commandVo.isCtlSuccess()) {
                this.m_nErrCode = ERROR.NBioAPIERROR_DEVICE_NOT_OPENED;
            } else if (255 == (commandVo.getBuffer()[0] & 255)) {
                this.m_nErrCode = 0;
                bFingerExist[0] = 1;
            } else {
                this.m_nErrCode = 0;
            }
        }
        return this.m_nErrCode;
    }

    /* loaded from: classes4.dex */
    public class Export {
        public Export() {
            NBioBSPJNI.this = this$0;
        }

        /* loaded from: classes4.dex */
        public class TEMPLATE_DATA {
            public byte[] Data;

            public TEMPLATE_DATA() {
                Export.this = this$1;
            }

            public void NativeSetData(byte[] data) {
                this.Data = new byte[data.length];
                this.Data = data;
            }
        }

        /* loaded from: classes4.dex */
        public class FINGER_DATA {
            public byte FingerID;
            public TEMPLATE_DATA[] Template;

            public FINGER_DATA() {
                Export.this = this$1;
            }
        }

        /* loaded from: classes4.dex */
        public class DATA {
            public byte DefaultFingerID;
            public byte EncryptType;
            public FINGER_DATA[] FingerData;
            public byte FingerNum;
            public byte SamplesPerFinger;

            public DATA() {
                Export.this = this$1;
            }

            public void NativeInit(byte _EncryptType, byte _FingerNum, byte _DefaultFingerID, byte _SamplesPerFinger) {
                this.EncryptType = _EncryptType;
                this.FingerNum = _FingerNum;
                this.DefaultFingerID = _DefaultFingerID;
                this.SamplesPerFinger = _SamplesPerFinger;
                this.FingerData = new FINGER_DATA[this.FingerNum];
                for (int i = 0; i < this.FingerNum; i++) {
                    this.FingerData[i] = new FINGER_DATA();
                    this.FingerData[i].Template = new TEMPLATE_DATA[this.SamplesPerFinger];
                    for (int j = 0; j < this.SamplesPerFinger; j++) {
                        this.FingerData[i].Template[j] = new TEMPLATE_DATA();
                    }
                }
            }

            public void NativeSetData(int IdxFinger, int IdxSample, byte _FingerID, byte[] data) {
                FINGER_DATA[] finger_dataArr = this.FingerData;
                finger_dataArr[IdxFinger].FingerID = _FingerID;
                finger_dataArr[IdxFinger].Template[IdxSample].Data = new byte[data.length];
                this.FingerData[IdxFinger].Template[IdxSample].Data = data;
            }
        }

        /* loaded from: classes4.dex */
        public class AUDIT {
            public FINGER_DATA[] FingerData;
            public byte FingerNum;
            public int ImageHeight;
            public int ImageWidth;
            public byte SamplesPerFinger;

            public AUDIT() {
                Export.this = this$1;
            }

            public void NativeInit(byte _FingerNum, byte _SamplerPerFinger, int _ImageWidth, int _ImageHeight) {
                this.FingerNum = _FingerNum;
                this.SamplesPerFinger = _SamplerPerFinger;
                this.ImageWidth = _ImageWidth;
                this.ImageHeight = _ImageHeight;
                this.FingerData = new FINGER_DATA[this.FingerNum];
                for (int i = 0; i < this.FingerNum; i++) {
                    this.FingerData[i] = new FINGER_DATA();
                    this.FingerData[i].Template = new TEMPLATE_DATA[this.SamplesPerFinger];
                    for (int j = 0; j < this.SamplesPerFinger; j++) {
                        this.FingerData[i].Template[j] = new TEMPLATE_DATA();
                    }
                }
            }

            public void NativeSetData(int IdxFinger, int IdxSample, byte _FingerID, byte[] data) {
                FINGER_DATA[] finger_dataArr = this.FingerData;
                finger_dataArr[IdxFinger].FingerID = _FingerID;
                finger_dataArr[IdxFinger].Template[IdxSample].Data = new byte[data.length];
                this.FingerData[IdxFinger].Template[IdxSample].Data = data;
            }
        }

        public int ExportFIR(INPUT_FIR inputFIR, DATA exportData, int exportType) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeNBioBSPToFDx(nBioBSPJNI.m_hNBioBSP, inputFIR, exportData, exportType);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ImportFIR(byte[] Template, int nLen, int type, FIR_HANDLE hFIR) {
            return ImportFIR(Template, nLen, type, 1, hFIR);
        }

        public int ImportFIR(byte[] Template, int nLen, int type, int purpose, FIR_HANDLE hFIR) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeFDxToNBioBSP(nBioBSPJNI.m_hNBioBSP, Template, nLen, type, purpose, hFIR);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ImportFIR(DATA exportData, FIR_HANDLE hFIR) {
            return ImportFIR(exportData, 1, 2, hFIR);
        }

        public int ImportFIR(DATA exportData, int purpose, int dataType, FIR_HANDLE hFIR) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeImportDataToNBioBSP(nBioBSPJNI.m_hNBioBSP, exportData, purpose, dataType, hFIR);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ExportAudit(INPUT_FIR inputFIR, AUDIT exportAudit) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeNBioBSPToImage(nBioBSPJNI.m_hNBioBSP, inputFIR, exportAudit);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ImportAudit(AUDIT exportAudit, FIR_HANDLE hFIR) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeImageToNBioBSP(nBioBSPJNI.m_hNBioBSP, exportAudit, hFIR);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ImportBioAPIOpaqueToFIR(byte[] bioAPIOpaqueData, FIR_HANDLE hFIR) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeImportBioAPIOpaqueToFIRHandle(nBioBSPJNI.m_hNBioBSP, bioAPIOpaqueData, hFIR);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ConvertRawToWsq(byte[] rawImage, int nWidth, int nHeight, TEMPLATE_DATA wsqImage, float fQuality) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI.this.m_nErrCode = NBioBSPJNI.NBioAPI_NativeRawToWSQ(rawImage, nWidth, nHeight, wsqImage, fQuality);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ConvertWsqToRaw(byte[] wsqImage, int nWsqLen, AUDIT exportAudit) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI.this.m_nErrCode = NBioBSPJNI.NBioAPI_NativeWSQToRaw(wsqImage, nWsqLen, exportAudit);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ResizeRaw(byte[] srcAudit, AUDIT dstAudit, int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI.this.m_nErrCode = NBioBSPJNI.NBioAPI_NativeResizeRaw(srcAudit, dstAudit, srcWidth, srcHeight, dstWidth, dstHeight);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }
    }

    /* loaded from: classes4.dex */
    public class IndexSearch {

        /* loaded from: classes4.dex */
        public class INIT_INFO {
            public int PresearchRate = 12;
            public int researved0;
            public int researved1;
            public int researved2;
            public int researved3;
            public int researved4;
            public int researved5;

            public INIT_INFO() {
                IndexSearch.this = this$1;
            }
        }

        /* loaded from: classes4.dex */
        public class SAMPLE_INFO {
            public int ID;
            public byte[] SampleCount = new byte[11];

            public SAMPLE_INFO() {
                IndexSearch.this = this$1;
            }
        }

        /* loaded from: classes4.dex */
        public class FP_INFO {
            public byte FingerID;
            public int ID;
            public byte SampleNumber;

            public FP_INFO() {
                IndexSearch.this = this$1;
            }
        }

        public IndexSearch() {
            NBioBSPJNI.this = this$0;
            this$0.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                this$0.m_nErrCode = NBioBSPJNI.NBioAPI_NativeInitIndexSearchEngine(this$0.m_hNBioBSP);
            }
        }

        public void dispose() {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI.NBioAPI_NativeTerminateIndexSearchEngine(NBioBSPJNI.this.m_hNBioBSP);
            }
        }

        protected void finalize() throws Throwable {
            dispose();
        }

        public int GetInitInfo(INIT_INFO initInfo) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeGetIndexSearchInitInfo(nBioBSPJNI.m_hNBioBSP, initInfo);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int SetInitInfo(INIT_INFO initInfo) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeSetIndexSearchInitInfo(nBioBSPJNI.m_hNBioBSP, initInfo);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int AddFIR(INPUT_FIR inputFIR, int userID, SAMPLE_INFO sampleInfo) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeAddFIRToIndexSearchDB(nBioBSPJNI.m_hNBioBSP, inputFIR, userID, sampleInfo);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int Identify(INPUT_FIR inputFIR, int secuLevel, FP_INFO fpInfo) {
            return Identify(inputFIR, secuLevel, fpInfo, -1);
        }

        public int Identify(INPUT_FIR inputFIR, int secuLevel, FP_INFO fpInfo, int nTimeOut) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeIdentifyDataFromIndexSearchDB(nBioBSPJNI.m_hNBioBSP, inputFIR, secuLevel, fpInfo, nTimeOut);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int RemoveData(FP_INFO fpInfo) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeRemoveDataFromIndexSearchDB(nBioBSPJNI.m_hNBioBSP, fpInfo);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int RemoveUser(int userID) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeRemoveUserFromIndexSearchDB(nBioBSPJNI.m_hNBioBSP, userID);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int ClearDB() {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeClearIndexSearchDB(nBioBSPJNI.m_hNBioBSP);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int SaveDB(String szFilepath) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeSaveIndexSearchDBToFile(nBioBSPJNI.m_hNBioBSP, szFilepath);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int LoadDB(String szFilepath) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeLoadIndexSearchDBFromFile(nBioBSPJNI.m_hNBioBSP, szFilepath);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int GetDBCount(Integer nCount) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeGetDataCountFromIndexSearchDB(nBioBSPJNI.m_hNBioBSP, nCount);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }

        public int CheckDataExist(FP_INFO fpInfo, Boolean bExist) {
            NBioBSPJNI.this.m_nErrCode = 1;
            if (NBioBSPJNI.s_useNative) {
                NBioBSPJNI nBioBSPJNI = NBioBSPJNI.this;
                nBioBSPJNI.m_nErrCode = NBioBSPJNI.NBioAPI_NativeCheckDataExistFromIndexSearchDB(nBioBSPJNI.m_hNBioBSP, fpInfo, bExist);
            }
            return NBioBSPJNI.this.m_nErrCode;
        }
    }

    private byte ByteCheckSum(byte[] data, int nStart, int nEnd) {
        byte bySum = 0;
        for (int i = nStart; i < nEnd; i++) {
            bySum = (byte) (data[i] + bySum);
        }
        return bySum;
    }

    /* loaded from: classes4.dex */
    public class NIMPORTRAW {
        public byte[] Data;
        public byte FingerID;
        public short ImgHeight;
        public short ImgWidth;

        public NIMPORTRAW() {
            NBioBSPJNI.this = this$0;
        }
    }

    /* loaded from: classes4.dex */
    public class NIMPORTRAWSET {
        public byte Count;
        public NIMPORTRAW[] RawData;

        public NIMPORTRAWSET() {
            NBioBSPJNI.this = this$0;
        }

        public void NativeInit(byte _Count) {
            this.Count = _Count;
            this.RawData = new NIMPORTRAW[this.Count];
            for (byte i = 0; i < this.Count; i = (byte) (i + 1)) {
                this.RawData[i] = new NIMPORTRAW();
            }
        }

        public void NativeSetData(int _Index, byte _FingerID, short _ImgW, short _ImgH, byte[] _Data) {
            NIMPORTRAW[] nimportrawArr = this.RawData;
            nimportrawArr[_Index].FingerID = _FingerID;
            nimportrawArr[_Index].ImgWidth = _ImgW;
            nimportrawArr[_Index].ImgHeight = _ImgH;
            nimportrawArr[_Index].Data = new byte[_Data.length];
            nimportrawArr[_Index].Data = _Data;
        }
    }

    /* loaded from: classes4.dex */
    public class ISOBUFFER {
        public byte[] Data;

        public ISOBUFFER() {
            NBioBSPJNI.this = this$0;
        }

        public void NativeInit(byte[] _Data) {
            this.Data = new byte[_Data.length];
            this.Data = _Data;
        }
    }

    public int ExportRawToISOV1(Export.AUDIT AuditData, ISOBUFFER ISOBuf, boolean bIsRollDevice, byte CompressMode) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeExportRawToISOV1(AuditData, ISOBuf, bIsRollDevice, CompressMode);
        }
        return this.m_nErrCode;
    }

    public int ExportRawToISOV2(byte FingerID, short ImgW, short ImgH, byte[] RawBuf, ISOBUFFER ISOBuf, boolean bIsRollDevice, byte CompressMode) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeExportRawToISOV2(FingerID, ImgW, ImgH, RawBuf, ISOBuf, bIsRollDevice, CompressMode);
        }
        return this.m_nErrCode;
    }

    public int ImportISOToRaw(ISOBUFFER ISOBuf, NIMPORTRAWSET RawSet) {
        this.m_nErrCode = 1;
        if (s_useNative) {
            this.m_nErrCode = NBioAPI_NativeImportISOToRaw(ISOBuf, RawSet);
        }
        return this.m_nErrCode;
    }
}
