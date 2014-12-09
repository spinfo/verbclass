package de.uni_koeln.spinfo.verbclass.verbfeatures;
import is2.data.SentenceData09;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Class to aggegate verb features from sentences analyzed 
 * via the Mate tools dependence parser.
 * 
 * @author jhermes
 *
 */
public class VerbFeatureAggregator {
	
	
	private Map<String, VerbFeatures> verbsWithFeatures;
	
	private CategorySpecifier slf;
	
	/**
	 * Initializes a new VerbFeatureAggregator without specifying any verb of interest.
	 * Note that you have to specify the verbs of interest within the addVerbFeatures method. 
	 */
	public VerbFeatureAggregator(){
		if(verbsWithFeatures==null){
			verbsWithFeatures = new HashMap<String,VerbFeatures>();
		}
		slf = new CategorySpecifier();
	}
	
	/**
	 * Initializes a new VerbFeatureAggregator for specified verbs of interest;
	 * @param verbsOfInterest Verbs to aggregate features for.
	 */
	public VerbFeatureAggregator(Set<String> verbsOfInterest){
		this();
		for (String lemma : verbsOfInterest) {
			verbsWithFeatures.put(lemma, new VerbFeatures(lemma));
		}
	}
	
	/** 
	 * Searches the specified dependence tree of the sentence for
	 * features of verbs of interest and aggregates 
	 * them to the corresponding VerbFeature object.
	 * @param sd A sentence as dependence tree
	 * @return Number of verbs of interest found in the dependence tree
	 */
	public int addVerbFeatures(SentenceData09 sd){
		if(verbsWithFeatures.size()==0){
			throw new RuntimeException("No verbs of interest specified.");
		}
		int verbsFound = 0;
		String[] plemmas = sd.plemmas;
		for (int i = 0; i < plemmas.length; i++) {
			if(verbsWithFeatures.containsKey(plemmas[i])){
				addVerbFeatures(plemmas[i], i, sd);
				verbsFound++;
			}
		}
		return verbsFound;
	}
	
	/**
	 * Searches the specified dependence tree of the sentence for
	 * features for features of the specified verb and aggregates 
	 * them to the corresponding VerbFeature object.
	 * @param verb Verb of interest
	 * @param sd A sentence as dependence tree
	 * @return true, if verb is found in the dependence tree, false otherwise
	 */
	public boolean addVerbFeatures(String verb, SentenceData09 sd){
		String[] plemmas = sd.plemmas;
		for (int i = 0; i < plemmas.length; i++) {
			if(plemmas[i].equals(verb)){
				addVerbFeatures(plemmas[i], i, sd);
				return true;
			}
		}		
		return false;
	}
	
