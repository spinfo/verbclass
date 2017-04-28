package de.uni_koeln.spinfo.verbclass.goldstandard;

import java.util.List;

public class AmbigiousRate<V> {
	
	private List<V> rates;
	
	public AmbigiousRate(){
		
	}
	
	public void addRate(V rate){
		rates.add(rate);
	}
	
	@SuppressWarnings("unchecked")
	public void setRates(V rates){		
		String ratesS = (String) rates;
		String[] split = ratesS.split("_");
		for (String string : split) {
			V toAdd = (V) string;
			((List<V>) rates).add(toAdd);
		}
	}
	
	public List<V> getRates(){
		return rates;
	}

}
