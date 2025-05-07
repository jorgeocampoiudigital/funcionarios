package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Funcionario;

public class FuncionarioList extends JFrame {
    private JTable table;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private DefaultTableModel tableModel;

    public FuncionarioList() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Listado de Funcionarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombres");
        tableModel.addColumn("Apellidos");
        tableModel.addColumn("Identificación");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Sexo");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Botones
        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        tableModel.setRowCount(0); // Limpiar tabla
        
        for (Funcionario funcionario : funcionarios) {
            Object[] row = {
                funcionario.getIdFuncionario(),
                funcionario.getNombres(),
                funcionario.getApellidos(),
                funcionario.getTipoIdentificacion() + ": " + funcionario.getNumeroIdentificacion(),
                funcionario.getTelefono(),
                funcionario.getSexo()
            };
            tableModel.addRow(row);
        }
    }

    public Funcionario getSelectedFuncionario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        
        // Crear un objeto Funcionario con los datos de la fila seleccionada
        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario((Integer) tableModel.getValueAt(selectedRow, 0));
        funcionario.setNombres((String) tableModel.getValueAt(selectedRow, 1));
        funcionario.setApellidos((String) tableModel.getValueAt(selectedRow, 2));
        
        // Nota: Para editar, necesitarás obtener el objeto completo desde la base de datos
        return funcionario;
    }

    // Métodos para los listeners de los botones
    public void addNuevoListener(ActionListener listener) {
        btnNuevo.addActionListener(listener);
    }

    public void addEditarListener(ActionListener listener) {
        btnEditar.addActionListener(listener);
    }

    public void addEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    // Método para mostrar mensajes
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}