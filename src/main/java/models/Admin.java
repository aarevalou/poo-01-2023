package models;

import java.util.Scanner;

public class Admin extends Usuario {
    private String fechaIngreso;
    private String salario;
    private String cargo;

    Scanner scanner = new Scanner(System.in);

    public Admin(String fechaRegistro, String salario, String cargo) {
        this.fechaIngreso = fechaRegistro;
        this.salario = salario;
        this.cargo = cargo;
    }

    public Admin(){}

    public Admin(Usuario usuario){
        super(usuario.getRut(), usuario.getNombre(), usuario.getEmail(), usuario.getPassword(), usuario.getImagen());
    }
    public Admin(String rut, String nombre, String email, String password, String imagen, String cargo, String fechaRegistro) {
        super(rut, nombre, email, password, imagen);
        this.cargo = cargo;
        this.fechaIngreso = fechaRegistro;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void crearProducto(){
        Producto p = new Producto();
        ingresoDeDatosDelProducto(p);
        GestorProductos.getProductos().add(p);
    }

    public void editarProducto(int pos){
        Producto p = new Producto();
        ingresoDeDatosDelProducto(p);
        GestorProductos.getProductos().set(pos, p);
    }

    public Producto editarProducto2(Producto p){
        ingresoDeDatosDelProducto(p);
        return p;
    }

    public void eliminarProducto(Producto p){GestorProductos.getProductos().remove(p);
    }

    private void ingresoDeDatosDelProducto(Producto p){

        System.out.println("Ingrese Marca del producto:");
        p.setMarca(scanner.nextInt());
        System.out.println("Ingrese Modelo del producto:");
        p.setModelo(scanner.nextLine());
        System.out.println("Ingrese Precio del producto:");
        p.setPrecio(Integer.parseInt(scanner.nextLine()));
        System.out.println("Ingrese Stock del producto:");
        p.setStock(Integer.parseInt(scanner.nextLine()));
        System.out.println("Ingrese Categoria del producto:");
        System.out.println("1) Procesador \n 2) Tarjeta gráfica \n 3) Placa madre \n 4) Memoria ram \n 5) Almacenamiento \n Fuente de poder");
        p.setCategoria(Integer.parseInt(scanner.nextLine()));

    }



    @Override
    public boolean verificarLogin(String email, String password) {
        return super.verificarLogin(email, password);
    }
}
