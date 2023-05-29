package models;

import java.util.HashMap;

public class Producto {

    private int id;
    private int marca;
    private String modelo;
    private int precio;
    private int stock;
    private String imagen;
    private int categoria;
    private HashMap atributosExtras = new HashMap();


    public Producto(){}

    public Producto(int id, int marca, String modelo, int precio, int stock, String imagen, int categoria, HashMap atributosExtras) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria = categoria;
        this.atributosExtras = atributosExtras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public HashMap getAtributos() {
        return atributosExtras;
    }

    public void setAtributos(HashMap atributos) {
        this.atributosExtras = atributos;
    }

    public boolean verificarStock(){
        if(this.stock > 0)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public void addSpec(String clave, String valor){
        atributosExtras.put(clave, valor);
    }

    @Override
    public String toString() {
        return "Producto{" +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
