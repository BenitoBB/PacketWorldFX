package packetworldfx;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldfx.dominio.ColaboradorImp;
import packetworldfx.dto.ColaboradorTablaDTO;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Colaborador;
import packetworldfx.utilidad.Utilidades;
import packetworldfx.interfaz.INotificador;

/**
 * FXML Controller class
 *
 * @author benit
 */
public class FXMLColaboradoresController implements Initializable, INotificador {

    @FXML
    private TableView<ColaboradorTablaDTO> tvColaboradores;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colSucursal;
    @FXML
    private TableColumn colRol;
    @FXML
    private TableColumn colLicencia;
    @FXML
    private TextField tfBuscar;

    private ObservableList<ColaboradorTablaDTO> colaboradores;
    @FXML
    private TableColumn colAPaterno;
    @FXML
    private TableColumn colAMaterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionColaboradores();

        // Listener para búsqueda
        tfBuscar.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarColaboradores(newValue);
        });
    }

    private void filtrarColaboradores(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            tvColaboradores.setItems(colaboradores); // Mostrar todos
            return;
        }

        String filtro = texto.trim().toLowerCase();

        ObservableList<ColaboradorTablaDTO> listaFiltrada = FXCollections.observableArrayList();

        for (ColaboradorTablaDTO c : colaboradores) {
            boolean coincideNombre = c.getNombre().toLowerCase().contains(filtro)
                    || (c.getApellidoPaterno() != null && c.getApellidoPaterno().toLowerCase().contains(filtro))
                    || (c.getApellidoMaterno() != null && c.getApellidoMaterno().toLowerCase().contains(filtro));

            boolean coincideNoPersonal = c.getNoPersonal() != null && c.getNoPersonal().toLowerCase().contains(filtro);

            boolean coincideRol = c.getNombreRol() != null && c.getNombreRol().toLowerCase().contains(filtro);

            if (coincideNombre || coincideNoPersonal || coincideRol) {
                listaFiltrada.add(c);
            }
        }

        tvColaboradores.setItems(listaFiltrada);
    }

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory<>("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colAMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("nombreRol"));
        colSucursal.setCellValueFactory(new PropertyValueFactory<>("nombreSucursal"));
        colLicencia.setCellValueFactory(new PropertyValueFactory<>("numeroLicencia"));
    }

    private void cargarInformacionColaboradores() {
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get("error");
        if (!esError) {
            List<ColaboradorTablaDTO> colaboradoresAPI = (List<ColaboradorTablaDTO>) respuesta.get("colaboradores");
            colaboradores = FXCollections.observableArrayList();
            colaboradores.addAll(colaboradoresAPI);
            tvColaboradores.setItems(colaboradores);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private void irFormulario(Colaborador colaborador) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioColaborador.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioColaboradorController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Colaborador");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        System.out.println("Operacion " + operacion + " , nombre Colaborador: " + nombre);
        cargarInformacionColaboradores();
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        ColaboradorTablaDTO seleccionado
                = tvColaboradores.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            Colaborador colaboradorCompleto
                    = ColaboradorImp.buscarPorNoPersonal(
                            seleccionado.getNoPersonal()
                    );

            if (colaboradorCompleto != null) {
                irFormulario(colaboradorCompleto);
            } else {
                Utilidades.mostrarAlertaSimple(
                        "Error",
                        "No se pudo obtener la información del colaborador",
                        Alert.AlertType.ERROR
                );
            }
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un colaborador",
                    "Debes seleccionar un colaborador para editar.",
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        ColaboradorTablaDTO seleccionado
                = tvColaboradores.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                    "Eliminar colaborador",
                    "¿Estás seguro de eliminar al colaborador "
                    + seleccionado.getNombre() + " "
                    + seleccionado.getApellidoPaterno()
                    + "?\nEsta acción no se puede deshacer."
            );

            if (confirmar) {
                eliminarColaborador(seleccionado.getIdColaborador());
            }
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un colaborador",
                    "Debes seleccionar un colaborador para eliminarlo.",
                    Alert.AlertType.WARNING
            );
        }
    }

    private void eliminarColaborador(int idColaborador) {
        Respuesta respuesta = ColaboradorImp.eliminar(idColaborador);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                    "Registro eliminado",
                    "El colaborador fue eliminado correctamente.",
                    Alert.AlertType.INFORMATION
            );
            cargarInformacionColaboradores();
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error al eliminar",
                    respuesta.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

}
