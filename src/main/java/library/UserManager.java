package main.java.library;

import main.java.book.Book;
import main.java.print.Print;
import main.java.userProfile.UserProfile;
import main.java.utils.email.EmailValidation;
import main.java.utils.password.PasswordValidation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserManager {
    Scanner sc = new Scanner(System.in);
    ArrayList<UserProfile> users = new ArrayList<>();
    UserProfile.UserType currentUserProfile;
    BookManager bookManager = new BookManager();

    public void addUser() {
        System.out.print("Insira o nome do usuário: ");
        String userName = sc.nextLine();

        String userEmail;

        do {
            System.out.print("Insira o e-mail do usuário: ");
            userEmail = sc.nextLine();

            if (!EmailValidation.isValidEmail(userEmail)) {
                System.out.println("E-mail inválido. Por favor, insira um email válido.");
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
                System.out.println("E-mail já cadastrado. Por favor, insira um e-mail diferente.");
                userEmail = null;
            }

        } while (userEmail == null);

        String userPassword;
        do {
            System.out.print("Insira a senha: ");
            userPassword = sc.nextLine();

            if (!PasswordValidation.isValidPassword(userPassword)) {
                System.out.println("A senha deve conter pelo menos 8 caracteres, sendo: 1 letra maiúscula, 1 letra minúscula, 1 caractere especial e 1 número.");
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

        users.add(new UserProfile(userName, userEmail, userPassword, profile));
    }

    public Boolean login() {
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

    public void handleMenu() {

        if (currentUserProfile.equals(UserProfile.UserType.ADMIN)) {
            handleAdminMenu();
        } else {
            handleStandardUserMenu();
        }
    }

    public void handleStandardUserMenu() {
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
                    bookManager.getBookByTitle(title);
                    break;
                case 2:
                    System.out.print("Insira o nome do autor: ");
                    String author = sc.nextLine();
                    bookManager.getBooksByAuthor(author);
                    break;
                case 3:
                    bookManager.getAllBooks();
                    break;
                case 4:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    Book foundedBook = bookManager.getBookByTitle(title);
                    bookManager.lendBook(foundedBook);
                    break;
                case 5:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    foundedBook = bookManager.getBookByTitle(title);
                    bookManager.returnBook(foundedBook);
                    break;
                case 6:
                    currentUserProfile = null;
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        } while (optionStandard != 0);
    }

    public void handleAdminMenu() {
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

                    bookManager.addBook(new Book(id, title, author));
                    break;
                case 2:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    bookManager.removeBookByTitle(title);
                    break;
                case 3:
                    bookManager.getAllBooks();
                    break;
                case 4:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    bookManager.getBookByTitle(title);
                    break;
                case 5:
                    System.out.print("Insira o nome do autor: ");
                    author = sc.nextLine();
                    bookManager.getBooksByAuthor(author);
                    break;
                case 6:
                    System.out.print("Insira o título do livro: ");
                    title = sc.nextLine();
                    Book foundedBook = bookManager.getBookByTitle(title);
                    bookManager.lendBook(foundedBook);
                    break;
                case 7:
                    if (currentUserProfile == UserProfile.UserType.STANDARD) {
                        System.out.print("Insira o título do livro: ");
                        title = sc.nextLine();
                        foundedBook = bookManager.getBookByTitle(title);
                        bookManager.returnBook(foundedBook);
                    } else {
                        System.out.println("Opção não disponível para usuários padrão.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        } while (optionAdmin != 0);
    }
}

