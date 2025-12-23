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
import packetworldfx.dominio.SEPOMEXImp;
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
    private TextField tfNumeroGuia;

    private ObservableList<Sucursal> sucursales;
    @FXML
    private Label lbNumeroGuia;
    @FXML
    private Label lbCostoEnvio;

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
            lbNombreRemitente.setText(
                    cliente.getNombre() + " "
                    + cliente.getApellidoPaterno() + " "
                    + cliente.getApellidoMaterno()
            );

            lbCorreoRemitente.setText(cliente.getCorreo());
            lbTelefonoRemitente.setText(cliente.getTelefono());
        }
    }

    @FXML
    private void clickBuscarCliente(ActionEvent event) {
        irBuscarCliente();
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) tfNumeroGuia.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
    }

}
