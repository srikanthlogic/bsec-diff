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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.FacefpmatchvoteridUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserVotingStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterDataGetResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.net.HttpHeaders;
import java.io.File;
import java.io.IOException;
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
    private String voterid = "";
    String facematchvoterid = "";
    String fpmatchovertid = "";
    String votedDone = "";
    String lan = "";
    int voted = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        this.lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, this.lan);
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
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
        String fpmatchvoterid = intent.getStringExtra("fpmatchvotertid");
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
            getMatchedVoterData(fpmatchvoterid);
            this.ButtonVisible.setVisibility(0);
        }
        this.ButtonList.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
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

    private void getMatchedVoterData(final String matchedvotertid) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByVoterId(matchedvotertid).enqueue(new Callback<VoterDataGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivityOffline.3
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterDataGetResponse> call, Response<VoterDataGetResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getVoters() != null) {
                    FinalScreenActivityOffline.this.showUserInfoIncaseofMatch(response.body().getVoters());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterDataGetResponse> call, Throwable t) {
                if (!(t instanceof SocketTimeoutException) && (t instanceof IOException)) {
                    FinalScreenActivityOffline.this.showUserInfoIncaseofMatch(FinalScreenActivityOffline.this.db.getVoter(matchedvotertid));
                }
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
            if (isUrlValid(imageurl)) {
                Toast.makeText(getApplicationContext(), "Url valid", 1).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "Url Not valid " + Voter.getID_DOCUMENT_IMAGE(), 1).show();
            File imgFile = new File("/sdcard/Images/" + Voter.getID_DOCUMENT_IMAGE());
            if (imgFile.exists()) {
                Toast.makeText(getApplicationContext(), "Url Not valid2", 1).show();
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                this.matchUserImage.setVisibility(0);
                this.matchUserImage.setImageBitmap(myBitmap);
            }
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
        this.db.updateVotingStatus(voterid, voted, getcurrentTime());
        if (voted == 2) {
            deleteFingerprint(voterid);
        }
        updateUserVotingStatusAllow(voted);
    }

    private void beep() {
        new ToneGenerator(4, 500).startTone(93, 600);
    }

    private void deleteFingerprint(String voterid) {
        this.db.clearFingerprint(voterid);
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
}
