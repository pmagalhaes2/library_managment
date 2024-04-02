package main.java.libraryManagment;

import main.java.book.Book;
import main.java.print.Print;
import main.java.userProfile.UserProfile;
import main.java.utils.email.EmailValidation;
import main.java.utils.password.PasswordValidation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

//Library Management Program. This program offers functionalities for business administration and client management. It allows administrators to add, remove, and update books in the library, as well as manage user accounts. Standard users can search for books by title or author, borrow available books, and return borrowed books. Administrators have additional functionalities such as adding users, managing books, and lending books on behalf of standard users.
//@authors Katherine Uchoas Rodrigues, Maria Cristina Leal Geardini, Patricia Magalhães, Sophia Contesini, Thamires Candida Barbosa
//@version 1.0

public class LibraryManagment {
    public ArrayList<Book> books = new ArrayList<>();
    public ArrayList<UserProfile> users = new ArrayList<>();
    public UserProfile.UserType currentUserProfile;
    public FileWriter writer;

    public FileWriter userwriter;

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManagment libraryManagment = new LibraryManagment();
        libraryManagment.init();
    }

    public void init() {
        Print printLoginOptions = new Print();

        books.add(new Book(UUID.randomUUID(), "Vidas Secas", "Graciliano Ramos"));
        books.add(new Book(UUID.randomUUID(), "Dom Casmurro", "Machado de Assis"));
        books.add(new Book(UUID.randomUUID(), "Dom Quixote", "Miguel de Cervantes"));

        try {
            writer = new FileWriter("books.txt");
            for (Book book : books) {
                writer.write(book.toString() + System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo books.txt: " + e.getMessage());
        }

        int option = -1;

        while (option != 0) {
            printLoginOptions.printLoginOptions();

            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente!");
                continue;
            }

            switch (option) {
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                case 1:
                    addUser();
                    break;
                case 2:
                    if (login()) handleMenu(currentUserProfile);
                    option = 0;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        }

        sc.close();
        closeWriter();
    }

   private void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Erro ao fechar o FileWriter: " + e.getMessage());
        }
    }

    private void handleMenu(UserProfile.UserType userType) {
        if (userType.equals(UserProfile.UserType.ADMIN)) {
            handleAdminMenu(sc);
        } else {
            handleStandardUserMenu(sc);
        }
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
                    System.out.print("Insira o título do livro: ");
                    String title = sc.nextLine();
                    getBookByTitle(title);
                    break;
                case 2:
                    System.out.print("Insira o nome do autor: ");
                    String author = sc.nextLine();
                    getBooksByAuthor(author);
                    break;
                case 3:
                    getAllBooks();
                    break;
                case 4:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    Book foundedBook = getBookByTitle(title);
                    lendBook(foundedBook);
                    break;
                case 5:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    foundedBook = getBookByTitle(title);
                    returnBook(foundedBook);
                    break;
                case 9:
                    currentUserProfile = null;
                    return;
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
                    System.out.print("Insira o título do livro: ");
                    String title = sc.nextLine();
                    System.out.print("Insira o autor do livro: ");
                    String author = sc.nextLine();
                    UUID id = UUID.randomUUID();

                    addBook(new Book(id, title, author));
                    break;
                case 2:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    removeBookByTitle(title);
                    break;
                case 3:
                    getAllBooks();
                    break;
                case 4:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    getBookByTitle(title);
                    break;
                case 5:
                    System.out.print("Insira o nome do autor: ");
                    author = sc.nextLine();
                    getBooksByAuthor(author);
                    break;
                case 6:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    Book foundedBook = getBookByTitle(title);
                    lendBook(foundedBook);
                    break;
                case 7:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    foundedBook = getBookByTitle(title);
                    returnBook(foundedBook);
                    break;
                case 8:
                    addUser();
                    break;
                case 9:
                    currentUserProfile = null;
                    System.out.println("Usuário desconectado. Retornando ao menu principal...");
                    init();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        } while (optionAdmin != 0);
    }

    private Boolean login() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        do {
            System.out.print("Insira o e-mail: ");
            String email = sc.nextLine();
            System.out.print("Insira a senha: ");
            String password = sc.nextLine();

            UserProfile existingUser = users.stream().filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password)).findFirst().orElse(null);

            if (existingUser != null) {
                currentUserProfile = existingUser.getUserType();
                return true;
            }

            attempts++;

            if (MAX_ATTEMPTS - attempts > 0) {
                System.out.printf("E-mail e/ou senha incorretos. Você possui %d tentativas!%n", MAX_ATTEMPTS - attempts);
            }

        } while (attempts < MAX_ATTEMPTS);

        System.out.println("Quantidade de tentativas excedidas. O login não foi bem sucedido!");
        return false;
    }

    private void addUser() {
        System.out.print("Insira o nome do usuário: ");
        String userName = sc.nextLine();

        String userEmail;

        do {
            System.out.print("Insira o email do usuário: ");
            userEmail = sc.nextLine();

            if (!EmailValidation.isValidEmail(userEmail)) {
                System.out.println("Email inválido. Por favor, insira um email válido.");
                userEmail = null;
            }

            boolean emailExists = false;
            for (UserProfile user : users) {
                if (user.getEmail().equals(userEmail)) {
                    emailExists = true;
                    break;
                }
            }
            if (emailExists) {
                System.out.println("Este email já está sendo usado. Por favor, insira um email diferente.");
                userEmail = null;
            }

        } while (userEmail == null);

        String userPassword;
        do {
            System.out.print("A senha deve conter pelo menos 8 caracteres, sendo: 1 letra maiúscula, 1 letra minúscula, 1 caractere especial, 1 número:   ");
            userPassword = sc.nextLine();

            if (!PasswordValidation.isValidPassword(userPassword)) {
                System.out.println("A senha não atende aos critérios mínimos de segurança.");
            }
        } while (!PasswordValidation.isValidPassword(userPassword));

        UserProfile.UserType profile;
        int profileOption;
        try {
            System.out.print("Escolha o perfil do usuário (1 - Bibliotecário | 2 - Leitor): ");
            profileOption = Integer.parseInt(sc.nextLine());
            profile = (profileOption == 1) ? UserProfile.UserType.ADMIN : UserProfile.UserType.STANDARD;
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida. O usuário será criado como padrão.");
            profile = UserProfile.UserType.STANDARD;
        }

        UserProfile newUser = new UserProfile(userName, userEmail, userPassword, profile);

        users.add(newUser);

        updateUserFile();
    }


    private void addBook(Book book) {
        boolean existingBook = books.stream().anyMatch(registeredBook -> registeredBook.getTitle().equals(book.getTitle()));
        if (existingBook) {
            System.out.printf("Livro '%s' já existe cadastrado na biblioteca.%n", book.getTitle());
        } else {
            try {
                books.add(book);
                writer.write(book.toString() + System.lineSeparator());
                writer.flush();
                System.out.printf("Livro '%s' adicionado com sucesso!%n", book.getTitle());
            } catch (IOException e) {
                System.out.println("Ocorreu um erro ao adicionar o livro no arquivo: " + e.getMessage());
            }
        }
    }

    private void removeBookByTitle(String bookName) {
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

    private void updateBooksFile() {
        try {
            writer = new FileWriter("books.txt");
            for (Book book : books) {
                writer.write(book.toString() + System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo books.txt: " + e.getMessage());
        }
    }

    private void updateUserFile() {
        try {
            userwriter = new FileWriter("users.txt");
            for (UserProfile user : users) {
                userwriter.write(user + System.lineSeparator());
            }
            userwriter.flush();
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo users.txt: " + e.getMessage());
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