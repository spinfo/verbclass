import is2.data.SentenceData09;
import is2.io.CONLLReader09;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.mate.ActantAggregator;


public class ActantAggregatorTests {

	@Test
	public void testToCSVFile() throws IOException {
		
		ActantAggregator acag = new ActantAggregator();
		
		File folder = new File("output/parsedSentencesWithVerbs");
		File[] listFiles = folder.listFiles();
		int sum = 0;
		for (File file : listFiles) {
			CONLLReader09 reader = new CONLLReader09(true);
			reader.startReading(file.getAbsolutePath());
			SentenceData09 nextCoNLL09 = reader.getNextCoNLL09();
			
			String verb = file.getName().substring(0, file.getName().length()-4);
			
			while(nextCoNLL09!=null){
				acag.addActants(verb, nextCoNLL09);				
				nextCoNLL09 = reader.getNextCoNLL09();
				sum++;
			}
		}
		
		System.out.println("Overall sentences " + sum);
		//acag.exportToCSV(new FileOutputStream(new File("test.csv")));
		
		Map<String, Map<String, List<String>>> nActantsOfEachActantClassFromVerbs = acag.getNActantsOfEachActantClassFromVerbs(15);
		Set<String> keySet = nActantsOfEachActantClassFromVerbs.keySet();
		Map<String, Set<String>> actantGroups = new TreeMap<String, Set<String>>(); 
		for (String verb : keySet) {
			Map<String, List<String>> map = nActantsOfEachActantClassFromVerbs.get(verb);
			Set<String> keySet2 = map.keySet();
			
			
			for (String actantClass : keySet2) {
				Set<String> words = new TreeSet<String>();
				List<String> list = map.get(actantClass);
				for (String actant : list) {
					words.add(actant);
				}
				Set<String> set = actantGroups.get(actantClass);
				if(set==null){
					set = new TreeSet<String>();
				}
				set.addAll(words);
				actantGroups.put(actantClass, set);
			}		
			
		}
		Set<String> keySet2 = actantGroups.keySet();
		for (String string : keySet2) {
			
			Set<String> set = actantGroups.get(string);
			System.out.println(string + ": " +set.size());
			for (String string2 : set) {
				//System.out.println(string2);
			}
			System.out.println("****************************************************");
		}
		
		
		//acag.printActantMap();
	}
	

}
