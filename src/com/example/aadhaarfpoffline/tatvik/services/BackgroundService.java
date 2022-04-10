package com.example.aadhaarfpoffline.tatvik.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.network.ImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransactionRowPostResponse;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class BackgroundService extends IntentService {
    public static final String ACTION = "com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private Context mContext1;

    public BackgroundService() {
        super("backgroundService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        Log.d("backgroundService", "Service running " + getCurrentTimeInFormat());
        if (isNetworkAvailable()) {
            Log.d("autosync", "Network available");
            syncTransTable();
            imageuploadRejectedVoters();
            imageuploadRejectedVotersMatch();
            imageuploadThumb();
            return;
        }
        Log.d("autosync", "Network not available");
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }

    private void imageupload() {
        boolean z;
        DBHelper db = new DBHelper(getApplicationContext());
        boolean z2 = true;
        if (db.getImageUnSyncCount() <= 0) {
            Toast.makeText(getApplicationContext(), "All images already synced", 1).show();
            return;
        }
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(getApplicationContext());
        Cursor cursor = db.getAllRowsofTransTableCursor();
        if (cursor.moveToFirst()) {
            while (true) {
                try {
                    Long transid = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID)));
                    cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    cursor.getInt(cursor.getColumnIndex("VOTED"));
                    int imagesynced = cursor.getInt(cursor.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    if (imagesynced != 0) {
                        z = z2;
                    } else {
                        String voterimagename = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("udevid", createPartFromString(androidId));
                        map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                        postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "image");
                        z = true;
                    }
                } catch (Exception e) {
                    Context applicationContext = getApplicationContext();
                    z = true;
                    Toast.makeText(applicationContext, "Background sync exception=" + e.getMessage(), 1).show();
                }
                if (cursor.moveToNext()) {
                    z2 = z;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x015b A[LOOP:0: B:28:0x0051->B:25:0x015b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0166 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void imageuploadRejectedVoters() {
        Cursor cursor;
        long imagecounttobesynced;
        boolean z;
        Exception e;
        DBHelper db = new DBHelper(this);
        long imagecounttobesynced2 = db.getImageUnSyncCountRejectedVoters();
        boolean z2 = true;
        if (imagecounttobesynced2 <= 0) {
            Toast.makeText(this, "All rejected images already synced", 1).show();
            return;
        }
        Toast.makeText(this, imagecounttobesynced2 + " images to be synced", 1).show();
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this);
        Cursor cursor2 = db.getAllRowsofTransTableCursorRejectedVoters();
        if (cursor2.moveToFirst()) {
            while (true) {
                try {
                    Long transid = Long.valueOf(cursor2.getLong(cursor2.getColumnIndex(DBHelper.Key_ID)));
                    cursor2.getInt(cursor2.getColumnIndex("SYNCED"));
                    cursor2.getInt(cursor2.getColumnIndex("VOTED"));
                    int imagesynced = cursor2.getInt(cursor2.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor2.getString(cursor2.getColumnIndex("SlNoInWard"));
                    if (imagesynced != 0) {
                        cursor = cursor2;
                        imagecounttobesynced = imagecounttobesynced2;
                        z = z2;
                    } else {
                        String voterimagename = cursor2.getString(cursor2.getColumnIndex("ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        imagecounttobesynced = imagecounttobesynced2;
                        try {
                            map.put("udevid", createPartFromString(androidId));
                            map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                            cursor = cursor2;
                        } catch (Exception e2) {
                            e = e2;
                            cursor = cursor2;
                        }
                        try {
                            postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "image");
                            z = true;
                        } catch (Exception e3) {
                            e = e3;
                            Context applicationContext = getApplicationContext();
                            z = true;
                            Toast.makeText(applicationContext, "Background sync exception=" + e.getMessage(), 1).show();
                            if (!cursor.moveToNext()) {
                            }
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    cursor = cursor2;
                    imagecounttobesynced = imagecounttobesynced2;
                }
                if (!cursor.moveToNext()) {
                    z2 = z;
                    imagecounttobesynced2 = imagecounttobesynced;
                    cursor2 = cursor;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x015b A[LOOP:0: B:28:0x0051->B:25:0x015b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0166 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void imageuploadRejectedVotersMatch() {
        Cursor cursor;
        long imagecounttobesynced;
        boolean z;
        Exception e;
        DBHelper db = new DBHelper(this);
        long imagecounttobesynced2 = db.getImageUnSyncCountRejectedVotersMatch();
        boolean z2 = true;
        if (imagecounttobesynced2 <= 0) {
            Toast.makeText(this, "All rejected matched images already synced", 1).show();
            return;
        }
        Toast.makeText(this, imagecounttobesynced2 + " images to be synced", 1).show();
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this);
        Cursor cursor2 = db.getAllRowsofTransTableCursorRejectedVotersMatch();
        if (cursor2.moveToFirst()) {
            while (true) {
                try {
                    Long transid = Long.valueOf(cursor2.getLong(cursor2.getColumnIndex(DBHelper.Key_ID)));
                    cursor2.getInt(cursor2.getColumnIndex("SYNCED"));
                    cursor2.getInt(cursor2.getColumnIndex("VOTED"));
                    int imagesynced = cursor2.getInt(cursor2.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor2.getString(cursor2.getColumnIndex("SlNoInWard"));
                    if (imagesynced != 0) {
                        cursor = cursor2;
                        imagecounttobesynced = imagecounttobesynced2;
                        z = z2;
                    } else {
                        String voterimagename = cursor2.getString(cursor2.getColumnIndex("MATCHED_ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        imagecounttobesynced = imagecounttobesynced2;
                        try {
                            map.put("udevid", createPartFromString(androidId));
                            map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                            cursor = cursor2;
                        } catch (Exception e2) {
                            e = e2;
                            cursor = cursor2;
                        }
                        try {
                            postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "matched_image");
                            z = true;
                        } catch (Exception e3) {
                            e = e3;
                            Context applicationContext = getApplicationContext();
                            z = true;
                            Toast.makeText(applicationContext, "Background sync exception=" + e.getMessage(), 1).show();
                            if (!cursor.moveToNext()) {
                            }
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    cursor = cursor2;
                    imagecounttobesynced = imagecounttobesynced2;
                }
                if (!cursor.moveToNext()) {
                    z2 = z;
                    imagecounttobesynced2 = imagecounttobesynced;
                    cursor2 = cursor;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x015b A[LOOP:0: B:28:0x0051->B:25:0x015b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0166 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void imageuploadThumb() {
        Cursor cursor;
        long imagecounttobesynced;
        boolean z;
        Exception e;
        DBHelper db = new DBHelper(this);
        long imagecounttobesynced2 = db.getImageUnSyncCountThumb();
        boolean z2 = true;
        if (imagecounttobesynced2 <= 0) {
            Toast.makeText(this, "All THUMB images already synced", 1).show();
            return;
        }
        Toast.makeText(this, imagecounttobesynced2 + " Thumb images to be synced", 1).show();
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this);
        Cursor cursor2 = db.getAllRowsofTransTableCursorThumbNail();
        if (cursor2.moveToFirst()) {
            while (true) {
                try {
                    Long transid = Long.valueOf(cursor2.getLong(cursor2.getColumnIndex(DBHelper.Key_ID)));
                    cursor2.getInt(cursor2.getColumnIndex("SYNCED"));
                    cursor2.getInt(cursor2.getColumnIndex("VOTED"));
                    int imagesynced = cursor2.getInt(cursor2.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor2.getString(cursor2.getColumnIndex("SlNoInWard"));
                    if (imagesynced != 0) {
                        cursor = cursor2;
                        imagecounttobesynced = imagecounttobesynced2;
                        z = z2;
                    } else {
                        String voterimagename = cursor2.getString(cursor2.getColumnIndex("THUMBNAIL_ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        imagecounttobesynced = imagecounttobesynced2;
                        try {
                            map.put("udevid", createPartFromString(androidId));
                            map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                            cursor = cursor2;
                        } catch (Exception e2) {
                            e = e2;
                            cursor = cursor2;
                        }
                        try {
                            postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "thumb_image");
                            z = true;
                        } catch (Exception e3) {
                            e = e3;
                            Context applicationContext = getApplicationContext();
                            z = true;
                            Toast.makeText(applicationContext, "Background sync exception=" + e.getMessage(), 1).show();
                            if (!cursor.moveToNext()) {
                            }
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    cursor = cursor2;
                    imagecounttobesynced = imagecounttobesynced2;
                }
                if (!cursor.moveToNext()) {
                    z2 = z;
                    imagecounttobesynced2 = imagecounttobesynced;
                    cursor2 = cursor;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(44:(7:139|15|16|117|17|18|(8:151|19|20|143|21|22|(1:24)|25))|(4:27|133|28|(43:30|31|36|131|37|38|125|39|40|153|41|42|141|43|44|129|45|(3:155|47|(25:51|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(2:157|158)(1:114)))|54|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(0)(0)))(1:34)|35|36|131|37|38|125|39|40|153|41|42|141|43|44|129|45|(0)|54|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(50:139|15|16|117|17|18|(8:151|19|20|143|21|22|(1:24)|25)|(4:27|133|28|(43:30|31|36|131|37|38|125|39|40|153|41|42|141|43|44|129|45|(3:155|47|(25:51|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(2:157|158)(1:114)))|54|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(0)(0)))(1:34)|35|36|131|37|38|125|39|40|153|41|42|141|43|44|129|45|(0)|54|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(57:139|15|16|117|17|18|151|19|20|143|21|22|(1:24)|25|(4:27|133|28|(43:30|31|36|131|37|38|125|39|40|153|41|42|141|43|44|129|45|(3:155|47|(25:51|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(2:157|158)(1:114)))|54|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(0)(0)))(1:34)|35|36|131|37|38|125|39|40|153|41|42|141|43|44|129|45|(0)|54|55|137|56|57|121|58|59|147|60|61|127|62|63|119|64|65|145|66|67|149|68|69|112|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x030d, code lost:
        r4 = r20;
        r3 = r13;
        r21 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0284, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0287, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x028b, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x028c, code lost:
        r4 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0290, code lost:
        r21 = r2;
        r3 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0298, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0299, code lost:
        r16 = r16;
        r4 = r20;
        r3 = r13;
        r21 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x02a5, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x02a6, code lost:
        r28 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x02ab, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x02ac, code lost:
        r27 = r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x02b1, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x02b2, code lost:
        r26 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x02b7, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x02b8, code lost:
        r33 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x02bd, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x02be, code lost:
        r35 = r35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x02c3, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x02c4, code lost:
        r36 = r36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x02c9, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x02ca, code lost:
        r38 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x02ce, code lost:
        r4 = r20;
        r3 = r13;
        r21 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x02d6, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x02d7, code lost:
        r38 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x02df, code lost:
        r35 = r5;
        r36 = r6;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0388 A[LOOP:0: B:123:0x006f->B:114:0x0388, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03a0 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void syncTransTable() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String fpString;
        DBHelper db;
        Exception e;
        long transid;
        int synced;
        int voted;
        String voting_type;
        Map<String, String> map;
        byte[] fp;
        int age;
        String gender;
        String voterimagename;
        int aadhaarmatch;
        String aadhaarNo;
        String fpString2;
        String voting_date;
        String UDevId = "MATCHED_USER_ID";
        String str9 = "user_id";
        String str10 = "AADHAAR_NO";
        String str11 = "AADHAAR_MATCH";
        String str12 = "ID_DOCUMENT_IMAGE";
        String str13 = "GENDER";
        String str14 = "AGE";
        String str15 = "VOTING_TYPE";
        DBHelper db2 = new DBHelper(getApplicationContext());
        if (db2.getUnSyncCount() <= 0) {
            Toast.makeText(getApplicationContext(), "All data already synced", 1).show();
            return;
        }
        String UDevId2 = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(getApplicationContext());
        String str16 = "MATCHED_ID_DOCUMENT_IMAGE";
        Toast.makeText(getApplicationContext(), "sync", 1).show();
        Cursor cursor = db2.getAllRowsofTransTableCursor();
        if (cursor.moveToFirst()) {
            while (true) {
                try {
                    transid = cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID));
                    synced = cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    voted = cursor.getInt(cursor.getColumnIndex("VOTED"));
                    try {
                        voting_type = cursor.getString(cursor.getColumnIndex(str15));
                    } catch (Exception e2) {
                        e = e2;
                        str = str9;
                        str4 = str10;
                        str3 = str11;
                        str2 = str12;
                        str5 = str13;
                        str6 = str14;
                        str7 = str15;
                        fpString = str16;
                        db = db2;
                        str8 = UDevId;
                    }
                } catch (Exception e3) {
                    e = e3;
                    str8 = UDevId;
                    str = str9;
                    str4 = str10;
                    str3 = str11;
                    str2 = str12;
                    str5 = str13;
                    str6 = str14;
                    str7 = str15;
                    db = db2;
                    fpString = str16;
                }
                if (synced == 1) {
                    str = str9;
                    str4 = str10;
                    str3 = str11;
                    str2 = str12;
                    str5 = str13;
                    str6 = str14;
                    str7 = str15;
                    fpString = str16;
                    db = db2;
                    str8 = UDevId;
                } else if (voted == 0) {
                    str = str9;
                    str4 = str10;
                    str3 = str11;
                    str2 = str12;
                    str5 = str13;
                    str6 = str14;
                    str7 = str15;
                    fpString = str16;
                    db = db2;
                    str8 = UDevId;
                } else {
                    try {
                        map = new HashMap<>();
                        fp = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
                        str7 = str15;
                        try {
                            age = cursor.getInt(cursor.getColumnIndex(str14));
                            str6 = str14;
                            try {
                                gender = cursor.getString(cursor.getColumnIndex(str13));
                                str5 = str13;
                                try {
                                    voterimagename = cursor.getString(cursor.getColumnIndex(str12));
                                    aadhaarmatch = cursor.getInt(cursor.getColumnIndex(str11));
                                    aadhaarNo = cursor.getString(cursor.getColumnIndex(str10));
                                    if (aadhaarNo == null) {
                                        aadhaarNo = "";
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    str = str9;
                                    str4 = str10;
                                }
                            } catch (Exception e5) {
                                e = e5;
                                str = str9;
                                str4 = str10;
                                str3 = str11;
                                str2 = str12;
                                str5 = str13;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            str = str9;
                            str4 = str10;
                            str3 = str11;
                            str2 = str12;
                            str5 = str13;
                            str6 = str14;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        str = str9;
                        str4 = str10;
                        str3 = str11;
                        str2 = str12;
                        str5 = str13;
                        str6 = str14;
                        str7 = str15;
                    }
                    if (fp != null) {
                        str4 = str10;
                        try {
                        } catch (Exception e8) {
                            e = e8;
                            str = str9;
                            str3 = str11;
                            str2 = str12;
                            fpString = str16;
                            db = db2;
                            str8 = UDevId;
                            Toast.makeText(getApplicationContext(), "sync exception inside=" + e.getMessage(), 1).show();
                            if (!cursor.moveToNext()) {
                            }
                        }
                        if (fp.length >= 0) {
                            fpString2 = Base64.encodeToString(fp, 0);
                            StringBuilder sb = new StringBuilder();
                            sb.append("");
                            str3 = str11;
                            str2 = str12;
                            sb.append(transid);
                            map.put("TRANSID", sb.toString());
                            String userId = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                            String boothid = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                            voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                            map.put(str9, userId);
                            map.put(str9, userId);
                            map.put("FINGERPRINT_TEMPLATE", fpString2);
                            map.put("VOTED", "" + voted);
                            str = str9;
                            map.put(str2, voterimagename);
                            str2 = str2;
                            map.put(str3, "" + aadhaarmatch);
                            str3 = str3;
                            map.put(str4, aadhaarNo);
                            if (voting_date != null) {
                                try {
                                } catch (Exception e9) {
                                    e = e9;
                                    str4 = str4;
                                    fpString = str16;
                                    db = db2;
                                    str8 = UDevId;
                                    Toast.makeText(getApplicationContext(), "sync exception inside=" + e.getMessage(), 1).show();
                                    if (!cursor.moveToNext()) {
                                    }
                                }
                                if (!voting_date.isEmpty() && voting_date.length() > 0) {
                                    map.put("VOTING_DATE", voting_date);
                                    str4 = str4;
                                    map.put(str7, voting_type);
                                    map.put("booth_id", boothid);
                                    str7 = str7;
                                    map.put(str6, "" + age);
                                    str6 = str6;
                                    map.put(str5, gender);
                                    str5 = str5;
                                    map.put("udevid", UDevId2);
                                    UDevId2 = UDevId2;
                                    String matched_user_id = cursor.getString(cursor.getColumnIndex(UDevId));
                                    fpString = str16;
                                    String matched_id_document_iamge = cursor.getString(cursor.getColumnIndex(fpString));
                                    map.put("matched_user_id", matched_user_id);
                                    map.put(UDevId, matched_user_id);
                                    map.put(fpString, matched_id_document_iamge);
                                    str8 = UDevId;
                                    db = db2;
                                    uploadTransactionRow(map, db);
                                    if (!cursor.moveToNext()) {
                                        db2 = db;
                                        str16 = fpString;
                                        UDevId = str8;
                                        str15 = str7;
                                        str14 = str6;
                                        str13 = str5;
                                        str10 = str4;
                                        str11 = str3;
                                        str12 = str2;
                                        str9 = str;
                                    } else {
                                        return;
                                    }
                                }
                            }
                            map.put("VOTING_DATE", "");
                            str4 = str4;
                            map.put(str7, voting_type);
                            map.put("booth_id", boothid);
                            str7 = str7;
                            map.put(str6, "" + age);
                            str6 = str6;
                            map.put(str5, gender);
                            str5 = str5;
                            map.put("udevid", UDevId2);
                            UDevId2 = UDevId2;
                            String matched_user_id2 = cursor.getString(cursor.getColumnIndex(UDevId));
                            fpString = str16;
                            String matched_id_document_iamge2 = cursor.getString(cursor.getColumnIndex(fpString));
                            map.put("matched_user_id", matched_user_id2);
                            map.put(UDevId, matched_user_id2);
                            map.put(fpString, matched_id_document_iamge2);
                            str8 = UDevId;
                            db = db2;
                            uploadTransactionRow(map, db);
                            if (!cursor.moveToNext()) {
                            }
                        }
                    } else {
                        str4 = str10;
                    }
                    fpString2 = "";
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("");
                    str3 = str11;
                    str2 = str12;
                    sb2.append(transid);
                    map.put("TRANSID", sb2.toString());
                    String userId2 = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    String boothid2 = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                    voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                    map.put(str9, userId2);
                    map.put(str9, userId2);
                    map.put("FINGERPRINT_TEMPLATE", fpString2);
                    map.put("VOTED", "" + voted);
                    str = str9;
                    map.put(str2, voterimagename);
                    str2 = str2;
                    map.put(str3, "" + aadhaarmatch);
                    str3 = str3;
                    map.put(str4, aadhaarNo);
                    if (voting_date != null) {
                    }
                    map.put("VOTING_DATE", "");
                    str4 = str4;
                    map.put(str7, voting_type);
                    map.put("booth_id", boothid2);
                    str7 = str7;
                    map.put(str6, "" + age);
                    str6 = str6;
                    map.put(str5, gender);
                    str5 = str5;
                    map.put("udevid", UDevId2);
                    UDevId2 = UDevId2;
                    String matched_user_id22 = cursor.getString(cursor.getColumnIndex(UDevId));
                    fpString = str16;
                    String matched_id_document_iamge22 = cursor.getString(cursor.getColumnIndex(fpString));
                    map.put("matched_user_id", matched_user_id22);
                    map.put(UDevId, matched_user_id22);
                    map.put(fpString, matched_id_document_iamge22);
                    str8 = UDevId;
                    db = db2;
                    uploadTransactionRow(map, db);
                    if (!cursor.moveToNext()) {
                    }
                }
                if (!cursor.moveToNext()) {
                }
            }
        }
    }

    private void uploadTransactionRow(Map<String, String> map, DBHelper db) {
        String transId = map.get("TRANSID");
        try {
            Response<TransactionRowPostResponse> response = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceForSync().create(GetDataService.class)).updateTransactionRow(map).execute();
            if (response != null && response.body() != null && response.body().getUpdated()) {
                db.updateSync(Integer.parseInt(transId), 1);
                Context applicationContext = getApplicationContext();
                Toast.makeText(applicationContext, "voterlistTransaction row updated successfully" + response.code() + " transid=" + transId, 1).show();
            }
        } catch (Exception e) {
        }
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    private synchronized void postDataWithImage(HashMap<String, RequestBody> map, File file, String filename, final DBHelper db, final int transid, final String which_image) {
        Call<ImageUploadResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceImageUploadNewUrl().create(GetDataService.class)).postVoterIdentification(MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, filename, RequestBody.create(MediaType.parse("multipart/form-data"), file)), map);
        Log.d("autosync", "postDataWithImage 1");
        call.enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.services.BackgroundService.1
            @Override // retrofit2.Callback
            public void onResponse(Call<ImageUploadResponse> call2, Response<ImageUploadResponse> response) {
                Log.d("autosync", "postDataWithImage 2");
                if (response != null && response.body() != null && response.body().isAdded().booleanValue()) {
                    Log.d("autosync", "postDataWithImage 3");
                    if (which_image.equalsIgnoreCase("image")) {
                        db.updateImageSync(transid, 1);
                    } else if (which_image.equalsIgnoreCase("matched_image")) {
                        db.updateImageSyncMatched(transid, 1);
                    } else if (which_image.equalsIgnoreCase("thumb_image")) {
                        db.updateImageSyncThumbnail(transid, 1);
                    }
                    Toast.makeText(BackgroundService.this.getApplicationContext(), "Auto sync Id uploaded ", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ImageUploadResponse> call2, Throwable t) {
                Log.d("autosync", "postDataWithImage fail" + t.getMessage());
                Log.d("taag", t.getMessage());
                if (!(t instanceof SocketTimeoutException)) {
                    boolean z = t instanceof IOException;
                }
            }
        });
    }

    public File saveBitmapToFile(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            int scale = 1;
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
            while ((o.outWidth / scale) / 2 >= 75 && (o.outHeight / scale) / 2 >= 75) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream inputStream2 = new FileInputStream(file);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream2, null, o2);
            inputStream2.close();
            file.createNewFile();
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private RequestBody createPartFromString(String descriptionString) {
        try {
            return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        } catch (Exception e) {
            return null;
        }
    }
}
