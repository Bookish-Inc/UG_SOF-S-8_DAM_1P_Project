package com.example.bookish_bookshop_app.Sugerencia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.VisualizarSugerencias;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.Calendar;

public class IngresarSugerencia extends AppCompatActivity {

    private String mSpinnerCubierta;
    EditText txtTitulo, txtEdicion, txtEditorial, txtNombreAutor, txtApellido, dtpFechaPublicacion, txtComentarios;
    Button btn_Registrar, btn_Ver;
    Spinner spnrCubierta;
    DbSugerencias dbSugerencias;
    private MyOpenHelper database;

    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_sugerencia);
        database = new MyOpenHelper(this, 1);
        dbSugerencias = new DbSugerencias(this, database);

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        dtpFechaPublicacion = (EditText) findViewById(R.id.dtp_FechaPublicaciion);
        dtpFechaPublicacion.setOnClickListener((view) -> {
            showDialog(DATE_ID);
        });


        //Spinner
        Spinner spnCubierta = (Spinner) findViewById(R.id.spn_Cubierta);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_cubierta,
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

        txtTitulo = findViewById(R.id.txt_Titulo);
        txtEdicion = findViewById(R.id.txt_Edicion);
        txtEditorial = findViewById(R.id.txt_Editorial);
        txtNombreAutor = findViewById(R.id.txt_NombreAutor);
        txtApellido = findViewById(R.id.txt_ApellidoAutor);
        txtComentarios = findViewById(R.id.txt_Comentarios);
    }

    private void Colocar_fecha() {
        dtpFechaPublicacion.setText(nDayIni + "-" + (nMonthIni + 1) + "-" + nYearIni + "");
    }

    private DatePickerDialog.OnDateSetListener nDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    nYearIni = year;
                    nMonthIni = monthOfYear;
                    nDayIni = dayOfMonth;
                    Colocar_fecha();
                }
            };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, nDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    public void onBtnRegistrar(View v) {
        boolean flag= true;
        long id = dbSugerencias.insertarSugerencia(txtTitulo.getText().toString(), txtEdicion.getText().toString(), txtEditorial.getText().toString(), mSpinnerCubierta, dtpFechaPublicacion.getText().toString(), txtNombreAutor.getText().toString(),
                txtApellido.getText().toString(), txtComentarios.getText().toString());

        if(validar()){
            if (id > 0) {
                Toast.makeText(IngresarSugerencia.this, "Registro Guardado", Toast.LENGTH_LONG).show();
                flag=true;
            } else {
                Toast.makeText(IngresarSugerencia.this, "Error al guardar Registro", Toast.LENGTH_LONG).show();
                flag=false;
            }
            if (flag){
                limpiar();
            }
        }else{
            Toast.makeText(IngresarSugerencia.this, "Llene los campos requeridos", Toast.LENGTH_LONG).show();
        }

    }

    public void onBtnVerSugerencias(View v){
        Toast.makeText(getApplicationContext(), "Consultando BD...", Toast.LENGTH_SHORT).show();
        Intent call_consultar = new Intent(v.getContext(), VisualizarSugerencias.class);
        startActivity(call_consultar);
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtEdicion.setText("");
        txtEditorial.setText("");
        dtpFechaPublicacion.setText("");
        txtNombreAutor.setText("");
        txtApellido.setText("");
        txtComentarios.setText("");
    }

    private boolean validar(){
        boolean retorno = true;
        String titulo, edicion, editorial, fecha, nombre, apellido;
        titulo = txtTitulo.getText().toString();
        edicion = txtEdicion.getText().toString();
        editorial = txtEditorial.getText().toString();
        fecha = dtpFechaPublicacion.getText().toString();
        nombre = txtNombreAutor.getText().toString();
        apellido = txtApellido.getText().toString();

        if(titulo.isEmpty()){
            txtTitulo.setError("Ingrese un titulo");
            retorno= false;
        }
        if(edicion.isEmpty()){
            txtEdicion.setError("Ingrese una edicion");
            retorno= false;
        }
        if(editorial.isEmpty()){
            txtEditorial.setError("Ingrese un editorial");
            retorno= false;
        }
        if(fecha.isEmpty()){
            dtpFechaPublicacion.setError("Ingrese una fecha");
            retorno= false;
        }
        if(nombre.isEmpty()){
            txtNombreAutor.setError("Ingrese un nombre");
            retorno= false;
        }
        if(apellido.isEmpty()){
            txtApellido.setError("Ingrese un apellido");
            retorno= false;
        }

        return retorno;
    }


}