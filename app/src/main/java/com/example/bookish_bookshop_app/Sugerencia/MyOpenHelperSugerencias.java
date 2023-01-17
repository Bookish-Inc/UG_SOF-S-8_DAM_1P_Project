package com.example.bookish_bookshop_app.Sugerencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelperSugerencias extends SQLiteOpenHelper {
    private static final String TABLE_CREATE = "CREATE TABLE sugerencias(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "titulo TEXT, " +
            "edicion TEXT, " +
            "editorial TEXT, " +
            "cubierta TEXT, " +
            "fechaPublicacion TEXT, " +
            "nombreAutor TEXT, " +
            "apellidoAutor TEXT, " +
            "comentarios TEXT)";
    private static final String TABLE_DROP = "DROP TABLE sugerencias";
    private static final String DB_NAME = "NetCore.sqlite";
    private static final int DB_VERSION = 1;

    public MyOpenHelperSugerencias(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(TABLE_DROP);
        onCreate(db);
    }
}
