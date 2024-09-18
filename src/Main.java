import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int numPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        Fork[] forks = new Fork[numPhilosophers];

        // Create forks
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork();
        }

        // Semaphore to limit the number of philosophers eating at the same time
        Semaphore table = new Semaphore(numPhilosophers - 1);

        // Create philosophers and assign them forks
        for (int i = 0; i < numPhilosophers; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % numPhilosophers];

            philosophers[i] = new Philosopher(i, leftFork, rightFork, table);
            philosophers[i].start();
        }
    }
}