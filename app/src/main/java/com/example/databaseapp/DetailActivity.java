package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView id, name, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String productID = String.valueOf(intent.getIntExtra("PRODUCT-ID", 0));
        String productName = intent.getStringExtra("PRODUCT-NAME").toString();
        String productPrice = String.valueOf(intent.getIntExtra("PRODUCT-PRICE", 0));

        id = findViewById(R.id.txt_id);
        name = findViewById(R.id.txt_name);
        price = findViewById(R.id.txt_price);

        id.setText("ID Produk : " + productID.toString());
        name.setText("Nama Produk : " + productName);
        price.setText("Harga Produk : " + productPrice.toString());

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_update = new Intent(view.getContext(), UpdateActivity.class);
                intent_update.putExtra("PRODUCT", productID);
                startActivity(intent_update);
            }
        });
    }
}