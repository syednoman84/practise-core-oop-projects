import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. View Bonuses");
            System.out.println("6. View Reports");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Department: ");
                    String deptName = scanner.nextLine();
                    Department department = new Department(deptName);
                    System.out.print("Enter Role: ");
                    String roleTitle = scanner.nextLine();
                    Role role = new Role(roleTitle);
                    System.out.print("1 for Full-Time, 2 for Part-Time: ");
                    int type = scanner.nextInt();

                    if (type == 1) {
                        manager.addEmployee(new FullTimeEmployee(id, name, salary, department, role));
                    } else if (type == 2) {
                        manager.addEmployee(new PartTimeEmployee(id, name, salary, department, role));
                    } else {
                        System.out.println("Invalid type.");
                    }
                }
                case 2 -> manager.viewEmployees();
                case 3 -> {
                    System.out.print("Enter ID to delete: ");
                    int id = scanner.nextInt();
                    manager.deleteEmployee(id);
                }
                case 4 -> {
                    System.out.print("Enter ID to update: ");
                    int id = scanner.nextInt();
                    manager.updateEmployee(id, scanner);
                }
                case 5 -> manager.viewEmployeeBonuses();
                case 6 -> manager.showReportsMenu(scanner);
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        scanner.close();
    }
}
