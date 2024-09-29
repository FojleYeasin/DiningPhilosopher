import java.util.Random;

class Philosopher extends Thread {
    private char label;
    private Fork leftFork;
    private Fork rightFork;
    private Table table;
    private boolean hungry;
    private static Random random = new Random();
    private int tableId;
    private int philosopherIndex;
    private static long startTime;

    public Philosopher(char label, Table table, int tableId, int philosopherIndex, long startTime) {
        this.label = label;
        this.table = table;
        this.philosopherIndex = philosopherIndex;
        this.leftFork = table.getLeftFork(philosopherIndex);
        this.rightFork = table.getRightFork(philosopherIndex);
        this.hungry = false;
        this.tableId = tableId;
        Philosopher.startTime = startTime;
    }

    public boolean isHungry() {
        return hungry;
    }
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
                table.detectDeadlock(this);
                if (leftFork.pickUp(label, tableId)) {
                    Thread.sleep(1000);
                    table.detectDeadlock(this);
                    if (rightFork.pickUp(label, tableId)) {
                        eat();
                        hungry = false;
                        leftFork.putDown(label, tableId);
                        rightFork.putDown(label, tableId);
                    }

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
