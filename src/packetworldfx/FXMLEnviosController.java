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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLEnviosController implements Initializable {

    @FXML
    private TableView<?> tvEnvios;
    @FXML
    private TableColumn<?, ?> colCliente;
    @FXML
    private TableColumn<?, ?> colOrigen;
    @FXML
    private TableColumn<?, ?> colDestino;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TableColumn<?, ?> colConductorAsignado;
    @FXML
    private TextField tfBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void irFormulario(){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioEnvio.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioEnvioController controlador = cargador.getController();

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Envio");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clickRegistrar(ActionEvent event) {
        irFormulario();
        
    }

    @FXML
    private void clickEditar(ActionEvent event) {
    }
    
}
