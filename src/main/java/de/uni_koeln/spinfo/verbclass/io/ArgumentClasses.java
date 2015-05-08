package de.uni_koeln.spinfo.verbclass.io;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ArgumentClasses {
	
	private Map<Integer, Set<String>> subjectClasses;
	
	private Map<Integer, Set<String>> accObjectClasses;
	
	private Map<Integer, Set<String>> prepObjectClasses;
	
	public ArgumentClasses(){
		this.subjectClasses = new TreeMap<Integer, Set<String>>();
		this.accObjectClasses = new TreeMap<Integer, Set<String>>();
		this.prepObjectClasses = new TreeMap<Integer, Set<String>>();
	}
	
	public void addArgument(String arg, int cls, String type){
		if(arg.startsWith("SB")){
			addSubj(cls, type);
		}
		if(arg.startsWith("OA")){
			addAccObj(cls, type);
		}
		if(arg.startsWith("PO")){
			addPObj(cls, type);
		}
	}
	
	public void addSubj(int cls, String type){
		Set<String> set = subjectClasses.get(cls);
		if(set==null){
			set = new TreeSet<String>();
		}
		set.add(type);
		subjectClasses.put(cls, set);		
	}
	
	public void addAccObj(int cls, String type){
		Set<String> set = accObjectClasses.get(cls);
		if(set==null){
			set = new TreeSet<String>();
		}
		set.add(type);
		accObjectClasses.put(cls, set);		
	}
	
	public void addPObj(int cls, String type){
		Set<String> set = prepObjectClasses.get(cls);
		if(set==null){
			set = new TreeSet<String>();
		}
		set.add(type);
		prepObjectClasses.put(cls, set);		
	}

	public Map<Integer, Set<String>> getSubjectClasses() {
		return subjectClasses;
	}

	public Map<Integer, Set<String>> getAccObjectClasses() {
		return accObjectClasses;
	}

	public Map<Integer, Set<String>> getPrepObjectClasses() {
		return prepObjectClasses;
	}
	
	

}
