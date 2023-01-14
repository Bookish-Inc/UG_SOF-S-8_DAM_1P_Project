package com.example.bookish_bookshop_app.Cart;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.bookish_bookshop_app.utils.CustomAdapter;

public class ListenerCart implements CustomAdapter.onClickItem {

    private CartDataBase cartDataBase;

    public ListenerCart(CartDataBase cartDataBase) {
        this.cartDataBase = cartDataBase;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClickAddAmount(Cart cart, CustomAdapter.ViewHolder view) {
        if (cart.getCount() >= 1) {
            cart.incrementAmount();
            view.getCantidad().setText(Integer.toString(cart.getCount()));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClickReduceAmount(Cart cart, CustomAdapter.ViewHolder view) {
        if (cart.getCount() > 1) {
            cart.decrementAmount();
            view.getCantidad().setText(Integer.toString(cart.getCount()));
        }
    }

    @Override
    public void onClickRemoveItem(Cart cart, CustomAdapter.ViewHolder view) {

    }

    @Override
    public void onClickPurchase(Cart cart, CustomAdapter.ViewHolder view) {

    }
}
