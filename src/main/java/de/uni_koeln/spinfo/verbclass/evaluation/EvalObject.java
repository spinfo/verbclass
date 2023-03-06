package de.uni_koeln.spinfo.verbclass.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EvalObject {
	
	private List<String> positionLabels;
	private List<Integer> goldList;
	private List<Integer> evalList;
		
	public EvalObject(Map<String,Integer> goldStandard, Map<String,Integer> toEvaluate) {
		Set<String> keySet = toEvaluate.keySet();
		positionLabels = new ArrayList<String>();
		goldList = new ArrayList<Integer>();
		evalList = new ArrayList<Integer>();
		for (String key : keySet) {
			Integer gold = goldStandard.get(key);
			if(gold==null){
				throw new RuntimeException("Label not part of the gold standard: " + key);
			}
			Integer eval = toEvaluate.get(key);
			positionLabels.add(key);
			goldList.add(gold);
			evalList.add(eval);			
		}
	}
	
	public List<String> getPositionLabels() {
		return positionLabels;
	}
	
	public int[] getGoldArray() {
		return goldList.stream().mapToInt(i->i).toArray();
	}
	
	public int[] getEvalArray() {
		return evalList.stream().mapToInt(i->i).toArray();
	}
	
	
	
	

}
