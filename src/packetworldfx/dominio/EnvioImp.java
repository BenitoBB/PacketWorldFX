package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.RSActualizarEstatus;
import packetworldfx.dto.RSEnvio;
import packetworldfx.dto.RSEnvioDetalle;
import packetworldfx.dto.RSEnvioTabla;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class EnvioImp {

    public static List<RSEnvioTabla> obtenerEnviosTabla() {

        String url = Constantes.URL_WS + "envio/tabla";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<RSEnvioTabla>>() {
            }.getType();

            return gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return null;
    }

    public static RSEnvioTabla buscarPorNumeroGuia(String numeroGuia) {

        String url = Constantes.URL_WS + "envio/detalle/" + numeroGuia;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {

            Gson gson = new Gson();
            RSEnvioDetalle detalle
                    = gson.fromJson(respuestaAPI.getContenido(), RSEnvioDetalle.class);

            if (detalle != null) {
                // Adaptamos DETALLE → TABLA
                RSEnvioTabla fila = new RSEnvioTabla();
                fila.setIdEnvio(detalle.getIdEnvio());
                fila.setNumeroGuia(detalle.getNumeroGuia());
                fila.setSucursalOrigen(detalle.getSucursalOrigen());
                fila.setDestino(
                        detalle.getDirCiudad() + ", " + detalle.getDirEstado()
                );
                fila.setEstatus(detalle.getEstatus());
                fila.setConductor(null); // no viene en el detalle (correcto)

                return fila;
            }
        }
        return null;
    }

    public static Respuesta actualizar(RSEnvio envio) {

        Respuesta respuesta = new Respuesta();

        try {
            String url = Constantes.URL_WS + "envio/actualizar";

            Gson gson = new Gson();
            String json = gson.toJson(envio);

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                    url,
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
                respuesta.setMensaje("Error al actualizar el envío.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setError(true);
            respuesta.setMensaje("Error inesperado al actualizar el envío.");
        }

        return respuesta;
    }

    public static Respuesta registrarEnvio(RSEnvio envio) {

        Respuesta respuesta = new Respuesta();

        try {
            String url = Constantes.URL_WS + "envio/registrar";

            Gson gson = new Gson();
            String json = gson.toJson(envio);

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                    url,
                    Constantes.PETICION_POST,
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
                respuesta.setMensaje("Error al registrar el envío.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setError(true);
            respuesta.setMensaje("Error inesperado al registrar el envío.");
        }

        return respuesta;
    }

    public static RSEnvioDetalle obtenerDetalleEnvio(String numeroGuia) {

        String url = Constantes.URL_WS + "envio/detalle/" + numeroGuia;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            return gson.fromJson(
                    respuestaAPI.getContenido(),
                    RSEnvioDetalle.class
            );
        }

        return null;
    }

    public static Respuesta actualizarEstatus(RSActualizarEstatus rs) {

        Respuesta respuesta = new Respuesta();

        try {
            String url = Constantes.URL_WS + "envio/estatus";

            Gson gson = new Gson();
            String json = gson.toJson(rs);

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                    url,
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
                respuesta.setMensaje("Error al actualizar el estatus.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setError(true);
            respuesta.setMensaje("Error inesperado al actualizar el estatus.");
        }

        return respuesta;
    }

    public static Respuesta asignar(String guia, int idColaborador) {

        Respuesta respuesta = new Respuesta();

        try {
            String url = Constantes.URL_WS
                    + "envio/asignar/" + guia + "/" + idColaborador;

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                    url,
                    Constantes.PETICION_PUT
            );

            if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(
                        respuestaAPI.getContenido(),
                        Respuesta.class
                );
            } else {
                respuesta.setError(true);
                respuesta.setMensaje("No se pudo asignar el conductor.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setError(true);
            respuesta.setMensaje("Error inesperado al asignar conductor.");
        }

        return respuesta;
    }

    public static Respuesta desasignar(String guia) {

        Respuesta respuesta = new Respuesta();

        try {
            String url = Constantes.URL_WS
                    + "envio/desasignar/" + guia;

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                    url,
                    Constantes.PETICION_PUT
            );

            if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(
                        respuestaAPI.getContenido(),
                        Respuesta.class
                );
            } else {
                respuesta.setError(true);
                respuesta.setMensaje("No se pudo desasignar el conductor.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setError(true);
            respuesta.setMensaje("Error inesperado al desasignar conductor.");
        }

        return respuesta;
    }

}
