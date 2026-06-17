// ─────────────────────────────────────────────
// Exercise 11: Facade Pattern
// Online Order Processing System
// Provides a simplified interface to a complex
// subsystem of loosely coupled services.
// ─────────────────────────────────────────────

// ═══════════════════════════════════════════════
// Subsystem Classes (complex internals)
// ═══════════════════════════════════════════════

class InventoryService {
    public boolean checkStock(String productId, int quantity) {
        System.out.printf("  [Inventory] Checking stock for %s (qty: %d)...%n", productId, quantity);
        boolean inStock = !productId.equals("OUT001"); // simulate out-of-stock
        System.out.println("  [Inventory] " + (inStock ? "In stock ✔" : "OUT OF STOCK ✘"));
        return inStock;
    }

    public void reserveStock(String productId, int quantity) {
        System.out.printf("  [Inventory] Reserved %d unit(s) of %s.%n", quantity, productId);
    }

    public void releaseStock(String productId, int quantity) {
        System.out.printf("  [Inventory] Released %d unit(s) of %s.%n", quantity, productId);
    }
}

class PaymentService {
    public boolean processPayment(String customerId, double amount, String method) {
        System.out.printf("  [Payment] Processing ₹%.2f via %s for customer %s...%n",
                amount, method, customerId);
        boolean success = !customerId.equals("BAD_CARD"); // simulate failure
        System.out.println("  [Payment] " + (success ? "Payment successful ✔" : "Payment FAILED ✘"));
        return success;
    }

    public void refundPayment(String customerId, double amount) {
        System.out.printf("  [Payment] Refunding ₹%.2f to customer %s.%n", amount, customerId);
    }
}

class ShippingService {
    public String scheduleShipment(String productId, String address) {
        String trackingId = "TRK" + (int)(Math.random() * 900000 + 100000);
        System.out.printf("  [Shipping] Shipment scheduled → %s | Tracking: %s%n",
                address, trackingId);
        return trackingId;
    }

    public void cancelShipment(String trackingId) {
        System.out.printf("  [Shipping] Shipment %s cancelled.%n", trackingId);
    }
}

class NotificationService {
    public void sendOrderConfirmation(String email, String trackingId) {
        System.out.printf("  [Notify] Confirmation email sent to %s | Tracking: %s%n",
                email, trackingId);
    }

    public void sendCancellationNotice(String email) {
        System.out.printf("  [Notify] Cancellation email sent to %s.%n", email);
    }

    public void sendPaymentFailureAlert(String email) {
        System.out.printf("  [Notify] Payment failure alert sent to %s.%n", email);
    }
}

// ═══════════════════════════════════════════════
// FACADE — single unified interface
// ═══════════════════════════════════════════════

class OrderFacade {
    private final InventoryService   inventory   = new InventoryService();
    private final PaymentService     payment     = new PaymentService();
    private final ShippingService    shipping    = new ShippingService();
    private final NotificationService notify     = new NotificationService();

    /**
     * Orchestrates: stock check → payment → reserve → ship → notify
     * Returns tracking ID or null on failure.
     */
    public String placeOrder(String customerId, String email,
                             String productId, int quantity,
                             double totalAmount, String paymentMethod,
                             String shippingAddress) {

        System.out.println("\n──────────────────────────────────────────");
        System.out.printf("ORDER: customer=%s product=%s qty=%d%n",
                customerId, productId, quantity);
        System.out.println("──────────────────────────────────────────");

        // Step 1: Check stock
        if (!inventory.checkStock(productId, quantity)) {
            notify.sendCancellationNotice(email);
            return null;
        }

        // Step 2: Process payment
        if (!payment.processPayment(customerId, totalAmount, paymentMethod)) {
            notify.sendPaymentFailureAlert(email);
            return null;
        }

        // Step 3: Reserve stock
        inventory.reserveStock(productId, quantity);

        // Step 4: Schedule shipment
        String trackingId = shipping.scheduleShipment(productId, shippingAddress);

        // Step 5: Notify customer
        notify.sendOrderConfirmation(email, trackingId);

        System.out.println("  ✔ Order placed successfully!");
        return trackingId;
    }

    /** Cancels order — reverses payment + releases stock */
    public void cancelOrder(String customerId, String email,
                            String productId, int quantity,
                            double amount, String trackingId) {

        System.out.println("\n──────────────────────────────────────────");
        System.out.println("CANCEL ORDER: tracking=" + trackingId);
        System.out.println("──────────────────────────────────────────");

        shipping.cancelShipment(trackingId);
        inventory.releaseStock(productId, quantity);
        payment.refundPayment(customerId, amount);
        notify.sendCancellationNotice(email);
        System.out.println("  ✔ Order cancelled and refund initiated.");
    }
}

public class Exercise11_FacadePattern {
    public static void main(String[] args) {
        OrderFacade orderSystem = new OrderFacade();

        // ── Successful order ─────────────────────
        String trackingId = orderSystem.placeOrder(
                "CUST001", "asmetranjan25@gmail.com",
                "LAPTOP01", 1,
                75000.00, "UPI",
                "Flat 3B, Tech Park, Bhubaneswar, Odisha");

        // ── Cancel that order ─────────────────────
        if (trackingId != null) {
            orderSystem.cancelOrder(
                    "CUST001", "asmetranjan25@gmail.com",
                    "LAPTOP01", 1, 75000.00, trackingId);
        }

        // ── Failed: out of stock ──────────────────
        orderSystem.placeOrder(
                "CUST002", "buyer2@example.com",
                "OUT001", 2,
                5000.00, "Credit Card",
                "MG Road, Bangalore");

        // ── Failed: bad payment ───────────────────
        orderSystem.placeOrder(
                "BAD_CARD", "buyer3@example.com",
                "MOUSE01", 1,
                499.00, "Credit Card",
                "Sector 18, Noida");

        System.out.println("\n--- Key Benefit ---");
        System.out.println("Client calls ONE method. Facade coordinates 4 subsystems internally.");
        System.out.println("Subsystems remain reusable independently too.");
        System.out.println("Adding a new step (e.g., LoyaltyPoints) only touches the Facade.");
    }
}
