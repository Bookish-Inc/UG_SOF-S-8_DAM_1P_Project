package com.example.bookish_bookshop_app.Categorias;

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

import com.example.bookish_bookshop_app.DetailActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;

public class SeccionCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_categoria);
        LinearLayout LayoutEstante1 = (LinearLayout) findViewById(R.id.LinearLayout1);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        int i = 0;
        MyOpenHelperCatalog dbHelper = new MyOpenHelperCatalog(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT l.imagen, l._id FROM libro l INNER JOIN categoria_libro cl ON l._id = cl.libro_id WHERE cl.categoria_id =" + id, null);
            if (c != null) {
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
                    LayoutEstante1.addView(ly, lp);
                } while (c.moveToNext());
            }
        }
    }

    public void onBtnRegresar(View v) {
        /*Intent call_home = new Intent(v.getContext(), HomeActivity.class);
        startActivity(call_home);*/
    }

    public void onBtnDetalle(View v, int id) {
        Intent call_detail = new Intent(v.getContext(), DetailActivity.class);
        call_detail.putExtra("id", id);
        startActivity(call_detail);
    }
}