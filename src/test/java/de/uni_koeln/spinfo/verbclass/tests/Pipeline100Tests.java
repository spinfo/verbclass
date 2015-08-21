package de.uni_koeln.spinfo.verbclass.tests;

import is2.data.SentenceData09;
import is2.io.CONLLReader09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.dewac.DewacSplitter;
import de.uni_koeln.spinfo.verbclass.dewac.StringsOfInterest;
import de.uni_koeln.spinfo.verbclass.mate.DewacDataParser;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatureAggregator;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatures;

public class Pipeline100Tests {

	@Test
	public void testCreateFilePerVerb() throws IOException{

		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("data/100verbsPure.txt");		
		DewacSplitter ds = new DewacSplitter("output/100verbs");
		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 3000);
	}
	
	@Test
	public void testCreateFilePerNom() throws IOException{

		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("data/nomsPure.txt");		
		DewacSplitter ds = new DewacSplitter("output/noms");
		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 3000);
	}
//	
//	@Test
//	public void testCreateFileForOneVerb() throws IOException{
//
//		StringsOfInterest soi = new StringsOfInterest();
//		List<String> oI = new ArrayList<String>();
//		oI.add("zersägen");
//		oI.add("hämmern");
//		
//		soi.setStringsOfInterest(oI);
//		//soi.setStringsOfInterest("data/100verbsPure.txt");		
//		DewacSplitter ds = new DewacSplitter("output");
//		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 3000);
//	}
	
	@Test
	public void testVerbFolderParsing() throws FileNotFoundException, IOException{
		//File inputFile = new File("sentencesWithVerbs");
		//File outputFile = new File("parsedSentencesWithVerbs");
		File inputFile = new File("output/100verbs_2");
		File outputFile = new File("output/100verbsParsedNew_2");
						
		String mtModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model";
		String pModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model";
		DewacDataParser dp = new DewacDataParser();
		
		dp.initialize(mtModel, pModel);		
		dp.parseToFolder(inputFile, outputFile,1000);
	}
	
	@Test
	public void splitToParseOther() throws IOException{
		DewacSplitter ds = new DewacSplitter("output/100verbs_2");
		ds.splitFilesinFolder("output/100verbs", 1002, 2004);
		
	}
	
	@Test
	public void verbFeatureAggregation() {
		VerbFeatureAggregator vfa = new VerbFeatureAggregator();
		
		
		File folder = new File("output/100verbsParsedConcat");
		File[] listFiles = folder.listFiles();
		int sum = 0;
		for (File file : listFiles) {
			CONLLReader09 reader = new CONLLReader09(true);
			reader.startReading(file.getAbsolutePath());
			SentenceData09 nextCoNLL09 = reader.getNextCoNLL09();
			
			String verb = file.getName().substring(0, file.getName().length()-4);
			
			while(nextCoNLL09!=null){
				vfa.addVerbFeatures(verb, nextCoNLL09);			
				nextCoNLL09 = reader.getNextCoNLL09();
				sum++;
			}
		}
		Map<String, VerbFeatures> verbsWithFeatures = vfa.getVerbsWithFeatures();
		Set<String> keySet = verbsWithFeatures.keySet();
		for (String string : keySet) {
			VerbFeatures vf = verbsWithFeatures.get(string);
			System.out.println(vf);
		}
	}
	
	@Test
	public void countParsedSentencesPerVerb(){
		File folder = new File("output/noms");
		File[] listFiles = folder.listFiles();
		int sum = 0;
		for (File file : listFiles) {
			CONLLReader09 reader = new CONLLReader09(true);
			reader.startReading(file.getAbsolutePath());
			SentenceData09 nextCoNLL09 = reader.getNextCoNLL09();
			int counter = 1;
			String verb = file.getName().substring(0, file.getName().length()-4);
			
			while(nextCoNLL09!=null){
					
				nextCoNLL09 = reader.getNextCoNLL09();
				sum++;
				counter++;
			}
			System.out.println(counter + "\toccurences: " + verb);
		}
		System.out.println("All verb occurences: " + sum);
		
	}
	
	@Test
	public void concatFiles() throws IOException{
		String sourceFolder1 = "output/100verbsParsed";
		String sourceFolder2 = "output/100verbsParsedNew_2";
		String destFolder = "output/100verbsParsedConcat";
		
		File fold1 = new File(sourceFolder1);
		File[] listFiles1 = fold1.listFiles();
				
		File fold2 = new File(sourceFolder2);
		File[] listFiles2 = fold2.listFiles();
		
		Map<String, File> filesMap = new TreeMap<String, File>();
		for (File file : listFiles2) {
			filesMap.put(file.getName(), file);
		}
		
		for (File file : listFiles1) {
			File destFile = new File(destFolder +"/" + file.getName());
			PrintWriter out = new PrintWriter(new FileWriter(destFile));
			BufferedReader in = new BufferedReader(new FileReader(file));
			String nextLine = in.readLine();
			while(nextLine!=null){
				out.println(nextLine);
				nextLine = in.readLine();				
			}
			in.close();
			File file2 = filesMap.get(file.getName());
			if(file2!=null){
				in = new BufferedReader(new FileReader(file2));
				nextLine = in.readLine();
				while(nextLine!=null){
					out.println(nextLine);
					nextLine = in.readLine();				
				}
			}
			else{
				System.out.println(file.getName());
			}
			in.close();
			out.flush();
			out.close();
		}
		
	}

	@Test
	public void testSentenceCount() throws IOException{
		File checkFolder = new File("output/noms");
		File[] listFiles = checkFolder.listFiles();
		int i=0;
		for (File file : listFiles) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String nextLine = in.readLine();
			int counter = 0;
			while(nextLine!=null){
				if(nextLine.trim().isEmpty()){
					counter++;
				}
				nextLine = in.readLine();				
			}
			System.out.println(i++ + " " + counter + "\t" + file.getName());
		}
	}
	
	@Test
	public void cleanCorpusCount() throws IOException{
		File checkFolder = new File("output/noms");
		File[] listFiles = checkFolder.listFiles();
		int i=0;
		System.out.println(listFiles.length);
		for (File file : listFiles) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			PrintWriter out = new PrintWriter(new FileWriter("output/nomsNew/"+file.getName()));
			String nextLine = in.readLine();
			StringBuffer buff = new StringBuffer();
			boolean inSentence = false;
			while(nextLine!=null){
				buff.append(nextLine + "\n");
				
				if(nextLine.startsWith("</s>")){
					out.println(buff.toString());
					buff = new StringBuffer();
				}
				nextLine = in.readLine();				
			}
			out.flush();
			out.close();
		}
	}

}
