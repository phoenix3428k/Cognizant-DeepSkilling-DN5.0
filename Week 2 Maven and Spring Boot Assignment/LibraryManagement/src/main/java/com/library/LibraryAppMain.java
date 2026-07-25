package com.library;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.model.Book;
import com.library.service.BookService;

public class LibraryAppMain {

    public static void main(String[] args) {
        // Load Spring context from applicationContext.xml
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get the BookService bean
        BookService bookService = context.getBean("bookService", BookService.class);

        // Create new books (POJOs) and add them to the service
        Book book1 = new Book(1, "The Pragmatic Programmer", "Andrew Hunt", "978-0201616224", 50.0);

        Book book2 = new Book(2, "Head First Java", "Kathy Sierra", "978-0596009205", 40.0);

        Book book3 = new Book(3, "Introduction to Algorithms", "Thomas H. Cormen", "978-0262046305", 85.0);

        Book book4 = new Book(4, "Design Patterns", "Erich Gamma", "978-0201633610", 60.0);

        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        bookService.addBook(book4);

        // Retrieve and print all books
        System.out.println("List of books: " + bookService.getAllBooks());

        // Find and print a book by ID
        System.out.println("Book with ID 1: " + bookService.findBookById(1));

        // Remove a book by ID and print the result
        System.out.println("Removing book with ID 1: " + bookService.removeBookById(1));

        // Retrieve and print all books after removal
        System.out.println("List of books after removal: " + bookService.getAllBooks());

        // Close the Spring context
        context.close();
    }
}