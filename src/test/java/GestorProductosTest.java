import controller.*;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class GestorProductosTest {

    private ArrayList<Producto> productos;

    @BeforeAll
    public static void conexion(){
        GestorSQlite.ActualizarConexion();
        GestorProductos.actualizarDatos();
    }

    @BeforeEach
    public void setup() {
        productos = new ArrayList<>();
        for (int i = 0; i < GestorSQlite.obtenerCantidadRegistros("Producto"); i++) {
            HashMap<String, String> registro = GestorSQlite.obtenerRegistro("Producto", "id", String.valueOf(i+1));
            Producto p = (Producto) GestorObjeto.construirObjeto(new Producto(), registro);
            productos.add(p);
        }
    }

    @Test
    public void testObtenerProductosPorCategoria(){
        List<Producto> productosFiltrados = productos.stream()
                .filter(producto -> producto.getCategoria_id()==1)
                .collect(Collectors.toList());
        assertEquals(GestorProductos.obtenerProductosPorCategoria(productos, 1), productosFiltrados);
        assertEquals(productosFiltrados.size(), 4);
    }

    @Test
    public void testObtenerProductosPorMarca(){
        List<Producto> productosFiltrados = productos.stream()
                .filter(producto -> producto.getMarca_id()==1)
                .collect(Collectors.toList());
        assertEquals(GestorProductos.obtenerProductosPorMarca(productos, 1), productosFiltrados);
        assertEquals(productosFiltrados.size(), 2);
        assertEquals(productosFiltrados.get(0), productos.get(0));
    }

    @Test
    public void testObtenerProductoPorIndice() {
        Producto producto = GestorProductos.obtenerProductoPorIndice(productos, 2);
        assertEquals(producto, productos.get(1));
    }


    @Test
    public void testObtenerProductosPorPrecio() {
        ArrayList<Producto> productosFiltrados = GestorProductos.obtenerProductosPorPrecio(productos, 0, 150000);
        assertEquals(productosFiltrados.size(), 3);
        assertEquals(productosFiltrados.get(0), productos.get(1));
    }

    @Test
    public void testOrdenarProductosPorPrecioAscendente() {
        ArrayList<Producto> productosOrdenadosAsc = new ArrayList<>(productos);
        productosOrdenadosAsc.sort(Comparator.comparing(Producto::getPrecio));
        assertEquals(GestorProductos.ordenarProductosPorPrecio(productos, true), productosOrdenadosAsc);
    }

    @Test
    public void testOrdenarProductosPorPrecioDescendente() {
        ArrayList<Producto> productosOrdenadosDes = new ArrayList<>(productos);
        productosOrdenadosDes.sort(Comparator.comparing(Producto::getPrecio).reversed());
        assertEquals(GestorProductos.ordenarProductosPorPrecio(productos, false), productosOrdenadosDes);
    }

    @Test
    public void testObtenerProductosPorBusqueda() {
        ArrayList<Producto> productosFiltrados = GestorProductos.obtenerProductosPorBusqueda(productos, "Beyerdynamic");
        assertEquals(2, productosFiltrados.size());
        assertEquals(productos.get(0), productosFiltrados.get(0));
    }

    @ParameterizedTest
    @CsvSource ({
            "Beyerdi, Beyerdinamic, 5",
            "Sennheiser, Senheise, 2",
            "Focusrite, focusrite, 1",
            "ADAM, A, 3"
    })
    public void testDistanciaLevenshtein(String a, String b, int actual){
        assertEquals(GestorProductos.distanciaLevenshtein(a, b), actual);
    }

}
