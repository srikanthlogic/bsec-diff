package com.example.aadhaarfpoffline.tatvik.receivers;

import android.content.BroadcastReceiver;
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
public class ResponseBroadcastReceiver extends BroadcastReceiver {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final int PERMISSION_CODE = 1000;
    private Context mContext;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d("onreceive", "time=" + getCurrentTimeInFormat());
        this.mContext = context;
        if (intent.getIntExtra("resultCode", 0) == -1) {
            intent.getStringExtra("toastMessage");
        }
        if (isNetworkAvailable()) {
            Log.d("autosync", "Network available");
            syncTransTable();
            imageupload();
            return;
        }
        Log.d("autosync", "Network not available");
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
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
        Exception e;
        String str;
        String androidId;
        String str2 = "_";
        DBHelper db = new DBHelper(this.mContext);
        if (db.getImageUnSyncCount() <= 0) {
            Toast.makeText(this.mContext, "Auto-sync all data already synced", 1).show();
            return;
        }
        String androidId2 = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this.mContext);
        try {
            Cursor cursor = db.getAllRowsofTransTableCursor();
            if (cursor.moveToFirst()) {
                while (true) {
                    Long transid = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID)));
                    cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    cursor.getInt(cursor.getColumnIndex("VOTED"));
                    int imagesynced = cursor.getInt(cursor.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    if (imagesynced != 0) {
                        str = str2;
                        androidId = androidId2;
                    } else {
                        String voterimagename = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/Images/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        androidId = androidId2;
                        try {
                            map.put("udevid", createPartFromString(androidId2));
                            str = str2;
                            map.put("user_id", createPartFromString(userAuth.getPanchayatId() + str2 + userAuth.getWardNo() + str2 + userAuth.getBoothNo() + str2 + slnoinward));
                            postDataWithImage(map, file2, voterimagename, db, transid.intValue());
                        } catch (Exception e2) {
                            e = e2;
                            Context context = this.mContext;
                            Toast.makeText(context, "sync exception=" + e.getMessage(), 1).show();
                            return;
                        }
                    }
                    if (cursor.moveToNext()) {
                        androidId2 = androidId;
                        str2 = str;
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX INFO: Multiple debug info for r3v17 'db'  com.example.aadhaarfpoffline.tatvik.database.DBHelper: [D('userId' java.lang.String), D('db' com.example.aadhaarfpoffline.tatvik.database.DBHelper)] */
    /* JADX WARN: Can't wrap try/catch for region: R(15:76|18|19|(8:86|20|21|74|22|23|(1:25)|26)|(3:(4:28|80|29|(12:31|32|37|38|(1:45)(1:44)|46|47|84|48|49|90|50))(1:35)|90|50)|36|37|38|(1:40)|45|46|47|84|48|49) */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0213, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0214, code lost:
        r2 = r35;
     */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void syncTransTable() {
        ResponseBroadcastReceiver responseBroadcastReceiver;
        Exception e;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String UDevId;
        String str6;
        DBHelper db;
        Map<String, String> map;
        byte[] fp;
        int age;
        String gender;
        String voterimagename;
        int aadhaarmatch;
        String aadhaarNo;
        String str7;
        String fpString;
        String voting_date;
        ResponseBroadcastReceiver responseBroadcastReceiver2 = this;
        String str8 = "user_id";
        String str9 = "AADHAAR_NO";
        String fpString2 = "AADHAAR_MATCH";
        String str10 = "ID_DOCUMENT_IMAGE";
        String str11 = "GENDER";
        String str12 = "AGE";
        DBHelper db2 = new DBHelper(responseBroadcastReceiver2.mContext);
        if (db2.getUnSyncCount() <= 0) {
            Toast.makeText(responseBroadcastReceiver2.mContext, "Auto-sync all data already synced", 1).show();
            return;
        }
        String androidId = Settings.Secure.getString(responseBroadcastReceiver2.mContext.getContentResolver(), "android_id");
        String UDevId2 = androidId;
        UserAuth userAuth = new UserAuth(responseBroadcastReceiver2.mContext);
        int count = 0;
        try {
            Cursor cursor = db2.getAllRowsofTransTableCursor();
            if (cursor.moveToFirst()) {
                while (true) {
                    try {
                        long transid = cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID));
                        int synced = cursor.getInt(cursor.getColumnIndex("SYNCED"));
                        try {
                            int voted = cursor.getInt(cursor.getColumnIndex("VOTED"));
                            if (synced == 1) {
                                str = str8;
                                str4 = str9;
                                str3 = fpString2;
                                str2 = str10;
                                str5 = str11;
                                db = db2;
                                UDevId = UDevId2;
                                responseBroadcastReceiver = this;
                                str6 = str12;
                            } else if (voted == 0) {
                                str = str8;
                                str4 = str9;
                                str3 = fpString2;
                                str2 = str10;
                                str5 = str11;
                                db = db2;
                                UDevId = UDevId2;
                                responseBroadcastReceiver = this;
                                str6 = str12;
                            } else {
                                try {
                                    map = new HashMap<>();
                                    fp = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
                                    try {
                                        age = cursor.getInt(cursor.getColumnIndex(str12));
                                        try {
                                            gender = cursor.getString(cursor.getColumnIndex(str11));
                                            voterimagename = cursor.getString(cursor.getColumnIndex(str10));
                                            aadhaarmatch = cursor.getInt(cursor.getColumnIndex(fpString2));
                                            aadhaarNo = cursor.getString(cursor.getColumnIndex(str9));
                                            if (aadhaarNo == null) {
                                                aadhaarNo = "";
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            responseBroadcastReceiver = this;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        responseBroadcastReceiver = this;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    responseBroadcastReceiver = this;
                                    Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                                    return;
                                }
                                try {
                                    if (fp != null) {
                                        str7 = str9;
                                        try {
                                            if (fp.length >= 0) {
                                                fpString = Base64.encodeToString(fp, 0);
                                                map.put("TRANSID", "" + transid);
                                                String userId = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                                                String boothid = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                                                voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                                                map.put(str8, userId);
                                                map.put(str8, userId);
                                                str = str8;
                                                map.put("FINGERPRINT_TEMPLATE", fpString);
                                                map.put("VOTED", "" + voted);
                                                map.put(str10, voterimagename);
                                                str2 = str10;
                                                map.put(fpString2, "" + aadhaarmatch);
                                                str3 = fpString2;
                                                map.put(str7, aadhaarNo);
                                                if (voting_date != null || voting_date.isEmpty() || voting_date.length() <= 0) {
                                                    map.put("VOTING_DATE", "");
                                                } else {
                                                    map.put("VOTING_DATE", voting_date);
                                                }
                                                str4 = str7;
                                                map.put("VOTING_TYPE", "NON_AADHAAR");
                                                map.put("booth_id", boothid);
                                                str6 = str12;
                                                map.put(str6, "" + age);
                                                map.put(str11, gender);
                                                str5 = str11;
                                                map.put("udevid", UDevId2);
                                                UDevId = UDevId2;
                                                db = db2;
                                                responseBroadcastReceiver = this;
                                                responseBroadcastReceiver.uploadTransactionRow(map, db);
                                            }
                                        } catch (Exception e5) {
                                            e = e5;
                                            responseBroadcastReceiver = this;
                                            Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                                            return;
                                        }
                                    } else {
                                        str7 = str9;
                                    }
                                    responseBroadcastReceiver.uploadTransactionRow(map, db);
                                } catch (Exception e6) {
                                    e = e6;
                                    Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                                    return;
                                }
                                fpString = "";
                                map.put("TRANSID", "" + transid);
                                String userId2 = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                                String boothid2 = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                                voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                                map.put(str8, userId2);
                                map.put(str8, userId2);
                                str = str8;
                                map.put("FINGERPRINT_TEMPLATE", fpString);
                                map.put("VOTED", "" + voted);
                                map.put(str10, voterimagename);
                                str2 = str10;
                                map.put(fpString2, "" + aadhaarmatch);
                                str3 = fpString2;
                                map.put(str7, aadhaarNo);
                                if (voting_date != null) {
                                }
                                map.put("VOTING_DATE", "");
                                str4 = str7;
                                map.put("VOTING_TYPE", "NON_AADHAAR");
                                map.put("booth_id", boothid2);
                                str6 = str12;
                                map.put(str6, "" + age);
                                map.put(str11, gender);
                                str5 = str11;
                                map.put("udevid", UDevId2);
                                UDevId = UDevId2;
                                db = db2;
                                responseBroadcastReceiver = this;
                            }
                            if (cursor.moveToNext()) {
                                responseBroadcastReceiver2 = responseBroadcastReceiver;
                                str12 = str6;
                                count = count;
                                UDevId2 = UDevId;
                                str11 = str5;
                                str9 = str4;
                                str10 = str2;
                                str8 = str;
                                db2 = db;
                                cursor = cursor;
                                androidId = androidId;
                                fpString2 = str3;
                            } else {
                                return;
                            }
                        } catch (Exception e7) {
                            e = e7;
                            responseBroadcastReceiver = responseBroadcastReceiver2;
                        }
                    } catch (Exception e8) {
                        e = e8;
                        responseBroadcastReceiver = responseBroadcastReceiver2;
                    }
                }
            }
        } catch (Exception e9) {
            e = e9;
            responseBroadcastReceiver = responseBroadcastReceiver2;
        }
    }

    private void uploadTransactionRow(Map<String, String> map, final DBHelper db) {
        final String transId = map.get("TRANSID");
        ((GetDataService) RetrofitClientInstance.getRetrofitInstanceForSync().create(GetDataService.class)).updateTransactionRow(map).enqueue(new Callback<TransactionRowPostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver.1
            @Override // retrofit2.Callback
            public void onResponse(Call<TransactionRowPostResponse> call, Response<TransactionRowPostResponse> response) {
                if (response != null && response.body() != null && response.body().getUpdated()) {
                    db.updateSync(Integer.parseInt(transId), 1);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<TransactionRowPostResponse> call, Throwable t) {
                Context context = ResponseBroadcastReceiver.this.mContext;
                Toast.makeText(context, "voterlistTransaction update Error. " + t.getMessage(), 1).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    private synchronized void postDataWithImage(HashMap<String, RequestBody> map, final File file, String filename, final DBHelper db, final int transid) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, filename, RequestBody.create(MediaType.parse("multipart/form-data"), file));
        Context context = this.mContext;
        Toast.makeText(context, "upload image name=" + filename, 0).show();
        Call<ImageUploadResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceCimUrlForVoterIdUpload().create(GetDataService.class)).postVoterIdentification(body, map);
        Log.d("autosync", "postDataWithImage 1");
        call.enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver.2
            @Override // retrofit2.Callback
            public void onResponse(Call<ImageUploadResponse> call2, Response<ImageUploadResponse> response) {
                Log.d("autosync", "postDataWithImage 2");
                if (response != null && response.body() != null && response.body().isAdded().booleanValue()) {
                    Log.d("autosync", "postDataWithImage 3");
                    db.updateImageSync(transid, 1);
                    Toast.makeText(ResponseBroadcastReceiver.this.mContext, "Auto sync Id uploaded ", 0).show();
                    file.delete();
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
