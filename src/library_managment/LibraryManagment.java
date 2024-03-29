package library_managment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class LibraryManagment {
    public ArrayList<Book> books = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManagment libraryManagment = new LibraryManagment();
        libraryManagment.init();
    }

    public void init() {
        int option;
        Print printMenu = new Print();

        books.add(new Book(UUID.randomUUID(), "Vidas Secas", "Graciliano Ramos"));
        books.add(new Book(UUID.randomUUID(), "Dom Casmurro", "Graciliano Ramos"));
        books.add(new Book(UUID.randomUUID(), "Dom Quixote", "Miguel de Cervantes"));

        do {
            printMenu.printMenu();

            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente!");
                option = -1;
            }

            switch (option) {
                case 0:
                    sc.close();
                    break;
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String title = sc.nextLine();
                    System.out.print("Digite o autor do livro: ");
                    String author = sc.nextLine();
                    UUID id = UUID.randomUUID();

                    addBook(new Book(id, title, author));
                    break;
                case 2:
                    System.out.print("Digite o título do livro: ");
                    title = sc.nextLine();
                    removeBookByTitle(title);
                    break;
                case 3:
                    getAllBooks();
                    break;
                case 4:
                    System.out.print("Digite o título do livro que deseja encontrar: ");
                    title = sc.nextLine();
                    getBookByTitle(title);
                    break;
                case 5:
                    System.out.print("Digite o Autor do livro que deseja encontrar: ");
                    author = sc.nextLine();
                    getBooksByAuthor(author);
                    break;
                case 6:
                    System.out.print("Digite o título do livro que deseja emprestar: ");
                    title = sc.nextLine();
                    Book foundedBook = getBookByTitle(title);
                    lendBook(foundedBook);
                    break;
                case 7:
                    System.out.print("Digite o título do livro a ser devolvido: ");
                    title = sc.nextLine();
                    foundedBook = getBookByTitle(title);
                    returnBook(foundedBook);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
            }

        } while (option != 0);
    }

    public void addBook(Book book) {
        boolean existingBook = books.stream().anyMatch(registeredBook -> registeredBook.getTitle().equals(book.getTitle()));
        if (existingBook) {
            System.out.printf("Livro '%s' já existe cadastrado na biblioteca.%n", book.getTitle());
        } else {
            try {
                books.add(book);
                System.out.printf("Livro '%s' adicionado com sucesso!%n", book.getTitle());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro ao adicionar o livro: %n" + e.getMessage());
            }
        }
    }

    public void removeBookByTitle(String bookName) {
        try {
            Book foundedBook = books.stream().filter(book -> book.getTitle().equals(bookName)).findFirst().get();
            books.remove(foundedBook);
            System.out.printf("Livro '%s' removido com sucesso!%n", foundedBook.getTitle());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao remover o livro: " + e.getMessage());
        }
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

    public List<Book> getBooksByAuthor(String bookAuthor) {
        List<Book> authorBooks = books.stream()
                .filter(book -> book.getAuthor().equals(bookAuthor))
                .collect(Collectors.toList());

        if (authorBooks.isEmpty()) {
            System.out.println("Não foram encontrados livros para o autor: " + bookAuthor);
        } else {
            System.out.printf("Livros encontrados para o autor %s:%n", bookAuthor);
            for (Book book : authorBooks) {
                System.out.printf("Título: %s - Autor: %s%n", book.getTitle(), book.getAuthor());
            }
        }
        return authorBooks;
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
