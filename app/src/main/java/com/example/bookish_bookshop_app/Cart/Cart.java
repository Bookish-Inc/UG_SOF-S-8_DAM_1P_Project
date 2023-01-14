package com.example.bookish_bookshop_app.Cart;

import android.graphics.Bitmap;

public class Cart {
    private int id;
    private int id_libro;
    private String titulo;
    private double price;
    private Bitmap imagen;
    private int count;

    public Cart(int id, int id_libro, String titulo, double price, Bitmap imagen, int count) {
        this.id = id;
        this.id_libro = id_libro;
        this.titulo = titulo;
        this.price = price;
        this.imagen = imagen;
        this.count = count;
    }

    public void incrementAmount(){
        this.count++;
    }
    public void decrementAmount(){
        this.count--;
    }

    public int getId() {
        return id;
    }

    public int getId_libro() {
        return id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getPrice() {
        return price;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public int getCount() {
        return count;
    }
}
