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
import packetworldfx.dominio.ClienteImp;
import packetworldfx.dominio.SEPOMEXImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Cliente;
import packetworldfx.pojo.Direccion;
import packetworldfx.utilidad.Utilidades;
import packetworldfx.utilidad.Validaciones;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLFormularioClienteController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAPaterno;
    @FXML
    private TextField tfAMaterno;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumeroCalle;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    @FXML
    private ComboBox<String> cbColonia;

    private Cliente clienteEdicion;
    private INotificador observador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validaciones.soloLetras(tfNombre);
        Validaciones.soloLetras(tfAPaterno);
        Validaciones.soloLetras(tfAMaterno);

        Validaciones.soloNumeros(tfTelefono);
        Validaciones.soloNumeros(tfCodigoPostal);
        Validaciones.soloNumeros(tfNumeroCalle);

        tfCodigoPostal.textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && newText.matches("\\d{5}")) {
                buscarColoniasPorCP();
            }
        });
    }

    private void buscarColoniasPorCP() {
        String cp = tfCodigoPostal.getText().trim();

        if (!cp.matches("\\d{5}")) {
            return;
        }

        HashMap<String, Object> respuesta = SEPOMEXImp.buscarPorCP(cp);

        if (!(boolean) respuesta.get("error")) {
            List<String> colonias = (List<String>) respuesta.get("colonias");
            cbColonia.setItems(FXCollections.observableArrayList(colonias));
            cbColonia.getSelectionModel().clearSelection();
        } else {
            cbColonia.getItems().clear();
        }
    }

    private boolean huboCambios() {
        if (clienteEdicion == null) {
            return true;
        }

        return !tfNombre.getText().trim().equals(clienteEdicion.getNombre())
                || !tfAPaterno.getText().trim().equals(clienteEdicion.getApellidoPaterno())
                || !String.valueOf(tfAMaterno.getText())
                        .equals(String.valueOf(clienteEdicion.getApellidoMaterno()))
                || !tfTelefono.getText().trim().equals(clienteEdicion.getTelefono())
                || !tfCorreo.getText().trim().equals(clienteEdicion.getCorreo())
                || !tfCalle.getText().trim().equals(clienteEdicion.getDireccion().getCalle())
                || !tfNumeroCalle.getText().trim().equals(clienteEdicion.getDireccion().getNumero())
                || !String.valueOf(cbColonia.getValue())
                        .equals(String.valueOf(clienteEdicion.getDireccion().getColonia()))
                || !tfCodigoPostal.getText().trim().equals(clienteEdicion.getDireccion().getCodigoPostal());
    }

    public void inicializarDatos(Cliente cliente, INotificador observador) {
        this.clienteEdicion = cliente;
        this.observador = observador;

        if (cliente != null) {
            tfNombre.setText(cliente.getNombre());
            tfAPaterno.setText(cliente.getApellidoPaterno());
            tfAMaterno.setText(cliente.getApellidoMaterno());
            tfTelefono.setText(cliente.getTelefono());
            tfCorreo.setText(cliente.getCorreo());

            if (cliente.getDireccion() != null) {
                Direccion d = cliente.getDireccion();

                tfCalle.setText(d.getCalle());
                tfNumeroCalle.setText(d.getNumero());
                tfCodigoPostal.setText(d.getCodigoPostal());

                buscarColoniasPorCP();
                cbColonia.setValue(d.getColonia());
            }

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

        // Si es edición y no hubo cambios
        if (clienteEdicion != null && !huboCambios()) {
            cerrarVentana();
            return;
        }

        // Validaciones obligatorias
        if (!Validaciones.validarCamposObligatorios(
                new TextField[]{
                    tfNombre, tfAPaterno, tfTelefono,
                    tfCorreo, tfCalle, tfNumeroCalle, tfCodigoPostal
                }
        )) {
            return;
        }

        if (!Validaciones.validarTelefono(tfTelefono)
                || !Validaciones.validarCorreo(tfCorreo)) {
            return;
        }

        // Construir Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText().trim());
        cliente.setApellidoPaterno(tfAPaterno.getText().trim());
        cliente.setApellidoMaterno(Validaciones.textoONull(tfAMaterno));
        cliente.setTelefono(tfTelefono.getText().trim());
        cliente.setCorreo(tfCorreo.getText().trim());

        // Dirección
        Direccion direccion = new Direccion();
        // SI ES EDICIÓN → conservar ID
        if (clienteEdicion != null && clienteEdicion.getDireccion() != null) {
            direccion.setIdDireccion(
                    clienteEdicion.getDireccion().getIdDireccion()
            );
        }
        direccion.setCalle(tfCalle.getText().trim());
        direccion.setNumero(tfNumeroCalle.getText().trim());
        direccion.setCodigoPostal(tfCodigoPostal.getText().trim());
        direccion.setColonia(
                cbColonia.getValue() != null ? cbColonia.getValue() : null
        );

        cliente.setDireccion(direccion);

        Respuesta respuesta;

        // conservar ciudad y estado si no se editan
        if (clienteEdicion != null && clienteEdicion.getDireccion() != null) {
            direccion.setCiudad(
                    clienteEdicion.getDireccion().getCiudad()
            );
            direccion.setEstado(
                    clienteEdicion.getDireccion().getEstado()
            );
        }

        if (clienteEdicion == null) {
            // REGISTRO
            respuesta = ClienteImp.registrar(cliente);
        } else {
            // EDICIÓN
            cliente.setIdCliente(clienteEdicion.getIdCliente());
            respuesta = ClienteImp.actualizar(cliente);
        }

        if (!respuesta.isError()) {
            observador.notificarOperacionExitosa(
                    "Cliente",
                    cliente.getNombre()
            );
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
