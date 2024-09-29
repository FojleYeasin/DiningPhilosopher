import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Table sixthTable;
    private static List<Philosopher> sixthTablePhilosophers = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophersPerTable = 5;
        int numberOfTables = 6;
        Table[] tables = new Table[numberOfTables];
        char[] philosopherLabels = "ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();

        long simulationStartTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfTables; i++) {
            tables[i] = new Table(i, numberOfPhilosophersPerTable);
        }

        sixthTable = tables[5];


        int labelIndex = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < numberOfPhilosophersPerTable; j++) {
                Philosopher philosopher = new Philosopher(philosopherLabels[labelIndex], tables[i], i, j, simulationStartTime);
                tables[i].addPhilosopher(philosopher);
                philosopher.start();
                labelIndex++;
            }
        }


    }

    public static synchronized void movePhilosopherToSixthTable(Philosopher philosopher) {
        if (sixthTablePhilosophers.size() < 5) {
            sixthTablePhilosophers.add(philosopher);
            int philosopherIndex = sixthTablePhilosophers.size() - 1;
            philosopher.moveToTable(sixthTable, 5);

            System.out.println("Philosopher " + philosopher.getLabel() + " transferred from Table " + philosopher.getTableId() + " to Table 5");
        } else {

            System.out.println("Sixth table is full, deadlock has occurred at the sixth table.");

        }
    }
}
