package com.example.capsule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capsule.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity {



    ImageView imgProduct;
    TextView txtProductName, txtShortDescription, txtLongDescription, txtProductPrice;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initView();

        Intent intent = getIntent();
        if (intent != null) {

            String productID = intent.getStringExtra(HomeFragment.PRODUCT_ID_KEY);

            if (productID != null){
                Product incomingProduct = Utils.getInstance().getProductById(productID);

                if (incomingProduct != null){
                    setData(incomingProduct);
                    handleAddtoCart(incomingProduct);
                }
            }
        }


    }


    private void handleAddtoCart(Product product) {
        ArrayList<Product> cartProducts = Utils.getInstance().getCartProducts();

        boolean existInCart = false;

        /*
        for (Product p: cartProducts)
        {
            if (Objects.equals(p.getProductID(), product.getProductID()))
            {
                existInCart = false;
            }
        }

        /*
            if (existInCart){
                btnAdd.setEnabled(false);
            } else {

            }*/
        btnAdd.setOnClickListener(v -> {
            if (Utils.getInstance().addToCart(product)){
                Toast.makeText(this, "Product Added to Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setData(Product product) {
        txtProductName.setText(product.getProductName());
        txtShortDescription.setText(product.getProductShortDescription());
        txtLongDescription.setText(product.getProductLongDescription());
        txtProductPrice.setText(String.valueOf(product.getProductPrice()));
        Glide.with(this)
                .asBitmap()
                .load(product.getImageUrl())
                .into(imgProduct);

    }

    protected void initView(){
        imgProduct = findViewById(R.id.imgProduct);
        txtProductName = findViewById(R.id.txtProductName);
        txtShortDescription = findViewById(R.id.txtShortDescription);
        txtLongDescription = findViewById(R.id.txtLongDescription);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        btnAdd = findViewById(R.id.btnAdd);
    }

}