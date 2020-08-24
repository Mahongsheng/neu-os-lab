package Lab2;

/**
 * @Author 马洪升 20175188
 *
 * @Date 2018.12.17 21:00
 *
 * @Version 1.0
 *
 * This is a class that achieves the Round Robin Algorithm.
 *
 */

public class Process {
    private String processName;
    private double arriveTime; // Arrival time
    private double serviceTime; // Service time
    private double lastRunTime; // Remain time to execute
    private double overTime; // Finished time
    private double turnaroundTime; // Turnaround time
    private double turnaroundWeightTime; // Normalized turnaround time

    public Process(String processName, double arriveTime, double serviceTime,double lastRunTime) {
        this.processName = processName;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
        this.lastRunTime = lastRunTime;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }


    public double getTurnaroundWeightTime() {
        return turnaroundWeightTime;
    }

    public void setTurnaroundWeightTime(double turnaroundWeightTime) {
        this.turnaroundWeightTime = turnaroundWeightTime;
    }

    public double getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(double lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}

