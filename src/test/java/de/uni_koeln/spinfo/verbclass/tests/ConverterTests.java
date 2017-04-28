package de.uni_koeln.spinfo.verbclass.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.uni_koeln.spinfo.verbclass.converters.SentenceDataToTeslaConverter;
import de.uni_koeln.spinfo.verbclass.converters.SentenceInfoObject;

public class ConverterTests {

	@Test
	public void testSDtoTesla() throws IOException {
		SentenceDataToTeslaConverter sdttc = new SentenceDataToTeslaConverter();
		BufferedReader in = new BufferedReader(new FileReader(new File(
				"output/parsedSentencesWithVerbs/zersägen.txt")));
		String line = in.readLine();
		StringBuffer buff = new StringBuffer();
		List<String> infoLines = new ArrayList<String>();
		int upset = 0;
		int nextupset = 0;
		while (line != null) {
			if (line.trim().length() == 0) {
				nextupset = nextupset+1;
				sdttc.addSentence(infoLines, upset);
				infoLines = new ArrayList<String>();
				upset = nextupset;
			} else {
				// String[] split = line.split("\\s");
				infoLines.add(line);
				nextupset = nextupset + line.length()+1;

			}
			buff.append(line + "\n");
			line = in.readLine();
		}
		String text = buff.toString();
		System.out.println(text.length());
		List<SentenceInfoObject> sentences = sdttc.getSentences();
		System.out.println(sentences.size());
		for (SentenceInfoObject sentenceInfoObject : sentences) {
			System.out.println(sentenceInfoObject.getStart() + " "
					+ sentenceInfoObject.getEnd());
			
			for (int i = 0; i < sentenceInfoObject.getWords().size(); i++) {
				int start = sentenceInfoObject.getWords().get(i).getStart();

				int end = sentenceInfoObject.getWords().get(i).getEnd();

				System.out.println(start + " to " + end);
				System.out.println(text.substring(start, end));
				System.out.println(sentenceInfoObject.getWords().get(i));
			}
			System.out.println();
			
		}

	}

}
