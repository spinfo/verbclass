package de.uni_koeln.spinfo.verbclass.calc;

import java.util.List;

public class RandIndex {
	
	public static double getRandIndex(int[] gold, int[] cluster){
		if(gold.length!=cluster.length){
			System.out.println("Count of objects should be equal");
			return 0.0;
		}
		int good = 0;
		int bad = 0;
		for (int i = 0; i < cluster.length-1; i++) {
			for (int j=1; j<cluster.length; j++){
				//if((gold[i]==gold[j]) == (cluster[i]==cluster[j])){
				if((gold[i]==gold[j]) == (cluster[i]==cluster[j])){
					//if((gold[i]==gold[j]) && (cluster[i]==cluster[j]))
					good++;
					
				}
				else{
					//if(gold[i]==gold[j])
					bad++;
				}
			}
		}
		//System.out.println("Good " + good);
		//System.out.println("Bad " + bad);
		
		return good/(double)(bad+good);
	}

}
