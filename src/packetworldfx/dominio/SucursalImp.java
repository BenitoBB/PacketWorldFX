package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.pojo.Sucursal;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class SucursalImp {

    // Obtener todas
    public static HashMap<String, Object> obtenerTodas() {

        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "sucursal/obtener-todas";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();

            List<Sucursal> sucursales = gson.fromJson(
                    respuestaAPI.getContenido(),
                    tipoLista
            );

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put("sucursales", sucursales);

        } else {
            respuesta.put(Constantes.KEY_ERROR, true);

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;

                default:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_OBTENER_SUCURSALES);
                    break;
            }
        }

        return respuesta;
    }

    // Registrar
    public static Respuesta registrar(Sucursal sucursal) {

        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "sucursal/insertar";

        Gson gson = new Gson();
        String json = gson.toJson(sucursal);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_POST,
                json,
                Constantes.APPLICATION_JSON
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
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
                    respuesta.setMensaje("Error al registrar la sucursal.");
                    break;
            }
        }

        return respuesta;
    }

    // Actualizar
    public static Respuesta actualizar(Sucursal sucursal) {

        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "sucursal/actualizar";

        Gson gson = new Gson();
        String json = gson.toJson(sucursal);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_PUT,
                json,
                Constantes.APPLICATION_JSON
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al actualizar la sucursal.");
        }

        return respuesta;
    }

    // Dar de baja
    public static Respuesta darDeBaja(Integer idSucursal) {

        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "sucursal/baja/" + idSucursal;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_PUT
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK
                || respuestaAPI.getCodigo() == HttpURLConnection.HTTP_NO_CONTENT) {

            respuesta.setError(false);
            respuesta.setMensaje("Sucursal dada de baja correctamente.");

        } else {
            respuesta.setError(true);

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;

                default:
                    respuesta.setMensaje("Error al dar de baja la sucursal.");
                    break;
            }
        }

        return respuesta;
    }
}
