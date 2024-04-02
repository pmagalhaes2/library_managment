package main.java.library;

import main.java.book.Book;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {
    private ArrayList<Book> books = new ArrayList<>();
    public FileWriter writer;

    public void initFileWriter() {
        try {
            writer = new FileWriter("books.txt", true);
            for (Book book : books) {
                writer.write(book.toString() + System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo books.txt: " + e.getMessage());
        }
    }

    public void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Erro ao fechar o FileWriter: " + e.getMessage());
        }
    }

    private void updateBooksFile() {
        try {
            writer = new FileWriter("books.txt", true);
            for (Book book : books) {
                writer.write(book.toString() + System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo books.txt: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        boolean existingBook = books.stream().anyMatch(registeredBook -> registeredBook.getTitle().equals(book.getTitle()));
        if (existingBook) {
            System.out.printf("Livro '%s' já existe cadastrado na biblioteca.%n", book.getTitle());
        } else {
            try {
                books.add(book);
                if (writer != null) {
                    writer.write(book.toString() + System.lineSeparator());
                    writer.flush();
                } else {
                    updateBooksFile();
                }
                System.out.printf("Livro '%s' adicionado com sucesso!%n", book.getTitle());
            } catch (IOException e) {
                System.out.println("Ocorreu um erro ao adicionar o livro no arquivo: " + e.getMessage());
            }
        }
    }

    public void removeBookByTitle(String bookName) {
        try {
            Book foundedBook = books.stream().filter(book -> book.getTitle().equals(bookName)).findFirst().orElse(null);
            if (foundedBook != null) {
                books.remove(foundedBook);
                updateBooksFile();
                System.out.printf("Livro '%s' removido com sucesso!%n", foundedBook.getTitle());
            } else {
                System.out.println("Livro não encontrado na biblioteca.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao remover o livro: " + e.getMessage());
        }
    }

    public Book getBookByTitle(String bookName) {
        try {
            Book foundedBook = books.stream().filter(book -> book.getTitle().equals(bookName)).findFirst().get();
            String available = foundedBook.getAvailable() ? "Disponível" : "Indisponível";
            System.out.printf("Título: %s - Autor: %s - Status: %s%n", foundedBook.getTitle(), foundedBook.getAuthor(), available);
            return foundedBook;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao localizar o livro: " + e.getMessage());
        }
        return null;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> authorBooks = books.stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());

        if (authorBooks.isEmpty()) {
            System.out.println("Não foram encontrados livros para o autor: " + author);
        } else {
            System.out.printf("Livros encontrados para o autor %s:%n", author);
            for (Book book : authorBooks) {
                System.out.printf("Título: %s - Autor: %s%n", book.getTitle(), book.getAuthor());
            }
        }
        return authorBooks;
    }

    public void getAllBooks() {
        if (books.isEmpty()) {
            System.out.println("Não existem livros cadastrados!");
        } else {
            System.out.println("Livros cadastrados: ");
            for (Book book : books) {
                System.out.println(book.getTitle());
            }
        }
    }

    public void lendBook(Book book) {
        try {
            if (book.getAvailable()) {
                book.setAvailable(false);
                System.out.printf("Livro '%s' emprestado com sucesso!%n", book.getTitle());
            } else {
                System.out.printf("Livro '%s' não está disponível para empréstimo!%n", book.getTitle());
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao emprestar o livro: " + e.getMessage());
        }
    }

    public void returnBook(Book book) {
        try {
            if (!(book.getAvailable())) {
                book.setAvailable(true);
                System.out.printf("Livro '%s' devolvido com sucesso!%n", book.getTitle());
            } else {
                System.out.printf("Livro '%s' já consta disponível para empréstimo!%n", book.getTitle());
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao devolver o livro: " + e.getMessage());
        }
    }
}
