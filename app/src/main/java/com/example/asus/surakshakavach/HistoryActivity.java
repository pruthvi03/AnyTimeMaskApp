package com.example.asus.surakshakavach;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    String userID;

    RecyclerView recyclerView;
    ArrayList<Order> orderArrayList;

    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setTitle("Order History");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        orderArrayList = new ArrayList<Order>();

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                    Order order = dataSnapshot1.getValue(Order.class);
//                    orderArrayList.add(order);
                    String  b = dataSnapshot1.child("bill").getValue().toString();
                    String q = dataSnapshot1.child("qty").getValue().toString();
                    String f = dataSnapshot1.child("flag").getValue().toString();
                    String t = dataSnapshot1.child("time_stamp").getValue().toString();

                    if (f.equals("false")){
                        f="Pending";
                    }else {
                        f="Completed";
                    }

                    Order order = new Order(b,f,q,t);


                    orderArrayList.add(order);

//                    Toast.makeText(HistoryActivity.this, b, Toast.LENGTH_SHORT).show();
                }
//                orderArrayList.add(new Order(12,false,2,"12 34"));
//                orderArrayList.add(new Order(12,false,2,"12 34"));
                orderAdapter = new OrderAdapter(HistoryActivity.this,orderArrayList);
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HistoryActivity.this, "Error in fetching Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
