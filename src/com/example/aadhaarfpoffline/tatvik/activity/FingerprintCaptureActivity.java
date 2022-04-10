package com.example.aadhaarfpoffline.tatvik.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.network.BoothOfficerDeviceStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.FinperprintCompareServerResponse;
import com.example.aadhaarfpoffline.tatvik.network.MultipleFpUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserVotingStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.commons.io.IOUtils;
import org.tatvik.fp.CaptureResult;
import org.tatvik.fp.TMF20API;
import org.tatvik.fp.TMF20ErrorCodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class FingerprintCaptureActivity extends AppCompatActivity implements MFS100Event, Observer {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final int QUALITY_LIMIT = 60;
    byte[] Enroll_Template;
    byte[] Verify_Template;
    private NBioBSPJNI bsp;
    Button btnBack;
    Button btnClearLog;
    Button btnExtractAnsi;
    Button btnExtractISOImage;
    Button btnExtractWSQImage;
    Button btnInit;
    Button btnMatchISOTemplate;
    Button btnStopCapture;
    Button btnSyncCapture;
    Button btnUninit;
    Button btnUpLoadFp;
    private byte[] byCapturedRaw1;
    private byte[] byTemplate1;
    CaptureResult captRslt1;
    CheckBox cbFastDetection;
    Context context;
    DBHelper db;
    private NBioBSPJNI.Export exportEngine;
    byte[] finger_template1;
    byte[] finger_template2;
    ImageView imgFinger;
    ImageView imgFinger2;
    ImageView imgFinger3;
    private NBioBSPJNI.IndexSearch indexSearch;
    TextView lblMessage;
    private int nCapturedRawHeight1;
    private int nCapturedRawWidth1;
    Resources resources;
    ProgressBar simpleProgressBar;
    TMF20API tmf20lib;
    EditText txtEventLog;
    UserAuth userAuth;
    private static long mLastClkTime = 0;
    private static long Threshold = 1500;
    Boolean firstfp = false;
    Boolean secondfp = false;
    Boolean thirdfp = false;
    Boolean FingerprintMatchFound = false;
    private FingerData lastCapFingerData = null;
    ScannerAction scannerAction = ScannerAction.Capture;
    int timeout = 10000;
    MFS100 mfs100 = null;
    private boolean isCaptureRunning = false;
    int fingerprintIndex = 0;
    List<File> fileList = new ArrayList();
    String votername = "";
    String district = "";
    String blockno = "";
    String blockid = "";
    String voterid = "";
    String slnoinward = "";
    String nameIfFoundFp = "";
    String nameIfFoundFace = "";
    String VoterIdentificationimage = "";
    String voted = "";
    String voteridtype = "";
    Boolean FaceFound = false;
    int nFIQ = 0;
    String msg = "";
    NBioBSPJNI.CAPTURE_CALLBACK mCallback = new NBioBSPJNI.CAPTURE_CALLBACK() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.1
        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public void OnDisConnected() {
            NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
            String str = "NBioBSP Disconnected: " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
        }

        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public void OnConnected() {
            String str = "Device Open Success : " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
        }

        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public int OnCaptured(NBioBSPJNI.CAPTURED_DATA capturedData) {
            if (capturedData.getImage() != null) {
                FingerprintCaptureActivity.this.imgFinger.setImageBitmap(capturedData.getImage());
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

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum ScannerAction {
        Capture,
        Verify
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_capture_new);
        String lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        this.tmf20lib = new TMF20API(this);
        this.userAuth = new UserAuth(this);
        Intent intent = getIntent();
        this.voterid = intent.getStringExtra("voter_id");
        this.votername = intent.getStringExtra("voter_name");
        this.district = intent.getStringExtra("district");
        this.blockno = intent.getStringExtra("blockno");
        this.blockid = intent.getStringExtra("blockid");
        this.VoterIdentificationimage = intent.getStringExtra("voteridentificationimage");
        this.voteridtype = intent.getStringExtra("iddoctype");
        this.FaceFound = Boolean.valueOf(intent.getBooleanExtra("facefound", false));
        this.nameIfFoundFace = intent.getStringExtra("nameIfFoundFace");
        this.voted = intent.getStringExtra("voted");
        this.slnoinward = intent.getStringExtra("slnoinward");
        FindFormControls();
        try {
            getWindow().setSoftInputMode(3);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        try {
            this.mfs100 = new MFS100(this);
            this.mfs100.SetApplicationContext(this);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        translate(lan);
        initData();
    }

    private void initData() {
        NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
        if (this.bsp == null) {
            this.bsp = new NBioBSPJNI("010701-613E5C7F4CC7C4B0-72E340B47E034015", this, this.mCallback);
            String msg = null;
            if (this.bsp.IsErrorOccured()) {
                msg = "NBioBSP Error: " + this.bsp.GetErrorCode();
            } else {
                NBioBSPJNI nBioBSPJNI = this.bsp;
                Objects.requireNonNull(nBioBSPJNI);
                this.exportEngine = new NBioBSPJNI.Export();
                NBioBSPJNI nBioBSPJNI2 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI2);
                this.indexSearch = new NBioBSPJNI.IndexSearch();
            }
            Toast.makeText(getApplicationContext(), msg, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void captureFingerPrintFromNitgen() {
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
            return;
        }
        NBioBSPJNI nBioBSPJNI4 = this.bsp;
        Objects.requireNonNull(nBioBSPJNI4);
        NBioBSPJNI.INPUT_FIR inputFIR = new NBioBSPJNI.INPUT_FIR();
        inputFIR.SetFIRHandle(hCapturedFIR);
        NBioBSPJNI.Export export = this.exportEngine;
        Objects.requireNonNull(export);
        NBioBSPJNI.Export.DATA exportData = new NBioBSPJNI.Export.DATA();
        this.exportEngine.ExportFIR(inputFIR, exportData, 3);
        if (this.bsp.IsErrorOccured()) {
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                    fingerprintCaptureActivity.msg = "NBioBSP ExportFIR Error: " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
                    Toast.makeText(FingerprintCaptureActivity.this.getApplicationContext(), FingerprintCaptureActivity.this.msg, 0).show();
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                    fingerprintCaptureActivity.msg = "NBioBSP ExportAudit Error: " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
                    Toast.makeText(FingerprintCaptureActivity.this.getApplicationContext(), FingerprintCaptureActivity.this.msg, 0).show();
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                    fingerprintCaptureActivity.msg = "NBioBSP getNFIQInfoFromRaw Error: " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
                    Toast.makeText(FingerprintCaptureActivity.this.getApplicationContext(), FingerprintCaptureActivity.this.msg, 0).show();
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                    fingerprintCaptureActivity.msg = "NBioBSP ExportRawToISOV1 Error: " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
                    Toast.makeText(FingerprintCaptureActivity.this.getApplicationContext(), FingerprintCaptureActivity.this.msg, 0).show();
                }
            });
            return;
        }
        NBioBSPJNI nBioBSPJNI7 = this.bsp;
        Objects.requireNonNull(nBioBSPJNI7);
        NBioBSPJNI.NIMPORTRAWSET rawSet = new NBioBSPJNI.NIMPORTRAWSET();
        this.bsp.ImportISOToRaw(ISOBuffer, rawSet);
        if (this.bsp.IsErrorOccured()) {
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.6
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                    fingerprintCaptureActivity.msg = "NBioBSP ImportISOToRaw Error: " + FingerprintCaptureActivity.this.bsp.GetErrorCode();
                    Toast.makeText(FingerprintCaptureActivity.this.getApplicationContext(), FingerprintCaptureActivity.this.msg, 0).show();
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
        runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.7
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(FingerprintCaptureActivity.this.getApplicationContext(), FingerprintCaptureActivity.this.msg, 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        try {
            if (this.isCaptureRunning) {
                this.mfs100.StopAutoCapture();
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        try {
            if (this.mfs100 != null) {
                this.mfs100.Dispose();
            }
            if (this.bsp != null) {
                this.bsp.dispose();
                this.bsp = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void FindFormControls() {
        try {
            this.btnInit = (Button) findViewById(R.id.btnInit);
            this.btnUninit = (Button) findViewById(R.id.btnUninit);
            this.btnMatchISOTemplate = (Button) findViewById(R.id.btnMatchISOTemplate);
            this.btnExtractISOImage = (Button) findViewById(R.id.btnExtractISOImage);
            this.btnExtractAnsi = (Button) findViewById(R.id.btnExtractAnsi);
            this.btnExtractWSQImage = (Button) findViewById(R.id.btnExtractWSQImage);
            this.btnClearLog = (Button) findViewById(R.id.btnClearLog);
            this.lblMessage = (TextView) findViewById(R.id.lblMessage);
            this.txtEventLog = (EditText) findViewById(R.id.txtEventLog);
            this.imgFinger = (ImageView) findViewById(R.id.imgFinger);
            this.imgFinger2 = (ImageView) findViewById(R.id.imgFinger2);
            this.imgFinger3 = (ImageView) findViewById(R.id.imgFinger3);
            this.btnSyncCapture = (Button) findViewById(R.id.btnSyncCapture);
            this.btnStopCapture = (Button) findViewById(R.id.btnStopCapture);
            this.cbFastDetection = (CheckBox) findViewById(R.id.cbFastDetection);
            this.simpleProgressBar = (ProgressBar) findViewById(R.id.simpleprogressbar);
            this.simpleProgressBar.setVisibility(8);
            this.btnUpLoadFp = (Button) findViewById(R.id.btnUploadFp);
            this.btnBack = (Button) findViewById(R.id.btnBack);
            this.btnUpLoadFp.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.8
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    System.out.println("print clicked");
                    FingerprintCaptureActivity.this.SetTextOnUIThread("upload clicked");
                    if (FingerprintCaptureActivity.this.fileList != null && FingerprintCaptureActivity.this.fileList.size() == 3) {
                        FingerprintCaptureActivity.this.simpleProgressBar.setVisibility(0);
                        FingerprintCaptureActivity.this.uploadImages();
                    }
                }
            });
            this.btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.9
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    FingerprintCaptureActivity.this.simpleProgressBar.setVisibility(8);
                    FingerprintCaptureActivity.this.startActivity(new Intent(FingerprintCaptureActivity.this, ListUserActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onControlClicked(View v) {
        if (SystemClock.elapsedRealtime() - mLastClkTime >= Threshold) {
            mLastClkTime = SystemClock.elapsedRealtime();
            try {
                switch (v.getId()) {
                    case R.id.btnClearLog:
                        ClearLog();
                        return;
                    case R.id.btnExtractAnsi:
                        ExtractANSITemplate();
                        return;
                    case R.id.btnExtractISOImage:
                        ExtractISOImage();
                        return;
                    case R.id.btnExtractWSQImage:
                        ExtractWSQImage();
                        return;
                    case R.id.btnInit:
                        InitScanner();
                        return;
                    case R.id.btnMatchISOTemplate:
                        this.scannerAction = ScannerAction.Verify;
                        if (!this.isCaptureRunning) {
                            StartSyncCapture();
                            return;
                        }
                        return;
                    case R.id.btnNext:
                    case R.id.btnSubmitDevice:
                    default:
                        return;
                    case R.id.btnStopCapture:
                        if (!this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                            if (this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                                if (this.captRslt1 != null && this.captRslt1.getFmrBytes() != null && this.captRslt1.getFmrBytes().length > 0) {
                                    String matchvoterid = CompareFingerprintTatvikTransTable(this.voterid, this.captRslt1.getFmrBytes());
                                    if (matchvoterid.length() == 0) {
                                        updatefingerprintdbTransTable(this.captRslt1.getFmrBytes(), this.userAuth.getTransactionId().longValue());
                                        offlineNextScreen("You can vote", true, matchvoterid);
                                    } else {
                                        updatefingerprintdbTransTable(this.captRslt1.getFmrBytes(), this.userAuth.getTransactionId().longValue());
                                        offlineNextScreen("You can't vote", false, matchvoterid);
                                    }
                                    return;
                                }
                                return;
                            } else if (!this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                                return;
                            } else {
                                if (this.byTemplate1 == null || this.byTemplate1.length <= 0) {
                                    Toast.makeText(getApplicationContext(), "Capture Fingerprint first", 1).show();
                                    return;
                                }
                                String matchvoterid2 = compareAndLockNetgin(this.voterid, this.byTemplate1);
                                Log.e("Matchvoterid1", matchvoterid2 + "!!!");
                                Context applicationContext = getApplicationContext();
                                Toast.makeText(applicationContext, "matchvoterid=" + matchvoterid2 + " len=" + matchvoterid2.length(), 1).show();
                                if (matchvoterid2.length() == 0) {
                                    Context applicationContext2 = getApplicationContext();
                                    Toast.makeText(applicationContext2, "matchvoterid=" + matchvoterid2 + " if len=" + matchvoterid2.length(), 1).show();
                                    updatefingerprintdbTransTable(this.byTemplate1, this.userAuth.getTransactionId().longValue());
                                    offlineNextScreen("You can vote", true, matchvoterid2);
                                    Log.e("updatefingerprintdbTransTable", "updatefingerprintdbTransTable !!!");
                                } else {
                                    Context applicationContext3 = getApplicationContext();
                                    Toast.makeText(applicationContext3, "matchvoterid=" + matchvoterid2 + " else len=" + matchvoterid2.length(), 1).show();
                                    offlineNextScreen("You can't vote", false, matchvoterid2);
                                }
                                return;
                            }
                        } else {
                            return;
                        }
                    case R.id.btnSyncCapture:
                        if (!this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                            if (this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                                captureFingerprintTatvik();
                                return;
                            } else if (this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                                new AsyncCaller().execute(new Void[0]);
                                return;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    case R.id.btnUninit:
                        UnInitScanner();
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* loaded from: classes2.dex */
    private class AsyncCaller extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pdLoading;

        private AsyncCaller() {
            this.pdLoading = null;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            this.pdLoading = new ProgressDialog(FingerprintCaptureActivity.this);
            this.pdLoading.setMessage("\tLoading...");
            this.pdLoading.show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            boolean isConnected = false;
            if (FingerprintCaptureActivity.this.bsp != null) {
                isConnected = FingerprintCaptureActivity.this.bsp.OpenDevice();
            }
            return Boolean.valueOf(isConnected);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute((AsyncCaller) result);
            ProgressDialog progressDialog = this.pdLoading;
            if (progressDialog != null && progressDialog.isShowing()) {
                this.pdLoading.dismiss();
            }
            if (result.booleanValue()) {
                FingerprintCaptureActivity.this.captureFingerPrintFromNitgen();
            } else {
                Toast.makeText(FingerprintCaptureActivity.this, Const.nBioDeviceError, 0).show();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_mfs100_sample);
        FindFormControls();
        try {
            if (this.mfs100 == null) {
                this.mfs100 = new MFS100(this);
                this.mfs100.SetApplicationContext(this);
            }
            if (this.isCaptureRunning && this.mfs100 != null) {
                this.mfs100.StopAutoCapture();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitScanner() {
        try {
            int ret = this.mfs100.Init();
            if (ret != 0) {
                SetTextOnUIThread(this.mfs100.GetErrorMsg(ret));
                return;
            }
            SetTextOnUIThread("Init success");
            SetLogOnUIThread("Serial: " + this.mfs100.GetDeviceInfo().SerialNo() + " Make: " + this.mfs100.GetDeviceInfo().Make() + " Model: " + this.mfs100.GetDeviceInfo().Model() + "\nCertificate: " + this.mfs100.GetCertification());
        } catch (Exception e) {
            while (true) {
                SetTextOnUIThread("Init failed, unhandled exception");
                return;
            }
        }
    }

    private void StartSyncCapture() {
        new Thread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.10
            @Override // java.lang.Runnable
            public void run() {
                FingerprintCaptureActivity.this.SetTextOnUIThread("");
                FingerprintCaptureActivity.this.isCaptureRunning = true;
                try {
                    try {
                        final FingerData fingerData = new FingerData();
                        int ret = FingerprintCaptureActivity.this.mfs100.AutoCapture(fingerData, FingerprintCaptureActivity.this.timeout, FingerprintCaptureActivity.this.cbFastDetection.isChecked());
                        Log.e("StartSyncCapture.RET", "" + ret);
                        if (ret != 0) {
                            FingerprintCaptureActivity.this.SetTextOnUIThread(FingerprintCaptureActivity.this.mfs100.GetErrorMsg(ret));
                        } else {
                            FingerprintCaptureActivity.this.lastCapFingerData = fingerData;
                            final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);
                            FingerprintCaptureActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.10.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (!FingerprintCaptureActivity.this.firstfp.booleanValue()) {
                                        FingerprintCaptureActivity.this.fileList.add(FingerprintCaptureActivity.this.bitmapToFile(bitmap, "abc1"));
                                        FingerprintCaptureActivity.this.imgFinger.setImageBitmap(bitmap);
                                        FingerprintCaptureActivity.this.firstfp = true;
                                        FingerprintCaptureActivity.this.finger_template1 = new byte[fingerData.ISOTemplate().length];
                                        System.arraycopy(fingerData.ISOTemplate(), 0, FingerprintCaptureActivity.this.finger_template1, 0, fingerData.ISOTemplate().length);
                                    }
                                }
                            });
                            FingerprintCaptureActivity.this.SetTextOnUIThread("Capture Success");
                            FingerprintCaptureActivity.this.SetLogOnUIThread("\nQuality: " + fingerData.Quality() + "\nNFIQ: " + fingerData.Nfiq() + "\nWSQ Compress Ratio: " + fingerData.WSQCompressRatio() + "\nImage Dimensions (inch): " + fingerData.InWidth() + "\" X " + fingerData.InHeight() + "\"\nImage Area (inch): " + fingerData.InArea() + "\"\nResolution (dpi/ppi): " + fingerData.Resolution() + "\nGray Scale: " + fingerData.GrayScale() + "\nBits Per Pixal: " + fingerData.Bpp() + "\nWSQ Info: " + fingerData.WSQInfo());
                            FingerprintCaptureActivity.this.SetData2(fingerData);
                        }
                    } catch (Exception e) {
                        FingerprintCaptureActivity.this.SetTextOnUIThread("Error");
                    }
                } finally {
                    FingerprintCaptureActivity.this.isCaptureRunning = false;
                }
            }
        }).start();
    }

    private void StopCapture() {
        try {
            this.mfs100.StopAutoCapture();
        } catch (Exception e) {
            SetTextOnUIThread("Error");
        }
    }

    private void ExtractANSITemplate() {
        try {
            if (this.lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[2000];
            int dataLen = this.mfs100.ExtractANSITemplate(this.lastCapFingerData.RawData(), tempData);
            if (dataLen > 0) {
                byte[] ansiTemplate = new byte[dataLen];
                System.arraycopy(tempData, 0, ansiTemplate, 0, dataLen);
                WriteFile("ANSITemplate.ansi", ansiTemplate);
                SetTextOnUIThread("Extract ANSI Template Success");
            } else if (dataLen == 0) {
                SetTextOnUIThread("Failed to extract ANSI Template");
            } else {
                SetTextOnUIThread(this.mfs100.GetErrorMsg(dataLen));
            }
        } catch (Exception e) {
            Log.e("Error", "Extract ANSI Template Error", e);
        }
    }

    private void ExtractISOImage() {
        try {
            if (this.lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[(this.mfs100.GetDeviceInfo().Width() * this.mfs100.GetDeviceInfo().Height()) + 1078];
            int dataLen = this.mfs100.ExtractISOImage(this.lastCapFingerData.RawData(), tempData, 2);
            if (dataLen > 0) {
                byte[] isoImage = new byte[dataLen];
                System.arraycopy(tempData, 0, isoImage, 0, dataLen);
                WriteFile("ISOImage.iso", isoImage);
                SetTextOnUIThread("Extract ISO Image Success");
            } else if (dataLen == 0) {
                SetTextOnUIThread("Failed to extract ISO Image");
            } else {
                SetTextOnUIThread(this.mfs100.GetErrorMsg(dataLen));
            }
        } catch (Exception e) {
            Log.e("Error", "Extract ISO Image Error", e);
        }
    }

    private void ExtractWSQImage() {
        try {
            if (this.lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[(this.mfs100.GetDeviceInfo().Width() * this.mfs100.GetDeviceInfo().Height()) + 1078];
            int dataLen = this.mfs100.ExtractWSQImage(this.lastCapFingerData.RawData(), tempData);
            if (dataLen > 0) {
                byte[] wsqImage = new byte[dataLen];
                System.arraycopy(tempData, 0, wsqImage, 0, dataLen);
                WriteFile("WSQ.wsq", wsqImage);
                SetTextOnUIThread("Extract WSQ Image Success");
            } else if (dataLen == 0) {
                SetTextOnUIThread("Failed to extract WSQ Image");
            } else {
                SetTextOnUIThread(this.mfs100.GetErrorMsg(dataLen));
            }
        } catch (Exception e) {
            Log.e("Error", "Extract WSQ Image Error", e);
        }
    }

    private void UnInitScanner() {
        try {
            int ret = this.mfs100.UnInit();
            if (ret != 0) {
                SetTextOnUIThread(this.mfs100.GetErrorMsg(ret));
                return;
            }
            SetLogOnUIThread("Uninit Success");
            SetTextOnUIThread("Uninit Success");
            this.lastCapFingerData = null;
        } catch (Exception e) {
            while (true) {
                Log.e("UnInitScanner.EX", e.toString());
                return;
            }
        }
    }

    private void WriteFile(String filename, byte[] bytes) {
        try {
            String path = Environment.getExternalStorageDirectory() + "//FingerData";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String path2 = path + "//" + filename;
            File file2 = new File(path2);
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path2);
            stream.write(bytes);
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void WriteFileString(String filename, String data) {
        try {
            String path = Environment.getExternalStorageDirectory() + "//FingerData";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String path2 = path + "//" + filename;
            File file2 = new File(path2);
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path2);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(data);
            writer.flush();
            writer.close();
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void ClearLog() {
        this.txtEventLog.post(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FingerprintCaptureActivity.this.txtEventLog.setText("", TextView.BufferType.EDITABLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void SetTextOnUIThread(final String str) {
        this.lblMessage.post(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FingerprintCaptureActivity.this.lblMessage.setText(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgressBarInvisible() {
        this.simpleProgressBar.post(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.13
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FingerprintCaptureActivity.this.simpleProgressBar.setVisibility(8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkingfn(String str) {
        this.lblMessage.post(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.14
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FingerprintCaptureActivity.this.updateUserVotingStatus(-6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void SetLogOnUIThread(final String str) {
        this.txtEventLog.post(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.15
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EditText editText = FingerprintCaptureActivity.this.txtEventLog;
                    editText.append(IOUtils.LINE_SEPARATOR_UNIX + str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void SetData2(FingerData fingerData) {
        try {
            if (this.scannerAction.equals(ScannerAction.Capture)) {
                this.Enroll_Template = new byte[fingerData.ISOTemplate().length];
                System.arraycopy(fingerData.ISOTemplate(), 0, this.Enroll_Template, 0, fingerData.ISOTemplate().length);
            } else if (this.scannerAction.equals(ScannerAction.Verify)) {
                if (this.Enroll_Template != null) {
                    this.Verify_Template = new byte[fingerData.ISOTemplate().length];
                    System.arraycopy(fingerData.ISOTemplate(), 0, this.Verify_Template, 0, fingerData.ISOTemplate().length);
                    int ret = this.mfs100.MatchISO(this.Enroll_Template, this.Verify_Template);
                    if (ret < 0) {
                        SetTextOnUIThread("Error: " + ret + "(" + this.mfs100.GetErrorMsg(ret) + ")");
                    } else if (ret >= 96) {
                        SetTextOnUIThread("Finger matched with score: " + ret);
                    } else {
                        SetTextOnUIThread("Finger not matched, score: " + ret);
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            WriteFile("Raw.raw", fingerData.RawData());
            WriteFile("Bitmap.bmp", fingerData.FingerImage());
            WriteFile("ISOTemplate.iso", fingerData.ISOTemplate());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        if (SystemClock.elapsedRealtime() - this.mLastAttTime >= Threshold) {
            this.mLastAttTime = SystemClock.elapsedRealtime();
            if (!hasPermission) {
                SetTextOnUIThread("Permission denied");
            } else if (vid == 1204 || vid == 11279) {
                try {
                    if (pid == 34323) {
                        int ret = this.mfs100.LoadFirmware();
                        if (ret != 0) {
                            SetTextOnUIThread(this.mfs100.GetErrorMsg(ret));
                        } else {
                            SetTextOnUIThread("Load firmware success");
                        }
                    } else if (pid == 4101) {
                        int ret2 = this.mfs100.Init();
                        if (ret2 == 0) {
                            showSuccessLog("Without Key");
                        } else {
                            SetTextOnUIThread(this.mfs100.GetErrorMsg(ret2));
                        }
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

    private void showSuccessLog(String key) {
        try {
            SetTextOnUIThread("Init success");
            SetLogOnUIThread("\nKey: " + key + "\nSerial: " + this.mfs100.GetDeviceInfo().SerialNo() + " Make: " + this.mfs100.GetDeviceInfo().Make() + " Model: " + this.mfs100.GetDeviceInfo().Model() + "\nCertificate: " + this.mfs100.GetCertification());
        } catch (Exception e) {
        }
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnDeviceDetached() {
        try {
            if (SystemClock.elapsedRealtime() - this.mLastDttTime >= Threshold) {
                this.mLastDttTime = SystemClock.elapsedRealtime();
                UnInitScanner();
                updatedevicedetached();
                SetTextOnUIThread(this.resources.getString(R.string.device_removed_text));
            }
        } catch (Exception e) {
        }
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnHostCheckFailed(String err) {
        try {
            SetLogOnUIThread(err);
            Toast.makeText(getApplicationContext(), err, 1).show();
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File bitmapToFile(Bitmap bitmap1, String filename) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, filename + ".jpg");
        try {
            OutputStream os = new FileOutputStream(imageFile);
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            return null;
        }
    }

    private MultipartBody.Part[] multipart(List<File> fileList) {
        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            surveyImagesParts[i] = MultipartBody.Part.createFormData("imagelist[]", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), fileList.get(i)));
        }
        return surveyImagesParts;
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    private RequestBody createPartFromByteArray(byte[] descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImages() {
        System.out.println("uploadimages");
        MultipartBody.Part[] multi = multipart(this.fileList);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("votername", createPartFromString(this.votername));
        map.put("district", createPartFromString(this.district));
        map.put("blockno", createPartFromString(this.blockno));
        map.put("blockid", createPartFromString(this.blockid));
        map.put("voterid", createPartFromString(this.voterid));
        map.put("voteridentificaiton", createPartFromString(this.VoterIdentificationimage));
        map.put("iddoctype", createPartFromString(this.voteridtype));
        System.out.println("uploadimageslast");
        postDataWithImage(map, multi);
    }

    private void postDataWithImage(HashMap<String, RequestBody> map, MultipartBody.Part[] multipart) {
        System.out.println("postdatawithimage1");
        Call<MultipleFpUploadResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVoterIdentificationMultiFingerP(multipart, map);
        System.out.println("postdatawithimage2");
        call.enqueue(new Callback<MultipleFpUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.16
            @Override // retrofit2.Callback
            public void onResponse(Call<MultipleFpUploadResponse> call2, Response<MultipleFpUploadResponse> response) {
                PrintStream printStream = System.out;
                printStream.println("postdatawithimage_success=" + response.toString());
                FingerprintCaptureActivity.this.SetTextOnUIThread("upload on response");
                FingerprintCaptureActivity.this.setProgressBarInvisible();
                FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                fingerprintCaptureActivity.deleteFpImages(fingerprintCaptureActivity.fileList);
                if (response == null || !response.isSuccessful() || response.body() == null) {
                    FingerprintCaptureActivity.this.SetTextOnUIThread("response problem");
                } else if (response.body().isFound().booleanValue()) {
                    FingerprintCaptureActivity.this.nameIfFoundFp = response.body().getName();
                    FingerprintCaptureActivity.this.FingerprintMatchFound = true;
                    FingerprintCaptureActivity.this.SetTextOnUIThread("response found");
                    System.out.println("postdatawithimage_success2 found");
                    FingerprintCaptureActivity.this.SetTextOnUIThread("You cannot vote");
                    FingerprintCaptureActivity.this.NextScreen("You are not allowed to vote.", true);
                } else {
                    FingerprintCaptureActivity.this.FingerprintMatchFound = false;
                    System.out.println("postdatawithimage_success2 not found");
                    FingerprintCaptureActivity.this.SetTextOnUIThread("You can vote");
                    FingerprintCaptureActivity.this.updateUserVotingStatus(1);
                    FingerprintCaptureActivity.this.NextScreen("You can vote.", false);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<MultipleFpUploadResponse> call2, Throwable t) {
                FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                fingerprintCaptureActivity.SetTextOnUIThread("on failure" + t.toString());
                FingerprintCaptureActivity.this.setProgressBarInvisible();
                PrintStream printStream = System.out;
                printStream.println("postdatawithimage_failure" + t.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserVotingStatus(final int votingstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("votingstatus", "" + votingstatus);
        map.put("voterid", this.voterid);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVotingStatusUpdate(map).enqueue(new Callback<UserVotingStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.17
            @Override // retrofit2.Callback
            public void onResponse(Call<UserVotingStatusUpdatePostResponse> call, Response<UserVotingStatusUpdatePostResponse> response) {
                int i = votingstatus;
                if (i == 1) {
                    FingerprintCaptureActivity.this.NextScreen("You can vote.", true);
                } else if (i == 2) {
                    FingerprintCaptureActivity.this.NextScreen("You can't vote,Duplicate", false);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserVotingStatusUpdatePostResponse> call, Throwable t) {
                FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                fingerprintCaptureActivity.SetTextOnUIThread("update voting status failure" + t.toString());
            }
        });
    }

    private void updatedevicedetached() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", new UserAuth(getApplicationContext()).getPhone());
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postFpDeviceStatusUpdate(map).enqueue(new Callback<BoothOfficerDeviceStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.18
            @Override // retrofit2.Callback
            public void onResponse(Call<BoothOfficerDeviceStatusUpdatePostResponse> call, Response<BoothOfficerDeviceStatusUpdatePostResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<BoothOfficerDeviceStatusUpdatePostResponse> call, Throwable t) {
                FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                fingerprintCaptureActivity.SetTextOnUIThread("update voting status failure" + t.toString());
            }
        });
    }

    public void NextScreen(String message, Boolean truefalse) {
        SetTextOnUIThread("Next screen1");
        Intent i = new Intent(this, FinalScreenActivity.class);
        i.putExtra("message", message);
        i.putExtra("facefound", this.FaceFound);
        i.putExtra("fingerprintfound", this.FingerprintMatchFound);
        i.putExtra("allowedtovote", truefalse);
        i.putExtra("voterid", this.voterid);
        i.putExtra("fpfoundname", this.nameIfFoundFp);
        i.putExtra("nameIfFoundFace", this.nameIfFoundFace);
        i.putExtra("facematchvoterid", this.nameIfFoundFace);
        i.putExtra("fpmatchvotertid", this.nameIfFoundFp);
        i.putExtra("voted", this.voted);
        startActivity(i);
    }

    public void NextScreenThread(final String str, Boolean truefalse) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.19
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent(FingerprintCaptureActivity.this, FinalScreenActivity.class);
                intent.putExtra("message", str);
                FingerprintCaptureActivity.this.startActivity(intent);
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        deleteFingerprint(this.voterid);
        super.onBackPressed();
    }

    private void translate(String lan) {
        getSupportActionBar().setTitle(this.resources.getString(R.string.fingerprint_authentication_text));
        this.btnSyncCapture.setText(this.resources.getString(R.string.capture_text));
        this.btnUpLoadFp.setText(this.resources.getString(R.string.upload_text));
        this.btnStopCapture.setText(this.resources.getString(R.string.stop_capture_text));
        this.cbFastDetection.setText(this.resources.getString(R.string.fast_fingerprint_detection_text));
        this.btnStopCapture.setText(this.resources.getString(R.string.compare));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFpImages(List<File> files) {
        if (!(files == null || files.isEmpty() || files.size() <= 0)) {
            for (int i = 0; i < files.size(); i++) {
                files.get(i).delete();
            }
        }
    }

    private void updatefingerprintdb(byte[] finger_template) {
        this.db.updateFingerprintTemplate(this.voterid, finger_template);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatefingerprintdbTransTable(byte[] finger_template, long transactionId) {
        this.db.updateFingerprintTemplateTransTable(this.voterid, finger_template, transactionId);
    }

    private void CompareFingerpintServer(String voterid, byte[] fingeprint3) {
    }

    private String CompareFingerprint(String voterid, byte[] fingerprint2) {
        Cursor cursor = this.db.fpcompare(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        do {
            numrows++;
            if (this.mfs100.MatchISO(cursor.getBlob(cursor.getColumnIndex("EnrollTemplate")), fingerprint2) > 100) {
                return cursor.getString(cursor.getColumnIndex("EPIC_NO"));
            }
        } while (cursor.moveToNext());
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void offlineNextScreen(String message, Boolean truefalse, String fpmatchvoterid) {
        Intent i = new Intent(this, FinalScreenActivityOffline.class);
        i.putExtra("message", message);
        i.putExtra("facefound", this.FaceFound);
        i.putExtra("fingerprintfound", this.FingerprintMatchFound);
        i.putExtra("allowedtovote", truefalse);
        i.putExtra("voterid", this.voterid);
        i.putExtra("fpfoundname", this.nameIfFoundFp);
        i.putExtra("nameIfFoundFace", this.nameIfFoundFace);
        i.putExtra("facematchvoterid", this.nameIfFoundFace);
        i.putExtra("fpmatchvotertid", fpmatchvoterid);
        i.putExtra("voted", this.voted);
        startActivity(i);
    }

    public void compareFingerprintServer(String voterid, byte[] fingerprinttemplate) {
        new HashMap();
        MultipartBody.Part part = toMultiPartFile("fpbytearray", fingerprinttemplate);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("voterid", createPartFromString(voterid));
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postFingerprintTemplateCompare(part, map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.20
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                if (response != null && response.isSuccessful()) {
                    response.body();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
            }
        });
    }

    public void compareFingerprintServer2(String voterid, byte[] fingerprinttemplate) {
        PrintStream printStream = System.out;
        printStream.println("fingerprinttemplate=" + fingerprinttemplate.toString());
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postCompareFpServer(voterid, fingerprinttemplate).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.21
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    PrintStream printStream2 = System.out;
                    printStream2.println("fingerprinttemplate2=" + response.body().getMatchedvoterid());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
            }
        });
    }

    public void compareFingerprintServer3(String voterid, byte[] fingerprinttemplate) {
        PrintStream printStream = System.out;
        printStream.println("fingerprinttemplate=" + fingerprinttemplate.toString());
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("voterid", createPartFromString(voterid));
        map.put("fpbytearray", createPartFromByteArray(fingerprinttemplate));
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postCompareFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.22
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    PrintStream printStream2 = System.out;
                    printStream2.println("fingerprinttemplate2=" + response.body().getMatchedvoterid());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
            }
        });
    }

    public static MultipartBody.Part toMultiPartFile(String name, byte[] byteArray) {
        return MultipartBody.Part.createFormData(name, null, RequestBody.create(MediaType.parse("image"), byteArray));
    }

    private void deleteFingerprint(String voterid) {
        this.db.clearFingerprint(voterid);
        this.db.clearFingerprintTransTable(this.userAuth.getTransactionId().longValue());
    }

    @Override // java.util.Observer
    public void update(Observable o, Object arg) {
    }

    private void captureFingerprintTatvik() {
        try {
            this.captRslt1 = this.tmf20lib.captureFingerprint(10000);
            if (this.captRslt1 == null || TMF20ErrorCodes.SUCCESS != this.captRslt1.getStatusCode()) {
                this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.wrong_icon_trp));
            } else {
                this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.right_icon_trp));
            }
        } catch (Exception e) {
            while (true) {
                return;
            }
        }
    }

    private String compareAndLockNetgin(String voterid, byte[] finger_template1) {
        Toast.makeText(getApplicationContext(), "CompareFingerprint", 1).show();
        Cursor cursor = this.db.fpcompare(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        do {
            byte[] fingerprint1 = cursor.getBlob(cursor.getColumnIndex("EnrollTemplate"));
            numrows++;
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "CompareFingerprint3 numrows=" + numrows, 1).show();
            if (this.byTemplate1 != null) {
                NBioBSPJNI nBioBSPJNI = this.bsp;
                Objects.requireNonNull(nBioBSPJNI);
                NBioBSPJNI.FIR_HANDLE hLoadFIR1 = new NBioBSPJNI.FIR_HANDLE();
                this.exportEngine.ImportFIR(finger_template1, finger_template1.length, 3, hLoadFIR1);
                if (this.bsp.IsErrorOccured()) {
                    this.msg = "Template NBioBSP ImportFIR Error: " + this.bsp.GetErrorCode();
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                }
                NBioBSPJNI nBioBSPJNI2 = this.bsp;
                Objects.requireNonNull(nBioBSPJNI2);
                NBioBSPJNI.FIR_HANDLE hLoadFIR2 = new NBioBSPJNI.FIR_HANDLE();
                this.exportEngine.ImportFIR(fingerprint1, fingerprint1.length, 3, hLoadFIR2);
                if (this.bsp.IsErrorOccured()) {
                    hLoadFIR1.dispose();
                    this.msg = "Template NBioBSP ImportFIR Error: " + this.bsp.GetErrorCode();
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
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
                } else if (bResult.booleanValue()) {
                    this.msg = "Template VerifyMatch Successed";
                    String matchvoterid = cursor.getString(cursor.getColumnIndex("EPIC_NO"));
                    Context applicationContext2 = getApplicationContext();
                    Toast.makeText(applicationContext2, "Match voterid" + matchvoterid, 1).show();
                    return matchvoterid;
                } else {
                    this.msg = "Template VerifyMatch Failed";
                    Toast.makeText(getApplicationContext(), this.msg, 1).show();
                }
                hLoadFIR1.dispose();
                hLoadFIR2.dispose();
            } else {
                this.msg = "Can not find captured data";
                Toast.makeText(getApplicationContext(), this.msg, 1).show();
            }
        } while (cursor.moveToNext());
        return "";
    }

    private String CompareFingerprintTatvik(String voterid, byte[] fingerprint2) {
        Cursor cursor = this.db.fpcompare(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        do {
            numrows++;
            if (this.tmf20lib.matchIsoTemplates(cursor.getBlob(cursor.getColumnIndex("EnrollTemplate")), fingerprint2)) {
                return cursor.getString(cursor.getColumnIndex("EPIC_NO"));
            }
        } while (cursor.moveToNext());
        return "";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private String CompareFingerprintTatvikTransTable(String voterid, byte[] fingerprint2) {
        ?? r0 = 1;
        r0 = 1;
        r0 = 1;
        try {
            Cursor cursor = this.db.fpcompareTransTable(voterid);
            int numrows = 0;
            if (cursor.moveToFirst()) {
                do {
                    numrows++;
                    if (this.tmf20lib.matchIsoTemplates(cursor.getBlob(cursor.getColumnIndex("FingerTemplate")), fingerprint2)) {
                        r0 = cursor.getString(cursor.getColumnIndex("EPIC_NO"));
                        return r0;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "CompareFingerprint transtable Exception " + e.getMessage(), r0 == true ? 1 : 0).show();
        }
        Context applicationContext2 = getApplicationContext();
        int i = r0 == true ? 1 : 0;
        int i2 = r0 == true ? 1 : 0;
        int i3 = r0 == true ? 1 : 0;
        Toast.makeText(applicationContext2, "CompareFingerprint transtable after while", i).show();
        return "";
    }

    private void updatefingerprintonlineasstring(String voter_id, final byte[] finger_template, final boolean truefalse, final String matchedvoterid) {
        String fp_string = Base64.encodeToString(finger_template, 0);
        Map<String, String> map = new HashMap<>();
        map.put("voterid", voter_id);
        map.put("fp", fp_string);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintCaptureActivity.23
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                if (!FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                    if (FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                        FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                        fingerprintCaptureActivity.updatefingerprintdbTransTable(fingerprintCaptureActivity.captRslt1.getFmrBytes(), FingerprintCaptureActivity.this.userAuth.getTransactionId().longValue());
                    } else if (FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                        FingerprintCaptureActivity fingerprintCaptureActivity2 = FingerprintCaptureActivity.this;
                        fingerprintCaptureActivity2.updatefingerprintdbTransTable(finger_template, fingerprintCaptureActivity2.userAuth.getTransactionId().longValue());
                    }
                }
                boolean z = truefalse;
                if (z) {
                    FingerprintCaptureActivity.this.offlineNextScreen("You can vote", Boolean.valueOf(z), matchedvoterid);
                } else {
                    FingerprintCaptureActivity.this.offlineNextScreen("You can't vote", false, matchedvoterid);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
                if (!FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                    if (FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                        FingerprintCaptureActivity fingerprintCaptureActivity = FingerprintCaptureActivity.this;
                        fingerprintCaptureActivity.updatefingerprintdbTransTable(fingerprintCaptureActivity.captRslt1.getFmrBytes(), FingerprintCaptureActivity.this.userAuth.getTransactionId().longValue());
                    } else if (FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                        FingerprintCaptureActivity fingerprintCaptureActivity2 = FingerprintCaptureActivity.this;
                        fingerprintCaptureActivity2.updatefingerprintdbTransTable(finger_template, fingerprintCaptureActivity2.userAuth.getTransactionId().longValue());
                    }
                }
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    if (!FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                        if (FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                            FingerprintCaptureActivity fingerprintCaptureActivity3 = FingerprintCaptureActivity.this;
                            fingerprintCaptureActivity3.updatefingerprintdbTransTable(fingerprintCaptureActivity3.captRslt1.getFmrBytes(), FingerprintCaptureActivity.this.userAuth.getTransactionId().longValue());
                        } else if (FingerprintCaptureActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                            FingerprintCaptureActivity fingerprintCaptureActivity4 = FingerprintCaptureActivity.this;
                            fingerprintCaptureActivity4.updatefingerprintdbTransTable(finger_template, fingerprintCaptureActivity4.userAuth.getTransactionId().longValue());
                        }
                    }
                    boolean z = truefalse;
                    if (z) {
                        FingerprintCaptureActivity.this.offlineNextScreen("You ca vote", Boolean.valueOf(z), matchedvoterid);
                    } else {
                        FingerprintCaptureActivity.this.offlineNextScreen("You can't vote", false, matchedvoterid);
                    }
                }
            }
        });
    }
}
