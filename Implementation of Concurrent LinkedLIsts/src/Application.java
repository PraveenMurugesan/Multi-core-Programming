
import java.util.Random;

public class Application implements Runnable {

    ConcurrentLinkedListInterface cll;
    int val;
    int testFlavor;

    public Application(ConcurrentLinkedListInterface cll, int val, int testFlavor) {
        this.cll = cll;
        this.val = val;
        this.testFlavor = testFlavor;
    }

    public void run() {
        Random random = new Random();
       // int key = 10;
        //int searchKey = 1;

        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(1001);
            int key = random.nextInt(10);
            if (testFlavor == 1) {
                if (number >= 0 && number < 900) {
                    System.out.println("Searching "+key );
                    cll.search(key);
                    
                } else if (number >= 900 && number < 990) {
                    System.out.println("Inserting "+key);
                    cll.insert(key);
                    //key += 10;
                } else {
                    cll.delete(key);
                    System.out.println("Deleting "+key);
                    //key += 20;
                }
            } else if (testFlavor == 2) {
                if (number >= 0 && number < 700) {
                    cll.search(key);
                    //earchKey += 2;
                } else if (number >= 700 && number < 900) {
                     System.out.println("Inserting " + key);
                    cll.insert(key);
                    //key += 10;
                } else {
                     System.out.println("Deleting " + key);
                    cll.delete(key);
                    //key += 20;
                }
            } else if (testFlavor == 3) {
                if (number >= 0 && number < 500) {
                    System.out.println("Inserting " + key);
                    cll.insert(key);
                    //key += 10;
                } else {
                    System.out.println("Deleting " + key);
                    cll.delete(key);
                   // key += 10;
                }
            }
        }
    }
}
