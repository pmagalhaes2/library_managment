package library_managment;

public class Print {

    public void printLoginOptions() {
        System.out.println("+ -------------------------------------+");
        System.out.println("|               BIBLIOTECA             |");
        System.out.println("+ -------------------------------------+");
        System.out.println("| Opção 1 - Cadastro                   |");
        System.out.println("| Opção 2 - Login                      |");
        System.out.println("| Opção 0 - Encerrar programa          |");
        System.out.println("+ -------------------------------------+");

        System.out.print("Insira aqui sua opção: ");
    }

    public void printAdmin() {
        System.out.println("+ -------------------------------------+");
        System.out.println("|        PERFIL DO ADMINISTRADOR       |");
        System.out.println("+ -------------------------------------+");
        System.out.println("| Opção 1 - Adicionar livro            |");
        System.out.println("| Opção 2 - Remover livro              |");
        System.out.println("| Opção 3 - Listar livros cadastrados  |");
        System.out.println("| Opção 4 - Buscar livro pelo título   |");
        System.out.println("| Opção 5 - Buscar livro pelo Autor    |");
        System.out.println("| Opção 6 - Emprestar livro            |");
        System.out.println("| Opção 7 - Devolver livro             |");
        System.out.println("| Opção 8 - Cadastrar usuário          |");
        System.out.println("| Opção 9 - Retornar ao menu principal |");
        System.out.println("| Opção 0 - Encerrar programa          |");
        System.out.println("+ -------------------------------------+");

        System.out.print("Insira aqui sua opção: ");
    }

    public void printStandard() {
        System.out.println("+ -------------------------------------+");
        System.out.println("|            PERFIL DO LEITOR          |");
        System.out.println("+ -------------------------------------+");
        System.out.println("| Opção 1 - Buscar livro pelo título   |");
        System.out.println("| Opção 2 - Buscar livro pelo Autor    |");
        System.out.println("| Opção 3 - Listar livros cadastrados  |");
        System.out.println("| Opção 4 - Pegar livro emprestado     |");
        System.out.println("| Opção 5 - Devolver livro             |");
        System.out.println("| Opção 0 - Encerrar programa          |");
        System.out.println("+ -------------------------------------+");

        System.out.print("Insira aqui sua opção: ");
    }
}

