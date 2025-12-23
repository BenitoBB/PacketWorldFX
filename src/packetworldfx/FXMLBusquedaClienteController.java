package packetworldfx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import packetworldfx.dominio.ClienteImp;
import packetworldfx.interfaz.ISeleccionCliente;
import packetworldfx.pojo.Cliente;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLBusquedaClienteController implements Initializable {

    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colAPaterno;
    @FXML
    private TableColumn colAMaterno;
    @FXML
    private TableColumn<Cliente, String> colCalle;
    @FXML
    private TableColumn<Cliente, String> colNumero;
    @FXML
    private TableColumn<Cliente, String> colColonia;
    @FXML
    private TableColumn<Cliente, String> colCodigoPostal;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TextField tfBuscar;

    private ObservableList<Cliente> clientes;
    private ISeleccionCliente observador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionClientes();
        tfBuscar.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarClientes(newValue);
        });
    }

    private void filtrarClientes(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            tvClientes.setItems(clientes);
            return;
        }

        String filtro = texto.trim().toLowerCase();
        ObservableList<Cliente> filtrados = FXCollections.observableArrayList();

        for (Cliente c : clientes) {

            boolean coincideNombre
                    = c.getNombre().toLowerCase().contains(filtro)
                    || (c.getApellidoPaterno() != null && c.getApellidoPaterno().toLowerCase().contains(filtro))
                    || (c.getApellidoMaterno() != null && c.getApellidoMaterno().toLowerCase().contains(filtro));

            boolean coincideContacto
                    = (c.getTelefono() != null && c.getTelefono().contains(filtro))
                    || (c.getCorreo() != null && c.getCorreo().toLowerCase().contains(filtro));

            if (coincideNombre || coincideContacto) {
                filtrados.add(c);
            }
        }

        tvClientes.setItems(filtrados);
    }

    private void cargarInformacionClientes() {
        List<Cliente> clientesAPI = ClienteImp.obtenerTodos();

        if (clientesAPI != null) {
            clientes = FXCollections.observableArrayList();
            clientes.addAll(clientesAPI);
            tvClientes.setItems(clientes);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo cargar la información de clientes",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colAMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        // Datos de la Dirección
        colCalle.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getDireccion().getCalle()
                )
        );
        colNumero.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getDireccion().getNumero()
                )
        );
        colColonia.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getDireccion().getColonia()
                )
        );
        colCodigoPostal.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getDireccion().getCodigoPostal()
                )
        );
    }

    public void setObservador(ISeleccionCliente observador) {
        this.observador = observador;
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) tfBuscar.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickSeleccionar(ActionEvent event) {
        Cliente seleccionado = tvClientes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un cliente",
                    "Debes seleccionar un cliente de la tabla",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (observador != null) {
            observador.clienteSeleccionado(seleccionado);
        }

        cerrarVentana();
    }

}
