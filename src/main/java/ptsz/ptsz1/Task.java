package ptsz.ptsz1;

import lombok.Data;

@Data
public class Task {
    private int id;
    private int processingTime;
    private int earliness;
    private int tardiness;

    private double earlToTard;
    private double variation;
    private int earlMinusTard;

    public void initStatistics(){
        earlToTard = ((double) earliness) / ((double) tardiness);
        variation = Math.abs((earliness + tardiness / 2) - earliness);
        earlMinusTard = earliness - tardiness;
    }
}
