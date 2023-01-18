package com.example.bookish_bookshop_app.Module_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.Arrays;
import java.util.List;

public class Control_SignUpActivity extends AppCompatActivity {

    // Variables
    EditText txtName;
    EditText txtLastname;
    EditText txtPhone;
    EditText txtEmail;
    RadioButton rbtFemale;
    RadioButton rbtMale;
    String genre;
    Spinner sprOccupation;
    String occupation;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_1_activity_sign_up);

        // Variables
        txtName = (EditText) findViewById(R.id.SignUp_txt_Name);
        txtLastname = (EditText) findViewById(R.id.SignUp_txt_Lastname);
        txtPhone = (EditText) findViewById(R.id.SignUp_txt_Phone);
        txtEmail = (EditText) findViewById(R.id.SignUp_txt_Email);
        rbtFemale = (RadioButton) findViewById(R.id.SignUp_rdb_Female);
        rbtMale = (RadioButton) findViewById(R.id.SignUp_rdb_Male);
        sprOccupation = (Spinner) findViewById(R.id.SignUp_spnr_Occupation);
        txtUsername = (EditText) findViewById(R.id.SignUp_txt_Username);
        txtPassword = (EditText) findViewById(R.id.SignUp_txt_Password);

        List<String> listOccupation = Arrays.asList("--Elegir--", "Estudiante", "Profesor", "Profesional", "Docente");

        sprOccupation.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listOccupation));

        sprOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                occupation = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onRdgGenre(View view) {
        RadioButton rdbGenre = ((RadioButton) view);

        if (!rdbGenre.isChecked()) {
            return;
        }

        if (view.getId() == R.id.SignUp_rdb_Female) {
            toastMessage("Female");
            genre = "Female";
        } else if (view.getId() == R.id.SignUp_rdb_Male) {
            toastMessage("Male");
            genre = "Male";
        }
    }

    public void onBtnSignUp(View view) {
        MyOpenHelper db = new MyOpenHelper(getApplicationContext(), 1);
        Data_Module_1 data = new Data_Module_1(getApplicationContext(), db);

        // Variables
        boolean flagUsername = false;
        String name = txtName.getText().toString();
        String lastname = txtLastname.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        int id_credential = 0;
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        // verifies if username already exist
        for (Model_Credential element: data.readDataCredentials()) {
            if (username.equals(element.username)){
                flagUsername = true;
                toastMessage("Nombre de usuario ya existe");
                break;
            }
        }
        if (!flagUsername) {
            data.createDataUser(name, lastname, phone, email, genre, occupation, username, password );
        }

        toastMessage("Crear Cuenta");
    }

    public void onBtnCancel(View view) {
        finish();
        toastMessage("Cancelar");
    }

    private void toastMessage(String message) {
        // toast a message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}