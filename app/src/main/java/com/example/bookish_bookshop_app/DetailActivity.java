package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookish_bookshop_app.utils.Imagen;

import org.w3c.dom.Text;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView TxtNumpaginas = (TextView)findViewById(R.id.txt_numpag);
        TextView TxtFechapubli = (TextView)findViewById(R.id.txt_fechapubli);
        TextView TxtAutor = (TextView)findViewById(R.id.txt_autor);
        TextView TxtPrecio = (TextView)findViewById(R.id.txt_precio);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        MyOpenHelperCatalog dbHelper = new MyOpenHelperCatalog(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM libro WHERE _id =" + id, null);
            if (c != null) {
                c.moveToFirst();
                do {
                   // @SuppressLint("Range") Date fecha_publi = c.getString(c.getColumnIndex("fecha_publi"));
                    @SuppressLint("Range") double precio = c.getDouble(c.getColumnIndex("precio"));
                    @SuppressLint("Range") String autor = c.getString(c.getColumnIndex("autor"));
                    @SuppressLint("Range") int num_paginas = c.getInt(c.getColumnIndex("num_paginas"));
                    LinearLayout ly = new LinearLayout(this);
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 360);
                    lp.setMargins(20, 20, 20, 20);

                } while (c.moveToNext());
            }
        }
    }
}