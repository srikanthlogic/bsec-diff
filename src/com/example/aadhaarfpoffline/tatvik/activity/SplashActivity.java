package com.example.aadhaarfpoffline.tatvik.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.receivers.ResponseBroadcastReceiver;
import com.example.aadhaarfpoffline.tatvik.receivers.ToastBroadcastReceiver;
import com.example.aadhaarfpoffline.tatvik.services.BackgroundService;
/* loaded from: classes2.dex */
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = PathInterpolatorCompat.MAX_NUM_POINTS;
    ResponseBroadcastReceiver broadcastReceiver;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        final UserAuth userAuth = new UserAuth(this);
        this.broadcastReceiver = new ResponseBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BackgroundService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, intentFilter);
        scheduleAlarm();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (userAuth.ifLogin().booleanValue()) {
                    Intent i = new Intent(SplashActivity.this.getApplicationContext(), ListUserActivity.class);
                    i.setFlags(i.getFlags() | 1073741824);
                    SplashActivity.this.startActivity(i);
                } else {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), LoginActivityWithoutLocation.class));
                }
                SplashActivity.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
        Settings.Secure.getString(getContentResolver(), "android_id");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void startMainActivity(String boothid) {
        startActivity(new Intent(this, ListUserActivity.class));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BackgroundService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, intentFilter);
    }

    private void scheduleAlarm() {
        PendingIntent toastAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(getApplicationContext(), ToastBroadcastReceiver.class), 134217728);
        ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).setInexactRepeating(0, System.currentTimeMillis(), 15000, toastAlarmIntent);
    }
}
