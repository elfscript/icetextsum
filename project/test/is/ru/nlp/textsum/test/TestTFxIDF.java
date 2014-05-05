package is.ru.nlp.textsum.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import is.ru.nlp.textsum.unsupervised.TFxIDF;
import is.ru.nlp.textsum.util.ReadInFile;

public class TestTFxIDF {
	
	private ReadInFile readIn;
	private final String TEST_SUMMARY = "Hann segir b�ndur � H�f�ahverfi �tla a� taka st��una eftir ve�ursp�r kv�ldsins, enn geti brug�i� til beggja vona \n" +
			"Ve�urstofa �slands sp�ir nor�anhr�� � f�studag me� slyddu e�a snj�komu � 150-250 metra h�� yfir sj�varm�li og vindhra�a allt a� 15-23 m/s \n" +
			"Birgir er forma�ur F�lags Sau�fj�rb�nda vi� Eyjafj�r� og �tlar a� heyra � m�nnum � sveitinni � kv�ld \n" +
			"\"Sumir eru ekki b�nir a� heyja, �tlu�u s�r kannski a� n� seinni sl�tti, �annig a� a�st��ur manna eru misjafnar en menn komast n� alveg � gegnum �a� held �g.\" \n" +
			"A�spur�ur segir ��rarinn allan gang � �v� hversu vel menn s�u undir �a� b�nir a� taka f�� heim � t�n svo snemma \n";

	
	@Before
    public void setUp(){
		readIn = new ReadInFile();
	}
	
	@Test
	public void testTFxIDF() {
		final File folder = new File("./resources/full/ice/text1.txt");
		List<String> lines = readIn.readSmallFileLines(folder.getPath());
		StringBuilder fullText = new StringBuilder();
		
		for ( String s : lines ) {
			  fullText.append(s).append(" ");
		}
		
		TFxIDF tdxidf = new TFxIDF();
		
		String summary = tdxidf.createSummary(fullText.toString(), false, 20, 100);
		
		assertTrue(summary.equals(TEST_SUMMARY));
	}
	
	@Test
	public void testTFxIDFStemmed() {
		final File folder = new File("./resources/full/ice/text1.txt");
		List<String> lines = readIn.readSmallFileLines(folder.getPath());
		StringBuilder fullText = new StringBuilder();
		
		for ( String s : lines ) {
			  fullText.append(s).append(" ");
		}
		
		TFxIDF tdxidf = new TFxIDF();
		
		String summary = tdxidf.createSummary(fullText.toString(), true, 20, 100);
		
		assertTrue(summary.equals(TEST_SUMMARY));
	}
}