package dao;

import exception.DAOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/funcionarios";
    private static final String USER = "iudigital";
    private static final String PASSWORD = "iudigital2025";
    
    public static Connection getConexion() throws DAOException {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            if(conexion.isClosed()) {
                throw new DAOException("La conexión se recibió cerrada");
            }
            return conexion;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new DAOException("Error al conectar a la base de datos: " + ex.getMessage());
        }
    }
    
    public static void cerrarConexion(Connection conexion) throws DAOException {
        if (conexion != null) {
            try {
                if(!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}