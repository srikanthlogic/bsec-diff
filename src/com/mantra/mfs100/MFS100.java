package com.mantra.mfs100;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Base64;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;
import com.facebook.common.statfs.StatFsHelper;
import com.facebook.imagepipeline.common.RotationOptions;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
/* loaded from: classes3.dex */
public class MFS100 {
    private String UnLockKey;
    private String certificate;
    private Context context;
    private DeviceInfo deviceInfo;
    private UsbManager mManager;
    private MFS100Event mfs100Event;
    private final String ACTION_USB_PERMISSION = "com.mantra.mfs100.USB_PERMISSION";
    private String ErrorString = "";
    private long gbldevice = 0;
    private boolean isHasPermissionDenied = true;
    private boolean isInitSuccess = false;
    private int fd = 0;
    private boolean isCaptureRunning = false;
    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() { // from class: com.mantra.mfs100.MFS100.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                MFS100.this.FindDeviceAndRequestPermission();
            } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra("device");
                int productid = device.getProductId();
                int vendorid = device.getVendorId();
                if (vendorid != 1204 && vendorid != 11279) {
                    return;
                }
                if (productid == 34323 || productid == 4101) {
                    MFS100.this.isInitSuccess = false;
                    MFS100.this.fd = 0;
                    if (MFS100.this.mfs100Event != null) {
                        MFS100.this.mfs100Event.OnDeviceDetached();
                    }
                }
            } else if ("com.mantra.mfs100.USB_PERMISSION".equals(action)) {
                synchronized (this) {
                    UsbDevice device2 = (UsbDevice) intent.getParcelableExtra("device");
                    int productid2 = device2.getProductId();
                    int vendorid2 = device2.getVendorId();
                    if (intent.getBooleanExtra("permission", false)) {
                        if ((vendorid2 == 1204 || vendorid2 == 11279) && (productid2 == 34323 || productid2 == 4101)) {
                            if (productid2 == 34323) {
                                MFS100.this.isInitSuccess = false;
                            }
                            MFS100.this.isHasPermissionDenied = false;
                            try {
                                UsbDeviceConnection deviceConn = MFS100.this.mManager.openDevice(device2);
                                MFS100.this.fd = deviceConn.getFileDescriptor();
                            } catch (Exception e) {
                                Log.e("Error", "Error while get fd", e);
                                MFS100.this.fd = 0;
                            }
                            if (MFS100.this.mfs100Event != null) {
                                MFS100.this.mfs100Event.OnDeviceAttached(vendorid2, productid2, true);
                            }
                        }
                    } else if ((vendorid2 == 1204 || vendorid2 == 11279) && (productid2 == 34323 || productid2 == 4101)) {
                        if (productid2 == 34323) {
                            try {
                                MFS100.this.isInitSuccess = false;
                            } catch (Exception ex) {
                                Log.e("mUsbReceiver.A_U_P.Ex", ex.toString());
                            }
                        }
                        MFS100.this.isHasPermissionDenied = true;
                        if (MFS100.this.mfs100Event != null) {
                            MFS100.this.mfs100Event.OnDeviceAttached(vendorid2, productid2, false);
                        }
                    }
                }
            }
        }
    };

    public MFS100(MFS100Event event) {
        this.mfs100Event = null;
        this.deviceInfo = null;
        this.certificate = "";
        this.UnLockKey = "";
        SetLastError(0);
        this.mfs100Event = event;
        this.UnLockKey = "";
        this.deviceInfo = new DeviceInfo();
        this.certificate = "";
    }

    public boolean SetApplicationContext(Context context) {
        try {
            SetLastError(0);
            this.context = context;
            this.mManager = (UsbManager) context.getSystemService("usb");
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.mantra.mfs100.USB_PERMISSION");
            filter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            this.context.registerReceiver(this.mUsbReceiver, filter);
            FindDeviceAndRequestPermission();
            return true;
        } catch (Exception ex) {
            Log.d("SetApplicationCon.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return false;
        }
    }

    public String GetSDKVersion() {
        try {
            return String.valueOf(mfs100api.MFS100GetSDKVersion());
        } catch (Exception ex) {
            Log.d("GetSDKVersion.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return "";
        }
    }

    public boolean IsConnected() {
        SetLastError(0);
        try {
        } catch (Exception ex) {
            Log.d("IsConnected.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }
        if (mfs100api.MFS100DeviceConnected(this.fd) == 0) {
            return true;
        }
        SetLastError(mfs100api.MFS100_E_NO_DEVICE);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public int LoadFirmware() {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        ?? r0 = this.isHasPermissionDenied;
        if (r0 != 0) {
            FindDeviceAndRequestPermission();
            return -1001;
        }
        try {
            r0 = 0;
            r0 = 0;
            try {
                r0 = (int) mfs100api.MFS100LoadFirmware(this.fd);
            } catch (Exception ex) {
                Log.d("LoadFirmware.Ex", ex.toString());
                r0 = -1000;
            }
            return r0;
        } finally {
            int ret = r0 == true ? 1 : 0;
            int ret2 = r0 == true ? 1 : 0;
            int ret3 = r0 == true ? 1 : 0;
            int ret4 = r0 == true ? 1 : 0;
            SetLastError(ret);
            this.isHasPermissionDenied = true;
        }
    }

    public int Init(String Key) {
        if (Key == null || Key.length() <= 0) {
            this.UnLockKey = "";
        } else {
            this.UnLockKey = Key;
        }
        return Init();
    }

    public int Init(byte[] Key) {
        if (Key == null || Key.length <= 0) {
            this.UnLockKey = "";
        } else {
            this.UnLockKey = Base64.encodeToString(Key, 0);
        }
        return Init();
    }

    public int Init() {
        int ret;
        byte[] serial;
        long devicevar;
        int ret2;
        if (!IsConnected()) {
            this.isInitSuccess = false;
            return mfs100api.MFS100_E_NO_DEVICE;
        } else if (this.isInitSuccess) {
            return 0;
        } else {
            boolean z = this.isHasPermissionDenied;
            if (z) {
                this.isInitSuccess = false;
                FindDeviceAndRequestPermission();
                return -1001;
            }
            try {
                z = false;
                this.deviceInfo = new DeviceInfo();
                this.certificate = "";
                try {
                    serial = new byte[10];
                    if (this.UnLockKey == null || this.UnLockKey.length() <= 0) {
                        devicevar = mfs100api.MFS100Init(serial, 80, 16384, this.fd);
                    } else {
                        try {
                            devicevar = mfs100api.MFS100InitWithKey(serial, 80, 16384, Base64.decode(this.UnLockKey, 0), this.fd);
                        } catch (Exception e) {
                            this.isInitSuccess = false;
                            int ret3 = z ? 1 : 0;
                            return mfs100api.MFS100_E_INVALID_KEY;
                        }
                    }
                    ret2 = mfs100api.MFS100LastErrorCode();
                } catch (Exception e2) {
                    this.isInitSuccess = false;
                    ret = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                }
                if (ret2 != 0) {
                    this.isInitSuccess = false;
                    mfs100api.CheckError(ret2);
                    return ret2;
                }
                this.gbldevice = devicevar;
                if (this.gbldevice == 0) {
                    this.isInitSuccess = false;
                    SetLastError(mfs100api.MFS100_E_MEMORY);
                    int ret4 = mfs100api.MFS100_E_MEMORY;
                    return ret4;
                }
                String str = new String(serial, "ASCII").trim().replaceAll("\\W", "").trim();
                this.deviceInfo._Height = mfs100api.MFS100GetHeight(this.gbldevice);
                this.deviceInfo._Width = mfs100api.MFS100GetWidth(this.gbldevice);
                this.deviceInfo._Make = mfs100api.MFS100GetMake(this.fd);
                this.deviceInfo._Model = mfs100api.MFS100GetModel(this.fd);
                this.deviceInfo._SerialNo = str;
                this.certificate = "STQC,PIV";
                this.isInitSuccess = true;
                ret = 0;
                return ret;
            } finally {
                int ret5 = z ? 1 : 0;
                int ret6 = z ? 1 : 0;
                SetLastError(ret5);
            }
        }
    }

    public DeviceInfo GetDeviceInfo() {
        return this.deviceInfo;
    }

    public String GetCertification() {
        DeviceInfo deviceInfo = this.deviceInfo;
        if (deviceInfo == null || deviceInfo.Width() == 0 || this.gbldevice == 0) {
            return "";
        }
        return this.certificate;
    }

    public int UnInit() {
        int ret = 0;
        this.isInitSuccess = false;
        try {
            try {
            } finally {
                SetLastError(0);
            }
        } catch (Exception e) {
            ret = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
        if (this.gbldevice == 0) {
            return mfs100api.MFS100_E_NOT_INITIALIZED;
        }
        if (this.isCaptureRunning) {
            mfs100api.MFS100StopAutoCapture();
            try {
                Thread.sleep(1000);
            } catch (Exception e2) {
            }
        }
        mfs100api.MFS100Uninit(this.gbldevice);
        this.deviceInfo = null;
        this.certificate = "";
        this.gbldevice = 0;
        this.isCaptureRunning = false;
        return ret;
    }

    public int AutoCapture(FingerData fingerData, int timeout, boolean detectFinger) {
        int _detectFinger;
        if (detectFinger) {
            _detectFinger = 1;
        } else {
            _detectFinger = 0;
        }
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        DeviceInfo deviceInfo = this.deviceInfo;
        if (deviceInfo == null || deviceInfo.Width() == 0 || this.gbldevice == 0) {
            return mfs100api.MFS100_E_NOT_INITIALIZED;
        }
        try {
            SetLastError(0);
            int width = this.deviceInfo.Width();
            int height = this.deviceInfo.Height();
            int rawOrgSize = width * height;
            byte[] _RawData = new byte[rawOrgSize + 2];
            int fiOrgSize = (width * height) + 1078;
            byte[] _FingerImage = new byte[fiOrgSize + 2];
            byte[] _ISOTemplate = new byte[2100];
            int[] ISO_TemplateLength = new int[1];
            int[] qty = new int[1];
            int[] nfiq = new int[1];
            this.isCaptureRunning = true;
            int ret = mfs100api.MFS100AutoCapture(this.gbldevice, timeout, _RawData, _FingerImage, _ISOTemplate, ISO_TemplateLength, qty, nfiq, _detectFinger);
            if (ret != 0) {
                if (ret == -1135) {
                    ret = mfs100api.MFS100_E_CAPTURING_STOPPED;
                }
                mfs100api.CheckError(ret);
                return ret;
            }
            fingerData._Quality = qty[0];
            fingerData._Nfiq = nfiq[0];
            fingerData._RawData = new byte[rawOrgSize];
            System.arraycopy(_RawData, 0, fingerData._RawData, 0, rawOrgSize);
            fingerData._FingerImage = new byte[fiOrgSize];
            System.arraycopy(_FingerImage, 0, fingerData._FingerImage, 0, fiOrgSize);
            fingerData._ISOTemplate = new byte[ISO_TemplateLength[0]];
            System.arraycopy(_ISOTemplate, 0, fingerData._ISOTemplate, 0, ISO_TemplateLength[0]);
            fingerData._WSQCompressRatio = 10.0d;
            new DecimalFormat("#.00");
            fingerData._Bpp = 8;
            fingerData._GrayScale = (int) Math.pow(2.0d, (double) 8);
            fingerData._Resolution = getDouble(500).doubleValue();
            double inWidth = (double) (((float) this.deviceInfo.Width()) / ((float) 500));
            fingerData._InWidth = getDouble(inWidth).doubleValue();
            double inHeight = (double) (((float) this.deviceInfo.Height()) / ((float) 500));
            fingerData._InHeight = getDouble(inHeight).doubleValue();
            fingerData._InArea = getDouble(((double) ((float) inWidth)) * inHeight).doubleValue();
            fingerData._WSQInfo = "WSQ encoding";
            return ret;
        } catch (Exception ex) {
            Log.e("AutoCapture.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        } finally {
            this.isCaptureRunning = false;
        }
    }

    private Double getDouble(int value) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        df.applyPattern("#.00");
        return Double.valueOf(df.format((long) value));
    }

    private Double getDouble(double value) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        df.applyPattern("#.00");
        return Double.valueOf(df.format(value));
    }

    public int StopAutoCapture() {
        try {
            if (!IsConnected()) {
                return mfs100api.MFS100_E_NO_DEVICE;
            }
            if (!(this.deviceInfo == null || this.deviceInfo.Width() == 0 || this.gbldevice == 0)) {
                SetLastError(0);
                mfs100api.MFS100StopAutoCapture();
                return 0;
            }
            return mfs100api.MFS100_E_NOT_INITIALIZED;
        } catch (Exception e) {
            Log.e("StopAutoCapture.Ex", e.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public int ExtractISOTemplate(byte[] _RawData, byte[] _Data) {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        if (_RawData == null || _RawData.length != this.deviceInfo.Width() * this.deviceInfo.Height() || _Data == null || _Data.length < 2000) {
            return mfs100api.MFS100_E_NULLPARAM;
        }
        try {
            int dataLen = mfs100api.MFS100ExtractISOTemplate(this.gbldevice, _RawData, _Data);
            if (dataLen <= 0) {
                SetLastError(dataLen);
                return dataLen;
            }
            SetLastError(0);
            return dataLen;
        } catch (Exception ex) {
            Log.e("ExtractISOTemplate.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public int ExtractANSITemplate(byte[] _RawData, byte[] _Data) {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        if (_Data == null || _Data.length < 2000 || _RawData == null || _RawData.length != this.deviceInfo.Width() * this.deviceInfo.Height()) {
            return mfs100api.MFS100_E_NULLPARAM;
        }
        try {
            int dataLen = mfs100api.MFS100ExtractANSITemplate(this.gbldevice, _RawData, _Data);
            if (dataLen <= 0) {
                SetLastError(dataLen);
                return dataLen;
            }
            SetLastError(0);
            return dataLen;
        } catch (Exception ex) {
            Log.e("ExtractANSITemplate.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public int ExtractISOImage(byte[] _RawData, byte[] _Data, int isoType) {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        if (_Data == null || _Data.length < (this.deviceInfo.Width() * this.deviceInfo.Height()) + 1078 || _RawData == null || _RawData.length != this.deviceInfo.Width() * this.deviceInfo.Height()) {
            return mfs100api.MFS100_E_NULLPARAM;
        }
        if (isoType != 1 && isoType != 2) {
            return mfs100api.MFS100_E_INVALID_TYPE;
        }
        try {
            int dataLen = mfs100api.MFS100ExtractISOImage(this.gbldevice, _RawData, _Data, (byte) 0, isoType);
            if (dataLen <= 0) {
                SetLastError(dataLen);
                return dataLen;
            }
            SetLastError(0);
            return dataLen;
        } catch (Exception ex) {
            Log.e("ExtractISOImage.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public int ExtractWSQImage(byte[] _RawData, byte[] _Data) {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        if (_Data == null || _Data.length < (this.deviceInfo.Width() * this.deviceInfo.Height()) + 1078 || _RawData == null || _RawData.length != this.deviceInfo.Width() * this.deviceInfo.Height()) {
            return mfs100api.MFS100_E_NULLPARAM;
        }
        try {
            int dataLen = mfs100api.MFS100ExtractWSQImage(this.gbldevice, _RawData, _Data, 1.0f);
            if (dataLen <= 0) {
                SetLastError(dataLen);
                return dataLen;
            }
            SetLastError(0);
            return dataLen;
        } catch (Exception ex) {
            Log.e("ExtractWSQImage.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public int MatchISO(byte[] probeISOTemplate, byte[] galleryISOTemplate) {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        SetLastError(0);
        if (probeISOTemplate == null || probeISOTemplate.length == 0 || galleryISOTemplate == null || galleryISOTemplate.length == 0) {
            SetLastError(mfs100api.MFS100_E_INVALIDPARAM);
        }
        try {
            int ret = mfs100api.MFS100MatchISO(this.gbldevice, probeISOTemplate, galleryISOTemplate, RotationOptions.ROTATE_180);
            if (ret >= 0) {
                SetLastError(0);
            }
            SetLastError(ret);
            return ret;
        } catch (Exception ex) {
            Log.e("MatchISO.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public boolean MatchMinutiaeISO(byte[] minutiae, byte[] galleryISOTemplate, int[] retValue) {
        int ret;
        if (!IsConnected()) {
            retValue[0] = -1307;
            return false;
        }
        SetLastError(0);
        byte[] probeISOTemplate = MTS(minutiae);
        if (probeISOTemplate == null || probeISOTemplate.length == 0 || galleryISOTemplate == null || galleryISOTemplate.length == 0) {
            SetLastError(mfs100api.MFS100_E_INVALIDPARAM);
        }
        try {
            ret = mfs100api.MFS100MatchISO(this.gbldevice, galleryISOTemplate, probeISOTemplate, RotationOptions.ROTATE_180);
            if (ret >= 0) {
                SetLastError(0);
            }
            SetLastError(ret);
        } catch (Exception ex) {
            Log.e("MatchISO.Ex", ex.toString());
            ret = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }
        if (ret >= 200) {
            retValue[0] = 0;
            return true;
        }
        retValue[0] = ret;
        return false;
    }

    public int MatchANSI(byte[] probeANSITemplate, byte[] galleryANSITemplate) {
        if (!IsConnected()) {
            return mfs100api.MFS100_E_NO_DEVICE;
        }
        SetLastError(0);
        if (probeANSITemplate == null || probeANSITemplate.length == 0 || galleryANSITemplate == null || galleryANSITemplate.length == 0) {
            SetLastError(mfs100api.MFS100_E_INVALIDPARAM);
        }
        try {
            int ret = mfs100api.MFS100MatchANSI(this.gbldevice, probeANSITemplate, galleryANSITemplate, RotationOptions.ROTATE_180);
            if (ret >= 0) {
                SetLastError(0);
            }
            SetLastError(ret);
            return ret;
        } catch (Exception ex) {
            Log.e("MatchANSI.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    public boolean RotateImage(int Direction) {
        SetLastError(0);
        try {
            mfs100api.MFS100RotateImage(this.gbldevice, Direction);
            SetLastError(0);
            return true;
        } catch (Exception ex) {
            Log.e("RotateImage.Ex", ex.toString());
            SetLastError(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            return false;
        }
    }

    public void Dispose() {
        try {
            if (this.mUsbReceiver != null) {
                this.context.unregisterReceiver(this.mUsbReceiver);
            }
        } catch (Exception e) {
        }
    }

    public String GetLastError() {
        return this.ErrorString;
    }

    public String GetErrorMsg(int errorCode) {
        return mfs100api.GetErrorMsg(errorCode);
    }

    private int SetLastError(int errorCode) {
        this.ErrorString = "";
        if (errorCode >= 0) {
            return 0;
        }
        this.ErrorString = mfs100api.GetErrorMsg(errorCode);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void FindDeviceAndRequestPermission() {
        try {
            UsbDevice device = GetDevice();
            if (device != null) {
                this.mManager.requestPermission(device, PendingIntent.getBroadcast(this.context, 0, new Intent("com.mantra.mfs100.USB_PERMISSION"), 0));
            }
        } catch (Exception ex) {
            Log.e("FindDeviceAndReq.Ex", ex.toString());
            UsbDevice device2 = GetDevice();
            if (device2 != null) {
                Intent intent = new Intent("com.mantra.mfs100.USB_PERMISSION");
                intent.putExtra("permission", true);
                intent.putExtra("device", device2);
            }
        }
    }

    private UsbDevice GetDevice() {
        try {
            for (UsbDevice usbDevice : this.mManager.getDeviceList().values()) {
                int productid = usbDevice.getProductId();
                int vendorid = usbDevice.getVendorId();
                if (vendorid == 1204 || vendorid == 11279) {
                    if (productid == 34323 || productid == 4101) {
                        return usbDevice;
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            Log.e("GetDevice.Ex", ex.toString());
            return null;
        }
    }

    private byte[] MTS(byte[] minIssue) {
        Exception e;
        int xCord;
        int temp1;
        boolean xIsOK;
        byte[] minIssue2 = minIssue;
        try {
            ByteArrayOutputStream lstTruncated_Data = new ByteArrayOutputStream();
            int DPI = 500;
            boolean isX = true;
            String cordinate = "";
            int byteCount = 0;
            boolean xIsOK2 = false;
            boolean yIsOK = false;
            int xx = 1;
            for (int i = 1; xx < minIssue2.length + i; i = 1) {
                if (xx % 5 != 0) {
                    byteCount++;
                    if (minIssue2[xx - 1] < 0) {
                        temp1 = Math.abs(256 - Math.abs((int) minIssue2[xx - 1]));
                    } else {
                        temp1 = minIssue2[xx - 1];
                    }
                    cordinate = cordinate + padLeft(Integer.toBinaryString(temp1 == 1 ? 1 : 0), 8);
                    if (byteCount % 2 == 0) {
                        if (isX) {
                            int xCord2 = (int) Math.round((((double) Integer.parseInt(cordinate.substring(2, cordinate.length()), 2)) / 2540.0d) * ((double) DPI));
                            if (xCord2 <= 42 || xCord2 >= 358) {
                                xIsOK = xIsOK2;
                            } else {
                                xIsOK = true;
                            }
                            byte[] xBytes = IntToBytes(Integer.parseInt(cordinate.substring(0, 2) + padLeft(Integer.toBinaryString(xCord2), 14), 2), 2);
                            minIssue2[xx + -2] = xBytes[0];
                            minIssue2[xx + -1] = xBytes[1];
                            isX = false;
                            xCord = DPI;
                            xIsOK2 = xIsOK;
                        } else {
                            int yCord = (int) Math.round((((double) Integer.parseInt(cordinate.substring(2, cordinate.length()), 2)) / 2540.0d) * ((double) DPI));
                            if (yCord > 0 && yCord < 330) {
                                yIsOK = true;
                            }
                            String yCordBin = padLeft(Integer.toBinaryString(yCord), 14);
                            StringBuilder sb = new StringBuilder();
                            xCord = DPI;
                            sb.append(cordinate.substring(0, 2));
                            sb.append(yCordBin);
                            byte[] yBytes = IntToBytes(Integer.parseInt(sb.toString(), 2), 2);
                            minIssue2[xx - 2] = yBytes[0];
                            minIssue2[xx - 1] = yBytes[1];
                            isX = true;
                            xIsOK2 = xIsOK2;
                        }
                        if (xIsOK2 && yIsOK) {
                            lstTruncated_Data.write(minIssue2[xx - 4]);
                            lstTruncated_Data.write(minIssue2[xx - 3]);
                            lstTruncated_Data.write(minIssue2[xx - 2]);
                            lstTruncated_Data.write(minIssue2[xx - 1]);
                            lstTruncated_Data.write(minIssue2[xx]);
                        }
                        cordinate = "";
                        byteCount = 0;
                    } else {
                        xCord = DPI;
                    }
                } else {
                    xCord = DPI;
                    cordinate = "";
                    byteCount = 0;
                    padLeft(Integer.toBinaryString(minIssue2[xx - 1]), 8);
                    yIsOK = false;
                    isX = true;
                    xIsOK2 = false;
                }
                xx++;
                DPI = xCord;
            }
            byte[] finalTruncatedData = lstTruncated_Data.toByteArray();
            if (finalTruncatedData.length >= 60) {
                minIssue2 = finalTruncatedData;
            }
            try {
                ByteArrayOutputStream lstHeader = new ByteArrayOutputStream();
                lstHeader.write(70);
                lstHeader.write(77);
                lstHeader.write(82);
                lstHeader.write(0);
                lstHeader.write(32);
                lstHeader.write(50);
                lstHeader.write(48);
                lstHeader.write(0);
                int len = 30 + minIssue2.length + (minIssue2.length / 5);
                lstHeader.write(IntToBytes(len, 4));
                lstHeader.write(0);
                lstHeader.write(0);
                lstHeader.write(IntToBytes(StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB, 2));
                lstHeader.write(IntToBytes(500, 2));
                lstHeader.write(IntToBytes(197, 2));
                lstHeader.write(IntToBytes(197, 2));
                lstHeader.write(1);
                lstHeader.write(0);
                lstHeader.write(0);
                lstHeader.write(0);
                lstHeader.write(40);
                lstHeader.write(IntToBytes(minIssue2.length / 5, 1));
                byte[] data = new byte[minIssue2.length + (minIssue2.length / 5)];
                int i2 = 0;
                int iteration = 0;
                while (i2 < minIssue2.length) {
                    System.arraycopy(minIssue2, i2, data, i2 + iteration, 5);
                    iteration++;
                    i2 += 5;
                    finalTruncatedData = finalTruncatedData;
                    len = len;
                }
                lstHeader.write(data);
                lstHeader.write(0);
                lstHeader.write(0);
                return lstHeader.toByteArray();
            } catch (Exception e2) {
                e = e2;
                Log.e("MTS", "Error", e);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private byte[] IntToBytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[i] = (byte) ((value >> (((b.length - 1) - i) * 8)) & 255);
        }
        return b;
    }

    private String padLeft(String s, int n) {
        return String.format("%0$" + n + "s", s).replace(' ', '0');
    }
}
