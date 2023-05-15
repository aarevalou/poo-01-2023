package models;

public class Usuario {

    private String rut;
    private String nombre;
    private String email;
    private String password;
    private boolean superUsuario;
    private String imagen;

    public Usuario(){}

    public Usuario(String rut, String nonbre, String email, String password, boolean superUsuario, String imagen) {
        this.rut = rut;
        this.nombre = nonbre;
        this.email = email;
        this.password = password;
        this.superUsuario = superUsuario;
        this.imagen = imagen;
    }

    public String getId() {
        return rut;
    }

    public void setId(String rut) {
        this.rut = rut;
    }

    public String getNonbre() {
        return nombre;
    }

    public void setNonbre(String nonbre) {
        this.nombre = nonbre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuperUsuario() {
        return superUsuario;
    }

    public void setSuperUsuario(boolean superUsuario) {
        this.superUsuario = superUsuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void iniciarSesion(){}
    public void crearCuenta(){}
    public boolean validadPassword(String password){

        if(this.password == password){
            return true;
        }
        else {
            return false;
        }
    }
    public void cerrarSesion(){}
}
