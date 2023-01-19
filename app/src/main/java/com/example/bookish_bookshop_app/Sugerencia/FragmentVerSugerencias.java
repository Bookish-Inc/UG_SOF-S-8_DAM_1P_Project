package com.example.bookish_bookshop_app.Sugerencia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookish_bookshop_app.R;

import java.util.ArrayList;

public class FragmentVerSugerencias extends Fragment {
    private View view;
    RecyclerView listaSugerencias;
    ArrayList<Sugerencia> listaArraySugerencias;


    public FragmentVerSugerencias() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_ver_sugerencias, container, false);
        listaSugerencias = view.findViewById(R.id.lista_sugerencias);
        listaSugerencias.setLayoutManager(new LinearLayoutManager(getContext()));
        DbSugerencias dbSugerencias = new DbSugerencias(getContext());

        listaArraySugerencias = new ArrayList<>();
        ListaSugerenciasAdapter adapter = new ListaSugerenciasAdapter(dbSugerencias.mostrarSugerencia());
        listaSugerencias.setAdapter(adapter);
        return view;
    }
}