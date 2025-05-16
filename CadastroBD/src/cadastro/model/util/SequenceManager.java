package cadastro.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {

    public static int getValue(String nomeSequence) {
        String sql = "SELECT NEXT VALUE FOR " + nomeSequence;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int valor = -1;

        try {
            conn = ConectorBD.getConnection();
            ps = ConectorBD.getPrepared(conn, sql);
            rs = ConectorBD.getSelect(ps);
            if (rs != null && rs.next()) {
                valor = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar valor da sequência: " + e.getMessage());
        } finally {
            ConectorBD.close(rs);
            ConectorBD.close(ps);
            ConectorBD.close(conn);
        }

        return valor;
    }
}