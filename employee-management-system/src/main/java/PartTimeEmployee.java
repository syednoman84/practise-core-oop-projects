public class PartTimeEmployee extends Employee {
    public PartTimeEmployee(int id, String name, double salary, Department department, Role role) {
        super(id, name, salary, department, role);
    }

    @Override
    public double calculateBonus() {
        return salary * 0.05;
    }

    @Override
    public String toString() {
        return "Part-Time | " + super.toString();
    }
}
