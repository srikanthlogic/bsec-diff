package com.sec.biometric.license;

import android.content.Context;
/* loaded from: classes3.dex */
public class SecBiometricLicenseManager {
    public static final String ACTION_LICENSE_STATUS = "edm.intent.action.license.status";
    public static final int ERROR_INTERNAL = 301;
    public static final int ERROR_INTERNAL_SERVER = 401;
    public static final int ERROR_INVALID_LICENSE = 201;
    public static final int ERROR_INVALID_PACKAGE_NAME = 204;
    public static final int ERROR_LICENSE_TERMINATED = 203;
    public static final int ERROR_NETWORK_DISCONNECTED = 501;
    public static final int ERROR_NETWORK_GENERAL = 502;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_CURRENT_DATE = 205;
    public static final int ERROR_NULL_PARAMS = 101;
    public static final int ERROR_UNKNOWN = 102;
    public static final int ERROR_USER_DISAGREES_LICENSE_AGREEMENT = 601;
    public static final String EXTRA_LICENSE_ERROR_CODE = "edm.intent.extra.license.errorcode";
    public static final String EXTRA_LICENSE_RESULT_TYPE = "edm.intent.extra.license.result_type";
    public static final String EXTRA_LICENSE_STATUS = "edm.intent.extra.license.status";
    public static final int LICENSE_RESULT_TYPE_ACTIVATION = 800;
    public static final int LICENSE_RESULT_TYPE_VALIDATION = 801;

    SecBiometricLicenseManager() {
        throw new RuntimeException("Stub!");
    }

    public static SecBiometricLicenseManager getInstance(Context context) {
        throw new RuntimeException("Stub!");
    }

    public void activateLicense(String str, String str2) {
        throw new RuntimeException("Stub!");
    }

    public boolean isLicenseActivated() {
        throw new RuntimeException("Stub!");
    }
}
