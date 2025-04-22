public class FullTimeEmployee extends Employee {
    public FullTimeEmployee(int id, String name, double salary, Department department, Role role) {
        super(id, name, salary, department, role);
    }

    @Override
    public double calculateBonus() {
        return salary * 0.10;
    }

    @Override
    public String toString() {
        return "Full-Time | " + super.toString();
    }
}
