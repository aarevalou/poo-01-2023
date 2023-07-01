import controller.GestorSQlite;
import models.Admin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GestorSQliteTest {


    @BeforeAll
    public static void conexion(){
        GestorSQlite.ActualizarConexion();
    }

    @BeforeEach
    public void setUp() {
        Admin admin = new Admin("33444555-K", "Alejandro Arévalo Sanchez", "a.arevalo02@ufromail.cl", "1234", "Boldo 10143", "", "Productos y Pedidos", "18/07/2023");
    }

    @Test
    public void testEjecutarSQL() {
        String sql = "SELECT * FROM Producto";
        GestorSQlite.ejecutarSQL(sql);
        assertDoesNotThrow(() -> GestorSQlite.ejecutarSQL(sql));
    }

    @Test
    public void testInsertarRegistro(){
        String tabla = "Marca";
        HashMap<String, String> nombre_valor = new HashMap<>();
        nombre_valor.put("nombre", "'Presonus'");
        assertDoesNotThrow(() -> GestorSQlite.insertarRegistro(tabla, nombre_valor));
    }

    @Test
    public void testEliminarRegistro(){
        String tabla = "Marca";
        String campos = "id, nombre";
        String valores = "7, Presonus";
        assertDoesNotThrow(() -> GestorSQlite.eliminarRegistro(tabla, campos, valores));
    }

    @Test
    public void testObtenerRegistro(){
        HashMap<String, String> registros = new HashMap<>();
        registros.put("rut", "206695846");
        registros.put("nombre", "Alejandro Arévalo Carrillo");
        registros.put("email", "email1");
        registros.put("password", "pwd1");
        registros.put("imagen", null);
        registros.put("direccion", "tubul 81");
        registros.put("saldo", "999999999");
        assertEquals(GestorSQlite.obtenerRegistro("Cliente", "rut", "206695846"), registros);
        registros.clear();

        registros.put("id", "1");
        registros.put("nombre", "Beyerdynamic");
        assertEquals(GestorSQlite.obtenerRegistro("Marca", "id", "1"), registros);
    }

    @Test
    public void testObtenerCantidadRegistros(){
        assertEquals(GestorSQlite.obtenerCantidadRegistros("Producto"), 10);
        assertEquals(GestorSQlite.obtenerCantidadRegistros("Cliente"), 1);
    }

    @Test
    public void testFormarCondicionWhere(){
        assertEquals(GestorSQlite.formarCondicionalWhere("id", "1"), " WHERE id = 1 ");
        assertEquals(GestorSQlite.formarCondicionalWhere("id,nombre,email", "1,Alejandro,email@gmail.com"), " WHERE id = 1 AND nombre = 'Alejandro' AND email = 'email@gmail.com' ");
    }

    @Test
    public void testConcatenarCampos(){
        ArrayList<String> campos = new ArrayList<>();
        campos.add("id");
        campos.add("nombre");
        campos.add("email");
        assertEquals(GestorSQlite.concatenarCampos(campos), "id, nombre, email");
    }
}
