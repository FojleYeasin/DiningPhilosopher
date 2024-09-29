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

    public void addPhilosopher(Philosopher philosopher) {
        philosophers.add(philosopher);
    }

    public Fork getLeftFork(int philosopherIndex) {
        return forks[philosopherIndex];
    }

    public Fork getRightFork(int philosopherIndex) {
        // Right fork is the next fork in the circular arrangement
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

            if (tableId != 5) {
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
