package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.RSDireccion;
import packetworldfx.dto.Respuesta;
import packetworldfx.pojo.Cliente;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class ClienteImp {

    // Obtener todos
    public static List<Cliente> obtenerTodos() {

        String URL = Constantes.URL_WS + "cliente/obtener-todos";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Cliente>>() {
            }.getType();

            return gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return null;
    }

    // Registrar
    public static Respuesta registrar(Cliente cliente) {

        Respuesta respuesta = new Respuesta();

        try {
            RSDireccion rsDireccion = DireccionImp.insertar(
                    cliente.getDireccion()
            );

            if (rsDireccion.isError()) {
                respuesta.setError(true);
                respuesta.setMensaje(rsDireccion.getMensaje());
                return respuesta;
            }

            cliente.setIdDireccion(
                    rsDireccion.getDireccion().getIdDireccion()
            );

            String URL = Constantes.URL_WS + "cliente/insertar";
            Gson gson = new Gson();
            String json = gson.toJson(cliente);

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(
                    URL,
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
                respuesta.setMensaje("Error al registrar cliente.");
            }

        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setMensaje("Error inesperado al registrar cliente.");
        }

        return respuesta;
    }

    // Actualizar 
    public static Respuesta actualizar(Cliente cliente) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS + "cliente/actualizar";
        Gson gson = new Gson();
        String json = gson.toJson(cliente);

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
            respuesta.setMensaje(Constantes.MSJ_ERROR_ACTUALIZAR_CLIENTE);
        }

        return respuesta;
    }

    // Eliminar
    public static Respuesta eliminar(int idCliente) {
        Respuesta respuesta = new Respuesta();

        String URL = Constantes.URL_WS + "cliente/eliminar/" + idCliente;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(
                URL,
                Constantes.PETICION_DELETE
        );

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
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
                default:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_ELIMINAR_CLIENTE);
                    break;
            }
        }

        return respuesta;
    }

    // Buscar por Nombre Completo
    public static List<Cliente> buscarPorNombre(String nombre) {

        String URL = Constantes.URL_WS + "cliente/buscar/nombre/" + nombre;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Cliente>>() {
            }.getType();
            return gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return null;
    }

    // Buscar por Telefono
    public static List<Cliente> buscarPorTelefono(String telefono) {

        String URL = Constantes.URL_WS + "cliente/buscar/telefono/" + telefono;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Cliente>>() {
            }.getType();
            return gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return null;
    }

    // Buscar por Correo
    public static List<Cliente> buscarPorCorreo(String correo) {

        String URL = Constantes.URL_WS + "cliente/buscar/correo/" + correo;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Cliente>>() {
            }.getType();
            return gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return null;
    }

}
