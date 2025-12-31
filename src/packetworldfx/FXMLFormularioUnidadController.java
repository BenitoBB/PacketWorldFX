package packetworldfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldfx.dominio.UnidadImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Unidad;
import packetworldfx.utilidad.Validaciones;
import packetworldfx.interfaz.INotificador;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLFormularioUnidadController implements Initializable {

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfVIN;
    @FXML
    private TextField tfNoIdentificacion;
    @FXML
    private ComboBox<String> cbTipoUnidad;

    private Unidad unidadEdicion;
    private INotificador observador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validaciones.soloLetras(tfMarca);
        Validaciones.letrasYNumeros(tfModelo);
        Validaciones.soloNumeros(tfAnio);
        Validaciones.letrasYNumeros(tfVIN);
        Validaciones.letrasYNumeros(tfNoIdentificacion);

        cbTipoUnidad.getItems().addAll(Constantes.TIPOS_UNIDAD);

        tfVIN.textProperty().addListener((obs, oldText, newText) -> {
            actualizarNII();
        });
        tfAnio.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) { // el usuario salió del campo
                if (!Validaciones.validarAnio(tfAnio)) {
                    tfNoIdentificacion.setText("");
                } else {
                    actualizarNII();
                }
            }
        });

    }

    private void actualizarNII() {
        // Validar año antes de generar NII
        if (!Validaciones.validarAnio(tfAnio)) {
            tfNoIdentificacion.setText("");
            return;
        }

        String vin = tfVIN.getText().trim();
        int anio = Integer.parseInt(tfAnio.getText().trim());
        tfNoIdentificacion.setText(Validaciones.generarNII(anio, vin));
    }

    private boolean validarCamposObligatorios() {
        return Validaciones.validarCamposObligatorios(
                new TextField[]{tfMarca, tfModelo, tfAnio, tfVIN, tfNoIdentificacion},
                cbTipoUnidad
        );
    }

    private boolean validarVIN() {
        String vin = tfVIN.getText().trim();

        if (vin.length() != 17) {
            Utilidades.mostrarAlertaSimple(
                    "VIN inválido",
                    "El VIN debe contener exactamente 17 caracteres.",
                    Alert.AlertType.WARNING
            );
            tfVIN.requestFocus();
            return false;
        }
        return true;
    }

    public void inicializarDatos(Unidad unidadEdicion, INotificador observador) {
        this.unidadEdicion = unidadEdicion;
        this.observador = observador;

        if (unidadEdicion != null) {
            // Cargar datos en los campos
            tfMarca.setText(unidadEdicion.getMarca());
            tfModelo.setText(unidadEdicion.getModelo());
            tfAnio.setText(String.valueOf(unidadEdicion.getAnio()));
            tfVIN.setText(unidadEdicion.getVin());
            tfNoIdentificacion.setText(unidadEdicion.getNumeroIdentificacionInterno());

            cbTipoUnidad.setValue(unidadEdicion.getTipoUnidad());

            // VIN no editable en edición
            tfVIN.setEditable(false);
        }
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) tfMarca.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        // Validar campos obligatorios
        if (!validarCamposObligatorios()) {
            return;
        }

        // VALIDAR VIN (17 caracteres)
        if (!validarVIN()) {
            return;
        }

        // Construir objeto Unidad
        Unidad unidad = new Unidad();
        unidad.setMarca(tfMarca.getText().trim());
        unidad.setModelo(tfModelo.getText().trim());
        int anio = Integer.parseInt(tfAnio.getText().trim());
        unidad.setAnio(anio);
        String vin = tfVIN.getText().trim();
        unidad.setVin(vin);
        String tipoNormalizado = Validaciones.normalizarTipoUnidad(cbTipoUnidad.getValue());
        unidad.setTipoUnidad(tipoNormalizado);

        // Generar NII automáticamente usando Validaciones
        String nii = Validaciones.generarNII(anio, vin);
        unidad.setNumeroIdentificacionInterno(nii);
        tfNoIdentificacion.setText(nii);

        Respuesta respuesta;

        if (unidadEdicion == null) {
            // REGISTRO
            respuesta = UnidadImp.registrarUnidad(unidad);
        } else {
            // EDICIÓN
            unidad.setIdUnidad(unidadEdicion.getIdUnidad());
            respuesta = UnidadImp.actualizarUnidad(unidad);
        }

        // Manejar respuesta
        if (!respuesta.isError()) {
            observador.notificarOperacionExitosa("Unidad", unidad.getMarca() + " " + unidad.getModelo());
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
