package com.hbb20;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import java.util.ArrayList;
import java.util.List;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeViewHolder> implements SectionTitleProvider {
    CountryCodePicker codePicker;
    Context context;
    Dialog dialog;
    EditText editText_search;
    List<CCPCountry> filteredCountries;
    ImageView imgClearQuery;
    LayoutInflater inflater;
    List<CCPCountry> masterCountries;
    int preferredCountriesCount = 0;
    RelativeLayout rlQueryHolder;
    TextView textView_noResult;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CountryCodeAdapter(Context context, List<CCPCountry> countries, CountryCodePicker codePicker, RelativeLayout rlQueryHolder, EditText editText_search, TextView textView_noResult, Dialog dialog, ImageView imgClearQuery) {
        this.filteredCountries = null;
        this.masterCountries = null;
        this.context = context;
        this.masterCountries = countries;
        this.codePicker = codePicker;
        this.dialog = dialog;
        this.textView_noResult = textView_noResult;
        this.editText_search = editText_search;
        this.rlQueryHolder = rlQueryHolder;
        this.imgClearQuery = imgClearQuery;
        this.inflater = LayoutInflater.from(context);
        this.filteredCountries = getFilteredCountries("");
        setSearchBar();
    }

    private void setSearchBar() {
        if (this.codePicker.isSearchAllowed()) {
            this.imgClearQuery.setVisibility(8);
            setTextWatcher();
            setQueryClearListener();
            return;
        }
        this.rlQueryHolder.setVisibility(8);
    }

    private void setQueryClearListener() {
        this.imgClearQuery.setOnClickListener(new View.OnClickListener() { // from class: com.hbb20.CountryCodeAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CountryCodeAdapter.this.editText_search.setText("");
            }
        });
    }

    private void setTextWatcher() {
        EditText editText = this.editText_search;
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() { // from class: com.hbb20.CountryCodeAdapter.2
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    CountryCodeAdapter.this.applyQuery(s.toString());
                    if (s.toString().trim().equals("")) {
                        CountryCodeAdapter.this.imgClearQuery.setVisibility(8);
                    } else {
                        CountryCodeAdapter.this.imgClearQuery.setVisibility(0);
                    }
                }
            });
            this.editText_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.hbb20.CountryCodeAdapter.3
                @Override // android.widget.TextView.OnEditorActionListener
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId != 3) {
                        return false;
                    }
                    ((InputMethodManager) CountryCodeAdapter.this.context.getSystemService("input_method")).hideSoftInputFromWindow(CountryCodeAdapter.this.editText_search.getWindowToken(), 0);
                    return true;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyQuery(String query) {
        this.textView_noResult.setVisibility(8);
        String query2 = query.toLowerCase();
        if (query2.length() > 0 && query2.charAt(0) == '+') {
            query2 = query2.substring(1);
        }
        this.filteredCountries = getFilteredCountries(query2);
        if (this.filteredCountries.size() == 0) {
            this.textView_noResult.setVisibility(0);
        }
        notifyDataSetChanged();
    }

    private List<CCPCountry> getFilteredCountries(String query) {
        List<CCPCountry> tempCCPCountryList = new ArrayList<>();
        this.preferredCountriesCount = 0;
        if (this.codePicker.preferredCountries != null && this.codePicker.preferredCountries.size() > 0) {
            for (CCPCountry CCPCountry : this.codePicker.preferredCountries) {
                if (CCPCountry.isEligibleForQuery(query)) {
                    tempCCPCountryList.add(CCPCountry);
                    this.preferredCountriesCount++;
                }
            }
            if (tempCCPCountryList.size() > 0) {
                tempCCPCountryList.add(null);
                this.preferredCountriesCount++;
            }
        }
        for (CCPCountry CCPCountry2 : this.masterCountries) {
            if (CCPCountry2.isEligibleForQuery(query)) {
                tempCCPCountryList.add(CCPCountry2);
            }
        }
        return tempCCPCountryList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CountryCodeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CountryCodeViewHolder(this.inflater.inflate(R.layout.layout_recycler_country_tile, viewGroup, false));
    }

    public void onBindViewHolder(CountryCodeViewHolder countryCodeViewHolder, final int i) {
        countryCodeViewHolder.setCountry(this.filteredCountries.get(i));
        if (this.filteredCountries.size() <= i || this.filteredCountries.get(i) == null) {
            countryCodeViewHolder.getMainView().setOnClickListener(null);
        } else {
            countryCodeViewHolder.getMainView().setOnClickListener(new View.OnClickListener() { // from class: com.hbb20.CountryCodeAdapter.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (CountryCodeAdapter.this.filteredCountries != null && CountryCodeAdapter.this.filteredCountries.size() > i) {
                        CountryCodeAdapter.this.codePicker.onUserTappedCountry(CountryCodeAdapter.this.filteredCountries.get(i));
                    }
                    if (view != null && CountryCodeAdapter.this.filteredCountries != null && CountryCodeAdapter.this.filteredCountries.size() > i && CountryCodeAdapter.this.filteredCountries.get(i) != null) {
                        ((InputMethodManager) CountryCodeAdapter.this.context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        CountryCodeAdapter.this.dialog.dismiss();
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.filteredCountries.size();
    }

    @Override // com.futuremind.recyclerviewfastscroll.SectionTitleProvider
    public String getSectionTitle(int position) {
        CCPCountry ccpCountry = this.filteredCountries.get(position);
        if (this.preferredCountriesCount > position) {
            return "★";
        }
        if (ccpCountry != null) {
            return ccpCountry.getName().substring(0, 1);
        }
        return "☺";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class CountryCodeViewHolder extends RecyclerView.ViewHolder {
        View divider;
        ImageView imageViewFlag;
        LinearLayout linearFlagHolder;
        RelativeLayout relativeLayout_main;
        TextView textView_code;
        TextView textView_name;

        public CountryCodeViewHolder(View itemView) {
            super(itemView);
            this.relativeLayout_main = (RelativeLayout) itemView;
            this.textView_name = (TextView) this.relativeLayout_main.findViewById(R.id.textView_countryName);
            this.textView_code = (TextView) this.relativeLayout_main.findViewById(R.id.textView_code);
            this.imageViewFlag = (ImageView) this.relativeLayout_main.findViewById(R.id.image_flag);
            this.linearFlagHolder = (LinearLayout) this.relativeLayout_main.findViewById(R.id.linear_flag_holder);
            this.divider = this.relativeLayout_main.findViewById(R.id.preferenceDivider);
            if (CountryCodeAdapter.this.codePicker.getDialogTextColor() != 0) {
                this.textView_name.setTextColor(CountryCodeAdapter.this.codePicker.getDialogTextColor());
                this.textView_code.setTextColor(CountryCodeAdapter.this.codePicker.getDialogTextColor());
                this.divider.setBackgroundColor(CountryCodeAdapter.this.codePicker.getDialogTextColor());
            }
            try {
                if (CountryCodeAdapter.this.codePicker.getDialogTypeFace() == null) {
                    return;
                }
                if (CountryCodeAdapter.this.codePicker.getDialogTypeFaceStyle() != -99) {
                    this.textView_code.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace(), CountryCodeAdapter.this.codePicker.getDialogTypeFaceStyle());
                    this.textView_name.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace(), CountryCodeAdapter.this.codePicker.getDialogTypeFaceStyle());
                    return;
                }
                this.textView_code.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace());
                this.textView_name.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setCountry(CCPCountry ccpCountry) {
            if (ccpCountry != null) {
                this.divider.setVisibility(8);
                this.textView_name.setVisibility(0);
                this.textView_code.setVisibility(0);
                if (CountryCodeAdapter.this.codePicker.isCcpDialogShowPhoneCode()) {
                    this.textView_code.setVisibility(0);
                } else {
                    this.textView_code.setVisibility(8);
                }
                String countryName = "";
                if (CountryCodeAdapter.this.codePicker.getCcpDialogShowFlag() && CountryCodeAdapter.this.codePicker.ccpUseEmoji) {
                    countryName = countryName + CCPCountry.getFlagEmoji(ccpCountry) + "   ";
                }
                String countryName2 = countryName + ccpCountry.getName();
                if (CountryCodeAdapter.this.codePicker.getCcpDialogShowNameCode()) {
                    countryName2 = countryName2 + " (" + ccpCountry.getNameCode().toUpperCase() + ")";
                }
                this.textView_name.setText(countryName2);
                this.textView_code.setText("+" + ccpCountry.getPhoneCode());
                if (!CountryCodeAdapter.this.codePicker.getCcpDialogShowFlag() || CountryCodeAdapter.this.codePicker.ccpUseEmoji) {
                    this.linearFlagHolder.setVisibility(8);
                    return;
                }
                this.linearFlagHolder.setVisibility(0);
                this.imageViewFlag.setImageResource(ccpCountry.getFlagID());
                return;
            }
            this.divider.setVisibility(0);
            this.textView_name.setVisibility(8);
            this.textView_code.setVisibility(8);
            this.linearFlagHolder.setVisibility(8);
        }

        public RelativeLayout getMainView() {
            return this.relativeLayout_main;
        }
    }
}
