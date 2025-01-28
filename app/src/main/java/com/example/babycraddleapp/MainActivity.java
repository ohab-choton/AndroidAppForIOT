package com.example.babycraddleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText edtUName,edtUsemail,edtUspass;
    Button btnRegister, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUName = findViewById(R.id.editTextUserName);
        edtUsemail = findViewById(R.id.editTextUserEmail);
        edtUspass = findViewById(R.id.editTextUserPassword);

        btnRegister = findViewById(R.id.buttonRegister);
        btnLogin = findViewById(R.id.buttonLogIN);

        // Initilaize Firebase Database variable/instances
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userNameRef = database.getReference("User").child("Name");
        DatabaseReference userEmailRef = database.getReference("User").child("Email");
        DatabaseReference userPasswordRef = database.getReference("User").child("Password");

        // User Registratiion

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Write Database
                String uname = edtUName.getText().toString().trim();
                String uemail = edtUsemail.getText().toString().trim();
                String upass = edtUspass.getText().toString().trim();

                if(uname.isEmpty() || uemail.isEmpty() || upass.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"One or More Field is empty, Kindly" +
                            "input all fields",Toast.LENGTH_LONG).show();
                }
                else {

                    userNameRef.setValue(uname);
                    userEmailRef.setValue(uemail);
                    userPasswordRef.setValue(upass);

                    Toast.makeText(MainActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();
                }

            }
        });



        // Move to LogIN activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

    }
}