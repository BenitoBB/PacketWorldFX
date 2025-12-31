package packetworldfx.utilidad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @authors Ohana & Benito
 */
public class Utilidades {

    public static String streamToString(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        String inputLine;
        StringBuffer respuestaEntrada = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            respuestaEntrada.append(inputLine);
        }
        in.close();
        return respuestaEntrada.toString();
    }

    public static void mostrarAlertaSimple(String title, String content, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(content);
        alerta.showAndWait();
    }

    public static boolean mostrarAlertaConfirmacion(String title, String content) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(content);
        Optional<ButtonType> btnSeleccion = alerta.showAndWait();
        return (btnSeleccion.get() == ButtonType.OK);
    }
}
