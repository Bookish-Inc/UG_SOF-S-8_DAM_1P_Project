package com.example.bookish_bookshop_app.Categorias;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bookish_bookshop_app.Book.Book;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.Tablas;

public class MyOpenHelperCatalog extends SQLiteOpenHelper {
    //esto se debe ejecutar sola una vez
    private static final String TABLE_LIBRO = "CREATE TABLE IF NOT EXISTS Libro(" +
            "_id INTEGER PRIMARY KEY, " +
            "titulo TEXT, " +
            "imagen BLOB, " +
            "descripcion TEXT, " +
            "precio FLOAT, " +
            "autor TEXT, " +
            "num_paginas INTEGER," +
            "fecha_publicacion DATE," +
            "cantidad INTEGER," +
            "estado BIT" +
            ")";
    private static final String TABLE_CATEGORIA = "CREATE TABLE IF NOT EXISTS categoria(" +
            "_id INTEGER PRIMARY KEY, " +
            "nombre TEXT, " +
            "imagen BLOB,"+
            "estado BIT" +
            ")";

    private static final String TABLE_CATEGORIA_LIBRO = "CREATE TABLE IF NOT EXISTS categoria_libro(" +
            "_id INTEGER PRIMARY KEY, " +
            "categoria_id INTEGER, " +
            "libro_id INTEGER" +
            ")";
    private static final String TABLE_USUARIO_FAVORITOS = "CREATE TABLE IF NOT EXISTS usuario_libro(" +
            "_id INTEGER PRIMARY KEY, " +
            "usuario_id INTEGER, " +
            "libro_id INTEGER" +
            ")";
    String sql_categoria_libro =
            "INSERT OR IGNORE INTO categoria_libro (_id, categoria_id, libro_id) VALUES (1,4,1),(2,3,2),(3,1,3),(4,4,4),(5,6,4),(6,5,5),(7,2,6)";
    private static final int DB_VERSION = 1;

    public MyOpenHelperCatalog(Context context) {
        super(context, "BookishDB", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_LIBRO);
        db.execSQL(TABLE_CATEGORIA);
        db.execSQL(TABLE_CATEGORIA_LIBRO);
        db.execSQL(TABLE_USUARIO_FAVORITOS);
        db.execSQL(sql_categoria_libro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
