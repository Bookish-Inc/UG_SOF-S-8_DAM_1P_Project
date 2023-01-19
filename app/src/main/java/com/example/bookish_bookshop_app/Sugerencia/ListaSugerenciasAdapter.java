package com.example.bookish_bookshop_app.Sugerencia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish_bookshop_app.R;

import java.util.ArrayList;

public class ListaSugerenciasAdapter extends RecyclerView.Adapter<ListaSugerenciasAdapter.SugerenciasViewHolder> {
    ArrayList<Sugerencia> listaSugerencias;
    public ListaSugerenciasAdapter (ArrayList<Sugerencia> listaSugerencias){
        this.listaSugerencias = listaSugerencias;
    }

    @NonNull
    @Override
    public ListaSugerenciasAdapter.SugerenciasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_sugerencia, null, false);
        return new SugerenciasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaSugerenciasAdapter.SugerenciasViewHolder holder, int position) {
        holder.viewTitulo.setText(listaSugerencias.get(position).getTitulo());
        holder.viewEditorial.setText(listaSugerencias.get(position).getEditorial());
        holder.viewAutor.setText(listaSugerencias.get(position).getNombreAutor()+" " +listaSugerencias.get(position).getApellidoAutor());
        holder.viewFecha.setText(listaSugerencias.get(position).getFechaPublicacion());
    }

    @Override
    public int getItemCount() {
        return listaSugerencias.size();
    }

    public class SugerenciasViewHolder extends RecyclerView.ViewHolder{

        TextView viewTitulo, viewEditorial, viewAutor, viewFecha;
        public SugerenciasViewHolder(@NonNull View itemView) {
            super(itemView);

            viewTitulo = itemView.findViewById(R.id.viewTitulo);
            viewEditorial = itemView.findViewById(R.id.viewEditorial);
            viewAutor = itemView.findViewById(R.id.viewAutor);
            viewFecha = itemView.findViewById(R.id.viewFecha);
        }
    }
}
