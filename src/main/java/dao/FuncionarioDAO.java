package dao;

import java.util.List;
import model.Funcionario;

public interface FuncionarioDAO {
    void crear(Funcionario funcionario) throws DAOException;
    Funcionario leer(int id) throws DAOException;
    void actualizar(Funcionario funcionario) throws DAOException;
    void eliminar(int id) throws DAOException;
    List<Funcionario> listarTodos() throws DAOException;
}
