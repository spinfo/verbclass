package de.uni_koeln.spinfo.verbclass.converters;

import java.util.List;

public class SentenceInfoObject {
	
	private List<WordInfoObject> words;
	private int start;
	private int end;
	
	public SentenceInfoObject(List<WordInfoObject> words, int start, int end) {
		super();
		this.words = words;
		this.start = start;
		this.end = end;
	}

	public List<WordInfoObject> getWords() {
		return words;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}	
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(start + " " + end + "\n" + words);
		return buff.toString();
	}

}
