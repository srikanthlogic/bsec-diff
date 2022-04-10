package com.nitgen.SDK.AndroidBSP;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.alcorlink.camera.AKAVImage;
import com.alcorlink.camera.AKXU;
import com.alcorlink.camera.AlCameraDevice;
import com.alcorlink.camera.AlDevManager;
import com.alcorlink.camera.AlErrorCode;
import com.alcorlink.camera.AlFrame;
import com.alcorlink.camera.CameraException;
import com.alcorlink.camera.StreamConfig;
import com.facebook.imageutils.JfifUtil;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;
import org.zz.protocol.MXErrCode;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class HFDU06SP {
    private static final int SIZE_OF_VERSION = 16;
    private AlCameraDevice alCameraDevice;
    private Context context;
    private AKAVImage mAKImage;
    private AlDevManager mDevMan;
    private AlFrame mImageBuffer;
    private StreamConfig mPreviewCfg;
    private AKXU mXu;
    byte[] temp;
    String TAG = HFDU06SP.class.getSimpleName();
    private final int DEFAULT_IMAGE_FORMAT = 4;
    private final int DELAY_MILLI_S = 100;
    private int TIMEOUT_MICROSECOND = 100;
    final int CSTM_UC_SPI_SECTOR_VALUE = 13;
    final int CSTM_UC_SPI_SECTOR_ID = 12;
    final int CSTM_UC_SPI_SECTOR_SENSOR_BAK = 14;
    final byte RegA_BANK_SELECT = 3;
    final byte GROUP_REG_B = 1;
    final byte GROUP_REG_C = 2;
    final byte ADDRESS_BRIGHTNESS = -98;
    byte LED = 0;
    byte[] BRIGHTNESS_TABLE = {118, 115, 112, 108, 104, 100, 96, 92, 88, 84, 80, 76, 72, 68, SignedBytes.MAX_POWER_OF_TWO, 60, 56, 52, 48, 44, 40, 36, 32, Ascii.FS, Ascii.CAN, Ascii.DC4, Ascii.DLE, Ascii.FF, 8, 4, 0, -4, -8, -12, -16, -20, -24, -28, -32, -36, -37, -38, -39, -40, -41, -42, -43, -44, -45, -46, -47, -48, -49, -50, -51, -52, -53, -54, -55, -56, -57, -58, -59, -60, -61, -62, -63, -64, -66, -68, -70, -72, -74, -76, -78, -80, -82, -84, -86, -88, -90, -92, -94, -96, -98, -100, -102, -104, -106, -108, -110, -112, -114, -116, -118, -120, -122, -124, -126, Byte.MIN_VALUE};

    /* JADX INFO: Access modifiers changed from: protected */
    public HFDU06SP(UsbManager mManager, Context context) {
        this.context = context;
        this.mDevMan = AlDevManager.getInstance(mManager, context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AlCameraDevice getAlCameraDevice() {
        Iterator<AlCameraDevice> it = this.mDevMan.getDeviceList().values().iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean requestPermission() {
        AlCameraDevice dev = getAlCameraDevice();
        if (dev == null) {
            return false;
        }
        this.alCameraDevice = dev;
        this.mDevMan.requestPermission(dev);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDevice(AlCameraDevice device) {
        this.alCameraDevice = device;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AlCameraDevice getDevice() {
        return this.alCameraDevice;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[] readSensorSettingValues() {
        byte[] value = new byte[64];
        try {
            this.mXu.MpQueryExtRom();
            this.mXu.UcSpiSectorRead(Ascii.SO, 64, value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String getVersion() {
        char[] ver = new char[16];
        synchronized (this.mXu) {
            this.mXu.GetVersion(ver);
        }
        return String.valueOf(ver, 0, 16);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean onTouch() {
        byte[] pStatus = new byte[1];
        if (this.mXu.UcTouchStatus(pStatus) == 0) {
            new String(Integer.toHexString(pStatus[0] & 255));
            if (pStatus[0] != 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[] capture() {
        Log.e(this.TAG, "capture()");
        try {
            sleep(100);
            getImage(this.mImageBuffer);
            getImage(this.mImageBuffer);
            getImage(this.mImageBuffer);
            return yuyv422toRaw(this.mImageBuffer.getFrameByteBuffer(), this.mPreviewCfg.width, this.mPreviewCfg.height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected byte[] captureForPreview() {
        try {
            getImage(this.mImageBuffer);
            return yuyv422toRaw(this.mImageBuffer.getFrameByteBuffer(), this.mPreviewCfg.width, this.mPreviewCfg.height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setLED(boolean on) {
        byte brightness;
        String str = this.TAG;
        Log.e(str, "setLED(" + on + ")");
        if (!on) {
            brightness = 0;
        } else if (this.LED <= 0) {
            brightness = 8;
        } else {
            brightness = this.LED;
        }
        this.mXu.UcLedBrightnessSet(brightness);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void getValue(byte[] value) {
        try {
            this.mXu.MpQueryExtRom();
            this.mXu.UcSpiSectorRead(Ascii.CR, 64, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[] getDeviceID(byte[] value) {
        try {
            this.mXu.MpQueryExtRom();
            this.mXu.UcSpiSectorRead(Ascii.FF, 8, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setValue(byte[] value) {
        try {
            this.mXu.MpQueryExtRom();
            this.mXu.UcSpiSectorWrite(Ascii.CR, 64, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void release() {
        if (this.mAKImage != null) {
            new Thread(new Runnable() { // from class: com.nitgen.SDK.AndroidBSP.HFDU06SP.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        HFDU06SP.this.mAKImage.videoStop(HFDU06SP.this.mPreviewCfg);
                        HFDU06SP.this.mDevMan.closeAlCameraDevice(HFDU06SP.this.alCameraDevice);
                        HFDU06SP.this.alCameraDevice = null;
                        HFDU06SP.this.mAKImage = null;
                        HFDU06SP.this.mPreviewCfg = null;
                    } catch (CameraException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    protected void setExposure(int level) {
        if (level == 0) {
            SetSensorReg(2, 46, 120);
        } else if (level == 1) {
            SetSensorReg(2, 46, AlErrorCode.ERR_COMMAND);
        } else if (level == 2) {
            SetSensorReg(2, 46, 255);
        }
    }

    private void setSettings(byte[] set) {
        this.mXu.XuSensorWrite((short) set[0], 1, new byte[]{set[1]});
    }

    private void SetSensorReg(int bank, int address, int value) {
        setSettings(new byte[]{3, (byte) bank});
        setSettings(new byte[]{(byte) address, (byte) value});
    }

    private void getImage(AlFrame frame) throws CameraException {
        NullPointerException e;
        int i = 0;
        while (true) {
            try {
                frame.getFrameByteBuffer().rewind();
                frame.validBufferLength = 0;
                int ret = this.mAKImage.getVideo(frame, this.TIMEOUT_MICROSECOND);
                frame.getFrameByteBuffer().rewind();
                if ((frame.validBufferLength <= 0 || ret != 0) && ret != 164) {
                    String str = this.TAG;
                    Log.e(str, "AlErrorCode : " + ret);
                }
                int i2 = i + 1;
                if (i > 500) {
                    Log.e(this.TAG, "getImage ERROR");
                    return;
                }
                try {
                    if (frame.validBufferLength == 0) {
                        i = i2;
                    } else {
                        return;
                    }
                } catch (NullPointerException e2) {
                    e = e2;
                }
                e = e2;
            } catch (NullPointerException e3) {
                e = e3;
            }
            e.printStackTrace();
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean openDevice() {
        Log.e(this.TAG, "openDevice");
        try {
            this.mDevMan.openAlCameraDevice(this.alCameraDevice, this.context);
            this.mAKImage = this.mDevMan.getAKAVImage(this.alCameraDevice);
            this.mPreviewCfg = getDefaultCfg(4, this.mAKImage);
            this.mAKImage.videoStart(this.mPreviewCfg);
            this.mXu = this.mDevMan.getAKXU(this.alCameraDevice);
            this.mImageBuffer = new AlFrame(this.mPreviewCfg);
            sleep(300);
            initSettings();
            setBrightness(40);
            return true;
        } catch (CameraException e) {
            Log.e(this.TAG, "error : " + e.getErrorCode() + IOUtils.LINE_SEPARATOR_UNIX + e.toString());
            return false;
        }
    }

    public StreamConfig getDefaultCfg(int format, AKAVImage img) {
        Iterator<StreamConfig> it = img.getStreamConfigList();
        while (it.hasNext()) {
            StreamConfig cfg = it.next();
            if (cfg.format == format) {
                return cfg;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBrightness(int brightness) {
        SetSensorReg(1, AlErrorCode.ERR_NO_MEM, this.BRIGHTNESS_TABLE[99 - brightness]);
    }

    public byte[] yuyv422toRaw(ByteBuffer source, int width, int height) {
        int frameSize = width * height;
        if (this.temp == null) {
            this.temp = new byte[frameSize];
        }
        byte[] pData = source.array();
        for (int i = 0; i < frameSize; i += 2) {
            byte[] bArr = this.temp;
            bArr[i] = (byte) (pData[i] & 255);
            bArr[i + 1] = (byte) (pData[i + 1] & 255);
        }
        return this.temp;
    }

    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void initSettings() {
        SetSensorReg(0, 17, 95);
        SetSensorReg(0, 18, 112);
        SetSensorReg(0, 19, 122);
        SetSensorReg(0, 21, 23);
        SetSensorReg(0, 33, 31);
        SetSensorReg(0, 128, 20);
        SetSensorReg(0, 16, 61);
        SetSensorReg(0, 176, 236);
        SetSensorReg(0, 177, 3);
        SetSensorReg(0, 183, 158);
        SetSensorReg(0, 192, AlErrorCode.ERR_RESOURCE);
        SetSensorReg(1, 156, 63);
        SetSensorReg(1, 157, 32);
        SetSensorReg(1, 158, 255);
        SetSensorReg(1, 159, 8);
        SetSensorReg(1, 50, 9);
        SetSensorReg(2, 5, 29);
        SetSensorReg(2, 69, 2);
        SetSensorReg(2, 70, 128);
        SetSensorReg(2, 84, 4);
        SetSensorReg(2, 87, 8);
        SetSensorReg(2, 91, AlErrorCode.ERR_RESOURCE);
        SetSensorReg(2, 92, 112);
        SetSensorReg(2, 93, 104);
        SetSensorReg(2, 94, 80);
        SetSensorReg(2, 95, 16);
        SetSensorReg(2, 96, 32);
        SetSensorReg(2, 98, 2);
        SetSensorReg(2, 99, 5);
        SetSensorReg(2, 100, 12);
        SetSensorReg(2, 101, 37);
        SetSensorReg(2, 104, 2);
        SetSensorReg(2, 110, 16);
        SetSensorReg(2, 117, 64);
        SetSensorReg(2, 118, 128);
        SetSensorReg(2, 119, 255);
        SetSensorReg(2, 128, 40);
        SetSensorReg(2, AlErrorCode.ERR_INVALID_PARAM, 127);
        SetSensorReg(2, 134, 8);
        SetSensorReg(2, 135, 1);
        SetSensorReg(2, 140, 35);
        SetSensorReg(2, 141, 64);
        SetSensorReg(2, 142, 54);
        SetSensorReg(2, 143, 28);
        SetSensorReg(2, AlErrorCode.ERR_RESOURCE, 7);
        SetSensorReg(2, AlErrorCode.ERR_PERMISSION_DENIED, 10);
        SetSensorReg(2, AlErrorCode.ERR_NO_MEM, 117);
        SetSensorReg(2, AlErrorCode.ERR_NULL_POINTER, 54);
        SetSensorReg(2, AlErrorCode.ERR_VERIFY, 42);
        SetSensorReg(2, AlErrorCode.ERR_NATIVE_LAYER, 83);
        SetSensorReg(2, 150, 28);
        SetSensorReg(2, 151, 14);
        SetSensorReg(2, 152, 14);
        SetSensorReg(2, 153, 31);
        SetSensorReg(2, 154, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        SetSensorReg(2, 155, 10);
        SetSensorReg(2, 156, 16);
        SetSensorReg(2, AlErrorCode.ERR_COMMAND, 61);
        SetSensorReg(2, 161, 102);
        SetSensorReg(2, 162, 71);
        SetSensorReg(2, 163, 111);
        SetSensorReg(2, 182, 61);
        SetSensorReg(2, 183, 99);
        SetSensorReg(2, 184, 86);
        SetSensorReg(2, 185, 46);
        SetSensorReg(2, 188, 37);
        SetSensorReg(2, 189, 37);
        SetSensorReg(2, 192, 37);
        SetSensorReg(2, AlErrorCode.ERR_NO_DEVICE, 40);
        SetSensorReg(2, AlErrorCode.ERR_IN_USE, 37);
        SetSensorReg(2, AlErrorCode.ERR_BAD_FRAME, 120);
        SetSensorReg(2, 210, 125);
        SetSensorReg(2, 211, 128);
        SetSensorReg(2, 212, 128);
        SetSensorReg(2, 213, 128);
        SetSensorReg(2, 214, 128);
        SetSensorReg(2, JfifUtil.MARKER_RST7, 120);
        SetSensorReg(2, JfifUtil.MARKER_SOI, 128);
        SetSensorReg(2, JfifUtil.MARKER_EOI, 128);
        SetSensorReg(2, JfifUtil.MARKER_SOS, 36);
        SetSensorReg(2, 219, 0);
        SetSensorReg(2, 220, 133);
        SetSensorReg(2, 221, 150);
        SetSensorReg(2, 222, 51);
        SetSensorReg(2, 223, 2);
        SetSensorReg(2, 224, 142);
        SetSensorReg(2, JfifUtil.MARKER_APP1, AlErrorCode.ERR_NATIVE_LAYER);
        SetSensorReg(2, 226, 67);
        SetSensorReg(2, 227, 52);
        SetSensorReg(2, 228, AlErrorCode.ERR_NATIVE_LAYER);
        SetSensorReg(2, 229, 1);
        SetSensorReg(2, 230, 134);
        SetSensorReg(2, 231, 45);
        SetSensorReg(2, 232, 134);
        SetSensorReg(2, 233, 1);
        SetSensorReg(2, 234, AlErrorCode.ERR_PERMISSION_DENIED);
        SetSensorReg(2, 235, 47);
        SetSensorReg(2, 236, 39);
        SetSensorReg(2, 237, 135);
        SetSensorReg(2, 238, 0);
        SetSensorReg(2, 239, 137);
        SetSensorReg(2, 240, 51);
        SetSensorReg(2, 241, 138);
        SetSensorReg(2, 242, 139);
        SetSensorReg(2, 243, AlErrorCode.ERR_NATIVE_LAYER);
        SetSensorReg(2, 244, 65);
        SetSensorReg(2, 17, 0);
        SetSensorReg(2, 41, 2);
        SetSensorReg(2, 42, 87);
        SetSensorReg(2, 43, 2);
        SetSensorReg(2, 44, 87);
        SetSensorReg(2, 72, 3);
        SetSensorReg(2, 73, 31);
        SetSensorReg(2, 52, 4);
        SetSensorReg(2, 53, 164);
        SetSensorReg(2, 54, 4);
        SetSensorReg(2, 55, MXErrCode.ERR_NO_CERT_TEE);
        SetSensorReg(2, 60, 0);
        SetSensorReg(2, 61, 18);
        SetSensorReg(2, 62, 152);
        SetSensorReg(2, 64, 0);
        SetSensorReg(2, 65, 37);
        SetSensorReg(2, 66, 48);
        SetSensorReg(2, 72, 2);
        SetSensorReg(2, 73, 82);
        SetSensorReg(1, 164, 48);
        SetSensorReg(1, 165, AlErrorCode.ERR_RESOURCE);
        SetSensorReg(1, MXErrCode.ERR_NO_CERT_TEE, 0);
        SetSensorReg(1, MXErrCode.ERR_NO_KEY_TEE, 134);
        SetSensorReg(1, MXErrCode.ERR_NO_PIN_TEE, 46);
        SetSensorReg(1, MXErrCode.ERR_INVALID_PARAMETER_TEE, 134);
        SetSensorReg(1, MXErrCode.ERR_VERIFIED_FAILED_TEE, 128);
        SetSensorReg(1, MXErrCode.ERR_NO_CMD_TEE, AlErrorCode.ERR_RESOURCE);
        SetSensorReg(1, MXErrCode.ERR_NO_ALG_TEE, 50);
        SetSensorReg(2, 132, 128);
        SetSensorReg(2, 133, 128);
        SetSensorReg(2, 164, 96);
        SetSensorReg(2, 165, 64);
        SetSensorReg(2, MXErrCode.ERR_NO_CERT_TEE, 80);
        SetSensorReg(2, MXErrCode.ERR_NO_KEY_TEE, 0);
        SetSensorReg(2, MXErrCode.ERR_NO_PIN_TEE, 0);
    }
}
