package com.example.capsule.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capsule.Product;
import com.example.capsule.ProductActivity;
import com.example.capsule.ProductAdapter;
import com.example.capsule.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recycleVerticalItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recycleVerticalItems = view.findViewById(R.id.recycleVerticalItems);

        
        ArrayList<Product> productsList = new ArrayList<>();

        productsList.add(new Product("Panadol", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Amoxilin", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Panadol", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Amoxilin", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Panadol", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Amoxilin", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Panadol", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Amoxilin", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Panadol", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));
        productsList.add(new Product("Amoxilin", "MED1", "PANADOL 500mg FILM-COATED TABLETS", LongDescription, 50.13, url));

        ProductAdapter adapter = new ProductAdapter(getActivity());
        adapter.setProduct(productsList);

        recycleVerticalItems.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleVerticalItems.setLayoutManager(linearLayoutManager);



        return view;
    }

    String LongDescription = "Panadol contains paracetamol; recognised by the medical profession as effective medication " +
            "for you and your family. Panadol is indicated for: Headache, Colds & Influenza, Backache, Period Pain, Pain of Osteoarthritis, " +
            "Muscle Pain, Toothache, Rheumatic Pain.";
    String url = "https://oneononepharmacy.com/wp-content/uploads/Panadol-Tab-24.jpg";
}