package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.pojo.Unidad;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Validaciones;

/**
 *
 * @authors Ohana & Benito
 */
public class UnidadImp {

    // Obtener todas
    public static HashMap<String, Object> obtenerTodas() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "unidad/obtener-todas";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Unidad>>() {
            }.getType();
            List<Unidad> unidades = gson.fromJson(respuestaAPI.getContenido(), tipoLista);

            respuesta.put("error", false);
            respuesta.put("unidades", unidades);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_URL);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put("mensaje", Constantes.ERROR_INFORMACION);
                    break;
            }
        }

        return respuesta;
    }

    // Registrar
    public static Respuesta registrarUnidad(Unidad unidad) {
        String URL = Constantes.URL_WS + "unidad/insertar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(unidad);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_POST,
                parametrosJSON,
                Constantes.APPLICATION_JSON
        );

        Respuesta respuesta = new Respuesta();
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_REGISTRO_UNIDAD);
        }

        return respuesta;
    }

    // Actualizar
    public static Respuesta actualizarUnidad(Unidad unidad) {
        String URL = Constantes.URL_WS + "unidad/actualizar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(unidad);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                URL,
                Constantes.PETICION_PUT,
                parametrosJSON,
                Constantes.APPLICATION_JSON
        );

        Respuesta respuesta = new Respuesta();
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_ACTUALIZAR_UNIDAD);
        }

        return respuesta;
    }

    // Eliminar
    public static Respuesta bajaUnidad(int idUnidad, String motivo) {

        String motivoCodificado = Validaciones.codificarURL(motivo);

        String URL = Constantes.URL_WS + "unidad/baja/" + idUnidad + "/" + motivoCodificado;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_PUT
        );

        Respuesta respuesta = new Respuesta();

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK
                || respuestaAPI.getCodigo() == HttpURLConnection.HTTP_NO_CONTENT) {

            respuesta.setError(false);
            respuesta.setMensaje("Unidad dada de baja correctamente");

        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_ELIMINAR_UNIDAD);
        }

        return respuesta;
    }

}
