package packetworldfx.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import packetworldfx.conexion.ConexionAPI;
import packetworldfx.pojo.Dashboard.DashboardResumen;
import packetworldfx.pojo.Dashboard.DashboardConteo;
import packetworldfx.pojo.Dashboard.DashboardEnvio;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.utilidad.Constantes;

/**
 *
 * @authors Ohana & Benito
 */
public class DashboardImp {

    // =============================
    // RESUMEN GENERAL (KPIs)
    // =============================
    public static DashboardResumen obtenerResumen() {

        DashboardResumen resumen = null;
        String URL = Constantes.URL_WS + "dashboard/resumen";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            resumen = gson.fromJson(
                    respuestaAPI.getContenido(),
                    DashboardResumen.class
            );
        }

        return resumen;
    }

    // =============================
    // ENVIOS POR ESTATUS (PieChart)
    // =============================
    public static List<DashboardConteo> obtenerEnviosPorEstatus() {

        List<DashboardConteo> lista = null;
        String URL = Constantes.URL_WS + "dashboard/envios-estatus";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<DashboardConteo>>() {
            }.getType();

            lista = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return lista;
    }

    // =============================
    // ENVIOS POR SUCURSAL (BarChart)
    // =============================
    public static List<DashboardConteo> obtenerEnviosPorSucursal() {

        List<DashboardConteo> lista = null;
        String URL = Constantes.URL_WS + "dashboard/envios-sucursal";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<DashboardConteo>>() {
            }.getType();

            lista = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return lista;
    }

    // =============================
    // ÚLTIMOS ENVÍOS (TableView)
    // =============================
    public static List<DashboardEnvio> obtenerUltimosEnvios() {

        List<DashboardEnvio> lista = null;
        String URL = Constantes.URL_WS + "dashboard/ultimos-envios";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<DashboardEnvio>>() {
            }.getType();

            lista = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
        }

        return lista;
    }
}
