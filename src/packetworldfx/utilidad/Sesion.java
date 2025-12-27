package packetworldfx.utilidad;

import packetworldfx.pojo.Colaborador;

/**
 *
 * @authors Ohana & Benito
 */
public class Sesion {
    
    private static Colaborador colaborador;

    private Sesion() {}

    public static void iniciarSesion(Colaborador colab) {
        colaborador = colab;
    }

    public static Colaborador getColaborador() {
        return colaborador;
    }

    public static boolean haySesion() {
        return colaborador != null;
    }

    public static void cerrarSesion() {
        colaborador = null;
    }
}
