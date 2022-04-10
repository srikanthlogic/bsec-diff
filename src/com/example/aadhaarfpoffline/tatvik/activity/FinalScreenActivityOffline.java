package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.FacefpmatchvoteridUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.TransactionRowPostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserVotingStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterDataGetResponse;
import com.example.aadhaarfpoffline.tatvik.util.Const;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.net.HttpHeaders;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import okhttp3.internal.cache.DiskLruCache;
import org.apache.commons.io.IOUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class FinalScreenActivityOffline extends AppCompatActivity {
    private Button ButtonList;
    private Button ButtonVisible;
    Context context;
    DBHelper db;
    private ImageView image;
    private ImageView matchImage;
    private TextView matchMessageText;
    private SimpleDraweeView matchUserImage;
    private TextView messageText;
    Resources resources;
    private UserAuth userAuth;
    private static long mLastClkTime = 0;
    private static long Threshold = 10000;
    private String voterid = "";
    String facematchvoterid = "";
    String fpmatchovertid = "";
    String votedDone = "0";
    String lan = "";
    int voted = 0;
    String slnoinward = "";
    String matchslnoinward = "";
    String androidId = "";
    String UDevId = "";
    String matchiddocumentimage = "";
    String matchedvoterimagename = "";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        this.lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, this.lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        this.userAuth = new UserAuth(this);
        this.androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        this.UDevId = this.androidId;
        getSupportActionBar().setTitle(this.resources.getString(R.string.authentication_complete_text));
        this.messageText = (TextView) findViewById(R.id.message);
        this.matchMessageText = (TextView) findViewById(R.id.match_message);
        this.ButtonList = (Button) findViewById(R.id.ok_button);
        this.ButtonVisible = (Button) findViewById(R.id.allow_button);
        this.image = (ImageView) findViewById(R.id.image_1);
        this.matchImage = (ImageView) findViewById(R.id.image_0);
        this.matchUserImage = (SimpleDraweeView) findViewById(R.id.usermatchimage);
        Intent intent = getIntent();
        this.voterid = intent.getStringExtra("voterid");
        intent.getStringExtra("message");
        Boolean allowedtovote = Boolean.valueOf(intent.getBooleanExtra("allowedtovote", false));
        this.votedDone = intent.getStringExtra("voted");
        this.slnoinward = intent.getStringExtra("slnoinward");
        this.matchslnoinward = intent.getStringExtra("matchslnoinward");
        String fpmatchvoterid = intent.getStringExtra("fpmatchvotertid");
        this.matchedvoterimagename = intent.getStringExtra("matchedvoterimagename");
        Context applicationContext = getApplicationContext();
        Toast.makeText(applicationContext, "matchedvoterimage=" + this.matchedvoterimagename, 1).show();
        if (allowedtovote.booleanValue()) {
            String votingmessage = this.resources.getString(R.string.u_can_vote_text);
            this.image.setImageResource(R.drawable.right_icon_trp);
            this.voted = 1;
            this.messageText.setText(votingmessage);
            this.ButtonVisible.setVisibility(8);
        } else {
            beep();
            this.voted = 2;
            this.image.setImageResource(R.drawable.wrong_icon_trp);
            String finalmessage = this.resources.getString(R.string.u_cannot_vote_text);
            this.messageText.setText(finalmessage + IOUtils.LINE_SEPARATOR_UNIX + this.resources.getString(R.string.fingerprintrecord_match_text) + fpmatchvoterid);
            getMatchedVoterData(this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward);
            this.ButtonVisible.setVisibility(0);
        }
        this.ButtonList.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - FinalScreenActivityOffline.mLastClkTime < FinalScreenActivityOffline.Threshold) {
                    Toast.makeText(FinalScreenActivityOffline.this.getApplicationContext(), "Waiting for response from server", 1).show();
                    return;
                }
                long unused = FinalScreenActivityOffline.mLastClkTime = SystemClock.elapsedRealtime();
                if (FinalScreenActivityOffline.this.votedDone.equalsIgnoreCase(DiskLruCache.VERSION_1) && FinalScreenActivityOffline.this.voted == 1) {
                    FinalScreenActivityOffline.this.voted = 3;
                }
                FinalScreenActivityOffline finalScreenActivityOffline = FinalScreenActivityOffline.this;
                finalScreenActivityOffline.updateVotingStatusOffline(finalScreenActivityOffline.voterid, FinalScreenActivityOffline.this.voted);
            }
        });
        this.ButtonList.setText(this.resources.getString(R.string.ok_text));
        this.ButtonVisible.setText(this.resources.getString(R.string.allow_text));
        this.ButtonVisible.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - FinalScreenActivityOffline.mLastClkTime < FinalScreenActivityOffline.Threshold) {
                    Toast.makeText(FinalScreenActivityOffline.this.getApplicationContext(), "Waiting for response from server", 1).show();
                    return;
                }
                long unused = FinalScreenActivityOffline.mLastClkTime = SystemClock.elapsedRealtime();
                if (FinalScreenActivityOffline.this.votedDone.equalsIgnoreCase(DiskLruCache.VERSION_1)) {
                    FinalScreenActivityOffline finalScreenActivityOffline = FinalScreenActivityOffline.this;
                    finalScreenActivityOffline.voted = 3;
                    finalScreenActivityOffline.updateVotingStatusOffline(finalScreenActivityOffline.voterid, FinalScreenActivityOffline.this.voted);
                    return;
                }
                FinalScreenActivityOffline finalScreenActivityOffline2 = FinalScreenActivityOffline.this;
                finalScreenActivityOffline2.voted = 1;
                finalScreenActivityOffline2.updateVotingStatusOffline(finalScreenActivityOffline2.voterid, FinalScreenActivityOffline.this.voted);
            }
        });
    }

    private void getMatchedVoterData(String matchedvotertid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", matchedvotertid);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByUserId(map).enqueue(new Callback<VoterDataGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.3
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterDataGetResponse> call, Response<VoterDataGetResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getVoters() != null) {
                    FinalScreenActivityOffline.this.showUserInfoIncaseofMatch(response.body().getVoters());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterDataGetResponse> call, Throwable t) {
                if (!(t instanceof SocketTimeoutException)) {
                    boolean z = t instanceof IOException;
                }
                FinalScreenActivityOffline.this.showUserInfoFromLocaldb(FinalScreenActivityOffline.this.db.getVoterBySlNoInWard(FinalScreenActivityOffline.this.matchslnoinward));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserInfoIncaseofMatch(VoterDataNewModel Voter) {
        System.out.println("showUserInfoIncaseofMatch1");
        String name = this.resources.getString(R.string.name) + ":";
        if (this.lan.equalsIgnoreCase("en")) {
            if (Voter.getFM_NAME_EN() != null) {
                name = name + Voter.getFM_NAME_EN();
            }
            if (Voter.getLASTNAME_EN() != null) {
                name = name + " " + Voter.getLASTNAME_EN();
            }
        } else {
            if (Voter.getFM_NAME_V1() != null) {
                name = name + Voter.getFM_NAME_V1();
            }
            if (Voter.getLASTNAME_EN() != null) {
                name = name + " " + Voter.getLASTNAME_V1();
            }
        }
        String gender = this.resources.getString(R.string.gender) + ":" + Voter.getGENDER();
        String blockNo = this.resources.getString(R.string.block_no) + ":" + Voter.getBlockID();
        System.out.println("showUserInfoIncaseofMatch 2 name=" + name);
        if (Voter.getAge() != null) {
            Voter.getAge();
        }
        String age = this.resources.getString(R.string.age) + ":" + Voter.getAge();
        String message = name + IOUtils.LINE_SEPARATOR_UNIX + gender + IOUtils.LINE_SEPARATOR_UNIX + age + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.ward_no) + ":" + Voter.getWardNo()) + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.voting_date) + ":" + Voter.getVOTING_DATE()) + IOUtils.LINE_SEPARATOR_UNIX + blockNo;
        System.out.println("showUserInfoIncaseofMatch3 age=" + age);
        String imageurl = "";
        if (Voter.getID_DOCUMENT_IMAGE() != null) {
            imageurl = Voter.getID_DOCUMENT_IMAGE();
        }
        if (name != null && !name.isEmpty() && name.length() > 0) {
            this.matchMessageText.setVisibility(0);
            this.matchMessageText.setText(message);
            System.out.println("showUserInfoIncaseofMatch4 ");
            Uri uri1 = Uri.parse(imageurl);
            this.matchUserImage.setVisibility(0);
            this.matchUserImage.setImageURI(uri1);
            if (!isUrlValid(imageurl)) {
                File imgFile = new File("/sdcard/" + Const.PublicImageName + "/" + Voter.getID_DOCUMENT_IMAGE());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    this.matchUserImage.setVisibility(0);
                    this.matchUserImage.setImageBitmap(myBitmap);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserInfoFromLocaldb(VoterDataNewModel Voter) {
        String voterimage;
        System.out.println("showUserInfoIncaseofMatch1");
        String name = this.resources.getString(R.string.name) + ":";
        if (this.lan.equalsIgnoreCase("en")) {
            if (Voter.getFM_NAME_EN() != null) {
                name = name + Voter.getFM_NAME_EN();
            }
            if (Voter.getLASTNAME_EN() != null) {
                name = name + " " + Voter.getLASTNAME_EN();
            }
        } else {
            if (Voter.getFM_NAME_V1() != null) {
                name = name + Voter.getFM_NAME_V1();
            }
            if (Voter.getLASTNAME_EN() != null) {
                name = name + " " + Voter.getLASTNAME_V1();
            }
        }
        String gender = this.resources.getString(R.string.gender) + ":" + Voter.getGENDER();
        String blockNo = this.resources.getString(R.string.panchayat_id) + "/" + this.resources.getString(R.string.ward_no) + "/" + this.resources.getString(R.string.booth_no_text) + ":" + Voter.getPanchayatID() + "/" + Voter.getWardNo() + "/" + Voter.getBoothNo();
        System.out.println("showUserInfoIncaseofMatch 2 name=" + name);
        if (Voter.getAge() != null) {
            Voter.getAge();
        }
        String age = this.resources.getString(R.string.age) + ":" + Voter.getAge();
        String message = name + IOUtils.LINE_SEPARATOR_UNIX + gender + IOUtils.LINE_SEPARATOR_UNIX + age + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.ward_no) + ":" + Voter.getWardNo()) + IOUtils.LINE_SEPARATOR_UNIX + (this.resources.getString(R.string.voting_date) + ":" + this.db.getDateFromSlNoinWard(this.matchslnoinward)) + IOUtils.LINE_SEPARATOR_UNIX + blockNo;
        System.out.println("showUserInfoIncaseofMatch3 age=" + age);
        if (Voter.getID_DOCUMENT_IMAGE() != null) {
            Voter.getID_DOCUMENT_IMAGE();
        }
        if (name != null && !name.isEmpty() && name.length() > 0) {
            this.matchMessageText.setVisibility(0);
            this.matchMessageText.setText(message);
            System.out.println("showUserInfoIncaseofMatch4 ");
            String str = this.matchedvoterimagename;
            if (str == null || str.isEmpty() || this.matchedvoterimagename.length() <= 0) {
                voterimage = this.db.getImageFromTransactionTable(this.matchslnoinward);
            } else {
                voterimage = this.matchedvoterimagename;
            }
            File imgFile = new File("/sdcard/" + Const.PublicImageName + "/" + voterimage);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                this.matchUserImage.setVisibility(0);
                this.matchUserImage.setImageBitmap(myBitmap);
                return;
            }
            Uri uri1 = Uri.parse("http://election.phoneme.in:9090/VoterAuthenticationapi/getimagesthumb?file=" + voterimage);
            this.matchUserImage.setVisibility(0);
            this.matchUserImage.setImageURI(uri1);
        }
    }

    private void showUserInfoIncaseofMatchOffline() {
        String age = "";
        Cursor cursor = this.db.SingleUserRowByVoterId(this.voterid);
        if (cursor.moveToFirst()) {
            do {
                try {
                    age = cursor.getString(cursor.getColumnIndex(HttpHeaders.AGE));
                } catch (Exception e) {
                }
            } while (cursor.moveToNext());
            this.matchUserImage.setVisibility(0);
            this.matchMessageText.setVisibility(0);
            this.matchMessageText.setText(" | Age:" + age);
        }
        this.matchUserImage.setVisibility(0);
        this.matchMessageText.setVisibility(0);
        this.matchMessageText.setText(" | Age:" + age);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void UserListScreen() {
        Intent i = new Intent(this, ListUserActivity.class);
        i.setFlags(i.getFlags() | 1073741824);
        startActivity(i);
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        updateVotingStatusOffline(this.voterid, this.voted);
    }

    private void updateUserVotingStatus(final int votingstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("votingstatus", "" + votingstatus);
        map.put("voterid", this.voterid);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVotingStatusUpdate(map).enqueue(new Callback<UserVotingStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.4
            @Override // retrofit2.Callback
            public void onResponse(Call<UserVotingStatusUpdatePostResponse> call, Response<UserVotingStatusUpdatePostResponse> response) {
                if (votingstatus == 1) {
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserVotingStatusUpdatePostResponse> call, Throwable t) {
            }
        });
    }

    private void updateUserVotingStatusAllow(final int votingstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("votingstatus", "" + votingstatus);
        map.put("voterid", this.voterid);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVotingStatusUpdate(map).enqueue(new Callback<UserVotingStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.5
            @Override // retrofit2.Callback
            public void onResponse(Call<UserVotingStatusUpdatePostResponse> call, Response<UserVotingStatusUpdatePostResponse> response) {
                if (votingstatus == 1) {
                }
                FinalScreenActivityOffline.this.UserListScreen();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserVotingStatusUpdatePostResponse> call, Throwable t) {
                Toast.makeText(FinalScreenActivityOffline.this.getApplicationContext(), "Some network issue. Please press Allow button again", 1);
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    FinalScreenActivityOffline.this.UserListScreen();
                }
            }
        });
    }

    private void updatefacefpmatchvoterid(String voterid, String facematchvoterid, String fpmatchovertid) {
        Map<String, String> map = new HashMap<>();
        map.put("vidfacematch", facematchvoterid);
        map.put("vidfpmatch", fpmatchovertid);
        map.put("voterid", voterid);
        map.put("udevid", this.UDevId);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postfacefpmatchvoterid(map).enqueue(new Callback<FacefpmatchvoteridUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.6
            @Override // retrofit2.Callback
            public void onResponse(Call<FacefpmatchvoteridUpdatePostResponse> call, Response<FacefpmatchvoteridUpdatePostResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FacefpmatchvoteridUpdatePostResponse> call, Throwable t) {
            }
        });
    }

    private void translate(String lan) {
        getSupportActionBar().setTitle(this.resources.getString(R.string.authentication_complete_text));
    }

    private String getcurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVotingStatusOffline(String voterid, int voted) {
        String matchdocument;
        String currentTime = getcurrentTime();
        String userId = this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward;
        this.db.updateVOTEDByUSER_ID_Maintable(userId, voted);
        this.db.updateVotingStatusTransTable(voterid, voted, currentTime, 0, this.userAuth.getTransactionId().longValue(), "NON_AADHAAR", userId);
        if (voted == 2) {
            String matchedUserId = this.db.getUser_IdBySlNoinWard(this.matchslnoinward);
            String str = this.matchedvoterimagename;
            if (str == null || str.isEmpty() || this.matchedvoterimagename.length() <= 0) {
                matchdocument = this.db.getImageFromTransactionTable(this.matchslnoinward);
            } else {
                matchdocument = this.matchedvoterimagename;
            }
            this.db.updateMatchedVoterData(this.userAuth.getTransactionId().longValue(), matchedUserId, matchdocument);
        }
        uploadTransactionRow();
    }

    private void beep() {
        new ToneGenerator(4, 500).startTone(93, 600);
    }

    private void deleteFingerprint(String voterid) {
        this.db.clearFingerprintTransTable(this.userAuth.getTransactionId().longValue());
    }

    public static boolean isUrlValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e2) {
            return false;
        }
    }

    private void uploadTransactionRow() {
        new HashMap();
        Map<String, String> map = getTransactionRowData();
        PrintStream printStream = System.out;
        printStream.println("MAP=" + map.toString());
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).updateTransactionRow(map).enqueue(new Callback<TransactionRowPostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.7
            @Override // retrofit2.Callback
            public void onResponse(Call<TransactionRowPostResponse> call, Response<TransactionRowPostResponse> response) {
                if (response == null || response.body() == null || !response.body().getUpdated()) {
                    Toast.makeText(FinalScreenActivityOffline.this.getApplicationContext(), "Transaction row Not updated " + response.code(), 1).show();
                } else {
                    String userId = FinalScreenActivityOffline.this.userAuth.getPanchayatId() + "_" + FinalScreenActivityOffline.this.userAuth.getWardNo() + "_" + FinalScreenActivityOffline.this.userAuth.getBoothNo() + "_" + FinalScreenActivityOffline.this.slnoinward;
                    FinalScreenActivityOffline.this.db.updateVOTEDByUSER_ID_Maintable(userId, FinalScreenActivityOffline.this.voted);
                    FinalScreenActivityOffline.this.db.updateVotingStatusTransTable(FinalScreenActivityOffline.this.voterid, FinalScreenActivityOffline.this.voted, FinalScreenActivityOffline.this.getCurrentTimeInFormat(), 1, FinalScreenActivityOffline.this.userAuth.getTransactionId().longValue(), "NON_AADHAAR", userId);
                }
                FinalScreenActivityOffline.this.UserListScreen();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<TransactionRowPostResponse> call, Throwable t) {
                Context applicationContext = FinalScreenActivityOffline.this.getApplicationContext();
                Toast.makeText(applicationContext, "Transaction update Error. " + t.getMessage(), 1).show();
                FinalScreenActivityOffline.this.UserListScreen();
            }
        });
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:5|6|(1:8)|9|(4:11|42|12|(5:14|19|44|20|22))(1:17)|18|19|44|20|22) */
    /* Code decompiled incorrectly, please refer to instructions dump */
    private Map<String, String> getTransactionRowData() {
        Throwable th;
        Map<String, String> map;
        Cursor cursor;
        Cursor cursor2;
        Exception e;
        String str;
        String fpString;
        try {
            try {
                cursor = this.db.SingleTransactionRow(this.userAuth.getTransactionId().longValue());
                map = new HashMap<>();
            } catch (Exception e2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
        } catch (Exception e3) {
            e = e3;
            cursor2 = cursor;
        } catch (Throwable th3) {
            th = th3;
            try {
                cursor.close();
            } catch (Exception e4) {
            }
            throw th;
        }
        if (cursor.moveToFirst()) {
            new VoterDataNewModel().setId(cursor.getString(0));
            byte[] fp = cursor.getBlob(cursor.getColumnIndex("FingerTemplate"));
            int voted = cursor.getInt(cursor.getColumnIndex("VOTED"));
            int age = cursor.getInt(cursor.getColumnIndex("AGE"));
            String gender = cursor.getString(cursor.getColumnIndex("GENDER"));
            String voterimagename = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
            int aadhaarmatch = cursor.getInt(cursor.getColumnIndex("AADHAAR_MATCH"));
            this.slnoinward = cursor.getString(cursor.getColumnIndex("SlNoInWard"));
            String aadhaarNo = cursor.getString(cursor.getColumnIndex("AADHAAR_NO"));
            String MATCHED_USER_ID = cursor.getString(cursor.getColumnIndex("MATCHED_USER_ID"));
            String MATCHED_ID_DOCUMENT_IMAGE = cursor.getString(cursor.getColumnIndex("MATCHED_ID_DOCUMENT_IMAGE"));
            cursor2 = cursor;
            if (aadhaarNo == null) {
                aadhaarNo = "";
            }
            if (fp != null) {
                str = "MATCHED_ID_DOCUMENT_IMAGE";
                try {
                    if (fp.length > 0) {
                        fpString = Base64.encodeToString(fp, 0);
                        map.put("TRANSID", "" + this.userAuth.getTransactionId());
                        map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward);
                        map.put("FINGERPRINT_TEMPLATE", fpString);
                        map.put("VOTED", "" + voted);
                        map.put("ID_DOCUMENT_IMAGE", voterimagename);
                        map.put("AADHAAR_MATCH", "" + aadhaarmatch);
                        map.put("AADHAAR_NO", aadhaarNo);
                        map.put("VOTING_DATE", getCurrentTimeInFormat());
                        map.put("VOTING_TYPE", "NON_AADHAAR");
                        map.put("booth_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo());
                        map.put("GENDER", gender);
                        map.put("AGE", "" + age);
                        map.put("udevid", this.UDevId);
                        map.put("MATCHED_USER_ID", MATCHED_USER_ID);
                        map.put("matched_user_id", MATCHED_USER_ID);
                        map.put(str, MATCHED_ID_DOCUMENT_IMAGE);
                        cursor2.close();
                        return map;
                    }
                } catch (Exception e5) {
                    e = e5;
                    Toast.makeText(getApplicationContext(), "Exception while forming transaction row " + e.getMessage(), 1).show();
                    cursor2.close();
                    return map;
                }
            } else {
                str = "MATCHED_ID_DOCUMENT_IMAGE";
            }
            fpString = "";
            map.put("TRANSID", "" + this.userAuth.getTransactionId());
            map.put("user_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo() + "_" + this.slnoinward);
            map.put("FINGERPRINT_TEMPLATE", fpString);
            map.put("VOTED", "" + voted);
            map.put("ID_DOCUMENT_IMAGE", voterimagename);
            map.put("AADHAAR_MATCH", "" + aadhaarmatch);
            map.put("AADHAAR_NO", aadhaarNo);
            map.put("VOTING_DATE", getCurrentTimeInFormat());
            map.put("VOTING_TYPE", "NON_AADHAAR");
            map.put("booth_id", this.userAuth.getPanchayatId() + "_" + this.userAuth.getWardNo() + "_" + this.userAuth.getBoothNo());
            map.put("GENDER", gender);
            map.put("AGE", "" + age);
            map.put("udevid", this.UDevId);
            map.put("MATCHED_USER_ID", MATCHED_USER_ID);
            map.put("matched_user_id", MATCHED_USER_ID);
            map.put(str, MATCHED_ID_DOCUMENT_IMAGE);
            cursor2.close();
            return map;
        }
        cursor.close();
        return map;
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }
}
