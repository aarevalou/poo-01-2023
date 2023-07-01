import controller.GestorObjeto;
import models.Cliente;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GestorObjectoTest {

    @Test
    public void testConstruirObjeto(){
        HashMap<String, String> atributosCliente = new HashMap<>();
        atributosCliente.put("rut", "206695846");
        atributosCliente.put("nombre", "Alejandro Arévalo Carrillo");
        atributosCliente.put("email", "email1@gmail.com");
        Cliente cliente = (Cliente) GestorObjeto.construirObjeto(new Cliente(), atributosCliente);

        assertEquals("206695846", cliente.getRut());
        assertEquals("Alejandro Arévalo Carrillo", cliente.getNombre());
        assertEquals("email1@gmail.com", cliente.getEmail());
    }

    @Test
    public void testObtenerAtributosObjeto(){
        Cliente cliente = new Cliente();
        cliente.setRut("206695846");
        cliente.setNombre("Alejandro Arévalo Carrillo");
        cliente.setEmail("email1@gmail.com");
        HashMap<String, String> atributos = GestorObjeto.obtenerAtributosObjeto(cliente);
        System.out.println(atributos);
        assertNotNull(atributos);
        assertEquals("206695846", atributos.get("rut"));
        assertEquals("Alejandro Arévalo Carrillo", atributos.get("nombre"));
        assertEquals("email1",atributos.get("email"));

    }
}
