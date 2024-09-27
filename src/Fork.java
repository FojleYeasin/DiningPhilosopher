public class Fork {
    private int id; // Fork's identifier
    private boolean isHeld;

    public Fork(int id) {
        this.id = id;
        this.isHeld = false;
    }

    public synchronized boolean pickUp(char philosopherLabel, int tableId) {
        if (!isHeld) {
            isHeld = true;
            System.out.println("Philosopher " + philosopherLabel + " picked up fork " + id + " from Table " + tableId);
            return true;
        }
        return false;
    }

    public synchronized void putDown(char philosopherLabel, int tableId) {
        isHeld = false;
        System.out.println("Philosopher " + philosopherLabel + " put down fork " + id + " from Table " + tableId);
    }

    public int getId() {
        return id;
    }
}
