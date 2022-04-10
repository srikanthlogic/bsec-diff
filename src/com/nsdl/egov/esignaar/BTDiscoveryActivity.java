package com.nsdl.egov.esignaar;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.surepass.surepassesign.R;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;
/* loaded from: classes3.dex */
public class BTDiscoveryActivity extends AppCompatActivity {
    public static BluetoothAdapter e = BluetoothAdapter.getDefaultAdapter();
    TextView h;
    private boolean i;
    String a = "";
    String b = "";
    String c = "";
    String d = "";
    private ListView j = null;
    private Hashtable<String, Hashtable<String, String>> k = null;
    private ArrayList<HashMap<String, Object>> l = null;
    Context f = this;
    private SimpleAdapter m = null;
    private String n = "";
    private String o = "";
    String g = "";
    private String p = "Act_btdiscovery";
    private BroadcastReceiver q = new BroadcastReceiver() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Hashtable hashtable = new Hashtable();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Bundle extras = intent.getExtras();
            hashtable.put("RSSI", String.valueOf(extras.get("android.bluetooth.device.extra.RSSI")));
            hashtable.put("NAME", bluetoothDevice.getName() == null ? "Null" : bluetoothDevice.getName());
            hashtable.put("COD", String.valueOf(extras.get("android.bluetooth.device.extra.CLASS")));
            hashtable.put("BOND", bluetoothDevice.getBondState() == 12 ? "bonded" : "nothing");
            String valueOf = String.valueOf(extras.get("android.bluetooth.device.extra.DEVICE_TYPE"));
            if (valueOf.equals("null")) {
                valueOf = "-1";
            }
            hashtable.put("DEVICE_TYPE", valueOf);
            BTDiscoveryActivity.this.k.put(bluetoothDevice.getAddress(), hashtable);
            BTDiscoveryActivity.this.a();
        }
    };
    private BroadcastReceiver r = new BroadcastReceiver() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BTDiscoveryActivity.this.i = true;
            BTDiscoveryActivity bTDiscoveryActivity = BTDiscoveryActivity.this;
            bTDiscoveryActivity.unregisterReceiver(bTDiscoveryActivity.q);
            BTDiscoveryActivity bTDiscoveryActivity2 = BTDiscoveryActivity.this;
            bTDiscoveryActivity2.unregisterReceiver(bTDiscoveryActivity2.r);
            if (BTDiscoveryActivity.this.k != null) {
                BTDiscoveryActivity.this.k.size();
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends AsyncTask<String, String, Integer> {
        private ProgressDialog b;

        private a() {
            BTDiscoveryActivity.this = r1;
            this.b = null;
        }

        /* renamed from: a */
        public Integer doInBackground(String... strArr) {
            if (!BTDiscoveryActivity.e.isEnabled()) {
                return 1;
            }
            int i = 10000;
            while (i > 0 && !BTDiscoveryActivity.this.i) {
                i -= 150;
                SystemClock.sleep(150);
            }
            return 2;
        }

        /* renamed from: a */
        public void onPostExecute(Integer num) {
            if (this.b.isShowing()) {
                this.b.dismiss();
            }
            if (BTDiscoveryActivity.e.isDiscovering()) {
                BTDiscoveryActivity.e.cancelDiscovery();
            }
            if (2 != num.intValue()) {
                num.intValue();
            }
        }

        /* renamed from: b */
        public void onProgressUpdate(String... strArr) {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            this.b = new ProgressDialog(BTDiscoveryActivity.this);
            this.b.setMessage("Scanning for Devices");
            this.b.setCancelable(false);
            this.b.setCanceledOnTouchOutside(false);
            this.b.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.a.1
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    BTDiscoveryActivity.this.i = true;
                }
            });
            this.b.show();
            BTDiscoveryActivity.this.b();
        }
    }

    public String a(String str) {
        if (!Pattern.compile("^[-\\+]?[\\d]+$").matcher(str).matches()) {
            return str;
        }
        int intValue = Integer.valueOf(str).intValue();
        return intValue != 1 ? intValue != 2 ? intValue != 3 ? "device_type_bredr" : "device_type_dumo" : "device_type_ble" : "device_type_bredr";
    }

    public void b() {
        this.i = false;
        Hashtable<String, Hashtable<String, String>> hashtable = this.k;
        if (hashtable == null) {
            this.k = new Hashtable<>();
        } else {
            hashtable.clear();
        }
        registerReceiver(this.r, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        registerReceiver(this.q, new IntentFilter("android.bluetooth.device.action.FOUND"));
        e.startDiscovery();
        a();
    }

    protected void a() {
        if (this.l == null) {
            this.l = new ArrayList<>();
        }
        if (this.m == null) {
            this.m = new SimpleAdapter(this, this.l, R.layout.list_view_item_devices, new String[]{"NAME", "MAC"}, new int[]{R.id.device_item_ble_name, R.id.device_item_ble_mac});
            this.j.setAdapter((ListAdapter) this.m);
        }
        this.l.clear();
        Enumeration<String> keys = this.k.keys();
        while (keys.hasMoreElements()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            String nextElement = keys.nextElement();
            hashMap.put("MAC", nextElement);
            hashMap.put("NAME", this.k.get(nextElement).get("NAME"));
            hashMap.put("RSSI", this.k.get(nextElement).get("RSSI"));
            hashMap.put("COD", this.k.get(nextElement).get("COD"));
            hashMap.put("BOND", this.k.get(nextElement).get("BOND"));
            hashMap.put("DEVICE_TYPE", a(this.k.get(nextElement).get("DEVICE_TYPE")));
            this.l.add(hashMap);
        }
        this.m.notifyDataSetChanged();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        BiometricActivityPieSupport.e = 10;
        WithoutOTGSupportBiometricActivity.I = 10;
        finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bt_discovery);
        if (!e.isEnabled()) {
            e.enable();
        }
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_bt_discovery);
        this.j = (ListView) findViewById(R.id.actDiscovery_lv);
        this.h = (TextView) findViewById(R.id.btnrescan);
        this.j.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                StringBuilder sb;
                BTDiscoveryActivity bTDiscoveryActivity;
                String str;
                BTDiscoveryActivity.this.n = ((TextView) view.findViewById(R.id.device_item_ble_mac)).getText().toString();
                Intent intent = new Intent();
                intent.putExtra("MAC", BTDiscoveryActivity.this.n);
                intent.putExtra("RSSI", (String) ((Hashtable) BTDiscoveryActivity.this.k.get(BTDiscoveryActivity.this.n)).get("RSSI"));
                intent.putExtra("NAME", (String) ((Hashtable) BTDiscoveryActivity.this.k.get(BTDiscoveryActivity.this.n)).get("NAME"));
                intent.putExtra("COD", (String) ((Hashtable) BTDiscoveryActivity.this.k.get(BTDiscoveryActivity.this.n)).get("COD"));
                intent.putExtra("BOND", (String) ((Hashtable) BTDiscoveryActivity.this.k.get(BTDiscoveryActivity.this.n)).get("BOND"));
                BTDiscoveryActivity bTDiscoveryActivity2 = BTDiscoveryActivity.this;
                intent.putExtra("DEVICE_TYPE", bTDiscoveryActivity2.a((String) ((Hashtable) bTDiscoveryActivity2.k.get(BTDiscoveryActivity.this.n)).get("DEVICE_TYPE")));
                BTDiscoveryActivity bTDiscoveryActivity3 = BTDiscoveryActivity.this;
                bTDiscoveryActivity3.o = (String) ((Hashtable) bTDiscoveryActivity3.k.get(BTDiscoveryActivity.this.n)).get("NAME");
                BTDiscoveryActivity bTDiscoveryActivity4 = BTDiscoveryActivity.this;
                bTDiscoveryActivity4.b = bTDiscoveryActivity4.getIntent().getStringExtra("urlType");
                BTDiscoveryActivity bTDiscoveryActivity5 = BTDiscoveryActivity.this;
                bTDiscoveryActivity5.c = bTDiscoveryActivity5.getIntent().getStringExtra("DeviceType");
                BTDiscoveryActivity bTDiscoveryActivity6 = BTDiscoveryActivity.this;
                bTDiscoveryActivity6.d = bTDiscoveryActivity6.getIntent().getStringExtra("wadh");
                if (BTDiscoveryActivity.this.b == null || !BTDiscoveryActivity.this.b.equalsIgnoreCase("PROD")) {
                    bTDiscoveryActivity = BTDiscoveryActivity.this;
                    sb = new StringBuilder();
                    str = "<PidOptions ver =\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" otp=\"\" posh=\"LEFT_INDEX\" env=\"PP\" wadh=\"";
                } else {
                    bTDiscoveryActivity = BTDiscoveryActivity.this;
                    sb = new StringBuilder();
                    str = "<PidOptions ver =\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" otp=\"\" posh=\"LEFT_INDEX\" env=\"P\" wadh=\"";
                }
                sb.append(str);
                sb.append(BTDiscoveryActivity.this.d);
                sb.append("\" /> <Demo></Demo> <CustOpts> <Param name=\"");
                sb.append(BTDiscoveryActivity.this.o);
                sb.append("\" value=\"");
                sb.append(BTDiscoveryActivity.this.n);
                sb.append("\" /> </CustOpts> </PidOptions>");
                bTDiscoveryActivity.a = sb.toString();
                BiometricActivityPieSupport.e = 30;
                BiometricActivityPieSupport.f = 5;
                WithoutOTGSupportBiometricActivity.I = 30;
                WithoutOTGSupportBiometricActivity.L = 5;
                Intent intent2 = new Intent();
                intent2.putExtra("DEVICENAME", BTDiscoveryActivity.this.o);
                intent2.putExtra("DEVICEMAC", BTDiscoveryActivity.this.n);
                intent2.putExtra("ResponseXml", BTDiscoveryActivity.this.a);
                BTDiscoveryActivity.this.setResult(-1, intent2);
                BTDiscoveryActivity.this.finish();
            }
        });
        this.h.setOnClickListener(new View.OnClickListener() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BluetoothAdapter.getDefaultAdapter().enable();
                new Handler().postDelayed(new Runnable() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        new a().execute("");
                    }
                }, 1000);
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: com.nsdl.egov.esignaar.BTDiscoveryActivity.5
            @Override // java.lang.Runnable
            public void run() {
                new a().execute("");
            }
        }, 1000);
    }
}
