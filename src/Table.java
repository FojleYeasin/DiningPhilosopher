import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableId;
    private Fork[] forks;
    private List<Philosopher> philosophers;

    public Table(int tableId, int numberOfPhilosophersPerTable) {
        this.tableId = tableId;
        this.forks = new Fork[numberOfPhilosophersPerTable];
        this.philosophers = new ArrayList<>();

        // Initialize the forks for the table
        for (int i = 0; i < numberOfPhilosophersPerTable; i++) {
            forks[i] = new Fork(i);  // Each fork is shared between two philosophers
        }
    }

    // Method to add philosophers to the table
    public void addPhilosopher(Philosopher philosopher) {
        philosophers.add(philosopher);
    }

    // Get the left fork of a philosopher based on their index at the table
    public Fork getLeftFork(int philosopherIndex) {
        return forks[philosopherIndex];  // Left fork is at the philosopher's index
    }

    // Get the right fork of a philosopher based on their index at the table
    public Fork getRightFork(int philosopherIndex) {
        // Right fork is the next fork in the circular arrangement
        return forks[(philosopherIndex + 1) % forks.length];
    }

    // Method to detect deadlock
    // Method to detect deadlock
    public synchronized void detectDeadlock(Philosopher philosopher) {
        int hungryPhilosophers = 0;
        for (Philosopher p : philosophers) {
            if (p.isHungry()) {
                hungryPhilosophers++;
            }
        }

        // Deadlock occurs when all philosophers at the table are hungry
        if (hungryPhilosophers == philosophers.size()) {
            System.out.println("Deadlock detected at Table " + tableId);

            // Move the philosopher to the sixth table if deadlock occurs
            if (tableId != 5) { // Do not move philosophers from the sixth table
                Main.movePhilosopherToSixthTable(philosopher);
            }
        }
    }


    public int getTableId() {
        return tableId;
    }

    public List<Philosopher> getPhilosophers() {
        return philosophers;
    }
}
