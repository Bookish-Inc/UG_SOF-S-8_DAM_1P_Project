package com.example.bookish_bookshop_app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class Imagen {

    public static byte[] serializar(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap deserializar(byte[] bytes) {
        if (bytes == null){
            throw new NullPointerException();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


}
