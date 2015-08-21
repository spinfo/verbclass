package de.uni_koeln.spinfo.verbclass.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.io.ArgumentClasses;
import de.uni_koeln.spinfo.verbclass.io.ClassifiedNomsReader;

public class ArgReaderTest {

	@Test
	public void test() throws IOException {
		ClassifiedNomsReader cnr = new ClassifiedNomsReader();
		ArgumentClasses nomsWithClasses = cnr.getNomsWithClasses(new File("data/classArg"));
		System.out.println(nomsWithClasses.getAccObjectClasses().size());
		Map<Integer, Set<String>> actantClasses = nomsWithClasses.getAccObjectClasses();
		Set<Integer> keySet = actantClasses.keySet();
		for (Integer integer : keySet) {
			System.out.println(integer + ": " + actantClasses.get(integer).size());
		}
		System.out.println();
		actantClasses = nomsWithClasses.getPrepObjectClasses();
		keySet = actantClasses.keySet();
		for (Integer integer : keySet) {
			System.out.println(integer + ": " + actantClasses.get(integer).size());
		}
		System.out.println();
		actantClasses = nomsWithClasses.getSubjectClasses();
		keySet = actantClasses.keySet();
		for (Integer integer : keySet) {
			System.out.println(integer + ": " + actantClasses.get(integer).size());
		}
		
	}

}
