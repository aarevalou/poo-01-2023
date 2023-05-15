package models;

import java.util.ArrayList;


public class BaseDatos {

    private static ArrayList<Cliente> clientes;
    private static ArrayList<Admin> administradores;
    private static ArrayList<Producto> productos;

    public static void addCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public static void addAdmin(Admin admin){
        administradores.add(admin);
    }

    public static void addProducto(Producto producto){
        productos.add(producto);
    }

    public static void removerCliente(Cliente cliente){
        clientes.remove(cliente);
    }

    public static void removerAdmin(Admin admin){
        administradores.remove(admin);
    }

    public static void removerProducto(Producto producto){
        productos.remove(producto);
    }

    public static void removerTodo(){
        clientes.clear();
        administradores.clear();
        productos.clear();
    }

}