package de.uni_koeln.spinfo.verbclass.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ClassifiedNomsReader {
	
	public ArgumentClasses getNomsWithClasses(File inputFolder) throws IOException{
		ArgumentClasses toReturn = new ArgumentClasses();
		
		File[] listFiles = inputFolder.listFiles();
		for (File file : listFiles) {
			String arg = file.getName().substring(0, 2);
			System.out.println(arg);
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			do{
				String[] split = line.split(";");
				String type = split[0];
				int cls = Integer.parseInt(split[split.length-1]);
				toReturn.addArgument(arg, cls, type);
				line = in.readLine();
			}
			while(line!=null);
				
		}
		
		return toReturn;
		
	}

}
