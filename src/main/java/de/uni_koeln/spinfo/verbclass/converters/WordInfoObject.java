package de.uni_koeln.spinfo.verbclass.converters;

public class WordInfoObject {
	
	private String token;
	private int start;
	private int end;
	
	private String lemma;
	
	private String posTag;
	
	private String role;
	
	private int headID;
	
	public WordInfoObject(String token, int start, int end){
		this.token = token;
		this.start = start;
		this.end = end;
	}
	
	
	
	public String getToken() {
		return token;
	}



	public int getStart() {
		return start;
	}



	public int getEnd() {
		return end;
	}



	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(token + " " + start + " " + end);
		buff.append(" lemma:" + lemma + " pos:" + posTag);
		buff.append(" head:" + headID + " role:" +role + "\n");
		return buff.toString();
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public String getPosTag() {
		return posTag;
	}

	public void setPosTag(String posTag) {
		this.posTag = posTag;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getHeadID() {
		return headID;
	}

	public void setHeadID(int headID) {
		this.headID = headID;
	}
	
	
	

}
