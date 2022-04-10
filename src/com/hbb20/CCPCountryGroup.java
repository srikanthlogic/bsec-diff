package com.hbb20;

import android.content.Context;
import android.util.SparseArray;
import com.hbb20.CountryCodePicker;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes3.dex */
public class CCPCountryGroup {
    private static SparseArray<CCPCountryGroup> countryGroups = null;
    int areaCodeLength;
    String defaultNameCode;
    private HashMap<String, String> nameCodeToAreaCodesMap;

    private CCPCountryGroup(String defaultNameCode, int areaCodeLength, HashMap<String, String> nameCodeToAreaCodesMap) {
        this.defaultNameCode = defaultNameCode;
        this.areaCodeLength = areaCodeLength;
        this.nameCodeToAreaCodesMap = nameCodeToAreaCodesMap;
    }

    private static void initializeGroups() {
        countryGroups = new SparseArray<>();
        addGroupForPhoneCode1();
        addGroupForPhoneCode44();
        addGroupForPhoneCode358();
    }

    private static void addGroupForPhoneCode358() {
        HashMap<String, String> nameCodeToAreaCodes = new HashMap<>();
        nameCodeToAreaCodes.put("ax", "18");
        countryGroups.put(358, new CCPCountryGroup("fi", 2, nameCodeToAreaCodes));
    }

    private static void addGroupForPhoneCode44() {
        HashMap<String, String> nameCodeToAreaCodes = new HashMap<>();
        nameCodeToAreaCodes.put("gg", "1481");
        nameCodeToAreaCodes.put("im", "1624");
        nameCodeToAreaCodes.put("je", "1534");
        countryGroups.put(44, new CCPCountryGroup("gb", 4, nameCodeToAreaCodes));
    }

    private static void addGroupForPhoneCode1() {
        HashMap<String, String> nameCodeToAreaCodes = new HashMap<>();
        nameCodeToAreaCodes.put("ag", "268");
        nameCodeToAreaCodes.put("ai", "264");
        nameCodeToAreaCodes.put("as", "684");
        nameCodeToAreaCodes.put("bb", "246");
        nameCodeToAreaCodes.put("bm", "441");
        nameCodeToAreaCodes.put("bs", "242");
        nameCodeToAreaCodes.put("ca", "204/226/236/249/250/289/306/343/365/403/416/418/431/437/438/450/506/514/519/579/581/587/600/601/604/613/639/647/705/709/769/778/780/782/807/819/825/867/873/902/905/");
        nameCodeToAreaCodes.put("dm", "767");
        nameCodeToAreaCodes.put("do", "809/829/849");
        nameCodeToAreaCodes.put("gd", "473");
        nameCodeToAreaCodes.put("gu", "671");
        nameCodeToAreaCodes.put("jm", "876");
        nameCodeToAreaCodes.put("kn", "869");
        nameCodeToAreaCodes.put("ky", "345");
        nameCodeToAreaCodes.put("lc", "758");
        nameCodeToAreaCodes.put("mp", "670");
        nameCodeToAreaCodes.put("ms", "664");
        nameCodeToAreaCodes.put("pr", "787");
        nameCodeToAreaCodes.put("sx", "721");
        nameCodeToAreaCodes.put("tc", "649");
        nameCodeToAreaCodes.put("tt", "868");
        nameCodeToAreaCodes.put("vc", "784");
        nameCodeToAreaCodes.put("vg", "284");
        nameCodeToAreaCodes.put("vi", "340");
        countryGroups.put(1, new CCPCountryGroup("us", 3, nameCodeToAreaCodes));
    }

    public static CCPCountryGroup getCountryGroupForPhoneCode(int countryCode) {
        if (countryGroups == null) {
            initializeGroups();
        }
        return countryGroups.get(countryCode);
    }

    public CCPCountry getCountryForAreaCode(Context context, CountryCodePicker.Language language, String areaCode) {
        String nameCode = this.defaultNameCode;
        for (Map.Entry<String, String> entry : this.nameCodeToAreaCodesMap.entrySet()) {
            if (entry.getValue().contains(areaCode)) {
                nameCode = entry.getKey();
            }
        }
        return CCPCountry.getCountryForNameCodeFromLibraryMasterList(context, language, nameCode);
    }
}
