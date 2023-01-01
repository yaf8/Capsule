package com.example.capsule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capsule.LoginActivity;
import com.example.capsule.MainActivity;
import com.example.capsule.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    TextView txtBtnLogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtBtnLogout = view.findViewById(R.id.txtBtnLogout);

        txtBtnLogout.setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut(); //signout from fire base if login with custom

            Toast.makeText(getActivity(), "Sign out", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });



        // Inflate the layout for this fragment
        return view;
    }

}