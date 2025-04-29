package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/iudigital";
    private static final String USER = "bappco";
    private static final String PASSWORD = "1234567";
    
    public static Connection obtenerConexion() throws DAOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new DAOException("No se encontró el driver JDBC de MySQL", e);
        } catch (SQLException e) {
            throw new DAOException("Error al conectar con la base de datos MySQL", e);
        }
    }
}