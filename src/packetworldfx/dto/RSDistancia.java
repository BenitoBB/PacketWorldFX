package packetworldfx.dto;

/**
 *
 * @authors Ohana & Benito
 */
public class RSDistancia {

    public double distanciaKM;
    public boolean error;
    public String mensaje;

    public RSDistancia() {
    }

    public RSDistancia(double distanciaKM, boolean error, String mensaje) {
        this.distanciaKM = distanciaKM;
        this.error = error;
        this.mensaje = mensaje;
    }

    public double getDistanciaKM() {
        return distanciaKM;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setDistanciaKM(double distanciaKM) {
        this.distanciaKM = distanciaKM;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
