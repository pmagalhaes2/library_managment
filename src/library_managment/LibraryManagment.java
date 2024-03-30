package library_managment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import library_managment.ValidationUtil;





@SuppressWarnings("ALL")
public class LibraryManagment<ValidationUtil> {
    public ArrayList<Book> books = new ArrayList<>();
    public ArrayList<UserProfile> users = new ArrayList<>();
    public UserProfile currentUserProfile;


        public enum UserProfileType {
            ADMIN,
            STANDARD,
            ;


            public UserProfile getProfile() {
                return null;
            }

            public Object getUsername() {
                return null;
            }

            public boolean getPassword() {
                return false;
            }
        }

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
                        if (currentUserProfile == UserProfile.ADMIN) {
                            addBook();
                        } else {
                            System.out.println("Opção não disponível para usuários padrão.");
                        }
                        break;
                    case 2:
                        if (currentUserProfile == UserProfile.ADMIN) {
                            removeBookByTitle();
                        } else {
                            System.out.println("Opção não disponível para usuários padrão.");
                        }
                        break;
                    case 3:
                        getAllBooks();
                        break;
                    case 4:
                        searchByTitle();
                        break;
                    case 5:
                        searchByAuthor();
                        break;
                    case 6:
                        if (currentUserProfile == UserProfile.STANDARD) {
                            lendBook();
                        } else {
                            System.out.println("Opção não disponível para usuários padrão.");
                        }
                        break;
                    case 7:
                        if (currentUserProfile == UserProfile.STANDARD) {
                            returnBook();
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

            } while (option != 0);
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

        System.out.println("Usuário ou senha incorretos. Tente novamente.");
        login();
    }


    private void addBook() {
            System.out.print("Digite o título do livro: ");
            String title = sc.nextLine();
            System.out.print("Digite o autor do livro: ");
            String author = sc.nextLine();
            UUID id = UUID.randomUUID();

            addBook(new Book(id, title, author));
        }

        private void removeBookByTitle() {
            System.out.print("Digite o título do livro: ");
            String title = sc.nextLine();
            removeBookByTitle(title);
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

        private void searchByTitle() {
            System.out.print("Digite o título do livro que deseja encontrar: ");
            String title = sc.nextLine();
            getBookByTitle(title);
        }

        private void searchByAuthor() {
            System.out.print("Digite o Autor do livro que deseja encontrar: ");
            String author = sc.nextLine();
            getBooksByAuthor(author);
        }

        private void lendBook() {
            System.out.print("Digite o título do livro que deseja emprestar: ");
            String title = sc.nextLine();
            Book foundedBook = getBookByTitle(title);
            lendBook(foundedBook);
        }

        private void returnBook() {
            System.out.print("Digite o título do livro a ser devolvido: ");
            String title = sc.nextLine();
            Book foundedBook = getBookByTitle(title);
            returnBook(foundedBook);
        }

    private void addUser() {
        System.out.print("Digite o nome do usuário: ");
        String userName = sc.nextLine();

        String userEmail;
        library_managment.ValidationUtil validationUtil = new library_managment.ValidationUtil();
        do {
            System.out.print("Digite o email do usuário: ");
            userEmail = sc.nextLine();
            if (!validationUtil.isValidEmail(userEmail)) {
                System.out.println("O email inserido é inválido. Por favor, tente novamente.");
            }
        } while (!validationUtil.isValidEmail(userEmail));



        String userPassword;
        do {
            System.out.print("Digite a senha do usuário (mínimo de 8 caracteres): ");
            userPassword = sc.nextLine();
            if (userPassword.length() < 8) {
                System.out.println("A senha deve ter no mínimo 8 caracteres. Por favor, tente novamente.");
            }
        } while (userPassword.length() < 8);

        System.out.println("Escolha o perfil do usuário:");
        System.out.println("1. ADMIN");
        System.out.println("2. STANDARD");
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
                profile = UserProfile.ADMIN;
                break;
            case 2:
            default:
                profile = UserProfile.STANDARD;
                break;
        }
        users.add(new UserProfile(userName, userEmail, userPassword, profile));
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

        private List<Book> getBooksByAuthor(String bookAuthor) {
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

