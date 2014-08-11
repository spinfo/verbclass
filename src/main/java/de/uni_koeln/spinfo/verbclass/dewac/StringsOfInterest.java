package de.uni_koeln.spinfo.verbclass.dewac;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class StringsOfInterest {
	
	private List<String> stringsOfInterest;
	
	private Map<String, Integer> stringsFound; 
	
	public void initialize(String filename) throws IOException{
		stringsOfInterest = new ArrayList<String>();
		stringsFound = new HashMap<String, Integer>();
		
		BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
		String nextString = in.readLine();
		while(nextString!=null){
			nextString = nextString.trim();
			if(nextString!=""){
				stringsOfInterest.add(nextString);
				stringsFound.put(nextString, 0);
			}
			nextString = in.readLine();
		}
		in.close();
	}
	
	public List<String> getStringsOfInterest() {
		return stringsOfInterest;
	}

	
	public int getId(String string){
		int toReturn = -1;
		toReturn = stringsOfInterest.indexOf(string);
		return toReturn;
	}
	
	public boolean isOfInterest(String string){
		boolean toReturn = stringsOfInterest.contains(string);
		if(toReturn){
			stringsFound.put(string, stringsFound.get(string)+1);
		}
//		if(toReturn){
//			System.out.println(string);
//		}
		return toReturn;
	}
	
	public void printMap(){
		Set<String> keySet = stringsFound.keySet();
		for (String string : keySet) {
			System.out.println(string + ": " + stringsFound.get(string));
		}
	}
	
	public String toString(){
		return stringsOfInterest.toString();
	}

}
