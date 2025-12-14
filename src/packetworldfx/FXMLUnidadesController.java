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
import packetworldfx.dominio.UnidadImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Unidad;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLUnidadesController implements Initializable, INotificador {

    @FXML
    private TableView<Unidad> tvUnidades;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colAnio;
    @FXML
    private TableColumn colVIN;
    @FXML
    private TableColumn colTipoUnidad;
    @FXML
    private TableColumn colNoIdentificacion;

    private ObservableList<Unidad> unidades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionUnidades();

        // Listener para búsqueda
        tfBuscar.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarUnidades(newValue);
        });
    }

    private void filtrarUnidades(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            tvUnidades.setItems(unidades);
            return;
        }

        String filtro = texto.trim().toLowerCase();

        ObservableList<Unidad> listaFiltrada = FXCollections.observableArrayList();

        for (Unidad u : unidades) {
            boolean coincideVIN = u.getVin() != null && u.getVin().toLowerCase().contains(filtro);
            boolean coincideMarca = u.getMarca() != null && u.getMarca().toLowerCase().contains(filtro);
            boolean coincideNII = u.getNumeroIdentificacionInterno() != null
                    && u.getNumeroIdentificacionInterno().toLowerCase().contains(filtro);

            if (coincideVIN || coincideMarca || coincideNII) {
                listaFiltrada.add(u);
            }
        }

        tvUnidades.setItems(listaFiltrada);
    }

    private void configurarTabla() {
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colNoIdentificacion.setCellValueFactory(new PropertyValueFactory<>("numeroIdentificacionInterno"));
        colTipoUnidad.setCellValueFactory(new PropertyValueFactory<>("tipoUnidad"));
        colVIN.setCellValueFactory(new PropertyValueFactory<>("vin"));
    }

    private void cargarInformacionUnidades() {
        HashMap<String, Object> respuesta = UnidadImp.obtenerTodas();

        if (!(boolean) respuesta.get("error")) {
            List<Unidad> unidadesAPI = (List<Unidad>) respuesta.get("unidades");
            unidades = FXCollections.observableArrayList();
            unidades.addAll(unidadesAPI);
            tvUnidades.setItems(unidades);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar unidades", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private void irFormulario(Unidad unidad) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioUnidad.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioUnidadController controlador = cargador.getController();
            controlador.inicializarDatos(unidad, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Unidad");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir el formulario", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        System.out.println("Operacion " + operacion + " , nombre Unidad: " + nombre);
        cargarInformacionUnidades();
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        Unidad seleccionado = tvUnidades.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            irFormulario(seleccionado);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona una unidad",
                    "Debes seleccionar una unidad para editar.",
                    Alert.AlertType.WARNING
            );
        }
    }

    private String solicitarMotivoBaja() {
        TextField tfMotivo = new TextField();
        tfMotivo.setPromptText("Ej. Desgaste excesivo");

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Baja de unidad");
        alerta.setHeaderText("Motivo de baja");
        alerta.setContentText("Ingresa el motivo de la baja:");

        alerta.getDialogPane().setContent(tfMotivo);

        alerta.showAndWait();

        return tfMotivo.getText().trim();
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        Unidad seleccionado = tvUnidades.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona una unidad",
                    "Debes seleccionar una unidad para darla de baja.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                "Baja de unidad",
                "¿Estás seguro de dar de baja la unidad "
                + seleccionado.getMarca() + " " + seleccionado.getModelo() + "?"
        );

        if (!confirmar) {
            return;
        }

        String motivo = solicitarMotivoBaja();

        if (motivo.isEmpty()) {
            Utilidades.mostrarAlertaSimple(
                    "Motivo requerido",
                    "Debes ingresar un motivo de baja.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Respuesta respuesta = UnidadImp.bajaUnidad(seleccionado.getIdUnidad(), motivo);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                    "Unidad dada de baja",
                    "La unidad fue dada de baja correctamente.",
                    Alert.AlertType.INFORMATION
            );
            cargarInformacionUnidades();
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    respuesta.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

}
