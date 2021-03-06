package com.shikhir.StrWrangler4j.nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.util.LangProfile;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStreamReader;

import com.shikhir.StrWrangler4j.fileops.ClassLoaderUtilz;

public class LangDetector {

	
	private static LangDetector INSTANCE;
	private static List<String> langList = null;

	
	private LangDetector() throws IOException, LangDetectException {
        DetectorFactory.clear();
        ArrayList<String> profilesAl = new ArrayList<String>();

		java.io.InputStream in = ClassLoaderUtilz.getResourceAsStream("lang-profile.txt", LangDetector.class);
		try {
		   InputStreamReader inR = new InputStreamReader( in );
		   BufferedReader buf = new BufferedReader( inR );
		   String line;
		   while ( ( line = buf.readLine() ) != null ) {
			   profilesAl.add(line);
		   }
		 } finally {
		   in.close();
		 }
		
        DetectorFactory.loadProfile(profilesAl);
        langList = DetectorFactory.getLangList();
	}
	
  	public static String detect(String text) throws LangDetectException, IOException {
  		getInstance();
		Detector detector = DetectorFactory.create();
		detector.append(text);
		return detector.detect();
	}

  	
  	public static List<String> getLanguages() throws LangDetectException, IOException {
  		getInstance();
  		return langList;
	}


	
	public static LangDetector getInstance() throws IOException, LangDetectException {
		if(INSTANCE==null) {
			INSTANCE = new LangDetector();
		}
		
		return INSTANCE;
	}
	
	/**
	 * This method is used to test if the string contains characters that are from
	 * the CJKV languages (Chinese, Japanese, Korean, or Vietnamese ).
	 * The CJKV languages are unique because they don't use space a separator 
	 * for words. This is often important because the words need to be tokenized
	 * differently.
	 *   
	 * @param strTest The string that needs tested for CJKV
	 * @return the count of CJKV characters in string
	 * @since 1.0.0
	 */
	public static int countCJKCharecters(String strTest) {
		final int length = strTest.length();
		int counter=0;
		for (int offset = 0; offset < length; ) {
		    final int codepoint = Character.codePointAt(strTest, offset);
			if(Character.isIdeographic(codepoint)){
				counter++;
			};
			
		    offset += Character.charCount(codepoint);
		}
		return counter;
	}
	
	/**
	 * This method is used to test if the string contains characters that are from
	 * the Cyrillic languages. It counts the number of characters. 
	 *   
	 * @param text The string that needs tested for cyrillic 
	 * @return the count of cyrillic characters in string
	 * @since 1.0.0
	 */
	
	public static int countCyrillic(String text) {
		int count=0;
		for(int i = 0; i < text.length(); i++) {
		    if(Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
		    	count++;
		    }
		}
		return count;
	}
	
	/**
	 * This method is used to test if the string contains characters that are from
	 * the Arabic languages. It counts the number of characters. 
	 *   
	 * @param text The string that needs tested for Cyrillic 
	 * @return the count of Arabic characters in string
	 * @since 1.0.0
	 */
	
	public static int countArabic(String text) {
		int count=0;
		for(int i = 0; i < text.length(); i++) {
		    if(Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.ARABIC)) {
		    	count++;
		    }
		}
		return count;
	}
	
	
	public static void close() {
		INSTANCE=null;
		langList=null;
		System.gc();
	}
	
}
