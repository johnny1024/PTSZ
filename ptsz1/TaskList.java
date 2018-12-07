package ptsz1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Evolution.Evolution;

public class TaskList {
	private List<Task> taskList;
	private int processingTimeSum = 0;
	private int numberOfTasks;
    private int k;
	private int r = 0;
    private float h = 0.2f;
    private int d = 0;
	private int F = 0;
	private String inputFileName = "";
	private long time = 0;
        
	public TaskList() {
		taskList = new ArrayList<>();
	}

	private void countFullProcessingTime() {
		for (Task task : taskList) {
			processingTimeSum += task.getProcessingTime();
		}
	}
	
    public void setParameters(int r, float h) {
    	this.r = r;
    	this.h = h;
    	if (processingTimeSum == 0) countFullProcessingTime();
    	this.d = (int)Math.floor(h * processingTimeSum);
    }
    
    public void solve() {
        long startTime = System.nanoTime();
        
        //algorithm1();
        //algorithm2();
        algorithm3_Evolution();
        
        time = (System.nanoTime()-startTime) / 1000; // 1k - micro, 1kk - mili
        countF();
        printResult();
        saveResult();
    }
    
    private void algorithm1() {
    	if (h >= 0.5f) {
    		// sort asc earl
    		Collections.sort(taskList, (o1, o2) -> Integer.valueOf(o1.getEarliness()).compareTo(Integer.valueOf(o2.getEarliness())));
    	}
    	else {
    		// sort desc tard
    		Collections.sort(taskList, Collections.reverseOrder((o1, o2) -> Integer.valueOf(o1.getTardiness()).compareTo(Integer.valueOf(o2.getTardiness()))));
    	}
    }
    
    private void algorithm2() {
        List<Task> resultTaskList = new ArrayList<>();
        int currEnd = r;        
        for(int i = 0; i < numberOfTasks-1; i++) {
            if (currEnd <= d) {
                //find min early
                Task min = Collections.min(taskList, (o1, o2) -> Integer.valueOf(o1.getEarliness()).compareTo(Integer.valueOf(o2.getEarliness())));
                taskList.remove(min);
                resultTaskList.add(min);
                currEnd += min.getProcessingTime();
            }
            else {
                //find max tard
                Task max = Collections.max(taskList, (o1, o2) -> Integer.valueOf(o1.getTardiness()).compareTo(Integer.valueOf(o2.getTardiness())));
                taskList.remove(max);
                resultTaskList.add(max);
                currEnd += max.getProcessingTime();
            }
        }
        resultTaskList.add(taskList.get(0));
        taskList = resultTaskList;
    }
    
    private void algorithm3_Evolution() {
    	Evolution evolution = new Evolution(taskList, r, d);
    	evolution.solve();
    	taskList = evolution.getResultList();
    }
    
    private void countF() {
    	int currentEnd = r;
    	F = 0;
    	if(d == 0) System.out.println("Parameters not set");
            
    	for (Task task : taskList) {
    		currentEnd += task.getProcessingTime();
    		if (currentEnd < d) F += Math.abs(currentEnd - d) * task.getEarliness();
    		else if (currentEnd > d) F += Math.abs(currentEnd - d) * task.getTardiness();
        }
	}
    
    private void printResult() {
    	System.out.println("Result for " + inputFileName + " for k=" + k + " is " + F + " (" + time+").");
    	//System.out.println(time);
        //System.out.println(F);
    }
    
    private String makeFileName() {
    	String fileName = inputFileName.substring(inputFileName.lastIndexOf('\\') + 1, inputFileName.lastIndexOf('.'));
    	fileName += "_" + k + "_" + (int)(h*10) + ".txt";
    	return fileName;
    }
    
    private void saveResult() {
    	String fileName = makeFileName();
    	File outFile = new File(fileName);
    	PrintWriter out;
    	
    	try {
    		outFile.createNewFile();
    		out = new PrintWriter(outFile);
    		
    		out.print(F + " " + r);
        	for (Task task : taskList) {
    			out.print(" " + task.getId());
    		}
        	out.close();
		} catch (IOException e) {
			System.out.println("ERROR");
		}
    }
        
	public List<Task> getTaskList() {
		return taskList;
	}

	public int getProcessingTimeSum() {
		return processingTimeSum;
	}

	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	public void setNumberOfTasks(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}
	
	public int getK() {
		return k;
	}
	
	public void setK(int k) {
		this.k = k;
	}
	
	public void setInputFileName (String inputFileName) {
		this.inputFileName = inputFileName;
	}
	
	public int getR() {
		return r;
	}
	
	public float getH() {
		return h;
	}
}
