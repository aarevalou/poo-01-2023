package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Principal {

    private static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Producto> productos = new ArrayList<>();
    public static ArrayList<UnidadCarrito> listaCarrito = new ArrayList<>();
    private static String[] categorias = {"Audífonos", "Monitores de estudio", "Controladores MIDI", "Interfaces de audio","Microfonos"};

    private static String[] marcas = {"Beyerdynamic", "Sennheiser", "KRK" ,"Focusrite"};
    private static Cliente cliente;


    public static void main(String[] args) {

        cliente = new Cliente("206695846", "Alejandro", "croverbass@gmail.com","password", false, "asdf", "boldo 10143", 999999999, 0, new ArrayList<>(), new ArrayList<>());
        IConsola consola = new IConsola();

        HashMap<String, Object> specs1 = new HashMap<>();
        specs1.put("Impedancia", "80 Ohm");
        specs1.put("Respuesta en frecuencia", "5 Hz - 35000 Hz");

        HashMap<String, Object> specs2 = new HashMap<>();
        specs2.put("Impedancia", "250 Ohm");
        specs2.put("Respuesta en frecuencia", "5 Hz - 35000 Hz");

        HashMap<String, Object> specs3 = new HashMap<>();
        specs3.put("Impedancia", "300 Ohm");
        specs3.put("Respuesta en frecuencia", "10 Hz - 30000 Hz");

        HashMap<String, Object> specs4 = new HashMap<>();
        specs4.put("Pulgadas Woofer", 5);
        specs4.put("Pulgadas Tweeter", 1);
        specs4.put("Respuesta en frecuencia", "43 Hz - 40000 Hz");

        HashMap<String, Object> specs5 = new HashMap<>();
        specs5.put("Sampleo", "24 bit / 192 Khz");
        specs5.put("Salidas", 2);
        specs5.put("Entradas", 2);


        Producto p1 = new Producto(1, "Beyerdynamic", "DT-770 Pro 80 Ohm", 170000, 5, "asdf", 0, specs1);
        Producto p2 = new Producto(1, "Beyerdynamic", "DT-990 Pro 250 Ohm", 150000, 8, "asdf", 0, specs2);
        Producto p3 = new Producto(1, "Sennheiser", "HD 650", 250000, 11, "asdf", 0, specs3);
        Producto p4 = new Producto(1, "KRK", "Rokit RP5 G4", 150000, 5, "asdf", 1, specs4);
        Producto p5 = new Producto(1, "Focusrite", "Scarlett 2i2", 150000, 5, "asdf", 1, specs5);
        //Producto p5 = new Producto(1, "KRK", "Rokit RP7 G4", 300000, 7, "asdf", 1, " Fabricante: KRK, Modelo: Rokit RP7 G4, Pulgadas Woofer: 7, Pulgadas Tweeter: 1, Respuesta en frecuencia: 27 Hz - 40000 Hz");
        //Producto p6 = new Producto(1, "ADAM", "T5V", 170000, 5, "asdf", 1, " Fabricante: ADAM, Modelo: T5V, Pulgadas Woofer: 5, Pulgadas Tweeter: 1, Respuesta en frecuencia: 45 Hz - 25000 Hz");
        //Producto p8 = new Producto(1, "Focusrite", "Scarlett 4i2", 350000, 5, "asdf", 1, " Fabricante: Focusrite, Modelo: Scarlett 4i2 3rd Gen, Sampleo: 24 bit / 192 Khz, Salidas: 4, Entradas: 2");
        //Producto p9 = new Producto(1, "Behringer", "UMC1820", 190000, 5, "asdf", 1, " Fabricante: Behringer, Modelo: UMC1820, Sampleo: 24 bit / 192 Khz, Salidas: 8, Entradas: 8");
        //Producto p10 = new Producto(1, "Novation", "Launchpad S ", 190000, 5, "asdf", 1, " Fabricante: Novation, Modelo: Launchpad S MK3, Cantidad de pads: 64, Conexión: USB-midi");*/
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);
        productos.add(p5);
        
        
	int opcion = consola.validarEntero("1) Inicar como Cliente, 2) Iniciar como Administrador", 2);
        consola.inicio(opcion);
    }

    public static ArrayList<Producto> getProductos() {
        return productos;
    }

    public static void setProductos(ArrayList<Producto> productos) {
        Principal.productos = productos;
    }

    public static String[] getCategorias() {
        return categorias;
    }

    public static String[] getMarcas() {
        return marcas;
    }

    public static void setCategorias(String[] categorias) {
        Principal.categorias = categorias;
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        Principal.cliente = cliente;
    }

    public static void obtenerRegistrosBD(){}


}
