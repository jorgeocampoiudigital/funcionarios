package dao;

import exception.DAOException;
import java.util.List;
import model.Funcionario;

public interface FuncionarioDAO {
    void insertar(Funcionario funcionario) throws DAOException;
    void actualizar(Funcionario funcionario) throws DAOException;
    void eliminar(int id) throws DAOException;
    Funcionario obtenerPorId(int id) throws DAOException;
    List<Funcionario> obtenerTodos() throws DAOException;
}