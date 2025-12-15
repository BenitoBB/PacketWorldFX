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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldfx.dominio.ClienteImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Cliente;
import packetworldfx.interfaz.INotificador;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benit0
 */
public class FXMLClientesController implements Initializable, INotificador {

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

    private void irFormulario(Cliente cliente) {
        try {
            FXMLLoader cargador = new FXMLLoader(
                    getClass().getResource("FXMLFormularioCliente.fxml")
            );
            Parent vista = cargador.load();

            FXMLFormularioClienteController controlador
                    = cargador.getController();

            controlador.inicializarDatos(cliente, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Cliente");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo abrir el formulario de cliente",
                    Alert.AlertType.ERROR
            );
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        System.out.println("Operacion " + operacion + " , nombre Cliente: " + nombre);
        cargarInformacionClientes();
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        Cliente seleccionado = tvClientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            irFormulario(seleccionado);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un cliente",
                    "Debes seleccionar un cliente para editar.",
                    Alert.AlertType.WARNING
            );
        }
    }

    private void eliminarCliente(int idCliente) {

        Respuesta respuesta = ClienteImp.eliminar(idCliente);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                    "Registro eliminado",
                    "El cliente fue eliminado correctamente.",
                    Alert.AlertType.INFORMATION
            );
            cargarInformacionClientes();
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error al eliminar",
                    respuesta.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        Cliente seleccionado = tvClientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                    "Eliminar cliente",
                    "¿Estás seguro de eliminar al cliente "
                    + seleccionado.getNombre() + " "
                    + seleccionado.getApellidoPaterno()
                    + "?\nEsta acción no se puede deshacer."
            );

            if (confirmar) {
                eliminarCliente(seleccionado.getIdCliente());
            }

        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selecciona un cliente",
                    "Debes seleccionar un cliente para eliminarlo.",
                    Alert.AlertType.WARNING
            );
        }
    }

}
