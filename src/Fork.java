import java.util.concurrent.Semaphore;
class Fork {
    private Semaphore semaphore = new Semaphore(1);

    public boolean pickUp(char philosopherLabel) {
        boolean pickedUp = semaphore.tryAcquire();
        if (pickedUp) {
            System.out.println("Philosopher " + philosopherLabel + " picked up the fork.");
        }
        return pickedUp;
    }

    public void putDown(char philosopherLabel) {
        semaphore.release();
        System.out.println("Philosopher " + philosopherLabel + " put down the fork.");
    }
}
