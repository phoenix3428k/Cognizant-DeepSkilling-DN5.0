// ─────────────────────────────────────────────
// Exercise 5: Decorator Pattern
// Coffee Ordering System
// Adds responsibilities to objects dynamically
// without modifying existing class hierarchy.
// ─────────────────────────────────────────────

// ── Component Interface ───────────────────────
interface Coffee {
    String getDescription();
    double getCost();
}

// ── Concrete Component ────────────────────────
class SimpleCoffee implements Coffee {
    @Override public String getDescription() { return "Simple Coffee"; }
    @Override public double getCost()        { return 50.00; }
}

class Espresso implements Coffee {
    @Override public String getDescription() { return "Espresso"; }
    @Override public double getCost()        { return 80.00; }
}

// ── Abstract Decorator ────────────────────────
abstract class CoffeeDecorator implements Coffee {
    protected final Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    @Override public String getDescription() { return decoratedCoffee.getDescription(); }
    @Override public double getCost()        { return decoratedCoffee.getCost(); }
}

// ── Concrete Decorators ───────────────────────
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }

    @Override public String getDescription() { return decoratedCoffee.getDescription() + ", Milk"; }
    @Override public double getCost()        { return decoratedCoffee.getCost() + 15.00; }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) { super(coffee); }

    @Override public String getDescription() { return decoratedCoffee.getDescription() + ", Sugar"; }
    @Override public double getCost()        { return decoratedCoffee.getCost() + 5.00; }
}

class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) { super(coffee); }

    @Override public String getDescription() { return decoratedCoffee.getDescription() + ", Whipped Cream"; }
    @Override public double getCost()        { return decoratedCoffee.getCost() + 25.00; }
}

class VanillaSyrupDecorator extends CoffeeDecorator {
    public VanillaSyrupDecorator(Coffee coffee) { super(coffee); }

    @Override public String getDescription() { return decoratedCoffee.getDescription() + ", Vanilla Syrup"; }
    @Override public double getCost()        { return decoratedCoffee.getCost() + 20.00; }
}

public class Exercise5_DecoratorPattern {

    private static void printOrder(Coffee coffee) {
        System.out.printf("  %-55s ₹%.2f%n", coffee.getDescription(), coffee.getCost());
    }

    public static void main(String[] args) {
        System.out.println("=== Coffee Ordering System ===\n");

        // Order 1: Plain coffee
        Coffee order1 = new SimpleCoffee();

        // Order 2: Espresso + Milk + Sugar
        Coffee order2 = new SugarDecorator(new MilkDecorator(new Espresso()));

        // Order 3: Simple Coffee + Milk + Whip + Vanilla
        Coffee order3 = new VanillaSyrupDecorator(
                            new WhipDecorator(
                                new MilkDecorator(new SimpleCoffee())));

        // Order 4: Double whip espresso
        Coffee order4 = new WhipDecorator(new WhipDecorator(new Espresso()));

        System.out.printf("  %-55s %s%n", "Order", "Price");
        System.out.println("  " + "-".repeat(65));
        printOrder(order1);
        printOrder(order2);
        printOrder(order3);
        printOrder(order4);

        System.out.println("\n--- Key Benefit ---");
        System.out.println("Each add-on is a separate class. No combinatorial subclass explosion.");
        System.out.println("Decorators wrap any Coffee — composable at runtime.");
    }
}
