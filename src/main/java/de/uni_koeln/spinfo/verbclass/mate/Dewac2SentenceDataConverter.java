package de.uni_koeln.spinfo.verbclass.mate;
import is2.data.SentenceData09;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to convert sentence data in the (s)dewac format to SentenceData09 objects
 * 
 * @author jhermes
 *
 */
public class Dewac2SentenceDataConverter {
	
	/**
	 * Writes the data from the input stream to a list of SentenceData09 objects
	 * @param input The input stream - should contain valid (s)Dewac data. 
	 * @param nSentences Max sentences to convert
	 * @return A list of SentenceData09 objects
	 * @throws IOException
	 */
	public List<SentenceData09> processStream(InputStream input, int nSentences) throws IOException{
		List<SentenceData09> toReturn = new ArrayList<SentenceData09>();
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
	
		String nextLine = in.readLine();
		if(nSentences<1){
			nSentences = Integer.MAX_VALUE;
		}
		int i =0;
		while(nextLine!=null){
			if(nextLine.startsWith("<s")){
				//System.out.println("Satzanfang");
				SentenceData09 processSentence = processSentence(in);
				if(processSentence!=null){
					toReturn.add(processSentence);
					i++;
				}
			}
			nextLine = in.readLine();
			if(i>nSentences){
				break;
			}
		}
		
		return toReturn;
	}
	
	private SentenceData09 processSentence(BufferedReader in) throws IOException{
		SentenceData09 sd = new SentenceData09();
		List<String> words = new ArrayList<String>();
		words.add("<root>");
		List<String> postags = new ArrayList<String>();
		postags.add("<root>");
		List<String> lemmata = new ArrayList<String>();	
		lemmata.add("<root>");
		String nextLine = in.readLine().trim();
		
		while(nextLine!=null){
			if(nextLine.length()==0){
				nextLine = in.readLine().trim();
				continue;
			}
			if(nextLine.startsWith("</s")){
				//System.out.println("Satzende");
				sd.init(words.toArray(new String[words.size()]));
				sd.plemmas = lemmata.toArray(new String[lemmata.size()]);
				sd.ppos = postags.toArray(new String[postags.size()]);
				return sd;
				
			}
			else{
				String[] splits = nextLine.split("\\s");
				if(splits.length == 3){
					words.add(splits[0]);
					postags.add(splits[1]);
					lemmata.add(splits[2]);
				}
				else{
					if(splits.length>3){
						StringBuffer token = new StringBuffer();
						for (int i = 0; i < splits.length-2; i++) {
							token.append(splits[i]);
						}
						words.add(token.toString());
						postags.add(splits[splits.length-2]);
						lemmata.add(splits[splits.length-1]);
						
					}
					else{
						System.out.println("The specified signal is not valid! " + nextLine);
					}
				}
			}
			nextLine = in.readLine().trim();
		}
		sd.init(words.toArray(new String[words.size()]));
		System.out.println(sd.toString());
		return sd;
	}
	
}
