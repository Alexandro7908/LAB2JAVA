import java.util.Scanner;


public class Zad15 {
    int floors;

    public void House(int floors) {
        this.floors = floors;
    }

    public String getDescription() {
        int lastTwo = floors % 100;
        if (lastTwo % 10 == 1 && lastTwo != 11) {
            return "Дом с " + floors + " этажом";
        } else {
            return "Дом с " + floors + " этажами";
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        Zad15 house1 = new Zad15();
        while (true) {
            System.out.print("Введите кол-во этажей: ");

            if (!sc.hasNextInt()) {
                System.out.println("Введите целое число!");
                sc.next();
                continue;
            }

            floors = sc.nextInt();

            if (floors <= 0) {
                System.out.println("Ошибка: количество этажей не может быть отрицательным и равным 0!");
                continue;
            }

            house1.House(floors);
            System.out.println(house1.getDescription());
            break;
        }
    }
    static void main() {
        Zad15 obj = new Zad15();
        obj.run();
    }
}

