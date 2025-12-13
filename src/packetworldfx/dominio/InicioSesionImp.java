package packetworldfx.dominio;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.RSColaborador;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class InicioSesionImp {

    public static RSColaborador verificarCredenciales(String noPersonal, String password) {
        RSColaborador respuesta = new RSColaborador();

        String parametros = "noPersonal=" + noPersonal + "&password=" + password;
        String URL = Constantes.URL_WS + "colaborador/login";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, Constantes.PETICION_POST, parametros, Constantes.APPLICATION_FORM);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaAPI.getContenido(), RSColaborador.class);
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Constantes.ERROR_INFORMACION);
            }
        } else {

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setError(true);
                    respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuesta.setError(true);
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;

                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setError(true);
                    respuesta.setMensaje(Constantes.MSJ_DATOS_REQUERIDOS);
                    break;

                default:
                    respuesta.setError(true);
                    respuesta.setMensaje(Constantes.MSJ_ERROR_CREDENCIALES);
                    break;
            }
        }

        return respuesta;
    }
}
