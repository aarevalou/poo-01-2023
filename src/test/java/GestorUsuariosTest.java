import controller.GestorProductos;
import controller.GestorSQlite;
import controller.GestorUsuarios;
import models.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class GestorUsuariosTest {

    @BeforeAll
    public static void conexion() {
        GestorSQlite.ActualizarConexion();
        GestorProductos.actualizarDatos();
    }

    @BeforeEach
    public void setUp() {
    }

    @ParameterizedTest
    @CsvSource({
            "206695846",
            "20669584-6",
            "20.669.584-6"
    })
    public void testValidarTest(String rut){
        assertTrue(GestorUsuarios.validarRut(rut));
    }

    @ParameterizedTest
    @CsvSource({
            "email1, pwd1",
            "email2, pwd2"
    })
    public void testIniciarSesion(String email, String password){
        Usuario usuario = GestorUsuarios.iniciarSesion(email, password);
        assertNotNull(usuario);
    }
    @Test
    public void testObtenerFechaActual(){
        String fecha = "01/07/2023";
        assertEquals(fecha, GestorUsuarios.obtenerFechaActual());
    }

}
