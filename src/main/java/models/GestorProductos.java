package models;

import basedatos.GestorSQlite;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GestorProductos {

    public static ArrayList<Producto> productos = new ArrayList<>();

    public static ArrayList<Producto> getProductos() {
        return productos;
    }

    public static void setProductos(ArrayList<Producto> productos) {
        GestorProductos.productos = productos;
    }

    private static ArrayList<Producto> listaFiltrada = new ArrayList<>();
    public static ArrayList<Producto> obtenerProductosPorCategoria(ArrayList<Producto> productos, int categoria){

        listaFiltrada.clear();
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getCategoria() == categoria){
                listaFiltrada.add(productos.get(i));
            }
        }
        return listaFiltrada;
    }

    public static Producto obtenerProductoPorIndice(ArrayList<Producto> productos, int indice){
        Producto producto = productos.get(indice-1);
        return producto;
    }
    public static ArrayList<Producto> obtenerProductosPorMarca(ArrayList<Producto> productos, int marca){

        listaFiltrada.clear();
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getMarca() == marca){
                listaFiltrada.add(productos.get(i));
            }
        }
        return listaFiltrada;
    }

    public static ArrayList<Producto> obtenerProductosPorPrecio(ArrayList<Producto> productos, int minimo, int maximo){

        listaFiltrada.clear();

        for (int i = 0; i < productos.size(); i++) {
            int precio = productos.get(i).getPrecio();
            if(precio >= minimo && precio <= maximo){
                listaFiltrada.add(productos.get(i));
            }
        }
        return listaFiltrada;
    }

    public static ArrayList<Producto> ordenarProductosPorPrecio(ArrayList<Producto> productos, Boolean ascendente){

        listaFiltrada = productos;
        Comparator<Producto> comparador = new Comparator<>() {
            @Override
            public int compare(Producto p1, Producto p2) {
                if (ascendente){
                    return Double.compare(p1.getPrecio(), p2.getPrecio());
                }
                else{
                    return Double.compare(p2.getPrecio(), p1.getPrecio());
                }
            }
        };
        listaFiltrada.sort(comparador);
        return listaFiltrada;
    }

    public static ArrayList<Producto> obtenerProductosPorBusqueda(ArrayList<Producto> productos, String busqueda) {
        listaFiltrada.clear();
        int distancia=4;


        for (int i = 0; i < productos.size(); i++) {
            String marca = GestorSQlite.obtenerMarca(productos.get(i).getMarca());
            String modelo = productos.get(i).getModelo();
            String nombre = marca + " " + modelo;

            if (distanciaLevenshtein(busqueda, marca) <= distancia ||
                    distanciaLevenshtein(busqueda, modelo) <= distancia ||
                    distanciaLevenshtein(busqueda, nombre) <= distancia) {
                listaFiltrada.add(productos.get(i));
            }

            else if (distanciaLevenshtein(busqueda, nombre) <= distancia) {
                listaFiltrada.add(productos.get(i));
            }
        }

        return listaFiltrada;
    }


    private static int distanciaLevenshtein(String a, String b) {
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
