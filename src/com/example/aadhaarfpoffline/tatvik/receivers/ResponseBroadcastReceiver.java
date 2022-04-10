package com.example.aadhaarfpoffline.tatvik.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.provider.Settings;
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

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:67:0x029d
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:86)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    private void syncTransTable() {
        /*
        // Method dump skipped, instructions count: 729
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver.syncTransTable():void");
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
