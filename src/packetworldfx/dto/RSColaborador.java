package packetworldfx.dto;

import packetworldfx.pojo.Colaborador;

/**
 *
 * @authors Ohana & Benito
 */
public class RSColaborador {

    private boolean error;
    private String mensaje;
    private Colaborador colaborador;

    public RSColaborador() {
    }

    public RSColaborador(boolean error, String mensaje, Colaborador colaborador) {
        this.error = error;
        this.mensaje = mensaje;
        this.colaborador = colaborador;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
}
