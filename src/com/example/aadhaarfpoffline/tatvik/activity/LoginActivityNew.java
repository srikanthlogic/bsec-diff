package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.network.ElectionBoothLoginGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.LoginForUrlResponse;
import com.example.aadhaarfpoffline.tatvik.network.LoginTimeUpdateGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.OfficialDataGetResponse;
import com.example.aadhaarfpoffline.tatvik.servece.LocationTrack;
import com.scwang.wave.MultiWaveHeader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class LoginActivityNew extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private EditText booth;
    private Button button;
    DBHelper db;
    private EditText email;
    Handler handler;
    Double latitude;
    LocationTrack locationTrack;
    Double longitude;
    private EditText password;
    Runnable r;
    private int BOOTH_RADIUS = 2;
    private String device = "";
    private String responseString = "";

    public LoginActivityNew() {
        Double valueOf = Double.valueOf(0.0d);
        this.longitude = valueOf;
        this.latitude = valueOf;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        this.db = new DBHelper(this);
        this.handler = new Handler();
        this.r = new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.1
            @Override // java.lang.Runnable
            public void run() {
            }
        };
        startHandler();
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
        MultiWaveHeader waveHeader = (MultiWaveHeader) findViewById(R.id.wavebottom);
        waveHeader.setColorAlpha(0.5f);
        waveHeader.start();
        this.button = (Button) findViewById(R.id.ok_button);
        this.button.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!LoginActivityNew.this.checkLocationPermission()) {
                    LoginActivityNew.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
                    return;
                }
                LoginActivityNew.this.initialLogin();
            }
        });
    }

    public void initialLogin() {
        String email_text = this.email.getText().toString();
        String booth_text = this.booth.getText().toString();
        String password_text = this.password.getText().toString();
        PrintStream printStream = System.out;
        printStream.println("emailra+" + email_text);
        this.locationTrack = LocationTrack.getInstance(this);
        if (this.locationTrack.canGetLocation()) {
            this.longitude = Double.valueOf(this.locationTrack.getLongitude());
            this.latitude = Double.valueOf(this.locationTrack.getLatitude());
        }
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

    private void getBoothId(final String phone) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getBoothIdBasedOnPhone(phone).enqueue(new Callback<OfficialDataGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.3
            @Override // retrofit2.Callback
            public void onResponse(Call<OfficialDataGetResponse> call, Response<OfficialDataGetResponse> response) {
                if (response == null || response.body() == null || response.body().getOfficialDataModel() == null || response.body().getOfficialDataModel().getBoothId() == null || response.body().getOfficialDataModel().getBoothId().isEmpty() || response.body().getOfficialDataModel().getBoothId().length() <= 0) {
                    Toast.makeText(LoginActivityNew.this.getApplicationContext(), "This phone number doesn't exist in database", 1).show();
                    return;
                }
                String boothid = response.body().getOfficialDataModel().getBoothId();
                UserAuth userAuth = new UserAuth(LoginActivityNew.this.getApplicationContext());
                userAuth.setBoothLocation(response.body().getOfficialDataModel().getLocation());
                LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(response.body().getOfficialDataModel().getLocation()));
                LoginActivityNew.this.locationTrack.setPhone(phone);
                float distance = LoginActivityNew.this.locationTrack.getBoothDistance();
                if (distance < ((float) LoginActivityNew.this.locationTrack.distanceAllowed())) {
                    Context applicationContext = LoginActivityNew.this.getApplicationContext();
                    Toast.makeText(applicationContext, "Inside booth " + distance, 1).show();
                    userAuth.setBoothId(boothid);
                    userAuth.setPhone(phone);
                    userAuth.setLogin(true);
                    LoginActivityNew.this.startMainActivity(boothid);
                    return;
                }
                LoginActivityNew.this.locationTrack.locationUpdateWithAction(phone, "outside");
                Context applicationContext2 = LoginActivityNew.this.getApplicationContext();
                Toast.makeText(applicationContext2, "Please try to login when you are inside booth " + distance, 1).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<OfficialDataGetResponse> call, Throwable t) {
            }
        });
    }

    private void loginMethodWithDevice(final String panchayat, final String boothid, final String password, final String device, final String loc) {
        Map<String, String> map = new HashMap<>();
        map.put("PanchayatID", panchayat);
        map.put("BoothNo", boothid);
        map.put("password", password);
        map.put("device", device);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstanceLoginOnly().create(GetDataService.class)).getLoginWithUrl(map).enqueue(new Callback<LoginForUrlResponse>("9971791175") { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.4
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginForUrlResponse> call, Response<LoginForUrlResponse> response) {
                if (response != null && response.body() != null) {
                    LoginActivityNew.this.responseString = response.body().toString();
                    if (response.body().isLoginAllowed() == null) {
                        Toast.makeText(LoginActivityNew.this.getApplicationContext(), "loginallowed field set to null", 1).show();
                    } else if (!response.body().isLoginAllowed().booleanValue()) {
                        Context applicationContext = LoginActivityNew.this.getApplicationContext();
                        Toast.makeText(applicationContext, "Login Failed : " + response.body().getMessage(), 1).show();
                    } else if (response.body().getDblocation().equalsIgnoreCase("0.0:0.0")) {
                        UserAuth userAuth = new UserAuth(LoginActivityNew.this.getApplicationContext());
                        userAuth.setBoothLocation(loc);
                        LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(loc));
                        LoginActivityNew.this.locationTrack.setPhone("9971791175");
                        userAuth.setBoothId(response.body().getBoothid());
                        userAuth.setPhone("9971791175");
                        userAuth.setLogin(true);
                        userAuth.setPanchayatId(response.body().getPanchayatid());
                        userAuth.setDistrictNo(response.body().getDistNo());
                        userAuth.setBlockID(response.body().getBlockId());
                        userAuth.setBoothNo(LoginActivityNew.this.boothNoTextFormat(response.body().getBoothNo()));
                        userAuth.setWardNo(response.body().getWardno());
                        userAuth.setBaseUrl(response.body().getUrl());
                        LoginActivityNew.this.startMainActivity(response.body().getBoothid());
                    } else {
                        UserAuth userAuth2 = new UserAuth(LoginActivityNew.this.getApplicationContext());
                        userAuth2.setBoothLocation(response.body().getDblocation());
                        LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(response.body().getDblocation()));
                        LoginActivityNew.this.locationTrack.setPhone("9971791175");
                        LoginActivityNew.this.locationTrack.getBoothDistance();
                        userAuth2.setBoothId(response.body().getBoothid());
                        userAuth2.setPhone("9971791175");
                        userAuth2.setLogin(true);
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setWardNo(response.body().getWardno());
                        LoginActivityNew.this.postLoginTimeUdate("9971791175", response.body().getBoothid());
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setDistrictNo(response.body().getDistNo());
                        userAuth2.setBlockID(response.body().getBlockId());
                        userAuth2.setBoothNo(LoginActivityNew.this.boothNoTextFormat(response.body().getBoothNo()));
                        userAuth2.setWardNo(response.body().getWardno());
                        userAuth2.setBaseUrl(response.body().getUrl());
                    }
                } else if (LoginActivityNew.this.db.getAllElements() == null || LoginActivityNew.this.db.getAllElements().size() <= 0) {
                    Toast.makeText(LoginActivityNew.this.getApplicationContext(), "Resposne not in format and No data in database", 1).show();
                    LoginActivityNew.this.loginMethodWithDeviceUrlfailCheck(panchayat, boothid, password, device, loc);
                } else {
                    Context applicationContext2 = LoginActivityNew.this.getApplicationContext();
                    Toast.makeText(applicationContext2, "Resposne not in format but data exists in database" + response.toString(), 1).show();
                    LoginActivityNew.this.startMainActivity("12");
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginForUrlResponse> call, Throwable t) {
                Context applicationContext = LoginActivityNew.this.getApplicationContext();
                Toast.makeText(applicationContext, "Login Failure " + t.getMessage(), 1).show();
                LoginActivityNew.this.loginMethodWithDeviceUrlfailCheck(panchayat, boothid, password, device, loc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginMethodWithDeviceUrlfailCheck(String panchayat, String boothid, String password, String device, final String loc) {
        Toast.makeText(getApplicationContext(), "Login Failure Hitting other url to login", 1).show();
        Map<String, String> map = new HashMap<>();
        map.put("PanchayatID", panchayat);
        map.put("BoothNo", boothid);
        map.put("password", password);
        map.put("device", device);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstanceLoginFailCheck().create(GetDataService.class)).getLoginWithUrl(map).enqueue(new Callback<LoginForUrlResponse>("9971791175") { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.5
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginForUrlResponse> call, Response<LoginForUrlResponse> response) {
                if (response != null && response.body() != null) {
                    LoginActivityNew.this.responseString = response.body().toString();
                    if (response.body().isLoginAllowed() == null) {
                        Toast.makeText(LoginActivityNew.this.getApplicationContext(), "loginallowed field set to null", 1).show();
                    } else if (!response.body().isLoginAllowed().booleanValue()) {
                        Context applicationContext = LoginActivityNew.this.getApplicationContext();
                        Toast.makeText(applicationContext, "Login Failed : " + response.body().getMessage(), 1).show();
                    } else if (response.body().getDblocation().equalsIgnoreCase("0.0:0.0")) {
                        UserAuth userAuth = new UserAuth(LoginActivityNew.this.getApplicationContext());
                        userAuth.setBoothLocation(loc);
                        LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(loc));
                        LoginActivityNew.this.locationTrack.setPhone("9971791175");
                        userAuth.setBoothId(response.body().getBoothid());
                        userAuth.setPhone("9971791175");
                        userAuth.setLogin(true);
                        userAuth.setPanchayatId(response.body().getPanchayatid());
                        userAuth.setDistrictNo(response.body().getDistNo());
                        userAuth.setBlockID(response.body().getBlockId());
                        userAuth.setBoothNo(LoginActivityNew.this.boothNoTextFormat(response.body().getBoothNo()));
                        userAuth.setWardNo(response.body().getWardno());
                        userAuth.setBaseUrl(response.body().getUrl());
                        LoginActivityNew.this.startMainActivity(response.body().getBoothid());
                    } else {
                        UserAuth userAuth2 = new UserAuth(LoginActivityNew.this.getApplicationContext());
                        userAuth2.setBoothLocation(response.body().getDblocation());
                        LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(response.body().getDblocation()));
                        LoginActivityNew.this.locationTrack.setPhone("9971791175");
                        LoginActivityNew.this.locationTrack.getBoothDistance();
                        userAuth2.setBoothId(response.body().getBoothid());
                        userAuth2.setPhone("9971791175");
                        userAuth2.setLogin(true);
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setWardNo(response.body().getWardno());
                        LoginActivityNew.this.postLoginTimeUdate("9971791175", response.body().getBoothid());
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setDistrictNo(response.body().getDistNo());
                        userAuth2.setBlockID(response.body().getBlockId());
                        userAuth2.setBoothNo(LoginActivityNew.this.boothNoTextFormat(response.body().getBoothNo()));
                        userAuth2.setWardNo(response.body().getWardno());
                        userAuth2.setBaseUrl(response.body().getUrl());
                    }
                } else if (LoginActivityNew.this.db.getAllElements() == null || LoginActivityNew.this.db.getAllElements().size() <= 0) {
                    Toast.makeText(LoginActivityNew.this.getApplicationContext(), "Resposne not in format and No data in database", 1).show();
                } else {
                    Context applicationContext2 = LoginActivityNew.this.getApplicationContext();
                    Toast.makeText(applicationContext2, "Resposne not in format but data exists in database" + response.toString(), 1).show();
                    LoginActivityNew.this.startMainActivity("12");
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginForUrlResponse> call, Throwable t) {
                Context applicationContext = LoginActivityNew.this.getApplicationContext();
                Toast.makeText(applicationContext, "Login Failure " + t.getMessage(), 1).show();
            }
        });
    }

    private void loginGetMethod(final String phone, String otp, final String loc, String device) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getLoginNewMethod(phone, otp, loc).enqueue(new Callback<ElectionBoothLoginGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.6
            @Override // retrofit2.Callback
            public void onResponse(Call<ElectionBoothLoginGetResponse> call, Response<ElectionBoothLoginGetResponse> response) {
                if (response != null && response.body() != null) {
                    if (!response.body().isLoginAllowed().booleanValue()) {
                        Toast.makeText(LoginActivityNew.this.getApplicationContext(), response.body().getMessage(), 1).show();
                    } else if (response.body().getDblocation().equalsIgnoreCase("0.0:0.0")) {
                        UserAuth userAuth = new UserAuth(LoginActivityNew.this.getApplicationContext());
                        userAuth.setBoothLocation(loc);
                        LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(loc));
                        LoginActivityNew.this.locationTrack.setPhone(phone);
                        userAuth.setBoothId(response.body().getBoothid());
                        userAuth.setPhone(phone);
                        userAuth.setLogin(true);
                        LoginActivityNew.this.startMainActivity(response.body().getBoothid());
                    } else {
                        UserAuth userAuth2 = new UserAuth(LoginActivityNew.this.getApplicationContext());
                        userAuth2.setBoothLocation(response.body().getDblocation());
                        LoginActivityNew.this.locationTrack.setBoothLocation(LoginActivityNew.this.getLocationFromString(response.body().getDblocation()));
                        LoginActivityNew.this.locationTrack.setPhone(phone);
                        LoginActivityNew.this.locationTrack.getBoothDistance();
                        userAuth2.setBoothId(response.body().getBoothid());
                        userAuth2.setPhone(phone);
                        userAuth2.setLogin(true);
                        userAuth2.setPanchayatId(response.body().getPanchayatid());
                        userAuth2.setWardNo(response.body().getWardno());
                        LoginActivityNew.this.postLoginTimeUdate(phone, response.body().getBoothid());
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ElectionBoothLoginGetResponse> call, Throwable t) {
                Context applicationContext = LoginActivityNew.this.getApplicationContext();
                Toast.makeText(applicationContext, "Inside booth " + t.toString(), 1).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postLoginTimeUdate(String phone, final String boothid) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getLoginTimeUpdateMethod(phone).enqueue(new Callback<LoginTimeUpdateGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.7
            @Override // retrofit2.Callback
            public void onResponse(Call<LoginTimeUpdateGetResponse> call, Response<LoginTimeUpdateGetResponse> response) {
                LoginActivityNew.this.startMainActivity(boothid);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<LoginTimeUpdateGetResponse> call, Throwable t) {
            }
        });
    }

    private void postLoginData(String email, String password) {
        GetDataService getDataService = (GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("pwd", password);
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

    private float getBoothLocation(String location) {
        String[] longilati = location.split(":", 2);
        Double boothLong = Double.valueOf(Double.parseDouble(longilati[0]));
        Double boothLat = Double.valueOf(Double.parseDouble(longilati[1]));
        Location currentLocation = new Location("locationA");
        currentLocation.setLatitude(this.latitude.doubleValue());
        currentLocation.setLongitude(this.longitude.doubleValue());
        Location boothLocation = new Location("BoothLocation");
        boothLocation.setLatitude(boothLat.doubleValue());
        boothLocation.setLongitude(boothLong.doubleValue());
        return currentLocation.distanceTo(boothLocation);
    }

    private void getLocation() {
        LocationTrack locationTrack = new LocationTrack(this);
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            PrintStream printStream = System.out;
            printStream.println("longitudelatitude " + Double.toString(longitude) + " " + Double.toString(latitude));
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
            new AlertDialog.Builder(this).setTitle("Permission Denied").setMessage("Please approve permission again.").setPositiveButton("Okay", new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.LoginActivityNew.8
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(LoginActivityNew.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
                }
            }).create().show();
            return false;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 99 && grantResults.length > 0 && grantResults[0] == 0) {
            initialLogin();
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

    public String boothNoTextFormat(String boothNoString) {
        int boothNo = Integer.parseInt(boothNoString);
        if (boothNo < 501) {
            return String.valueOf(boothNo);
        }
        if (boothNo > 1000 && boothNo < 2000) {
            int actualboothNO = boothNo + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
            return actualboothNO + "क";
        } else if (boothNo > 2000 && boothNo < 3000) {
            StringBuilder sb = new StringBuilder();
            sb.append(boothNo - 2000);
            sb.append("ख");
            return sb.toString();
        } else if (boothNo > 3000 && boothNo < 4000) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(boothNo - 3000);
            sb2.append("ग");
            return sb2.toString();
        } else if (boothNo <= 4000 || boothNo >= 5000) {
            return "outofrange";
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(boothNo - 4000);
            sb3.append("घ");
            return sb3.toString();
        }
    }
}
