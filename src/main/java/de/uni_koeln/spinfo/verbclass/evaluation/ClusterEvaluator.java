package de.uni_koeln.spinfo.verbclass.evaluation;

import java.util.Map;

import de.uni_koeln.spinfo.verbclass.calc.RandIndex;

public class ClusterEvaluator {
	
	
	private Map<String,Integer> goldStandard;
	
	public void setGoldStandard(Map<String,Integer> goldStandard){
		this.goldStandard = goldStandard;
	}
	
	
	public double evaluateWithRandIndex(Map<String,Integer> toEvaluate){
		EvalObject eo = new EvalObject(goldStandard, toEvaluate);
		double randIndex = RandIndex.getRandIndex(eo.getGoldArray(), eo.getEvalArray());
		return randIndex;
	}
	
	
	

}
