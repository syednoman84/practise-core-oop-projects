import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee emp) {
        employees.add(emp);
        System.out.println("Employee added successfully.");
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to show.");
            return;
        }

        String format = "| %-5s | %-20s | %-10s | %-15s | %-20s | %-19s | %-19s |%n";
        String separator = "+-------+----------------------+------------+-----------------+----------------------+---------------------+---------------------+";

        System.out.println(separator);
        System.out.printf(format, "ID", "Name", "Salary", "Department", "Role", "Created At", "Updated At");
        System.out.println(separator);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Employee emp : employees) {
            System.out.printf(format,
                    emp.id,
                    emp.name,
                    String.format("%.2f", emp.salary),
                    emp.department.getName(),
                    emp.role.getTitle(),
                    emp.createdAt.format(formatter),
                    emp.updatedAt.format(formatter));
        }

        System.out.println(separator);
    }


    public void deleteEmployee(int id) {
        boolean removed = employees.removeIf(emp -> emp.getId() == id);
        if (removed) {
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void viewEmployeeBonuses() {
        if (employees.isEmpty()) {
            System.out.println("No employees to show.");
            return;
        }

        String format = "| %-5s | %-20s | %-10s | %-15s | %-20s | %-10s |%n";
        String separator = "+-------+----------------------+------------+-----------------+----------------------+------------+";

        System.out.println(separator);
        System.out.printf(format, "ID", "Name", "Salary", "Department", "Role", "Bonus");
        System.out.println(separator);

        for (Employee emp : employees) {
            System.out.printf(format,
                    emp.id,
                    emp.name,
                    String.format("%.2f", emp.salary),
                    emp.department.getName(),
                    emp.role.getTitle(),
                    String.format("%.2f", emp.calculateBonus()));
        }

        System.out.println(separator);
    }


    public void updateEmployee(int id, Scanner scanner) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                scanner.nextLine(); // consume newline

                System.out.print("Enter new name (current: " + emp.name + "): ");
                String newName = scanner.nextLine();

                System.out.print("Enter new salary (current: " + emp.salary + "): ");
                double newSalary = scanner.nextDouble();
                scanner.nextLine(); // consume newline

                System.out.print("Enter new department (current: " + emp.department + "): ");
                String newDept = scanner.nextLine();

                System.out.print("Enter new role (current: " + emp.role + "): ");
                String newRole = scanner.nextLine();

                // Update fields
                emp.name = newName;
                emp.salary = newSalary;
                emp.department = new Department(newDept);
                emp.role = new Role(newRole);
                emp.updatedAt = LocalDateTime.now();

                System.out.println("Employee updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public void showReportsMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Total Number of Employees");
            System.out.println("2. Total Employees by Role");
            System.out.println("3. Total Employees by Department");
            System.out.println("4. Average Salary per Department");
            System.out.println("5. Total Payroll (Sum of Salaries)");
            System.out.println("6. Total Bonuses by Department");
            System.out.println("7. Total Bonuses by Role");
            System.out.println("8. Back to Main Menu");
            System.out.print("Choose a report: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> totalEmployees();
                case 2 -> totalByRole();
                case 3 -> totalByDepartment();
                case 4 -> averageSalaryByDepartment();
                case 5 -> totalPayroll();
                case 6 -> totalBonusesByDepartment();
                case 7 -> totalBonusesByRole();
                case 8 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 8);
    }

    private void totalEmployees() {
        System.out.println("Total number of employees: " + employees.size());
    }

    private void totalByRole() {
        Map<String, Long> countByRole = employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.role.getTitle(), Collectors.counting()));

        System.out.println("Employees by Role:");
        countByRole.forEach((role, count) -> System.out.println(role + ": " + count));
    }

    private void totalByDepartment() {
        Map<String, Long> countByDept = employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.department.getName(), Collectors.counting()));

        System.out.println("Employees by Department:");
        countByDept.forEach((dept, count) -> System.out.println(dept + ": " + count));
    }

    private void averageSalaryByDepartment() {
        Map<String, Double> avgByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department.getName(),
                        Collectors.averagingDouble(emp -> emp.salary)
                ));

        System.out.println("Average Salary per Department:");
        avgByDept.forEach((dept, avg) -> System.out.println(dept + ": " + String.format("%.2f", avg)));
    }

    private void totalPayroll() {
        double total = employees.stream()
                .mapToDouble(emp -> emp.salary)
                .sum();
        System.out.println("Total Payroll: " + total);
    }

    private void totalBonusesByDepartment() {
        Map<String, Double> bonusByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department.getName(),
                        Collectors.summingDouble(emp -> emp.calculateBonus())
                ));

        System.out.println("\nTotal Bonuses by Department:");
        bonusByDept.forEach((dept, total) ->
                System.out.printf("• %-15s : %.2f%n", dept, total));
    }

    private void totalBonusesByRole() {
        Map<String, Double> bonusByRole = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.role.getTitle(),
                        Collectors.summingDouble(emp -> emp.calculateBonus())
                ));

        System.out.println("\nTotal Bonuses by Role:");
        bonusByRole.forEach((role, total) ->
                System.out.printf("• %-20s : %.2f%n", role, total));
    }




}

