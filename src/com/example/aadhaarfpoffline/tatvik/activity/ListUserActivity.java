package com.example.aadhaarfpoffline.tatvik.activity;

import android.app.ProgressDialog;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
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
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.example.aadhaarfpoffline.tatvik.BuildConfig;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.TransTableDataModel;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.DBUpdateResponse;
import com.example.aadhaarfpoffline.tatvik.network.FinperprintCompareServerResponse;
import com.example.aadhaarfpoffline.tatvik.network.ImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.LockUpdateResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransTableGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransactionRowPostResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterListNewTableGetResponse;
import com.example.aadhaarfpoffline.tatvik.services.LocationTrack;
import com.example.aadhaarfpoffline.tatvik.services.ManualSyncService;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.facebook.common.util.UriUtil;
import com.google.android.material.navigation.NavigationView;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.tatvik.fp.CaptureResult;
import org.tatvik.fp.TMF20API;
import org.tatvik.fp.TMF20ErrorCodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class ListUserActivity extends AppCompatActivity implements MFS100Event, NavigationView.OnNavigationItemSelectedListener, VoterListNewTableAdapter.OnItemClickListener, Observer {
    public static final String MULTIPART_FORM_DATA;
    private static final int PERMISSION_CODE;
    public static final int QUALITY_LIMIT;
    private VoterListNewTableAdapter adapter2;
    private Button alternateButton;
    private TextView blockBooth;
    private NBioBSPJNI bsp;
    private byte[] byCapturedRaw1;
    private byte[] byTemplate1;
    CaptureResult captRslt1;
    private Button captureFingerprint;
    CheckBox cbFastDetection;
    Context context;
    private CountDownLatch countDownLatch;
    DBHelper db;
    private DrawerLayout drawer;
    private NBioBSPJNI.Export exportEngine;
    byte[] finger_template1;
    Uri imageUri;
    ImageView imgFinger;
    private NBioBSPJNI.IndexSearch indexSearch;
    private Button insertFpButton;
    LocationTrack locationTrack;
    private Button lockButton;
    private LinearLayout lockLayout;
    private Button loginButton;
    private int nCapturedRawHeight1;
    private int nCapturedRawWidth1;
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
    private List<VoterDataNewModel> voterDataNewModelList;
    private List<VoterDataNewModel> voterDataNewModelList2;
    private List<VoterDataNewModel> voterListFromServer;
    List<Integer> voterlist;
    private static long Threshold = 1500;
    private static long mLastClkTime = 0;
    private static long ThresholdFPcapture = 3000;
    private boolean isCaptureRunning = false;
    private FingerData lastCapFingerData = null;
    int timeout = 10000;
    MFS100 mfs100 = null;
    private boolean isLockLayoutVisible = true;
    private int IMAGE_CAPTURE_CODE = 1001;
    int nFIQ = 0;
    String msg = "";
    private boolean bspConnected = false;
    String androidId = "";
    String UDevId = "";
    String lan = "";
    NBioBSPJNI.CAPTURE_CALLBACK mCallback = new NBioBSPJNI.CAPTURE_CALLBACK() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.8
        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public void OnDisConnected() {
            NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
            ListUserActivity.this.bspConnected = false;
            if (ListUserActivity.this.countDownLatch != null) {
                Log.v("Pawan", "latch countdown on device open fail");
                ListUserActivity.this.countDownLatch.countDown();
            }
            String str = "NBioBSP Disconnected: " + ListUserActivity.this.bsp.GetErrorCode();
        }

        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public void OnConnected() {
            String str = "Device Open Success : " + ListUserActivity.this.bsp.GetErrorCode();
            ListUserActivity.this.bspConnected = true;
            if (ListUserActivity.this.countDownLatch != null) {
                Log.v("Pawan", "latch countdown on device open success");
                ListUserActivity.this.countDownLatch.countDown();
            }
        }

        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public int OnCaptured(NBioBSPJNI.CAPTURED_DATA capturedData) {
            if (capturedData.getImage() != null) {
                ListUserActivity.this.imgFinger.setImageBitmap(capturedData.getImage());
            }
            if (capturedData.getImageQuality() >= 60) {
                return 513;
            }
            if (capturedData.getDeviceError() != 0) {
                return capturedData.getDeviceError();
            }
            return 0;
        }
    };
    private long mLastAttTime = 0;
    long mLastDttTime = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterlist);
        this.lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, this.lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        this.androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        this.UDevId = this.androidId;
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
        initData();
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem nav_app_version = navigationView.getMenu().findItem(R.id.nav_app_version);
        nav_app_version.setTitle("Version 36/" + BuildConfig.VERSION_NAME);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_vendor_list);
        this.userAuth.getBoothId();
        this.search = (EditText) findViewById(R.id.search_text);
        this.search.setHint(this.resources.getString(R.string.all_voters_text));
        this.search.setHintTextColor(Color.parseColor("#ffffff"));
        this.stateDistrict = (TextView) findViewById(R.id.state_district);
        this.blockBooth = (TextView) findViewById(R.id.block_booth);
        this.numVoters = (TextView) findViewById(R.id.num_voters);
        if (this.lan.equalsIgnoreCase("en")) {
            TextView textView = this.stateDistrict;
            textView.setText(this.resources.getString(R.string.panchayat_name) + ":" + this.userAuth.getPanchayat_NAME_EN() + ", " + this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDIST_NAME_EN() + ", " + this.resources.getString(R.string.block_name) + ":" + this.userAuth.getBlock_NAME_EN());
        } else {
            TextView textView2 = this.stateDistrict;
            textView2.setText(this.resources.getString(R.string.panchayat_name) + ":" + this.userAuth.getPanchayat_NAME_HN() + ", " + this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDIST_NAME_HN() + ", " + this.resources.getString(R.string.block_name) + ":" + this.userAuth.getBlock_NAME_HN());
        }
        this.searchButton = (Button) findViewById(R.id.search_button);
        this.searchButton.setText(this.resources.getString(R.string.search));
        this.loginButton = (Button) findViewById(R.id.logout_button);
        this.loginButton.setVisibility(8);
        this.captureFingerprint.setText(this.resources.getString(R.string.aadhaar_fingerpint_capture));
        this.lockButton.setText(this.resources.getString(R.string.lock_text));
        this.insertFpButton.setText(this.resources.getString(R.string.insert_record_text));
        this.alternateButton.setText(this.resources.getString(R.string.alternate_text));
        this.loginButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ListUserActivity listUserActivity = ListUserActivity.this;
                listUserActivity.setlogin(listUserActivity.userAuth, false);
            }
        });
        this.searchButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ListUserActivity.this.search(ListUserActivity.this.search.getText().toString());
            }
        });
        this.voterDataNewModelList = new ArrayList();
        this.progressBar.setVisibility(0);
        if (this.db.getAllElements() == null || this.db.getAllElements().size() <= 0) {
            getVoterListNewTableByBooth(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo());
        } else {
            this.voterDataNewModelList = this.db.getAllElements();
            List<VoterDataNewModel> list = this.voterDataNewModelList;
            if (list == null || list.isEmpty() || this.voterDataNewModelList.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No data in local database. Please ensure net conection and try again", 1).show();
            } else {
                if (this.lan.equalsIgnoreCase("en")) {
                    TextView textView3 = this.stateDistrict;
                    textView3.setText(this.resources.getString(R.string.panchayat_name) + ":" + this.userAuth.getPanchayat_NAME_EN() + " " + this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDIST_NAME_EN() + " " + this.resources.getString(R.string.block_name) + ":" + this.userAuth.getBlock_NAME_EN());
                } else {
                    TextView textView4 = this.stateDistrict;
                    textView4.setText(this.resources.getString(R.string.panchayat_name) + ":" + this.userAuth.getPanchayat_NAME_HN() + " " + this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDIST_NAME_HN() + " " + this.resources.getString(R.string.block_name) + ":" + this.userAuth.getBlock_NAME_HN());
                }
                TextView textView5 = this.blockBooth;
                textView5.setText(this.userAuth.getPanchayatId() + " " + this.resources.getString(R.string.booth_no_text) + ":" + getBoothInFormat(this.userAuth.getBoothNo()) + "," + this.resources.getString(R.string.ward_no_text) + ":" + this.userAuth.getWardNo());
                this.numVoters.setText(this.resources.getString(R.string.total_no_voters_text, Integer.valueOf(this.voterDataNewModelList.size())));
                setRecyclerViewNewTable();
            }
        }
        try {
            this.mfs100 = new MFS100(this);
            this.mfs100.SetApplicationContext(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tmf20lib = new TMF20API(this);
        this.captureFingerprint.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - ListUserActivity.mLastClkTime < ListUserActivity.ThresholdFPcapture) {
                    Toast.makeText(ListUserActivity.this.getApplicationContext(), "Please wait for some time", 1).show();
                    return;
                }
                long unused = ListUserActivity.mLastClkTime = SystemClock.elapsedRealtime();
                if (ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                    ListUserActivity.this.StartSyncCapture();
                } else if (ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                    ListUserActivity.this.captureFingerPrintFromTatvik();
                } else if (ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                    new AsyncCaller().execute(new Void[0]);
                }
            }
        });
        this.insertFpButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                    if (ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                        if (ListUserActivity.this.captRslt1 != null && ListUserActivity.this.captRslt1.getFmrBytes() != null) {
                            ListUserActivity listUserActivity = ListUserActivity.this;
                            listUserActivity.insertFpBoothOfficer(listUserActivity.captRslt1.getFmrBytes());
                        }
                    } else if (ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan) && ListUserActivity.this.byTemplate1 != null) {
                        ListUserActivity listUserActivity2 = ListUserActivity.this;
                        listUserActivity2.insertFpBoothOfficer(listUserActivity2.byTemplate1);
                    }
                }
            }
        });
        this.lockButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                    if (ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                        if (ListUserActivity.this.captRslt1 == null || ListUserActivity.this.captRslt1.getFmrBytes() == null || ListUserActivity.this.captRslt1.getFmrBytes().length <= 0) {
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), "Please capture fingerprint first.", 1).show();
                            return;
                        }
                        ListUserActivity listUserActivity = ListUserActivity.this;
                        if (listUserActivity.compareAndLockTatvik(listUserActivity.finger_template1).booleanValue()) {
                            ListUserActivity.this.userAuth.setLock(true);
                            ListUserActivity.this.lockButton.setVisibility(8);
                            ListUserActivity.this.insertFpButton.setVisibility(0);
                            ListUserActivity.this.lockRecordUpdate(true);
                            return;
                        }
                        ListUserActivity.this.userAuth.setLock(false);
                    } else if (!ListUserActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                    } else {
                        if (ListUserActivity.this.byTemplate1 != null) {
                            ListUserActivity listUserActivity2 = ListUserActivity.this;
                            if (listUserActivity2.compareAndLockNetgin(listUserActivity2.byTemplate1).booleanValue()) {
                                ListUserActivity.this.userAuth.setLock(true);
                                ListUserActivity.this.lockButton.setVisibility(8);
                                ListUserActivity.this.insertFpButton.setVisibility(0);
                                ListUserActivity.this.lockRecordUpdate(true);
                                return;
                            }
                            ListUserActivity.this.userAuth.setLock(false);
                            return;
                        }
                        Toast.makeText(ListUserActivity.this.getApplicationContext(), "Please capture fingerprint first Lock", 1).show();
                    }
                }
            }
        });
        this.alternateButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
            }
        });
    }

    public void initData() {
        NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
        if (this.bsp == null) {
            this.bsp = new NBioBSPJNI("010701-613E5C7F4CC7C4B0-72E340B47E034015", this, this.mCallback);
            if (this.bsp.IsErrorOccured()) {
                Toast.makeText(getApplicationContext(), "NBioBSP Error: " + this.bsp.GetErrorCode(), 1).show();
                return;
            }
            NBioBSPJNI nBioBSPJNI = this.bsp;
            Objects.requireNonNull(nBioBSPJNI);
            this.exportEngine = new NBioBSPJNI.Export();
            NBioBSPJNI nBioBSPJNI2 = this.bsp;
            Objects.requireNonNull(nBioBSPJNI2);
            this.indexSearch = new NBioBSPJNI.IndexSearch();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        NBioBSPJNI nBioBSPJNI = this.bsp;
        if (nBioBSPJNI != null) {
            nBioBSPJNI.dispose();
            this.bsp = null;
        }
        super.onDestroy();
    }

    /* loaded from: classes2.dex */
    private class AsyncCaller extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pdLoading;

        private AsyncCaller() {
            ListUserActivity.this = r1;
            this.pdLoading = null;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            this.pdLoading = new ProgressDialog(ListUserActivity.this);
            this.pdLoading.setMessage("\tLoading...");
            this.pdLoading.show();
        }

        public Boolean doInBackground(Void... params) {
            if (ListUserActivity.this.bspConnected) {
                Log.v("Pawan", "device already connected");
                return true;
            }
            ListUserActivity.this.countDownLatch = new CountDownLatch(1);
            Boolean isConnected = Boolean.valueOf(ListUserActivity.this.bsp.OpenDevice());
            try {
                ListUserActivity.this.countDownLatch.await();
                Log.v("Pawan", "OpenDevice call finsihed");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isConnected;
        }

        public void onPostExecute(Boolean result) {
            super.onPostExecute((AsyncCaller) result);
            ProgressDialog progressDialog = this.pdLoading;
            if (progressDialog != null && progressDialog.isShowing()) {
                this.pdLoading.dismiss();
            }
            if (ListUserActivity.this.bspConnected) {
                ListUserActivity.this.captureFingerPrintFromNitgen();
            } else {
                Toast.makeText(ListUserActivity.this, Const.nBioDeviceError, 0).show();
            }
        }
    }

    public synchronized void captureFingerPrintFromNitgen() {
        try {
            NBioBSPJNI nBioBSPJNI = this.bsp;
            Objects.requireNonNull(nBioBSPJNI);
            NBioBSPJNI.FIR_HANDLE hCapturedFIR = new NBioBSPJNI.FIR_HANDLE();
            NBioBSPJNI nBioBSPJNI2 = this.bsp;
            Objects.requireNonNull(nBioBSPJNI2);
            NBioBSPJNI.FIR_HANDLE hAuditFIR = new NBioBSPJNI.FIR_HANDLE();
            NBioBSPJNI nBioBSPJNI3 = this.bsp;
            Objects.requireNonNull(nBioBSPJNI3);
            this.bsp.Capture(3, hCapturedFIR, this.timeout, hAuditFIR, new NBioBSPJNI.CAPTURED_DATA());
            if (this.bsp.IsErrorOccured()) {
                this.msg = "NBioBSP Capture Error: " + this.bsp.GetErrorCode();
            } else {
                NBioBSPJNI nBioBSPJNI4 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI4);
                NBioBSPJNI.INPUT_FIR inputFIR = new NBioBSPJNI.INPUT_FIR();
                inputFIR.SetFIRHandle(hCapturedFIR);
                NBioBSPJNI.Export export = this.exportEngine;
                Objects.requireNonNull(export);
                NBioBSPJNI.Export.DATA exportData = new NBioBSPJNI.Export.DATA();
                this.exportEngine.ExportFIR(inputFIR, exportData, 3);
                if (this.bsp.IsErrorOccured()) {
                    runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.9
                        @Override // java.lang.Runnable
                        public void run() {
                            ListUserActivity listUserActivity = ListUserActivity.this;
                            listUserActivity.msg = "NBioBSP ExportFIR Error: " + ListUserActivity.this.bsp.GetErrorCode();
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), ListUserActivity.this.msg, 0).show();
                        }
                    });
                    return;
                }
                if (this.byTemplate1 != null) {
                    this.byTemplate1 = null;
                }
                this.byTemplate1 = new byte[exportData.FingerData[0].Template[0].Data.length];
                this.byTemplate1 = exportData.FingerData[0].Template[0].Data;
                inputFIR.SetFIRHandle(hAuditFIR);
                NBioBSPJNI.Export export2 = this.exportEngine;
                Objects.requireNonNull(export2);
                NBioBSPJNI.Export.AUDIT exportAudit = new NBioBSPJNI.Export.AUDIT();
                this.exportEngine.ExportAudit(inputFIR, exportAudit);
                if (this.bsp.IsErrorOccured()) {
                    runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.10
                        @Override // java.lang.Runnable
                        public void run() {
                            ListUserActivity listUserActivity = ListUserActivity.this;
                            listUserActivity.msg = "NBioBSP ExportAudit Error: " + ListUserActivity.this.bsp.GetErrorCode();
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), ListUserActivity.this.msg, 0).show();
                        }
                    });
                    return;
                }
                if (this.byCapturedRaw1 != null) {
                    this.byCapturedRaw1 = null;
                }
                this.byCapturedRaw1 = new byte[exportAudit.FingerData[0].Template[0].Data.length];
                this.byCapturedRaw1 = exportAudit.FingerData[0].Template[0].Data;
                this.nCapturedRawWidth1 = exportAudit.ImageWidth;
                this.nCapturedRawHeight1 = exportAudit.ImageHeight;
                this.msg = "First Capture Success";
                NBioBSPJNI nBioBSPJNI5 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI5);
                NBioBSPJNI.NFIQInfo info = new NBioBSPJNI.NFIQInfo();
                info.pRawImage = this.byCapturedRaw1;
                info.nImgWidth = this.nCapturedRawWidth1;
                info.nImgHeight = this.nCapturedRawHeight1;
                this.bsp.getNFIQInfoFromRaw(info);
                if (this.bsp.IsErrorOccured()) {
                    runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.11
                        @Override // java.lang.Runnable
                        public void run() {
                            ListUserActivity listUserActivity = ListUserActivity.this;
                            listUserActivity.msg = "NBioBSP getNFIQInfoFromRaw Error: " + ListUserActivity.this.bsp.GetErrorCode();
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), ListUserActivity.this.msg, 0).show();
                        }
                    });
                    return;
                }
                this.nFIQ = info.pNFIQ;
                NBioBSPJNI nBioBSPJNI6 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI6);
                NBioBSPJNI.ISOBUFFER ISOBuffer = new NBioBSPJNI.ISOBUFFER();
                this.bsp.ExportRawToISOV1(exportAudit, ISOBuffer, false, (byte) 0);
                if (this.bsp.IsErrorOccured()) {
                    runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.12
                        @Override // java.lang.Runnable
                        public void run() {
                            ListUserActivity listUserActivity = ListUserActivity.this;
                            listUserActivity.msg = "NBioBSP ExportRawToISOV1 Error: " + ListUserActivity.this.bsp.GetErrorCode();
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), ListUserActivity.this.msg, 0).show();
                        }
                    });
                    return;
                }
                NBioBSPJNI nBioBSPJNI7 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI7);
                NBioBSPJNI.NIMPORTRAWSET rawSet = new NBioBSPJNI.NIMPORTRAWSET();
                this.bsp.ImportISOToRaw(ISOBuffer, rawSet);
                if (this.bsp.IsErrorOccured()) {
                    runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.13
                        @Override // java.lang.Runnable
                        public void run() {
                            ListUserActivity listUserActivity = ListUserActivity.this;
                            listUserActivity.msg = "NBioBSP ImportISOToRaw Error: " + ListUserActivity.this.bsp.GetErrorCode();
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), ListUserActivity.this.msg, 0).show();
                        }
                    });
                    return;
                }
                for (int i = 0; i < rawSet.Count; i++) {
                    this.msg += "  DataLen: " + rawSet.RawData[i].Data.length + "  FingerID: " + ((int) rawSet.RawData[i].FingerID) + "  Width: " + ((int) rawSet.RawData[i].ImgWidth) + "  Height: " + ((int) rawSet.RawData[i].ImgHeight) + "  ";
                }
                if (this.byCapturedRaw1 != null) {
                    this.byCapturedRaw1 = null;
                }
                this.byCapturedRaw1 = new byte[rawSet.RawData[0].Data.length];
                this.byCapturedRaw1 = rawSet.RawData[0].Data;
                this.nCapturedRawWidth1 = rawSet.RawData[0].ImgWidth;
                this.nCapturedRawHeight1 = rawSet.RawData[0].ImgHeight;
                runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.14
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(ListUserActivity.this.getApplicationContext(), ListUserActivity.this.msg, 0).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setlogin(UserAuth userAuth, Boolean login) {
        userAuth.setLogin(login);
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    public void stopHandler() {
    }

    public void startHandler() {
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
        VoterListNewTableAdapter voterListNewTableAdapter2 = this.adapter2;
        if (voterListNewTableAdapter2 != null) {
            voterListNewTableAdapter2.setNewData(voterList);
        }
    }

    private void setRecyclerView() {
    }

    public void setRecyclerViewNewTable() {
        this.adapter2 = new VoterListNewTableAdapter(this, this, this.voterDataNewModelList, false);
        this.recyclerView.setAdapter(this.adapter2);
        LinearLayoutManager linearVertical = new LinearLayoutManager(this, 1, false);
        this.progressBar.setVisibility(8);
        this.recyclerView.setLayoutManager(linearVertical);
        this.adapter2.notifyDataSetChanged();
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter.OnItemClickListener
    public void onItemClick3(int pos, String voterid) {
        List<VoterDataNewModel> list = this.voterDataNewModelList;
        if (list == null || list.isEmpty() || this.voterDataNewModelList.size() < 1) {
            Toast.makeText(getApplicationContext(), "No voter data", 1).show();
        } else if (voterid == null || voterid.length() < 1) {
            Toast.makeText(getApplicationContext(), "VoterId Empty", 1).show();
        } else {
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
                if (this.voterDataNewModelList.get(position).getVOTED() == null || this.voterDataNewModelList.get(position).getVOTED().isEmpty()) {
                    intent.putExtra("voted", "0");
                } else {
                    intent.putExtra("voted", this.voterDataNewModelList.get(position).getVOTED());
                }
                intent.putExtra("slnoinward", this.voterDataNewModelList.get(position).getSlNoInWard());
                intent.putExtra("age", this.voterDataNewModelList.get(position).getAge());
                intent.putExtra("gender", this.voterDataNewModelList.get(position).getGENDER());
                startActivity(intent);
                return;
            }
            Toast.makeText(getApplicationContext(), "Voterid not retroeved properly ", 1).show();
        }
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter.OnItemClickListener
    public void onItemClick4(int pos, String slnoinward) {
        List<VoterDataNewModel> list = this.voterDataNewModelList;
        if (list == null || list.isEmpty() || this.voterDataNewModelList.size() < 1) {
            Toast.makeText(getApplicationContext(), "No voter data", 1).show();
        } else if (slnoinward == null || slnoinward.length() < 1) {
            Toast.makeText(getApplicationContext(), "slnoinward Empty", 1).show();
        } else {
            int position = -1;
            int i = 0;
            while (true) {
                if (i >= this.voterDataNewModelList.size()) {
                    break;
                } else if (this.voterDataNewModelList.get(i).getSlNoInWard().equalsIgnoreCase(slnoinward)) {
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
                if (this.voterDataNewModelList.get(position).getVOTED() == null || this.voterDataNewModelList.get(position).getVOTED().isEmpty()) {
                    intent.putExtra("voted", "0");
                } else {
                    intent.putExtra("voted", this.voterDataNewModelList.get(position).getVOTED());
                }
                intent.putExtra("slnoinward", this.voterDataNewModelList.get(position).getSlNoInWard());
                intent.putExtra("age", this.voterDataNewModelList.get(position).getAge());
                intent.putExtra("gender", this.voterDataNewModelList.get(position).getGENDER());
                startActivity(intent);
                return;
            }
            Toast.makeText(getApplicationContext(), "Voterid not retroeved properly ", 1).show();
        }
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter.OnItemClickListener
    public void makeLockButtonsVisible(Boolean visible) {
    }

    private void getVoterListNewTableByBooth(String dist, String block, String panchayatId, String ward, String booth) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dist_no", dist);
        map.put("block_id", block);
        map.put("panchayat_id", panchayatId);
        map.put("ward_no", ward);
        map.put("booth_no", booth);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListNewTableByBoothNo(map).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.15
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                Log.d("getdata", response.raw().toString());
                if (response != null) {
                    try {
                        if (response.isSuccessful() && response.body() != null && response.body().getVoters() != null && !response.body().getVoters().isEmpty() && response.body().getVoters().size() > 0) {
                            Log.d("getVoterListNewTableByBooth", response.toString());
                            ListUserActivity.this.voterDataNewModelList = response.body().getVoters();
                            Log.d("getVoterListNewTableByBooth2", ListUserActivity.this.voterDataNewModelList.toString());
                            $$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs r2 = $$Lambda$ListUserActivity$15$c0P9NQJvKTCgzbWfDwcOxEt6Ncs.INSTANCE;
                            $$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY r3 = $$Lambda$ListUserActivity$15$Xr44Ek0tKiSrJEG5vOQ5dsEiwvY.INSTANCE;
                            if (ListUserActivity.this.lan.equalsIgnoreCase("en")) {
                                TextView textView = ListUserActivity.this.stateDistrict;
                                textView.setText(ListUserActivity.this.resources.getString(R.string.panchayat_name) + ":" + ListUserActivity.this.userAuth.getPanchayat_NAME_EN() + " " + ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ListUserActivity.this.userAuth.getDIST_NAME_EN() + " " + ListUserActivity.this.resources.getString(R.string.block_name) + ":" + ListUserActivity.this.userAuth.getBlock_NAME_EN());
                            } else {
                                TextView textView2 = ListUserActivity.this.stateDistrict;
                                textView2.setText(ListUserActivity.this.resources.getString(R.string.panchayat_name) + ":" + ListUserActivity.this.userAuth.getPanchayat_NAME_HN() + " " + ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ListUserActivity.this.userAuth.getDIST_NAME_HN() + " " + ListUserActivity.this.resources.getString(R.string.block_name) + ":" + ListUserActivity.this.userAuth.getBlock_NAME_HN());
                            }
                            TextView textView3 = ListUserActivity.this.blockBooth;
                            textView3.setText(ListUserActivity.this.resources.getString(R.string.block_no_text) + ":" + response.body().getVoters().get(0).getBlockID() + "," + ListUserActivity.this.resources.getString(R.string.ward_no_text) + ":" + response.body().getVoters().get(0).getWardNo() + "," + ListUserActivity.this.resources.getString(R.string.booth_no_text) + ":" + ListUserActivity.this.getBoothInFormat(ListUserActivity.this.userAuth.getBoothNo()));
                            ListUserActivity.this.numVoters.setText(ListUserActivity.this.resources.getString(R.string.total_no_voters_text, Integer.valueOf(response.body().getVoters().size())));
                            ListUserActivity.this.insertUsersOffline();
                            return;
                        }
                    } catch (Exception e) {
                        Context applicationContext = ListUserActivity.this.getApplicationContext();
                        Toast.makeText(applicationContext, "Exception Response" + e.getMessage(), 1).show();
                        return;
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
                    Context applicationContext = ListUserActivity.this.getApplicationContext();
                    Toast.makeText(applicationContext, "IO Exception" + t.getMessage(), 1).show();
                    ListUserActivity listUserActivity = ListUserActivity.this;
                    listUserActivity.voterDataNewModelList = listUserActivity.db.getAllElementsForAPanchayatBooth(ListUserActivity.this.userAuth.getPanchayatId(), ListUserActivity.this.userAuth.getBoothNo());
                    if (ListUserActivity.this.voterDataNewModelList == null || ListUserActivity.this.voterDataNewModelList.isEmpty() || ListUserActivity.this.voterDataNewModelList.size() <= 0) {
                        Toast.makeText(ListUserActivity.this.getApplicationContext(), "No data in local database. Please ensure net conection and try again", 1).show();
                        return;
                    }
                    if (ListUserActivity.this.lan.equalsIgnoreCase("en")) {
                        TextView textView = ListUserActivity.this.stateDistrict;
                        textView.setText(ListUserActivity.this.resources.getString(R.string.panchayat_name) + ":" + ListUserActivity.this.userAuth.getPanchayat_NAME_EN() + " " + ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ListUserActivity.this.userAuth.getDIST_NAME_EN() + " " + ListUserActivity.this.resources.getString(R.string.block_name) + ":" + ListUserActivity.this.userAuth.getBlock_NAME_EN());
                    } else {
                        TextView textView2 = ListUserActivity.this.stateDistrict;
                        textView2.setText(ListUserActivity.this.resources.getString(R.string.panchayat_name) + ":" + ListUserActivity.this.userAuth.getPanchayat_NAME_HN() + " " + ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ListUserActivity.this.userAuth.getDIST_NAME_HN() + " " + ListUserActivity.this.resources.getString(R.string.block_name) + ":" + ListUserActivity.this.userAuth.getBlock_NAME_HN());
                    }
                    TextView textView3 = ListUserActivity.this.blockBooth;
                    StringBuilder sb = new StringBuilder();
                    sb.append(ListUserActivity.this.userAuth.getPanchayatId());
                    sb.append(" ");
                    sb.append(ListUserActivity.this.resources.getString(R.string.block_no_text));
                    sb.append(":");
                    ListUserActivity listUserActivity2 = ListUserActivity.this;
                    sb.append(listUserActivity2.getBoothInFormat(listUserActivity2.userAuth.getBoothNo()));
                    sb.append(",");
                    sb.append(ListUserActivity.this.resources.getString(R.string.ward_no_text));
                    sb.append(":");
                    sb.append(ListUserActivity.this.userAuth.getWardNo());
                    sb.append(",");
                    sb.append(ListUserActivity.this.resources.getString(R.string.booth_no_text));
                    sb.append(":");
                    ListUserActivity listUserActivity3 = ListUserActivity.this;
                    sb.append(listUserActivity3.getBoothInFormat(listUserActivity3.userAuth.getBoothNo()));
                    textView3.setText(sb.toString());
                    ListUserActivity.this.numVoters.setText(ListUserActivity.this.resources.getString(R.string.total_no_voters_text, Integer.valueOf(ListUserActivity.this.voterDataNewModelList.size())));
                    ListUserActivity.this.setRecyclerViewNewTable();
                }
            }
        });
    }

    private void getTransTableData(String dist, String block, String panchayatId, String ward, String booth) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dist_no", dist);
        map.put("block_id", block);
        map.put("panchayat_id", panchayatId);
        map.put("ward_no", ward);
        map.put("booth_no", booth);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getTransTable(map).enqueue(new Callback<TransTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.16
            /* JADX WARN: Type inference failed for: r0v2 */
            /* JADX WARN: Type inference failed for: r0v3 */
            /* JADX WARN: Type inference failed for: r0v4, types: [android.widget.Toast] */
            /* JADX WARN: Unknown variable types count: 1 */
            @Override // retrofit2.Callback
            /* Code decompiled incorrectly, please refer to instructions dump */
            public void onResponse(Call<TransTableGetResponse> call, Response<TransTableGetResponse> response) {
                Log.d("getdatatrans", response.raw().toString());
                ?? r0 = 1;
                if (response != null) {
                    try {
                        if (response.isSuccessful() && response.body() != null && response.body().getTransTableData() != null && !response.body().getTransTableData().isEmpty() && response.body().getTransTableData().size() > 0) {
                            Toast.makeText(ListUserActivity.this.getApplicationContext(), "Response right . Just before insertTransTable", 1).show();
                            ListUserActivity.this.insertTransTable(response.body().getTransTableData());
                            ListUserActivity.this.setRecyclerViewNewTable();
                            return;
                        }
                    } catch (Exception e) {
                        while (true) {
                            ListUserActivity.this.setRecyclerViewNewTable();
                            Context applicationContext = ListUserActivity.this.getApplicationContext();
                            r0 = Toast.makeText(applicationContext, "Exception Response" + e.getMessage(), r0 == true ? 1 : 0);
                            r0.show();
                            return;
                        }
                    }
                }
                Log.d("getTransTableData", "Some problem in  Response" + response.isSuccessful() + " body=" + response.body().toString() + " " + response.body().getMessage() + " raw" + response.raw().toString() + " gettranstable" + response.body().getTransTableData());
                ListUserActivity.this.setRecyclerViewNewTable();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<TransTableGetResponse> call, Throwable t) {
                ListUserActivity.this.setRecyclerViewNewTable();
                ListUserActivity.this.progressBar.setVisibility(8);
                Log.d("basictag failure", t.getMessage());
                if (!(t instanceof SocketTimeoutException)) {
                    boolean z = t instanceof IOException;
                }
            }
        });
    }

    private void getVoterListNewTableByBlock(String block) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterListNewTableByPanchayatId(block).enqueue(new Callback<VoterListNewTableGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.17
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterListNewTableGetResponse> call, Response<VoterListNewTableGetResponse> response) {
                Log.d("basictag success", response.toString());
                ListUserActivity.this.voterDataNewModelList = response.body().getVoters();
                if (ListUserActivity.this.lan.equalsIgnoreCase("en")) {
                    TextView textView = ListUserActivity.this.stateDistrict;
                    textView.setText(ListUserActivity.this.resources.getString(R.string.panchayat_name) + ":" + ListUserActivity.this.userAuth.getPanchayat_NAME_EN() + " " + ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ListUserActivity.this.userAuth.getDIST_NAME_EN() + " " + ListUserActivity.this.resources.getString(R.string.block_name) + ":" + ListUserActivity.this.userAuth.getBlock_NAME_EN());
                } else {
                    TextView textView2 = ListUserActivity.this.stateDistrict;
                    textView2.setText(ListUserActivity.this.resources.getString(R.string.panchayat_name) + ":" + ListUserActivity.this.userAuth.getPanchayat_NAME_HN() + " " + ListUserActivity.this.resources.getString(R.string.district_name_text) + ":" + ListUserActivity.this.userAuth.getDIST_NAME_HN() + " " + ListUserActivity.this.resources.getString(R.string.block_name) + ":" + ListUserActivity.this.userAuth.getBlock_NAME_HN());
                }
                TextView textView3 = ListUserActivity.this.blockBooth;
                StringBuilder sb = new StringBuilder();
                sb.append(ListUserActivity.this.userAuth.getPanchayatId());
                sb.append(" ");
                sb.append(ListUserActivity.this.resources.getString(R.string.booth_no_text));
                sb.append(":");
                ListUserActivity listUserActivity = ListUserActivity.this;
                sb.append(listUserActivity.getBoothInFormat(listUserActivity.userAuth.getBoothNo()));
                sb.append(",");
                sb.append(ListUserActivity.this.resources.getString(R.string.ward_no_text));
                sb.append(":");
                sb.append(ListUserActivity.this.userAuth.getWardNo());
                textView3.setText(sb.toString());
                ListUserActivity.this.numVoters.setText(ListUserActivity.this.resources.getString(R.string.total_no_voters_text, Integer.valueOf(ListUserActivity.this.voterDataNewModelList.size())));
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

    public void StartSyncCapture() {
        new Thread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.18
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
                        ListUserActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.18.1
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
            Toast.makeText(getApplicationContext(), "Language is changed to English", 0).show();
            translate("en");
        } else if (id == R.id.nav_hindi) {
            Toast.makeText(getApplicationContext(), "Language is changed to Hindi", 0).show();
            translate("hi");
        } else if (id == R.id.nav_get_offline) {
            Toast.makeText(getApplicationContext(), "Get Offline is clicked", 0).show();
            insertUsersOffline();
        } else if (id == R.id.nav_count_offline) {
            getsqlitedata();
        } else if (id == R.id.nav_sync) {
            if (!checkPermission()) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
            } else {
                startService(new Intent(this, ManualSyncService.class));
            }
        } else if (id == R.id.nav_export) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!checkPermission()) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
                } else {
                    boolean exportsuccess = this.db.exportDatabaseTransTable(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo(), getCurrentTimeInFormatForCSV());
                    Context applicationContext = getApplicationContext();
                    Toast.makeText(applicationContext, "Export " + exportsuccess, 1).show();
                }
            }
        } else if (id == R.id.nav_voting_status) {
            startVotingStatusActivity();
        } else if (id == R.id.nav_lock) {
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
        } else if (id == R.id.nav_get_trans_table) {
            getTransTableData(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo());
        } else if (id == R.id.nav_logout) {
            setlogin(this.userAuth, false);
            logoutEvent();
        } else if (id == R.id.nav_changeFingerprintDevice) {
            startActivity(new Intent(this, FingerprintDeviceSelectionActivity.class));
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.END);
        return true;
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
            cols.put("AGE", offlinevoter.get(i).getAge());
            cols.put("DOB", offlinevoter.get(i).getDOB());
            cols.put("EMAIL_ID", offlinevoter.get(i).getEMAIL_ID());
            cols.put("MOBILE_NO", offlinevoter.get(i).getMOBILE_NO());
            cols.put("ELECTOR_TYPE", offlinevoter.get(i).getELECTOR_TYPE());
            cols.put("BlockID", offlinevoter.get(i).getBlockID());
            cols.put("PanchayatID", offlinevoter.get(i).getPanchayatID());
            cols.put("BoothNo", offlinevoter.get(i).getBoothNo());
            cols.put("VillageName", offlinevoter.get(i).getVillageName());
            cols.put("WardNo", offlinevoter.get(i).getWardNo());
            cols.put("SlNoInWard", offlinevoter.get(i).getSlNoInWard());
            cols.put("UserId", offlinevoter.get(i).getUserId());
            cols.put("VOTED", (Integer) 0);
            cols.put("FACE_MATCH", offlinevoter.get(i).getFACE_MATCH());
            cols.put("VOTER_IMAGE", offlinevoter.get(i).getVOTER_IMAGE());
            cols.put("VOTER_FINGERPRINT", offlinevoter.get(i).getVOTER_FINGERPRINT());
            cols.put("ID_DOCUMENT_IMAGE", offlinevoter.get(i).getID_DOCUMENT_IMAGE());
            cols.put("FINGERPRINT_MATCH", offlinevoter.get(i).getFINGERPRINT_MATCH());
            cols.put("VOTING_DATE", offlinevoter.get(i).getVOTING_DATE());
            cols.put("AADHAAR_MATCH", offlinevoter.get(i).getAADHAAR_MATCH());
            cols.put("AADHAAR_NO", offlinevoter.get(i).getAC_NO());
            cols.put("USER_ID", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + offlinevoter.get(i).getSlNoInWard());
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
        getTransTableData(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo());
    }

    public void insertTransTable(List<TransTableDataModel> transTableDataModelList) {
        ListUserActivity listUserActivity = this;
        int i = 0;
        if (transTableDataModelList == null || transTableDataModelList.isEmpty() || transTableDataModelList.size() <= 0) {
            Toast.makeText(getApplicationContext(), "Empty transation table", 0).show();
            return;
        }
        int i2 = 0;
        while (i2 < transTableDataModelList.size()) {
            String voted = transTableDataModelList.get(i2).getVOTED();
            if (!voted.equalsIgnoreCase("0")) {
                ContentValues cols = new ContentValues();
                String fptemplate = transTableDataModelList.get(i2).getFINGERPRINT_TEMPLATE();
                String user_id = transTableDataModelList.get(i2).getUser_id();
                if (user_id != null && !user_id.isEmpty() && user_id.length() > 0) {
                    String[] parts = user_id.split("_");
                    if (parts.length == 4) {
                        String panchayateid = parts[i];
                        String wardno = parts[1];
                        String boothno = parts[2];
                        String slnoinward = parts[3];
                        cols.put("PanchayatID", panchayateid);
                        cols.put("SlNoInWard", slnoinward);
                    }
                }
                if (fptemplate != null && !fptemplate.isEmpty() && fptemplate.length() >= 8) {
                    cols.put("FingerTemplate", Base64.decode(fptemplate, i));
                }
                cols.put(DBHelper.Key_ID, transTableDataModelList.get(i2).getTRANSID());
                cols.put("VOTED", Integer.valueOf(Integer.parseInt(transTableDataModelList.get(i2).getVOTED())));
                cols.put("VOTING_DATE", transTableDataModelList.get(i2).getVOTING_DATE());
                cols.put("ID_DOCUMENT_IMAGE", transTableDataModelList.get(i2).getID_DOCUMENT_IMAGE());
                cols.put("AADHAAR_MATCH", Integer.valueOf(Integer.parseInt(transTableDataModelList.get(i2).getAADHAAR_MATCH())));
                cols.put("AADHAAR_NO", transTableDataModelList.get(i2).getAADHAAR_NO());
                cols.put("VOTING_TYPE", transTableDataModelList.get(i2).getVOTING_TYPE());
                cols.put("GENDER", transTableDataModelList.get(i2).getGENDER());
                cols.put("AGE", Integer.valueOf(Integer.parseInt(transTableDataModelList.get(i2).getAGE())));
                cols.put("USER_ID", user_id);
                cols.put("SYNCED", (Integer) 1);
                cols.put("IMAGE_SYNCED", (Integer) 1);
                cols.put("MATCHED_ID_DOCUMENT_IMAGE", transTableDataModelList.get(i2).getMATCHED_ID_DOCUMENT_IMAGE());
                cols.put("MATCHED_USER_ID", transTableDataModelList.get(i2).getMATCHED_USER_ID());
                cols.put("matched_user_id", transTableDataModelList.get(i2).getMATCHED_USER_ID());
                cols.put("MATCHED_IMAGE_SYNCED", (Integer) 1);
                cols.put("THUMBNAIL_ID_DOCUMENT_IMAGE", "");
                cols.put("THUMBNAIL_IMAGE_SYNCED", (Integer) 1);
                DBHelper dBHelper = listUserActivity.db;
                long res = dBHelper.insertData(dBHelper.tbl_transaction, cols);
                if (res > 0) {
                    listUserActivity.db.updateVOTEDByUSER_ID_Maintable(user_id, Integer.parseInt(voted));
                    PrintStream printStream = System.out;
                    printStream.println("Transaction data inserted true i=" + i2 + " res=" + res);
                } else {
                    PrintStream printStream2 = System.out;
                    printStream2.println("Transaction data inserted false i=" + i2 + " res=" + res);
                }
            }
            i2++;
            i = 0;
            listUserActivity = this;
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

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivityWithoutLocation.class));
        finish();
    }

    private void translate(String lan) {
        LocaleHelper.setLocale(this, lan);
        this.context = LocaleHelper.setLocale(this, lan);
        this.resources = this.context.getResources();
        this.searchButton.setText(this.resources.getString(R.string.search));
        this.search.setHint(this.resources.getString(R.string.all_voters_text));
        setTitle(this.resources.getString(R.string.menu_text));
        this.captureFingerprint.setText(this.resources.getString(R.string.aadhaar_fingerpint_capture));
        if (lan.equalsIgnoreCase("en")) {
            TextView textView = this.stateDistrict;
            textView.setText(this.resources.getString(R.string.panchayat_name) + ":" + this.userAuth.getPanchayat_NAME_EN() + ", " + this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDIST_NAME_EN() + " ," + this.resources.getString(R.string.block_name) + ":" + this.userAuth.getBlock_NAME_EN());
        } else {
            TextView textView2 = this.stateDistrict;
            textView2.setText(this.resources.getString(R.string.panchayat_name) + ":" + this.userAuth.getPanchayat_NAME_HN() + ", " + this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDIST_NAME_HN() + " , " + this.resources.getString(R.string.block_name) + ":" + this.userAuth.getBlock_NAME_HN());
        }
        List<VoterDataNewModel> list = this.voterDataNewModelList;
        if (list != null && !list.isEmpty() && this.voterDataNewModelList.size() > 0) {
            TextView textView3 = this.blockBooth;
            textView3.setText(this.userAuth.getPanchayatId() + " " + this.resources.getString(R.string.booth_no_text) + ":" + getBoothInFormat(this.userAuth.getBoothNo()) + "," + this.resources.getString(R.string.ward_no_text) + ":" + this.userAuth.getWardNo());
            this.numVoters.setText(this.resources.getString(R.string.total_no_voters_text, Integer.valueOf(this.voterDataNewModelList.size())));
            VoterListNewTableAdapter voterListNewTableAdapter = this.adapter2;
            if (voterListNewTableAdapter != null) {
                voterListNewTableAdapter.changeLanguage(lan);
            } else {
                Toast.makeText(getBaseContext(), "Wait. Adapter null", 1).show();
            }
        }
        this.lockButton.setText(this.resources.getString(R.string.lock_text));
        this.insertFpButton.setText(this.resources.getString(R.string.insert_record_text));
        this.alternateButton.setText(this.resources.getString(R.string.alternate_text));
    }

    private long getsqlitedata() {
        long count = this.db.getUsersCount();
        this.db.getTransTableCount();
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
            lockRecordUpdate(false);
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

    public Boolean compareAndLockNetgin(byte[] finger_template1) {
        Cursor cursor = this.db.fpcompareLock();
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return false;
        }
        do {
            byte[] fingerprint1 = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
            numrows++;
            if (this.byTemplate1 != null) {
                NBioBSPJNI nBioBSPJNI = this.bsp;
                Objects.requireNonNull(nBioBSPJNI);
                NBioBSPJNI.FIR_HANDLE hLoadFIR1 = new NBioBSPJNI.FIR_HANDLE();
                this.exportEngine.ImportFIR(finger_template1, finger_template1.length, 3, hLoadFIR1);
                if (this.bsp.IsErrorOccured()) {
                    this.msg = "Template NBioBSP ImportFIR Error: " + this.bsp.GetErrorCode();
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                    return false;
                }
                NBioBSPJNI nBioBSPJNI2 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI2);
                NBioBSPJNI.FIR_HANDLE hLoadFIR2 = new NBioBSPJNI.FIR_HANDLE();
                this.exportEngine.ImportFIR(fingerprint1, fingerprint1.length, 3, hLoadFIR2);
                if (this.bsp.IsErrorOccured()) {
                    hLoadFIR1.dispose();
                    this.msg = "Template NBioBSP ImportFIR Error: " + this.bsp.GetErrorCode();
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                    return false;
                }
                Boolean bResult = new Boolean(false);
                NBioBSPJNI nBioBSPJNI3 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI3);
                NBioBSPJNI.INPUT_FIR inputFIR1 = new NBioBSPJNI.INPUT_FIR();
                NBioBSPJNI nBioBSPJNI4 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI4);
                NBioBSPJNI.INPUT_FIR inputFIR2 = new NBioBSPJNI.INPUT_FIR();
                inputFIR1.SetFIRHandle(hLoadFIR1);
                inputFIR2.SetFIRHandle(hLoadFIR2);
                this.bsp.VerifyMatch(inputFIR1, inputFIR2, bResult, null);
                if (this.bsp.IsErrorOccured()) {
                    this.msg = "Template NBioBSP VerifyMatch Error: " + this.bsp.GetErrorCode();
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                    hLoadFIR1.dispose();
                    hLoadFIR2.dispose();
                } else if (bResult.booleanValue()) {
                    this.msg = "Template VerifyMatch Successed";
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                    return true;
                } else {
                    this.msg = "Template VerifyMatch Failed";
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                    return false;
                }
            } else {
                this.msg = "Can not find captured data";
                Toast.makeText(getApplicationContext(), this.msg, 1).show();
                return false;
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

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    @Override // java.util.Observer
    public void update(Observable o, Object arg) {
    }

    public void captureFingerPrintFromTatvik() {
        try {
            this.captRslt1 = this.tmf20lib.captureFingerprint(10000);
            if (this.captRslt1 == null || TMF20ErrorCodes.SUCCESS != this.captRslt1.getStatusCode()) {
                Toast.makeText(getApplicationContext(), this.resources.getString(R.string.fingerpint_capture_try_again), 1).show();
                if (this.captRslt1.getStatusCode() == 15) {
                    deleteCache(this);
                }
                this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.wrong_icon_trp));
                return;
            }
            this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.right_icon_trp));
        } catch (Exception e) {
        }
    }

    private void updatefingerprintonlineasstring(byte[] finger_template) {
        String voterid = this.voterDataNewModelList.get(0).getEPIC_NO();
        String fp_string = Base64.encodeToString(finger_template, 0);
        Map<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("fp", fp_string);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.19
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
                Context applicationContext = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "updatefingerprint server failed" + t.getMessage(), 1).show();
            }
        });
    }

    private void sync(List<Integer> voters) {
        for (int i = 0; i < voters.size(); i++) {
            updateSingleRow(this.voterDataNewModelList2.get(voters.get(i).intValue()).getEPIC_NO());
        }
    }

    private void updateSingleRow(String voterid) {
        VoterDataNewModel voter = this.db.getVoter(voterid);
        String id_documentimage = voter.getID_DOCUMENT_IMAGE();
        String voteD = voter.getVOTED();
        voter.getVOTING_DATE();
        HashMap<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("fpstring", "yahoo");
        map.put("VOTED", voteD);
        map.put("ID_DOCUMENT_IMAGE", id_documentimage);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postDBUpdate(map).enqueue(new Callback<DBUpdateResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.20
            @Override // retrofit2.Callback
            public void onResponse(Call<DBUpdateResponse> call, Response<DBUpdateResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<DBUpdateResponse> call, Throwable t) {
            }
        });
    }

    private void updateSingleRowExecute(String voterid) {
        VoterDataNewModel voter = this.db.getVoter(voterid);
        String id_documentimage = voter.getID_DOCUMENT_IMAGE();
        String voteD = voter.getVOTED();
        voter.getVOTING_DATE();
        HashMap<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("fpstring", "yahoo");
        map.put("VOTED", voteD);
        map.put("ID_DOCUMENT_IMAGE", id_documentimage);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postDBUpdate(map);
    }

    private void syncTransTable() {
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
        String aadhaarNo;
        String fpString;
        String voting_date;
        String slnoinward = "MATCHED_ID_DOCUMENT_IMAGE";
        String str10 = "MATCHED_USER_ID";
        String str11 = "user_id";
        String str12 = "AADHAAR_NO";
        String str13 = "AADHAAR_MATCH";
        String str14 = "ID_DOCUMENT_IMAGE";
        String str15 = "GENDER";
        String str16 = "AGE";
        String str17 = "VOTING_TYPE";
        int count = 0;
        try {
            Cursor cursor = this.db.getAllRowsofTransTableCursor();
            if (cursor.moveToFirst()) {
                while (true) {
                    long transid = cursor.getLong(cursor.getColumnIndex(DBHelper.Key_ID));
                    int synced = cursor.getInt(cursor.getColumnIndex("SYNCED"));
                    try {
                        int voted = cursor.getInt(cursor.getColumnIndex("VOTED"));
                        String voting_type = cursor.getString(cursor.getColumnIndex(str17));
                        if (synced == 1) {
                            str4 = str11;
                            str6 = str12;
                            str5 = str13;
                            str3 = str14;
                            str8 = str15;
                            str9 = str16;
                            str7 = str17;
                            str = slnoinward;
                            str2 = str10;
                        } else if (voted == 0) {
                            str4 = str11;
                            str6 = str12;
                            str5 = str13;
                            str3 = str14;
                            str8 = str15;
                            str9 = str16;
                            str7 = str17;
                            str = slnoinward;
                            str2 = str10;
                        } else {
                            Map<String, String> map = new HashMap<>();
                            byte[] fp = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
                            int age = cursor.getInt(cursor.getColumnIndex(str16));
                            String gender = cursor.getString(cursor.getColumnIndex(str15));
                            String voterimagename = cursor.getString(cursor.getColumnIndex(str14));
                            int aadhaarmatch = cursor.getInt(cursor.getColumnIndex(str13));
                            String aadhaarNo2 = cursor.getString(cursor.getColumnIndex(str12));
                            if (aadhaarNo2 == null) {
                                aadhaarNo2 = "";
                            }
                            if (fp != null) {
                                aadhaarNo = aadhaarNo2;
                                if (fp.length >= 0) {
                                    fpString = Base64.encodeToString(fp, 0);
                                    map.put("TRANSID", "" + transid);
                                    String userId = this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                                    String boothid = this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo();
                                    voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                                    map.put(str11, userId);
                                    map.put(str11, userId);
                                    str4 = str11;
                                    map.put("FINGERPRINT_TEMPLATE", fpString);
                                    map.put("VOTED", "" + voted);
                                    map.put(str14, voterimagename);
                                    str3 = str14;
                                    map.put(str13, "" + aadhaarmatch);
                                    str5 = str13;
                                    map.put(str12, aadhaarNo);
                                    if (voting_date != null || voting_date.isEmpty() || voting_date.length() <= 0) {
                                        map.put("VOTING_DATE", "");
                                    } else {
                                        map.put("VOTING_DATE", voting_date);
                                    }
                                    str6 = str12;
                                    map.put(str17, voting_type);
                                    map.put("booth_id", boothid);
                                    str7 = str17;
                                    map.put(str16, "" + age);
                                    str9 = str16;
                                    map.put(str15, gender);
                                    str8 = str15;
                                    map.put("udevid", this.UDevId);
                                    String matched_user_id = cursor.getString(cursor.getColumnIndex(str10));
                                    String matched_id_document_iamge = cursor.getString(cursor.getColumnIndex(slnoinward));
                                    map.put("matched_user_id", matched_user_id);
                                    map.put(str10, matched_user_id);
                                    map.put(slnoinward, matched_id_document_iamge);
                                    str2 = str10;
                                    str = slnoinward;
                                    new Handler().postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.21
                                        @Override // java.lang.Runnable
                                        public void run() {
                                        }
                                    }, 100);
                                    uploadTransactionRow(map);
                                }
                            } else {
                                aadhaarNo = aadhaarNo2;
                            }
                            fpString = "";
                            map.put("TRANSID", "" + transid);
                            String userId2 = this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                            String boothid2 = this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo();
                            voting_date = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                            map.put(str11, userId2);
                            map.put(str11, userId2);
                            str4 = str11;
                            map.put("FINGERPRINT_TEMPLATE", fpString);
                            map.put("VOTED", "" + voted);
                            map.put(str14, voterimagename);
                            str3 = str14;
                            map.put(str13, "" + aadhaarmatch);
                            str5 = str13;
                            map.put(str12, aadhaarNo);
                            if (voting_date != null) {
                            }
                            map.put("VOTING_DATE", "");
                            str6 = str12;
                            map.put(str17, voting_type);
                            map.put("booth_id", boothid2);
                            str7 = str17;
                            map.put(str16, "" + age);
                            str9 = str16;
                            map.put(str15, gender);
                            str8 = str15;
                            map.put("udevid", this.UDevId);
                            String matched_user_id2 = cursor.getString(cursor.getColumnIndex(str10));
                            String matched_id_document_iamge2 = cursor.getString(cursor.getColumnIndex(slnoinward));
                            map.put("matched_user_id", matched_user_id2);
                            map.put(str10, matched_user_id2);
                            map.put(slnoinward, matched_id_document_iamge2);
                            str2 = str10;
                            str = slnoinward;
                            new Handler().postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.21
                                @Override // java.lang.Runnable
                                public void run() {
                                }
                            }, 100);
                            uploadTransactionRow(map);
                        }
                        if (cursor.moveToNext()) {
                            count = count;
                            str16 = str9;
                            str15 = str8;
                            str17 = str7;
                            str12 = str6;
                            str13 = str5;
                            str11 = str4;
                            str14 = str3;
                            str10 = str2;
                            slnoinward = str;
                        } else {
                            return;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Toast.makeText(getApplicationContext(), "sync exception=" + e.getMessage(), 1).show();
                        return;
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private void syncIndividualRowTransTable(Map<String, String> map) {
    }

    private void logoutSendUpdate() {
        Map<String, String> map = new HashMap<>();
        map.put("logoutdate", getCurrentTimeInFormat());
        map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getBoothNo());
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).lockRecordUpdate(map).enqueue(new Callback<LockUpdateResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.22
            @Override // retrofit2.Callback
            public void onResponse(Call<LockUpdateResponse> call, Response<LockUpdateResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LockUpdateResponse> call, Throwable t) {
            }
        });
    }

    public void lockRecordUpdate(boolean islock) {
        String device;
        Map<String, String> map = new HashMap<>();
        if (((TelephonyManager) Objects.requireNonNull((TelephonyManager) getApplicationContext().getSystemService("phone"))).getPhoneType() == 0) {
            device = "tablet";
        } else {
            device = "mobile";
        }
        if (islock) {
            map.put("lockdate", getCurrentTimeInFormat());
            map.put(NotificationCompat.CATEGORY_EVENT, "lock");
        } else {
            map.put("unlockdate", getCurrentTimeInFormat());
            map.put(NotificationCompat.CATEGORY_EVENT, "unlock");
        }
        map.put("machine", device);
        map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo());
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).lockRecordUpdate(map).enqueue(new Callback<LockUpdateResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.23
            @Override // retrofit2.Callback
            public void onResponse(Call<LockUpdateResponse> call, Response<LockUpdateResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LockUpdateResponse> call, Throwable t) {
            }
        });
    }

    private void logoutEvent() {
        String device;
        Map<String, String> map = new HashMap<>();
        if (((TelephonyManager) Objects.requireNonNull((TelephonyManager) getApplicationContext().getSystemService("phone"))).getPhoneType() == 0) {
            device = "tablet";
        } else {
            device = "mobile";
        }
        map.put(NotificationCompat.CATEGORY_EVENT, "logout");
        map.put("machine", device);
        map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo());
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).lockRecordUpdate(map).enqueue(new Callback<LockUpdateResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.24
            @Override // retrofit2.Callback
            public void onResponse(Call<LockUpdateResponse> call, Response<LockUpdateResponse> response) {
                Toast.makeText(ListUserActivity.this.getApplicationContext(), "response positive", 1).show();
                ListUserActivity.this.startLoginActivity();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LockUpdateResponse> call, Throwable t) {
                ListUserActivity.this.startLoginActivity();
            }
        });
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }

    public String getCurrentTimeInFormatForCSV() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }

    private void uploadTransactionRow(Map<String, String> map) {
        final String transId = map.get("TRANSID");
        ((GetDataService) RetrofitClientInstance.getRetrofitInstanceForSync().create(GetDataService.class)).updateTransactionRow(map).enqueue(new Callback<TransactionRowPostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.25
            @Override // retrofit2.Callback
            public void onResponse(Call<TransactionRowPostResponse> call, Response<TransactionRowPostResponse> response) {
                Log.d("syncing", "responserawstring list" + response.raw().toString());
                if (response == null || response.body() == null || !response.body().getUpdated()) {
                    Context applicationContext = ListUserActivity.this.getApplicationContext();
                    Toast.makeText(applicationContext, "voterlistTransaction row Not updated " + response.code(), 1).show();
                    return;
                }
                ListUserActivity.this.db.updateSync(Integer.parseInt(transId), 1);
                Context applicationContext2 = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext2, "voterlistTransaction row updated successfully" + response.code(), 1).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<TransactionRowPostResponse> call, Throwable t) {
                Context applicationContext = ListUserActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "voterlistTransaction update Error. " + t.getMessage(), 1).show();
            }
        });
    }

    private void startVotingStatusActivity() {
        startActivity(new Intent(this, VotingStatusActivity.class));
    }

    public String getBoothInFormat(String boothnumstr) {
        Integer boothnum = Integer.valueOf(Integer.parseInt(boothnumstr));
        if (boothnum.intValue() <= 1000) {
            return String.valueOf(boothnum);
        }
        if (boothnum.intValue() > 1000 && boothnum.intValue() <= 2000) {
            return String.valueOf((boothnum.intValue() - 1000) + "क");
        } else if (boothnum.intValue() > 2000 && boothnum.intValue() <= 3000) {
            return String.valueOf((boothnum.intValue() - 2000) + "ख");
        } else if (boothnum.intValue() > 3000 && boothnum.intValue() <= 4000) {
            return String.valueOf((boothnum.intValue() - PathInterpolatorCompat.MAX_NUM_POINTS) + "ग");
        } else if (boothnum.intValue() > 4000 && boothnum.intValue() <= 5000) {
            return String.valueOf((boothnum.intValue() - 4000) + "घ");
        } else if (boothnum.intValue() > 5000 && boothnum.intValue() <= 6000) {
            return String.valueOf((boothnum.intValue() - 5000) + "ङ");
        } else if (boothnum.intValue() <= 6000 || boothnum.intValue() > 7000) {
            return "";
        } else {
            return String.valueOf((boothnum.intValue() - 6000) + "च");
        }
    }

    public static void deleteCache(Context context) {
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (String str : dir.list()) {
                if (!deleteDir(new File(dir, str))) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir == null || !dir.isFile()) {
            return false;
        } else {
            return dir.delete();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x015b A[LOOP:0: B:28:0x0051->B:25:0x015b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0166 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private void imageupload() {
        Cursor cursor;
        long imagecounttobesynced;
        boolean z;
        Exception e;
        DBHelper db = new DBHelper(this);
        long imagecounttobesynced2 = db.getImageUnSyncCount();
        boolean z2 = true;
        if (imagecounttobesynced2 <= 0) {
            Toast.makeText(this, "All images already synced", 1).show();
            return;
        }
        Toast.makeText(this, imagecounttobesynced2 + " images to be synced", 1).show();
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        UserAuth userAuth = new UserAuth(this);
        Cursor cursor2 = db.getAllRowsofTransTableCursor();
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
        Toast.makeText(this, imagecounttobesynced2 + "rejected images to be synced", 1).show();
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

    private synchronized void postDataWithImage(HashMap<String, RequestBody> map, File file, String filename, final DBHelper db, final int transid, final String which_image) {
        Call<ImageUploadResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceImageUploadNewUrl().create(GetDataService.class)).postVoterIdentification(MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, filename, RequestBody.create(MediaType.parse("multipart/form-data"), file)), map);
        Log.d("autosync", "postDataWithImage 1");
        call.enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.ListUserActivity.26
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
                    Toast.makeText(ListUserActivity.this.getApplicationContext(), "Image Synchronizing ", 0).show();
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

    private RequestBody createPartFromString(String descriptionString) {
        try {
            return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        } catch (Exception e) {
            return null;
        }
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

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.tmf20lib == null) {
            this.tmf20lib = new TMF20API(this);
        }
    }
}
