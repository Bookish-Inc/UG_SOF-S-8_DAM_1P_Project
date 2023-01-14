package com.example.bookish_bookshop_app.Cart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;

import java.util.HashMap;

/**
 * Clase
 */
public class CartDataBase {

    private Context context;
    private MyOpenHelper db;

    public CartDataBase(Context context, MyOpenHelper db) {
        this.context = context;
        this.db = db;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void insertLibro() {
        ContentValues value = new ContentValues();
        value.put("titulo", "San Andreas");
        value.put("autor", "Larry");
        value.put("precio", 2.5);
        value.put("descripcion", "Ningunasdsdsd");
        value.put("categoria", "VideoJuegos");
        value.put("imagen", Imagen.serializar(context.getResources().getDrawable(R.drawable.book_alma)));
        db.getWritableDatabase().insert(Tablas.Libro, null, value);
    }

    @SuppressLint("Recycle")
    public HashMap<Integer, Cart> getCarts() {
        HashMap<Integer, Cart> cartList = new HashMap<>();
        Cursor data = db.getReadableDatabase().rawQuery("SELECT c.id, c.id_libro, l.titulo, l.precio,l.imagen, c.cantidad FROM Libro l, Cart c where l.id = c.id;", null);
        while (data != null && data.moveToNext()) {
            cartList.put(data.getInt(0), new Cart(data.getInt(0), data.getInt(1), data.getString(2), data.getDouble(3), Imagen.deserializar(data.getBlob(4)), data.getInt(5)));
        }
        return cartList;
    }

    public void insertCart(int idLibro) {
        ContentValues values = new ContentValues();
        values.put("id_libro", 2);
        values.put("cantidad", 3);
        db.getWritableDatabase().insert(Tablas.Cart, null, values);
    }

}
