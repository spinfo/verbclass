package de.uni_koeln.spinfo.verbclass.verbfeatures;
import is2.data.SentenceData09;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Class to aggegate verb features from sentences with SentenceData09 format
 * 
 * @author jhermes
 *
 */
public abstract class AbstractVerbFeatureAggregator {
	
	
	protected Map<String, VerbFeatures> verbsWithFeatures;
	
	protected CategorySpecifier slf;
	
	/**
	 * Initializes a new VerbFeatureAggregator without specifying any verb of interest.
	 * Note that you have to specify the verbs of interest within the addVerbFeatures method. 
	 */
	public AbstractVerbFeatureAggregator(){
		if(verbsWithFeatures==null){
			verbsWithFeatures = new HashMap<String,VerbFeatures>();
		}
		slf = new CategorySpecifier();
	}
	
	/**
	 * Initializes a new VerbFeatureAggregator for specified verbs of interest;
	 * @param verbsOfInterest Verbs to aggregate features for.
	 */
	public AbstractVerbFeatureAggregator(Set<String> verbsOfInterest){
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
	
	protected abstract void addVerbFeatures(String verb, int verbID, SentenceData09 sd);// {

	
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
	
	public abstract void info();
	
}
