package de.uni_koeln.spinfo.verbclass.mate;
import is2.data.SentenceData09;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ActantAggregator {
	
	
	
	
	private Set<String> verbsOfInterest;
	
	private Map<String, Map<String, Map<String, Integer>>> verbsWithActants;
	
	private Map<String, Map<String, Integer>> actantsWithVerbs;
	
	public ActantAggregator(){
		verbsWithActants = new HashMap<String, Map<String, Map<String, Integer>>>();
		actantsWithVerbs = new HashMap<String, Map<String, Integer>>();
		if(verbsOfInterest==null){
			verbsOfInterest=new HashSet<String>();
		}
	}
	
	public ActantAggregator(Set<String> verbsOfInterest){
		this();
		this.verbsOfInterest = verbsOfInterest;
	}
	
	public Map<String, Map<String, Map<String, Integer>>> getVerbsWithActants() {
		return verbsWithActants;
	}	
	
	public Map<String, Map<String, Integer>> getActantsWithVerbs() {
		return actantsWithVerbs;
	}

	public boolean addActants(SentenceData09 sd){
		if(verbsOfInterest==null){
			throw new RuntimeException("No verbs of interest specified.");
		}
		String[] plemmas = sd.plemmas;
		for (int i = 0; i < plemmas.length; i++) {
			if(verbsOfInterest.contains(plemmas[i])){
				addActants(plemmas[i], i, sd);
				return true;
			}
		}
		return false;
	}
	
	public boolean addActants(String verb, SentenceData09 sd){
		String[] plemmas = sd.plemmas;
		for (int i = 0; i < plemmas.length; i++) {
			if(plemmas[i].equals(verb)){
				addActants(plemmas[i], i, sd);
				return true;
			}
		}		
		return false;
	}
	
	private void addActants(String verb, int verbID, SentenceData09 sd) {
			
		verbsOfInterest.add(verb);
		
		List<String> edgeLabels = new ArrayList<String>();
		List<String> edgeLemmata = new ArrayList<String>();
		int[] heads = sd.pheads;
		for (int i = 0; i < heads.length; i++) {
			if(heads[i] == verbID){
				String lemmaOfInterest = sd.plemmas[i];
				String edgeLabelConv = sd.plabels[i];
				//If PrepObj: Get the contained nom
				if(sd.ppos[i].equals("APPR")||sd.ppos[i].equals("APPRART")||sd.ppos[i].equals("PTKVZ")){
					
					String nom = getNomLemmaFromPP(i, sd);
					if(nom==null){
						//No nominal element found in PO
						nonom++;
						continue;
					}
					lemmaOfInterest = nom;					
					edgeLabelConv = "PO";
				}				
				if(lemmaOfInterest.equals("<unknown>")){
					lemmaOfInterest = sd.forms[i];
				}
				
				if(isEdgeOfInterest(edgeLabelConv)){
					
					edgeLabels.add(edgeLabelConv);
					edgeLemmata.add(lemmaOfInterest);	
					
				}
			}			
		}	
		//If Verb is subordinate of an Aux/Modal add subj
		String lemmaOfInterest = null;
		int headOfVerbID = heads[verbID-1];
		if(headOfVerbID !=0){					
			for(int j=0; j<heads.length; j++){
				if(heads[j] == headOfVerbID){
					lemmaOfInterest = sd.plemmas[j];
					
					//If PrepObj: Get the contained nom
					String edgeLabel = sd.plabels[j];
					if(edgeLabel.startsWith("SB")){
						
						sbcount++;
						if(lemmaOfInterest.equals("<unknown>")){
							lemmaOfInterest = sd.forms[j];
						}			
					
						edgeLabels.add(edgeLabel);
						edgeLemmata.add(lemmaOfInterest);
					}							
				}
			}
		}
		//System.out.println(edgeLabels);
		//System.out.println(edgeLemmata);
		for(int i=0; i<edgeLabels.size(); i++){
			//Put Actants to verb
			Map<String, Map<String, Integer>> singleVerbMap = verbsWithActants.get(verb);
			if(singleVerbMap==null){
				singleVerbMap = new HashMap<String, Map<String,Integer>>();	
				verbsWithActants.put(verb, singleVerbMap);
			}			
			Map<String, Integer> edgeLabelMap = singleVerbMap.get(edgeLabels.get(i));
			if(edgeLabelMap==null){
				edgeLabelMap = new HashMap<String, Integer>();
				singleVerbMap.put(edgeLabels.get(i), edgeLabelMap);
			}
			Integer count = edgeLabelMap.get(edgeLemmata.get(i));
			if(count == null){
				edgeLabelMap.put(edgeLemmata.get(i), 1);
			}			
			else{
				edgeLabelMap.put(edgeLemmata.get(i), count+1);
			}
			
			//Put verb to actants
			String actantID = edgeLemmata.get(i)+"_"+edgeLabels.get(i);
			Map<String, Integer> verbCount = actantsWithVerbs.get(actantID);
			if(verbCount==null){
				verbCount = new HashMap<String, Integer>();
			}			
			Integer integer = verbCount.get(verb);
			if(integer==null){
				integer = 0;
			}
			integer+=1;
			verbCount.put(verb, integer);
			actantsWithVerbs.put(actantID, verbCount);
			
		}
		//System.out.println(verbsWithActants);
	}

	
	
	
	private String getNomLemmaFromPP(int prepNodeID, SentenceData09 sd){
		int[] heads = sd.pheads;
		for (int i =0; i<heads.length; i++) {
			if(heads[i]==prepNodeID && (sd.ppos[i].startsWith("N")||sd.ppos[i].startsWith("PREL"))){
				return sd.plemmas[i];
			}
		}		
		return null;
	}
	
	private int sbcount = 0;
	private int dacount = 0;
	private int oacount = 0;
	private int pocount = 0;
	
//	Set<String> subjects = new HashSet<String>();
//	Set<String> accObjs = new HashSet<String>();
//	Set<String> datObjs = new HashSet<String>();
//	Set<String> prepObjs = new HashSet<String>();
	
	private int nonom =0;
	
	private boolean isEdgeOfInterest(String edgeLabel){
		switch(edgeLabel){
			case "SB": sbcount++; return true;
			case "DA": dacount++; return true;
			case "OA": oacount++; return true;
			case "PO": pocount++; return true;		
		}
		return false;
	}
	
	public void exportToCSV(OutputStream outStream) throws FileNotFoundException{
		//There must be a faster solution to generate the matrix
		List<String> rows = new ArrayList<String>(verbsWithActants.keySet());
	
		List<String> columns = new ArrayList<String>(actantsWithVerbs.keySet());
		
		System.out.println("Rows (unique verbs): " + rows.size());
		System.out.println("Columns (unique actants): " + columns.size());
		
		int sum = 0;
		
		int[][] matrix = new int[columns.size()][rows.size()];
		int i = 0;
		for (String actualActant : columns) {
			Map<String, Integer> verbCounts = actantsWithVerbs.get(actualActant);
			Set<String> verbs = verbCounts.keySet();
			for (String verb : verbs) {
				matrix[i][rows.indexOf(verb)] = verbCounts.get(verb);
				sum += verbCounts.get(verb);
			}
			i++;
		}
		
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream));
		//Table header
		System.out.println("****START ACTANTS****");
		for (int j=0; j<columns.size(); j++) {
			out.print("\tWert"+ j);
			System.out.println(columns.get(j));
		}
		System.out.println("****END ACTANTS****");
		out.println();
		System.out.println("columns: " + columns.size());
		//The rows		
		for(int j =0; j<rows.size(); j++){
			out.print(rows.get(j));
			System.out.println(rows.get(j));
			//The columns
			for (int k=0; k<columns.size(); k++){
				out.print("\t" + matrix[k][j]);
				//System.out.println(k);
			}
			out.println();
		}
		out.flush();
		out.close();
		
		OutputStream matrixOutputStream = new FileOutputStream("output/singleFiles/matrix.txt");
		printMatrix(matrix, matrixOutputStream);
		
		System.out.println("Overall actants: " + sum);
		System.out.println("Subjects: "+ sbcount);
		System.out.println("Direct objects: " + oacount);  
		System.out.println("Propositional objects/adjuncts: " + pocount);
		System.out.println("Dative objects: " + dacount);
		System.out.println("Number of prepositional phrases, the nominal element can't be found: " + nonom);
	}
	
	public void printActantMap(){
		Set<String> keySet = actantsWithVerbs.keySet();
		Map<Integer, Set<String>> actantCounts = new TreeMap<Integer, Set<String>>();
		
		for (String string : keySet) {
			Map<String, Integer> map = actantsWithVerbs.get(string);
			Set<String> set = actantCounts.get(map.size());
			if(set==null){
				set = new HashSet<String>();
			}
			set.add(string);
			actantCounts.put(map.size(), set);
		}
		Set<Integer> keySet2 = actantCounts.keySet();
		for (Integer integer : keySet2) {
			System.out.println(integer + " " + actantCounts.get(integer));
		}
	}
	
	public void printMatrix(int[][] matrix, OutputStream outStream){
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream));
		int datalength = 0;
		//The rows		
		for(int j=0; j<matrix[0].length; j++){
			for (int k=0; k<matrix.length; k++){
				out.print(matrix[k][j]);
				if(k!=matrix.length-1){
					out.print("\t");
				}
				datalength++;
				
			}
			out.println();
		}
		System.out.println(datalength);
		out.flush();
		out.close();
		
	}
	
	public Map<String, Map<String, List<String>>> getNActantsOfEachActantClassFromVerbs(int number){
		try {
			//Export most present actants of verbs to a file
			PrintWriter out = new PrintWriter(new FileWriter(new File("output/singleFiles/ verb_actant_list_15.txt")));
			
			
			Map<String, Map<String, List<String>>> toReturn = new HashMap<String, Map<String, List<String>>>();
			Set<String> keySet = verbsWithActants.keySet();
			for (String verb : keySet) {
				out.println("**************************************************************");
				out.println(verb.toUpperCase()+":");
				Map<String, Map<String, Integer>> actantClasses = verbsWithActants.get(verb);
				// Die verschiedenen Rollen der Aktanten (SU, DO, PO, DA)
				Set<String> actantRoles = actantClasses.keySet();
				
				
				Map<String, List<String>> actantClassLists = new HashMap<String, List<String>>(); 
				for (String actantClass : actantRoles) {
					if(actantClass.equals("SBP")){
						continue;
					}
					out.println("++++++++++++++++++");
					out.println(actantClass + ":");
					Map<String, Integer> actantCount = actantClasses.get(actantClass);
					List<Integer> values = new ArrayList<Integer>(actantCount.values());
					Collections.sort(values);
					List<String> mostPresentActants = new ArrayList<String>();
					for (Integer count : values) {
						Set<String> keySet3 = actantCount.keySet();
						for (String string : keySet3) {
							if(count.equals(actantCount.get(string))){
								mostPresentActants.add(string);
							}
							if(mostPresentActants.size() >= number){
								break;
							}
						}
						if(mostPresentActants.size() >= number){
							break;
						}
					}
					System.out.println(mostPresentActants.size());
					for (String string : mostPresentActants) {
						if(string.equals("%")){
							continue;
						}
						out.println(string);
					}
					actantClassLists.put(actantClass, mostPresentActants);
				}
				toReturn.put(verb, actantClassLists);
			}		
			out.flush();
			out.close();
			return toReturn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
