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

    void evaluate(int r, int d) {
        reevaluateR(0,d,0);
//        int currentEnd = (r == -1) ? this.r : r;
//        F = getFForList(r, d);

        /*for (Task task : taskList) {
            currentEnd += task.getProcessingTime();
            if (currentEnd < d) F += Math.abs(currentEnd - d) * task.getEarliness();
            else if (currentEnd > d) F += Math.abs(currentEnd - d) * task.getTardiness();
        }*/
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

    public void reevaluateR(int iterations, int d, int iloscPrzedzialow) {
        r = countBestRForList(iterations, d, iloscPrzedzialow);
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

    private int countBestRForList(int iterations, int d, int iloscPrzedzialowDlaR) {
        int bestR = 0;
//        int bestF = getFForList(tasks, bestR, this.d);

        int maxR = (int) 0.8 * d;

        return IntStream.range(0, maxR)
                .parallel()
                .reduce((i1, i2) -> getFForList(i1, d) > getFForList(i2, d) ? i2 : i1)
                .orElse(bestR);
    }
}
