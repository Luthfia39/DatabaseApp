package com.example.databaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListDataActivity extends AppCompatActivity {
    List<Product> products;
    RecyclerView rv;
    DatabaseHelper dbHelper;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        dbHelper = new DatabaseHelper(this);
        products = dbHelper.getAllProduct();
        adapter = new ProductAdapter(this, products);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_search = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent_search);
            }
        });

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_insert = new Intent(view.getContext(), InsertActivity.class);
                startActivity(intent_insert);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }

//    item touch helper digunakan untuk menambah fungsi swipe ketika menghapus item
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
//        method ini dipanggil ketika item moved
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
//        method ini dipanggil ketika item di-swipe
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Product deletedProduct = products.get(position);
            products.remove(position);
            dbHelper.deleteProduct(deletedProduct);
            Toast.makeText(ListDataActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
//            adapter.notifyItemRemoved(position);
//            Snackbar.make(rv, deletedProduct.getName(), Snackbar.LENGTH_LONG).
//                    setAction("Undo", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    products.add(position, deletedProduct);
//                    adapter.notifyItemInserted(position);
//                }
//            });
        }
    };
}