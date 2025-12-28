package packetworldfx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldfx.dominio.EnvioImp;
import packetworldfx.dto.RSEnvioTabla;
import packetworldfx.pojo.Cliente;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLEnviosController implements Initializable {

    @FXML
    private TableView<RSEnvioTabla> tvEnvios;
    @FXML
    private TableColumn<RSEnvioTabla, String> colOrigen;
    @FXML
    private TableColumn<RSEnvioTabla, String> colDestino;
    @FXML
    private TableColumn<RSEnvioTabla, String> colEstado;
    @FXML
    private TableColumn<RSEnvioTabla, String> colConductorAsignado;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn<RSEnvioTabla, String> colGuia;

    private List<RSEnvioTabla> listaEnvios;
    private ObservableList<RSEnvioTabla> envios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarEnvios();

        tfBuscar.textProperty().addListener((obs, oldText, newText) -> {
            filtrarEnvios(newText);
        });

    }

    private void filtrarEnvios(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            tvEnvios.setItems(envios);
            return;
        }

        String filtro = texto.toLowerCase();
        ObservableList<RSEnvioTabla> filtrados = FXCollections.observableArrayList();

        for (RSEnvioTabla e : envios) {
            if (e.getNumeroGuia() != null
                    && e.getNumeroGuia().toLowerCase().contains(filtro)) {
                filtrados.add(e);
            }
        }

        tvEnvios.setItems(filtrados);
    }

    private void buscarPorGuia(String guia) {

        RSEnvioTabla envio = EnvioImp.buscarPorNumeroGuia(guia);

        if (envio != null) {
            tvEnvios.setItems(
                    javafx.collections.FXCollections.observableArrayList(envio)
            );
        } else {
            tvEnvios.getItems().clear();
        }
    }

    private void configurarTabla() {

        colGuia.setCellValueFactory(cd
                -> new SimpleStringProperty(cd.getValue().getNumeroGuia())
        );

        colOrigen.setCellValueFactory(cd
                -> new SimpleStringProperty(cd.getValue().getSucursalOrigen())
        );

        colDestino.setCellValueFactory(cd
                -> new SimpleStringProperty(cd.getValue().getDestino())
        );

        colEstado.setCellValueFactory(cd
                -> new SimpleStringProperty(cd.getValue().getEstatus())
        );

        colConductorAsignado.setCellValueFactory(cd
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
            envios = FXCollections.observableArrayList(lista);
            tvEnvios.setItems(envios);
        }
    }

    private void irFormulario(String numeroGuia) {
        try {
            FXMLLoader cargador
                    = new FXMLLoader(getClass().getResource("FXMLFormularioEnvio.fxml"));
            Parent vista = cargador.load();

            FXMLFormularioEnvioController controlador
                    = cargador.getController();

            if (numeroGuia != null) {
                controlador.inicializarEdicion(numeroGuia);
            }

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Envío");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

            // refrescar tabla al cerrar
            cargarEnvios();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario(null);

    }

    @FXML
    private void clickEditar(ActionEvent event) {
        RSEnvioTabla seleccionado
                = tvEnvios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            irFormulario(seleccionado.getNumeroGuia());
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un envío",
                    "Debes seleccionar un envío para editar.",
                    Alert.AlertType.WARNING
            );
        }
    }

    private void irFormularioStatus(RSEnvioTabla envio) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("FXMLActualizarStatusEnvio.fxml")
            );
            Parent vista = loader.load();

            FXMLActualizarStatusEnvioController controller
                    = loader.getController();

            controller.inicializar(envio);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle("Actualizar estatus de envío");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarEnvios(); // refresca tabla

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickEstatus(ActionEvent event) {
        RSEnvioTabla seleccionado
                = tvEnvios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un envío",
                    "Debes seleccionar un envío para actualizar su estatus.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        irFormularioStatus(seleccionado);
    }

    private void irAsignacionEnvioConductor() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("FXMLAsignacionEnvioConductor.fxml")
            );
            Parent vista = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle("Asignar conductor a envío");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarEnvios(); // refrescar tabla

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicAsignar(ActionEvent event) {
        irAsignacionEnvioConductor();
    }

}
