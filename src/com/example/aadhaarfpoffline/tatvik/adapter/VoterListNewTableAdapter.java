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
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import java.text.SimpleDateFormat;
import java.util.List;
import okhttp3.internal.cache.DiskLruCache;
/* loaded from: classes2.dex */
public class VoterListNewTableAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Boolean Clickable;
    private SimpleDateFormat formatter;
    private String lan;
    private Context langContext;
    private OnItemClickListener listener;
    private Context mcontext;
    private Resources resources;
    private UserAuth userAuth;
    private List<VoterDataNewModel> voterDataModelList;

    /* loaded from: classes2.dex */
    public interface OnItemClickListener {
        void makeLockButtonsVisible(Boolean bool);

        void onItemClick3(int i, String str);

        void onItemClick4(int i, String str);
    }

    public VoterListNewTableAdapter(Context context) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
    }

    public VoterListNewTableAdapter(Context context, OnItemClickListener listener) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
        this.listener = listener;
    }

    public VoterListNewTableAdapter(Context context, OnItemClickListener listener, List<VoterDataNewModel> voterDataModelList) {
        this.lan = "";
        this.Clickable = false;
        this.mcontext = context;
        this.listener = listener;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.voterDataModelList = voterDataModelList;
        this.userAuth = new UserAuth(this.mcontext);
        this.lan = LocaleHelper.getLanguage(this.mcontext);
        this.langContext = LocaleHelper.setLocale(this.mcontext, this.lan);
        this.resources = this.langContext.getResources();
    }

    public VoterListNewTableAdapter(Context context, OnItemClickListener listener, List<VoterDataNewModel> voterDataModelList, Boolean clickable) {
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
            v.setOnClickListener(new View.OnClickListener(VoterListNewTableAdapter.this) { // from class: com.example.aadhaarfpoffline.tatvik.adapter.VoterListNewTableAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (!VoterListNewTableAdapter.this.userAuth.ifLocked().booleanValue()) {
                        int pos = ViewHolder.this.getAdapterPosition();
                        if (pos >= 0) {
                            VoterListNewTableAdapter.this.listener.onItemClick4(pos, ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(pos)).getSlNoInWard());
                        } else {
                            Toast.makeText(VoterListNewTableAdapter.this.mcontext, "Voter with Improper position is clicked", 1).show();
                        }
                    } else {
                        Toast.makeText(VoterListNewTableAdapter.this.mcontext, "App is locked.Booth officer fingerprint required", 1).show();
                    }
                }
            });
            this.VoterName = (TextView) v.findViewById(R.id.voter_name);
            this.BoothId = (TextView) v.findViewById(R.id.boothid);
            this.District = (TextView) v.findViewById(R.id.district);
            this.BlockNo = (TextView) v.findViewById(R.id.blockNo);
            this.VoterId = (TextView) v.findViewById(R.id.voter_id);
            this.Age = (TextView) v.findViewById(R.id.age);
            this.SlNoInWard = (TextView) v.findViewById(R.id.slnoinward);
            this.imageView = (ImageView) v.findViewById(R.id.malefemale);
            this.votedOrNot = (Button) v.findViewById(R.id.votednotvoted);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData2(int position) {
            if (VoterListNewTableAdapter.this.lan.equalsIgnoreCase("en")) {
                TextView textView = this.VoterName;
                textView.setText(VoterListNewTableAdapter.this.resources.getString(R.string.name_text) + ":" + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getFM_NAME_EN() + " " + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getLASTNAME_EN());
            } else if (VoterListNewTableAdapter.this.lan.equalsIgnoreCase("hi")) {
                TextView textView2 = this.VoterName;
                textView2.setText(VoterListNewTableAdapter.this.resources.getString(R.string.name_text) + ":" + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getFM_NAME_V1() + " " + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getLASTNAME_V1());
            }
            this.BoothId.setText(((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getBlockID());
            this.District.setText(((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getBlockID());
            this.BlockNo.setText(((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getWardNo());
            TextView textView3 = this.VoterId;
            textView3.setText(VoterListNewTableAdapter.this.resources.getString(R.string.voterid_text) + ":" + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getEPIC_NO());
            TextView textView4 = this.Age;
            textView4.setText(VoterListNewTableAdapter.this.resources.getString(R.string.age_text) + ":" + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getAge());
            TextView textView5 = this.SlNoInWard;
            textView5.setText(VoterListNewTableAdapter.this.resources.getString(R.string.slnoinward) + ":" + ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getSlNoInWard());
            if (((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getGENDER().contains("M")) {
                this.imageView.setImageResource(R.drawable.man);
            } else {
                this.imageView.setImageResource(R.drawable.woman);
            }
            if (((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getVOTED() == null || ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getVOTED().isEmpty()) {
                this.votedOrNot.setText(VoterListNewTableAdapter.this.resources.getString(R.string.not_voted_text));
                this.votedOrNot.setBackgroundTintList(ContextCompat.getColorStateList(VoterListNewTableAdapter.this.mcontext, R.color.blue));
                return;
            }
            if (((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getVOTED().equalsIgnoreCase("0")) {
                this.votedOrNot.setText(VoterListNewTableAdapter.this.resources.getString(R.string.not_voted_text));
                this.votedOrNot.setBackgroundTintList(ContextCompat.getColorStateList(VoterListNewTableAdapter.this.mcontext, R.color.blue));
            } else if (((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getVOTED().equalsIgnoreCase(DiskLruCache.VERSION_1) || ((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getVOTED().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
                this.votedOrNot.setText(VoterListNewTableAdapter.this.resources.getString(R.string.voted_text));
                this.votedOrNot.setBackgroundTintList(ContextCompat.getColorStateList(VoterListNewTableAdapter.this.mcontext, R.color.green));
            } else if (((VoterDataNewModel) VoterListNewTableAdapter.this.voterDataModelList.get(position)).getVOTED().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                this.votedOrNot.setText(VoterListNewTableAdapter.this.resources.getString(R.string.not_voted_text));
                this.votedOrNot.setBackgroundTintList(ContextCompat.getColorStateList(VoterListNewTableAdapter.this.mcontext, R.color.blue));
            }
            this.votedOrNot.setBackgroundResource(R.drawable.button_bg_rounded_corners_yellow_background);
            this.votedOrNot.setBackgroundColor(VoterListNewTableAdapter.this.mcontext.getResources().getColor(R.color.greenColor));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mcontext).inflate(R.layout.adapter_voter_list_new, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder vh, int position) {
        if (position == this.voterDataModelList.size() - 1) {
            this.listener.makeLockButtonsVisible(true);
        } else {
            this.listener.makeLockButtonsVisible(true);
        }
        vh.setData2(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.voterDataModelList.size();
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
