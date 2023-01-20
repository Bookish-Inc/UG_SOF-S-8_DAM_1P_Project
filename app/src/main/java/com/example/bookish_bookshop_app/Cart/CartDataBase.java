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
        value.put("titulo", "Silencio");
        value.put("autor", "Larry");
        value.put("precio", 10);
        value.put("descripcion", "Ningunasdsdsd");
        value.put("categoria", "VideoJuegos");
        value.put("imagen", Imagen.serializar(context.getResources().getDrawable(R.drawable.book_silencio)));
        db.getWritableDatabase().insert(Tablas.Libro, null, value);

        ContentValues value1 = new ContentValues();
        value1.put("titulo", "Fastasma");
        value1.put("autor", "Jhon Titor");
        value1.put("precio", 15);
        value1.put("descripcion", "Habla sobre fantasmas");
        value1.put("categoria", "Terror");
        value1.put("imagen", Imagen.serializar(context.getResources().getDrawable(R.drawable.book_fantasma)));
        db.getWritableDatabase().insert(Tablas.Libro, null, value1);

        ContentValues value2 = new ContentValues();
        value2.put("titulo", "Alma");
        value2.put("autor", "Jhon Morrinson");
        value2.put("precio", 25);
        value2.put("descripcion", "Habla sobre Alma");
        value2.put("categoria", "Espiritus");
        value2.put("imagen", Imagen.serializar(context.getResources().getDrawable(R.drawable.book_alma)));
        db.getWritableDatabase().insert(Tablas.Libro, null, value2);

        ContentValues value3 = new ContentValues();
        value3.put("titulo", "Mito");
        value3.put("autor", "Vanessa Ronquillo");
        value3.put("precio", 90);
        value3.put("descripcion", "Habla sobre mitos");
        value3.put("categoria", "Sobre cosas de la vida");
        value3.put("imagen", Imagen.serializar(context.getResources().getDrawable(R.drawable.book_mito)));
        db.getWritableDatabase().insert(Tablas.Libro, null, value3);

        ContentValues value4 = new ContentValues();
        value4.put("titulo", "Soledad");
        value4.put("autor", "Elver Galarza");
        value4.put("precio", 30);
        value4.put("descripcion", "Sobre la soledad");
        value4.put("categoria", "No c bro disculpa");
        value4.put("imagen", Imagen.serializar(context.getResources().getDrawable(R.drawable.book_soledad)));
        db.getWritableDatabase().insert(Tablas.Libro, null, value4);
    }

    @SuppressLint("Recycle")
    public HashMap<Integer, Cart> getCarts() {
        HashMap<Integer, Cart> cartList = new HashMap<>();
        Cursor data = db.getReadableDatabase().rawQuery("SELECT c.id, c.id_libro, l.titulo, l.precio,l.imagen, c.cantidad FROM Libro l, Cart c where c.id_libro = l._id;", null);
        while (data != null && data.moveToNext()) {
            cartList.put(data.getInt(0), new Cart(data.getInt(0), data.getInt(1), data.getString(2), data.getDouble(3), Imagen.deserializar(data.getBlob(4)), data.getInt(5)));
        }
        return cartList;
    }

    public void insertCart(int idLibro) {
        ContentValues values = new ContentValues();
        values.put("id_libro", idLibro);
        values.put("cantidad", 1);
        db.getWritableDatabase().insert(Tablas.Cart, null, values);
    }

    public void updateAmount(Cart cart) {
        ContentValues cv = new ContentValues();
        cv.put("cantidad", cart.getCount());
        db.getWritableDatabase().update(Tablas.Cart, cv, "id = " + cart.getId(), null);
    }

    public void removeCart(Cart cart) {
        db.getWritableDatabase().delete(Tablas.Cart, "id=" + cart.getId(), null);
    }

}
