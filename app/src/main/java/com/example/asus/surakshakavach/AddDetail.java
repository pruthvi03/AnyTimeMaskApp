package com.example.asus.surakshakavach;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddDetail extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    EditText firstName,lastName,email;
    Button savebtn;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.emailAddress);
        savebtn = findViewById(R.id.savebtn);

        firebaseAuth = FirebaseAuth.getInstance();



        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()){

                    userID = firebaseAuth.getCurrentUser().getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                    String first = firstName.getText().toString();
                    String last = lastName.getText().toString();
                    String userEmail = email.getText().toString();

                    HashMap<String,Object> user=new HashMap<>();
                    user.put("firstName",first);
                    user.put("lastName",last);
                    user.put("emailAddress",userEmail);
                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            pd.dismiss();
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Data is not inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
