package packetworldfx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldfx.dominio.ConductorUnidadImp;
import packetworldfx.dto.RSConductorUnidad;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Colaborador;
import packetworldfx.pojo.Unidad;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLAsignacionConductorUnidadController implements Initializable {

    @FXML
    private TableView<Unidad> tvUnidades;
    @FXML
    private TableView<Colaborador> tvConductores;
    @FXML
    private TableColumn<Unidad, String> colMarca;
    @FXML
    private TableColumn<Unidad, String> colModelo;
    @FXML
    private TableColumn<Colaborador, String> colAPaterno;
    @FXML
    private TableColumn<Colaborador, String> colAMaterno;
    @FXML
    private TableColumn<Colaborador, String> colNombre;
    @FXML
    private Label lbIndicacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
        cargarConductores();
        cargarUnidades();
        configurarListeners();
        configurarColoresConductores();
        configurarColoresUnidades();
    }

    private void configurarColoresConductores() {

        tvConductores.setRowFactory(tv -> new TableRow<Colaborador>() {
            @Override
            protected void updateItem(Colaborador conductor, boolean empty) {
                super.updateItem(conductor, empty);

                if (conductor == null || empty) {
                    setStyle("");
                    return;
                }

                RSConductorUnidad rs
                        = ConductorUnidadImp.obtenerUnidadActualPorConductor(
                                conductor.getIdColaborador()
                        );

                if (rs != null && !rs.isError()) {
                    // Tiene unidad asignada
                    setStyle("-fx-background-color: #ffcccc;");
                } else {
                    // Libre
                    setStyle("-fx-background-color: #ccffcc;");
                }
            }
        });
    }

    private void configurarColoresUnidades() {

        tvUnidades.setRowFactory(tv -> new TableRow<Unidad>() {
            @Override
            protected void updateItem(Unidad unidad, boolean empty) {
                super.updateItem(unidad, empty);

                if (unidad == null || empty) {
                    setStyle("");
                    return;
                }

                RSConductorUnidad rs
                        = ConductorUnidadImp.obtenerConductorActualPorUnidad(
                                unidad.getIdUnidad()
                        );

                if (rs != null && !rs.isError()) {
                    // Unidad ocupada
                    setStyle("-fx-background-color: #ffcccc;");
                } else {
                    // Unidad libre
                    setStyle("-fx-background-color: #ccffcc;");
                }
            }
        });
    }

    private void actualizarIndicacion() {
        Colaborador conductor = tvConductores.getSelectionModel().getSelectedItem();
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();

        if (conductor == null && unidad == null) {
            lbIndicacion.setText("Selecciona un conductor o una unidad.");
        } else if (conductor != null && unidad == null) {
            lbIndicacion.setText("Selecciona una unidad o desasigna la unidad actual del conductor.");
        } else if (conductor == null && unidad != null) {
            lbIndicacion.setText("Selecciona un conductor o desasigna el conductor actual de la unidad.");
        } else {
            lbIndicacion.setText("Listo para asignar conductor a unidad.");
        }
    }

    private void configurarListeners() {
        tvConductores.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> actualizarIndicacion()
        );

        tvUnidades.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> actualizarIndicacion()
        );
    }

    private void configurarTablas() {
        // Conductores 
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );
        colAPaterno.setCellValueFactory(
                new PropertyValueFactory<>("apellidoPaterno")
        );
        colAMaterno.setCellValueFactory(
                new PropertyValueFactory<>("apellidoMaterno")
        );

        // Unidades 
        colMarca.setCellValueFactory(
                new PropertyValueFactory<>("marca")
        );
        colModelo.setCellValueFactory(
                new PropertyValueFactory<>("modelo")
        );
    }

    private void cargarConductores() {

        List<Colaborador> conductores
                = ConductorUnidadImp.obtenerConductores();

        if (conductores != null) {
            tvConductores.setItems(
                    FXCollections.observableArrayList(conductores)
            );
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo cargar la información de conductores",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void cargarUnidades() {

        List<Unidad> unidades
                = ConductorUnidadImp.obtenerUnidadesActivas();

        if (unidades != null) {
            tvUnidades.setItems(
                    FXCollections.observableArrayList(unidades)
            );
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo cargar la información de unidades",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void clickAsignar(ActionEvent event) {
        Colaborador conductor = tvConductores.getSelectionModel().getSelectedItem();
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();

        if (conductor == null || unidad == null) {
            Utilidades.mostrarAlertaSimple(
                    "Selección incompleta",
                    "Debes seleccionar un conductor y una unidad.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                "Asignar conductor",
                "¿Deseas asignar al conductor "
                + conductor.getNombre() + " "
                + conductor.getApellidoPaterno()
                + " a la unidad "
                + unidad.getMarca() + " " + unidad.getModelo()
                + "?"
        );

        if (!confirmar) {
            return;
        }

        Respuesta respuesta = ConductorUnidadImp.asignar(
                conductor.getIdColaborador(),
                unidad.getIdUnidad()
        );

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                    "Asignación exitosa",
                    "El conductor fue asignado correctamente.",
                    Alert.AlertType.INFORMATION
            );
            cargarConductores();
            cargarUnidades();
            lbIndicacion.setText("Selecciona un conductor y una unidad para asignar.");
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    respuesta.getMensaje(),
                    Alert.AlertType.ERROR
            );
        }
    }

    private void limpiarSeleccion() {
        tvConductores.getSelectionModel().clearSelection();
        tvUnidades.getSelectionModel().clearSelection();
        lbIndicacion.setText("Selecciona un conductor o una unidad.");
    }

    @FXML
    private void clickDesasignar(ActionEvent event) {
        Colaborador conductor = tvConductores.getSelectionModel().getSelectedItem();
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();

        if (conductor == null && unidad == null) {
            Utilidades.mostrarAlertaSimple(
                    "Sin selección",
                    "Selecciona un conductor o una unidad para desasignar.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (conductor != null) {

            RSConductorUnidad rs = ConductorUnidadImp.obtenerUnidadActualPorConductor(
                    conductor.getIdColaborador()
            );

            if (rs.isError()) {
                Utilidades.mostrarAlertaSimple(
                        "Sin asignación",
                        "Este conductor no tiene una unidad asignada.",
                        Alert.AlertType.INFORMATION
                );
                return;
            }

            boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                    "Desasignar unidad",
                    "¿Deseas desasignar la unidad del conductor "
                    + conductor.getNombre() + " " + conductor.getApellidoPaterno() + "?"
            );

            if (!confirmar) {
                return;
            }

            Respuesta respuesta = ConductorUnidadImp.desasignarPorConductor(
                    conductor.getIdColaborador()
            );

            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple(
                        "Desasignación exitosa",
                        respuesta.getMensaje(),
                        Alert.AlertType.INFORMATION
                );
                cargarConductores();
                cargarUnidades();
                limpiarSeleccion();
            } else {
                Utilidades.mostrarAlertaSimple(
                        "Error",
                        respuesta.getMensaje(),
                        Alert.AlertType.ERROR
                );
            }
            return;
        }

        // 🔹 DESASIGNAR POR UNIDAD
        if (unidad != null) {

            RSConductorUnidad rs = ConductorUnidadImp.obtenerConductorActualPorUnidad(
                    unidad.getIdUnidad()
            );

            if (rs.isError()) {
                Utilidades.mostrarAlertaSimple(
                        "Sin asignación",
                        "Esta unidad no tiene un conductor asignado.",
                        Alert.AlertType.INFORMATION
                );
                return;
            }

            boolean confirmar = Utilidades.mostrarAlertaConfirmacion(
                    "Desasignar conductor",
                    "¿Deseas desasignar el conductor de la unidad "
                    + unidad.getMarca() + " " + unidad.getModelo() + "?"
            );

            if (!confirmar) {
                return;
            }

            Respuesta respuesta = ConductorUnidadImp.desasignarPorUnidad(
                    unidad.getIdUnidad()
            );

            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple(
                        "Desasignación exitosa",
                        respuesta.getMensaje(),
                        Alert.AlertType.INFORMATION
                );
                cargarConductores();
                cargarUnidades();
                limpiarSeleccion();
            } else {
                Utilidades.mostrarAlertaSimple(
                        "Error",
                        respuesta.getMensaje(),
                        Alert.AlertType.ERROR
                );
            }
        }
    }
}
