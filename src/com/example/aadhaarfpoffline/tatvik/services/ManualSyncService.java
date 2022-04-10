package com.example.aadhaarfpoffline.tatvik.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.network.ImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransactionRowPostResponse;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.example.aadhaarfpoffline.tatvik.util.DisplayToast;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class ManualSyncService extends IntentService {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    DBHelper db;
    Handler mHandler = new Handler();

    public ManualSyncService() {
        super("ManualSyncService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        this.db = new DBHelper(this);
        if (isNetworkAvailable()) {
            Log.d("autosync manual", "Network available");
            syncTransTable();
            imageuploadRejectedVoters();
            imageuploadRejectedVotersMatch();
            imageuploadThumb();
            imageupload();
            return;
        }
        Log.d("autosync manual", "Network not available");
        this.mHandler.post(new DisplayToast(this, "Internet not available!!!"));
    }

    private void imageupload() {
        DBHelper db = new DBHelper(getApplicationContext());
        if (db.getImageUnSyncCount() <= 0) {
            this.mHandler.post(new DisplayToast(this, "Manual sync.All images already synced"));
            return;
        }
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(getApplicationContext());
        Cursor cursor = db.getAllRowsofTransTableCursor();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Long transid = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID)));
                    cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    cursor.getInt(cursor.getColumnIndex("VOTED"));
                    int imagesynced = cursor.getInt(cursor.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    if (imagesynced == 0) {
                        String voterimagename = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("udevid", createPartFromString(androidId));
                        map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                        postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "image");
                    }
                } catch (Exception e) {
                    Handler handler = this.mHandler;
                    handler.post(new DisplayToast(this, "Background sync exception=" + e.getMessage()));
                }
            } while (cursor.moveToNext());
        }
    }

    private void imageuploadRejectedVoters() {
        DBHelper db = new DBHelper(this);
        long imagecounttobesynced = db.getImageUnSyncCountRejectedVoters();
        if (imagecounttobesynced <= 0) {
            Toast.makeText(this, "All rejected images already synced", 1).show();
            this.mHandler.post(new DisplayToast(this, "All rejected images already synced"));
            return;
        }
        Toast.makeText(this, imagecounttobesynced + " images to be synced", 1).show();
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this);
        Cursor cursor = db.getAllRowsofTransTableCursorRejectedVoters();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Long transid = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID)));
                    cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    cursor.getInt(cursor.getColumnIndex("VOTED"));
                    int imagesynced = cursor.getInt(cursor.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    if (imagesynced == 0) {
                        String voterimagename = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("udevid", createPartFromString(androidId));
                        map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                        postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "image");
                    }
                } catch (Exception e) {
                    Handler handler = this.mHandler;
                    handler.post(new DisplayToast(this, "Background sync exception=" + e.getMessage()));
                }
            } while (cursor.moveToNext());
        }
    }

    private void imageuploadRejectedVotersMatch() {
        DBHelper db = new DBHelper(this);
        long imagecounttobesynced = db.getImageUnSyncCountRejectedVotersMatch();
        if (imagecounttobesynced <= 0) {
            this.mHandler.post(new DisplayToast(this, "All rejected matched images already synced"));
            return;
        }
        Handler handler = this.mHandler;
        handler.post(new DisplayToast(this, imagecounttobesynced + " images to be synced"));
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this);
        Cursor cursor = db.getAllRowsofTransTableCursorRejectedVotersMatch();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Long transid = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID)));
                    cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    cursor.getInt(cursor.getColumnIndex("VOTED"));
                    int imagesynced = cursor.getInt(cursor.getColumnIndex("IMAGE_SYNCED"));
                    String slnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    if (imagesynced == 0) {
                        String voterimagename = cursor.getString(cursor.getColumnIndex("MATCHED_ID_DOCUMENT_IMAGE"));
                        File file2 = saveBitmapToFile(new File("/sdcard/" + Const.PublicImageName + "/", voterimagename));
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("udevid", createPartFromString(androidId));
                        map.put("user_id", createPartFromString(userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo() + "_" + slnoinward));
                        postDataWithImage(map, file2, voterimagename, db, transid.intValue(), "matched_image");
                    }
                } catch (Exception e) {
                    Context applicationContext = getApplicationContext();
                    Toast.makeText(applicationContext, "Background sync exception=" + e.getMessage(), 1).show();
                    Handler handler2 = this.mHandler;
                    handler2.post(new DisplayToast(this, "Background sync exception=" + e.getMessage()));
                }
            } while (cursor.moveToNext());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x017b A[LOOP:0: B:32:0x0054->B:25:0x017b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0186 A[SYNTHETIC] */
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
        Handler handler = this.mHandler;
        handler.post(new DisplayToast(this, imagecounttobesynced2 + " Thumb images to be synced"));
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
                            Handler handler2 = this.mHandler;
                            handler2.post(new DisplayToast(this, "Background sync exception=" + e.getMessage()));
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

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:98:0x02f4
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:86)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    private void syncTransTable() {
        /*
        // Method dump skipped, instructions count: 1030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.aadhaarfpoffline.tatvik.services.ManualSyncService.syncTransTable():void");
    }

    private void uploadTransactionRow(Map<String, String> map, DBHelper db) {
        String transId = map.get("TRANSID");
        try {
            Response<TransactionRowPostResponse> response = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceForSync().create(GetDataService.class)).updateTransactionRow(map).execute();
            if (response != null && response.body() != null && response.body().getUpdated()) {
                db.updateSync(Integer.parseInt(transId), 1);
                Context applicationContext = getApplicationContext();
                Toast.makeText(applicationContext, "voterlistTransaction row updated successfully" + response.code() + " transid=" + transId, 1).show();
                Handler handler = this.mHandler;
                handler.post(new DisplayToast(this, "voterlistTransaction row updated successfully" + response.code() + " transid=" + transId));
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
        call.enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.services.ManualSyncService.1
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
                    Toast.makeText(ManualSyncService.this.getApplicationContext(), "Manual Auto sync Id uploaded ", 0).show();
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
