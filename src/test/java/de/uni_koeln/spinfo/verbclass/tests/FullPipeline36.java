package de.uni_koeln.spinfo.verbclass.tests;


import is2.data.SentenceData09;
import is2.io.CONLLWriter09;
import is2.lemmatizer.Lemmatizer;
import is2.parser.Parser;
import is2.tag.Tagger;
import is2.tools.Tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Bernd Bohnet, 13.09.2010
 * 
 * Illustrates the application the full pipeline: lemmatizer, morphologic, tagger, and parser
 */
public class FullPipeline36 {

	
	
	//	shows how to parse a sentences and call the tools
	public static void main(String[] args) throws IOException {
		
		// Create a data container for a sentence
		SentenceData09 i = new SentenceData09();

		if (args.length==1) { // input might be a sentence: "This is another test ." 
			StringTokenizer st = new StringTokenizer(args[0]);
			ArrayList<String> forms = new ArrayList<String>();
			
			forms.add("<root>");
			while(st.hasMoreTokens()) forms.add(st.nextToken());
			
			i.init(forms.toArray(new String[0]));
			
		} else {
			// provide a default sentence: Haus has a mutated vowel
			i.init(new String[] {"<root>","Sie","müssen","ihren","Rechner","neu", "installieren",",","um","ihn","starten", "zu", "können", "."});

		}
		

		// lemmatizing
		
		System.out.println("\nReading the model of the lemmatizer");
		Tool lemmatizer = new Lemmatizer("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/lemma-ger-3.6.model");  // create a lemmatizer
		//Tool lemmatizer = new Lemmatizer("models/CoNLL2009-ST-English-ALL.anna-3.3.lemmatizer.model");  // create a lemmatizer
		
			
		System.out.println("Applying the lemmatizer");
		i=lemmatizer.apply(i);

		System.out.print(i.toString());
		System.out.print("Lemmata: "); for (String l : i.plemmas) System.out.print(l+" "); System.out.println();
		
		//lemmatizer = null;
		// morphologic tagging
		
		System.out.println("\nReading the model of the morphologic tagger");
		is2.mtag.Tagger morphTagger = new is2.mtag.Tagger("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/morphology-ger-3.6.model");
		
		System.out.println("\nApplying the morpholoigc tagger");
		i=morphTagger.apply(i);
		
		System.out.print(i.toString());
		System.out.print("Morph: "); for (String f : i.pfeats) System.out.print(f+" "); System.out.println();
	
		//morphTagger = null;
		// part-of-speech tagging
		
		System.out.println("\nReading the model of the part-of-speech tagger");
		Tool tagger = new Tagger("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/tag-ger-3.6.model");

		System.out.println("\nApplying the part-of-speech tagger");
		i=tagger.apply(i);

		System.out.print(i.toString());
		System.out.print("Part-of-Speech tags: "); for (String p : i.ppos) System.out.print(p+" "); System.out.println();
		
		//tagger = null;
		// parsing
		
		System.out.println("\nReading the model of the dependency parser");
		Tool parser = new Parser("models/ger-tagger+lemmatizer+morphology+graph-based-3.6/parser-ger-3.6.model");

		System.out.println("\nApplying the parser");
		i=parser.apply(i);
		
		System.out.println(i.toString());

		//System.out.print("arg: "); for (String[] p : i.arg) for(String s:p) System.out.print(s+" "); System.out.println();
		//System.out.print("argpos: "); for (int[] p : i.argposition) for(int s:p) System.out.print(s+" "); System.out.println();
		//System.out.print("feats: "); for (String[] p : i.feats) for(String s:p) System.out.print(s+" "); System.out.println();
		// write the result to a file
		
		CONLLWriter09 writer = new is2.io.CONLLWriter09("example-out.txt");
		
		writer.write(i, CONLLWriter09.NO_ROOT);
		writer.finishWriting();
		
		

	}

	
}
