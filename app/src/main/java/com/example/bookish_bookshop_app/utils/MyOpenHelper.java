package com.example.bookish_bookshop_app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    /**
     * Aqui poner sus tablas que van a usar.
     */

    // region Module_1
    String CREDENTIAL = "CREATE TABLE Credential (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username VARCHAR(30), " +
            "password VARCHAR(30) " +
            ")";
    String USER = "CREATE TABLE User (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name VARCHAR(30), " +
            "lastname VARCHAR(30), " +
            "phone VARCHAR(10), " +
            "email VARCHAR(30), " +
            "genre VARCHAR(10), " +
            "occupation VARCHAR(30), " +
            "id_credential INTEGER, FOREIGN KEY (id_credential) references Credential" +
            ")";
    // endregion
    String CART = "create table Cart (id INTEGER primary key autoincrement, id_libro INTEGER, cantidad INTEGER, foreign key (id_libro) references Libro);";
    String SUGERENCIA = "CREATE TABLE Sugerencia (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR(255), edicion VARCHAR(255),editorial VARCHAR(255), cubierta VARCHAR(255), nombreAutor VARCHAR(255), apellidoAutor VARCHAR(255), comentarios VARCHAR(255))";
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
            "imagen BLOB," +
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
    private final String[] table = new String[]{CREDENTIAL, USER, CART, SUGERENCIA, TABLE_LIBRO, TABLE_CATEGORIA, TABLE_CATEGORIA_LIBRO, TABLE_USUARIO_FAVORITOS, sql_categoria_libro};

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
