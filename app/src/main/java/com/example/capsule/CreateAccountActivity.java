package com.example.capsule;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    EditText edtEmail, edtFirstName, edtLastName, edtPhoneNumber, edtPassword, edtConfirmPassword;
    Button btnCreateAccount;
    TextView txtSignIn;
    ProgressBar progressBar;
    FirebaseFirestore db, db2;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseFirestore = FirebaseFirestore.getInstance();

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
        saveToFirebaseFirestore(firstName, lastName, phoneNumber, email);

        saveUserData();
        auth = FirebaseAuth.getInstance();
        auth.signOut();


    }

    private void saveUserData(){


        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();

        //------------------------------------Save_Data----------------------------

        Map<String, Object> user = new HashMap<>();
        user.put("email", edtEmail.getText().toString());
        user.put("isAdmin", false);
        user.put("isDeleted", false);
        user.put("password", edtPassword.getText().toString());
        user.put("PhoneNumber", edtPhoneNumber.getText().toString());
        user.put("firstName", edtFirstName.getText().toString());
        user.put("lastName", edtLastName.getText().toString());
        user.put("userType", "Customer");
        user.put("profileURL", "https://icon-library.com/images/user-icon-jpg/user-icon-jpg-8.jpg");

        db.collection("Accounts/").document(edtEmail.getText().toString())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateAccountActivity.this, "Successfully saved", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                        finish();
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        //------------------------------------Save_Data----------------------------
    }

    private void saveToFirebaseFirestore(String firstName, String lastName, String phoneNumber, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("phoneNumber", phoneNumber);
        user.put("email", email);

        firebaseFirestore.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateAccountActivity.this, "Failed to save data", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void createAccountInFirebase(String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()){

                    //Todo: test profile update
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(edtFirstName.getText().toString() + " " + edtLastName.getText().toString())
                            .build();
                    assert user != null;
                    user.updateProfile(profile);

                    Toast.makeText(CreateAccountActivity.this, "Successfully created account", Toast.LENGTH_SHORT).show();



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