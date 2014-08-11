package de.uni_koeln.spinfo.verbclass.dewac;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class DewacSplitter {
	
	private File[] inputFiles; 
	private File destinationDir;
	
	
	
	public DewacSplitter(String destination){
		this.destinationDir = new File(destination);
		if(!destinationDir.exists())
			destinationDir.mkdirs();
	}
	
//	public void splitAllFiles(int textsPerFile, int nFiles){
//		for(int i=0; i<inputFiles.length; i++){
//			nFiles -= splitFile(inputFiles[i], textsPerFile, nFiles); 
//		}
//	}
	
	public void createFilePerVerb(File inputFile, StringsOfInterest soi, int maxSentencesPerFile) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		String nextLine = in.readLine();
		
		List<PrintWriter> outs = new ArrayList<PrintWriter>();
		for (String string : soi.getStringsOfInterest()) {
			outs.add(new PrintWriter(new FileWriter(new File(destinationDir + "//"+ string + ".txt"))));
		}
		
		StringBuffer toWrite = new StringBuffer();
		
		int ofInterest = -1;
	
		List<Integer> found = new ArrayList<Integer>(outs.size()); 
		for (int i=0; i<outs.size(); i++) {
			found.add(0);
		}
		int notOfInterest = 0;
		while(nextLine!= null){
			if(nextLine.startsWith("<")){
				if(nextLine.trim().equals("<s>")){					
					toWrite.append(nextLine + '\n');					
				}
				else{
					if(nextLine.trim().equals("</s>")){
						toWrite.append(nextLine + "\n\n");
						if(ofInterest>=0){
							if(found.get(ofInterest) <= maxSentencesPerFile){
								outs.get(ofInterest).println(toWrite.toString());
								found.set(ofInterest, found.get(ofInterest)+1);
							}
						}
						else{
							notOfInterest++;
						}
						
						toWrite = new StringBuffer();
						ofInterest = -1;											
					}
				}				
			}
			else{
				String[] splits = nextLine.split("\\s");
				String lemma = splits[2].trim();
				if(soi.isOfInterest(lemma)){
					ofInterest = soi.getId(lemma);
				}
				toWrite.append(nextLine + '\n');
			}
			
			if(notOfInterest>10000000){
				break;
			}
			//System.out.println(nextLine);
			nextLine = in.readLine();		
			
		}	
		for (PrintWriter pw : outs) {
			pw.close();
		}
		
		System.out.println("Sentences of interest: " + found);
		System.out.println("Verbs: " + soi.getStringsOfInterest()) ;
		System.out.println("Sentences not of interest: " + notOfInterest);
		System.out.println();
		soi.printMap();
	}
	
	public void sentencesOfInterestToFile(File inputFile, StringsOfInterest soi, int maxSentences) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		String nextLine = in.readLine();
		StringBuffer toWrite = new StringBuffer();
		PrintWriter out = new PrintWriter(new FileWriter(new File(destinationDir + "//ofInterest.txt")));
		boolean ofInterest = false;
		int found = 0;
		int notOfInterest = 0;
		while(nextLine!= null){
			if(nextLine.startsWith("<")){
				if(nextLine.trim().equals("<s>")){					
					toWrite.append(nextLine + '\n');					
				}
				else{
					if(nextLine.trim().equals("</s>")){
						toWrite.append(nextLine + "\n\n");
						if(ofInterest){
							out.println(toWrite.toString());
							found++;
						}
						else{
							notOfInterest++;
						}
						
						toWrite = new StringBuffer();
						ofInterest = false;											
					}
				}				
			}
			else{
				String[] splits = nextLine.split("\\s");
				String lemma = splits[2].trim();
				if(soi.isOfInterest(lemma)){
					ofInterest = true;
				}
				toWrite.append(nextLine + '\n');
			}
			//System.out.println(nextLine);
			nextLine = in.readLine();		
			if(found>maxSentences){
				break;
			}
		}	
		System.out.println("Sentences of interest: " + found);
		System.out.println("Sentences not of interest: " + notOfInterest);
		System.out.println();
		soi.printMap();
	}
	
	/**
	 * Splits the specified file into nFiles subfiles containing sentencesPerFile sentences
	 * @param inputFile
	 * @param sentencesPerFile
	 * @param nFiles
	 * @throws IOException
	 */
	public void splitFile(File inputFile, int sentencesPerFile, int nFiles) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		String nextLine = in.readLine();
		
		StringBuffer toWrite = new StringBuffer();
		int sentenceCounter = 0;
		int fileCounter = nFiles;
		PrintWriter out;
		
		while(nextLine!= null){
			if(nextLine.startsWith("<")){
				if(nextLine.trim().equals("<s>")){
					sentenceCounter++;;
					toWrite.append(nextLine + '\n');
					//System.out.println(sentenceCounter);
				}
				else{
					if(nextLine.trim().equals("</s>")){
						toWrite.append(nextLine + "\n\n");
						if(sentenceCounter>=sentencesPerFile){
							//toWrite.append(nextLine + "\n\n");
							System.out.println("File: " + fileCounter);
							System.out.println("sentences: " + sentenceCounter);
							System.out.println("Buffer: " + toWrite.length());
							System.out.println();
							File destFile = new File(destinationDir +"//"+ inputFile.getName() + "_" + fileCounter++);
							out = new PrintWriter(new FileWriter(destFile));
							out.println(toWrite.toString());
							out.close();
							toWrite = new StringBuffer();
							sentenceCounter = 0;
							if(fileCounter>(nFiles*2)){
								break;
							}
						}						
					}
				}				
			}
			else{
				toWrite.append(nextLine + '\n');
			}
			nextLine = in.readLine();			
		}		
		in.close();
	}
}
