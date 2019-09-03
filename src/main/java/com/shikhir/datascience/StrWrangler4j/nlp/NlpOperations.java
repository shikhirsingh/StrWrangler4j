package com.shikhir.datascience.StrWrangler4j.nlp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;

import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class NlpOperations {
	private static final Logger log = Logger.getLogger(NlpOperations.class.getName());

	private static double log2(double a) {
	    return Math.log(a) / Math.log(2);
	}

	/** Finds the Shannon Enthropy of a string
	 * 
	 * @param s the text for which the shannon enthropy needs to be computed
	 * @return shannon enthropy value 
	 */

	public static double getShannonEntropy(String s) {
		if(s==null || s.length()==0) return 0.0;
	    int n = 0;
	    Map<Character, Integer> occ = new HashMap<>();
		 
	    for (int c_ = 0; c_ < s.length(); ++c_) {
	      char cx = s.charAt(c_);
	      if (occ.containsKey(cx)) {
	        occ.put(cx, occ.get(cx) + 1);
	      } else {
	        occ.put(cx, 1);
	      }
	      ++n;
	    }
		 
	    double e = 0.0;
	    for (Map.Entry<Character, Integer> entry : occ.entrySet()) {
	      char cx = entry.getKey();
	      double p = (double) entry.getValue() / n;
	      e += p * log2(p);
	    }
		BigDecimal bigDecimal = BigDecimal.valueOf(-e);
	    bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
	    return bigDecimal.doubleValue();
	    
	 }
	
	/** Stem of each word in the string using Porter Stemmer
	 * 
	 * @param text the text that needs to be stemmed
	 * @return the stemmed string 
	 */

	public static String stem(String text) {
		 WhitespaceTokenizer wtokenizer = WhitespaceTokenizer.INSTANCE;
		 String[] tokens = wtokenizer.tokenize(text);

		 String[] result = stem(tokens);
		 return String.join(" ", result);
	}

	/** Stem of each word in the string using Porter Stemmer
	 * 
	 * @param textArr The words that needs to be stemmed
	 * @return the stemmed string Array
	 */
	public static String[] stem(String[] textArr) {
		 PorterStemmer stemmer = new PorterStemmer();

		 for (int i = 0; i < textArr.length; i++) {
			 textArr[i] = stemmer.stem(textArr[i]);
		 }
		 return textArr;

	}

	
	/** Finds synonyms of the same words
	 * 
	 * @param textArr The words that needs to be stemmed
	 * @return the stemmed string Array
	 */

	public static String[] wordNetSynonym(String oneWord) {
		Dictionary dictionary = null;

		TreeSet<String> retVal = new TreeSet<String>();
	    IndexWord[] iwa = null;
	    try {
			dictionary = Dictionary.getDefaultResourceInstance();
			iwa = dictionary.lookupAllIndexWords(oneWord).getIndexWordArray();
	    } catch (final Exception e) {
	    	log.severe("Error: Failed to load dictionary for extjwnl, needed to find word: "+oneWord);
	    	return null;
	    }
	    
		for(IndexWord indexWord : iwa) {
		    if (indexWord == null) {
		    	continue;
		    }
		    for (Synset synset : indexWord.getSenses()) {
		        for (net.sf.extjwnl.data.Word w : synset.getWords()) {
		            retVal.add(w.getLemma());
		        }
		    }

		}

		String[] result = retVal.toArray(new String[retVal.size()]);
		return result;
	}


}
