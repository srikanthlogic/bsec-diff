package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.loader.content.CursorLoader;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.ProgressRequestBody;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstanceSurepass;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstanceUrl;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.UploadLinkDataModel;
import com.example.aadhaarfpoffline.tatvik.network.GetUploadLinkResponse;
import com.example.aadhaarfpoffline.tatvik.network.ImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.InitializeResponse;
import com.example.aadhaarfpoffline.tatvik.network.PostUploadResponse;
import com.example.aadhaarfpoffline.tatvik.servece.LocationTrack;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.location.LocationListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mantra.mfs100.MFS100;
import com.surepass.surepassesign.InitSDK;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.cache.DiskLruCache;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class UserIdCaptureActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    private static final String FILE_NAME = "TransactionTable.json";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int PERMISSION_CODE = 1000;
    Button Next;
    Button backToUserList;
    Context context;
    DBHelper db;
    Uri imageUri;
    protected LocationListener locationListener;
    protected LocationManager locationManager;
    private LocationTrack locationTrack;
    Button mCaptureBtn;
    ImageView mImageView;
    Button mUploadBtn;
    ProgressBar progressBar;
    Resources resources;
    private Spinner spinner;
    UserAuth userAuth;
    private TextView voterDistrict;
    private TextView voterId;
    private TextView voterName;
    private static long mLastClkTime = 0;
    private static long Threshold = 1500;
    private String Token = "";
    private int IMAGE_CAPTURE_CODE = 1001;
    String imagePath = "";
    String votername = "";
    String district = "";
    String blockno = "";
    String blockid = "";
    String voterid = "";
    String slnoinward = "";
    String voteridtype = "";
    String voterIdentificationImage = "";
    String voted = "";
    private ArrayList<String> voteridtypes = new ArrayList<>();
    private ArrayList<String> voteridtypesvalues = new ArrayList<>();
    private String GeneratedClientId = "";
    MFS100 mfs100 = null;
    int timeout = 10000;
    int k = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterid_capture);
        String lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        this.imageUri = null;
        this.userAuth = new UserAuth(this);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.mImageView = (ImageView) findViewById(R.id.imageView);
        this.mCaptureBtn = (Button) findViewById(R.id.btnCaptureImage);
        this.mUploadBtn = (Button) findViewById(R.id.btnUploadImage);
        this.backToUserList = (Button) findViewById(R.id.btnbacktouserlis);
        this.Next = (Button) findViewById(R.id.btnNext);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent == null || parent.getChildAt(0) == null)) {
                    ((TextView) parent.getChildAt(0)).setTextColor(-1);
                }
                ((String) UserIdCaptureActivity.this.voteridtypesvalues.get(position)).equalsIgnoreCase("aadhaarcard");
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setvoteridtypes();
        setSpinnerData();
        this.voterName = (TextView) findViewById(R.id.voter_name);
        this.voterId = (TextView) findViewById(R.id.voter_id);
        this.voterDistrict = (TextView) findViewById(R.id.district);
        Intent intent = getIntent();
        this.voterid = intent.getStringExtra("voter_id");
        this.slnoinward = intent.getStringExtra("slnoinward");
        this.votername = intent.getStringExtra("voter_name");
        this.district = intent.getStringExtra("district");
        this.blockno = intent.getStringExtra("blockno");
        this.blockid = intent.getStringExtra("blockid");
        this.voted = intent.getStringExtra("voted");
        this.voterName.setText(this.votername);
        this.voterId.setText(this.voterid);
        this.voterDistrict.setText(this.district);
        this.mCaptureBtn.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (UserIdCaptureActivity.this.spinner.getSelectedItemPosition() <= 0) {
                    Toast.makeText(UserIdCaptureActivity.this.getApplicationContext(), "Please select an ID type", 0).show();
                } else if (Build.VERSION.SDK_INT < 23) {
                } else {
                    if (!UserIdCaptureActivity.this.checkPermission()) {
                        UserIdCaptureActivity.this.requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
                        return;
                    }
                    UserIdCaptureActivity.this.openCamera();
                }
            }
        });
        this.mUploadBtn.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - UserIdCaptureActivity.mLastClkTime >= UserIdCaptureActivity.Threshold) {
                    long unused = UserIdCaptureActivity.mLastClkTime = SystemClock.elapsedRealtime();
                    if (UserIdCaptureActivity.this.imageUri != null) {
                        UserIdCaptureActivity.this.updatevoterDocumentSqlite();
                        UserIdCaptureActivity.this.updatevoterDocumentSqliteTransTable();
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (!UserIdCaptureActivity.this.checkPermission2()) {
                                UserIdCaptureActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1000);
                            } else if (UserIdCaptureActivity.this.imageUri != null) {
                                UserIdCaptureActivity.this.uploadImage();
                            }
                            if (!UserIdCaptureActivity.this.checkLocationPermission()) {
                                UserIdCaptureActivity.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
                                return;
                            }
                            UserIdCaptureActivity.this.getLocation();
                            return;
                        }
                        return;
                    }
                    Toast.makeText(UserIdCaptureActivity.this.getApplicationContext(), "Please select an ID document", 0).show();
                }
            }
        });
        this.backToUserList.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserIdCaptureActivity.this.finish();
            }
        });
        this.Next.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserIdCaptureActivity userIdCaptureActivity = UserIdCaptureActivity.this;
                userIdCaptureActivity.getReport(userIdCaptureActivity.GeneratedClientId);
            }
        });
        this.Next.setVisibility(8);
        translate(lan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCamera() {
        ContentValues values = new ContentValues();
        values.put("title", "New Picture");
        values.put("description", "From the Camera");
        this.imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra("output", this.imageUri);
        startActivityForResult(cameraIntent, this.IMAGE_CAPTURE_CODE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0012, code lost:
        if (r0 == 0) goto L_0x001e;
     */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 99) {
            if (requestCode == 1000) {
                if (grantResults.length > 0) {
                    int i = grantResults[0];
                    getPackageManager();
                }
                Toast.makeText(this, "Permission denied...", 0).show();
            } else {
                return;
            }
        }
        if (grantResults.length > 0 && grantResults[0] == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            getLocation();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            this.mImageView.setImageURI(this.imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPermission2() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImage() {
        BitmapDrawable drawable = (BitmapDrawable) this.mImageView.getDrawable();
        drawable.getBitmap();
        File file = new File(getRealPathFromURI(this.imageUri));
        getImageLocation3(this.imageUri);
        try {
            drawable.getBitmap().compress(Bitmap.CompressFormat.JPEG, 6, new FileOutputStream(file));
        } catch (Throwable t) {
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }
        this.voteridtype = this.voteridtypesvalues.get(this.spinner.getSelectedItemPosition());
        setImagewithLocagtion(file);
        checkExif(file);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("votername", createPartFromString(this.votername));
        map.put("district", createPartFromString(this.userAuth.getDistrictNo()));
        map.put("blockno", createPartFromString(this.blockno));
        map.put("blockid", createPartFromString(this.blockid));
        map.put("voterid", createPartFromString(this.voterid));
        map.put("iddoctype", createPartFromString(this.voteridtype));
        map.put("user_id", createPartFromString(this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward));
        postDataWithImage(map, file);
    }

    private void checkExif(File file) {
    }

    private void setImagewithLocagtion(File file) {
    }

    private synchronized void postDataWithImage(HashMap<String, RequestBody> map, final File file) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVoterIdentification(MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, file.getName(), new ProgressRequestBody(file, "jpg", this)), map).enqueue(new Callback<ImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.6
            @Override // retrofit2.Callback
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                if (response == null || response.body() == null || !response.body().isAdded().booleanValue()) {
                    Context applicationContext = UserIdCaptureActivity.this.getApplicationContext();
                    Toast.makeText(applicationContext, "Not verified.Try again code=" + response.code(), 0).show();
                    UserIdCaptureActivity.this.startnonAadhaarfingerprintactivity();
                    return;
                }
                file.delete();
                UserIdCaptureActivity.this.startofflinefingerprintactivity();
                UserIdCaptureActivity.this.voterIdentificationImage = response.body().getFilename();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                Log.d("taag", t.getMessage());
                Context applicationContext = UserIdCaptureActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "failure post" + t.getMessage(), 1).show();
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    UserIdCaptureActivity.this.startnonAadhaarfingerprintactivity();
                }
            }
        });
    }

    private void startUserimageActivity() {
        if (!this.voteridtype.equalsIgnoreCase("aadhaarcard")) {
            Intent intent = new Intent(getApplicationContext(), FingerprintCaptureActivity.class);
            intent.putExtra("voteridentificationimage", this.voterIdentificationImage);
            intent.putExtra("voter_name", this.votername);
            intent.putExtra("voter_id", this.voterid);
            intent.putExtra("district", this.district);
            intent.putExtra("blockno", this.blockno);
            intent.putExtra("blockid", this.blockid);
            intent.putExtra("iddoctype", this.voteridtype);
            intent.putExtra("nameIfFoundFace", "");
            intent.putExtra("slnoinward", this.slnoinward);
            intent.putExtra("voted", this.voted);
            startActivity(intent);
            return;
        }
        startNewAadharActivity();
    }

    private void startNewAadharActivity() {
        Intent intent = new Intent(getApplicationContext(), AadharActivity.class);
        intent.putExtra("voter_id", this.voterid);
        intent.putExtra("voted", this.voted);
        startActivity(intent);
    }

    private void startFingerprintActivity() {
    }

    private RequestBody createPartFromString(String descriptionString) {
        try {
            return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        } catch (Exception e) {
            startnonAadhaarfingerprintactivity();
            return null;
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = new CursorLoader(this, contentUri, new String[]{"_data"}, null, null, null).loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onProgressUpdate(int percentage) {
        this.progressBar.setProgress(percentage);
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onError() {
        Toast.makeText(getApplicationContext(), "upload image ", 0).show();
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onFinish() {
        this.progressBar.setProgress(100);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
            new AlertDialog.Builder(this).setTitle("Dhansu title").setMessage("Dhansu message").setPositiveButton("Dhansu ok", new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(UserIdCaptureActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
                }
            }).create().show();
            return false;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLocation() {
        LocationTrack locationTrack = new LocationTrack(this);
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            PrintStream printStream = System.out;
            printStream.println("longitudelatitude " + Double.toString(longitude) + " " + Double.toString(latitude));
        }
    }

    private void getImagelocation(String filepath) {
    }

    private void getImageLocation2(String filepath) {
    }

    private void getImageLocation3(Uri selectedImage) {
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    private void setvoteridtypes() {
        this.voteridtypes.add(this.context.getResources().getString(R.string.select_id_type_text));
        this.voteridtypesvalues.add("0");
        this.voteridtypes.add(this.context.getResources().getString(R.string.voter_id_text));
        this.voteridtypesvalues.add("voteridcard");
        this.voteridtypes.add(this.context.getResources().getString(R.string.aadhaar_card_text));
        this.voteridtypesvalues.add("aadhaarcard");
        this.voteridtypes.add(this.context.getResources().getString(R.string.pan_card_text));
        this.voteridtypesvalues.add("pancard");
        this.voteridtypes.add(this.context.getResources().getString(R.string.ration_card_text));
        this.voteridtypesvalues.add("rationcard");
        this.voteridtypes.add(this.context.getResources().getString(R.string.passport_text));
        this.voteridtypesvalues.add("passport");
        this.voteridtypes.add(this.context.getResources().getString(R.string.bank_passbook_text));
        this.voteridtypesvalues.add("bankpassbook");
        this.voteridtypes.add(this.context.getResources().getString(R.string.driving_licence_text));
        this.voteridtypesvalues.add("drivinglicence");
        this.voteridtypes.add(this.context.getResources().getString(R.string.smart_card_text));
        this.voteridtypesvalues.add("smartcard");
        this.voteridtypes.add(this.context.getResources().getString(R.string.mnrega_job_card_text));
        this.voteridtypesvalues.add("mnregacard");
        this.voteridtypes.add(this.context.getResources().getString(R.string.public_govt_company_id_card_text));
        this.voteridtypesvalues.add("publicgovid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.mla_mp_id_card_text));
        this.voteridtypesvalues.add("mlampid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.freedom_fighter_id_card_text));
        this.voteridtypesvalues.add("freedomfighterid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.handicap_certificate_photo_text));
        this.voteridtypesvalues.add("handicapid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.arms_license_card_text));
        this.voteridtypesvalues.add("armslicenseid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.land_document_photo_card_text));
        this.voteridtypesvalues.add("landdocumentid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.student_teachers_id_card_text));
        this.voteridtypesvalues.add("studentteacherid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.npr_rgi_smart_card_text));
        this.voteridtypesvalues.add("nprrgiid");
        this.voteridtypes.add(this.context.getResources().getString(R.string.no_document_text));
        this.voteridtypesvalues.add("nodocument");
    }

    private void setSpinnerData() {
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), 17367048, this.voteridtypes);
        aa.setDropDownViewResource(R.layout.dropdown);
        this.spinner.setAdapter((SpinnerAdapter) aa);
        this.spinner.setSelection(0);
    }

    private void translate(String lan) {
        getSupportActionBar().setTitle(this.resources.getString(R.string.id_document_menu_text));
        this.mUploadBtn.setText(this.resources.getString(R.string.upload_document_text));
        this.mCaptureBtn.setText(this.resources.getString(R.string.capture_document_text));
    }

    private void postUpload(String url, HashMap<String, RequestBody> map) {
        Uri imageUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + R.drawable.faceimage2);
        PrintStream printStream = System.out;
        printStream.println("fileuripath=" + imageUri.getPath());
        String photoAg1 = Uri.parse("android.resource://com.example.aadhaarfpoffline.tatvik/2131230872").getPath();
        PrintStream printStream2 = System.out;
        printStream2.println("fileuripath2=" + photoAg1);
        File file = getFile3();
        PrintStream printStream3 = System.out;
        printStream3.println("filename=" + file.getName() + " filestring=" + file.toString());
        ((GetDataService) RetrofitClientInstanceUrl.getRetrofitInstanceDefaultTokenNewUrl(url).create(GetDataService.class)).postUploadFile(map, MultipartBody.Part.createFormData(UriUtil.LOCAL_FILE_SCHEME, file.getName(), RequestBody.create(MediaType.parse("image/*"), file))).enqueue(new Callback<String>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.8
            @Override // retrofit2.Callback
            public void onResponse(Call<String> call, Response<String> response) {
                PrintStream printStream4 = System.out;
                printStream4.println("after upload success " + response.toString());
                if (response.code() == 204) {
                    UserIdCaptureActivity.this.startAutomaticDirectActivity();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<String> call, Throwable t) {
                PrintStream printStream4 = System.out;
                printStream4.println("after upload failure" + t.getMessage());
                Context applicationContext = UserIdCaptureActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "Upload suppose failure " + t.getMessage(), 1).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUploadLink(String cliendId) {
        ((GetDataService) RetrofitClientInstanceSurepass.getRetrofitInstanceDefaultToken().create(GetDataService.class)).getUploadLink(getUploadLinkData(cliendId).toString()).enqueue(new Callback<GetUploadLinkResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.9
            @Override // retrofit2.Callback
            public void onResponse(Call<GetUploadLinkResponse> call, Response<GetUploadLinkResponse> response) {
                UserIdCaptureActivity.this.printUploadLinkDataModel(response.body().getDataModel());
                UserIdCaptureActivity.this.uploadRequestBody(response.body().getDataModel());
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<GetUploadLinkResponse> call, Throwable t) {
            }
        });
    }

    private void getInitialize(String votername) {
        ((GetDataService) RetrofitClientInstanceSurepass.getRetrofitInstanceDefaultToken().create(GetDataService.class)).getInitialize2(getInitializeData(votername).toString()).enqueue(new Callback<InitializeResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.10
            @Override // retrofit2.Callback
            public void onResponse(Call<InitializeResponse> call, Response<InitializeResponse> response) {
                if (response.body() != null) {
                    UserIdCaptureActivity.this.Token = response.body().getInitializeDataModel().getToken();
                    UserIdCaptureActivity.this.GeneratedClientId = response.body().getInitializeDataModel().getClientId();
                    UserIdCaptureActivity.this.getUploadLink(response.body().getInitializeDataModel().getClientId());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<InitializeResponse> call, Throwable t) {
            }
        });
    }

    private void startAutomaticActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("token", this.Token);
        intent.putExtra("env", "PREPROD");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutomaticDirectActivity() {
        Intent intent = new Intent(this, InitSDK.class);
        intent.putExtra("token", this.Token);
        intent.putExtra("env", "PREPROD");
        startActivityForResult(intent, CameraAccessExceptionCompat.CAMERA_UNAVAILABLE_DO_NOT_DISTURB);
    }

    private JSONObject getReportData() {
        JSONObject jsonObj_outer = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            array.put("name_match");
            String[] strArr = {"name_match"};
            jsonObj_outer.put("categories", array);
        } catch (JSONException e) {
        }
        return jsonObj_outer;
    }

    private JSONObject getInitializeData(String name) {
        JSONObject jsonObj_outer = new JSONObject();
        new JsonObject();
        try {
            jsonObj_outer.put("pdf_pre_uploaded", true);
            JSONObject jsonObj_prefill__options = new JSONObject();
            jsonObj_prefill__options.put("full_name", name);
            jsonObj_prefill__options.put("mobile_number", "8176071570");
            jsonObj_prefill__options.put("user_email", "divyanshusahu08@gmail.com");
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObj_config_data = new JSONObject();
            jsonObj_config_data.put("auth_mode", ExifInterface.GPS_MEASUREMENT_2D);
            jsonObj_config_data.put("reason", "Contract");
            jsonObj_config_data.put("skip_otp", true);
            JSONObject jsonObj_positions1 = new JSONObject();
            jsonObj_positions1.put("x", 10);
            jsonObj_positions1.put("y", 20);
            JSONArray ja = new JSONArray();
            ja.put(jsonObj_positions1);
            JSONObject jsonObj_positions_full = new JSONObject();
            jsonObj_positions_full.put(DiskLruCache.VERSION_1, ja);
            jsonObj_config_data.put("positions", jsonObj_positions_full);
            jsonObj_outer.put("config", jsonObj_config_data);
            jsonObj_outer.put("prefill_options", jsonObj_prefill__options);
            PrintStream printStream = System.out;
            printStream.println("created jsonarray" + jsonObj_outer);
            PrintStream printStream2 = System.out;
            printStream2.println("created jsonarray2" + ((JsonObject) jsonParser.parse(jsonObj_outer.toString())));
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonObj_outer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj_outer;
    }

    private JSONObject getUploadLinkData(String cliendId) {
        JSONObject jsonObj_outer = new JSONObject();
        try {
            jsonObj_outer.put("client_id", cliendId);
            PrintStream printStream = System.out;
            printStream.println("clientidrawjson=" + jsonObj_outer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj_outer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printUploadLinkDataModel(UploadLinkDataModel dataModel) {
        PrintStream printStream = System.out;
        printStream.println("printUploadLinkDataModel=" + dataModel.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadRequestBody(UploadLinkDataModel dataModel) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("x-amz-signature", createPartFromString(dataModel.getFields().getX_amz_signature()));
        map.put("x-amz-date", createPartFromString(dataModel.getFields().getX_amz_date()));
        map.put("policy", createPartFromString(dataModel.getFields().getPolicy()));
        map.put("x-amz-credential", createPartFromString(dataModel.getFields().getX_amz_credential()));
        PrintStream printStream = System.out;
        printStream.println("key=" + dataModel.getFields().getKey());
        map.put("key", createPartFromString(dataModel.getFields().getKey()));
        map.put("x-amz-algorithm", createPartFromString(dataModel.getFields().getX_amz_algorithm()));
        PrintStream printStream2 = System.out;
        printStream2.println("URL=" + dataModel.getUrl());
        postUpload(dataModel.getUrl(), map);
    }

    private File getFile3() {
        InputStream ins = getResources().openRawResource(getResources().getIdentifier("file_name_1", "raw", getPackageName()));
        File file = new File(Environment.getExternalStorageDirectory().toString(), "fileupload.pdf");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(ins, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        }
        return file;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getReport(String clientid) {
        JSONObject jsonObject = getReportData();
        PrintStream printStream = System.out;
        printStream.println("clientid=" + clientid + " jsonobject=" + jsonObject.toString());
        ((GetDataService) RetrofitClientInstanceSurepass.getRetrofitInstanceDefaultToken().create(GetDataService.class)).getReport(clientid, jsonObject.toString()).enqueue(new Callback<PostUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserIdCaptureActivity.11
            @Override // retrofit2.Callback
            public void onResponse(Call<PostUploadResponse> call, Response<PostUploadResponse> response) {
                PrintStream printStream2 = System.out;
                printStream2.println("matchreport success=" + response.body().getReportDataModel().getReports().toString());
                if (response.isSuccessful() && response.body() != null && response.body().getReportDataModel() != null) {
                    UserIdCaptureActivity.this.startFinalActivity(response.body().getReportDataModel().getReports().getName_match().getCertificate_details().getName(), response.body().getReportDataModel().getReports().getName_match().getCertificate_details().getPincode(), response.body().getReportDataModel().getReports().getName_match().getCertificate_details().getState(), response.body().getReportDataModel().getReports().getName_match().getCertificate_details().getAaadhar_last_four_digits());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<PostUploadResponse> call, Throwable t) {
                PrintStream printStream2 = System.out;
                printStream2.println("matchreport error = " + t.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFinalActivity(String name, String pincode, String state, String last4digit) {
        Intent intent = new Intent(this, FinalScreenActivity.class);
        intent.putExtra("aadhaarapi", true);
        if (name == null) {
            intent.putExtra("aadhaarmatch", false);
        } else {
            intent.putExtra("aadhaarmatch", true);
            intent.putExtra("votername", name);
            intent.putExtra("pincode", pincode);
            intent.putExtra("state", state);
            intent.putExtra("last4digit", last4digit);
        }
        intent.putExtra("voterid", this.voterid);
        startActivity(intent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Intent i = new Intent(this, ListUserActivity.class);
        i.setFlags(i.getFlags() | 1073741824);
        startActivity(i);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startnonAadhaarfingerprintactivity() {
        Intent intent = new Intent(getApplicationContext(), FingerprintCaptureActivity.class);
        intent.putExtra("voteridentificationimage", this.voterIdentificationImage);
        intent.putExtra("voter_name", this.votername);
        intent.putExtra("voter_id", this.voterid);
        intent.putExtra("district", this.district);
        intent.putExtra("blockno", this.blockno);
        intent.putExtra("blockid", this.blockid);
        intent.putExtra("iddoctype", this.voteridtype);
        intent.putExtra("nameIfFoundFace", "");
        intent.putExtra("voted", this.voted);
        intent.putExtra("slnoinward", this.slnoinward);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startofflinefingerprintactivity() {
        if (!this.voteridtype.equalsIgnoreCase("aadhaarcard")) {
            Intent intent = new Intent(getApplicationContext(), FingerprintCaptureActivity.class);
            intent.putExtra("voteridentificationimage", this.voterIdentificationImage);
            intent.putExtra("voter_name", this.votername);
            intent.putExtra("voter_id", this.voterid);
            intent.putExtra("district", this.district);
            intent.putExtra("blockno", this.blockno);
            intent.putExtra("blockid", this.blockid);
            intent.putExtra("iddoctype", this.voteridtype);
            intent.putExtra("nameIfFoundFace", "");
            intent.putExtra("voted", this.voted);
            intent.putExtra("slnoinward", this.slnoinward);
            startActivity(intent);
            return;
        }
        startNewAadharActivity();
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        if (!new File(Environment.getExternalStorageDirectory() + "/Images").exists()) {
            new File("/sdcard/Images/").mkdirs();
        }
        File file = new File("/sdcard/Images/", fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatevoterDocumentSqlite() {
        Bitmap bitmap = ((BitmapDrawable) this.mImageView.getDrawable()).getBitmap();
        String filename = this.voterid + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        createDirectoryAndSaveFile(bitmap, filename);
        this.db.updateVoterIdImage(this.voterid, filename);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatevoterDocumentSqliteTransTable() {
        Bitmap bitmap = ((BitmapDrawable) this.mImageView.getDrawable()).getBitmap();
        String filename = this.voterid + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        createDirectoryAndSaveFile(bitmap, filename);
        ContentValues cols = new ContentValues();
        cols.put("ID_DOCUMENT_IMAGE", filename);
        cols.put("EPIC_NO", this.voterid);
        DBHelper dBHelper = this.db;
        this.userAuth.setTransactionId(Long.valueOf(dBHelper.insertData(dBHelper.tbl_transaction, cols)));
    }
}
