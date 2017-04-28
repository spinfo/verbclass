package de.uni_koeln.spinfo.verbclass.converters;

import is2.data.SentenceData09;
import is2.io.CONLLReader09;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class TeslaDataToSentenceDataConverter {	
	
	private SentenceData09 sd;
	
	public void initNewSD(List<String> words){
		sd = new SentenceData09();
		words.add(0, CONLLReader09.ROOT);
		sd.init(words.toArray(new String[words.size()]));
	}
	
	public void addLemmata(List<String> lemmata){
		lemmata.add(0, CONLLReader09.ROOT_LEMMA);
		sd.plemmas = lemmata.toArray(new String[lemmata.size()]);
	}
	
	public void addPOSTags(List<String> posTags){
		posTags.add(0, CONLLReader09.ROOT_POS);
		sd.ppos = posTags.toArray(new String[posTags.size()]);
	}
	
	public void addMorphSynInfo(List<String> morphsyn){
		morphsyn.add(0, CONLLReader09.NO_TYPE);
		sd.pfeats = morphsyn.toArray(new String[morphsyn.size()]);
	}
	
	public void addDependencies(List<String> childroles, List<Integer> mothernodes){
		mothernodes.add(0,-1);
		sd.pheads = ArrayUtils.toPrimitive(mothernodes.toArray(new Integer[mothernodes.size()]));
		childroles.add(0, CONLLReader09.NO_TYPE);
		sd.plabels = childroles.toArray(new String[childroles.size()]);
	}
	
	public SentenceData09 getSentenceData(){
		return sd;
	}

}
