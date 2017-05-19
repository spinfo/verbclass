package de.uni_koeln.spinfo.verbclass.tests;

import java.io.IOException;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.goldstandard.ConsistentRated;
import de.uni_koeln.spinfo.verbclass.goldstandard.DataImporterNotAmbigious;

public class InterRaterTest {

	@Test
	public void test() throws IOException {
		DataImporterNotAmbigious dina = new DataImporterNotAmbigious();
		
		dina.importFromFile("data/Experts_all.csv");
		//dina.printAttributesWithRates();
		System.out.println();
		System.out.println();
		
		System.out.println("data/Experts_omr_cu_sh.csv");
		dina.importFromFile("data/Experts_omr_cu_sh.csv");
		
		System.out.println();
		System.out.println();
		System.out.println("data/Experts_omr_cu_mr.csv");
		dina.importFromFile("data/Experts_omr_cu_mr.csv");
		
		System.out.println();
		System.out.println();
		System.out.println("data/Experts_cu_sh_mr.csv");
		dina.importFromFile("data/Experts_cu_sh_mr.csv");
		
		System.out.println();
		System.out.println();
		System.out.println("data/Experts_omr_sh_mr.csv");
		dina.importFromFile("data/Experts_omr_sh_mr.csv");
	}

}
