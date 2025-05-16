package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import java.sql.SQLException;

public class CadastroBDTeste {

    public static void main(String[] args) {

       
            PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
            PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();

            System.out.println("==== TESTE PESSOA FÍSICA ====");
            PessoaFisica pf = new PessoaFisica(0, "Carlos Silva", "Rua A", "Cidade A", "Estado A", "111111111", "carlos@email.com", "12345678901");
            pfDao.incluir(pf);

            System.out.println("\n--- Alterando pessoa física ---");
            pf.setNome("Carlos Silva Alterado");
            pf.setEmail("carlos.alterado@email.com");
            pfDao.alterar(pf);

            System.out.println("\n--- Listando todas as pessoas físicas ---");
            for (PessoaFisica p : pfDao.getPessoas()) {
               
            }

            System.out.println("\n--- Excluindo pessoa física ---");
            pfDao.excluir(pf.getId());

            System.out.println("\n==== TESTE PESSOA JURÍDICA ====");
            PessoaJuridica pj = new PessoaJuridica(0, "Empresa XYZ", "Rua B", "Cidade B", "Estado B", "222222222", "contato@empresa.com", "12345678000101");
            pjDao.incluir(pj);

            System.out.println("\n--- Alterando pessoa jurídica ---");
            pj.setNome("Empresa XYZ Alterada");
            pj.setEmail("contato@empresaalterada.com");
            pjDao.alterar(pj);

            System.out.println("\n--- Listando todas as pessoas jurídicas ---");
            for (PessoaJuridica p : pjDao.getPessoas()) {
            }

            System.out.println("\n--- Excluindo pessoa jurídica ---");
            pjDao.excluir(pj.getId());

        
    }
}