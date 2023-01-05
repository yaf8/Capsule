package com.example.capsule.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.capsule.Product;
import com.example.capsule.ProductAdapter;
import com.example.capsule.R;
import com.example.capsule.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private RecyclerView recycleCart;
    public ProductAdapter adapter;
    private MaterialButton btnOrder;
    private ImageView capsuleLogo;
    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        recycleCart = view.findViewById(R.id.recycleCart);
        btnOrder = view.findViewById(R.id.btnOrder);
        capsuleLogo = view.findViewById(R.id.CapsuleLogo);

        adapter = new ProductAdapter(getActivity());
        adapter.setProduct(Utils.getInstance().getCartProducts());

        recycleCart.setAdapter(adapter);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 2000);

        capsuleLogo.setOnClickListener(v -> adapter.notifyDataSetChanged());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleCart.setLayoutManager(linearLayoutManager);

        btnOrder.setOnClickListener(v -> initiateOrder());


        // Inflate the layout for this fragment
        return view;
    }

    private void initiateOrder() {

        database = FirebaseDatabase.getInstance();

        UserInfo user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = database.getReference("order/messages/med");
        if (Utils.getCartProducts() != null) {

            for (Product cartProduct : Utils.getCartProducts()) {

                String order = "   User Email : " + user.getEmail() +
                        "   User Name : " + user.getDisplayName() +
                        "   Medicine : " +cartProduct.getProductName();

                myRef.setValue(order);
            }
            Toast.makeText(requireActivity(), "Order Successful", Toast.LENGTH_SHORT).show();
            Utils.getCartProducts().clear();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            }, 2000);

        }

    }
}