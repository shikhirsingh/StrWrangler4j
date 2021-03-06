package com.shikhir.StrWrangler4j.nlp;

import java.util.HashMap;
import java.util.Map;

public enum HumanLanguage {
	AFRIKAANS("AF"), ARABIC("AR"), BULGARIAN("BG"),
	BENGALI("BN"), CZECH("CS"), DANISH("DA"), GERMAN("DE"), 
	GREEK("EL"), ENGLISH("EN"), SPANISH("ES"),  ESTONIAN("ET"),
	PERISAN("FA"), FINNISH("FI"), FRENCH("FR"),  GUARANI("GU"),
	HEBREW("HE"), HINDI("HI"), CROATIAN("HR"),  HUNGARIAN("HU"),
	INDONESIAN("ID"), ITALIAN("IT"), JAPANESE("JA"), KANNADA("KN"),
	KOREAN("KO"), LITHUANIAN("LT"), LATVIAN("LV"),  MACEDONIAN("MK"),
	MALAYALAM("ML"), MARATHI("MR"), NEPALI("NE"),  DUTCH("NL"),
	NORWEGIAN("NO"), PUNJABI("PA"), POLISH("PL"),  PORTUGUESE("PT"),
	ROMANIAN("RO"), RUSSIAN("RU"), SLOVAK("SK"),  SLOVENIAN("HU"),
	SOMALI("SO"), ALBANIAN("SQ"), SWEDISH("SV"),  SWAHILI("SW"),
	TAMIL("TA"), TELUGU("TE"), THAI("TH"),  TAGALOG("TL"),
	TURKISH("TR"), UNKNOWN("UN"), UKRAINIAN("UK"), URDU("UR"), VIETNAMESE("VI"),
	CHINESE_CN("ZH-CN"), CHINESE_TW("ZH-TW");

	
    private final String code;
    private static Map<String, HumanLanguage> lookup = new HashMap<String, HumanLanguage>();

    static {
        for (HumanLanguage l : HumanLanguage.values()) {
            lookup.put(l.getISO639_1Code(), l);
        }
    }
    
    HumanLanguage(String code){
    	this.code=code.toLowerCase();
    }

    public static HumanLanguage get(String iso639_1Code) {
    	return lookup.get(iso639_1Code);
    }
    
    public String getISO639_1Code() {
        return code;
    }
}
