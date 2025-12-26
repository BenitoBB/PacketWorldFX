package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.RSConductorUnidad;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Colaborador;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.pojo.Unidad;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class ConductorUnidadImp {

    public static List<Colaborador> obtenerConductores() {

        String url = Constantes.URL_WS
                + "conductor-unidad/conductores";

        RespuestaHTTP api = ConexionAPI.peticionGET(url);

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipo = new TypeToken<List<Colaborador>>() {
            }.getType();
            return gson.fromJson(api.getContenido(), tipo);
        }

        return null;
    }

    public static List<Unidad> obtenerUnidadesActivas() {

        String url = Constantes.URL_WS
                + "conductor-unidad/unidades-activas";

        RespuestaHTTP api = ConexionAPI.peticionGET(url);

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipo = new TypeToken<List<Unidad>>() {
            }.getType();
            return gson.fromJson(api.getContenido(), tipo);
        }

        return null;
    }

    public static Respuesta asignar(int idConductor, int idUnidad) {

        Respuesta respuesta = new Respuesta();

        String url = Constantes.URL_WS
                + "conductor-unidad/asignar/"
                + idConductor + "/" + idUnidad;

        RespuestaHTTP api = ConexionAPI.peticionSinBody(
                url,
                Constantes.PETICION_POST
        );

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(api.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No se pudo realizar la asignación.");
        }

        return respuesta;
    }

    public static Respuesta desasignarPorConductor(int idColaborador) {

        Respuesta respuesta = new Respuesta();

        String url = Constantes.URL_WS
                + "conductor-unidad/desasignar/conductor/" + idColaborador;

        RespuestaHTTP api = ConexionAPI.peticionSinBody(
                url,
                Constantes.PETICION_PUT
        );

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(api.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No se pudo desasignar la unidad del conductor.");
        }

        return respuesta;
    }

    public static Respuesta desasignarPorUnidad(int idUnidad) {

        Respuesta respuesta = new Respuesta();

        String url = Constantes.URL_WS
                + "conductor-unidad/desasignar/unidad/" + idUnidad;

        RespuestaHTTP api = ConexionAPI.peticionSinBody(
                url,
                Constantes.PETICION_PUT
        );

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(api.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No se pudo desasignar el conductor de la unidad.");
        }

        return respuesta;
    }

    public static RSConductorUnidad obtenerUnidadActualPorConductor(int idColaborador) {

        String url = Constantes.URL_WS
                + "conductor-unidad/actual/conductor/" + idColaborador;

        RespuestaHTTP api = ConexionAPI.peticionGET(url);

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            return gson.fromJson(api.getContenido(), RSConductorUnidad.class);
        }

        RSConductorUnidad rs = new RSConductorUnidad();
        rs.setError(true);
        rs.setMensaje("No se pudo consultar la asignación.");
        return rs;
    }

    public static RSConductorUnidad obtenerConductorActualPorUnidad(int idUnidad) {

        String url = Constantes.URL_WS
                + "conductor-unidad/actual/unidad/" + idUnidad;

        RespuestaHTTP api = ConexionAPI.peticionGET(url);

        if (api.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            return gson.fromJson(api.getContenido(), RSConductorUnidad.class);
        }

        RSConductorUnidad rs = new RSConductorUnidad();
        rs.setError(true);
        rs.setMensaje("No se pudo consultar la asignación.");
        return rs;
    }

}
