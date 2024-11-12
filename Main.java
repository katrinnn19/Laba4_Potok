import java.util.concurrent.Semaphore;

public class Main {
    private static final int numPhilosopher = 5; // к-ть філософів та виделок
    private static final int NUM_MEALS = 10; // к-ть прийомів їжі

    // Метод для отримання зн-ня NUM_MEALS
    public static int getNumMeals() {
        return NUM_MEALS;
    }

    // Масив семафоров, представляющих вилки
    private static final Semaphore[] forks = new Semaphore[numPhilosopher];

    public static void main(String[] args) {
        // ініці-я семафоров, один семафор = одна виделка
        for (int i = 0; i < numPhilosopher; i++) {
            forks[i] = new Semaphore(1); // кожна виделка може бути зайнята тільки одним філ-м
        }

        // Масив потоков,1 потік = 1 філософ
        Thread[] philosophers = new Thread[numPhilosopher];
        
        // Ств-ня і запуск потоків філософів (окрім останнього)
        for (int i = 0; i < numPhilosopher - 1; i++) {
            philosophers[i] = new Thread(new Philosopher(i, forks[i], forks[(i + 1) % numPhilosopher]));
            philosophers[i].start();
        }

        // Спеціальна обробка для останньго філософа, щоб не було deadlock
        philosophers[numPhilosopher - 1] = new Thread(new Philosopher(
                numPhilosopher - 1, forks[numPhilosopher % numPhilosopher], forks[numPhilosopher - 1]));
        philosophers[numPhilosopher - 1].start();

        // Очікування завершення всіх потоків філософів
        try {
            for (int i = 0; i < numPhilosopher; i++) {
                philosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // Обрабка переривання роботи потоков
        }
    }
}
