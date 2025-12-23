package packetworldfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import packetworldfx.pojo.Colaborador;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private AnchorPane apCentral;
    @FXML
    private Label lbHeader;
    @FXML
    private Label lbDatosUsuario;
    private Colaborador colaboradorSesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickCerrarSesion(ActionEvent event) {
        cerrarSesion();
    }

    @FXML
    private void clickIrColaboradores(ActionEvent event) {
        lbHeader.setText("Administración de Colaboradores");
        cargarVista("FXMLColaboradores.fxml");
    }

    @FXML
    private void clickIrClientes(ActionEvent event) {
        lbHeader.setText("Administración de Clientes");
        cargarVista("FXMLClientes.fxml");
    }

    @FXML
    private void clickIrSucursales(ActionEvent event) {
        lbHeader.setText("Administración de Sucursales");
        cargarVista("FXMLSucursales.fxml");
    }

    @FXML
    private void clickIrUnidades(ActionEvent event) {
        lbHeader.setText("Administración de Unidades");
        cargarVista("FXMLUnidades.fxml");
    }

    @FXML
    private void clickIrPaquetes(ActionEvent event) {
        lbHeader.setText("Administración de Paquetes");
        cargarVista("FXMLPaquetes.fxml");
    }

    @FXML
    private void clickIrEnvios(ActionEvent event) {
        lbHeader.setText("Administración de Envíos");
        cargarVista("FXMLEnvios.fxml");
    }

    // Método para cargar vistas dentro del AnchorPane "apCentral"
    private void cargarVista(String nombreFXML) {
        try {
            AnchorPane vista = FXMLLoader.load(getClass().getResource(nombreFXML));
            apCentral.getChildren().setAll(vista); // obtiene componentes dentro del panel, elimina lo que habia antes y agrega la nueva vista
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para cerrar sesión
    private void cerrarSesion() {
        try {
            // 1. Crear la ESCENA
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene escenaPrincipal = new Scene(vista);

            // 2. Obtener ESCENARIO actual (donde me encuentro)
            Stage stPrincipal = (Stage) lbHeader.getScene().getWindow();

            // 3. Cambio de ESCENA
            stPrincipal.setScene(escenaPrincipal);

            // 4. Agregar Titulo
            stPrincipal.setTitle("Inicio de Sesión");

            // 5. Mostrar ESCENSARIO
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void cargarInformacion(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;

        // 1. Nombre completo
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(colaborador.getNombre());

        if (colaborador.getApellidoPaterno() != null && !colaborador.getApellidoPaterno().isEmpty()) {
            nombreCompleto.append(" ").append(colaborador.getApellidoPaterno());
        }

        if (colaborador.getApellidoMaterno() != null && !colaborador.getApellidoMaterno().isEmpty()) {
            nombreCompleto.append(" ").append(colaborador.getApellidoMaterno());
        }

        // 2. Rol
        String rol = colaborador.getNombreRol() != null
                ? colaborador.getNombreRol()
                : "Sin rol";

        // 3. Número de personal
        String noPersonal = colaborador.getNoPersonal() != null
                ? colaborador.getNoPersonal()
                : "N/D";

        // 4. Construir texto final
        String datosUsuario = nombreCompleto
                + " | " + rol
                + " | No. Personal: " + noPersonal;

        // 5. Mostrar en el label
        lbDatosUsuario.setText(datosUsuario);
    }

}
