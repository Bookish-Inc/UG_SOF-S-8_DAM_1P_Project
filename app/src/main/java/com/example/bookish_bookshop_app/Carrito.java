package com.example.bookish_bookshop_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bookish_bookshop_app.Cart.Cart;
import com.example.bookish_bookshop_app.Cart.CartDataBase;
import com.example.bookish_bookshop_app.Cart.ListenerCart;
import com.example.bookish_bookshop_app.utils.CustomAdapter;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.HashMap;

public class Carrito extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
    }
}