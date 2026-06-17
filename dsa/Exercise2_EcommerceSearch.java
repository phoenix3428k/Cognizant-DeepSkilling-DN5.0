import java.util.Arrays;
import java.util.Comparator;

// ─────────────────────────────────────────────
// Exercise 2: E-commerce Platform Search Function
//
// Linear Search  → O(n) — works on unsorted data
// Binary Search  → O(log n) — requires sorted data
// ─────────────────────────────────────────────

class SearchProduct {
    int productId;
    String productName;
    String category;

    public SearchProduct(int productId, String productName, String category) {
        this.productId   = productId;
        this.productName = productName;
        this.category    = category;
    }

    @Override
    public String toString() {
        return String.format("SearchProduct[id=%d, name=%s, category=%s]",
                productId, productName, category);
    }
}

public class Exercise2_EcommerceSearch {

    // ── Linear Search: O(n) ──────────────────────────────────────────
    // Best case  : O(1) – found at index 0
    // Worst case : O(n) – not found / last element
    // Average    : O(n/2) ≈ O(n)
    public static SearchProduct linearSearch(SearchProduct[] products, int targetId) {
        for (SearchProduct p : products) {
            if (p.productId == targetId) return p;
        }
        return null;
    }

    // ── Binary Search: O(log n) ───────────────────────────────────────
    // Precondition: array must be sorted by productId
    // Best case  : O(1) – target is the mid element
    // Worst case : O(log n)
    public static SearchProduct binarySearch(SearchProduct[] sortedProducts, int targetId) {
        int low = 0, high = sortedProducts.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedProducts[mid].productId == targetId)  return sortedProducts[mid];
            else if (sortedProducts[mid].productId < targetId) low  = mid + 1;
            else                                               high = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        SearchProduct[] products = {
            new SearchProduct(104, "Keyboard", "Peripherals"),
            new SearchProduct(201, "Webcam",   "Peripherals"),
            new SearchProduct(305, "SSD",      "Storage"),
            new SearchProduct(410, "RAM",      "Memory"),
            new SearchProduct(512, "CPU Fan",  "Cooling"),
        };

        // Linear search on unsorted data
        System.out.println("=== Linear Search ===");
        SearchProduct result = linearSearch(products, 305);
        System.out.println(result != null ? "Found: " + result : "Not Found");

        result = linearSearch(products, 999);
        System.out.println(result != null ? "Found: " + result : "Not Found (999)");

        // Sort by productId before binary search
        SearchProduct[] sorted = Arrays.copyOf(products, products.length);
        Arrays.sort(sorted, Comparator.comparingInt(p -> p.productId));

        System.out.println("\n=== Binary Search (sorted array) ===");
        result = binarySearch(sorted, 410);
        System.out.println(result != null ? "Found: " + result : "Not Found");

        result = binarySearch(sorted, 999);
        System.out.println(result != null ? "Found: " + result : "Not Found (999)");

        System.out.println("\n--- Complexity Comparison ---");
        System.out.println("Linear Search : O(n)     — no sorting needed");
        System.out.println("Binary Search : O(log n) — requires sorted data");
        System.out.println("For large catalogs, Binary Search (or HashMap) wins.");
    }
}
