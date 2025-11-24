/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author benit
 */
public class PacketWorldFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try{
            Parent vista = FXMLLoader.load(
                getClass().getResource("FXMLInicioSesion.fxml")
            );
            Scene escenaLogin = new Scene(vista);
            primaryStage.setScene(escenaLogin);
            primaryStage.setTitle("Inicio de Sesion");
            primaryStage.show();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
