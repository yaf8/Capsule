package com.example.capsule.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capsule.ProductAdapter;
import com.example.capsule.R;
import com.example.capsule.Utils;
import com.google.android.material.button.MaterialButton;

public class OrderFragment extends Fragment {

    RecyclerView recycleCart;
    MaterialButton btnOrder;
    ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        recycleCart = view.findViewById(R.id.recycleCart);
        btnOrder = view.findViewById(R.id.btnOrder);

        adapter = new ProductAdapter(getActivity());
        adapter.setProduct(Utils.getInstance().getCartProducts());

        recycleCart.setAdapter(adapter);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 2000);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleCart.setLayoutManager(linearLayoutManager);

        btnOrder.setOnClickListener(v -> {




        });


        // Inflate the layout for this fragment
        return view;
    }
}