package ptsz.ptsz1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullData {
	private List<TaskList> problemList;
	private int numberOfProblems;
	private String instance;
	
	public FullData(String instance){
		problemList = new ArrayList<>();
		this.instance = instance;
		
		try {
			loadData();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR - file not found");
		}
	}
	
	public void setParameters(int r, float h) {
		for (TaskList taskList : problemList) {
        	taskList.setParameters(r, h);
        }
	}
	
	public void solveAll() {
		for (TaskList taskList : problemList) {
			taskList.solve();
        }
	}
	
	private void loadData() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(instance));
		numberOfProblems = scanner.nextInt();
		
		for (int i = 0; i < numberOfProblems; i++) {
			TaskList taskList = new TaskList();			
			taskList.setK(i + 1); // i + 1 ???
			taskList.setInputFileName(instance);
			taskList.setNumberOfTasks(scanner.nextInt());
						
			for (int j = 0; j < taskList.getNumberOfTasks(); j++) {
				Task task = new Task();
				
                task.setId(j);
				task.setProcessingTime(scanner.nextInt());
				task.setEarliness(scanner.nextInt());
				task.setTardiness(scanner.nextInt());
				
				taskList.getTaskList().add(task);
			}
			
			problemList.add(taskList);
		}
		scanner.close();
	}
	
	public List<TaskList> getProblemList() {
		return problemList;
	}
}
