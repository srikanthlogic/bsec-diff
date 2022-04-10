package com.surepass.surepassesign;

import com.facebook.common.util.UriUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;
/* compiled from: Constants.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\n"}, d2 = {"Lcom/surepass/surepassesign/Constants;", "", "()V", "getJsonResponse", "Lorg/json/JSONObject;", "statusCode", "", "message", "", "Companion", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class Constants {
    public static final Companion Companion = new Companion(null);
    private static String BASEURL_PRE_PROD = "https://sandbox.surepass.io/api/v1/esign/";
    private static String BASEURL_PROD = "https://kyc-api.aadhaarkyc.io/api/v1/esign/";
    private static final String ESIGN_CONSOLE = ESIGN_CONSOLE;
    private static final String ESIGN_CONSOLE = ESIGN_CONSOLE;
    private static String MY_PREFS_NAME = "MyPrefsFile";
    private static String UNAUTH_ACCESS = "UNAUTH_ACCESS";
    private static String MAX_RETRY = "MAX_RETRY";
    private static String VERIFY_REFUSAL = "VERIFY_REFUSAL";
    private static String POPUP_CLOSED = "POPUP_CLOSED";
    private static String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    private static String INT_SERVER_ERROR = "INT_SERVER_ERROR";
    private static String NSDL_ERROR = "NSDL_ERROR";

    /* compiled from: Constants.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\"\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0006R\u001a\u0010\u000e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u001a\u0010\u001d\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR\u001a\u0010 \u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\bR\u001a\u0010#\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0006\"\u0004\b%\u0010\b¨\u0006&"}, d2 = {"Lcom/surepass/surepassesign/Constants$Companion;", "", "()V", "BASEURL_PRE_PROD", "", "getBASEURL_PRE_PROD", "()Ljava/lang/String;", "setBASEURL_PRE_PROD", "(Ljava/lang/String;)V", "BASEURL_PROD", "getBASEURL_PROD", "setBASEURL_PROD", Constants.ESIGN_CONSOLE, "getESIGN_CONSOLE", "INT_SERVER_ERROR", "getINT_SERVER_ERROR", "setINT_SERVER_ERROR", "MAX_RETRY", "getMAX_RETRY", "setMAX_RETRY", "MY_PREFS_NAME", "getMY_PREFS_NAME", "setMY_PREFS_NAME", "NSDL_ERROR", "getNSDL_ERROR", "setNSDL_ERROR", "POPUP_CLOSED", "getPOPUP_CLOSED", "setPOPUP_CLOSED", "UNAUTH_ACCESS", "getUNAUTH_ACCESS", "setUNAUTH_ACCESS", "UNKNOWN_ERROR", "getUNKNOWN_ERROR", "setUNKNOWN_ERROR", "VERIFY_REFUSAL", "getVERIFY_REFUSAL", "setVERIFY_REFUSAL", "app_release"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final String getBASEURL_PRE_PROD() {
            return Constants.BASEURL_PRE_PROD;
        }

        public final void setBASEURL_PRE_PROD(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.BASEURL_PRE_PROD = str;
        }

        public final String getBASEURL_PROD() {
            return Constants.BASEURL_PROD;
        }

        public final void setBASEURL_PROD(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.BASEURL_PROD = str;
        }

        public final String getESIGN_CONSOLE() {
            return Constants.ESIGN_CONSOLE;
        }

        public final String getMY_PREFS_NAME() {
            return Constants.MY_PREFS_NAME;
        }

        public final void setMY_PREFS_NAME(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.MY_PREFS_NAME = str;
        }

        public final String getUNAUTH_ACCESS() {
            return Constants.UNAUTH_ACCESS;
        }

        public final void setUNAUTH_ACCESS(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.UNAUTH_ACCESS = str;
        }

        public final String getMAX_RETRY() {
            return Constants.MAX_RETRY;
        }

        public final void setMAX_RETRY(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.MAX_RETRY = str;
        }

        public final String getVERIFY_REFUSAL() {
            return Constants.VERIFY_REFUSAL;
        }

        public final void setVERIFY_REFUSAL(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.VERIFY_REFUSAL = str;
        }

        public final String getPOPUP_CLOSED() {
            return Constants.POPUP_CLOSED;
        }

        public final void setPOPUP_CLOSED(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.POPUP_CLOSED = str;
        }

        public final String getUNKNOWN_ERROR() {
            return Constants.UNKNOWN_ERROR;
        }

        public final void setUNKNOWN_ERROR(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.UNKNOWN_ERROR = str;
        }

        public final String getINT_SERVER_ERROR() {
            return Constants.INT_SERVER_ERROR;
        }

        public final void setINT_SERVER_ERROR(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.INT_SERVER_ERROR = str;
        }

        public final String getNSDL_ERROR() {
            return Constants.NSDL_ERROR;
        }

        public final void setNSDL_ERROR(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            Constants.NSDL_ERROR = str;
        }
    }

    public final JSONObject getJsonResponse(int statusCode, String message) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("status_code", statusCode);
        responseJson.put("message", message);
        responseJson.put(UriUtil.DATA_SCHEME, "");
        if (statusCode == 200) {
            responseJson.put("error", "");
            return responseJson;
        } else if (statusCode == 401) {
            responseJson.put("error", UNAUTH_ACCESS);
            return responseJson;
        } else if (statusCode == 403) {
            responseJson.put("error", MAX_RETRY);
            return responseJson;
        } else if (statusCode == 422) {
            responseJson.put("error", VERIFY_REFUSAL);
            return responseJson;
        } else if (statusCode == 433) {
            responseJson.put("error", POPUP_CLOSED);
            return responseJson;
        } else if (statusCode == 450) {
            responseJson.put("error", UNKNOWN_ERROR);
            return responseJson;
        } else if (statusCode == 500) {
            responseJson.put("error", INT_SERVER_ERROR);
            return responseJson;
        } else if (statusCode != 501) {
            return responseJson;
        } else {
            responseJson.put("error", NSDL_ERROR);
            return responseJson;
        }
    }

    public final JSONObject getJsonResponse(int statusCode) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("status_code", statusCode);
        responseJson.put(UriUtil.DATA_SCHEME, "");
        if (statusCode == 200) {
            responseJson.put("error", "");
            responseJson.put("message", "successfully e-sign the document");
            return responseJson;
        } else if (statusCode == 401) {
            responseJson.put("error", UNAUTH_ACCESS);
            responseJson.put("message", "Invalid Token ");
            return responseJson;
        } else if (statusCode == 403) {
            responseJson.put("error", MAX_RETRY);
            responseJson.put("message", "The message about the possible cause. Due to phone number or OTPs.");
            return responseJson;
        } else if (statusCode == 422) {
            responseJson.put("error", VERIFY_REFUSAL);
            responseJson.put("message", "User refused to verify the document");
            return responseJson;
        } else if (statusCode == 433) {
            responseJson.put("error", POPUP_CLOSED);
            responseJson.put("message", "User closed the popup window before process completed");
            return responseJson;
        } else if (statusCode == 450) {
            responseJson.put("error", UNKNOWN_ERROR);
            responseJson.put("message", "An error occurred");
            return responseJson;
        } else if (statusCode == 500) {
            responseJson.put("error", INT_SERVER_ERROR);
            responseJson.put("message", "Internal server error occurred");
            return responseJson;
        } else if (statusCode != 501) {
            return responseJson;
        } else {
            responseJson.put("error", NSDL_ERROR);
            responseJson.put("message", "error from nsdl while signing document");
            return responseJson;
        }
    }
}
