package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.network.FacefpmatchvoteridUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserVotingStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.VoterDataGetResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class FinalScreenActivity extends AppCompatActivity {
    private Button ButtonList;
    private Button ButtonVisible;
    Context context;
    private ImageView image;
    private ImageView matchImage;
    private TextView matchMessageText;
    private SimpleDraweeView matchUserImage;
    private TextView messageText;
    Resources resources;
    private String voterid = "";
    String facematchvoterid = "";
    String fpmatchovertid = "";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        this.context = LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        this.resources = this.context.getResources();
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
        Boolean.valueOf(intent.getBooleanExtra("allowedtovote", false));
        Boolean aadhaarapi = Boolean.valueOf(intent.getBooleanExtra("aadhaarapi", false));
        Boolean aadhaarmatch = Boolean.valueOf(intent.getBooleanExtra("aadhaarmatch", false));
        if (aadhaarapi.booleanValue()) {
            String votername = intent.getStringExtra("votername");
            if (aadhaarmatch.booleanValue()) {
                this.image.setImageResource(R.drawable.right_icon);
                String pincode = intent.getStringExtra("pincode");
                this.messageText.setText("You can vote " + votername + ". Your state is " + intent.getStringExtra("state") + ".Your pincode is " + pincode + ".Your Aadhaar last 4 digits are " + intent.getStringExtra("last4digit"));
                updateUserVotingStatus(1);
            } else {
                this.image.setImageResource(R.drawable.wrong_icon);
                this.messageText.setText(this.resources.getString(R.string.u_cannot_vote_aadhaar_text));
                updateUserVotingStatus(2);
            }
        } else {
            Boolean faceFound = Boolean.valueOf(intent.getBooleanExtra("facefound", false));
            Boolean fingerprintFound = Boolean.valueOf(intent.getBooleanExtra("fingerprintfound", false));
            this.facematchvoterid = intent.getStringExtra("facematchvoterid");
            this.fpmatchovertid = intent.getStringExtra("fpmatchvotertid");
            String facename = intent.getStringExtra("nameIfFoundFace");
            String fingername = intent.getStringExtra("fpfoundname");
            if (this.facematchvoterid == null) {
                this.facematchvoterid = "";
            }
            if (this.fpmatchovertid == null) {
                this.fpmatchovertid = "";
            }
            updatefacefpmatchvoterid(this.voterid, this.facematchvoterid, this.fpmatchovertid);
            if (faceFound.booleanValue() || fingerprintFound.booleanValue()) {
                this.image.setImageResource(R.drawable.wrong_icon);
                String finalmessage = this.resources.getString(R.string.u_cannot_vote_text);
                if (facename != null && facename.length() > 0) {
                    finalmessage = finalmessage + IOUtils.LINE_SEPARATOR_UNIX + this.resources.getString(R.string.facerecord_match_text) + facename;
                }
                if (fingername != null && fingername.length() > 0) {
                    finalmessage = finalmessage + IOUtils.LINE_SEPARATOR_UNIX + this.resources.getString(R.string.fingerprintrecord_match_text) + fingername;
                }
                this.messageText.setText(finalmessage);
                updateUserVotingStatus(2);
            } else {
                this.image.setImageResource(R.drawable.right_icon);
                this.messageText.setText(this.resources.getString(R.string.u_can_vote_text));
                updateUserVotingStatus(1);
            }
            if (fingerprintFound.booleanValue()) {
                this.ButtonVisible.setVisibility(0);
                getMatchedVoterData(this.fpmatchovertid);
            } else {
                this.ButtonVisible.setVisibility(8);
            }
        }
        this.ButtonList.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FinalScreenActivity.this.UserListScreen();
            }
        });
        this.ButtonList.setText(this.resources.getString(R.string.ok_text));
        this.ButtonVisible.setText(this.resources.getString(R.string.allow_text));
        this.ButtonVisible.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FinalScreenActivity.this.updateUserVotingStatusAllow(1);
            }
        });
    }

    private void getMatchedVoterData(String matchedvotertid) {
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).getVoterByVoterId(matchedvotertid).enqueue(new Callback<VoterDataGetResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivity.3
            @Override // retrofit2.Callback
            public void onResponse(Call<VoterDataGetResponse> call, Response<VoterDataGetResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    if (response.code() != 200) {
                        Toast.makeText(FinalScreenActivity.this.getApplicationContext(), "The server is temporarily unable to process your request.Please try again later", 1).show();
                    } else if (response.body().getVoters() != null) {
                        FinalScreenActivity.this.showUserInfoIncaseofMatch(response.body().getVoters());
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<VoterDataGetResponse> call, Throwable t) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserInfoIncaseofMatch(VoterDataNewModel Voter) {
        System.out.println("showUserInfoIncaseofMatch1");
        String name = "";
        if (Voter.getFM_NAME_EN() != null) {
            name = name + Voter.getFM_NAME_EN();
        }
        if (Voter.getLASTNAME_EN() != null) {
            name = name + Voter.getLASTNAME_EN();
        }
        System.out.println("showUserInfoIncaseofMatch 2 name=" + name);
        String age = "";
        if (Voter.getAge() != null) {
            age = Voter.getAge();
        }
        String message = name + " | Age:" + age;
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
        }
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
    }

    private void updateUserVotingStatus(final int votingstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("votingstatus", "" + votingstatus);
        map.put("voterid", this.voterid);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVotingStatusUpdate(map).enqueue(new Callback<UserVotingStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivity.4
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserVotingStatusAllow(final int votingstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("votingstatus", "" + votingstatus);
        map.put("voterid", this.voterid);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVotingStatusUpdate(map).enqueue(new Callback<UserVotingStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivity.5
            @Override // retrofit2.Callback
            public void onResponse(Call<UserVotingStatusUpdatePostResponse> call, Response<UserVotingStatusUpdatePostResponse> response) {
                if (votingstatus == 1) {
                }
                FinalScreenActivity.this.UserListScreen();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserVotingStatusUpdatePostResponse> call, Throwable t) {
                Toast.makeText(FinalScreenActivity.this.getApplicationContext(), "Some network issue. Please press Allow button again", 1);
            }
        });
    }

    private void updatefacefpmatchvoterid(String voterid, String facematchvoterid, String fpmatchovertid) {
        Map<String, String> map = new HashMap<>();
        map.put("vidfacematch", facematchvoterid);
        map.put("vidfpmatch", fpmatchovertid);
        map.put("voterid", voterid);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postfacefpmatchvoterid(map).enqueue(new Callback<FacefpmatchvoteridUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.FinalScreenActivity.6
            @Override // retrofit2.Callback
            public void onResponse(Call<FacefpmatchvoteridUpdatePostResponse> call, Response<FacefpmatchvoteridUpdatePostResponse> response) {
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<FacefpmatchvoteridUpdatePostResponse> call, Throwable t) {
                Context applicationContext = FinalScreenActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "failed=" + t.getMessage(), 1).show();
            }
        });
    }

    private void translate(String lan) {
        getSupportActionBar().setTitle(this.resources.getString(R.string.authentication_complete_text));
    }
}
