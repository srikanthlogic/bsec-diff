package com.alcorlink.camera;
/* loaded from: classes5.dex */
public class AlErrorCode {
    public static final int ERR_ACCESS_DENIED = 162;
    public static final int ERR_ARGUMENT = 128;
    public static final int ERR_BAD_FRAME = 209;
    public static final int ERR_BUSY = 163;
    public static final int ERR_COMMAND = 160;
    public static final int ERR_DEVICE = 192;
    public static final int ERR_FATAL = 255;
    public static final int ERR_INVALID_DEVICE = 194;
    public static final int ERR_INVALID_PARAM = 129;
    public static final int ERR_IN_USE = 196;
    public static final int ERR_LENGTH = 131;
    public static final int ERR_NATIVE_LAYER = 149;
    public static final int ERR_NOT_FOUND = 199;
    public static final int ERR_NOT_INIT = 197;
    public static final int ERR_NOT_SUPPORTED = 130;
    public static final int ERR_NO_DEVICE = 193;
    public static final int ERR_NO_MEM = 146;
    public static final int ERR_NO_SPACE = 195;
    public static final int ERR_NULL_POINTER = 147;
    public static final int ERR_OVERFLOW = 165;
    public static final int ERR_PERMISSION_DENIED = 145;
    public static final int ERR_RESERVE1 = 161;
    public static final int ERR_RESOURCE = 144;
    public static final int ERR_STREAM_CLOSED = 208;
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_TIMEOUT = 164;
    public static final int ERR_TRANSFER = 198;
    public static final int ERR_VERIFY = 148;

    /* renamed from: a  reason: collision with root package name */
    private int f12a;
    private String b;

    public AlErrorCode() {
    }

    public AlErrorCode(int i) {
        String str;
        this.f12a = i;
        int i2 = this.f12a;
        if (i2 == 0) {
            str = new String("ERR_SUCCESS");
        } else if (i2 == 255) {
            str = new String("ERR_FATAL");
        } else if (i2 == 208) {
            str = new String("ERR_STREAM_CLOSED ");
        } else if (i2 != 209) {
            switch (i2) {
                case 128:
                    str = new String("ERR_ARGUMENT");
                    break;
                case ERR_INVALID_PARAM /* 129 */:
                    str = new String("ERR_INVALID_PARAM");
                    break;
                case ERR_NOT_SUPPORTED /* 130 */:
                    str = new String("ERR_NOT_SUPPORTED");
                    break;
                case ERR_LENGTH /* 131 */:
                    str = new String("ERR_LENGTH");
                    break;
                default:
                    switch (i2) {
                        case ERR_RESOURCE /* 144 */:
                            str = new String("ERR_RESOURCE");
                            break;
                        case ERR_PERMISSION_DENIED /* 145 */:
                            str = new String("ERR_PERMISSION_DENIED");
                            break;
                        case ERR_NO_MEM /* 146 */:
                            str = new String("ERR_NO_MEM");
                            break;
                        case ERR_NULL_POINTER /* 147 */:
                            str = new String("ERR_NULL_POINTER");
                            break;
                        default:
                            switch (i2) {
                                case ERR_COMMAND /* 160 */:
                                    str = new String("ERR_COMMAND");
                                    break;
                                case 161:
                                    str = new String("ERR_RESERVE1");
                                    break;
                                case 162:
                                    str = new String("ERR_ACCESS_DENIED");
                                    break;
                                case 163:
                                    str = new String("ERR_BUSY");
                                    break;
                                case 164:
                                    str = new String("ERR_TIMEOUT");
                                    break;
                                case 165:
                                    str = new String("ERR_OVERFLOW");
                                    break;
                                default:
                                    switch (i2) {
                                        case 192:
                                            str = new String("ERR_DEVICE");
                                            break;
                                        case ERR_NO_DEVICE /* 193 */:
                                            str = new String("ERR_NO_DEVICE");
                                            break;
                                        case ERR_INVALID_DEVICE /* 194 */:
                                            str = new String("ERR_INVALID_DEVICE");
                                            break;
                                        case ERR_NO_SPACE /* 195 */:
                                            str = new String("ERR_NO_SPACE");
                                            break;
                                        case ERR_IN_USE /* 196 */:
                                            str = new String("ERR_IN_USE");
                                            break;
                                        case ERR_NOT_INIT /* 197 */:
                                            str = new String("ERR_NOT_INIT ");
                                            break;
                                        default:
                                            str = new String("UNKWON ERROR");
                                            break;
                                    }
                            }
                    }
            }
        } else {
            str = new String("ERR_BAD_FRAME ");
        }
        this.b = str;
    }

    public int getErrCode() {
        return this.f12a;
    }

    public String getErrCodeString() {
        return this.b;
    }
}
