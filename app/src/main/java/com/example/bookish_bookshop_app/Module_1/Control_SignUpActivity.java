package com.example.bookish_bookshop_app.Module_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookish_bookshop_app.MainActivity;
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

        // Array for spinner
        List<String> listOccupation = Arrays.asList("", "Estudiante", "Profesor", "Profesional", "Docente");

        // Set spinner text options
        sprOccupation.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listOccupation));

        // Sett Spinner behavior
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

    /**
     * Methods--------------------------------------------------------------------------------------
     */

    public void onRdgGenreSignUp(View view) {
        RadioButton rdbGenre = ((RadioButton) view);

        if (!rdbGenre.isChecked()) {
            return;
        }

        if (view.getId() == R.id.SignUp_rdb_Female) {
            genre = "Femenino";
        } else if (view.getId() == R.id.SignUp_rdb_Male) {
            genre = "Masculino";
        }
    }

    public void onBtnSignUp(View view) {
        MyOpenHelper db = new MyOpenHelper(getApplicationContext(), 1);
        Data_Module_1 data = new Data_Module_1(getApplicationContext(), db);

        // Variables
        boolean existCredential = false;
        boolean saveUser = false;
        int id_user = 0;
        String name = txtName.getText().toString();
        String lastname = txtLastname.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        // Verifies if username exist
        existCredential = data.readCredential_ByUsername(username);

        if (!existCredential) {
            // Create data User
            saveUser = data.createUser(name, lastname, phone, email, genre, occupation, username, password);
            if (saveUser) {
                // Save SharedPreferences
                id_user = data.readUser_IdByUsername(username);
                savePreferences(username, password, id_user);
                callMainActivity();
                toastMessage("Cuenta creada");
            } else {
                toastMessage("No se pudo crear la cuenta");
            }
        } else {
            // User
            toastMessage("Nombre de usuario ya existe");
        }

    }

    public void onBtnCancel(View view) {
        toastMessage("Cancelar");
        finish();
    }

    private void toastMessage(String message) {
        // toast a message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void callMainActivity() {   // Only calls MainActivity
//        startActivity(new Intent(Control_SignUpActivity.this, MainActivity.class));
        startActivity(new Intent(Control_SignUpActivity.this, MainActivity.class));
        finish();
    }

    private void savePreferences(String username_pref, String password_pref, int id_user_pref) {
        SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username_pref);
        editor.putString("password", password_pref);
        editor.putInt("id_user", id_user_pref);

        editor.commit();
    }

}