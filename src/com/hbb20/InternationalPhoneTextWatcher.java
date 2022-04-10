package com.hbb20;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import io.michaelrocks.libphonenumber.android.AsYouTypeFormatter;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
/* loaded from: classes3.dex */
public class InternationalPhoneTextWatcher implements TextWatcher {
    private static final String TAG = "Int'l Phone TextWatcher";
    private String countryNameCode;
    private int countryPhoneCode;
    private boolean internationalOnly;
    Editable lastFormatted;
    private AsYouTypeFormatter mFormatter;
    private boolean mSelfChange;
    private boolean mStopFormatting;
    private boolean needUpdateForCountryChange;
    PhoneNumberUtil phoneNumberUtil;

    public InternationalPhoneTextWatcher(Context context, String countryNameCode, int countryPhoneCode) {
        this(context, countryNameCode, countryPhoneCode, true);
    }

    public InternationalPhoneTextWatcher(Context context, String countryNameCode, int countryPhoneCode, boolean internationalOnly) {
        this.mSelfChange = false;
        this.lastFormatted = null;
        this.needUpdateForCountryChange = false;
        if (countryNameCode == null || countryNameCode.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.phoneNumberUtil = PhoneNumberUtil.createInstance(context);
        updateCountry(countryNameCode, countryPhoneCode);
        this.internationalOnly = internationalOnly;
    }

    public void updateCountry(String countryNameCode, int countryPhoneCode) {
        this.countryNameCode = countryNameCode;
        this.countryPhoneCode = countryPhoneCode;
        this.mFormatter = this.phoneNumberUtil.getAsYouTypeFormatter(countryNameCode);
        this.mFormatter.clear();
        Editable editable = this.lastFormatted;
        if (editable != null) {
            this.needUpdateForCountryChange = true;
            PhoneNumberUtil phoneNumberUtil = this.phoneNumberUtil;
            String onlyDigits = PhoneNumberUtil.normalizeDigitsOnly(editable);
            Editable editable2 = this.lastFormatted;
            editable2.replace(0, editable2.length(), onlyDigits, 0, onlyDigits.length());
            this.needUpdateForCountryChange = false;
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!this.mSelfChange && !this.mStopFormatting && count > 0 && hasSeparator(s, start, count) && !this.needUpdateForCountryChange) {
            stopFormatting();
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!this.mSelfChange && !this.mStopFormatting && count > 0 && hasSeparator(s, start, count)) {
            stopFormatting();
        }
    }

    @Override // android.text.TextWatcher
    public synchronized void afterTextChanged(Editable s) {
        int finalCursorPosition;
        boolean z = false;
        if (this.mStopFormatting) {
            if (s.length() != 0) {
                z = true;
            }
            this.mStopFormatting = z;
        } else if (!this.mSelfChange) {
            int selectionEnd = Selection.getSelectionEnd(s);
            boolean isCursorAtEnd = selectionEnd == s.length();
            String formatted = reformat(s);
            int finalCursorPosition2 = 0;
            if (formatted.equals(s.toString())) {
                finalCursorPosition2 = selectionEnd;
            } else if (isCursorAtEnd) {
                finalCursorPosition2 = formatted.length();
            } else {
                int digitsBeforeCursor = 0;
                int i = 0;
                while (i < s.length() && i < selectionEnd) {
                    if (PhoneNumberUtils.isNonSeparator(s.charAt(i))) {
                        digitsBeforeCursor++;
                    }
                    i++;
                }
                int i2 = 0;
                int digitPassed = 0;
                while (true) {
                    if (i2 >= formatted.length()) {
                        break;
                    } else if (digitPassed == digitsBeforeCursor) {
                        finalCursorPosition2 = i2;
                        break;
                    } else {
                        if (PhoneNumberUtils.isNonSeparator(formatted.charAt(i2))) {
                            digitPassed++;
                        }
                        i2++;
                    }
                }
            }
            if (!isCursorAtEnd) {
                while (finalCursorPosition2 - 1 > 0 && !PhoneNumberUtils.isNonSeparator(formatted.charAt(finalCursorPosition2 - 1))) {
                    finalCursorPosition2--;
                }
                finalCursorPosition = finalCursorPosition2;
            } else {
                finalCursorPosition = finalCursorPosition2;
            }
            if (formatted != null) {
                try {
                    this.mSelfChange = true;
                    s.replace(0, s.length(), formatted, 0, formatted.length());
                    this.mSelfChange = false;
                    this.lastFormatted = s;
                    Selection.setSelection(s, finalCursorPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String reformat(CharSequence s) {
        String internationalFormatted = "";
        this.mFormatter.clear();
        char lastNonSeparator = 0;
        String countryCallingCode = "+" + this.countryPhoneCode;
        if (this.internationalOnly || (s.length() > 0 && s.charAt(0) != '0')) {
            s = countryCallingCode + ((Object) s);
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (PhoneNumberUtils.isNonSeparator(c)) {
                if (lastNonSeparator != 0) {
                    internationalFormatted = this.mFormatter.inputDigit(lastNonSeparator);
                }
                lastNonSeparator = c;
            }
        }
        if (lastNonSeparator != 0) {
            internationalFormatted = this.mFormatter.inputDigit(lastNonSeparator);
        }
        String internationalFormatted2 = internationalFormatted.trim();
        if (this.internationalOnly || s.length() == 0 || s.charAt(0) != '0') {
            if (internationalFormatted2.length() <= countryCallingCode.length()) {
                internationalFormatted2 = "";
            } else if (internationalFormatted2.charAt(countryCallingCode.length()) == ' ') {
                internationalFormatted2 = internationalFormatted2.substring(countryCallingCode.length() + 1);
            } else {
                internationalFormatted2 = internationalFormatted2.substring(countryCallingCode.length());
            }
        }
        return TextUtils.isEmpty(internationalFormatted2) ? "" : internationalFormatted2;
    }

    private void stopFormatting() {
        this.mStopFormatting = true;
        this.mFormatter.clear();
    }

    private boolean hasSeparator(CharSequence s, int start, int count) {
        for (int i = start; i < start + count; i++) {
            if (!PhoneNumberUtils.isNonSeparator(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
