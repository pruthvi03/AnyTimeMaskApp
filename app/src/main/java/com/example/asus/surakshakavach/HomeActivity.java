package com.example.asus.surakshakavach;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    int flag = 0;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;

                fAuth = FirebaseAuth.getInstance();
                FirebaseUser user = fAuth.getCurrentUser();
                if (user!=null){
//                    Toast.makeText(HomeActivity.this, "user is logged in", Toast.LENGTH_SHORT).show();
                    intent = new Intent(HomeActivity.this, MainActivity.class);
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getCurrentUser().getUid());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
//                                Toast.makeText(HomeActivity.this, "details are available", Toast.LENGTH_SHORT).show();
                                flag = 1;
                            } else {
                                flag = 2;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    intent = new Intent(HomeActivity.this, Register.class);
                }
                    if (flag == 1){
                        intent = new Intent(HomeActivity.this, MainActivity.class);
                    }
                    else if (flag == 2){
                        intent = new Intent(HomeActivity.this, AddDetail.class);
                    }
                    startActivity(intent);
                    finish();

            }
        },SPLASH_TIME_OUT);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (fAuth.getCurrentUser()!=null){
//            checkUserProfile();
//        }
//    }

    private void checkUserProfile() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(HomeActivity.this, "details are available", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(),AddDetail.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
