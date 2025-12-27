package packetworldfx.dto;

/**
 *
 * @authors Ohana & Benito
 */
public class RSActualizarEstatus {

    private String numeroGuia;
    private int nuevoIdEstatus;
    private String comentario;
    private int idColaborador; //conductor que realiza la acción

    public RSActualizarEstatus(String numeroGuia, int nuevoIdEstatus, String comentario, int idColaborador) {
        this.numeroGuia = numeroGuia;
        this.nuevoIdEstatus = nuevoIdEstatus;
        this.comentario = comentario;
        this.idColaborador = idColaborador;
    }

    public RSActualizarEstatus() {
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public int getNuevoIdEstatus() {
        return nuevoIdEstatus;
    }

    public String getComentario() {
        return comentario;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public void setNuevoIdEstatus(int nuevoIdEstatus) {
        this.nuevoIdEstatus = nuevoIdEstatus;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }
}
