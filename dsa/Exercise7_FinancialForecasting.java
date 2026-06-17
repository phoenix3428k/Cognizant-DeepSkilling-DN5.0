import java.util.HashMap;
import java.util.Map;

// ─────────────────────────────────────────────
// Exercise 7: Financial Forecasting
// Concept: Recursion + Memoization
//
// Plain recursion: O(2^n) — exponential due to recomputation
// With memoization: O(n)  — each sub-problem solved once
// ─────────────────────────────────────────────

public class Exercise7_FinancialForecasting {

    // ── Plain recursive future-value calculation ──────────────────────
    // futureValue(principal, rate, years) = principal * (1 + rate)^years
    // Recursion: fv(p, r, n) = fv(p, r, n-1) * (1 + r)
    // Time  : O(n)  — tail-recursive depth n
    // Space : O(n)  — call stack depth
    public static double futureValueRecursive(double principal, double rate, int years) {
        if (years == 0) return principal;                          // base case
        return futureValueRecursive(principal, rate, years - 1) * (1 + rate);
    }

    // ── Fibonacci-based growth forecast (demonstrates memoization) ────
    // Some forecasting models use Fibonacci-like growth sequences.
    // Plain fib: O(2^n); memoized fib: O(n)
    private static Map<Integer, Long> memo = new HashMap<>();

    public static long fibonacciForecast(int n) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n); // cache hit
        long result = fibonacciForecast(n - 1) + fibonacciForecast(n - 2);
        memo.put(n, result);
        return result;
    }

    // ── Compound growth series: print year-by-year projection ─────────
    public static void printProjection(double principal, double rate, int years) {
        System.out.printf("%-6s %-15s%n", "Year", "Value (₹)");
        System.out.println("─".repeat(22));
        for (int y = 0; y <= years; y++) {
            double val = futureValueRecursive(principal, rate, y);
            System.out.printf("%-6d %-15.2f%n", y, val);
        }
    }

    public static void main(String[] args) {
        double principal = 100_000.0; // ₹1 lakh
        double rate      = 0.12;      // 12% annual growth
        int    years     = 10;

        System.out.println("=== Compound Growth Projection ===");
        System.out.printf("Principal: ₹%.0f | Rate: %.0f%% | Years: %d%n%n",
                principal, rate * 100, years);
        printProjection(principal, rate, years);

        double finalValue = futureValueRecursive(principal, rate, years);
        System.out.printf("%nFinal value after %d years: ₹%.2f%n", years, finalValue);

        System.out.println("\n=== Fibonacci Growth Forecast (memoized) ===");
        System.out.println("Fib sequence as a growth index:");
        for (int i = 1; i <= 10; i++) {
            System.out.printf("  Month %2d → index %d%n", i, fibonacciForecast(i));
        }

        System.out.println("\n--- Complexity Notes ---");
        System.out.println("futureValueRecursive : O(n) time, O(n) stack space");
        System.out.println("Plain Fibonacci      : O(2^n) — exponential, avoid");
        System.out.println("Memoized Fibonacci   : O(n)  — each value computed once");
        System.out.println("Optimization tip     : Use iterative or bottom-up DP to cut stack space to O(1).");
    }
}
