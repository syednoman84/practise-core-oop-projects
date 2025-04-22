import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Employee {
    protected int id;
    protected String name;
    protected double salary;
    protected Department department;
    protected Role role;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;


    public Employee(int id, String name, double salary, Department department, Role role) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public int getId() {
        return id;
    }

    public abstract double calculateBonus();

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "ID: " + id +
                ", Name: " + name +
                ", Salary: " + salary +
                ", Department: " + department +
                ", Role: " + role +
                ", Created At: " + createdAt.format(formatter) +
                ", Updated At: " + updatedAt.format(formatter);
    }
}
