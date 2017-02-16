

class ThreadDemo extends Thread {
    
    public ThreadDemo(String name) {
        super(name);
    }
    
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello" + this.getName());
        }
    }
}

public class Learning {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo("Thread1");
        ThreadDemo td1 = new ThreadDemo("Thread2");
        td.start();
        td1.start();
    }
}
