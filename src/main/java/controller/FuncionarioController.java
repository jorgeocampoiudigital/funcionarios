package controller;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import exception.DAOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.Funcionario;
import view.FuncionarioForm;
import view.FuncionarioList;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO;
    private FuncionarioList listView;
    private FuncionarioForm formView;

    public FuncionarioController(FuncionarioList listView) {
        this.funcionarioDAO = new FuncionarioDAOImpl();
        this.listView = listView;
        
        initController();
        cargarFuncionarios();
    }

    private void initController() {
        listView.addNuevoListener(e -> mostrarFormularioNuevo());
        listView.addEditarListener(e -> editarFuncionario());
        listView.addEliminarListener(e -> eliminarFuncionario());
    }

    private void cargarFuncionarios() {
        try {
            List<Funcionario> funcionarios = funcionarioDAO.obtenerTodos();
            listView.setFuncionarios(funcionarios);
        } catch (DAOException ex) {
            listView.mostrarMensaje("Error al cargar funcionarios: " + ex.getMessage(), 
                                   "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarFormularioNuevo() {
        formView = new FuncionarioForm();
        formView.addGuardarListener(e -> guardarNuevoFuncionario());
        formView.addCancelarListener(e -> formView.dispose());
        formView.setVisible(true);
    }

    private void guardarNuevoFuncionario() {
        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setTipoIdentificacion(formView.getTipoIdentificacion());
            funcionario.setNumeroIdentificacion(formView.getNumeroIdentificacion());
            funcionario.setNombres(formView.getNombres());
            funcionario.setApellidos(formView.getApellidos());
            funcionario.setEstadoCivil(formView.getEstadoCivil());
            funcionario.setSexo(formView.getSexo());
            funcionario.setDireccion(formView.getDireccion());
            funcionario.setTelefono(formView.getTelefono());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(formView.getFechaNacimiento());
            funcionario.setFechaNacimiento(fechaNacimiento);
            
            funcionarioDAO.insertar(funcionario);
            cargarFuncionarios();
            formView.dispose();
            formView.mostrarExito("Funcionario creado exitosamente");
        } catch (ParseException ex) {
            formView.mostrarError("Formato de fecha incorrecto. Use YYYY-MM-DD");
        } catch (DAOException ex) {
            formView.mostrarError("Error al crear funcionario: " + ex.getMessage());
        }
    }

    private void editarFuncionario() {
      Funcionario funcionario = listView.getSelectedFuncionario();
      if (funcionario == null) {
          listView.mostrarMensaje("Seleccione un funcionario para editar", 
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
          return;
      }
      
      try {
          Funcionario funcionarioCompleto = funcionarioDAO.obtenerPorId(funcionario.getIdFuncionario());
          
          formView = new FuncionarioForm();
          formView.setTitle("Editar Funcionario - " + funcionarioCompleto.getNombres());
          
          formView.setTipoIdentificacion(funcionarioCompleto.getTipoIdentificacion());
          formView.setNumeroIdentificacion(funcionarioCompleto.getNumeroIdentificacion());
          formView.setNombres(funcionarioCompleto.getNombres());
          formView.setApellidos(funcionarioCompleto.getApellidos());
          formView.setEstadoCivil(funcionarioCompleto.getEstadoCivil());
          formView.setSexo(funcionarioCompleto.getSexo());
          formView.setDireccion(funcionarioCompleto.getDireccion());
          formView.setTelefono(funcionarioCompleto.getTelefono());
          formView.setFechaNacimiento(funcionarioCompleto.getFechaNacimiento());
          
          int idFuncionario = funcionarioCompleto.getIdFuncionario();
          formView.addGuardarListener(e -> actualizarFuncionario(idFuncionario));
          
          formView.addCancelarListener(e -> formView.dispose());
          formView.setVisible(true);
      } catch (DAOException ex) {
          listView.mostrarMensaje("Error al obtener datos del funcionario: " + ex.getMessage(), 
                               "Error", JOptionPane.ERROR_MESSAGE);
      }
  }

    private void actualizarFuncionario(int idFuncionario) {
        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setIdFuncionario(idFuncionario);
            funcionario.setTipoIdentificacion(formView.getTipoIdentificacion());
            funcionario.setNumeroIdentificacion(formView.getNumeroIdentificacion());
            funcionario.setNombres(formView.getNombres());
            funcionario.setApellidos(formView.getApellidos());
            funcionario.setEstadoCivil(formView.getEstadoCivil());
            funcionario.setSexo(formView.getSexo());
            funcionario.setDireccion(formView.getDireccion());
            funcionario.setTelefono(formView.getTelefono());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(formView.getFechaNacimiento());
            funcionario.setFechaNacimiento(fechaNacimiento);
            
            funcionarioDAO.actualizar(funcionario);
            cargarFuncionarios();
            formView.dispose();
            formView.mostrarExito("Funcionario actualizado exitosamente");
        } catch (ParseException ex) {
            formView.mostrarError("Formato de fecha incorrecto. Use YYYY-MM-DD");
        } catch (DAOException ex) {
            formView.mostrarError("Error al actualizar funcionario: " + ex.getMessage());
        }
    }

    private void eliminarFuncionario() {
        Funcionario funcionario = listView.getSelectedFuncionario();
        if (funcionario == null) {
            listView.mostrarMensaje("Seleccione un funcionario para eliminar", 
                                  "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(listView, 
            "¿Está seguro de eliminar este funcionario?", "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                funcionarioDAO.eliminar(funcionario.getIdFuncionario());
                cargarFuncionarios();
                listView.mostrarMensaje("Funcionario eliminado exitosamente", 
                                      "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (DAOException ex) {
                listView.mostrarMensaje("Error al eliminar funcionario: " + ex.getMessage(), 
                                       "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}