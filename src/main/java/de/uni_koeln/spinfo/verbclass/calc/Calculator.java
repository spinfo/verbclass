package de.uni_koeln.spinfo.verbclass.calc;

public class Calculator {
	
	public static double calculatePresssQ(int sampleSize, int correct, int groups){
		
		double numerator = Math.pow(sampleSize - (correct*groups),2);
		double denominator = sampleSize * (groups-1);
		double result = numerator/denominator;
		
		return result;
		
	}

}
