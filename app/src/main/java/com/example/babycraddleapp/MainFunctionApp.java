package com.example.babycraddleapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainFunctionApp extends AppCompatActivity {
    TextView txtSoundfreq, txtBabyPeelvl, txtAIsugstn, txtBuzzerCtrlAIsgtn;
    Button btnUpdate, btnLogout;

    // Initilaize Firebase Database variable/instances
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference babyScreamingRef = database.getReference("Situation").child("Sound");
    DatabaseReference babyPeeLevelRef = database.getReference("Situation").child("PeeL");
    DatabaseReference servoRef = database.getReference("Situation").child("servo");
    DatabaseReference buzzerRef = database.getReference("Situation").child("buzzer");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_function_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtSoundfreq = findViewById(R.id.textViewBabyScremaingLevel);
        txtBabyPeelvl = findViewById(R.id.textViewBabyPeeLevel);
        txtAIsugstn = findViewById(R.id.textViewAISuggestion);
        txtBuzzerCtrlAIsgtn = findViewById(R.id.textViewAIsugstnBuzzerCtrl);

        btnUpdate = findViewById(R.id.buttonRefresh);
        btnLogout = findViewById(R.id.buttonLogOut);

        // store database saved email and password in global String Array variable
        final String babyScreamingFrequency[] = new String[1];
        final String babyPeeLevel[] = new String[1];



        //default value for pee level
        //babyPeeLevelRef.setValue("1");
       // servoRef.setValue("0");
       // buzzerRef.setValue("0");

        // READ saved data  from Firebase database

       /* // Read baby screaming frequency from the database
        babyScreamingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                babyScreamingFrequency[0] = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + babyScreamingFrequency[0]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // Read pee leve of Baby from the database
        babyPeeLevelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                babyPeeLevel[0] = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + babyPeeLevel[0]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
*/




       // Float babyScreamFreqncy = Float.parseFloat(babyScreamingFrequency[0]);

       // Float babyPeelvl = Float.parseFloat( babyPeeLevel[0]);
        // Decision tree algorithm
        /*if(babyScreamFreqncy >= 140  && babyScreamFreqncy<=230)
        {

            servoRef.setValue("0");
            txtAIsugstn.setText("Baby is in Normal Situation. No need to move craddle as fast");
        }
        else if(babyScreamFreqncy >= 231  && babyScreamFreqncy<=500)
        {
            servoRef.setValue("1");
            txtAIsugstn.setText("Baby is screamming situation. So, craddle is moving to make comfortable for baby");
        }


        // control buzzer using Decision tree
        if(babyPeelvl >= 0  && babyPeelvl<=2)
        {

            buzzerRef.setValue("0");
            txtBuzzerCtrlAIsgtn.setText("Baby diaper is ok. No need to change");
        }
        else
        {
            buzzerRef.setValue("1");
            txtBuzzerCtrlAIsgtn.setText("Baby diaper is not ok, something is happening. Kindly change it as soon as possible to " +
                    "make comfort for the baby");
        }*/



        // Read baby screaming frequency from the database
        babyScreamingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                babyScreamingFrequency[0] = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + babyScreamingFrequency[0]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // Read pee leve of Baby from the database
        babyPeeLevelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                babyPeeLevel[0] = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + babyPeeLevel[0]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        txtSoundfreq.setText(babyScreamingFrequency[0]);
        txtBabyPeelvl.setText(babyPeeLevel[0]);

        // Refresh

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float babyScreamFreqncy = Float.parseFloat(babyScreamingFrequency[0]);
                Float babyPeelvl = Float.parseFloat( babyPeeLevel[0]);
                txtSoundfreq.setText( babyScreamingFrequency[0]);
                txtBabyPeelvl.setText(babyPeeLevel[0]);
                
                // Decision tree algorithm
                if(babyScreamFreqncy ==0)
                {
                    servoRef.setValue("0");
                    txtAIsugstn.setText("Baby is in Normal Situation. No need to move craddle as fast");
                }
                else if(babyScreamFreqncy == 1)
                {
                    servoRef.setValue("1");
                    txtAIsugstn.setText("Baby is screamming situation. So, craddle is moving to make comfortable for baby");
                }
                // control buzzer using Decision tree
                if(babyPeelvl >=1000  && babyPeelvl<=1023)
                {
                    buzzerRef.setValue("0");
                    txtBuzzerCtrlAIsgtn.setText("Baby diaper is ok. No need to change");
                }
                else
                {
                    buzzerRef.setValue("1");
                    txtBuzzerCtrlAIsgtn.setText("Baby diaper is not ok, something is happening. Kindly change it as soon as possible to " +
                            "make comfort for the baby");
                }
            }
        });





        // LogOUT

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFunctionApp.this,SignInActivity.class));
            }
        });

    }
}