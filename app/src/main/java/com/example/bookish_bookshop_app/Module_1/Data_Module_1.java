package com.example.bookish_bookshop_app.Module_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;

import java.util.ArrayList;
import java.util.List;

public class Data_Module_1 {

    // Global Variables
    private Context context;
    private MyOpenHelper db;

    public Data_Module_1(Context context, MyOpenHelper db) {
        this.context = context;
        this.db = db;
    }


    // region Default data for database  -----------------------------------------------
    public void insertDataUser() {
        /**
         * Insert default user data into database
         */
        // Variables
        Model_User[] users = {
                new Model_User(1, "Helen", "Bernal", "0983 775 380", "helen.bernalv@ug.edu.ec", "Femenino", "Estudiante", 1),
                new Model_User(2, "Nefi", "Reyes", "0958 140 795", "nefi.reyest@ug.edu.ec", "Masculino", "Estudiante", 2),
                new Model_User(3, "Renán", "Pérez", "0967 143 109", "rehan.perezb@ug.edu.ec", "Masculino", "Estudiante", 3),
                new Model_User(4, "Vanessa", "Ronquillo", "0978 588 276", "vanessa.ronquillos@ug.edu.ec", "Femenino", "Estudiante", 4)
        };

        Cursor data = db.getReadableDatabase().rawQuery("SELECT * FROM User", null);

        if (data.getCount() == 0) {

            for (Model_User element : users) {
                ContentValues values = new ContentValues();
                values.put("name", element.name);
                values.put("lastname", element.lastname);
                values.put("phone", element.phone);
                values.put("email", element.email);
                values.put("genre", element.genre);
                values.put("occupation", element.occupation);
                values.put("id_credential", element.id_credential);
                db.getWritableDatabase().insert(Tablas.USER, null, values);
            }

            insertDataCredential();
        }

    }

    public void insertDataCredential() {
        /**
         * Insert default credential data into database if table is empty
         */
        // Variables
        Model_Credential[] credentials = {
                new Model_Credential(1, "helen", "1234"),
                new Model_Credential(2, "nefi", "1234"),
                new Model_Credential(3, "renan", "1234"),
                new Model_Credential(4, "vane", "1234"),
        };

        for (Model_Credential element : credentials) {
            ContentValues values = new ContentValues();
            values.put("username", element.username);
            values.put("password", element.password);
            db.getWritableDatabase().insert(Tablas.CREDENTIAL, null, values);
        }

    }
    // endregion


    // region CRUD User  -----------------------------------------------
    public boolean createUser(String name, String lastname, String phone, String email, String genre, String occupation, String username, String password) {
        // Variables
        boolean saveUser = false;
        boolean saveCredential = false;
        int id_credential = 0;

        // create new Credential
        saveCredential = createCredential(username, password);

        if (saveCredential) {

            try {
                // find id_credential
                id_credential = readCredential_IdByUsername(username);

                // create new User
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("lastname", lastname);
                values.put("phone", phone);
                values.put("email", email);
                values.put("genre", genre);
                values.put("occupation", occupation);
                values.put("id_credential", id_credential);
                db.getWritableDatabase().insert(Tablas.USER, null, values);
                saveUser = true;
            } catch (Exception exception) {
                exception.printStackTrace();
                saveUser = false;
            }

        }

        return saveUser;
    }

    public List<String> readDataUser(int id_user) {
        // Variables
        List<String> userData = new ArrayList<>();
        String queryUser = "SELECT u.name, u.lastname, u.phone, u.email, u.genre, u.occupation, c.username, c.password FROM User u INNER JOIN Credential c ON c.id = u.id_credential WHERE u.id = '" + id_user + "'";

        Cursor data = db.getReadableDatabase().rawQuery(queryUser, null);

        if (data != null && data.moveToNext()) {
            for (int i = 0; i <= 7; i++) {
                userData.add(data.getString(i));
            }
        }

        return userData;
    }

    public int readUser_IdByUsername(String username) {
        int id_user = 0;
        String queryUser = "SELECT u.id FROM User u INNER JOIN Credential c ON u.id_credential = c.id WHERE c.username = '" + username + "'";
        Cursor data = db.getReadableDatabase().rawQuery(queryUser, null);

        if (data != null && data.moveToNext()) {
            id_user = data.getInt(0);
        }

        return id_user;
    }

