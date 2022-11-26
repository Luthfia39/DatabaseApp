package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    List<Product> products;
    EditText productName, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbHelper = new DatabaseHelper(this);

        productName = findViewById(R.id.input_name);
        productPrice = findViewById(R.id.input_price);

        Intent intent = getIntent();
        String id = intent.getStringExtra("PRODUCT_ID").toString();
        String name = intent.getStringExtra("PRODUCT_NAME").toString();
        String price = intent.getStringExtra("PRODUCT_PRICE").toString();

        productName.setText(name);
        productPrice.setText(price);

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(Integer.parseInt(id));
                Intent intent = new Intent(view.getContext(), ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateData(int id){
        products = dbHelper.getAllProduct();

        for (Product item : products){
            if (item.getId() == id){

                Product product = new Product();
                product.setId(id);
                product.setName(productName.getText().toString());
                product.setPrice(Integer.parseInt(productPrice.getText().toString()));

                dbHelper.updateProduct(product);
                Toast.makeText(UpdateActivity.this, "Update Success", Toast.LENGTH_LONG).show();
            }
        }
    }
}