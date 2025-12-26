package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class ConductorUnidad {

    private Integer idConductorUnidad;
    private String fechaAsignacion;
    private String fechaDesasignacion;
    private int activo; // 1 o 0
    // Foreign Keys
    private Integer idColaborador;
    private Integer idUnidad;

    public ConductorUnidad() {
    }

    public ConductorUnidad(Integer idConductorUnidad, String fechaAsignacion, String fechaDesasignacion, int activo, Integer idColaborador, Integer idUnidad) {
        this.idConductorUnidad = idConductorUnidad;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaDesasignacion = fechaDesasignacion;
        this.activo = activo;
        this.idColaborador = idColaborador;
        this.idUnidad = idUnidad;
    }

    public Integer getIdConductorUnidad() {
        return idConductorUnidad;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public String getFechaDesasignacion() {
        return fechaDesasignacion;
    }

    public int getActivo() {
        return activo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdConductorUnidad(Integer idConductorUnidad) {
        this.idConductorUnidad = idConductorUnidad;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public void setFechaDesasignacion(String fechaDesasignacion) {
        this.fechaDesasignacion = fechaDesasignacion;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }
}
