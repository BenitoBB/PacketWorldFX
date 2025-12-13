package packetworldfx.utilidad;

/**
 *
 * @authors Ohana & Benito
 */
public class Constantes {

    // URL API
    public static final String URL_WS = "http://localhost:8080/APIPacketWorld/api/";

    // Peticiones 
    public static final String PETICION_GET = "GET";
    public static final String PETICION_POST = "POST";
    public static final String PETICION_PUT = "PUT";
    public static final String PETICION_DELETE = "DELETE";

    // MimeTypes 
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";

    // Mensajes de Errores 
    public static final String ERROR_GENERICO = "Lo sentimos, hubo un error.";
    public static final String ERROR_INFORMACION = "Lo sentimos, hubo un error al obtener la información.";
    public static final int ERROR_MALFORMED_URL = 1001;
    public static final int ERROR_PETICION = 1002;

    public static final String MSJ_ERROR_URL = "Lo sentimos, su solicitud no puede ser realizada en este momento. Intente más tarde.";
    public static final String MSJ_ERROR_PETICION = "Lo sentimos, tenemos problemas de conexión. Intente más tarde.";
    public static final String MSJ_DATOS_REQUERIDOS = "Datos requeridos para poder realizar la operación solicitada.";
    public static final String MSJ_ERROR_CREDENCIALES = "Lo sentimos hay problemas para verificar sus credenciales.";

}
