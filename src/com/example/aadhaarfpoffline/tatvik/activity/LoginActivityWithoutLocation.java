package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aadhaarfpoffline.tatvik.BuildConfig;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.network.LoginForUrlResponse;
import com.example.aadhaarfpoffline.tatvik.network.LoginTimeUpdateGetResponse;
import com.example.aadhaarfpoffline.tatvik.services.LocationTrack;
import com.scwang.wave.MultiWaveHeader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class LoginActivityWithoutLocation extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private TextView appVersion;
    private EditText booth;
    private Button button;
    Context context;
    DBHelper db;
    private EditText email;
    Handler handler;
    Double latitude;
    LocationTrack locationTrack;
    private TextView loginMessage;
    Double longitude;
    private EditText password;
    private ProgressBar progressBar;
    Runnable r;
    Resources resources;
    private static long mLastClkTime = 0;
    private static long Threshold = 90000;
    private int BOOTH_RADIUS = 2;
    private String device = "";
    private String responseString = "";
    String androidId = "";
    String UDevId = "";
    private String PHASE = "7";

    public LoginActivityWithoutLocation() {
        Double valueOf = Double.valueOf(0.0d);
        this.longitude = valueOf;
        this.latitude = valueOf;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        this.db = new DBHelper(this);
        this.handler = new Handler();
        this.r = new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityWithoutLocation.1
            @Override // java.lang.Runnable
            public void run() {
            }
        };
        startHandler();
        this.context = LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        this.resources = this.context.getResources();
        this.androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        this.UDevId = this.androidId;
        if (((TelephonyManager) Objects.requireNonNull((TelephonyManager) getApplicationContext().getSystemService("phone"))).getPhoneType() == 0) {
            this.device = "tablet";
        } else {
            this.device = "mobile";
        }
        this.email = (EditText) findViewById(R.id.id_phone1);
        this.booth = (EditText) findViewById(R.id.id_Booth);
        this.password = (EditText) findViewById(R.id.id_password);
        this.email.setHintTextColor(Color.parseColor("#ffffff"));
        this.booth.setHintTextColor(Color.parseColor("#ffffff"));
        this.password.setHintTextColor(Color.parseColor("#ffffff"));
        this.loginMessage = (TextView) findViewById(R.id.loginmessage);
        this.appVersion = (TextView) findViewById(R.id.versioncode);
        this.progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        TextView textView = this.appVersion;
        textView.setText("App version:34/" + BuildConfig.VERSION_NAME);
        MultiWaveHeader waveHeader = (MultiWaveHeader) findViewById(R.id.wavebottom);
        waveHeader.setColorAlpha(0.5f);
        waveHeader.start();
        this.button = (Button) findViewById(R.id.ok_button);
        this.button.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityWithoutLocation.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - LoginActivityWithoutLocation.mLastClkTime < LoginActivityWithoutLocation.Threshold) {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "Waiting for response from Server.Please wait for some time", 1).show();
                    return;
                }
                long unused = LoginActivityWithoutLocation.mLastClkTime = SystemClock.elapsedRealtime();
                LoginActivityWithoutLocation.this.initialLogin();
            }
        });
    }

    public void initialLogin() {
        String email_text = this.email.getText().toString();
        String booth_text = this.booth.getText().toString();
        String password_text = this.password.getText().toString();
        PrintStream printStream = System.out;
        printStream.println("emailra+" + email_text);
        if (email_text == null || email_text.isEmpty() || email_text.length() == 0 || booth_text == null || booth_text.isEmpty() || booth_text.length() == 0 || password_text == null || password_text.isEmpty() || password_text.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter all fields", 1).show();
            return;
        }
        loginMethodWithDevice(email_text, booth_text, password_text, this.device, this.longitude + ":" + this.latitude);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void startMainActivity(String boothid) {
        Intent intent = new Intent(this, FingerprintDeviceSelectionActivity.class);
        intent.putExtra("boothid", boothid);
        startActivity(intent);
    }

    private void loginMethodWithDevice(final String panchayat, final String boothid, final String password, final String device, final String loc) {
        Map<String, String> map = new HashMap<>();
        map.put("PanchayatID", panchayat);
        map.put("BoothNo", boothid);
        map.put("password", password);
        map.put("device", device);
        map.put("udevid", this.UDevId);
        map.put("phaseno", this.PHASE);
        Call<LoginForUrlResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceLoginOnly().create(GetDataService.class)).getLoginWithUrl(map);
        this.progressBar.setVisibility(0);
        call.enqueue(new Callback<LoginForUrlResponse>("9971791175") { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityWithoutLocation.3
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginForUrlResponse> call2, Response<LoginForUrlResponse> response) {
                LoginActivityWithoutLocation.this.progressBar.setVisibility(8);
                Log.d("getloginresposne", response.raw().toString());
                if (response == null || response.body() == null) {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "Resposne not in format.Trying alternate url to login" + response.toString(), 1).show();
                    LoginActivityWithoutLocation.this.loginMethodWithDeviceUrlfailCheck(panchayat, boothid, password, device, loc);
                    return;
                }
                LoginActivityWithoutLocation.this.responseString = response.body().toString();
                if (response.body().isLoginAllowed() == null) {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "loginallowed field set to null", 1).show();
                    LoginActivityWithoutLocation.this.loginMethodWithDeviceUrlfailCheck(panchayat, boothid, password, device, loc);
                } else if (response.body().isLoginAllowed().booleanValue()) {
                    UserAuth userAuth = new UserAuth(LoginActivityWithoutLocation.this.getApplicationContext());
                    String BoothIdOld = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                    if (BoothIdOld.equalsIgnoreCase(response.body().getPanchayatid() + "_" + response.body().getWardno() + "_" + response.body().getBoothNo()) || BoothIdOld.equalsIgnoreCase("__")) {
                        userAuth.setBoothId(response.body().getBoothid());
                        userAuth.setPhone("9971791175");
                        userAuth.setLogin(true);
                        userAuth.setPanchayatId(response.body().getPanchayatid());
                        userAuth.setDistrictNo(response.body().getDistNo());
                        userAuth.setBlockID(response.body().getBlockId());
                        userAuth.setBoothNo(response.body().getBoothNo());
                        userAuth.setWardNo(response.body().getWardno());
                        userAuth.setBaseUrl(response.body().getUrl());
                        userAuth.setPanchayat_NAME_EN(response.body().getPanchayat_NAME_EN());
                        userAuth.setPanchayat_NAME_HN(response.body().getPanchayat_NAME_HN());
                        userAuth.setDIST_NAME_EN(response.body().getDIST_NAME_EN());
                        userAuth.setDIST_NAME_HN(response.body().getDIST_NAME_HN());
                        userAuth.setBlock_NAME_EN(response.body().getBlock_NAME_EN());
                        userAuth.setBlock_NAME_HN(response.body().getBlock_NAME_HN());
                        LoginActivityWithoutLocation.this.startMainActivity(response.body().getBoothid());
                        return;
                    }
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "Device is mapped with " + userAuth.getBoothNo() + ", You cannot login for another booth", 1).show();
                    LoginActivityWithoutLocation.this.loginMessage.setText(LoginActivityWithoutLocation.this.resources.getString(R.string.login_message_old_data));
                } else {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "Login Failed : " + response.body().getMessage(), 1).show();
                    LoginActivityWithoutLocation.this.loginMethodWithDeviceUrlfailCheck(panchayat, boothid, password, device, loc);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginForUrlResponse> call2, Throwable t) {
                LoginActivityWithoutLocation.this.progressBar.setVisibility(8);
                Context applicationContext = LoginActivityWithoutLocation.this.getApplicationContext();
                Toast.makeText(applicationContext, "Login Failure .Trying alternate url" + t.getMessage(), 1).show();
                LoginActivityWithoutLocation.this.loginMethodWithDeviceUrlfailCheck(panchayat, boothid, password, device, loc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginMethodWithDeviceUrlfailCheck(String panchayat, String boothid, String password, String device, String loc) {
        Map<String, String> map = new HashMap<>();
        map.put("PanchayatID", panchayat);
        map.put("BoothNo", boothid);
        map.put("password", password);
        map.put("device", device);
        map.put("udevid", this.UDevId);
        map.put("phaseno", this.PHASE);
        Call<LoginForUrlResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstanceLoginFailCheck().create(GetDataService.class)).getLoginWithUrl(map);
        this.progressBar.setVisibility(0);
        call.enqueue(new Callback<LoginForUrlResponse>("9971791175") { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityWithoutLocation.4
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginForUrlResponse> call2, Response<LoginForUrlResponse> response) {
                LoginActivityWithoutLocation.this.progressBar.setVisibility(8);
                Log.d("oldcimresponse", response.raw().toString());
                if (response == null || response.body() == null) {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "2Resposne not in format", 1).show();
                    return;
                }
                LoginActivityWithoutLocation.this.responseString = response.body().toString();
                if (response.body().isLoginAllowed() == null) {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "2loginallowed field set to null", 1).show();
                } else if (response.body().isLoginAllowed().booleanValue()) {
                    UserAuth userAuth = new UserAuth(LoginActivityWithoutLocation.this.getApplicationContext());
                    String BoothIdOld = userAuth.getPanchayatId() + "_" + userAuth.getWardNo() + "_" + userAuth.getBoothNo();
                    String BoothIdNew = response.body().getPanchayatid() + "_" + response.body().getWardno() + "_" + response.body().getBoothNo();
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "BoothIdold=" + BoothIdOld + " boothnew=" + BoothIdNew, 1).show();
                    if (BoothIdOld.equalsIgnoreCase(BoothIdNew) || BoothIdOld.equalsIgnoreCase("__")) {
                        userAuth.setBoothId(response.body().getBoothid());
                        userAuth.setPhone("9971791175");
                        userAuth.setLogin(true);
                        userAuth.setPanchayatId(response.body().getPanchayatid());
                        userAuth.setDistrictNo(response.body().getDistNo());
                        userAuth.setBlockID(response.body().getBlockId());
                        userAuth.setBoothNo(response.body().getBoothNo());
                        userAuth.setWardNo(response.body().getWardno());
                        userAuth.setBaseUrl(response.body().getUrl());
                        userAuth.setPanchayat_NAME_EN(response.body().getPanchayat_NAME_EN());
                        userAuth.setPanchayat_NAME_HN(response.body().getPanchayat_NAME_HN());
                        userAuth.setDIST_NAME_EN(response.body().getDIST_NAME_EN());
                        userAuth.setDIST_NAME_HN(response.body().getDIST_NAME_HN());
                        userAuth.setBlock_NAME_EN(response.body().getBlock_NAME_EN());
                        userAuth.setBlock_NAME_HN(response.body().getBlock_NAME_HN());
                        LoginActivityWithoutLocation.this.startMainActivity(response.body().getBoothid());
                        return;
                    }
                    LoginActivityWithoutLocation.this.loginMessage.setText(LoginActivityWithoutLocation.this.resources.getString(R.string.login_message_old_data));
                } else {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "2Login Failed : " + response.body().getMessage(), 1).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginForUrlResponse> call2, Throwable t) {
                LoginActivityWithoutLocation.this.progressBar.setVisibility(8);
                Context applicationContext = LoginActivityWithoutLocation.this.getApplicationContext();
                Toast.makeText(applicationContext, "Login Failure " + t.getMessage(), 1).show();
            }
        });
    }

    private void loginMethodWithDeviceUrlfailCheck_old(String panchayat, String boothid, String password, String device, final String loc) {
        Toast.makeText(getApplicationContext(), "Login Failure Hitting other url to login", 1).show();
        Map<String, String> map = new HashMap<>();
        map.put("PanchayatID", panchayat);
        map.put("BoothNo", boothid);
        map.put("password", password);
        map.put("device", device);
        map.put("udevid", this.UDevId);
        map.put("phaseno", this.PHASE);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstanceLoginFailCheck().create(GetDataService.class)).getLoginWithUrl(map).enqueue(new Callback<LoginForUrlResponse>("9971791175") { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityWithoutLocation.5
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginForUrlResponse> call, Response<LoginForUrlResponse> response) {
                if (response != null && response.body() != null) {
                    LoginActivityWithoutLocation.this.responseString = response.body().toString();
                    if (response.body().isLoginAllowed() == null) {
                        Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "loginallowed field set to null", 1).show();
                    } else if (!response.body().isLoginAllowed().booleanValue()) {
                        Context applicationContext = LoginActivityWithoutLocation.this.getApplicationContext();
                        Toast.makeText(applicationContext, "Login Failed : " + response.body().getMessage(), 1).show();
                    } else if (response.body().getDblocation().equalsIgnoreCase("0.0:0.0")) {
                        UserAuth userAuth = new UserAuth(LoginActivityWithoutLocation.this.getApplicationContext());
                        userAuth.setBoothLocation(loc);
                        LoginActivityWithoutLocation.this.locationTrack.setBoothLocation(LoginActivityWithoutLocation.this.getLocationFromString(loc));
                        LoginActivityWithoutLocation.this.locationTrack.setPhone("9971791175");
                        userAuth.setBoothId(response.body().getBoothid());
                        userAuth.setPhone("9971791175");
                        userAuth.setLogin(true);
                        userAuth.setPanchayatId(response.body().getPanchayatid());
                        userAuth.setDistrictNo(response.body().getDistNo());
                        userAuth.setBlockID(response.body().getBlockId());
                        userAuth.setBoothNo(response.body().getBoothNo());
                        userAuth.setWardNo(response.body().getWardno());
                        userAuth.setBaseUrl(response.body().getUrl());
                        LoginActivityWithoutLocation.this.startMainActivity(response.body().getBoothid());
                    } else {
                        UserAuth userAuth2 = new UserAuth(LoginActivityWithoutLocation.this.getApplicationContext());
                        userAuth2.setBoothLocation(response.body().getDblocation());
                        LoginActivityWithoutLocation.this.locationTrack.setBoothLocation(LoginActivityWithoutLocation.this.getLocationFromString(response.body().getDblocation()));
                        LoginActivityWithoutLocation.this.locationTrack.setPhone("9971791175");
                        LoginActivityWithoutLocation.this.locationTrack.getBoothDistance();
                        userAuth2.setBoothId(response.body().getBoothid());
                        userAuth2.setPhone("9971791175");
                        userAuth2.setLogin(true);
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setWardNo(response.body().getWardno());
                        LoginActivityWithoutLocation.this.postLoginTimeUdate("9971791175", response.body().getBoothid());
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setDistrictNo(response.body().getDistNo());
                        userAuth2.setBlockID(response.body().getBlockId());
                        userAuth2.setBoothNo(response.body().getBoothNo());
                        userAuth2.setWardNo(response.body().getWardno());
                        userAuth2.setBaseUrl(response.body().getUrl());
                    }
                } else if (LoginActivityWithoutLocation.this.db.getAllElements() == null || LoginActivityWithoutLocation.this.db.getAllElements().size() <= 0) {
                    Toast.makeText(LoginActivityWithoutLocation.this.getApplicationContext(), "Resposne not in format and No data in database", 1).show();
                } else {
                    Context applicationContext2 = LoginActivityWithoutLocation.this.getApplicationContext();
                    Toast.makeText(applicationContext2, "Resposne not in format but data exists in database" + response.toString(), 1).show();
                    LoginActivityWithoutLocation.this.startMainActivity("12");
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginForUrlResponse> call, Throwable t) {
                Context applicationContext = LoginActivityWithoutLocation.this.getApplicationContext();
                Toast.makeText(applicationContext, "Login Failure " + t.getMessage(), 1).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postLoginTimeUdate(String phone, final String boothid) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getLoginTimeUpdateMethod(phone).enqueue(new Callback<LoginTimeUpdateGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityWithoutLocation.6
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginTimeUpdateGetResponse> call, Response<LoginTimeUpdateGetResponse> response) {
                LoginActivityWithoutLocation.this.startMainActivity(boothid);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginTimeUpdateGetResponse> call, Throwable t) {
            }
        });
    }

    private void setDatainSharedPreferences(String token) {
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Location getLocationFromString(String location) {
        String[] longilati = location.split(":", 2);
        Double boothLong = Double.valueOf(Double.parseDouble(longilati[0]));
        Double boothLat = Double.valueOf(Double.parseDouble(longilati[1]));
        Location boothLocation = new Location("BoothLocation");
        boothLocation.setLatitude(boothLat.doubleValue());
        boothLocation.setLongitude(boothLong.doubleValue());
        return boothLocation;
    }
}
