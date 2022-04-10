package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.adapter.VoterListAdapter;
import com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataModel;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.DBUpdateResponse;
import com.example.aadhaarfpoffline.tatvik.network.FinperprintCompareServerResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterListGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterListNewTableGetResponse;
import com.example.aadhaarfpoffline.tatvik.servece.LocationTrack;
import com.google.android.material.navigation.NavigationView;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import okhttp3.internal.cache.DiskLruCache;
import org.tatvik.fp.CaptureResult;
import org.tatvik.fp.TMF20API;
import org.tatvik.fp.TMF20ErrorCodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class ListUserActivity extends AppCompatActivity implements VoterListAdapter.OnItemClickListener, MFS100Event, NavigationView.OnNavigationItemSelectedListener, VoterListNewTableAdapter.OnItemClickListener, Observer {
    private static final int PERMISSION_CODE;
    private static long Threshold = 1500;
    private VoterListAdapter adapter;
    private VoterListNewTableAdapter adapter2;
    private Button alternateButton;
    private TextView blockBooth;
    CaptureResult captRslt1;
    private Button captureFingerprint;
    CheckBox cbFastDetection;
    Context context;
    DBHelper db;
    private DrawerLayout drawer;
    byte[] finger_template1;
    Handler handler;
    Uri imageUri;
    ImageView imgFinger;
    private Button insertFpButton;
    LocationTrack locationTrack;
    private Button lockButton;
    private LinearLayout lockLayout;
    private Button loginButton;
    private Context mcontext;
    private TextView numVoters;
    private ProgressBar progressBar;
    Runnable r;
    private RecyclerView recyclerView;
    Resources resources;
    private EditText search;
    private Button searchButton;
    private TextView stateDistrict;
    TMF20API tmf20lib;
    UserAuth userAuth;
    private List<VoterDataModel> voterDataModelList;
    private List<VoterDataNewModel> voterDataNewModelList;
    private List<VoterDataNewModel> voterDataNewModelList2;
    private List<VoterDataNewModel> voterListFromServer;
    List<Integer> voterlist;
    private boolean isCaptureRunning = false;
    private FingerData lastCapFingerData = null;
    int timeout = 10000;
    MFS100 mfs100 = null;
    private boolean isLockLayoutVisible = true;
    private int IMAGE_CAPTURE_CODE = 1001;
    private long mLastAttTime = 0;
    long mLastDttTime = 0;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterlist);
        this.context = LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(this.resources.getString(R.string.menu_text));
        this.userAuth = new UserAuth(getApplicationContext());
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        this.imgFinger = (ImageView) findViewById(R.id.imgFinger);
        this.cbFastDetection = (CheckBox) findViewById(R.id.cbFastDetection);
        this.captureFingerprint = (Button) findViewById(R.id.button_capture);
        this.insertFpButton = (Button) findViewById(R.id.button_insert_record);
        this.lockButton = (Button) findViewById(R.id.button_lock_record);
        this.alternateButton = (Button) findViewById(R.id.button_alternate_record);
        this.alternateButton.setVisibility(8);
        this.lockLayout = (LinearLayout) findViewById(R.id.locklayout);
        if (this.userAuth.ifLocked().booleanValue()) {
            this.lockButton.setVisibility(8);
            this.insertFpButton.setVisibility(0);
            this.alternateButton.setVisibility(8);
        } else {
            this.lockButton.setVisibility(0);
            this.insertFpButton.setVisibility(8);
            this.alternateButton.setVisibility(0);
        }
        if (this.userAuth.ifLockedVisible().booleanValue()) {
            this.lockLayout.setVisibility(0);
        } else {
            this.lockLayout.setVisibility(8);
        }
        this.progressBar.setVisibility(8);
        findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ListUserActivity.this.drawer.isDrawerOpen(GravityCompat.END)) {
                    ListUserActivity.this.drawer.closeDrawer(GravityCompat.END);
                } else {
                    ListUserActivity.this.drawer.openDrawer(GravityCompat.END);
                }
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        this.drawer.addDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.locationTrack = LocationTrack.getInstance(this);
        String loct = this.userAuth.getBoothLocation();
        if (loct.equalsIgnoreCase("")) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.setFlags(i.getFlags() | 1073741824);
            startActivity(i);
            finish();
        }
        this.locationTrack.setBoothLocation(getLocationFromString(loct));
        this.locationTrack.setPhone(this.userAuth.getPhone());
        this.handler = new Handler();
        this.r = new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.2
            @Override // java.lang.Runnable
            public void run() {
                Log.d("userinactivetag", "userinactive");
                ListUserActivity.this.locationTrack.locationUpdateWithAction(null, "idle");
            }
        };
        startHandler();
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_vendor_list);
        this.userAuth.getBoothId();
        this.search = (EditText) findViewById(R.id.search_text);
        this.search.setHint(this.resources.getString(R.string.all_voters_text));
        this.search.setHintTextColor(Color.parseColor("#ffffff"));
        this.stateDistrict = (TextView) findViewById(R.id.state_district);
        this.blockBooth = (TextView) findViewById(R.id.block_booth);
        this.numVoters = (TextView) findViewById(R.id.num_voters);
        this.searchButton = (Button) findViewById(R.id.search_button);
        this.searchButton.setText(this.resources.getString(R.string.search));
        this.loginButton = (Button) findViewById(R.id.logout_button);
        this.loginButton.setVisibility(8);
        this.captureFingerprint.setText(this.resources.getString(R.string.capture_text));
        this.lockButton.setText(this.resources.getString(R.string.lock_text));
        this.insertFpButton.setText(this.resources.getString(R.string.insert_record_text));
        this.alternateButton.setText(this.resources.getString(R.string.alternate_text));
        this.loginButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ListUserActivity listUserActivity = ListUserActivity.this;
                listUserActivity.setlogin(listUserActivity.userAuth, false);
            }
        });
        this.searchButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ListUserActivity.this.search(ListUserActivity.this.search.getText().toString());
            }
        });
        this.voterDataModelList = new ArrayList();
        this.voterDataNewModelList = new ArrayList();
        this.progressBar.setVisibility(0);
        getVoterListNewTableByBooth(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo());
        try {
            this.mfs100 = new MFS100(this);
            this.mfs100.SetApplicationContext(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tmf20lib = new TMF20API(this);
        this.captureFingerprint.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ListUserActivity.this.captureFingerPrintFromTatvik();
            }
        });
        this.insertFpButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ListUserActivity.this.captRslt1 != null && ListUserActivity.this.captRslt1.getFmrBytes() != null) {
                    ListUserActivity listUserActivity = ListUserActivity.this;
                    listUserActivity.insertFpBoothOfficer(listUserActivity.captRslt1.getFmrBytes());
                }
            }
        });
        this.lockButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ListUserActivity.this.captRslt1 == null || ListUserActivity.this.captRslt1.getFmrBytes() == null || ListUserActivity.this.captRslt1.getFmrBytes().length <= 0) {
                    Toast.makeText(ListUserActivity.this.getApplicationContext(), "Please capture fingerprint first Locak", 1).show();
                    return;
                }
                ListUserActivity listUserActivity = ListUserActivity.this;
                if (listUserActivity.compareAndLockTatvik(listUserActivity.finger_template1).booleanValue()) {
                    ListUserActivity.this.userAuth.setLock(true);
                    ListUserActivity.this.lockButton.setVisibility(8);
                    ListUserActivity.this.insertFpButton.setVisibility(0);
                    return;
                }
                ListUserActivity.this.userAuth.setLock(false);
            }
        });
        this.alternateButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
            }
        });
    }

    public void setlogin(UserAuth userAuth, Boolean login) {
        userAuth.setLogin(login);
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    public void stopHandler() {
        this.handler.removeCallbacks(this.r);
    }

    public void startHandler() {
        this.handler.postDelayed(this.r, 15000);
    }

    public void search(String text) {
        List<VoterDataNewModel> voterList = new ArrayList<>();
        if (text == null || text.isEmpty() || text.length() == 0) {
            VoterListNewTableAdapter voterListNewTableAdapter = this.adapter2;
            if (voterListNewTableAdapter != null) {
                voterListNewTableAdapter.setNewData(this.voterDataNewModelList);
                return;
            }
            return;
        }
        for (int i = 0; i < this.voterDataNewModelList.size(); i++) {
            if ((this.voterDataNewModelList.get(i).getFM_NAME_EN() != null && this.voterDataNewModelList.get(i).getFM_NAME_EN().toLowerCase().contains(text.toLowerCase())) || ((this.voterDataNewModelList.get(i).getLASTNAME_EN() != null && this.voterDataNewModelList.get(i).getLASTNAME_EN().toLowerCase().contains(text.toLowerCase())) || ((this.voterDataNewModelList.get(i).getFM_NAME_V1() != null && this.voterDataNewModelList.get(i).getFM_NAME_V1().toLowerCase().contains(text.toLowerCase())) || ((this.voterDataNewModelList.get(i).getLASTNAME_V1() != null && this.voterDataNewModelList.get(i).getLASTNAME_V1().toLowerCase().contains(text.toLowerCase())) || (this.voterDataNewModelList.get(i).getSlNoInWard() != null && this.voterDataNewModelList.get(i).getSlNoInWard().toLowerCase().contains(text.toLowerCase())))))) {
                voterList.add(this.voterDataNewModelList.get(i));
            }
        }
        this.adapter2.setNewData(voterList);
    }

    private void getdata() {
        for (int i = 0; i < 10; i++) {
            VoterDataModel voterDataModel = new VoterDataModel();
            voterDataModel.setCreatedAt("121 A/3 Katwaria sarai 110016" + i);
            voterDataModel.setVoterId("Napo san" + i);
            voterDataModel.setBlockNo("napo@yeputuye.com" + i);
            voterDataModel.setGSTIN("gst2345_" + i);
            voterDataModel.setId("id435_" + i);
            voterDataModel.setPAM("pam123_" + i);
            voterDataModel.setDistrict("punjab_" + i);
            voterDataModel.setBooth_id("punjabstateid_" + i);
            voterDataModel.setVoter_name("vendor_" + i);
            this.voterDataModelList.add(voterDataModel);
        }
        setRecyclerView();
    }

    public void setRecyclerView() {
        this.adapter = new VoterListAdapter(this, this, this.voterDataModelList);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    public void setRecyclerViewNewTable() {
        Toast.makeText(getApplicationContext(), "setrecyclerview new table1", 1).show();
        this.adapter2 = new VoterListNewTableAdapter(this, this, this.voterDataNewModelList, false);
        this.recyclerView.setAdapter(this.adapter2);
        LinearLayoutManager linearVertical = new LinearLayoutManager(this, 1, false);
        this.progressBar.setVisibility(8);
        this.recyclerView.setLayoutManager(linearVertical);
    }

    private void fetchUsersData() {
        getVoterList();
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListAdapter.OnItemClickListener
    public void onItemClick(int position) {
        Intent intent = new Intent(this, UserIdCaptureActivity.class);
        intent.putExtra("voter_name", this.voterDataModelList.get(position).getVoter_name());
        intent.putExtra("voter_id", this.voterDataModelList.get(position).getVoterId());
        intent.putExtra("district", this.voterDataModelList.get(position).getDistrict());
        intent.putExtra("blockno", this.voterDataModelList.get(position).getBlockNo());
        intent.putExtra("blockid", this.voterDataModelList.get(position).getBooth_id());
        startActivity(intent);
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListAdapter.OnItemClickListener
    public void onItemClick2(int position) {
        Intent intent = new Intent(this, UserIdCaptureActivity.class);
        intent.putExtra("voter_name", this.voterDataModelList.get(position).getVoter_name());
        intent.putExtra("voter_id", this.voterDataModelList.get(position).getVoterId());
        intent.putExtra("district", this.voterDataModelList.get(position).getDistrict());
        intent.putExtra("blockno", this.voterDataModelList.get(position).getBlockNo());
        intent.putExtra("blockid", this.voterDataModelList.get(position).getBooth_id());
        startActivity(intent);
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter.OnItemClickListener
    public void onItemClick3(int pos, String voterid) {
        int position = -1;
        int i = 0;
        while (true) {
            if (i >= this.voterDataNewModelList.size()) {
                break;
            } else if (this.voterDataNewModelList.get(i).getEPIC_NO().equalsIgnoreCase(voterid)) {
                position = i;
                break;
            } else {
                i++;
            }
        }
        if (position > -1) {
            Intent intent = new Intent(this, UserIdCaptureActivity.class);
            intent.putExtra("voter_name", this.voterDataNewModelList.get(position).getFM_NAME_EN() + " " + this.voterDataNewModelList.get(position).getLASTNAME_EN());
            intent.putExtra("voter_id", this.voterDataNewModelList.get(position).getEPIC_NO());
            intent.putExtra("district", this.voterDataNewModelList.get(position).getDIST_NO());
            intent.putExtra("blockno", this.voterDataNewModelList.get(position).getWardNo());
            intent.putExtra("blockid", this.voterDataNewModelList.get(position).getBlockID());
            intent.putExtra("voted", this.voterDataNewModelList.get(position).getVOTED());
            startActivity(intent);
            return;
        }
        Toast.makeText(getApplicationContext(), "Voterid not retroeved properly ", 1).show();
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter.OnItemClickListener
    public void makeLockButtonsVisible(Boolean visible) {
    }

    private void getVoterList() {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterList().enqueue(new Callback<VoterListGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.9
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListGetResponse> call, Response<VoterListGetResponse> response) {
                ListUserActivity.this.voterDataModelList = response.body().getVoters();
                ListUserActivity.this.setRecyclerView();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListGetResponse> call, Throwable t) {
            }
        });
    }

    private void getVoterListByBooth(String boothid) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListByBoothId(boothid).enqueue(new Callback<VoterListGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.10
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListGetResponse> call, Response<VoterListGetResponse> response) {
                ListUserActivity.this.voterDataModelList = response.body().getVoters();
                TextView textView = ListUserActivity.this.stateDistrict;
                textView.setText(ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + response.body().getVoters().get(0).getDistrict());
                TextView textView2 = ListUserActivity.this.blockBooth;
                textView2.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + response.body().getVoters().get(0).getBlockNo() + ",Booth No:" + response.body().getVoters().get(0).getBooth_id());
                TextView textView3 = ListUserActivity.this.numVoters;
                StringBuilder sb = new StringBuilder();
                sb.append(ListUserActivity.this.resources.getString(R.string.total_no_voters_text));
                sb.append(":");
                sb.append(response.body().getVoters().size());
                textView3.setText(sb.toString());
                ListUserActivity.this.setRecyclerView();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListGetResponse> call, Throwable t) {
            }
        });
    }

    private void getVoterListNewTableByBooth(String dist, String block, String panchayatId, String ward, String booth) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListNewTableByBoothNo(dist, block, panchayatId, ward, booth).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.11
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                Log.d("basictag success", response.toString());
                if (response != null) {
                    try {
                        if (response.isSuccessful() && response.body() != null && response.body().getVoters() != null && !response.body().getVoters().isEmpty() && response.body().getVoters().size() > 0) {
                            ListUserActivity.this.voterDataNewModelList = response.body().getVoters();
                            TextView textView = ListUserActivity.this.stateDistrict;
                            textView.setText(ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + response.body().getVoters().get(0).getDIST_NO());
                            TextView textView2 = ListUserActivity.this.blockBooth;
                            textView2.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + response.body().getVoters().get(0).getBlockID() + "," + ListUserActivity.this.resources.getString(R.string.ward_no_text) + ":" + response.body().getVoters().get(0).getWardNo());
                            TextView textView3 = ListUserActivity.this.numVoters;
                            StringBuilder sb = new StringBuilder();
                            sb.append(ListUserActivity.this.resources.getString(R.string.total_no_voters_text));
                            sb.append(":");
                            sb.append(response.body().getVoters().size());
                            textView3.setText(sb.toString());
                            ListUserActivity.this.setRecyclerViewNewTable();
                            ListUserActivity.this.insertUsersOffline();
                            return;
                        }
                    } catch (Exception e) {
                        while (true) {
                            return;
                        }
                    }
                }
                Toast.makeText(ListUserActivity.this.getApplicationContext(), "No data available for this user", 1).show();
                ListUserActivity.this.progressBar.setVisibility(8);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListNewTableGetResponse> call, Throwable t) {
                ListUserActivity.this.progressBar.setVisibility(8);
                Log.d("basictag failure", t.getMessage());
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    ListUserActivity listUserActivity = ListUserActivity.this;
                    listUserActivity.voterDataNewModelList = listUserActivity.db.getAllElements();
                    TextView textView = ListUserActivity.this.stateDistrict;
                    textView.setText(ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ((VoterDataNewModel) ListUserActivity.this.voterDataNewModelList.get(0)).getDIST_NO());
                    TextView textView2 = ListUserActivity.this.blockBooth;
                    textView2.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + ((VoterDataNewModel) ListUserActivity.this.voterDataNewModelList.get(0)).getBlockID() + "," + ListUserActivity.this.resources.getString(R.string.ward_no_text) + ":" + ((VoterDataNewModel) ListUserActivity.this.voterDataNewModelList.get(0)).getWardNo());
                    TextView textView3 = ListUserActivity.this.numVoters;
                    StringBuilder sb = new StringBuilder();
                    sb.append(ListUserActivity.this.resources.getString(R.string.total_no_voters_text));
                    sb.append(":");
                    sb.append(ListUserActivity.this.voterDataNewModelList.size());
                    textView3.setText(sb.toString());
                    ListUserActivity.this.setRecyclerViewNewTable();
                }
            }
        });
    }

    private void getVoterListNewTableByWard(String ward) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListNewTableByWard(ward).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.12
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                Log.d("basictag success", response.toString());
                if (response == null || !response.isSuccessful() || response.body() == null || response.body().getVoters() == null || response.body().getVoters().isEmpty() || response.body().getVoters().size() <= 0) {
                    Toast.makeText(ListUserActivity.this.getApplicationContext(), "No data available for this user", 1).show();
                    ListUserActivity.this.progressBar.setVisibility(8);
                    return;
                }
                ListUserActivity.this.voterDataNewModelList = response.body().getVoters();
                TextView textView = ListUserActivity.this.stateDistrict;
                textView.setText(ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + response.body().getVoters().get(0).getDIST_NO());
                TextView textView2 = ListUserActivity.this.blockBooth;
                textView2.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + response.body().getVoters().get(0).getBlockID() + "," + ListUserActivity.this.resources.getString(R.string.ward_no_text) + ":" + response.body().getVoters().get(0).getWardNo());
                TextView textView3 = ListUserActivity.this.numVoters;
                StringBuilder sb = new StringBuilder();
                sb.append(ListUserActivity.this.resources.getString(R.string.total_no_voters_text));
                sb.append(":");
                sb.append(response.body().getVoters().size());
                textView3.setText(sb.toString());
                ListUserActivity.this.setRecyclerViewNewTable();
                ListUserActivity.this.insertUsersOffline();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListNewTableGetResponse> call, Throwable t) {
                ListUserActivity.this.progressBar.setVisibility(8);
                Log.d("basictag failure", t.getMessage());
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    ListUserActivity listUserActivity = ListUserActivity.this;
                    listUserActivity.voterDataNewModelList = listUserActivity.db.getAllElements();
                    TextView textView = ListUserActivity.this.stateDistrict;
                    textView.setText(ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ((VoterDataNewModel) ListUserActivity.this.voterDataNewModelList.get(0)).getDIST_NO());
                    TextView textView2 = ListUserActivity.this.blockBooth;
                    textView2.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + ((VoterDataNewModel) ListUserActivity.this.voterDataNewModelList.get(0)).getBlockID() + "," + ListUserActivity.this.resources.getString(R.string.ward_no_text) + ":" + ((VoterDataNewModel) ListUserActivity.this.voterDataNewModelList.get(0)).getWardNo());
                    TextView textView3 = ListUserActivity.this.numVoters;
                    StringBuilder sb = new StringBuilder();
                    sb.append(ListUserActivity.this.resources.getString(R.string.total_no_voters_text));
                    sb.append(":");
                    sb.append(ListUserActivity.this.voterDataNewModelList.size());
                    textView3.setText(sb.toString());
                    ListUserActivity.this.setRecyclerViewNewTable();
                }
            }
        });
    }

    private void getVoterListNewTableByBlock(String block) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListNewTableByPanchayatId(block).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.13
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                Log.d("basictag success", response.toString());
                ListUserActivity.this.voterDataNewModelList = response.body().getVoters();
                TextView textView = ListUserActivity.this.stateDistrict;
                textView.setText(ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + response.body().getVoters().get(0).getDIST_NO());
                TextView textView2 = ListUserActivity.this.blockBooth;
                textView2.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + response.body().getVoters().get(0).getBlockID() + ",Booth No:" + response.body().getVoters().get(0).getPanchayatID());
                TextView textView3 = ListUserActivity.this.numVoters;
                StringBuilder sb = new StringBuilder();
                sb.append(ListUserActivity.this.resources.getString(R.string.total_no_voters_text));
                sb.append(":");
                sb.append(response.body().getVoters().size());
                textView3.setText(sb.toString());
                ListUserActivity.this.setRecyclerViewNewTable();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListNewTableGetResponse> call, Throwable t) {
                ListUserActivity.this.progressBar.setVisibility(8);
                Log.d("basictag failure", t.getMessage());
            }
        });
    }

    private Location getLocationFromString(String location) {
        String[] longilati = location.split(":", 2);
        Double boothLong = Double.valueOf(Double.parseDouble(longilati[0]));
        Double boothLat = Double.valueOf(Double.parseDouble(longilati[1]));
        Location boothLocation = new Location("BoothLocation");
        boothLocation.setLatitude(boothLat.doubleValue());
        boothLocation.setLongitude(boothLong.doubleValue());
        return boothLocation;
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        if (SystemClock.elapsedRealtime() - this.mLastAttTime >= Threshold) {
            this.mLastAttTime = SystemClock.elapsedRealtime();
            if (hasPermission) {
                if (vid == 1204 || vid == 11279) {
                    try {
                        if (pid == 34323) {
                            this.mfs100.LoadFirmware();
                        } else if (pid == 4101) {
                            this.mfs100.Init();
                        }
                    } catch (Exception e) {
                        while (true) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnDeviceDetached() {
        try {
            if (SystemClock.elapsedRealtime() - this.mLastDttTime >= Threshold) {
                this.mLastDttTime = SystemClock.elapsedRealtime();
                UnInitScanner();
            }
        } catch (Exception e) {
        }
    }

    private void UnInitScanner() {
        try {
            if (this.mfs100.UnInit() == 0) {
                this.lastCapFingerData = null;
            }
        } catch (Exception e) {
            Log.e("UnInitScanner.EX", e.toString());
        }
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnHostCheckFailed(String err) {
    }

    private void StartSyncCapture() {
        new Thread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.14
            @Override // java.lang.Runnable
            public void run() {
                ListUserActivity.this.isCaptureRunning = true;
                try {
                    final FingerData fingerData = new FingerData();
                    int ret = ListUserActivity.this.mfs100.AutoCapture(fingerData, ListUserActivity.this.timeout, ListUserActivity.this.cbFastDetection.isChecked());
                    Log.e("StartSyncCapture.RET", "" + ret);
                    if (ret == 0) {
                        ListUserActivity.this.lastCapFingerData = fingerData;
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);
                        ListUserActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.14.1
                            @Override // java.lang.Runnable
                            public void run() {
                                ListUserActivity.this.imgFinger.setImageBitmap(bitmap);
                                ListUserActivity.this.finger_template1 = new byte[fingerData.ISOTemplate().length];
                                System.arraycopy(fingerData.ISOTemplate(), 0, ListUserActivity.this.finger_template1, 0, fingerData.ISOTemplate().length);
                            }
                        });
                        String str = "\nQuality: " + fingerData.Quality() + "\nNFIQ: " + fingerData.Nfiq() + "\nWSQ Compress Ratio: " + fingerData.WSQCompressRatio() + "\nImage Dimensions (inch): " + fingerData.InWidth() + "\" X " + fingerData.InHeight() + "\"\nImage Area (inch): " + fingerData.InArea() + "\"\nResolution (dpi/ppi): " + fingerData.Resolution() + "\nGray Scale: " + fingerData.GrayScale() + "\nBits Per Pixal: " + fingerData.Bpp() + "\nWSQ Info: " + fingerData.WSQInfo();
                    }
                } catch (Exception e) {
                } catch (Throwable th) {
                    ListUserActivity.this.isCaptureRunning = false;
                    throw th;
                }
                ListUserActivity.this.isCaptureRunning = false;
            }
        }).start();
    }

    @Override // com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_attendance) {
            Toast.makeText(getApplicationContext(), "Attendance is clicked", 0).show();
        } else if (id == R.id.nav_project) {
            Toast.makeText(getApplicationContext(), "Project is clicked", 0).show();
        } else if (id == R.id.nav_english) {
            Toast.makeText(getApplicationContext(), "English is clicked", 0).show();
            translate("en");
        } else if (id == R.id.nav_hindi) {
            Toast.makeText(getApplicationContext(), "Hindi is clicked", 0).show();
            translate("hi");
        } else if (id == R.id.nav_get_offline) {
            Toast.makeText(getApplicationContext(), "Get Offline is clicked", 0).show();
            insertUsersOffline();
        } else if (id == R.id.nav_count_offline) {
            Toast.makeText(getApplicationContext(), "Offline count", 0).show();
            getsqlitedata();
        } else if (id == R.id.nav_sync) {
            getVoterListForSyncComparison(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo());
        } else if (id == R.id.nav_lock) {
            Toast.makeText(getApplicationContext(), "Lock clicked", 0).show();
            if (this.userAuth.ifLockedVisible().booleanValue()) {
                this.isLockLayoutVisible = false;
                this.lockLayout.setVisibility(8);
                this.userAuth.setLockVisible(false);
            } else {
                this.isLockLayoutVisible = true;
                this.lockLayout.setVisibility(0);
                this.userAuth.setLockVisible(true);
            }
        } else if (id == R.id.nav_crash_check) {
            throw new RuntimeException("Test Crash");
        } else if (id == R.id.nav_logout) {
            setlogin(this.userAuth, false);
            startLoginActivity();
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.END);
        return true;
    }

    private void startSyncActivity() {
        startActivity(new Intent(this, SyncActivity.class));
    }

    public void insertUsersOffline() {
        List<VoterDataNewModel> offlinevoter = this.voterDataNewModelList;
        for (int i = 0; i < offlinevoter.size(); i++) {
            ContentValues cols = new ContentValues();
            cols.put("ID", offlinevoter.get(i).getId());
            cols.put("DIST_NO", offlinevoter.get(i).getDIST_NO());
            cols.put("AC_NO", offlinevoter.get(i).getAC_NO());
            cols.put("PART_NO", offlinevoter.get(i).getPART_NO());
            cols.put("SECTION_NO", offlinevoter.get(i).getSECTION_NO());
            cols.put("SLNOINPART", offlinevoter.get(i).getSLNOINPART());
            cols.put("C_HOUSE_NO", offlinevoter.get(i).getC_HOUSE_NO());
            cols.put("C_HOUSE_NO_V1", offlinevoter.get(i).getC_HOUSE_NO_V1());
            cols.put("FM_NAME_EN", offlinevoter.get(i).getFM_NAME_EN());
            cols.put("LASTNAME_EN", offlinevoter.get(i).getLASTNAME_EN());
            cols.put("FM_NAME_V1", offlinevoter.get(i).getFM_NAME_V1());
            cols.put("LASTNAME_V1", offlinevoter.get(i).getLASTNAME_V1());
            cols.put("RLN_TYPE", offlinevoter.get(i).getRLN_TYPE());
            cols.put("STATUS_TYPE", offlinevoter.get(i).getSTATUS_TYPE());
            cols.put("RLN_L_NM_EN", offlinevoter.get(i).getRLN_L_NM_EN());
            cols.put("RLN_FM_NM_V1", offlinevoter.get(i).getRLN_FM_NM_V1());
            cols.put("RLN_L_NM_V1", offlinevoter.get(i).getRLN_L_NM_V1());
            cols.put("EPIC_NO", offlinevoter.get(i).getEPIC_NO());
            cols.put("RLN_FM_NM_EN", offlinevoter.get(i).getRLN_FM_NM_EN());
            cols.put("GENDER", offlinevoter.get(i).getGENDER());
            cols.put("AGE", "age" + offlinevoter.get(i).getAge());
            cols.put("DOB", offlinevoter.get(i).getDOB());
            cols.put("EMAIL_ID", offlinevoter.get(i).getEMAIL_ID());
            cols.put("MOBILE_NO", offlinevoter.get(i).getMOBILE_NO());
            cols.put("ELECTOR_TYPE", offlinevoter.get(i).getELECTOR_TYPE());
            cols.put("BlockID", offlinevoter.get(i).getBlockID());
            cols.put("PanchayatID", offlinevoter.get(i).getPanchayatID());
            cols.put("VillageName", offlinevoter.get(i).getVillageName());
            cols.put("WardNo", offlinevoter.get(i).getWardNo());
            cols.put("SlNoInWard", offlinevoter.get(i).getSlNoInWard());
            cols.put("UserId", offlinevoter.get(i).getUserId());
            cols.put("VOTED", offlinevoter.get(i).getVOTED());
            cols.put("FACE_MATCH", offlinevoter.get(i).getFACE_MATCH());
            cols.put("VOTER_IMAGE", offlinevoter.get(i).getVOTER_IMAGE());
            cols.put("VOTER_FINGERPRINT", offlinevoter.get(i).getVOTER_FINGERPRINT());
            cols.put("ID_DOCUMENT_IMAGE", offlinevoter.get(i).getID_DOCUMENT_IMAGE());
            cols.put("FINGERPRINT_MATCH", offlinevoter.get(i).getFINGERPRINT_MATCH());
            cols.put("VOTING_DATE", offlinevoter.get(i).getVOTING_DATE());
            cols.put("AADHAAR_MATCH", offlinevoter.get(i).getAADHAAR_MATCH());
            cols.put("AADHAAR_NO", offlinevoter.get(i).getAC_NO());
            DBHelper dBHelper = this.db;
            long res = dBHelper.insertData(dBHelper.tbl_registration_master, cols);
            if (res > 0) {
                PrintStream printStream = System.out;
                printStream.println("datainserted true i=" + i + " res=" + res);
            } else {
                PrintStream printStream2 = System.out;
                printStream2.println("datainserted false i=" + i + " res=" + res);
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.END)) {
            this.drawer.closeDrawer(GravityCompat.END);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        try {
            if (this.mfs100 == null) {
                this.mfs100 = new MFS100(this);
                this.mfs100.SetApplicationContext(this);
            } else {
                InitScanner();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }

    private void InitScanner() {
        try {
            if (this.mfs100.Init() == 0) {
                String str = "Serial: " + this.mfs100.GetDeviceInfo().SerialNo() + " Make: " + this.mfs100.GetDeviceInfo().Make() + " Model: " + this.mfs100.GetDeviceInfo().Model() + "\nCertificate: " + this.mfs100.GetCertification();
            }
        } catch (Exception e) {
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivityNew.class));
    }

    private void translate(String lan) {
        LocaleHelper.setLocale(this, lan);
        this.context = LocaleHelper.setLocale(this, lan);
        this.resources = this.context.getResources();
        this.searchButton.setText(this.resources.getString(R.string.search));
        this.search.setHint(this.resources.getString(R.string.all_voters_text));
        setTitle(this.resources.getString(R.string.menu_text));
        this.captureFingerprint.setText(this.resources.getString(R.string.capture_text));
        List<VoterDataNewModel> list = this.voterDataNewModelList;
        if (list != null && !list.isEmpty() && this.voterDataNewModelList.size() > 0) {
            TextView textView = this.stateDistrict;
            textView.setText(this.resources.getString(R.string.district_name_text) + ":" + this.voterDataNewModelList.get(0).getDIST_NO());
            TextView textView2 = this.blockBooth;
            textView2.setText(this.resources.getString(R.string.block_no_text) + ":" + this.voterDataNewModelList.get(0).getBlockID() + "," + this.resources.getString(R.string.ward_no_text) + ":" + this.voterDataNewModelList.get(0).getWardNo());
            TextView textView3 = this.numVoters;
            StringBuilder sb = new StringBuilder();
            sb.append(this.resources.getString(R.string.total_no_voters_text));
            sb.append(":");
            sb.append(this.voterDataNewModelList.size());
            textView3.setText(sb.toString());
            this.adapter2.changeLanguage(lan);
        }
        this.lockButton.setText(this.resources.getString(R.string.lock_text));
        this.insertFpButton.setText(this.resources.getString(R.string.insert_record_text));
        this.alternateButton.setText(this.resources.getString(R.string.alternate_text));
    }

    private long getsqlitedata() {
        long count = this.db.getUsersCount();
        Context baseContext = getBaseContext();
        Toast.makeText(baseContext, "Count=" + count, 1).show();
        return count;
    }

    public void insertFpBoothOfficer(byte[] finger_template) {
        ContentValues cols = new ContentValues();
        cols.put("FingerTemplate", finger_template);
        DBHelper dBHelper = this.db;
        dBHelper.clearAllTableData(dBHelper.tbl_lock_boothofficer);
        DBHelper dBHelper2 = this.db;
        if (dBHelper2.insertData(dBHelper2.tbl_lock_boothofficer, cols) > 0) {
            this.userAuth.setLock(false);
            this.lockButton.setVisibility(0);
            this.alternateButton.setVisibility(0);
            this.insertFpButton.setVisibility(8);
        }
    }

    private Boolean compareAndLock(byte[] finger_template1) {
        Cursor cursor = this.db.fpcompareLock();
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return false;
        }
        do {
            numrows++;
            if (this.mfs100.MatchISO(cursor.getBlob(cursor.getColumnIndex("FingerTemplate")), finger_template1) > 100) {
                Toast.makeText(getApplicationContext(), "Fingerprint matches. Lock now", 1).show();
                return true;
            }
        } while (cursor.moveToNext());
        return false;
    }

    public Boolean compareAndLockTatvik(byte[] finger_template1) {
        Cursor cursor = this.db.fpcompareLock();
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return false;
        }
        do {
            numrows++;
            if (this.tmf20lib.matchIsoTemplates(this.captRslt1.getFmrBytes(), cursor.getBlob(cursor.getColumnIndex("FingerTemplate")))) {
                Toast.makeText(getApplicationContext(), "Fingerprint matches.Tatvik Lock now", 1).show();
                return true;
            }
        } while (cursor.moveToNext());
        return false;
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put("title", "New Picture");
        values.put("description", "From the Camera");
        this.imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra("output", this.imageUri);
        startActivityForResult(cameraIntent, this.IMAGE_CAPTURE_CODE);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length > 0) {
                int i = grantResults[0];
                getPackageManager();
                if (i == 0) {
                    return;
                }
            }
            Toast.makeText(this, "Permission denied...", 0).show();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PrintStream printStream = System.out;
        printStream.println("resultcode=" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkPermission2() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            return false;
        }
        return true;
    }

    @Override // java.util.Observer
    public void update(Observable o, Object arg) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void captureFingerPrintFromTatvik() {
        ?? r0 = 2131231197;
        try {
            this.captRslt1 = this.tmf20lib.captureFingerprint(10000);
            if (this.captRslt1 == null || TMF20ErrorCodes.SUCCESS != this.captRslt1.getStatusCode()) {
                this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.wrong_icon_trp));
                return;
            }
            this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.right_icon_trp));
            Toast.makeText(getApplicationContext(), "Tatvik capture complete", 1).show();
        } catch (Exception e) {
            while (true) {
                ImageView imageView = this.imgFinger;
                r0 = getResources().getDrawable(r0 == true ? 1 : 0);
                imageView.setImageDrawable(r0);
                return;
            }
        }
    }

    private void updatefingerprintonlineasstring(byte[] finger_template) {
        Toast.makeText(getApplicationContext(), "updatefingerprint server 0", 1).show();
        String voterid = this.voterDataNewModelList.get(0).getEPIC_NO();
        String fp_string = Base64.encodeToString(finger_template, 0);
        Map<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("fp", fp_string);
        Call<FinperprintCompareServerResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateFpServer2(map);
        Context applicationContext = getApplicationContext();
        Toast.makeText(applicationContext, "updatefingerprint server voterid " + voterid, 1).show();
        Context applicationContext2 = getApplicationContext();
        Toast.makeText(applicationContext2, "updatefingerprint server 1 " + fp_string, 1).show();
        call.enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.15
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call2, Response<FinperprintCompareServerResponse> response) {
                Toast.makeText(ListUserActivity.this.getApplicationContext(), "updatefingerprint server success", 1).show();
                Context applicationContext3 = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext3, "updatefingerprint message=" + response.body().getMessage(), 1).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call2, Throwable t) {
                Context applicationContext3 = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext3, "updatefingerprint server failed" + t.getMessage(), 1).show();
            }
        });
    }

    private void getVoterListForSyncComparison(String dist, String block, String panchayatId, String ward, String booth) {
        this.voterDataNewModelList2 = new ArrayList();
        this.voterDataNewModelList2 = this.db.getAllElements();
        Toast.makeText(getApplicationContext(), "Sync started", 1).show();
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListTableForSync(dist, block, panchayatId, ward, booth).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.16
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                ListUserActivity.this.voterListFromServer = response.body().getVoters();
                ListUserActivity listUserActivity = ListUserActivity.this;
                listUserActivity.CompareServerToLocal(listUserActivity.voterDataNewModelList2, ListUserActivity.this.voterListFromServer);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterListNewTableGetResponse> call, Throwable t) {
                Context applicationContext = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Sync failed " + t.getMessage(), 0).show();
            }
        });
    }

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
                        } else if (!voterFromServer.get(j).getEPIC_NO().equalsIgnoreCase(localVoter.get(i).getEPIC_NO())) {
                            j++;
                        } else if (!localVoter.get(i).getVOTED().equalsIgnoreCase(voterFromServer.get(j).getVOTED())) {
                            hash_Set.add(Integer.valueOf(i));
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

    private void sync(List<Integer> voters) {
        for (int i = 0; i < voters.size(); i++) {
            updateSingleRow(this.voterDataNewModelList2.get(voters.get(i).intValue()).getEPIC_NO());
        }
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
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postDBUpdate(map).enqueue(new Callback<DBUpdateResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.17
            @Override // retrofit2.Callback
            public void onResponse(Call<DBUpdateResponse> call, Response<DBUpdateResponse> response) {
                Context applicationContext = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Sync success for voterid " + voterid, 1).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<DBUpdateResponse> call, Throwable t) {
                Toast.makeText(ListUserActivity.this.getApplicationContext(), "Sync failed for voterid ", 1).show();
            }
        });
    }
}
