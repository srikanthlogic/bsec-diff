package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import java.util.Arrays;
import java.util.Objects;
/* loaded from: classes2.dex */
public class FingerprintDeviceSelectionActivity extends AppCompatActivity {
    Button btnSubmitDevice;
    Spinner spnDeviceSelection;
    String[] strDevices = {Const.Tatvik, Const.eNBioScan};
    String selectedDevice = "";

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_device_selection);
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setTitle("Device Selection");
        initDropdownAndSetAdapter();
        initSubmitBtn();
    }

    void initDropdownAndSetAdapter() {
        this.spnDeviceSelection = (Spinner) findViewById(R.id.spnDeviceSelection);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 17367048, this.strDevices);
        adapter.setDropDownViewResource(R.layout.dropdown);
        this.spnDeviceSelection.setAdapter((SpinnerAdapter) adapter);
        this.spnDeviceSelection.setSelection(Arrays.asList(this.strDevices).indexOf(new UserAuth(getApplicationContext()).getFingerPrintDevice()));
        this.spnDeviceSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FingerprintDeviceSelectionActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FingerprintDeviceSelectionActivity fingerprintDeviceSelectionActivity = FingerprintDeviceSelectionActivity.this;
                fingerprintDeviceSelectionActivity.selectedDevice = fingerprintDeviceSelectionActivity.strDevices[position];
                if (parent != null && parent.getChildAt(0) != null) {
                    ((TextView) parent.getChildAt(0)).setTextColor(-1);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    void initSubmitBtn() {
        this.btnSubmitDevice = (Button) findViewById(R.id.btnSubmitDevice);
        this.btnSubmitDevice.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$FingerprintDeviceSelectionActivity$B88b5WrJiOludfE_NM3ct2yU46Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FingerprintDeviceSelectionActivity.this.lambda$initSubmitBtn$0$FingerprintDeviceSelectionActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initSubmitBtn$0$FingerprintDeviceSelectionActivity(View view) {
        UserAuth userAuth = new UserAuth(getApplicationContext());
        userAuth.setFingerPrintDevice(this.selectedDevice);
        Toast.makeText(this, "Selected device is : " + userAuth.getFingerPrintDevice(), 1).show();
        startActivity(new Intent(this, ListUserActivity.class));
    }
}
