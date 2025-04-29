package controller;

import java.util.List;
import dao.DAOException;
import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import model.Funcionario;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO;
    
    public FuncionarioController() throws DAOException {
        funcionarioDAO = new FuncionarioDAOImpl();
    }
    
    public void crearFuncionario(Funcionario funcionario) throws DAOException {
        funcionarioDAO.crear(funcionario);
    }
    
    public Funcionario obtenerFuncionario(int id) throws DAOException {
        return funcionarioDAO.leer(id);
    }
    
    public void actualizarFuncionario(Funcionario funcionario) throws DAOException {
        funcionarioDAO.actualizar(funcionario);
    }
    
    public void eliminarFuncionario(int id) throws DAOException {
        funcionarioDAO.eliminar(id);
    }
    
    public List<Funcionario> listarFuncionarios() throws DAOException {
        return funcionarioDAO.listarTodos();
    }
}