package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bookish_bookshop_app.Cart.FragmentCarrito;
import com.example.bookish_bookshop_app.Categorias.Home;
import com.example.bookish_bookshop_app.Categorias.favoritosFragment;
import com.example.bookish_bookshop_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        replaceFragment(new Home());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.itemmenuhome: {
                    replaceFragment(new Home());
                    break;
                }
                case R.id.itemmenufav: {
                    replaceFragment(new favoritosFragment());
                    break;
                }
                case R.id.itemmenucart:{
                    replaceFragment(new FragmentCarrito());
                    break;
                }

            }
            return true;
        });

    }

    public static void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fram_layaout_main, fragment).commit();
    }

    /**
     * Methods--------------------------------------------------------------------------------------
     */



}