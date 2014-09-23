package de.uni_koeln.spinfo.verbclass.mate;

import java.util.Map;

/**
 * Class to collect various features for a special verb. 
 * @author jhermes
 *
 */
public class VerbFeatures {
	
	private String lemma;
		//The Lemma of this verb
	
	private Map<String, Integer> subjects; 
		//Heads of subject phrases with counts 
	
	private Map<String, Integer> directObjects; 
		//Heads of accusative object phrases with counts
	
	private Map<String, Integer> dativeObjects; 
		//Heads of dative object phrases with counts 
	
	private Map<String, Integer> prepositionalObjects; 
		//Nouns of prepositional object phrases with counts
	
	private int imperativeCount; 
		//Count of verb in imperative forms
	
	private int stoppedActionCount; 
	 	//Count of verb occurences (infinitive form) with dependent "aufhören" or "stoppen"
	
	private int persuadeCount; 
		//Count of verb (infinitive form) occurences with "überzeugen"
	
	private int sincePointOfTimeCount; 
		//Count of verb occurences with dependent "seit" and a time unit (e.g. "Sekunde|Minute")
	
	private int durationCount; 
		//Count of verb occurences with dependent [time unit]+"lang"
	
	private int timeUnitCount; 
		//Count of verb occurences with a dependent time unit;
	
	private int withIntentCount; 
		//Count of the verb occurences form with dependent preposition "mit" 
		// and noun "Vorsatz|Absicht" or adverb "vorsätzlich|absichtlich"
	
	private int carefullyCount; 
		//Count of verb occurences with dependent preposition "mit" 
		// and noun "Sorfalt" or adverb "sorgfältig"
	
	private int almostlyCount;
		//Count of verb occurences with dependent adverb "fast|beinahe"

}
