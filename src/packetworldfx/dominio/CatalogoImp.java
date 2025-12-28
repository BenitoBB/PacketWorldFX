package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.pojo.Colaborador;
import packetworldfx.pojo.Estatus;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.pojo.Rol;
import packetworldfx.pojo.Sucursal;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class CatalogoImp {

    // Obtener Roles
    public static HashMap<String, Object> obtenerRoles() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "catalogo/roles";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Rol>>() {
            }.getType();
            List<Rol> roles = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, roles);
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
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_OBTENER_ROLES);
                    break;
            }
        }

        return respuesta;
    }

    // Obtener Sucursales
    public static HashMap<String, Object> obtenerSucursales() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "catalogo/sucursales";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();
            List<Sucursal> sucursales = gson.fromJson(respuestaAPI.getContenido(), tipoLista);

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, sucursales);
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

    // Obtener Estatus de Envío
    public static List<Estatus> obtenerEstatusEnvio() {

        String URL = Constantes.URL_WS + "catalogo/estatus";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Estatus>>() {
            }.getType();
            return gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return null;
    }
    
    // Obtener Conductores
    public static HashMap<String, Object> obtenerConductores() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "catalogo/conductores";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Colaborador>>() {
            }.getType();

            List<Colaborador> conductores
                    = gson.fromJson(respuestaAPI.getContenido(), tipoLista);

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, conductores);
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
                    respuesta.put(Constantes.KEY_MENSAJE,
                            "Error al obtener la lista de conductores");
                    break;
            }
        }

        return respuesta;
    }

}
