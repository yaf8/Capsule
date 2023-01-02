package com.example.capsule;

import java.util.ArrayList;
import java.util.Objects;

public class Utils {
    private static Utils instance;
    private static ArrayList<Product> allProducts;
    private static  ArrayList<Product> cartProducts;

    private Utils() {
        if (allProducts == null)
            allProducts = new ArrayList<>();

        if (cartProducts == null)
            cartProducts = new ArrayList<>();

        initData();
    }

    private void initData() {


        allProducts.add(new Product("MED1", "Panadol", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED2", "Amoxilin", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED3", "Panadol", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED4", "Amoxilin", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED5", "Panadol", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED6", "Amoxilin", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED7", "Panadol", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED8", "Amoxilin", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED9", "Panadol", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        allProducts.add(new Product("MED10", "Amoxilin", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));

    }

    public static Utils getInstance() {
        if (null != instance)
            return instance;
        else{
            instance = new Utils();
            return instance;
        }
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public Product getProductById(String id){
        for (Product p : allProducts)
        {
            if (Objects.equals(p.getProductID(), id)){
                return p;
            }
        }

        return null;
    }


    ///////////////////////////////////////////////////////////////////////
    String LongDescription = "Panadol contains paracetamol; recognised by the medical profession as effective medication " +
            "for you and your family. Panadol is indicated for: Headache, Colds & Influenza, Backache, Period Pain, Pain of Osteoarthritis, " +
            "Muscle Pain, Toothache, Rheumatic Pain.";
    String url = "https://oneononepharmacy.com/wp-content/uploads/Panadol-Tab-24.jpg";
    //////////////////////////////////////////////////////////////////////
}
