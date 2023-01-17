package com.example.bookish_bookshop_app;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookish_bookshop_app.Sugerencia.User;

public class LogInActivity extends AppCompatActivity {

    /**
     * Variables
     */

    public EditText txtUsername;
    public EditText txtPassword;

    private User[] users = {
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

        /*if (loadPreferences()) {

            callActivity();
        } else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean flag = false;
                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();

                    for (User user : users) {
                        if (username.equals(user.nombre) && password.equals(user.contrasenia)) {
                            createNewPopUp();
                            flag = true;
                            break; // para que salga cuando ya encuentre la respuesta
                        }
                    }

                    if (!flag) {
                        toastMessage("Datos Incorrectos");
                    }
                }
            });
        }*/
    }

    public void onBtnIniciarSesion(View v) {
        Intent call_home = new Intent(v.getContext(), HomeActivity.class);
        startActivity(call_home);
    }

    /**
     * Methods--------------------------------------------------------------------------------------
     */

    // https://www.youtube.com/watch?v=4GYKOzgQDWI
    public void createNewPopUp () {
        /**
         * Creates/calls activity as a pop up windowa
         */
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
                callActivity();
                toastMessage("Pereferncias guardadas");
            }
        });

        btnPopup_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // define what it does to cancel
                callActivity();
                toastMessage("Preferencias NO guardadas");
            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void callActivity() {
        /**
         * Only calls MainActivity
         */
        startActivity(new Intent(LogInActivity.this,MainActivity.class));
        finish();
    }

    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);
        String username_pref = txtUsername.getText().toString();
        String password_pref = txtPassword.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username_pref);
        editor.putString("password", password_pref);

        editor.commit();
    };

    private boolean loadPreferences() {
        /**
         * true: SharedPreferences do exist
         * false: SharedPreferences doesn't exist
         */
        boolean flag = false;

        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");

        if (username_temp != "" && password_temp !="") {
            flag = true;
        }

        return flag;
    };

}