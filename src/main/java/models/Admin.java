package models;

import controller.GestorProductos;

import java.util.Scanner;

public class Admin extends Usuario {
    private String fecha_ingreso;
    private String salario;
    private String cargo;

    Scanner scanner = new Scanner(System.in);

    public Admin(String fechaRegistro, String salario, String cargo) {
        this.fecha_ingreso = fechaRegistro;
        this.salario = salario;
        this.cargo = cargo;
    }

    public Admin(){}

    public Admin(Usuario usuario){
        super(usuario.getRut(), usuario.getNombre(), usuario.getEmail(), usuario.getPassword(), usuario.getImagen(), usuario.getDireccion());
    }
    public Admin(String rut, String nombre, String email, String password, String direccion, String imagen, String cargo, String fechaIngreso) {
        super(rut, nombre, email, password, imagen, direccion);
        this.cargo = cargo;
        this.fecha_ingreso = fechaIngreso;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
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

    @Override
    public String getRut() {
        return super.getRut();
    }

    @Override
    public void setRut(String rut) {
        super.setRut(rut);
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getImagen() {
        return super.getImagen();
    }

    @Override
    public void setImagen(String imagen) {
        super.setImagen(imagen);
    }

    @Override
    public String getDireccion() {
        return super.getDireccion();
    }

    @Override
    public void setDireccion(String direccion) {
        super.setDireccion(direccion);
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

    public void eliminarProducto(Producto p){
        GestorProductos.getProductos().remove(p);
    }

    private void ingresoDeDatosDelProducto(Producto p){

        System.out.println("Ingrese Marca del producto:");
        p.setMarca_id(scanner.nextInt());
        System.out.println("Ingrese Modelo del producto:");
        p.setModelo(scanner.nextLine());
        System.out.println("Ingrese Precio del producto:");
        p.setPrecio(Integer.parseInt(scanner.nextLine()));
        System.out.println("Ingrese Stock del producto:");
        p.setStock(Integer.parseInt(scanner.nextLine()));
        System.out.println("Ingrese Categoria del producto:");
        System.out.println("1) Procesador \n 2) Tarjeta gráfica \n 3) Placa madre \n 4) Memoria ram \n 5) Almacenamiento \n Fuente de poder");
        p.setCategoria_id(Integer.parseInt(scanner.nextLine()));

    }



    @Override
    public boolean verificarLogin(String email, String password) {
        return super.verificarLogin(email, password);
    }
}
