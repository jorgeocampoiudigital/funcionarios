package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Funcionario;

public class FuncionarioDAOImpl implements FuncionarioDAO {
    private Connection conexion;

    public FuncionarioDAOImpl() throws DAOException {
        conexion = ConexionBD.obtenerConexion();
    }

    @Override
    public void crear(Funcionario funcionario) throws DAOException {
        String sql = "INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, "
                   + "apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, funcionario.getTipoIdentificacion());
            ps.setString(2, funcionario.getNumeroIdentificacion());
            ps.setString(3, funcionario.getNombres());
            ps.setString(4, funcionario.getApellidos());
            ps.setString(5, funcionario.getEstadoCivil());
            ps.setString(6, String.valueOf(funcionario.getSexo()));
            ps.setString(7, funcionario.getDireccion());
            ps.setString(8, funcionario.getTelefono());
            ps.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al crear funcionario, ninguna fila afectada.");
            }
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    funcionario.setIdFuncionario(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Error al crear funcionario, no se obtuvo el ID.");
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al crear funcionario en la base de datos", ex);
        }
    }

    @Override
    public Funcionario leer(int id) throws DAOException {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        Funcionario funcionario = null;
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                    funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                    funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                    funcionario.setNombres(rs.getString("nombres"));
                    funcionario.setApellidos(rs.getString("apellidos"));
                    funcionario.setEstadoCivil(rs.getString("estado_civil"));
                    funcionario.setSexo(rs.getString("sexo").charAt(0));
                    funcionario.setDireccion(rs.getString("direccion"));
                    funcionario.setTelefono(rs.getString("telefono"));
                    funcionario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al leer funcionario de la base de datos", ex);
        }
        
        return funcionario;
    }

    @Override
    public void actualizar(Funcionario funcionario) throws DAOException {
        String sql = "UPDATE funcionario SET tipo_identificacion = ?, numero_identificacion = ?, "
                   + "nombres = ?, apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, "
                   + "telefono = ?, fecha_nacimiento = ? WHERE id_funcionario = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, funcionario.getTipoIdentificacion());
            ps.setString(2, funcionario.getNumeroIdentificacion());
            ps.setString(3, funcionario.getNombres());
            ps.setString(4, funcionario.getApellidos());
            ps.setString(5, funcionario.getEstadoCivil());
            ps.setString(6, String.valueOf(funcionario.getSexo()));
            ps.setString(7, funcionario.getDireccion());
            ps.setString(8, funcionario.getTelefono());
            ps.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            ps.setInt(10, funcionario.getIdFuncionario());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al actualizar funcionario, ninguna fila afectada.");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar funcionario en la base de datos", ex);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al eliminar funcionario, ninguna fila afectada.");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar funcionario de la base de datos", ex);
        }
    }

    @Override
    public List<Funcionario> listarTodos() throws DAOException {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                funcionario.setNombres(rs.getString("nombres"));
                funcionario.setApellidos(rs.getString("apellidos"));
                funcionario.setEstadoCivil(rs.getString("estado_civil"));
                funcionario.setSexo(rs.getString("sexo").charAt(0));
                funcionario.setDireccion(rs.getString("direccion"));
                funcionario.setTelefono(rs.getString("telefono"));
                funcionario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al listar funcionarios de la base de datos", ex);
        }
        
        return funcionarios;
    }
}