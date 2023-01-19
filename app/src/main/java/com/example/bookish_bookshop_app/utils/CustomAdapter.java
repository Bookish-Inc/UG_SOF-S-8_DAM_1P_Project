package com.example.bookish_bookshop_app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish_bookshop_app.Cart.Cart;
import com.example.bookish_bookshop_app.Cart.ListenerCart;
import com.example.bookish_bookshop_app.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    final private HashMap<Integer, Cart> carts;
    private Context contextCarrito;
    private ListenerCart listenerCart;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param carts containing the data to populate views to be used
     *              by RecyclerView.
     */
    public CustomAdapter(HashMap<Integer, Cart> carts, Context context, ListenerCart listenerCart) {
        this.carts = carts;
        this.contextCarrito = context;
        this.listenerCart = listenerCart;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titulo, identificador;
        private final TextView precio, cantidad;
        private final ImageView imagen;
        private final ImageView restaCantidad, aumentaCantidad, borrar;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            cantidad = view.findViewById(R.id.cantidadItem);
            restaCantidad = view.findViewById(R.id.restaCantidad);
            aumentaCantidad = view.findViewById(R.id.sumaCantidad);
            borrar = view.findViewById(R.id.imagenBorrar);
            titulo = view.findViewById(R.id.tituloItem);
            precio = view.findViewById(R.id.precioItem);
            imagen = view.findViewById(R.id.imagenLbro);
            identificador = view.findViewById(R.id.identeificadorItem);
            view.setOnClickListener(this);
            aumentaCantidad.setOnClickListener(this);
            borrar.setOnClickListener(this);
            restaCantidad.setOnClickListener(this);
            loadTag();
        }

        void loadTag() {
            aumentaCantidad.setTag(1);
            restaCantidad.setTag(2);
            borrar.setTag(3);
        }

        public TextView getIdentificador() {
            return identificador;
        }

        public TextView getCantidad() {
            return cantidad;
        }

        public ImageView getRestaCantidad() {
            return restaCantidad;
        }

        public ImageView getAumentaCantidad() {
            return aumentaCantidad;
        }

        public ImageView getBorrar() {
            return borrar;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public TextView getPrecio() {
            return precio;
        }

        public ImageView getImagen() {
            return imagen;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int iden;
            try {
                iden = Integer.parseInt(("" + identificador.getText()).replace("#", ""));
            } catch (Throwable exception) {
                Toast.makeText(contextCarrito, "Ha ocurrido un problema Error 0x001", Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
                return;
            }
            Cart cart = carts.get(iden);
            if (cart != null) {
                System.out.println("Obtiene id: " + cart.getId());
                switch (view.getId()) {
                    case R.id.sumaCantidad: {
                        listenerCart.onClickAddAmount(cart, this);
                        break;
                    }
                    case R.id.restaCantidad: {
                        listenerCart.onClickReduceAmount(cart, this);
                        break;
                    }
                    case R.id.imagenBorrar: {
                        listenerCart.onClickRemoveItem(cart, this);
                        carts.remove(iden);
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            }

        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(contextCarrito).inflate(R.layout.listacarrito, null);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Cart cart = new ArrayList<>(carts.values()).get(position);
        if (cart != null) {
            System.out.println("aaaaaaaaaaaaaaaaaa");
            viewHolder.getTitulo().setText(cart.getTitulo());
            viewHolder.getCantidad().setText(Integer.toString(cart.getCount()));
            viewHolder.getImagen().setImageBitmap(cart.getImagen());
            viewHolder.getPrecio().setText("$" + Double.toString(cart.getPrice() * cart.getCount()));
            viewHolder.getIdentificador().setText("#" + Integer.toString(cart.getId()));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return carts.size();
    }

    public interface onClickItem {
        void onClickAddAmount(Cart cart, ViewHolder view);

        void onClickReduceAmount(Cart cart, ViewHolder view);

        void onClickRemoveItem(Cart cart, ViewHolder view);

        void onClickPurchase(Cart cart, ViewHolder view);
    }
}

