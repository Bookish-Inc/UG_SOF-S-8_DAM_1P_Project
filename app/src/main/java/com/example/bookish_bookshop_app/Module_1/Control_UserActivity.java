package com.example.bookish_bookshop_app.Module_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.List;

public class Control_UserActivity extends AppCompatActivity {

    // Variables
    TextView txtFullName;
    TextView txtPhone;
    TextView txtEmail;
    TextView txtGenre;
    TextView txtOccupation;
    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_1_activity_user);

        txtFullName = (TextView) findViewById(R.id.User_lbl_FullName);
        txtPhone = (TextView) findViewById(R.id.User_lbl_Phone);
        txtEmail = (TextView) findViewById(R.id.User_lbl_Email);
        txtGenre = (TextView) findViewById(R.id.User_lbl_Genre);
        txtOccupation = (TextView) findViewById(R.id.User_lbl_Occupation);
        txtUsername = (TextView) findViewById(R.id.User_lbl_Username);


        // load User id
        int id_user = loadPreferences();

        // database connection
        MyOpenHelper db = new MyOpenHelper(getApplicationContext(), 1);
        Data_Module_1 data = new Data_Module_1(getApplicationContext(), db);

        List<String> userData = data.readDataUser(id_user);

        txtFullName.setText(userData.get(0) + " " + userData.get(1));
        txtPhone.setText(userData.get(2));
        txtEmail.setText(userData.get(3));
        txtGenre.setText(userData.get(4));
        txtOccupation.setText(userData.get(5));
        txtUsername.setText(userData.get(6));

    }

    public void onBtnEdit(View view) {
        callUserEditActivity();
    }

    public void onBtnLogOut(View view) {
        deletePreferences();
        toastMessage("Sessión cerrada");
        callLogInActivity();
    }

    private void callUserEditActivity() {   // Only calls LogInActivity
        startActivity(new Intent(Control_UserActivity.this, Control_UserEditActivity.class));
    }

    private void callLogInActivity() {   // Only calls LogInActivity
        startActivity(new Intent(Control_UserActivity.this, Control_LogInActivity.class));
        finish();
    }

    private void toastMessage(String message) { // toast a message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private int loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String id_user_temp = preferences.getString("id_user", "");
        return Integer.parseInt(id_user_temp);
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