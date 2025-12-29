package packetworldfx.conexion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import packetworldfx.pojo.RespuestaHTTP;
import packetworldfx.pojo.RespuestaHTTPBIN;
import packetworldfx.utilidad.Constantes;
import packetworldfx.utilidad.Utilidades;

/**
 *
 * @authors Ohana & Benito
 */
public class ConexionAPI {
    // Solicitudes GET y POST-PUT-DELETE

    // Solicitud GET
    public static RespuestaHTTP peticionGET(String URL) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }

    // Solicitudes POST-PUT-DELETE 
    public static RespuestaHTTP peticionBody(String URL, String metodoHTTP, String parametros, String contentType) {
        RespuestaHTTP respuesta = new RespuestaHTTP();

        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            conexionHTTP.setRequestMethod(metodoHTTP);
            conexionHTTP.setRequestProperty("Content-Type", contentType);
            conexionHTTP.setDoOutput(true);
            OutputStream os = conexionHTTP.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }

    // Solicitud sin body
    public static RespuestaHTTP peticionSinBody(String URL, String metodoHTTP) {
        RespuestaHTTP respuesta = new RespuestaHTTP();

        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            conexionHTTP.setRequestMethod(metodoHTTP);

            int codigo = conexionHTTP.getResponseCode();
            respuesta.setCodigo(codigo);

            // 204 es éxito, NO intentar leer stream
            if (codigo == HttpURLConnection.HTTP_NO_CONTENT) {
                respuesta.setContenido("");
                return respuesta;
            }

            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(
                        Utilidades.streamToString(conexionHTTP.getInputStream())
                );
            }

        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }

        return respuesta;
    }

    // Solicitud para Foto
    public static RespuestaHTTP peticionBinaria(
            String url,
            String metodo,
            byte[] datos
    ) throws IOException {

        URL direccion = new URL(url);
        HttpURLConnection conexion = (HttpURLConnection) direccion.openConnection();

        conexion.setRequestMethod(metodo);
        conexion.setDoOutput(true);
        conexion.setRequestProperty("Content-Type", "application/octet-stream");

        try (OutputStream os = conexion.getOutputStream()) {
            os.write(datos);
        }

        int codigo = conexion.getResponseCode();

        RespuestaHTTP r = new RespuestaHTTP();
        r.setCodigo(codigo);

        InputStream is = (codigo >= 200 && codigo < 300)
                ? conexion.getInputStream()
                : conexion.getErrorStream();

        if (is != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            r.setContenido(new String(buffer.toByteArray(), StandardCharsets.UTF_8));
        } else {
            r.setContenido("");
        }

        return r;
    }

    public static RespuestaHTTPBIN peticionGETBinaria(String URL) {
        RespuestaHTTPBIN respuesta = new RespuestaHTTPBIN();

        try {
            URL url = new URL(URL);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            int codigo = conexion.getResponseCode();
            respuesta.setCodigo(codigo);

            if (codigo == HttpURLConnection.HTTP_OK) {

                InputStream is = conexion.getInputStream();

                if (is != null) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = is.read(data)) != -1) {
                        buffer.write(data, 0, bytesRead);
                    }

                    respuesta.setContenidoBinario(buffer.toByteArray());
                } else {
                    respuesta.setContenidoBinario(null);
                }
            }

        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
        } catch (IOException e) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
        }

        return respuesta;
    }

}
