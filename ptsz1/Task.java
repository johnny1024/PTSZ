package ptsz1;

public class Task {
	private int id;
    private int processingTime;
	private int earliness;
	private int tardiness;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }        
	public int getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	public int getEarliness() {
		return earliness;
	}
	public void setEarliness(int earliness) {
		this.earliness = earliness;
	}
	public int getTardiness() {
		return tardiness;
	}
	public void setTardiness(int tardiness) {
		this.tardiness = tardiness;
	}
}
