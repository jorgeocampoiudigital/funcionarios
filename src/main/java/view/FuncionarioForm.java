package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class FuncionarioForm extends JFrame {
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

    public FuncionarioForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Formulario de Funcionario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Tipo Identificación:"));
        cmbTipoIdentificacion = new JComboBox<>(new String[]{"", "Cédula", "Cédula Extranjería", "Pasaporte", "NIT"});
        panel.add(cmbTipoIdentificacion);

        panel.add(new JLabel("Número Identificación:"));
        txtNumeroIdentificacion = new JTextField();
        panel.add(txtNumeroIdentificacion);

        panel.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        panel.add(txtNombres);

        panel.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panel.add(txtApellidos);

        panel.add(new JLabel("Estado Civil:"));
        cmbEstadoCivil = new JComboBox<>(new String[]{"", "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a"});
        panel.add(cmbEstadoCivil);

        panel.add(new JLabel("Sexo:"));
        cmbSexo = new JComboBox<>(new String[]{"", "M", "F", "O"});
        panel.add(cmbSexo);

        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panel.add(txtDireccion);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):"));
        txtFechaNacimiento = new JTextField();
        panel.add(txtFechaNacimiento);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        cmbTipoIdentificacion.setSelectedItem(tipoIdentificacion);
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        txtNumeroIdentificacion.setText(numeroIdentificacion);
    }

    public void setNombres(String nombres) {
        txtNombres.setText(nombres);
    }

    public void setApellidos(String apellidos) {
        txtApellidos.setText(apellidos);
    }

    public void setEstadoCivil(String estadoCivil) {
        cmbEstadoCivil.setSelectedItem(estadoCivil);
    }

    public void setSexo(String sexo) {
        cmbSexo.setSelectedItem(sexo);
    }

    public void setDireccion(String direccion) {
        txtDireccion.setText(direccion);
    }

    public void setTelefono(String telefono) {
        txtTelefono.setText(telefono);
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtFechaNacimiento.setText(sdf.format(fechaNacimiento));
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        txtFechaNacimiento.setText(fechaNacimiento);
    }

    public String getTipoIdentificacion() {
        return (String) cmbTipoIdentificacion.getSelectedItem();
    }

    public String getNumeroIdentificacion() {
        return txtNumeroIdentificacion.getText();
    }

    public String getNombres() {
        return txtNombres.getText();
    }

    public String getApellidos() {
        return txtApellidos.getText();
    }

    public String getEstadoCivil() {
        return (String) cmbEstadoCivil.getSelectedItem();
    }

    public String getSexo() {
        return (String) cmbSexo.getSelectedItem();
    }

    public String getDireccion() {
        return txtDireccion.getText();
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }

    public String getFechaNacimiento() {
        return txtFechaNacimiento.getText();
    }

    public void addGuardarListener(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}