package com.example.aadhaarfpoffline.tatvik.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataModel;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;
/* loaded from: classes2.dex */
public class VoterListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private SimpleDateFormat formatter;
    private Context langContext;
    private OnItemClickListener listener;
    private Context mcontext;
    private Resources resources;
    private List<VoterDataModel> voterDataModelList;

    /* loaded from: classes2.dex */
    public interface OnItemClickListener {
        void onItemClick(int i);

        void onItemClick2(int i);
    }

    public VoterListAdapter(Context context) {
        this.mcontext = context;
    }

    public VoterListAdapter(Context context, OnItemClickListener listener) {
        this.mcontext = context;
        this.listener = listener;
    }

    public VoterListAdapter(Context context, OnItemClickListener listener, List<VoterDataModel> voterDataModelList) {
        this.mcontext = context;
        this.listener = listener;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.voterDataModelList = voterDataModelList;
        String lan = LocaleHelper.getLanguage(this.mcontext);
        this.langContext = LocaleHelper.setLocale(this.mcontext, lan);
        this.resources = this.langContext.getResources();
        Context context2 = this.mcontext;
        Toast.makeText(context2, "language=" + lan, 1).show();
        Context context3 = this.mcontext;
        Toast.makeText(context3, "voted?=" + this.resources.getString(R.string.not_voted_text), 1).show();
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Age;
        private TextView BlockNo;
        private TextView BoothId;
        private TextView District;
        private TextView Pam;
        private TextView VoterId;
        private TextView VoterName;
        private TextView allocated_users;
        private CardView cardView;
        private TextView company_name;
        private TextView createdat;
        private TextView description;
        private TextView edit;
        private Button editButton;
        private ImageView imageView;
        private RelativeLayout relativeLayoutView;
        private TextView status;
        private TextView title;
        private Button votedOrNot;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener(VoterListAdapter.this) { // from class: com.example.aadhaarfpoffline.tatvik.adapter.VoterListAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    VoterListAdapter.this.listener.onItemClick(ViewHolder.this.getAdapterPosition());
                }
            });
            this.VoterName = (TextView) v.findViewById(R.id.voter_name);
            this.BoothId = (TextView) v.findViewById(R.id.boothid);
            this.District = (TextView) v.findViewById(R.id.district);
            this.BlockNo = (TextView) v.findViewById(R.id.blockNo);
            this.VoterId = (TextView) v.findViewById(R.id.voter_id);
            this.Age = (TextView) v.findViewById(R.id.age);
            this.imageView = (ImageView) v.findViewById(R.id.malefemale);
            this.votedOrNot = (Button) v.findViewById(R.id.votednotvoted);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData2(int position) {
            TextView textView = this.VoterName;
            textView.setText(VoterListAdapter.this.resources.getString(R.string.name_text) + ":" + ((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getVoter_name());
            this.BoothId.setText(((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getBooth_id());
            this.District.setText(((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getDistrict());
            this.BlockNo.setText(((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getBlockNo());
            TextView textView2 = this.VoterId;
            textView2.setText(VoterListAdapter.this.resources.getString(R.string.voterid_text) + ":" + ((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getVoterId());
            PrintStream printStream = System.out;
            printStream.println(VoterListAdapter.this.resources.getString(R.string.voterid_text) + "voterid=" + ((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getVoterId());
            TextView textView3 = this.Age;
            textView3.setText(VoterListAdapter.this.resources.getString(R.string.age_text) + ":" + ((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getAge());
            if (((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getGender().equalsIgnoreCase("M")) {
                this.imageView.setImageResource(R.drawable.man);
            } else {
                this.imageView.setImageResource(R.drawable.woman);
            }
            if (((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getVoted() == 0) {
                this.votedOrNot.setText(VoterListAdapter.this.resources.getString(R.string.not_voted_text));
            } else if (((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getVoted() == 1) {
                this.votedOrNot.setText(VoterListAdapter.this.resources.getString(R.string.voted_text));
            } else if (((VoterDataModel) VoterListAdapter.this.voterDataModelList.get(position)).getVoted() == 2) {
                this.votedOrNot.setText(VoterListAdapter.this.resources.getString(R.string.rejected_text));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mcontext).inflate(R.layout.adapter_voter_list_new, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder vh, int position) {
        this.voterDataModelList.size();
        vh.setData2(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.voterDataModelList.size();
    }

    public void setNewData(List<VoterDataModel> voterDataModelList) {
        this.voterDataModelList = voterDataModelList;
        notifyDataSetChanged();
    }

    public void changeLanguage(String lan) {
        this.langContext = LocaleHelper.setLocale(this.mcontext, lan);
        this.resources = this.langContext.getResources();
        notifyDataSetChanged();
    }
}
