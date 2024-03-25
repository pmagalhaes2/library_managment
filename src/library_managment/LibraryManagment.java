package library_managment;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class LibraryManagment {
    static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        int option;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("+ -------------------------------------+");
            System.out.println("|    Sistema de Gestão de Biblioteca   |");
            System.out.println("+ -------------------------------------+");
            System.out.println("| Opção 1 - Adicionar livro            |");
            System.out.println("| Opção 2 - Remover livro              |");
            System.out.println("| Opção 3 - Listar livros cadastrados  |");
            System.out.println("| Opção 4 - Buscar livro pelo título   |");
            System.out.println("| Opção 0 - Encerrar programa          |");
            System.out.println("+ -------------------------------------+");

            System.out.print("Digite aqui sua opção: ");

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
                    System.out.print("Digite o título do livro: ");
                    title = sc.nextLine();
                    getBookByTitle(title);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
            }

        } while (option != 0);
    }

    public static void addBook(Book book) {
        try {
            books.add(book);
            System.out.printf("Livro '%s' adicionado com sucesso!", book.getTitle());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao adicionar o livro: " + e.getMessage());
        }
    }

    public static void removeBookByTitle(String bookName) {
        try {
            Book foundedBook = books.stream().filter(book -> book.getTitle().equals(bookName)).findFirst().get();
            books.remove(foundedBook);
            System.out.printf("Livro '%s' removido com sucesso!%n", foundedBook.getTitle());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao remover o livro: " + e.getMessage());
        }
    }

    public static void getAllBooks() {
        if (books.isEmpty()) {
            System.out.println("Não existem livros cadastrados!");
        } else {
            System.out.println("Livros cadastrados: ");
            for (Book book : books) {
                System.out.println(book.getTitle());
            }
        }
    }

    public static void getBookByTitle(String bookName) {
        try {
            Book foundedBook = books.stream().filter(book -> book.getTitle().equals(bookName)).findFirst().get();

            System.out.printf("Título: %s - Autor: %s", foundedBook.getTitle(), foundedBook.getAuthor());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao localizar o livro: " + e.getMessage());
        }
    }
}
