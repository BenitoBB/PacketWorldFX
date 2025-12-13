/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldfx.dominio.InicioSesionImp;
import packetworldfx.dto.RSColaborador;
import packetworldfx.pojo.Colaborador;
import packetworldfx.utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @author benit
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private PasswordField pfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickIniciarSesion(ActionEvent event) {
        String noPersonal = tfNoPersonal.getText();
        String password = pfPassword.getText();

        if (!noPersonal.isEmpty() && !password.isEmpty()) {
            verificarCredenciales(noPersonal, password);
        } else {
            Utilidades.mostrarAlertaSimple("Campos requeridos.", "NoPersonal y/o Contraseña obligatorios", Alert.AlertType.WARNING);
        }
    }

    // Verificar Credenciales Validas
    private void verificarCredenciales(String noPersonal, String password) {
        RSColaborador respuesta = InicioSesionImp.verificarCredenciales(noPersonal, password);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Credenciales verificadas", "Bienvenido de vuelta, " + respuesta.getColaborador().getNombre(), Alert.AlertType.INFORMATION);
            irMenuPrincipal(respuesta.getColaborador());
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void irMenuPrincipal(Colaborador colaborador) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
            Parent vista = cargador.load();
            FXMLMenuPrincipalController controlador = cargador.getController();
            controlador.cargarInformacion(colaborador);
            Scene escenaPrincipal = new Scene(vista);
            Stage stPrincipal = (Stage) tfNoPersonal.getScene().getWindow();
            stPrincipal.setScene(escenaPrincipal);
            stPrincipal.setTitle("Menú principal");
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
