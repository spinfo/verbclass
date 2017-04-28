package de.uni_koeln.spinfo.verbclass.goldstandard;

import java.util.HashMap;
import java.util.Map;

public class Rater<T> {
	
	private String id;
	
	private Map<String, T> rates;
	
	public Rater(String id){
		this.id=id;
		rates = new HashMap<String, T>();
	}
	
	public T addRate(String attribute, T value){
		return rates.put(attribute, value);
	}
	
	public T getRate(String attribute){
		return rates.get(attribute);
	}

	public String getId() {
		return id;
	}

	public Map<String, T> getRates() {
		return rates;
	}
	
	
}
