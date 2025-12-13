package packetworldfx.pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Rol {

    private Integer idRol;
    private String nombre;

    public Rol() {
    }

    public Rol(Integer idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
