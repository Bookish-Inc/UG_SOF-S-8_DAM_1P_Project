package com.example.bookish_bookshop_app.Categorias;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bookish_bookshop_app.Categorias.DetailsFragment;
import com.example.bookish_bookshop_app.Categorias.MyOpenHelperCatalog;
import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;


public class favoritosFragment extends Fragment {

    private View view;

    public favoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayout LayoutEstante1 = view.findViewById(R.id.LinearLayout1);
        LinearLayout LayoutEstante2 = view.findViewById(R.id.LinearLayout2);
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        HayFavoritos(db, LayoutEstante1, LayoutEstante2);
    }

    public void HayFavoritos(SQLiteDatabase db, LinearLayout LayoutEstante1, LinearLayout LayoutEstante2) {
        int id = MainActivity.getIdUser();
        if (db != null && id != 0) {
            Cursor c = db.rawQuery("SELECT l._id, l.imagen FROM usuario_libro ul " +
                    "INNER JOIN libro l ON ul.libro_id = l._id " +
                    "WHERE usuario_id = " + id, null);
            int i = 0;
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                do {
                    i++;
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") int id_libro = c.getInt(c.getColumnIndex("_id"));
                    LinearLayout ly = new LinearLayout(getContext());
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 360);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(getContext());
                    img.setId(i);
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
                    if (i > 3) {
                        LayoutEstante2.addView(ly, lp);
                    } else {
                        LayoutEstante1.addView(ly, lp);
                    }
                } while (c.moveToNext());
            } else {
                Toast.makeText(getContext(), "No tienes favoritos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}