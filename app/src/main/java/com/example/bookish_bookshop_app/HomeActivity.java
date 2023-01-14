package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout LayoutSugerencias = (LinearLayout) findViewById(R.id.LayoutSugerencias);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<5; i++){
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Boton "+String.format("%02d", i ));
            //Añadimos el botón a la botonera
            LayoutSugerencias.addView(button);
        }
    }

    public void onBtnLibro(View v) {
        Intent call_detail = new Intent(v.getContext(), DetailActivity.class);
        startActivity(call_detail);
    }
}