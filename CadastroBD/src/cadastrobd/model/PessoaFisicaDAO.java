package cadastrobd.model;

import cadastro.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;

public class PessoaFisicaDAO {

    public PessoaFisica getPessoa(int idPessoa) {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, f.cpf " +
                     "FROM Pessoa p JOIN PessoaFisica f ON p.idPessoa = f.idPessoa WHERE p.idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPessoa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                            rs.getInt("idPessoa"),
                            rs.getString("nome"),
                            rs.getString("logradouro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cpf")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa fisica: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<PessoaFisica> getPessoas() {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, f.cpf " +
                     "FROM Pessoa p JOIN PessoaFisica f ON p.idPessoa = f.idPessoa";
        ArrayList<PessoaFisica> lista = new ArrayList<>();
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new PessoaFisica(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoas fisicas: " + e.getMessage());
        }
        return lista;
    }

    public boolean incluir(PessoaFisica pf) {
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFisica = "INSERT INTO PessoaFisica (idPessoa, cpf) VALUES (?, ?)";
        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                psPessoa.setString(1, pf.getNome());
                psPessoa.setString(2, pf.getLogradouro());
                psPessoa.setString(3, pf.getCidade());
                psPessoa.setString(4, pf.getEstado());
                psPessoa.setString(5, pf.getTelefone());
                psPessoa.setString(6, pf.getEmail());
                psPessoa.executeUpdate();

                try (ResultSet rs = psPessoa.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPessoa = rs.getInt(1);
                        pf.setId(idPessoa);

                        try (PreparedStatement psFisica = conn.prepareStatement(sqlFisica)) {
                            psFisica.setInt(1, idPessoa);
                            psFisica.setString(2, pf.getCpf());
                            psFisica.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao incluir pessoa fisica: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(PessoaFisica pf) {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlFisica = "UPDATE PessoaFisica SET cpf = ? WHERE idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
                 PreparedStatement psFisica = conn.prepareStatement(sqlFisica)) {

                psPessoa.setString(1, pf.getNome());
                psPessoa.setString(2, pf.getLogradouro());
                psPessoa.setString(3, pf.getCidade());
                psPessoa.setString(4, pf.getEstado());
                psPessoa.setString(5, pf.getTelefone());
                psPessoa.setString(6, pf.getEmail());
                psPessoa.setInt(7, pf.getId());
                psPessoa.executeUpdate();

                psFisica.setString(1, pf.getCpf());
                psFisica.setInt(2, pf.getId());
                psFisica.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar pessoa fisica: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idPessoa) {
        String sqlFisica = "DELETE FROM PessoaFisica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psFisica = conn.prepareStatement(sqlFisica);
                 PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {

                psFisica.setInt(1, idPessoa);
                psFisica.executeUpdate();

                psPessoa.setInt(1, idPessoa);
                psPessoa.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pessoa fisica: " + e.getMessage());
            return false;
        }
    }
}