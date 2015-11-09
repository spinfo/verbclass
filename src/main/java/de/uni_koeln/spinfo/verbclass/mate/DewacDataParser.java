package de.uni_koeln.spinfo.verbclass.mate;
import is2.data.SentenceData09;
import is2.io.CONLLWriter09;
import is2.parser.Parser;
import is2.tools.Tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.uni_koeln.spinfo.verbclass.deprecated.ActantAggregator;


/**
 * A class to use the Mate tools dependency parser on pre-tagged data 
 * e.g. (s)Dewac-Data 
 *  
 * @see https://code.google.com/p/mate-tools/
 * @author jhermes
 *
 */
public class DewacDataParser {
	
	private is2.mtag.Tagger morphTagger;
	private Tool parser;
	private Dewac2SentenceDataConverter conv = new Dewac2SentenceDataConverter();
	
	/**
	 * Initializes the parser with specified Models 
	 * @param morphTaggerModel Model of a morphosyntactic tagger
	 * @param parserModel Model of a dependency parser
	 */
	public void initialize(String morphTaggerModel, String parserModel){
		System.out.println("\nReading the model of the morphologic tagger");
		morphTagger = new is2.mtag.Tagger(morphTaggerModel);
		System.out.println("\nReading the model of the dependency parser");
		parser = new Parser(parserModel);		
	}
	
	/**
	 * Parses the sentences of each file of the specified input folder 
	 * to a file in the output folder 
	 * @param inputFolder Folder with input files
	 * @param outputFolder Folder the output file were written to.
	 * @param maxSentences max count of sentences to parse for each file
	 * @throws IOException
	 */
	public void parseToFolder(File inputFolder, File outputFolder, int maxSentences) throws IOException{
		if(maxSentences<1){
			maxSentences = Integer.MAX_VALUE;
		}
		if(!outputFolder.exists()){
			outputFolder.mkdirs();
		}
		File[] listFiles = inputFolder.listFiles();
		for (File inputFile : listFiles) {
			System.out.println("Parsing: " + inputFile.getName());
			File outputFile = new File(outputFolder + "/" + inputFile.getName());
			parseToFile(new FileInputStream(inputFile), new FileOutputStream(outputFile), maxSentences);
		}
	}
	
	public void countPOSTags(File inputFolder, int maxSentences) throws IOException{
		
		Map<String, Integer> posCounts = new TreeMap<String, Integer>(); 
		if(maxSentences<1){
			maxSentences = Integer.MAX_VALUE;
		}
		
		File[] listFiles = inputFolder.listFiles();
		for (File inputFile : listFiles) {
			System.out.println("Parsing: " + inputFile.getName());
			countPOSTags(new FileInputStream(inputFile), maxSentences, posCounts);			 
		}
		Set<String> keySet = posCounts.keySet();
		for (String string : keySet) {
			System.out.println(string + ": " + posCounts.get(string));
		}
	}
	
	private Map<String, Integer> countPOSTags(InputStream in, int maxSentences, Map<String, Integer> posCounts) throws IOException {
		List<SentenceData09> processStream = conv.processStream(in, 0);
		
		
		for (SentenceData09 sentence : processStream) {			
			//System.out.println("\nApplying the morphologic tagger");
			//sentence=morphTagger.apply(sentence);
			//System.out.println("\nApplying the parser");
			//sentence=parser.apply(sentence);			
			//writer.write(sentence, CONLLWriter09.NO_ROOT);	
			String[] postags = sentence.ppos;
			for (String postag : postags) {
				Integer count = posCounts.get(postag);
				if(count==null){
					count = 0;
				}
				count++;
				posCounts.put(postag, count);
			}
			//System.out.println(maxSentences);
			if(maxSentences--<1){
				break;
			}
		}
		return posCounts;
	}

	/** 
	 * Parses the sentences found on the input stream and writes them to the output stream
	 * @param in Input stream
	 * @param out Output stream
	 * @param maxSentences max sentences to parse
	 * @throws IOException
	 */
	public void parseToFile(InputStream in, OutputStream out, int maxSentences) throws IOException{
		List<SentenceData09> processStream = conv.processStream(in, 0);
		
		
		CONLLWriter09 writer = new is2.io.CONLLWriter09(new PrintWriter(new OutputStreamWriter(out)));
	
		for (SentenceData09 sentence : processStream) {			
			//System.out.println("\nApplying the morphologic tagger");
			sentence=morphTagger.apply(sentence);
			//System.out.println("\nApplying the parser");
			sentence=parser.apply(sentence);			
			writer.write(sentence, CONLLWriter09.NO_ROOT);	
			System.out.println(maxSentences);
			if(maxSentences--<1){
				break;
			}
		}
		writer.finishWriting();
	}
	

	
	
	@Deprecated
	public void parseToActantFile(String verb, InputStream in, OutputStream out) throws IOException{
		List<SentenceData09> processStream = conv.processStream(in, 0);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
		ActantAggregator aa = new ActantAggregator();
		System.out.println("Parsing");
		int count = 0;
		for (SentenceData09 sentence : processStream) {			
			//System.out.println("\nApplying the morphologic tagger");
			sentence=morphTagger.apply(sentence);
			//System.out.println("\nApplying the parser");
			sentence=parser.apply(sentence);			
			aa.addActants(verb, sentence);
			count++;
			if(count>100){
				break;
			}
		}
		
		System.out.println(aa.getActantsWithVerbs());
		
		System.out.println("Writing file");
		Map<String, Map<String, Map<String, Integer>>> verbsWithActants = aa.getVerbsWithActants();
		Map<String, Map<String, Integer>> singleVerbMap = verbsWithActants.get(verb);
		Set<String> keySet = singleVerbMap.keySet();
		for (String edgeLabel : keySet) {
			Map<String, Integer> wordCount = singleVerbMap.get(edgeLabel);
			writer.println(edgeLabel + ": " + wordCount);
			writer.println();
		}
		writer.close();
	}

	

}
