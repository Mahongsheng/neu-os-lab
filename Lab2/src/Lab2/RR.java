package Lab2;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

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

public class RR {
    static Queue<Process> newProcess; // We use this Queue to store the process that is new.
    static Queue<Process> waitProcess; // We use this Queue to store the process that doesn't finish.
    static Queue<Process> finishedProcess; // We use this Queue to store finished processes.
    private int timeSlice;
    private int processCount;
    private double mTotalWholeTime = 0.0;
    private double mTotalWeightWholeTime = 0.0;

    public RR(int processCount, Queue<Process> processeQueue,int timeSlice) {
        this.processCount = processCount;
        this.newProcess = new PriorityQueue<>(processCount,arriveTime);
        this.newProcess = processeQueue;
        this.timeSlice = timeSlice;
        this.waitProcess = new LinkedBlockingDeque<>();
        this.finishedProcess = new LinkedList<>();
    }

    /**
     * We create a RR algorithm.
     */
    public void RRAlgorithm(){
        waitProcess.add(newProcess.poll());
        Process currProcess = waitProcess.poll();
        // Execute the first process.
        double currTime = executeProcess(currProcess,0);
        while(!waitProcess.isEmpty() || !newProcess.isEmpty()) {
            // Add all the processes reached by the "arrival time" to the waitProcess queue's header.
            while(!newProcess.isEmpty()) {
                if(newProcess.peek().getArriveTime() <= currTime) {
                    waitProcess.add(newProcess.poll());
                }
                else {
                    break;
                }
            }
            // If the remain run time of the process is greater than 0. We add it into waitProcess queue's Tail.
            if(currProcess.getLastRunTime() > 0){
                waitProcess.add(currProcess);
            }
            // When the waitProcess queue is not empty. Poll and run!
            if(!waitProcess.isEmpty()) {
                currProcess = waitProcess.poll();
                currTime = executeProcess(currProcess, currTime);
            } else {
                // There is currently no process execution,
                // but there are still processes that arrive, so time jumps directly to the arrival time.
                currTime = newProcess.peek().getArriveTime();
            }
        }
    }

    /**
     *
     * @param currProcess
     * @param currTime
     * @return currTime
     *
     * We use this method to execute the process. And set over time, turnaround time etc.
     */

    public double executeProcess(Process currProcess,double currTime){
        if (currProcess.getLastRunTime() - timeSlice <= 0){
            // Make the running visible.
            showExecuteMessage(currTime, currTime += currProcess.getLastRunTime(), currProcess.getProcessName());
            currProcess.setOverTime(currTime);
            currProcess.setLastRunTime(0);
            // Calculate the turnaround time and normalized turnaround time.
            calculateTurnaroundTime(currProcess);
            calculateWeightTurnaroundTime(currProcess);
            mTotalWholeTime += currProcess.getTurnaroundTime();
            mTotalWeightWholeTime += currProcess.getTurnaroundWeightTime();
            finishedProcess.add(currProcess);
        } else {
            // If the process can't be execute over.
            showExecuteMessage(currTime, currTime += timeSlice, currProcess.getProcessName());
            currProcess.setLastRunTime(currProcess.getLastRunTime() - timeSlice);
        }
        return currTime;
    }

    /**
     * Calculate the turnaround time.
     * @param process
     */
    private void calculateTurnaroundTime(Process process) {
        process.setTurnaroundTime(process.getOverTime() - process.getArriveTime());
    }

    /**
     * Calculate the normalized turnaround time.
     * @param process
     */
    private void calculateWeightTurnaroundTime(Process process) {
        process.setTurnaroundWeightTime(process.getTurnaroundTime() / process.getServiceTime());
    }

    /**
     * In this method we make the running process visible.
     * @param startTime
     * @param endTime
     * @param name
     */
    private void showExecuteMessage(double startTime, double endTime, String name) {
        System.out.println(startTime + "～" + endTime + "：【Process" + name + "】Running");
    }

    /**
     * In this method we print all the process that enter the program.
     * @param queue
     */
    public static void printAll(Queue<Process> queue) {
        System.out.print("Process name    ");
        System.out.print("Arrival time    ");
        System.out.println("Service time    ");
        Process process = null;
        while (!queue.isEmpty()){
            process = queue.poll();
            System.out.print("Process" + process.getProcessName() + "    ");
            System.out.print("    " + process.getArriveTime() + "        ");
            System.out.println("        " + process.getServiceTime() + "    ");
        }
    }


    /**
     * In this method we print the result about every process's running.
     */
    public void showResult() {
        System.out.print("Process name    ");
        System.out.print("Turnaround time    ");
        System.out.println("Normalized turnaround time    ");
        Process process;
        while(!finishedProcess.isEmpty()) {
            process = finishedProcess.poll();
            System.out.print("Process" + process.getProcessName() + "    ");
            System.out.print("    " + process.getTurnaroundTime() + "        ");
            System.out.println("        " + process.getTurnaroundWeightTime() + "    ");
        }
        System.out.println("Average turnaround time：" + mTotalWholeTime / (double) processCount);
        System.out.println("Average normalized turnaround time：" + mTotalWeightWholeTime / (double) processCount);
    }


    /**
     * We use camparator to make the queue is sorted by arrival time from small to large.
     */
    public static Comparator<Process> arriveTime = new Comparator<Process>() {
        @Override
        public int compare(Process o1, Process o2) {
            return (int) (o1.getArriveTime() - o2.getArriveTime());
        }
    };

    /**
     * We use this method to get the processes in the file.
     * @return Queue
     */
    public static Queue getProcess(){
        try {
            File processFile = new File("D:\\Java项目\\Lab1\\src\\Lab2\\ProcessFile");
            BufferedReader readProcess = new BufferedReader(new FileReader(processFile));
            Queue<Process> newProcess = new LinkedBlockingDeque<>();
            String result;
            while ((result = readProcess.readLine()) != null){
                String[] processList;
                processList = result.split(" ");
                // Create a process.
                Process process = new Process(processList[0],Double.parseDouble(processList[1]),Double.parseDouble(processList[2]),Double.parseDouble(processList[2]));
                newProcess.offer(process);
            }

            return newProcess;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Test the program.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter the time slice：");
        Scanner scanner = new Scanner(System.in);
        int timeSlice = scanner.nextInt();
        Queue<Process> processQueue = getProcess();
        RR rr = new RR(20,processQueue,timeSlice);

        System.err.println("*******************Process overview*****************");
        Thread.sleep(1000);
        printAll(new LinkedBlockingQueue<>(processQueue));
        Thread.sleep(1000);

        System.err.println("********************Running*******************");
        Thread.sleep(1000);
        rr.RRAlgorithm();
        Thread.sleep(1000);

        System.err.println("*******************Running Result*****************");
        Thread.sleep(1000);
        rr.showResult();

    }
}
