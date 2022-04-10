package com.example.aadhaarfpoffline.tatvik.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.model.VotingHistoryModel;
import java.text.SimpleDateFormat;
import java.util.List;
import okhttp3.internal.cache.DiskLruCache;
/* loaded from: classes2.dex */
public class VotingHistoryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Boolean Clickable;
    private SimpleDateFormat formatter;
    private String lan;
    private Context langContext;
    private OnItemClickListener listener;
    private Context mcontext;
    private Resources resources;
    private UserAuth userAuth;
    private List<VoterDataNewModel> voterDataModelList;
    private List<VotingHistoryModel> votingHistoryModelList;

    /* loaded from: classes2.dex */
    public interface OnItemClickListener {
        void onItemClick3(int i, String str);
    }

    public VotingHistoryAdapter(Context context) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
    }

    public VotingHistoryAdapter(Context context, OnItemClickListener listener) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
        this.listener = listener;
    }

    public VotingHistoryAdapter(Context context, OnItemClickListener listener, List<VotingHistoryModel> VotingHistoryModelList) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
        this.listener = listener;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.votingHistoryModelList = VotingHistoryModelList;
        this.userAuth = new UserAuth(this.mcontext);
        this.lan = LocaleHelper.getLanguage(this.mcontext);
        this.langContext = LocaleHelper.setLocale(this.mcontext, this.lan);
        this.resources = this.langContext.getResources();
    }

    public VotingHistoryAdapter(Context context, OnItemClickListener listener, List<VoterDataNewModel> voterDataModelList, Boolean clickable) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
        this.listener = listener;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.voterDataModelList = voterDataModelList;
        this.userAuth = new UserAuth(this.mcontext);
        if (this.userAuth.ifLocked().booleanValue()) {
            this.Clickable = false;
        }
        this.lan = LocaleHelper.getLanguage(this.mcontext);
        this.langContext = LocaleHelper.setLocale(this.mcontext, this.lan);
        this.resources = this.langContext.getResources();
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Age;
        private TextView BlockNo;
        private TextView BoothId;
        private TextView District;
        private TextView Pam;
        private TextView SlNoInWard;
        private TextView SlNoInWard2;
        private TextView VotingDate;
        private TextView allocated_users;
        private CardView cardView;
        private TextView company_name;
        private TextView createdat;
        private TextView description;
        private TextView edit;
        private Button editButton;
        private ImageView imageView;
        private LinearLayout mainLayout;
        private RelativeLayout relativeLayoutView;
        private TextView status;
        private TextView title;
        private Button votedOrNot;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener(VotingHistoryAdapter.this) { // from class: com.example.aadhaarfpoffline.tatvik.adapter.VotingHistoryAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                }
            });
            this.SlNoInWard2 = (TextView) v.findViewById(R.id.slnoinward);
            this.VotingDate = (TextView) v.findViewById(R.id.voting_date);
            this.votedOrNot = (Button) v.findViewById(R.id.votednotvoted);
            this.mainLayout = (LinearLayout) v.findViewById(R.id.main_layout);
            this.imageView = (ImageView) v.findViewById(R.id.malefemale);
        }

        private void setData2(int position) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData3(int position) {
            this.SlNoInWard2.setText(((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getSlNoInWard());
            if (((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVoted() == null || ((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVoted().isEmpty() || ((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVoted().length() <= 0) {
                this.votedOrNot.setText("NOT COMPLETE");
            } else if (((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVoted().equalsIgnoreCase(DiskLruCache.VERSION_1) || ((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVoted().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
                this.votedOrNot.setText("VOTED");
            } else if (((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVoted().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                this.votedOrNot.setText("REJECTED");
            }
            if (((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVotingDate() == null || ((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVotingDate().isEmpty() || ((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVotingDate().length() < 1) {
                this.VotingDate.setText("Voting Not complete");
            } else {
                TextView textView = this.VotingDate;
                textView.setText("Voting Date:" + ((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getVotingDate());
            }
            if (((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getSynced() == 0) {
                this.mainLayout.setBackgroundResource(R.drawable.border_synced);
            } else {
                this.mainLayout.setBackgroundResource(R.drawable.border_unsynced);
            }
            if (((VotingHistoryModel) VotingHistoryAdapter.this.votingHistoryModelList.get(position)).getGender().contains("M")) {
                this.imageView.setImageResource(R.drawable.male);
            } else {
                this.imageView.setImageResource(R.drawable.female);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mcontext).inflate(R.layout.adapter_voting_history, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder vh, int position) {
        vh.setData3(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.votingHistoryModelList.size();
    }

    public void setNewData(List<VoterDataNewModel> voterDataModelList) {
        this.voterDataModelList = voterDataModelList;
        notifyDataSetChanged();
    }

    public void changeLanguage(String lan) {
        this.langContext = LocaleHelper.setLocale(this.mcontext, lan);
        this.lan = lan;
        this.resources = this.langContext.getResources();
        notifyDataSetChanged();
    }

    public void setClickable(Boolean clickable) {
        this.Clickable = clickable;
    }
}
