import java.util.*;

/**
 * @author JosemiMoran
 * @version 1.0, 22/04/21
 */
public class MyProblem {
    public static Map<Integer, Integer> statistic;

    public static void main(String[] args) {
        System.out.println("Starting main...");
        int numThreads = Integer.parseInt(args[0]); //Number of threads of the program
        ArrayList<Thread> threadArrayList = new ArrayList<>(numThreads);
        ArrayList<ClassA> classAList = new ArrayList<>(numThreads);
        //ClassA classA = new ClassA(); //ClassA object
        statistic = new HashMap<>();
        for (int j = 0; j < numThreads; j++) {
            ClassA classA = new ClassA(j);
            classAList.add(classA);
        }
        for (int j = 0; j < numThreads; j++) {
            if (j == numThreads - 1) classAList.get(j).setNext(classAList.get(0));
            else classAList.get(j).setNext(classAList.get(j + 1));
        }
        //Creating threads
        for (int i = 0; i < numThreads; i++) {
            System.out.println("Creating thread " + i);
            Thread thread = new Thread(new ClassB(classAList.get(i), i), "Thread " + i);
            threadArrayList.add(thread); // Adding the thread into the arraylist
        }

        //Starting threads run routine
        for (Thread thread : threadArrayList) {
            thread.start();
        }

        if (!threadArrayList.get(0).getState().toString().equals("RUNNING")) {
            synchronized (classAList.get(0)) {
                classAList.get(0).notify();
            }
        }


        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Join error in: " + thread.getName());
            }
        }
        for (Integer key : statistic.keySet()
        ) {
            System.out.println("Thread " + key + " ,value -> " + statistic.get(key));
        }

        //System.out.println("Max Value: " + Collections.max(statistic.values()));
        //System.out.println("Min Value: " + Collections.min(statistic.values()));
        //System.out.println("Avg Value: " + statistic.values().stream().mapToDouble(Integer::doubleValue).average());
        System.out.println("Ending main...");
    }
}
