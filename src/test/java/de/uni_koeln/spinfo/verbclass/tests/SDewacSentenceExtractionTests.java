package de.uni_koeln.spinfo.verbclass.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.dewac.DewacSplitter;
import de.uni_koeln.spinfo.verbclass.dewac.StringsOfInterest;

public class SDewacSentenceExtractionTests {
	
	@Test
	public void countOccurencesOfString() throws IOException{
		File inputFolder = new File("output/anglizisms");
		File[] listFiles = inputFolder.listFiles();
		for (File file : listFiles) {
			String name = file.getName();
			name = name.substring(0, name.length()-4);
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			int counter = 0;
			while(line!=null){
				if(line.contains(name)){
					counter++;
				}
				line = in.readLine();
			}
			System.out.println(name + ": " + counter);
		}
	}

	//@Test
	public void testCreateFilePerVerb() throws IOException{
		StringsOfInterest soi = new StringsOfInterest();
		soi.setStringsOfInterest("data/anglizisms/oneWordAnglizisms2.txt");		
		DewacSplitter ds = new DewacSplitter("output/anglizisms");
		ds.createFilePerVerb(new File("C://Korpora//DeWaC//sdewac-v3.tagged"), soi, 3000);
	}

	//@Test
	public void deleteEmptyFiles() throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(new File("output/anglizisms/notFound.txt")));
		File dir = new File("output/anglizisms");
		File[] files = dir.listFiles();
		for (File file : files) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if(line == null){
				line = br.readLine();
				if(line == null){
					out.write(file.getName().substring(0, file.getName().indexOf("."))+"\n");
					br.close();
					boolean b = file.delete();
					System.out.println(b);
				}
				
			}
			br.close();
		}
		out.flush();
		out.close();
	}
	
	//@Test
	public void changeFormat() throws IOException{
		String regex = "[0-9]+/[0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m;
		File dir = new File("output/anglizisms");
		File[] files = dir.listFiles();
		for (File file : files) {
			if(file.getName().startsWith("notFound")){
				continue;
			}
			BufferedReader in = new BufferedReader(new FileReader(file));
			PrintWriter out = new PrintWriter(new FileWriter(new File("output/readable/"+file.getName())));
			String line = in.readLine();
			int n = 0;
			while(line!= null){
				if(line.startsWith("<s>")){
					out.write("\n");
					line = in.readLine();
					n++;
					continue;
				}
				if(line.startsWith("</s>")){
					out.write("\n");
					line = in.readLine();
					n++;
					continue;
				}
				if(line.isEmpty()){
					line = in.readLine();
					n++;
					continue;
				}
				String[] split = line.split("\\s");
				if(!(split.length == 3)){
					if(split.length > 3){
						if(split[split.length-2].equals("CARD")){
							for (int i = 0; i < split.length-2; i++) {
								out.write(split[i]+ " ");
							}
							out.write(" ["+split[split.length-2]+"] ["+split[split.length-1]+"] ");
							line = in.readLine();
							n++;
							continue;
						}
					}
					else{
						System.out.println(file.getName());
						System.out.println(n);
						System.out.println(line);
						line = in.readLine();
						n++;
						continue;
					}
				}
				out.write(split[0]+" ["+split[1]+"] ["+split[2]+"] ");
				line = in.readLine();
				n++;
			}
			in.close();
			out.flush();
			out.close();
		}
	}


	//@Test
	public void createReadableSentences() throws IOException{
		 String regex = "";
		 Pattern p = Pattern.compile(regex);
		 Matcher m;
		 File dir = new File("output/anglizisms");
		 File[] files = dir.listFiles();
		 for (File file : files) {
			 BufferedReader in = new BufferedReader(new FileReader(file));
			 PrintWriter out = new PrintWriter(new FileWriter(new File("output/anglizisms/"+file.getName().substring(0, file.getName().indexOf("."))+"_readable.txt")));
			 String line = in.readLine();
			 while(line!= null){
				 if(line.startsWith("<s>")){
					 out.write("\n"+"\n");
					 line = in.readLine();
					 continue;
				 }
				 if(line.startsWith("</s>")){
					 line=in.readLine();
					 continue;
				 }
				 String [] split = line.split("\t");
				 if(split[0].length() == 1){
					 m = p.matcher(split[0]);
					 if(m.find()){
						 out.write(split[0]);
					 }
					 else{
						 out.write(" "+split[0]);
					 }
				 }
				 else{
					 out.write(" "+split[0]);
				 }
				 line = in.readLine();
			 }
			 in.close();
			 out.flush();
			 out.close();
		}
	}
}