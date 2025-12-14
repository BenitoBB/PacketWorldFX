package packetworldfx;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldfx.dominio.SEPOMEXImp;
import packetworldfx.dominio.SucursalImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Direccion;
import packetworldfx.pojo.Sucursal;
import packetworldfx.utilidad.Utilidades;
import packetworldfx.utilidad.Validaciones;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLFormularioSucursalController implements Initializable {

    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<String> cbCiudad;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private ComboBox<String> cbColonia;

    private Sucursal sucursalEdicion;
    private INotificador observador;
    private Direccion direccionOriginal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validaciones.soloNumeros(tfCodigoPostal);
        Validaciones.soloNumeros(tfNumero);

        tfCodigoPostal.textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && newText.matches("\\d{5}")) {
                buscarDireccionPorCP();
            }
        });

    }

    private void limpiarCombos() {
        cbEstado.setValue(null);
        cbCiudad.getItems().clear();
        cbColonia.getItems().clear();
    }

    private void buscarDireccionPorCP() {
        String cp = tfCodigoPostal.getText().trim();

        if (!cp.matches("\\d{5}")) {
            return;
        }

        HashMap<String, Object> respuesta = SEPOMEXImp.buscarPorCP(cp);

        if (!(boolean) respuesta.get("error")) {

            String estado = (String) respuesta.get("estado");
            List<String> ciudades = (List<String>) respuesta.get("ciudades");
            List<String> colonias = (List<String>) respuesta.get("colonias");

            cbEstado.setItems(FXCollections.observableArrayList(estado));
            cbEstado.getSelectionModel().selectFirst();

            cbCiudad.setItems(FXCollections.observableArrayList(ciudades));
            cbCiudad.getSelectionModel().selectFirst();

            cbColonia.setItems(FXCollections.observableArrayList(colonias));
            cbColonia.getSelectionModel().selectFirst();

        } else {
            limpiarCombos();
        }
    }

    private boolean direccionCambio() {
        if (direccionOriginal == null) {
            return true;
        }

        return !direccionOriginal.getCalle().equals(tfCalle.getText().trim())
                || !direccionOriginal.getNumero().equals(tfNumero.getText().trim())
                || !direccionOriginal.getCodigoPostal().equals(tfCodigoPostal.getText().trim())
                || !direccionOriginal.getCiudad().equals(cbCiudad.getValue())
                || !direccionOriginal.getEstado().equals(cbEstado.getValue())
                || !direccionOriginal.getColonia().equals(cbColonia.getValue());
    }

    public void inicializarDatos(Sucursal sucursal, INotificador observador) {
        this.sucursalEdicion = sucursal;
        this.observador = observador;

        if (sucursal == null) {
            return;
        }

        tfCodigo.setText(sucursal.getCodigo());
        tfCodigo.setEditable(false);

        tfNombre.setText(sucursal.getNombre());

        if (sucursal.getDireccion() != null) {
            Direccion d = sucursal.getDireccion();
            direccionOriginal = d;

            tfCalle.setText(d.getCalle());
            tfNumero.setText(d.getNumero());
            tfCodigoPostal.setText(d.getCodigoPostal());

            buscarDireccionPorCP();

            cbCiudad.setValue(d.getCiudad());
            cbColonia.setValue(d.getColonia());
        }
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) tfNombre.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        if (!Validaciones.validarCamposObligatorios(
                new TextField[]{
                    tfCodigo, tfNombre, tfCalle, tfNumero, tfCodigoPostal
                }
        )) {
            return;
        }

        if (cbEstado.getValue() == null
                || cbCiudad.getValue() == null
                || cbColonia.getValue() == null) {

            Utilidades.mostrarAlertaSimple(
                    "Validación",
                    "Debes seleccionar estado, ciudad y colonia",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Direccion direccion;

        if (sucursalEdicion != null && !direccionCambio()) {
            direccion = direccionOriginal;
        } else {
            direccion = new Direccion();
            direccion.setCalle(tfCalle.getText().trim());
            direccion.setNumero(tfNumero.getText().trim());
            direccion.setCodigoPostal(tfCodigoPostal.getText().trim());
            direccion.setCiudad(cbCiudad.getValue());
            direccion.setEstado(cbEstado.getValue());
            direccion.setColonia(cbColonia.getValue());

            if (sucursalEdicion != null && direccionOriginal != null) {
                direccion.setIdDireccion(direccionOriginal.getIdDireccion());
            }
        }

        Sucursal sucursal = new Sucursal();
        sucursal.setCodigo(tfCodigo.getText().trim());
        sucursal.setNombre(tfNombre.getText().trim());
        sucursal.setDireccion(direccion);

        Respuesta respuesta;

        if (sucursalEdicion == null) {
            sucursal.setEstatus("ACTIVA");
            respuesta = SucursalImp.registrar(sucursal);
        } else {
            sucursal.setIdSucursal(sucursalEdicion.getIdSucursal());
            respuesta = SucursalImp.actualizar(sucursal);
        }

        if (!respuesta.isError()) {
            observador.notificarOperacionExitosa("Sucursal", sucursal.getNombre());
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
