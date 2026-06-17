import java.util.NoSuchElementException;

// ─────────────────────────────────────────────
// Exercise 10: Iterator Pattern
// Book Collection traversal without exposing
// the underlying storage structure.
// ─────────────────────────────────────────────

// ── Model ────────────────────────────────────
class LibraryBook {
    private final String title;
    private final String author;
    private final String genre;
    private final double price;

    public LibraryBook(String title, String author, String genre, double price) {
        this.title  = title;
        this.author = author;
        this.genre  = genre;
        this.price  = price;
    }

    public String getTitle()  { return title;  }
    public String getAuthor() { return author; }
    public String getGenre()  { return genre;  }
    public double getPrice()  { return price;  }

    @Override
    public String toString() {
        return String.format("Book{title=\"%s\", author=%s, genre=%s, price=₹%.2f}",
                title, author, genre, price);
    }
}

// ── Iterator Interface ────────────────────────
interface BookIterator {
    boolean hasNext();
    LibraryBook next();
    void reset();
}

// ── Collection Interface ──────────────────────
interface BookCollection {
    void addBook(LibraryBook book);
    BookIterator iterator();
    BookIterator genreIterator(String genre); // filtered iterator
    int size();
}

// ── Concrete Collection ───────────────────────
class BookShelf implements BookCollection {
    private LibraryBook[] books;
    private int count;

    public BookShelf(int capacity) {
        books = new LibraryBook[capacity];
        count = 0;
    }

    @Override
    public void addBook(LibraryBook book) {
        if (count < books.length) books[count++] = book;
        else System.out.println("Shelf full. Cannot add: " + book.getTitle());
    }

    @Override public int size() { return count; }

    // ── All-books iterator ────────────────────
    @Override
    public BookIterator iterator() {
        return new BookShelfIterator(books, count, null);
    }

    // ── Genre-filtered iterator ───────────────
    @Override
    public BookIterator genreIterator(String genre) {
        return new BookShelfIterator(books, count, genre);
    }
}

// ── Concrete Iterator ─────────────────────────
class BookShelfIterator implements BookIterator {
    private final LibraryBook[] books;
    private final int           count;
    private final String        genreFilter; // null = no filter
    private int                 index;

    public BookShelfIterator(LibraryBook[] books, int count, String genreFilter) {
        this.books       = books;
        this.count       = count;
        this.genreFilter = genreFilter;
        this.index       = 0;
        advanceToNext();
    }

    /** Skip books that don't match the genre filter */
    private void advanceToNext() {
        while (index < count && !matches(books[index])) index++;
    }

    private boolean matches(LibraryBook book) {
        return genreFilter == null || book.getGenre().equalsIgnoreCase(genreFilter);
    }

    @Override
    public boolean hasNext() { return index < count; }

    @Override
    public LibraryBook next() {
        if (!hasNext()) throw new NoSuchElementException("No more books.");
        LibraryBook book = books[index++];
        advanceToNext();
        return book;
    }

    @Override
    public void reset() {
        index = 0;
        advanceToNext();
    }
}

public class Exercise10_IteratorPattern {
    public static void main(String[] args) {
        BookShelf shelf = new BookShelf(10);

        shelf.addBook(new LibraryBook("Clean Code",                  "Robert C. Martin", "Programming", 799));
        shelf.addBook(new LibraryBook("The Pragmatic Programmer",    "Hunt & Thomas",    "Programming", 849));
        shelf.addBook(new LibraryBook("Atomic Habits",               "James Clear",      "Self-Help",   499));
        shelf.addBook(new LibraryBook("Introduction to Algorithms",  "CLRS",             "Programming", 999));
        shelf.addBook(new LibraryBook("Deep Work",                   "Cal Newport",      "Self-Help",   399));
        shelf.addBook(new LibraryBook("Design Patterns",             "Gang of Four",     "Programming", 899));
        shelf.addBook(new LibraryBook("Ikigai",                      "Garcia & Miralles","Self-Help",   299));

        // ── Traverse all books ────────────────────
        System.out.println("=== All Books (" + shelf.size() + ") ===");
        BookIterator allIt = shelf.iterator();
        while (allIt.hasNext()) System.out.println("  " + allIt.next());

        // ── Traverse only Programming books ───────
        System.out.println("\n=== Programming Books Only ===");
        BookIterator progIt = shelf.genreIterator("Programming");
        while (progIt.hasNext()) System.out.println("  " + progIt.next());

        // ── Traverse only Self-Help books ─────────
        System.out.println("\n=== Self-Help Books Only ===");
        BookIterator selfIt = shelf.genreIterator("Self-Help");
        while (selfIt.hasNext()) System.out.println("  " + selfIt.next());

        // ── Reset and reuse ───────────────────────
        System.out.println("\n=== Reset and Re-traverse Programming ===");
        progIt.reset();
        double total = 0;
        while (progIt.hasNext()) total += progIt.next().getPrice();
        System.out.printf("Total cost of Programming books: ₹%.2f%n", total);

        System.out.println("\n--- Key Benefit ---");
        System.out.println("Client code uses the same iterator interface regardless of");
        System.out.println("whether the shelf is backed by an array, list, or tree.");
    }
}
