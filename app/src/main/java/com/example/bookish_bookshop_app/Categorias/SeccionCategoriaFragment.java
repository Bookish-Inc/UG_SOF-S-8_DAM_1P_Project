package com.example.bookish_bookshop_app.Categorias;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;


public class SeccionCategoriaFragment extends Fragment {
    private View view;
    private int id;

    public SeccionCategoriaFragment(int id) {
        // Required empty public constructor
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_seccion_categoria, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategory();
    }

    void loadCategory() {
        LinearLayout layoutEstante1 = view.findViewById(R.id.LinearLayout1);
        int id = this.id;
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        layoutEstante1.removeAllViews();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT l.imagen, l._id FROM Libro l, categoria_libro cl  WHERE cl.categoria_id =" + id + " and libro_id = l._id", null);
            if (c != null && c.moveToNext()) {
                do {
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") int id_libro = c.getInt(c.getColumnIndex("_id"));
                    LinearLayout ly = new LinearLayout(getContext());
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 360);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(getContext());
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    ly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.replaceFragment(new DetailsFragment(id_libro));
                        }
                    });
                    ly.addView(img, 250, 360);
                    layoutEstante1.addView(ly, lp);
                } while (c.moveToNext());
            }
        }
    }
}