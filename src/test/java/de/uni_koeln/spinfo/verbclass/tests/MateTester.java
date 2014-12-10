package de.uni_koeln.spinfo.verbclass.tests;
import is2.data.SentenceData09;
import is2.io.CONLLReader09;
import is2.io.CONLLWriter09;
import is2.parser.Parser;
import is2.tools.Tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.mate.Dewac2SentenceDataConverter;
import de.uni_koeln.spinfo.verbclass.mate.DewacDataParser;


public class MateTester {
	
	
	
	@Test
	public void testAggregateAllVerbActants() throws IOException{
		
		String mtModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model";
		String pModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model";
		DewacDataParser dp = new DewacDataParser();
		dp.initialize(mtModel, pModel);
		
		File outputFolder = new File("output");
		if(!outputFolder.exists()){
			outputFolder.mkdirs();
		}
		
		File inputFolder = new File("sentencesWithVerbs");
		File[] listFiles = inputFolder.listFiles();
		for (File file : listFiles) {
			String fileName = file.getName();
			String verb = fileName.substring(0, fileName.length()-4);			
			System.out.println("Processing verb: " + verb);
			dp.parseToActantFile(verb, new FileInputStream(file), new FileOutputStream(new File(outputFolder + "/" + fileName)));
		}
	}

	@Test
	public void testActantToFileParsing() throws FileNotFoundException, IOException{
		File inputFile = new File("sentencesWithVerbs/abweichen.txt");
		File outputFile = new File("abweichen_actants.txt");
		outputFile.createNewFile();
		
		String mtModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model";
		String pModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model";
		DewacDataParser dp = new DewacDataParser();
		dp.initialize(mtModel, pModel);
		
		dp.parseToActantFile("abweichen", new FileInputStream(inputFile), new FileOutputStream(outputFile));
	}
	
	@Test
	public void testVerbFileParsing() throws FileNotFoundException, IOException{
		File inputFile = new File("sentencesWithVerbs/abgrenzen.txt");
		File outputFile = new File("parsedSentencesWithVerbs/abgrenzen.txt");
		outputFile.createNewFile();
		
		String mtModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model";
		String pModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model";
		DewacDataParser dp = new DewacDataParser();
		dp.initialize(mtModel, pModel);
		
		dp.parseToFile(new FileInputStream(inputFile), new FileOutputStream(outputFile),100);
	}
	
	@Test 
	public void testMateReader(){
		CONLLReader09 reader = new CONLLReader09(true);
		reader.startReading("output/parsedSentencesWithVerbs/abgrenzen.txt");
		SentenceData09 nextCoNLL09 = reader.getNextCoNLL09();
		int i=0;
		while(nextCoNLL09!=null){
			nextCoNLL09 = reader.getNextCoNLL09();
			String[] pfeats = nextCoNLL09.pfeats;
			for (String string : pfeats) {
				if(string!=null && string.endsWith("|imp")){
					System.out.println(nextCoNLL09);
				}
			}
			
			System.out.println(i++);
		}
	}
	
	@Test
	public void testVerbFolderParsing() throws FileNotFoundException, IOException{
		File inputFile = new File("sentencesWithVerbs");
		File outputFile = new File("parsedSentencesWithVerbs");
				
		String mtModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model";
		String pModel = "models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model";
		DewacDataParser dp = new DewacDataParser();
		
		dp.initialize(mtModel, pModel);		
		dp.parseToFolder(inputFile, outputFile,1000);
	}
	
	@Test
	public void testReadDewac() throws IOException {
		Dewac2SentenceDataConverter conv = new Dewac2SentenceDataConverter();
		//InputStream in = new FileInputStream(new File("texts/sdewac-v3.tagged_1999"));
		InputStream in = new FileInputStream(new File("C:/workspace_1/DeWaC/sentencesWithVerbs/ofInterest.txt"));
		List<SentenceData09> processStream = conv.processStream(in, 10);
		
		System.out.println("\nReading the model of the morphologic tagger");
		is2.mtag.Tagger morphTagger = new is2.mtag.Tagger("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model");
		System.out.println("\nReading the model of the dependency parser");
		Tool parser = new Parser("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model");
		
		
		CONLLWriter09 writer = new is2.io.CONLLWriter09("example-out.txt");
		
		
		
		
		for (SentenceData09 sentence : processStream) {
			
			System.out.println("\nApplying the morphologic tagger");
			sentence=morphTagger.apply(sentence);
			System.out.println("\nApplying the parser");
			sentence=parser.apply(sentence);
			writer.write(sentence, CONLLWriter09.NO_ROOT);
			
			
		}
		writer.finishWriting();
		
	}
	
	@Test
	public void testGetDitransitives() throws IOException {
		Dewac2SentenceDataConverter conv = new Dewac2SentenceDataConverter();
		//InputStream in = new FileInputStream(new File("texts/sdewac-v3.tagged_1999"));
		InputStream in = new FileInputStream(new File("sentencesWithVerbs/übermitteln.txt"));
		List<SentenceData09> processStream = conv.processStream(in, 1000);
		
		System.out.println("\nReading the model of the morphologic tagger");
		is2.mtag.Tagger morphTagger = new is2.mtag.Tagger("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model");
		System.out.println("\nReading the model of the dependency parser");
		Tool parser = new Parser("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model");
		
		
		CONLLWriter09 writer = new is2.io.CONLLWriter09("example-out.txt");
		
		
		
		
		for (SentenceData09 sentence : processStream) {
			boolean isDitrans = false;
			String[] lemmas = sentence.plemmas;
			System.out.println(lemmas);
			for (int i = 0; i < lemmas.length; i++) {
				if(lemmas[i].equals("übermitteln")){					
					isDitrans = true;
				}
			}
			if(isDitrans){
				System.out.println("\nApplying the morphologic tagger");
				sentence=morphTagger.apply(sentence);
				System.out.println("\nApplying the parser");
				sentence=parser.apply(sentence);
				writer.write(sentence, CONLLWriter09.NO_ROOT);
			}
			isDitrans = false;
			
			
		}
		writer.finishWriting();
		
	}

}
