package controller;

import models.Producto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class GestorProductos {
    public static  ArrayList<Producto> productos;
    public static ArrayList<String> marcas;
    public static ArrayList<String> categorias;
    private static ArrayList<String> atributosExtras;
    private static ArrayList<Producto> productosFiltrados = new ArrayList<>();

    public static void setAtributosExtras() {
        atributosExtras = new ArrayList<>();
        for (int i = 0; i < GestorSQlite.obtenerCantidadRegistros("Atributo"); i++) {
            HashMap<String, String> atributo = GestorSQlite.obtenerRegistro("Atributo", "id", String.valueOf(i+1));
            String nombre = atributo.get("nombre");
            atributosExtras.add(nombre);
        }
    }
    public static void setMarcas() {
        marcas = new ArrayList<>();
        for (int i = 0; i < GestorSQlite.obtenerCantidadRegistros("Marca"); i++) {
            HashMap<String, String> marca = GestorSQlite.obtenerRegistro("Marca", "id", String.valueOf(i+1));
            String nombre = marca.get("nombre");
            marcas.add(nombre);
        }
    }
    public static void setCategorias() {
        categorias = new ArrayList<>();
        for (int i = 0; i < GestorSQlite.obtenerCantidadRegistros("Categoria"); i++) {
            HashMap<String, String> categoria = GestorSQlite.obtenerRegistro("Categoria", "id", String.valueOf(i+1));
            String nombre = categoria.get("nombre");
            categorias.add(nombre);
        }
    }
    public static void setProductos() {
        productos = new ArrayList<>();
        String tabla = "Producto";
        for (int i = 0; i < GestorSQlite.obtenerCantidadRegistros(tabla); i++) {
            HashMap<String, String> registros = GestorSQlite.obtenerRegistro(tabla, "id", String.valueOf(i+1));
            Producto producto = new Producto();
            producto.setId(Integer.parseInt(registros.get("id")));
            producto.setMarca_id(Integer.parseInt(registros.get("marca_id")));
            producto.setCategoria_id(Integer.parseInt(registros.get("categoria_id")));
            producto.setPrecio(Integer.parseInt(registros.get("precio")));
            producto.setStock(Integer.parseInt(registros.get("stock")));
            producto.setImagen(registros.get("imagen"));
            producto.setModelo(registros.get("modelo"));
            productos.add(producto);
        }
    }
    public static ArrayList<String> getCategorias() { return categorias; }
    public static ArrayList<Producto> getProductos() { return productos; }
    public static ArrayList<String> getMarcas() { return marcas; }
    public static ArrayList<String> getAtributosExtras() { return atributosExtras; }

    public static void actualizarDatos() {
        setProductos();
        setCategorias();
        setMarcas();
    }
    public static ArrayList<Producto> obtenerProductosPorCategoria(ArrayList<Producto> productos, int categoria){

        productosFiltrados.clear();
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getCategoria_id() == categoria){
                productosFiltrados.add(productos.get(i));
            }
        }
        return productosFiltrados;
    }
    public static Producto obtenerProductoPorIndice(ArrayList<Producto> productos, int indice){
        Producto producto = productos.get(indice-1);
        return producto;
    }
    public static ArrayList<Producto> obtenerProductosPorMarca(ArrayList<Producto> productos, int marca){
        productosFiltrados.clear();
        for (Producto producto : productos){
            if(producto.getMarca_id() == marca){
                productosFiltrados.add(producto);
            }
        }
        return productosFiltrados;
    }
    public static ArrayList<Producto> obtenerProductosPorPrecio(ArrayList<Producto> productos, int minimo, int maximo){
        productosFiltrados.clear();
        for (Producto producto : productos){
            int precio = producto.getPrecio();
            if(precio >= minimo && precio <=maximo){
                productosFiltrados.add(producto);
            }
        }
        return productosFiltrados;
    }

    public static ArrayList<Producto> ordenarProductosPorPrecio(ArrayList<Producto> productos, Boolean ascendente) {
        ArrayList<Producto> productosFiltrados = new ArrayList<>(productos);
        Comparator<Producto> comparadorPrecio = Comparator.comparing(Producto::getPrecio);
        if (!ascendente) {
            comparadorPrecio = comparadorPrecio.reversed();
        }
        productosFiltrados = productosFiltrados.stream()
                .sorted(comparadorPrecio)
                .collect(Collectors.toCollection(ArrayList::new));
        return productosFiltrados;
    }

    public static ArrayList<Producto> obtenerProductosPorBusqueda(ArrayList<Producto> productos, String busqueda) {
        productosFiltrados.clear();
        int distancia = 4;
        for(Producto producto : productos){
            String marca = marcas.get(producto.getMarca_id()-1);
            String modelo = producto.getModelo();
            String nombre = marca + " " + modelo;

            if (distanciaLevenshtein(busqueda, marca) <= distancia ||
                    distanciaLevenshtein(busqueda, modelo) <= distancia ||
                    distanciaLevenshtein(busqueda, nombre) <= distancia) {
                productosFiltrados.add(producto);
            }
        }
        return productosFiltrados;
    }

    public static void addProducto(Producto producto){
        productos.add(producto);
    }

    public static int distanciaLevenshtein(String a, String b) {
        int longitudA = a.length();
        int longitudB = b.length();
        int[][] distancia = new int[longitudA + 1][longitudB + 1];

        for (int i = 0; i <= longitudA; i++) {
            distancia[i][0] = i;
        }
        for (int j = 0; j <= longitudB; j++) {
            distancia[0][j] = j;
        }
        for (int i = 1; i <= longitudA; i++) {
            for (int j = 1; j <= longitudB; j++) {
                int costo = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                distancia[i][j] = Math.min(Math.min(distancia[i - 1][j] + 1, distancia[i][j - 1] + 1), distancia[i - 1][j - 1] + costo);
            }
        }
        return distancia[longitudA][longitudB];
    }

}
