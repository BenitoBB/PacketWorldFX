package packetworldfx;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldfx.dominio.SucursalImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Sucursal;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLSucursalesController implements Initializable, INotificador {

    @FXML
    private TableColumn<Sucursal, String> colCodigo;
    @FXML
    private TableColumn<Sucursal, String> colNombre;
    @FXML
    private TableColumn<Sucursal, String> colCalle;
    @FXML
    private TableColumn<Sucursal, String> colNumero;
    @FXML
    private TableColumn<Sucursal, String> colColonia;
    @FXML
    private TableColumn<Sucursal, String> colCodigoPostal;
    @FXML
    private TableColumn<Sucursal, String> colCiudad;
    @FXML
    private TableColumn<Sucursal, String> colEstado;

    @FXML
    private TableView<Sucursal> tvSucursales;

    private ObservableList<Sucursal> sucursales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionSucursales();
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colCalle.setCellValueFactory(c -> {
            if (c.getValue().getDireccion() != null) {
                return new SimpleStringProperty(
                        c.getValue().getDireccion().getCalle()
                );
            }
            return new SimpleStringProperty("");
        });

        colNumero.setCellValueFactory(c -> {
            if (c.getValue().getDireccion() != null) {
                return new SimpleStringProperty(
                        c.getValue().getDireccion().getNumero()
                );
            }
            return new SimpleStringProperty("");
        });
        colColonia.setCellValueFactory(c -> {
            if (c.getValue().getDireccion() != null) {
                return new SimpleStringProperty(
                        c.getValue().getDireccion().getColonia()
                );
            }
            return new SimpleStringProperty("");
        });
        colCodigoPostal.setCellValueFactory(c -> {
            if (c.getValue().getDireccion() != null) {
                return new SimpleStringProperty(
                        c.getValue().getDireccion().getCodigoPostal()
                );
            }
            return new SimpleStringProperty("");
        });

        colCiudad.setCellValueFactory(c -> {
            if (c.getValue().getDireccion() != null) {
                return new SimpleStringProperty(
                        c.getValue().getDireccion().getCiudad()
                );
            }
            return new SimpleStringProperty("");
        });

        colEstado.setCellValueFactory(c -> {
            if (c.getValue().getDireccion() != null) {
                return new SimpleStringProperty(
                        c.getValue().getDireccion().getEstado()
                );
            }
            return new SimpleStringProperty("");
        });
    }

    private void cargarInformacionSucursales() {
        HashMap<String, Object> respuesta = SucursalImp.obtenerTodas();

        if (!(boolean) respuesta.get("error")) {
            sucursales = FXCollections.observableArrayList(
                    (List<Sucursal>) respuesta.get("sucursales")
            );
            tvSucursales.setItems(sucursales);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.ERROR
            );
        }
    }

    private void irFormulario(Sucursal sucursal) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioSucursalController controlador = cargador.getController();
            controlador.inicializarDatos(sucursal, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Sucursal");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        System.out.println("Operacion " + operacion + " , nombre Colaborador: " + nombre);
        cargarInformacionSucursales();
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        Sucursal seleccionada = tvSucursales.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Utilidades.mostrarAlertaSimple(
                    "Selección requerida",
                    "Debes seleccionar una sucursal para editar.",
                    Alert.AlertType.WARNING
            );
            return;
        }
        irFormulario(seleccionada);
    }

    private void eliminarSucursal(Integer idSucursal) {
        Respuesta respuesta = SucursalImp.darDeBaja(idSucursal);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                    "Sucursal dada de baja",
                    "La sucursal fue dada de baja correctamente.",
                    Alert.AlertType.INFORMATION
            );
            cargarInformacionSucursales();
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
        Sucursal seleccionada = tvSucursales.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                    "Dar de baja sucursal",
                    "¿Estás seguro de dar de baja la sucursal \""
                    + seleccionada.getNombre()
                    + "\"?\nEsta acción no se puede deshacer."
            );

            if (confirmar) {
                eliminarSucursal(seleccionada.getIdSucursal());
            }
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Selección requerida",
                    "Debes seleccionar una sucursal para eliminarla.",
                    Alert.AlertType.WARNING
            );
        }
    }

}
