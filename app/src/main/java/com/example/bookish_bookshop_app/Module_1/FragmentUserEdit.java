package com.example.bookish_bookshop_app.Module_1;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookish_bookshop_app.FragmentUser;
import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.Arrays;
import java.util.List;


public class FragmentUserEdit extends Fragment {
    private View view;
    // Variables
    EditText txtName;
    EditText txtLastname;
    EditText txtPhone;
    EditText txtEmail;
    RadioButton rbtFemale;
    RadioButton rbtMale;
    String genre;
    Spinner sprOccupation;
    String occupation;
    EditText txtUsername;
    EditText txtPassword;
    int id_user;
    List<String> listOccupation;

    public FragmentUserEdit() {
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
        this.view = inflater.inflate(R.layout.fragment_user_edit, container, false);
        // Variables
        txtName = (EditText) view.findViewById(R.id.UserEdit_txt_Name);
        txtLastname = (EditText) view.findViewById(R.id.UserEdit_txt_Lastname);
        txtPhone = (EditText) view.findViewById(R.id.UserEdit_txt_Phone);
        txtEmail = (EditText) view.findViewById(R.id.UserEdit_txt_Email);
        rbtFemale = (RadioButton) view.findViewById(R.id.UserEdit_rdb_Female);
        rbtMale = (RadioButton) view.findViewById(R.id.UserEdit_rdb_Male);
        sprOccupation = (Spinner) view.findViewById(R.id.UserEdit_spnr_Occupation);
        txtUsername = (EditText) view.findViewById(R.id.UserEdit_txt_Username);
        txtPassword = (EditText) view.findViewById(R.id.UserEdit_txt_Password);
        Button update = view.findViewById(R.id.UserEdit_btn_Update);
        update.setOnClickListener(this::onBtnUpdate);
        Button cancel = view.findViewById(R.id.UserEdit_btn_Cancel);
        cancel.setOnClickListener(this::onBtnCancel);
        Button delete = view.findViewById(R.id.UserEdit_btn_Delete);
        delete.setOnClickListener(this::onBtnDelete);
        RadioGroup radioGroup = view.findViewById(R.id.UserEdit_rdg_Genre);
        radioGroup.setOnClickListener(this::onRdgGenreUpdate);
        // Array for spinner
        listOccupation = Arrays.asList("", "Estudiante", "Profesor", "Profesional", "Docente");

        // Set spinner text options
        sprOccupation.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listOccupation));

        // Sett Spinner behavior
        sprOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                occupation = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        id_user = loadPreferences();
        loadDataUser();
        return view;
    }

    public void loadDataUser() {
        // database connection
        MyOpenHelper db = new MyOpenHelper(getContext(), 1);
        Data_Module_1 data = new Data_Module_1(getContext(), db);

        List<String> userData = data.readDataUser(id_user);

        txtName.setText(userData.get(0));
        txtLastname.setText(userData.get(1));
        txtPhone.setText(userData.get(2));
        txtEmail.setText(userData.get(3));
        genre = userData.get(4);
        occupation = userData.get(5);
        txtUsername.setText(userData.get(6));
        txtPassword.setText(userData.get(7));

        // Set RadioButton
        if (genre.equals("Femenino")) {
            rbtFemale.setChecked(true);
        } else if (genre.equals("Masculino")) {
            rbtMale.setChecked(true);
        }

        // Set Spinner
        sprOccupation.setSelection(listOccupation.indexOf(occupation));

    }

    public void onRdgGenreUpdate(View view) {
        RadioButton rdbGenre = ((RadioButton) view);

        if (!rdbGenre.isChecked()) {
            return;
        }
        if (view.getId() == R.id.UserEdit_rdb_Female) {
            toastMessage("Femenino");
            genre = "Femenino";
        } else if (view.getId() == R.id.UserEdit_rdb_Male) {
            toastMessage("Masculino");
            genre = "Masculino";
        }
    }

    public void onBtnUpdate(View view) {

        boolean updateData = false;

        // database connection
        MyOpenHelper db = new MyOpenHelper(getContext(), 1);
        Data_Module_1 data = new Data_Module_1(getContext(), db);

        String name = txtName.getText().toString();
        String lastname = txtLastname.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        updateData = data.updateUser(id_user, name, lastname, phone, email, genre, occupation, username, password);

        if (updateData) {
            deletePreferences();
            savePreferences(username, password, id_user);
            toastMessage("Datos actualizados");
            MainActivity.replaceFragment(new FragmentUser());
        } else {
            toastMessage("Datos NO eliminados");
        }

    }

    public void onBtnCancel(View view) {
        MainActivity.replaceFragment(new FragmentUser());
    }

    public void onBtnDelete(View view) {
        boolean deletedData = false;

        // database connection
        MyOpenHelper db = new MyOpenHelper(getContext(), 1);
        Data_Module_1 data = new Data_Module_1(getContext(), db);

        deletedData = data.deleteDataUser(id_user);

        if (deletedData) {
            deletePreferences();
            toastMessage("Datos eliminados");
            callLogInActivity();
        } else {
            toastMessage("Datos NO eliminados");
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private int loadPreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("credentials", MODE_PRIVATE);
        return preferences.getInt("id_user", 0);

    }

    private void callLogInActivity() {   // Only calls MainActivity
        startActivity(new Intent(getContext(), Control_LogInActivity.class));
    }


    private void savePreferences(String username_pref, String password_pref, int id_user_pref) {
        SharedPreferences preferences = getContext().getSharedPreferences("credentials", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username_pref);
        editor.putString("password", password_pref);
        editor.putInt("id_user", id_user_pref);

        editor.commit();
    }

    private void deletePreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("credentials", MODE_PRIVATE);
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