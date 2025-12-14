package packetworldfx.utilidad;

/**
 *
 * @authors Ohana & Benito
 */
public class Constantes {

    // URL API
    public static final String URL_WS = "http://localhost:8080/APIPacketWorld/api/";
    // URL INEGI
    public static final String URL_INEGI = "https://gaia.inegi.org.mx/wscatgeo/mgee/";
    // URL INEGI Código Postal
    public static final String URL_INEGI_CP = "https://gaia.inegi.org.mx/wscatgeo/cp/";
    

    // Peticiones 
    public static final String PETICION_GET = "GET";
    public static final String PETICION_POST = "POST";
    public static final String PETICION_PUT = "PUT";
    public static final String PETICION_DELETE = "DELETE";

    // MimeTypes 
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";

    // Llaves Hashmap
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_LISTA = "lista_valores";
    public static final String KEY_OBJETO = "objeto";

    // Mensajes de Errores 
    public static final String ERROR_GENERICO = "Lo sentimos, hubo un error.";
    public static final String ERROR_INFORMACION = "Lo sentimos, hubo un error al obtener la información.";
    public static final int ERROR_MALFORMED_URL = 1001;
    public static final int ERROR_PETICION = 1002;

    public static final String MSJ_ERROR_URL = "Lo sentimos, su solicitud no puede ser realizada en este momento. Intente más tarde.";
    public static final String MSJ_ERROR_PETICION = "Lo sentimos, tenemos problemas de conexión. Intente más tarde.";
    public static final String MSJ_DATOS_REQUERIDOS = "Datos requeridos para poder realizar la operación solicitada.";
    public static final String MSJ_ERROR_CREDENCIALES = "Lo sentimos hay problemas para verificar sus credenciales.";

    public static final String MSJ_ERROR_OBTENER_ROLES = "Lo sentimos. No se pudieron obtener los roles. Intente mas tarde";

    public static final String MSJ_ERROR_OBTENER_SUCURSALES = "Lo sentimos. No se pudieron obtener las sucursales. Intente más tarde.";

    // Mensajes específicos Colaborador
    public static final String MSJ_ERROR_REGISTRO_COLABORADOR = "Lo sentimos, hubo un problema al registrar el colaborador. Intente más tarde.";
    public static final String MSJ_ERROR_LICENCIA_REQUERIDA = "El número de licencia es obligatorio para el rol Conductor.";
    public static final String MSJ_ERROR_DATOS_INVALIDOS = "Campos en formato incorrecto, por favor verifica la información enviada.";
    public static final String MSJ_ERROR_ACTUALIZAR_COLABORADOR = "Error al actualizar el colaborador";
    public static final String MSJ_ERROR_ELIMINAR_COLABORADOR = "No fue posible eliminar el colaborador. Intenta más tarde.";

    // Mensajes específicos Unidad
    public static final String[] TIPOS_UNIDAD = {"Gasolina", "Diesel", "Eléctrica", "Híbrida"};
    public static final String MSJ_ERROR_REGISTRO_UNIDAD = "Lo sentimos, hubo un problema al registrar la unidad. Intente más tarde.";
    public static final String MSJ_ERROR_ACTUALIZAR_UNIDAD = "Error al actualizar la unidad.";
    public static final String MSJ_ERROR_ELIMINAR_UNIDAD = "No fue posible eliminar la unidad. Intenta más tarde.";

    // Roles del sistema (IDs de negocio)
    public static final Integer ID_ROL_CONDUCTOR = 3;
}
