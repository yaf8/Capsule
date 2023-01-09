package com.example.capsule;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FirebaseFirestore db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.view_pager2);
        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager2.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        updateDeleted();

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

                                    Toast.makeText(getApplicationContext(), "Your account has been Deleted", Toast.LENGTH_SHORT).show();
                                    docRef.delete();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
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