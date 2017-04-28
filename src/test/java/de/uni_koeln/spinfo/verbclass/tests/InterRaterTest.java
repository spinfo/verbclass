package de.uni_koeln.spinfo.verbclass.tests;

import java.io.IOException;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.goldstandard.DataImporterNotAmbigious;

public class InterRaterTest {

	@Test
	public void test() throws IOException {
		DataImporterNotAmbigious dina = new DataImporterNotAmbigious();
		dina.importFromFile("data/Experts_1.csv");
		dina.printAttributesWithRates();
	}

}
