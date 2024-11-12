import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    final private int id; // Унікальний ідентифікатор філософа
    final private Semaphore leftFork; // Семафор для лівої виделки
    final private Semaphore rightFork; // Семафор для правої виделки
    final private Random random; // Генератор випадкових чисел для імітації часу
    final private int time; // Максимальний час, протягом якого філософ думає або їсть

    // Конструктор для ініціалізації філософа з ідентифікатором та виделками
    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.random = new Random();
        time = 100; // Встановлюємо максимальний час для кожної дії
    }

    // Метод run(), який виконується при запуску потоку
    public void run() {
        try {
            // Цикл для кількості прийомів їжі, які філософ має здійснити
            for (int i = 1; i <= Main.getNumMeals(); i++) {
                think(); // Філософ думає
                pickUpLeftFork(); // Піднімає ліву виделку
                pickUpRightFork(); // Піднімає праву виделку
                eat(); // Філософ їсть
                putDownRightFork(); // Кладе праву виделку
                putDownLeftFork(); // Кладе ліву виделку
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // Обробка переривання потоку
        }
    }

    // Метод для імітації розмірковування філософа
    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep(random.nextInt(time)); // Призупинення потоку на випадковий час
    }

    // Метод для підняття лівої виделки
    private void pickUpLeftFork() throws InterruptedException {
        leftFork.acquire(); // Захоплення лівої виделки
        System.out.println("Philosopher " + id + " is picking up left fork");
    }

    // Метод для підняття правої виделки
    private void pickUpRightFork() throws InterruptedException {
        rightFork.acquire(); // Захоплення правої виделки
        System.out.println("Philosopher " + id + " is picking up right fork");
    }

    // Метод для імітації їжі
    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep(random.nextInt(time)); // Призупинення потоку на випадковий час
    }

    // Метод для покладання правої виделки
    private void putDownRightFork() {
        System.out.println("Philosopher " + id + " is putting down right fork");
        rightFork.release(); // Вивільнення правої виделки
    }

    // Метод для покладання лівої виделки
    private void putDownLeftFork() {
        System.out.println("Philosopher " + id + " is putting down left fork");
        leftFork.release(); // Вивільнення лівої виделки
    }
}
