package packetworldfx.dto;

import packetworldfx.pojo.Direccion;

/**
 *
 * @authors Ohana & Benito
 */
public class RSDireccion {

    private boolean error;
    private String mensaje;
    private Direccion direccion;

    public RSDireccion() {
    }

    public RSDireccion(boolean error, String mensaje, Direccion direccion) {
        this.error = error;
        this.mensaje = mensaje;
        this.direccion = direccion;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
