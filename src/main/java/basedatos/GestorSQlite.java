package basedatos;

import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestorSQlite {

    static Connection connection = null;
    static Statement statement = null;

    // Conexion BD
    public static void Conexion() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/basedatos/database.db");
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    // CRUD Clientes:
    public static void insertarCliente(Cliente cliente) {
        String ClienteSQL = "INSERT INTO Cliente (rut, nombre, email, password, direccion, saldo) VALUES ('" +
                cliente.getRut() + "', '" +
                cliente.getNombre() + "', '" +
                cliente.getEmail() + "', '" +
                cliente.getPassword() + "', '" +
                cliente.getDireccionDespacho() + "', " +
                cliente.getSaldo() + ")";
        ejecutarSQL(ClienteSQL);

        // TODO : Validar si el rut existe antes de registrar
    }

    public static Cliente obtenerCliente(String email, String password) {
        String datosCliente = "SELECT * FROM Cliente WHERE email = '" + email + "' AND password = '" + password + "'";
        //
        ejecutarSQL(datosCliente);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(datosCliente);

            Cliente cliente = new Cliente();

            if(resultSet.next()) {
                String rut = resultSet.getString("rut");
                String nombre = resultSet.getString("nombre");
                String imagen = resultSet.getString("imagen");
                String direccion = resultSet.getString("direccion");
                int saldo = resultSet.getInt("saldo");

                cliente.setId(rut);
                cliente.setNombre(nombre);
                cliente.setEmail(email);
                cliente.setPassword(password);
                cliente.setImagen(imagen);
                cliente.setDireccionDespacho(direccion);
                cliente.setSaldo(saldo);
            }
            else { return null; }

            ArrayList<Pedido> pedidos = new ArrayList<>();
            String Pedidos = "SELECT * FROM Pedido WHERE cliente_id = " + cliente.getRut();
            ResultSet resultSet2 = statement.executeQuery(Pedidos);

            while (resultSet2.next()) {
                int id_pedido = resultSet2.getInt("id");
                String fecha_creacion = resultSet2.getString("fecha_creacion");
                int despacho_id = resultSet2.getInt("despacho_id");
                String metodo_pago = resultSet2.getString("metodo_pago");
                int total = resultSet2.getInt("subtotal");

                Despacho despacho = new Despacho();
                Pedido pedido = new Pedido(id_pedido, null, fecha_creacion, metodo_pago, despacho, null, total);
                pedidos.add(pedido);
            }
            cliente.setPedidos(pedidos);
            return cliente;

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static void modificarCliente(Cliente cliente) {
        String Query = "UPDATE Cliente SET (rut, nombre, email, password, direccion, saldo) VALUES (" +
                cliente.getRut() + ", '" +
                cliente.getNombre() + "', '" +
                cliente.getEmail() + "', '" +
                cliente.getPassword() + "', " +
                cliente.getSaldo() +
                ") WHERE rut = " + cliente.getRut();
        ejecutarSQL(Query);
    }

    public static void eliminarCliente(Cliente cliente) {
        String Query = "DELETE FROM Cliente WHERE rut = " + cliente.getRut();
        ejecutarSQL(Query);
    }

    // CRUD Admins:
    public static void insertarAdmin(Admin admin) {
        String ClienteSQL = "INSERT INTO Admin (rut, nombre, email, password, imagen, cargo, fecha_ingreso) VALUES ('" +
                admin.getRut() + "', '" +
                admin.getNombre() + "', '" +
                admin.getEmail() + "', '" +
                admin.getPassword() + "', '" +
                admin.getImagen() + "', '" +
                admin.getCargo() + "', '" +
                admin.getFechaIngreso() + "')";
        ejecutarSQL(ClienteSQL);

        // TODO : Validar si el rut existe antes de registrar
    }

    public static Admin obtenerAdmin(String email, String password) {
        String datosCliente = "SELECT * FROM Admin WHERE email = '" + email + "' AND password = '" + password + "'";
        ejecutarSQL(datosCliente);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(datosCliente);


            if(resultSet.next()) {
                String rut = resultSet.getString("rut");
                String nombre = resultSet.getString("nombre");
                String imagen = resultSet.getString("imagen");
                String cargo = resultSet.getString("cargo");
                String fecha_ingreso = resultSet.getString("fecha_ingreso");

                Admin admin = new Admin(rut, nombre, email, password, imagen, cargo, fecha_ingreso);

                return admin;
            }
            else { return null; }

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static void modificarAdmin(Admin admin) {
        String Query = "UPDATE Admin SET (rut, nombre, email, password, imagen, cargo, fecha_ingreso) VALUES (" +
                admin.getRut() + ", '" +
                admin.getNombre() + "', '" +
                admin.getEmail() + "', '" +
                admin.getPassword() + "', " +
                admin.getImagen() + "', " +
                admin.getCargo() + "', " +
                admin.getFechaIngreso() +
                ") WHERE rut = " + admin.getRut();
        ejecutarSQL(Query);
    }

    public static void eliminarAdmin(Admin admin) {
        String Query = "DELETE FROM Admin WHERE rut = " + admin.getRut();
        ejecutarSQL(Query);
    }

    // CRUD Productos:
    public static void insertarProducto(Producto producto){
        String ProductoInsert = "INSERT INTO Producto (marca_id, modelo, precio, stock, imagen, categoria_id) VALUES (" +
                producto.getMarca() + ", '" +
                producto.getModelo() + "', " +
                producto.getPrecio() + ", " +
                producto.getStock() + ", '" +
                producto.getImagen() + "', " +
                producto.getCategoria()  + ")";
        //ejecutarSQL(ProductoInsert);

        try {
            PreparedStatement statement = connection.prepareStatement(ProductoInsert);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al realizar la inserción: " + e.getMessage());
        }

        HashMap<String, String> atributos = producto.getAtributos();

        for (Map.Entry<String, String> entry : atributos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Clave: " + key + ", Valor: " + value);

            String AtributosInsert = "INSERT INTO Atributo_Producto (producto_id, atributo_id, valor) VALUES (" +
                    producto.getId() + ", '" +
                    key +  "', '" + value + "')";
            ejecutarSQL(AtributosInsert);
        }
    }

    public static ArrayList<Producto> obtenerProductos(){
        String productosSQL = "SELECT * FROM Producto";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(productosSQL);

            ArrayList<Producto> listaObtenida = new ArrayList<>();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                int marca_id = resultSet.getInt("marca_id");
                String modelo = resultSet.getString("modelo");
                int precio = resultSet.getInt("precio");
                int stock = resultSet.getInt("stock");
                String imagen = resultSet.getString("imagen");
                int categoria = resultSet.getInt("categoria_id");

                Producto producto = new Producto(id, marca_id, modelo, precio, stock, imagen, categoria, null);
                listaObtenida.add(producto);
            }

            for (Producto producto : listaObtenida) {
                String atributosSQL = "SELECT * FROM Atributo_Producto WHERE producto_id = " + producto.getId();
                ResultSet resultSet2 = statement.executeQuery(atributosSQL);
                HashMap atributosExtras = new HashMap();

                while (resultSet2.next()) {

                    int atributo_id = resultSet2.getInt("atributo_id");
                    String valor = resultSet2.getString("valor");

                    String nombreAtributoSQL = "SELECT nombre FROM Atributo WHERE id = " + atributo_id;
                    ejecutarSQL(nombreAtributoSQL);
                    ResultSet resultSet3 = statement.executeQuery(nombreAtributoSQL);

                    if(resultSet3.next()){
                        String claveAtributo = resultSet3.getString("nombre");
                        atributosExtras.put(claveAtributo, valor);
                    }
                }

                producto.setAtributos(atributosExtras);
            }

            return listaObtenida;

        } catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public static void modificarProducto(Producto producto){
        String ProductoUpdate = "UPDATE Producto SET (id, marca, modelo, precio, stock, imagen, categoria_id) VALUES (" +
                producto.getId() + ", '" +
                producto.getMarca() + ", '" +
                producto.getModelo() + ", '" +
                producto.getPrecio() + ", '" +
                producto.getStock() + ", '" +
                producto.getImagen() + ", '" +
                producto.getCategoria()  +
                ") WHERE id = " + producto.getId();
        ejecutarSQL(ProductoUpdate);

        HashMap<String, String> atributos = producto.getAtributos();

        for (Map.Entry<String, String> entry : atributos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Clave: " + key + ", Valor: " + value);

            String AtributosInsert = "UPDATE Atributo_Producto SET (producto_id, atributo_id, valor) VALUES (" +
                    producto.getId() + ", '" +
                    key +  "', '" + value + "')" +
                    ") WHERE producto_id = " + producto.getId();
            ejecutarSQL(AtributosInsert);
        }
    }

    public static void eliminarProducto(Producto producto) {
        String Query = "DELETE FROM Admin WHERE id = " + producto.getId();
        ejecutarSQL(Query);
    }

    // CRUD Marcas:
    public static void insertarMarca(String marca){

        String marcasSelect = "SELECT nombre FROM Marca";
        String marcaInsert = "INSERT INTO Marca (nombre) VALUES (" + marca + ")";

        ArrayList<String> marcas = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(marcasSelect);

            while(resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                marcas.add(nombre);
            }

            if(!marcas.contains(marca)){
                ejecutarSQL(marcaInsert);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<String> obtenerMarcas(){
        String marcasQuery = "SELECT * FROM Marca";
        ejecutarSQL(marcasQuery);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(marcasQuery);

            ArrayList<String> marcas = new ArrayList<>();

            while(resultSet.next()) {
                String marca = resultSet.getString("nombre");
                marcas.add(marca);
            }

            return marcas;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static String obtenerMarca(int marca_id){
        String marcasQuery = "SELECT nombre FROM Marca WHERE id = " + marca_id;
        ejecutarSQL(marcasQuery);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(marcasQuery);

            while(resultSet.next()) {
                String marca = resultSet.getString("nombre");
                return marca;
            }

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static void modificarMarca(int id, String nombre){
        String MarcaUpdate = "UPDATE Categoria SET (nombre) VALUES ('" + nombre +
                "') WHERE id = " + id;
        ejecutarSQL(MarcaUpdate);
    }

    public static void eliminarMarca(int marca_id){
        String Query = "DELETE FROM Marca WHERE id = " + marca_id;
        ejecutarSQL(Query);
    }

    // CRUD Categorias:

    public static void insertarCategoria(String categoria_id, String descripcion){

        String categoriaInsert = "INSERT INTO Categoria (nombre, descripcion) VALUES ('" + categoria_id + "', '" + descripcion + "')";
        ejecutarSQL(categoriaInsert);

        // TODO : Verificar si la categoria ya existe
    }

    public static ArrayList<String> obtenerCategorias(){
        String marcasQuery = "SELECT * FROM Categoria";
        ejecutarSQL(marcasQuery);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(marcasQuery);

            ArrayList<String> marcas = new ArrayList<>();

            while(resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                marcas.add(nombre);
            }

            return marcas;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void modificarCategoria(int id, String nombre, String descripcion){
        String CategoriaUpdate = "UPDATE Categoria SET (nombre, descripcion) VALUES ('" + nombre + "', '" + descripcion +
                "') WHERE id = " + id;
        ejecutarSQL(CategoriaUpdate);
    }

    public static void eliminarCategoria(int categoria_id){
        String DeleteCategoria = "DELETE FROM Categoria WHERE id = " + categoria_id;
        ejecutarSQL(DeleteCategoria);
    }

    // QUERY:
    private static void ejecutarSQL(String Query){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // TODO : Hacer CRUD para tabla Pedido
}
