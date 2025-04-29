package view;

import controller.FuncionarioController;
import dao.DAOException;

public class MainApp {
    public static void main(String[] args) {
        try {
            FuncionarioController controller = new FuncionarioController();
            FuncionarioList list = new FuncionarioList(controller);
            list.setVisible(true);
        } catch (DAOException e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}