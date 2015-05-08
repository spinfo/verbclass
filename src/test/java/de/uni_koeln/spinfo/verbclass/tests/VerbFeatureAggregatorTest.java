package de.uni_koeln.spinfo.verbclass.tests;
import is2.data.SentenceData09;
import is2.io.CONLLReader09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.io.ArgumentClasses;
import de.uni_koeln.spinfo.verbclass.io.ClassifiedNomsReader;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatureAggregator;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatureIntersection;
import de.uni_koeln.spinfo.verbclass.verbfeatures.VerbFeatures;


public class VerbFeatureAggregatorTest {

	@Test
	public void test() {
		VerbFeatureAggregator vfa = new VerbFeatureAggregator();
		
		
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
		ClassifiedNomsReader cnr = new ClassifiedNomsReader();
		ArgumentClasses nomsWithClasses = cnr.getNomsWithClasses(new File("data/classArg"));
		VerbFeatureIntersection vfi = new VerbFeatureIntersection();
		
		PrintWriter out = new PrintWriter(new FileWriter(new File("output/100verbsMatrix.csv")));
		List<String> buildIntersection = vfi.buildIntersection(verbsWithFeatures, nomsWithClasses);
		for (String string : buildIntersection) {
			out.println(string);
		}
		out.flush();
		out.close();
		
		
	}

}
