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
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_voter_history_list);
        TextView textView = this.txtNumAllVoted;
        textView.setText("Voter Turnout " + this.db.getNumbersVoted());
        TextView textView2 = this.txtNumFemaleVoted;
        textView2.setText("Female voters " + this.db.getNumberFemalesVoted());
        TextView textView3 = this.txtNumMaleVoted;
        textView3.setText("Male voters " + this.db.getNumberMalesVoted());
        TextView textView4 = this.txtNumRejected;
        textView4.setText("Rejected voters " + this.db.getNumbersRejected());
        TextView textView5 = this.stateDistrict;
        textView5.setText(this.resources.getString(R.string.district_name_text) + ":" + this.userAuth.getDistrictNo());
        TextView textView6 = this.blockBooth;
        textView6.setText(this.resources.getString(R.string.panchayat_id) + ":" + this.userAuth.getPanchayatId() + ", " + this.resources.getString(R.string.block_no_text) + ":" + this.userAuth.getBoothNo() + "," + this.resources.getString(R.string.ward_no_text) + ":" + this.userAuth.getWardNo());
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
}
