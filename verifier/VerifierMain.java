/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import ptsz1.FullData;
import ptsz1.Task;
import ptsz1.TaskList;

public class VerifierMain {
    public static void main(String[] args) {
    	System.out.println("Pick solution:");
		String solution = null;
		
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			solution = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Pick instance:");
		String instance = null;
		
		try {
			instance = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FullData fullData = new FullData(instance);
		
		int k = Integer.parseInt(solution.substring(solution.lastIndexOf('_') - 1, solution.lastIndexOf('_')));
                if (k == 0) k = 10;
		float h = Float.parseFloat(solution.substring(solution.lastIndexOf('_') + 1, solution.lastIndexOf('_') + 2)) / 10;
		
		try {
			Scanner scanner = new Scanner(new File(solution));
			TaskList checkedTaskList = fullData.getProblemList().get(k-1);
			int provF = scanner.nextInt();
			int r = scanner.nextInt();
			checkedTaskList.setParameters(r, h);
			int d = (int)Math.floor(h * checkedTaskList.getProcessingTimeSum());
			int checkF = 0;
			int currEnd = r;
			
            for (int i = 0; i < checkedTaskList.getNumberOfTasks(); i++) {
				int taskNo = scanner.nextInt();
				Task task = checkedTaskList.getTaskList().get(taskNo);
				currEnd += task.getProcessingTime();
				if (currEnd < d) checkF += Math.abs(currEnd-d) * task.getEarliness();
				else if (currEnd > d) checkF += Math.abs(currEnd-d) * task.getTardiness();
			}
                        
			System.out.println("Received F: " + provF);
			System.out.println("Checked F:  " + checkF);
			System.out.println("Result:     " + ((checkF == provF) ? "OK" : "ERROR"));
			
		} catch (FileNotFoundException e) {
			//
		}
	}
}
