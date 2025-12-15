package packetworldfx;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldfx.dominio.PaqueteImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Paquete;
import packetworldfx.utilidad.Utilidades;
import packetworldfx.utilidad.Validaciones;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLFormularioPaqueteController implements Initializable {

    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfAlto;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfProfundidad;

    private Paquete paqueteEdicion;
    private INotificador observador;
    private Paquete paqueteOriginal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validaciones.letrasYNumeros(tfDescripcion);

        Validaciones.soloNumerosDecimales(tfPeso);
        Validaciones.soloNumerosDecimales(tfAlto);
        Validaciones.soloNumerosDecimales(tfAncho);
        Validaciones.soloNumerosDecimales(tfProfundidad);
    }

    private boolean huboCambios(Paquete nuevo) {
        return !nuevo.getDescripcion().equals(paqueteOriginal.getDescripcion())
                || nuevo.getPeso().compareTo(paqueteOriginal.getPeso()) != 0
                || nuevo.getAlto().compareTo(paqueteOriginal.getAlto()) != 0
                || nuevo.getAncho().compareTo(paqueteOriginal.getAncho()) != 0
                || nuevo.getProfundidad().compareTo(paqueteOriginal.getProfundidad()) != 0;
    }

    public void inicializarDatos(Paquete paqueteEdicion, INotificador observador) {
        this.paqueteEdicion = paqueteEdicion;
        this.observador = observador;

        if (paqueteEdicion != null) {
            // Copia para comparar cambios
            paqueteOriginal = new Paquete();
            paqueteOriginal.setDescripcion(paqueteEdicion.getDescripcion());
            paqueteOriginal.setPeso(paqueteEdicion.getPeso());
            paqueteOriginal.setAlto(paqueteEdicion.getAlto());
            paqueteOriginal.setAncho(paqueteEdicion.getAncho());
            paqueteOriginal.setProfundidad(paqueteEdicion.getProfundidad());

            tfDescripcion.setText(paqueteEdicion.getDescripcion());
            tfPeso.setText(String.valueOf(paqueteEdicion.getPeso()));
            tfAlto.setText(String.valueOf(paqueteEdicion.getAlto()));
            tfAncho.setText(String.valueOf(paqueteEdicion.getAncho()));
            tfProfundidad.setText(String.valueOf(paqueteEdicion.getProfundidad()));
        }
    }

    private void cerrarVentana() {
        Stage ventana = (Stage) tfDescripcion.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        // 1. Campos obligatorios
        if (!Validaciones.validarCamposObligatorios(
                new TextField[]{
                    tfDescripcion, tfPeso, tfAlto, tfAncho, tfProfundidad
                }
        )) {
            return;
        }

        // 2. Validaciones numéricas
        if (!Validaciones.validarDimensiones(
                tfPeso, tfAlto, tfAncho, tfProfundidad
        )) {
            return;
        }

        // 3. Construir Paquete
        Paquete paquete = new Paquete();
        paquete.setDescripcion(tfDescripcion.getText().trim());
        paquete.setPeso(new BigDecimal(tfPeso.getText().trim()));
        paquete.setAlto(new BigDecimal(tfAlto.getText().trim()));
        paquete.setAncho(new BigDecimal(tfAncho.getText().trim()));
        paquete.setProfundidad(new BigDecimal(tfProfundidad.getText().trim()));

        Respuesta respuesta;

        if (paqueteEdicion != null) {
            // EDICIÓN
            paquete.setIdPaquete(paqueteEdicion.getIdPaquete());
            paquete.setIdEnvio(paqueteEdicion.getIdEnvio());

            if (!huboCambios(paquete)) {
                Utilidades.mostrarAlertaSimple(
                        "Sin cambios",
                        "No se realizaron cambios en el paquete.",
                        Alert.AlertType.INFORMATION
                );
                cerrarVentana();
                return;
            }

            respuesta = PaqueteImp.actualizar(paquete);
        } else {
            // REGISTRO
            paquete.setIdEnvio(paqueteEdicion != null
                    ? paqueteEdicion.getIdEnvio()
                    : 1);

            respuesta = PaqueteImp.registrar(paquete);
        }

        if (!respuesta.isError()) {
            observador.notificarOperacionExitosa(
                    "Paquete",
                    paquete.getDescripcion()
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
