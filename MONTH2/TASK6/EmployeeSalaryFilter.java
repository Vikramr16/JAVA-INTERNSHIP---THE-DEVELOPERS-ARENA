package month2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Employee {
    private String id;
    String name, dept;
    double salary;

    public Employee(String name, String dept, double salary) {
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public String toString() {
        return "Employee{name='" + name + "', dept='" + dept + "', salary=" + salary + "}";
    }
}

public class EmployeeSalaryFilter {
    public static void main(String[] args) {
        Employee e1 = new Employee("Arun", "Marketing", 25000);
        e1.setId("Ash123");
        Employee e2 = new Employee("Vinoth", "Consultant", 22000);
        e2.setId("Con121");

        List<Employee> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the salary starting range to filter: ");
        double start = sc.nextDouble();
        System.out.print("Enter the salary ending range to filter: ");
        double end = sc.nextDouble();

        List<Employee> filtered = list.stream()
                .filter(emp -> emp.getSalary() > start && emp.getSalary() < end)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No employees found in the given range.");
        } else {
            filtered.forEach(System.out::println);
        }

        sc.close();
    }
}
