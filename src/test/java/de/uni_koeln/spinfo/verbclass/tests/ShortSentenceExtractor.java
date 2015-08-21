package de.uni_koeln.spinfo.verbclass.tests;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.dewac.DewacSplitter;


public class ShortSentenceExtractor {

	@Test
	public void test() throws IOException {
		DewacSplitter ds = new DewacSplitter("C://Korpora//DeWaC");
		//Map<Integer, Integer> sentencesLengths = ds.analyseSentencesLengths(new File("C://Korpora//DeWaC//sdewac-v3.tagged"));
		//System.out.println(sentencesLengths);
		Set<Integer> lengthes = new HashSet<Integer>();
		lengthes.add(3); 
		lengthes.add(4); 
		lengthes.add(5);
		lengthes.add(6); 
		ds.getSentencesWithMaxLength(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), lengthes,10);
	}

}
