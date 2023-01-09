package com.example.capsule.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule.LoginActivity;
import com.example.capsule.MainActivity;
import com.example.capsule.ProductAdapter;
import com.example.capsule.R;
import com.example.capsule.TempActivity;
import com.example.capsule.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {

    public static final String PRODUCT_ID_KEY = "productID";

    private RecyclerView recycleVerticalItems;
    public static ProductAdapter adapter;
    private ImageButton imgBtnSearch;
    private StorageReference storageReference;
    private ImageView capsuleLogo;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recycleVerticalItems = view.findViewById(R.id.recycleVerticalItems);
        imgBtnSearch = view.findViewById(R.id.imgBtnSearch);
        capsuleLogo = view.findViewById(R.id.capsuleLogo);


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

        capsuleLogo.setOnClickListener(v -> adapter.notifyDataSetChanged());

        updateDeleted();

        return view;
    }


    private void updateDeleted() {

        UserInfo userInfo = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //CollectionReference colRef = db.collection("Accounts");

        //-----------------------------------Read_Data-----------------------------
        System.out.println("user.getEmail() : " + user.getEmail());
        DocumentReference docRef = db.collection("Accounts/").document(Objects.requireNonNull(user.getEmail()));

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("Data After Deleted : ", "Current data: " + snapshot.getData());

                    if (Boolean.TRUE.equals(snapshot.getBoolean("isDeleted"))) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if(firebaseUser != null) {
                            firebaseUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    System.out.println("Is user deleted : " + firebaseUser == null);

                                    Toast.makeText(getActivity(), "Your account has been Deleted", Toast.LENGTH_SHORT).show();
                                    docRef.delete();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    firebaseAuth.signOut();
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}