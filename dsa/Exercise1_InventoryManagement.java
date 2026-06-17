import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// ─────────────────────────────────────────────
// Exercise 1: Inventory Management System
// Data Structure: HashMap<productId, Product>
//
// Time Complexity:
//   add    → O(1) average
//   update → O(1) average
//   delete → O(1) average
// ─────────────────────────────────────────────

class Product {
    int productId;
    String productName;
    int quantity;
    double price;

    public Product(int productId, String productName, int quantity, double price) {
        this.productId   = productId;
        this.productName = productName;
        this.quantity    = quantity;
        this.price       = price;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%d, name=%s, qty=%d, price=%.2f]",
                productId, productName, quantity, price);
    }
}

class Inventory {
    // HashMap: O(1) average for add / update / delete
    private Map<Integer, Product> store = new HashMap<>();

    /** O(1) average – hash-based insertion */
    public void addProduct(Product p) {
        if (store.containsKey(p.productId)) {
            System.out.println("Product ID " + p.productId + " already exists. Use updateProduct().");
            return;
        }
        store.put(p.productId, p);
        System.out.println("Added: " + p);
    }

    /** O(1) average – hash-based lookup then update */
    public void updateProduct(int productId, int newQty, double newPrice) {
        Product p = store.get(productId);
        if (p == null) {
            System.out.println("Product ID " + productId + " not found.");
            return;
        }
        p.quantity = newQty;
        p.price    = newPrice;
        System.out.println("Updated: " + p);
    }

    /** O(1) average – hash-based removal */
    public void deleteProduct(int productId) {
        Product removed = store.remove(productId);
        if (removed == null) {
            System.out.println("Product ID " + productId + " not found.");
        } else {
            System.out.println("Deleted: " + removed);
        }
    }

    public void displayAll() {
        if (store.isEmpty()) { System.out.println("Inventory is empty."); return; }
        store.values().forEach(System.out::println);
    }
}

public class Exercise1_InventoryManagement {
    public static void main(String[] args) {
        Inventory inv = new Inventory();

        inv.addProduct(new Product(101, "Laptop",  50, 75000.00));
        inv.addProduct(new Product(102, "Mouse",  200,   499.00));
        inv.addProduct(new Product(103, "Monitor", 30, 15000.00));

        System.out.println("\n--- All Products ---");
        inv.displayAll();

        System.out.println("\n--- Update Mouse price and qty ---");
        inv.updateProduct(102, 180, 449.00);

        System.out.println("\n--- Delete Monitor ---");
        inv.deleteProduct(103);

        System.out.println("\n--- Final Inventory ---");
        inv.displayAll();
    }
}
