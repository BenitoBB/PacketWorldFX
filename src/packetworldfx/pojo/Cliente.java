package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Cliente {

    private Integer idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    // Foreign Key
    private Integer idDireccion;
    // OBJETO COMPLETO
    private Direccion direccion;

    public Cliente() {
    }

    public Cliente(Integer idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, Integer idDireccion, Direccion direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.idDireccion = idDireccion;
        this.direccion = direccion;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
