package de.uni_koeln.spinfo.verbclass.tests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.io.ArgumentClasses;
import de.uni_koeln.spinfo.verbclass.io.ClassifiedNomsReader;

public class ArgReaderTest {

	@Test
	public void test() throws IOException {
		ClassifiedNomsReader cnr = new ClassifiedNomsReader();
		ArgumentClasses nomsWithClasses = cnr.getNomsWithClasses(new File("data/classArg"));
		System.out.println(nomsWithClasses.getAccObjectClasses().size());
		System.out.println(nomsWithClasses.getAccObjectClasses());
		System.out.println();
		System.out.println(nomsWithClasses.getPrepObjectClasses().size());
		System.out.println(nomsWithClasses.getPrepObjectClasses());
		System.out.println();
		System.out.println(nomsWithClasses.getSubjectClasses().size());
		System.out.println(nomsWithClasses.getSubjectClasses());
		System.out.println();
	}

}
