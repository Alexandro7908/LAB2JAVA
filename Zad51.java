import java.util.Scanner;

public class Zad51 {

    /**
     * Модель пистолета с изменяемым количеством патронов.
     */
    public static class Handgun {

        private int ammo;

        /**
         * Создаёт пистолет с указанным количеством патронов.
         *
         * @param ammo начальное количество патронов (≥ 0)
         * @throws IllegalArgumentException если ammo < 0
         */
        public Handgun(int ammo) {
            if (ammo < 0) {
                throw new IllegalArgumentException("Количество патронов не может быть отрицательным");
            }
            this.ammo = ammo;
        }

        /**
         * Создаёт пистолет с количеством патронов по умолчанию (5).
         */
        public Handgun() {
            this(5);
        }

        /**
         * Выполняет выстрел. Уменьшает количество патронов на 1, если они есть.
         *
         * @return "Бах!" при успешном выстреле, "Клац!" если магазин пуст
         */
        public String fire() {
            if (ammo > 0) {
                ammo--;
                return "Бах!";
            }
            return "Клац!";
        }

        @Override
        public String toString() {
            return String.format("Патронов в магазине: %d", ammo);
        }
    }

    /**
     * Запускает консольный интерфейс для управления пистолетом.
     */
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            Handgun currentGun = null;

            System.out.println("Команды: СОЗДАТЬ [число] | ОГОНЬ | СТОП");
            while (true) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] cmd = line.split("\\s+");
                switch (cmd[0].toUpperCase()) {
                    case "СТОП", "ВЫХОД" -> {
                        return;
                    }
                    case "СОЗДАТЬ" -> {
                        currentGun = createGun(cmd);
                    }
                    case "ОГОНЬ" -> {
                        fireGun(currentGun);
                    }
                    default -> System.out.println("Неизвестная команда.");
                }
            }
        }
    }

    /**
     * Валидирует ввод и создаёт экземпляр пистолета.
     *
     * @param cmd массив токенов команды
     * @return новый экземпляр Handgun или null при ошибке валидации
     */
    private Handgun createGun(String[] cmd) {
        if (cmd.length != 2) {
            System.out.println("Создан пистолет с 5 патронами (по умолчанию).");
            return new Handgun();
        }

        int ammo;
        try {
            ammo = Integer.parseInt(cmd[1]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное целое число.");
            return null;
        }

        try {
            Handgun gun = new Handgun(ammo);
            System.out.println("Пистолет создан. " + gun);
            return gun;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Выполняет выстрел с проверкой инициализации.
     *
     * @param gun ссылка на пистолет
     */
    private void fireGun(Handgun gun) {
        if (gun == null) {
            System.out.println("Сначала создайте пистолет командой 'СОЗДАТЬ'.");
            return;
        }
        System.out.println(gun.fire());
        System.out.println(gun);
    }


    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        new Zad51().run();
    }
}