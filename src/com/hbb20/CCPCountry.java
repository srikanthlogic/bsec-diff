package com.hbb20;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imageutils.JfifUtil;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.common.base.Ascii;
import com.hbb20.CountryCodePicker;
import com.sec.biometric.license.SecBiometricLicenseManager;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import kotlin.text.Typography;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.ws.WebSocketProtocol;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.zz.protocol.MXConStant;
import org.zz.protocol.MXErrCode;
/* loaded from: classes3.dex */
public class CCPCountry implements Comparable<CCPCountry> {
    static String dialogTitle;
    static CountryCodePicker.Language loadedLibraryMasterListLanguage;
    static List<CCPCountry> loadedLibraryMaterList;
    static String noResultFoundAckMessage;
    static String searchHintMessage;
    String englishName;
    int flagResID;
    String name;
    String nameCode;
    String phoneCode;
    static int DEFAULT_FLAG_RES = -99;
    static String TAG = "Class Country";
    private static String ANTIGUA_AND_BARBUDA_AREA_CODES = "268";
    private static String ANGUILLA_AREA_CODES = "264";
    private static String BARBADOS_AREA_CODES = "246";
    private static String BERMUDA_AREA_CODES = "441";
    private static String BAHAMAS_AREA_CODES = "242";
    private static String CANADA_AREA_CODES = "204/226/236/249/250/289/306/343/365/403/416/418/431/437/438/450/506/514/519/579/581/587/600/604/613/639/647/705/709/769/778/780/782/807/819/825/867/873/902/905/";
    private static String DOMINICA_AREA_CODES = "767";
    private static String DOMINICAN_REPUBLIC_AREA_CODES = "809/829/849";
    private static String GRENADA_AREA_CODES = "473";
    private static String JAMAICA_AREA_CODES = "876";
    private static String SAINT_KITTS_AND_NEVIS_AREA_CODES = "869";
    private static String CAYMAN_ISLANDS_AREA_CODES = "345";
    private static String SAINT_LUCIA_AREA_CODES = "758";
    private static String MONTSERRAT_AREA_CODES = "664";
    private static String PUERTO_RICO_AREA_CODES = "787";
    private static String SINT_MAARTEN_AREA_CODES = "721";
    private static String TURKS_AND_CAICOS_ISLANDS_AREA_CODES = "649";
    private static String TRINIDAD_AND_TOBAGO_AREA_CODES = "868";
    private static String SAINT_VINCENT_AND_THE_GRENADINES_AREA_CODES = "784";
    private static String BRITISH_VIRGIN_ISLANDS_AREA_CODES = "284";
    private static String US_VIRGIN_ISLANDS_AREA_CODES = "340";
    private static String ISLE_OF_MAN = "1624";

    public CCPCountry() {
        this.flagResID = DEFAULT_FLAG_RES;
    }

    public CCPCountry(String nameCode, String phoneCode, String name, int flagResID) {
        this.flagResID = DEFAULT_FLAG_RES;
        this.nameCode = nameCode.toUpperCase(Locale.ROOT);
        this.phoneCode = phoneCode;
        this.name = name;
        this.flagResID = flagResID;
    }

    static CountryCodePicker.Language getLoadedLibraryMasterListLanguage() {
        return loadedLibraryMasterListLanguage;
    }

    static void setLoadedLibraryMasterListLanguage(CountryCodePicker.Language loadedLibraryMasterListLanguage2) {
        loadedLibraryMasterListLanguage = loadedLibraryMasterListLanguage2;
    }

    public static List<CCPCountry> getLoadedLibraryMaterList() {
        return loadedLibraryMaterList;
    }

    static void setLoadedLibraryMaterList(List<CCPCountry> loadedLibraryMaterList2) {
        loadedLibraryMaterList = loadedLibraryMaterList2;
    }

