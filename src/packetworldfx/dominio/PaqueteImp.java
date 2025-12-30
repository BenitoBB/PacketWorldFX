package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Paquete;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class PaqueteImp {

    // Obtener todos los paquetes
    public static List<Paquete> obtenerTodos() {

        List<Paquete> paquetes = null;

        String URL = Constantes.URL_WS + "paquete/obtener-todos";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Paquete>>() {
            }.getType();
            paquetes = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return paquetes;
    }

    // Obtener paquetes por idEnvio
    public static List<Paquete> obtenerPorEnvio(Integer idEnvio) {

        List<Paquete> paquetes = null;

        String URL = Constantes.URL_WS
                + "paquete/envio/"
                + idEnvio;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Paquete>>() {
            }.getType();
            paquetes = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return paquetes;
    }

    // Registrar
    public static Respuesta registrar(Paquete paquete) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS + "paquete/insertar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_POST,
                parametrosJSON,
                Constantes.APPLICATION_JSON
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al registrar el paquete");
        }

        return respuesta;
    }

    // Actualizar
    public static Respuesta actualizar(Paquete paquete) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS + "paquete/actualizar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_PUT,
                parametrosJSON,
                Constantes.APPLICATION_JSON
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al actualizar el paquete");
        }

        return respuesta;
    }

    // Eliminar
    public static Respuesta eliminar(Integer idPaquete) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS + "paquete/eliminar/" + idPaquete;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_DELETE
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al eliminar el paquete");
        }

        return respuesta;
    }

    // Asignar paquete a envío
    public static Respuesta asignar(Integer idPaquete, Integer idEnvio) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS
                + "paquete/asignar/"
                + idPaquete + "/"
                + idEnvio;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_PUT
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al asignar el paquete al envío");
        }

        return respuesta;
    }

    // Desasignar paquete de envío
    public static Respuesta desasignar(Integer idPaquete) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS
                + "paquete/desasignar/"
                + idPaquete;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_PUT
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al desasignar el paquete");
        }

        return respuesta;
    }
}
