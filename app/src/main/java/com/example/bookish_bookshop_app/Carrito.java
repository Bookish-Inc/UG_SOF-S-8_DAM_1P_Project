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
    private RecyclerView recyclerView;
    private MyOpenHelper database;
    private CartDataBase cartDataBase;
    private HashMap<Integer, Cart> carts;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        this.recyclerView = findViewById(R.id.recyclerViewCarrito);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = new MyOpenHelper(this, 1);
        cartDataBase = new CartDataBase(this, database);
        carts = cartDataBase.getCarts();
        customAdapter = new CustomAdapter(carts, this, new ListenerCart(cartDataBase));
        recyclerView.setAdapter(customAdapter);
    }
}