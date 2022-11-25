package com.example.databaseapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> products;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> products) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.mName.setText(product.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_detail = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent_detail.putExtra("PRODUCT-ID", product.getId());
                intent_detail.putExtra("PRODUCT-NAME", product.getName());
                intent_detail.putExtra("PRODUCT-PRICE", product.getPrice());
                holder.itemView.getContext().startActivity(intent_detail);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mName = itemView.findViewById(R.id.txt_name);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
