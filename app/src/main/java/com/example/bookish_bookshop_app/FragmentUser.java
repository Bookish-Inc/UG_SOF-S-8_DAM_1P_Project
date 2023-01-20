package com.example.bookish_bookshop_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookish_bookshop_app.Module_1.Control_LogInActivity;
import com.example.bookish_bookshop_app.Module_1.Data_Module_1;
import com.example.bookish_bookshop_app.Module_1.FragmentUserEdit;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.List;


public class FragmentUser extends Fragment {

    private View view;
    TextView txtFullName;
    TextView txtPhone;
    TextView txtEmail;
    TextView txtGenre;
    TextView txtOccupation;
    TextView txtUsername;

    public FragmentUser() {
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
        this.view = inflater.inflate(R.layout.fragment_user, container, false);
        txtFullName = (TextView) view.findViewById(R.id.User_lbl_FullName);
        txtPhone = (TextView) view.findViewById(R.id.User_lbl_Phone);
        txtEmail = (TextView) view.findViewById(R.id.User_lbl_Email);
        txtGenre = (TextView) view.findViewById(R.id.User_lbl_Genre);
        txtOccupation = (TextView) view.findViewById(R.id.User_lbl_Occupation);
        txtUsername = (TextView) view.findViewById(R.id.User_lbl_Username);
        Button btnuser = view.findViewById(R.id.User_btn_Edit);
        btnuser.setOnClickListener(view1 -> {
            MainActivity.replaceFragment(new FragmentUserEdit());
        });
        Button btnLogut = view.findViewById(R.id.User_btn_LogOut);
        btnLogut.setOnClickListener(this::onBtnLogOut);

        // load User id
        int id_user = loadPreferences();
        if (id_user != 0) {
            MyOpenHelper db = new MyOpenHelper(getContext(), 1);
            Data_Module_1 data = new Data_Module_1(getContext(), db);

            List<String> userData = data.readDataUser(id_user);

            txtFullName.setText(userData.get(0) + " " + userData.get(1));
            txtPhone.setText(userData.get(2));
            txtEmail.setText(userData.get(3));
            txtGenre.setText(userData.get(4));
            txtOccupation.setText(userData.get(5));
            txtUsername.setText(userData.get(6));
        }
        return view;
    }

    public void onBtnLogOut(View view) {
        deletePreferences();
        toastMessage("Sessión cerrada");
        callLogInActivity();
    }

    private void callUserEditActivity() {   // Only calls LogInActivity
    }

    private void callLogInActivity() {   // Only calls LogInActivity
        startActivity(new Intent(getContext(), Control_LogInActivity.class));
    }

    private void toastMessage(String message) { // toast a message
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private int loadPreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        return preferences.getInt("id_user", 0);
    }

    private void deletePreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");
        int id_user_temp = preferences.getInt("id_user", 0);

        if (username_temp != "" && password_temp != "" && id_user_temp != 0) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.remove("id_user");
            editor.apply();
        }

    }
}