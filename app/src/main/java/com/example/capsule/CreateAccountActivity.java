package com.example.capsule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    EditText edtEmail, edtFirstName, edtLastName, edtPhoneNumber, edtPassword, edtConfirmPassword;
    Button btnCreateAccount;
    TextView txtSignIn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        txtSignIn = findViewById(R.id.txtSignIn);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        btnCreateAccount.setOnClickListener(v -> createAccount());

        txtSignIn.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

    }

    private void createAccount() {
        String firstName, lastName, phoneNumber, email, password, confirmPassword;
        firstName = edtFirstName.getText().toString();
        lastName = edtLastName.getText().toString();
        phoneNumber = edtPhoneNumber.getText().toString();
        email  = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();

        boolean isValidated = validateData(firstName, lastName, phoneNumber, email, password, confirmPassword);
        if (!isValidated){
            return;
        }

        createAccountInFirebase(email, password);

    }

    private void createAccountInFirebase(String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()){

                    //create account is done
                    Toast.makeText(CreateAccountActivity.this, "Successfully created account", Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                    finish();

                } else {

                    //failure
                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void changeInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            btnCreateAccount.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            btnCreateAccount.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateData(String firstName, String lastName, String phoneNumber, String email, String password, String confirmPassword){

        if (firstName == null){
            edtFirstName.setError("Name is empty");
            return false;
        }

        if (lastName == null){
            edtLastName.setError("Name is empty");
            return false;
        }

        if(phoneNumber.length() < 10 && !Patterns.PHONE.matcher(phoneNumber).matches()){
            edtPhoneNumber.setError("Invalid phone");
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Email is invalid");
            return false;
        }

        if (password.length() < 8){
            edtPassword.setError("Password length is invalid");
            return false;
        }

        if (!password.equals(confirmPassword)){
            edtConfirmPassword.setError("Password not matched");
            return false;
        }

        return true;
    }

}