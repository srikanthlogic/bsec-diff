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
public class ResponseBroadcastReceiver extends BroadcastReceiver {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final int PERMISSION_CODE = 1000;
    private Context mContext;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d("onreceive1", "time1=" + getCurrentTimeInFormat());
        this.mContext = context;
        if (intent.getIntExtra("resultCode", 0) == -1) {
            intent.getStringExtra("toastMessage");
        }
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
            Toast.makeText(this.mContext, "All images already synced", 1).show();
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
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
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

    private void syncTransTable() {
        ResponseBroadcastReceiver responseBroadcastReceiver;
        Exception e;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        DBHelper db;
        Map<String, String> map;
        byte[] fp;
        int age;
        String gender;
        String voterimagename;
        int aadhaarmatch;
        String aadhaarNo;
        String aadhaarNo2;
        String fpString;
        String voting_date;
        String voting_type = "MATCHED_USER_ID";
        String str10 = "user_id";
        String str11 = "AADHAAR_NO";
        String fpString2 = "AADHAAR_MATCH";
        String str12 = "ID_DOCUMENT_IMAGE";
        String str13 = "GENDER";
        String str14 = "AGE";
        String str15 = "VOTING_TYPE";
        DBHelper db2 = new DBHelper(this.mContext);
        if (db2.getUnSyncCount() <= 0) {
            Toast.makeText(this.mContext, "All data already synced", 1).show();
            return;
        }
        String UDevId = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
        String str16 = "MATCHED_ID_DOCUMENT_IMAGE";
        UserAuth userAuth = new UserAuth(this.mContext);
        try {
            Cursor cursor = db2.getAllRowsofTransTableCursor();
            if (cursor.moveToFirst()) {
                while (true) {
                    try {
                        long transid = cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID));
                        int synced = cursor.getInt(cursor.getColumnIndex("SYNCED"));
                        try {
                            int voted = cursor.getInt(cursor.getColumnIndex("VOTED"));
                            String voting_type2 = cursor.getString(cursor.getColumnIndex(str15));
                            String UDevId2 = UDevId;
                            if (synced == 1) {
                                str3 = str11;
                                str2 = fpString2;
                                str = str12;
                                str5 = str13;
                                str6 = str14;
                                str4 = str15;
                                db = db2;
                                str8 = voting_type;
                                responseBroadcastReceiver = this;
                                str7 = str10;
                                str9 = str16;
                            } else if (voted == 0) {
                                str3 = str11;
                                str2 = fpString2;
                                str = str12;
                                str5 = str13;
                                str6 = str14;
                                str4 = str15;
                                db = db2;
                                str8 = voting_type;
                                responseBroadcastReceiver = this;
                                str7 = str10;
                                str9 = str16;
                            } else {
                                try {
                                    map = new HashMap<>();
                                    fp = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
                                    age = cursor.getInt(cursor.getColumnIndex(str14));
                                    gender = cursor.getString(cursor.getColumnIndex(str13));
                                    voterimagename = cursor.getString(cursor.getColumnIndex(str12));
                                    aadhaarmatch = cursor.getInt(cursor.getColumnIndex(fpString2));
                                    aadhaarNo = cursor.getString(cursor.getColumnIndex(str11));
                                    if (aadhaarNo == null) {
                                        aadhaarNo = "";
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    responseBroadcastReceiver = this;
                                }
                                try {
                                    try {
                                        if (fp != null) {
                                            aadhaarNo2 = aadhaarNo;
                                            try {
                                                if (fp.length >= 0) {
                                                    fpString = Base64.encodeToString(fp, 0);
                                                    map.put("TRANSID", "" + transid);
                                                    String userId = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                                                    String boothid = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                                                    voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                                                    map.put(str10, userId);
                                                    map.put(str10, userId);
                                                    map.put("FINGERPRINT_TEMPLATE", fpString);
                                                    map.put("VOTED", "" + voted);
                                                    map.put(str12, voterimagename);
                                                    str = str12;
                                                    map.put(fpString2, "" + aadhaarmatch);
                                                    str2 = fpString2;
                                                    map.put(str11, aadhaarNo2);
                                                    if (voting_date != null || voting_date.isEmpty() || voting_date.length() <= 0) {
                                                        map.put("VOTING_DATE", "");
                                                    } else {
                                                        map.put("VOTING_DATE", voting_date);
                                                    }
                                                    str3 = str11;
                                                    map.put(str15, voting_type2);
                                                    map.put("booth_id", boothid);
                                                    str4 = str15;
                                                    map.put(str14, "" + age);
                                                    str6 = str14;
                                                    map.put(str13, gender);
                                                    str5 = str13;
                                                    map.put("udevid", UDevId2);
                                                    UDevId2 = UDevId2;
                                                    String matched_user_id = cursor.getString(cursor.getColumnIndex(voting_type));
                                                    str7 = str10;
                                                    str9 = str16;
                                                    String matched_id_document_iamge = cursor.getString(cursor.getColumnIndex(str9));
                                                    map.put("matched_user_id", matched_user_id);
                                                    map.put(voting_type, matched_user_id);
                                                    map.put(str9, matched_id_document_iamge);
                                                    responseBroadcastReceiver = this;
                                                    str8 = voting_type;
                                                    db = db2;
                                                    responseBroadcastReceiver.uploadTransactionRow(map, db);
                                                }
                                            } catch (Exception e3) {
                                                e = e3;
                                                responseBroadcastReceiver = this;
                                                Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                                                return;
                                            }
                                        } else {
                                            aadhaarNo2 = aadhaarNo;
                                        }
                                        responseBroadcastReceiver.uploadTransactionRow(map, db);
                                    } catch (Exception e4) {
                                        e = e4;
                                        Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                                        return;
                                    }
                                    map.put("udevid", UDevId2);
                                    UDevId2 = UDevId2;
                                    String matched_user_id2 = cursor.getString(cursor.getColumnIndex(voting_type));
                                    str7 = str10;
                                    str9 = str16;
                                    String matched_id_document_iamge2 = cursor.getString(cursor.getColumnIndex(str9));
                                    map.put("matched_user_id", matched_user_id2);
                                    map.put(voting_type, matched_user_id2);
                                    map.put(str9, matched_id_document_iamge2);
                                    responseBroadcastReceiver = this;
                                    str8 = voting_type;
                                    db = db2;
                                } catch (Exception e5) {
                                    e = e5;
                                    responseBroadcastReceiver = this;
                                    Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                                    return;
                                }
                                fpString = "";
                                map.put("TRANSID", "" + transid);
                                String userId2 = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                                String boothid2 = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                                voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                                map.put(str10, userId2);
                                map.put(str10, userId2);
                                map.put("FINGERPRINT_TEMPLATE", fpString);
                                map.put("VOTED", "" + voted);
                                map.put(str12, voterimagename);
                                str = str12;
                                map.put(fpString2, "" + aadhaarmatch);
                                str2 = fpString2;
                                map.put(str11, aadhaarNo2);
                                if (voting_date != null) {
                                }
                                map.put("VOTING_DATE", "");
                                str3 = str11;
                                map.put(str15, voting_type2);
                                map.put("booth_id", boothid2);
                                str4 = str15;
                                map.put(str14, "" + age);
                                str6 = str14;
                                map.put(str13, gender);
                                str5 = str13;
                            }
                            if (cursor.moveToNext()) {
                                db2 = db;
                                str16 = str9;
                                voting_type = str8;
                                str10 = str7;
                                UDevId = UDevId2;
                                str14 = str6;
                                str13 = str5;
                                str15 = str4;
                                str11 = str3;
                                fpString2 = str2;
                                str12 = str;
                            } else {
                                return;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            responseBroadcastReceiver = this;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        responseBroadcastReceiver = this;
                        Toast.makeText(responseBroadcastReceiver.mContext, "sync exception=" + e.getMessage(), 1).show();
                        return;
                    }
                }
            }
        } catch (Exception e8) {
            e = e8;
            responseBroadcastReceiver = this;
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

    private synchronized void postDataWithImage(HashMap<String, RequestBody> map, File file, String filename, final DBHelper db, final int transid) {
        Call<ImageUploadResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceImageUploadNewUrl().create(GetDataService.class)).postVoterIdentification(MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, filename, RequestBody.create(MediaType.parse("multipart/form-data"), file)), map);
        Log.d("autosync", "postDataWithImage 1");
        call.enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver.2
            @Override // retrofit2.Callback
            public void onResponse(Call<ImageUploadResponse> call2, Response<ImageUploadResponse> response) {
                Log.d("autosync", "postDataWithImage 2");
                if (response != null && response.body() != null && response.body().isAdded().booleanValue()) {
                    Log.d("autosync", "postDataWithImage 3");
                    db.updateImageSync(transid, 1);
                    Toast.makeText(ResponseBroadcastReceiver.this.mContext, "Auto sync Id uploaded ", 0).show();
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
