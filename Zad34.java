import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Zad34 {

    /**
     * Модель отдела, агрегирующая список сотрудников.
     */
    public static class Department {

        private final String name;
        private final List<Employee> employees = new ArrayList<>();

        /**
         * Создаёт отдел с заданным названием.
         *
         * @param name название отдела
         */
        public Department(String name) {
            this.name = name;
        }

        /**
         * Регистрирует сотрудника в отделе.
         *
         * @param employee сотрудник для добавления
         */
        public void add(Employee employee) {
            employees.add(employee);
        }

        /**
         * Возвращает защищённую от изменения копию списка сотрудников.
         *
         * @return неизменяемый список сотрудников
         */
        public List<Employee> getEmployees() {
            return Collections.unmodifiableList(employees);
        }

        /**
         * Возвращает название отдела.
         *
         * @return название отдела
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Модель сотрудника с поддержкой ссылки на отдел и руководителя.
     */
    public static class Employee {

        private final String name;
        private final boolean isChief;
        private final Department department;
        private final Employee boss;

        /**
         * Создаёт сотрудника с явным указанием статуса руководителя.
         *
         * @param name      имя сотрудника
         * @param isChief   признак руководства
         * @param department отдел, к которому привязывается сотрудник
         */
        public Employee(String name, boolean isChief, Department department) {
            this.name = name;
            this.isChief = isChief;
            this.department = department;
            this.boss = null;
            department.add(this);
        }

        /**
         * Создаёт рядового сотрудника с указанием руководителя.
         *
         * @param name      имя сотрудника
         * @param boss      руководитель сотрудника
         * @param department отдел, к которому привязывается сотрудник
         */
        public Employee(String name, Employee boss, Department department) {
            this.name = name;
            this.isChief = false;
            this.boss = boss;
            this.department = department;
            department.add(this);
        }

        /**
         * Возвращает список всех коллег сотрудника в том же отделе.
         * Алгоритм: берёт копию списка отдела и исключает текущего сотрудника.
         *
         * @return список коллег (может быть пустым)
         */
        public List<Employee> getColleagues() {
            var colleagues = new ArrayList<>(department.getEmployees());
            colleagues.remove(this);
            return colleagues;
        }

        @Override
        public String toString() {
            if (isChief) {
                return "%s, начальник, отдел: %s".formatted(name, department.getName());
            }
            return "%s (босс: %s), отдел: %s".formatted(name, boss.getName(), department.getName());
        }

        /**
         * Возвращает имя сотрудника.
         *
         * @return имя
         */
        public String getName() {
            return name;
        }

        /**
         * Возвращает отдел сотрудника.
         *
         * @return отдел
         */
        public Department getDepartment() {
            return department;
        }
    }

    /**
     * Запускает консольный интерфейс с валидацией команд.
     */
    public void run() {
        try (var scanner = new Scanner(System.in)) {
            var depts = new HashMap<String, Department>();
            var staff = new ArrayList<Employee>();

            System.out.println("Доступные команды:");
            System.out.println("  ОТДЕЛ <Название>");
            System.out.println("  СОТРУДНИК <Имя> <Босс/-> <Отдел>");
            System.out.println("  КОЛЛЕГИ <Имя_сотрудника>");
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
                    case "ОТДЕЛ" -> handleAddDepartment(cmd, depts);
                    case "СОТРУДНИК" -> handleAddEmployee(cmd, depts, staff);
                    case "КОЛЛЕГИ" -> handleGetColleagues(cmd, staff);
                    default -> System.out.println("Неизвестная команда. Проверьте синтаксис.");
                }
            }
        }
    }

    /**
     * Обрабатывает команду создания отдела.
     */
    private void handleAddDepartment(String[] cmd, Map<String, Department> depts) {
        if (cmd.length != 2) {
            System.out.println("Ошибка: формат 'ОТДЕЛ <Уникальное название>'.");
            return;
        }
        if (depts.containsKey(cmd[1])) {
            System.out.println("Ошибка: отдел уже существует.");
            return;
        }
        depts.put(cmd[1], new Department(cmd[1]));
        System.out.println("Отдел создан.");
    }

    /**
     * Обрабатывает команду создания сотрудника с проверкой существования отдела и босса.
     */
    private void handleAddEmployee(String[] cmd, Map<String, Department> depts, List<Employee> staff) {
        if (cmd.length != 4) {
            System.out.println("Ошибка: формат 'СОТРУДНИК <Имя> <Босс/-> <Отдел>'.");
            return;
        }

        var dept = depts.get(cmd[3]);
        if (dept == null) {
            System.out.println("Ошибка: отдел не найден. Создайте его командой 'ОТДЕЛ'.");
            return;
        }

        boolean isChief = cmd[2].equalsIgnoreCase("-") || cmd[2].equalsIgnoreCase("нет");
        var bossOpt = isChief ? Optional.<Employee>empty() : findByName(staff, cmd[2]);

        if (!isChief && bossOpt.isEmpty()) {
            System.out.println("Ошибка: указанный босс не найден в списке.");
            return;
        }

        staff.add(isChief
                ? new Employee(cmd[1], true, dept)
                : new Employee(cmd[1], bossOpt.get(), dept));
        System.out.println("Сотрудник добавлен.");
    }

    /**
     * Ищет сотрудника по имени и выводит его коллег по отделу.
     */
    private void handleGetColleagues(String[] cmd, List<Employee> staff) {
        if (cmd.length != 2) {
            System.out.println("Ошибка: формат 'КОЛЛЕГИ <Имя_сотрудника>'.");
            return;
        }

        var empOpt = findByName(staff, cmd[1]);
        if (empOpt.isEmpty()) {
            System.out.println("Ошибка: сотрудник не найден.");
            return;
        }

        var emp = empOpt.get();
        var colleagues = emp.getColleagues();
        var deptName = emp.getDepartment().getName();

        System.out.println("Коллеги %s (отдел %s):".formatted(emp.getName(), deptName));
        if (colleagues.isEmpty()) {
            System.out.println("  (в отделе нет других сотрудников)");
        } else {
            colleagues.forEach(c -> System.out.println("  • " + c.getName()));
        }
    }

    /**
     * Выполняет регистронезависимый поиск сотрудника в списке.
     *
     * @param list список для поиска
     * @param name искомое имя
     * @return Optional с найденным сотрудником или пустой Optional
     */
    private Optional<Employee> findByName(List<Employee> list, String name) {
        return list.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    static void main(String[] args) {
        new Zad34().run();
    }
}