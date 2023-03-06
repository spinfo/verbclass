package de.uni_koeln.spinfo.verbclass.verbfeatures;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to collect various features for a special verb. 
 * @author jhermes
 *
 */
public class VerbFeatures implements Serializable{
	
	private String lemma;
		//The Lemma of this verb	
	
	private Map<String, Integer> unlabeledCoOccurences; 
		//Unspecified co occurences  
	
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
	
	public String toString(){
		StringBuffer buff= new StringBuffer();
		
		buff.append(lemma+"\t");
		buff.append(imperativeCount+"\t");
		buff.append(stoppedActionCount+"\t");
		buff.append(persuadeCount+"\t");
		buff.append(sincePointOfTimeCount+"\t");
		buff.append(durationCount+"\t");
		buff.append(timeUnitCount+"\t");
		buff.append(withIntentCount+"\t");
		buff.append(carefullyCount+"\t");
		buff.append(almostlyCount+"\t");
		
		//buff.append(subjects);
		return buff.toString();
	}

	/**
	 * Initializes a new Verbfeature object with specified lemma
	 * @param lemma
	 */
	public VerbFeatures(String lemma) {
		this.lemma = lemma;
		this.subjects = new HashMap<String, Integer>();
		this.dativeObjects = new HashMap<String, Integer>();
		this.directObjects = new HashMap<String, Integer>();
		this.prepositionalObjects = new HashMap<String, Integer>();
		this.unlabeledCoOccurences = new HashMap<String, Integer>();
	}

	/** Returns the lemma of the verb
	 * @return lemma of the verb
	 */
	public String getLemma() {
		return lemma;
	}
	
	/** Adds the specified co occurence lemma to the subjects map 
	 * @param lemma co occurence lemma 
	 */
	public void addCoOccurence(String lemma){
		addToMap(unlabeledCoOccurences, lemma);
	}
	
	
	/** Returns the unlabeled co occurences map
	 * @return unlabeled co occurences map
	 */
	public Map<String, Integer> getUnlabeledCoOccurences() {
		return unlabeledCoOccurences;
	}

	/** Adds the specified subject lemma to the subjects map 
	 * @param lemma subject 
	 */
	public void addSubject(String lemma){
		addToMap(subjects, lemma);
	}
	
	/** Returns the subjects map
	 * @return subjects with counts
	 */
	public Map<String, Integer> getSubjects() {
		return subjects;
	}
	
	/** Adds the specified direct object lemma to the direct objects map
	 * @param lemma direct object
	 */
	public void addDirectObject(String lemma){
		addToMap(directObjects, lemma);
	}

	/** Returns the direct objects map
	 * @return direct objects with counts
	 */
	public Map<String, Integer> getDirectObjects() {
		return directObjects;
	}

	public void addDativeObject(String lemma){
		addToMap(dativeObjects, lemma);
	}
	
	public Map<String, Integer> getDativeObjects() {
		return dativeObjects;
	}
	
	public void addPrepositionalObject(String lemma){
		addToMap(prepositionalObjects, lemma);
	}

	public Map<String, Integer> getPrepositionalObjects() {
		return prepositionalObjects;
	}

	public int getImperativeCount() {
		return imperativeCount;
	}

	public void increaseImperativeCount() {
		this.imperativeCount++;
	}

	public int getStoppedActionCount() {
		return stoppedActionCount;
	}

	public void increaseStoppedActionCount() {
		this.stoppedActionCount++;
	}

	public int getPersuadeCount() {
		return persuadeCount;
	}

	public void increasePersuadeCount() {
		this.persuadeCount++;
	}

	public int getSincePointOfTimeCount() {
		return sincePointOfTimeCount;
	}

	public void increaseSincePointOfTimeCount() {
		this.sincePointOfTimeCount++;
	}

	public int getDurationCount() {
		return durationCount;
	}

	public void increaseDurationCount() {
		this.durationCount++;
	}

	public int getTimeUnitCount() {
		return timeUnitCount;
	}

	public void increaseTimeUnitCount() {
		this.timeUnitCount++;
	}

	public int getWithIntentCount() {
		return withIntentCount;
	}

	public void increaseWithIntentCount() {
		this.withIntentCount++;
	}

	public int getCarefullyCount() {
		return carefullyCount;
	}

	public void increaseCarefullyCount() {
		this.carefullyCount++;
	}

	public int getAlmostlyCount() {
		return almostlyCount;
	}

	public void increaseAlmostlyCount() {
		this.almostlyCount++;
	}
	
	public void addToMap(Map<String, Integer> map, String lemma){
		Integer count = map.get(lemma);
		if(count==null){
			count = 0;
		}
		count += 1;
		map.put(lemma, count);
	}
	
	public void removeFromMap(Map<String, Integer> map, String lemma){
		map.remove(lemma);
	}
	
	public void removeCoOccurence(String lemma){
		removeFromMap(this.unlabeledCoOccurences, lemma);
	}
	
	public void removeSubject(String lemma){
		removeFromMap(this.subjects, lemma);
	}
	
	public void removePrepositionalObject(String lemma){
		removeFromMap(this.prepositionalObjects, lemma);
	}
	
	public void removeDirectObject(String lemma){
		removeFromMap(this.directObjects, lemma);
	}
	
	public void removeDativeObject(String lemma){
		removeFromMap(this.dativeObjects, lemma);
	}

}
