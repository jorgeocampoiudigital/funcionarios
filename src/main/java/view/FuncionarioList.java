package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Funcionario;
import controller.FuncionarioController;

public class FuncionarioList extends JFrame {
    private JTable tablaFuncionarios;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    
    private FuncionarioController controller;
    
    public FuncionarioList(FuncionarioController controller) {
        this.controller = controller;
        initComponents();
        cargarFuncionarios();
    }
    
    private void initComponents() {
        setTitle("Gestión de Funcionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Tabla
        tablaFuncionarios = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaFuncionarios);
        
        // Botones
        JPanel panelBotones = new JPanel();
        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        btnNuevo.addActionListener(e -> nuevoFuncionario());
        btnEditar.addActionListener(e -> editarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnActualizar.addActionListener(e -> cargarFuncionarios());
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarFuncionarios() {
        try {
            List<Funcionario> funcionarios = controller.listarFuncionarios();
            
            String[] columnas = {"ID", "Tipo ID", "Número ID", "Nombres", "Apellidos", 
                                "Estado Civil", "Sexo", "Dirección", "Teléfono", "Fecha Nacimiento"};
            
            Object[][] datos = new Object[funcionarios.size()][columnas.length];
            
            for (int i = 0; i < funcionarios.size(); i++) {
                Funcionario f = funcionarios.get(i);
                datos[i][0] = f.getIdFuncionario();
                datos[i][1] = f.getTipoIdentificacion();
                datos[i][2] = f.getNumeroIdentificacion();
                datos[i][3] = f.getNombres();
                datos[i][4] = f.getApellidos();
                datos[i][5] = f.getEstadoCivil();
                datos[i][6] = f.getSexo();
                datos[i][7] = f.getDireccion();
                datos[i][8] = f.getTelefono();
                datos[i][9] = f.getFechaNacimiento();
            }
            
            tablaFuncionarios.setModel(new DefaultTableModel(datos, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar funcionarios: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void nuevoFuncionario() {
        Funcionario nuevo = new Funcionario();
        FuncionarioForm form = new FuncionarioForm(this, controller, true, nuevo);
        form.setVisible(true);
        cargarFuncionarios();
    }
    
    private void editarFuncionario() {
        int filaSeleccionada = tablaFuncionarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para editar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) tablaFuncionarios.getValueAt(filaSeleccionada, 0);
            Funcionario funcionario = controller.obtenerFuncionario(id);
            
            FuncionarioForm form = new FuncionarioForm(this, controller, false, funcionario);
            form.setVisible(true);
            cargarFuncionarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar funcionario: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarFuncionario() {
        int filaSeleccionada = tablaFuncionarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para eliminar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este funcionario?", "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tablaFuncionarios.getValueAt(filaSeleccionada, 0);
                controller.eliminarFuncionario(id);
                cargarFuncionarios();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar funcionario: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}