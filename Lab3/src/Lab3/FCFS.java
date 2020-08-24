package Lab3;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Autor 马洪升 20175188
 *
 * @Date 2018.12.22
 *
 * @version 3.0
 */

public class FCFS {
    static Queue<MemoryProcess> newProcess; // We use this Queue to store the process that is new.
    static ArrayList<MemoryProcess> handlingProcess; // We use this list to store the process that is running.
    static Queue<MemoryProcess> waitProcess; // We use this Queue to store the process that doesn't finish.

    public FCFS(Queue<MemoryProcess> processeQueue) {
        this.newProcess = new PriorityQueue<>(arriveTime);
        this.newProcess = processeQueue;
        this.handlingProcess = new ArrayList<>();
        this.waitProcess = new PriorityQueue<>(arriveTime);;
    }

    /**
     * We use this method to make a FCFS scheduling.
     * @param memory
     * @param algorithm
     */
    public void FCFSAlgorithm(Memory memory, int algorithm) {
        int currTime = 1;
        // We traverse these three list or queue until they are empty.
        while (!newProcess.isEmpty() || !handlingProcess.isEmpty() || !waitProcess.isEmpty()){
            int size = handlingProcess.size();
            if (size !=0 ){
                // Iterate all the processes that are running.
                Iterator<MemoryProcess> iterator = handlingProcess.iterator();
                while (iterator.hasNext()){
                    MemoryProcess memoryProcess = iterator.next();
                    // If arrival time plus required time is less than current time, release!
                    if ((memoryProcess.getTimeStamp() + memoryProcess.getTimeRequired()) <= currTime){
                        // Remove this process from the list.
                        iterator.remove();
                        System.out.println("Current time： " + currTime + "s");
                        release(memory, memoryProcess);
                    }
                }
            }
            // Ensure if the process's arrival time is less than current time.
            if (!newProcess.isEmpty()){
                if (newProcess.peek().getTimeStamp() <= currTime){
                    waitProcess.add(newProcess.poll());
                }
            }
            // Run the process in the wait queue. Allocate them!
            if (!waitProcess.isEmpty()){
                System.out.println("Current time：" + currTime + "s");
                executeProcess(memory, waitProcess.poll(), algorithm);
            }
            // Iterate the time by one second.
            currTime++;
        }

    }

    /**
     * We use this method to execute the process. And we choose the algorithm we want to use.
     * @param memory
     * @param currProcess
     * @param algorithm
     */
    public void executeProcess(Memory memory, MemoryProcess currProcess,int algorithm){
        switch (algorithm){
            case 1:
                currProcess.setAddress(memory.firstFit(currProcess));break;
            case 2:
                currProcess.setAddress(memory.nextFit(currProcess));break;
            case 3:
                currProcess.setAddress(memory.bestFit(currProcess));break;
        }
    }

    /**
     * We use this method to release the process.
     * @param memory
     * @param memoryProcess
     */
    public void release(Memory memory, MemoryProcess memoryProcess){
        memory.recycle(memoryProcess.getAddress());
    }


    /**
     * A comparator which can ensure the arrival time is sorted.
     */
    public static Comparator<MemoryProcess> arriveTime = new Comparator<MemoryProcess>() {
        @Override
        public int compare(MemoryProcess o1, MemoryProcess o2) {
            return (int) (o1.getTimeStamp() - o2.getTimeStamp());
        }
    };

    /**
     * Interface. Test the whole program.
     * @param args
     */
    public static void main(String[] args) {
        Memory memory = new Memory();
        FCFS fcfs = new FCFS(memory.getMemoryProcess());
        while (true){
            System.err.println("------------------------------------------");
            System.err.println("1.FirstFit");
            System.err.println("2.NextFit");
            System.err.println("3.BestFit");
            System.err.print("Please choose the algorithm by its number: ");
            Scanner in = new Scanner(System.in);
            int algorithm = in.nextInt();
            if (algorithm == 1 || algorithm == 2 || algorithm == 3){
                fcfs.FCFSAlgorithm(memory,algorithm);
                break;
            }else {
                System.out.println("Please reselect!");
            }
        }

    }
}
