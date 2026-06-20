package exercise_4;

public class Main {
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem(5);

        // Add employees for Testing Purpose
        system.addEmployee(new Employee(101, "Rahul", "Team Lead", 82000));
        system.addEmployee(new Employee(102, "Priya", "Software Engineer", 68000));
        system.addEmployee(new Employee(103, "Arjun", "Data Analyst", 62000));
        system.addEmployee(new Employee(104, "Sneha", "UI/UX Designer", 59000));
        system.addEmployee(new Employee(105, "Karan", "HR Executive", 65000));

        // Traverse employees
        System.out.println("Employee List:");
        system.traverseEmployees();

        // Search for an employee - TESTING PURPOSE
        System.out.println("\nSearching for Employee with ID 103:");
        Employee emp = system.searchEmployee(103);

        if (emp != null) {
            System.out.println("Employee found: " + emp);
        } else {
            System.out.println("Employee not found.");
        }

        // Delete an employee
        System.out.println("\nDeleting Employee with ID 102:");
        boolean isDeleted = system.deleteEmployee(102);

        if (isDeleted) {
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }

        // Traverse employees again
        System.out.println("\nEmployee List after Deletion:");
        system.traverseEmployees();
    }
}