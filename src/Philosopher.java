import java.util.Random;

class Philosopher extends Thread {
    private char label; // Label of philosopher (A, B, C, ...)
    private Fork leftFork;
    private Fork rightFork;
    private Table table;
    private boolean hungry;
    private static Random random = new Random(); // Initialize Random
    private int tableId; // Philosopher's current table
    private int philosopherIndex; // Philosopher index within the table (0 to 4)
    private static long startTime;

    public Philosopher(char label, Table table, int tableId, int philosopherIndex, long startTime) {
        this.label = label;
        this.table = table;
        this.philosopherIndex = philosopherIndex; // Index relative to the table
        this.leftFork = table.getLeftFork(philosopherIndex);  // Left fork is at the philosopher's index
        this.rightFork = table.getRightFork(philosopherIndex); // Right fork is at the next index
        this.hungry = false;
        this.tableId = tableId;
        Philosopher.startTime = startTime; // Record start time of the simulation
    }

    // Method to check if the philosopher is hungry
    public boolean isHungry() {
        return hungry;
    }

    // Method to return the current table ID of the philosopher
    public int getTableId() {
        return this.tableId;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + label + " is thinking at Table " + tableId);
        Thread.sleep(random.nextInt(10000)); // Random thinking time
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + label + " is eating at Table " + tableId);
        Thread.sleep(random.nextInt(5000)); // Random eating time
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                hungry = true;
                System.out.println("Philosopher " + label + " is hungry at Table " + tableId);
                table.detectDeadlock(this); // Detect deadlock after each cycle
                // Philosopher tries to pick up the left fork
                if (leftFork.pickUp(label, tableId)) {
                    Thread.sleep(1000); // wait before trying the right fork

                    // Philosopher tries to pick up the right fork
                    if (rightFork.pickUp(label, tableId)) {
                        eat(); // If both forks are picked up, the philosopher eats
                        hungry = false;
                        leftFork.putDown(label, tableId);
                        rightFork.putDown(label, tableId);
                    }

                    // Philosopher puts down the left fork after attempting to pick the right fork

                }



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
        this.leftFork = newTable.getLeftFork(philosopherIndex);
        this.rightFork = newTable.getRightFork(philosopherIndex);
        this.tableId = newTableId;
        System.out.println("Philosopher " + label + " moved to Table " + newTableId);
    }
}
