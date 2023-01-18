package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bookish_bookshop_app.Categorias.MyOpenHelperCatalog;
import com.example.bookish_bookshop_app.utils.Imagen;

public class Favoritos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        LinearLayout LayoutEstante1 = (LinearLayout) findViewById(R.id.LinearLayout1);
        LinearLayout LayoutEstante2 = (LinearLayout) findViewById(R.id.LinearLayout2);
        MyOpenHelperCatalog dbHelper = new MyOpenHelperCatalog(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        HayFavoritos(db, LayoutEstante1, LayoutEstante2);
    }

    public void HayFavoritos(SQLiteDatabase db, LinearLayout LayoutEstante1, LinearLayout LayoutEstante2) {
        if (db != null) {
            Cursor c = db.rawQuery("SELECT l._id, l.imagen FROM usuario_libro ul " +
                    "INNER JOIN libro l ON ul.libro_id = l._id " +
                    "WHERE usuario_id = " + 1, null);
            int i = 0;
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                do {
                    i++;
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") int id_libro = c.getInt(c.getColumnIndex("_id"));
                    LinearLayout ly = new LinearLayout(this);
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 360);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(this);
                    img.setId(i);
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    ly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBtnDetalle(view, id_libro);
                        }
                    });
                    ly.addView(img, 250, 360);
                    if (i > 3) {
                        LayoutEstante2.addView(ly, lp);
                    } else {
                        LayoutEstante1.addView(ly, lp);
                    }
                } while (c.moveToNext());
            } else {
                Toast.makeText(getApplicationContext(), "No tienes favoritos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBtnDetalle(View v, int id) {
        /*Intent call_detail = new Intent(v.getContext(), DetailActivity.class);
        call_detail.putExtra("id", id);
        startActivity(call_detail);*/
    }
}