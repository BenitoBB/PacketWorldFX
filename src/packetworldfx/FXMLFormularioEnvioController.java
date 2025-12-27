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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldfx.dominio.CatalogoImp;
import packetworldfx.dominio.EnvioImp;
import packetworldfx.dominio.SEPOMEXImp;
import packetworldfx.dto.RSEnvio;
import packetworldfx.dto.RSEnvioDetalle;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.ISeleccionCliente;
import packetworldfx.pojo.Cliente;
import packetworldfx.pojo.Sucursal;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Utilidades;
import packetworldfx.utilidad.Validaciones;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLFormularioEnvioController implements Initializable, ISeleccionCliente {

    @FXML
    private Label lbNombreRemitente;
    @FXML
    private Label lbCorreoRemitente;
    @FXML
    private Label lbTelefonoRemitente;
    @FXML
    private TextField tfNombreDestinatario;
    @FXML
    private TextField tfAPaternoDestinatario;
    @FXML
    private TextField tfAMaternoDestinatario;
    @FXML
    private ComboBox<Sucursal> cbSucursalOrigen;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumeroDeCalle;
    @FXML
    private ComboBox<String> cbColonia;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<String> cbCiudad;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private Label lbNumeroGuia;
    @FXML
    private Label lbCostoEnvio;

    private ObservableList<Sucursal> sucursales;
    private RSEnvioDetalle envioEdicion;
    private boolean modoEdicion = false;
    private Cliente clienteSeleccionado;
    private Runnable recalcularEnvio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validaciones.soloLetras(tfNombreDestinatario);
        Validaciones.soloLetras(tfAPaternoDestinatario);
        Validaciones.soloLetras(tfAMaternoDestinatario);
        Validaciones.letrasYNumeros(tfCalle);
        Validaciones.letrasYNumeros(tfNumeroDeCalle);

        cargarSucursales();

        tfCodigoPostal.textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && newText.matches("\\d{5}")) {
                buscarDireccionPorCP();
            }
        });
        agregarListenersCalculo();

    }

    private void calcularCosto() {

        double costoBase = 80.0;

        if (cbEstado.getValue() != null
                && !cbEstado.getValue().equalsIgnoreCase("Veracruz")) {
            costoBase += 40;
        }

        lbCostoEnvio.setText(
                String.format("$ %.2f", costoBase)
        );
    }

    private void agregarListenersCalculo() {

        recalcularEnvio = () -> {
            if (!modoEdicion
                    && clienteSeleccionado != null
                    && cbSucursalOrigen.getValue() != null
                    && cbCiudad.getValue() != null
                    && cbEstado.getValue() != null) {

                if (lbNumeroGuia.getText() == null || lbNumeroGuia.getText().isEmpty()) {

                    String guia = Validaciones.generarNumeroGuia(
                            clienteSeleccionado.getNombre(),
                            tfNombreDestinatario.getText(),
                            cbSucursalOrigen.getValue().getNombre(),
                            cbCiudad.getValue()
                    );

                    lbNumeroGuia.setText(guia);
                }

                calcularCosto();
            }
        };

        tfNombreDestinatario.textProperty().addListener((o, a, b) -> recalcularEnvio.run());
        tfAPaternoDestinatario.textProperty().addListener((o, a, b) -> recalcularEnvio.run());
        tfAMaternoDestinatario.textProperty().addListener((o, a, b) -> recalcularEnvio.run());
        tfCalle.textProperty().addListener((o, a, b) -> recalcularEnvio.run());
        tfNumeroDeCalle.textProperty().addListener((o, a, b) -> recalcularEnvio.run());
        tfCodigoPostal.textProperty().addListener((o, a, b) -> recalcularEnvio.run());

        cbSucursalOrigen.valueProperty().addListener((o, a, b) -> recalcularEnvio.run());
        cbCiudad.valueProperty().addListener((o, a, b) -> recalcularEnvio.run());
        cbEstado.valueProperty().addListener((o, a, b) -> recalcularEnvio.run());
    }

    private boolean camposCompletos() {
        return lbNombreRemitente.getText() != null
                && !lbNombreRemitente.getText().isEmpty()
                && !tfNombreDestinatario.getText().trim().isEmpty()
                && !tfAPaternoDestinatario.getText().trim().isEmpty()
                && cbSucursalOrigen.getValue() != null
                && !tfCalle.getText().trim().isEmpty()
                && !tfNumeroDeCalle.getText().trim().isEmpty()
                && tfCodigoPostal.getText().matches("\\d{5}")
                && cbColonia.getValue() != null
                && cbCiudad.getValue() != null
                && cbEstado.getValue() != null;
    }

    private void cargarDatosEnvio() {

        lbNumeroGuia.setText(envioEdicion.getNumeroGuia());
        lbCostoEnvio.setText(
                String.format("$ %.2f", envioEdicion.getCosto())
        );
        lbCostoEnvio.setDisable(true);

        tfNombreDestinatario.setText(envioEdicion.getNombreDestinatario());
        tfAPaternoDestinatario.setText(envioEdicion.getApellidoPaternoDestinatario());
        tfAMaternoDestinatario.setText(envioEdicion.getApellidoMaternoDestinatario());

        // Recuperar Cliente Remitente
        clienteSeleccionado = new Cliente();
        clienteSeleccionado.setIdCliente(envioEdicion.getIdClienteRemitente());
        clienteSeleccionado.setNombre(envioEdicion.getClienteNombre());
        clienteSeleccionado.setApellidoPaterno(envioEdicion.getClienteApellidoPaterno());
        clienteSeleccionado.setApellidoMaterno(envioEdicion.getClienteApellidoMaterno());
        clienteSeleccionado.setCorreo(envioEdicion.getClienteCorreo());
        clienteSeleccionado.setTelefono(envioEdicion.getClienteTelefono());

        lbNombreRemitente.setText(
                clienteSeleccionado.getNombre() + " "
                + clienteSeleccionado.getApellidoPaterno() + " "
                + clienteSeleccionado.getApellidoMaterno()
        );
        lbCorreoRemitente.setText(clienteSeleccionado.getCorreo());
        lbTelefonoRemitente.setText(clienteSeleccionado.getTelefono());

        // Sucursal
        for (Sucursal s : sucursales) {
            if (s.getNombre().equals(envioEdicion.getSucursalOrigen())) {
                cbSucursalOrigen.setValue(s);
                break;
            }
        }

        // Dirección
        tfCalle.setText(envioEdicion.getDirCalle());
        tfNumeroDeCalle.setText(envioEdicion.getDirNumero());
        tfCodigoPostal.setText(envioEdicion.getDirCP());

        buscarDireccionPorCP();

        cbCiudad.setValue(envioEdicion.getDirCiudad());
        cbEstado.setValue(envioEdicion.getDirEstado());
    }

    public void inicializarEdicion(String numeroGuia) {

        envioEdicion = EnvioImp.obtenerDetalleEnvio(numeroGuia);

        if (envioEdicion != null) {
            modoEdicion = true;
            cargarDatosEnvio();
        }
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

    private void cargarSucursales() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerSucursales();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Sucursal> sucursalesAPI = (List<Sucursal>) respuesta.get(Constantes.KEY_LISTA);
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(sucursalesAPI);
            cbSucursalOrigen.setItems(sucursales);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }

    private void irBuscarCliente() {
        try {
            FXMLLoader cargador
                    = new FXMLLoader(getClass().getResource("FXMLBusquedaCliente.fxml"));
            Parent vista = cargador.load();

            FXMLBusquedaClienteController controlador = cargador.getController();
            controlador.setObservador(this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Buscar Cliente");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void clienteSeleccionado(Cliente cliente) {
        if (cliente != null) {

            this.clienteSeleccionado = cliente;

            lbNombreRemitente.setText(
                    cliente.getNombre() + " "
                    + cliente.getApellidoPaterno() + " "
                    + cliente.getApellidoMaterno()
            );

            lbCorreoRemitente.setText(cliente.getCorreo());
            lbTelefonoRemitente.setText(cliente.getTelefono());

            if (recalcularEnvio != null) {
                recalcularEnvio.run();
            }
        }
    }

    private boolean huboCambiosGenerales() {
        return !tfNombreDestinatario.getText().trim()
                .equals(envioEdicion.getNombreDestinatario())
                || !tfAPaternoDestinatario.getText().trim()
                        .equals(envioEdicion.getApellidoPaternoDestinatario())
                || !String.valueOf(tfAMaternoDestinatario.getText())
                        .equals(String.valueOf(envioEdicion.getApellidoMaternoDestinatario()))
                || !cbSucursalOrigen.getValue().getNombre()
                        .equals(envioEdicion.getSucursalOrigen());
    }

    private boolean huboCambiosDireccion() {

        if (envioEdicion == null) {
            return false;
        }

        String calle = tfCalle.getText().trim();
        String numero = tfNumeroDeCalle.getText().trim();
        String cp = tfCodigoPostal.getText().trim();
        String colonia = cbColonia.getValue();
        String ciudad = cbCiudad.getValue();
        String estado = cbEstado.getValue();

        return !calle.equals(envioEdicion.getDirCalle())
                || !numero.equals(envioEdicion.getDirNumero())
                || !cp.equals(envioEdicion.getDirCP())
                || !String.valueOf(colonia).equals(envioEdicion.getDirColonia())
                || !String.valueOf(ciudad).equals(envioEdicion.getDirCiudad())
                || !String.valueOf(estado).equals(envioEdicion.getDirEstado());
    }

    private boolean huboCambios() {
        return !tfNombreDestinatario.getText().trim()
                .equals(envioEdicion.getNombreDestinatario())
                || !tfAPaternoDestinatario.getText().trim()
                        .equals(envioEdicion.getApellidoPaternoDestinatario())
                || !String.valueOf(tfAMaternoDestinatario.getText())
                        .equals(String.valueOf(envioEdicion.getApellidoMaternoDestinatario()))
                || !tfCalle.getText().trim().equals(envioEdicion.getDirCalle())
                || !tfNumeroDeCalle.getText().trim().equals(envioEdicion.getDirNumero())
                || !tfCodigoPostal.getText().trim().equals(envioEdicion.getDirCP())
                || !String.valueOf(cbCiudad.getValue())
                        .equals(String.valueOf(envioEdicion.getDirCiudad()))
                || !cbEstado.getValue().equals(envioEdicion.getDirEstado())
                || !cbSucursalOrigen.getValue().getNombre().equals(envioEdicion.getSucursalOrigen());

    }

    @FXML
    private void clickBuscarCliente(ActionEvent event) {
        irBuscarCliente();
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) lbNumeroGuia.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void actualizarEnvio(boolean actualizarDireccion) {

        RSEnvio envio = new RSEnvio();
        envio.setNumeroGuia(envioEdicion.getNumeroGuia());

        envio.setIdClienteRemitente(clienteSeleccionado.getIdCliente());
        envio.setNombreDestinatario(tfNombreDestinatario.getText().trim());
        envio.setApellidoPaternoDestinatario(tfAPaternoDestinatario.getText().trim());
        envio.setApellidoMaternoDestinatario(
                Validaciones.textoONull(tfAMaternoDestinatario)
        );
        envio.setIdSucursalOrigen(cbSucursalOrigen.getValue().getIdSucursal());

        if (actualizarDireccion) {
            envio.setCalle(tfCalle.getText().trim());
            envio.setNumero(tfNumeroDeCalle.getText().trim());
            envio.setCodigoPostal(tfCodigoPostal.getText().trim());
            envio.setColonia(cbColonia.getValue());
            envio.setCiudad(cbCiudad.getValue());
            envio.setEstado(cbEstado.getValue());
        }

        Respuesta respuesta = EnvioImp.actualizar(envio);

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

    @FXML
    private void clickGuardar(ActionEvent event) {
        // MODO EDICIÓN
        if (modoEdicion) {

            boolean cambiosGenerales = huboCambiosGenerales();
            boolean cambiosDireccion = huboCambiosDireccion();

            if (!cambiosGenerales && !cambiosDireccion) {
                cerrarVentana();
                return;
            }

            actualizarEnvio(cambiosDireccion);
            return;
        }

        // REGISTRO NUEVO
        if (!camposCompletos()) {
            Utilidades.mostrarAlertaSimple(
                    "Datos incompletos",
                    "Completa todos los campos obligatorios.",
                    Alert.AlertType.WARNING
            );
            return;
        }
        if (clienteSeleccionado == null) {
            Utilidades.mostrarAlertaSimple(
                    "Cliente requerido",
                    "Debes seleccionar un cliente remitente.",
                    Alert.AlertType.WARNING
            );
            return;
        }
        if (!Validaciones.validarNumeroGuia(lbNumeroGuia.getText())) {
            return;
        }

        calcularCosto();

        RSEnvio envio = new RSEnvio();

        envio.setNumeroGuia(lbNumeroGuia.getText());
        envio.setNombreDestinatario(tfNombreDestinatario.getText().trim());
        envio.setApellidoPaternoDestinatario(tfAPaternoDestinatario.getText().trim());
        envio.setApellidoMaternoDestinatario(
                Validaciones.textoONull(tfAMaternoDestinatario)
        );

        envio.setIdSucursalOrigen(
                cbSucursalOrigen.getValue().getIdSucursal()
        );

        envio.setIdClienteRemitente(
                clienteSeleccionado.getIdCliente()
        );

        // Dirección
        envio.setCalle(tfCalle.getText().trim());
        envio.setNumero(tfNumeroDeCalle.getText().trim());
        envio.setColonia(cbColonia.getValue());
        envio.setCodigoPostal(tfCodigoPostal.getText().trim());
        envio.setCiudad(cbCiudad.getValue());
        envio.setEstado(cbEstado.getValue());

        // Costo
        envio.setCosto(
                new java.math.BigDecimal(
                        lbCostoEnvio.getText().replace("$", "").trim()
                )
        );

        Respuesta respuesta = EnvioImp.registrarEnvio(envio);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                    "Éxito",
                    respuesta.getMensaje(),
                    Alert.AlertType.INFORMATION
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
