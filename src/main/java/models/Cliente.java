package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Cliente extends Usuario {

    private String direccion;
    private int saldo;
    private ArrayList<Pedido> pedidos = new ArrayList<>();
    private Carrito carrito;


    public Cliente(){}

    public Cliente(Usuario usuario){
        super(usuario.getRut(), usuario.getNombre(), usuario.getEmail(), usuario.getPassword(), usuario.getImagen(), usuario.getDireccion());
    }

    public Cliente(String rut, String nonbre, String email, String password, String imagen, String direccion, int saldo) {
        super(rut, nonbre, email, password, imagen, direccion);
        this.direccion = direccion;
        this.saldo = saldo;
    }

    public String getDireccionDespacho() {
        return direccion;
    }

    public void setDireccionDespacho(String direccionDespacho) {
        this.direccion = direccionDespacho;
    }

    public int getSaldo() { return saldo; }

    public void setSaldo(int saldo) { this.saldo = saldo; }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }


    public void vaciarCarrito(){
        this.carrito.equals(null);
    }

    public void eliminarUnidadCarrito(Carrito unidad){
        this.carrito.getProductos().remove(unidad);
    }

    public void agregarUnidadCarrito(Producto unidad, int cantidad){
        this.carrito.getProductos().put(unidad, cantidad);
    }

    public Pedido crearPedido(){
        Pedido pedido = new Pedido();
        pedidos.add(pedido);

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        Despacho despacho = new Despacho("Envio por Chilexpress", 0, "Boldo 10143", "La Florida", "Metropolitana");
        pedido.setCliente(this);
        pedido.setFechaCreacion(fechaFormateada);
        pedido.setDespacho(despacho);
        pedido.setMetodoPago(despacho.getMetodoDespacho());
        pedido.setDespacho(despacho);
        pedido.setTotal(pedido.obtenerTotal());

        return pedido;
    }

    public void addPedidos(Pedido pedido){
        this.pedidos.add(pedido);
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

    @Override
    public boolean verificarLogin(String email, String password) {
        return super.verificarLogin(email, password);
    }


}
