import java.io.IOException;

import de.uni_koeln.spinfo.verbclass.dewac.DeWaCReader;


public class Starter {

	public static void main(String[] args) throws IOException {
		DeWaCReader dwr = new DeWaCReader("C://Korpora//DeWaC//sdewac-v3.tagged");
		dwr.process();
	}

}
