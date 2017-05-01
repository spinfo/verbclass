package de.uni_koeln.spinfo.verbclass.goldstandard;

import java.util.Collection;
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
	
	public Collection<T> getRates(){
		return rated.values();
	}
	
	public String toString(){
		return rated.toString();
	}

	public String getID() {
		return id;
	}
	

}
