import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class lab2 {


    public class zad1 {

        public class zad14 {
            public void refactor_time(int seconds) {
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
                refactor_time(10);
                refactor_time(10000);
                refactor_time(100000);
            }
        }
        public class zad15 {
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
                zad15 house1 = new zad15();
                house1.House(1);
                zad15 house2 = new zad15();
                house2.House(5);
                zad15 house3 = new zad15();
                house3.House(23);
                System.out.println(house1.getDescription());
                System.out.println(house2.getDescription());
                System.out.println(house3.getDescription());
            }
        }
    }
    public class zad2 {
        public class zad24 {

            public class employee {
                private String name;
                private boolean chief;
                private String otdel;
                private employee boss;

                public employee(String name, boolean chief, String otdel) {
                    this.name = name;
                    this.chief = chief;
                    this.otdel = otdel;
                }

                public employee(String name, employee boss, String otdel) {
                    this.name = name;
                    this.chief = false;
                    this.otdel = otdel;
                    this.boss = boss;
                }

                public String toString() {
                    if (chief == false) {
                        return name + " " + boss.name + ", отдел: " + otdel;
                    } else {
                        return name + ", начальник отдела, отдел: " + otdel;
                    }
                }
            }

            public void run() {
                employee boss = new employee("Петров", true, "IT");
                employee worker = new employee("Казаков", boss, "IT");
                employee worker1 = new employee("Сидоров", boss, "IT");
                System.out.println(boss);
                System.out.println(worker);
                System.out.println(worker1);
            }
        }
    }
    public class zad3 {
        public class zad34 {
            public class Department{
                private String name;
                private List<employee> employees = new ArrayList<>();

                public Department(String name){
                    this.name = name;
                }
                void addemployee(employee emp){
                    employees.add(emp);
                }
                public List<employee> getEmployees(){
                    return new ArrayList<>(employees);
                }
                public String getName(){
                    return name;
                }
            }

            public class employee {
                private String name;
                private Department department;
                private boolean chief;
                private employee boss;

                public employee(String name, boolean chief, Department department) {
                    this.name = name;
                    this.chief = chief;
                    this.department = department;
                    department.addemployee(this);
                }

                public employee(String name, employee boss, Department department) {
                    this.name = name;
                    this.chief = false;
                    this.boss = boss;
                    this.department = department;
                    department.addemployee(this);
                }
                public List<employee> getCollegues(){
                    List<employee> collegues = new ArrayList<>(department.getEmployees());
                    collegues.remove(this);
                    return collegues;
                }

                public String toString() {
                    if (chief == false) {
                        return name + " " + boss.name + ", отдел: " + department.getName();
                    } else {
                        return name + ", начальник отдела, отдел: " + department.getName();
                    }
                }
            }

            public void run() {
                Department it = new Department("IT");
                employee boss = new employee("Петров", true, it);
                employee worker = new employee("Казаков", boss, it);
                employee worker1 = new employee("Сидоров", boss, it);
                System.out.println("Коллеги Казакова:");
                for (employee emp: worker.getCollegues()){
                    System.out.println(emp);
                }
            }
        }
    }
    public class zad4{
        public class zad43 {
            private final int floors;
            public zad43(int floors){
                this.floors = floors;
            }
            public String getDescription(){
                int lastTwo = floors % 100;
                if (lastTwo % 10 == 1 && lastTwo != 11) {
                    return "Дом с " + floors + " этажом";
                } else {
                    return "Дом с " + floors + " этажами";
                }
            }
            public void run(){
                zad43 house1 = new zad43(2);
                zad43 house2 = new zad43(35);
                zad43 house3 = new zad43(91);
                System.out.println(house1.getDescription());
                System.out.println(house2.getDescription());
                System.out.println(house3.getDescription());
            }
        }
    }
    public class zad5{
        public class zad51{
            public class handgun{
                private int magcapacity;
                public handgun(int magcapacity){
                    this.magcapacity = magcapacity;
                }
                public handgun(){
                    this(5);
                }
                public String fire(){
                    if (magcapacity == 0){
                        return "Клац";
                    }
                    else{
                        magcapacity--;
                        return "Бах";
                    }
                }
                public String toString(){
                    return "Патронов: " + magcapacity;
                }

            }
            public void run(){
                handgun handgun = new handgun();
                System.out.println("Команды: f - стрелять, q - выйти");
                while (true){
                    java.util.Scanner sc = new Scanner(System.in);
                    String action = sc.nextLine();
                    if (action.equals("f")){
                        System.out.println(handgun.fire());
                    }
                    else if (action.equals("q")) {
                        System.out.println("Выход");
                        break;
                    }
                    else{
                        System.out.println("Неизвестная команда");
                    }
                }
            }
        }
    }

    void main(String[] args) {
        lab2 lab = new lab2();
//        lab.new zad1().new zad14().run();
//        lab.new zad1().new zad15().run();
//        lab.new zad1().run();
//        lab.new zad2().new zad24().run();
//        lab.new zad3().new zad34().run();
//        lab.new zad4().new zad43(0).run();
//        lab.new zad5().new zad51().run();
    }
}
