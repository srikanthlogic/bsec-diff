package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.adapter.VotingHistoryAdapter;
import com.example.aadhaarfpoffline.tatvik.database.DBHelper;
import com.example.aadhaarfpoffline.tatvik.model.VotingHistoryModel;
import java.util.List;
/* loaded from: classes2.dex */
public class VotingStatusActivity extends AppCompatActivity implements VotingHistoryAdapter.OnItemClickListener {
    private TextView aadhaaNonAaadhaatCount;
    private VotingHistoryAdapter adapter;
    private TextView blockBooth;
    Context context;
    DBHelper db;
    private RecyclerView recyclerView;
    Resources resources;
    private TextView stateDistrict;
    private TextView txtNumAllVoted;
    private TextView txtNumFemaleVoted;
    private TextView txtNumMaleVoted;
    private TextView txtNumRejected;
    private UserAuth userAuth;
    private List<VotingHistoryModel> votingHistoryModelList;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votingstatus);
        this.context = LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        this.resources = this.context.getResources();
        this.db = new DBHelper(this);
        this.userAuth = new UserAuth(this);
        this.txtNumAllVoted = (TextView) findViewById(R.id.num_all_voted);
        this.txtNumFemaleVoted = (TextView) findViewById(R.id.num_female_voted);
        this.txtNumMaleVoted = (TextView) findViewById(R.id.num_male_voted);
        this.txtNumRejected = (TextView) findViewById(R.id.num_rejected);
        this.stateDistrict = (TextView) findViewById(R.id.state_district);
        this.blockBooth = (TextView) findViewById(R.id.block_booth);
        this.aadhaaNonAaadhaatCount = (TextView) findViewById(R.id.aadhaarnonaadhaar);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_voter_history_list);
        TextView textView = this.txtNumAllVoted;
        textView.setText("Voter Turnout:" + this.db.getNumbersVoted());
        TextView textView2 = this.txtNumFemaleVoted;
        textView2.setText("Female voters:" + this.db.getNumberFemalesVoted() + " Male Voters:" + this.db.getNumberMalesVoted());
        TextView textView3 = this.txtNumRejected;
        StringBuilder sb = new StringBuilder();
        sb.append("Rejected voters:");
        sb.append(this.db.getNumbersRejected());
        textView3.setText(sb.toString());
        TextView textView4 = this.stateDistrict;
        textView4.setText(this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDistrictNo());
        TextView textView5 = this.blockBooth;
        textView5.setText(this.resources.getString(R.string.panchayat_id) + ":" + this.userAuth.getPanchayatId() + ", " + this.resources.getString(R.string.block_no_text) + ":" + getBoothInFormat(this.userAuth.getBoothNo()) + "," + this.resources.getString(R.string.ward_no_text) + ":" + this.userAuth.getWardNo());
        TextView textView6 = this.aadhaaNonAaadhaatCount;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Voters by Aadhaar:");
        sb2.append(this.db.getAadhaarVotedCount());
        sb2.append(", Voters by Non Aadhaar:");
        sb2.append(this.db.getNonAadhaarVotedCount());
        textView6.setText(sb2.toString());
        TextView textView7 = this.txtNumMaleVoted;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Male voters ");
        sb3.append(this.db.getNumberMalesVoted());
        textView7.setText(sb3.toString());
        this.txtNumMaleVoted.setVisibility(8);
        List<VotingHistoryModel> list = this.db.getAllTransactionTableData();
        if (list != null && !list.isEmpty() && list.size() > 0) {
            setRecyclerView(list);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        startActivity(new Intent(this, ListUserActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override // com.example.aadhaarfpoffline.tatvik.adapter.VotingHistoryAdapter.OnItemClickListener
    public void onItemClick3(int position, String voterid) {
    }

    private void getTransTableInList() {
    }

    private void setRecyclerView(List<VotingHistoryModel> list) {
        this.adapter = new VotingHistoryAdapter(this, this, list);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
    }

    private String getBoothInFormat(String boothnumstr) {
        Integer boothnum = Integer.valueOf(Integer.parseInt(boothnumstr));
        if (boothnum.intValue() <= 1000) {
            return String.valueOf(boothnum);
        }
        if (boothnum.intValue() > 1000 && boothnum.intValue() <= 2000) {
            return String.valueOf(boothnum + "क");
        } else if (boothnum.intValue() > 2000 && boothnum.intValue() <= 3000) {
            return String.valueOf(boothnum + "ख");
        } else if (boothnum.intValue() > 3000 && boothnum.intValue() <= 4000) {
            return String.valueOf(boothnum + "ग");
        } else if (boothnum.intValue() <= 4000 || boothnum.intValue() > 5000) {
            return "";
        } else {
            return String.valueOf(boothnum + "घ");
        }
    }
}
