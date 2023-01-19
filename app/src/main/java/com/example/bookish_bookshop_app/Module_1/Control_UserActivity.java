package com.example.bookish_bookshop_app.Module_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;

public class Control_UserActivity extends AppCompatActivity {

//    String txtFullName


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_1_activity_user);

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