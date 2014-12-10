package de.uni_koeln.spinfo.verbclass.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.verbfeatures.CategorySpecifier;


public class VerbFeatureTester {

	@Test
	public void testSpecialLexemFinder() {
		CategorySpecifier slf = new CategorySpecifier(); 
		System.out.println(slf.belongsToCategory("sekundenlang"));
	}

}