	private void addVerbFeatures(String verb, int verbID, SentenceData09 sd) {
			
		VerbFeatures verbFeatures = verbsWithFeatures.get(verb);
		if(verbFeatures==null){
			verbFeatures = new VerbFeatures(verb);
			verbsWithFeatures.put(verb, verbFeatures);
		}		
		
		
		
		int[] heads = sd.pheads;
		
		int successCounter = 0;
		
		//setImperative
		if(sd.pfeats[verbID]!=null && sd.pfeats[verbID].contains("|imp")){
			verbFeatures.increaseImperativeCount();
			successCounter++;
		}
		
		//Search features within dependent nodes  
		for (int i = 0; i < heads.length; i++) {
			if(heads[i] == verbID){
				String lemmaOfInterest = sd.plemmas[i];
				String edgeLabelConv = sd.plabels[i];
				
				//1. Set dependent actants
				//If PrepObj: Get the contained nom
				if(sd.ppos[i].equals("APPR")||sd.ppos[i].equals("APPRART")||sd.ppos[i].equals("PTKVZ")){
					
					String nom = getNomLemmaFromPP(i, sd);
					if(nom==null){
						//No nominal element found in PO
						nonom++;
						continue;
					}
					 //"mit Absicht";
					if(sd.plemmas[i].equals("mit")){
						if(nom.equals("Absicht")||nom.equals("Absicht")){
							verbFeatures.increaseWithIntentCount();
						}
						if(nom.equals("Sorgfalt")){
							verbFeatures.increaseCarefullyCount();
						}
					}
					
					//"in/seit/von"
					if(sd.plemmas[i].equals("in")||sd.plemmas[i].equals("seit")||sd.plemmas[i].equals("vor")){
						if(slf.belongsToCategory(nom)!=null && slf.belongsToCategory(nom).equals("timeUnit")){
							verbFeatures.increaseTimeUnitCount();
						}
					}
					
					lemmaOfInterest = nom;					
					edgeLabelConv = "PO";
					
					
				}				
				if(lemmaOfInterest.equals("<unknown>")){
					lemmaOfInterest = sd.forms[i];
				}				
				if(setEdgeOfInterest(edgeLabelConv, lemmaOfInterest, verbFeatures)){
					successCounter++;					
				}
				
				//2. Set other dependent features
				String belongsToCategory = slf.belongsToCategory(sd.plemmas[i]);
				if(belongsToCategory!=null){
					switch (belongsToCategory) {
					
					case "duration": verbFeatures.increaseDurationCount();
					break;
					case "intent": verbFeatures.increaseWithIntentCount();
					break;
					case "carefully": verbFeatures.increaseCarefullyCount();
					break;
					case "almostly": verbFeatures.increaseAlmostlyCount();
					break;
					default:
						break;
					}
				}
				
			}			
		}	
		
		//Search aux-dependent subjects
		String lemmaOfInterest = null;
		int headOfVerbID = heads[verbID-1];
		if(headOfVerbID !=0){					
			for(int j=0; j<heads.length; j++){
				if(heads[j] == headOfVerbID){
					lemmaOfInterest = sd.plemmas[j];
					String belongsToCategory = slf.belongsToCategory(sd.plemmas[j]);
					if(belongsToCategory!=null){
						if(belongsToCategory.equals("persuade")){
							verbFeatures.increasePersuadeCount();
						}
						if(belongsToCategory.equals("stoppedAction")){
							verbFeatures.increaseStoppedActionCount();
						}
					}
					
					//If PrepObj: Get the contained nom
					String edgeLabel = sd.plabels[j];
					if(edgeLabel.startsWith("SB")){
						
						sbcount++;
						if(lemmaOfInterest.equals("<unknown>")){
							lemmaOfInterest = sd.forms[j];
						}	
						verbFeatures.addSubject(lemmaOfInterest);
					
					}		
					
				}
			}
		}
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
	private int nonom =0;
	
	private boolean setEdgeOfInterest(String edgeLabel, String lemma, VerbFeatures vf){
		switch(edgeLabel){
			case "SB": sbcount++; vf.addSubject(lemma); return true;
			case "DA": dacount++; vf.addDativeObject(lemma); return true;
			case "OA": oacount++; vf.addDirectObject(lemma); return true;
			case "PO": pocount++; vf.addPrepositionalObject(lemma); return true;		
		}
		return false;
	}

	
	/**
	 * A simple helper method
	 * @param matrix
	 * @param outStream
	 */
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
	
	/** Returns all verbs of interest with their aggregated verb features
	 * @return all verbs of interest with their aggregated verb features
	 */
	public Map<String, VerbFeatures> getVerbsWithFeatures() {
		return verbsWithFeatures;
	}
	
	/** Returns the aggregated features of the specified verb
	 * @param verb Verb of interest
	 * @return aggregated features of the specified verb
	 */
	public VerbFeatures getFeatures(String verb){
		return verbsWithFeatures.get(verb);
	}
	
	public void info(){
		System.out.println("Subjects found: " + sbcount);
		System.out.println("Direct objects found: " + oacount);
		System.out.println("Dative objects found: " + dacount);
		System.out.println("Prepositional objects found: " + pocount + "(" + nonom + " of them had no nominal complement)");
	}

}
