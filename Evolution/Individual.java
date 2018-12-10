package Evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ptsz1.Task;

public class Individual {
	private List<Task> taskList;
	private boolean used = false;
	private int F = 0;
	
	private Random random = new Random();
	
	Individual() {
		taskList = new ArrayList<>();
	}
	
	void mutate(int instanceSize) {
		for(int i = 0; i < instanceSize / 10; i++) {
			int rand = random.nextInt(taskList.size());
			Collections.swap(taskList, rand, (rand+1)%taskList.size());
		}
	}
	
	void evaluate(int r, int d) {
		int currentEnd = r;
    	F = 0;
            
    	for (Task task : taskList) {
    		currentEnd += task.getProcessingTime();
    		if (currentEnd < d) F += Math.abs(currentEnd - d) * task.getEarliness();
    		else if (currentEnd > d) F += Math.abs(currentEnd - d) * task.getTardiness();
        }
	}
	
	List<Task> getTaskList() {
		return taskList;
	}
	
	boolean getUsed() {
		return used;
	}
	
	void setUsed(boolean value) {
		this.used = value;
	}
	
	int getF() {
		return F;
	}
}
