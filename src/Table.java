import java.util.ArrayList;
import java.util.List;

class Table {
    private Fork[] forks;
    private List<Philosopher> philosophers;
    private boolean deadlockDetected;
    private int tableId;

    public Table(int tableId, int numberOfPhilosophers) {
        forks = new Fork[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork();
        }
        philosophers = new ArrayList<>();
        deadlockDetected = false;
        this.tableId = tableId;
    }

    public Fork getLeftFork(int philosopherIndex) {
        return forks[philosopherIndex];
    }

    public Fork getRightFork(int philosopherIndex) {
        return forks[(philosopherIndex + 1) % forks.length];
    }

    public synchronized void detectDeadlock(Philosopher philosopher) {
        int hungryPhilosophers = 0;
        for (Philosopher p : philosophers) {
            if (p.isHungry()) {
                hungryPhilosophers++;
            }
        }
        if (hungryPhilosophers == philosophers.size()) {
            System.out.println("Deadlock detected at Table " + tableId);
            deadlockDetected = true;
            // Philosopher moves to the sixth table when deadlock occurs
            if (tableId != 5) { // No moving from the sixth table
                Main.movePhilosopherToSixthTable(philosopher);
            }
        }
    }

    public synchronized void addPhilosopher(Philosopher philosopher) {
        philosophers.add(philosopher);
    }

    public synchronized boolean isDeadlocked() {
        return deadlockDetected;
    }
}
