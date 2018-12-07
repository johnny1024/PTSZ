package Evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ptsz1.Task;
import ptsz1.TaskList;

public class Evolution {
	private int iterations = 1000;
	private int populationSize = 10;
	private int r;
	private int d;
	private int mutationProbability = 10; // %
	
	private List<Task> inputList;
	private List<Task> resultList;
	
	private List<Individual> population;
	
	private Random random = new Random();
	
	public Evolution (List<Task> taskList, int r, int d) {
		this.inputList = taskList;
		this.r = r;
		this.d = d;
		population = new ArrayList<>();
	}
	
	// setup and start
	public void solve() {
		for(int i = 0; i < populationSize; i++) {
			Individual individual = new Individual();
			for(int j = 0; j < inputList.size(); j++) {
				individual.getTaskList().add(inputList.get(j));
			}
			Collections.shuffle(individual.getTaskList());
			population.add(individual);
		}
				
		for(int i = 0; i < iterations; i++) {
			cross();
			kill();
		}
		
		resultList = population.get(populationSize - 1).getTaskList();
	}
	
	// cross and mutate
	private void cross() {
		for(int i = 0; i < populationSize / 2; i++) {
			int parent1 = random.nextInt(populationSize);
			while (population.get(parent1).getUsed()) parent1 = (parent1+1)%populationSize;
			population.get(parent1).setUsed(true);
			int parent2 = random.nextInt(populationSize);
			while (population.get(parent2).getUsed()) parent2 = (parent2+1)%populationSize;
			population.get(parent2).setUsed(true);
			
			Individual child = new Individual();
			child.setUsed(true);
			
			for (int j = 0; j < population.get(parent1).getTaskList().size() / 2; j ++) {
				child.getTaskList().add(population.get(parent1).getTaskList().get(j));
			}
			for (int j = population.get(parent2).getTaskList().size() / 2; j < population.get(parent2).getTaskList().size(); j++) {
				if (!child.getTaskList().contains(population.get(parent2).getTaskList().get(j))) {
					child.getTaskList().add(population.get(parent2).getTaskList().get(j));
				}
			}
			for (int j = 0; j < population.get(parent2).getTaskList().size() / 2; j++) {
				if (!child.getTaskList().contains(population.get(parent2).getTaskList().get(j))) {
					child.getTaskList().add(population.get(parent2).getTaskList().get(j));
				}
			}
			
			if(random.nextInt(100)+1 < mutationProbability) {
				child.mutate();
			}
			population.add(child);
		}
		
		for (Individual individual : population) {
			individual.setUsed(false);
		}
	}
	
	// evaluate and kill
	private void kill() {
		for (Individual individual : population) if (individual.getF() == 0) individual.evaluate(r, d);
		Collections.sort(population, (o1, o2) -> Integer.valueOf(o1.getF()).compareTo(Integer.valueOf(o2.getF())));
		
		for(int i = 0; i < populationSize / 2; i++) population.remove(0);
	}
	
	public List<Task> getResultList() {
		return resultList;
	}
}
