package packetworldfx.dto;

import packetworldfx.pojo.ConductorUnidad;

/**
 *
 * @authors Ohana & Benito
 */
public class RSConductorUnidad {

    private boolean error;
    private String mensaje;
    private ConductorUnidad asignacion;

    public RSConductorUnidad() {
    }

    public RSConductorUnidad(boolean error, String mensaje, ConductorUnidad asignacion) {
        this.error = error;
        this.mensaje = mensaje;
        this.asignacion = asignacion;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public ConductorUnidad getAsignacion() {
        return asignacion;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setAsignacion(ConductorUnidad asignacion) {
        this.asignacion = asignacion;
    }
}
