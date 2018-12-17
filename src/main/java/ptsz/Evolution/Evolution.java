package ptsz.Evolution;

import lombok.Data;
import ptsz.ptsz1.Task;

import java.util.*;
import java.util.stream.Collectors;
@Data
public class Evolution {

    private int instanceSize;
    private int iterations = 200;
    private int rIterations = 100;
    private int populationSize = 10;
    private int r;
    private int d;
    private int mutationProbability = 40; // %
    private int iloscPrzedzialowDlaR = 10;

    private List<Task> inputList;
    private List<Task> resultList;
    private int resultR = 0;
    private Individual resultIndividual;

    private List<Individual> population;

    private Random random = new Random();

    public Evolution(List<Task> taskList, int r, int d, int instanceSize) {
        this.instanceSize = instanceSize;
        this.populationSize = (int) (Math.log(instanceSize))*20;
//        this.populationSize = instanceSize;
        this.inputList = taskList;
        this.r = r;
        this.d = d;
        population = new ArrayList<>();
    }


    private Individual getVariationVariant(List<Task> list) {
//		List<Task> unOrdered = list.stream().sorted((o1, o2) -> o1.).collect(Collectors.toList());
//		List<Task> ordered = new LinkedList<>();


        return null;
    }

    private Individual getVariantEarlToTard(List<Task> list) {
        Individual individual = new Individual();
        individual.getTaskList().addAll(list.stream().sorted(Comparator.comparingDouble(Task::getEarlToTard)).collect(Collectors.toList()));
        return individual;
    }


    private void nonRandomMethod() {
        // 1 	malejaco
        // 2	variation; MAX ... 0 ... MAX
        // 3	earlToTard;
        // 4	earlMinusTard
        // 5
        // 6
    }

    // setup and start
    public void solve() { // wykonywane K razy dla roznych k (0..10)
        inputList.parallelStream().forEach(Task::initStatistics);

        population.add(getVariantEarlToTard(inputList));
        population.add(getVariantEarlToTard(inputList));


        for (int i = population.size(); i < populationSize; i++) {
            Individual individual = new Individual();
            for(int j = 0; j < inputList.size(); j++) {
                individual.getTaskList().add(inputList.get(j));
            }
            Collections.shuffle(individual.getTaskList());
            population.add(individual);
        }

        for (int i = 0; i < iterations; i++) {
            cross();
            kill();
        }
        resultIndividual = population.get(populationSize -1);
        resultR = resultIndividual.getR();
        resultList = population.get(populationSize - 1).getTaskList();
        resultIndividual.reevaluateR(0, d, 0);
    }



    // cross and mutate
    private void cross() {
        for (int i = 0; i < populationSize / 2; i++) {
            int parent1 = random.nextInt(populationSize);
            while (population.get(parent1).getUsed()) parent1 = (parent1 + 1) % populationSize;
            population.get(parent1).setUsed(true);
            int parent2 = random.nextInt(populationSize);
            while (population.get(parent2).getUsed()) parent2 = (parent2 + 1) % populationSize;
            population.get(parent2).setUsed(true);

            Individual child = new Individual();
            child.setUsed(true);

            for (int j = 0; j < population.get(parent1).getTaskList().size() / 2; j++) {
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

            if (random.nextInt(100) + 1 < mutationProbability) {
                child.mutate(instanceSize);
            }
            child.reevaluateR(rIterations, this.d, this.iloscPrzedzialowDlaR);
            population.add(child);
        }

        for (Individual individual : population) {
            individual.setUsed(false);
        }
    }

    // evaluate and kill
    private void kill() {
        for (Individual individual : population) if (individual.getF() == 0) individual.evaluate(r, d);
        Collections.sort(population, Collections.reverseOrder(Comparator.comparing(Individual::getF)));

        for (int i = 0; i < populationSize / 2; i++) population.remove(0);
    }

    public List<Task> getResultList() {
        return resultList;
    }
}
