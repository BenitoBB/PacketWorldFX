package packetworldfx.dto;

import java.math.BigDecimal;
import java.util.List;
import packetworldfx.pojo.Paquete;

/**
 *
 * @authors Ohana & Benito
 */
public class RSEnvio {

    // Identidad 
    private Integer idEnvio;      // null = nuevo
    private String numeroGuia;

    // Registro obligatorio 
    private Integer idClienteRemitente;
    private Integer idSucursalOrigen;
    private BigDecimal costo;

    // Destinatario 
    private String nombreDestinatario;
    private String apellidoPaternoDestinatario;
    private String apellidoMaternoDestinatario;

    // Dirección
    private Integer idDireccion;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private String ciudad;
    private String estado;

    public RSEnvio() {
    }

    public RSEnvio(Integer idEnvio, String numeroGuia, Integer idClienteRemitente, Integer idSucursalOrigen, BigDecimal costo, String nombreDestinatario, String apellidoPaternoDestinatario, String apellidoMaternoDestinatario, Integer idDireccion, String calle, String numero, String colonia, String codigoPostal, String ciudad, String estado) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.idClienteRemitente = idClienteRemitente;
        this.idSucursalOrigen = idSucursalOrigen;
        this.costo = costo;
        this.nombreDestinatario = nombreDestinatario;
        this.apellidoPaternoDestinatario = apellidoPaternoDestinatario;
        this.apellidoMaternoDestinatario = apellidoMaternoDestinatario;
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public Integer getIdClienteRemitente() {
        return idClienteRemitente;
    }

    public Integer getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public String getApellidoPaternoDestinatario() {
        return apellidoPaternoDestinatario;
    }

    public String getApellidoMaternoDestinatario() {
        return apellidoMaternoDestinatario;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getColonia() {
        return colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public void setIdClienteRemitente(Integer idClienteRemitente) {
        this.idClienteRemitente = idClienteRemitente;
    }

    public void setIdSucursalOrigen(Integer idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public void setApellidoPaternoDestinatario(String apellidoPaternoDestinatario) {
        this.apellidoPaternoDestinatario = apellidoPaternoDestinatario;
    }

    public void setApellidoMaternoDestinatario(String apellidoMaternoDestinatario) {
        this.apellidoMaternoDestinatario = apellidoMaternoDestinatario;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
