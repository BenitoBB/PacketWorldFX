package packetworldfx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldfx.dominio.EnvioImp;
import packetworldfx.dominio.PaqueteImp;
import packetworldfx.dto.RSEnvioTabla;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Paquete;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLAsignacionPaqueteEnvioController implements Initializable {

    @FXML
    private Label lbIndicacion;
    @FXML
    private TableView<RSEnvioTabla> tvEnvio;
    @FXML
    private TableColumn<RSEnvioTabla, String> colNumeroGuia;
    @FXML
    private TableColumn<RSEnvioTabla, String> colOrigen;
    @FXML
    private TableColumn<RSEnvioTabla, String> colDestino;
    @FXML
    private TableColumn<RSEnvioTabla, String> colEstado;
    @FXML
    private TableColumn<RSEnvioTabla, String> colConductorActual;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
        cargarEnvios();
        cargarPaquetes();
        configurarListeners();

        tvPaquetes.setRowFactory(tv -> new TableRow<Paquete>() {
            @Override
            protected void updateItem(Paquete p, boolean empty) {
                super.updateItem(p, empty);
                if (p == null || empty) {
                    setStyle("");
                } else if (p.getIdEnvio() != null) {
                    setStyle("-fx-background-color: #FFE5E5;");
                } else {
                    setStyle("");
                }
            }
        });
        tvEnvio.setRowFactory(tv -> new TableRow<RSEnvioTabla>() {
            @Override
            protected void updateItem(RSEnvioTabla e, boolean empty) {
                super.updateItem(e, empty);
                if (e == null || empty) {
                    setStyle("");
                } else if (e.getIdEstatus() != 1 && e.getIdEstatus() != 2) {
                    setStyle("-fx-background-color: #E0E0E0;");
                } else {
                    setStyle("");
                }
            }
        });

    }

    private void configurarTablas() {
        // Envíos
        colNumeroGuia.setCellValueFactory(new PropertyValueFactory<>("numeroGuia"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("sucursalOrigen"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        colConductorActual.setCellValueFactory(cd
                -> new SimpleStringProperty(
                        cd.getValue().getConductor() != null
                        ? cd.getValue().getConductor()
                        : "Sin conductor"
                )
        );

        // Paquetes
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colAlto.setCellValueFactory(new PropertyValueFactory<>("alto"));
        colAncho.setCellValueFactory(new PropertyValueFactory<>("ancho"));
        colProfundidad.setCellValueFactory(new PropertyValueFactory<>("profundidad"));
    }

    private void cargarEnvios() {
        List<RSEnvioTabla> lista = EnvioImp.obtenerEnviosTabla();
        if (lista != null) {
            tvEnvio.setItems(FXCollections.observableArrayList(lista));
        }
    }

    private void cargarPaquetes() {
        List<Paquete> lista = PaqueteImp.obtenerTodos();
        if (lista != null) {
            tvPaquetes.setItems(FXCollections.observableArrayList(lista));
        }
    }

    private void actualizarIndicacion() {
        RSEnvioTabla envio = tvEnvio.getSelectionModel().getSelectedItem();
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();

        if (envio == null) {
            lbIndicacion.setText("Selecciona un envío.");
        } else if (paquete == null) {
            lbIndicacion.setText("Selecciona un paquete.");
        } else if (paquete.getIdEnvio() != null) {
            lbIndicacion.setText("Este paquete ya está asignado. Puedes desasignarlo.");
        } else {
            lbIndicacion.setText("Listo para asignar paquete al envío.");
        }
    }

    private void configurarListeners() {
        tvEnvio.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> actualizarIndicacion()
        );
        tvPaquetes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> actualizarIndicacion()
        );
    }

    private boolean envioPermiteAsignacion(RSEnvioTabla envio) {
        return envio.getIdEstatus() == 1 || envio.getIdEstatus() == 2;
    }

    @FXML
    private void clickAsignar(ActionEvent event) {
        RSEnvioTabla envio = tvEnvio.getSelectionModel().getSelectedItem();
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();

        // PRIMERO validar selección
        if (envio == null || paquete == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Selecciona un envío y un paquete",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // DESPUÉS validar estatus
        if (!envioPermiteAsignacion(envio)) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Solo se pueden asignar paquetes a envíos en estado:\n"
                    + "- Recibido en sucursal\n"
                    + "- Procesado",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // Paquete ya asignado
        if (paquete.getIdEnvio() != null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Este paquete ya está asignado",
                    Alert.AlertType.WARNING
            );
            return;
        }

        // Asignar
        Respuesta r = PaqueteImp.asignar(
                paquete.getIdPaquete(),
                envio.getIdEnvio()
        );

        if (!r.isError()) {
            cargarPaquetes();
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    r.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void clickDeasignar(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        RSEnvioTabla envio = tvEnvio.getSelectionModel().getSelectedItem();

        if (paquete == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Selecciona un paquete",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (paquete.getIdEnvio() == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Este paquete no está asignado",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Respuesta r = PaqueteImp.desasignar(paquete.getIdPaquete());

        if (!r.isError()) {
            cargarPaquetes();
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    r.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

}
