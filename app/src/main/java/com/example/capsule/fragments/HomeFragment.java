package com.example.capsule.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule.ProductAdapter;
import com.example.capsule.R;
import com.example.capsule.TempActivity;
import com.example.capsule.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    public static final String PRODUCT_ID_KEY = "productID";

    private RecyclerView recycleVerticalItems;
    public static ProductAdapter adapter;
    private ImageButton imgBtnSearch;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recycleVerticalItems = view.findViewById(R.id.recycleVerticalItems);
        imgBtnSearch = view.findViewById(R.id.imgBtnSearch);


        adapter = new ProductAdapter(getActivity());
        adapter.setProduct(Utils.getInstance().getAllProducts());

        recycleVerticalItems.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 2000);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleVerticalItems.setLayoutManager(linearLayoutManager);


        imgBtnSearch.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TempActivity.class));
        });


        return view;
    }

    private void uploadImage() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = simpleDateFormat.format(now);



    }
}