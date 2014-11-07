import static org.junit.Assert.*;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.verbfeatures.SpecialLexemFinder;


public class VerbFeatureTester {

	@Test
	public void testSpecialLexemFinder() {
		SpecialLexemFinder slf = new SpecialLexemFinder(); 
		System.out.println(slf.belongsToCategory("sekundenlang"));
	}

}
