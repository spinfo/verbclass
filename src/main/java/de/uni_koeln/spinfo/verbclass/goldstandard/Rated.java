package de.uni_koeln.spinfo.verbclass.goldstandard;

import java.util.HashMap;
import java.util.Map;

public class Rated<T> {
		
	private String id;
	
	private Map<Rater<T>, T> rated;
	
	public Rated(String id){
		this.id = id;
		rated = new HashMap<Rater<T>, T>();
	}
	
	public T addRate(Rater<T> rater, T rate){
		return rated.put(rater, rate);
	}
	
	public String getRates(){
		return rated.values().toString();
	}

}
