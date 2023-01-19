package com.example.bookish_bookshop_app.Sugerencia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.Calendar;


public class FragmentSugerencia extends Fragment {

    private String mSpinnerCubierta;
    EditText txtTitulo, txtEdicion, txtEditorial, txtNombreAutor, txtApellido, dtpFechaPublicacion, txtComentarios;
    Button btn_Registrar, btn_Ver;
    Spinner spnrCubierta;
    DbSugerencias dbSugerencias;
    private MyOpenHelper database;
    private View view;

    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    public FragmentSugerencia() {
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
        this.view = inflater.inflate(R.layout.fragment_sugerencia, container, false);
        database = new MyOpenHelper(getContext(), 1);
        dbSugerencias = new DbSugerencias(getContext(), database);

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        this.btn_Registrar = view.findViewById(R.id.btn_registrar);
        btn_Registrar.setOnClickListener(this::onBtnRegistrar);
        btn_Ver = view.findViewById(R.id.btn_verSugerencias);
        btn_Ver.setOnClickListener(view1 -> MainActivity.replaceFragment(new FragmentVerSugerencias()));
        //Spinner
        Spinner spnCubierta = view.findViewById(R.id.spn_Cubierta);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_cubierta,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCubierta.setAdapter(adapter);
        spnCubierta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpinnerCubierta = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //EditText txtTitulo, txtEdicion, txtEditorial, txtNombreAutor, txtApellido, dtpFechaPublicacion, txtComentarios;

        txtTitulo = view.findViewById(R.id.txt_Titulo);
        txtEdicion = view.findViewById(R.id.txt_Edicion);
        txtEditorial = view.findViewById(R.id.txt_Editorial);
        txtNombreAutor = view.findViewById(R.id.txt_NombreAutor);
        txtApellido = view.findViewById(R.id.txt_ApellidoAutor);
        txtComentarios = view.findViewById(R.id.txt_Comentarios);
        return view;
    }


    public void onBtnRegistrar(View v) {
        boolean flag = true;
        long id = dbSugerencias.insertarSugerencia(txtTitulo.getText().toString(), txtEdicion.getText().toString(), txtEditorial.getText().toString(), mSpinnerCubierta, txtNombreAutor.getText().toString(),
                txtApellido.getText().toString(), txtComentarios.getText().toString());

        if (validar()) {
            if (id > 0) {
                Toast.makeText(getContext(), "Registro Guardado", Toast.LENGTH_LONG).show();
                flag = true;
            } else {
                Toast.makeText(getContext(), "Error al guardar Registro", Toast.LENGTH_LONG).show();
                flag = false;
            }
            if (flag) {
                limpiar();
            }
        } else {
            Toast.makeText(getContext(), "Llene los campos requeridos", Toast.LENGTH_LONG).show();
        }

    }


    private void limpiar() {
        txtTitulo.setText("");
        txtEdicion.setText("");
        txtEditorial.setText("");
        txtNombreAutor.setText("");
        txtApellido.setText("");
        txtComentarios.setText("");
    }

    private boolean validar() {
        boolean retorno = true;
        String titulo, edicion, editorial, fecha, nombre, apellido;
        titulo = txtTitulo.getText().toString();
        edicion = txtEdicion.getText().toString();
        editorial = txtEditorial.getText().toString();
        nombre = txtNombreAutor.getText().toString();
        apellido = txtApellido.getText().toString();

        if (titulo.isEmpty()) {
            txtTitulo.setError("Ingrese un titulo");
            retorno = false;
        }
        if (edicion.isEmpty()) {
            txtEdicion.setError("Ingrese una edicion");
            retorno = false;
        }
        if (editorial.isEmpty()) {
            txtEditorial.setError("Ingrese un editorial");
            retorno = false;
        }
        if (nombre.isEmpty()) {
            txtNombreAutor.setError("Ingrese un nombre");
            retorno = false;
        }
        if (apellido.isEmpty()) {
            txtApellido.setError("Ingrese un apellido");
            retorno = false;
        }

        return retorno;
    }
}