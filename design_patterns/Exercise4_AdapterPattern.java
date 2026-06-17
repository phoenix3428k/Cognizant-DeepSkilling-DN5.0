// ─────────────────────────────────────────────
// Exercise 4: Adapter Pattern
// Payment Processing System integrating with
// third-party gateways (Razorpay, PayPal, Stripe)
// ─────────────────────────────────────────────

// ── Target Interface — our system's contract ──
interface PaymentProcessor {
    void processPayment(double amount, String currency);
    void refund(double amount);
}

// ── Adaptee 1: Razorpay (3rd-party, own API) ─
class RazorpayGateway {
    public void makePayment(double amount, String currency) {
        System.out.printf("[Razorpay] Payment of %.2f %s initiated.%n", amount, currency);
    }
    public void initiateRefund(double amount) {
        System.out.printf("[Razorpay] Refund of %.2f initiated.%n", amount);
    }
}

// ── Adaptee 2: PayPal (3rd-party, own API) ───
class PayPalGateway {
    public void sendMoney(double amount, String currencyCode) {
        System.out.printf("[PayPal] Sending %.2f %s via PayPal.%n", amount, currencyCode);
    }
    public void returnMoney(double amount) {
        System.out.printf("[PayPal] Returning %.2f to payer.%n", amount);
    }
}

// ── Adaptee 3: Stripe (3rd-party, own API) ───
class StripeGateway {
    public void charge(String currency, double amount) {
        System.out.printf("[Stripe] Charging %.2f %s on card.%n", amount, currency);
    }
    public void reverseCharge(double amount) {
        System.out.printf("[Stripe] Reversing %.2f charge.%n", amount);
    }
}

// ── Adapter 1 ─────────────────────────────────
class RazorpayAdapter implements PaymentProcessor {
    private final RazorpayGateway razorpay = new RazorpayGateway();

    @Override public void processPayment(double amount, String currency) {
        razorpay.makePayment(amount, currency);
    }
    @Override public void refund(double amount) {
        razorpay.initiateRefund(amount);
    }
}

// ── Adapter 2 ─────────────────────────────────
class PayPalAdapter implements PaymentProcessor {
    private final PayPalGateway payPal = new PayPalGateway();

    @Override public void processPayment(double amount, String currency) {
        payPal.sendMoney(amount, currency);
    }
    @Override public void refund(double amount) {
        payPal.returnMoney(amount);
    }
}

// ── Adapter 3 ─────────────────────────────────
class StripeAdapter implements PaymentProcessor {
    private final StripeGateway stripe = new StripeGateway();

    @Override public void processPayment(double amount, String currency) {
        stripe.charge(currency, amount);
    }
    @Override public void refund(double amount) {
        stripe.reverseCharge(amount);
    }
}

// ── Client code ───────────────────────────────
class CheckoutService {
    private final PaymentProcessor processor;

    public CheckoutService(PaymentProcessor processor) {
        this.processor = processor;
    }

    public void checkout(double amount, String currency) {
        System.out.println("==> Processing payment...");
        processor.processPayment(amount, currency);
    }

    public void cancelOrder(double amount) {
        System.out.println("==> Processing refund...");
        processor.refund(amount);
    }
}

public class Exercise4_AdapterPattern {
    public static void main(String[] args) {
        double amount   = 4999.00;
        String currency = "INR";

        PaymentProcessor[] gateways = {
            new RazorpayAdapter(),
            new PayPalAdapter(),
            new StripeAdapter()
        };

        for (PaymentProcessor gateway : gateways) {
            CheckoutService service = new CheckoutService(gateway);
            service.checkout(amount, currency);
            service.cancelOrder(amount);
            System.out.println();
        }

        System.out.println("--- Key Benefit ---");
        System.out.println("CheckoutService works with ANY payment gateway via");
        System.out.println("the unified PaymentProcessor interface — no gateway-specific code.");
    }
}
