import java.util.concurrent.Semaphore;
public class Fork {
    private int id; // Fork's identifier
    private boolean isHeld;

    public Fork(int id) {  // Constructor to accept the fork's ID
        this.id = id;
        this.isHeld = false;
    }

    public synchronized boolean pickUp(char philosopherLabel) {
        if (!isHeld) {
            isHeld = true;
            System.out.println("Philosopher " + philosopherLabel + " picked up fork " + id);
            return true;
        }
        return false;
    }

    public synchronized void putDown(char philosopherLabel) {
        isHeld = false;
        System.out.println("Philosopher " + philosopherLabel + " put down fork " + id);
    }

    public int getId() {
        return id;
    }
}
