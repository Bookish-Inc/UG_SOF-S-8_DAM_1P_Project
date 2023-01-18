package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bookish_bookshop_app.Sugerencia.DbSugerencias;
import com.example.bookish_bookshop_app.Sugerencia.ListaSugerenciasAdapter;
import com.example.bookish_bookshop_app.Sugerencia.Sugerencia;

import java.util.ArrayList;

public class VisualizarSugerencias extends AppCompatActivity {

    RecyclerView listaSugerencias;
    ArrayList<Sugerencia> listaArraySugerencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_sugerencias);
        listaSugerencias = findViewById(R.id.lista_sugerencias);
        listaSugerencias.setLayoutManager(new LinearLayoutManager(this));
        DbSugerencias dbSugerencias = new DbSugerencias(VisualizarSugerencias.this);

        listaArraySugerencias = new ArrayList<>();
        ListaSugerenciasAdapter adapter = new ListaSugerenciasAdapter(dbSugerencias.mostrarSugerencia());
        listaSugerencias.setAdapter(adapter);
    }
}