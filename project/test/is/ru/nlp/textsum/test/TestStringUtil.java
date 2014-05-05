package is.ru.nlp.textsum.test;

import static org.junit.Assert.assertTrue;
import is.ru.nlp.textsum.util.StringUtil;

import org.junit.Before;
import org.junit.Test;

public class TestStringUtil {
	
	
	@Before
    public void setUp(){
		
	}
	
	@Test
	public void splitIntoSentences(){
		String s =  "\" Sumir eru ekki b�nir a� heyja , �tlu�u s�r kannski a� n� seinni sl�tti , "
				    + "�annig a� a�st��ur manna eru misjafnar en menn komast n� alveg � gegnum �a� held �g . \""; 
		
		String s2 =  "Sumir eru ekki b�nir a� heyja, �tlu�u s�r kannski a� n� seinni sl�tti, "
			    + "�annig a� a�st��ur manna eru misjafnar en menn komast n� alveg � gegnum �a� held �g."; 
		
		String s3 =  "\" Sumir eru ekki b�nir a� heyja, �tlu�u s�r kannski a� n� seinni sl�tti, "
			    + "�annig a� a�st��ur manna eru misjafnar en menn komast n� alveg � gegnum �a� held �g. \" B�ndur � t�num vegna illvi�rissp�r. "; 
		
		String[] sentence = StringUtil.splitIntoSentences(s);
		
		for(String str : sentence){
			System.out.println(str);
		}
		
		assertTrue(sentence.length == 1);
		
		String[] sentence2 = StringUtil.splitIntoSentences(s2);
		
		for(String str : sentence2){
			System.out.println(str);
		}
		
		assertTrue(sentence2.length == 1);
		
		String[] sentence3 = StringUtil.splitIntoSentences(s3);
		
		for(String str : sentence3){
			System.out.println(str);
		}
		
		assertTrue(sentence3.length == 2);
		
	}

}
