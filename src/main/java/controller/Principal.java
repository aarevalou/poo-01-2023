package controller;
import controller.GestorProductos;
import controller.GestorSQlite;
import view.IConsola;

public class Principal {
    public static void main(String[] args) {
        GestorSQlite.ActualizarConexion();
        GestorProductos.actualizarDatos();
        IConsola.mostrarMenuLogin();
    }
}