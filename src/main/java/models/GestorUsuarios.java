package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestorUsuarios {

    public static Cliente clienteActual;
    public static Admin adminActual;
    public static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void addUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public static String obtenerFechaActual(){
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaActual.format(formatter);
    }

    public static boolean validarRut(String rut) {
        // Remover puntos y guión del RUT
        rut = rut.replace(".", "").replace("-", "");

        // Validar longitud mínima del RUT
        if (rut.length() < 9) {
            return false;
        }

        // Separar dígito verificador del RUT
        char dv = rut.charAt(rut.length() - 1);
        String rutNumeros = rut.substring(0, rut.length() - 1);

        try {
            // Validar que los caracteres restantes sean solo dígitos
            int rutParsed = Integer.parseInt(rutNumeros);

            // Calcular dígito verificador esperado
            int m = 0, s = 1;
            for (; rutParsed != 0; rutParsed /= 10) {
                s = (s + rutParsed % 10 * (9 - m++ % 6)) % 11;
            }

            char dvEsperado = (char) (s != 0 ? s + 47 : 75); // Conversión ASCII

            // Comparar dígito verificador esperado con el recibido
            return Character.toUpperCase(dv) == Character.toUpperCase(dvEsperado);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
