package models;

import java.util.Random;
import java.util.Scanner;

public class Despacho {

    private String metodoDespacho;
    private int costoDespacho;
    private String direccion;
    private String comuna;
    private String region;

    Scanner scanner = new Scanner(System.in);

    public Despacho(){}

    public Despacho(String metodoDespacho, int costoDespacho, String direccion, String comuna, String region) {
        this.metodoDespacho = metodoDespacho;
        this.costoDespacho = costoDespacho;
        this.direccion = direccion;
        this.comuna = comuna;
        this.region = region;
    }

    // constructor por defecto para el cliente
    public Despacho(String direccion, String comuna, String region) {
        this.direccion = direccion;
        this.comuna = comuna;
        this.region = region;
    }

    public String getMetodoDespacho() {
        return metodoDespacho;
    }

    public void setMetodoDespacho(String metodoDespacho) {
        this.metodoDespacho = metodoDespacho;
    }

    public int getCostoDespacho() {
        return costoDespacho;
    }

    public void setCostoDespacho(int costoDespacho) {
        this.costoDespacho = costoDespacho;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int calcularCosto(){
        Random rand = new Random();
        int minimo = 1000;
        int maximo = 10000;
        int intervalo = 100;

        int costo = intervalo * (rand.nextInt((maximo - minimo) / intervalo) + 1) + minimo;
        return costo;
    }

    public void actualizarInfo(){
        System.out.println("Ingrese Metodo de pago:");
        setMetodoDespacho(scanner.nextLine());
        System.out.println("Ingrese Direccion:");
        setDireccion(scanner.nextLine());
        System.out.println("Ingrese Comuna:");
        setComuna(scanner.nextLine());
        System.out.println("Ingrese Region:");
        setRegion(scanner.nextLine());
    }
}
