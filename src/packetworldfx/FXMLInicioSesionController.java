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
            irMenuPrincipal();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos vacios");
            alerta.setHeaderText("Falta informacion");
            alerta.setContentText("Debes ingresar tu Número Personal y Contraseña para iniciar sesion");
            alerta.showAndWait();
        }
    }

    private void irMenuPrincipal() {
        try {
            // 1. Crear la ESCENA
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLMenuPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);

            // 2. Obtener ESCENARIO actual (donde me encuentro)
            Stage stPrincipal = (Stage) tfNoPersonal.getScene().getWindow();

            // 3. Cambio de ESCENA
            stPrincipal.setScene(escenaPrincipal);

            // 4. Agregar Titulo
            stPrincipal.setTitle("Menú principal");

            // 5. Mostrar ESCENSARIO
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
