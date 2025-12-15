package packetworldfx;

import java.io.IOException;
import java.net.URL;
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
import packetworldfx.dominio.PaqueteImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Paquete;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLPaquetesController implements Initializable, INotificador {

    @FXML
    private TableView<Paquete> tvPaquetes;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colAlto;
    @FXML
    private TableColumn colAncho;
    @FXML
    private TableColumn colProfundidad;
    @FXML
    private TextField tfBuscar;

    private ObservableList<Paquete> paquetes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarPaquetesIniciales();
    }

    private void configurarTabla() {
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colAlto.setCellValueFactory(new PropertyValueFactory<>("alto"));
        colAncho.setCellValueFactory(new PropertyValueFactory<>("ancho"));
        colProfundidad.setCellValueFactory(new PropertyValueFactory<>("profundidad"));
    }

    private void cargarPaquetesIniciales() {

        List<Paquete> lista = PaqueteImp.obtenerTodos();

        if (lista != null) {
            paquetes = FXCollections.observableArrayList(lista);
            tvPaquetes.setItems(paquetes);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudieron cargar los paquetes.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void cargarInformacionPaquetes(Integer idEnvio) {

        List<Paquete> lista = PaqueteImp.obtenerPorEnvio(idEnvio);

        if (lista != null) {
            paquetes = FXCollections.observableArrayList(lista);
            tvPaquetes.setItems(paquetes);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudieron cargar los paquetes del envío.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void irFormulario(Paquete paquete) {
        try {
            FXMLLoader cargador = new FXMLLoader(
                    getClass().getResource("FXMLFormularioPaquete.fxml")
            );
            Parent vista = cargador.load();

            FXMLFormularioPaqueteController controlador
                    = cargador.getController();
            controlador.inicializarDatos(paquete, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Paquete");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        System.out.println("Operacion " + operacion + " , nombre Paquete: " + nombre);
        cargarPaquetesIniciales();
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        Paquete seleccionado
                = tvPaquetes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            irFormulario(seleccionado);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un paquete",
                    "Debes seleccionar un paquete para editar.",
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {

        Paquete seleccionado
                = tvPaquetes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                    "Eliminar paquete",
                    "¿Estás seguro de eliminar el paquete "
                    + seleccionado.getDescripcion()
                    + "?\nEsta acción no se puede deshacer."
            );

            if (confirmar) {
                Respuesta respuesta
                        = PaqueteImp.eliminar(seleccionado.getIdPaquete());

                if (!respuesta.isError()) {
                    Utilidades.mostrarAlertaSimple(
                            "Registro eliminado",
                            "El paquete fue eliminado correctamente.",
                            Alert.AlertType.INFORMATION
                    );
                    cargarPaquetesIniciales();
                } else {
                    Utilidades.mostrarAlertaSimple(
                            "Error al eliminar",
                            respuesta.getMensaje(),
                            Alert.AlertType.ERROR
                    );
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un paquete",
                    "Debes seleccionar un paquete para eliminarlo.",
                    Alert.AlertType.WARNING
            );
        }
    }

}
