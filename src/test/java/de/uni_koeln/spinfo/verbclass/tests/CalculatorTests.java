package de.uni_koeln.spinfo.verbclass.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.calc.Calculator;

public class CalculatorTests {

	@Test
	public void testPresssQ() {
		//System.out.println(Calculator.calculatePresssQ(35, 26, 10));
		//System.out.println(Calculator.calculatePresssQ(35, 32, 5));
		int sampleSize = 35;
		int numberOfGroups = 5 ;
		for(int correct=0; correct<=sampleSize;correct++){
			System.out.print("Correct: " + correct + " Result: ");
			System.out.println(Calculator.calculatePresssQ(sampleSize, correct, numberOfGroups));
			//System.out.println();
		}
	}

}
