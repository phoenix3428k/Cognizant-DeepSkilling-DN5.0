// ─────────────────────────────────────────────
// Exercise 4: Employee Management System
// Data Structure: Fixed-size Array
//
// Time Complexity:
//   add      → O(1) amortized (append at end)
//   search   → O(n) linear scan
//   traverse → O(n)
//   delete   → O(n) shift elements left
// ─────────────────────────────────────────────

class Employee {
    int    employeeId;
    String name;
    String position;
    double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name       = name;
        this.position   = position;
        this.salary     = salary;
    }

    @Override
    public String toString() {
        return String.format("Employee[id=%d, name=%s, position=%s, salary=%.2f]",
                employeeId, name, position, salary);
    }
}

class EmployeeManager {
    private Employee[] employees;
    private int size;
    private final int CAPACITY;

    public EmployeeManager(int capacity) {
        this.CAPACITY  = capacity;
        this.employees = new Employee[capacity];
        this.size      = 0;
    }

    /** Add: O(1) — append at current size index */
    public void addEmployee(Employee e) {
        if (size == CAPACITY) {
            System.out.println("Array full! Cannot add " + e.name);
            return;
        }
        employees[size++] = e;
        System.out.println("Added: " + e);
    }

    /** Search: O(n) — linear scan through array */
    public Employee searchById(int id) {
        for (int i = 0; i < size; i++) {
            if (employees[i].employeeId == id) return employees[i];
        }
        return null;
    }

    /** Traverse: O(n) */
    public void traverse() {
        if (size == 0) { System.out.println("No employees."); return; }
        System.out.println("--- Employee Records (" + size + ") ---");
        for (int i = 0; i < size; i++) System.out.println("  " + employees[i]);
    }

    /** Delete: O(n) — find + left-shift remaining elements */
    public boolean deleteEmployee(int id) {
        for (int i = 0; i < size; i++) {
            if (employees[i].employeeId == id) {
                // shift left
                for (int j = i; j < size - 1; j++) employees[j] = employees[j + 1];
                employees[--size] = null; // help GC
                System.out.println("Deleted employee ID " + id);
                return true;
            }
        }
        System.out.println("Employee ID " + id + " not found.");
        return false;
    }
}

public class Exercise4_EmployeeManagement {
    public static void main(String[] args) {
        EmployeeManager mgr = new EmployeeManager(10);

        mgr.addEmployee(new Employee(1, "Asmet",  "SDE Intern",   25000));
        mgr.addEmployee(new Employee(2, "Pooja",  "Data Analyst",  55000));
        mgr.addEmployee(new Employee(3, "Vikram", "DevOps Lead",   90000));
        mgr.addEmployee(new Employee(4, "Anita",  "QA Engineer",   60000));

        System.out.println();
        mgr.traverse();

        System.out.println("\n--- Search ID 3 ---");
        Employee found = mgr.searchById(3);
        System.out.println(found != null ? "Found: " + found : "Not Found");

        System.out.println("\n--- Delete ID 2 ---");
        mgr.deleteEmployee(2);
        mgr.traverse();

        System.out.println("\n--- Complexity Summary ---");
        System.out.println("add(at end) : O(1)");
        System.out.println("search      : O(n)");
        System.out.println("traverse    : O(n)");
        System.out.println("delete      : O(n) — due to left shift");
        System.out.println("Limitation  : Fixed size; use ArrayList for dynamic growth.");
    }
}
