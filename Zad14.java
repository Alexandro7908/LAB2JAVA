import java.util.Scanner;
public class Zad14{
    public static void refactorTime(int seconds) {
        int hours = seconds / 3600;
        seconds %= 3600;
        int minutes = seconds / 60;
        seconds %= 60;

        if (hours >= 24) {
            hours %= 24;
        }

        System.out.println(hours + ":" + minutes + ":" + seconds);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Введите кол-во секунд: ");

            if (!sc.hasNextInt()) {
                System.out.println("Ошибка: введите корректное целое число!");
                sc.next();
                continue;
            }

            int seconds = sc.nextInt();

            if (seconds < 0) {
                System.out.println("Ошибка: количество секунд не может быть отрицательным!");
                continue;
            }

            refactorTime(seconds);
            break;
        }
    }
    static void main() {
        Zad14 obj = new Zad14();
        obj.run();
    }
        }


