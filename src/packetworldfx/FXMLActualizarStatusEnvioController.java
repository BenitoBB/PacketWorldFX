package packetworldfx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import packetworldfx.dominio.CatalogoImp;
import packetworldfx.dominio.EnvioImp;
import packetworldfx.dto.RSActualizarEstatus;
import packetworldfx.dto.RSEnvioTabla;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Estatus;
import packetworldfx.utilidad.Sesion;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLActualizarStatusEnvioController implements Initializable {

    @FXML
    private ComboBox<Estatus> cbEstatus;
    @FXML
    private Label lbGuia;
    @FXML
    private Label lbNombreCliente;
    @FXML
    private Label lbSucursal;
    @FXML
    private Label lbEstatusActual;
    @FXML
    private TextArea taComentario;

    private RSEnvioTabla envioSeleccionado;
    private static final int ID_DETENIDO = 4;
    private static final int ID_CANCELADO = 6;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void cargarEstatus() {
        List<Estatus> lista = CatalogoImp.obtenerEstatusEnvio();
        cbEstatus.setItems(FXCollections.observableArrayList(lista));
    }

    public void inicializar(RSEnvioTabla envio) {
        this.envioSeleccionado = envio;
        lbGuia.setText(envio.getNumeroGuia());
        lbSucursal.setText(envio.getSucursalOrigen());
        lbNombreCliente.setText(envio.getDestino());
        lbEstatusActual.setText(envio.getEstatus());

        cargarEstatus();
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) lbGuia.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        if (cbEstatus.getValue() == null) {
            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Seleccione un estatus.",
                    Alert.AlertType.WARNING
            );
            return;
        }
        if (Sesion.getColaborador() == null) {
            Utilidades.mostrarAlertaSimple(
                    "Sesión inválida",
                    "No hay un colaborador en sesión.",
                    Alert.AlertType.ERROR
            );
            return;
        }

        RSActualizarEstatus rs = new RSActualizarEstatus();
        rs.setNumeroGuia(envioSeleccionado.getNumeroGuia());
        rs.setNuevoIdEstatus(cbEstatus.getValue().getIdEstatus());
        rs.setComentario(taComentario.getText().trim());
        rs.setIdColaborador(Sesion.getColaborador().getIdColaborador());

        int idEstatus = cbEstatus.getValue().getIdEstatus();
        if (idEstatus != ID_DETENIDO && idEstatus != ID_CANCELADO) {
            rs.setComentario(null);
        }

        if ((idEstatus == ID_DETENIDO || idEstatus == ID_CANCELADO)
                && (taComentario.getText() == null || taComentario.getText().trim().isEmpty())) {
            taComentario.getStyleClass().add("obligatorio");

            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "El comentario es obligatorio para estatus Detenido o Cancelado.",
                    Alert.AlertType.WARNING
            );
            return;
        } else {
            taComentario.getStyleClass().remove("obligatorio");

        }

        Respuesta respuesta = EnvioImp.actualizarEstatus(rs);

        if (!respuesta.isError()) {
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    respuesta.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

}
