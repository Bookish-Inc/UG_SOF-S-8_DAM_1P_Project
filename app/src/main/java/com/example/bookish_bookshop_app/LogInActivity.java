package com.example.bookish_bookshop_app;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;

    public User[] users =
            {
                    new User("Helen", "1234"),
                    new User("Nefi", "1234"),
                    new User("Renan", "1234"),
                    new User("Vane", "1234"),
            };

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button btnPopup_yes, btnPopup_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txtUsername =(EditText) findViewById(R.id.txt_Username);
        txtPassword = (EditText) findViewById(R.id.txt_Password);
        Button btnLogin = (Button) findViewById(R.id.btn_LogIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bandera = false;
               //Intent call_principal = new Intent(view.getContext(), Registro.class);

                for (User user : users) {
                    System.out.println("users.length = " + users.length + "\n Nombre: " + user.nombre + "\n Contraseña: " + user.contrasenia);
                    if ((txtUsername.getText().toString().equals(user.nombre)) && (txtPassword.getText().toString().equals(user.contrasenia))) {
                        createNewPopUp ();
                        bandera = true;
                        break; // para que salga cuando ya encuentre la respuesta
                    }
                }

                if (!bandera) {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // https://www.youtube.com/watch?v=4GYKOzgQDWI
    public void createNewPopUp () {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.activity_pop_up, null);

        btnPopup_yes = (Button) popupView.findViewById(R.id.btn_Yes);
        btnPopup_no = (Button) popupView.findViewById(R.id.btn_No);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnPopup_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // define what it does to save
                savePreferences();

                // calls main activity
                callActivity(view);

            }
        });

        btnPopup_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // define what it does to cancel
                callActivity(view);
            }
        });

    }

    private void callActivity(View view) {
        Intent call_activity = new Intent(view.getContext(), MainActivity.class);
        startActivity(call_activity);
    }

    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);
        String username_pref = txtUsername.getText().toString();
        String password_pref = txtPassword.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", username_pref);
        editor.putString("clave", password_pref);

        editor.commit();

    };

}