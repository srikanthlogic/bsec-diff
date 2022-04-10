package com.alcorlink.camera;

import a.a.a;
import com.alcorlink.camera.extension.OsdDateTime;
import com.alcorlink.camera.extension.OsdString;
/* loaded from: classes5.dex */
public class AKOSD {
    public static final short EX_CTRL_ATTR_GET_CUR = -127;
    public static final short EX_CTRL_ATTR_GET_MAX = -125;
    public static final short EX_CTRL_ATTR_GET_MIN = -126;
    public static final short EX_CTRL_ATTR_SET = 1;
    public static final short H264_DEFAULT_LAYER = 0;
    public static final boolean LATITUE_NORTH = true;
    public static final boolean LATITUE_SOUTH = false;
    public static final boolean LONGITUE_EAST = true;
    public static final boolean LONGITUE_WEST = false;
    public static final boolean SPEED_UNIT_KM = true;
    public static final boolean SPEED_UNIT_MILES = false;

    /* renamed from: a  reason: collision with root package name */
    private static byte f6a = 0;
    private static byte b = 1;
    private static byte c = 2;
    private a d = a.a();

    private void a(OsdString osdString, int i) {
        a(osdString, i, 8);
    }

    private void a(OsdString osdString, int i, int i2) {
        osdString.szString[i2] = (byte) (((byte) ((i % 100) / 10)) + 48);
        osdString.szString[i2 + 1] = (byte) (((byte) (i % 10)) + 48);
    }

    private void b(OsdString osdString, int i, int i2) {
        byte b2 = (byte) (i % 10);
        byte b3 = (byte) ((i % 100) / 10);
        byte b4 = (byte) ((i % 1000) / 100);
        StringBuilder sb = new StringBuilder("osdStringSetThreeDigit = ");
        sb.append((int) b4);
        sb.append("-");
        sb.append((int) b3);
        sb.append("-");
        sb.append((int) b2);
        osdString.szString[i2] = (byte) (b4 + 48);
        osdString.szString[i2 + 1] = (byte) (b3 + 48);
        osdString.szString[i2 + 2] = (byte) (b2 + 48);
    }

    public OsdDateTime osdGetDateTime() throws CameraException {
        OsdDateTime osdDateTime = new OsdDateTime();
        AlCamHAL alCamHAL = null;
        int XuOsdGetDatetime = alCamHAL.XuOsdGetDatetime(osdDateTime);
        if (XuOsdGetDatetime == 0) {
            osdDateTime.bMonth = (byte) (osdDateTime.bMonth - 1);
            return osdDateTime;
        }
        throw new CameraException("osdGetDateTime Error with reason:" + Integer.toString(XuOsdGetDatetime));
    }

    public int osdGetSpeed() throws CameraException {
        OsdString osdString = new OsdString();
        AlCamHAL alCamHAL = null;
        int XuOsdGetString = alCamHAL.XuOsdGetString((byte) 0, osdString);
        if (XuOsdGetString == 0) {
            byte b2 = (byte) (osdString.szString[0] - 48);
            byte b3 = (byte) (osdString.szString[1] - 48);
            byte b4 = (byte) (osdString.szString[2] - 48);
            StringBuilder sb = new StringBuilder("speed = ");
            sb.append((int) b4);
            sb.append("-");
            sb.append((int) b3);
            sb.append("-");
            sb.append((int) b2);
            return (b2 * 100) + (b3 * 10) + b4;
        }
        throw new CameraException("osdGetSpeed Error with reason:" + Integer.toString(XuOsdGetString));
    }

    public int osdSetDateTime(OsdDateTime osdDateTime) {
        osdDateTime.bMonth = (byte) (osdDateTime.bMonth + 1);
        AlCamHAL alCamHAL = null;
        return alCamHAL.XuOsdSetDatetime(osdDateTime);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.alcorlink.camera.AlCamHAL, com.alcorlink.camera.extension.OsdString] */
    /* JADX WARN: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void osdSetLatitude(boolean z, int i, int i2, int i3) throws CameraException {
        ?? r2 = 0;
        if (z) {
            r2.szString[0] = 78;
        } else {
            r2.szString[0] = 83;
        }
        b(null, i, 1);
        a(null, i2, 5);
        a(null, i3);
        int XuOsdSetString = r2.XuOsdSetString(c, null);
        if (XuOsdSetString != 0) {
            throw new CameraException("osdSetLatitude Fail with reason:" + Integer.toString(XuOsdSetString));
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.alcorlink.camera.AlCamHAL, com.alcorlink.camera.extension.OsdString] */
    /* JADX WARN: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void osdSetLongitude(boolean z, int i, int i2, int i3) throws CameraException {
        ?? r2 = 0;
        if (z) {
            r2.szString[0] = 69;
        } else {
            r2.szString[0] = 87;
        }
        b(null, i, 1);
        a(null, i2, 5);
        a(null, i3);
        int XuOsdSetString = r2.XuOsdSetString(b, null);
        if (XuOsdSetString != 0) {
            throw new CameraException("osdSetLongitude Fail with reason:" + Integer.toString(XuOsdSetString));
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.alcorlink.camera.AlCamHAL, com.alcorlink.camera.extension.OsdString] */
    /* JADX WARN: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void osdSetSpeed(boolean z, int i) throws CameraException {
        ?? r3 = 0;
        if (z) {
            r3.szString[3] = 75;
            r3.szString[4] = 77;
        } else {
            r3.szString[3] = 77;
            r3.szString[4] = 76;
        }
        b(null, i, 0);
        new StringBuilder("privateOsdSetSpeed.Addr=").append((int) r3.wRamAddr);
        int XuOsdSetString = r3.XuOsdSetString((byte) 0, null);
        if (XuOsdSetString != 0) {
            throw new CameraException("OsdSetSpeed Fail with reason:" + Integer.toString(XuOsdSetString));
        }
    }
}
