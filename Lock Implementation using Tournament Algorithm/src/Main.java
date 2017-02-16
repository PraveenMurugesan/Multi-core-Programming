


import java.util.Scanner;

/**
 *
 * @author Praveen
 */
public class Main {

     static int noOfThreads = 0;
     static int noOfExecutions = 0;
     static  int levels = 0;
     
     private static int phase = 0;
private static long startTime, endTime, elapsedTime;

/**
 * Timer to calculate the running time
 */
public static void timer() {
    if (phase == 0) {
        startTime = System.currentTimeMillis();
        phase = 1;
    } else {
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Time: " + elapsedTime + " msec.");
        memory();
        phase = 0;
    }
}

/**
 * This method determines the memory usage
 */
public static void memory() {
    long memAvailable = Runtime.getRuntime().totalMemory();
    long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
    System.out.println("Memory: " + memUsed / 1000000 + " MB / " + memAvailable / 1000000 + " MB.");
}
     
     
    public static void main(String args[]) throws InterruptedException {
        Scanner s = new Scanner(System.in);
        
        noOfThreads = Integer.parseInt(args[0]);
        
        noOfExecutions = Integer.parseInt(args[1]);
        
        levels = (int)Math.ceil(Math.log(noOfThreads)/Math.log(2));
        Mutex m;
        for(int choice=1;choice<=3;choice++)
        {
            if(choice == 1)
            {
                m = new Test_And_Set();
                System.out.println("Running Test and Set Algorithm");
            }
            else if(choice == 2)
            {
                m = new Test_Test_And_Set();
                System.out.println("Running Test Test and Set Algorithm");
            }
            else
            {  
                System.out.println("Running Tournament Algorithm");
                m = new Tournament();
            }

            Critical_Section cs = new Critical_Section(m);
            ThreadGenerator threads[] = new ThreadGenerator[noOfThreads];
            for(int i = 0;i<noOfThreads;i++)
            {
                threads[i] = new ThreadGenerator(cs,i);
            }
            timer();
            for(int i = 0;i<noOfThreads;i++)
            {
                threads[i].start();
            }
            for(int i = 0;i<noOfThreads;i++)
            {
                threads[i].join();
            }
            timer();
            System.out.println("Number of threads: "+noOfThreads);
            System.out.println("Number of requests "+noOfExecutions);
            System.out.println("Value of shared variable: "+cs.sharedVarible);
            System.out.println();
            System.out.println();
        }
    }
}
