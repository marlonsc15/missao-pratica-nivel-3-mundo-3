package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastroBD {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();

        int opcao;
        do {
            System.out.println("\n===============");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Buscar pelo ID");
            System.out.println("5 - Exibir todos");
            System.out.println("0 - Finalizar Programa");
            System.out.println("===============");
            opcao = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcao) {
                    case 1 -> incluir(scanner, pfDao, pjDao);
                    case 2 -> alterar(scanner, pfDao, pjDao);
                    case 3 -> excluir(scanner, pfDao, pjDao);
                    case 4 -> exibirPorId(scanner, pfDao, pjDao);
                    case 5 -> exibirTodos(scanner, pfDao, pjDao);
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opcao invalida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro de banco de dados: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void incluir(Scanner scanner, PessoaFisicaDAO pfDao, PessoaJuridicaDAO pjDao) throws SQLException {
        System.out.print("F - Fisica | J - Juridica \n");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            PessoaFisica pf = new PessoaFisica(0,
                    solicitarInput(scanner, "Nome: "),
                    solicitarInput(scanner, "Logradouro: "),
                    solicitarInput(scanner, "Cidade: "),
                    solicitarInput(scanner, "Estado: "),
                    solicitarInput(scanner, "Telefone: "),
                    solicitarInput(scanner, "Email: "),
                    solicitarInput(scanner, "CPF: "));
            pfDao.incluir(pf);
            System.out.println("Pessoa Fisica incluida com sucesso!");

        } else if (tipo.equals("J")) {
            PessoaJuridica pj = new PessoaJuridica(0,
                    solicitarInput(scanner, "Nome: "),
                    solicitarInput(scanner, "Logradouro: "),
                    solicitarInput(scanner, "Cidade: "),
                    solicitarInput(scanner, "Estado: "),
                    solicitarInput(scanner, "Telefone: "),
                    solicitarInput(scanner, "Email: "),
                    solicitarInput(scanner, "CNPJ: "));
            pjDao.incluir(pj);
            System.out.println("Pessoa Juridica incluida com sucesso!");
        } else {
            System.out.println("Tipo invalido.");
        }
    }

    private static void alterar(Scanner scanner, PessoaFisicaDAO pfDao, PessoaJuridicaDAO pjDao) throws SQLException {
        System.out.print("F - Fisica | J - Juridica \n");
        String tipo = scanner.nextLine().toUpperCase();

        System.out.print("Informe o ID: \n");
        int id = Integer.parseInt(scanner.nextLine());

        if (tipo.equals("F")) {
            PessoaFisica pf = pfDao.getPessoa(id);
            if (pf == null) {
                System.out.println("Pessoa Fisica não encontrada.");
                return;
            }
            pf.exibir();
            pf.setNome(solicitarInput(scanner, "Novo Nome: "));
            pf.setLogradouro(solicitarInput(scanner, "Novo Logradouro: "));
            pf.setCidade(solicitarInput(scanner, "Nova Cidade: "));
            pf.setEstado(solicitarInput(scanner, "Novo Estado: "));
            pf.setTelefone(solicitarInput(scanner, "Novo Telefone: "));
            pf.setEmail(solicitarInput(scanner, "Novo Email: "));
            pf.setCpf(solicitarInput(scanner, "Novo CPF: "));
            pfDao.alterar(pf);
            System.out.println("Pessoa Fisica alterada com sucesso.");

        } else if (tipo.equals("J")) {
            PessoaJuridica pj = pjDao.getPessoa(id);
            if (pj == null) {
                System.out.println("Pessoa Juridica não encontrada.");
                return;
            }
            pj.exibir();
            pj.setNome(solicitarInput(scanner, "Novo Nome: "));
            pj.setLogradouro(solicitarInput(scanner, "Novo Logradouro: "));
            pj.setCidade(solicitarInput(scanner, "Nova Cidade: "));
            pj.setEstado(solicitarInput(scanner, "Novo Estado: "));
            pj.setTelefone(solicitarInput(scanner, "Novo Telefone: "));
            pj.setEmail(solicitarInput(scanner, "Novo Email: "));
            pj.setCnpj(solicitarInput(scanner, "Novo CNPJ: "));
            pjDao.alterar(pj);
            System.out.println("Pessoa Juridica alterada com sucesso.");
        } else {
            System.out.println("Tipo invalido.");
        }
    }

    private static void excluir(Scanner scanner, PessoaFisicaDAO pfDao, PessoaJuridicaDAO pjDao) throws SQLException {
        System.out.print("F - Fisica | J - Juridica \n");
        String tipo = scanner.nextLine().toUpperCase();

        System.out.print("Informe o ID: \n");
        int id = Integer.parseInt(scanner.nextLine());

        if (tipo.equals("F")) {
            pfDao.excluir(id);
            System.out.println("Pessoa Fisica excluída com sucesso.");
        } else if (tipo.equals("J")) {
            pjDao.excluir(id);
            System.out.println("Pessoa Juridica excluída com sucesso.");
        } else {
            System.out.println("Tipo invalido.");
        }
    }

    private static void exibirPorId(Scanner scanner, PessoaFisicaDAO pfDao, PessoaJuridicaDAO pjDao) throws SQLException {
        System.out.print("F - Fisica | J - Juridica \n");
        String tipo = scanner.nextLine().toUpperCase();

        System.out.print("Informe o ID: \n");
        int id = Integer.parseInt(scanner.nextLine());

        if (tipo.equals("F")) {
            PessoaFisica pf = pfDao.getPessoa(id);
            if (pf != null) {
                pf.exibir();
            } else {
                System.out.println("Pessoa Fisica não encontrada.");
            }
        } else if (tipo.equals("J")) {
            PessoaJuridica pj = pjDao.getPessoa(id);
            if (pj != null) {
                pj.exibir();
            } else {
                System.out.println("Pessoa Juridica não encontrada.");
            }
        } else {
            System.out.println("Tipo invalido.");
        }
    }

    private static void exibirTodos(Scanner scanner, PessoaFisicaDAO pfDao, PessoaJuridicaDAO pjDao) throws SQLException {
        System.out.print("F - Fisica | J - Juridica \n");
        String tipo = scanner.nextLine().toUpperCase();

        if (tipo.equals("F")) {
            for (PessoaFisica pf : pfDao.getPessoas()) {
                pf.exibir();
            }
        } else if (tipo.equals("J")) {
            for (PessoaJuridica pj : pjDao.getPessoas()) {
                pj.exibir();
            }
        } else {
            System.out.println("Tipo invalido.");
        }
    }

    private static String solicitarInput(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}