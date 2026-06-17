import java.util.Arrays;
import java.util.Comparator;

// ─────────────────────────────────────────────
// Exercise 6: Library Management System
// Linear Search  → O(n)
// Binary Search  → O(log n) on sorted list
// ─────────────────────────────────────────────

class Book {
    int    bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title  = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("Book[id=%d, title=\"%s\", author=%s]", bookId, title, author);
    }
}

public class Exercise6_LibraryManagement {

    // ── Linear Search by title: O(n) ─────────────────────────────────
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    // ── Binary Search by title: O(log n) ─────────────────────────────
    // Precondition: array sorted alphabetically by title
    public static Book binarySearchByTitle(Book[] sortedBooks, String title) {
        int low = 0, high = sortedBooks.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = sortedBooks[mid].title.compareToIgnoreCase(title);
            if      (cmp == 0) return sortedBooks[mid];
            else if (cmp < 0)  low  = mid + 1;
            else               high = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        Book[] catalog = {
            new Book(1, "The Pragmatic Programmer", "Hunt & Thomas"),
            new Book(2, "Clean Code",               "Robert C. Martin"),
            new Book(3, "Introduction to Algorithms","CLRS"),
            new Book(4, "Design Patterns",          "Gang of Four"),
            new Book(5, "Effective Java",           "Joshua Bloch"),
        };

        // ── Linear Search (unsorted ok) ──
        System.out.println("=== Linear Search ===");
        Book result = linearSearchByTitle(catalog, "Clean Code");
        System.out.println(result != null ? "Found: " + result : "Not Found");

        result = linearSearchByTitle(catalog, "SICP");
        System.out.println(result != null ? "Found: " + result : "Not Found (SICP)");

        // ── Sort alphabetically for binary search ──
        Book[] sorted = Arrays.copyOf(catalog, catalog.length);
        Arrays.sort(sorted, Comparator.comparing(b -> b.title.toLowerCase()));

        System.out.println("\n=== Binary Search (sorted by title) ===");
        result = binarySearchByTitle(sorted, "Effective Java");
        System.out.println(result != null ? "Found: " + result : "Not Found");

        result = binarySearchByTitle(sorted, "SICP");
        System.out.println(result != null ? "Found: " + result : "Not Found (SICP)");

        System.out.println("\n--- When to use what? ---");
        System.out.println("Small / unsorted catalog    → Linear Search  O(n)");
        System.out.println("Large / sorted catalog      → Binary Search  O(log n)");
        System.out.println("Even larger / frequent ops  → HashMap/Trie   O(1)/O(k)");
    }
}
