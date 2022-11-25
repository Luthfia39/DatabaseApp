package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText keyword;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbHelper = new DatabaseHelper(this);

        keyword = findViewById(R.id.input_keyword);
        result = findViewById(R.id.searchResult);

        result.setVisibility(View.GONE);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setVisibility(View.VISIBLE);
                searchProductFromName(keyword.getText().toString());
            }
        });
    }

    private void searchProductFromName(String keyword) {
        try {
            Product product = dbHelper.getProductFromName(keyword);
            result.setText("Nama Produk : "+ product.getName() +
                    "\nHarga : " + String.valueOf(product.getPrice()) + "\n\n");
        } catch (Exception e) {
            result.setText("Data Tidak Ditemukan!");
        }
    }
}