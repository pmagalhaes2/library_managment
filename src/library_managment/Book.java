package library_managment;

import java.util.UUID;

public class Book {
    UUID id;
    String title;
    String author;

    public Book(UUID id, String title, String author) {
        setId(id);
        setTitle(title);
        setAuthor(author);
    }


    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
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
