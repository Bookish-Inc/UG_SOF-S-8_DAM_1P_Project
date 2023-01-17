package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookish_bookshop_app.utils.Imagen;

public class DetailActivity extends AppCompatActivity {

    private int id_libro;
    private int id_categoria;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       // TextView TxtNumpaginas = (TextView)findViewById(R.id.txt_numpaginas);
        TextView TxtFechapubli = (TextView)findViewById(R.id.txt_fechapubli);
        TextView TxtAutor = (TextView)findViewById(R.id.txt_autor);
        TextView TxtPrecio = (TextView)findViewById(R.id.txt_precio);
        TextView TxtSinopsis = (TextView)findViewById(R.id.txt_sinopsis);
        ImageView ImgPortada = (ImageView)findViewById(R.id.img_portada);
        TextView TxtCategoria = (TextView)findViewById(R.id.txt_categoria);
        checkBox = (CheckBox)findViewById(R.id.chb_favorito);
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        MyOpenHelperCatalog dbHelper = new MyOpenHelperCatalog(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        ConsultaLibro(db, id, TxtFechapubli, TxtAutor, TxtPrecio, TxtSinopsis, ImgPortada, TxtCategoria);
        EsFavorito(db, id);
    }

    public void OnCheckedStar(View v){
        MyOpenHelperCatalog dbHelper = new MyOpenHelperCatalog(v.getContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(checkBox.isChecked()){
            if (db != null) {
                ContentValues cv = new ContentValues();
                cv.put("usuario_id", 1); // ESPERANDING DATOS DE RENÁN MODULO USUARIOS
                cv.put("libro_id", id_libro);
                db.insert("usuario_libro", null, cv);
                Toast.makeText(getApplicationContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (db != null) {
                db.delete("usuario_libro", "libro_id=" + id_libro + " AND usuario_id=" +1, null);
                Toast.makeText(getApplicationContext(), "Se quitó de favoritos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void EsFavorito(SQLiteDatabase db, int id){
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM usuario_libro " +
                    "WHERE libro_id = " + id, null);
            if (c != null && c.moveToNext()) {
                checkBox.setChecked(true);
                Toast.makeText(getApplicationContext(), "Cambia estado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void ConsultaLibro(SQLiteDatabase db, int id, TextView TxtFechapubli, TextView TxtAutor,
    TextView TxtPrecio, TextView TxtSinopsis, ImageView ImgPortada, TextView TxtCategoria )
    {
        if (db != null) {
            Cursor c = db.rawQuery("SELECT l._id, l.fecha_publicacion, l.titulo, l.imagen, l.descripcion, l.precio, l.autor, " +
                    "l.num_paginas, l.cantidad, c._id AS id_categoria, GROUP_CONCAT(c.nombre, '/ ') AS categoria " +
                    "FROM libro l " +
                    "INNER JOIN categoria_libro cl ON l._id = cl.libro_id " +
                    "INNER JOIN categoria c ON cl.categoria_id = c._id " +
                    "WHERE l._id = " + id, null);
            if (c != null) {
                c.moveToFirst();
                do {
                    @SuppressLint("Range") int libro_id = c.getInt(c.getColumnIndex("_id"));
                    @SuppressLint("Range") int categoria_id = c.getInt(c.getColumnIndex("id_categoria"));
                    @SuppressLint("Range") String fecha_publi = c.getString(c.getColumnIndex("fecha_publicacion"));
                    @SuppressLint("Range") String titulo = c.getString(c.getColumnIndex("titulo"));
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") String descripcion = c.getString(c.getColumnIndex("descripcion"));
                    @SuppressLint("Range") double precio = c.getDouble(c.getColumnIndex("precio"));
                    @SuppressLint("Range") String autor = c.getString(c.getColumnIndex("autor"));
                    @SuppressLint("Range") int num_paginas = c.getInt(c.getColumnIndex("num_paginas"));
                    @SuppressLint("Range") int cantidad = c.getInt(c.getColumnIndex("cantidad"));
                    @SuppressLint("Range") String categorias = c.getString(c.getColumnIndex("categoria"));
                    id_libro = libro_id;
                    id_categoria = categoria_id;
                    TxtFechapubli.setText("Fecha publicación: "+fecha_publi);
                    TxtSinopsis.setText(descripcion);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    ImgPortada.setImageBitmap(imgb);
                    TxtPrecio.setText("$"+precio);
                    TxtAutor.setText("Autor: "+autor);
                    //        TxtNumpaginas.setText(num_paginas);
                    TxtCategoria.setText(categorias);
                } while (c.moveToNext());
            }
        }
    }

    public void onBtnRegresar(View v) {
        Intent call_catalog = new Intent(v.getContext(), SeccionCategoria.class);
        call_catalog.putExtra("id", id_categoria);
        startActivity(call_catalog);
    }
}