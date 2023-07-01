package controller;

import models.Admin;
import models.Cliente;
import models.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class GestorUsuarios {

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

    public static Usuario iniciarSesion(String email, String password) {
        HashMap<String, String> atributosUsuario = GestorSQlite.obtenerRegistro("Cliente", "email,password", email + "," + password);
        if (!atributosUsuario.isEmpty()) {
            return (Cliente) GestorObjeto.construirObjeto(new Cliente(), atributosUsuario);
        }
        atributosUsuario = GestorSQlite.obtenerRegistro("Admin", "email,password", email + "," + password);
        if (!atributosUsuario.isEmpty()) {
            return (Admin) GestorObjeto.construirObjeto(new Admin(), atributosUsuario);
        }
        return null;
    }

    public static boolean validarRut(String rut) {
        rut = rut.replace(".", "").replace("-", "");
        if (rut.length() < 9) {
            return false;
        }

        char dv = rut.charAt(rut.length() - 1);
        String rutNumeros = rut.substring(0, rut.length() - 1);

        try {
            int rutParsed = Integer.parseInt(rutNumeros);
            int m = 0, s = 1;
            for (; rutParsed != 0; rutParsed /= 10) {
                s = (s + rutParsed % 10 * (9 - m++ % 6)) % 11;
            }
            char dvEsperado = (char) (s != 0 ? s + 47 : 75);

            return Character.toUpperCase(dv) == Character.toUpperCase(dvEsperado);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
