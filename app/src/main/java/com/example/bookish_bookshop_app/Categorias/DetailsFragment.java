package com.example.bookish_bookshop_app.Categorias;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookish_bookshop_app.Cart.CartDataBase;
import com.example.bookish_bookshop_app.Categorias.MyOpenHelperCatalog;
import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.Arrays;


public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int id_libro;
    private int id_categoria;
    private CheckBox checkBox;
    private View view;
    private int idFinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment(int idFinder) {
        this.idFinder = idFinder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_details, container, false);
        CheckBox checkBox = view.findViewById(R.id.chb_favorito);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnCheckedStar();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView TxtFechapubli = view.findViewById(R.id.txt_fechapubli);
        TextView TxtAutor = view.findViewById(R.id.txt_autor);
        TextView TxtPrecio = view.findViewById(R.id.txt_precio);
        TextView TxtSinopsis = view.findViewById(R.id.txt_sinopsis);
        ImageView ImgPortada = view.findViewById(R.id.img_portada);
        TextView TxtCategoria = view.findViewById(R.id.txt_categoria);
        checkBox = view.findViewById(R.id.chb_favorito);
        Button btnAdd = view.findViewById(R.id.Btn_Agregar);
        btnAdd.setOnClickListener(this::addCart);

        MyOpenHelper dbHelper = new MyOpenHelper(view.getContext(), 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        ConsultaLibro(db, this.idFinder, TxtFechapubli, TxtAutor, TxtPrecio, TxtSinopsis, ImgPortada, TxtCategoria);
        EsFavorito(db, this.idFinder);
    }

    private void addCart(View view) {
        MyOpenHelper db = new MyOpenHelper(getContext(), 1);
        CartDataBase cartDataBase = new CartDataBase(getContext(), db);
        cartDataBase.insertCart(idFinder);
        Toast.makeText(view.getContext(), "Se ha agregado un elemento", Toast.LENGTH_SHORT).show();
    }

    public void OnCheckedStar() {
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(), 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = MainActivity.getIdUser();
        if (checkBox.isChecked()) {
            if (db != null) {
                ContentValues cv = new ContentValues();
                cv.put("usuario_id", id);
                cv.put("libro_id", id_libro);
                db.insert("usuario_libro", null, cv);
                Toast.makeText(view.getContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (db != null) {
                db.delete("usuario_libro", "libro_id=" + id_libro + " AND usuario_id=" + id, null);
                Toast.makeText(view.getContext(), "Se quitó de favoritos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void EsFavorito(SQLiteDatabase db, int id) {
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM usuario_libro " +
                    "WHERE libro_id = " + id, null);
            if (c != null && c.moveToNext()) {
                checkBox.setChecked(true);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void ConsultaLibro(SQLiteDatabase db, int id, TextView TxtFechapubli, TextView TxtAutor,
                              TextView TxtPrecio, TextView TxtSinopsis, ImageView ImgPortada, TextView TxtCategoria) {
        if (db != null) {
            Cursor c = db.rawQuery("SELECT l._id, l.fecha_publicacion, l.titulo, l.imagen, l.descripcion, l.precio, l.autor, " +
                    "l.num_paginas, l.cantidad, c._id AS id_categoria, GROUP_CONCAT(c.nombre, '/ ') AS categoria " +
                    "FROM Libro l " +
                    ", categoria c " +
                    "WHERE l._id = " + id + " and l._id = c._id;", null);
            if (c != null) {
                c.moveToFirst();
                int libro_id = c.getInt(0);
                int categoria_id = c.getInt(9);
                String fecha_publi = c.getString(1);
                String titulo = c.getString(2);
                byte[] imagen = c.getBlob(3);
                String descripcion = c.getString(4);
                @SuppressLint("Range") double precio = c.getDouble(5);
                @SuppressLint("Range") String autor = c.getString(6);
                @SuppressLint("Range") int num_paginas = c.getInt(7);
                @SuppressLint("Range") int cantidad = c.getInt(8);
                @SuppressLint("Range") String categorias = c.getString(10);
                System.out.println(fecha_publi);
                if (imagen == null || imagen.length == 0) {
                    return;
                }
                id_libro = libro_id;
                id_categoria = categoria_id;
                TxtFechapubli.setText("Fecha publicación: " + fecha_publi);
                TxtSinopsis.setText(descripcion);
                Bitmap imgb = Imagen.deserializar(imagen);
                ImgPortada.setImageBitmap(imgb);
                TxtPrecio.setText("$" + precio);
                TxtAutor.setText("Autor: " + autor);
                TxtCategoria.setText(categorias);
            }
        }
    }
}