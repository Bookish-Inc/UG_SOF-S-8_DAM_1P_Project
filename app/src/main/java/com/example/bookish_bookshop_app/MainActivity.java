package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDeleteSP = (Button) findViewById(R.id.btn_DeleteSP);

        btnDeleteSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePreferences();
                callActivity();
            }
        });

    }

    /**
     * Methods--------------------------------------------------------------------------------------
     */

    private void callActivity() {
        /**
         * Only calls LogInActivity
         */
        startActivity(new Intent(MainActivity.this,LogInActivity.class));
        finish();
    }

    private void deletePreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");

        if (username_temp != "" && password_temp !="") {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");

            editor.apply();
        }
    }

}