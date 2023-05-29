package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Cliente extends Usuario {

    private String numeroTelefono;
    private String direccion;
    private int saldo;
    private ArrayList<Pedido> pedidos = new ArrayList<>();
    private ArrayList<UnidadCarrito> listaCarrito = new ArrayList<>();



    public Cliente(){}

    public Cliente(Usuario usuario){
        super(usuario.getRut(), usuario.getNombre(), usuario.getEmail(), usuario.getPassword(), usuario.getImagen());
    }

    public Cliente(String rut, String nonbre, String email, String password, String imagen, String direccionDespacho, int saldo, ArrayList<Pedido> pedidos, ArrayList<UnidadCarrito> listaCarrito) {
        super(rut, nonbre, email, password, imagen);
        this.direccion = direccionDespacho;
        this.saldo = saldo;
        this.pedidos = pedidos;
        this.listaCarrito = listaCarrito;
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

    public ArrayList<UnidadCarrito> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(ArrayList<UnidadCarrito> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }


    public void vaciarCarrito(){
        this.listaCarrito.clear();
    }

    public void eliminarUnidadCarrito(UnidadCarrito unidad){
        this.listaCarrito.remove(unidad);
    }

    public void agregarUnidadCarrito(UnidadCarrito unidad){
        this.listaCarrito.add(unidad);
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
        pedido.setListaProductos(GestorUsuarios.clienteActual.getListaCarrito());
        pedido.setDespacho(despacho);
        pedido.setMetodoPago(despacho.getMetodoDespacho());
        pedido.setDespacho(despacho);
        pedido.setTotal(pedido.obtenerTotal());

        return pedido;
    }

    public void addPedidos(Pedido pedido){
        this.pedidos.add(pedido);
    }
    private ArrayList<Producto> obtenerProductosCarrito(){
        ArrayList<Producto> nuevaLista = new ArrayList<>();
        for (int i = 0; i < listaCarrito.size(); i++) {
            nuevaLista.add(listaCarrito.get(i).getProducto());
        }
        return nuevaLista;
    }

    @Override
    public boolean verificarLogin(String email, String password) {
        return super.verificarLogin(email, password);
    }
}
