package com.example.aadhaarfpoffline.tatvik.activity;

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
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.aadhaarfpoffline.tatvik.network.VoterDataGetResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
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
    public static final String MULTIPART_FORM_DATA;
    private static long Threshold = 1500;
    EditText aadhaarEditText;
    Button button4;
    private Button button_authenticate;
    private Button button_ok;
    Button button_validate;
    Document capDoc;
    CaptureResult captRslt1;
    private Button captureFingerprint;
    private TextView captureResult;
    Document capturedDoc;
    CheckBox cbFastDetection;
    Context context;
    DBHelper db;
    byte[] finger_template1;
    ImageView imgFinger;
    String lan;
    Document metaDoc;
    ProgressBar progressBar;
    Resources resources;
    TMF20API tmf20lib;
    private TextView xmlResponse;
    private TextView xmlToPost;
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
    private long mLastAttTime = 0;
    long mLastDttTime = 0;

    /* loaded from: classes2.dex */
    private enum ScannerAction {
        Capture,
        Verify
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar);
        this.lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, this.lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        getSupportActionBar().setTitle(this.resources.getString(R.string.aadhaar_activity));
        this.tmf20lib = new TMF20API(this);
        this.voterid = getIntent().getStringExtra("voter_id");
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

    public void validateAadhaarUser(String aadhaarnum) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByVoterAadhaarNum(aadhaarnum).enqueue(new Callback<AadhaarUserCheckGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.1
            @Override // retrofit2.Callback
            public void onResponse(Call<AadhaarUserCheckGetResponse> call, Response<AadhaarUserCheckGetResponse> response) {
                if (response == null || !response.isSuccessful() || response.body() == null) {
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "Error getting response.", 0).show();
                } else if (response.body().getAadhaaruserexists().booleanValue()) {
                    AadharActivity.this.captureFingerprint.setEnabled(false);
                    AadharActivity.this.captureFingerprint.setVisibility(8);
                    AadharActivity.this.button_authenticate.setEnabled(false);
                    AadharActivity.this.button_authenticate.setVisibility(8);
                    AadharActivity.this.button_ok.setEnabled(false);
                    AadharActivity.this.button_ok.setVisibility(8);
                    response.body().getVoter().getEPIC_NO();
                    AadharActivity.this.resources.getString(R.string.aadhaar_exist);
                    AadharActivity.this.popupFailed(AadharActivity.this.resources.getString(R.string.you_cannot_vote_only) + "," + AadharActivity.this.resources.getString(R.string.aadhaar_not_vote), response.body().getVoter());
                } else {
                    AadharActivity.this.captureFingerprint.setVisibility(0);
                    AadharActivity.this.captureFingerprint.setEnabled(true);
                    AadharActivity.this.button_authenticate.setEnabled(true);
                    AadharActivity.this.button_authenticate.setVisibility(0);
                    AadharActivity.this.button_ok.setEnabled(true);
                    AadharActivity.this.button_ok.setVisibility(0);
                    AadharActivity.this.popup(AadharActivity.this.resources.getString(R.string.aadhaar_doesnt_exist), R.drawable.right_icon_trp);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<AadhaarUserCheckGetResponse> call, Throwable t) {
                Toast.makeText(AadharActivity.this.getApplicationContext(), "Failure", 0).show();
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public String retVal(Document doc) {
        String retvalue = doc.getElementsByTagName("AuthRes").item(0).getAttributes().getNamedItem("ret").getNodeValue();
        PrintStream printStream = System.out;
        printStream.println("retvalue=" + retvalue);
        return retvalue;
    }

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
            if (voter.getLASTNAME_EN() != null) {
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
        alertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.4
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

    public void popup(String message, int icon) {
        System.out.println("popup1");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        System.out.println("popup2");
        alertDialogBuilder.setTitle(this.resources.getString(R.string.aadhaar_authentication));
        alertDialogBuilder.setIcon(icon);
        System.out.println("popup3");
        alertDialogBuilder.setMessage(message).setCancelable(false);
        System.out.println("popup4");
        alertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.5
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

    public void updateAadhaarResult(String voterid, String aadhaarid, String aadhaarmatchstatus, String voted) {
        HashMap<String, String> map = new HashMap<>();
        map.put("voterid", voterid);
        map.put("aadhaarnum", aadhaarid);
        map.put("aadhaarmatchstatus", aadhaarmatchstatus);
        map.put("voted", voted);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postAadhaarMatchUpdate(map).enqueue(new Callback<AadhaarMatchUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.6
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
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postFpDeviceStatusUpdate(map).enqueue(new Callback<BoothOfficerDeviceStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.7
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
        new Thread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.8
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
                        AadharActivity.this.runOnUiThread(new Runnable() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.8.1
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

    public void startAadhaarCapture() {
        String AadhaarNo = this.aadhaarEditText.getText().toString();
        if (AadhaarNo == null || AadhaarNo.isEmpty() || AadhaarNo.length() != 12 || !onlyDigits(AadhaarNo, AadhaarNo.length())) {
            Toast.makeText(getApplicationContext(), "Please enter correct Aadhaar Number", 1).show();
            return;
        }
        this.UID = AadhaarNo;
        Intent intent2 = new Intent("in.gov.uidai.rdservice.fp.CAPTURE");
        intent2.putExtra("PID_OPTIONS", "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts> <Param name=\"ValidationKey\" value=\"\" /> </CustOpts> </PidOptions>");
        Log.v("Pawan ", "starting aadhaar capture");
        startActivityForResult(intent2, this.CAPTURE_FINGERPRINT_ACTIVITY);
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
        this.button_ok.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String voted;
                Toast.makeText(AadharActivity.this.getApplicationContext(), "ok pressed= before", 1).show();
                if (AadharActivity.this.authenticatePartDone.booleanValue()) {
                    if (AadharActivity.this.votertoballowed.booleanValue()) {
                        voted = DiskLruCache.VERSION_1;
                        AadharActivity aadharActivity = AadharActivity.this;
                        aadharActivity.updatefingerprintdb(aadharActivity.captRslt1.getFmrBytes());
                    } else {
                        voted = ExifInterface.GPS_MEASUREMENT_2D;
                    }
                    AadharActivity aadharActivity2 = AadharActivity.this;
                    aadharActivity2.updateFingerprintToServer(aadharActivity2.voterid, AadharActivity.this.captRslt1.getFmrBytes(), voted);
                    return;
                }
                Toast.makeText(AadharActivity.this.getApplicationContext(), "First Authenticate then press 'Ok'", 1).show();
            }
        });
        this.captureFingerprint.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AadharActivity.this.captureFingerprintTatvik();
            }
        });
        this.button_authenticate.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Toast.makeText(AadharActivity.this.getApplicationContext(), "AUTHENTICATE CLICKED", 1).show();
                AadharActivity aadharActivity = AadharActivity.this;
                String voter_id = aadharActivity.CompareFingerprintTatvik(aadharActivity.voterid, AadharActivity.this.captRslt1.getFmrBytes());
                if (voter_id != null && !voter_id.isEmpty() && voter_id.length() > 0) {
                    AadharActivity.this.beep();
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "Fingerprint exists in local dataa\base", 1).show();
                    AadharActivity.this.aadhaarmatch = DiskLruCache.VERSION_1;
                    AadharActivity.this.votertoballowed = false;
                    AadharActivity.this.authenticatePartDone = true;
                    AadharActivity.this.getMatchedVoterData(voter_id);
                } else if (AadharActivity.this.fpcaptureString.length() > 200) {
                    Log.d("finalresultra", AadharActivity.this.partresult1 + " ###result4=" + AadharActivity.this.partresult2);
                    AadharActivity aadharActivity2 = AadharActivity.this;
                    aadharActivity2.capDoc = aadharActivity2.changeTagName(aadharActivity2.capDoc, "", "DeviceInfo", "Meta");
                    AadharActivity aadharActivity3 = AadharActivity.this;
                    aadharActivity3.printXML(aadharActivity3.capDoc, "metaadded");
                    AadharActivity aadharActivity4 = AadharActivity.this;
                    aadharActivity4.createStringXml(aadharActivity4.UID, AadharActivity.this.capDoc);
                } else {
                    Toast.makeText(AadharActivity.this.getApplicationContext(), "Fingerprint not captured properly.Capture again then Authenticate", 1).show();
                }
            }
        });
        this.button_validate.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.12
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

    public void updatefingerprintdb(byte[] finger_template) {
        Toast.makeText(getApplicationContext(), "updatefingerprintdb", 1).show();
        this.db.updateFingerprintTemplate(this.voterid, finger_template);
    }

    private String CompareFingerprint(String voterid, byte[] fingerprint2) {
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

    public void beep() {
        Toast.makeText(getApplicationContext(), "Beep", 1).show();
        new ToneGenerator(4, 500).startTone(93, 600);
    }

    public void getMatchedVoterData(String matchedvotertid) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByVoterId(matchedvotertid).enqueue(new Callback<VoterDataGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.13
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
            }
        });
    }

    public void updateFingerprintToServer(final String voterid, byte[] fingerprinttemplate, final String voted) {
        PrintStream printStream = System.out;
        printStream.println("fingerprinttemplate=" + fingerprinttemplate.toString());
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("voterid", createPartFromString(voterid));
        map.put("fpbytearray", createPartFromByteArray(fingerprinttemplate));
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postCompareFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.14
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    PrintStream printStream2 = System.out;
                    printStream2.println("fingerprinttemplate2=" + response.body().getMatchedvoterid());
                    Context applicationContext = AadharActivity.this.getApplicationContext();
                    Toast.makeText(applicationContext, "matchedvoterid=" + response.body().getMatchedvoterid(), 1).show();
                    AadharActivity aadharActivity = AadharActivity.this;
                    aadharActivity.updateAadhaarResult(voterid, aadharActivity.UID, AadharActivity.this.aadhaarmatch, voted);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
                Context applicationContext = AadharActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "matchedvoterid= Error=" + t.getMessage(), 1).show();
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
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        deleteFingerprint(this.voterid);
        super.onBackPressed();
    }

    @Override // java.util.Observer
    public void update(Observable o, Object arg) {
    }

    public String CompareFingerprintTatvik(String voterid, byte[] fingerprint2) {
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

    public void captureFingerprintTatvik() {
        this.captRslt1 = this.tmf20lib.captureFingerprint(10000);
        if (this.captRslt1 == null || TMF20ErrorCodes.SUCCESS != this.captRslt1.getStatusCode()) {
            this.imgFinger.setImageDrawable(getResources().getDrawable(R.drawable.wrong_icon_trp));
            Toast.makeText(getApplicationContext(), "Capture Failed", 1).show();
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
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateFpServer2(map).enqueue(new Callback<FinperprintCompareServerResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.AadharActivity.15
            @Override // retrofit2.Callback
            public void onResponse(Call<FinperprintCompareServerResponse> call, Response<FinperprintCompareServerResponse> response) {
                AadharActivity aadharActivity = AadharActivity.this;
                aadharActivity.updateAadhaarResult(voter_id, aadharActivity.UID, AadharActivity.this.aadhaarmatch, voted);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FinperprintCompareServerResponse> call, Throwable t) {
            }
        });
    }
}
