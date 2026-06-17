import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
// Exercise 7: Observer Pattern
// Stock Market Notification System
// Subject broadcasts state changes; observers
// react without tight coupling.
// ─────────────────────────────────────────────

// ── Observer Interface ────────────────────────
interface StockObserver {
    void update(String stockSymbol, double oldPrice, double newPrice);
}

// ── Subject Interface ─────────────────────────
interface StockSubject {
    void registerObserver(StockObserver observer);
    void removeObserver(StockObserver observer);
    void notifyObservers(String symbol, double oldPrice, double newPrice);
}

// ── Concrete Subject ──────────────────────────
class StockMarket implements StockSubject {
    private final List<StockObserver> observers = new ArrayList<>();
    private String stockSymbol;
    private double price;

    public StockMarket(String stockSymbol, double initialPrice) {
        this.stockSymbol = stockSymbol;
        this.price       = initialPrice;
        System.out.printf("[Market] %s initialized at ₹%.2f%n", stockSymbol, price);
    }

    @Override
    public void registerObserver(StockObserver observer) {
        observers.add(observer);
        System.out.println("[Market] Observer registered: " + observer.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
        System.out.println("[Market] Observer removed: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers(String symbol, double oldPrice, double newPrice) {
        for (StockObserver obs : observers) {
            obs.update(symbol, oldPrice, newPrice);
        }
    }

    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price      = newPrice;
        System.out.printf("%n[Market] %s price changed: ₹%.2f → ₹%.2f%n",
                stockSymbol, oldPrice, newPrice);
        notifyObservers(stockSymbol, oldPrice, newPrice);
    }

    public double getPrice() { return price; }
}

// ── Concrete Observers ────────────────────────
class MobileAppAlert implements StockObserver {
    private final String userId;

    public MobileAppAlert(String userId) { this.userId = userId; }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        double change = ((newPrice - oldPrice) / oldPrice) * 100;
        System.out.printf("  [MobileApp→%s] %s %s %.2f%% | ₹%.2f%n",
                userId, symbol,
                change >= 0 ? "▲" : "▼",
                Math.abs(change), newPrice);
    }
}

class EmailNotification implements StockObserver {
    private final String email;

    public EmailNotification(String email) { this.email = email; }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        System.out.printf("  [Email→%s] Alert: %s moved from ₹%.2f to ₹%.2f%n",
                email, symbol, oldPrice, newPrice);
    }
}

class TradingBot implements StockObserver {
    private final double buyThreshold;
    private final double sellThreshold;

    public TradingBot(double buyBelow, double sellAbove) {
        this.buyThreshold  = buyBelow;
        this.sellThreshold = sellAbove;
    }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        if (newPrice <= buyThreshold) {
            System.out.printf("  [TradingBot] BUY signal for %s at ₹%.2f%n", symbol, newPrice);
        } else if (newPrice >= sellThreshold) {
            System.out.printf("  [TradingBot] SELL signal for %s at ₹%.2f%n", symbol, newPrice);
        } else {
            System.out.printf("  [TradingBot] HOLD %s — price within range.%n", symbol);
        }
    }
}

public class Exercise7_ObserverPattern {
    public static void main(String[] args) {
        StockMarket tcs = new StockMarket("TCS", 3800.00);

        StockObserver mobile  = new MobileAppAlert("Asmet");
        StockObserver email   = new EmailNotification("asmetranjan25@gmail.com");
        StockObserver bot     = new TradingBot(3600.00, 4000.00);

        System.out.println();
        tcs.registerObserver(mobile);
        tcs.registerObserver(email);
        tcs.registerObserver(bot);

        // Simulate price changes
        tcs.setPrice(3950.00);
        tcs.setPrice(4050.00); // sell signal
        tcs.setPrice(3580.00); // buy signal

        // Remove email observer
        System.out.println();
        tcs.removeObserver(email);
        tcs.setPrice(3700.00); // email won't be notified

        System.out.println("\n--- Key Benefit ---");
        System.out.println("StockMarket knows nothing about observer implementations.");
        System.out.println("Observers can be added/removed at runtime — open/closed principle.");
    }
}
