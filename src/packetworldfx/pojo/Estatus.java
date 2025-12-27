package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Estatus {

    private Integer idEstatus;
    private String nombre;
    private String descripcion;

    public Estatus() {
    }

    public Estatus(Integer idEstatus, String nombre, String descripcion) {
        this.idEstatus = idEstatus;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
