package packetworldfx.pojo;

import java.math.BigDecimal;

/**
 *
 * @authors Ohana & Benito
 */
public class Paquete {

    private Integer idPaquete;
    private String descripcion;
    private BigDecimal peso;
    private BigDecimal alto;
    private BigDecimal ancho;
    private BigDecimal profundidad;
    // Foreign Key
    private Integer idEnvio;

    public Paquete() {
    }

    public Paquete(Integer idPaquete, String descripcion, BigDecimal peso, BigDecimal alto, BigDecimal ancho, BigDecimal profundidad, Integer idEnvio) {
        this.idPaquete = idPaquete;
        this.descripcion = descripcion;
        this.peso = peso;
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.idEnvio = idEnvio;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public void setProfundidad(BigDecimal profundidad) {
        this.profundidad = profundidad;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public BigDecimal getAlto() {
        return alto;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public BigDecimal getProfundidad() {
        return profundidad;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }
}
