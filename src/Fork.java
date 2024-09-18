import java.util.concurrent.Semaphore;
public class Fork {
    private Semaphore semaphore = new Semaphore(1);

    public void pickUp(int philosopherId) throws InterruptedException {
        semaphore.acquire();
        System.out.println("Philosopher " + philosopherId + " picked up a fork.");
    }

    public void putDown(int philosopherId) {
        semaphore.release();
        System.out.println("Philosopher " + philosopherId + " put down a fork.");
    }
}
