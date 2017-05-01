package de.uni_koeln.spinfo.verbclass.goldstandard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataImporterNotAmbigious {
	
	
	List<Rater<Integer>> raters;
	
	Map<String, Rated<Integer>> ratedAttributes;
	
	
	
	public DataImporterNotAmbigious() {
		raters = new ArrayList<Rater<Integer>>();
		ratedAttributes = new HashMap<String, Rated<Integer>>();
	}



	public void importFromFile(String filename) throws IOException{
		File file = new File(filename);
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine();
		String[] split = line.split(";");
		for (int i = 1; i < split.length; i++) {
			String raterName = split[i].trim();
			if(!raterName.isEmpty()){
				raters.add(new Rater<Integer>(raterName));
			}
		}
		line = in.readLine();
		while(line!=null){
			
			split = line.split(";");
			String attribute = split[0];
			Rated<Integer> rated = new Rated<Integer>(attribute);
			for (int i = 1; i < split.length; i++) {
				Integer value = Integer.parseInt(split[i].trim());
				Rater rater = raters.get(i-1);
				rater.addRate(attribute, value);
				rated.addRate(rater, value);
				ratedAttributes.put(attribute, rated);			
				
			}
			line = in.readLine();
		}
		System.out.println();
		ConsistentRated<Integer> cr = new ConsistentRated<Integer>(ratedAttributes.values());
		System.out.println(cr.getTypesWithMinRaterAgreementN(1).size() + ":  " + cr.getTypesWithMinRaterAgreementN(1));
		System.out.println(cr.getTypesWithMinRaterAgreementN(2).size() + ":  " + cr.getTypesWithMinRaterAgreementN(2));
		System.out.println(cr.getTypesWithMinRaterAgreementN(3).size() + ":  " + cr.getTypesWithMinRaterAgreementN(3));
		System.out.println(cr.getTypesWithMinRaterAgreementN(4).size() + ":  " + cr.getTypesWithMinRaterAgreementN(4));
	}
	
	public void printAttributesWithRates(){
		Set<String> keySet = ratedAttributes.keySet();
		for (String string : keySet) {
			System.out.print(string + ": ");
			Rated<Integer> rated = ratedAttributes.get(string);
			System.out.println(rated);
		}
	}
	 
}
