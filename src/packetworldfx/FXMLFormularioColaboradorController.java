package packetworldfx;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import packetworldfx.dominio.CatalogoImp;
import packetworldfx.dominio.ColaboradorImp;
import packetworldfx.dto.Respuesta;
import packetworldfx.interfaz.INotificador;
import packetworldfx.pojo.Colaborador;
import packetworldfx.pojo.Rol;
import packetworldfx.pojo.Sucursal;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Utilidades;
import packetworldfx.utilidad.Validaciones;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLFormularioColaboradorController implements Initializable {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAPaterno;
    @FXML
    private TextField tfAMaterno;
    @FXML
    private TextField tfCurp;
    @FXML
    private ComboBox<Rol> cbRol;
    private ObservableList<Rol> roles;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfLicencia;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    private ObservableList<Sucursal> sucursales;
    @FXML
    private Label lbLicencia;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private ImageView ivFoto;

    private Colaborador colaboradorEdicion;
    private INotificador observador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();
        cargarSucursales();

        Validaciones.soloLetras(tfNombre);
        Validaciones.soloLetras(tfAPaterno);
        Validaciones.soloLetras(tfAMaterno);

        Validaciones.soloNumeros(tfTelefono);
        Validaciones.letrasYNumeros(tfCurp);

        cbRol.valueProperty().addListener((obs, oldRol, newRol) -> {
            if (newRol != null) {
                mostrarCampoLicencia(newRol.getIdRol());
            }
        });
        mostrarCampoLicencia(null);
    }

    private int obtenerPosicionRol(Integer idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getIdRol().equals(idRol)) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPosicionSucursal(Integer idSucursal) {
        for (int i = 0; i < sucursales.size(); i++) {
            if (sucursales.get(i).getIdSucursal().equals(idSucursal)) {
                return i;
            }
        }
        return -1;
    }

    private boolean esRolConductor(Integer idRol) {
        return idRol != null && Constantes.ID_ROL_CONDUCTOR.equals(idRol);
    }

    private void mostrarCampoLicencia(Integer idRol) {
        boolean esConductor = esRolConductor(idRol);

        tfLicencia.setVisible(esConductor);
        tfLicencia.setManaged(esConductor);
        lbLicencia.setVisible(esConductor);

        if (!esConductor) {
            tfLicencia.clear();
        }
    }

    private boolean validarCamposObligatorios() {

        if (tfNoPersonal.getText().trim().isEmpty()
                || tfNombre.getText().trim().isEmpty()
                || tfAPaterno.getText().trim().isEmpty()
                || tfCurp.getText().trim().isEmpty()
                || tfCorreo.getText().trim().isEmpty()
                || tfTelefono.getText().trim().isEmpty()
                || cbRol.getValue() == null
                || cbSucursal.getValue() == null) {

            Utilidades.mostrarAlertaSimple(
                    "Campos requeridos",
                    Constantes.MSJ_DATOS_REQUERIDOS,
                    Alert.AlertType.WARNING
            );
            return false;
        }

        return true;
    }

    private boolean validarLicenciaConductor() {

        Rol rolSeleccionado = cbRol.getValue();

        if (rolSeleccionado != null
                && esRolConductor(rolSeleccionado.getIdRol())
                && tfLicencia.getText().trim().isEmpty()) {

            Utilidades.mostrarAlertaSimple(
                    "Licencia requerida",
                    Constantes.MSJ_ERROR_LICENCIA_REQUERIDA,
                    Alert.AlertType.WARNING
            );
            return false;
        }

        return true;
    }

    public void inicializarDatos(Colaborador colaboradorEdicion, INotificador observador) {
        this.colaboradorEdicion = colaboradorEdicion;
        this.observador = observador;

        // Cargar roles y sucursales primero
        cargarRoles();
        cargarSucursales();

        if (colaboradorEdicion != null) {
            tfNoPersonal.setText(colaboradorEdicion.getNoPersonal());
            tfNombre.setText(colaboradorEdicion.getNombre());
            tfAPaterno.setText(colaboradorEdicion.getApellidoPaterno());
            tfAMaterno.setText(colaboradorEdicion.getApellidoMaterno());
            tfCurp.setText(colaboradorEdicion.getCurp());
            tfTelefono.setText(colaboradorEdicion.getTelefono());
            tfCorreo.setText(colaboradorEdicion.getCorreo());
            tfLicencia.setText(colaboradorEdicion.getNumeroLicencia());

            // Seleccionar Rol
            int posicionRol = obtenerPosicionRol(colaboradorEdicion.getIdRol());
            cbRol.getSelectionModel().select(posicionRol);
            // Deshabilitar cambio de rol en edición
            cbRol.setDisable(true);
            // No permitir cambiar número de personal
            tfNoPersonal.setEditable(false);

            // Seleccionar Sucursal
            int posicionSucursal = obtenerPosicionSucursal(colaboradorEdicion.getIdSucursal());
            cbSucursal.getSelectionModel().select(posicionSucursal);

            // No permitir cambiar número de personal
            tfNoPersonal.setEditable(false);

            // Mostrar u ocultar licencia según rol
            mostrarCampoLicencia(colaboradorEdicion.getIdRol());

            // Password 
            pfPassword.clear();
            pfPassword.setPromptText("Deja vacío si no deseas cambiar la contraseña");
        }
    }

    private void cargarRoles() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerRoles();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Rol> rolesAPI = (List<Rol>) respuesta.get(Constantes.KEY_LISTA);
            roles = FXCollections.observableArrayList();
            roles.addAll(rolesAPI);
            cbRol.setItems(roles);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }

    private void cargarSucursales() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerSucursales();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Sucursal> sucursalesAPI = (List<Sucursal>) respuesta.get(Constantes.KEY_LISTA);
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(sucursalesAPI);
            cbSucursal.setItems(sucursales);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            cerrarVentana();
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

    private boolean huboCambios() {
        if (colaboradorEdicion == null) {
            return true;
        }

        return !tfNombre.getText().trim().equals(colaboradorEdicion.getNombre())
                || !tfAPaterno.getText().trim().equals(colaboradorEdicion.getApellidoPaterno())
                || !String.valueOf(Validaciones.textoONull(tfAMaterno)).equals(String.valueOf(colaboradorEdicion.getApellidoMaterno()))
                || !tfCurp.getText().trim().equals(colaboradorEdicion.getCurp())
                || !tfTelefono.getText().trim().equals(colaboradorEdicion.getTelefono())
                || !tfCorreo.getText().trim().equals(colaboradorEdicion.getCorreo())
                || !cbSucursal.getValue().getIdSucursal().equals(colaboradorEdicion.getIdSucursal())
                || (esRolConductor(cbRol.getValue().getIdRol())
                && !tfLicencia.getText().trim().equals(colaboradorEdicion.getNumeroLicencia()))
                || !pfPassword.getText().trim().isEmpty();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        // Si es edición y no hubo cambios → cerrar
        if (colaboradorEdicion != null && !huboCambios()) {
            cerrarVentana();
            return;
        }

        // 1. Validar campos obligatorios
        if (!Validaciones.validarCamposObligatorios(
                new TextField[]{tfNoPersonal, tfNombre, tfAPaterno, tfCurp, tfCorreo, tfTelefono},
                cbRol, cbSucursal
        )) {
            return;
        }

        // 2. Validaciones de negocio
        if (!validarLicenciaConductor()) {
            return;
        }

        if (!Validaciones.validarCorreo(tfCorreo)
                || !Validaciones.validarTelefono(tfTelefono)
                || !Validaciones.validarCurp(tfCurp)) {
            return;
        }

        // 3. Construir objeto Colaborador
        Colaborador colaborador = new Colaborador();
        colaborador.setNoPersonal(tfNoPersonal.getText().trim());
        colaborador.setNombre(tfNombre.getText().trim());
        colaborador.setApellidoPaterno(tfAPaterno.getText().trim());
        colaborador.setApellidoMaterno(Validaciones.textoONull(tfAMaterno));
        colaborador.setCurp(tfCurp.getText().trim());
        colaborador.setTelefono(tfTelefono.getText().trim());
        colaborador.setCorreo(tfCorreo.getText().trim());
        colaborador.setIdRol(cbRol.getValue().getIdRol());
        colaborador.setIdSucursal(cbSucursal.getValue().getIdSucursal());

        if (esRolConductor(cbRol.getValue().getIdRol())) {
            colaborador.setNumeroLicencia(tfLicencia.getText().trim());
        }

        Respuesta respuesta;

        if (colaboradorEdicion == null) {
            // REGISTRO
            if (pfPassword.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple(
                        "Contraseña requerida",
                        "La contraseña es obligatoria",
                        Alert.AlertType.WARNING
                );
                return;
            }

            colaborador.setPassword(pfPassword.getText().trim());
            respuesta = ColaboradorImp.registrarColaborador(colaborador);

        } else {
            // EDICIÓN
            colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());

            // Solo setear password si el usuario escribió algo
            if (!pfPassword.getText().trim().isEmpty()) {
                colaborador.setPassword(pfPassword.getText().trim());
            } else {
                colaborador.setPassword(null);
            }

            respuesta = ColaboradorImp.actualizarColaborador(colaborador);
        }

        // 4. Respuesta
        if (!respuesta.isError()) {
            observador.notificarOperacionExitosa(
                    "Colaborador",
                    colaborador.getNombre()
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
