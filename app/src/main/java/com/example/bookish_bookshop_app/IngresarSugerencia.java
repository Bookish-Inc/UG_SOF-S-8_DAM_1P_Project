package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class IngresarSugerencia extends AppCompatActivity {

    private String mSpinnerCubierta;
    EditText txtTitulo, txtEdicion, txtEditorial, txtNombreAutor, txtApellido, dtpFechaPublicacion, txtComentarios;
    Button btn_Registrar, btn_Ver;
    Spinner spnrCubierta;

    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_sugerencia);

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

    private void Colocar_fecha(){
        dtpFechaPublicacion.setText(nDayIni + "-" +(nMonthIni + 1) + "-" +  nYearIni + "");
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

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, nDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    public void onBtnRegistrar(View v){
        DbSugerencias dbSugerencias = new DbSugerencias(IngresarSugerencia.this,1);
        long id= dbSugerencias.insertarSugerencia(txtTitulo.getText().toString(),txtEdicion.getText().toString() ,txtEditorial.getText().toString(),
                mSpinnerCubierta, dtpFechaPublicacion.getText().toString(), txtNombreAutor.getText().toString(),
                txtApellido.getText().toString(),txtComentarios.getText().toString());
        if (id>0){
            Toast.makeText(IngresarSugerencia.this, "Registro Guardado", Toast.LENGTH_LONG).show();
            limpiar();
        }else{
            Toast.makeText(IngresarSugerencia.this, "Errore al guardar Registro", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar (){
        txtTitulo.setText("");
        txtEdicion.setText("");
        txtEditorial.setText("");
        dtpFechaPublicacion.setText("");
        txtNombreAutor.setText("");
        txtApellido.setText("");
        txtComentarios.setText("");
        spnrCubierta.setSelection(0);
    }


}