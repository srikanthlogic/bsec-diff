package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
/* loaded from: classes2.dex */
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = PathInterpolatorCompat.MAX_NUM_POINTS;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        if (new UserAuth(this).ifLogin().booleanValue()) {
            Intent i = new Intent(getApplicationContext(), ListUserActivity.class);
            i.setFlags(i.getFlags() | 1073741824);
            startActivity(i);
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), LoginActivityNew.class));
                SplashActivity.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void startMainActivity(String boothid) {
        startActivity(new Intent(this, ListUserActivity.class));
    }
}
