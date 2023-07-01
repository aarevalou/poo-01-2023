package models;

public abstract class Usuario {

    private String rut;
    private String nombre;
    private String email;
    private String password;
    private String direccion;
    private String imagen;

    public Usuario(){}

    public Usuario(String rut, String nombre, String email, String password, String imagen, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.imagen = imagen;
        this.direccion = direccion;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void iniciarSesion(){}
    public void crearCuenta(){}
    public boolean verificarLogin(String email, String password){

        if(this.email.equals(email) && this.password.equals(password)){
            return true;
        }
        else {
            return false;
        }
    }
}
