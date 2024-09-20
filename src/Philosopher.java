import java.util.concurrent.Semaphore;
import java.util.Random;

class Philosopher extends Thread {
    private char label; // Label of philosopher (A, B, C, ...)
    private Fork leftFork;
    private Fork rightFork;
    private Table table;
    private boolean hungry;
    private static Random random = new Random();
    private int tableId; // Philosopher's current table
    private static long startTime;

    public Philosopher(char label, Table table, int tableId, long startTime) {
        this.label = label;
        this.table = table;
        this.leftFork = table.getLeftFork(label - 'A');
        this.rightFork = table.getRightFork(label - 'A');
        this.hungry = false;
        this.tableId = tableId;
        Philosopher.startTime = startTime; // Record start time of the simulation
    }

    public boolean isHungry() {
        return hungry;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + label + " is thinking.");
        Thread.sleep(random.nextInt(10000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + label + " is eating.");
        Thread.sleep(random.nextInt(5000));
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                hungry = true;
                // Philosopher tries to pick up the left fork
                if (leftFork.pickUp(label)) {
                    Thread.sleep(4000); // wait for 4 seconds before trying the right fork
                    // Philosopher tries to pick up the right fork
                    if (rightFork.pickUp(label)) {
                        eat();
                        // Philosopher puts down the right fork after eating
                        rightFork.putDown(label);
                    }
                    // Philosopher puts down the left fork if unsuccessful or after eating
                    leftFork.putDown(label);
                }
                hungry = false;
                table.detectDeadlock(this); // Detect deadlock after each cycle
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public char getLabel() {
        return label;
    }

    public void moveToTable(Table newTable, int newTableId) {
        this.table = newTable;
        this.leftFork = newTable.getLeftFork(label - 'A');
        this.rightFork = newTable.getRightFork(label - 'A');
        this.tableId = newTableId;
        System.out.println("Philosopher " + label + " moved to Table " + newTableId);
    }
}
