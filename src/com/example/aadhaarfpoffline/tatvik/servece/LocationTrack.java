package com.example.aadhaarfpoffline.tatvik.servece;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.network.UserLocationUpdatePostResponse;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class LocationTrack extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50000;
    private static final long MIN_TIME_BW_UPDATES = 60000;
    static double latitude;
    static Location loc;
    protected static LocationManager locationManager;
    static double longitude;
    private static Context mContext;
    private Location boothLocation;
    private String phone;
    private static LocationTrack instance = null;
    static boolean checkGPS = false;
    static boolean checkNetwork = false;
    static boolean canGetLocation = false;

    private LocationTrack() {
    }

    public static LocationTrack getInstance(Context context) {
        if (instance == null) {
            instance = new LocationTrack();
        }
        mContext = context;
        getLocation();
        return instance;
    }

    public LocationTrack(Context mContext2) {
        mContext = mContext2;
        getLocation();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    private static Location getLocation() {
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(1);
            criteria.setPowerRequirement(1);
            criteria.setAccuracy(1);
            criteria.setSpeedRequired(true);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            locationManager = (LocationManager) mContext.getSystemService(FirebaseAnalytics.Param.LOCATION);
            locationManager.getBestProvider(criteria, true);
            checkGPS = locationManager.isProviderEnabled("gps");
            checkNetwork = locationManager.isProviderEnabled("network");
            if (checkGPS || checkNetwork) {
                canGetLocation = true;
                if (checkGPS) {
                    if (ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                        ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_COARSE_LOCATION");
                    }
                    locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 50000.0f, instance);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation("gps");
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    }
                }
                if (checkNetwork) {
                    if (ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                        ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_COARSE_LOCATION");
                    }
                    locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 50000.0f, instance);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation("network");
                    }
                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                    }
                }
            } else {
                Toast.makeText(mContext, "No Service Provider is available", 0).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loc;
    }

    public double getLongitude() {
        Location location = loc;
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public double getLatitude() {
        Location location = loc;
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public boolean canGetLocation() {
        return canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.servece.LocationTrack.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                LocationTrack.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.servece.LocationTrack.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void stopListener() {
        if (locationManager == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            locationManager.removeUpdates(this);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String s) {
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String s) {
    }

    public void setBoothLocation(Location location) {
        this.boothLocation = location;
    }

    public float getBoothDistance() {
        Location presentLocation = new Location("PresentLocation");
        presentLocation.setLatitude(latitude);
        presentLocation.setLongitude(longitude);
        return presentLocation.distanceTo(this.boothLocation);
    }

    public void locationUpdate(String phone) {
        if (phone == null) {
            phone = this.phone;
        }
        Map<String, String> map = new HashMap<>();
        map.put(FirebaseAnalytics.Param.LOCATION, latitude + ":" + longitude);
        map.put("phone", phone);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postLocationUpdate(map).enqueue(new Callback<UserLocationUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.servece.LocationTrack.3
            @Override // retrofit2.Callback
            public void onResponse(Call<UserLocationUpdatePostResponse> call, Response<UserLocationUpdatePostResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserLocationUpdatePostResponse> call, Throwable t) {
            }
        });
    }

    public void locationUpdateWithAction(String phone, String action) {
        if (phone == null) {
            phone = this.phone;
        }
        Map<String, String> map = new HashMap<>();
        map.put(FirebaseAnalytics.Param.LOCATION, latitude + ":" + longitude);
        map.put("phone", phone);
        map.put("action", action);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postLocationUpdate(map).enqueue(new Callback<UserLocationUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.servece.LocationTrack.4
            @Override // retrofit2.Callback
            public void onResponse(Call<UserLocationUpdatePostResponse> call, Response<UserLocationUpdatePostResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserLocationUpdatePostResponse> call, Throwable t) {
            }
        });
    }

    public long distanceAllowed() {
        return MIN_DISTANCE_CHANGE_FOR_UPDATES;
    }

    @Override // android.app.Service
    public void onCreate() {
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        return 1;
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
}