    public boolean updateUser(int id_user, String name, String lastname, String phone, String email, String genre, String occupation, String username, String password) {
        /**
         * Update date based on User id
         */
        boolean updateUser = false;
        boolean updateCredential = false;

        // Update data Credential
        updateCredential = updateCredential(id_user, username, password);


        if (updateCredential) {

            try {
                // Update data User
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("lastname", lastname);
                values.put("phone", phone);
                values.put("email", email);
                values.put("genre", genre);
                values.put("occupation", occupation);
                db.getWritableDatabase().update(Tablas.USER, values, "id = " + id_user, null);
                updateUser = true;
            } catch (Exception exception) {
                exception.printStackTrace();
                updateUser = false;
            }

        }

        return updateUser;
    }

    public boolean deleteDataUser(int id_user) {
        /**
         * Dale data on DataBase based on ID
         */
        boolean deleteDataUser = false;
        boolean deleteDataCredential = false;

        // Delete data Credential
        deleteDataCredential = deleteDataCredential(id_user);

        if (deleteDataCredential) {

            try {
                // Delete data User
                db.getWritableDatabase().delete(Tablas.USER, "id=" + id_user, null);
                deleteDataUser = true;
            } catch (Exception exception) {
                deleteDataUser = false;
            }

        }

        return deleteDataUser;
    }
    // endregion


    // region CRUD Credential  -----------------------------------------------
    public boolean createCredential(String username, String password) {
        // Variables
        boolean createData = false;

        try {
            // Save Credential data
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            db.getWritableDatabase().insert(Tablas.CREDENTIAL, null, values);
            createData = true;
        } catch (Exception exception) {
            exception.printStackTrace();
            createData = false;
        }

        return createData;
    }

    public boolean readCredential(String username, String password) {
        // Variables
        boolean readData = false;
        String queryCredential = "SELECT * FROM Credential c INNER JOIN User u ON c.id = u.id_credential WHERE username = '" + username + "' AND password = '" + password + "'";
        Cursor data = db.getReadableDatabase().rawQuery(queryCredential, null);
//        data != null && data.moveToNext()
        if (data.getCount() != 0) {
            readData = true;
        }

        return readData;
    }

    public boolean readCredential_ByUsername(String username) {
        // Variables
        boolean readData = false;
        String queryCredential = "SELECT * FROM Credential WHERE username = '" + username + "'";

        Cursor data = db.getReadableDatabase().rawQuery(queryCredential, null);

        if (data.getCount() != 0) {
            readData = true;
        }

        return readData;
    }

    public int readCredential_IdByUsername(String username) {
        /**
         * Return Credential id if username exist
         */
        // Variables
        int id_credential = 0;
        String query = "SELECT id FROM Credential WHERE username = '" + username + "'";
        Cursor data = db.getReadableDatabase().rawQuery(query, null);

        if (data != null && data.moveToNext()) {
            id_credential = data.getInt(0);
        }

        return id_credential;
    }

    public boolean updateCredential(int id_user, String username, String password) {
        boolean updateDataCredential = false;

        // Find id_credential by id_user
        String query = "SELECT id_credential FROM User WHERE id = " + id_user;
        Cursor data = db.getReadableDatabase().rawQuery(query, null);

        if (data != null && data.moveToNext()) {

            try {
                // Update data Credential by id
                ContentValues values = new ContentValues();
                values.put("username", username);
                values.put("password", password);
                db.getWritableDatabase().update(Tablas.CREDENTIAL, values, "id = " + data.getInt(0), null);
                updateDataCredential = true;
            } catch (Exception exception) {
                exception.printStackTrace();
                updateDataCredential = false;
            }

        }

        return updateDataCredential;
    }

    public boolean deleteDataCredential(int id_user) {
        boolean deleteDataCredential = false;

        // Find id_credential by id_user
        String query = "SELECT id_credential FROM User WHERE id = " + id_user;
        Cursor data = db.getReadableDatabase().rawQuery(query, null);

        if (data != null && data.moveToNext()) {

            try {
                // Delete data Credential by id
                db.getWritableDatabase().delete(Tablas.CREDENTIAL, "id = " + data.getInt(0), null);
                deleteDataCredential = true;
            } catch (Exception exception) {
                exception.printStackTrace();
                deleteDataCredential = false;
            }

        }

        return deleteDataCredential;
    }
    // endregion

}
