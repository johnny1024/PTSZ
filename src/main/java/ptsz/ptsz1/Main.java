package ptsz.ptsz1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		System.out.println("Pick instance:");
		String instance = null;
		
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			instance = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		float h = 0.8f;

		FullData fullData = new FullData(instance);
        fullData.setParameters(0, h);

		System.out.println("H: " + h);
        //for (int i = 0; i < 11; i++) {
        fullData.solveAll();
        //System.out.println("");
        //}
	}

}
