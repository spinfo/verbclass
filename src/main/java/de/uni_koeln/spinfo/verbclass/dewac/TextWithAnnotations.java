package de.uni_koeln.spinfo.verbclass.dewac;
import java.util.List;


public class TextWithAnnotations {
	
	private String text;
	private List<TeslaAnnotation> sentences;
	private List<TeslaAnnotation> words;
	private List<TeslaAnnotation> posTags;
	private List<TeslaAnnotation> lemmata;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TeslaAnnotation> getSentences() {
		return sentences;
	}
	public void setSentences(List<TeslaAnnotation> sentences) {
		this.sentences = sentences;
	}
	public List<TeslaAnnotation> getWords() {
		return words;
	}
	public void setWords(List<TeslaAnnotation> words) {
		this.words = words;
	}
	public List<TeslaAnnotation> getPosTags() {
		return posTags;
	}
	public void setPosTags(List<TeslaAnnotation> posTags) {
		this.posTags = posTags;
	}
	public List<TeslaAnnotation> getLemmata() {
		return lemmata;
	}
	public void setLemmata(List<TeslaAnnotation> lemmata) {
		this.lemmata = lemmata;
	}
	
	

}
