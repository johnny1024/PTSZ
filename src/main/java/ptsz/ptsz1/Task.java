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

    public void initStatistics() {
        earlToTard = ((double) earliness) / ((double) tardiness);
        variation = (double) (earliness + tardiness) / 2 - tardiness; // - oznacza ze
        earlMinusTard = earliness - tardiness;
    }
}

/**
 * earl 10
 * avg  15
 * tard 20
 *  avg - earl = 5 > 0 -> ze lepiej na prawo polozyc
 *  < 0 -> lepiej na lewo
 */
