package library_managment;

import java.util.UUID;

public class Book {
    private UUID id;
    String title;
    String author;
    Boolean available = true;

    public Book(UUID id, String title, String author) {
        this.id = id;
        setTitle(title);
        setAuthor(author);
        setAvailable(available);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
