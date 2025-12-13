package packetworldfx.dto;

/**
 *
 * @authors Ohana & Benito
 */
public class ColaboradorTablaDTO {

    private Integer idColaborador;
    private String noPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private Integer idRol;
    private String nombreRol;
    private Integer idSucursal;
    private String nombreSucursal;
    private String numeroLicencia;
    private String curp;

    public ColaboradorTablaDTO() {
    }

    public ColaboradorTablaDTO(Integer idColaborador, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String telefono, Integer idRol, String nombreRol, Integer idSucursal, String nombreSucursal, String numeroLicencia, String curp) {
        this.idColaborador = idColaborador;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.numeroLicencia = numeroLicencia;
        this.curp = curp;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
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

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public String getNoPersonal() {
        return noPersonal;
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

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public String getCurp() {
        return curp;
    }
}
