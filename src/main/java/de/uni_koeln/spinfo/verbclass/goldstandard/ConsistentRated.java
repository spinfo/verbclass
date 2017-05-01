package de.uni_koeln.spinfo.verbclass.goldstandard;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsistentRated<T> {
	
	private Collection<Rated<T>> rated;
	
	private Map<Integer, List<String>> raterAgreement;

	public ConsistentRated(Collection<Rated<T>> rated) {
		super();
		this.rated = rated;
		
		raterAgreement = new HashMap<Integer, List<String>>();
		
		for (Rated<T> rated2 : rated) {
			Collection<T> rates = rated2.getRates();
			int[] agreed = new int[6];
			int rater = 1;
			for (T t : rates) {
				if(t instanceof Integer){
					int i = ((Integer) t);
					agreed[i]+=1;
				}
			}
			Integer maxPos = findMax(agreed);
			Integer maxValue = agreed[maxPos];
			List<String> strings = raterAgreement.get(maxValue);
			if(strings==null){
				strings = new LinkedList<String>();
			}
			strings.add(rated2.getID());
			raterAgreement.put(maxValue, strings);
		}
		System.out.println(raterAgreement);
	}

	public List<String> getTypesWithMinRaterAgreementN(int n){
		List<String> toReturn = new LinkedList<String>();
		Set<Integer> keySet = raterAgreement.keySet();
		for (Integer count : keySet) {
			if(count>=n){
				toReturn.addAll(raterAgreement.get(count));
			}
		}
		return toReturn;
	}
	
	private int findMax(int[] agreed){
		int maxPos=-1;
		int maxValue = 0;
		for(int i=0; i<agreed.length; i++){
			if(agreed[i]>maxValue){
				maxPos = i;
				maxValue = agreed[i];
			}
		}
		return maxPos;
	}
	

}
