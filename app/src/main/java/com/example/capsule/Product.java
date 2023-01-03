package com.example.capsule;

import android.net.Uri;

public class Product {
    private Uri productImage;
    private String productName, productID, productShortDescription, productLongDescription;
    private double productPrice;
    private boolean isExpanded;

    private String imageUrl;

    public Product(String productID, String productName, String productShortDescription, String productLongDescription, double productPrice, String imageUrl) {
        this.productID = productID;
        this.productName = productName;
        this.productShortDescription = productShortDescription;
        this.productLongDescription = productLongDescription;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
        isExpanded = false;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product() {
    }

    public Product(Uri productImage, String productName, String productID, String productShortDescription, String productLongDescription, double productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productID = productID;
        this.productShortDescription = productShortDescription;
        this.productLongDescription = productLongDescription;
        this.productPrice = productPrice;
    }

    public Uri getProductImage() {
        return productImage;
    }

    public void setProductImage(Uri productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductShortDescription() {
        return productShortDescription;
    }

    public void setProductShortDescription(String productShortDescription) {
        this.productShortDescription = productShortDescription;
    }

    public String getProductLongDescription() {
        return productLongDescription;
    }

    public void setProductLongDescription(String productLongDescription) {
        this.productLongDescription = productLongDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
