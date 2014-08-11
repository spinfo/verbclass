package de.uni_koeln.spinfo.verbclass.dewac;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class DeWaCReader {
	
	private Set<String> tagSet;
	private File[] listFiles;
	
	public DeWaCReader(String filename){
		File textFiles = new File(filename);
		tagSet = new TreeSet<String>();
		if(textFiles.isDirectory()){
			listFiles = textFiles.listFiles();
		}
		else{
			listFiles = new File[1];
			listFiles[0] = textFiles;
		}
	}
	
	public void process() throws IOException{
		
		for (int i = 0; i < listFiles.length; i++) {
			File actualFile = listFiles[i];
			processFile(actualFile);
		}
	}
	
	public void processFile(File file) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(file));
		String nextLine = in.readLine();
		
		textbuffer = new StringBuffer();
		textAnns = new ArrayList<TeslaAnnotation>();
		
		sentenceAnns = new ArrayList<TeslaAnnotation>();
		wordAnns  = new ArrayList<TeslaAnnotation>();
		posTags  = new ArrayList<TeslaAnnotation>();
		lemmata  = new ArrayList<TeslaAnnotation>();
		
		while(nextLine!=null){
			System.out.println(nextLine);
			processLine(nextLine);
			nextLine = in.readLine();
		}
		
		
	}
	
	private StringBuffer textbuffer;
	private int startOfText;
	private int startOfSentence;
	
	
	private List<TeslaAnnotation> textAnns;
	private List<TeslaAnnotation> sentenceAnns;	
	private List<TeslaAnnotation> wordAnns; 	
	private List<TeslaAnnotation> posTags;
	private List<TeslaAnnotation> lemmata;
	
	public void processLine(String line){
		if(line.trim().length()==0){
			return;
		}
		if(line.trim().startsWith("<text")){
			startOfText = textbuffer.length();
		}
		else{
			
			if(line.trim().startsWith("</text")){
				textAnns.add(new TeslaAnnotation("Text", startOfText, textbuffer.length()));	
				textbuffer.append("\n");
				textbuffer.append("\n");
				
			}
			else{
				if(line.trim().startsWith("<s")){
					startOfSentence = textbuffer.length();
				}
				else{
					if(line.trim().startsWith("</s")){
						//sentenceAnns.add(new TeslaAnnotation("Sentence", startOfSentence, textbuffer.length()));
						textbuffer.append("\n");
					}
					else{
						processWord(line);
					}
				}
			}
		}		
	}
	private void processWord(String line){
		String[] splits = line.split("\\s");
		//System.out.println(splits.length);
		//System.out.println(splits[1]);
		int wordStart = textbuffer.length();
		for (int i = 0; i < splits.length; i++) {
			//add text to buffer
			//textbuffer.append(splits[0]);
			//textbuffer.append(" ");	
			
			//check type
			if(splits[1].startsWith("$")){
				//wordAnns.add(new TeslaAnnotation("Punctuation", wordStart, textbuffer.length()));
			}
			else{
				//wordAnns.add(new TeslaAnnotation("Word", wordStart, textbuffer.length()));
			}
			tagSet.add(splits[1]);
			//posTags.add(new Annotation(splits[1], wordStart, textbuffer.length()));
			//lemmata.add(new Annotation(splits[2], wordStart, textbuffer.length()));		
		}
	}
	
	private void toFile(){
		
	}

	public Set<String> getTagSet() {
		return tagSet;
	}
}

