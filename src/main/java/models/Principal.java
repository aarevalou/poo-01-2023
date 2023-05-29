package models;
import basedatos.GestorSQlite;

public class Principal {

    public static void main(String[] args) {

        GestorSQlite.Conexion();
        GestorProductos.setProductos(GestorSQlite.obtenerProductos());
        IConsola.mostrarMenuLogin();

        for (int i = 0; i < GestorProductos.productos.size(); i++) {
            System.out.println(GestorProductos.productos.get(i));
        }
    }
}
