package com.example.capsule;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> products = new ArrayList<>();
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtProductName.setText(products.get(position).getProductName());
        holder.txtShortDescription.setText(products.get(position).getProductShortDescription());
        Glide.with(context)
                .asBitmap()
                .load(products.get(position).getImageUrl())
                .into(holder.imgProduct);


        holder.parentLayout.setOnClickListener(v -> {
            Toast.makeText(context, products.get(position).getProductName(), Toast.LENGTH_SHORT).show();
        });
    }

    public void setProduct(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtProductName;
        private TextView txtShortDescription;
        private ImageView imgProduct;
        private CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtShortDescription = itemView.findViewById(R.id.txtShortDescription);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            imgProduct = itemView.findViewById(R.id.imgProduct);

        }
    }
}