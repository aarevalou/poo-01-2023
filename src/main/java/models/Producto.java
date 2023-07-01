package models;

import java.util.HashMap;

public class Producto {

    private int id;
    private int marca_id;
    private String modelo;
    private int precio;
    private int stock;
    private String imagen;
    private int categoria_id;
    private HashMap atributosExtras = new HashMap<>();


    public Producto(){}

    public Producto(int id, int marca, String modelo, int precio, int stock, String imagen, int categoria, HashMap atributosExtras) {
        this.id = id;
        this.marca_id = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria_id = categoria;
        this.atributosExtras = atributosExtras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarca_id() {
        return marca_id;
    }

    public void setMarca_id(int marca_id) {
        this.marca_id = marca_id;
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

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public HashMap getAtributos() { return atributosExtras;}

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

    public void addSpec(int atributo_id, String valor){
        atributosExtras.put(atributo_id, valor);
    }

    @Override
    public String toString() {
        return "Producto{" +
                ", marca='" + marca_id + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
