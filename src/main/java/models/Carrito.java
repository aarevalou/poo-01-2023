package models;

import java.util.HashMap;
import java.util.Map;

public class Carrito {

    private Cliente cliente;
    private HashMap<Producto, Integer> productos;

    public Carrito(){}

    public Carrito(Cliente cliente, HashMap<Producto, Integer> productos) {
        this.cliente = cliente;
        this.productos = productos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public HashMap<Producto, Integer> getProductos() {
        return productos;
    }
    public void setProductos(HashMap productos) {
        this.productos = productos;
    }
    public int calcularPrecioTotal(){
        int total=0;
        for (Map.Entry<Producto, Integer> producto : productos.entrySet()){
            total += producto.getKey().getPrecio() * producto.getValue();
        }
        return total;
    }
}
