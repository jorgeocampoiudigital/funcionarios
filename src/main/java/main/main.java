package main;

import controller.FuncionarioController;
import javax.swing.SwingUtilities;
import view.FuncionarioList;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FuncionarioList listView = new FuncionarioList();
            new FuncionarioController(listView);
            listView.setVisible(true);
        });
    }
}