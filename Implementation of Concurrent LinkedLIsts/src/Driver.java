
public class Driver {

    /**
     * Timer to calculate the running time
     */
    private static int phase = 0;
    private static long startTime, endTime, elapsedTime;

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

    public static void main(String[] args) throws InterruptedException {

        if (args.length < 2) {
            System.out.println("Please enter the input in proper format");
            System.out.println("format: <test-flavor> <number of threads>");
            System.out.println("<test-falvor> takes values 1, 2, 3");
            System.out.println("1: Read Dominated");
            System.out.println("2: Mixed");
            System.out.println("3: Write Dominated");
        }

        int testFlavor = Integer.parseInt(args[0]);
        int noOfThreads = Integer.parseInt(args[1]);

        int noOfOperations = 20;
        int val = 0;

        int choice = 1;
        while (choice <= 3) {
            Thread threads[] = new Thread[noOfThreads];
            ConcurrentLinkedListInterface llcg;
            if (choice == 1) {
                System.out.println("Coarse-Grained Locking");
                llcg = new ConcurrentLinkedListCG();
            } else if (choice == 2) {
                System.out.println("Fine-Grained Locking");
                llcg = new  ConcurrentLinkedListFG();
            } else {
                System.out.println("Lock-Free Algorithm");
                llcg = new ConcurrentLinkedListLockFree();
            }

            for (int i = 0; i < noOfThreads; i++) {
                threads[i] = new Thread(new Application(llcg, val, testFlavor));
                val += 10;
            }

            // start the timer
            timer();
            for (int i = 0; i < noOfThreads; i++) {
                threads[i].start();
            }

            for (int i = 0; i < noOfThreads; i++) {
                threads[i].join();
            }
            timer();
            llcg.printList();
            choice++;
            System.out.println();
            System.out.println();
        }
    }
}
