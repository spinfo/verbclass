package de.uni_koeln.spinfo.verbclass.evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ResultImporter {
	
	public static Map<String, Integer> importClusterResults(File file) throws IOException{
		Map<String, Integer> toReturn = new TreeMap<String, Integer>();
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine();
		while(line!=null){
			String[] parts = line.split("\t");
			toReturn.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
			line = in.readLine();
		}		
		return toReturn;
	}

}
