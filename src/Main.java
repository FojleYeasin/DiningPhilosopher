import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Table sixthTable;
    private static List<Philosopher> sixthTablePhilosophers = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophersPerTable = 5;
        int numberOfTables = 5; // 5 tables with philosophers, 1 empty table with forks
        Table[] tables = new Table[numberOfTables];
        char[] philosopherLabels = "ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();

        // Record simulation start time
        long simulationStartTime = System.currentTimeMillis();

        // Initialize tables
        for (int i = 0; i < numberOfTables; i++) {
            tables[i] = new Table(i, numberOfPhilosophersPerTable);
        }

        // Assign the sixth table to the variable for later use
        sixthTable = tables[1]; // Sixth table is the last one (index 5)

        // Initialize philosophers at the first 5 tables
        int labelIndex = 0;
        for (int i = 0; i < 5; i++) { // Iterate over the 5 tables
            for (int j = 0; j < numberOfPhilosophersPerTable; j++) { // For each table, 5 philosophers
                Philosopher philosopher = new Philosopher(philosopherLabels[labelIndex], tables[i], i, j, simulationStartTime);
                tables[i].addPhilosopher(philosopher);
                philosopher.start();
                labelIndex++; // Move to the next philosopher label
            }
        }

        // Additional logic for deadlock detection...
    }

    // Method to move a philosopher to the sixth table when deadlock occurs
    public static synchronized void movePhilosopherToSixthTable(Philosopher philosopher) {
        // Check if the sixth table already has 5 philosophers
        if (sixthTablePhilosophers.size() < 5) {
            sixthTablePhilosophers.add(philosopher);
            int philosopherIndex = sixthTablePhilosophers.size() - 1; // Get the philosopher's new index at the sixth table
            philosopher.moveToTable(sixthTable, 5); // Move to sixth table (index 5)

            // Print message about philosopher transfer
            System.out.println("Philosopher " + philosopher.getLabel() + " transferred from Table " + philosopher.getTableId() + " to Table 5");
        } else {
            // Print message when deadlock happens on the sixth table
            System.out.println("Sixth table is full, deadlock has occurred at the sixth table.");
            // Optionally, stop the simulation or handle further deadlock detection
        }
    }
}
