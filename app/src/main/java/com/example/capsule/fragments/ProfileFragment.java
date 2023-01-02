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

import com.example.capsule.HelpAndSupportActivity;
import com.example.capsule.LoginActivity;
import com.example.capsule.MainActivity;
import com.example.capsule.PrivacyPolicyActivity;
import com.example.capsule.R;
import com.example.capsule.TermAndConditionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


public class ProfileFragment extends Fragment {

    TextView  txtEmail, txtBtnPrivacy, txtBtnHelp, txtBtnTerm, txtBtnLogout;
    public static TextView txtFullName;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //txtEmail.setText("firebaseUser.getEmail()");

        txtFullName = view.findViewById(R.id.txtFullName);
        txtEmail = view.findViewById(R.id.txtEmailAddress);
        txtBtnPrivacy = view.findViewById(R.id.txtBtnPrivacy);
        txtBtnHelp = view.findViewById(R.id.txtBtnHelp);
        txtBtnTerm = view.findViewById(R.id.txtBtnTerm);
        txtBtnLogout = view.findViewById(R.id.txtBtnLogout);


        txtBtnLogout.setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut(); //signout from fire base if login with custom

            Toast.makeText(getActivity(), "Sign out", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        txtBtnPrivacy.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class));
        });

        txtBtnHelp.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), HelpAndSupportActivity.class));
        });

        txtBtnTerm.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TermAndConditionActivity.class));
        });



        // Inflate the layout for this fragment
        return view;
    }

}