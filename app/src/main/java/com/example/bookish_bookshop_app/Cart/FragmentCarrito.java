package com.example.bookish_bookshop_app.Cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.CustomAdapter;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.HashMap;

public class FragmentCarrito extends Fragment {

    private RecyclerView recyclerView;
    private MyOpenHelper database;
    private CartDataBase cartDataBase;
    private HashMap<Integer, Cart> carts;
    private CustomAdapter customAdapter;
    private View view;



    public FragmentCarrito() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_carrito, container, false);
        this.recyclerView = view.findViewById(R.id.recyclerViewCarrito);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        database = new MyOpenHelper(getContext(), 1);
        cartDataBase = new CartDataBase(getContext(), database);
        carts = cartDataBase.getCarts();
        if (carts.size() == 0 ){
            cartDataBase.insertLibro();
            cartDataBase.insertCart(1);
            cartDataBase.insertCart(2);
            cartDataBase.insertCart(3);
            cartDataBase.insertCart(4);
            cartDataBase.insertCart(5);
        }
        System.out.println("DAtossssss: " + carts.size());
        customAdapter = new CustomAdapter(carts, getContext(), new ListenerCart(cartDataBase));
        recyclerView.setAdapter(customAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}