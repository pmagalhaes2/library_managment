package library_managment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Book> borrowedBooks;

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getter and Setter methods for User attributes
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Password should not be accessible directly
    // as it's sensitive information
    // If necessary, you can add methods to handle password securely

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Method to add a book to user's borrowed books
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Method to remove a book from user's borrowed books
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