    static void loadDataFromXML(Context context, CountryCodePicker.Language language) {
        List<CCPCountry> countries = new ArrayList<>();
        String tempDialogTitle = "";
        String tempSearchHint = "";
        String tempNoResultAck = "";
        try {
            try {
                XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
                Resources resources = context.getResources();
                Resources resources2 = context.getResources();
                xmlPullParser.setInput(resources.openRawResource(resources2.getIdentifier("ccp_" + language.toString().toLowerCase(Locale.ROOT), "raw", context.getPackageName())), "UTF-8");
                for (int event = xmlPullParser.getEventType(); event != 1; event = xmlPullParser.next()) {
                    String name = xmlPullParser.getName();
                    if (event != 2 && event == 3) {
                        if (name.equals("country")) {
                            CCPCountry ccpCountry = new CCPCountry();
                            ccpCountry.setNameCode(xmlPullParser.getAttributeValue(null, "name_code").toUpperCase());
                            ccpCountry.setPhoneCode(xmlPullParser.getAttributeValue(null, "phone_code"));
                            ccpCountry.setEnglishName(xmlPullParser.getAttributeValue(null, "english_name"));
                            ccpCountry.setName(xmlPullParser.getAttributeValue(null, AppMeasurementSdk.ConditionalUserProperty.NAME));
                            countries.add(ccpCountry);
                        } else if (name.equals("ccp_dialog_title")) {
                            tempDialogTitle = xmlPullParser.getAttributeValue(null, "translation");
                        } else if (name.equals("ccp_dialog_search_hint_message")) {
                            tempSearchHint = xmlPullParser.getAttributeValue(null, "translation");
                        } else if (name.equals("ccp_dialog_no_result_ack_message")) {
                            tempNoResultAck = xmlPullParser.getAttributeValue(null, "translation");
                        }
                    }
                }
                loadedLibraryMasterListLanguage = language;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (countries.size() == 0) {
            loadedLibraryMasterListLanguage = CountryCodePicker.Language.ENGLISH;
            countries = getLibraryMasterCountriesEnglish();
        }
        dialogTitle = tempDialogTitle.length() > 0 ? tempDialogTitle : "Select a country";
        searchHintMessage = tempSearchHint.length() > 0 ? tempSearchHint : "Search...";
        noResultFoundAckMessage = tempNoResultAck.length() > 0 ? tempNoResultAck : "Results not found";
        loadedLibraryMaterList = countries;
        Collections.sort(loadedLibraryMaterList);
    }

    public static String getDialogTitle(Context context, CountryCodePicker.Language language) {
        String str;
        CountryCodePicker.Language language2 = loadedLibraryMasterListLanguage;
        if (language2 == null || language2 != language || (str = dialogTitle) == null || str.length() == 0) {
            loadDataFromXML(context, language);
        }
        return dialogTitle;
    }

    public static String getSearchHintMessage(Context context, CountryCodePicker.Language language) {
        String str;
        CountryCodePicker.Language language2 = loadedLibraryMasterListLanguage;
        if (language2 == null || language2 != language || (str = searchHintMessage) == null || str.length() == 0) {
            loadDataFromXML(context, language);
        }
        return searchHintMessage;
    }

    public static String getNoResultFoundAckMessage(Context context, CountryCodePicker.Language language) {
        String str;
        CountryCodePicker.Language language2 = loadedLibraryMasterListLanguage;
        if (language2 == null || language2 != language || (str = noResultFoundAckMessage) == null || str.length() == 0) {
            loadDataFromXML(context, language);
        }
        return noResultFoundAckMessage;
    }

    public static void setDialogTitle(String dialogTitle2) {
        dialogTitle = dialogTitle2;
    }

    public static void setSearchHintMessage(String searchHintMessage2) {
        searchHintMessage = searchHintMessage2;
    }

    public static void setNoResultFoundAckMessage(String noResultFoundAckMessage2) {
        noResultFoundAckMessage = noResultFoundAckMessage2;
    }

    public static CCPCountry getCountryForCode(Context context, CountryCodePicker.Language language, List<CCPCountry> preferredCountries, String code) {
        if (preferredCountries != null && !preferredCountries.isEmpty()) {
            for (CCPCountry CCPCountry : preferredCountries) {
                if (CCPCountry.getPhoneCode().equals(code)) {
                    return CCPCountry;
                }
            }
        }
        for (CCPCountry CCPCountry2 : getLibraryMasterCountryList(context, language)) {
            if (CCPCountry2.getPhoneCode().equals(code)) {
                return CCPCountry2;
            }
        }
        return null;
    }

    public static CCPCountry getCountryForCodeFromEnglishList(String code) {
        for (CCPCountry ccpCountry : getLibraryMasterCountriesEnglish()) {
            if (ccpCountry.getPhoneCode().equals(code)) {
                return ccpCountry;
            }
        }
        return null;
    }

    public static List<CCPCountry> getCustomMasterCountryList(Context context, CountryCodePicker codePicker) {
        codePicker.refreshCustomMasterList();
        if (codePicker.customMasterCountriesList == null || codePicker.customMasterCountriesList.size() <= 0) {
            return getLibraryMasterCountryList(context, codePicker.getLanguageToApply());
        }
        return codePicker.getCustomMasterCountriesList();
    }

    public static CCPCountry getCountryForNameCodeFromCustomMasterList(Context context, List<CCPCountry> customMasterCountriesList, CountryCodePicker.Language language, String nameCode) {
        if (customMasterCountriesList == null || customMasterCountriesList.size() == 0) {
            return getCountryForNameCodeFromLibraryMasterList(context, language, nameCode);
        }
        for (CCPCountry ccpCountry : customMasterCountriesList) {
            if (ccpCountry.getNameCode().equalsIgnoreCase(nameCode)) {
                return ccpCountry;
            }
        }
        return null;
    }

    public static CCPCountry getCountryForNameCodeFromLibraryMasterList(Context context, CountryCodePicker.Language language, String nameCode) {
        for (CCPCountry ccpCountry : getLibraryMasterCountryList(context, language)) {
            if (ccpCountry.getNameCode().equalsIgnoreCase(nameCode)) {
                return ccpCountry;
            }
        }
        return null;
    }

    public static CCPCountry getCountryForNameCodeFromEnglishList(String nameCode) {
        for (CCPCountry CCPCountry : getLibraryMasterCountriesEnglish()) {
            if (CCPCountry.getNameCode().equalsIgnoreCase(nameCode)) {
                return CCPCountry;
            }
        }
        return null;
    }

    public static CCPCountry getCountryForCode(Context context, CountryCodePicker.Language language, List<CCPCountry> preferredCountries, int code) {
        return getCountryForCode(context, language, preferredCountries, code + "");
    }

    public static CCPCountry getCountryForNumber(Context context, CountryCodePicker.Language language, List<CCPCountry> preferredCountries, String fullNumber) {
        int firstDigit;
        if (fullNumber == null) {
            return null;
        }
        String fullNumber2 = fullNumber.trim();
        if (fullNumber2.length() != 0) {
            if (fullNumber2.charAt(0) == '+') {
                firstDigit = 1;
            } else {
                firstDigit = 0;
            }
            for (int i = firstDigit; i <= fullNumber2.length(); i++) {
                String code = fullNumber2.substring(firstDigit, i);
                CCPCountryGroup countryGroup = null;
                try {
                    countryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(Integer.parseInt(code));
                } catch (Exception e) {
                }
                if (countryGroup != null) {
                    int areaCodeStartsAt = code.length() + firstDigit;
                    if (fullNumber2.length() >= countryGroup.areaCodeLength + areaCodeStartsAt) {
                        return countryGroup.getCountryForAreaCode(context, language, fullNumber2.substring(areaCodeStartsAt, countryGroup.areaCodeLength + areaCodeStartsAt));
                    }
                    return getCountryForNameCodeFromLibraryMasterList(context, language, countryGroup.defaultNameCode);
                }
                CCPCountry ccpCountry = getCountryForCode(context, language, preferredCountries, code);
                if (ccpCountry != null) {
                    return ccpCountry;
                }
            }
        }
        return null;
    }

    public static CCPCountry getCountryForNumber(Context context, CountryCodePicker.Language language, String fullNumber) {
        return getCountryForNumber(context, language, null, fullNumber);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static int getFlagMasterResID(CCPCountry CCPCountry) {
        char c;
        String lowerCase = CCPCountry.getNameCode().toLowerCase();
        int hashCode = lowerCase.hashCode();
        if (hashCode == 3115) {
            if (lowerCase.equals("al")) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode == 3116) {
            if (lowerCase.equals("am")) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode == 3126) {
            if (lowerCase.equals("aw")) {
                c = '\r';
            }
            c = 65535;
        } else if (hashCode == 3127) {
            if (lowerCase.equals("ax")) {
                c = 14;
            }
            c = 65535;
        } else if (hashCode == 3135) {
            if (lowerCase.equals("ba")) {
                c = 16;
            }
            c = 65535;
        } else if (hashCode == 3136) {
            if (lowerCase.equals("bb")) {
                c = 17;
            }
            c = 65535;
        } else if (hashCode == 3159) {
            if (lowerCase.equals("by")) {
                c = '!';
            }
            c = 65535;
        } else if (hashCode == 3160) {
            if (lowerCase.equals("bz")) {
                c = Typography.quote;
            }
            c = 65535;
        } else if (hashCode == 3168) {
            if (lowerCase.equals("cc")) {
                c = Typography.dollar;
            }
            c = 65535;
        } else if (hashCode != 3169) {
            switch (hashCode) {
                case 3107:
                    if (lowerCase.equals("ad")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3108:
                    if (lowerCase.equals("ae")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3109:
                    if (lowerCase.equals("af")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3110:
                    if (lowerCase.equals("ag")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 3112:
                            if (lowerCase.equals("ai")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3118:
                            if (lowerCase.equals("ao")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3129:
                            if (lowerCase.equals("az")) {
                                c = 15;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3138:
                            if (lowerCase.equals("bd")) {
                                c = 18;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3139:
                            if (lowerCase.equals("be")) {
                                c = 19;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3140:
                            if (lowerCase.equals("bf")) {
                                c = 20;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3141:
                            if (lowerCase.equals("bg")) {
                                c = 21;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3142:
                            if (lowerCase.equals("bh")) {
                                c = 22;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3143:
                            if (lowerCase.equals("bi")) {
                                c = 23;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3144:
                            if (lowerCase.equals("bj")) {
                                c = 24;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3157:
                            if (lowerCase.equals("bw")) {
                                c = ' ';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3166:
                            if (lowerCase.equals("ca")) {
                                c = '#';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3171:
                            if (lowerCase.equals("cf")) {
                                c = Typography.amp;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3172:
                            if (lowerCase.equals("cg")) {
                                c = '\'';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3173:
                            if (lowerCase.equals("ch")) {
                                c = '(';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3174:
                            if (lowerCase.equals("ci")) {
                                c = ')';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3176:
                            if (lowerCase.equals("ck")) {
                                c = '*';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3177:
                            if (lowerCase.equals("cl")) {
                                c = '+';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3178:
                            if (lowerCase.equals("cm")) {
                                c = ',';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3179:
                            if (lowerCase.equals("cn")) {
                                c = '-';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3180:
                            if (lowerCase.equals("co")) {
                                c = FilenameUtils.EXTENSION_SEPARATOR;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3183:
                            if (lowerCase.equals("cr")) {
                                c = IOUtils.DIR_SEPARATOR_UNIX;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3186:
                            if (lowerCase.equals("cu")) {
                                c = '0';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3187:
                            if (lowerCase.equals("cv")) {
                                c = '1';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3188:
                            if (lowerCase.equals("cw")) {
                                c = '2';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3189:
                            if (lowerCase.equals("cx")) {
                                c = '3';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3190:
                            if (lowerCase.equals("cy")) {
                                c = '4';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3191:
                            if (lowerCase.equals("cz")) {
                                c = '5';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3201:
                            if (lowerCase.equals("de")) {
                                c = '6';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3206:
                            if (lowerCase.equals("dj")) {
                                c = '7';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3207:
                            if (lowerCase.equals("dk")) {
                                c = '8';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3209:
                            if (lowerCase.equals("dm")) {
                                c = '9';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3211:
                            if (lowerCase.equals("do")) {
                                c = ':';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3222:
                            if (lowerCase.equals("dz")) {
                                c = ';';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3230:
                            if (lowerCase.equals("ec")) {
                                c = Typography.less;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3232:
                            if (lowerCase.equals("ee")) {
                                c = '=';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3234:
                            if (lowerCase.equals("eg")) {
                                c = Typography.greater;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3245:
                            if (lowerCase.equals("er")) {
                                c = '?';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3246:
                            if (lowerCase.equals("es")) {
                                c = '@';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3247:
                            if (lowerCase.equals("et")) {
                                c = 'A';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3267:
                            if (lowerCase.equals("fi")) {
                                c = 'B';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3268:
                            if (lowerCase.equals("fj")) {
                                c = 'C';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3269:
                            if (lowerCase.equals("fk")) {
                                c = 'D';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3271:
                            if (lowerCase.equals("fm")) {
                                c = 'E';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3273:
                            if (lowerCase.equals("fo")) {
                                c = 'F';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3276:
                            if (lowerCase.equals("fr")) {
                                c = 'G';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3290:
                            if (lowerCase.equals("ga")) {
                                c = 'H';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3291:
                            if (lowerCase.equals("gb")) {
                                c = 'I';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3293:
                            if (lowerCase.equals("gd")) {
                                c = 'J';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3294:
                            if (lowerCase.equals("ge")) {
                                c = 'K';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3295:
                            if (lowerCase.equals("gf")) {
                                c = 'L';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3296:
                            if (lowerCase.equals("gg")) {
                                c = 'M';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3297:
                            if (lowerCase.equals("gh")) {
                                c = 'N';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3298:
                            if (lowerCase.equals("gi")) {
                                c = 'O';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3301:
                            if (lowerCase.equals("gl")) {
                                c = 'P';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3302:
                            if (lowerCase.equals("gm")) {
                                c = 'Q';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3303:
                            if (lowerCase.equals("gn")) {
                                c = 'R';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3305:
                            if (lowerCase.equals("gp")) {
                                c = 'S';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3306:
                            if (lowerCase.equals("gq")) {
                                c = 'T';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3307:
                            if (lowerCase.equals("gr")) {
                                c = 'U';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3309:
                            if (lowerCase.equals("gt")) {
                                c = 'V';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3310:
                            if (lowerCase.equals("gu")) {
                                c = 'W';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3312:
                            if (lowerCase.equals("gw")) {
                                c = 'X';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3314:
                            if (lowerCase.equals("gy")) {
                                c = 'Y';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3331:
                            if (lowerCase.equals("hk")) {
                                c = 'Z';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3334:
                            if (lowerCase.equals("hn")) {
                                c = '[';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3338:
                            if (lowerCase.equals("hr")) {
                                c = IOUtils.DIR_SEPARATOR_WINDOWS;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3340:
                            if (lowerCase.equals("ht")) {
                                c = ']';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3341:
                            if (lowerCase.equals("hu")) {
                                c = '^';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3355:
                            if (lowerCase.equals("id")) {
                                c = '_';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3356:
                            if (lowerCase.equals("ie")) {
                                c = '`';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3363:
                            if (lowerCase.equals("il")) {
                                c = 'a';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3364:
                            if (lowerCase.equals("im")) {
                                c = 'b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3365:
                            if (lowerCase.equals("in")) {
                                c = 'd';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3366:
                            if (lowerCase.equals("io")) {
                                c = 'e';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3368:
                            if (lowerCase.equals("iq")) {
                                c = 'f';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3369:
                            if (lowerCase.equals("ir")) {
                                c = 'g';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3370:
                            if (lowerCase.equals("is")) {
                                c = 'c';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3371:
                            if (lowerCase.equals("it")) {
                                c = 'h';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3387:
                            if (lowerCase.equals("je")) {
                                c = 'i';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3395:
                            if (lowerCase.equals("jm")) {
                                c = 'j';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3397:
                            if (lowerCase.equals("jo")) {
                                c = 'k';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3398:
                            if (lowerCase.equals("jp")) {
                                c = 'l';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3418:
                            if (lowerCase.equals("ke")) {
                                c = 'm';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3420:
                            if (lowerCase.equals("kg")) {
                                c = 'n';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3421:
                            if (lowerCase.equals("kh")) {
                                c = 'o';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3422:
                            if (lowerCase.equals("ki")) {
                                c = 'p';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3426:
                            if (lowerCase.equals("km")) {
                                c = 'q';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3427:
                            if (lowerCase.equals("kn")) {
                                c = 'r';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3429:
                            if (lowerCase.equals("kp")) {
                                c = 's';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3431:
                            if (lowerCase.equals("kr")) {
                                c = 't';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3436:
                            if (lowerCase.equals("kw")) {
                                c = 'u';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3438:
                            if (lowerCase.equals("ky")) {
                                c = 'v';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3439:
                            if (lowerCase.equals("kz")) {
                                c = 'w';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3445:
                            if (lowerCase.equals("la")) {
                                c = 'x';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3446:
                            if (lowerCase.equals("lb")) {
                                c = 'y';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3447:
                            if (lowerCase.equals("lc")) {
                                c = 'z';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3453:
                            if (lowerCase.equals("li")) {
                                c = '{';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3455:
                            if (lowerCase.equals("lk")) {
                                c = '|';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3462:
                            if (lowerCase.equals("lr")) {
                                c = '}';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3463:
                            if (lowerCase.equals("ls")) {
                                c = '~';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3464:
                            if (lowerCase.equals("lt")) {
                                c = Ascii.MAX;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3465:
                            if (lowerCase.equals("lu")) {
                                c = 128;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3466:
                            if (lowerCase.equals("lv")) {
                                c = 129;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3469:
                            if (lowerCase.equals("ly")) {
                                c = 130;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3476:
                            if (lowerCase.equals("ma")) {
                                c = 131;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3478:
                            if (lowerCase.equals("mc")) {
                                c = 132;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3479:
                            if (lowerCase.equals("md")) {
                                c = 133;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3480:
                            if (lowerCase.equals("me")) {
                                c = 134;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3481:
                            if (lowerCase.equals("mf")) {
                                c = 135;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3482:
                            if (lowerCase.equals("mg")) {
                                c = 136;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3483:
                            if (lowerCase.equals("mh")) {
                                c = 137;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3486:
                            if (lowerCase.equals("mk")) {
                                c = 138;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3487:
                            if (lowerCase.equals("ml")) {
                                c = 139;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3488:
                            if (lowerCase.equals("mm")) {
                                c = 140;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3489:
                            if (lowerCase.equals("mn")) {
                                c = 141;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3490:
                            if (lowerCase.equals("mo")) {
                                c = 142;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3491:
                            if (lowerCase.equals("mp")) {
                                c = 143;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3492:
                            if (lowerCase.equals("mq")) {
                                c = 144;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3493:
                            if (lowerCase.equals("mr")) {
                                c = 145;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3494:
                            if (lowerCase.equals("ms")) {
                                c = 146;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3495:
                            if (lowerCase.equals("mt")) {
                                c = 147;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3496:
                            if (lowerCase.equals("mu")) {
                                c = 148;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3497:
                            if (lowerCase.equals("mv")) {
                                c = 149;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3498:
                            if (lowerCase.equals("mw")) {
                                c = 150;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3499:
                            if (lowerCase.equals("mx")) {
                                c = 151;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3500:
                            if (lowerCase.equals("my")) {
                                c = 152;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3501:
                            if (lowerCase.equals("mz")) {
                                c = 153;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3507:
                            if (lowerCase.equals("na")) {
                                c = 154;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3509:
                            if (lowerCase.equals("nc")) {
                                c = 155;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3511:
                            if (lowerCase.equals("ne")) {
                                c = 156;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3512:
                            if (lowerCase.equals("nf")) {
                                c = 157;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3513:
                            if (lowerCase.equals("ng")) {
                                c = 158;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3515:
                            if (lowerCase.equals("ni")) {
                                c = 159;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3518:
                            if (lowerCase.equals("nl")) {
                                c = Typography.nbsp;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3521:
                            if (lowerCase.equals("no")) {
                                c = 161;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3522:
                            if (lowerCase.equals("np")) {
                                c = Typography.cent;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3524:
                            if (lowerCase.equals("nr")) {
                                c = Typography.pound;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3527:
                            if (lowerCase.equals("nu")) {
                                c = 164;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3532:
                            if (lowerCase.equals("nz")) {
                                c = 165;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3550:
                            if (lowerCase.equals("om")) {
                                c = 166;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3569:
                            if (lowerCase.equals("pa")) {
                                c = Typography.section;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3573:
                            if (lowerCase.equals("pe")) {
                                c = 168;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3574:
                            if (lowerCase.equals("pf")) {
                                c = Typography.copyright;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3575:
                            if (lowerCase.equals("pg")) {
                                c = 170;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3576:
                            if (lowerCase.equals("ph")) {
                                c = Typography.leftGuillemete;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3579:
                            if (lowerCase.equals("pk")) {
                                c = 172;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3580:
                            if (lowerCase.equals("pl")) {
                                c = 173;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3581:
                            if (lowerCase.equals("pm")) {
                                c = Typography.registered;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3582:
                            if (lowerCase.equals("pn")) {
                                c = 175;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3586:
                            if (lowerCase.equals("pr")) {
                                c = Typography.degree;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3587:
                            if (lowerCase.equals("ps")) {
                                c = Typography.plusMinus;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3588:
                            if (lowerCase.equals("pt")) {
                                c = 178;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3591:
                            if (lowerCase.equals("pw")) {
                                c = 179;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3593:
                            if (lowerCase.equals("py")) {
                                c = 180;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3600:
                            if (lowerCase.equals("qa")) {
                                c = 181;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3635:
                            if (lowerCase.equals("re")) {
                                c = Typography.paragraph;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3645:
                            if (lowerCase.equals("ro")) {
                                c = Typography.middleDot;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3649:
                            if (lowerCase.equals("rs")) {
                                c = 184;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3651:
                            if (lowerCase.equals("ru")) {
                                c = 185;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3653:
                            if (lowerCase.equals("rw")) {
                                c = 186;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3662:
                            if (lowerCase.equals("sa")) {
                                c = Typography.rightGuillemete;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3663:
                            if (lowerCase.equals("sb")) {
                                c = 188;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3664:
                            if (lowerCase.equals("sc")) {
                                c = Typography.half;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3665:
                            if (lowerCase.equals("sd")) {
                                c = 190;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3666:
                            if (lowerCase.equals("se")) {
                                c = 191;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3668:
                            if (lowerCase.equals("sg")) {
                                c = 192;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3669:
                            if (lowerCase.equals("sh")) {
                                c = 193;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3670:
                            if (lowerCase.equals("si")) {
                                c = 194;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3672:
                            if (lowerCase.equals("sk")) {
                                c = 195;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3673:
                            if (lowerCase.equals("sl")) {
                                c = 196;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3674:
                            if (lowerCase.equals("sm")) {
                                c = 197;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3675:
                            if (lowerCase.equals("sn")) {
                                c = 198;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3676:
                            if (lowerCase.equals("so")) {
                                c = 199;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3679:
                            if (lowerCase.equals("sr")) {
                                c = 200;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3680:
                            if (lowerCase.equals("ss")) {
                                c = 201;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3681:
                            if (lowerCase.equals("st")) {
                                c = 202;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3683:
                            if (lowerCase.equals("sv")) {
                                c = 203;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3685:
                            if (lowerCase.equals("sx")) {
                                c = 204;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3686:
                            if (lowerCase.equals("sy")) {
                                c = 205;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3687:
                            if (lowerCase.equals("sz")) {
                                c = 206;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3695:
                            if (lowerCase.equals("tc")) {
                                c = 207;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3696:
                            if (lowerCase.equals("td")) {
                                c = 208;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3699:
                            if (lowerCase.equals("tg")) {
                                c = 209;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3700:
                            if (lowerCase.equals("th")) {
                                c = 210;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3702:
                            if (lowerCase.equals("tj")) {
                                c = 211;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3703:
                            if (lowerCase.equals("tk")) {
                                c = 212;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3704:
                            if (lowerCase.equals("tl")) {
                                c = 213;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3705:
                            if (lowerCase.equals("tm")) {
                                c = 214;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3706:
                            if (lowerCase.equals("tn")) {
                                c = Typography.times;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3707:
                            if (lowerCase.equals("to")) {
                                c = 216;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3710:
                            if (lowerCase.equals("tr")) {
                                c = 217;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3712:
                            if (lowerCase.equals("tt")) {
                                c = 218;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3714:
                            if (lowerCase.equals("tv")) {
                                c = 219;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3715:
                            if (lowerCase.equals("tw")) {
                                c = 220;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3718:
                            if (lowerCase.equals("tz")) {
                                c = 221;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3724:
                            if (lowerCase.equals("ua")) {
                                c = 222;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3730:
                            if (lowerCase.equals("ug")) {
                                c = 223;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3742:
                            if (lowerCase.equals("us")) {
                                c = 224;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3748:
                            if (lowerCase.equals("uy")) {
                                c = 225;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3749:
                            if (lowerCase.equals("uz")) {
                                c = 226;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3755:
                            if (lowerCase.equals("va")) {
                                c = 227;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3757:
                            if (lowerCase.equals("vc")) {
                                c = 228;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3759:
                            if (lowerCase.equals("ve")) {
                                c = 229;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3761:
                            if (lowerCase.equals("vg")) {
                                c = 230;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3763:
                            if (lowerCase.equals("vi")) {
                                c = 231;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3768:
                            if (lowerCase.equals("vn")) {
                                c = 232;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3775:
                            if (lowerCase.equals("vu")) {
                                c = 233;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3791:
                            if (lowerCase.equals("wf")) {
                                c = 234;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3804:
                            if (lowerCase.equals("ws")) {
                                c = 235;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3827:
                            if (lowerCase.equals("xk")) {
                                c = 236;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3852:
                            if (lowerCase.equals("ye")) {
                                c = 237;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3867:
                            if (lowerCase.equals("yt")) {
                                c = 238;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3879:
                            if (lowerCase.equals("za")) {
                                c = 239;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3891:
                            if (lowerCase.equals("zm")) {
                                c = 240;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3901:
                            if (lowerCase.equals("zw")) {
                                c = 241;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            switch (hashCode) {
                                case 3120:
                                    if (lowerCase.equals("aq")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3121:
                                    if (lowerCase.equals("ar")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3122:
                                    if (lowerCase.equals("as")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3123:
                                    if (lowerCase.equals("at")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3124:
                                    if (lowerCase.equals("au")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    switch (hashCode) {
                                        case 3146:
                                            if (lowerCase.equals("bl")) {
                                                c = 25;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3147:
                                            if (lowerCase.equals("bm")) {
                                                c = 26;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3148:
                                            if (lowerCase.equals("bn")) {
                                                c = 27;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3149:
                                            if (lowerCase.equals("bo")) {
                                                c = 28;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            switch (hashCode) {
                                                case 3152:
                                                    if (lowerCase.equals("br")) {
                                                        c = 29;
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                case 3153:
                                                    if (lowerCase.equals("bs")) {
                                                        c = 30;
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                case 3154:
                                                    if (lowerCase.equals("bt")) {
                                                        c = 31;
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                default:
                                                    c = 65535;
                                                    break;
                                            }
                                    }
                            }
                    }
            }
        } else {
            if (lowerCase.equals("cd")) {
                c = '%';
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return R.drawable.flag_andorra;
            case 1:
                return R.drawable.flag_uae;
            case 2:
                return R.drawable.flag_afghanistan;
            case 3:
                return R.drawable.flag_antigua_and_barbuda;
            case 4:
                return R.drawable.flag_anguilla;
            case 5:
                return R.drawable.flag_albania;
            case 6:
                return R.drawable.flag_armenia;
            case 7:
                return R.drawable.flag_angola;
            case '\b':
                return R.drawable.flag_antarctica;
            case '\t':
                return R.drawable.flag_argentina;
            case '\n':
                return R.drawable.flag_american_samoa;
            case 11:
                return R.drawable.flag_austria;
            case '\f':
                return R.drawable.flag_australia;
            case '\r':
                return R.drawable.flag_aruba;
            case 14:
                return R.drawable.flag_aland;
            case 15:
                return R.drawable.flag_azerbaijan;
            case 16:
                return R.drawable.flag_bosnia;
            case 17:
                return R.drawable.flag_barbados;
            case 18:
                return R.drawable.flag_bangladesh;
            case 19:
                return R.drawable.flag_belgium;
            case 20:
                return R.drawable.flag_burkina_faso;
            case 21:
                return R.drawable.flag_bulgaria;
            case 22:
                return R.drawable.flag_bahrain;
            case 23:
                return R.drawable.flag_burundi;
            case 24:
                return R.drawable.flag_benin;
            case 25:
                return R.drawable.flag_saint_barthelemy;
            case 26:
                return R.drawable.flag_bermuda;
            case 27:
                return R.drawable.flag_brunei;
            case 28:
                return R.drawable.flag_bolivia;
            case 29:
                return R.drawable.flag_brazil;
            case 30:
                return R.drawable.flag_bahamas;
            case 31:
                return R.drawable.flag_bhutan;
            case ' ':
                return R.drawable.flag_botswana;
            case '!':
                return R.drawable.flag_belarus;
            case '\"':
                return R.drawable.flag_belize;
            case '#':
                return R.drawable.flag_canada;
            case '$':
                return R.drawable.flag_cocos;
            case '%':
                return R.drawable.flag_democratic_republic_of_the_congo;
            case '&':
                return R.drawable.flag_central_african_republic;
            case '\'':
                return R.drawable.flag_republic_of_the_congo;
            case '(':
                return R.drawable.flag_switzerland;
            case ')':
                return R.drawable.flag_cote_divoire;
            case '*':
                return R.drawable.flag_cook_islands;
            case '+':
                return R.drawable.flag_chile;
            case ',':
                return R.drawable.flag_cameroon;
            case '-':
                return R.drawable.flag_china;
            case '.':
                return R.drawable.flag_colombia;
            case '/':
                return R.drawable.flag_costa_rica;
            case '0':
                return R.drawable.flag_cuba;
            case '1':
                return R.drawable.flag_cape_verde;
            case '2':
                return R.drawable.flag_curacao;
            case '3':
                return R.drawable.flag_christmas_island;
            case '4':
                return R.drawable.flag_cyprus;
            case '5':
                return R.drawable.flag_czech_republic;
            case '6':
                return R.drawable.flag_germany;
            case '7':
                return R.drawable.flag_djibouti;
            case '8':
                return R.drawable.flag_denmark;
            case '9':
                return R.drawable.flag_dominica;
            case ':':
                return R.drawable.flag_dominican_republic;
            case ';':
                return R.drawable.flag_algeria;
            case '<':
                return R.drawable.flag_ecuador;
            case '=':
                return R.drawable.flag_estonia;
            case '>':
                return R.drawable.flag_egypt;
            case '?':
                return R.drawable.flag_eritrea;
            case '@':
                return R.drawable.flag_spain;
            case 'A':
                return R.drawable.flag_ethiopia;
            case 'B':
                return R.drawable.flag_finland;
            case 'C':
                return R.drawable.flag_fiji;
            case 'D':
                return R.drawable.flag_falkland_islands;
            case 'E':
                return R.drawable.flag_micronesia;
            case 'F':
                return R.drawable.flag_faroe_islands;
            case 'G':
                return R.drawable.flag_france;
            case 'H':
                return R.drawable.flag_gabon;
            case 'I':
                return R.drawable.flag_united_kingdom;
            case 'J':
                return R.drawable.flag_grenada;
            case 'K':
                return R.drawable.flag_georgia;
            case 'L':
                return R.drawable.flag_guyane;
            case 'M':
                return R.drawable.flag_guernsey;
            case 'N':
                return R.drawable.flag_ghana;
            case 'O':
                return R.drawable.flag_gibraltar;
            case 'P':
                return R.drawable.flag_greenland;
            case 'Q':
                return R.drawable.flag_gambia;
            case 'R':
                return R.drawable.flag_guinea;
            case 'S':
                return R.drawable.flag_guadeloupe;
            case 'T':
                return R.drawable.flag_equatorial_guinea;
            case 'U':
                return R.drawable.flag_greece;
            case 'V':
                return R.drawable.flag_guatemala;
            case 'W':
                return R.drawable.flag_guam;
            case 'X':
                return R.drawable.flag_guinea_bissau;
            case 'Y':
                return R.drawable.flag_guyana;
            case 'Z':
                return R.drawable.flag_hong_kong;
            case '[':
                return R.drawable.flag_honduras;
            case '\\':
                return R.drawable.flag_croatia;
            case ']':
                return R.drawable.flag_haiti;
            case '^':
                return R.drawable.flag_hungary;
            case '_':
                return R.drawable.flag_indonesia;
            case '`':
                return R.drawable.flag_ireland;
            case 'a':
                return R.drawable.flag_israel;
            case 'b':
                return R.drawable.flag_isleof_man;
            case 'c':
                return R.drawable.flag_iceland;
            case 'd':
                return R.drawable.flag_india;
            case 'e':
                return R.drawable.flag_british_indian_ocean_territory;
            case 'f':
                return R.drawable.flag_iraq_new;
            case 'g':
                return R.drawable.flag_iran;
            case 'h':
                return R.drawable.flag_italy;
            case 'i':
                return R.drawable.flag_jersey;
            case 'j':
                return R.drawable.flag_jamaica;
            case 'k':
                return R.drawable.flag_jordan;
            case 'l':
                return R.drawable.flag_japan;
            case 'm':
                return R.drawable.flag_kenya;
            case 'n':
                return R.drawable.flag_kyrgyzstan;
            case 'o':
                return R.drawable.flag_cambodia;
            case 'p':
                return R.drawable.flag_kiribati;
            case 'q':
                return R.drawable.flag_comoros;
            case 'r':
                return R.drawable.flag_saint_kitts_and_nevis;
            case 's':
                return R.drawable.flag_north_korea;
            case 't':
                return R.drawable.flag_south_korea;
            case 'u':
                return R.drawable.flag_kuwait;
            case 'v':
                return R.drawable.flag_cayman_islands;
            case 'w':
                return R.drawable.flag_kazakhstan;
            case 'x':
                return R.drawable.flag_laos;
            case 'y':
                return R.drawable.flag_lebanon;
            case 'z':
                return R.drawable.flag_saint_lucia;
            case '{':
                return R.drawable.flag_liechtenstein;
            case '|':
                return R.drawable.flag_sri_lanka;
            case '}':
                return R.drawable.flag_liberia;
            case WebSocketProtocol.PAYLOAD_SHORT:
                return R.drawable.flag_lesotho;
            case 127:
                return R.drawable.flag_lithuania;
            case 128:
                return R.drawable.flag_luxembourg;
            case 129:
                return R.drawable.flag_latvia;
            case 130:
                return R.drawable.flag_libya;
            case 131:
                return R.drawable.flag_morocco;
            case 132:
                return R.drawable.flag_monaco;
            case 133:
                return R.drawable.flag_moldova;
            case 134:
                return R.drawable.flag_of_montenegro;
            case 135:
                return R.drawable.flag_saint_martin;
            case 136:
                return R.drawable.flag_madagascar;
            case 137:
                return R.drawable.flag_marshall_islands;
            case 138:
                return R.drawable.flag_macedonia;
            case 139:
                return R.drawable.flag_mali;
            case 140:
                return R.drawable.flag_myanmar;
            case 141:
                return R.drawable.flag_mongolia;
            case 142:
                return R.drawable.flag_macao;
            case 143:
                return R.drawable.flag_northern_mariana_islands;
            case 144:
                return R.drawable.flag_martinique;
            case 145:
                return R.drawable.flag_mauritania;
            case 146:
                return R.drawable.flag_montserrat;
            case 147:
                return R.drawable.flag_malta;
            case 148:
                return R.drawable.flag_mauritius;
            case 149:
                return R.drawable.flag_maldives;
            case 150:
                return R.drawable.flag_malawi;
            case 151:
                return R.drawable.flag_mexico;
            case 152:
                return R.drawable.flag_malaysia;
            case 153:
                return R.drawable.flag_mozambique;
            case 154:
                return R.drawable.flag_namibia;
            case 155:
                return R.drawable.flag_new_caledonia;
            case 156:
                return R.drawable.flag_niger;
            case 157:
                return R.drawable.flag_norfolk_island;
            case 158:
                return R.drawable.flag_nigeria;
            case 159:
                return R.drawable.flag_nicaragua;
            case 160:
                return R.drawable.flag_netherlands;
            case MXErrCode.ERR_FAILED_TEE:
                return R.drawable.flag_norway;
            case MXErrCode.ERR_KEY_LEN_TEE:
                return R.drawable.flag_nepal;
            case MXErrCode.ERR_DATA_LEN_TEE:
                return R.drawable.flag_nauru;
            case MXErrCode.ERR_KEY_ENCRYPT_TEE:
                return R.drawable.flag_niue;
            case MXErrCode.ERR_KEY_DECRYPT_TEE:
                return R.drawable.flag_new_zealand;
            case MXErrCode.ERR_NO_CERT_TEE:
                return R.drawable.flag_oman;
            case MXErrCode.ERR_NO_KEY_TEE:
                return R.drawable.flag_panama;
            case MXErrCode.ERR_NO_PIN_TEE:
                return R.drawable.flag_peru;
            case MXErrCode.ERR_INVALID_PARAMETER_TEE:
                return R.drawable.flag_french_polynesia;
            case MXErrCode.ERR_VERIFIED_FAILED_TEE:
                return R.drawable.flag_papua_new_guinea;
            case MXErrCode.ERR_NO_CMD_TEE:
                return R.drawable.flag_philippines;
            case MXErrCode.ERR_NO_ALG_TEE:
                return R.drawable.flag_pakistan;
            case 173:
                return R.drawable.flag_poland;
            case 174:
                return R.drawable.flag_saint_pierre;
            case 175:
                return R.drawable.flag_pitcairn_islands;
            case 176:
                return R.drawable.flag_puerto_rico;
            case 177:
                return R.drawable.flag_palestine;
            case 178:
                return R.drawable.flag_portugal;
            case 179:
                return R.drawable.flag_palau;
            case RotationOptions.ROTATE_180:
                return R.drawable.flag_paraguay;
            case 181:
                return R.drawable.flag_qatar;
            case 182:
                return R.drawable.flag_martinique;
            case 183:
                return R.drawable.flag_romania;
            case 184:
                return R.drawable.flag_serbia;
            case 185:
                return R.drawable.flag_russian_federation;
            case 186:
                return R.drawable.flag_rwanda;
            case 187:
                return R.drawable.flag_saudi_arabia;
            case 188:
                return R.drawable.flag_soloman_islands;
            case 189:
                return R.drawable.flag_seychelles;
            case 190:
                return R.drawable.flag_sudan;
            case 191:
                return R.drawable.flag_sweden;
            case JfifUtil.MARKER_SOFn:
                return R.drawable.flag_singapore;
            case 193:
                return R.drawable.flag_saint_helena;
            case 194:
                return R.drawable.flag_slovenia;
            case 195:
                return R.drawable.flag_slovakia;
            case 196:
                return R.drawable.flag_sierra_leone;
            case 197:
                return R.drawable.flag_san_marino;
            case 198:
                return R.drawable.flag_senegal;
            case 199:
                return R.drawable.flag_somalia;
            case ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION:
                return R.drawable.flag_suriname;
            case SecBiometricLicenseManager.ERROR_INVALID_LICENSE:
                return R.drawable.flag_south_sudan;
            case 202:
                return R.drawable.flag_sao_tome_and_principe;
            case SecBiometricLicenseManager.ERROR_LICENSE_TERMINATED:
                return R.drawable.flag_el_salvador;
            case SecBiometricLicenseManager.ERROR_INVALID_PACKAGE_NAME:
                return R.drawable.flag_sint_maarten;
            case 205:
                return R.drawable.flag_syria;
            case 206:
                return R.drawable.flag_swaziland;
            case 207:
                return R.drawable.flag_turks_and_caicos_islands;
            case JfifUtil.MARKER_RST0:
                return R.drawable.flag_chad;
            case 209:
                return R.drawable.flag_togo;
            case 210:
                return R.drawable.flag_thailand;
            case 211:
                return R.drawable.flag_tajikistan;
            case 212:
                return R.drawable.flag_tokelau;
            case 213:
                return R.drawable.flag_timor_leste;
            case 214:
                return R.drawable.flag_turkmenistan;
            case JfifUtil.MARKER_RST7:
                return R.drawable.flag_tunisia;
            case JfifUtil.MARKER_SOI:
                return R.drawable.flag_tonga;
            case JfifUtil.MARKER_EOI:
                return R.drawable.flag_turkey;
            case JfifUtil.MARKER_SOS:
                return R.drawable.flag_trinidad_and_tobago;
            case 219:
                return R.drawable.flag_tuvalu;
            case 220:
                return R.drawable.flag_taiwan;
            case 221:
                return R.drawable.flag_tanzania;
            case 222:
                return R.drawable.flag_ukraine;
            case 223:
                return R.drawable.flag_uganda;
            case 224:
                return R.drawable.flag_united_states_of_america;
            case JfifUtil.MARKER_APP1:
                return R.drawable.flag_uruguay;
            case 226:
                return R.drawable.flag_uzbekistan;
            case 227:
                return R.drawable.flag_vatican_city;
            case 228:
                return R.drawable.flag_saint_vicent_and_the_grenadines;
            case 229:
                return R.drawable.flag_venezuela;
            case 230:
                return R.drawable.flag_british_virgin_islands;
            case 231:
                return R.drawable.flag_us_virgin_islands;
            case 232:
                return R.drawable.flag_vietnam;
            case 233:
                return R.drawable.flag_vanuatu;
            case 234:
                return R.drawable.flag_wallis_and_futuna;
            case 235:
                return R.drawable.flag_samoa;
            case 236:
                return R.drawable.flag_kosovo;
            case 237:
                return R.drawable.flag_yemen;
            case 238:
                return R.drawable.flag_martinique;
            case 239:
                return R.drawable.flag_south_africa;
            case 240:
                return R.drawable.flag_zambia;
            case 241:
                return R.drawable.flag_zimbabwe;
            default:
                return R.drawable.flag_transparent;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getFlagEmoji(CCPCountry CCPCountry) {
        char c;
        String lowerCase = CCPCountry.getNameCode().toLowerCase();
        int hashCode = lowerCase.hashCode();
        if (hashCode == 3115) {
            if (lowerCase.equals("al")) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode == 3116) {
            if (lowerCase.equals("am")) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode == 3126) {
            if (lowerCase.equals("aw")) {
                c = '\r';
            }
            c = 65535;
        } else if (hashCode == 3127) {
            if (lowerCase.equals("ax")) {
                c = 14;
            }
            c = 65535;
        } else if (hashCode == 3135) {
            if (lowerCase.equals("ba")) {
                c = 16;
            }
            c = 65535;
        } else if (hashCode == 3136) {
            if (lowerCase.equals("bb")) {
                c = 17;
            }
            c = 65535;
        } else if (hashCode == 3156) {
            if (lowerCase.equals("bv")) {
                c = '!';
            }
            c = 65535;
        } else if (hashCode == 3157) {
            if (lowerCase.equals("bw")) {
                c = Typography.quote;
            }
            c = 65535;
        } else if (hashCode == 3159) {
            if (lowerCase.equals("by")) {
                c = '#';
            }
            c = 65535;
        } else if (hashCode != 3160) {
            switch (hashCode) {
                case 3107:
                    if (lowerCase.equals("ad")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3108:
                    if (lowerCase.equals("ae")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3109:
                    if (lowerCase.equals("af")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3110:
                    if (lowerCase.equals("ag")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 3112:
                            if (lowerCase.equals("ai")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3118:
                            if (lowerCase.equals("ao")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3129:
                            if (lowerCase.equals("az")) {
                                c = 15;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3138:
                            if (lowerCase.equals("bd")) {
                                c = 18;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3139:
                            if (lowerCase.equals("be")) {
                                c = 19;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3140:
                            if (lowerCase.equals("bf")) {
                                c = 20;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3141:
                            if (lowerCase.equals("bg")) {
                                c = 21;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3142:
                            if (lowerCase.equals("bh")) {
                                c = 22;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3143:
                            if (lowerCase.equals("bi")) {
                                c = 23;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3144:
                            if (lowerCase.equals("bj")) {
                                c = 24;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3166:
                            if (lowerCase.equals("ca")) {
                                c = '%';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3168:
                            if (lowerCase.equals("cc")) {
                                c = Typography.amp;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3169:
                            if (lowerCase.equals("cd")) {
                                c = '\'';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3171:
                            if (lowerCase.equals("cf")) {
                                c = '(';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3172:
                            if (lowerCase.equals("cg")) {
                                c = ')';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3173:
                            if (lowerCase.equals("ch")) {
                                c = '*';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3174:
                            if (lowerCase.equals("ci")) {
                                c = '+';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3176:
                            if (lowerCase.equals("ck")) {
                                c = ',';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3177:
                            if (lowerCase.equals("cl")) {
                                c = '-';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3178:
                            if (lowerCase.equals("cm")) {
                                c = FilenameUtils.EXTENSION_SEPARATOR;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3179:
                            if (lowerCase.equals("cn")) {
                                c = IOUtils.DIR_SEPARATOR_UNIX;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3180:
                            if (lowerCase.equals("co")) {
                                c = '0';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3183:
                            if (lowerCase.equals("cr")) {
                                c = '1';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3186:
                            if (lowerCase.equals("cu")) {
                                c = '2';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3187:
                            if (lowerCase.equals("cv")) {
                                c = '3';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3188:
                            if (lowerCase.equals("cw")) {
                                c = '4';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3189:
                            if (lowerCase.equals("cx")) {
                                c = '5';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3190:
                            if (lowerCase.equals("cy")) {
                                c = '6';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3191:
                            if (lowerCase.equals("cz")) {
                                c = '7';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3201:
                            if (lowerCase.equals("de")) {
                                c = '8';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3206:
                            if (lowerCase.equals("dj")) {
                                c = '9';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3207:
                            if (lowerCase.equals("dk")) {
                                c = ':';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3209:
                            if (lowerCase.equals("dm")) {
                                c = ';';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3211:
                            if (lowerCase.equals("do")) {
                                c = Typography.less;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3222:
                            if (lowerCase.equals("dz")) {
                                c = '=';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3230:
                            if (lowerCase.equals("ec")) {
                                c = Typography.greater;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3232:
                            if (lowerCase.equals("ee")) {
                                c = '?';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3234:
                            if (lowerCase.equals("eg")) {
                                c = '@';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3235:
                            if (lowerCase.equals("eh")) {
                                c = 'A';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3245:
                            if (lowerCase.equals("er")) {
                                c = 'B';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3246:
                            if (lowerCase.equals("es")) {
                                c = 'C';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3247:
                            if (lowerCase.equals("et")) {
                                c = 'D';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3267:
                            if (lowerCase.equals("fi")) {
                                c = 'E';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3268:
                            if (lowerCase.equals("fj")) {
                                c = 'F';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3269:
                            if (lowerCase.equals("fk")) {
                                c = 'G';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3271:
                            if (lowerCase.equals("fm")) {
                                c = 'H';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3273:
                            if (lowerCase.equals("fo")) {
                                c = 'I';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3276:
                            if (lowerCase.equals("fr")) {
                                c = 'J';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3290:
                            if (lowerCase.equals("ga")) {
                                c = 'K';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3291:
                            if (lowerCase.equals("gb")) {
                                c = 'L';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3293:
                            if (lowerCase.equals("gd")) {
                                c = 'M';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3294:
                            if (lowerCase.equals("ge")) {
                                c = 'N';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3295:
                            if (lowerCase.equals("gf")) {
                                c = 'O';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3296:
                            if (lowerCase.equals("gg")) {
                                c = 'P';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3297:
                            if (lowerCase.equals("gh")) {
                                c = 'Q';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3298:
                            if (lowerCase.equals("gi")) {
                                c = 'R';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3301:
                            if (lowerCase.equals("gl")) {
                                c = 'S';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3302:
                            if (lowerCase.equals("gm")) {
                                c = 'T';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3303:
                            if (lowerCase.equals("gn")) {
                                c = 'U';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3305:
                            if (lowerCase.equals("gp")) {
                                c = 'V';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3306:
                            if (lowerCase.equals("gq")) {
                                c = 'W';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3307:
                            if (lowerCase.equals("gr")) {
                                c = 'X';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3308:
                            if (lowerCase.equals("gs")) {
                                c = 'Y';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3309:
                            if (lowerCase.equals("gt")) {
                                c = 'Z';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3310:
                            if (lowerCase.equals("gu")) {
                                c = '[';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3312:
                            if (lowerCase.equals("gw")) {
                                c = IOUtils.DIR_SEPARATOR_WINDOWS;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3314:
                            if (lowerCase.equals("gy")) {
                                c = ']';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3331:
                            if (lowerCase.equals("hk")) {
                                c = '^';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3333:
                            if (lowerCase.equals("hm")) {
                                c = '_';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3334:
                            if (lowerCase.equals("hn")) {
                                c = '`';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3338:
                            if (lowerCase.equals("hr")) {
                                c = 'a';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3340:
                            if (lowerCase.equals("ht")) {
                                c = 'b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3341:
                            if (lowerCase.equals("hu")) {
                                c = 'c';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3355:
                            if (lowerCase.equals("id")) {
                                c = 'd';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3356:
                            if (lowerCase.equals("ie")) {
                                c = 'e';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3363:
                            if (lowerCase.equals("il")) {
                                c = 'f';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3364:
                            if (lowerCase.equals("im")) {
                                c = 'g';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3365:
                            if (lowerCase.equals("in")) {
                                c = 'h';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3366:
                            if (lowerCase.equals("io")) {
                                c = 'i';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3368:
                            if (lowerCase.equals("iq")) {
                                c = 'j';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3369:
                            if (lowerCase.equals("ir")) {
                                c = 'k';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3370:
                            if (lowerCase.equals("is")) {
                                c = 'l';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3371:
                            if (lowerCase.equals("it")) {
                                c = 'm';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3387:
                            if (lowerCase.equals("je")) {
                                c = 'n';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3395:
                            if (lowerCase.equals("jm")) {
                                c = 'o';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3397:
                            if (lowerCase.equals("jo")) {
                                c = 'p';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3398:
                            if (lowerCase.equals("jp")) {
                                c = 'q';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3418:
                            if (lowerCase.equals("ke")) {
                                c = 'r';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3420:
                            if (lowerCase.equals("kg")) {
                                c = 's';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3421:
                            if (lowerCase.equals("kh")) {
                                c = 't';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3422:
                            if (lowerCase.equals("ki")) {
                                c = 'u';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3426:
                            if (lowerCase.equals("km")) {
                                c = 'v';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3427:
                            if (lowerCase.equals("kn")) {
                                c = 'w';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3429:
                            if (lowerCase.equals("kp")) {
                                c = 'x';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3431:
                            if (lowerCase.equals("kr")) {
                                c = 'y';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3436:
                            if (lowerCase.equals("kw")) {
                                c = 'z';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3438:
                            if (lowerCase.equals("ky")) {
                                c = '{';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3439:
                            if (lowerCase.equals("kz")) {
                                c = '|';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3445:
                            if (lowerCase.equals("la")) {
                                c = '}';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3446:
                            if (lowerCase.equals("lb")) {
                                c = '~';
                                break;
                            }
                            c = 65535;
                            break;
                        case 3447:
                            if (lowerCase.equals("lc")) {
                                c = Ascii.MAX;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3453:
                            if (lowerCase.equals("li")) {
                                c = 128;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3455:
                            if (lowerCase.equals("lk")) {
                                c = 129;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3462:
                            if (lowerCase.equals("lr")) {
                                c = 130;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3463:
                            if (lowerCase.equals("ls")) {
                                c = 131;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3464:
                            if (lowerCase.equals("lt")) {
                                c = 132;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3465:
                            if (lowerCase.equals("lu")) {
                                c = 133;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3466:
                            if (lowerCase.equals("lv")) {
                                c = 134;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3469:
                            if (lowerCase.equals("ly")) {
                                c = 135;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3476:
                            if (lowerCase.equals("ma")) {
                                c = 136;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3478:
                            if (lowerCase.equals("mc")) {
                                c = 137;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3479:
                            if (lowerCase.equals("md")) {
                                c = 138;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3480:
                            if (lowerCase.equals("me")) {
                                c = 139;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3481:
                            if (lowerCase.equals("mf")) {
                                c = 140;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3482:
                            if (lowerCase.equals("mg")) {
                                c = 141;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3483:
                            if (lowerCase.equals("mh")) {
                                c = 142;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3486:
                            if (lowerCase.equals("mk")) {
                                c = 143;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3487:
                            if (lowerCase.equals("ml")) {
                                c = 144;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3488:
                            if (lowerCase.equals("mm")) {
                                c = 145;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3489:
                            if (lowerCase.equals("mn")) {
                                c = 146;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3490:
                            if (lowerCase.equals("mo")) {
                                c = 147;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3491:
                            if (lowerCase.equals("mp")) {
                                c = 148;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3492:
                            if (lowerCase.equals("mq")) {
                                c = 149;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3493:
                            if (lowerCase.equals("mr")) {
                                c = 150;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3494:
                            if (lowerCase.equals("ms")) {
                                c = 151;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3495:
                            if (lowerCase.equals("mt")) {
                                c = 152;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3496:
                            if (lowerCase.equals("mu")) {
                                c = 153;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3497:
                            if (lowerCase.equals("mv")) {
                                c = 154;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3498:
                            if (lowerCase.equals("mw")) {
                                c = 155;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3499:
                            if (lowerCase.equals("mx")) {
                                c = 156;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3500:
                            if (lowerCase.equals("my")) {
                                c = 157;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3501:
                            if (lowerCase.equals("mz")) {
                                c = 158;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3507:
                            if (lowerCase.equals("na")) {
                                c = 159;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3509:
                            if (lowerCase.equals("nc")) {
                                c = Typography.nbsp;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3511:
                            if (lowerCase.equals("ne")) {
                                c = 161;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3512:
                            if (lowerCase.equals("nf")) {
                                c = Typography.cent;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3513:
                            if (lowerCase.equals("ng")) {
                                c = Typography.pound;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3515:
                            if (lowerCase.equals("ni")) {
                                c = 164;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3518:
                            if (lowerCase.equals("nl")) {
                                c = 165;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3521:
                            if (lowerCase.equals("no")) {
                                c = 166;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3522:
                            if (lowerCase.equals("np")) {
                                c = Typography.section;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3524:
                            if (lowerCase.equals("nr")) {
                                c = 168;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3527:
                            if (lowerCase.equals("nu")) {
                                c = Typography.copyright;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3532:
                            if (lowerCase.equals("nz")) {
                                c = 170;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3550:
                            if (lowerCase.equals("om")) {
                                c = Typography.leftGuillemete;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3569:
                            if (lowerCase.equals("pa")) {
                                c = 172;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3573:
                            if (lowerCase.equals("pe")) {
                                c = 173;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3574:
                            if (lowerCase.equals("pf")) {
                                c = Typography.registered;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3575:
                            if (lowerCase.equals("pg")) {
                                c = 175;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3576:
                            if (lowerCase.equals("ph")) {
                                c = Typography.degree;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3579:
                            if (lowerCase.equals("pk")) {
                                c = Typography.plusMinus;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3580:
                            if (lowerCase.equals("pl")) {
                                c = 178;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3581:
                            if (lowerCase.equals("pm")) {
                                c = 179;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3582:
                            if (lowerCase.equals("pn")) {
                                c = 180;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3586:
                            if (lowerCase.equals("pr")) {
                                c = 181;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3587:
                            if (lowerCase.equals("ps")) {
                                c = Typography.paragraph;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3588:
                            if (lowerCase.equals("pt")) {
                                c = Typography.middleDot;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3591:
                            if (lowerCase.equals("pw")) {
                                c = 184;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3593:
                            if (lowerCase.equals("py")) {
                                c = 185;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3600:
                            if (lowerCase.equals("qa")) {
                                c = 186;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3635:
                            if (lowerCase.equals("re")) {
                                c = Typography.rightGuillemete;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3645:
                            if (lowerCase.equals("ro")) {
                                c = 188;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3649:
                            if (lowerCase.equals("rs")) {
                                c = Typography.half;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3651:
                            if (lowerCase.equals("ru")) {
                                c = 190;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3653:
                            if (lowerCase.equals("rw")) {
                                c = 191;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3662:
                            if (lowerCase.equals("sa")) {
                                c = 192;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3663:
                            if (lowerCase.equals("sb")) {
                                c = 193;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3664:
                            if (lowerCase.equals("sc")) {
                                c = 194;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3665:
                            if (lowerCase.equals("sd")) {
                                c = 195;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3666:
                            if (lowerCase.equals("se")) {
                                c = 196;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3668:
                            if (lowerCase.equals("sg")) {
                                c = 197;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3669:
                            if (lowerCase.equals("sh")) {
                                c = 198;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3670:
                            if (lowerCase.equals("si")) {
                                c = 199;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3671:
                            if (lowerCase.equals("sj")) {
                                c = 200;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3672:
                            if (lowerCase.equals("sk")) {
                                c = 201;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3673:
                            if (lowerCase.equals("sl")) {
                                c = 202;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3674:
                            if (lowerCase.equals("sm")) {
                                c = 203;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3675:
                            if (lowerCase.equals("sn")) {
                                c = 204;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3676:
                            if (lowerCase.equals("so")) {
                                c = 205;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3679:
                            if (lowerCase.equals("sr")) {
                                c = 206;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3680:
                            if (lowerCase.equals("ss")) {
                                c = 207;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3681:
                            if (lowerCase.equals("st")) {
                                c = 208;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3683:
                            if (lowerCase.equals("sv")) {
                                c = 209;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3685:
                            if (lowerCase.equals("sx")) {
                                c = 210;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3686:
                            if (lowerCase.equals("sy")) {
                                c = 211;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3687:
                            if (lowerCase.equals("sz")) {
                                c = 212;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3695:
                            if (lowerCase.equals("tc")) {
                                c = 213;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3696:
                            if (lowerCase.equals("td")) {
                                c = 214;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3698:
                            if (lowerCase.equals("tf")) {
                                c = Typography.times;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3699:
                            if (lowerCase.equals("tg")) {
                                c = 216;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3700:
                            if (lowerCase.equals("th")) {
                                c = 217;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3702:
                            if (lowerCase.equals("tj")) {
                                c = 218;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3703:
                            if (lowerCase.equals("tk")) {
                                c = 219;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3704:
                            if (lowerCase.equals("tl")) {
                                c = 220;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3705:
                            if (lowerCase.equals("tm")) {
                                c = 221;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3706:
                            if (lowerCase.equals("tn")) {
                                c = 222;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3707:
                            if (lowerCase.equals("to")) {
                                c = 223;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3710:
                            if (lowerCase.equals("tr")) {
                                c = 224;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3712:
                            if (lowerCase.equals("tt")) {
                                c = 225;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3714:
                            if (lowerCase.equals("tv")) {
                                c = 226;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3715:
                            if (lowerCase.equals("tw")) {
                                c = 227;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3718:
                            if (lowerCase.equals("tz")) {
                                c = 228;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3724:
                            if (lowerCase.equals("ua")) {
                                c = 229;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3730:
                            if (lowerCase.equals("ug")) {
                                c = 230;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3736:
                            if (lowerCase.equals("um")) {
                                c = 231;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3742:
                            if (lowerCase.equals("us")) {
                                c = 232;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3748:
                            if (lowerCase.equals("uy")) {
                                c = 233;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3749:
                            if (lowerCase.equals("uz")) {
                                c = 234;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3755:
                            if (lowerCase.equals("va")) {
                                c = 235;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3757:
                            if (lowerCase.equals("vc")) {
                                c = 236;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3759:
                            if (lowerCase.equals("ve")) {
                                c = 237;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3761:
                            if (lowerCase.equals("vg")) {
                                c = 238;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3763:
                            if (lowerCase.equals("vi")) {
                                c = 239;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3768:
                            if (lowerCase.equals("vn")) {
                                c = 240;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3775:
                            if (lowerCase.equals("vu")) {
                                c = 241;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3791:
                            if (lowerCase.equals("wf")) {
                                c = 242;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3804:
                            if (lowerCase.equals("ws")) {
                                c = 243;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3827:
                            if (lowerCase.equals("xk")) {
                                c = 244;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3852:
                            if (lowerCase.equals("ye")) {
                                c = 245;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3867:
                            if (lowerCase.equals("yt")) {
                                c = 246;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3879:
                            if (lowerCase.equals("za")) {
                                c = 247;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3891:
                            if (lowerCase.equals("zm")) {
                                c = 248;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3901:
                            if (lowerCase.equals("zw")) {
                                c = 249;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            switch (hashCode) {
                                case 3120:
                                    if (lowerCase.equals("aq")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3121:
                                    if (lowerCase.equals("ar")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3122:
                                    if (lowerCase.equals("as")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3123:
                                    if (lowerCase.equals("at")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3124:
                                    if (lowerCase.equals("au")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    switch (hashCode) {
                                        case 3146:
                                            if (lowerCase.equals("bl")) {
                                                c = 25;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3147:
                                            if (lowerCase.equals("bm")) {
                                                c = 26;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3148:
                                            if (lowerCase.equals("bn")) {
                                                c = 27;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3149:
                                            if (lowerCase.equals("bo")) {
                                                c = 28;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            switch (hashCode) {
                                                case 3151:
                                                    if (lowerCase.equals("bq")) {
                                                        c = 29;
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                case 3152:
                                                    if (lowerCase.equals("br")) {
                                                        c = 30;
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                case 3153:
                                                    if (lowerCase.equals("bs")) {
                                                        c = 31;
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                case 3154:
                                                    if (lowerCase.equals("bt")) {
                                                        c = ' ';
                                                        break;
                                                    }
                                                    c = 65535;
                                                    break;
                                                default:
                                                    c = 65535;
                                                    break;
                                            }
                                    }
                            }
                    }
            }
        } else {
            if (lowerCase.equals("bz")) {
                c = Typography.dollar;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return "🇦🇩";
            case 1:
                return "🇦🇪";
            case 2:
                return "🇦🇫";
            case 3:
                return "🇦🇬";
            case 4:
                return "🇦🇮";
            case 5:
                return "🇦🇱";
            case 6:
                return "🇦🇲";
            case 7:
                return "🇦🇴";
            case '\b':
                return "🇦🇶";
            case '\t':
                return "🇦🇷";
            case '\n':
                return "🇦🇸";
            case 11:
                return "🇦🇹";
            case '\f':
                return "🇦🇺";
            case '\r':
                return "🇦🇼";
            case 14:
                return "🇦🇽";
            case 15:
                return "🇦🇿";
            case 16:
                return "🇧🇦";
            case 17:
                return "🇧🇧";
            case 18:
                return "🇧🇩";
            case 19:
                return "🇧🇪";
            case 20:
                return "🇧🇫";
            case 21:
                return "🇧🇬";
            case 22:
                return "🇧🇭";
            case 23:
                return "🇧🇮";
            case 24:
                return "🇧🇯";
            case 25:
                return "🇧🇱";
            case 26:
                return "🇧🇲";
            case 27:
                return "🇧🇳";
            case 28:
                return "🇧🇴";
            case 29:
                return "🇧🇶";
            case 30:
                return "🇧🇷";
            case 31:
                return "🇧🇸";
            case ' ':
                return "🇧🇹";
            case '!':
                return "🇧🇻";
            case '\"':
                return "🇧🇼";
            case '#':
                return "🇧🇾";
            case '$':
                return "🇧🇿";
            case '%':
                return "🇨🇦";
            case '&':
                return "🇨🇨";
            case '\'':
                return "🇨🇩";
            case '(':
                return "🇨🇫";
            case ')':
                return "🇨🇬";
            case '*':
                return "🇨🇭";
            case '+':
                return "🇨🇮";
            case ',':
                return "🇨🇰";
            case '-':
                return "🇨🇱";
            case '.':
                return "🇨🇲";
            case '/':
                return "🇨🇳";
            case '0':
                return "🇨🇴";
            case '1':
                return "🇨🇷";
            case '2':
                return "🇨🇺";
            case '3':
                return "🇨🇻";
            case '4':
                return "🇨🇼";
            case '5':
                return "🇨🇽";
            case '6':
                return "🇨🇾";
            case '7':
                return "🇨🇿";
            case '8':
                return "🇩🇪";
            case '9':
                return "🇩🇯";
            case ':':
                return "🇩🇰";
            case ';':
                return "🇩🇲";
            case '<':
                return "🇩🇴";
            case '=':
                return "🇩🇿";
            case '>':
                return "🇪🇨";
            case '?':
                return "🇪🇪";
            case '@':
                return "🇪🇬";
            case 'A':
                return "🇪🇭";
            case 'B':
                return "🇪🇷";
            case 'C':
                return "🇪🇸";
            case 'D':
                return "🇪🇹";
            case 'E':
                return "🇫🇮";
            case 'F':
                return "🇫🇯";
            case 'G':
                return "🇫🇰";
            case 'H':
                return "🇫🇲";
            case 'I':
                return "🇫🇴";
            case 'J':
                return "🇫🇷";
            case 'K':
                return "🇬🇦";
            case 'L':
                return "🇬🇧";
            case 'M':
                return "🇬🇩";
            case 'N':
                return "🇬🇪";
            case 'O':
                return "🇬🇫";
            case 'P':
                return "🇬🇬";
            case 'Q':
                return "🇬🇭";
            case 'R':
                return "🇬🇮";
            case 'S':
                return "🇬🇱";
            case 'T':
                return "🇬🇲";
            case 'U':
                return "🇬🇳";
            case 'V':
                return "🇬🇵";
            case 'W':
                return "🇬🇶";
            case 'X':
                return "🇬🇷";
            case 'Y':
                return "🇬🇸";
            case 'Z':
                return "🇬🇹";
            case '[':
                return "🇬🇺";
            case '\\':
                return "🇬🇼";
            case ']':
                return "🇬🇾";
            case '^':
                return "🇭🇰";
            case '_':
                return "🇭🇲";
            case '`':
                return "🇭🇳";
            case 'a':
                return "🇭🇷";
            case 'b':
                return "🇭🇹";
            case 'c':
                return "🇭🇺";
            case 'd':
                return "🇮🇩";
            case 'e':
                return "🇮🇪";
            case 'f':
                return "🇮🇱";
            case 'g':
                return "🇮🇲";
            case 'h':
                return "🇮🇳";
            case 'i':
                return "🇮🇴";
            case 'j':
                return "🇮🇶";
            case 'k':
                return "🇮🇷";
            case 'l':
                return "🇮🇸";
            case 'm':
                return "🇮🇹";
            case 'n':
                return "🇯🇪";
            case 'o':
                return "🇯🇲";
            case 'p':
                return "🇯🇴";
            case 'q':
                return "🇯🇵";
            case 'r':
                return "🇰🇪";
            case 's':
                return "🇰🇬";
            case 't':
                return "🇰🇭";
            case 'u':
                return "🇰🇮";
            case 'v':
                return "🇰🇲";
            case 'w':
                return "🇰🇳";
            case 'x':
                return "🇰🇵";
            case 'y':
                return "🇰🇷";
            case 'z':
                return "🇰🇼";
            case '{':
                return "🇰🇾";
            case '|':
                return "🇰🇿";
            case '}':
                return "🇱🇦";
            case WebSocketProtocol.PAYLOAD_SHORT:
                return "🇱🇧";
            case 127:
                return "🇱🇨";
            case 128:
                return "🇱🇮";
            case 129:
                return "🇱🇰";
            case 130:
                return "🇱🇷";
            case 131:
                return "🇱🇸";
            case 132:
                return "🇱🇹";
            case 133:
                return "🇱🇺";
            case 134:
                return "🇱🇻";
            case 135:
                return "🇱🇾";
            case 136:
                return "🇲🇦";
            case 137:
                return "🇲🇨";
            case 138:
                return "🇲🇩";
            case 139:
                return "🇲🇪";
            case 140:
                return "🇲🇫";
            case 141:
                return "🇲🇬";
            case 142:
                return "🇲🇭";
            case 143:
                return "🇲🇰";
            case 144:
                return "🇲🇱";
            case 145:
                return "🇲🇲";
            case 146:
                return "🇲🇳";
            case 147:
                return "🇲🇴";
            case 148:
                return "🇲🇵";
            case 149:
                return "🇲🇶";
            case 150:
                return "🇲🇷";
            case 151:
                return "🇲🇸";
            case 152:
                return "🇲🇹";
            case 153:
                return "🇲🇺";
            case 154:
                return "🇲🇻";
            case 155:
                return "🇲🇼";
            case 156:
                return "🇲🇽";
            case 157:
                return "🇲🇾";
            case 158:
                return "🇲🇿";
            case 159:
                return "🇳🇦";
            case 160:
                return "🇳🇨";
            case MXErrCode.ERR_FAILED_TEE:
                return "🇳🇪";
            case MXErrCode.ERR_KEY_LEN_TEE:
                return "🇳🇫";
            case MXErrCode.ERR_DATA_LEN_TEE:
                return "🇳🇬";
            case MXErrCode.ERR_KEY_ENCRYPT_TEE:
                return "🇳🇮";
            case MXErrCode.ERR_KEY_DECRYPT_TEE:
                return "🇳🇱";
            case MXErrCode.ERR_NO_CERT_TEE:
                return "🇳🇴";
            case MXErrCode.ERR_NO_KEY_TEE:
                return "🇳🇵";
            case MXErrCode.ERR_NO_PIN_TEE:
                return "🇳🇷";
            case MXErrCode.ERR_INVALID_PARAMETER_TEE:
                return "🇳🇺";
            case MXErrCode.ERR_VERIFIED_FAILED_TEE:
                return "🇳🇿";
            case MXErrCode.ERR_NO_CMD_TEE:
                return "🇴🇲";
            case MXErrCode.ERR_NO_ALG_TEE:
                return "🇵🇦";
            case 173:
                return "🇵🇪";
            case 174:
                return "🇵🇫";
            case 175:
                return "🇵🇬";
            case 176:
                return "🇵🇭";
            case 177:
                return "🇵🇰";
            case 178:
                return "🇵🇱";
            case 179:
                return "🇵🇲";
            case RotationOptions.ROTATE_180:
                return "🇵🇳";
            case 181:
                return "🇵🇷";
            case 182:
                return "🇵🇸";
            case 183:
                return "🇵🇹";
            case 184:
                return "🇵🇼";
            case 185:
                return "🇵🇾";
            case 186:
                return "🇶🇦";
            case 187:
                return "🇷🇪";
            case 188:
                return "🇷🇴";
            case 189:
                return "🇷🇸";
            case 190:
                return "🇷🇺";
            case 191:
                return "🇷🇼";
            case JfifUtil.MARKER_SOFn:
                return "🇸🇦";
            case 193:
                return "🇸🇧";
            case 194:
                return "🇸🇨";
            case 195:
                return "🇸🇩";
            case 196:
                return "🇸🇪";
            case 197:
                return "🇸🇬";
            case 198:
                return "🇸🇭";
            case 199:
                return "🇸🇮";
            case ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION:
                return "🇸🇯";
            case SecBiometricLicenseManager.ERROR_INVALID_LICENSE:
                return "🇸🇰";
            case 202:
                return "🇸🇱";
            case SecBiometricLicenseManager.ERROR_LICENSE_TERMINATED:
                return "🇸🇲";
            case SecBiometricLicenseManager.ERROR_INVALID_PACKAGE_NAME:
                return "🇸🇳";
            case 205:
                return "🇸🇴";
            case 206:
                return "🇸🇷";
            case 207:
                return "🇸🇸";
            case JfifUtil.MARKER_RST0:
                return "🇸🇹";
            case 209:
                return "🇸🇻";
            case 210:
                return "🇸🇽";
            case 211:
                return "🇸🇾";
            case 212:
                return "🇸🇿";
            case 213:
                return "🇹🇨";
            case 214:
                return "🇹🇩";
            case JfifUtil.MARKER_RST7:
                return "🇹🇫";
            case JfifUtil.MARKER_SOI:
                return "🇹🇬";
            case JfifUtil.MARKER_EOI:
                return "🇹🇭";
            case JfifUtil.MARKER_SOS:
                return "🇹🇯";
            case 219:
                return "🇹🇰";
            case 220:
                return "🇹🇱";
            case 221:
                return "🇹🇲";
            case 222:
                return "🇹🇳";
            case 223:
                return "🇹🇴";
            case 224:
                return "🇹🇷";
            case JfifUtil.MARKER_APP1:
                return "🇹🇹";
            case 226:
                return "🇹🇻";
            case 227:
                return "🇹🇼";
            case 228:
                return "🇹🇿";
            case 229:
                return "🇺🇦";
            case 230:
                return "🇺🇬";
            case 231:
                return "🇺🇲";
            case 232:
                return "🇺🇸";
            case 233:
                return "🇺🇾";
            case 234:
                return "🇺🇿";
            case 235:
                return "🇻🇦";
            case 236:
                return "🇻🇨";
            case 237:
                return "🇻🇪";
            case 238:
                return "🇻🇬";
            case 239:
                return "🇻🇮";
            case 240:
                return "🇻🇳";
            case 241:
                return "🇻🇺";
            case 242:
                return "🇼🇫";
            case 243:
                return "🇼🇸";
            case 244:
                return "🇽🇰";
            case MXConStant.RSA_ENCRYPT_DATA_LEN_MAX:
                return "🇾🇪";
            case 246:
                return "🇾🇹";
            case 247:
                return "🇿🇦";
            case 248:
                return "🇿🇲";
            case 249:
                return "🇿🇼";
            default:
                return " ";
        }
    }

    public static List<CCPCountry> getLibraryMasterCountryList(Context context, CountryCodePicker.Language language) {
        List<CCPCountry> list;
        CountryCodePicker.Language language2 = loadedLibraryMasterListLanguage;
        if (language2 == null || language != language2 || (list = loadedLibraryMaterList) == null || list.size() == 0) {
            loadDataFromXML(context, language);
        }
        return loadedLibraryMaterList;
    }

    public static List<CCPCountry> getLibraryMasterCountriesEnglish() {
        List<CCPCountry> countries = new ArrayList<>();
        countries.add(new CCPCountry("ad", "376", "Andorra", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ae", "971", "United Arab Emirates (UAE)", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("af", "93", "Afghanistan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ag", DiskLruCache.VERSION_1, "Antigua and Barbuda", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ai", DiskLruCache.VERSION_1, "Anguilla", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("al", "355", "Albania", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("am", "374", "Armenia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ao", "244", "Angola", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("aq", "672", "Antarctica", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ar", "54", "Argentina", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("as", DiskLruCache.VERSION_1, "American Samoa", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("at", "43", "Austria", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("au", "61", "Australia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("aw", "297", "Aruba", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ax", "358", "Åland Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("az", "994", "Azerbaijan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ba", "387", "Bosnia And Herzegovina", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bb", DiskLruCache.VERSION_1, "Barbados", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bd", "880", "Bangladesh", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("be", "32", "Belgium", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bf", "226", "Burkina Faso", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bg", "359", "Bulgaria", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bh", "973", "Bahrain", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bi", "257", "Burundi", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bj", "229", "Benin", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bl", "590", "Saint Barthélemy", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bm", DiskLruCache.VERSION_1, "Bermuda", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bn", "673", "Brunei Darussalam", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bo", "591", "Bolivia, Plurinational State Of", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("br", "55", "Brazil", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bs", DiskLruCache.VERSION_1, "Bahamas", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bt", "975", "Bhutan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bw", "267", "Botswana", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("by", "375", "Belarus", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("bz", "501", "Belize", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ca", DiskLruCache.VERSION_1, "Canada", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cc", "61", "Cocos (keeling) Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cd", "243", "Congo, The Democratic Republic Of The", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cf", "236", "Central African Republic", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cg", "242", "Congo", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ch", "41", "Switzerland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ci", "225", "Côte D'ivoire", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ck", "682", "Cook Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cl", "56", "Chile", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cm", "237", "Cameroon", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cn", "86", "China", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("co", "57", "Colombia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cr", "506", "Costa Rica", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cu", "53", "Cuba", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cv", "238", "Cape Verde", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cw", "599", "Curaçao", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cx", "61", "Christmas Island", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cy", "357", "Cyprus", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("cz", "420", "Czech Republic", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("de", "49", "Germany", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("dj", "253", "Djibouti", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("dk", "45", "Denmark", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("dm", DiskLruCache.VERSION_1, "Dominica", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("do", DiskLruCache.VERSION_1, "Dominican Republic", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("dz", "213", "Algeria", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ec", "593", "Ecuador", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ee", "372", "Estonia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("eg", "20", "Egypt", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("er", "291", "Eritrea", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("es", "34", "Spain", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("et", "251", "Ethiopia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("fi", "358", "Finland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("fj", "679", "Fiji", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("fk", "500", "Falkland Islands (malvinas)", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("fm", "691", "Micronesia, Federated States Of", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("fo", "298", "Faroe Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("fr", "33", "France", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ga", "241", "Gabon", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gb", "44", "United Kingdom", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gd", DiskLruCache.VERSION_1, "Grenada", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ge", "995", "Georgia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gf", "594", "French Guyana", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gh", "233", "Ghana", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gi", "350", "Gibraltar", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gl", "299", "Greenland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gm", "220", "Gambia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gn", "224", "Guinea", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gp", "450", "Guadeloupe", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gq", "240", "Equatorial Guinea", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gr", "30", "Greece", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gt", "502", "Guatemala", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gu", DiskLruCache.VERSION_1, "Guam", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gw", "245", "Guinea-bissau", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("gy", "592", "Guyana", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("hk", "852", "Hong Kong", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("hn", "504", "Honduras", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("hr", "385", "Croatia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ht", "509", "Haiti", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("hu", "36", "Hungary", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("id", "62", "Indonesia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ie", "353", "Ireland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("il", "972", "Israel", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("im", "44", "Isle Of Man", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("is", "354", "Iceland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("in", "91", "India", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("io", "246", "British Indian Ocean Territory", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("iq", "964", "Iraq", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ir", "98", "Iran, Islamic Republic Of", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("it", "39", "Italy", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("je", "44", "Jersey ", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("jm", DiskLruCache.VERSION_1, "Jamaica", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("jo", "962", "Jordan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("jp", "81", "Japan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ke", "254", "Kenya", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kg", "996", "Kyrgyzstan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kh", "855", "Cambodia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ki", "686", "Kiribati", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("km", "269", "Comoros", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kn", DiskLruCache.VERSION_1, "Saint Kitts and Nevis", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kp", "850", "North Korea", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kr", "82", "South Korea", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kw", "965", "Kuwait", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ky", DiskLruCache.VERSION_1, "Cayman Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("kz", "7", "Kazakhstan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("la", "856", "Lao People's Democratic Republic", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lb", "961", "Lebanon", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lc", DiskLruCache.VERSION_1, "Saint Lucia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("li", "423", "Liechtenstein", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lk", "94", "Sri Lanka", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lr", "231", "Liberia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ls", "266", "Lesotho", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lt", "370", "Lithuania", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lu", "352", "Luxembourg", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("lv", "371", "Latvia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ly", "218", "Libya", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ma", "212", "Morocco", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mc", "377", "Monaco", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("md", "373", "Moldova, Republic Of", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("me", "382", "Montenegro", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mf", "590", "Saint Martin", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mg", "261", "Madagascar", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mh", "692", "Marshall Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mk", "389", "Macedonia (FYROM)", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ml", "223", "Mali", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mm", "95", "Myanmar", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mn", "976", "Mongolia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mo", "853", "Macau", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mp", DiskLruCache.VERSION_1, "Northern Mariana Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mq", "596", "Martinique", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mr", "222", "Mauritania", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ms", DiskLruCache.VERSION_1, "Montserrat", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mt", "356", "Malta", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mu", "230", "Mauritius", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mv", "960", "Maldives", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mw", "265", "Malawi", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mx", "52", "Mexico", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("my", "60", "Malaysia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("mz", "258", "Mozambique", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("na", "264", "Namibia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("nc", "687", "New Caledonia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ne", "227", "Niger", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("nf", "672", "Norfolk Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ng", "234", "Nigeria", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ni", "505", "Nicaragua", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("nl", "31", "Netherlands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("no", "47", "Norway", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("np", "977", "Nepal", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("nr", "674", "Nauru", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("nu", "683", "Niue", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("nz", "64", "New Zealand", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("om", "968", "Oman", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pa", "507", "Panama", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pe", "51", "Peru", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pf", "689", "French Polynesia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pg", "675", "Papua New Guinea", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ph", "63", "Philippines", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pk", "92", "Pakistan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pl", "48", "Poland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pm", "508", "Saint Pierre And Miquelon", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pn", "870", "Pitcairn Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pr", DiskLruCache.VERSION_1, "Puerto Rico", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ps", "970", "Palestine", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pt", "351", "Portugal", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("pw", "680", "Palau", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("py", "595", "Paraguay", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("qa", "974", "Qatar", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("re", "262", "Réunion", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ro", "40", "Romania", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("rs", "381", "Serbia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ru", "7", "Russian Federation", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("rw", "250", "Rwanda", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sa", "966", "Saudi Arabia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sb", "677", "Solomon Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sc", "248", "Seychelles", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sd", "249", "Sudan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("se", "46", "Sweden", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sg", "65", "Singapore", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sh", "290", "Saint Helena, Ascension And Tristan Da Cunha", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("si", "386", "Slovenia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sk", "421", "Slovakia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sl", "232", "Sierra Leone", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sm", "378", "San Marino", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sn", "221", "Senegal", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("so", "252", "Somalia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sr", "597", "Suriname", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ss", "211", "South Sudan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("st", "239", "Sao Tome And Principe", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sv", "503", "El Salvador", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sx", DiskLruCache.VERSION_1, "Sint Maarten", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sy", "963", "Syrian Arab Republic", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("sz", "268", "Swaziland", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tc", DiskLruCache.VERSION_1, "Turks and Caicos Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("td", "235", "Chad", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tg", "228", "Togo", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("th", "66", "Thailand", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tj", "992", "Tajikistan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tk", "690", "Tokelau", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tl", "670", "Timor-leste", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tm", "993", "Turkmenistan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tn", "216", "Tunisia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("to", "676", "Tonga", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tr", "90", "Turkey", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tt", DiskLruCache.VERSION_1, "Trinidad &amp; Tobago", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tv", "688", "Tuvalu", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tw", "886", "Taiwan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("tz", "255", "Tanzania, United Republic Of", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ua", "380", "Ukraine", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ug", "256", "Uganda", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("us", DiskLruCache.VERSION_1, "United States", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("uy", "598", "Uruguay", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("uz", "998", "Uzbekistan", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("va", "379", "Holy See (vatican City State)", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("vc", DiskLruCache.VERSION_1, "Saint Vincent &amp; The Grenadines", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ve", "58", "Venezuela, Bolivarian Republic Of", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("vg", DiskLruCache.VERSION_1, "British Virgin Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("vi", DiskLruCache.VERSION_1, "US Virgin Islands", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("vn", "84", "Vietnam", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("vu", "678", "Vanuatu", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("wf", "681", "Wallis And Futuna", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ws", "685", "Samoa", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("xk", "383", "Kosovo", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("ye", "967", "Yemen", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("yt", "262", "Mayotte", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("za", "27", "South Africa", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("zm", "260", "Zambia", DEFAULT_FLAG_RES));
        countries.add(new CCPCountry("zw", "263", "Zimbabwe", DEFAULT_FLAG_RES));
        return countries;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public int getFlagID() {
        if (this.flagResID == -99) {
            this.flagResID = getFlagMasterResID(this);
        }
        return this.flagResID;
    }

    public String getNameCode() {
        return this.nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getPhoneCode() {
        return this.phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void log() {
        try {
            String str = TAG;
            Log.d(str, "Country->" + this.nameCode + ":" + this.phoneCode + ":" + this.name);
        } catch (NullPointerException e) {
            Log.d(TAG, "Null");
        }
    }

    String logString() {
        return this.nameCode.toUpperCase() + " +" + this.phoneCode + "(" + this.name + ")";
    }

    public boolean isEligibleForQuery(String query) {
        String query2 = query.toLowerCase();
        return containsQueryWord("Name", getName(), query2) || containsQueryWord("NameCode", getNameCode(), query2) || containsQueryWord("PhoneCode", getPhoneCode(), query2) || containsQueryWord("EnglishName", getEnglishName(), query2);
    }

    private boolean containsQueryWord(String fieldName, String fieldValue, String query) {
        if (fieldValue == null || query == null) {
            return false;
        }
        try {
            return fieldValue.toLowerCase(Locale.ROOT).contains(query);
        } catch (Exception e) {
            Log.w("CCPCountry", fieldName + ":" + fieldValue + " failed to execute toLowerCase(Locale.ROOT).contains(query) for query:" + query);
            return false;
        }
    }

    public int compareTo(CCPCountry o) {
        return Collator.getInstance().compare(getName(), o.getName());
    }
}
