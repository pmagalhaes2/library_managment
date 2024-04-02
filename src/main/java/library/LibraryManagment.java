package main.java.library;

import main.java.print.Print;

import java.util.Scanner;

//Library Management Program. This program offers functionalities for business administration and client management. It allows administrators to add, remove, and update books in the library, as well as manage user accounts. Standard users can search for books by title or author, borrow available books, and return borrowed books. Administrators have additional functionalities such as adding users, managing books, and lending books on behalf of standard users.
//@authors Katherine Uchoas Rodrigues, Maria Cristina Leal Geardini, Patricia Magalhães, Sophia Contesini, Thamires Candida Barbosa
//@version 1.0

public class LibraryManagment {
    UserManager userManager = new UserManager();
    BookManager bookManager = new BookManager();
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManagment libraryManagment = new LibraryManagment();
        libraryManagment.init();
    }

    public void init() {
        Print printLoginOptions = new Print();

        bookManager.initFileWriter();

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
                    userManager.addUser();
                    break;
                case 2:
                    if (userManager.login()) userManager.handleMenu();
                    option = 0;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente!");
                    break;
            }
        }

        sc.close();
        bookManager.closeWriter();
    }
}