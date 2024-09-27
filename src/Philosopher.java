import java.util.Random;

class Philosopher extends Thread {
    private char label; // Label of philosopher (A, B, C, ...)
    private Fork leftFork;
    private Fork rightFork;
    private Table table;
    private boolean hungry;
    private static Random random = new Random();
    private int tableId; // Philosopher's current table
    private int philosopherIndex; // Philosopher index within the table (0 to 4)
    private static long startTime;

    public Philosopher(char label, Table table, int tableId, int philosopherIndex, long startTime) {
        this.label = label;
        this.table = table;
        this.philosopherIndex = philosopherIndex; // Index relative to the table
        this.leftFork = table.getLeftFork(philosopherIndex);
        this.rightFork = table.getRightFork(philosopherIndex);
        this.hungry = false;
        this.tableId = tableId;
        Philosopher.startTime = startTime; // Record start time of the simulation
    }

    // Method to check if the philosopher is hungry
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
                if (leftFork.pickUp(label, tableId)) {  // Pass tableId as well
                    Thread.sleep(4000); // wait for 4 seconds before trying the right fork
                    // Philosopher tries to pick up the right fork
                    if (rightFork.pickUp(label, tableId)) {  // Pass tableId as well
                        eat();
                        // Philosopher puts down the right fork after eating
                        rightFork.putDown(label, tableId);  // Pass tableId as well
                    }
                    // Philosopher puts down the left fork if unsuccessful or after eating
                    leftFork.putDown(label, tableId);  // Pass tableId as well
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

    public int getTableId() {
        return tableId;
    }

    public void moveToTable(Table newTable, int newTableId) {
        this.table = newTable;
        this.leftFork = newTable.getLeftFork(philosopherIndex);
        this.rightFork = newTable.getRightFork(philosopherIndex);
        this.tableId = newTableId;
        System.out.println("Philosopher " + label + " moved to Table " + newTableId);
    }
}
