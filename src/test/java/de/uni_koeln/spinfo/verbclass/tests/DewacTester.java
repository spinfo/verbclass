package de.uni_koeln.spinfo.verbclass.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.deprecated.DeWaCReader;
import de.uni_koeln.spinfo.verbclass.dewac.DewacSplitter;
import de.uni_koeln.spinfo.verbclass.dewac.StringsOfInterest;


public class DewacTester {

	@Test
	public void countOccurencesOfString() throws IOException{
		File inputFolder = new File("output/nomsNew");
		File[] listFiles = inputFolder.listFiles();
		for (File file : listFiles) {
			String name = file.getName();
			name = name.substring(0, name.length()-4);
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			int counter = 0;
			while(line!=null){
				if(line.contains(name)){
					counter++;
				}
				line = in.readLine();
			}
			System.out.println(name + ": " + counter);
		}
	}
	
	/**
	 * Splits the sdewac corpus to files containing 1MB data
	 * @throws IOException
	 */
	@Test
	public void testSDEWACSplitter() throws IOException {
		DewacSplitter ds = new DewacSplitter("C://Korpora//DeWaC//sdewac_splitted");
		ds.splitFile(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), 1000, 1000);
		//ds.splitFile(new File("C://Korpora//DeWaC//DEWAC-1.xml"), 100, 100);
	}
	
	@Test
	public void testOfInterest() throws IOException {
		
		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("verbsOfInterest");
		
		DewacSplitter ds = new DewacSplitter("sentencesWithVerbs");
		ds.sentencesOfInterestToFile(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 1000000);
		//ds.splitFile(new File("C://Korpora//DeWaC//DEWAC-1.xml"), 100, 100);
	}
	
	@Test
	public void testCreateFilePerVerb() throws IOException{

		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("data/100verbsPure.txt");
		
		DewacSplitter ds = new DewacSplitter("output/100verbs");
		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 3000);
	}
	
	@Test
	public void testCreateFilePerVerbUseParticles() throws IOException{

		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("data/splittedparticles.txt");
		
		DewacSplitter ds = new DewacSplitter("output/sentencesWithParticleVerbs");
		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 1000);
	}
	
	@Test
	public void testStringsOfInterest() throws IOException{
		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("verbsOfInterest");
		assertFalse(soi.isOfInterest("Killefitz"));
		assertTrue(soi.isOfInterest("halten"));
		System.out.println(soi);
	}
	
	@Test
	public void testShowHeader() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(new File("C://Korpora//DeWaC//sdewac-v3.tagged")));
		for(int i=0; i<20; i++){
			System.out.println(in.readLine());
		}
		System.out.println();
		System.out.println();
		in = new BufferedReader(new FileReader(new File("C://Korpora//DeWaC//DEWAC-1.xml")));
		for(int i=0; i<20; i++){
			System.out.println(in.readLine());
		}
	}
	
	@Test
	public void testListTags() throws IOException{
		DeWaCReader dr = new DeWaCReader("C://Korpora//DeWaC//sdewac_splitted");
		dr.process();
		Set<String> tagSet = dr.getTagSet();
		for (String string : tagSet) {
			System.out.println(string);
		}
	}

}
