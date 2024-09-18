import java.util.concurrent.Semaphore;
public class Philosopher extends Thread{
    private int id;
    private Fork leftFork;
    private Fork rightFork;
    private Semaphore table;

    public Philosopher(int id, Fork leftFork, Fork rightFork, Semaphore table) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.table = table;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    public void run() {
        try {
            while (true) {
                think();

                // Wait to enter the dining table
                table.acquire();

                // Pick up left fork
                leftFork.pickUp(id);
                // Pick up right fork
                rightFork.pickUp(id);

                eat();

                // Put down right fork
                rightFork.putDown(id);
                // Put down left fork
                leftFork.putDown(id);

                // Leave the dining table
                table.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
