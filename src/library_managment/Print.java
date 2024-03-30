package library_managment;

public class Print {
    public void printMenu() {
        System.out.println("+ -------------------------------------+");
        System.out.println("|    Sistema de Gestão de Biblioteca   |");
        System.out.println("+ -------------------------------------+");
        System.out.println("| Opção 1 - Adicionar livro            |");
        System.out.println("| Opção 2 - Remover livro              |");
        System.out.println("| Opção 3 - Listar livros cadastrados  |");
        System.out.println("| Opção 4 - Buscar livro pelo título   |");
        System.out.println("| Opção 5 - Buscar livro pelo Autor    |");
        System.out.println("| Opção 6 - Emprestar livro            |");
        System.out.println("| Opção 7 - Devolver livro             |");
        System.out.println("| Opção 8 - Cadastrar usuário          |");
        System.out.println("| Opção 0 - Encerrar programa          |");
        System.out.println("+ -------------------------------------+");

        System.out.print("Digite aqui sua opção: ");
    }
}

