package com.example.babycraddleapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    EditText edtSignUEmail, edtSignUpasswrd;
    Button btnSignUILogin, btnSignUIRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSignUEmail = findViewById(R.id.editTxtSignUIEmail);
        edtSignUpasswrd = findViewById(R.id.editTxtSignUIPassword);

        btnSignUILogin = findViewById(R.id.buttonSignUISignIN);
        btnSignUIRegister = findViewById(R.id.buttonSignUISingUp);

        // store database saved email and password in global String Array variable
        final String databaseUserEmail[] = new String[1];
        final String databaseUserPassword[] = new String[1];


        // Initilaize Firebase Database variable/instances
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userEmailRef = database.getReference("User").child("Email");
        DatabaseReference userPasswordRef = database.getReference("User").child("Password");





        // Check email and password for Login Access

        btnSignUILogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // READ email and password from Firebase database

                // Read email from the database
                userEmailRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        databaseUserEmail[0] = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + databaseUserEmail[0]);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


                // Read password from the database
                userPasswordRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        databaseUserPassword[0] = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + databaseUserPassword[0]);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


                String userTypeEmail = edtSignUEmail.getText().toString().trim();
                String userTypePassword = edtSignUpasswrd.getText().toString().trim();

                if(userTypeEmail.isEmpty() || userTypePassword.isEmpty())
                {
                    Toast.makeText(SignInActivity.this,"One or More Field is empty, Kindly" +
                            "input all fields",Toast.LENGTH_LONG).show();

                }
                //if(databaseUserEmail[0].equals(userTypeEmail) && databaseUserPassword[0].equals(userTypePassword) )
                else{

                    startActivity(new Intent(SignInActivity.this, MainFunctionApp.class));
                   // Toast.makeText(SignInActivity.this,"SignIN Successful",Toast.LENGTH_SHORT).show();

                }
            }
        });

        // Move to Registration User UI Layout
        btnSignUIRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        });
    }
}