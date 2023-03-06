package de.uni_koeln.spinfo.verbclass.verbfeatures;

import is2.data.SentenceData09;
import is2.io.CONLLReader09;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.io.ArgumentClasses;
import de.uni_koeln.spinfo.verbclass.io.ClassifiedNomsReader;

public class VectorOutputGenerator {
	
	
	public void serializeVerbsWithFeatures() throws IOException{
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
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("output_150512/verbfeatures.bin")));
		oos.writeObject(verbsWithFeatures);
	}
	
	
	public void exportVerbVectors(File inputFolder, File outputFolder) throws ClassNotFoundException, IOException{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("output_150512/verbfeatures.bin")));
		Map<String, VerbFeatures> verbsWithFeatures = (Map<String, VerbFeatures>) ois.readObject();
		
		//System.out.println(verbsWithFeatures);
		File[] listFolders = inputFolder.listFiles();
		for (File innerFolder : listFolders) {
			if(innerFolder.getName().endsWith("PREVIEW")){
				continue;
			}
			String outputFileName = innerFolder.getName();
			System.out.println(outputFileName);
//			File[] listFiles = innerFolder.listFiles();
//			for (File file : listFiles) {
//				
//			}
			ClassifiedNomsReader cnr = new ClassifiedNomsReader();
			ArgumentClasses nomsWithClasses = cnr.getNomsWithClasses(innerFolder);
			VerbFeatureIntersection vfi = new VerbFeatureIntersection();
			
			PrintWriter out = new PrintWriter(new FileWriter(new File(outputFolder + "/" + outputFileName + "_Types.csv")));
			List<String> buildIntersection = vfi.buildIntersection(verbsWithFeatures, nomsWithClasses, true, true);
			for (String string : buildIntersection) {
				out.println(string);
			}
			out.flush();
			out.close();
			
			ois = new ObjectInputStream(new FileInputStream(new File("output_150512/verbfeatures.bin")));
			verbsWithFeatures = (Map<String, VerbFeatures>) ois.readObject();
			
			out = new PrintWriter(new FileWriter(new File(outputFolder + "/" + outputFileName + "_Tokens.csv")));
			buildIntersection = vfi.buildIntersection(verbsWithFeatures, nomsWithClasses, true, false);
			for (String string : buildIntersection) {
				out.println(string);
			}
			out.flush();
			out.close();
		}
		
		
				
	}

}
