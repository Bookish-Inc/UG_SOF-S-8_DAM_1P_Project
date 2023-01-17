package com.example.bookish_bookshop_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;

public class DbSugerencias extends MyOpenHelper{
    Context context;

    public DbSugerencias(Context context, int version) {
        super(context, version);
    }


    public long insertarSugerencia(String titulo, String edicion, String editorial, String cubierta,
                                   String fechaPublicacion, String nombreAutor, String apellidoAutor, String comentarios){

        long id =0;
        try {
            MyOpenHelper dbHelper = new MyOpenHelper(context,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("edicion", edicion);
            values.put("editorial", editorial);
            values.put("cubierta", cubierta);
            values.put("fechaPublicacion", fechaPublicacion);
            values.put("nombreAutor", nombreAutor);
            values.put("apellidoAutor", apellidoAutor);
            values.put("comentarios", comentarios);

            id = db.insert(Tablas.Sugerencia, null, values);

        }catch ( Exception ex){
            ex.toString();
        }
        return id;
    }
}
