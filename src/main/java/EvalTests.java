import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.evaluation.ClusterEvaluator;
import de.uni_koeln.spinfo.verbclass.evaluation.ResultImporter;

public class EvalTests {

	@Test
	public void test() throws IOException {
		File resultFolder = new File("data/clusterResults");
		File[] files = resultFolder.listFiles();
		
		File vendlerGold = new File("data/Vendler.csv");
		File schumacherGold = new File("data/Schumacher.csv");
		
		System.out.println(ResultImporter.importClusterResults(vendlerGold));
		System.out.println(ResultImporter.importClusterResults(schumacherGold));
		
		ClusterEvaluator vendlerEval = new ClusterEvaluator();
		vendlerEval.setGoldStandard(ResultImporter.importClusterResults(vendlerGold));
		
		ClusterEvaluator schumacherEval = new ClusterEvaluator();
		schumacherEval.setGoldStandard(ResultImporter.importClusterResults(schumacherGold));
		
		System.out.println();
		for (File file : files) {
			//System.out.println(file.getName());
			if(!file.getName().contains("PREVIEW")){
				Map<String, Integer> importClusterResults = ResultImporter.importClusterResults(file);
				System.out.println(file.getName());
				double vE= vendlerEval.evaluateWithRandIndex(importClusterResults);
				double sE = schumacherEval.evaluateWithRandIndex(importClusterResults);
				System.out.println("RAND Vendler: " + vE);
				System.out.println("RAND Schumacher" + sE);
				System.out.println();
			}			
		}
	}
	
	@Test
	public void testRandom() throws IOException{
		File vendlerGold = new File("data/Vendler.csv");
		File schumacherGold = new File("data/Schumacher.csv");
		ClusterEvaluator vendlerEval = new ClusterEvaluator();
		Map<String, Integer> gold = ResultImporter.importClusterResults(vendlerGold);
		vendlerEval.setGoldStandard(gold);
		
		double evaluateWithRandIndex = vendlerEval.evaluateWithRandIndex(getRandomClusters(gold, 5));
		System.out.println(evaluateWithRandIndex);
		
		evaluateWithRandIndex = vendlerEval.evaluateWithRandIndex(gold);
		System.out.println(evaluateWithRandIndex);
		
		ClusterEvaluator schumacherEval = new ClusterEvaluator();
		
		gold = ResultImporter.importClusterResults(schumacherGold);
		schumacherEval.setGoldStandard(gold);
		evaluateWithRandIndex = schumacherEval.evaluateWithRandIndex(getRandomClusters(gold, 7));
		System.out.println(evaluateWithRandIndex);
		
		evaluateWithRandIndex = schumacherEval.evaluateWithRandIndex(gold);
		System.out.println(evaluateWithRandIndex);
		
	}
	
	private static Map<String, Integer> getRandomClusters(Map<String, Integer> goldClasses, int numberOfClasses){
		
		Random random = new Random();
		Set<String> keySet = goldClasses.keySet();
		for (String string : keySet) {
			
		}
		Map<String, Integer> toReturn = new TreeMap<String, Integer>();
		for (String string : keySet) {
			toReturn.put(string, random.nextInt(numberOfClasses-1));
		}
		
		return toReturn;
	}
}
