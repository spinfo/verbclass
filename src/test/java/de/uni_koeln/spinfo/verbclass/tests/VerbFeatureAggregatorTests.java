package de.uni_koeln.spinfo.verbclass.tests;
import is2.data.SentenceData09;
import is2.io.CONLLReader09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.io.ArgumentClasses;
import de.uni_koeln.spinfo.verbclass.io.ClassifiedNomsReader;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VectorOutputGenerator;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatureIntersection;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatures;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFlatFeatureAggregator;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbParsedFeatureAggregator;


public class VerbFeatureAggregatorTests {

	@Test
	public void test() {
		VerbParsedFeatureAggregator vfa = new VerbParsedFeatureAggregator();
		
		
		File folder = new File("output/100verbsParsed");
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
	public void testToMatrix() throws IOException {
		VerbParsedFeatureAggregator vfa = new VerbParsedFeatureAggregator();
		
		
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
		ClassifiedNomsReader cnr = new ClassifiedNomsReader();
		ArgumentClasses nomsWithClasses = cnr.getNomsWithClasses(new File("data/classArg"));
		VerbFeatureIntersection vfi = new VerbFeatureIntersection();
		
		PrintWriter out = new PrintWriter(new FileWriter(new File("output/CyrilMatrixAggregatedTypes_9_3_5_3.csv")));
		List<String> buildIntersection = vfi.buildIntersection(verbsWithFeatures, nomsWithClasses, true, true);
		for (String string : buildIntersection) {
			out.println(string);
		}
		out.flush();
		out.close();		
	}
	
	@Test
	public void testFlatFeaturesAggregator() throws IOException {
		VerbFlatFeatureAggregator vfa = new VerbFlatFeatureAggregator();
		
		
		File folder = new File("output/100verbsParsed");
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
		Map<String, Integer> documentCounts = new TreeMap<String, Integer>();
		Map<String, VerbFeatures> verbsWithFeatures = vfa.getVerbsWithFeatures();
		Set<String> keySet = verbsWithFeatures.keySet();
		for (String string : keySet) {
			VerbFeatures vf = verbsWithFeatures.get(string);
			System.out.println(vf);
			Map<String, Integer> unlabeledCoOccurences = vf.getUnlabeledCoOccurences();
			Set<String> keySet2 = unlabeledCoOccurences.keySet();
			for (String string2 : keySet2) {
				Integer integer = documentCounts.get(string2);
				if(integer==null){
					integer = 0;
				}
				integer++;
				documentCounts.put(string2, integer);
			}			
		}
		Set<String> keySet2 = documentCounts.keySet();
		Map<Integer, List<String>> reverse = new TreeMap<Integer, List<String>>();
		for (String string : keySet2) {
			//System.out.println(string + ": " + documentCounts.get(string));
			Integer integer = documentCounts.get(string);
			if(integer<11){
				continue;
			}
			List<String> list = reverse.get(integer);
			if(list==null){
				list = new ArrayList<String>();
			}
			list.add(string);
			reverse.put(integer, list);
		}
		
		Set<Integer> keySet3 = reverse.keySet();
		for (Integer integer : keySet3) {
			System.out.println(integer + " (" +reverse.get(integer).size() + "): " + reverse.get(integer));
		}
	 
		vfa.info();
		System.out.println("Types: " + documentCounts.size());
	}
	
	@Test
	public void testVerbVectorExporter() throws ClassNotFoundException, IOException{
		VectorOutputGenerator vog = new VectorOutputGenerator();
		vog.exportVerbVectors(new File("output_150512"), new File("output_150512/verbvectors"));
	}

}
