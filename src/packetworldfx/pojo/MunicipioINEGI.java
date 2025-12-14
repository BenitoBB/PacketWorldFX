package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class MunicipioINEGI {

    private String clave;
    private String nombre;

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
