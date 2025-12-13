package packetworldfx.conexion;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Utilidades;

/**
 *
 * @authors Ohana & Benito
 */
public class ConexionAPI {
    // Solicitudes GET y POST-PUT-DELETE

    // Solicitud GET
    public static RespuestaHTTP peticionGET(String URL) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }

    // Solicitudes POST-PUT-DELETE 
    public static RespuestaHTTP peticionBody(String URL, String metodoHTTP, String parametros, String contentType) {
        RespuestaHTTP respuesta = new RespuestaHTTP();

        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            conexionHTTP.setRequestMethod(metodoHTTP);
            conexionHTTP.setRequestProperty("Content-Type", contentType);
            conexionHTTP.setDoOutput(true);
            OutputStream os = conexionHTTP.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }

    // Solicitud sin body
    public static RespuestaHTTP peticionSinBody(String URL, String metodoHTTP) {
        RespuestaHTTP respuesta = new RespuestaHTTP();

        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            conexionHTTP.setRequestMethod(metodoHTTP);
            int codigo = conexionHTTP.getResponseCode(); // codigo de respuesta - 200/404/...
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }
}
