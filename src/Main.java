import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Philosopher> sixthTablePhilosophers = new ArrayList<>();
    private static long simulationStartTime;
    private static boolean sixthTableDeadlock = false;
    private static Philosopher lastMovedPhilosopher;

    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophers = 5;
        int numberOfTables = 6; // 5 tables with philosophers, 1 empty table with forks
        Table[] tables = new Table[numberOfTables];
        char[] philosopherLabels = "ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();

        // Record simulation start time
        simulationStartTime = System.currentTimeMillis();

        // Initialize tables
        for (int i = 0; i < numberOfTables; i++) {
            tables[i] = new Table(i, numberOfPhilosophers);
        }

        // Initialize philosophers at 5 tables
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < numberOfPhilosophers; j++) {
                Philosopher philosopher = new Philosopher(philosopherLabels[i * numberOfPhilosophers + j], tables[i], i, simulationStartTime);
                tables[i].addPhilosopher(philosopher);
                philosopher.start();
            }
        }

        // Monitor for deadlock at the sixth table
        while (!sixthTableDeadlock) {
            Thread.sleep(1000); // Check every second
        }

        // Output the result
        long deadlockTime = (System.currentTimeMillis() - simulationStartTime) / 1000;
        System.out.println("Sixth table deadlocked after " + deadlockTime + " seconds.");
        System.out.println("The last philosopher to move to the sixth table was " + lastMovedPhilosopher.getLabel() + ".");
    }

    public static synchronized void movePhilosopherToSixthTable(Philosopher philosopher) {
        if (sixthTablePhilosophers.size() < 5) {
            sixthTablePhilosophers.add(philosopher);
            philosopher.moveToTable(new Table(5, 5), 5); // Move to the sixth table
            lastMovedPhilosopher = philosopher;

            if (sixthTablePhilosophers.size() == 5) {
                sixthTableDeadlock = true; // Set flag when the sixth table is full
            }
        }
    }
}
