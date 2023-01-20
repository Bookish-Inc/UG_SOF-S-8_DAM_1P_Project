package com.example.bookish_bookshop_app.Sugerencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;

import java.util.ArrayList;

public class DbSugerencias {
    Context context;
    MyOpenHelper dbHelper;

    public DbSugerencias(Context context) {
        this.context = context;
    }

    public DbSugerencias(Context context, MyOpenHelper db) {
        this.context = context;
        this.dbHelper = db;
    }


    public long insertarSugerencia(String titulo, String edicion, String editorial, String cubierta,
                                   String nombreAutor, String apellidoAutor, String comentarios) {

        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("edicion", edicion);
            values.put("editorial", editorial);
            values.put("cubierta", cubierta);
            values.put("nombreAutor", nombreAutor);
            values.put("apellidoAutor", apellidoAutor);
            values.put("comentarios", comentarios);

            id = dbHelper.getWritableDatabase().insert(Tablas.Sugerencia, null, values);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<Sugerencia> mostrarSugerencia() {

        dbHelper = new MyOpenHelper(context, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Sugerencia> listaSugerencias = new ArrayList<>();
        Sugerencia sugerencia = null;
        Cursor cursorSugerencias = null;

        cursorSugerencias = db.rawQuery("SELECT * FROM " + Tablas.Sugerencia, null);

        if (cursorSugerencias.moveToFirst()) {
            do {
                sugerencia = new Sugerencia();
                sugerencia.setId(cursorSugerencias.getInt(0));
                sugerencia.setTitulo(cursorSugerencias.getString(1));
                sugerencia.setEdicion(cursorSugerencias.getString(2));
                sugerencia.setEditorial(cursorSugerencias.getString(3));
                sugerencia.setCubierta(cursorSugerencias.getString(4));
                sugerencia.setNombreAutor(cursorSugerencias.getString(5));
                sugerencia.setApellidoAutor(cursorSugerencias.getString(6));
                sugerencia.setComentarios(cursorSugerencias.getString(7));

                listaSugerencias.add(sugerencia);
            } while (cursorSugerencias.moveToNext());
        }

        cursorSugerencias.close();

        return listaSugerencias;
    }
}
