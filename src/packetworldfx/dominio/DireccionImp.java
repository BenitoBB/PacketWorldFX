package packetworldfx.dominio;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.RSDireccion;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Direccion;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class DireccionImp {

    // Insertar Dirección
    public static RSDireccion insertar(Direccion direccion) {

        RSDireccion respuesta = new RSDireccion();
        String URL = Constantes.URL_WS + "direccion/insertar";

        Gson gson = new Gson();
        String json = gson.toJson(direccion);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_POST,
                json,
                Constantes.APPLICATION_JSON
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(
                    respuestaAPI.getContenido(),
                    RSDireccion.class
            );
        } else {
            respuesta.setError(true);

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;

                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setMensaje(Constantes.MSJ_DATOS_REQUERIDOS);
                    break;

                default:
                    respuesta.setMensaje("Error al registrar la dirección.");
                    break;
            }
        }

        return respuesta;
    }

    // Actualizar Dirección
    public static Respuesta actualizar(Direccion direccion) {

        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "direccion/actualizar";

        Gson gson = new Gson();
        String json = gson.toJson(direccion);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_PUT,
                json,
                Constantes.APPLICATION_JSON
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(
                    respuestaAPI.getContenido(),
                    Respuesta.class
            );
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al actualizar la dirección.");
        }

        return respuesta;
    }

    // Obtener Dirección por ID
    public static RSDireccion obtenerPorId(Integer idDireccion) {

        RSDireccion respuesta = new RSDireccion();
        String URL = Constantes.URL_WS + "direccion/" + idDireccion;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_GET
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(
                    respuestaAPI.getContenido(),
                    RSDireccion.class
            );
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No se pudo obtener la dirección.");
        }

        return respuesta;
    }

}
