package packetworldfx;

import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldfx.dominio.CatalogoImp;
import packetworldfx.dominio.EnvioImp;
import packetworldfx.dto.RSEnvioTabla;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Colaborador;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLAsignacionEnvioConductorController implements Initializable {

    @FXML
    private TableColumn<RSEnvioTabla, String> colNumeroGuia;
    @FXML
    private TableColumn<RSEnvioTabla, String> colOrigen;
    @FXML
    private TableColumn<RSEnvioTabla, String> colDestino;
    @FXML
    private TableColumn<RSEnvioTabla, String> colEstado;
    @FXML
    private TableColumn<Colaborador, String> colNoPersonal;
    @FXML
    private TableColumn<Colaborador, String> colNombreCompleto;
    @FXML
    private TableColumn<Colaborador, String> colNumeroLicencia;
    @FXML
    private Label lbIndicacion;
    @FXML
    private TableView<RSEnvioTabla> tvEnvio;
    @FXML
    private TableView<Colaborador> tvConductor;
    @FXML
    private TableColumn<RSEnvioTabla, String> colConductorActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
        cargarEnvios();
        cargarConductores();
        configurarListeners();
        colNombreCompleto.setCellValueFactory(cellData -> {
            Colaborador c = cellData.getValue();

            String nombreCompleto = c.getNombre() + " " + c.getApellidoPaterno();

            if (c.getApellidoMaterno() != null && !c.getApellidoMaterno().isEmpty()) {
                nombreCompleto += " " + c.getApellidoMaterno();
            }

            return new SimpleStringProperty(nombreCompleto);
        });
    }

    private void actualizarIndicacion() {
        RSEnvioTabla envio = tvEnvio.getSelectionModel().getSelectedItem();
        Colaborador conductor = tvConductor.getSelectionModel().getSelectedItem();

        if (envio == null && conductor == null) {
            lbIndicacion.setText("Selecciona un envío o un conductor.");
        } else if (envio != null && conductor == null) {
            if (envio.getConductor() != null) {
                lbIndicacion.setText("Este envío ya tiene conductor. Puedes desasignarlo.");
            } else {
                lbIndicacion.setText("Selecciona un conductor para asignarlo al envío.");
            }
        } else if (envio == null && conductor != null) {
            lbIndicacion.setText("Selecciona un envío para asignarlo al conductor.");
        } else {
            lbIndicacion.setText("Listo para asignar conductor al envío.");
        }
    }

    private void configurarListeners() {
        tvEnvio.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> actualizarIndicacion()
        );

        tvConductor.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> actualizarIndicacion()
        );
    }

    private void configurarTablas() {
        colNumeroGuia.setCellValueFactory(new PropertyValueFactory<>("numeroGuia"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("sucursalOrigen"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        colNoPersonal.setCellValueFactory(new PropertyValueFactory<>("noPersonal"));
        colNumeroLicencia.setCellValueFactory(new PropertyValueFactory<>("numeroLicencia"));

        colConductorActual.setCellValueFactory(cd
                -> new SimpleStringProperty(
                        cd.getValue().getConductor() != null
                        ? cd.getValue().getConductor()
                        : "Sin asignar"
                )
        );
    }

    private void cargarEnvios() {
        List<RSEnvioTabla> lista = EnvioImp.obtenerEnviosTabla();
        if (lista != null) {
            tvEnvio.setItems(FXCollections.observableArrayList(lista));
        }
    }

    private void cargarConductores() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerConductores();

        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Colaborador> lista
                    = (List<Colaborador>) respuesta.get(Constantes.KEY_LISTA);
            tvConductor.setItems(FXCollections.observableArrayList(lista));
        }
    }

    @FXML
    private void clickAsignar(ActionEvent event) {
        RSEnvioTabla envio = tvEnvio.getSelectionModel().getSelectedItem();
        Colaborador conductor = tvConductor.getSelectionModel().getSelectedItem();

        if (envio == null || conductor == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Seleccione un envío y un conductor",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (envio.getConductor() != null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Este envío ya tiene un conductor asignado",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Respuesta r = EnvioImp.asignar(
                envio.getNumeroGuia(),
                conductor.getIdColaborador()
        );

        if (!r.isError()) {
            cargarEnvios();
        } else {
            Utilidades.mostrarAlertaSimple("Error", r.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clickDesasignar(ActionEvent event) {

        RSEnvioTabla envio = tvEnvio.getSelectionModel().getSelectedItem();

        if (envio == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Seleccione un envío",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (envio.getConductor() == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Este envío no tiene conductor asignado",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Respuesta r = EnvioImp.desasignar(envio.getNumeroGuia());

        if (!r.isError()) {
            cargarEnvios();
        } else {
            Utilidades.mostrarAlertaSimple("Error", r.getMensaje(), Alert.AlertType.ERROR);
        }
    }

}
