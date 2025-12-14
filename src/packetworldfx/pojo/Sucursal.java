package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Sucursal {

    private Integer idSucursal;
    private String codigo;
    private String nombre;
    private String estatus;
    private Direccion direccion;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String codigo, String nombre, String estatus, Direccion direccion) {
        this.idSucursal = idSucursal;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estatus = estatus;
        this.direccion = direccion;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
