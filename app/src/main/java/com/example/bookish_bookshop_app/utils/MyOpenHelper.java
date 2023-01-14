package com.example.bookish_bookshop_app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    /**
     * Aqui poner sus tablas que van a usar.
     */
    String CART = "create table Libro ( id INTEGER primary key autoincrement, titulo VARCHAR(255), autor VARCHAR(255), precio REAL, descripcion VARCHAR(255), categoria VARCHAR(255), imagen BLOB );";
    String LIBRO = "create table Cart (id INTEGER primary key autoincrement, id_libro INTEGER, cantidad INTEGER, foreign key (id_libro) references Libro);";
    private final String[] table = new String[]{CART, LIBRO};

    /**
     * De aqui para abajo no tocar
     *
     * @param context
     * @param version
     */
    public MyOpenHelper(Context context, int version) {
        super(context, "BookishDB", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String table : table) {
            db.execSQL(table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
