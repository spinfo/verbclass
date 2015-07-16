package de.uni_koeln.spinfo.verbclass.verbfeatures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_koeln.spinfo.verbclass.io.ArgumentClasses;

public class VerbFeatureIntersection {
	
	public List<String> buildIntersection(Map<String, VerbFeatures> verbsWithFeatures, ArgumentClasses acs, boolean aggregateClasses, boolean typeBased){
		
		List<String> toReturn = new ArrayList<String>();
		
		Set<String> keySet = verbsWithFeatures.keySet();
		
		for (String verb : keySet) {
			VerbFeatures verbFeatures = verbsWithFeatures.get(verb);
			Map<String, Integer> subjectsOfVerb = verbFeatures.getSubjects();
			
			
			System.out.println(verb);
			List<Integer> counts = new LinkedList<Integer>();
			
			Map<Integer, Set<String>> subjectClasses = acs.getSubjectClasses();
			Set<Integer> subjs = subjectClasses.keySet();
			for (Integer sc : subjs) {
				Set<String> subjSet = subjectClasses.get(sc);
				int aggregatedCount = 0;
				for (String subj : subjSet) {
					Integer count = subjectsOfVerb.get(subj);
					if(count==null){
						count = 0;
					}
					if(aggregateClasses){
						if(typeBased){
							if(count>0) 
								aggregatedCount++;
						}
						else{
							aggregatedCount += count;
						}
					}
					else{
						if(typeBased){
							if(count>0){ 
								counts.add(1);
							}
							else{
								counts.add(0);
							}
						}
						else{
							counts.add(count);
						}
					}
				}
				if(aggregateClasses){
					counts.add(aggregatedCount);
				}
				System.out.println("Subjects size class " + sc + ": " + subjSet.size());
			}
			
			Map<String, Integer> dosOfVerb = verbFeatures.getDirectObjects();
			Map<Integer, Set<String>> directObjectClasses = acs.getAccObjectClasses();
			Set<Integer> dos = directObjectClasses.keySet();
			for (Integer doClass : dos) {
				Set<String> dOSet = directObjectClasses.get(doClass);
				int aggregatedCount = 0;
				for (String doc : dOSet) {
					Integer count = dosOfVerb.get(doc);
					if(count==null){
						count = 0;
					}
					if(aggregateClasses){
						if(typeBased){
							if(count>0) 
								aggregatedCount++;
						}
						else{
							aggregatedCount += count;
						}
					}
					else{
						if(typeBased){
							if(count>0){ 
								counts.add(1);
							}
							else{
								counts.add(0);
							}
						}
						else{
							counts.add(count);
						}
					}
				}
				if(aggregateClasses){
					counts.add(aggregatedCount);
				}
				System.out.println("DO size class " + doClass + ": " + dOSet.size());
			}
			
			Map<String, Integer> posOfVerb = verbFeatures.getPrepositionalObjects();
			Map<Integer, Set<String>> prepObjectClasses = acs.getPrepObjectClasses();
			Set<Integer> pos = prepObjectClasses.keySet();
			
			for (Integer poClass : pos) {
				Set<String> pOSet = prepObjectClasses.get(poClass);
				int aggregatedCount = 0;
				for (String poc : pOSet) {
					Integer count = posOfVerb.get(poc);
					if(count==null){
						count = 0;
					}
					if(aggregateClasses){
						if(typeBased){
							if(count>0) 
								aggregatedCount++;
						}
						else{
							aggregatedCount += count;
						}
					}
					else{
						if(typeBased){
							if(count>0){ 
								counts.add(1);
							}
							else{
								counts.add(0);
							}
						}
						else{
							counts.add(count);
						}
					}
				}
				if(aggregateClasses){
					counts.add(aggregatedCount);
				}
				System.out.println("PO size class " + poClass + ": " + pOSet.size());
			}
			System.out.println(counts.size());
			System.out.println(counts);
			
			StringBuffer verbFeatureString = new StringBuffer();
			verbFeatureString.append(verbFeatures);
			for (Integer integer : counts) {
				verbFeatureString.append(integer + "\t");
			}
			System.out.println(verbFeatureString);
			toReturn.add(verbFeatureString.toString());
		}
		return toReturn;
	}

}
