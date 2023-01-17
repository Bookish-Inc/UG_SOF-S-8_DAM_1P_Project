package com.example.bookish_bookshop_app.Sugerencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;

public class DbSugerencias {
    Context context;
    MyOpenHelper db;

    public DbSugerencias(Context context, MyOpenHelper db) {
        this.context = context;
        this.db = db;
    }


    public long insertarSugerencia(String titulo, String edicion, String editorial, String cubierta,
                                   String fechaPublicacion, String nombreAutor, String apellidoAutor, String comentarios) {

        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("titulo", "titulo");
            values.put("edicion", "edicion");
            values.put("editorial", "editorial");
            values.put("cubierta", "cubierta");
            values.put("fechaPublicacion", "fechaPublicacion");
            values.put("nombreAutor", "nombreAutor");
            values.put("apellidoAutor", "apellidoAutor");
            values.put("comentarios", "comentarios");

            id = db.getWritableDatabase().insert(Tablas.Sugerencia, null, values);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
