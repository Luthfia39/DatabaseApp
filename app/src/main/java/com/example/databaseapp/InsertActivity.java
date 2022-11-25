package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText productName, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        dbHelper = new DatabaseHelper(this);

        productName = findViewById(R.id.input_name);
        productPrice = findViewById(R.id.input_price);

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                Intent intent = new Intent(view.getContext(), ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertData() {
        Product product = new Product();
        product.setName(productName.getText().toString());
        product.setPrice(Integer.parseInt(productPrice.getText().toString()));

        dbHelper.insertProduct(product);
        Toast.makeText(InsertActivity.this, "Insert Success", Toast.LENGTH_SHORT).show();
    }
}