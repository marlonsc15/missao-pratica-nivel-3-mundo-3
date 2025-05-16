package cadastrobd.model;

import cadastro.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;

public class PessoaJuridicaDAO {

    public PessoaJuridica getPessoa(int idPessoa) {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, j.cnpj " +
                     "FROM Pessoa p JOIN PessoaJuridica j ON p.idPessoa = j.idPessoa WHERE p.idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPessoa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaJuridica(
                            rs.getInt("idPessoa"),
                            rs.getString("nome"),
                            rs.getString("logradouro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cnpj")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa juridica: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<PessoaJuridica> getPessoas() {
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, j.cnpj " +
                     "FROM Pessoa p JOIN PessoaJuridica j ON p.idPessoa = j.idPessoa";
        ArrayList<PessoaJuridica> lista = new ArrayList<>();
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new PessoaJuridica(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoas juridicas: " + e.getMessage());
        }
        return lista;
    }

    public boolean incluir(PessoaJuridica pj) {
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlJuridica = "INSERT INTO PessoaJuridica (idPessoa, cnpj) VALUES (?, ?)";
        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                psPessoa.setString(1, pj.getNome());
                psPessoa.setString(2, pj.getLogradouro());
                psPessoa.setString(3, pj.getCidade());
                psPessoa.setString(4, pj.getEstado());
                psPessoa.setString(5, pj.getTelefone());
                psPessoa.setString(6, pj.getEmail());
                psPessoa.executeUpdate();

                try (ResultSet rs = psPessoa.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPessoa = rs.getInt(1);
                        pj.setId(idPessoa);

                        try (PreparedStatement psJuridica = conn.prepareStatement(sqlJuridica)) {
                            psJuridica.setInt(1, idPessoa);
                            psJuridica.setString(2, pj.getCnpj());
                            psJuridica.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao incluir pessoa juridica: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(PessoaJuridica pj) {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
                 PreparedStatement psJuridica = conn.prepareStatement(sqlJuridica)) {

                psPessoa.setString(1, pj.getNome());
                psPessoa.setString(2, pj.getLogradouro());
                psPessoa.setString(3, pj.getCidade());
                psPessoa.setString(4, pj.getEstado());
                psPessoa.setString(5, pj.getTelefone());
                psPessoa.setString(6, pj.getEmail());
                psPessoa.setInt(7, pj.getId());
                psPessoa.executeUpdate();

                psJuridica.setString(1, pj.getCnpj());
                psJuridica.setInt(2, pj.getId());
                psJuridica.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar pessoa juridica: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idPessoa) {
        String sqlJuridica = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";
        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psJuridica = conn.prepareStatement(sqlJuridica);
                 PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {

                psJuridica.setInt(1, idPessoa);
                psJuridica.executeUpdate();

                psPessoa.setInt(1, idPessoa);
                psPessoa.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pessoa juridica: " + e.getMessage());
            return false;
        }
    }
}