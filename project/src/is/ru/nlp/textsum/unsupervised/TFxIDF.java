package is.ru.nlp.textsum.unsupervised;

import is.iclt.icenlp.core.tokenizer.Token;
import is.ru.nlp.textsum.util.StringUtil;
import is.ru.nlp.textsum.util.TextLength;
import is.ru.nlp.textsum.util.TokenizeSentences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Calculate the TFxIDF scores for all sentences in Document and
 * generate a summary.
 * 
 * @author Karin Christiansen, karin12@ru.is
 *
 */
public class TFxIDF {

	private TF tf;
	private IDF idf;
	private String summary = "";
	private final String IDF_FILE = "/IDF.txt";
	
	private final static Log logger = LogFactory.getLog(TFxIDF.class.getName());
	
	public TFxIDF(){

	}
	
	public String createSummary(String fullText, boolean lemmatized, int minSizeInPercent, int minLengthInWords) {
		  
			try {
				Map<String, Double> scoreMap = new HashMap<String, Double>();
				TokenizeSentences tokenizeSentences = new TokenizeSentences(3); //3 for splitting into sentences within paragraph
		    	String[] sentences = StringUtil.splitIntoSentences( fullText );
		    	
		    	
				tf = new TF();
				tf.countTermsInDocument(fullText, lemmatized);
				TextLength summaryLength = new TextLength(tf.getWordCount());
				
				idf = new IDF();
				idf.loadIDFFile(IDF_FILE, lemmatized);
				
				for ( int i = 0; i < sentences.length; i++ ) {
					  String s = sentences[i];
					  ArrayList<Token> tokenList = tokenizeSentences.tokenizeToToken(s);
					  for ( Token t : tokenList ) {
						//	double logIDF = Math.log(idf.getIDF(t.lexeme)) > 0.0 ? Math.log(idf.getIDF(t.lexeme)) : 0.0;
						  	
							double score = tf.getTF(t.lexeme) * idf.getIDF(t.lexeme);
							score = score / tokenList.size(); //Normalization factor
							Double newScore = (Double) scoreMap.get(s) == null ? 0 : scoreMap.get(s);
							score += newScore;
							scoreMap.put(s, score);
					}
				}
				
				Map<String, Double> sortedScoreMap = sortByValues(scoreMap);
				int wordCount = 0;
				int percent = 0;
				
				if ( logger.isDebugEnabled()) {
				        logger.info("########################## Summary Generated by TFxIDF, Lemmatized = " + lemmatized + " ##########################");
				}
				
				for(Map.Entry<String, Double> entry : sortedScoreMap.entrySet()) {
	
					if ( logger.isDebugEnabled())
						    logger.info(entry.getKey());
				
					summary += entry.getKey() + "\n";
					String[] words = Pattern.compile("\\s+").split(entry.getKey());
		    		wordCount += words.length;
					percent = summaryLength.percentage(wordCount);
					if(percent >= minSizeInPercent && wordCount >= minLengthInWords)
						break;
				}
		
			} catch (IOException e) {
				logger.error("TFxIDF.java: createSummary - IOException");
				e.printStackTrace();
			}
		return summary;
	}
	
	public static <K extends Comparable<String>,V extends Comparable<Double>> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

           // @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o2.getValue().compareTo((Double) o1.getValue());
            }
        });
      
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
      
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
      
        return sortedMap;
    }
}