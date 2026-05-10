import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Zad43 {

    /**
     * Модель дома с фиксированным количеством этажей.
     */
    public static class House {

        private final int floors;

        /**
         * Создаёт дом с указанным количеством этажей.
         *
         * @param floors количество этажей (должно быть больше 0)
         * @throws IllegalArgumentException если floors <= 0
         */
        public House(int floors) {
            if (floors <= 0) {
                throw new IllegalArgumentException("Количество этажей должно быть больше 0");
            }
            this.floors = floors;
        }

        /**
         * Возвращает текстовое описание дома с правильным грамматическим склонением.
         *
         * @return строка вида "Дом с X этажом/этажами"
         */
        public String getDescription() {
            int mod10 = floors % 10;
            int mod100 = floors % 100;
            String suffix = (mod10 == 1 && mod100 != 11) ? "этажом" : "этажами";
            return String.format("Дом с %d %s", floors, suffix);
        }
    }

    /**
     * Запускает консольный интерфейс для создания и управления домами.
     */
    public void run() {
        try (var scanner = new Scanner(System.in)) {
            var houses = new ArrayList<House>();

            System.out.println("Доступные команды:");
            System.out.println("  ДОМ <количество этажей>");
            System.out.println("  СПИСОК");
            System.out.println("  СТОП\n");

            while (true) {
                var line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                var cmd = line.split("\\s+");

                switch (cmd[0].toUpperCase()) {
                    case "СТОП", "ВЫХОД" -> {
                        return;
                    }
                    case "СПИСОК" -> {
                        printHouseList(houses);
                    }
                    case "ДОМ" -> {
                        handleCreateHouse(cmd, houses);
                    }
                    default -> System.out.println("Неизвестная команда.");
                }
            }
        }
    }

    /**
     * Валидирует ввод и создаёт экземпляр дома.
     */
    private void handleCreateHouse(String[] cmd, List<House> houses) {
        if (cmd.length != 2) {
            System.out.println("Ошибка: формат команды 'ДОМ <число>'.");
            return;
        }

        int floors;
        try {
            floors = Integer.parseInt(cmd[1]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное целое число.");
            return;
        }


        var house = new House(floors);
        houses.add(house);
        System.out.println("Дом создан: " + house.getDescription());
    }

    /**
     * Выводит список всех созданных домов.
     */
    private void printHouseList(List<House> houses) {
        System.out.println("\nСозданные дома:");
        if (houses.isEmpty()) {
            System.out.println("список пуст");
        } else {
            houses.forEach(h -> System.out.println("  • " + h.getDescription()));
        }
        System.out.println();
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    static void main(String[] args) {
        new Zad43().run();
    }
}