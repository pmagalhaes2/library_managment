package library_managment;

import library_managment.utils.EmailValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import static library_managment.UserProfile.ADMIN;
import static library_managment.UserProfile.STANDARD;

public class LibraryManagment {
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<UserProfile> users = new ArrayList<>();
    UserProfile currentUserProfile;

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManagment libraryManagment = new LibraryManagment();
        libraryManagment.init();
    }

    public void init() {
        Print printUserOptions = new Print();
        users.add(new UserProfile("admin", "admin@admin.com", "admin123", UserProfile.ADMIN));

        books.add(new Book(UUID.randomUUID(), "Vidas Secas", "Graciliano Ramos"));
        books.add(new Book(UUID.randomUUID(), "Dom Casmurro", "Graciliano Ramos"));
        books.add(new Book(UUID.randomUUID(), "Dom Quixote", "Miguel de Cervantes"));


        int option = -1;

        while (option != 0) {
            printUserOptions.printUserOptions();

            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente!");
                continue;
            }

            if (option == 1 || option == 2) {
                login();
            }

            switch (option) {
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                case 1:
                    handleStandardUserMenu(sc);
                    break;
                case 2:
                    handleAdminMenu(sc);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        }

        sc.close();
    }

    private void handleStandardUserMenu(Scanner sc) {
        Print printStandard = new Print();
        int optionStandard;

        do {
            printStandard.printStandard();

            try {
                optionStandard = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente!");
                optionStandard = -1;
            }

            switch (optionStandard) {
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String title = sc.nextLine();
                    getBookByTitle(title);
                    break;
                case 2:
                    System.out.print("Digite o nome do autor: ");
                    String author = sc.nextLine();
                    getBooksByAuthor(author);
                    break;
                case 3:
                    getAllBooks();
                    break;
                case 4:
                    System.out.print("Digite o título do livro: ");
                    title = sc.nextLine();
                    Book foundedBook = getBookByTitle(title);
                    lendBook(foundedBook);
                    break;
                case 5:
                    System.out.print("Digite o título do livro: ");
                    title = sc.nextLine();
                    foundedBook = getBookByTitle(title);
                    returnBook(foundedBook);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        } while (optionStandard != 0);
    }

    private void handleAdminMenu(Scanner sc) {
        Print printAdmin = new Print();
        int optionAdmin;

        do {
            printAdmin.printAdmin();

            try {
                optionAdmin = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente!");
                optionAdmin = -1;
            }

            switch (optionAdmin) {
                case 0:
                    System.out.println("Saindo do programa...");
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
                case 5:
                    System.out.print("Digite o nome do autor: ");
                    author = sc.nextLine();
                    getBooksByAuthor(author);
                    break;
                case 6:
                    System.out.print("Digite o título do livro: ");
                    title = sc.nextLine();
                    Book foundedBook = getBookByTitle(title);
                    lendBook(foundedBook);

                    break;
                case 7:
                    if (currentUserProfile == STANDARD) {
                        System.out.print("Digite o título do livro: ");
                        title = sc.nextLine();
                        foundedBook = getBookByTitle(title);
                        returnBook(foundedBook);
                    } else {
                        System.out.println("Opção não disponível para usuários padrão.");
                    }
                    break;
                case 8:
                    addUser();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        } while (optionAdmin != 0);
    }



    private void login() {
        System.out.print("Digite o nome de usuário: ");
        String username = sc.nextLine();
        System.out.print("Digite a senha: ");
        String password = sc.nextLine();

        for (UserProfile user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUserProfile = user.getProfile();
                return;
            }
        }

        System.out.println("Usuário e/ou senha incorretos. Tente novamente.");
        login();
    }

    private void getAllBooks() {
        if (books.isEmpty()) {
            System.out.println("Não existem livros cadastrados!");
        } else {
            System.out.println("Livros cadastrados: ");
            for (Book book : books) {
                System.out.println(book.getTitle());
            }
        }
    }

    private void addUser() {
        System.out.print("Digite o nome do usuário: ");
        String userName = sc.nextLine();

        EmailValidation emailValidation = new EmailValidation();

        String userEmail;

        do {
            System.out.print("Digite o email do usuário: ");
            userEmail = sc.nextLine();
            if (userEmail == null || !EmailValidation.isValidEmail(userEmail)) {
                System.out.println("O email inserido é inválido. Por favor, tente novamente.");
            }
        } while (userEmail == null || !EmailValidation.isValidEmail(userEmail));


        String userPassword;
        do {
            System.out.print("Digite a senha do usuário (mínimo de 8 caracteres): ");
            userPassword = sc.nextLine();
            if (userPassword.length() < 8) {
                System.out.println("A senha deve ter no mínimo 8 caracteres. Por favor, tente novamente.");
            }
        } while (userPassword.length() < 8);

        Print printUserOptions = new Print();
        printUserOptions.printUserOptions();

        int profileOption;
        try {
            profileOption = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida. O usuário será criado como padrão.");
            profileOption = 2;
        }

        UserProfile profile;
        switch (profileOption) {
            case 1:
                profile = ADMIN;
                break;
            case 2:
            default:
                profile = STANDARD;
                break;
        }
        users.add(new UserProfile(userName, userEmail, userPassword, profile));
        return;
    }



    private void addBook(Book book) {
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

    private void removeBookByTitle(String bookName) {
        try {
            Book foundedBook = books.stream().filter(book -> book.getTitle().equals(bookName)).findFirst().get();
            books.remove(foundedBook);
            System.out.printf("Livro '%s' removido com sucesso!%n", foundedBook.getTitle());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao remover o livro: " + e.getMessage());
        }
    }

    private Book getBookByTitle(String bookName) {
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

    private List<Book> getBooksByAuthor(String author) {
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

    private void lendBook(Book book) {
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

    private void returnBook(Book book) {
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
