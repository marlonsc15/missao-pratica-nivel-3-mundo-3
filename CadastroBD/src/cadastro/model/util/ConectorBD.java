package cadastro.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectorBD {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "loja";
    private static final String SENHA = "loja";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        }
    }

    public static PreparedStatement getPrepared(Connection conn, String sql) {
        try {
            return conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a consulta: " + e.getMessage());
            return null;
        }
    }

    public static ResultSet getSelect(PreparedStatement ps) {
        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
            return null;
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar ResultSet: " + e.getMessage());
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar Statement: " + e.getMessage());
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar Connection: " + e.getMessage());
            }
        }
    }
}