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
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
/* loaded from: classes2.dex */
public class FingerprintDeviceSelectionActivity extends AppCompatActivity {
    Button btnExportCSV;
    Button btnSubmitDevice;
    DBHelper db;
    Spinner spnDeviceSelection;
    UserAuth userAuth;
    String[] strDevices = {Const.Tatvik, Const.eNBioScan};
    String selectedDevice = "";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_device_selection);
        this.db = new DBHelper(this);
        this.userAuth = new UserAuth(this);
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
        this.btnExportCSV = (Button) findViewById(R.id.btnExportCSV);
        this.btnSubmitDevice.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$FingerprintDeviceSelectionActivity$B88b5WrJiOludfE_NM3ct2yU46Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FingerprintDeviceSelectionActivity.this.lambda$initSubmitBtn$0$FingerprintDeviceSelectionActivity(view);
            }
        });
        this.btnExportCSV.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.-$$Lambda$FingerprintDeviceSelectionActivity$Tho6qp2IYddEQVvCBNA80UYzTa4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FingerprintDeviceSelectionActivity.this.lambda$initSubmitBtn$1$FingerprintDeviceSelectionActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initSubmitBtn$0$FingerprintDeviceSelectionActivity(View view) {
        UserAuth userAuth = new UserAuth(getApplicationContext());
        userAuth.setFingerPrintDevice(this.selectedDevice);
        Toast.makeText(this, "Selected device is : " + userAuth.getFingerPrintDevice(), 1).show();
        startActivity(new Intent(this, ListUserActivity.class));
    }

    public /* synthetic */ void lambda$initSubmitBtn$1$FingerprintDeviceSelectionActivity(View view) {
        this.db.exportDatabaseTransTable(this.userAuth.getDistrictNo(), this.userAuth.getBlockID(), this.userAuth.getPanchayatId(), this.userAuth.getWardNo(), this.userAuth.getBoothNo(), getCurrentTimeInFormatForCSV());
    }

    public String getCurrentTimeInFormatForCSV() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }
}
