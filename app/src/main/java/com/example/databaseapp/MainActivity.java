package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;

    private SharedPreferences mSharedPref;
    private final String sharedPref = "Shared-Preference-Login";
    //    key
    private final String LOGIN_KEY = "login-key";
    //    value
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        cara untuk mendapatkan akses sharedPreferences
        mSharedPref = getSharedPreferences(sharedPref, MODE_PRIVATE);
        isLogin = mSharedPref.getBoolean(LOGIN_KEY, false);

        if (isLogin){
            Intent loginIntent = new Intent(this, ListDataActivity.class);
            startActivity(loginIntent);
        }

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.uname);
                password = findViewById(R.id.pw);

//                pengecekan username dan password yang dimasukkan
                if ((username.getText().toString().equals("admin")) && (password.getText().toString().equals("rahasia"))){
                    isLogin = true;
                    saveLogin();
                    Intent loginIntent = new Intent(view.getContext(), ListDataActivity.class);
                    startActivity(loginIntent);
                }else {
                    isLogin = false;
                    saveLogin();
                }
            }
        });
    }

    //    mengubah value dalam SharedPreferences
    private void saveLogin(){
//        Untuk menyimpan data ke dalam SharedPreferences dibutuhkan class Editor
//        yang bisa didapatkan dari method edit()
        SharedPreferences.Editor editor = mSharedPref.edit();
//        mengambil data
        editor.putBoolean(LOGIN_KEY, isLogin);
//        menyimpan data ke dalam SharedPreferences
        editor.apply();
    }
}