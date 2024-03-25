package library_managment;

import java.util.UUID;

public class Book {
    private UUID id;
    String title;
    String author;

    public Book(UUID id, String title, String author) {
        this.id = id;
        setTitle(title);
        setAuthor(author);
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
}
