package packetworldfx.dominio;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.dto.RSDistancia;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class DistanciaImp {

    public static double obtenerDistancia(String cpOrigen, String cpDestino) {

        try {
            String url = Constantes.URL_DISTANCIA
                    + cpOrigen + "," + cpDestino;

            RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

            if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {

                Gson gson = new Gson();
                RSDistancia respuesta = gson.fromJson(
                        respuestaAPI.getContenido(),
                        RSDistancia.class
                );

                if (!respuesta.isError()
                        && respuesta.getDistanciaKM() > 0) {
                    return respuesta.getDistanciaKM();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // fallback seguro
        return 0.0;
    }
}
