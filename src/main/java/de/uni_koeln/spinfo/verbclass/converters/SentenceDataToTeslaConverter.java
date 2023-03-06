package de.uni_koeln.spinfo.verbclass.converters;

import java.util.ArrayList;
import java.util.List;

public class SentenceDataToTeslaConverter {
	
	private List<SentenceInfoObject> sentences; 
	
	public List<SentenceInfoObject> getSentences() {
		return sentences;
	}

	public SentenceDataToTeslaConverter(){
		sentences = new ArrayList<SentenceInfoObject>();
	}
	
	public void addSentence(List<String> infoStrings, int upset){
		List<WordInfoObject> wios = new ArrayList<WordInfoObject>();
		
		int sentenceStart = upset;
		int actualPosition = upset;
		
		for (String info : infoStrings) {
			
			String[] parts = info.split("\\s");
			String token = parts[1];
			String lemma = parts[3];
			String posTag = parts[5];
			String role = parts[11];
			int head = Integer.parseInt(parts[9].trim());	
			int wordStart = actualPosition + info.indexOf(token);
			int wordEnd = wordStart+token.length();
			WordInfoObject wio = new WordInfoObject(token,wordStart, wordEnd);	
			wio.setHeadID(head);
			wio.setLemma(lemma);
			wio.setPosTag(posTag);
			wio.setRole(role);
			wios.add(wio);
			actualPosition += info.length()+1 ;
			//sentenceStart = sentenceStart + info.length() +1;
			
		}
		SentenceInfoObject sio = new SentenceInfoObject(wios, sentenceStart , actualPosition);
		sentences.add(sio);
	}

}
