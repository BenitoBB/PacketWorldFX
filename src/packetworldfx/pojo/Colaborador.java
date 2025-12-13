/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Colaborador {

    private Integer idColaborador;
    private String noPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String curp;
    private String correo;
    private String telefono;
    private byte[] foto;
    private String password;
    // Caso para Conductor
    private String numeroLicencia;
    // Foreign Keys
    private Integer idRol;
    private Integer idSucursal;
    private String nombreRol;

    public Colaborador() {
    }

    public Colaborador(Integer idColaborador, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String curp, String correo, String telefono, byte[] foto, String password, String numeroLicencia, Integer idRol, Integer idSucursal, String nombreRol) {
        this.idColaborador = idColaborador;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.curp = curp;
        this.correo = correo;
        this.telefono = telefono;
        this.foto = foto;
        this.password = password;
        this.numeroLicencia = numeroLicencia;
        this.idRol = idRol;
        this.idSucursal = idSucursal;
        this.nombreRol = nombreRol;
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

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
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

    public String getCurp() {
        return curp;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getPassword() {
        return password;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getNombreRol() {
        return nombreRol;
    }
}
