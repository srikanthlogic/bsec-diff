package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.ProgressRequestBody;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.adapter.SyncTableAdapter;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.DBUpdateResponse;
import com.example.aadhaarfpoffline.tatvik.network.ImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterListNewTableGetResponse;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.facebook.common.util.UriUtil;
import com.google.common.net.HttpHeaders;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.cache.DiskLruCache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class SyncActivity extends AppCompatActivity implements SyncTableAdapter.OnItemClickListener, ProgressRequestBody.UploadCallbacks {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private Button CSVButton;
    private Button ExportButton;
    private Button MatchButton;
    private Button SyncButton;
    private SyncTableAdapter adapter2;
    Context context;
    DBHelper db;
    private TextView fpcount;
    private TextView imagecount;
    private RecyclerView recyclerView;
    Resources resources;
    private List<VoterDataNewModel> voterDataNewModelList2;
    private List<VoterDataNewModel> voterListFromServer;
    private TextView votercount;
    List<Integer> voterlist;
    String dist = "";
    String block = "";
    String panchayatId = "";
    String ward = "";
    String booth = "";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        this.context = LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        this.resources = this.context.getResources();
        UserAuth userAuth = new UserAuth(this);
        this.db = new DBHelper(this);
        this.dist = userAuth.getDistrictNo();
        this.block = userAuth.getBlockID();
        this.panchayatId = userAuth.getPanchayatId();
        this.ward = userAuth.getWardNo();
        this.booth = userAuth.getBoothNo();
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_sync_list);
        this.ExportButton = (Button) findViewById(R.id.export_button);
        this.MatchButton = (Button) findViewById(R.id.check_match);
        this.SyncButton = (Button) findViewById(R.id.sync_button);
        this.CSVButton = (Button) findViewById(R.id.csv_button);
        this.fpcount = (TextView) findViewById(R.id.finger_count);
        this.votercount = (TextView) findViewById(R.id.voter_count);
        this.imagecount = (TextView) findViewById(R.id.image_count);
        this.voterDataNewModelList2 = new ArrayList();
        this.voterDataNewModelList2 = this.db.getAllElements();
        setRecyclerViewNewTable();
        this.ExportButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Toast.makeText(SyncActivity.this.getApplicationContext(), "Export button clicked", 1).show();
            }
        });
        this.ExportButton.setVisibility(8);
        this.MatchButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.MatchButton.setVisibility(8);
        this.SyncButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SyncActivity syncActivity = SyncActivity.this;
                syncActivity.getVoterListForSyncComparison(syncActivity.dist, SyncActivity.this.block, SyncActivity.this.panchayatId, SyncActivity.this.ward, SyncActivity.this.booth);
            }
        });
        this.CSVButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.CSVButton.setVisibility(8);
        TextView textView = this.fpcount;
        textView.setText(" fp count=" + this.db.getFingerCount());
        TextView textView2 = this.votercount;
        textView2.setText(" voter count=" + this.db.getTotalVoters());
        TextView textView3 = this.imagecount;
        textView3.setText(" document image count=" + this.db.getIdDocumentCount());
    }

    private void csvCreate() {
    }

    private int votercount() {
        return 0;
    }

    private void setRecyclerViewNewTable() {
        Toast.makeText(getApplicationContext(), "setrecyclerview new table", 1).show();
        this.adapter2 = new SyncTableAdapter(this, this, this.voterDataNewModelList2, false);
        this.recyclerView.setAdapter(this.adapter2);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        UserListScreen();
    }

    private void sync(List<Integer> voters) {
        for (int i = 0; i < voters.size(); i++) {
            updateSingleRow(this.voterDataNewModelList2.get(voters.get(i).intValue()).getEPIC_NO());
        }
    }

    private void UserListScreen() {
        Intent i = new Intent(this, ListUserActivity.class);
        i.setFlags(i.getFlags() | 1073741824);
        startActivity(i);
        finish();
    }

    private void updateSingleRow(final String voterid) {
        VoterDataNewModel voter = this.db.getVoter(voterid);
        String id_documentimage = voter.getID_DOCUMENT_IMAGE();
        String voteD = voter.getVOTED();
        voter.getVOTING_DATE();
        HashMap<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("fpstring", "yahoo");
        map.put("VOTED", voteD);
        map.put("ID_DOCUMENT_IMAGE", id_documentimage);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postDBUpdate(map).enqueue(new Callback<DBUpdateResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.5
            @Override // retrofit2.Callback
            public void onResponse(Call<DBUpdateResponse> call, Response<DBUpdateResponse> response) {
                Context applicationContext = SyncActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Success voterid updated" + voterid, 1).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<DBUpdateResponse> call, Throwable t) {
                Context applicationContext = SyncActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Exception " + t.getMessage(), 1).show();
            }
        });
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.SyncTableAdapter.OnItemClickListener
    public void updateThisRow2(int position, String voterid) {
        this.db.getVoter(voterid);
    }

    public void updateThisRow(int position, String voterid) {
        Context applicationContext = getApplicationContext();
        Toast.makeText(applicationContext, "voterid=" + voterid + " clicked", 1).show();
        Cursor cursor = this.db.SingleUserRowByVoterId(voterid);
        String age = "";
        if (cursor.moveToFirst()) {
            do {
                try {
                    age = cursor.getString(cursor.getColumnIndex(HttpHeaders.AGE));
                } catch (Exception e) {
                    PrintStream printStream = System.out;
                    printStream.println("updatethisrow Exception " + e.getMessage());
                    Context applicationContext2 = getApplicationContext();
                    Toast.makeText(applicationContext2, "showUserInfoIncaseofMatchOffline exception=" + e.getMessage(), 1).show();
                }
                Context applicationContext3 = getApplicationContext();
                Toast.makeText(applicationContext3, "showUserInfoIncaseofMatchOffline age=" + age, 1).show();
            } while (cursor.moveToNext());
        }
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.SyncTableAdapter.OnItemClickListener
    public void makeLockButtonsVisible(Boolean visible) {
    }

    private void updateSingleRowinServer() {
    }

    private void updateWithImagePrepareReqMap(String voterid) {
        VoterDataNewModel voter = this.db.getVoter(voterid);
        String id_documentimage = voter.getID_DOCUMENT_IMAGE();
        String voted = voter.getVOTED();
        voter.getVOTING_DATE();
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("VOTED", createPartFromString(voted));
        map.put("fpstring", createPartFromString("pappu"));
        map.put("ID_DOCUMENT_IMAGE", createPartFromString(id_documentimage));
        uploadImage22september(map, id_documentimage);
    }

    private void uploadImage22september(HashMap<String, RequestBody> map, String imagename) {
        File file = new File("/sdcard/" + Const.PublicImageName + "/", imagename);
        try {
            BitmapFactory.decodeFile(file.getPath()).compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(file));
        } catch (Throwable t) {
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postUpdateDBrow(MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)), map).enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.6
            @Override // retrofit2.Callback
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                Toast.makeText(SyncActivity.this.getApplicationContext(), "upload image successfully", 0).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ImageUploadResponse> call, Throwable t2) {
                Context applicationContext = SyncActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Exceptoin upload image failure" + t2.getMessage(), 0).show();
            }
        });
    }

    private void uploadImage() {
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = new CursorLoader(this, contentUri, new String[]{"_data"}, null, null, null).loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    private synchronized void postDataWithImage(HashMap<String, RequestBody> map, File file) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVoterIdentification(MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, file.getName(), new ProgressRequestBody(file, "jpg", this)), map).enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.7
            @Override // retrofit2.Callback
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                if (!response.body().isAdded().booleanValue()) {
                    Toast.makeText(SyncActivity.this.getApplicationContext(), "Not verified.Try again", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                Log.d("taag", t.getMessage());
            }
        });
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onProgressUpdate(int percentage) {
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onError() {
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onFinish() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void CompareServerToLocal(List<VoterDataNewModel> localVoter, List<VoterDataNewModel> voterFromServer) {
        this.voterlist = new ArrayList();
        Set<Integer> hash_Set = new HashSet<>();
        if (localVoter != null && localVoter.size() > 0 && voterFromServer != null && voterFromServer.size() > 0) {
            for (int i = 0; i < localVoter.size(); i++) {
                if (localVoter.get(i).getVOTED().equalsIgnoreCase(DiskLruCache.VERSION_1) || localVoter.get(i).getVOTED().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D) || localVoter.get(i).getVOTED().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                    int j = 0;
                    while (true) {
                        if (j >= voterFromServer.size()) {
                            break;
                        } else if (voterFromServer.get(j).getEPIC_NO().equalsIgnoreCase(localVoter.get(i).getEPIC_NO())) {
                            if (!localVoter.get(i).getVOTED().equalsIgnoreCase(voterFromServer.get(j).getVOTED())) {
                                hash_Set.add(Integer.valueOf(i));
                            }
                            if ((voterFromServer.get(j).getID_DOCUMENT_IMAGE() == null || voterFromServer.get(j).getID_DOCUMENT_IMAGE().length() == 0) && localVoter.get(i).getID_DOCUMENT_IMAGE() != null && localVoter.get(i).getID_DOCUMENT_IMAGE().length() > 0) {
                                hash_Set.add(Integer.valueOf(i));
                            }
                        } else {
                            j++;
                        }
                    }
                }
            }
            for (Integer x : hash_Set) {
                this.voterlist.add(x);
            }
        }
        List<Integer> list = this.voterlist;
        if (list == null || list.size() <= 0) {
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "Total voters " + this.voterlist.size(), 0).show();
            return;
        }
        sync(this.voterlist);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVoterListForSyncComparison(String dist, String block, String panchayatId, String ward, String booth) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListTableForSync(dist, block, panchayatId, ward, booth).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SyncActivity.8
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                SyncActivity.this.voterListFromServer = response.body().getVoters();
                SyncActivity syncActivity = SyncActivity.this;
                syncActivity.CompareServerToLocal(syncActivity.voterDataNewModelList2, SyncActivity.this.voterListFromServer);
                Context applicationContext = SyncActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Voters received from server " + SyncActivity.this.voterListFromServer.size(), 0).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListNewTableGetResponse> call, Throwable t) {
                Context applicationContext = SyncActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Error  from server " + t.getMessage(), 0).show();
            }
        });
    }

    private void exportCSV() {
    }

    private void uploadTransactionTable() {
    }
}
