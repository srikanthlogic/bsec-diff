package com.example.aadhaarfpoffline.tatvik.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.android.volley.VolleyError;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstanceAadhaar;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.AadhaarMatchUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.AadhaarUserCheckGetResponse;
import com.example.aadhaarfpoffline.tatvik.network.BoothOfficerDeviceStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.FinperprintCompareServerResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransactionRowPostResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterDataGetResponse;
import com.example.aadhaarfpoffline.tatvik.uid.RDService;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.cache.DiskLruCache;
import org.apache.commons.io.IOUtils;
import org.tatvik.fp.CaptureResult;
import org.tatvik.fp.TMF20API;
import org.tatvik.fp.TMF20ErrorCodes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class AadharActivity extends AppCompatActivity implements MFS100Event, Observer {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final int QUALITY_LIMIT = 60;
    public static final String biometricEnv = "\"P\"";
    EditText aadhaarEditText;
    private NBioBSPJNI bsp;
    Button button4;
    private Button button_authenticate;
    private Button button_ok;
    Button button_validate;
    private byte[] byCapturedRaw1;
    private byte[] byTemplate1;
    Document capDoc;
    CaptureResult captRslt1;
    private Button captureFingerprint;
    private TextView captureResult;
    Document capturedDoc;
    CheckBox cbFastDetection;
    Context context;
    private CountDownLatch countDownLatch;
    DBHelper db;
    private NBioBSPJNI.Export exportEngine;
    byte[] finger_template1;
    ImageView imgFinger;
    private NBioBSPJNI.IndexSearch indexSearch;
    String lan;
    Document metaDoc;
    private int nCapturedRawHeight1;
    private int nCapturedRawWidth1;
    ProgressBar progressBar;
    Resources resources;
    TMF20API tmf20lib;
    UserAuth userAuth;
    String voted;
    private TextView xmlResponse;
    private TextView xmlToPost;
    private static long mLastClkTime = 0;
    private static long Threshold = 3000;
    private String UID = "725462470794";
    int LAUNCH_SECOND_ACTIVITY = 1;
    int CAPTURE_FINGERPRINT_ACTIVITY = 2;
    int DEVICE_INFORMATION_CODE = 10;
    String partresult1 = "";
    String partresult2 = "";
    String fullresult3 = "";
    private String totalResponse = "";
    private String voterid = "";
    private String aadhaarmatch = "0";
    private String fpcaptureString = "";
    private Boolean authenticatePartDone = false;
    private FingerData lastCapFingerData = null;
    ScannerAction scannerAction = ScannerAction.Capture;
    int timeout = 10000;
    MFS100 mfs100 = null;
    private boolean isCaptureRunning = false;
    Boolean firstfp = false;
    Boolean votertoballowed = false;
    int nFIQ = 0;
    String msg = "";
    String slnoinward = "";
    String matchslnoinward = "";
    private boolean bspConnected = false;
    String androidId = "";
    String UDevId = "";
    private long mLastAttTime = 0;
    long mLastDttTime = 0;
    NBioBSPJNI.CAPTURE_CALLBACK mCallback = new NBioBSPJNI.CAPTURE_CALLBACK() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.18
        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public void OnDisConnected() {
            NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
            AadharActivity.this.bspConnected = false;
            if (AadharActivity.this.countDownLatch != null) {
                Log.v("Pawan", "latch countdown on device open fail");
                AadharActivity.this.countDownLatch.countDown();
            }
            String str = "NBioBSP Disconnected: " + AadharActivity.this.bsp.GetErrorCode();
        }

        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public void OnConnected() {
            String str = "Device Open Success : " + AadharActivity.this.bsp.GetErrorCode();
            AadharActivity.this.bspConnected = true;
            if (AadharActivity.this.countDownLatch != null) {
                Log.v("Pawan", "latch countdown on device open success");
                AadharActivity.this.countDownLatch.countDown();
            }
        }

        @Override // com.nitgen.SDK.AndroidBSP.NBioBSPJNI.CAPTURE_CALLBACK
        public int OnCaptured(NBioBSPJNI.CAPTURED_DATA capturedData) {
            if (capturedData.getImage() != null) {
                AadharActivity.this.imgFinger.setImageBitmap(capturedData.getImage());
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

    /* loaded from: classes2.dex */
    private enum ScannerAction {
        Capture,
        Verify
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar);
        this.userAuth = new UserAuth(this);
        if (this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
            RDService.registerForActivityResult(this);
        }
        this.lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, this.lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        this.androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        this.UDevId = this.androidId;
        getSupportActionBar().setTitle(this.resources.getString(R.string.aadhaar_activity));
        this.tmf20lib = new TMF20API(this);
        Intent intent = getIntent();
        this.voterid = intent.getStringExtra("voter_id");
        this.slnoinward = intent.getStringExtra("slnoinward");
        PrintStream printStream = System.out;
        printStream.println("voterid===" + this.voterid);
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateAadhaarUser(final String aadhaarnum) {
        HashMap<String, String> map = new HashMap<>();
        map.put("aadhaarnum", aadhaarnum);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByVoterAadhaarNum(map).enqueue(new Callback<AadhaarUserCheckGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:18:0x014e  */
            /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Type inference failed for: r0v0 */
            /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.String] */
            /* JADX WARN: Type inference failed for: r0v3 */
            /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
            /* JADX WARN: Type inference failed for: r1v3, types: [com.example.aadhaarfpoffline.tatvik.activity.AadharActivity] */
            /* JADX WARN: Type inference failed for: r2v0 */
            /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.String] */
            /* JADX WARN: Type inference failed for: r2v2 */
            /* JADX WARN: Unknown variable types count: 2 */
            @Override // retrofit2.Callback
            /* Code decompiled incorrectly, please refer to instructions dump */
            public void onResponse(Call<AadhaarUserCheckGetResponse> call, Response<AadhaarUserCheckGetResponse> response) {
                ?? r0 = 2131820578;
                String str = ",";
                ?? r2 = 2131820872;
                if (response == null || !response.isSuccessful() || response.body() == null) {
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "Error getting response.", 0).show();
                    if (AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum) > 0) {
                        VoterDataNewModel voter = AadharActivity.this.db.getVoterBySlNoInWard(String.valueOf(AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum)));
                        StringBuilder sb = new StringBuilder();
                        r2 = AadharActivity.this.resources.getString(R.string.you_cannot_vote_only);
                        sb.append((String) r2);
                        sb.append((String) str);
                        sb.append(AadharActivity.this.resources.getString(R.string.aadhaar_not_vote));
                        r0 = sb.toString();
                        str = AadharActivity.this;
                        str.popupFailedOffline(r0, voter);
                        return;
                    }
                    return;
                }
                try {
                    if (response.body().getAadhaaruserexists().booleanValue()) {
                        AadharActivity.this.captureFingerprint.setEnabled(false);
                        AadharActivity.this.captureFingerprint.setVisibility(8);
                        AadharActivity.this.button_authenticate.setEnabled(false);
                        AadharActivity.this.button_authenticate.setVisibility(8);
                        AadharActivity.this.button_ok.setEnabled(false);
                        AadharActivity.this.button_ok.setVisibility(8);
                        response.body().getVoter().getEPIC_NO();
                        AadharActivity.this.resources.getString(R.string.aadhaar_exist);
                        AadharActivity.this.popupFailed(AadharActivity.this.resources.getString(R.string.you_cannot_vote_only) + ((String) str) + AadharActivity.this.resources.getString(R.string.aadhaar_not_vote), response.body().getVoter());
                        return;
                    } else if (AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum) > 0) {
                        VoterDataNewModel voter2 = AadharActivity.this.db.getVoterBySlNoInWard(String.valueOf(AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum)));
                        AadharActivity.this.popupFailedOffline(AadharActivity.this.resources.getString(R.string.you_cannot_vote_only) + ((String) str) + AadharActivity.this.resources.getString(R.string.aadhaar_not_vote), voter2);
                        return;
                    } else {
                        AadharActivity.this.captureFingerprint.setVisibility(0);
                        AadharActivity.this.captureFingerprint.setEnabled(true);
                        AadharActivity.this.button_authenticate.setEnabled(true);
                        AadharActivity.this.button_authenticate.setVisibility(0);
                        AadharActivity.this.button_ok.setEnabled(true);
                        AadharActivity.this.button_ok.setVisibility(0);
                        AadharActivity.this.popup(AadharActivity.this.resources.getString(R.string.aadhaar_doesnt_exist), R.drawable.right_icon_trp);
                        return;
                    }
                } catch (Exception e) {
                    if (AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum) <= 0) {
                    }
                }
                if (AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum) <= 0) {
                    VoterDataNewModel voter3 = AadharActivity.this.db.getVoterBySlNoInWard(String.valueOf(AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum)));
                    AadharActivity.this.popupFailedOffline(AadharActivity.this.resources.getString(r2 == true ? 1 : 0) + str + AadharActivity.this.resources.getString(r0 == true ? 1 : 0), voter3);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<AadhaarUserCheckGetResponse> call, Throwable t) {
                Toast.makeText(AadharActivity.this.getApplicationContext(), "Failure", 0).show();
                if (AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum) > 0) {
                    VoterDataNewModel voter = AadharActivity.this.db.getVoterBySlNoInWard(String.valueOf(AadharActivity.this.db.getSlNoInWardForAadhaar(aadhaarnum)));
                    AadharActivity.this.popupFailedOffline(AadharActivity.this.resources.getString(R.string.you_cannot_vote_only) + "," + AadharActivity.this.resources.getString(R.string.aadhaar_not_vote), voter);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == -1) {
                String result = data.getStringExtra("DEVICE_INFO");
                String result2 = data.getStringExtra("RD_SERVICE_INFO");
                this.partresult1 = "result1=" + result + "  ####result2=" + result2;
                this.metaDoc = changeTagName(convertStringToXMLDocument(result), "", "DeviceInfo", "Meta");
                try {
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty("indent", "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", ExifInterface.GPS_MEASUREMENT_2D);
                    StreamResult streamRresult = new StreamResult(new StringWriter());
                    transformer.transform(new DOMSource(this.metaDoc), streamRresult);
                    String xmlString = streamRresult.getWriter().toString();
                    PrintStream printStream = System.out;
                    printStream.println("xml444=" + xmlString);
                } catch (Exception e) {
                }
                Log.d("result device info===", result);
                Log.d("result RD_SERVICE_INFO===", result2);
            }
            if (resultCode == 0) {
                Toast.makeText(getApplicationContext(), "result= canceled", 1).show();
            }
        } else if (requestCode == this.CAPTURE_FINGERPRINT_ACTIVITY) {
            Log.v("Pawan ", "CAPTURE_FINGERPRINT_ACTIVITY on activity result");
            if (resultCode == -1) {
                Bundle b = data.getExtras();
                if (b != null) {
                    Log.v("Pawan ", b.getString("PID_DATA"));
                }
                String result3 = data.getStringExtra("PID_DATA");
                data.getStringExtra("CAPTURE");
                this.fpcaptureString = result3;
                Log.d("result device info===", result3);
                this.captureResult.setText(result3);
                this.partresult2 = result3;
                this.capDoc = convertStringToXMLDocument(result3);
            }
            if (resultCode == 0) {
                Toast.makeText(getApplicationContext(), "result capture= canceled", 1).show();
            }
        } else if (requestCode == this.DEVICE_INFORMATION_CODE && resultCode == -1) {
            data.getStringExtra("RD_SERVICE_INFO");
            data.getStringExtra("DEVICE_INFO");
        }
    }

    private boolean aadhaarexistsLocalDb(String aadhaanum) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(22:2|(4:44|3|4|(3:50|5|6))|(3:54|7|8)|16|42|17|18|21|52|22|23|(3:56|24|25)|31|46|32|33|48|34|35|40|41|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(25:2|44|3|4|(3:50|5|6)|(3:54|7|8)|16|42|17|18|21|52|22|23|(3:56|24|25)|31|46|32|33|48|34|35|40|41|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(27:2|44|3|4|50|5|6|(3:54|7|8)|16|42|17|18|21|52|22|23|(3:56|24|25)|31|46|32|33|48|34|35|40|41|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x017e, code lost:
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01e6, code lost:
        r0 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0249, code lost:
        r0 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x024c, code lost:
        r0 = r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void createStringXml(String uid, Document doc) {
        String s3Meta;
        String hmacString;
        String s3Meta2;
        String s3Meta3;
        Document newDocument;
        Element table;
        System.out.println("createStringXml1");
        String s1 = "<Auth  uid=\"" + uid + "\"  rc=\"Y\" tid=\"registered\" sa=\"PRWDB22452\" scheme=\"54\" ver=\"2.5\" txn=\"" + (new SimpleDateFormat("yyyyMMddHHmm").format(Calendar.getInstance().getTime()) + ":BIH:DIT") + "\" xmlns=\"http://www.uidai.gov.in/authentication/uid-auth-request/2.0\">";
        System.out.println("createStringXml2");
        NodeList metaNodes = doc.getElementsByTagName("Meta");
        System.out.println("createStringXml3" + metaNodes);
        Element element = (Element) metaNodes.item(0);
        String s3Meta4 = "";
        try {
            Node node = metaNodes.item(0);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            newDocument = factory.newDocumentBuilder().newDocument();
            newDocument.appendChild(newDocument.importNode(node, true));
            table = newDocument.getDocumentElement();
            try {
            } catch (Exception e) {
                s3Meta3 = s3Meta4;
            }
        } catch (Exception e2) {
            s3Meta3 = s3Meta4;
        }
        try {
            table.removeChild(table.getElementsByTagName("additional_info").item(0));
            String s3Meta5 = getStringFromDocument(newDocument);
            System.out.println("createStringXml3a" + s3Meta5);
            s3Meta4 = s3Meta5.replace(" </Meta>", "").replace(">", "/>");
            s3Meta = s3Meta4.replace("//>", "/>");
        } catch (Exception e3) {
            s3Meta3 = s3Meta4;
            s3Meta = s3Meta3;
            System.out.println("createStringXml4" + element);
            String sofar = (s1 + "<Uses pi=\"n\" pa=\"n\" pfa=\"n\" bio=\"y\" bt=\"FMR\" otp=\"n\" pin=\"n\"/>" + s3Meta).replace(IOUtils.LINE_SEPARATOR_UNIX, "");
            System.out.println("sofar1" + sofar);
            String skeyString = "";
            Node node1 = doc.getElementsByTagName("Skey").item(0);
            DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
            factory1.setNamespaceAware(true);
            Document newDocument1 = factory1.newDocumentBuilder().newDocument();
            newDocument1.appendChild(newDocument1.importNode(node1, true));
            skeyString = getStringFromDocument(newDocument1);
            String skeyString2 = skeyString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            String sofar2 = sofar + skeyString2;
            System.out.println("sofar2" + sofar2);
            hmacString = "";
            Node node2 = doc.getElementsByTagName("Hmac").item(0);
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            factory2.setNamespaceAware(true);
            Document newDocument2 = factory2.newDocumentBuilder().newDocument();
            newDocument2.appendChild(newDocument2.importNode(node2, true));
            hmacString = getStringFromDocument(newDocument2);
            s3Meta2 = hmacString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            String sofar3 = sofar2 + s3Meta2;
            System.out.println("sofar3" + sofar3);
            String dataString = "";
            Node node3 = doc.getElementsByTagName("Data").item(0);
            DocumentBuilderFactory factory3 = DocumentBuilderFactory.newInstance();
            factory3.setNamespaceAware(true);
            Document newDocument3 = factory3.newDocumentBuilder().newDocument();
            newDocument3.appendChild(newDocument3.importNode(node3, true));
            dataString = getStringFromDocument(newDocument3);
            String dataString2 = dataString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            String sofar4 = sofar3 + dataString2;
            System.out.println("sofar4" + sofar4);
            String sofar5 = (sofar4 + "</Auth>").replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?/>", "");
            System.out.println("sofar5" + sofar5);
            this.xmlToPost.setText(sofar5);
            postXMLRetrofit(sofar5);
        }
        System.out.println("createStringXml4" + element);
        String sofar6 = (s1 + "<Uses pi=\"n\" pa=\"n\" pfa=\"n\" bio=\"y\" bt=\"FMR\" otp=\"n\" pin=\"n\"/>" + s3Meta).replace(IOUtils.LINE_SEPARATOR_UNIX, "");
        System.out.println("sofar1" + sofar6);
        String skeyString3 = "";
        Node node12 = doc.getElementsByTagName("Skey").item(0);
        DocumentBuilderFactory factory12 = DocumentBuilderFactory.newInstance();
        factory12.setNamespaceAware(true);
        Document newDocument12 = factory12.newDocumentBuilder().newDocument();
        newDocument12.appendChild(newDocument12.importNode(node12, true));
        skeyString3 = getStringFromDocument(newDocument12);
        String skeyString22 = skeyString3.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        String sofar22 = sofar6 + skeyString22;
        System.out.println("sofar2" + sofar22);
        hmacString = "";
        Node node22 = doc.getElementsByTagName("Hmac").item(0);
        DocumentBuilderFactory factory22 = DocumentBuilderFactory.newInstance();
        factory22.setNamespaceAware(true);
        Document newDocument22 = factory22.newDocumentBuilder().newDocument();
        try {
            newDocument22.appendChild(newDocument22.importNode(node22, true));
            hmacString = getStringFromDocument(newDocument22);
            s3Meta2 = hmacString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        } catch (Exception e4) {
            String hmacString2 = hmacString;
            s3Meta2 = hmacString2;
            String sofar32 = sofar22 + s3Meta2;
            System.out.println("sofar3" + sofar32);
            String dataString3 = "";
            Node node32 = doc.getElementsByTagName("Data").item(0);
            DocumentBuilderFactory factory32 = DocumentBuilderFactory.newInstance();
            factory32.setNamespaceAware(true);
            Document newDocument32 = factory32.newDocumentBuilder().newDocument();
            newDocument32.appendChild(newDocument32.importNode(node32, true));
            dataString3 = getStringFromDocument(newDocument32);
            String dataString22 = dataString3.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            String sofar42 = sofar32 + dataString22;
            System.out.println("sofar4" + sofar42);
            String sofar52 = (sofar42 + "</Auth>").replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?/>", "");
            System.out.println("sofar5" + sofar52);
            this.xmlToPost.setText(sofar52);
            postXMLRetrofit(sofar52);
        }
        String sofar322 = sofar22 + s3Meta2;
        System.out.println("sofar3" + sofar322);
        String dataString32 = "";
        Node node322 = doc.getElementsByTagName("Data").item(0);
        DocumentBuilderFactory factory322 = DocumentBuilderFactory.newInstance();
        factory322.setNamespaceAware(true);
        Document newDocument322 = factory322.newDocumentBuilder().newDocument();
        newDocument322.appendChild(newDocument322.importNode(node322, true));
        dataString32 = getStringFromDocument(newDocument322);
        String dataString222 = dataString32.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        String sofar422 = sofar322 + dataString222;
        System.out.println("sofar4" + sofar422);
        String sofar522 = (sofar422 + "</Auth>").replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?/>", "");
        System.out.println("sofar5" + sofar522);
        this.xmlToPost.setText(sofar522);
        postXMLRetrofit(sofar522);
    }

    public static boolean onlyDigits(String str, int n) {
        if (0 >= n || str.charAt(0) < '0' || str.charAt(0) > '9') {
            return false;
        }
        return true;
    }

    private static Node getCrunchifyCompanyElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static <T> void setProtectedFieldValue(Class<T> clazz, String fieldName, T object, Object newValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, newValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Document convertStringToXMLDocument(String xmlString) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Document changeTagName(Document doc, String tag, String fromTag, String toTag) {
        NodeList nodes = doc.getElementsByTagName(fromTag);
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i) instanceof Element) {
                Element elem = (Element) nodes.item(i);
                doc.renameNode(elem, elem.getNamespaceURI(), toTag);
            }
        }
        return doc;
    }

    public void printXML(Document doc, String logTag) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", ExifInterface.GPS_MEASUREMENT_2D);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(new DOMSource(doc), result);
            String xmlString = result.getWriter().toString();
            PrintStream printStream = System.out;
            printStream.println(logTag + "=xxx=" + xmlString);
            System.out.println("bus");
        } catch (Exception e) {
        }
    }

    public String getStringFromDocument(Document doc) {
        try {
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            StringWriter sw = new StringWriter();
            trans.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private void postXMLRetrofit(String xmlBody) {
        this.progressBar.setVisibility(0);
        PrintStream printStream = System.out;
        printStream.println("postXMLRetrofit0" + xmlBody);
        Call<String> call = ((GetDataService) RetrofitClientInstanceAadhaar.getRetrofitInstance().create(GetDataService.class)).postFPAadhaar1(RequestBody.create(MediaType.parse("text/xml"), xmlBody));
        System.out.println("postXMLRetrofit1");
        call.enqueue(new Callback<String>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.2
            @Override // retrofit2.Callback
            public void onResponse(Call<String> call2, Response<String> response) {
                AadharActivity.this.progressBar.setVisibility(8);
                PrintStream printStream2 = System.out;
                printStream2.println("postXMLRetrofit2 response=" + response);
                if (response == null || !response.isSuccessful() || response.body() == null) {
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "Response 0", 1).show();
                    return;
                }
                AadharActivity.this.totalResponse = response.body().toString();
                PrintStream printStream3 = System.out;
                printStream3.println("finalresponse=" + AadharActivity.this.totalResponse);
                Document responseDocument = AadharActivity.convertStringToXMLDocument(AadharActivity.this.totalResponse);
                String retvalue = AadharActivity.this.retVal(responseDocument);
                AadharActivity.this.errVal(responseDocument);
                if (retvalue.equalsIgnoreCase("y")) {
                    AadharActivity.this.popupElse(AadharActivity.this.resources.getString(R.string.u_can_vote_text), true);
                    AadharActivity.this.aadhaarmatch = DiskLruCache.VERSION_1;
                    AadharActivity.this.votertoballowed = true;
                    AadharActivity.this.authenticatePartDone = true;
                    return;
                }
                AadharActivity aadharActivity = AadharActivity.this;
                aadharActivity.popupElse(aadharActivity.resources.getString(R.string.aadhaar_authentication_failed), false);
                AadharActivity.this.aadhaarmatch = "0";
                AadharActivity.this.votertoballowed = false;
                AadharActivity.this.authenticatePartDone = true;
                AadharActivity.this.beep();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<String> call2, Throwable t) {
                AadharActivity.this.progressBar.setVisibility(8);
                Context applicationContext = AadharActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "postXMLRetrofit 4" + t.getMessage(), 1).show();
                PrintStream printStream2 = System.out;
                printStream2.println("postXMLRetrofit error " + t.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String retVal(Document doc) {
        String retvalue = doc.getElementsByTagName("AuthRes").item(0).getAttributes().getNamedItem("ret").getNodeValue();
        PrintStream printStream = System.out;
        printStream.println("retvalue=" + retvalue);
        return retvalue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String errVal(Document doc) {
        String errvalue;
        NodeList nodeList = doc.getElementsByTagName("AuthRes");
        if (nodeList == null || nodeList.getLength() <= 0) {
            return "";
        }
        System.out.println(nodeList.toString());
        if (nodeList.item(0).getAttributes().getNamedItem(NotificationCompat.CATEGORY_ERROR) != null) {
            errvalue = nodeList.item(0).getAttributes().getNamedItem(NotificationCompat.CATEGORY_ERROR).getNodeValue();
        } else {
            errvalue = "";
        }
        PrintStream printStream = System.out;
        printStream.println("errvalue=" + errvalue);
        return errvalue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popupFailed(String failedmessage, VoterDataNewModel voter) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        System.out.println("popup2");
        alertDialogBuilder.setTitle(this.resources.getString(R.string.aadhaar_authentication));
        alertDialogBuilder.setIcon(R.drawable.wrong_icon_trp);
        System.out.println("popup4");
        alertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        View dialogView = getLayoutInflater().inflate(R.layout.alert_custom_layout, (ViewGroup) null);
        alertDialogBuilder.setView(dialogView);
        String imageurl = "";
        SimpleDraweeView matchUserImage = (SimpleDraweeView) dialogView.findViewById(R.id.usermatchimage);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_1);
        imageView.setVisibility(0);
        imageView.setImageResource(R.drawable.wrong_icon_trp);
        TextView messageText = (TextView) dialogView.findViewById(R.id.match_message);
        TextView messageVoterOnNot = (TextView) dialogView.findViewById(R.id.message);
        if (voter.getID_DOCUMENT_IMAGE() != null) {
            imageurl = voter.getID_DOCUMENT_IMAGE();
            Uri uri1 = Uri.parse(imageurl);
            matchUserImage.setVisibility(0);
            matchUserImage.setImageURI(uri1);
        }
        String name = this.resources.getString(R.string.name) + ":";
        if (this.lan.equalsIgnoreCase("en")) {
            if (voter.getFM_NAME_EN() != null) {
                name = name + voter.getFM_NAME_EN();
            }
            if (voter.getLASTNAME_EN() != null) {
                name = name + " " + voter.getLASTNAME_EN();
            }
        } else {
            if (voter.getFM_NAME_V1() != null) {
                name = name + voter.getFM_NAME_V1();
            }
            if (voter.getLASTNAME_V1() != null) {
                name = name + " " + voter.getLASTNAME_V1();
            }
        }
        String age = this.resources.getString(R.string.age) + ":" + voter.getAge();
        String wardnum = this.resources.getString(R.string.ward_no) + ":" + voter.getWardNo();
        String votingdate = this.resources.getString(R.string.voting_date) + ":" + voter.getVOTING_DATE();
        String str = "Voterid:" + voter.getEPIC_NO();
        messageText.setText(name + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.gender) + ":" + voter.getGENDER()) + IOUtils.LINE_SEPARATOR_UNIX + age + IOUtils.LINE_SEPARATOR_UNIX + wardnum + IOUtils.LINE_SEPARATOR_UNIX + votingdate + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.block_no) + ":" + voter.getBlockID()));
        String finalmessage = this.resources.getString(R.string.u_cannot_vote_text);
        StringBuilder sb = new StringBuilder();
        sb.append(finalmessage);
        sb.append(IOUtils.LINE_SEPARATOR_UNIX);
        sb.append(this.resources.getString(R.string.fingerprintrecord_match_text));
        sb.append(voter.getEPIC_NO());
        sb.toString();
        messageVoterOnNot.setVisibility(0);
        messageVoterOnNot.setText(failedmessage);
        AlertDialog alertDialog = alertDialogBuilder.create();
        System.out.println("popup5");
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popupFailedOffline(String failedmessage, VoterDataNewModel voter) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        System.out.println("popup2");
        alertDialogBuilder.setTitle(this.resources.getString(R.string.aadhaar_authentication));
        alertDialogBuilder.setIcon(R.drawable.wrong_icon_trp);
        System.out.println("popup4");
        alertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        View dialogView = getLayoutInflater().inflate(R.layout.alert_custom_layout, (ViewGroup) null);
        alertDialogBuilder.setView(dialogView);
        SimpleDraweeView matchUserImage = (SimpleDraweeView) dialogView.findViewById(R.id.usermatchimage);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_1);
        imageView.setVisibility(0);
        imageView.setImageResource(R.drawable.wrong_icon_trp);
        TextView messageText = (TextView) dialogView.findViewById(R.id.match_message);
        TextView messageVoterOnNot = (TextView) dialogView.findViewById(R.id.message);
        String voterimage = this.db.getImageFromTransactionTable(this.matchslnoinward);
        File imgFile = new File("/sdcard/Images/" + voterimage);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            matchUserImage.setVisibility(0);
            matchUserImage.setImageBitmap(myBitmap);
        } else {
            Uri uri1 = Uri.parse("http://cim.phoneme.in/PanchayatElectionoff/getimages/?file=" + voterimage);
            matchUserImage.setVisibility(0);
            matchUserImage.setImageURI(uri1);
        }
        String name = this.resources.getString(R.string.name) + ":";
        if (this.lan.equalsIgnoreCase("en")) {
            if (voter.getFM_NAME_EN() != null) {
                name = name + voter.getFM_NAME_EN();
            }
            if (voter.getLASTNAME_EN() != null) {
                name = name + " " + voter.getLASTNAME_EN();
            }
        } else {
            if (voter.getFM_NAME_V1() != null) {
                name = name + voter.getFM_NAME_V1();
            }
            if (voter.getLASTNAME_V1() != null) {
                name = name + " " + voter.getLASTNAME_V1();
            }
        }
        String age = this.resources.getString(R.string.age) + ":" + voter.getAge();
        String wardnum = this.resources.getString(R.string.ward_no) + ":" + voter.getWardNo();
        String votingdate = this.resources.getString(R.string.voting_date) + ":" + this.db.getDateFromSlNoinWard(this.matchslnoinward);
        String str = "Voterid:" + voter.getEPIC_NO();
        messageText.setText(name + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.gender) + ":" + voter.getGENDER()) + IOUtils.LINE_SEPARATOR_UNIX + age + IOUtils.LINE_SEPARATOR_UNIX + wardnum + IOUtils.LINE_SEPARATOR_UNIX + votingdate + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.panchayat_id) + "/" + this.resources.getString(R.string.ward_no) + "/" + this.resources.getString(R.string.booth_no_text) + ":" + voter.getPanchayatID() + "/" + voter.getWardNo() + "/" + voter.getBoothNo()));
        String finalmessage = this.resources.getString(R.string.u_cannot_vote_text);
        StringBuilder sb = new StringBuilder();
        sb.append(finalmessage);
        sb.append(IOUtils.LINE_SEPARATOR_UNIX);
        sb.append(this.resources.getString(R.string.fingerprintrecord_match_text));
        sb.append(voter.getEPIC_NO());
        sb.toString();
        messageVoterOnNot.setVisibility(0);
        messageVoterOnNot.setText(failedmessage);
        AlertDialog alertDialog = alertDialogBuilder.create();
        System.out.println("popup5");
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popupElse(String failedmessage, boolean allow) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        System.out.println("popup2");
        alertDialogBuilder.setTitle(this.resources.getString(R.string.aadhaar_authentication));
        if (allow) {
            alertDialogBuilder.setIcon(R.drawable.right_icon_trp);
        } else {
            alertDialogBuilder.setIcon(R.drawable.wrong_icon_trp);
        }
        System.out.println("popup4");
        alertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        View dialogView = getLayoutInflater().inflate(R.layout.alert_custom_layout, (ViewGroup) null);
        alertDialogBuilder.setView(dialogView);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) dialogView.findViewById(R.id.usermatchimage);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_1);
        imageView.setVisibility(0);
        if (allow) {
            imageView.setImageResource(R.drawable.right_icon_trp);
        } else {
            imageView.setImageResource(R.drawable.wrong_icon_trp);
        }
        TextView messageText = (TextView) dialogView.findViewById(R.id.match_message);
        messageText.setVisibility(8);
        TextView messageVoterOnNot = (TextView) dialogView.findViewById(R.id.message);
        messageText.setText("");
        this.resources.getString(R.string.u_cannot_vote_text);
        messageVoterOnNot.setVisibility(0);
        messageVoterOnNot.setText(failedmessage);
        AlertDialog alertDialog = alertDialogBuilder.create();
        System.out.println("popup5");
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popup(String message, int icon) {
        System.out.println("popup1");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        System.out.println("popup2");
        alertDialogBuilder.setTitle(this.resources.getString(R.string.aadhaar_authentication));
        alertDialogBuilder.setIcon(icon);
        System.out.println("popup3");
        alertDialogBuilder.setMessage(message).setCancelable(false);
        System.out.println("popup4");
        alertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        System.out.println("popup5");
        alertDialog.show();
        System.out.println("popup6");
        System.out.println("popup7");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAadhaarResult(String voterid, String aadhaarid, String aadhaarmatchstatus, String voted) {
        HashMap<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("aadhaarnum", aadhaarid);
        map.put("aadhaarmatchstatus", aadhaarmatchstatus);
        map.put("voted", voted);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postAadhaarMatchUpdate(map).enqueue(new Callback<AadhaarMatchUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.7
            @Override // retrofit2.Callback
            public void onResponse(Call<AadhaarMatchUpdatePostResponse> call, Response<AadhaarMatchUpdatePostResponse> response) {
                if (response.isSuccessful()) {
                    response.body().isAdded().booleanValue();
                    AadharActivity.this.startVoterList();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<AadhaarMatchUpdatePostResponse> call, Throwable t) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startVoterList() {
        startActivity(new Intent(this, ListUserActivity.class));
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        if (SystemClock.elapsedRealtime() - this.mLastAttTime >= Threshold) {
            this.mLastAttTime = SystemClock.elapsedRealtime();
            if (hasPermission) {
                if (vid == 1204 || vid == 11279) {
                    try {
                        if (pid == 34323) {
                            this.mfs100.LoadFirmware();
                        } else if (pid == 4101) {
                            this.mfs100.Init();
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
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnDeviceDetached() {
        try {
            if (SystemClock.elapsedRealtime() - this.mLastDttTime >= Threshold) {
                this.mLastDttTime = SystemClock.elapsedRealtime();
                UnInitScanner();
                updatedevicedetached();
            }
        } catch (Exception e) {
        }
    }

    private void UnInitScanner() {
        try {
            if (this.mfs100.UnInit() == 0) {
                this.lastCapFingerData = null;
            }
        } catch (Exception e) {
            Log.e("UnInitScanner.EX", e.toString());
        }
    }

    private void updatedevicedetached() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", new UserAuth(getApplicationContext()).getPhone());
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postFpDeviceStatusUpdate(map).enqueue(new Callback<BoothOfficerDeviceStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.8
            @Override // retrofit2.Callback
            public void onResponse(Call<BoothOfficerDeviceStatusUpdatePostResponse> call, Response<BoothOfficerDeviceStatusUpdatePostResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<BoothOfficerDeviceStatusUpdatePostResponse> call, Throwable t) {
            }
        });
    }

    @Override // com.mantra.mfs100.MFS100Event
    public void OnHostCheckFailed(String err) {
        try {
            Toast.makeText(getApplicationContext(), err, 1).show();
        } catch (Exception e) {
        }
    }

    private void StartSyncCapture() {
        new Thread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.9
            @Override // java.lang.Runnable
            public void run() {
                AadharActivity.this.isCaptureRunning = true;
                try {
                    final FingerData fingerData = new FingerData();
                    int ret = AadharActivity.this.mfs100.AutoCapture(fingerData, AadharActivity.this.timeout, AadharActivity.this.cbFastDetection.isChecked());
                    Log.e("StartSyncCapture.RET", "" + ret);
                    if (ret == 0) {
                        AadharActivity.this.lastCapFingerData = fingerData;
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);
                        AadharActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.9.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AadharActivity.this.imgFinger.setImageBitmap(bitmap);
                                AadharActivity.this.finger_template1 = new byte[fingerData.ISOTemplate().length];
                                System.arraycopy(fingerData.ISOTemplate(), 0, AadharActivity.this.finger_template1, 0, fingerData.ISOTemplate().length);
                                AadharActivity.this.startAadhaarCapture();
                            }
                        });
                        String str = "\nQuality: " + fingerData.Quality() + "\nNFIQ: " + fingerData.Nfiq() + "\nWSQ Compress Ratio: " + fingerData.WSQCompressRatio() + "\nImage Dimensions (inch): " + fingerData.InWidth() + "\" X " + fingerData.InHeight() + "\"\nImage Area (inch): " + fingerData.InArea() + "\"\nResolution (dpi/ppi): " + fingerData.Resolution() + "\nGray Scale: " + fingerData.GrayScale() + "\nBits Per Pixal: " + fingerData.Bpp() + "\nWSQ Info: " + fingerData.WSQInfo();
                        AadharActivity.this.SetData2(fingerData);
                    }
                } catch (Exception e) {
                } catch (Throwable th) {
                    AadharActivity.this.isCaptureRunning = false;
                    throw th;
                }
                AadharActivity.this.isCaptureRunning = false;
            }
        }).start();
    }

    public void SetData2(FingerData fingerData) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAadhaarCapture() {
        String AadhaarNo = this.aadhaarEditText.getText().toString();
        if (AadhaarNo == null || AadhaarNo.isEmpty() || AadhaarNo.length() != 12 || !onlyDigits(AadhaarNo, AadhaarNo.length())) {
            Toast.makeText(getApplicationContext(), "Please enter correct Aadhaar Number", 1).show();
            return;
        }
        this.UID = AadhaarNo;
        if (!this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
            if (this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                Intent intent2 = new Intent("in.gov.uidai.rdservice.fp.CAPTURE");
                intent2.putExtra("PID_OPTIONS", "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts> <Param name=\"ValidationKey\" value=\"\" /> </CustOpts> </PidOptions>");
                Log.v("Pawan ", "starting aadhaar capture");
                startActivityForResult(intent2, this.CAPTURE_FINGERPRINT_ACTIVITY);
            } else if (this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                try {
                    final ProgressDialog pdLoading = new ProgressDialog(this);
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.show();
                    RDService.discoverAndCapture(this, false, new RDService.OnRDServiceDiscoveryAndCapture() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.10
                        @Override // com.example.aadhaarfpoffline.tatvik.uid.RDService.OnRDServiceDiscoveryAndCapture
                        public void onSuccess(final String result) {
                            AadharActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.10.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    new Handler().postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.10.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (pdLoading != null && pdLoading.isShowing()) {
                                                pdLoading.dismiss();
                                            }
                                            Log.v("Pawan", String.format("pid xml is %s", result));
                                            AadharActivity.this.fpcaptureString = result;
                                            AadharActivity.this.captureResult.setText(result);
                                            AadharActivity.this.partresult2 = result;
                                            AadharActivity.this.capDoc = AadharActivity.convertStringToXMLDocument(result);
                                        }
                                    }, 3000);
                                }
                            });
                        }

                        @Override // com.example.aadhaarfpoffline.tatvik.uid.RDService.OnRDServiceDiscoveryAndCapture
                        public void onError(final VolleyError error) {
                            AadharActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.10.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    new Handler().postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.10.2.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (pdLoading != null && pdLoading.isShowing()) {
                                                pdLoading.dismiss();
                                            }
                                            String errorMsg = new String(error.networkResponse.data);
                                            Log.v("Pawan", "error is " + errorMsg);
                                        }
                                    }, 3000);
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    Log.e("exceptiontry", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private void findrdservice() {
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(new Intent("in.gov.uidai.rdservice.fp.INFO"), 0);
        for (int i = 0; i < resolveInfoList.size(); i++) {
            try {
                Log.e("Packages", resolveInfoList.get(i).activityInfo.packageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void finddeviceinformation() {
        Intent intent = new Intent();
        intent.setAction("in.gov.uidai.rdservice.fp.INFO");
        startActivityForResult(intent, this.DEVICE_INFORMATION_CODE);
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
            if (this.mfs100.Init() == 0) {
                String str = "Serial: " + this.mfs100.GetDeviceInfo().SerialNo() + " Make: " + this.mfs100.GetDeviceInfo().Make() + " Model: " + this.mfs100.GetDeviceInfo().Model() + "\nCertificate: " + this.mfs100.GetCertification();
            }
        } catch (Exception e) {
        }
    }

    private void StopCapture() {
        try {
            this.mfs100.StopAutoCapture();
        } catch (Exception e) {
        }
    }

    public void FindFormControls() {
        this.progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        this.progressBar.setVisibility(8);
        this.button_ok = (Button) findViewById(R.id.ok_button);
        this.captureFingerprint = (Button) findViewById(R.id.button2);
        this.button_authenticate = (Button) findViewById(R.id.button3);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button_validate = (Button) findViewById(R.id.button_validate);
        this.cbFastDetection = (CheckBox) findViewById(R.id.cbFastDetection);
        this.imgFinger = (ImageView) findViewById(R.id.imgFinger);
        this.captureFingerprint.setEnabled(false);
        this.button_authenticate.setEnabled(false);
        this.button_authenticate.setText(this.resources.getString(R.string.aadhaar_button_authenticate));
        this.captureFingerprint.setText(this.resources.getString(R.string.aadhaar_button_capture));
        this.button_validate.setText(this.resources.getString(R.string.aadhaar_button_validate));
        this.captureResult = (TextView) findViewById(R.id.id_captured_result);
        this.xmlToPost = (TextView) findViewById(R.id.id_xml_to_post);
        this.xmlResponse = (TextView) findViewById(R.id.id_xml_response);
        this.aadhaarEditText = (EditText) findViewById(R.id.id_aadhaar);
        this.button_ok.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AadharActivity.this.authenticatePartDone.booleanValue()) {
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.voted = "0";
                    if (aadharActivity.votertoballowed.booleanValue()) {
                        AadharActivity aadharActivity2 = AadharActivity.this;
                        aadharActivity2.voted = DiskLruCache.VERSION_1;
                        if (!aadharActivity2.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                            if (AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                                AadharActivity aadharActivity3 = AadharActivity.this;
                                aadharActivity3.updatefingerprintdb(aadharActivity3.captRslt1.getFmrBytes());
                            } else if (AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                                AadharActivity aadharActivity4 = AadharActivity.this;
                                aadharActivity4.updatefingerprintdb(aadharActivity4.byTemplate1);
                            }
                        }
                    } else {
                        AadharActivity.this.voted = ExifInterface.GPS_MEASUREMENT_2D;
                    }
                    AadharActivity.this.db.updateVOTEDByUSER_ID_Maintable(AadharActivity.this.userAuth.getPanchayatId() + "_" + AadharActivity.this.userAuth.getWardNo() + "_" + AadharActivity.this.userAuth.getBoothNo() + "_" + AadharActivity.this.slnoinward, Integer.parseInt(AadharActivity.this.voted));
                    AadharActivity.this.db.updateAadhaarResultTransTable(AadharActivity.this.voterid, AadharActivity.this.UID, AadharActivity.this.aadhaarmatch, AadharActivity.this.voted, AadharActivity.this.getCurrentTimeInFormat(), AadharActivity.this.userAuth.getTransactionId().longValue());
                    AadharActivity.this.uploadTransactionRow();
                    if (!AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra) && !AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                        AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan);
                        return;
                    }
                    return;
                }
                Toast.makeText(AadharActivity.this.getApplicationContext(), "First Authenticate then press 'Ok'", 1).show();
            }
        });
        this.captureFingerprint.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                    if (AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                        AadharActivity.this.captureFingerprintTatvik();
                    } else if (AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                        AadharActivity.this.initData();
                    }
                }
            }
        });
        this.button_authenticate.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - AadharActivity.mLastClkTime >= AadharActivity.Threshold) {
                    long unused = AadharActivity.mLastClkTime = SystemClock.elapsedRealtime();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "AUTHENTICATE CLICKED", 1).show();
                    String voter_id = "";
                    if (!AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Mantra)) {
                        if (AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.Tatvik)) {
                            AadharActivity aadharActivity = AadharActivity.this;
                            voter_id = aadharActivity.CompareFingerprintTatvikTransTable(aadharActivity.voterid, AadharActivity.this.captRslt1.getFmrBytes());
                        } else if (AadharActivity.this.userAuth.getFingerPrintDevice().equals(Const.eNBioScan)) {
                            AadharActivity aadharActivity2 = AadharActivity.this;
                            voter_id = aadharActivity2.compareAndLockNetgin(aadharActivity2.voterid, AadharActivity.this.byTemplate1);
                        }
                    }
                    if (voter_id != null && !voter_id.isEmpty() && voter_id.length() > 0) {
                        AadharActivity.this.beep();
                        Toast.makeText(AadharActivity.this.getApplicationContext(), "Fingerprint exists in local dataa\base", 1).show();
                        AadharActivity.this.aadhaarmatch = DiskLruCache.VERSION_1;
                        AadharActivity.this.votertoballowed = false;
                        AadharActivity.this.authenticatePartDone = true;
                        AadharActivity aadharActivity3 = AadharActivity.this;
                        aadharActivity3.getMatchedVoterData(AadharActivity.this.userAuth.getPanchayatId() + "_" + AadharActivity.this.userAuth.getWardNo() + "_" + AadharActivity.this.userAuth.getBoothNo() + "_" + AadharActivity.this.slnoinward, aadharActivity3.matchslnoinward);
                    } else if (AadharActivity.this.fpcaptureString.length() > 200) {
                        Log.d("finalresultra", AadharActivity.this.partresult1 + " ###result4=" + AadharActivity.this.partresult2);
                        AadharActivity aadharActivity4 = AadharActivity.this;
                        aadharActivity4.capDoc = aadharActivity4.changeTagName(aadharActivity4.capDoc, "", "DeviceInfo", "Meta");
                        AadharActivity aadharActivity5 = AadharActivity.this;
                        aadharActivity5.printXML(aadharActivity5.capDoc, "metaadded");
                        AadharActivity aadharActivity6 = AadharActivity.this;
                        aadharActivity6.createStringXml(aadharActivity6.UID, AadharActivity.this.capDoc);
                    } else {
                        Toast.makeText(AadharActivity.this.getApplicationContext(), "Fingerprint not captured properly.Capture again then Authenticate", 1).show();
                    }
                }
            }
        });
        this.button_validate.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String AadhaarNo = AadharActivity.this.aadhaarEditText.getText().toString();
                if (AadhaarNo == null || AadhaarNo.isEmpty() || AadhaarNo.length() != 12 || !AadharActivity.onlyDigits(AadhaarNo, AadhaarNo.length())) {
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "Please enter correct Aadhaar Number", 1).show();
                } else {
                    AadharActivity.this.validateAadhaarUser(AadhaarNo);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatefingerprintdb(byte[] finger_template) {
        this.db.updateFingerprintTemplateTransTable(this.voterid, finger_template, this.userAuth.getTransactionId().longValue());
    }

    private String CompareFingerprint(String voterid, byte[] fingerprint2) {
        Cursor cursor = this.db.fpcompare(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        Toast.makeText(getApplicationContext(), "CompareFingerprint2", 1).show();
        do {
            byte[] fingerprint1 = cursor.getBlob(cursor.getColumnIndex("EnrollTemplate"));
            numrows++;
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "CompareFingerprint3 numrows=" + numrows, 1).show();
            int ret = this.mfs100.MatchISO(fingerprint1, fingerprint2);
            Context applicationContext2 = getApplicationContext();
            Toast.makeText(applicationContext2, "CompareFingerprint3 retvalue=" + ret, 1).show();
            if (ret > 100) {
                String matchvoterid = cursor.getString(cursor.getColumnIndex("EPIC_NO"));
                Context applicationContext3 = getApplicationContext();
                Toast.makeText(applicationContext3, "Match voterid" + matchvoterid, 1).show();
                return matchvoterid;
            }
        } while (cursor.moveToNext());
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beep() {
        new ToneGenerator(4, 500).startTone(93, 600);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMatchedVoterData(String matchedvotertid, final String slnoinward) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dist_no", matchedvotertid);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByUserId(map).enqueue(new Callback<VoterDataGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.15
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterDataGetResponse> call, Response<VoterDataGetResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getVoters() != null) {
                    VoterDataNewModel Voter = response.body().getVoters();
                    AadharActivity.this.resources.getString(R.string.aadhaar_authentication_failure);
                    AadharActivity.this.popupFailed(AadharActivity.this.resources.getString(R.string.you_cannot_vote_only) + "," + AadharActivity.this.resources.getString(R.string.aadhaar_not_vote), Voter);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterDataGetResponse> call, Throwable t) {
                if (!(t instanceof SocketTimeoutException)) {
                    boolean z = t instanceof IOException;
                }
                VoterDataNewModel Voter = AadharActivity.this.db.getVoterBySlNoInWard(slnoinward);
                AadharActivity.this.popupFailedOffline(AadharActivity.this.resources.getString(R.string.you_cannot_vote_only) + "," + AadharActivity.this.resources.getString(R.string.aadhaar_not_vote), Voter);
            }
        });
    }

    public void updateFingerprintToServer(final String voterid, byte[] fingerprinttemplate, final String voted) {
        PrintStream printStream = System.out;
        printStream.println("fingerprinttemplate=" + fingerprinttemplate.toString());
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("voterid", createPartFromString(voterid));
        map.put("fpbytearray", createPartFromByteArray(fingerprinttemplate));
        map.put("udevid", createPartFromString(this.UDevId));
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postCompareFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.16
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    PrintStream printStream2 = System.out;
                    printStream2.println("fingerprinttemplate2=" + response.body().getMatchedvoterid());
                    Context applicationContext = AadharActivity.this.getApplicationContext();
                    Toast.makeText(applicationContext, "matchedvoterid=" + response.body().getMatchedvoterid(), 1).show();
                    AadharActivity.this.db.updateVOTEDByUSER_ID_Maintable(AadharActivity.this.userAuth.getPanchayatId() + "_" + AadharActivity.this.userAuth.getWardNo() + "_" + AadharActivity.this.userAuth.getBoothNo() + "_" + AadharActivity.this.slnoinward, Integer.parseInt(voted));
                    AadharActivity.this.db.updateAadhaarResultTransTable(voterid, AadharActivity.this.UID, AadharActivity.this.aadhaarmatch, voted, AadharActivity.this.getCurrentTimeInFormat(), AadharActivity.this.userAuth.getTransactionId().longValue());
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.updateAadhaarResult(voterid, aadharActivity.UID, AadharActivity.this.aadhaarmatch, voted);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
                Context applicationContext = AadharActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "matchedvoterid= Error=" + t.getMessage(), 1).show();
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    AadharActivity.this.db.updateVOTEDByUSER_ID_Maintable(AadharActivity.this.userAuth.getPanchayatId() + "_" + AadharActivity.this.userAuth.getWardNo() + "_" + AadharActivity.this.userAuth.getBoothNo() + "_" + AadharActivity.this.slnoinward, Integer.parseInt(voted));
                    AadharActivity.this.db.updateAadhaarResultTransTable(voterid, AadharActivity.this.UID, AadharActivity.this.aadhaarmatch, voted, AadharActivity.this.getCurrentTimeInFormat(), AadharActivity.this.userAuth.getTransactionId().longValue());
                }
            }
        });
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    private RequestBody createPartFromByteArray(byte[] descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    private void deleteFingerprint(String voterid) {
        this.db.clearFingerprint(voterid);
        this.db.clearFingerprintTransTable(this.userAuth.getTransactionId().longValue());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        deleteFingerprint(this.voterid);
        super.onBackPressed();
    }

    @Override // java.util.Observer
    public void update(Observable o, Object arg) {
    }

    private String CompareFingerprintTatvik(String voterid, byte[] fingerprint2) {
        Toast.makeText(getApplicationContext(), "CompareFingerprint", 1).show();
        Cursor cursor = this.db.fpcompare(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        Toast.makeText(getApplicationContext(), "CompareFingerprint2", 1).show();
        do {
            byte[] fingerprint1 = cursor.getBlob(cursor.getColumnIndex("EnrollTemplate"));
            numrows++;
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "CompareFingerprint3 numrows=" + numrows, 1).show();
            if (this.tmf20lib.matchIsoTemplates(fingerprint1, fingerprint2)) {
                String matchvoterid = cursor.getString(cursor.getColumnIndex("EPIC_NO"));
                Context applicationContext2 = getApplicationContext();
                Toast.makeText(applicationContext2, "Match voterid" + matchvoterid, 1).show();
                return matchvoterid;
            }
        } while (cursor.moveToNext());
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String CompareFingerprintTatvikTransTable(String voterid, byte[] fingerprint2) {
        Cursor cursor = this.db.fpcompareTransTable(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        do {
            numrows++;
            if (this.tmf20lib.matchIsoTemplates(cursor.getBlob(cursor.getColumnIndex("FingerTemplate")), fingerprint2)) {
                String matchvoterid = cursor.getString(cursor.getColumnIndex("EPIC_NO"));
                this.matchslnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                Context applicationContext = getApplicationContext();
                Toast.makeText(applicationContext, "Match voterid" + matchvoterid, 1).show();
                return this.matchslnoinward;
            }
        } while (cursor.moveToNext());
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void captureFingerprintTatvik() {
        this.captRslt1 = this.tmf20lib.captureFingerprint(10000);
        if (this.captRslt1 == null || TMF20ErrorCodes.SUCCESS != this.captRslt1.getStatusCode()) {
            this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.wrong_icon_trp));
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "Capture Failed" + this.captRslt1.getErrorString(), 1).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Capture successful", 1).show();
        this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.right_icon_trp));
        startAadhaarCapture();
    }

    private void updatefingerprintonlineasstring(final String voter_id, byte[] finger_template, final String voted) {
        Toast.makeText(getApplicationContext(), "updatefingerprint server 0", 1).show();
        String fp_string = Base64.encodeToString(finger_template, 0);
        Map<String, String> map = new HashMap<>();
        map.put("voterid", voter_id);
        map.put("fp", fp_string);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.17
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                AadharActivity.this.db.updateVOTEDByUSER_ID_Maintable(AadharActivity.this.userAuth.getPanchayatId() + "_" + AadharActivity.this.userAuth.getWardNo() + "_" + AadharActivity.this.userAuth.getBoothNo() + "_" + AadharActivity.this.slnoinward, Integer.parseInt(voted));
                AadharActivity.this.db.updateAadhaarResultTransTable(voter_id, AadharActivity.this.UID, AadharActivity.this.aadhaarmatch, voted, AadharActivity.this.getCurrentTimeInFormat(), AadharActivity.this.userAuth.getTransactionId().longValue());
                AadharActivity aadharActivity = AadharActivity.this;
                aadharActivity.updateAadhaarResult(voter_id, aadharActivity.UID, AadharActivity.this.aadhaarmatch, voted);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    AadharActivity.this.db.updateVOTEDByUSER_ID_Maintable(AadharActivity.this.userAuth.getPanchayatId() + "_" + AadharActivity.this.userAuth.getWardNo() + "_" + AadharActivity.this.userAuth.getBoothNo() + "_" + AadharActivity.this.slnoinward, Integer.parseInt(voted));
                    AadharActivity.this.db.updateAadhaarResultTransTable(voter_id, AadharActivity.this.UID, AadharActivity.this.aadhaarmatch, voted, AadharActivity.this.getCurrentTimeInFormat(), AadharActivity.this.userAuth.getTransactionId().longValue());
                }
            }
        });
    }

    public void initData() {
        NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
        if (this.bsp == null) {
            this.bsp = new NBioBSPJNI("010701-613E5C7F4CC7C4B0-72E340B47E034015", this, this.mCallback);
            if (this.bsp.IsErrorOccured()) {
                Toast.makeText(getApplicationContext(), "NBioBSP Error: " + this.bsp.GetErrorCode(), 1).show();
                return;
            }
            NBioBSPJNI nBioBSPJNI = this.bsp;
            Objects.requireNonNull(nBioBSPJNI);
            this.exportEngine = new NBioBSPJNI.Export();
            NBioBSPJNI nBioBSPJNI2 = this.bsp;
            Objects.requireNonNull(nBioBSPJNI2);
            this.indexSearch = new NBioBSPJNI.IndexSearch();
            new AsyncCaller().execute(new Void[0]);
            return;
        }
        new AsyncCaller().execute(new Void[0]);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        NBioBSPJNI nBioBSPJNI = this.bsp;
        if (nBioBSPJNI != null) {
            nBioBSPJNI.dispose();
            this.bsp = null;
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class AsyncCaller extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pdLoading;

        private AsyncCaller() {
            this.pdLoading = null;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            this.pdLoading = new ProgressDialog(AadharActivity.this);
            this.pdLoading.setMessage("\tLoading...");
            this.pdLoading.show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            if (AadharActivity.this.bspConnected) {
                Log.v("Pawan", "device already connected");
                return true;
            }
            AadharActivity.this.countDownLatch = new CountDownLatch(1);
            Boolean isConnected = Boolean.valueOf(AadharActivity.this.bsp.OpenDevice());
            try {
                AadharActivity.this.countDownLatch.await();
                Log.v("Pawan", "OpenDevice call finsihed");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isConnected;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute((AsyncCaller) result);
            ProgressDialog progressDialog = this.pdLoading;
            if (progressDialog != null && progressDialog.isShowing()) {
                this.pdLoading.dismiss();
            }
            if (AadharActivity.this.bspConnected) {
                AadharActivity.this.captureFingerPrintFromNitgen();
            } else {
                Toast.makeText(AadharActivity.this, Const.nBioDeviceError, 0).show();
            }
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.19
                @Override // java.lang.Runnable
                public void run() {
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.msg = "NBioBSP ExportFIR Error: " + AadharActivity.this.bsp.GetErrorCode();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), AadharActivity.this.msg, 0).show();
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.20
                @Override // java.lang.Runnable
                public void run() {
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.msg = "NBioBSP ExportAudit Error: " + AadharActivity.this.bsp.GetErrorCode();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), AadharActivity.this.msg, 0).show();
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.21
                @Override // java.lang.Runnable
                public void run() {
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.msg = "NBioBSP getNFIQInfoFromRaw Error: " + AadharActivity.this.bsp.GetErrorCode();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), AadharActivity.this.msg, 0).show();
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
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.22
                @Override // java.lang.Runnable
                public void run() {
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.msg = "NBioBSP ExportRawToISOV1 Error: " + AadharActivity.this.bsp.GetErrorCode();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), AadharActivity.this.msg, 0).show();
                }
            });
            return;
        }
        NBioBSPJNI nBioBSPJNI7 = this.bsp;
        Objects.requireNonNull(nBioBSPJNI7);
        NBioBSPJNI.NIMPORTRAWSET rawSet = new NBioBSPJNI.NIMPORTRAWSET();
        this.bsp.ImportISOToRaw(ISOBuffer, rawSet);
        if (this.bsp.IsErrorOccured()) {
            runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.23
                @Override // java.lang.Runnable
                public void run() {
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.msg = "NBioBSP ImportISOToRaw Error: " + AadharActivity.this.bsp.GetErrorCode();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), AadharActivity.this.msg, 0).show();
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
        runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.24
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(AadharActivity.this.getApplicationContext(), AadharActivity.this.msg, 0).show();
            }
        });
        if (this.byTemplate1 != null) {
            closeDevice();
            final ProgressDialog pdLoading = new ProgressDialog(this);
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
            new Handler().postDelayed(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.25
                @Override // java.lang.Runnable
                public void run() {
                    ProgressDialog progressDialog = pdLoading;
                    if (progressDialog != null && progressDialog.isShowing()) {
                        pdLoading.dismiss();
                    }
                    AadharActivity.this.startAadhaarCapture();
                }
            }, 2000);
        }
    }

    private void closeDevice() {
        NBioBSPJNI nBioBSPJNI = this.bsp;
        if (nBioBSPJNI != null) {
            nBioBSPJNI.dispose();
            this.bsp = null;
            this.bspConnected = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String compareAndLockNetgin(String voterid, byte[] finger_template1) {
        Cursor cursor = this.db.fpcompareTransTable(voterid);
        int numrows = 0;
        if (!cursor.moveToFirst()) {
            return "";
        }
        do {
            byte[] fingerprint1 = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
            numrows++;
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
                    cursor.getString(cursor.getColumnIndex("EPIC_NO"));
                    this.matchslnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
                    return this.matchslnoinward;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadTransactionRow() {
        new HashMap();
        Map<String, String> map = getTransactionRowData();
        final String transId = map.get("TRANSID");
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateTransactionRow(map).enqueue(new Callback<TransactionRowPostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.26
            @Override // retrofit2.Callback
            public void onResponse(Call<TransactionRowPostResponse> call, Response<TransactionRowPostResponse> response) {
                if (!(response == null || response.body() == null || !response.body().getUpdated())) {
                    AadharActivity.this.db.updateSync(Integer.parseInt(transId), 1);
                }
                AadharActivity.this.startVoterList();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<TransactionRowPostResponse> call, Throwable t) {
                AadharActivity.this.startVoterList();
            }
        });
    }

    /* JADX WARN: Can't wrap try/catch for region: R(6:(4:8|48|9|(9:11|18|19|20|46|21|40|22|24))(1:16)|46|21|40|22|24) */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private Map<String, String> getTransactionRowData() {
        Map<String, String> map;
        Cursor cursor;
        Cursor cursor2;
        Throwable th;
        String fpString;
        try {
            cursor = this.db.SingleTransactionRow(this.userAuth.getTransactionId().longValue());
            map = new HashMap<>();
            try {
            } catch (Exception e) {
                cursor2 = cursor;
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursor;
            }
        } catch (Exception e2) {
        }
        if (cursor.moveToFirst()) {
            new VoterDataNewModel().setId(cursor.getString(0));
            byte[] fp = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
            String voterimagename = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
            int aadhaarmatch = cursor.getInt(cursor.getColumnIndex("AADHAAR_MATCH"));
            int age = cursor.getInt(cursor.getColumnIndex("AGE"));
            String gender = cursor.getString(cursor.getColumnIndex("GENDER"));
            String str = this.UID;
            try {
                if (fp != null) {
                    try {
                        if (fp.length > 0) {
                            fpString = Base64.encodeToString(fp, 0);
                            StringBuilder sb = new StringBuilder();
                            sb.append("");
                            cursor2 = cursor;
                            sb.append(this.userAuth.getTransactionId());
                            map.put("TRANSID", sb.toString());
                            map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward);
                            map.put("FINGERPRINT_TEMPLATE", fpString);
                            map.put("VOTED", "" + this.voted);
                            map.put("ID_DOCUMENT_IMAGE", voterimagename);
                            map.put("AADHAAR_MATCH", "" + aadhaarmatch);
                            map.put("AADHAAR_NO", this.UID);
                            map.put("VOTING_DATE", getCurrentTimeInFormat());
                            map.put("VOTING_TYPE", "AADHAAR");
                            map.put("booth_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo());
                            map.put("GENDER", gender);
                            map.put("AGE", "" + age);
                            map.put("udevid", this.UDevId);
                            cursor2.close();
                            return map;
                        }
                    } catch (Exception e3) {
                        cursor2 = cursor;
                        cursor2.close();
                        return map;
                    } catch (Throwable th3) {
                        th = th3;
                        cursor2 = cursor;
                        try {
                            cursor2.close();
                        } catch (Exception e4) {
                        }
                        throw th;
                    }
                }
                sb.append(this.userAuth.getTransactionId());
                map.put("TRANSID", sb.toString());
                map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward);
                map.put("FINGERPRINT_TEMPLATE", fpString);
                map.put("VOTED", "" + this.voted);
                map.put("ID_DOCUMENT_IMAGE", voterimagename);
                map.put("AADHAAR_MATCH", "" + aadhaarmatch);
                map.put("AADHAAR_NO", this.UID);
                map.put("VOTING_DATE", getCurrentTimeInFormat());
                map.put("VOTING_TYPE", "AADHAAR");
                map.put("booth_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo());
                map.put("GENDER", gender);
                map.put("AGE", "" + age);
                map.put("udevid", this.UDevId);
                cursor2.close();
                return map;
            } catch (Exception e5) {
                cursor2.close();
                return map;
            } catch (Throwable th4) {
                th = th4;
                cursor2.close();
                throw th;
            }
            fpString = "";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            cursor2 = cursor;
        } else {
            cursor.close();
            return map;
        }
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }

    public static void deleteCache(Context context) {
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (String str : dir.list()) {
                if (!deleteDir(new File(dir, str))) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir == null || !dir.isFile()) {
            return false;
        } else {
            return dir.delete();
        }
    }
}
