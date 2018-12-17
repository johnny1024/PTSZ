package ptsz.Evolution;

import lombok.Data;
import ptsz.ptsz1.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Data
public class Individual {
    private List<Task> taskList;
    private int r = 0;
    private boolean used = false;
    private int F = 0;

    private Random random = new Random();

    Individual() {
        taskList = new ArrayList<>();
    }

    void mutate(int instanceSize) {
        for (int i = 0; i < instanceSize / 10; i++) {
            int rand = random.nextInt(taskList.size());
            Collections.swap(taskList, rand, (rand + 1) % taskList.size());
        }
    }

//    void evaluate(int d) {
//        reevaluateR(d,Evolution.iloscPrzedzialow);
//    }

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

    public void reevaluateR(int d, int iloscPrzedzialow) {
        r = countBestRForList(d, iloscPrzedzialow);
        F = getFForList(r, d);
    }

    private int getFForList(int r, int d) {
        int currentEnd = r;
        int f = 0;
        for (Task task : taskList) {
            currentEnd += task.getProcessingTime();
            f += Math.abs(currentEnd - d) * ((currentEnd < d) ? task.getEarliness() : task.getTardiness());
        }
        return f;
    }

    private int countBestRForList(int d, int iloscPrzedzialowDlaR) {
        int bestR = 0;
        int maxR = (int) (d * 0.8);

        int stepR = maxR / iloscPrzedzialowDlaR;

        return IntStream.range(0, maxR)
                .parallel()
                .filter(i -> i%stepR == 0)
                .reduce((i1, i2) -> getFForList(i1, d) > getFForList(i2, d) ? i2 : i1)
                .orElse(bestR);
    }
}
