package com.example.bookish_bookshop_app.Module_1;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.Sugerencia.User;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

public class Control_LogInActivity extends AppCompatActivity {
    // Global Variables
    public EditText txtUsername;
    public EditText txtPassword;
    public CheckBox chkSession;
    // Database Variables
    public Data_Module_1 data;
    public MyOpenHelper db;
    public int iduser=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_1_activity_log_in);

        // Variables
        txtUsername = (EditText) findViewById(R.id.LogIn_txt_Username);
        txtPassword = (EditText) findViewById(R.id.LogIn_txt_Password);
        chkSession = (CheckBox) findViewById(R.id.LogIn_chk_Session);

        // database connection
        db = new MyOpenHelper(getApplicationContext(), 1);
        data = new Data_Module_1(getApplicationContext(), db);

        // Initialize default data in database
        data.insertDataUser();

        // Verifies: if SharedPreferences exist then autologin
        if (loadPreferences()) {
            callMainActivity(iduser);
            // TODO: send credential to UserActivity
        }
    }

    /**
     * Methods--------------------------------------------------------------------------------------
     */

    public void onBtnLogIn(View view) {

        // Variables
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        boolean activeSession = chkSession.isChecked();
        boolean existCredential = false;
        int id_user = 0;

        // Verifies if credentials exist
        existCredential = data.readCredential(username, password);

        if (existCredential) {
            if (activeSession) {
                // if checkbox = true, then saves SharedPreferences
                id_user = data.readUser_IdByUsername(username);
                savePreferences(username, password, id_user);
                toastMessage("Pereferncias guardadas");
            } else {
                toastMessage("Preferencias NO guardadas");
            }
            callMainActivity(id_user);
        } else {
            toastMessage("Datos Incorrectos");
        }

    }

    public void onLblSignUp(View view) {
        // Calls SignUpActivity
        startActivity(new Intent(this, Control_SignUpActivity.class));
    }

    private void toastMessage(String message) {
        // toast a message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void callMainActivity(int idUser) {
        // Only calls MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
        finish();
    }

    // region Methods: SharedPreferences
    private void savePreferences(String username_pref, String password_pref, int id_user_pref) {
        SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username_pref);
        editor.putString("password", password_pref);
        editor.putInt("id_user", id_user_pref);

        editor.commit();
    }

    private boolean loadPreferences() {
        /**
         * true: SharedPreferences do exist
         * false: SharedPreferences doesn't exist
         */
        boolean flag = false;

        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");
        this.iduser = preferences.getInt("id_user",0);

        if (username_temp != "" && password_temp != "") {
            flag = true;
        }

        return flag;
    }
    // endregion

}