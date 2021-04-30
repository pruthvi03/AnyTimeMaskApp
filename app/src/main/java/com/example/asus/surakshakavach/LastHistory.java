package com.example.asus.surakshakavach;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class LastHistory extends AppCompatActivity {

    TextView qty,amountBill,orderTime,orderStatus;
    ImageView qrImage;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_history);

        qty = (TextView)findViewById(R.id.qty);
        amountBill = (TextView)findViewById(R.id.bill);
        orderTime = (TextView)findViewById(R.id.orderTime);
        orderStatus = (TextView)findViewById(R.id.status);
        qrImage = findViewById(R.id.qrPlaceHolder);

        firebaseAuth = FirebaseAuth.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    long lastNo = (dataSnapshot.getChildrenCount());
//                    Toast.makeText(LastHistory.this,lastNo+" " + Integer.toString((int) lastNo), Toast.LENGTH_SHORT).show();
                    String qtyText = dataSnapshot.child(Integer.toString((int) lastNo)).child("qty").getValue().toString();
                    String billText = dataSnapshot.child(Integer.toString((int) lastNo)).child("bill").getValue().toString();
                    String stsText = dataSnapshot.child(Integer.toString((int) lastNo)).child("flag").getValue().toString();
                    String tsText = dataSnapshot.child(Integer.toString((int) lastNo)).child("time_stamp").getValue().toString();

                    qty.setText("Quantity: "+qtyText);
                    amountBill.setText("Bill: "+billText);
                    orderTime.setText("Order Time: "+tsText);
                    if (stsText.equalsIgnoreCase("false")){orderStatus.setText("Status: Pending");}
                    else if (stsText.equalsIgnoreCase("true")){orderStatus.setText("Status: Completed");}


                    String data = userID + "\n" + Integer.toString((int) lastNo) + "\n" + qtyText + "\n" + billText + "\n" + tsText;

                    // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 1000);


                    try {
                        // Getting QR-Code as Bitmap
                        Bitmap bitmap = qrgEncoder.getBitmap();
//                    Toast.makeText(MainActivity.this, "here", Toast.LENGTH_SHORT).show();

                        // Setting Bitmap to ImageView
                        qrImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
//                    Log.v(TAG, e.toString());
                        Toast.makeText(LastHistory.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(LastHistory.this,MainActivity.class));
//        finish();
//    }
}
