// ─────────────────────────────────────────────
// Exercise 8: Strategy Pattern
// Navigation / Route Planning App
// Defines a family of algorithms, encapsulates
// each one, and makes them interchangeable.
// ─────────────────────────────────────────────

// ── Strategy Interface ────────────────────────
interface RouteStrategy {
    void buildRoute(String origin, String destination);
    String getModeName();
}

// ── Concrete Strategies ───────────────────────
class DrivingStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String origin, String destination) {
        System.out.printf("[Driving]  %s → %s | Using highways. ETA: ~45 min. Toll: ₹120%n",
                origin, destination);
    }
    @Override public String getModeName() { return "Driving"; }
}

class WalkingStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String origin, String destination) {
        System.out.printf("[Walking]  %s → %s | Shortest pedestrian path. ETA: ~25 min. Free.%n",
                origin, destination);
    }
    @Override public String getModeName() { return "Walking"; }
}

class CyclingStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String origin, String destination) {
        System.out.printf("[Cycling]  %s → %s | Cycle lane preferred. ETA: ~15 min. Free.%n",
                origin, destination);
    }
    @Override public String getModeName() { return "Cycling"; }
}

class PublicTransitStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String origin, String destination) {
        System.out.printf("[Transit]  %s → %s | Metro Line 2 + Bus 47. ETA: ~35 min. ₹25%n",
                origin, destination);
    }
    @Override public String getModeName() { return "Public Transit"; }
}

class FlightStrategy implements RouteStrategy {
    @Override
    public void buildRoute(String origin, String destination) {
        System.out.printf("[Flight]   %s → %s | Direct flight. ETA: ~2 hrs. ₹4,500%n",
                origin, destination);
    }
    @Override public String getModeName() { return "Flight"; }
}

// ── Context ───────────────────────────────────
class Navigator {
    private RouteStrategy strategy;

    public Navigator(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    /** Strategy can be swapped at runtime */
    public void setStrategy(RouteStrategy strategy) {
        System.out.println("[Navigator] Switching to: " + strategy.getModeName());
        this.strategy = strategy;
    }

    public void navigate(String from, String to) {
        strategy.buildRoute(from, to);
    }
}

public class Exercise8_StrategyPattern {
    public static void main(String[] args) {
        String origin      = "Connaught Place, Delhi";
        String destination = "Cyber Hub, Gurugram";

        Navigator nav = new Navigator(new DrivingStrategy());

        System.out.println("=== Route Planning App ===\n");
        System.out.println("All routes from: " + origin + " → " + destination + "\n");

        nav.navigate(origin, destination);

        nav.setStrategy(new WalkingStrategy());
        nav.navigate(origin, destination);

        nav.setStrategy(new CyclingStrategy());
        nav.navigate(origin, destination);

        nav.setStrategy(new PublicTransitStrategy());
        nav.navigate(origin, destination);

        System.out.println("\n--- Long Distance ---");
        nav.setStrategy(new FlightStrategy());
        nav.navigate("Delhi", "Bhubaneswar");

        System.out.println("\n--- Key Benefit ---");
        System.out.println("Navigator doesn't know HOW to route — it delegates to the strategy.");
        System.out.println("Adding a new mode (e.g., Boat) = new class, zero changes to Navigator.");
    }
}
