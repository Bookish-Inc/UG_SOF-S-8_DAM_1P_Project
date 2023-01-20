package com.example.bookish_bookshop_app.Categorias;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;


public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
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
    public void onResume() {
        super.onResume();
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT COUNT(*) AS CONTAR FROM Libro", null);
            if (c != null && c.moveToNext()) {
                @SuppressLint("Range") int contador = c.getInt(0);
                if(contador == 0){
                    InsertCategories(db);
                    InsertBooks(db);
                    insertCategoriasxLibro(dbw);
                }
            }
        }
        loadCategoria();
        loadLibros();
    }

    private void loadLibros() {
        LinearLayout layoutCategorias = view.findViewById(R.id.LayoutSugerencias);
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        layoutCategorias.removeAllViews();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT _id, titulo, imagen FROM Libro WHERE estado = 1", null);
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                int i = 0;
                do {
                    @SuppressLint("Range") int id = c.getInt(0);
                    @SuppressLint("Range") byte[] imagen = c.getBlob(2);
                    @SuppressLint("Range") String nombre = c.getString(1);
                    LinearLayout ly = new LinearLayout(view.getContext());
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 460);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(getContext());
                    TextView txt = new TextView(getContext());
                    txt.setBackgroundColor(Color.parseColor("#20B2AA"));
                    txt.setTextColor(Color.parseColor("white"));
                    img.setId(i);
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    txt.setText(nombre);
                    txt.setGravity(Gravity.CENTER_HORIZONTAL);
                    ly.setTag(id);
                    ly.addView(img, 280, 380);
                    ly.addView(txt, 280, 60);
                    ly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.replaceFragment(new DetailsFragment((Integer) view.getTag()));
                        }
                    });
                    layoutCategorias.addView(ly, lp);
                } while (c.moveToNext());
            }
            //fragmentTransaction.commit();
        }
    }

    private void loadCategoria() {
        LinearLayout layoutCategorias = view.findViewById(R.id.LayoutCategorias);
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
/*        if ("hola".equalsIgnoreCase("hoa")) {
            InsertCategories(dbw);
            InsertBooks(dbw);
        }*/
        //insertCategoriasxLibro(dbw);
        layoutCategorias.removeAllViews();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM categoria WHERE estado = 1", null);
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                int i = 0;
                do {
                    System.out.println("-============================================================-==============================================================");
                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("_id"));
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre")).toString();
                    LinearLayout ly = new LinearLayout(view.getContext());
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 460);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(getContext());
                    TextView txt = new TextView(getContext());
                    txt.setBackgroundColor(Color.parseColor("#20B2AA"));
                    txt.setTextColor(Color.parseColor("white"));
                    img.setId(i);
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    txt.setText(nombre);
                    txt.setGravity(Gravity.CENTER_HORIZONTAL);
                    ly.setTag(id);
                    ly.addView(img, 280, 380);
                    ly.addView(txt, 280, 60);
                    ly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (view.getTag() != null) {
                                MainActivity.replaceFragment(new SeccionCategoriaFragment((Integer) view.getTag()));
                            }
                        }
                    });
                    layoutCategorias.addView(ly, lp);
                } while (c.moveToNext());
            }
            //fragmentTransaction.commit();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    public void insertCategoriasxLibro(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("categoria_id", 1);
        cv.put("libro_id", 1);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv);
        ContentValues cv1 = new ContentValues();
        cv1.put("categoria_id", 2);
        cv1.put("libro_id", 2);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv1);
        ContentValues cv2 = new ContentValues();
        cv2.put("categoria_id", 3);
        cv2.put("libro_id", 3);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv2);
        ContentValues cv3 = new ContentValues();
        cv3.put("categoria_id", 4);
        cv3.put("libro_id", 4);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv3);
        ContentValues cv4 = new ContentValues();
        cv4.put("categoria_id", 4);
        cv4.put("libro_id", 4);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv4);

    }

    public void InsertCategories(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("nombre", "Ciencia Ficción");
        cv.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_cf)));
        cv.put("estado", 1);
        db.insert("categoria", null, cv);

        ContentValues cv1 = new ContentValues();
        cv1.put("_id", 2);
        cv1.put("nombre", "Clásicos");
        cv1.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_clasic)));
        cv1.put("estado", 1);
        db.insert("categoria", null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("_id", 3);
        cv2.put("nombre", "Histórica");
        cv2.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_historia)));
        cv2.put("estado", 1);
        db.insert("categoria", null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("_id", 4);
        cv3.put("nombre", "Juvenil");
        cv3.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_juvenil)));
        cv3.put("estado", 1);
        db.insert("categoria", null, cv3);

        ContentValues cv5 = new ContentValues();
        cv5.put("_id", 5);
        cv5.put("nombre", "Romance");
        cv5.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_romance)));
        cv5.put("estado", 1);
        db.insert("categoria", null, cv5);

        ContentValues cv6 = new ContentValues();
        cv6.put("_id", 6);
        cv6.put("nombre", "Terror");
        cv6.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_terror)));
        cv6.put("estado", 1);
        db.insert("categoria", null, cv6);
    }

    public void InsertBooks(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("titulo", "Ciencia Ficción");
        cv.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.milnochessinti)));
        cv.put("descripcion", "Tras un paréntesis en Rusia, para Sofia ha llegado el momento de poner orden a su vida sentimental. Ya no puede seguir huyendo de su pasado, de la soledad de su matrimonio, ni de la historia pasional y rota con Tancredi, y decide regresar a Roma. En un viaje a Sicilia para visitar a sus padres, descubrirá un secreto familiar que le afectará profundamente. Mientras tanto, Tancredi sigue todos sus pasos; es un hombre enamorado que nunca se ha rendido a la primera. Pero Sofía no confía en él… ¿Acabarán reencontrándose?");
        cv.put("precio", 9.59);
        cv.put("autor", "Federico Moccia");
        cv.put("num_paginas", 464);
        cv.put("fecha_publicacion", "2022-02-09");
        cv.put("cantidad", 5);
        cv.put("estado", 1);
        db.insert("libro", null, cv);

        ContentValues cv1 = new ContentValues();
        cv1.put("_id", 2);
        cv1.put("titulo", "Los que sobran");
        cv1.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.losquesobran)));
        cv1.put("descripcion", "Un análisis profundo, sobre el estado actual de la sociedad humana. Un libro que nos incita a cambiar lo que ahora tenemos, y, desde nosotros mismos, empezar a realizar la verdadera revolución pacífica. Recomendado del librero. Qué insólita paradoja define nuestra época, las primeras generaciones globales, las más educadas de la historia, no logran hallar el camino para realizarse. ¿Qué se los impide? ¿Qué poderosas fuerzas tienen el poder de cerrarle el camino a las mayorías y de decidir por ellas su destino? ¿Por qué parecemos avanzar imparables hacia cada vez más grandes catástrofes sin que podamos detener a quienes controlan el mundo? ¿Qué se halla en la raíz del profundo desasosiego y angustia que hoy nos invade en todos los rincones de nuestro frágil planeta?");
        cv1.put("precio", 10.59);
        cv1.put("autor", "Juan Carlos Flórez");
        cv1.put("num_paginas", 356);
        cv1.put("fecha_publicacion", "2021-05-24");
        cv1.put("cantidad", 15);
        cv1.put("estado", 1);
        db.insert("libro", null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("_id", 3);
        cv2.put("titulo", "La Invitada");
        cv2.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.lainvitada)));
        cv2.put("descripcion", "Helen y Nate dejan atrás la ciudad para mudarse al campo. Quieren construir la casa de sus sueños en un terreno rural a las afueras de un bosque. Cuando descubren que su magnífica propiedad tiene un pasado violento y oscuro, Helen, que era profesora de historia, quedará fascinada por la leyenda local de Hattie Breckenridge, que fue acusada de brujería hace más de cien años. Cuando se sumerge en la historia de Hattie y sus descendientes, descubrirá que ese linaje llega hasta la actualidad. Conforme avance la construcción de la casa, un peligro inesperado acechará a sus dueños y al resto de habitantes del lugar.");
        cv2.put("precio", 19.8);
        cv2.put("autor", "Jennifer McMahon");
        cv2.put("num_paginas", 496);
        cv2.put("fecha_publicacion", "2021-08-16");
        cv2.put("cantidad", 2);
        cv2.put("estado", 1);
        db.insert("libro", null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("_id", 4);
        cv3.put("titulo", "Una Herencia En Juego");
        cv3.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.unaherenciaenjuego)));
        cv3.put("descripcion", "Adéntrate en la historia de una cenicienta moderna, repleta de giros inesperados y misterios sin resolver. CUARENTA Y SEIS MIL DOSCIENTOS MILLONES DE DÓLARES. —Pensé. El corazón me retumbaba contra las costillas y tenía la boca tan seca como el papel de lija. Tobias Hawthorne tenía cuarenta y seis mil doscientos millones de dólares. Tobias Hawthorne no se lo había dejado todo a sus nietos. No se lo había dejado todo a sus hijas. Los números de esa ecuación no salían. Pero ni de lejos. Y mi cerebro se paró en seco. Me pitaban los oídos. ¿Por qué a mí? ¿Por qué era yo la principal heredera de su fortuna? Uno por uno, todos los presentes se volvieron para mirarme. Atrapada en un mundo de riqueza y privilegios, con el peligro acechando a cada paso, Avery tendrá que ir a por todas y jugar a ese juego... si quiere sobrevivir.");
        cv3.put("precio", 17.35);
        cv3.put("autor", "Jennifer Lynn Barnes");
        cv3.put("num_paginas", 448);
        cv3.put("fecha_publicacion", "2022-03-17");
        cv3.put("cantidad", 5);
        cv3.put("estado", 1);
        db.insert("libro", null, cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put("_id", 5);
        cv4.put("titulo", "¿A qué estás esperando?");
        cv4.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.aqueestasesperando)));
        cv4.put("descripcion", "Can Drogo, piloto e hijo del dueño de la empresa aeronáutica High Drogo, es un hombre alto, guapo, adinerado, simpático… Puede elegir a la mujer que desee, y aunque disfruta de esa «magia especial» con la que le ha dotado la vida, en su interior siente que todas lo aburren. Por su parte, Sonia Becher es la mayor de cuatro hermanas y la propietaria de una empresa de eventos y de una agencia de modelos. Can ve en ella a una chica divertida, atrevida, sin tabúes, con la que se puede hablar de todo, incluido de sexo, pero poco más, pues considera que no es su tipo. Hasta que un día las sonrisas y las miradas de la joven no van dirigidas a él, y eso, sin saber por qué, comienza a molestarlo. ¿En serio Sonia va a sonreír a otros hombres estando él delante? Sexo. Familia. Diversión. Locura. Todo esto es lo que vas a encontrar en ¿A qué estás esperando?, una novela que te hará ver que, en ocasiones, tu corazón se desboca por quien menos esperas sin que puedas frenarlo.");
        cv4.put("precio", 28.88);
        cv4.put("autor", "Megan Maxwell");
        cv4.put("num_paginas", 672);
        cv4.put("fecha_publicacion", "2020-10-29");
        cv4.put("cantidad", 4);
        cv4.put("estado", 1);
        db.insert("libro", null, cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put("_id", 6);
        cv5.put("titulo", "El Conde de Montecristo");
        cv5.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.elcondedemontecristo)));
        cv5.put("descripcion", "El Conde de Montecristo es una de las novelas de aventuras más famosas de todos los tiempos. Escrita por el autor francés Alexandre Dumas (1802-1870) y publicada en 1844. El Conde de Montecristo fue un éxito comercial al momento de su publicación, gracias en parte a la acogida de otra novela reciente de Dumas, Los Tres Mosqueteros (1844). La novela narra la vida de Edmundo Dantés desde que fue apresado injustamente en el castillo de If por un falso cargo de traición, hasta que regresa años después, convertido en el Conde de Montecristo, para ejercer su venganza sobre aquellos que destruyeron su vida. La historia trata temas como la búsqueda de la justicia, la ceguera aristocrática, la ambición, el honor, los cambios de una época tumultuosa en la historia de Francia, la naturaleza del odio y el peso de la maldad sobre el alma humana. Es una ventana a un momento turbio de manipulaciones sociales y políticas, de intriga cortesana y de fascinación por lo exótico y lo desconocido, y un inmortal ejemplo de lo que estaría dispuesto a hacer un hombre por rehacer su vida.");
        cv5.put("precio", 19.23);
        cv5.put("autor", "Alejandro Dumas");
        cv5.put("num_paginas", 1188);
        cv5.put("fecha_publicacion", "2020-08-06");
        cv5.put("cantidad", 12);
        cv5.put("estado", 1);
        db.insert("libro", null, cv5);
    }
}