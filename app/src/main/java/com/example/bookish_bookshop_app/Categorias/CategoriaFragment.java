package com.example.bookish_bookshop_app.Categorias;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CategoriaFragment extends Fragment {

    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String IMAGEN = "imagen";

    private int id;
    private String nombre;
    private byte[] imagen;
    Bitmap imgb;
    TextView TxtNombre;
    ImageView ImgCateg;

    public CategoriaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            setId(getArguments().getInt(ID));
            setNombre(bundle.getString(NOMBRE));
            setImagen(bundle.getByteArray(IMAGEN));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TxtNombre = (TextView) view.findViewById(R.id.txt_categoriaNom);
        ImgCateg = (ImageView) view.findViewById(R.id.imgCategoria);
        TxtNombre.setText(getNombre());
        imgb = Imagen.deserializar(getImagen());
        ImgCateg.setImageBitmap(imgb);
    }

    public int get_Id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}