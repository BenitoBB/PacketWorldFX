package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class RespuestaHTTPBIN {

    private int codigo;
    private String contenido;
    private byte[] contenidoBinario;

    public RespuestaHTTPBIN() {
    }

    public RespuestaHTTPBIN(int codigo, String contenido, byte[] contenidoBinario) {
        this.codigo = codigo;
        this.contenido = contenido;
        this.contenidoBinario = contenidoBinario;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getContenido() {
        return contenido;
    }

    public byte[] getContenidoBinario() {
        return contenidoBinario;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setContenidoBinario(byte[] contenidoBinario) {
        this.contenidoBinario = contenidoBinario;
    }
}
