package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Unidad {

    private Integer idUnidad;
    private String marca;
    private String modelo;
    private Integer anio;
    private String vin;
    private String tipoUnidad;
    private String numeroIdentificacionInterno;
    private String estatus;
    private String motivoBaja;

    public Unidad() {
    }

    public Unidad(Integer idUnidad, String marca, String modelo, Integer anio, String vin, String tipoUnidad, String numeroIdentificacionInterno, String estatus, String motivoBaja) {
        this.idUnidad = idUnidad;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.vin = vin;
        this.tipoUnidad = tipoUnidad;
        this.numeroIdentificacionInterno = numeroIdentificacionInterno;
        this.estatus = estatus;
        this.motivoBaja = motivoBaja;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public void setNumeroIdentificacionInterno(String numeroIdentificacionInterno) {
        this.numeroIdentificacionInterno = numeroIdentificacionInterno;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public String getVin() {
        return vin;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public String getNumeroIdentificacionInterno() {
        return numeroIdentificacionInterno;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }
}
