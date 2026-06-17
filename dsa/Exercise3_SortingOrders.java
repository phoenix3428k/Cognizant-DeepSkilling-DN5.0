import java.util.Arrays;

// ─────────────────────────────────────────────
// Exercise 3: Sorting Customer Orders
//
// Bubble Sort → O(n²) — simple, rarely used in prod
// Quick Sort  → O(n log n) average — preferred
// ─────────────────────────────────────────────

class Order {
    int orderId;
    String customerName;
    double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.totalPrice   = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("Order[id=%d, customer=%s, total=%.2f]",
                orderId, customerName, totalPrice);
    }
}

public class Exercise3_SortingOrders {

    // ── Bubble Sort: O(n²) ───────────────────────────────────────────
    // Repeatedly swaps adjacent elements if out of order.
    // Best  : O(n) with early-exit flag (already sorted)
    // Worst : O(n²) (reverse-sorted input)
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    Order temp     = orders[j];
                    orders[j]      = orders[j + 1];
                    orders[j + 1]  = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // early exit — already sorted
        }
    }

    // ── Quick Sort: O(n log n) average ───────────────────────────────
    // Divide-and-conquer with in-place partitioning.
    // Worst  : O(n²) (already sorted + bad pivot) — rare with good pivot choice
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(orders, low, high);
            quickSort(orders, low, pivotIdx - 1);
            quickSort(orders, pivotIdx + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].totalPrice; // last element as pivot
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (orders[j].totalPrice <= pivot) {
                i++;
                Order temp = orders[i]; orders[i] = orders[j]; orders[j] = temp;
            }
        }
        Order temp = orders[i + 1]; orders[i + 1] = orders[high]; orders[high] = temp;
        return i + 1;
    }

    private static void print(Order[] orders) {
        for (Order o : orders) System.out.println("  " + o);
    }

    public static void main(String[] args) {
        Order[] original = {
            new Order(1, "Arjun",  4500.00),
            new Order(2, "Sneha",  1200.50),
            new Order(3, "Rahul", 15000.00),
            new Order(4, "Priya",  800.00),
            new Order(5, "Kiran",  9999.99),
        };

        // ── Bubble Sort ──
        Order[] bubbleArr = Arrays.copyOf(original, original.length);
        long start = System.nanoTime();
        bubbleSort(bubbleArr);
        long bubbleTime = System.nanoTime() - start;

        System.out.println("=== Bubble Sort (ascending by totalPrice) ===");
        print(bubbleArr);
        System.out.printf("Time: %d ns%n%n", bubbleTime);

        // ── Quick Sort ──
        Order[] quickArr = Arrays.copyOf(original, original.length);
        start = System.nanoTime();
        quickSort(quickArr, 0, quickArr.length - 1);
        long quickTime = System.nanoTime() - start;

        System.out.println("=== Quick Sort (ascending by totalPrice) ===");
        print(quickArr);
        System.out.printf("Time: %d ns%n%n", quickTime);

        System.out.println("--- Complexity Comparison ---");
        System.out.println("Bubble Sort : O(n²)       — simple but slow on large data");
        System.out.println("Quick Sort  : O(n log n)  — preferred for real workloads");
        System.out.println("Quick Sort is ~" + (bubbleTime / Math.max(quickTime, 1)) + "x faster here.");
    }
}
