package models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pedido {

    private int id;
    private Cliente cliente;
    private String fechaCreacion;
    private ArrayList<UnidadCarrito> listaProductos;
    private Despacho despacho;
    private String metodoPago;
    private int total;

    public String detalleFacturaTexto() {
        return  "------- DATOS DEL PEDIDO -------" +
                "Numero de pedido: " + id +
                "Fecha de creación: " + fechaCreacion +
                "Metodo de pago: " + metodoPago +
                "Despacho: " + despacho +
                "------- DATOS DEL CLIENTE -------" +
                "Nombre: " + cliente.getNombre() +
                "Correo:" + cliente.getEmail() +
                "Direccion: " + cliente.getDireccionDespacho() +
                "------- DETALLE DE ARTICULOS -------" +
                "" +
                "Total: " + total;
    }



    public Pedido(){}

    public Pedido(int id, Cliente cliente, String fechaCreacion, String metodoPago, Despacho despacho, ArrayList<UnidadCarrito> listaProductos, int total) {
        this.id = id;
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
        this.listaProductos = listaProductos;
        this.metodoPago = metodoPago;
        this.despacho = despacho;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ArrayList<UnidadCarrito> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<UnidadCarrito> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Despacho getDespacho() {
        return despacho;
    }

    public void setDespacho(Despacho despacho) {
        this.despacho = despacho;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public int obtenerTotal(){
        int total=0;
        for (int i = 0; i < listaProductos.size(); i++) {
            total += listaProductos.get(i).getProducto().getPrecio();
        }
        return total;
    }

    public void addUnidadCarrito(UnidadCarrito unidadCarrito){
        this.listaProductos.add(unidadCarrito);
    }

    public void generarFactura(){
        String nombreArchivoFactura = "factura_id_" + this.id + ".txt";

        String contenido = "Numero: " + this.id +
                           "\n Fecha: ";

        try {
            FileWriter writer = new FileWriter(nombreArchivoFactura);
            writer.write("\n" + "------- DATOS DEL PEDIDO -------" +
                            "\n" + "Numero de pedido: " + id +
                            "\n" + "Fecha de creación: " + fechaCreacion +
                            "\n" + "Metodo de pago: " + metodoPago +
                            "\n" + "Despacho: " + despacho +
                            "\n" + "------- DATOS DEL CLIENTE -------" +
                            "\n" + "Nombre: " + cliente.getNombre() +
                            "\n" + "Correo:" + cliente.getEmail() +
                            "\n" + "Direccion: " + cliente.getDireccionDespacho() +
                            "\n" + "------- DETALLE DE ARTICULOS -------" +
                            "\n" + detalleProductos() +
                            "\n" + "Total: " + total);

            writer.close();

            System.out.println("El archivo " + nombreArchivoFactura + " ha sido generado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String detalleProductos(){
        String detalle="";
        for (int i = 0; i < listaProductos.size(); i++) {

            detalle += "\n" + listaProductos.get(i).getProducto().getMarca() + " " +
                       listaProductos.get(i).getProducto().getModelo() + "     Cantidad: " +
                       listaProductos.get(i).getCantidad() + "      Precio unitario: " +
                       listaProductos.get(i).getPrecioUnitario();
        }
        return detalle;
    }

}
