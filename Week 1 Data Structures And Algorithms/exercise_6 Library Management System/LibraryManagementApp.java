package exercise_6;

import java.util.ArrayList;
import java.util.List;

public class LibraryManagementApp {
    public static void main(String[] args) {

        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book(101, "The Alchemist", "Paulo Coelho"));
        bookList.add(new Book(102, "Atomic Habits", "James Clear"));
        bookList.add(new Book(103, "The Hobbit", "J.R.R. Tolkien"));

        LibraryManager libraryManager = new LibraryManager(bookList);

        Book foundBook = libraryManager.linearSearchByTitle("Atomic Habits");

        if (foundBook != null) {
            System.out.println("Linear Search Found: " + foundBook.getTitle() + " by " + foundBook.getAuthor());
        } else {
            System.out.println("Book not found.");
        }

        libraryManager.sortBooksByTitle();

        foundBook = libraryManager.binarySearchByTitle("The Hobbit");

        if (foundBook != null) {
            System.out.println("Binary Search Found: " + foundBook.getTitle() + " by " + foundBook.getAuthor());
        } else {
            System.out.println("Book not found.");
        }
    }
}