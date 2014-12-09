package de.uni_koeln.spinfo.verbclass.deprecated;

@Deprecated
class TeslaAnnotation{
	private static int count ;
	public TeslaAnnotation(String content, int start, int end) {
		this.content = content;
		this.start = start;
		this.end = end;
		System.out.println(++count);
	}
	int start;
	int end;
	String content;
	
	public String toString(){
		return content + " " + start + "-" + end;
	}
}
