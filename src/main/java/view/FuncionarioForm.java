package view;

import controller.FuncionarioController;
import java.awt.*;
import java.util.Date;
import javax.swing.*;
import model.Funcionario;

public class FuncionarioForm extends JDialog {
    private JComboBox<String> cmbTipoIdentificacion;
    private JTextField txtNumeroIdentificacion;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JComboBox<String> cmbEstadoCivil;
    private JComboBox<String> cmbSexo;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private Funcionario funcionario;
    private FuncionarioController controller;
    private boolean esNuevo;
    
    public FuncionarioForm(JFrame parent, FuncionarioController controller, boolean esNuevo, Funcionario funcionario) {
        super(parent, esNuevo ? "Nuevo Funcionario" : "Editar Funcionario", true);
        this.controller = controller;
        this.esNuevo = esNuevo;
        this.funcionario = funcionario;
        
        initComponents();
        if (!esNuevo) {
            cargarDatos();
        }
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(9, 2, 5, 5));
        
        panelForm.add(new JLabel("Tipo Identificación:"));
        cmbTipoIdentificacion = new JComboBox<>(new String[]{"Cédula", "Cédula Extrangería", "Pasaporte"});
        panelForm.add(cmbTipoIdentificacion);
        
        panelForm.add(new JLabel("Número Identificación:"));
        txtNumeroIdentificacion = new JTextField();
        panelForm.add(txtNumeroIdentificacion);
        
        panelForm.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        panelForm.add(txtNombres);
        
        panelForm.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelForm.add(txtApellidos);
        
        panelForm.add(new JLabel("Estado Civil:"));
        cmbEstadoCivil = new JComboBox<>(new String[]{"Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a"});
        panelForm.add(cmbEstadoCivil);
        
        panelForm.add(new JLabel("Sexo:"));
        cmbSexo = new JComboBox<>(new String[]{"M", "F"});
        panelForm.add(cmbSexo);
        
        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);
        
        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);
        
        panelForm.add(new JLabel("Fecha Nacimiento (yyyy-mm-dd):"));
        txtFechaNacimiento = new JTextField();
        panelForm.add(txtFechaNacimiento);
        
        add(panelForm, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> guardarFuncionario());
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(getParent());
    }
    
    private void cargarDatos() {
        cmbTipoIdentificacion.setSelectedItem(funcionario.getTipoIdentificacion());
        txtNumeroIdentificacion.setText(funcionario.getNumeroIdentificacion());
        txtNombres.setText(funcionario.getNombres());
        txtApellidos.setText(funcionario.getApellidos());
        cmbEstadoCivil.setSelectedItem(funcionario.getEstadoCivil());
        cmbSexo.setSelectedItem(String.valueOf(funcionario.getSexo()));
        txtDireccion.setText(funcionario.getDireccion());
        txtTelefono.setText(funcionario.getTelefono());
        txtFechaNacimiento.setText(funcionario.getFechaNacimiento().toString());
    }
    
    private void guardarFuncionario() {
        try {
            funcionario.setTipoIdentificacion((String) cmbTipoIdentificacion.getSelectedItem());
            funcionario.setNumeroIdentificacion(txtNumeroIdentificacion.getText());
            funcionario.setNombres(txtNombres.getText());
            funcionario.setApellidos(txtApellidos.getText());
            funcionario.setEstadoCivil((String) cmbEstadoCivil.getSelectedItem());
            funcionario.setSexo(((String) cmbSexo.getSelectedItem()).charAt(0));
            funcionario.setDireccion(txtDireccion.getText());
            funcionario.setTelefono(txtTelefono.getText());
            
            // Parsear fecha (esto debería mejorarse con un JDatePicker)
            String[] partesFecha = txtFechaNacimiento.getText().split("-");
            int año = Integer.parseInt(partesFecha[0]) - 1900;
            int mes = Integer.parseInt(partesFecha[1]) - 1;
            int dia = Integer.parseInt(partesFecha[2]);
            funcionario.setFechaNacimiento(new Date(año, mes, dia));
            
            if (esNuevo) {
                controller.crearFuncionario(funcionario);
            } else {
                controller.actualizarFuncionario(funcionario);
            }
            
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}