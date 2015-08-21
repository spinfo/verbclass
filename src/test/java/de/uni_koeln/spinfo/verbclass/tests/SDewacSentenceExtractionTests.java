package de.uni_koeln.spinfo.verbclass.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.dewac.DewacSplitter;
import de.uni_koeln.spinfo.verbclass.dewac.StringsOfInterest;

public class SDewacSentenceExtractionTests {

	@Test
	public void testCreateFilePerVerb() throws IOException{
		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("data/100verbsPure.txt");		
		DewacSplitter ds = new DewacSplitter("output/100verbsNew");
		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 3000);
	}

}
