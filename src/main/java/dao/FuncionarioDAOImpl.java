package dao;

import exception.DAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Funcionario;

public class FuncionarioDAOImpl implements FuncionarioDAO {
    @Override
    public void insertar(Funcionario funcionario) throws DAOException {
        String sql = "INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, apellidos, " +
                     "estado_civil, sexo, direccion, telefono, fecha_nacimiento) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, funcionario.getTipoIdentificacion());
            stmt.setString(2, funcionario.getNumeroIdentificacion());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setString(5, funcionario.getEstadoCivil());
            stmt.setString(6, funcionario.getSexo());
            stmt.setString(7, funcionario.getDireccion());
            stmt.setString(8, funcionario.getTelefono());
            stmt.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al insertar, ninguna fila afectada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    funcionario.setIdFuncionario(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Error al insertar, no se obtuvo el ID generado.");
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al insertar funcionario: " + ex.getMessage());
        }
    }

    @Override
    public void actualizar(Funcionario funcionario) throws DAOException {
        String sql = "UPDATE funcionario SET tipo_identificacion = ?, numero_identificacion = ?, nombres = ?, " +
                     "apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? " +
                     "WHERE id_funcionario = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, funcionario.getTipoIdentificacion());
            stmt.setString(2, funcionario.getNumeroIdentificacion());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setString(5, funcionario.getEstadoCivil());
            stmt.setString(6, funcionario.getSexo());
            stmt.setString(7, funcionario.getDireccion());
            stmt.setString(8, funcionario.getTelefono());
            stmt.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            stmt.setInt(10, funcionario.getIdFuncionario());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar funcionario: " + ex.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar funcionario: " + ex.getMessage());
        }
    }

    @Override
    public Funcionario obtenerPorId(int id) throws DAOException {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        Funcionario funcionario = null;
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                    funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                    funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                    funcionario.setNombres(rs.getString("nombres"));
                    funcionario.setApellidos(rs.getString("apellidos"));
                    funcionario.setEstadoCivil(rs.getString("estado_civil"));
                    funcionario.setSexo(rs.getString("sexo"));
                    funcionario.setDireccion(rs.getString("direccion"));
                    funcionario.setTelefono(rs.getString("telefono"));
                    funcionario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al obtener funcionario por ID: " + ex.getMessage());
        }
        
        return funcionario;
    }

    @Override
    public List<Funcionario> obtenerTodos() throws DAOException {
        String sql = "SELECT * FROM funcionario ORDER BY apellidos, nombres";
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                funcionario.setNombres(rs.getString("nombres"));
                funcionario.setApellidos(rs.getString("apellidos"));
                funcionario.setEstadoCivil(rs.getString("estado_civil"));
                funcionario.setSexo(rs.getString("sexo"));
                funcionario.setDireccion(rs.getString("direccion"));
                funcionario.setTelefono(rs.getString("telefono"));
                funcionario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al obtener todos los funcionarios: " + ex.getMessage());
        }
        
        return funcionarios;
    }
}