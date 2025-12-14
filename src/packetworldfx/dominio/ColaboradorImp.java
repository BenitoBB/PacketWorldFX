package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.ColaboradorTablaDTO;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Colaborador;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class ColaboradorImp {

    // Obtener todos
    public static HashMap<String, Object> obtenerTodos() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "colaborador/obtener-todos";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<ColaboradorTablaDTO>>() {
            }.getType();
            List<ColaboradorTablaDTO> colaboradores = gson.fromJson(respuestaAPI.getContenido(), tipoLista);

            respuesta.put("error", false);
            respuesta.put("colaboradores", colaboradores);
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
    public static Respuesta registrarColaborador(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        // Validación regla de negocio:
        // Si es Conductor (idRol = 3) requiere número de licencia
        if (Integer.valueOf(3).equals(colaborador.getIdRol())
                && (colaborador.getNumeroLicencia() == null
                || colaborador.getNumeroLicencia().trim().isEmpty())) {

            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_LICENCIA_REQUERIDA);
            return respuesta;
        }

        String URL = Constantes.URL_WS + "colaborador/insertar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(colaborador);

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

            switch (respuestaAPI.getCodigo()) {

                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;

                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_DATOS_INVALIDOS);
                    break;

                default:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_REGISTRO_COLABORADOR);
                    break;
            }
        }

        return respuesta;
    }

    // Buscar por NoPersonal
    public static Colaborador buscarPorNoPersonal(String noPersonal) {
        Colaborador colaborador = null;

        String URL = Constantes.URL_WS
                + "colaborador/buscar/noPersonal/"
                + noPersonal;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            colaborador = gson.fromJson(
                    respuestaAPI.getContenido(),
                    Colaborador.class
            );
        }

        return colaborador;
    }

    // Actualizar
    public static Respuesta actualizarColaborador(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS + "colaborador/actualizar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(colaborador);

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
            respuesta.setMensaje(Constantes.MSJ_ERROR_ACTUALIZAR_COLABORADOR);
        }

        return respuesta;
    }

    // Eliminar
    public static Respuesta eliminar(int idColaborador) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS
                + "colaborador/eliminar/"
                + idColaborador;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_DELETE
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(
                    respuestaAPI.getContenido(),
                    Respuesta.class
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

                default:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_ELIMINAR_COLABORADOR);
                    break;
            }
        }

        return respuesta;
    }

    private static boolean esRolConductor(Integer idRol) {
        return idRol != null && Constantes.ID_ROL_CONDUCTOR.equals(idRol);
    }

}
