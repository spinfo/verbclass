package de.uni_koeln.spinfo.verbclass.verbfeatures;
import is2.data.SentenceData09;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Class to aggregate verb features from sentences 
 * that were POS tagged and lemmatized
 * 
 * @author jhermes
 *
 */
public class VerbFlatFeatureAggregator extends AbstractVerbFeatureAggregator {
	
	private int cooccCount = 0;
	
	/**
	 * Initializes a new VerbFeatureAggregator without specifying any verb of interest.
	 * Note that you have to specify the verbs of interest within the addVerbFeatures method. 
	 */
	public VerbFlatFeatureAggregator(){
		super();
	}
	
	/**
	 * Initializes a new VerbFeatureAggregator for specified verbs of interest;
	 * @param verbsOfInterest Verbs to aggregate features for.
	 */
	public VerbFlatFeatureAggregator(Set<String> verbsOfInterest){
		super(verbsOfInterest); 
	}
	
	
	protected void addVerbFeatures(String verb, int verbID, SentenceData09 sd) {
			
		VerbFeatures verbFeatures = verbsWithFeatures.get(verb);
		if(verbFeatures==null){
			verbFeatures = new VerbFeatures(verb);
			verbsWithFeatures.put(verb, verbFeatures);
		}		
		
		String[] lemmata = sd.plemmas;
		
		int verbPos = 0;
		for(verbPos=0; verbPos<lemmata.length; verbPos++){
			if(lemmata[verbPos].equals(verb)){
				break;
			}
		}
		
		String[] posTags = sd.ppos;
		//To do context window?
		for (int i = 0; i < posTags.length; i++) {
			if(posTags[i].equals("NN")||posTags[i].equals("NE")){
				verbFeatures.addCoOccurence(lemmata[i]);
				cooccCount++;
			}
		}
		
		//imperative forms can't be count, because there were no morphological annotations in the flat sentences 
		
		//"mit" + intent/careful
		for (int i = 0; i < lemmata.length; i++) {
			if(lemmata[i].equals("mit")){
				int nextPos = i+1;
				int maxDist = 3; //Maximum distance between preposition and Noun
				while(lemmata.length>nextPos){
					maxDist--;
					if(posTags[nextPos].equals("NN")){
						if(lemmata[nextPos].equals("Absicht")){
							verbFeatures.increaseWithIntentCount();
							break;
						}
						if(lemmata[nextPos].equals("Sorgfalt")){
							verbFeatures.increaseCarefullyCount();
							break;
						}
					}
					nextPos++;
					if(maxDist<=0){
						break;
					}
				}				
			}			
		}
		
		//"in/seit/von" TimeUnitCount
		for (int i = 0; i < lemmata.length; i++) {
			
			if(sd.plemmas[i].equals("in")||sd.plemmas[i].equals("seit")||sd.plemmas[i].equals("vor")){
				
				int nextPos = i+1;
				int maxDist = 3; //Maximum distance between preposition and Noun
				
				while(lemmata.length>nextPos){
					maxDist--;
					if(posTags[nextPos].equals("NN")){
						if(slf.belongsToCategory(lemmata[nextPos])!=null && slf.belongsToCategory(lemmata[nextPos]).equals("timeUnit")){
							verbFeatures.increaseSincePointOfTimeCount();							
							break;
						}
					}
					nextPos++;
					if(maxDist<=0){
						break;
					}
				}				
			}
		}
		
		//other aspectual categories
		for (int i = 0; i < lemmata.length; i++) {
			String belongsToCategory = slf.belongsToCategory(lemmata[i]);
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
					case "persuade": verbFeatures.increasePersuadeCount();
					break;
					case "stoppedAction": verbFeatures.increaseStoppedActionCount();
					break;
					default:
					break;
				}
			}
		}					
	}	
		
	public void info(){
		System.out.println("Co occurences found: " + cooccCount);
	}

}
