package com.example.bookish_bookshop_app.Module_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.Arrays;
import java.util.List;

public class Control_UserEditActivity extends AppCompatActivity {

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
    int id_user;
    List<String> listOccupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_1_activity_user_edit);

        // Variables
        txtName = (EditText) findViewById(R.id.UserEdit_txt_Name);
        txtLastname = (EditText) findViewById(R.id.UserEdit_txt_Lastname);
        txtPhone = (EditText) findViewById(R.id.UserEdit_txt_Phone);
        txtEmail = (EditText) findViewById(R.id.UserEdit_txt_Email);
        rbtFemale = (RadioButton) findViewById(R.id.UserEdit_rdb_Female);
        rbtMale = (RadioButton) findViewById(R.id.UserEdit_rdb_Male);
        sprOccupation = (Spinner) findViewById(R.id.UserEdit_spnr_Occupation);
        txtUsername = (EditText) findViewById(R.id.UserEdit_txt_Username);
        txtPassword = (EditText) findViewById(R.id.UserEdit_txt_Password);

        // Array for spinner
        listOccupation = Arrays.asList("", "Estudiante", "Profesor", "Profesional", "Docente");

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

        id_user = loadPreferences();
        loadDataUser();

    }

    public void loadDataUser() {
        // database connection
        MyOpenHelper db = new MyOpenHelper(getApplicationContext(), 1);
        Data_Module_1 data = new Data_Module_1(getApplicationContext(), db);

        List<String> userData = data.readDataUser(id_user);

        txtName.setText(userData.get(0));
        txtLastname.setText(userData.get(1));
        txtPhone.setText(userData.get(2));
        txtEmail.setText(userData.get(3));
        genre = userData.get(4);
        occupation = userData.get(5);
        txtUsername.setText(userData.get(6));
        txtPassword.setText(userData.get(7));

        // Set RadioButton
        if (genre.equals("Femenino")) {
            rbtFemale.setChecked(true);
        } else if (genre.equals("Masculino")) {
            rbtMale.setChecked(true);
        }

        // Set Spinner
        sprOccupation.setSelection(listOccupation.indexOf(occupation));

    }

    public void onRdgGenreUpdate(View view) {
        RadioButton rdbGenre = ((RadioButton) view);

        if (!rdbGenre.isChecked()) {
            return;
        }

        if (view.getId() == R.id.UserEdit_rdb_Female) {
            toastMessage("Femenino");
            genre = "Femenino";
        } else if (view.getId() == R.id.UserEdit_rdb_Male) {
            toastMessage("Masculino");
            genre = "Masculino";
        }
    }

    public void onBtnUpdate(View view) {

        boolean updateData = false;

        // database connection
        MyOpenHelper db = new MyOpenHelper(getApplicationContext(), 1);
        Data_Module_1 data = new Data_Module_1(getApplicationContext(), db);

        String name = txtName.getText().toString();
        String lastname = txtLastname.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        updateData = data.updateUser(id_user, name, lastname, phone, email, genre, occupation, username, password);

        if (updateData) {
            deletePreferences();
            savePreferences(username, password, id_user + "");
            toastMessage("Datos actualizados");
            callUserActivity();
            finish();
        } else {
            toastMessage("Datos NO eliminados");
        }

    }

    public void onBtnCancel(View view) {
        callUserActivity();
        finish();
    }

    public void onBtnDelete(View view) {
        boolean deletedData = false;

        // database connection
        MyOpenHelper db = new MyOpenHelper(getApplicationContext(), 1);
        Data_Module_1 data = new Data_Module_1(getApplicationContext(), db);

        deletedData = data.deleteDataUser(id_user);

        if (deletedData) {
            deletePreferences();
            toastMessage("Datos eliminados");
            callLogInActivity();
            finish();
        } else {
            toastMessage("Datos NO eliminados");
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void callUserActivity() {   // Only calls MainActivity
        startActivity(new Intent(Control_UserEditActivity.this, Control_UserActivity.class));
        finish();
    }

    private void callLogInActivity() {   // Only calls MainActivity
        startActivity(new Intent(Control_UserEditActivity.this, Control_LogInActivity.class));
        finish();
    }

    private int loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String id_user_temp = preferences.getString("id_user", "");
        return Integer.parseInt(id_user_temp);
    }

    private void savePreferences(String username_pref, String password_pref, String id_user_pref) {
        SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username_pref);
        editor.putString("password", password_pref);
        editor.putString("id_user", id_user_pref);

        editor.commit();
    }

    private void deletePreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");
        String id_user_temp = preferences.getString("id_user", "");

        if (username_temp != "" && password_temp != "" && id_user_temp != "") {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.remove("id_user");

            editor.apply();
        }

    }

}