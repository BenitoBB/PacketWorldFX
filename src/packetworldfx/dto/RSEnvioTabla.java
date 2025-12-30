package packetworldfx.dto;

import java.math.BigDecimal;

/**
 *
 * @authors Ohana & Benito
 */
public class RSEnvioTabla {

    private int idEnvio;
    private String numeroGuia;

    private String sucursalOrigen;
    private String destino;
    private int idEstatus;
    private String estatus;
    private String conductor;
    private BigDecimal costo;

    public RSEnvioTabla() {
    }

    public RSEnvioTabla(int idEnvio, String numeroGuia, String sucursalOrigen, String destino, int idEstatus, String estatus, String conductor, BigDecimal costo) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.sucursalOrigen = sucursalOrigen;
        this.destino = destino;
        this.idEstatus = idEstatus;
        this.estatus = estatus;
        this.conductor = conductor;
        this.costo = costo;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public String getDestino() {
        return destino;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getConductor() {
        return conductor;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public void setSucursalOrigen(String sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
}
