package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookish_bookshop_app.Categorias.MyOpenHelperCatalog;
import com.example.bookish_bookshop_app.Categorias.SeccionCategoria;
import com.example.bookish_bookshop_app.utils.Imagen;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout LayoutSugerencias = (LinearLayout) findViewById(R.id.LayoutSugerencias);
        LinearLayout LayoutCategorias = (LinearLayout) findViewById(R.id.LayoutCategorias);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
       // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       /* //Creamos los botones en bucle
        for (int i=0; i<5; i++){
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Boton "+String.format("%02d", i ));
            //Añadimos el botón a la botonera
            LayoutSugerencias.addView(button);
        }*/

        MyOpenHelperCatalog dbHelper = new MyOpenHelperCatalog(this);
        final SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        InsertCategories(dbw);
        InsertBooks(dbw);
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM categoria WHERE estado = 1", null);
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                int i = 0;
                do {
                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("_id"));
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre")).toString();
                    /* Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("nombre", nombre);
                    bundle.putByteArray("imagen", imagen);
                    CategoriaFragment cfrag = new CategoriaFragment();
                    cfrag.setArguments(bundle);
                    fragmentTransaction.add(R.id.fragmentCategoria, cfrag, "categorieFrag"+i);
                    fragmentTransaction.commit();*/
                    LinearLayout ly = new LinearLayout(this);
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280,460);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(this);
                    TextView txt = new TextView(this);
                    txt.setBackgroundColor(Color.parseColor("#20B2AA"));
                    txt.setTextColor(Color.parseColor("white"));
                    img.setId(i);
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    txt.setText(nombre);
                    ly.addView(img, 280, 380);
                    ly.addView(txt, 280, 60);
                    LayoutCategorias.addView(ly, lp);
                } while (c.moveToNext());
            }
            //fragmentTransaction.commit();
            c.close();
            db.close();
        }
    }
    public void onBtnFav(View v) {
        Intent call_detail = new Intent(v.getContext(), Favoritos.class);
        startActivity(call_detail);
    }
    public void onBtnLibro(View v) {
        Intent call_detail = new Intent(v.getContext(), DetailActivity.class);
        startActivity(call_detail);
    }

    public void onBtnCategoria(View v) {
        Intent call_categoria = new Intent(v.getContext(), SeccionCategoria.class);
        switch(v.getId()){
            case R.id.btn_cienciaficcion:
                call_categoria.putExtra("id", 1);
                break;
            case R.id.btn_clasico:
                call_categoria.putExtra("id", 2);
                break;
            case R.id.btn_historia:
                call_categoria.putExtra("id", 3);
                break;
            case R.id.btn_juvenil:
                call_categoria.putExtra("id", 4);
                break;
            case R.id.btn_romance:
                call_categoria.putExtra("id", 5);
                break;
            case R.id.btn_terror:
                call_categoria.putExtra("id", 6);
                break;
        }
        startActivity(call_categoria);
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
        cv.put("precio",9.59);
        cv.put("autor","Federico Moccia");
        cv.put("num_paginas",464);
        cv.put("fecha_publicacion","2022-02-09");
        cv.put("cantidad",5);
        cv.put("estado",1);
        db.insert("libro", null, cv);

        ContentValues cv1 = new ContentValues();
        cv1.put("_id", 2);
        cv1.put("titulo", "Los que sobran");
        cv1.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.losquesobran)));
        cv1.put("descripcion", "Un análisis profundo, sobre el estado actual de la sociedad humana. Un libro que nos incita a cambiar lo que ahora tenemos, y, desde nosotros mismos, empezar a realizar la verdadera revolución pacífica. Recomendado del librero. Qué insólita paradoja define nuestra época, las primeras generaciones globales, las más educadas de la historia, no logran hallar el camino para realizarse. ¿Qué se los impide? ¿Qué poderosas fuerzas tienen el poder de cerrarle el camino a las mayorías y de decidir por ellas su destino? ¿Por qué parecemos avanzar imparables hacia cada vez más grandes catástrofes sin que podamos detener a quienes controlan el mundo? ¿Qué se halla en la raíz del profundo desasosiego y angustia que hoy nos invade en todos los rincones de nuestro frágil planeta?");
        cv1.put("precio",10.59);
        cv1.put("autor","Juan Carlos Flórez");
        cv1.put("num_paginas",356);
        cv1.put("fecha_publicacion","2021-05-24");
        cv1.put("cantidad",15);
        cv1.put("estado",1);
        db.insert("libro", null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("_id", 3);
        cv2.put("titulo", "La Invitada");
        cv2.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.lainvitada)));
        cv2.put("descripcion", "Helen y Nate dejan atrás la ciudad para mudarse al campo. Quieren construir la casa de sus sueños en un terreno rural a las afueras de un bosque. Cuando descubren que su magnífica propiedad tiene un pasado violento y oscuro, Helen, que era profesora de historia, quedará fascinada por la leyenda local de Hattie Breckenridge, que fue acusada de brujería hace más de cien años. Cuando se sumerge en la historia de Hattie y sus descendientes, descubrirá que ese linaje llega hasta la actualidad. Conforme avance la construcción de la casa, un peligro inesperado acechará a sus dueños y al resto de habitantes del lugar.");
        cv2.put("precio",19.8);
        cv2.put("autor","Jennifer McMahon");
        cv2.put("num_paginas",496);
        cv2.put("fecha_publicacion","2021-08-16");
        cv2.put("cantidad",2);
        cv2.put("estado",1);
        db.insert("libro", null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("_id", 4);
        cv3.put("titulo", "Una Herencia En Juego");
        cv3.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.unaherenciaenjuego)));
        cv3.put("descripcion", "Adéntrate en la historia de una cenicienta moderna, repleta de giros inesperados y misterios sin resolver. CUARENTA Y SEIS MIL DOSCIENTOS MILLONES DE DÓLARES. —Pensé. El corazón me retumbaba contra las costillas y tenía la boca tan seca como el papel de lija. Tobias Hawthorne tenía cuarenta y seis mil doscientos millones de dólares. Tobias Hawthorne no se lo había dejado todo a sus nietos. No se lo había dejado todo a sus hijas. Los números de esa ecuación no salían. Pero ni de lejos. Y mi cerebro se paró en seco. Me pitaban los oídos. ¿Por qué a mí? ¿Por qué era yo la principal heredera de su fortuna? Uno por uno, todos los presentes se volvieron para mirarme. Atrapada en un mundo de riqueza y privilegios, con el peligro acechando a cada paso, Avery tendrá que ir a por todas y jugar a ese juego... si quiere sobrevivir.");
        cv3.put("precio",17.35);
        cv3.put("autor","Jennifer Lynn Barnes");
        cv3.put("num_paginas",448);
        cv3.put("fecha_publicacion","2022-03-17");
        cv3.put("cantidad",5);
        cv3.put("estado",1);
        db.insert("libro", null, cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put("_id", 5);
        cv4.put("titulo", "¿A qué estás esperando?");
        cv4.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.aqueestasesperando)));
        cv4.put("descripcion", "Can Drogo, piloto e hijo del dueño de la empresa aeronáutica High Drogo, es un hombre alto, guapo, adinerado, simpático… Puede elegir a la mujer que desee, y aunque disfruta de esa «magia especial» con la que le ha dotado la vida, en su interior siente que todas lo aburren. Por su parte, Sonia Becher es la mayor de cuatro hermanas y la propietaria de una empresa de eventos y de una agencia de modelos. Can ve en ella a una chica divertida, atrevida, sin tabúes, con la que se puede hablar de todo, incluido de sexo, pero poco más, pues considera que no es su tipo. Hasta que un día las sonrisas y las miradas de la joven no van dirigidas a él, y eso, sin saber por qué, comienza a molestarlo. ¿En serio Sonia va a sonreír a otros hombres estando él delante? Sexo. Familia. Diversión. Locura. Todo esto es lo que vas a encontrar en ¿A qué estás esperando?, una novela que te hará ver que, en ocasiones, tu corazón se desboca por quien menos esperas sin que puedas frenarlo.");
        cv4.put("precio",28.88);
        cv4.put("autor","Megan Maxwell");
        cv4.put("num_paginas",672);
        cv4.put("fecha_publicacion","2020-10-29");
        cv4.put("cantidad",4);
        cv4.put("estado",1);
        db.insert("libro", null, cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put("_id", 6);
        cv5.put("titulo", "El Conde de Montecristo");
        cv5.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.elcondedemontecristo)));
        cv5.put("descripcion", "El Conde de Montecristo es una de las novelas de aventuras más famosas de todos los tiempos. Escrita por el autor francés Alexandre Dumas (1802-1870) y publicada en 1844. El Conde de Montecristo fue un éxito comercial al momento de su publicación, gracias en parte a la acogida de otra novela reciente de Dumas, Los Tres Mosqueteros (1844). La novela narra la vida de Edmundo Dantés desde que fue apresado injustamente en el castillo de If por un falso cargo de traición, hasta que regresa años después, convertido en el Conde de Montecristo, para ejercer su venganza sobre aquellos que destruyeron su vida. La historia trata temas como la búsqueda de la justicia, la ceguera aristocrática, la ambición, el honor, los cambios de una época tumultuosa en la historia de Francia, la naturaleza del odio y el peso de la maldad sobre el alma humana. Es una ventana a un momento turbio de manipulaciones sociales y políticas, de intriga cortesana y de fascinación por lo exótico y lo desconocido, y un inmortal ejemplo de lo que estaría dispuesto a hacer un hombre por rehacer su vida.");
        cv5.put("precio",19.23);
        cv5.put("autor","Alejandro Dumas");
        cv5.put("num_paginas",1188);
        cv5.put("fecha_publicacion","2020-08-06");
        cv5.put("cantidad",12);
        cv5.put("estado",1);
        db.insert("libro", null, cv5);
    }

}