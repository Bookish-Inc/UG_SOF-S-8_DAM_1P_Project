package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bookish_bookshop_app.Categorias.CategoriaFragment;
import com.example.bookish_bookshop_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Home());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.itemmenuhome: {
                    replaceFragment(new Home());
                    break;
                }
                case R.id.itemmenucategoria: {
                    replaceFragment(new CategoriaFragment());
                    break;
                }

            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fram_layaout_main, fragment).commit();
    }

    /**
     * Methods--------------------------------------------------------------------------------------
     */

    private void callActivity() {
        /**
         * Only calls LogInActivity
         */
        startActivity(new Intent(MainActivity.this, LogInActivity.class));
        finish();
    }

    private void deletePreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");

        if (username_temp != "" && password_temp != "") {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");

            editor.apply();
        }
    }

}