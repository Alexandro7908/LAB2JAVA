import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Демонстрационный класс для управления иерархией сотрудников.
 * Реализует ввод данных в одной строке и вывод итогового списка.
 */
public class Zad24 {

    /**
     * Модель сотрудника, поддерживающая указание руководителя.
     */
    public static class Employee {

        private final String name;
        private final boolean isChief;
        private final String department;
        private final Employee boss;

        /**
         * Создаёт сотрудника с явным указанием статуса руководителя.
         *
         * @param name имя сотрудника
         * @param isChief признак руководства
         * @param department название отдела
         */
        public Employee(String name, boolean isChief, String department) {
            this.name = name;
            this.isChief = isChief;
            this.department = department;
            this.boss = null;
        }

        /**
         * Создаёт рядового сотрудника с указанием руководителя.
         *
         * @param name имя сотрудника
         * @param boss руководитель сотрудника
         * @param department название отдела
         */
        public Employee(String name, Employee boss, String department) {
            this.name = name;
            this.isChief = false;
            this.department = department;
            this.boss = boss;
        }

        @Override
        public String toString() {
            if (!isChief) {
                return String.format("%s (подчинённый %s), отдел: %s", name, boss.getName(), department);
            }
            return String.format("%s, начальник отдела, отдел: %s", name, department);
        }

        /**
         * Возвращает имя сотрудника.
         *
         * @return имя сотрудника
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Запускает интерактивный режим ввода сотрудников в формате одной строки.
     */
    public void run() {
        try (var scanner = new Scanner(System.in)) {
            var staff = new ArrayList<Employee>();
            System.out.println("Формат ввода: Имя Руководитель Отдел");
            System.out.println("Для начальника вместо 'Руководитель' введите '-' или 'нет'. Для выхода: 'стоп'");

            while (true) {
                var input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("стоп") || input.equalsIgnoreCase("exit")) {
                    break;
                }
                if (input.isEmpty()) {
                    continue;
                }

                parseAndCreateEmployee(input, staff).ifPresent(staff::add);
            }

            printStaffSummary(staff);
        }
    }

    /**
     * Парсит строку ввода и создаёт экземпляр сотрудника.
     *
     * @param input строка ввода в формате "Имя Руководитель Отдел"
     * @param staff текущий список сотрудников для поиска руководителя
     * @return опциональный экземпляр сотрудника или пустой Optional при ошибке
     */
    private Optional<Employee> parseAndCreateEmployee(String input, List<Employee> staff) {
        var parts = input.split("\\s+");
        if (parts.length != 3) {
            System.out.println("Ошибка: требуется ровно 3 параметра, разделённых пробелом.");
            return Optional.empty();
        }

        var name = parts[0];
        var bossInput = parts[1];
        var department = parts[2];

        boolean isChief = bossInput.equals("-") || bossInput.equalsIgnoreCase("нет");
        Employee boss = null;

        if (!isChief) {
            boss = findBossByName(bossInput, staff);
            if (boss == null) {
                System.out.println("Ошибка: руководитель '" + bossInput + "' не найден в списке.");
                return Optional.empty();
            }
        }

        var employee = isChief
                ? new Employee(name, true, department)
                : new Employee(name, boss, department);
        return Optional.of(employee);
    }

    /**
     * Ищет сотрудника по имени в текущем списке.
     *
     * @param name имя для поиска
     * @param staff список сотрудников
     * @return найденный сотрудник или null
     */
    private Employee findBossByName(String name, List<Employee> staff) {
        for (var emp : staff) {
            if (emp.getName().equalsIgnoreCase(name)) {
                return emp;
            }
        }
        return null;
    }

    /**
     * Выводит итоговый список всех зарегистрированных сотрудников.
     *
     * @param staff список сотрудников
     */
    private void printStaffSummary(List<Employee> staff) {
        System.out.println("\nИтоговый список сотрудников:");
        if (staff.isEmpty()) {
            System.out.println("Сотрудники не были добавлены.");
            return;
        }
        staff.forEach(System.out::println);
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    static void main(String[] args) {
        new Zad24().run();
    }
}